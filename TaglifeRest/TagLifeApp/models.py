from django.db import models
from django.utils import timezone
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver
#from rest_framework.authtoken.models import Token
from django.conf import settings


# This code is triggered whenever a new user has been created and saved to the database
# @receiver(post_save, sender=settings.AUTH_USER_MODEL)
# def create_auth_token(sender, instance=None, created=False, **kwargs):
#     if created:
#         Token.objects.create(user=instance)

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
    vote = models.IntegerField(default=0 , blank=True)
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def __unicode__(self):
        return str(self.id) + ' ' +self.content

class Comment(models.Model):
    content = models.CharField(max_length=512)
    entry = models.ForeignKey(Entry, related_name='comments')
    user = models.ForeignKey(User, related_name='comments')
    vote = models.IntegerField(default=0 , blank=True)
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def save(self, *args, **kwargs):
        return super(Comment, self).save(*args, **kwargs)

    def __unicode__(self):
        return str(self.id) + ' ' + self.content

class Tag(models.Model):
    tagString = models.CharField(max_length=128, unique=True)

    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def save(self, *args, **kwargs):
        return super(Tag, self).save(*args, **kwargs)

    def __unicode__(self):
        return self.tagString

class Predicate(models.Model):
    predicateString = models.CharField(max_length=64, unique=True)
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed


    def save(self, *args, **kwargs):
        return super(Predicate, self).save(*args, **kwargs)
    def __unicode__(self):
        return self.predicateString


class TopicTagRelation(models.Model):
    topic = models.ForeignKey(Topic, related_name='relations')
    predicate = models.ForeignKey(Predicate, related_name='relations')
    tag = models.ForeignKey(Tag, related_name='relations')
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def save(self, *args, **kwargs):
        return super(TopicTagRelation, self).save(*args, **kwargs)

    class Meta:
        unique_together = ('topic', 'tag')

    def __unicode__(self):
        return self.pk

class EntryTagRelation(models.Model):
    entry = models.ForeignKey(Entry, related_name='relations')
    tag = models.ForeignKey(Tag, related_name='relatedEntries')
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def save(self, *args, **kwargs):
        return super(EntryTagRelation, self).save(*args, **kwargs)

    def __unicode__(self):
        return self.pk

class FollowTopicRelation(models.Model):
    topic = models.ForeignKey(Topic, related_name= 'followers')
    user = models.ForeignKey(User, related_name='followings')
    created_at = models.DateTimeField(auto_now_add=True) #Add time when added
    updated_at = models.DateTimeField(auto_now=True) #Add time when changed

    def save(self, *args, **kwargs):
        return super(FollowTopicRelation, self).save(*args, **kwargs)

    def __unicode__(self):
        return self.pk
