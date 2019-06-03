<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("sid");
	String personName = request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var humanDocSid = '<%=humanDocSid%>';
var personName = "<%=personName%>";
function doInit(){
	window.title0.innerHTML = personName+"的档案信息";
	$("#layout").layout({auto:true});
	$("#group").group();
	changePage(1);
}

function changePage(sel){
	if(sel==1){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/ht.jsp?humanDocSid="+humanDocSid);
	}else if(sel==2){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/jc.jsp?humanDocSid="+humanDocSid);
	}else if(sel==3){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/zs.jsp?humanDocSid="+humanDocSid);
	}else if(sel==4){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/px.jsp?humanDocSid="+humanDocSid);
	}else if(sel==5){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/xxjl.jsp?humanDocSid="+humanDocSid);
	}else if(sel==6){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/gzjl.jsp?humanDocSid="+humanDocSid);
	}else if(sel==7){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/gzjn.jsp?humanDocSid="+humanDocSid);
	}else if(sel==8){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/shgx.jsp?humanDocSid="+humanDocSid);
	}else if(sel==9){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/rsdd.jsp?humanDocSid="+humanDocSid);
	}else if(sel==10){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/lz.jsp?humanDocSid="+humanDocSid);
	}else if(sel==11){
		$("#frame0").attr("src",contextPath+"/system/core/base/pm/manage/fz.jsp?humanDocSid="+humanDocSid);
	}
}

function back(){
	CloseWindow();
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px 0px 0px 5px;">
<div id="layout">
	<div layout="west" width="180">
<!-- 		<button class="btn btn-default" style="width:100%" onClick="back()">关闭</button> -->
		<h6 id="title0" style="font-weight:bold;font-family:微软雅黑;text-align:center"></h6>
		<div id="group" class="list-group" style="margin-top:10px">
		  <a href="javascript:void(0)" class="list-group-item active" onclick="changePage(1)">
		  	<i class="glyphicon glyphicon-play-circle" style="float:left;"></i>
			<i class="glyphicon glyphicon-chevron-right pull-right"></i>
			&nbsp;合同管理
			<i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(2)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		    &nbsp;奖惩管理
		  	<i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(3)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;证书管理
		  	<i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(4)">
		  	<i class="glyphicon glyphicon-record"  style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;培训管理
		    <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(5)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;学习经历管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(6)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;工作经历管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(7)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;工作技能管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(8)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;社会关系管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(9)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>	
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;人事调动管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(10)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	&nbsp;离职管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(11)">
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  		&nbsp;复职管理
		  	 <i style="clear:both;"></i>
		  </a>
		</div>
	</div>
	<div layout="center" style="padding-left:10px;">
		<iframe id="frame0" frameborder="0" style="width:100%;height:100%"></iframe>
	</div>
</div>
</body>
</html>