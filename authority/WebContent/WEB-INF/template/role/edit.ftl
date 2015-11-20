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
<form action="update.jhtml" method="post" id="inputForm">
	<input type="hidden" name="id" id="id" value="${role.id}" />
	<div class="tabContent">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>编码:
				</th>
				<td colspan="2">
					<input type="text" name="code" id="code" class="text" maxlength="200" value="${role.code}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>名称:
				</th>
				<td colspan="2">
					<input type="text" name="name" class="text" maxlength="9" value="${role.name}"/>
				</td>
			</tr>
		</table>
	</div>
	<table class="input">
		<tr>
			<th>描述</th>
			<td>
				<textarea id="editor" name="description" class="editor" style="width: 60%;">${role.description}</textarea>
			</td>
		</tr>
		<tr>
			<th>
				&nbsp;
			</th>
			<td>
				<input type="submit" class="button" value="提交" />
				<input type="button" class="button" value="取消" onclick="javascript:parent.$.fancybox.close();" />
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
				code: {
					required: true,
					remote: {
						url: "${base}/role/checkCode.jhtml",
						type: "post",
						data:{code:function(){
								return $("#code").val();
							   },
							   id:function(){
							     return $("#id").val();
							   }
						}
					}
				},
				name: {
					required: true
				}
			},
			messages: {
				code: {
					required: "角色编码不能为空",
					remote: "角色编码已经存在"
				},
				name: {
					required: "角色名称不能为空"
				}
			},
			submitHandler: function(form) {
				$.ajax({
					url: "${base}/role/update.jhtml",
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
	});
</script> 
</body>
</html>