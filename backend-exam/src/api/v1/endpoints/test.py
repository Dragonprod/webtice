from ....database.database import User, get_db, Session
from fastapi import APIRouter, Body, Depends

from fastapi.exceptions import HTTPException
from starlette.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from src.crud.test import get_tests, test_start, test_stop, get_tests_started, get_tests_stoped, send_answer, get_answers
from src.models.test import TestBase, TestCreate, TestList, TestAnswer
from src.models.launch import LaunchBase
from src.models.answer import AnswerBase, AnswerListBase
from src.core.jwt import get_current_user
from fastapi.responses import ORJSONResponse


router = APIRouter()


@router.get(
    "/tests",
    tags=["Test"],
    response_model=TestList,
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def list_tests(db: Session = Depends(get_db)):
    tests = await get_tests(db=db)
    return TestList(tests=tests)


@router.post(
    "/test/start",
    tags=["Test"],
    response_model=LaunchBase,
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def start(test_id: int, db: Session = Depends(get_db), current_user=Depends(get_current_user)):
    test = await test_start(test_id=test_id, current_user=current_user, db=db)
    if test is not HTTPException:
        return test
    return LaunchBase.from_orm(test)


@router.post(
    "/test/stop",
    tags=["Test"],
    response_model=LaunchBase,
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def stop(test_id: int, db: Session = Depends(get_db), current_user=Depends(get_current_user)):
    lauch = await test_stop(test_id=test_id, current_user=current_user, db=db)
    if lauch is not HTTPException:
        return lauch
    return LaunchBase.from_orm(lauch)


@router.get(
    "/tests/started",
    tags=["Test"],
    response_model=TestList,
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def list(db: Session = Depends(get_db),  current_user=Depends(get_current_user)):
    tests = await get_tests_started(current_user=current_user, db=db)
    return TestList(tests=tests)


@router.get(
    "/tests/stoped",
    tags=["Test"],
    status_code=HTTP_200_OK,
    response_model=TestList,
    response_class=ORJSONResponse,
)
async def list(db: Session = Depends(get_db),  current_user=Depends(get_current_user)):
    tests = await get_tests_stoped(current_user=current_user, db=db)
    return TestList(tests=tests)


@router.put(
    "/test/answer/send",
    tags=["Answer"],
    status_code=HTTP_200_OK,
    response_model=AnswerBase,
    response_class=ORJSONResponse,
)
async def send(answer: TestAnswer = Body(...), db: Session = Depends(get_db), current_user=Depends(get_current_user)):
    answer = await send_answer(answer=answer, current_user=current_user, db=db)
    return AnswerBase.from_orm(answer)


@router.get(
    "/test/answers",
    tags=["Answer"],
    response_model=AnswerListBase,
    status_code=HTTP_200_OK,
    response_class=ORJSONResponse,
)
async def get(test_id: int, db: Session = Depends(get_db), current_user=Depends(get_current_user)):
    answers = await get_answers(test_id=test_id, current_user=current_user, db=db)
    print(answers)
    return AnswerListBase(answers=answers)
