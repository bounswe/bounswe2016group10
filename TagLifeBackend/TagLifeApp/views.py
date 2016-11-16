from django.shortcuts import render

from django.http import HttpResponse
from django.template import RequestContext
from django.shortcuts import render_to_response
from TagLifeApp.models import Entry, Topic
from TagLifeApp.forms import EntryForm, TopicForm


def index(request):
    context = RequestContext(request)

    topic_list = Topic.objects.order_by('title')
    context_dict = {'topics': topic_list}

    for topic in topic_list:
        topic.url = topic.title.replace(' ','_')

    return render_to_response('index.html', context_dict, context)


def topic(request, topic_name_url):
    context = RequestContext(request)
    topic_name = topic_name_url.replace('_', ' ')
    context_dict = {"topic_name": topic_name}

    try:
        topic = Topic.objects.get(title=topic_name)
        entrys = Entry.objects.filter(topic=topic)

        context_dict['entrys'] = entrys
        context_dict['topic'] = topic_name

    except Topic.DoesNotExist:
        pass

    return render_to_response('topic.html', context_dict, context)

def create_topic(request):
    context = RequestContext(request)

    if request.method == 'POST':
        form = TopicForm(request.POST)

        # Have we been provided with a valid form?
        if form.is_valid():

            # Save the new category to the database.
            form.save(commit=True)

            # Now call the index() view.
            # The user will be shown the homepage.

            return index(request)

    else:
        # If the request was not a POST, display the form to enter details.
        form = TopicForm()

    # Bad form (or form details), no form supplied...
    # Render the form with error messages (if any).
    return render_to_response('create_topic.html', {'form': form,}, context)