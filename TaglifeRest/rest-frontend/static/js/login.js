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
    url: "http://localhost:8000/api-auth/login/?next=/users/",
    data: userJSON,
    // dataType: "json",
    contentType: "application/json",
    error: function(xhr, textStatus, error) {
        console.log(xhr.statusText);
          console.log(textStatus);
          console.log(error);
    },
    success: function(data) {
      console.log(data);
      alert("USER SUCCESFULLY LOGGED IN");
      setTimeout(function() { location.href = './index.html'}, 1500);
      
    }
  });

  return false;
});

