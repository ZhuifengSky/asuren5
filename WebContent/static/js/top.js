$(document).ready(function() {
	  $.ajax({
	    	url:"/api/user/logincheck",
	        type:"POST",
	        dataType:'json',	       
	        data: null, 
	        success:function(result){
	          if (result.code=='1000' || result.code=='1009') {
				  $('#loginReg').hide();
				  $("#welcome").show();
				  var userNickName=result.data.userName;
				  if(userNickName.length>10)userNickName =userNickName.substring(0,10) + '...';// 控制显示8个字符+....；
				  $("#welcome").html('<span>欢迎您！&nbsp;&nbsp;<font color="red">'+userNickName+'</font>&nbsp;&nbsp;&nbsp;<a href="#" onclick="logout()">退出</a></span> ');
	          }      	
	        },
	        error:function(e){
	          alert("发生错误！！+top1");
	        } 
      });
	  
	  $.ajax({
	    	url:"/api/sys/getSiteMenus",
	        type:"GET",
	        dataType:'json',	       
	        data: null, 
	        success:function(result){
	        	if (result!= null) {
	        		if (result.code!='1000') {
	  	        	  // alert(result.info);
	  			    }else{
	  			    	var dataObj = eval(result.data);// 转换为json对象
						var navMenu = "";
						// 遍历json数组
						$.each(dataObj, function(i, item) {
                            navMenu+= "<li><a href="+item.targetUrl+">"+item.title+"</a>";
                            if(item.secondMenus!=null){
                                navMenu+= "<ul>";
                                $.each(item.secondMenus, function(i, item) {
                                    navMenu+= "<li><a href="+item.targetUrl+">"+item.title+"</a>";
                                    if(item.thirdMenus!=null){
                                        navMenu+= "<ul>";
                                        $.each(item.thirdMenus, function(i, item) {
                                            navMenu+= "<li><a href="+item.targetUrl+">"+item.title+"</a></li>";
                                        });
                                        navMenu+= "</ul>";
                                    }
                                    navMenu+= "</li>";
                                });
                                navMenu+= "</ul>";
                            }
                            navMenu+= "</li>";
						});
                        $("#nav").append(navMenu);
                        $("#nav li").hover(function(){
                            $(this).addClass("on");
                            $(this).parent("ul").siblings("a").addClass("choice");
                        }, function(){
                            $(this).parent("ul").siblings("a").removeClass("choice");
                            $(this).removeClass("on");
                        });
	  			    }  	        		
				} else {
					alert("请求失败!")
				}
	              	
	        },
	        error:function(e){
	          alert("发生错误！！top2");
	        } 
    });
  });

function logout() {
    $.ajax({
    	url:"/api/user/logout",
        type:"POST",
        dataType:'json',       
        data: null, 
        success:function(result){
          if (result.code!='1111') {
        	  alert(result.info);
		  }else{
			  $("#welcome").html();
			  $("#welcome").hide();
			  $('#loginReg').show();
			  parent.location.reload();
		  }      	
        },
        error:function(e){
          alert(e);
          alert("错误！！");
        }
    });
  }
function regLogin(dd){
	if(dd==1){
		parent.location.href='/static/html/login.html';
	}
	if(dd==2){
		parent.location.href='/static/html/register.html';
	}
}

function getContextPath(){   
    var pathName = document.location.pathname;   
    var index = pathName.substr(1).indexOf("/");   
    var result = pathName.substr(0,index+1);   
    return result;   
} 
