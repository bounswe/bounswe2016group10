var userAuth = false;
var userID = getParameterByName('user');
console.log(userID);
if (userID) {
  userAuth = true;
}

var topicID = getParameterByName('id');
var topictitle = getParameterByName('title');
$('#addTagButton').text('Add Relation to ' + topictitle);
$('#tagTopicModalLabel').html(`Create Tag - Topic Relation to <strong> ${topictitle} </strong>`);
$('#topic_header').text(topictitle);

var userPromise = $.getJSON('http://localhost:8000/users/?format=json');
var entryPromise = $.getJSON('http://localhost:8000/topics/'+topicID+'/entries?format=json');

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
          <div class='col-offset-2' id="alert-add-comment"></div>            
      </form>
      </div>
      `);
    
    if (entry.comments.length != 0 ) {
      var commentsPromise = $.getJSON('http://localhost:8000/entries/'+ entry.id +'/comments/?format=json');

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

        if (userAuth) {
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
            url: "http://localhost:8000/comments/create/",
            data: commentJSON,
            dataType: "json",
            contentType: "application/json",
            error: function(xhr, textStatus, error) {
                $('#commentForm-entry'+entry.id).find('#alert-add-comment').append(`<div class="alert alert-danger" role="alert">
                           <strong>Oopss..</strong> Entry could not added due to ${error}.
                       </div>`);
                console.log(xhr.statusText);
                  console.log(textStatus);
                  console.log(error);
            },
            success: function(data) {
                $('#commentForm-entry'+entry.id).find('#alert-add-comment').append(`<div class="alert alert-success" role="alert">
                           <strong>Comment successfully added..</strong> 
                       </div>`);
              console.log(data);
              setTimeout(function() { location.reload(true)}, 1500);
            }
          });

          return false;
        }
        else{
          $('#commentForm-entry'+entry.id).find('#alert-add-comment').append(`<div class="alert alert-danger" role="alert">
                     <strong>Login to add comment!</strong>.
                 </div>`);
        }
    });
  });
});


$('#entryForm').submit(function(event) {

  if (userAuth) {
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
      url: "http://localhost:8000/entries/create/",
      data: entryJSON,
      dataType: "json",
      contentType: "application/json",
      error: function(xhr, textStatus, error) {
         $('#alert-add-entry').append(`<div class="alert alert-danger" role="alert">
                      <strong>Oopss..</strong> Entry could not added due to ${error}.
                  </div>`);
          console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
      },
      success: function(data) {
        $('#alert-add-entry').append(`<div class="alert alert-success" role="alert">
                      <strong>Entry successfully added..</strong>
                  </div>`);
        console.log(data);
        setTimeout(function() { location.reload(true)}, 1500);
      }
    });

    return false;
  }
  else{
    $('#alert-add-entry').append(`<div class="alert alert-danger" role="alert">
                      <strong>Login to add entry!</strong> 
                  </div>`);
  }
});

$('#modal-addTopic-form').submit(function(event) {

  if (userAuth) {
    var topicObj = {};
    var title = $(this).find("input[name='title']").val() ;
    var userID = $(this).find("input[name='user']").val() ;
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
        $('#alert-add-topic').append(`<div class="alert alert-danger" role="alert">
                      <strong>Oopss..</strong> Topic could not added due to ${error}.
                  </div>`);
          console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
      },
      success: function(data) {
        $('#alert-add-topic').append(`<div class="alert alert-success" role="alert">
                      <strong>Topic successfully added..</strong>
                  </div>`);
        console.log(data);
        setTimeout(function() { location.reload(true)}, 1500);
      }
    });

    return false;
  }
  else{
    $('#alert-add-topic').append(`<div class="alert alert-danger" role="alert">
                      <strong>Login to create a topic...</strong> 
                  </div>`);
  }
});

var predicatePromise = $.getJSON('http://localhost:8000/predicates');
var tagPromise = $.getJSON('http://localhost:8000/tags');

$.when(predicatePromise, tagPromise).then(function(predicates, tags) {
  predicate = predicates[0]['results'];
  tag = tags[0]['results'];

 $.each(predicate, function(i, pre) {
   $('#modal-addTag-form').find('select[name="predicateSelect"]').append(`<option value='${pre.id}'> ${pre.predicateString} </option>`);
 });
 
 $.each(tag, function(i, tag) {
   $('#modal-addTag-form').find('select[name="tagSelect"]').append(`<option value='${tag.id}'> ${tag.tagString} </option>`);
 });

});
$('#modal-addTag-form').submit(function(event) {

  if (userAuth) {
    var relObj = {};
    var topic = parseInt(topicID);
    var predicate = $(this).find('select[name="predicateSelect"]').val() ;
    var tag = $(this).find('select[name="tagSelect"]').val() ;
    
    relObj['topic'] = topic ;
    relObj['predicate'] = predicate ;
    relObj['tag'] = tag ;

    var relJSON = JSON.stringify(relObj);

    $.ajax({
      type: "POST",
      url: "http://localhost:8000/topictagrelations/create/",
      data: relJSON,
      dataType: "json",
      contentType: "application/json",
      error: function(xhr, textStatus, error) {
        $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                      <strong>Oopss..</strong> Relation could not added due to ${error}.
                  </div>`);
          console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
      },
      success: function(data) {
        $('#alert-add-tag').append(`<div class="alert alert-success" role="alert">
                      <strong>Relation successfully added..</strong>
                  </div>`);
        console.log(data);
        setTimeout(function() { location.reload(true)}, 1500);
      }
    });

    return false;
  }
  else{
    $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                  <strong>Login to add relation!</strong>
              </div>`);
  }
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