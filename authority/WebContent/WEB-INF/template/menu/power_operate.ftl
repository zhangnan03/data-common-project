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

<body class="page-header-fixed">
<form method="post" id="inputForm">
  <input type="hidden" name="id" id="id" value="${parentIds}" />
  <table class="table table-striped table-hover table-bordered check-table" id="sample_editable_1">
     <thead>
        <tr>
           <th class="check"><input type="checkbox" id="selectAll" value="" name="checkPower" class="group-checkable" data-set=".check-table .checkboxes" /></th>
           <th>名称</th>
           <th>编码</th>
        </tr>
     </thead>
     <tbody>
        <#list pageInfo as role>
           <tr class="">
             <td>
             <#if role.id=='49bfba44be9d2dd0'>
                 <input type="checkbox" id="${role.id}" name="checkPower" checked class="checkboxes" disabled value="${role.id}" />
             <#else>
                 <#if role.isChecked==1><input type="checkbox" id="${role.id}" name="checkPower" class="checkboxes" checked value="${role.id}" />
	             <#else>
	               <input type="checkbox" id="${role.id}" name="checkPower" class="checkboxes" value="${role.id}" />
	             </#if>
             </#if>
             </td>
             <td>${role.name}</td>
             <td>${role.code}</td>
             </tr>
             </#list>
     </tbody>
   </table>
   <table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="btnSubmit" class="button" value="提交" />
					<input type="button" class="button" id="cancelBtn" onclick="closePage()" value="取消" />
				</td>
			</tr>
	</table>
</form>
<script src="${base}/static/js/jquery.min.js" type="text/javascript"></script>   
<script src="${base}/static/page/jquery.pager.js" type="text/javascript"></script>
<script src="${base}/static/page/page_util.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/static/fancybox/jquery.fancybox.pack.js"></script>
<script type="text/javascript">
	
	$(function() {
    	$('#selectAll').click(function(){
			$('[name=checkPower]:checkbox').attr("checked", this.checked);
		}) 
			     
	    //得到选中的值，ajax操作使用  
	    $("#btnSubmit").click(function() {  
	        var ids = "";
	        var id  = $("#id").val();
	        $(":checkbox:checked").each(function() {
	           ids += ","+$(this).val();
	        });
	        
	        $.ajax({
				url: "${base}/menu/authorizeMenuRoleOperate.jhtml",
				type: "POST",
				data: "ids="+ids+"&id="+id,
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.code == "0000") {
						closePage(); 
					} else {
						alert(data.message);
					}
				}
			});
	    });
	    
	    var numbers = ${pageInfo?size};
		var checks = $("input[name='checkPower']:checked").length;
		if(numbers==checks){
			 $('#selectAll').attr("checked",true);
		}
	});
	
	function closePage(){
	    parent.$.fancybox.close(); 
	}
</script> 
</body>
</html>