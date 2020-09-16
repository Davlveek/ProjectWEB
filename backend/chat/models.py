from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager
from django.contrib.auth.hashers import make_password

class UserManager(BaseUserManager):
    next_id = 1
    def getNextId(self):
        to_return = self.next_id
        self.next_id += 1
        return to_return

    def create_user(self, login, age, gender, password=None):
        user = self.model(id=self.getNextId(), login=login, age=age, gender=gender)
        user.set_password(password)
        user.save()
        return user

    def create_superuser(self, login, age, gender, password=None):
        user = self.create_user(0, login, age, gender, password)
        user.is_admin = True
        user.save()
        return user


class User(AbstractBaseUser):
    id = models.TextField(primary_key=True, unique=True)
    login = models.TextField(unique=True)
    first_name = models.TextField(blank=True)
    last_name = models.TextField(blank=True)
    age = models.IntegerField()

    GENDERS = (
        ("m", "male"),
        ("f", "female"),
        ("o", "other"),
    )

    gender = models.CharField(max_length=1, choices=GENDERS)

    objects = UserManager()

    USERNAME_FIELD = 'login'
    REQUIRED_FIELDS = ['age', 'gender']

    def __str__(self):
        return f'{self.login}, {self.age} years old, {self.get_gender_display()}'

    def get_partners(self, partners_list):
        self.partners_list = partners_list
