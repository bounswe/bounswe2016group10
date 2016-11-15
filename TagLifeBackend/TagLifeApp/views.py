from django.shortcuts import render

from django.http import HttpResponse

def index(request):
    return HttpResponse("Taglife Welcomes You")

def beyler(request):
    return HttpResponse("Hadi beyler hadi")

