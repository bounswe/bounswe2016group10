from django.db import models
from django.utils import timezone
from django.contrib.auth.models import User

# class TagLifeUser(models.Model):
#     REQUIRED_FIELDS = ('user',)
#     user = models.OneToOneField(User, on_delete=models.CASCADE)
#
#
#     def save(self, *args, **kwargs):
#         return super(TagLifeUser, self).save(*args, **kwargs)
#
#     def __str__(self):
#         return str(self.id) + ' ' + self.username

class Topic(models.Model):
    title = models.CharField(max_length=128)
    user = models.ForeignKey(User, related_name='topics', on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def save(self, *args, **kwargs):
        return super(Topic, self).save(*args, **kwargs)

    def __unicode__(self):
        return str(self.id) + ' ' +self.title

class Entry(models.Model):
    content = models.CharField(max_length=512)
    topic = models.ForeignKey(Topic, related_name='entries')
    user = models.ForeignKey(User, related_name='entries')
    vote = models.IntegerField()
    created = models.DateTimeField(auto_now_add=True)

    def __unicode__(self):
        return str(self.id) + ' ' +self.content

class Comment(models.Model):
    content = models.CharField(max_length=512)
    entry = models.ForeignKey(Entry, related_name='comments')
    author = models.ForeignKey(User, related_name='comments')
    vote = models.IntegerField()
    created = models.DateTimeField(auto_now_add=True)

    def __unicode__(self):
        return self.content

class Tag(models.Model):
    tagString = models.CharField(max_length=128)

    def __unicode__(self):
        return self.tagString

class Predicate(models.Model):
    predicateString = models.CharField(max_length=64)

    def __unicode__(self):
        return self.predicateString


class TopicTagRelation(models.Model):
    topic = models.ForeignKey(Topic, related_name='tags')
    predicate = models.ForeignKey(Predicate, related_name='types')
    tag = models.ForeignKey(Tag, related_name='relatedTopic')

    def __unicode__(self):
        return self.pk

class EntryTagRelation(models.Model):
    entry = models.ForeignKey(Entry, related_name='tags')
    tag = models.ForeignKey(Tag, related_name='relatedEntry')

    def __unicode__(self):
        return self.pk

class FollowTopicRelation(models.Model):
    topic = models.ForeignKey(Topic, related_name= 'follower')
    user = models.ForeignKey(User, related_name='following')

    def __unicode__(self):
        return self.pk
