import typing
import aiohttp
from requests.exceptions import HTTPError
from sqlalchemy import create_engine
from sqlalchemy.orm import backref, sessionmaker, relationship
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, String, Integer, Date, ForeignKey, Boolean, Table
from src.core.config import DATABASE_HOST, DATABASE_PORT, DATABASE_USER, DATABASE_NAME, DATABASE_PASSWORD
import os
import datetime

engine_postrgesql = create_engine(
    f'postgresql+psycopg2://{DATABASE_USER}:{DATABASE_PASSWORD}@{DATABASE_HOST}:{DATABASE_PORT}/{DATABASE_NAME}')
Session = sessionmaker(bind=engine_postrgesql)
Base = declarative_base(bind=engine_postrgesql)


def get_db():
    """Auto closed"""
    db = Session()
    try:
        yield db
    finally:
        db.close()

Base.metadata.create_all(engine_postrgesql)