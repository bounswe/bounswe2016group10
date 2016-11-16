from django.shortcuts import render

from django.template import RequestContext
from django.shortcuts import render_to_response
from TagLifeApp.models import Entry, Topic, User, UserProfile
from TagLifeApp.forms import EntryForm, TopicForm, UserProfileForm, UserForm
from datetime import datetime
from django.contrib.auth import authenticate, login, logout
from django.http import HttpResponseRedirect, HttpResponse
from django.contrib.auth.decorators import login_required


def index(request):
    context = RequestContext(request)

    topic_list = Topic.objects.order_by('title')
    context_dict = {'topics': topic_list}
    context_dict['user'] = request.user


    for topic in topic_list:
        topic.url = topic.title.replace(' ','_')

    if request.user.is_authenticated():
        print(request.user)
    else:
        print("WHY")
    return render_to_response('index.html', context_dict, context)


def topic(request, topic_name_url):
    context = RequestContext(request)
    topic_name = topic_name_url.replace('_', ' ')
    context_dict = {"topic_name": topic_name}
    context_dict["topic_name_url"] = topic_name_url

    try:
        topic = Topic.objects.get(title=topic_name)
        entrys = Entry.objects.filter(topic=topic)

        context_dict['entrys'] = entrys
        context_dict['topic'] = topic_name

    except Topic.DoesNotExist:
        pass

    return render_to_response('topic.html', context_dict, context)

@login_required
def create_topic(request):
    context = RequestContext(request)

    if request.method == 'POST':
        form = TopicForm(request.POST)

        # Have we been provided with a valid form?
        if form.is_valid():

            # Save the new topic to the database.
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

@login_required
def create_entry(request, topic_name_url):
    context = RequestContext(request)

    topic_name = topic_name_url.replace('_',' ')

    if request.method =='POST':
        form = EntryForm(request.POST)

        if form.is_valid():
            # This time we cannot commit straight away.
            # Not all fields are automatically populated!
            entry = form.save(commit=False)

            # Retrieve the associated Topic object so we can add it.
            # Wrap the code in a try block - check if the topic actually exists!
            try:
                topic = Topic.objects.get(title=topic_name)
                entry.topic = topic
                entry.date = datetime.now()
            except topic.DoesNotExist:
                # If we get here, the topic does not exist.
                # Go back and render the add topic form as a way of saying the topic does not exist.
                return render_to_response('/create_topic.html', {}, context)

            entry.save()

            # Now that the entry is saved, display the topic instead.
            #return topic(request, topic_name_url)
            return HttpResponseRedirect('/topic/'+topic_name_url)
    else:
        form = EntryForm()


    return render_to_response('create_entry.html',{
        'topic_name_url' : topic_name_url,
        'topic_name' : topic_name, 'form' : form,
        },context)


def register(request):
    # Like before, get the request's context.
    context = RequestContext(request)

    # A boolean value for telling the template whether the registration was successful.
    # Set to False initially. Code changes value to True when registration succeeds.
    registered = False

    # If it's a HTTP POST, we're interested in processing form data.
    if request.method == 'POST':
        # Attempt to grab information from the raw form information.
        # Note that we make use of both UserForm and UserProfileForm.
        user_form = UserForm(data=request.POST)
        profile_form = UserProfileForm(data=request.POST)

        # If the two forms are valid...
        if user_form.is_valid() and profile_form.is_valid():
            # Save the user's form data to the database.
            user = user_form.save()

            # Now we hash the password with the set_password method.
            # Once hashed, we can update the user object.
            user.set_password(user.password)
            user.save()

            # Now sort out the UserProfile instance.
            # Since we need to set the user attribute ourselves, we set commit=False.
            # This delays saving the model until we're ready to avoid integrity problems.
            profile = profile_form.save(commit=False)
            profile.user = user

            # Did the user provide a profile picture?
            # If so, we need to get it from the input form and put it in the UserProfile model.
            if 'picture' in request.FILES:
                profile.picture = request.FILES['picture']

            # Now we save the UserProfile model instance.
            profile.save()

            # Update our variable to tell the template registration was successful.
            registered = True

    # Not a HTTP POST, so we render our form using two ModelForm instances.
    # These forms will be blank, ready for user input.
    else:
        user_form = UserForm()
        profile_form = UserProfileForm()

    # Render the template depending on the context.
    return render_to_response(
            'register.html',
            {'user_form': user_form, 'profile_form': profile_form, 'registered': registered},
            context)

def user_login(request):
    # Like before, obtain the context for the user's request.
    context = RequestContext(request)

    # If the request is a HTTP POST, try to pull out the relevant information.
    if request.method == 'POST':
        # Gather the username and password provided by the user.
        # This information is obtained from the login form.
        username = request.POST['username']
        password = request.POST['password']

        # Use Django's machinery to attempt to see if the username/password
        # combination is valid - a User object is returned if it is.
        user = authenticate(username=username, password=password)

        # If we have a User object, the details are correct.
        # If None (Python's way of representing the absence of a value), no user
        # with matching credentials was found.
        if user:
            # Is the account active? It could have been disabled.
            if user.is_active:
                # If the account is valid and active, we can log the user in.
                # We'll send the user back to the homepage.
                login(request, user)
                #return HttpResponseRedirect('/')
                return HttpResponseRedirect('/')
            else:
                # An inactive account was used - no logging in!
                return HttpResponse("Your TagLife account is disabled.")
        else:
            # Bad login details were provided. So we can't log the user in.
            print('Invalid login details: {0}, {1}'.format(username, password))
            return HttpResponse("Invalid login details supplied.")

    # The request is not a HTTP POST, so display the login form.
    # This scenario would most likely be a HTTP GET.
    else:
        # No context variables to pass to the template system, hence the
        # blank dictionary object...
        return render_to_response('login.html', {}, context)


@login_required
def user_logout(request):
    # Since we know the user is logged in, we can now just log them out.
    logout(request)

    # Take the user back to the homepage.
    return HttpResponseRedirect('/')