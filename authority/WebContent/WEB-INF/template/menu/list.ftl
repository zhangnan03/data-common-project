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
      <div class="row-fluid">
        <div class="span12"> 
          <div class="color-panel hidden-phone" style="margin-top: 10px;">
          <h3 class="page-title"> 菜单管理 <small>列表</small> </h3>
          <hr />
        </div>
      </div>
      
      <div class="row-fluid">
        <div class="span8">
         [@shiro.hasPermission name = "power:menu:add:system"]
          <button type="button" id="addBtn" onclick="addSystem()"  class="btn blue" style="margin-bottom:20px"> 添加系统 </button>
         [/@shiro.hasPermission]
        </div>
      </div>
      
      <div class="row-fluid">
        <div class="span4">
          <div class="portlet box grey">
            <div class="portlet-title">
              <div class="caption"><i class="icon-link"></i>菜单列表</div>
              <!--<div class="actions"> <a href="javascript:;" id="tree_1_collapse" class="btn purple"><i class="icon-resize-small"></i> 全部收起</a> <a href="javascript:;" id="tree_1_expand" class="btn yellow"><i class="icon-resize-full"></i> 全部展开</a> </div>-->
            </div>
            <div class="portlet-body fuelux">
              <ul id="auth-tree" class="ztree"></ul>
            </div>
          </div>
        </div>
        
        <form action="" method="post" id="inputForm">
        <input type="hidden" name="id" id="id" />
        <input type="hidden" name="parent.id" id="parentId" />
        <span id="menuSpan" style="display:none;">
        
        <div class="span6">
        <div class="row-fluid">
          <div class="span6 ">
            <div class="control-group">
              <label class="control-label">菜单中文名称</label>
              <div class="controls">
                <input type="text" name="name" id="name" class="m-wrap span12" placeholder="不能为空且不能超过10个字符">
                <span class="help-block"</span> 
               </div>
            </div>
          </div>
          
          <div class="span6 ">
            <div class="control-group">
              <label class="control-label">菜单英文名称</label>
              <div class="controls">
                <input type="text" name="code" id="code" class="m-wrap span12" placeholder="不能为空且只能输入英文">
                <span class="help-block"></span> 
               </div>
            </div>
          </div>
          
          <div class="span6 ">
            <div class="control-group">
              <label class="control-label">系统标识</label>
              <div class="controls">
                <input type="text" name="systemMark" id="systemMark" class="m-wrap span12" placeholder="不能为空且只能输入数字">
                <span class="help-block"></span> 
               </div>
            </div>
          </div>
          
          <div class="span6 ">
            <div class="control-group">
              <label class="control-label">菜单标识</label>
              <div class="controls">
                <input type="text" name="menuFlag" id="menuFlag" class="m-wrap span12" placeholder="">
                <span class="help-block"></span> 
               </div>
            </div>
          </div>
          
        </div>
        
        <div class="row-fluid">
          <div class="span12">
            <div class="control-group">
              <label class="control-label">排序</label>
              <div class="controls">
                <input type="text" name="menuSort" id="menuSort" class="m-wrap span12">
                <span class="help-block"></span> </div>
            </div>
          </div>
        </div> 
        
         <div class="row-fluid">
          <div class="span12">
            <div class="control-group">
              <label class="control-label">路径</label>
              <div class="controls">
                <input type="text" name="url" id="url" class="m-wrap span12">
                <span class="help-block"></span> </div>
            </div>
          </div>
        </div> 
        
        
        <div class="row-fluid">
          <div class="span12">
            <div class="control-group">
              <label class="control-label">描述</label>
              <div class="controls">
                <textarea class="span12 m-wrap" name="description" id="description" rows="3" style="resize: none;"></textarea>
                <span class="help-block"></span> </div>
            </div>
          </div>
        </div>
        
        <div class="row-fluid" style="margin-bottom: 30px;">
          <button type="submit" id="menuSubmit" class="btn blue"> <i class="icon-save"></i>&nbsp;&nbsp;提交 </button>
          <button type="button" class="btn" onclick="clearAll()"> 重置 </button>
        </div>
        </div>
      </div>
	  <div class="clearfix"></div>
	  </span>
	  </form>
    </div>
  </div>
</div>

<div id="rMenu">
	<ul>
	[@shiro.hasPermission name = "power:menu:add"]
		<li id="m_add" onclick="addTreeNode()">增加节点</li>
	[/@shiro.hasPermission]
	[@shiro.hasPermission name = "power:menu:edit"]
		<li id="m_edit" onclick="editTreeNode()">修改节点</li>
	[/@shiro.hasPermission]
	[@shiro.hasPermission name = "power:menu:delete"]
		<li id="m_del" onclick="removeTreeNode()">删除节点</li>
	[/@shiro.hasPermission]
	</ul>
</div>
<script src="${base}/static/js/jquery-migrate-1.2.1.min.js" type="text/javascript"></script> 
<script src="${base}/static/js/bootstrap.min.js" type="text/javascript"></script> 
<script src="${base}/static/js/jquery.uniform.min.js" type="text/javascript" ></script> 
<script src="${base}/static/js/jquery.ztree.core-3.5.min.js"></script> 
<script src="${base}/static/js/jquery.ztree.excheck-3.5.min.js"></script> 
<script src="${base}/static/js/jquery.ztree.exedit-3.5.min.js"></script> 
<script src="${base}/static/js/app.js" type="text/javascript"></script> 
<script type="text/javascript" src="${base}/static/js/common.js"></script>
<script type="text/javascript" src="${base}/static/js/menu/menu.js"></script>
<script type="text/javascript" src="${base}/static/fancybox/jquery.fancybox.pack.js"></script>
<script>
		var zTree, rMenu;
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
				onRightClick: OnRightClick
			}
		};

		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}

		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			if (type=="root") {
				$("#m_del").hide();
			} else {
				$("#m_del").show();
			}
			/*var treeNode  = zTree.getSelectedNodes()[0];//获取当前选中节点
			if(treeNode==null){
			    $("#m_add").hide();
			    $("#m_edit").hide();
			}else{
			     $("#m_add").show();
			    $("#m_edit").show();
			}*/
			
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		
		function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		
		function clearAll(){
		   $("#code").val("");
		   $("#name").val("");
		   $("#url").val("");
		   $("#menuFlag").val("");
		   $("#url").val("");
		   $("#description").val("");
		}
		
		$(document).ready(function(){
		    zTree = $.fn.zTree.init($("#auth-tree"), setting, ${page});
		   // zTree.expandAll(true);//展开所有节点
			rMenu = $("#rMenu");
		});
    </SCRIPT>
</body>
</html>