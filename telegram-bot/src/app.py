import json
import logging

from src.core.config import TELEGRAM_BOT_TOKEN, ADMINS_CHAT_IDS
from telegram import Update, ReplyKeyboardMarkup, InlineKeyboardButton, InlineKeyboardMarkup
from telegram.ext import (Updater, CommandHandler, CallbackContext,
                          MessageHandler, CallbackQueryHandler, Filters)
from telegram_bot_pagination import InlineKeyboardPaginator


logger = logging.getLogger(__name__)


class Bot():
    def __init__(self):
        pass

    def startHandler(self, update: Update, context: CallbackContext) -> None:
        update.message.reply_text('Start')

    def helpHandler(self, update: Update, context: CallbackContext) -> None:
        update.message.reply_text('Help')

    def run(self):
        updater = Updater(token=TELEGRAM_BOT_TOKEN, use_context=True)
        dispatcher = updater.dispatcher

        dispatcher.add_handler(CommandHandler('start', self.startHandler))
        dispatcher.add_handler(CommandHandler('help', self.helpHandler))

        updater.start_polling()
        updater.idle()
