from enum import Enum
from typing import List, Optional
from pydantic import BaseModel
import datetime


class LaunchBase(BaseModel):
    id: int
    user_id: int
    date: datetime.datetime
    is_start: bool
    is_stop: bool
    test_id: int
    mark: int = None

    class Config:
        orm_mode = True


class LaunchListBase(BaseModel):
    launches: List[LaunchBase]
