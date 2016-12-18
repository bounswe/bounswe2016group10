// $.ajaxSetup({
//     async: false
// });

var topicPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/topics/');
var userPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/users/');

$.when(userPromise,topicPromise).then(function (users, topics) {
	var userlist = users[0]['results'];
	var topiclist1 = topics[0]['results'];
	showTopics(topiclist1);
	addTopicNodeset(topiclist1);

	checkNext(topics[0]);

	function getNext(promiseUrl) {
		var nextTopic = $.getJSON(promiseUrl);
		nextTopic.done( function(data) {
				showTopics(data['results']);
				addTopicNodeset(data['results']);
			});

	}

	function checkNext(e) {
		// console.log(e.next);
		if (e.next) {
			getNext(e.next);
		}
	}


	function showTopics(topics) {
		$.each(topics, function(i,topic){
			var username = "";
			
			$.each(userlist, function(i,user) {
				if (topic.user == user.id) 
					username = user.username;
			});
			
			$('#topics_list').append(`<div class="well">
	       <div class="media-body">
	         <a href="./topic.html?id=${topic.id}&title=${topic.title}"><h4 align="center" class="media-heading">${topic.title}</a> </h4> 
	 
	         <ul class="list-inline list-unstyled">
	           <li>Tags:
	               <a href="#"><span class="label label-info"></span></a> 
	               <a href="#"><span class="label label-info"></span></a> 
	               <a href="#"><span class="label label-info"></span></a> 
	           </li>
	           <li>|</li>
	           <li>
	             <a href="#"><i class="glyphicon glyphicon-user" ></i> ${username}</a> 
	           </li>
	           <li>|</li>
	           <li><span><i class="glyphicon glyphicon-calendar"></i> ${jQuery.timeago(topic.updated_at)} </span></li>
	           <li>|</li>
	           <i class="glyphicon glyphicon-comment"></i> ${topic.entries.length} entries
	           <li>|</li>
	           <li>
	              ${topic.followers.length} follower
	           </li>
	           <li>|</li>
	           <li>
	             <span><i class="fa fa-facebook-square"></i></span>
	             <span><i class="fa fa-twitter-square"></i></span>
	             <span><i class="fa fa-google-plus-square"></i></span>
	           </li>
	         </ul>
	       </div>
	   </div>`);
		});
	}
});

$('form').submit(function(event) {

	var topicObj = {};
	var title = $(this).find("input[name='title']").val() ;
	var userID = $(this).find("input[name='user']").val() ;
	userID = parseInt(userID);
	topicObj['title'] = title ;
	topicObj['user'] = userID;

	var topicJSON = JSON.stringify(topicObj);

	$.ajax({
	  type: "POST",
	  url: "http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/topics/create/",
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

	return false;	
});