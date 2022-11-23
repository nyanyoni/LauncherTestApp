$(function() {
  $('.btn').on('click', function() {
    var number = $('.input_area').val()
    var interface = new Interface();
    interface.double(number);
  });
});