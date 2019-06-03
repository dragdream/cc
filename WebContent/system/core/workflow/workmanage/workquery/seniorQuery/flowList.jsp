	<%@ page language="java" contentType="text/html; charset=UTF-8"
			 pageEncoding="UTF-8"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml" style="overflow:hidden">
		<head>
		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<%@ include file="/header/header2.0.jsp" %>
		<title></title>
		<script type="text/javascript">
		function toFlowQuery(flowId){
		//跳转页面，springMVC 返回对象
		window.location = contextPath + "/seniorQuery/toFlowQuery.action?flowId="+flowId;
		}
		</script>
		<style>
		.color2{
			color:green;
			font-size:20px;
		}
		.color3{
			color:red;
			font-size:20px;
		}
		.panel .panel-body h4{
			line-height:15px;
			border-left:3px solid #0685ef;
			padding:3px 0;
			font-size:14px;
			text-indent:10px;
		}
		.panel .panel-body p{
			line-height:30px;
			border-bottom:1px dashed #c3c3c3;
		}
		.base_layout_center{
			position:absolute;
			top:60px;
			left:5px;
			bottom:0px;
			right:5px;
			overflow:auto;
		}
		.base_layout_center .panel:first-child .panel-body{
			padding-top:0px;
		}

		</style>
		</head>
		<body style="overflow:hidden;padding-left: 10px;padding-right: 10px;">
		<div id="toolbar" class="topbar clearfix">
		<div class="fl left">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzcx/icon_liuchenggaojichaxun.png">
		<span class="title">工作流高级查询 </span>
		</div>
		<div class="fr right">
		<span>颜色标识说明： &nbsp;&nbsp;<span class="color2">■</span>&nbsp;&nbsp;办理中 &nbsp;&nbsp;<span class="color3">■</span>&nbsp;&nbsp;已办结&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button"  value="返回" class="btn-win-white" onClick="location='<%=contextPath%>/system/core/workflow/workmanage/workquery/index.jsp';"/>
		</span>
		</div>
		</div>


		<div class="base_layout_center" style="padding:10px">
		<!-- 加载流程分类，分出下属流程 -->
		<c:forEach items="${flowList}" var="flowSort" varStatus="flowSortStatus">
			<div class="panel panel-default">
			<div class="panel-body" style="padding:10px">
			<h4>${flowList[flowSortStatus.index].sortName}</h4>
			<c:forEach items="${flowList[flowSortStatus.index].flowTypeModelSet}" var="flow" varStatus="flowStatus">
				<p>
				<a href="javascript:void(0)"  onclick="toFlowQuery(${flowList[flowSortStatus.index].flowTypeModelSet[flowStatus.index].sid});">
				<font>${flowList[flowSortStatus.index].flowTypeModelSet[flowStatus.index].flowName}</font></a>&nbsp;&nbsp;<span class="color2">■</span>&nbsp;&nbsp;${flowList[flowSortStatus.index].flowTypeModelSet[flowStatus.index].DEAL_COUNT}&nbsp;&nbsp;<span class="color3">■</span>&nbsp;&nbsp;${flowList[flowSortStatus.index].flowTypeModelSet[flowStatus.index].OVER_COUNT}
				</p>
			</c:forEach>
			</div>
			</div>
		</c:forEach>
		</div>
		</body>
		</html>
