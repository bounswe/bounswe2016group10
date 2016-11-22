from django.contrib import admin
from django.contrib.auth.models import User
from TagLifeApp.models import Topic, Comment,Entry,EntryTagRelation,FollowTopicRelation,Predicate,Tag,TopicTagRelation

admin.site.register(Topic)
admin.site.register(Entry)
admin.site.register(Comment)
admin.site.register(EntryTagRelation)
admin.site.register(FollowTopicRelation)
admin.site.register(Predicate)
admin.site.register(Tag)
admin.site.register(TopicTagRelation)