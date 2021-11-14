import os

API_BASE_URL = "http://localhost:8080/api"
DEBUG = os.getenv('DEBUG', True)
TELEGRAM_BOT_TOKEN = os.getenv('TELEGRAM_BOT_TOKEN')
ADMINS_CHAT_IDS = os.getenv('ADMINS_CHAT_IDS').split(' ')

DATABASE_NAME = os.getenv('DATABASE_NAME')
DATABASE_USER = os.getenv('DATABASE_USER')
DATABASE_PASSWORD = os.getenv('DATABASE_PASSWORD')