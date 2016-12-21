var userAuth = false;
var userID = $.session.get('userID');

if (userID) {
  userAuth = true;
}

if (userAuth) {
  $('#navUserAuth').append(`<li><a href="/profile">
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

var user = getParameterByName('user');
var userPromise = $.getJSON('http://localhost:8000/users/'+user+'?format=json');
var entryPromise = $.getJSON('http://localhost:8000/entries?format=json');
var topicPromise = $.getJSON('http://localhost:8000/topics?format=json');

$.when(userPromise, entryPromise, topicPromise).then(function(user, entries, topics) {
	var user = user[0];
	var entryList = entries[0]['results'];
	var topicList = topics[0]['results'];
	
	$('#usernamefield').text(user.username);
	$('#userFirstName').text(user.first_name);
	$('#userLastName').text(user.last_name);
	$('#useremail').text(user.email);
	$('#userjoined').text(jQuery.timeago(user.date_joined));

	//<li class="list-group-item">{{topic.title}}</li>
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