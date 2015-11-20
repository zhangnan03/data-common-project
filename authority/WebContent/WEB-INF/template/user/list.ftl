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
<!-- 左侧菜单及头部信息 -->
  [#include "left.ftl"/]
  
  <div class="page-content">     
    <div class="container-fluid"> 
      <form id="queryForm" action="${base}/user/list.jhtml" method="post">
      <div class="row-fluid">
        <div class="span12"> 
          <div class="color-panel hidden-phone" style="margin-top: 10px;">
          <h3 class="page-title">员工管理 <small> 列表</small> </h3>
          <hr />
          
        </div>
      </div>
      <div id="dashboard">
        <div class="row-fluid form-horizontal"  style="margin-bottom: 20px;">
        <div class="span3 ">
            <div class="control-group">
              <label class="control-label">用户名</label>
              <div class="controls">
                <input type="text" name="username" id="username" value="${user.username}" class="m-wrap span11">
              </div>
            </div>
          </div>
          <div class="span3 ">
            <div class="control-group">
              <label class="control-label">真实姓名</label>
              <div class="controls">
                <input type="text" name="realName" id="realName" value="${user.realName}" class="m-wrap span11">
              </div>
            </div>
          </div>
          <div class="span4">
          	<button type="submit" class="btn blue"> <i class="icon-search"></i>&nbsp;&nbsp;查询 </button>
          	<button type="button" class="btn" onclick="clearAll()"> 重置 </button>
          </div>
          
        </div>
        </form>
        <div class="row-fluid">
          <div class="span12"> 
            <div class="portlet box blue">
              [@shiro.hasPermission name = "power:user:add"]
               <div class="portlet-title">
                <div class="actions">
                	<a href="${base}/user/perAdd.jhtml" name="fancyboxClose" id="userAdd" class="btn black"><i class="icon-plus"></i>&nbsp;&nbsp;添加</a>
                </div>
              </div>
              [/@shiro.hasPermission]
              <div class="portlet-body">
                <table class="table table-striped table-hover table-bordered check-table" id="sample_editable_1">
                  <thead>
                    <tr>
                      <th class="check" width="20"><input type="checkbox" class="group-checkable" data-set=".check-table .checkboxes" /></th>
                      <th>用户名</th>
                      <th>真实姓名</th>
                      <th>邮箱</th>
                      <th>状态</th>
                      <th>创建时间</th>
                      <th width="460">操作</th>
                    </tr>
                  </thead>
                  <tbody>
                  [#list page.content as user]
                     <tr class="">
                      <td><input type="checkbox" class="checkboxes" value="1" /></td>
                      <td>${user.username}</td>
                      <td>${user.realName}</td>
                      <td>${user.email}</td>
                      <td>[#if user.status == "0"]禁用[#else]启用[/#if]</td>
                      <td>${user.createTime}</td>
                      <td>
                      [@shiro.hasPermission name = "power:user:role:authorize"]
                          <a class="btn mini purple-stripe" href="${base}/user/authorizeRoleList.jhtml?id=${user.id}" name="fancyboxClose" id="power"><i class="icon-tag"></i>&nbsp;&nbsp;&nbsp;角色授权</a>
                      [/@shiro.hasPermission]
                      [@shiro.hasPermission name = "power:user:button:authorize"]
                          <a class="btn mini purple-stripe" href="${base}/user/authorizePowerList.jhtml?id=${user.id}" name="fancyboxClose" id="power"><i class="icon-tag"></i>&nbsp;&nbsp;&nbsp;按钮授权</a>
                      [/@shiro.hasPermission]
                      [@shiro.hasPermission name = "power:user:datafield:authorize"]
                          <a class="btn mini purple-stripe" href="${base}/user/authorizeDataFieldList.jhtml?id=${user.id}" name="fancyboxClose" id="power"><i class="icon-tag"></i>&nbsp;&nbsp;&nbsp;字段授权</a> 
                      [/@shiro.hasPermission]
                      [@shiro.hasPermission name = "power:user:delete"]
                      [#if user.id!='45e50b45c28e856c']
                          <a class="btn mini red-stripe" onclick="delUser('${user.id}')" data-toggle="modal"><i class="icon-trash"></i>&nbsp;&nbsp;&nbsp;删除</a>
                      [/#if]
                      [/@shiro.hasPermission]
                      [@shiro.hasPermission name = "power:user:edit"]
                          <a class="btn mini green-stripe" href="${base}/user/edit.jhtml?id=${user.id}" name="fancyboxClose" id="edit"><i class="icon-pencil"></i>&nbsp;&nbsp;&nbsp;编辑</a> 
                      [/@shiro.hasPermission]
                      [@shiro.hasPermission name = "power:user:enable"]
                        [#if user.id!='45e50b45c28e856c']
						  [#if user.status == 0]
						  <a class="btn mini green-stripe" onclick="changeStatus(1,'${user.id}')" id="userStatus${user.id}">启用</a>
						  [#else]
						  <a class="btn mini green-stripe" id="userStatus${user.id}" onclick="changeStatus(0,'${user.id}')">禁用</a>
						  [/#if]
						[/#if]
                      [/@shiro.hasPermission]
                      </td>
                    </tr>
                  [/#list]
                  </tbody>
                </table>
	              <div id="pager" style="float: right;"></div><div class="clearfix"><div>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
    </div>
  </div>
</div>

<script src="${base}/static/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${base}/static/js/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${base}/static/js/app.js" type="text/javascript"></script>
<script src="${base}/static/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="${base}/static/js/table-editable.js" type="text/javascript"></script>
  
<script>
		jQuery(document).ready(function() {
		   App.init();
		   TableEditable.init();
		});
</script>

<!--分页控件-->
<script src="${base}/static/page/jquery.pager.js" type="text/javascript"></script>
<script src="${base}/static/page/page_util.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/static/fancybox/jquery.fancybox.pack.js"></script>
<script type="text/javascript">
	$(function(){    
	   init(${page.number+1});
	   
   	    //弹出层关闭时，父页面刷新,添加
	    $("a[name='fancyboxClose']").fancybox({
	        'autoScale': false,
	        'autoSize'   : true,
	        'scrolling' : 'no',
	        'transitionIn': 'none',
	        'transitionOut': 'none',
	        'type': 'iframe',
	        'afterClose':function(){
	            window.location.reload();
	        }
	    });
	});
	
	//清空内容
    function clearAll(){
       $("#realName").val("");
       $("#username").val("");
    }
    
	function init(pagenumber){
		$("#pager").pager({pagenumber:pagenumber, pagecount:${page.totalPages},totalcount:${page.totalElements}, buttonClickCallback: ''});
	}
	//删除
	function delUser(id){
		if(confirm("确认要删除？")) {
           $.ajax({
	    		url:'${base}/user/enable.jhtml',
	    		type:'post',
	    		data:'id='+id,
	    		dataType: "json",
	    		success: function(data) {
					if (data.code == "0000") {
					    window.location.reload();
					} else {
						alert(data.message);
					}
				}
	    	});
        }
	}
	
	function changeStatus(status,id){
	     $.ajax({
	    		url:'${base}/user/updateStatus.jhtml',
	    		type:'post',
	    		data:'id='+id+"&status="+status,
	    		dataType: "json",
	    		success: function(data) {
	    		var str = "";
					if (data.code == "0000") {
					   window.location.reload();
					} else {
						alert(data.message);
					}
				}
	    	});
	}
</script> 
</body>
</html>