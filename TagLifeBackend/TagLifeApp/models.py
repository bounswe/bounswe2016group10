from django.db import models

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
