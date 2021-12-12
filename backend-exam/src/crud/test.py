from fastapi import Depends
from fastapi.exceptions import HTTPException
from src.database.database import Test, Session, Question, User, Launch, Answer


async def create_test(test, current_user, db: Session):
    dbtest = Test(test)
    questions = [Question(name=question_name)
                 for question_name in test.questions]
    db.add(dbtest)
    dbtest.questions.extend(questions)
    db.commit()
    return dbtest


async def get_tests(db: Session):
    dbtests = db.query(Test).all()
    return dbtests


async def get_tests_stoped(current_user: User, db: Session):
    launches = db.query(Launch).filter(
        Launch.user_id == current_user.id, Launch.is_stop == True).all()
    return [launch.test for launch in launches]


async def get_tests_started(current_user: User, db: Session):
    launches = db.query(Launch).filter(
        Launch.user_id == current_user.id, Launch.is_stop == False).all()
    return [launch.test for launch in launches]


async def test_start(test_id: int, current_user: User, db: Session):
    test = db.query(Test).filter(Test.id == test_id).first()
    if test is None:
        raise HTTPException(status_code=404, detail="test does not exist")
    launch = db.query(Launch).filter(Launch.test_id == test_id,
                                     Launch.user_id == current_user.id).first()
    if launch is not None:
        raise HTTPException(status_code=403, detail="You have already passed")
    launch = Launch()
    db.add(launch)
    current_user.launchs.append(launch)
    test.launchs.append(launch)
    db.commit()
    return launch


async def test_stop(test_id: int, current_user: User, db: Session):
    test = db.query(Test).filter(Test.id == test_id).first()
    if test is None:
        raise HTTPException(status_code=404, detail="test does not exist")
    launch = db.query(Launch).filter(
        Launch.user_id == current_user.id, Launch.test_id == test_id, Launch.is_stop == False).first()
    if launch is None:
        raise HTTPException(
            status_code=404, detail="you dont started this test")
    launch.is_stop = True
    db.commit()
    return launch


async def send_mark(launch, current_user, db: Session):
    launchdb = db.query(Launch).filter(Launch.id == launch.launch_id).first()
    if launchdb is None:
        raise HTTPException(status_code=404, detail="launch does not exist")
    launchdb.mark = launch.mark
    db.commit()
    return launchdb


async def get_launches_by_test(test_id: int, is_stoped: bool, current_user, db: Session):
    return db.query(Launch).filter(Launch.test_id == test_id,
                                   Launch.is_stop == is_stoped).all()


async def send_answer(answer, current_user, db: Session):
    launch = db.query(Launch).filter(
        Launch.id == answer.launch_id, Launch.user_id == current_user.id).first()
    if launch is None:
        raise HTTPException(status_code=404, detail="launch does not exist")
    question = db.query(Question).filter(
        Question.id == answer.question_id).first()
    if question is None:
        raise HTTPException(status_code=404, detail="question does not exist")
    answerdb = db.query(Answer).filter(
        Answer.launch_id == answer.launch_id).first()
    if answerdb is None:
        ans = Answer(answer=answer.answer)
        db.add(ans)
        launch.answers.append(ans)
        question.answers.append(ans)
    else:
        answerdb.name = answer.answer
    db.commit()
    return answerdb


async def get_answers(test_id: int, current_user, db: Session):
    answers = db.query(Answer).join(Launch).filter(
        Launch.test_id == test_id, Launch.user_id == current_user.id).all()
    return answers


async def get_answers_by_user(test_id: int, user_id: int, current_user, db: Session):
    answers = db.query(Answer).join(Launch).filter(
        Launch.test_id == test_id, Launch.user_id == user_id).all()
    return answers
