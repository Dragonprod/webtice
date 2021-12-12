from enum import Enum
from typing import List, Optional
from pydantic import BaseModel


class AnswerBase(BaseModel):
    id: int
    launch_id: int
    answer: str
    question_id: int

    class Config:
        orm_mode = True


class AnswerListBase(BaseModel):
    answers: List[AnswerBase]


class RoleCreateprUpdate(BaseModel):
    role: str

    class Config:
        orm_mode = True
