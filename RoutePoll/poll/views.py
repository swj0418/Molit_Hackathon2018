from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader

# Create your views here.


def index(request):
    template = loader.get_template('poll\index.html')
    context = {

    }
    # return HttpResponse(template.render(request, context))
    return render(request, 'poll/index.html')


def newpoll(request):
    return render(request, 'poll/newpoll.html')

def pollpost(request):
    if request.method == 'POST':
        print("")


def viewpoll(request):
    return render(request, 'poll/viewpoll.html')
