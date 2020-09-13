from django.contrib.auth.models import AbstractBaseUser, BaseUserManager
from django.db.models import (ForeignKey, DateTimeField, TextField, CASCADE, IntegerField, CharField, Model)


class UserManager(BaseUserManager):
    def create_user(self, name, age, gender, password=None):
        user = self.model(name=name, age=age, gender=gender)
        user.set_password(password)
        user.save()
        return user

    def create_superuser(self, name, age, gender, password=None):
        user = self.create_user(name, age, gender, password)
        user.is_admin = True
        user.save()
        return user


class User(AbstractBaseUser):
    name = TextField(unique=True)
    first_name = TextField(blank=True)
    last_name = TextField(blank=True)
    age = IntegerField()

    GENDERS = (
        ("m", "male"),
        ("f", "female"),
        ("o", "other"),
    )

    gender = CharField(max_length=1, choices=GENDERS)

    objects = UserManager()

    USERNAME_FIELD = 'name'
    REQUIRED_FIELDS = ['age', 'gender']

    def __str__(self):
        return f'{self.name}, {self.age} years old, {self.get_gender_display()}'


class MessageModel(Model):
    user = ForeignKey(User, on_delete=CASCADE, verbose_name='user',
                      related_name='from_user', db_index=True)
    recipient = ForeignKey(User, on_delete=CASCADE, verbose_name='recipient',
                           related_name='to_user', db_index=True)
    timestamp = DateTimeField('timestamp', auto_now_add=True, editable=False,
                              db_index=True)
    body = TextField('body')

    def __str__(self):
        return str(self.id)
