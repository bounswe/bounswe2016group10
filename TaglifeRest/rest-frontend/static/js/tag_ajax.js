var userAuth = false;
var userID = $.session.get('userID');

if (userID) {
  userAuth = true;
}

if (userAuth) {
  $('#navUserAuth').append(`<li><a href="./profile.html?user=${userID}">
            <span  class="glyphicon glyphicon-user bg-success pull-right" style="font-size:2.5em; margin-top: -5px"></span></a>
         </li> 
         <li>
             <a id="logout" onclick="logout();return false;" href="#"> Logout</a>
         </li> `);
}
else{
  $('#navUserAuth').append(`<li><a href="./register.html">Register</a></li>
         <li><a href="./login.html">Login </a></li>`);
}

var tagID = getParameterByName('tag');
tagID = parseInt(tagID);
var tagTitle = getParameterByName('title');
$('#tag_header').html('Topics tagged with <em><b>' + tagTitle +'</b></em>');

var topicsIDs = [];

var relationPromise = $.getJSON('http://localhost:8000/topictagrelations/');
$.when(relationPromise).then(function(relation) {
  $.each(relation['results'], function(i,rel) {
    if (rel.tag == tagID) {
      topicsIDs.push(rel.topic);
    }
  });
});

var userPromise = $.getJSON('http://localhost:8000/users/?format=json');
var topicPromise = $.getJSON('http://localhost:8000/topics/?format=json');
$.when(userPromise,topicPromise).then(function (users, topics) {
  var userlist = users[0]['results'];
  var topiclist = topics[0]['results'];
  
  var username = "";
  $.each(topicsIDs, function(i,tagTopicID){
    var topic = topiclist.find(function(topic) {
      return topic.id === tagTopicID;
    });

    username = userlist.find(function(user) {
      return user.id === topic.user;
    }).username ;
    
    $('#topics_list').append(`<div class="well">
           <div class="media-body" style="text-align: center; ">
             <a href="./topic.html?id=${topic.id}&title=${topic.title}"><h4  class="media-heading">${topic.title}</a> </h4> 
     
             <ul class="list-inline list-unstyled" style="display: table;   margin-right: auto;   margin-left: auto;" >
              
               <li>
                 <a href="./profile.html?user=${topic.user}"><i class="glyphicon glyphicon-user" ></i> ${username}</a> 
               </li>
               <li>|</li>
               <li><span><i class="glyphicon glyphicon-calendar"></i> ${jQuery.timeago(topic.updated_at)} </span></li>
               <li>|</li>
               <i class="glyphicon glyphicon-comment"></i> ${topic.entries.length} entries
               <li>|</li>
               <li>
                  ${topic.followers.length} follower
               </li>
             </ul>
           </div>
        </div>`);

  });
  
});


var popularPromise = $.getJSON('http://localhost:8000/topics/popular?format=json');
$.when(popularPromise).then(function(topics) {
  $.each(topics['results'],function(i, topic) {
    $('#popTopics').append(`<li class="list-group-item"><a href="./topic.html?id=${topic.id}&title=${topic.title}">${topic.title}</a></li>`);
  });
});


$('#topCreate').submit(function(event) {

  if(userAuth){
    var topicObj = {};
    var title = $(this).find("input[name='title']").val() ;
    userID = parseInt(userID);
    topicObj['title'] = title ;
    topicObj['user'] = userID;

    var topicJSON = JSON.stringify(topicObj);

    $.ajax({
      type: "POST",
      url: "http://localhost:8000/topics/create/",
      data: topicJSON,
      dataType: "json",
      contentType: "application/json",
      error: function(xhr, textStatus, error) {
          $('#alert').append(`<div class="alert alert-danger" role="alert">
                      <strong>Oopss..</strong> Topic could not added due to ${error}.
                  </div>`);
          console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
      },
      success: function(data) {
        $('#alert').append(`<div class="alert alert-success" role="alert">
                      <strong>Topic successfully added..</strong>
                  </div>`);
        console.log(data);
        setTimeout(function() { location.reload(true)}, 1500);
      }
    });

    
  }else{
    $('#alert').append(`<div class="alert alert-danger" role="alert">
                      <strong>Login to create a topic...</strong>
                  </div>`);
  }
  return false; 
});

function logout() {
  $.session.clear();
  location.href = './index.html';
}
function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

$('#redirectTopic').submit(function(event) {
		event.preventDefault();
		var topTitle = $(this).find("input[name='topList']").val()  ;
		var topID = $(this).find("option[name='"+topTitle+"']").attr("id");
		location.href = './topic.html?id='+ topID + '&title='+ topTitle ;
		return false;
		
});