from src.core.security import get_password_hash
from src.models.user import UserDetailModel, UserActivationCodeModel, UserTelegramResponse, UserBase, UserResponse
from src.database.database import Role, User, get_db, Session
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


async def get_user(id: int, db: Session):
    dbuser = db.query(User).filter(User.id == id).first()
    if dbuser is not None:
        return dbuser
    return None


async def set_user_telegram_id(telegram, db: Session):
    dbuser = db.query(User).filter(User.activationLink ==
                                   telegram.activationLink).first()
    dbuser.telegram_id = telegram.telegram_id
    db.commit()
    return UserTelegramResponse.from_orm(dbuser)
