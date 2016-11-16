from django import forms
from TagLifeApp.models import Entry, Topic, UserProfile
from django.contrib.auth.models import User

class TopicForm(forms.ModelForm):
    title = forms.CharField(max_length=128, help_text='Please Enter The Title Name')

    # An inline class to provide additional information on the form.
    class Meta:
        # Provide an association between the ModelForm and a model
        model = Topic

        fields = ('title',)

class EntryForm(forms.ModelForm):
    content = forms.CharField(max_length=512, help_text='Please Write Your Entry')
    class Meta:
         # Provide an association between the ModelForm and a model
        model = Entry

        # What fields do we want to include in our form?
        # Some fields may allow NULL values, so we may not want to include them...
        # Here, we are hiding the foreign key.
        fields = ('content',)


class UserForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = User
        fields = ('username', 'email', 'password')

class UserProfileForm(forms.ModelForm):
    class Meta:
        model = UserProfile
        fields = ()