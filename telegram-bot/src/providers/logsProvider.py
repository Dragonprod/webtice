import logging
from src.database.db import Session, Logs
from src.providers.functionsProvider import isAdmin, getCommand
from datetime import datetime

logger = logging.getLogger(__name__)


class Logger():
    def __init__(self) -> None:
        self.db = Session()

    def addLog(self, update):
        
        time = datetime.now()
        chatid = update.message.chat.id
        username = update.message.chat.username
        command = getCommand(update.message.text)
        admin = isAdmin(update)

        logRow = Logs(time=time, chatid=chatid, username=username, command=command, isAdmin=admin)

        self.db.add(logRow)
        self.db.commit()
        self.db.close()

    def getLogs(self):
        return self.db.query(Logs).all()
