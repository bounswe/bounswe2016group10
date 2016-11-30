from django.shortcuts import render
from django.shortcuts import get_object_or_404
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from rest_framework import generics

from django.http import Http404
from rest_framework import viewsets
from rest_framework.renderers import JSONRenderer
from TagLifeApp.models import Topic
from django.contrib.auth.models import User
from TagLifeApp.serializers import TopicSerializer,TopicGetSerializer,UserGetSerializer, UserSerializer
from rest_framework.parsers import JSONParser



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

    serializer_class = UserSerializer

# class TagLifeUserDetail(APIView):
#     """
#     Retrive specific TagLifeUser
#     """
#
#     def get(self, pk):
#         try:
#             TagLifeUser = TagLifeUser.objects.get(pk=pk)
#         except:
#             raise Http404
#         serializer = TagLifeUserSerializer(TagLifeUser)
#         return Response(serializer.data)



# @csrf_exempt
# @api_view(['GET', 'POST'])
# def topic_list(request, format=None):
#     """
#     List all topics, or create a new one
#     :param request:
#     :return:
#     """
#     if request.method=='GET':
#         topics = Topic.objects.all()
#         serializer = TopicSerializer(topics, many=True)
#         return Response(serializer.data)
#
#     elif request.method=='POST':
#         serializer = TopicSerializer(data=request.data)
#         if serializer.is_valid():
#             serializer.save()
#             return Response(serializer.data, status=status.HTTP_201_CREATED)
#         return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
#
# @csrf_exempt
# @api_view(['GET', 'PUT', 'DELETE'])
# def topic_detail(request, pk, format=None):
#     """
#     Retrieve, update or delete a topic
#     :param request:
#     :param pk:
#     :return:
#     """
#
#     try:
#         topic = Topic.objects.get(pk=pk)
#     except Topic.DoesNotExist:
#         return Response(status=status.HTTP_404_NOT_FOUND)
#
#     if request.method=='GET':
#         serializer = TopicSerializer(topic)
#         return Response(serializer.data)
#
#     elif request.method=='PUT':
#         data = JSONParser().parse(request)
#         serializer = TopicSerializer(topic, data=data)
#         if serializer.is_valid():
#             serializer.save()
#             return Response(serializer.data)
#         return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
#
#     elif request.method=='DELETE':
#         topic.delete()
#         return Response(status=status.HTTP_204_NO_CONTENT)