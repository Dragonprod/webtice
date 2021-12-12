from ....database.database import User, get_db, Session
from fastapi import APIRouter, Body, Depends

from starlette.exceptions import HTTPException
from starlette.status import HTTP_200_OK, HTTP_202_ACCEPTED
from src.crud.user import get_user, set_user_telegram_id
from src.models.user import UserBase
from src.core.jwt import get_current_user
from fastapi.responses import ORJSONResponse


router = APIRouter()


@router.get(
    "/user/{id}",
    tags=["User"],
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def get(id: int, db: Session = Depends(get_db)):
    user = get_user(id=id, db=db)
    return UserBase.from_orm(user)


@router.get(
    "/user",
    tags=["User"],
    status_code=HTTP_200_OK,
    response_model=UserBase,
    response_class=ORJSONResponse,
)
async def get(Session=Depends(get_db), current_user=Depends(get_current_user)):
    return UserBase.from_orm(current_user)
