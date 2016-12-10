var topicID = getParameterByName('id');
var topictitle = getParameterByName('title');
$('#topic_header').text(topictitle);

var userPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/users/?format=json');
var entryPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/topics/'+topicID+'/entries?format=json');

$.when(userPromise, entryPromise).then(function(users, entries) {
  var userList = users[0]['results'];
  var entryList = entries[0]['results'];
  $.each(entryList, function(i,entry){
    var username = "";

    $.each(userList, function(i,user) {
      if (entry.user == user.id) 
        username = user.username;
    });
    
    $('#entry_list').append(`
      <li class="media">
        <div class="media-body">
          <div class="well well-lg">
              <em> <h4 class="media-heading text-capitalize reviews">${username}</h4> </em>
              <ul class="media-date text-capitalize reviews list-inline">
                <em> <li class="dd">${jQuery.timeago(entry.updated_at)} </li> </em>
              </ul>
              <p class="media-comment">
                ${entry.content}
              </p>
              <a class="btn btn-info btn-circle text-uppercase" data-toggle="collapse" href="#div-reply-form-entry${entry.id}"><span class="glyphicon glyphicon-share-alt"></span> Reply</a>
              <a class="btn btn-warning btn-circle text-uppercase" data-toggle="collapse" href="#div-reply-entry${entry.id}"><span class="glyphicon glyphicon-comment"></span> ${entry.comments.length} comments</a>
          </div>
        </div>
      </li>
      <div class="collapse" id="div-reply-entry${entry.id}">
        <ul class="media-list" id="list-reply-entry${entry.id}">

        </ul>
      </div>
      <div class="collapse" id="div-reply-form-entry${entry.id}">
      <form action="#" method="post" class="form-horizontal" id="commentForm-entry${entry.id}" role="form" style="margin-left: 25px"> 
          <div class="form-group">
              <label for="content" class="col-sm-2 control-label">Comment</label>
              <div class="col-sm-10">
                <textarea class="form-control" name="content" rows="3"></textarea>
              </div>
              <label for="user"> User ID </label>
              <input type="number" class="form-control" name="user" />
          </div>
          
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">             
                <button class="btn btn-success btn-circle text-uppercase" type="submit" ><span class="glyphicon glyphicon-send"></span> Submit Comment</button>
            </div>
          </div>            
      </form>
      </div>
      `);
    
    if (entry.comments.length != 0 ) {
      var commentsPromise = $.getJSON('http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/entries/'+ entry.id +'/comments/?format=json');

      $.when(commentsPromise).then(function(comments) {
        
        $.each(comments['results'],function(i, comment) {
          var comment_username ="";
          $.each(userList, function(i,user) {
            if (comment.user == user.id) 
              comment_username = user.username;
          });
          
          $('#list-reply-entry'+entry.id).append(`
            <li class="media media-replied">
            <div class="media-body">
              <div class="well">
                  <em> <h5 class="media-heading text-capitalize reviews">${comment_username} </h5> </em>
                  <ul class="media-date text-capitalize reviews list-inline">
                    <em> <li>${jQuery.timeago(comment.updated_at)} </li> </em>
                  </ul>
                  <p class="media-comment">
                    ${comment.content}
                  </p>
              </div>              
            </div>
            </li>
            `);
        });
      });
    }

    $('#commentForm-entry'+entry.id).submit(function(event) {

      var commentObj = {};
      var content = $(this).find("textarea[name='content']").val();
      var userID = $(this).find("input[name='user']").val() ;
      userID = parseInt(userID);
      var entryID = parseInt(entry.id);
      
      commentObj['content'] = content ;
      commentObj['entry'] = entryID;
      commentObj['user'] = userID;

      var commentJSON = JSON.stringify(commentObj);
      console.log(commentJSON);

      $.ajax({
        type: "POST",
        url: "http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/comments/create/",
        data: commentJSON,
        dataType: "json",
        contentType: "application/json",
        error: function(xhr, textStatus, error) {
            console.log(xhr.statusText);
              console.log(textStatus);
              console.log(error);
        },
        success: function(data) {
          // $('#entryForm-result').append('<p> Entry successfully added </p>');
          console.log(data);
        }
      });

      return false;
    });
  });
});


$('#entryForm').submit(function(event) {

  var entryObj = {};
  var content = $(this).find("textarea[name='content']").val() ;
  var userID = $(this).find("input[name='user']").val() ;
  userID = parseInt(userID);
  topicID = parseInt(topicID);
  entryObj['topic'] = topicID;
  entryObj['content'] = content ;
  entryObj['user'] = userID;


  var entryJSON = JSON.stringify(entryObj);
  console.log(entryJSON);
  $.ajax({
    type: "POST",
    url: "http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/entries/create/",
    data: entryJSON,
    dataType: "json",
    contentType: "application/json",
    error: function(xhr, textStatus, error) {
        console.log(xhr.statusText);
          console.log(textStatus);
          console.log(error);
    },
    success: function(data) {
      $('#entryForm-result').append('<p> Entry successfully added </p>');
      console.log(data);
    }
  });

  return false;
});

$('#modal-addTopic-form').submit(function(event) {

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