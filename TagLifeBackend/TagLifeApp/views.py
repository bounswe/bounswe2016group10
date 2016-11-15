from django.shortcuts import render

from django.http import HttpResponse
from django.template import RequestContext
from django.shortcuts import render_to_response
import TagLifeApp.models


def index(request):
    context = RequestContext(request)

    topic_list = TagLifeApp.models.Topic.objects.order_by('title')
    context_dict = {'topics': topic_list}

    for topic in topic_list:
        topic.url = topic.title.replace(' ','_')

    return render_to_response('index.html', context_dict, context)


def beyler(request):
    return HttpResponse("This Is How You Know You Fucked Up")


def topic(request, topic_name_url):
    context = RequestContext(request)
    topic_name = topic_name_url.replace('_', ' ')
    context_dict = {"topic_name": topic_name}

    try:
        topic = TagLifeApp.models.Topic.objects.get(title=topic_name)
        entrys = TagLifeApp.models.Entry.objects.filter(topic=topic)

        context_dict['entrys'] = entrys
        context_dict['topic'] = topic_name

    except TagLifeApp.models.Topic.DoesNotExist:
        pass

    return render_to_response('topic.html', context_dict, context)
