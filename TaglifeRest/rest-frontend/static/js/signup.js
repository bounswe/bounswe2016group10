
$(function () {
    $('.button-checkbox').each(function () {

        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });
        
        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        // Initialization
        function init() {

            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }
        init();
    });
});
/*
{
    "username": "",
    "first_name": "",
    "last_name": "",
    "email": "",
    "password": ""
}
 */
$('#register_form').submit(function(event) {

  var userObj = {};
  var username = $(this).find("input[name='user_name']").val() ;
  var firstname = $(this).find("input[name='first_name']").val() ;
  var lastname = $(this).find("input[name='last_name']").val() ;
  var email = $(this).find("input[name='email']").val() ;
  var password = $(this).find("input[name='password']").val() ;
  userObj['username'] = username ;
  userObj['first_name'] = firstname ;
  userObj['last_name'] = lastname ;
  userObj['email'] = email ;
  userObj['password'] = password ;

  var userJSON = JSON.stringify(userObj);

  $.ajax({
    type: "POST",
    url: "http://localhost:8000/users/create/",
    data: userJSON,
    dataType: "json",
    contentType: "application/json",
    error: function(xhr, textStatus, error) {
        console.log(xhr.statusText);
          console.log(textStatus);
          console.log(error);
    },
    success: function(data) {
      alert("USER SUCCESFULLY ADDED");
      location.href="./login.html";
      console.log(data);
    }
  });

  return false;
});

