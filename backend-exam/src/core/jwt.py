import jwt

from datetime import datetime, timedelta
from typing import Dict
from fastapi.security import OAuth2PasswordBearer, HTTPBearer
from src.models.jwt import JWTUser, JWTMeta
from src.core.config import JWT_ACCESS_TOKEN_EXPIRE_MINUTES, JWT_SECRET
from src.database.database import get_db, Session, User
from starlette.exceptions import HTTPException
from fastapi import Depends, status
from pydantic import ValidationError

from src.crud.user import get_user
oauth2_scheme = HTTPBearer()

JWT_SUBJECT = 'access'
JWT_ALGORITHM = 'HS256'


def create_jwt_token(*, jwt_content: Dict[str, str], expires_delta: timedelta) -> str:
    data_to_encode = jwt_content.copy()
    expire_at = datetime.utcnow() + expires_delta
    data_to_encode |= JWTMeta(
        expire=expire_at.isoformat(), subject=JWT_SUBJECT).dict()
    encoded = jwt.encode(data_to_encode, JWT_SECRET, algorithm=JWT_ALGORITHM)
    return encoded


def create_access_token(user) -> str:
    return create_jwt_token(jwt_content=JWTUser(user_id=user.id, is_admin=user.is_admin, roles=user.roles).dict(), expires_delta=timedelta(minutes=JWT_ACCESS_TOKEN_EXPIRE_MINUTES))


async def get_current_user(token: str = Depends(oauth2_scheme), db: Session = Depends(get_db)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Error with JWT")
    try:
        pydantic_user = jwt.decode(token.credentials, JWT_SECRET,
                                   algorithms=[JWT_ALGORITHM])
        user_id: int = pydantic_user.get("user_id")
        if user_id is None:
            raise credentials_exception
        user = await get_user(user_id, db)
        if user is None:
            raise credentials_exception
    except:
        return credentials_exception
    return user


async def get_current_teacher(current_user: User = Depends(get_current_user)):
    ids_roles = [role.id for role in current_user.roles]
    if 3 not in ids_roles:
        raise HTTPException(status_code=403, detail="not a teacher")
    return current_user


async def get_current_user(current_user: User = Depends(get_current_user)):
    ids_roles = [role.id for role in current_user.roles]
    if 2 in ids_roles:
        raise HTTPException(status_code=400, detail="not a student")
    return current_user
