# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2016-11-23 10:41
from __future__ import unicode_literals

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('TagLifeApp', '0002_auto_20161120_1716'),
    ]

    operations = [
        migrations.AlterField(
            model_name='topic',
            name='author',
            field=models.ForeignKey(default=1, on_delete=django.db.models.deletion.CASCADE, related_name='topics', to=settings.AUTH_USER_MODEL),
            preserve_default=False,
        ),
    ]