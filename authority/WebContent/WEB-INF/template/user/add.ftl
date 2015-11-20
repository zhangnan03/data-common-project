<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<title>权限管理系统</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<link href="${base}/static/css/common.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form method="post" action="${base}/user/add.jhtml" id="inputForm" style="max-height:450px;">
		<div class="tabContent">
			<table class="input">
				<tr>
					<th>
						<span class="requiredField">*</span>用户名:
					</th>
					<td colspan="2">
						<input type="text" name="username" class="text" maxlength="200"maxlength="200"/>
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>真实姓名:
					</th>
					<td colspan="2">
						<input type="text" name="realName" class="text" />
					</td>
				</tr>
				<tr>
					<th>
						邮箱:
					</th>
					<td colspan="2">
						<input type="text" name="email" class="text" />
					</td>
				</tr>
			</table>
		</div>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="提交" />
					<input type="button" class="button" id="cancelBtn" value="取消" />
				</td>
			</tr>
		</table>
</form>
<script src="${base}/static/js/jquery.min.js" type="text/javascript"></script>   
<script type="text/javascript" src="${base}/static/js/jquery.validate.js"></script>
<script src="${base}/static/js/jquery.validate.min.js" type="text/javascript"></script> 
<script type="text/javascript" src="${base}/static/js/common.js"></script>
<script>
	$(function(){     
	   	var $inputForm = $("#inputForm");
	   	// 表单验证
		$inputForm.validate({
			rules: {
				username: {
					required: true,
					rangelength:[3,20],
					remote: {
						url: "${base}/user/checkUsername.jhtml",
						type: "post",
						cache: false
					}
				},
				realName: {
					required: true,
					rangelength:[1,10]
				},
				email:{
				    email:true
				}
			},
			messages: {
				username: {  
				    required: "请输入用户名",  
				    rangelength : "用户名必须在3-20个字符之间",
				    remote: "用户名已经存在，请重新输入用户名"  
				},
				realName: {  
				    required: "请输入用户名",
				    rangelength : "用户名必须在1-10个字符之间",
				},
				email: {  
				    email: "邮箱格式不正确"
				}
			},
			submitHandler: function(form) {
				$.ajax({
					url: "${base}/user/add.jhtml",
					type: "POST",
					data: $inputForm.serialize(),
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.code == "0000") {
							parent.$.fancybox.close(); 
						} else {
							alert(data.message);
						}
					}
				});
			}
		});
		$("#cancelBtn").click(function(){
			parent.$.fancybox.close(); 
		});
	});
</script> 
</body>
</html>