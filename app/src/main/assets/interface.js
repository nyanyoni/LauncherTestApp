window.double = function(number) {
  $('.text').text(number);
};

var Interface = function() {};

Interface.prototype.double = function(number) {
  alert("double:" + number);
};