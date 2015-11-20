<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

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

</head>

<body class="page-header-fixed">
[#include "left.ftl"/]
  <div class="page-content"> 
    <div class="container-fluid"> 
      <div class="row-fluid">
        <div class="span12"> 
          
        </div>
      </div>
      <div class="row-fluid">
        <div class="span12" style="text-align:center; padding-top:60px;">
        <img src="${base}/static/image/welcome.png" width="60%" style="margin-bottom:30px;" />
         <h2>欢迎使用权限管理系统 </h2>
         </div>
      </div>
      <div class="clearfix"></div>
    </div>
  </div>
</div>

<div class="footer">
  <div class="footer-inner"> 2015 &copy; SYMDATA. System Management. </div>
  <div class="footer-tools"> <span class="go-top"> <i class="icon-angle-up"></i> </span> </div>
</div>
 
</body>
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
</html>