import json
import logging

from src.core.config import TELEGRAM_BOT_TOKEN, ADMINS_CHAT_IDS
from src.providers.apiProvider import API
from src.providers.logsProvider import Logger
from src.providers.functionsProvider import isAdmin
from telegram import Update, ReplyKeyboardMarkup, InlineKeyboardButton, InlineKeyboardMarkup
from telegram.ext import Updater, CommandHandler, CallbackContext, ConversationHandler, MessageHandler, CallbackQueryHandler, Filters, PicklePersistence
from telegram_bot_pagination import InlineKeyboardPaginator


logger = logging.getLogger(__name__)

QUESTION2, QUESTION3, QUESTION4, QUESTION5, QUESTION6, QUESTION7, QUESTION8, QUESTION9, QUESTION10, RESULT = range(10)

ANSWER_REGEXP = r'\([a-d]{1}\) .*'

class Bot():
    def __init__(self):
        self.api = API()
        self.logs = Logger()
        self.attributes = []
        self.logsData = []
        self.questions = []

    def startHandler(self, update: Update, context: CallbackContext) -> None:
        if isAdmin(update):
            reply_keyboard = [
                ['{0} Telegram ID'.format(u"\u2699\ufe0f")],
                ['{0} Помощь'.format(u"\u2753")],
                ['{0} Сайт проекта'.format(u"\U0001f310")],
                ['{0} GitHub'.format(u"\U0001f525")]
            ]
        else:
            reply_keyboard = [
                ['{0} Помощь'.format(u"\u2753")],
                ['{0} Сайт проекта'.format(u"\U0001f310")],
                ['{0} GitHub'.format(u"\U0001f525")]
            ]
        welcomeMessage = "{0} Привет, <b>{1}</b>!\n\nМеня зовут Олег, я бот проекта <b>Webtice</b>, разработанного студентами <b>РТУ МИРЭА</b>.\n\nСписок моих команд можно увидеть, отправив мне команду /help".format(
            u"\U0001f44b", update.message.chat.first_name)
        update.message.reply_html(welcomeMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=False))

        self.logs.addLog(update)

    def helpHandler(self, update: Update, context: CallbackContext) -> None:
        helpMessage = "<b>Список моих команд</b>:\n/start - начать работу со мной\n/help - показать список моих команд\n/tag <code>тег</code> - посмотреть информацию о HTML теге\n/css <code>св-во</code> - посмотреть информацию о CSS св-ве\n/tests - посмотреть список доступных тестов"
        update.message.reply_html(helpMessage)
        self.logs.addLog(update)

    def cssHandler(self, update: Update, context: CallbackContext) -> None:
        if len(context.args) == 1:
            style = context.args[0]
            styleInfo = self.api.getCssTagInfo(style)
            update.message.reply_html(
                f'<b>Свойство:</b> <code>{styleInfo["name"]}</code>\n\n<b>Описание:</b> {styleInfo["description"]}\n\n<b>Синтаксис:</b> <code>{styleInfo["syntax"]}</code>')
            self.logs.addLog(update)
        else:
            update.effective_message.reply_html(
                "Ошибка. Используйте /css <code>св-во</code>")
            self.logs.addLog(update)

    def tagHandler(self, update: Update, context: CallbackContext) -> None:
        if len(context.args) == 1:
            tag = f'<{context.args[0]}>'
            tagInfo = self.api.getHtmlTagInfo(tag)

            self.attributes = []
            attributesMessage = ''

            for i in range(len(tagInfo['attributes'])):
                if i % 5 != 0 or i == 0:
                    attributesMessage += f'<b>Свойство:</b> <code>{tagInfo["attributes"][i]["attributeName"]}</code>\n<b>Описание:</b> {tagInfo["attributes"][i]["description"]}\n\n'
                else:
                    self.attributes.append(attributesMessage)
                    attributesMessage = ''

            if attributesMessage != '':
                self.attributes.append(attributesMessage)

            update.message.reply_html(
                f'<b>Тег:</b> <code>{tagInfo["name"]}</code>\n\n<b>Описание:</b> {tagInfo["description"]}')
            paginator = InlineKeyboardPaginator(
                len(self.attributes), data_pattern='attr#{page}')
            update.effective_message.reply_html(
                text=self.attributes[0], reply_markup=paginator.markup)

            self.logs.addLog(update)
        else:
            update.effective_message.reply_html(
                "Ошибка. Используйте /tag <code>тег</code>")
            self.logs.addLog(update)

    def attrPageCallback(self, update: Update, context: CallbackContext) -> None:
        query = update.callback_query
        query.answer()

        page = int(query.data.split('#')[1])

        paginator = InlineKeyboardPaginator(
            len(self.attributes), current_page=page, data_pattern='attr#{page}')

        query.edit_message_text(
            text=self.attributes[page - 1], reply_markup=paginator.markup, parse_mode='HTML')

    def testsHandler(self, update: Update, context: CallbackContext) -> None:
        reply_keyboard = [
            ['Тема 1', 'Тема 2', 'Тема 3']
        ]
        update.message.reply_html("Нажмите на кнопку, чтобы пройти тест", reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        self.logs.addLog(update)

    def startTestHandler(self, update: Update, context: CallbackContext) -> int:
        self.questions = self.api.getQuestions()

        reply_keyboard = [
            [f"(a) {self.questions[0]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[0]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[0]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[0]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"1. {self.questions[0]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[0]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[0]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[0]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[0]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)

        context.user_data['score'] = 0
        for i in range(len(self.questions[0]['answers'])):
            if self.questions[0]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[0]['answers'][i]['answerName']

        return QUESTION2

    # Next ten function is very stupid test system code, but actually I dont care about that
    # because I dont recieve any money for that ¯\_(ツ)_/¯

    def questionTwoHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[1]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[1]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[1]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[1]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"2. {self.questions[1]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[1]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[1]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[1]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[1]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[1]['answers'])):
            if self.questions[1]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[1]['answers'][i]['answerName']
        return QUESTION3

    def questionThreeHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[2]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[2]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[2]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[2]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"3. {self.questions[2]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[2]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[2]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[2]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[2]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[2]['answers'])):
            if self.questions[2]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[2]['answers'][i]['answerName']
        return QUESTION4

    def questionFourHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[3]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[3]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[3]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[3]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"4. {self.questions[3]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[3]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[3]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[3]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[3]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[3]['answers'])):
            if self.questions[3]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[3]['answers'][i]['answerName']
        return QUESTION5

    def questionFiveHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[4]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[4]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[4]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[4]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"5. {self.questions[4]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[4]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[4]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[4]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[4]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[4]['answers'])):
            if self.questions[4]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[4]['answers'][i]['answerName']
        return QUESTION6

    def questionSixHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[5]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[5]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[5]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[5]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"6. {self.questions[5]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[5]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[5]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[5]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[5]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[5]['answers'])):
            if self.questions[5]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[5]['answers'][i]['answerName']
        return QUESTION7

    def questionSevenHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[6]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[6]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[6]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[6]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"7. {self.questions[6]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[6]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[6]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[6]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[6]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[6]['answers'])):
            if self.questions[6]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[6]['answers'][i]['answerName']
        return QUESTION8

    def questionEightHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[7]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[7]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[7]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[7]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"8. {self.questions[7]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[7]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[7]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[7]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[7]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[7]['answers'])):
            if self.questions[7]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[7]['answers'][i]['answerName']
        return QUESTION9

    def questionNineHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[8]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[8]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[8]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[8]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"9. {self.questions[8]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[8]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[8]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[8]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[8]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[8]['answers'])):
            if self.questions[8]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[8]['answers'][i]['answerName']
        return QUESTION10

    def questionTenHandler(self, update: Update, context: CallbackContext) -> int:
        reply_keyboard = [
            [f"(a) {self.questions[9]['answers'][0]['answerName']}"],
            [f"(b) {self.questions[9]['answers'][1]['answerName']}"],
            [f"(c) {self.questions[9]['answers'][2]['answerName']}"],
            [f"(d) {self.questions[9]['answers'][3]['answerName']}"],
        ]

        questionMessage = f"10. {self.questions[9]['questionName']}\n\nВарианты ответов:"
        questionAnswerMessage = f"(a) {self.questions[9]['answers'][0]['answerName']}\n"
        questionAnswerMessage += f"(b) {self.questions[9]['answers'][1]['answerName']}\n"
        questionAnswerMessage += f"(c) {self.questions[9]['answers'][2]['answerName']}\n"
        questionAnswerMessage += f"(d) {self.questions[9]['answers'][3]['answerName']}"
        update.message.reply_text(questionMessage, reply_markup=ReplyKeyboardMarkup(
            reply_keyboard, resize_keyboard=True, one_time_keyboard=True))
        update.message.reply_text(questionAnswerMessage)
        
        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        for i in range(len(self.questions[9]['answers'])):
            if self.questions[9]['answers'][i]['is_right'] == True:
                context.user_data['last_correct'] = self.questions[9]['answers'][i]['answerName']
        return RESULT

    def testResultHandler(self, update: Update, context: CallbackContext) -> int:

        if update.message.text[4:] in context.user_data['last_correct']:
            context.user_data['score'] += 1

        resultMessage = f"Ваш результат: {context.user_data['score']}/10"
        update.message.reply_text(resultMessage, reply_markup=None)

        return ConversationHandler.END

    def cancelTestHandler(self, update: Update, context: CallbackContext) -> int:
        update.message.reply_html(
            "Вы прекратили проходить тест, чтобы пройти текст заново введите /tests")
        self.logs.addLog(update)
        return ConversationHandler.END

    def gitHubHandler(self, update: Update, context: CallbackContext) -> None:
        update.message.reply_html(
            "{0} <a href='https://github.com/maxcore25/FrontEndStudy'>GitHub проекта</a>".format(u"\U0001f525"))
        self.logs.addLog(update)

    def siteHandler(self, update: Update, context: CallbackContext) -> None:
        update.message.reply_html(
            "{0} <a href='https://webtice.ru/'>Сайт проекта</a>".format(u"\U0001f310"))
        self.logs.addLog(update)

    def getIdHandler(self, update: Update, context: CallbackContext) -> None:
        if isAdmin(update):
            update.message.reply_text(
                f'Ваш telegram id - {update.message.chat.id}')
        else:
            update.message.reply_text("У вас нет доступа к этой команде!")
        self.logs.addLog(update)

    def logsHandler(self, update: Update, context: CallbackContext) -> None:
        if isAdmin(update):
            data = self.logs.getLogs()

            self.logsData = []
            logsMessage = ''

            for i in range(len(data)):
                if i % 25 != 0 or i == 0:
                    logsMessage += f'Запись #{data[i].id}. Время - {data[i].time}. От - {data[i].chatid}/{data[i].username}. Команда - {data[i].command}. Админ - {data[i].isAdmin}\n\n'
                else:
                    self.logsData.append(logsMessage)
                    logsMessage = ''

            if logsMessage != '':
                self.logsData.append(logsMessage)

            paginator = InlineKeyboardPaginator(
                len(self.logsData), data_pattern='logs#{page}')
            update.effective_message.reply_html(
                text=self.logsData[0], reply_markup=paginator.markup)

        else:
            update.message.reply_text("У вас нет доступа к этой команде!")

        self.logs.addLog(update)

    def logsPageCallback(self, update: Update, context: CallbackContext) -> None:
        query = update.callback_query
        query.answer()

        page = int(query.data.split('#')[1])

        paginator = InlineKeyboardPaginator(
            len(self.logsData), current_page=page, data_pattern='logs#{page}')

        query.edit_message_text(
            text=self.logsData[page - 1], reply_markup=paginator.markup)

    def run(self):
        persistence = PicklePersistence(filename='storage.pickle')
        updater = Updater(token=TELEGRAM_BOT_TOKEN,
                          use_context=True, persistence=persistence)
        dispatcher = updater.dispatcher

        dispatcher.add_handler(CommandHandler('start', self.startHandler))
        dispatcher.add_handler(CommandHandler('help', self.helpHandler))
        dispatcher.add_handler(CommandHandler('tests', self.testsHandler))
        dispatcher.add_handler(MessageHandler(Filters.regex(
            '^{0} Помощь$'.format(u"\u2753")), self.helpHandler))
        dispatcher.add_handler(CommandHandler('getid', self.getIdHandler))
        dispatcher.add_handler(MessageHandler(Filters.regex(
            '^{0} Telegram ID$'.format(u"\u2699\ufe0f")), self.getIdHandler))

        dispatcher.add_handler(CommandHandler('tag', self.tagHandler))
        dispatcher.add_handler(CallbackQueryHandler(
            self.attrPageCallback, pattern='^attr#'))
        dispatcher.add_handler(CommandHandler('css', self.cssHandler))

        dispatcher.add_handler(CommandHandler('github', self.gitHubHandler))
        dispatcher.add_handler(MessageHandler(Filters.regex(
            '^{0} GitHub$'.format(u"\U0001f525")), self.gitHubHandler))
        dispatcher.add_handler(CommandHandler('site', self.siteHandler))
        dispatcher.add_handler(MessageHandler(Filters.regex(
            '^{0} Сайт проекта$'.format(u"\U0001f310")), self.siteHandler))

        accountSetupHandler = ConversationHandler(
            entry_points=[MessageHandler(Filters.regex(
                r'Тема [0-9]{1}'), self.startTestHandler)],

            states={
                QUESTION2: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionTwoHandler)],
                QUESTION3: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionThreeHandler)],
                QUESTION4: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionFourHandler)],
                QUESTION5: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionFiveHandler)],
                QUESTION6: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionSixHandler)],
                QUESTION7: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionSevenHandler)],
                QUESTION8: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionEightHandler)],
                QUESTION9: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionNineHandler)],
                QUESTION10: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.questionTenHandler)],
                RESULT: [MessageHandler(Filters.regex(ANSWER_REGEXP), self.testResultHandler)],
            },

            fallbacks=[
                CommandHandler('cancel', self.cancelTestHandler)
            ],
        )

        dispatcher.add_handler(accountSetupHandler)
        dispatcher.add_handler(CommandHandler('logs', self.logsHandler))
        dispatcher.add_handler(CallbackQueryHandler(
            self.logsPageCallback, pattern='^logs#'))

        updater.start_polling()
        updater.idle()
