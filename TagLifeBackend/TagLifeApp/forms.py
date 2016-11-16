from django import forms
from TagLifeApp.models import Entry, Topic

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


