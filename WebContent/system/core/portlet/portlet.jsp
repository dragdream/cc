<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeePortletModel" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/ztree.jsp"%>
	<title>Tenee办公自动化智能管理平台</title>
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	<style type="text/css">
	html,body{
		/*  overflow-x : hidden;   */
	}
    	.template {display:none;}
    	.version {margin-left: 0.5em; margin-right: 0.5em;}
    	.trace {margin-right: 0.5em;}
        .center {
            width: 100%;
            margin-left:5px;
            margin-right:2px;
        }
        
        li ul, li ol {margin:0;}
		ul, ol {margin:0 1.5em 1.5em 0;padding-left:1.5em;}
		ul {list-style-type:disc;}
		ol {list-style-type:decimal;}
		caption, th, td {text-align:left;font-weight:normal;float:none !important;}
		th, td, caption {padding:4px 10px 4px 5px;}
		caption {background:#eee;}
		.ui-widget-header{
			border-top-style:none;
			border-right-style:none;
			border-bottom-style:none;
			border-left-style:none;
			
		}
		.ui-sortable-placeholder {
			height: 50px !important;
			border:1px dotted black;
			background:#f0f0f0;
		}
    </style>
    <script src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
	<script src="<%=contextPath%>/common/jquery/portlet/portletPro.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			var str = "${portlet}";
			str = tools.strToJson(str);
			$('#portlet-container').portlet(str);
			var portletSet = tools.strToJson("${portletSet}");
			//判断是否有收缩桌面portlet的权限 1为有
			if(portletSet.setFold!="1"){
				$("#minusthick").hide();
			}
			//判断是否有调整桌面portlet高度的权限 1为有
			if(portletSet.setHeight=="1"){
				//$("#refresh").hide();
			}
		});
	</script>
	
</head>
<body style="margin:10px;">
	<div class="center" >
        <div style="text-align: center;">
        </div>
        <div id='portlet-container' ></div>
    </div>
    
</body>
</html>
