from enum import Enum
from typing import List, Optional
from pydantic import BaseModel
from src.models.question import QuestionBase


class TestBase(BaseModel):
    id: int
    name: str
    time: int
    questions: List[QuestionBase]

    class Config:
        orm_mode = True


class TestCreate(BaseModel):
    name: str
    time: int
    questions: List[str]


class TestList(BaseModel):
    tests: List[TestBase]


class TestMark(BaseModel):
    launch_id: int
    mark: int


class TestAnswer(BaseModel):
    launch_id: int
    answer: str
    question_id: int
