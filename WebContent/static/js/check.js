$(document).ready(function() {
	$.ajax({
		url : "/api/user/logincheck",
		type : "POST",
		dataType : 'json',
		async: false,
		data : null,
		success : function(result) {
			if (result.code == '1009') {
				window.location.href='/static/html/activate.html';
			}
		},
		error : function(e) {
			alert("1111错误！！");
		}
	});
});

/*function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}*/
function getContextPath() {
    return '';
}
