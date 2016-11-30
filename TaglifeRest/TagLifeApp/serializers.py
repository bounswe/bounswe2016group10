from rest_framework import serializers

from TagLifeApp.models import Topic
from datetime import datetime
from django.contrib.auth.models import User


class TopicSerializer(serializers.ModelSerializer):

    class Meta:
        model = Topic
        fields = ('id', 'title','user','created_at','updated_at')

class TopicGetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Topic
        fields = fields = ('id', 'title','user','entries','tags','created_at','updated_at')

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('id', 'username', 'first_name', 'last_name', 'email','password', 'date_joined', 'last_login')

class UserGetSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('id', 'username', 'first_name', 'last_name', 'email','password','topics','entries',  'date_joined', 'last_login')


