from src.helpers.codeGenerator import create_activation_code
from src.core.security import get_password_hash
from src.models.user import UserDetailModel, UserActivationCodeModel, UserTelegramResponse, UserBase, UserResponse
from src.database.database import Reviews, Role, Startup, User, UserDetail, get_db, Session, seconadary_applications
from src.helpers.exceptions import EntityDoesNotExist
from fastapi import Depends


async def create_user(user, db: Session):
    if db.query(User).filter(User.email == user.email).first() == None:
        user.password = get_password_hash(user.password)
        new_user = User(email=user.email, password=user.password,
                        is_admin=user.is_admin)
        roles = db.query(Role).filter(Role.id.in_(user.roles))
        new_user.roles.extend(roles)
        db.add(new_user)
        db.commit()
        return UserBase(id=new_user.id, email=user.email, is_admin=user.is_admin, roles=new_user.roles)
    else:
        return None


async def update_user(user, db: Session):
    dbuser = db.query(UserDetail).filter(
        UserDetail.user_id == user.user_id).first()
    if dbuser is None:
        db.add(UserDetail(user))
    else:
        dbuser.__init__(user)
    db.commit()
    return UserDetailModel.from_orm(dbuser)


async def get_user(id: int, db: Session):
    dbuser = db.query(User).filter(User.id == id).first()
    if dbuser is not None:
        return UserResponse.from_orm(dbuser)
    return None


async def get_user_activation_code(id: int, db: Session):
    dbuser = db.query(User).filter(User.id == id).first()
    dbuser.activationLink = create_activation_code()
    db.commit()
    return UserActivationCodeModel.from_orm(dbuser)


async def set_user_telegram_id(telegram, db: Session):
    dbuser = db.query(User).filter(User.activationLink ==
                                   telegram.activationLink).first()
    dbuser.telegram_id = telegram.telegram_id
    db.commit()
    return UserTelegramResponse.from_orm(dbuser)


async def create_review(review, db: Session):
    review = Reviews(review)
    db.add(review)
    db.commit()
    return review


async def get_favorites(user_id: int, db: Session):
    return db.query(User).filter(User.id == user_id).first()


async def get_applications(user_id: int, db: Session):
    startups = db.query(Startup).filter(Startup.author == user_id).all()
    return startups


async def get_category_own(user_id: int, db: Session):
    user = db.query(User).filter(User.id == user_id).first()
    return user.subscription_general, user.subscription_children
