$.ajax({
  type: 'GET',
  url: 'http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com/topics/?format=json',
  dataType: 'json',
  success: function (data) {
    // JSON.parse(data)
   console.log(data['results'][0].title); 
  }
});