from django.urls import path
from . import views
from django.urls import path, include
from django.views.generic import TemplateView
from django.contrib.auth.decorators import login_required
from rest_framework.routers import DefaultRouter

from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView


urlpatterns = [
    # Get or set info about currently authenticated user
    path('api/app/user/', views.UserInfoView.as_view(), name='userinfo'),

    # Auth
    path('api/auth/login/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/auth/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/auth/logout/', views.LogoutView.as_view(), name='logout'),
    path('api/auth/signup/', views.SignupView.as_view(), name='signup'),
]
