import typing
import aiohttp
from requests.exceptions import HTTPError
from sqlalchemy import create_engine
from sqlalchemy.orm import backref, sessionmaker, relationship
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, String, Integer, Date, ForeignKey, Boolean, Table
from sqlalchemy.sql.sqltypes import DateTime
from sqlalchemy.util.langhelpers import public_factory
from src.core.config import DATABASE_HOST, DATABASE_USER, DATABASE_NAME, DATABASE_PASSWORD
import os
import datetime

engine_postrgesql = create_engine(
    f'postgresql+psycopg2://{DATABASE_USER}:{DATABASE_PASSWORD}@{DATABASE_HOST}:5432/{DATABASE_NAME}')
Session = sessionmaker(bind=engine_postrgesql)
Base = declarative_base(bind=engine_postrgesql)


def get_db():
    """Auto closed"""
    db = Session()
    try:
        yield db
    finally:
        db.close()


secondary_role = Table('user_roles', Base.metadata,
                       Column('user_id', ForeignKey('user.id')),
                       Column('role_id', ForeignKey('role.id'))
                       )


class User(Base):
    __tablename__ = "user"
    id = Column(Integer, primary_key=True)
    email = Column(String(128))
    password = Column(String(256))
    is_admin = Column(Boolean, default=False)
    roles = relationship("Role",
                         secondary=secondary_role, lazy='joined')
    launchs = relationship("Launch", back_populates="user")


class Role(Base):
    __tablename__ = "role"
    id = Column(Integer, primary_key=True)
    role = Column(String(32))


class Answer(Base):
    __tablename__ = "answers"
    id = Column(Integer, primary_key=True)
    launch_id = Column(Integer, ForeignKey('launch.id'))
    answer = Column(String(512))
    question_id = Column(Integer, ForeignKey('questions.id'))
    launch = relationship("Launch", back_populates="answers")
    question = relationship("Question", back_populates="answers")


class Launch(Base):
    __tablename__ = 'launch'
    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey('user.id'))
    date = Column(DateTime, default=datetime.datetime.utcnow())
    is_start = Column(Boolean, default=True)
    is_stop = Column(Boolean, default=False)
    test_id = Column(Integer, ForeignKey('tests.id'))
    mark = Column(Integer)
    user = relationship("User", back_populates="launchs")
    answers = relationship("Answer", back_populates="launch")
    test = relationship("Test", back_populates="launchs")


class Test(Base):
    __tablename__ = "tests"
    id = Column(Integer, primary_key=True)
    name = Column(String(256))
    time = Column(Integer)
    questions = relationship("Question", back_populates="test")
    launchs = relationship("Launch", back_populates="test")

    def __init__(self, pydantic_model) -> None:
        self.name = pydantic_model.name
        self.time = pydantic_model.time


class Question(Base):
    __tablename__ = "questions"
    id = Column(Integer, primary_key=True)
    name = Column(String(256))
    test_id = Column(Integer, ForeignKey('tests.id'))
    test = relationship("Test", back_populates="questions")
    answers = relationship("Answer", back_populates="question")


Base.metadata.create_all(engine_postrgesql)
