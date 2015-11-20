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
<link href="${base}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/style-metro.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/light.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${base}/static/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${base}/static/css/metroStyle/metroStyle.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/page/Pager.css"/>	
<link href="${base}/static/fancybox/jquery.fancybox.css" rel="stylesheet">
</head>
<body>
<form method="post" id="inputForm" style="min-height:360px;max-height:450px;">
	<input type="hidden" name="id" id="id" value="${power.id}" />
	<input type="hidden" name="menu.id" id="menuId" value="${power.menu.id}" />
	<div class="tabContent">
		<table class="input">
			<tr>
				<th width="60">
					<span class="requiredField">*</span>编码:
				</th>
				<td colspan="2">
					<input type="text" name="code" id="code" class="text" maxlength="200" value="${power.code}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>名称:
				</th>
				<td colspan="2">
					<input type="text" name="name" class="text" maxlength="9" value="${power.name}"/>
				</td>
			</tr>
			<tr>
				   <th>菜单</th>
				   <td>
			              <div class="controls text-cont-box">
			              	<div class="text-cont" onMouseLeave="javascript:$(this).hide();">
			              		<ul id="auth-tree" class="ztree"></ul>
			              	</div>
			                <input type="text" id="menuName" class="m-wrap text-tree-inp" readonly onclick="focusMenu()" value="${power.menu.name}" placeholder="请选择菜单">
			              </div>	
				   </td>
			</tr>
		<tr>
			<th>描述</th>
			<td>
				<textarea id="editor" name="description" class="editor" style="width:60%;">${power.description}</textarea>
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
<script src="${base}/static/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${base}/static/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${base}/static/js/jquery.uniform.min.js" type="text/javascript" ></script> 
<script src="${base}/static/js/jquery.ztree.core-3.5.min.js"></script> 
<script src="${base}/static/js/jquery.ztree.excheck-3.5.min.js"></script> 
<script src="${base}/static/js/jquery.ztree.exedit-3.5.min.js"></script> 
<script src="${base}/static/js/app.js" type="text/javascript"></script> 
<script type="text/javascript" src="${base}/static/js/common.js"></script>
<script type="text/javascript" src="${base}/static/js/menu/menu.js"></script>
<script>
	$(function(){     
	   	var $inputForm = $("#inputForm");
	   	// 表单验证
		$inputForm.validate({
			rules: {
				code: {
					required: true,
					remote: {
						url: "checkCode.jhtml",
						cache: false,
						data:{
						   id:function(){
						      return $("#id").val();
						   },
						   code:function(){
						      return $("#code").val();
						   }
						}
					}
				},
				name: {
					required: true
				},
				menuId:{
				    required: true
				}
			},
			messages: {
				code: {
					required: "按钮编码不能为空",
					remote: "按钮编码已经存在"
				},
				name: {
					required: "按钮名称不能为空"
				},
				menuId:{
				    required: "请选择菜单"
				}
			},
			submitHandler: function(form) {
				$.ajax({
					url: "${base}/power/update.jhtml",
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
	
	var zTreeObj;
		var setting = {
			view: {
				dblClickExpand: false
			},data : {
				key : {
				    name : 'name',
				    url:''
				},
				simpleData : {
					enable : true,
					idKey : 'id',
					pIdKey : 'parentId',
					rootPId : '0'
				}
			},callback: {
				onClick: onClick
			}
        };
        
         //触发菜单操作
        function onClick(event, treeId, treeNode){
        	$("#menuId").val(treeNode.id);
        	$("#menuName").val(treeNode.name);
        }
        
        function focusMenu(){
           $(".text-cont").show();
        }
        
		jQuery(document).ready(function() {
		    zTreeObj = $.fn.zTree.init($("#auth-tree"), setting, ${page});
		});
		
</script>
</body>
</html>