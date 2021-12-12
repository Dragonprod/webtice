from enum import Enum
from typing import List, Optional
from pydantic import BaseModel


class QuestionBase(BaseModel):
    id: int
    name: str
    test_id: int

    class Config:
        orm_mode = True
