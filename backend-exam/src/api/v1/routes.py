from fastapi import APIRouter

from src.api.v1.endpoints.auth import router as auth_router
from src.api.v1.endpoints.test import router as test_router
from src.api.v1.endpoints.user import router as user_router
from src.api.v1.endpoints.teacher import router as teachers_router
from src.api.v1.endpoints.role import router as roles_router

router = APIRouter()
router.include_router(auth_router)
router.include_router(test_router)
router.include_router(user_router)
router.include_router(teachers_router)
router.include_router(roles_router)
