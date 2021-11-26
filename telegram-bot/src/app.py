import json
import logging

from src.core.config import DEBUG, TELEGRAM_BOT_TOKEN, ADMINS_CHAT_IDS
from src.providers.apiProvider import API
from src.providers.logsProvider import Logger
from src.providers.functionsProvider import isAdmin
from telegram import Update, ReplyKeyboardMarkup, InlineKeyboardButton, InlineKeyboardMarkup
from telegram.ext import (Updater, CommandHandler, CallbackContext,
                          MessageHandler, CallbackQueryHandler, Filters)
from telegram_bot_pagination import InlineKeyboardPaginator


logger = logging.getLogger(__name__)


class Bot():
    def __init__(self):
        self.api = API()
        self.logs = Logger()
        self.attributes = []
        self.logsData = []

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
        welcomeMessage = "{0} Привет, <b>{1}</b>!\n\nМеня зовут МАРГИНШТЕРН, я бот проекта <b>Webtice</b>, разработанного студентами <b>РТУ МИРЭА</b>.\n\nСписок моих команд можно увидеть, отправив мне команду /help".format(
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
        updater = Updater(token=TELEGRAM_BOT_TOKEN, use_context=True)
        dispatcher = updater.dispatcher

        dispatcher.add_handler(CommandHandler('start', self.startHandler))
        dispatcher.add_handler(CommandHandler('help', self.helpHandler))
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

        dispatcher.add_handler(CommandHandler('logs', self.logsHandler))
        dispatcher.add_handler(CallbackQueryHandler(
            self.logsPageCallback, pattern='^logs#'))

        updater.start_polling()
        updater.idle()
