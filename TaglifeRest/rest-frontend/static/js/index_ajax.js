// $.ajaxSetup({
//     async: false
// });

var topicPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/topics/?format=json');
var userPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/users/?format=json');

$.when(userPromise,topicPromise).then(function (users, topics) {
	var userlist = users[0]['results'];
	var topiclist = topics[0]['results'];

	$.each(topiclist, function(i,topic){
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
           <a href="#"><i class="glyphicon glyphicon-comment"></i> ${topic.entries.length} entry</a>
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
	    	console.log(xhr.statusText);
    	    console.log(textStatus);
    	    console.log(error);
	  },
	  success: function(data) {
	  	alert("TOPIC SUCCESFULLY ADDED");
	  	console.log(data);
	  }
	});

	return false;
});