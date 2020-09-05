from django.db import models
from django.contrib.auth.models import User


class Message(models.Model):
    sender = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        related_name='sender'
    )
    receiver = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        related_name='receiver'
    )
    message = models.CharField(max_length=1024)
    timestamp = models.DateTimeField(auto_now=True)
    is_read = models.BooleanField(default=False)

    def __str__(self):
        return self.message

    class Meta:
        ordering = ('timestamp',)
