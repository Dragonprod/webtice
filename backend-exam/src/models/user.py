from typing import List, Optional
from pydantic import BaseModel
from src.models.role import RoleBase


class UserBase(BaseModel):
    id: int
    email: str
    is_admin: bool
    roles: List[RoleBase] = None

    class Config:
        orm_mode = True


class UserDetailMinimal(BaseModel):
    first_name: str = None
    second_name: str = None
    patronymic: str = None

    class Config:
        orm_mode = True


class UserMinimal(BaseModel):
    id: int
    detail: UserDetailMinimal

    class Config:
        orm_mode = True


class UserInLogin(BaseModel):
    email: str
    password: str


class UserInCreate(UserInLogin):
    is_admin: bool
    roles: List[int]


class UserInResponse(BaseModel):
    token: str


class UserDetailModel(BaseModel):
    user_id: int = None
    first_name: str = None
    second_name: str = None
    patronymic: str = None
    phone: str = None
    position: str = None
    company_id: int = None

    class Config:
        orm_mode = True


class UserActivationCodeModel(BaseModel):
    activationLink: str = None

    class Config:
        orm_mode = True


class UserTelegramCreate(BaseModel):
    activationLink: str = None
    telegram_id: int = None

    class Config:
        orm_mode = True


class UserTelegramResponse(BaseModel):
    telegram_id: int = None
    id: int

    class Config:
        orm_mode = True


class UserResponse(UserBase):
    detail: UserDetailModel = None

    class Config:
        orm_mode = True
