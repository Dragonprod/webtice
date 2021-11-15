from src.core.config import ADMINS_CHAT_IDS


def isAdmin(update):
    if str(update.message.chat.id) in ADMINS_CHAT_IDS:
        return True
    return False


def getCommand(command):

    cmd = ''

    if command == '{0} Telegram ID'.format(u"\u2699\ufe0f"):
        cmd = "/getid"
    elif command == '{0} Помощь'.format(u"\u2753"):
        cmd = "/help"
    elif command == '{0} Сайт проекта'.format(u"\U0001f310"):
        cmd = "/site"
    elif command == '{0} GitHub'.format(u"\U0001f525"):
        cmd = "/github"
    else:
        cmd = command
        
    return cmd
