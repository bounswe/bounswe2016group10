from django.shortcuts import render
from django.shortcuts import get_object_or_404
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

from django.http import Http404
from rest_framework import viewsets
from django.contrib.auth.models import User, Group
from rest_framework.renderers import JSONRenderer
from TagLifeApp.models import Topic
from TagLifeApp.serializers import TopicSerializer,UserSerializer
from rest_framework.parsers import JSONParser



class TopicList(APIView):
    """
    List all snippets or create one
    """
    def get(self, request, format=None):
        topics = Topic.objects.all()
        serializer = TopicSerializer(topics, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        # author = User.objects.get(pk=request.data["author"])
        # userSerializer = UserSerializer(data=author)
        # request.data['author'] = userSerializer
        serializer = TopicSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    # def perform_create(self,serializer):
    #     serializer.save(author=self.request.user)

class TopicDetail(APIView):
    """
    Retrieve, update and delete a topic instance
    """
    def get_object(self,pk):
        try:
            return Topic.objects.get(pk=pk)
        except:
            raise Http404

    def get(self, request, pk,format=None):
        topic = self.get_object(pk)
        serializer = TopicSerializer(topic)
        return Response(serializer.data)

    def put(self, request, pk, format=None):

        topic = self.get_object(pk)
        serializer = TopicSerializer(topic, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        topic = self.get_object(pk)
        topic.delete();
        return Response(status=status.HTTP_204_NO_CONTENT)

class UserList(APIView):
    """
    List Users
    """

    def get(self,request, format=None):
        users = User.objects.all()
        serializer = UserSerializer(users, many=True)
        return Response(serializer.data)


class UserDetail(APIView):
    """
    Retrive specific user
    """

    def get(self, pk):
        try:
            user = User.objects.get(pk=pk)
        except:
            raise Http404
        serializer = UserSerializer(user)
        return Response(serializer.data)

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