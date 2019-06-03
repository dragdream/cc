<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
  //项目主键
  String projectId=TeeStringUtil.getString(request.getParameter("uuid"));
  String option=TeeStringUtil.getString(request.getParameter("option"));
  if(("").equals(option)){
	  option="基本信息";
  }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
	*{
		margin:0;
		padding:0;
	}
	body{
		font-family: "微软雅黑";
	}
	.menuList{
		position:absolute;
		width:8%;
		top:0;
		left:0;
		border:1px solid #ececec;
		border-bottom: none;
	}
	.menuList li{
		width:100;
		text-align: center;
		list-style: none;
		line-height: 25px;
		font-size: 14px;
		cursor: pointer;
		border-bottom:1px solid #ececec;
	}
	.itemContent{
		position: absolute;
		left: 12%;
		top: 10px;
		bottom:20px;
		right:0px;
	}
	.itemContent iframe{
		width: 100%;
		height: 100%;
	}
	.active{
		border-left: 2px solid #379ff7;
		background-color:#e5f3ff;
	}
</style>
<title>基本信息</title>
</head>
<body >
	<ul class="menuList" style="margin-top: 15px">
		<li class='active' menuSrc="<%=contextPath %>/system/subsys/project/projectdetail/basic/info.jsp?uuid=<%=projectId %>">基本消息</li>
		<li menuSrc="<%=contextPath %>/system/subsys/project/projectdetail/basic/file.jsp?uuid=<%=projectId %>">项目文档</li>
		<li menuSrc="<%=contextPath %>/system/subsys/project/projectdetail/basic/taskList.jsp?projectId=<%=projectId %>">任务列表</li>
		<li menuSrc="<%=contextPath %>/system/subsys/project/projectdetail/basic/questionList.jsp?projectId=<%=projectId %>">问题追踪</li>
		<li menuSrc="<%=contextPath %>/system/subsys/project/projectdetail/basic/communication.jsp?uuid=<%=projectId %>">相关交流</li>
		<li menuSrc="<%=contextPath %>/system/subsys/project/projectdetail/basic/notation.jsp?uuid=<%=projectId %>">项目批注</li>
	</ul>



<div class="itemContent">
	<iframe src="<%=contextPath %>/system/subsys/project/projectdetail/basic/info.jsp?uuid=<%=projectId %>" frameborder="0"></iframe>
</div>
	<script type="text/javascript">
	$(".menuList li").click(function(event) {
		var src = $(this).attr("menuSrc");
		$(".itemContent iframe").attr("src",src);
		$(".menuList li").removeClass('active');
		$(this).addClass('active');
	});
	var option = "<%=option%>";

	for(var i=0,l=$(".menuList li").length;i<l;i++){
		var text = $($(".menuList li")[i]).text();
		
		if(text == option){
			$($(".menuList li")[i]).click();
			break;
		}
	}
	</script>
</body>

</html>