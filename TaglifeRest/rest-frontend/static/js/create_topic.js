$('form').submit(function(event) {
	event.preventDefault();
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
	  }
	});
});
// http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/topics/create/