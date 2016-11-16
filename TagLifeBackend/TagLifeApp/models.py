from django.db import models
from django.contrib.auth.models import User


class Topic(models.Model):

    title = models.CharField(max_length=128)

    def __str__(self):
        return self.title


class Entry(models.Model):

    topic = models.ForeignKey(Topic)
    content = models.CharField(max_length=512)
    date = models.DateTimeField('Date Published',auto_now_add=True)

    def __str__(self):
        return self.content

class UserProfile(models.Model):
    user = models.OneToOneField(User)

    website = models.URLField(blank=True)
    picture = models.ImageField(upload_to='profile_images', blank=True)

    def __unicode__(self):
        return self.user.username