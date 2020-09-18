from rest_framework import serializers
from .models import User
from rest_framework.serializers import ModelSerializer


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['name', 'age', 'gender', 'first_name', 'last_name']
