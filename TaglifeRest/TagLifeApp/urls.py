from django.conf.urls import url
from TagLifeApp import views
from rest_framework.urlpatterns import format_suffix_patterns


urlpatterns = [
    url(r'^topics/$', views.TopicList.as_view()),
    url(r'^topics/create$', views.TopicCreate.as_view()),
#   url(r'^topics/(?P<pk>[0-9]+)/$', views.TopicDetail.as_view()),
    url(r'^users/$', views.UserList.as_view()),
    url(r'^users/create$', views.UserCreate.as_view()),
#    url(r'^users/(?P<pk>[0-9]+)/$', views.UserDetail.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)