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
var topicID = getParameterByName('id');
var topictitle = getParameterByName('title');
$('#addTagButton').text('Add Relation to ' + topictitle);
$('#tagTopicModalLabel').html(`Create Tag - Topic Relation to <strong> ${topictitle} </strong>`);
$('#topic_header').html('<b> ' + topictitle + '</b>');

var userPromise = $.getJSON('http://localhost:8000/users/?format=json');
var entryPromise = $.getJSON('http://localhost:8000/topics/'+topicID+'/entries?format=json');
var topicPromise = $.getJSON('http://localhost:8000/topics/'+topicID+'?format=json');
var relationPromise = $.getJSON('http://localhost:8000/topictagrelations/');
var tagPromise = $.getJSON('http://localhost:8000/tags/');
var predPromise = $.getJSON('http://localhost:8000/predicates/');

$.when(userPromise, entryPromise, topicPromise, relationPromise, tagPromise, predPromise).then(function(users, entries, topic, relations, tags, preds) {
  var userList = users[0]['results'];
  var entryList = entries[0]['results'];
  var rels = topic[0]['relations'];
  var relList = relations[0]['results'];
  var tagList = tags[0]['results'];
  var predList = preds[0]['results'];

  $.each(rels, function(i,topicRelID) {
    $.each(relList,function(i,rel) {
      if (rel.id == topicRelID) {
         
        var topicTag = tagList.find(function(tag) {
            return tag.id === rel.tag;
        });
        var topicPredTitle = predList.find(function(pred) {
            return pred.id === rel.predicate;
        }).predicateString;
        $('#topicRels').append(`<li> <span class="label label-default">${topicPredTitle}</span> 
          <a href='./tag.html?tag=${topicTag.id}&title=${topicTag.tagString}' > <span class="label label-primary">${topicTag.tagString}</span> </a> </li> `);
      }
    });
  });


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
              
              <p class="media-comment">
                ${entry.content}
              </p>
              <a class="btn btn-info btn-circle text-uppercase" data-toggle="collapse" href="#div-reply-form-entry${entry.id}"><span class="glyphicon glyphicon-share-alt"></span> Reply</a>
              <a class="btn btn-warning btn-circle text-uppercase" data-toggle="collapse" href="#div-reply-entry${entry.id}"><span class="glyphicon glyphicon-comment"></span> ${entry.comments.length} comments</a>
              <ul class="media-date reviews list-inline" display="block">
                 <li class="media-heading text-capitalize reviews">${username}  | </li> 
                 <li><em> ${jQuery.timeago(entry.updated_at)}</em></li> 
              </ul>
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
                  <p class="media-comment">
                    ${comment.content}
                  </p>
                  <ul class="media-date reviews list-inline" display="block">
                     <li class="media-heading text-capitalize reviews">${comment_username}  | </li> 
                     <li><em> ${jQuery.timeago(comment.updated_at)}</em></li> 
                  </ul>
                
                  
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

          
        }
        else{
          $('#commentForm-entry'+entry.id).find('#alert-add-comment').append(`<div class="alert alert-danger" role="alert">
                     <strong>Login to add comment!</strong>.
                 </div>`);
        }
        return false;
    });
  });
});

var popularPromise = $.getJSON('http://localhost:8000/topics/popular?format=json');
$.when(popularPromise).then(function(topics) {
  $.each(topics['results'],function(i, topic) {
    $('#popTopics').append(`<li class="list-group-item"><a href="./topic.html?id=${topic.id}&title=${topic.title}">${topic.title}</a></li>`);
  });
});

$('#entryForm').submit(function(event) {

  if (userAuth) {
    var entryObj = {};
    var content = $(this).find("textarea[name='content']").val() ;
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

    
  }
  else{
    $('#alert-add-entry').append(`<div class="alert alert-danger" role="alert">
                      <strong>Login to add entry!</strong> 
                  </div>`);
  }
  return false;
});

$('#modal-addTopic-form').submit(function(event) {

  if (userAuth) {
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

    
  }
  else{
    $('#alert-add-topic').append(`<div class="alert alert-danger" role="alert">
                      <strong>Login to create a topic...</strong> 
                  </div>`);
  }
  return false;
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

    
  }
  else{
    $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                  <strong>Login to add relation!</strong>
              </div>`);
  }

  return false;
});

$('#modal-addNewPredicate-form').submit(function(event) {
	if (userAuth) {
	 var relObj = {};
	 var predicateString=document.getElementById("NP").value;
	 console.log(predicateString);
	 relObj['predicateString'] = predicateString ;
	 
	 var relJSON = JSON.stringify(relObj);
	 console.log(relJSON);

	 $.ajax({
      type: "POST",
      url: "http://localhost:8000/predicates/create/",
      data: relJSON,
      dataType: "json",
      contentType: "application/json",
      error: function(xhr, textStatus, error) {
        $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                      <strong>Oopss..</strong> Predicate could not added due to ${error}.
                  </div>`);
          console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
      },
      success: function(data) {
        $('#alert-add-tag').append(`<div class="alert alert-success" role="alert">
                      <strong>Predicate successfully added..</strong>
                  </div>`);
        console.log(data);
        setTimeout(function() { location.reload(true)}, 1500);
      }
    });

    
  }
  else{
    $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                  <strong>Login to add predicate!</strong>
              </div>`);
  }

  return false;
});


$('#modal-addNewTag-form').submit(function(event) {
	if (userAuth) {
	 var relObj = {};
	 var tagString=document.getElementById("NT").value;
	 relObj['tagString'] = tagString;
	 var relJSON = JSON.stringify(relObj);
console.log(relJSON);
	 $.ajax({
      type: "POST",
      url: "http://localhost:8000/tags/create/",
      data: relJSON,
      dataType: "json",
      contentType: "application/json",
      error: function(xhr, textStatus, error) {
        $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                      <strong>Oopss..</strong> Tag could not added due to ${error}.
                  </div>`);
          console.log(xhr.statusText);
            console.log(textStatus);
            console.log(error);
      },
      success: function(data) {
        $('#alert-add-tag').append(`<div class="alert alert-success" role="alert">
                      <strong>Tag successfully added..</strong>
                  </div>`);
        console.log(data);
        setTimeout(function() { location.reload(true)}, 1500);
      }
    });

    
  }
  else{
    $('#alert-add-tag').append(`<div class="alert alert-danger" role="alert">
                  <strong>Login to add tag!</strong>
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

