/**
 * Created by evinpinar on 20/12/16.
 */


$('#login_form').submit(function(event) {

  var userObj = {};
  var username = $(this).find("input[name='username']").val() ;
  var password = $(this).find("input[name='password']").val() ;
  userObj['username'] = username ;
  userObj['password'] = password ;

  var userJSON = JSON.stringify(userObj);
  console.log(userJSON);
  $.ajax({
    type: "POST",
    url: "http://localhost:8000/login/",
    data: userJSON,
    // dataType: "json",
    contentType: "application/json",
    error: function(xhr, textStatus, error) {
        console.log(xhr.statusText);
          console.log(textStatus);
          console.log(error);
    },
    success: function(data) {
      if (data.length > 0) {
        parent._alert = new parent.Function("alert(arguments[0]);");

        alert("YOU HAVE SUCCESSFULLY LOGGED IN");
        $.session.set('userID', data[0].id);
        setTimeout(function() { location.href = './index.html';}, 500);
      }
      else{
        alert("USER NOT FOUND - PLEASE REGISTER");
      }
    }
  });

  return false;
});

