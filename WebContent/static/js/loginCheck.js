 $(document).ready(function() { 
		  $.ajax({
		    	url:"/api/user/logincheck",
		        type:"POST",
		        dataType:'json',
		        data: null, 
		        success:function(result){
		          if (result.code!='1000') {
					  if (result.code=='1009') {
						  setTimeout("javascript:location.href='/static/html/activate.html'", 1000);
					  }
				  }else{
					  setTimeout("javascript:location.href='/static/html/index.html'", 1000);
				  }      	
		        },
		        error:function(e){
		          alert("1111错误！！");
		        } 
	      });
	 });