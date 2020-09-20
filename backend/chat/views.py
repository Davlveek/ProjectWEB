from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated

from .models import User
from .serializers import UserSerializer


# AUTH VIEWS

class SignupView(APIView):
    def post(self, request):
        User.objects.create_user(name=request.data['name'], age=request.data['age'], gender=request.data['gender'], password=request.data['password'])
        return Response(status=201)


# INFO VIEWS

class UserInfoView(APIView):
    permission_classes = (IsAuthenticated, )

    def get(self, request):
        user = User.objects.get(name=request.user.name)
        serializer = UserSerializer(user, many=False)
        return Response(serializer.data)

    def post(self, request):
        print(request.data)
        User.objects.filter(name=request.user.name).update(**request.data)
        return Response(status=200)
