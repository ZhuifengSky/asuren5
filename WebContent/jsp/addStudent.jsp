<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login1-User.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.7/kindeditor-min.js"></script>
<script charset="utf-8" src="js/kindeditor-4.1.7/lang/zh_CN.js"></script>
<script charset="utf-8" src="js/jquery-ui-1.11.4/jquery.blockUI.js"></script>
<script defer="defer" src="js/fomvalidator4.0/datepicker/WdatePicker.js"type="text/javascript"></script>

<script type="text/javascript">
        var editor;
        KindEditor.ready(function(K) {
        	editor = K.create('#editor_id', {
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			allowImageRemote: false,
			items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
			uploadJson:'user/upload4Editor.do'
        	});
	});
	function regester(ope) {
		if (ope == 'add') {
			$("#form").attr("action", "user/addUser.do");
			$("#form").submit();
		} else {
			$("#form").attr("action", "user/updateUser.do");
			$("#form").submit();
		}
	}
	
	 function onFileUploadimg(bt, columnname) {
		var url = "user/uploadByHes.do";
		$.ajaxFileUpload({
			url : url,
			type: 'post',
			secureuri : false,
			fileElementId : bt.id,
			dataType : 'json',
			data : {},
			success : function(result, status) {

				if (result.resultCode == '1001') {
					alert("请选择图片!");
					return false;
				} else if(result.resultCode == '1002'){
					alert("图片格式不正确，请确认!");
					return false;
				}
				else{
					alert("上传成功");
					uploadCallback(result.message, columnname);
				}
			},
			error : function(result, status, e) {
				alert(e);
			}
		});
		return false;

	}

	function uploadCallback(url, id) {
		url = url.replace(/\//g,'');
		$("#"+id).val(url);
		$("#imageUrl_show").hide();
		//url='http://picm.photophoto.cn/005/008/007/0080071498.jpg';
		$('#' + id + '_file').after($('<a id="'+ id+'_show" href="javascript:;" onclick="javascript:showImage(\''
								+ url + '\')">预览图片</a>'));
	}
	
	
	function showImage(url) {
		$.blockUI({
			css : {
				top : '35%',
				left : '35%',
				cursor : null
			},
			message : ' <img  src="'+url+'"/>',
		});
		$('.blockOverlay').attr('title', 'Click to unblock').click($.unblockUI);
	}
	function hideBlock() {
		jQuery.unblockUI();
	}
	
</script>
</head>

<body>
	<c:if test="${s.id ne null}">
		<h2>学生编辑</h2>
	</c:if>
	<c:if test="${s.id eq null}">
		<h2>学生新增</h2>
	</c:if>
	<form method="post" id="form">
		<table>
			<tr>
				<td>学生姓名:</td>
				<td><input type="text" name="studentName"
					value="${s.studentName}"><c:if test="${s.id ne null}">
						<input type="hidden" value="${s.id}" name="id">
					</c:if>
					<input type="hidden" value="${s.imageUrl}" name="imageUrl" id="imageUrl">
					</td>
			</tr>
			<tr>
				<td>登录密码:</td>
				<td><input type="text" name="password"
					value="${s.password}">
					</td>
			</tr>
			<tr>
				<td>年龄:</td>
				<td>
				<input type="text" name="age" value="${s.age}">
				</td>
			</tr>
			<tr>
				<td>头像:</td>
				<td width="300"><input id="imageUrl_file" type="file"
						name="file" onchange="return onFileUploadimg(this,'imageUrl');" />
						<c:if test="${s.imageUrl ne null}">
							<a id="imageUrl_show" href="#" onclick="javascript:showImage('${s.imageUrl}')">预览图片</a>
						</c:if>
				</td>
			</tr>
			<tr>
				<td align="right">出生日期:</td>
				<td><input type="text" id="csny" name="birthDate"
					style="width:120px" readonly="readonly" value="${s.birthDate}"/>
					<img onclick="WdatePicker({el:'csny'})" src="js/fomvalidator4.0/datepicker/skin/datePicker.gif" width="16" height="22" >
				</td>
				<td><div id="csnyTip" style="width:300px"></div>
				</td>
			</tr>
			<tr>
			   <td>描述:</td>
			   <td>
			      <textarea id="editor_id" name="desc" style="width:700px;height:300px;">
							${s.desc}
				  </textarea>
			   </td>
			</tr>
			<tr>
				<c:if test="${s.id ne null}">
					<td><input type="button" value="修改" onclick="regester('edit')"></td>
				</c:if>
				<c:if test="${s.id eq null}">
					<td><input type="button" value="新增" onclick="regester('add')"></td>
				</c:if>	
		   </tr>
		</table>
	</form>
</body>
</html>
