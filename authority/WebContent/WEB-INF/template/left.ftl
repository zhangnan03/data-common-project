[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<link href="${base}/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/style-metro.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/light.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="${base}/static/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>
<link href="${base}/static/fancybox/jquery.fancybox.css" rel="stylesheet">
<div class="header navbar navbar-inverse navbar-fixed-top"> 
  <div class="navbar-inner">
    <div class="container-fluid"> 
      <a class="brand" href="${base}/common/main.jhtml"> <i class="icon-random header-tit-ico"></i> 权限管理系统 </a> 
      <ul class="nav pull-right">
        <li class="dropdown user"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 11px 8px;"> <img alt="" src="${base}/static/image/avatar.png" width="20" height="20" /> <span class="username">[@shiro.principal /]</span> <i class="icon-angle-down"></i> </a>
          <ul class="dropdown-menu">
          <!--  <li><a href="extra_profile.html"><i class="icon-user"></i> 个人信息 </a></li>-->
            <li><a onclick="javascript:$('#edit-psw').modal('show');"><i class="icon-key"></i> 修改密码 </a></li>
            <li><a href="${base}/logout"><i class="icon-off"></i> 退出登录</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</div>

<div class="page-container row-fluid"> 
<form method="post" id="pwdForm" name="pwdForm">
<div id="edit-psw" class="modal hide">
      <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button"></button>
        <h4>修改密码</h4>
      </div>
      
      <div class="modal-body">
        <div class="row-fluid">
          <div class="span12">
            <div class="control-group">
              <label class="control-label">原密码</label>
              <div class="controls">
                <input type="password" class="m-wrap span12" placeholder="原密码" name="oldPwd" id="oldPwd">
                <span class="help-block" id="oldPwdMsg"></span>
              </div>
            </div>
          </div>
          <div class="span12">
            <div class="control-group">
              <label class="control-label">新密码</label>
              <div class="controls">
                <input type="password" class="m-wrap span12" placeholder="新密码" name="newPwd" id="newPwd">
                <span class="help-block"></span>
              </div>
            </div>
          </div>
          <div class="span12">
            <div class="control-group">
              <label class="control-label">确认新密码</label>
              <div class="controls">
                <input type="password" class="m-wrap span12" placeholder="确认新密码" id="surNewPwd" name="surNewPwd">
                <span class="help-block"></span>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="modal-footer">
      	<button data-dismiss="modal" class="btn" type="button">取消</button>
      	<button class="btn blue" type="submit"><i class="icon-ok"></i>&nbsp;&nbsp;确定</button>
      </div>
    </div>
 </form>    
  <div class="page-sidebar nav-collapse collapse"> 
    <ul class="page-sidebar-menu">
      <li> 
        <div class="sidebar-toggler hidden-phone"></div>
      </li>
      <li class="start open"> <a href="${base}"> <i class="icon-home"></i> <span class="title">主页</span> <span class="selected"></span> </a> </li>
		[@menu_list] 
			[#list menus as menu]
      			<li class="open"> 
					<a href="javascript:;"> 
					  	<i class="icon-cogs"></i> 
					  	<span class="title">${menu.name}</span> 
					  	<span class="arrow "></span> 
					</a>
			        <ul class="sub-menu">
			        	[#if menu.childrenTmp?exists]
							[#list menu.childrenTmp as menu2]
								<li > <a href="${base}/${menu2.url}"> ${menu2.name}</a> </li>
							[/#list]
						[/#if]
			        </ul>
			      </li>
      		[/#list]
		[/@menu_list]
    </ul>
  </div>
<script src="${base}/static/js/jquery.min.js" type="text/javascript"></script>   
<script type="text/javascript" src="${base}/static/js/jquery.validate.js"></script>
<script src="${base}/static/js/jquery.validate.min.js" type="text/javascript"></script> 
<script>
	   	// 表单验证
		$(function(){
		
		$("#pwdForm").validate({
			rules: {
				oldPwd: {
					required: true
				},
				newPwd: {
					required: true,
					rangelength:[6,20]
				},
				surNewPwd:{
				   required: true,
				   equalTo:"#newPwd"
				}
			},
			messages: {
				oldPwd: {  
				    required: "旧密码不能为空"
				},
				newPwd: {  
				    required: "密码必须在6-20个字符之间"
				},
				surNewPwd: {
				     required: "不能为空",
				     equalTo: "两次输入密码不一致"
				}
			},
			submitHandler: function(form) {
				$.ajax({
					url: "${base}/user/updatePassword.jhtml",
					type: "POST",
					data: $("#pwdForm").serialize(),
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.code == "0000") {
							$('#edit-psw').modal('hide');
							$("#oldPwd").val("");
							$("#newPwd").val("");
							$("#surNewPwd").val("");
							window.location.href="${base}/logout";
						}else if(data.code="1003"){
							$("#oldPwdMsg").empty().html("旧密码输入错误");
						}else {
							alert(data.message);
						}
					}
				});
			}
		});
	});
</script>
 