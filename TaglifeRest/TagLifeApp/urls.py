from django.conf.urls import url
from TagLifeApp import views
from rest_framework.urlpatterns import format_suffix_patterns
from django.views.generic import TemplateView

urlpatterns = [

    #Frontend
    url(r'^$', TemplateView.as_view(template_name="index.html"), name='index'),
    url(r'^index\.html$', TemplateView.as_view(template_name="index.html"), name='index'),
    url(r'^topic\.html$', TemplateView.as_view(template_name="topic.html"), name='topics'),
    url(r'^tag\.html$', TemplateView.as_view(template_name="tag.html"), name='tags'),
    url(r'^login\.html$', TemplateView.as_view(template_name="login.html"), name='login'),
    url(r'^register\.html$', TemplateView.as_view(template_name="register.html"), name='register'),
    url(r'^create_topic\.html$', TemplateView.as_view(template_name="create_topic.html"), name='create_topic'),
    url(r'^profile\.html$', TemplateView.as_view(template_name="profile.html"), name='profile'),

    #login
    url(r'^login/$', views.Login.as_view()),

    #users
    url(r'^users/$', views.UserList.as_view()),
    url(r'^users/(?P<pk>[0-9]+)/$', views.UserDetail.as_view()),
    url(r'^users/create/$', views.UserCreate.as_view()),

    #topics
    url(r'^topics/$', views.TopicList.as_view()),
    url(r'^topics/(?P<pk>[0-9]+)/$', views.TopicDetail.as_view()),
    url(r'^topics/create/$', views.TopicCreate.as_view()),
    url(r'^topics/popular$', views.TopicPopular.as_view()),

    #entries
    url(r'^entries/$', views.EntryList.as_view()),
    url(r'^entries/(?P<pk>[0-9]+)/$', views.EntryDetail.as_view()),
    url(r'^topics/(?P<topicid>[0-9]+)/entries/$', views.EntryByTopicList.as_view()),
    url(r'^entries/create/$', views.EntryCreate.as_view()),

    #comments
    url(r'^comments/$', views.CommentList.as_view()),
    url(r'^comments/(?P<pk>[0-9]+)/$', views.CommentDetail.as_view()),
    url(r'^entries/(?P<entryid>[0-9]+)/comments/$', views.CommentByEntryList.as_view()),
    url(r'^comments/create/$',views.CommentCreate.as_view()),

    #tags
    url(r'^tags/$', views.TagList.as_view()),
    url(r'^tags/(?P<pk>[0-9]+)/$', views.TagDetail.as_view()),
    url(r'^tags/create/$',views.TagCreate.as_view()),

    #predicates
    url(r'^predicates/$', views.PredicateList.as_view()),
    url(r'^predicates/(?P<pk>[0-9]+)/$', views.PredicateDetail.as_view()),
    url(r'^predicates/create/$',views.PredicateCreate.as_view()),

    #topictagrelation
    url(r'^topictagrelations/$', views.TopicTagRelationList.as_view()),
    url(r'^topictagrelations/(?P<pk>[0-9]+)/$', views.TopicTagRelationDetail.as_view()),
    url(r'^topictagrelations/create/$',views.TopicTagRelationCreate.as_view()),

    #entrytagrelation
    url(r'^entrytagrelations/$', views.EntryTagRelationList.as_view()),
    url(r'^entrytagrelations/(?P<pk>[0-9]+)/$', views.EntryTagRelationDetail.as_view()),
    url(r'^entrytagrelations/create/$',views.EntryTagRelationCreate.as_view()),

    #followtopicrelation
    url(r'^followtopicrelations/$', views.FollowTopicRelationList.as_view()),
    url(r'^followtopicrelations/(?P<pk>[0-9]+)/$', views.FollowTopicRelationDetail.as_view()),
    url(r'^followtopicrelations/create/$',views.FollowTopicRelationCreate.as_view()),

]

urlpatterns = format_suffix_patterns(urlpatterns)