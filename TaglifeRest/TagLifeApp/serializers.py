from rest_framework import serializers
from django.contrib.auth.models import User, Group
from TagLifeApp.models import Topic
from datetime import datetime


class TopicSerializer(serializers.Serializer):
    id = serializers.IntegerField(read_only=True)
    title = serializers.CharField(required=True, max_length=128)
    created = serializers.DateTimeField(required=False)
    author = serializers.ReadOnlyField(source='author.id')

    def create(self, validated_data):
        """
        Create and return a new topic with given validated data
        :param validated_data:
        :return:
        """
        return Topic.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """
        Update the given instance with the new validated data
        :param instance:
        :param validated_data:
        :return:
        """

        instance.title = validated_data.get('title', instance.title)
        instance.save()
        return instance

    class Meta:
        model = Topic
        fields = ('id', 'title','created', 'author')


class UserSerializer(serializers.ModelSerializer):
    topics = serializers.PrimaryKeyRelatedField(many=True, queryset=Topic.objects.all())

    class Meta:
        model = User
        fields = ('id', 'username', 'email', 'topics', 'is_superuser')

