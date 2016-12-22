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
var topicPromise = $.getJSON('http://localhost:8000/topics/');
var userPromise = $.getJSON('http://localhost:8000/users/');
var relationPromise = $.getJSON('http://localhost:8000/topictagrelations/');
var tagPromise = $.getJSON('http://localhost:8000/tags/');
var predPromise = $.getJSON('http://localhost:8000/predicates/');
var availableTopics = [];

$.when(userPromise,topicPromise,relationPromise,tagPromise,predPromise).then(function (users, topics, rels, tags, preds) {
	var userlist = users[0]['results'];
	var topiclist1 = topics[0]['results'];
	var relList = rels[0]['results'];
	var tagList = tags[0]['results'];
	var predList = preds[0]['results'];
	
	showTopics(topiclist1);

	addTagNodeset(tagList);
	addTopicNodeset(topiclist1);
	addRelationEdge(relList,predList);

	function showTopics(topics) {
		
		$.each(topics, function(i,topic){
			
			var username = "";
			
			
			
			$.each(userlist, function(i,user) {
				if (topic.user == user.id) 
					username = user.username;
			});

			var topicrels =[];
			$.each(topic.relations, function(i,topicRelID) {
				$.each(relList,function(i,rel) {
					if (rel.id == topicRelID) {
						var topicTagTitle = tagList.find(function(tag) {
						    return tag.id === rel.tag;
						}).tagString;
						var tuple = { id : rel.tag, title : topicTagTitle};
						topicrels.push(tuple); //tuple tagid
					}
				});
			});
			
			$('#topics_list').append(`<div class="well">
		       <div class="media-body">
		         <a href="./topic.html?id=${topic.id}&title=${topic.title}"><h4 align="center" class="media-heading">${topic.title}</a> </h4> 
		 
		         <ul class="list-inline list-unstyled" >
		           <li id="tags_list${topic.id}">Tags:</li>
		           <li>|</li>
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
			topicrels.forEach(function(tag) {
				$('#tags_list'+topic.id).append("#<a href='./tag.html?tag="+ tag.id +"&title="+tag.title+"'><span class='label label-info'>" + tag.title  + " </span></a> ");
				$('#topics').append(`<option value='${topic.title} #${tag.title}' name='${topic.title}' id='${topic.id}'></option>`);
			});
		});
	}
	
});


var popularPromise = $.getJSON('http://localhost:8000/topics/popular?format=json');
$.when(popularPromise).then(function(topics) {
  $.each(topics['results'],function(i, topic) {
    $('#popTopics').append(`<li class="list-group-item"><a href="./topic.html?id=${topic.id}&title=${topic.title}">${topic.title}</a></li>`);
  });
});

$('#addTopicForm').submit(function(event) {

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


$('#redirectTopic').submit(function(event) {
		event.preventDefault();
		var topTitle = $(this).find("input[name='topList']").val()  ;
		var topID = $(this).find("option[value='"+topTitle+"']").attr("id");
		location.href = './topic.html?id='+ topID + '&title='+ topTitle ;
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
