from rest_framework import serializers

from TagLifeApp.models import Topic, Entry, Comment, Tag, Predicate, TopicTagRelation,FollowTopicRelation,EntryTagRelation
from datetime import datetime
from django.contrib.auth.models import User


class TopicSerializer(serializers.ModelSerializer):

    class Meta:
        model = Topic
        fields = ('id', 'title','user','created_at','updated_at')

class TopicGetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Topic
        fields = fields = ('id', 'title','user','entries','relations','followers','created_at','updated_at')

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('id', 'username', 'first_name', 'last_name', 'email','password')
    #

class UserGetSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ('id', 'username', 'first_name', 'last_name', 'email','password','topics','entries','comments', 'followings', 'date_joined', 'last_login')

class EntrySerializer(serializers.ModelSerializer):

    class Meta:
        model = Entry
        fields = ('id', 'topic', 'content', 'user')

class EntryGetSerializer(serializers.ModelSerializer):

    class Meta:
        model = Entry
        fields = ('id', 'topic', 'content', 'user','comments','relations', 'vote','created_at', 'updated_at')

class CommentSerializer(serializers.ModelSerializer):

    class Meta:
        model = Comment
        fields = ('id', 'content','entry', 'user')

class CommentGetSerializer(serializers.ModelSerializer):

    class Meta:
        model = Comment
        fields = ('id', 'content', 'entry', 'user', 'vote', 'created_at', 'updated_at')

class TagSerializer(serializers.ModelSerializer):

    class Meta:
        model = Tag
        fields = ('id', 'tagString')

class TagGetSerializer(serializers.ModelSerializer):

    class Meta:
        model = Tag
        fields = ('id', 'tagString', 'relations','created_at', 'updated_at')

class PredicateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Predicate
        fields = ('id', 'predicateString')

class PredicateGetSerializer(serializers.ModelSerializer):

    class Meta:
        model = Predicate
        fields = ('id', 'predicateString','relations','created_at', 'updated_at')

class TopicTagRelationSerializer(serializers.ModelSerializer):

    class Meta:
        model = TopicTagRelation
        fields = ('id', 'topic', 'predicate' ,'tag', )

class TopicTagRelationGetSerializer(serializers.ModelSerializer):
    class Meta:
        model = TopicTagRelation
        fields = ('id', 'topic', 'predicate' ,'tag', 'created_at', 'updated_at',)

class EntryTagRelationSerializer(serializers.ModelSerializer):
    class Meta:
        model = EntryTagRelation
        fields = ('id','entry','tag',)

class EntryTagRelationGetSerializer(serializers.ModelSerializer):
    class Meta:
        model = EntryTagRelation
        fields = ('id','entry','tag','created_at', 'updated_at',)

class FollowTopicRelationSerializer(serializers.ModelSerializer):
    class Meta:
        model = FollowTopicRelation
        fields = ('id', 'topic', 'user')

class FollowTopicRelationGetSerializer(serializers.ModelSerializer):
    class Meta:
        model = FollowTopicRelation
        fields = ('id', 'topic', 'user', 'created_at', 'updated_at')