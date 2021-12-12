import uvicorn
from src.app import app
from src.database.migrate import migrate_database

if __name__ == "__main__":
    migrate_database()
    uvicorn.run(app, host="0.0.0.0", port=8080)
