from types import DynamicClassAttribute
from src.database.database import *
from src.core.security import get_password_hash


def migrate_database():
    db = Session()
    roles = ["Админ", "Пользователь", "Преподаватель"]
    dbroles = db.query(Role).filter(Role.role.in_(roles)).all()
    if len(dbroles) == 0:
        roles_in_db = []
        for i in range(0, len(roles)):
            role = Role(role=roles[i])
            db.add(role)
            roles_in_db.append(role)

    users_name = ["admin@admin", "user@user", "teacher@teacher"]
    dbusers = db.query(User).filter(User.email.in_(users_name)).all()
    if len(dbusers) == 0:
        for i in range(0, len(users_name)):
            user = User(email=users_name[i],
                        password=get_password_hash(users_name[i]))
            db.add(user)
            user.roles.append(roles_in_db[i])

    db.commit()
