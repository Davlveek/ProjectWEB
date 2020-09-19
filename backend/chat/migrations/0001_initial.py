# Generated by Django 3.1.1 on 2020-09-14 08:45

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('password', models.CharField(max_length=128, verbose_name='password')),
                ('last_login', models.DateTimeField(blank=True, null=True, verbose_name='last login')),
                ('name', models.TextField(unique=True)),
                ('first_name', models.TextField(blank=True)),
                ('last_name', models.TextField(blank=True)),
                ('age', models.IntegerField()),
                ('gender', models.CharField(choices=[('m', 'male'), ('f', 'female'), ('o', 'other')], max_length=1)),
            ],
            options={
                'abstract': False,
            },
        ),
    ]
