<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<META content="IE=11.0000" http-equiv="X-UA-Compatible">
<META http-equiv="X-UA-Compatible" content="IE=Edge">
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<TITLE></TITLE>
<SCRIPT type="text/javascript">
	
</SCRIPT>
<body >
<div class="base_layout_left" style="width:200px;padding:10px;background:none;font-size:12px;border-right:0px" >
	<div class="panel panel-primary">
	  <!-- Default panel contents -->
	  <div class="panel-heading">基础设置</div>
	  <div class="list-group">
	  	<a class="list-group-item" onclick="$('#frame0').attr('src','/system/mobile/ding/paraset.jsp')">参数设置</a>
	    <a class="list-group-item" onclick="$('#frame0').attr('src','/system/mobile/ding/syncorg.jsp')">组织机构同步</a>
	    <a class="list-group-item" onclick="$('#frame0').attr('src','/system/mobile/ding/syncuser.jsp')">OA用户同步到钉钉</a>
	    <a class="list-group-item" onclick="$('#frame0').attr('src','/system/mobile/ding/binduser.jsp')">OA用户绑定到钉钉</a>
	  </div>	 
	</div>
	<div class="panel panel-primary">
	  <!-- Default panel contents -->
	  <div class="panel-heading">应用设置</div>
	  <div class="list-group">
	  	<a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=019');">电子邮件</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=021');">公告通知</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=020');">新闻</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=006');">工作流</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=022');">日程安排</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=018');">工作日志</a>
 	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=025');">个人网盘</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=024');">公共网盘</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=043');">计划管理</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=035');">任务管理</a>
	   	<a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=044');">客户管理</a>
	    <a class="list-group-item" style="cursor:pointer;" onclick="$('#frame0').attr('src','/system/mobile/ding/modules.jsp?modelType=050');">讨论区</a>
	  </div>	 
	</div>
</div>
<div class="base_layout_right" style="left:220px;top:10px;overflow:hidden;">
	<iframe id="frame0" frameborder=0 src="/system/mobile/ding/paraset.jsp" style="width:100%;height:100%"></iframe>
</div>
</body>
</HTML>
