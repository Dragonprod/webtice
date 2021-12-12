from ....database.database import Launch, User, get_db, Session
from fastapi import APIRouter, Body, Depends

from fastapi.exceptions import HTTPException
from starlette.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from src.crud.test import create_test, send_mark, get_launches_by_test, get_answers_by_user
from src.models.test import TestBase, TestCreate, TestList, TestMark
from src.models.launch import LaunchBase, LaunchListBase
from src.models.answer import AnswerBase, AnswerListBase
from src.core.jwt import get_current_user, get_current_teacher
from fastapi.responses import ORJSONResponse


router = APIRouter()


@router.post(
    "/test/create",
    tags=["Teacher"],
    status_code=HTTP_201_CREATED,
    response_class=ORJSONResponse,
)
async def create(test: TestCreate = Body(...), db: Session = Depends(get_db), current_user=Depends(get_current_teacher)):
    test = await create_test(test=test, current_user=current_user, db=db)
    return TestBase.from_orm(test)


@router.post(
    "/test/mark",
    tags=["Teacher"],
    status_code=HTTP_201_CREATED,
    response_class=ORJSONResponse,
)
async def send(launch: TestMark = Body(...), db: Session = Depends(get_db), current_user=Depends(get_current_teacher)):
    test = await send_mark(launch=launch, current_user=current_user, db=db)
    return LaunchBase.from_orm(test)


@router.get(
    "/test/launches",
    tags=["Teacher"],
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def list(test_id: int, is_stoped: bool = False, db: Session = Depends(get_db), current_user=Depends(get_current_teacher)):
    test = await get_launches_by_test(test_id=test_id, is_stoped=is_stoped, current_user=current_user, db=db)
    return LaunchListBase(launches=test)


@router.get(
    "/test/answers",
    tags=["Teacher"],
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def answers_by_user_and_test(test_id: int, user_id: int, db: Session = Depends(get_db), current_user=Depends(get_current_teacher)):
    answers = await get_answers_by_user(test_id=test_id, user_id=user_id, current_user=current_user, db=db)
    return AnswerListBase(answers=answers)
