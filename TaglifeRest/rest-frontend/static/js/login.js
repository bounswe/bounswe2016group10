/**
 * Created by evinpinar on 20/12/16.
 */


$('#login_form').submit(function(event) {

  var userObj = {};
  var username = $(this).find("input[name='user_name']").val() ;
  var password = $(this).find("input[name='password']").val() ;
  userObj['username'] = username ;
  userObj['password'] = password ;

  var userJSON = JSON.stringify(userObj);

  $.ajax({
    type: "POST",
    url: "http://localhost:8000/api-auth/login/",
    data: userJSON,
    dataType: "json",
    contentType: "application/json",
    error: function(xhr, textStatus, error) {
        console.log(xhr.statusText);
          console.log(textStatus);
          console.log(error);
    },
    success: function(data) {
      alert("USER SUCCESFULLY LOGGED IN");
      location.href = './index.html'
      console.log(data);
    }
  });

  return false;
});

