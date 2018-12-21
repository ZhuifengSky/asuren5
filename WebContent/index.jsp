<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<script src="./static/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	function dd(){
		var map = "loginName=111&password=111"; 
	    $.ajax({
	    	url:"/asuren5/api/user/login",
	        type:"POST",
	        dataType:'json',
	        data: map, 
	        success:function(result){
	          alert(result.info);
	          console.log("over..");
	        },
	        error:function(e){
	          alert(e);
	          alert("错误！！");
	        }
	    });
	}
</script>
</head>
<body>
  Hello World!
  <a href="#" onclick="dd()">test</a>
</body>
</html>