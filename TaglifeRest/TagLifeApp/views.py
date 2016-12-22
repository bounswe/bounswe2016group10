import os
from django.shortcuts import render
from django.shortcuts import get_object_or_404
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from rest_framework import generics
from django.contrib.auth import get_user_model
from rest_framework import permissions
from django.http import JsonResponse
from django.db.models import Count
from django.core import serializers
from django.db.models import Q
from django.http import Http404
from rest_framework import viewsets
from rest_framework.renderers import JSONRenderer
from TagLifeApp.models import Topic, Entry, Comment, Tag, Predicate,EntryTagRelation,FollowTopicRelation,TopicTagRelation
from django.contrib.auth.models import User
from TagLifeApp.serializers import TopicSerializer,TopicGetSerializer,UserGetSerializer, UserSerializer, EntrySerializer,EntryGetSerializer, CommentSerializer,CommentGetSerializer, TagSerializer, TagGetSerializer, PredicateSerializer,PredicateGetSerializer, TopicTagRelationSerializer,TopicTagRelationGetSerializer, EntryTagRelationSerializer,EntryTagRelationGetSerializer, FollowTopicRelationSerializer, FollowTopicRelationGetSerializer
from rest_framework.parsers import JSONParser
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

# /index/
def index(request):
    return render(request, "index.html", {})


class TopicList(generics.ListAPIView):
    """
    List all topics
    """
    #Used generics from rest framework to make it easy

    queryset = Topic.objects.all()
    serializer_class = TopicGetSerializer

    # def perform_create(self,serializer):
    #     serializer.save(author=self.request.user)


class TopicCreate(generics.CreateAPIView):
    """
    Used generics from rest framework to make it easy
    """
    serializer_class = TopicSerializer
    #   permission_classes = (permissions.IsAuthenticated)


class TopicDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    retrieve single topic instance
    """
    queryset = Topic.objects.all()
    serializer_class = TopicGetSerializer


class TopicPopular(generics.ListAPIView):

    """
    Ten topics that has the most followers
    """
    queryset = Topic.objects.all().annotate(follower_count=Count('followers')).order_by('-follower_count')[:10]
    serializer_class = TopicGetSerializer


class UserList(generics.ListAPIView):
    """
    List TagLifeUsers
    """
    queryset = User.objects.all()
    serializer_class = UserGetSerializer

class UserCreate(generics.CreateAPIView):
    """
    Create TagLifeUser
    """
    model = get_user_model()
    serializer_class = UserSerializer
    # permission_classes = [
    #     permissions.AllowAny  # Or anon users can't register
    # ]


class UserDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve single user instance
    """
    queryset = User.objects.all()
    serializer_class = UserGetSerializer


class EntryList(generics.ListAPIView):
    """
    List Entries
    """
    queryset = Entry.objects.all()
    serializer_class = EntryGetSerializer

class EntryCreate(generics.CreateAPIView):
    """
    Create entry
    """
    serializer_class = EntrySerializer
    # permission_classes = (permissions.IsAuthenticated,)


class EntryDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve single entry instance by id
    """
    queryset = Entry.objects.all()
    serializer_class = EntryGetSerializer

class EntryByTopicList(generics.ListAPIView):
    """
    List entries with the given topic id
    """
    serializer_class = EntryGetSerializer
    def get_queryset(self):
        topicid = self.kwargs['topicid']
        return Entry.objects.filter(topic=topicid)

class CommentList(generics.ListAPIView):
    """
    List Comments
    """
    queryset = Comment.objects.all()
    serializer_class = CommentGetSerializer


class CommentDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve single comment instance
    """
    serializer_class = CommentGetSerializer
    queryset = Comment.objects.all()


class CommentCreate(generics.CreateAPIView):
    """
    Create Comment
    """
    serializer_class = CommentSerializer
    # permission_classes = (permissions.IsAuthenticated,)


class CommentByEntryList(generics.ListAPIView):
    """
    List comments with the given entry id
    """
    serializer_class = CommentGetSerializer
    def get_queryset(self):
        entryid = self.kwargs['entryid']
        return Comment.objects.filter(entry=entryid)


class TagList(generics.ListAPIView):
    """
    List All Tags
    """
    serializer_class = TagGetSerializer
    queryset = Tag.objects.all()

class TagDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve single tag instance
    """
    serializer_class = TagGetSerializer
    queryset = Tag.objects.all()

class TagCreate(generics.CreateAPIView):
    """
    Create Tag instance
    """
    serializer_class = TagSerializer
    # permission_classes = (permissions.IsAuthenticated)


class PredicateList(generics.ListAPIView):
    """
    List all predicates
    """
    serializer_class = PredicateGetSerializer
    queryset = Predicate.objects.all()

class PredicateDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Get single predicate instance
    """
    serializer_class = PredicateGetSerializer
    queryset = Predicate.objects.all()

class PredicateCreate(generics.CreateAPIView):
    """
    Create Predicate instance
    """
    serializer_class = PredicateSerializer
    # permission_classes = (permissions.IsAuthenticated,)


class TopicTagRelationList(generics.ListAPIView):
    """
    List all topic tag relations
    """
    queryset = TopicTagRelation.objects.all()
    serializer_class = TopicTagRelationGetSerializer

class TopicTagRelationDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve single topic tag relation instance
    """
    queryset = TopicTagRelation.objects.all()
    serializer_class = TopicTagRelationGetSerializer

class TopicTagRelationCreate(generics.CreateAPIView):
    """
    Create topic tag relation
    """
    serializer_class = TopicTagRelationSerializer
    # permission_classes = (permissions.IsAuthenticated,)


class EntryTagRelationList(generics.ListAPIView):
    """
    List all Entry tag relations
    """
    queryset = EntryTagRelation.objects.all()
    serializer_class = EntryTagRelationGetSerializer

class EntryTagRelationDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Retrieve one instance of
    """
    serializer_class = EntryTagRelationGetSerializer
    queryset = EntryTagRelation.objects.all()

class EntryTagRelationCreate(generics.CreateAPIView):
    """
    Create entry tag relation
    """
    serializer_class = EntryTagRelationSerializer
    # permission_classes = (permissions.IsAuthenticated,)


class FollowTopicRelationList(generics.ListAPIView):
    """
    List all followings
    """
    serializer_class = FollowTopicRelationGetSerializer
    queryset = FollowTopicRelation.objects.all()

class FollowTopicRelationDetail(generics.RetrieveUpdateDestroyAPIView):
    """
    Get single instance of a Follow topic relation
    """
    serializer_class = FollowTopicRelationGetSerializer
    queryset = FollowTopicRelation.objects.all()

class FollowTopicRelationCreate(generics.CreateAPIView):
    """
    Create follow topic relation
    """
    serializer_class = FollowTopicRelationSerializer
    # permission_classes = (permissions.IsAuthenticated,)

class Login(APIView):
    """
    Gets username and password and returns user
    """

    def post(self, request, format=None):
        username = self.request.data['username']
        password = self.request.data['password']
        result = User.objects.filter(username=username).filter(password=password)
        resultjson = UserGetSerializer(result, many=True)
        print(resultjson.data)
        return Response(resultjson.data, content_type='application/json')
