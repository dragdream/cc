<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

   int userId=TeeStringUtil.getInteger(request.getParameter("userId"), 0);
   String userName=TeeStringUtil.getString(request.getParameter("userName"));
   int selectType=TeeStringUtil.getInteger(request.getParameter("selectType"), 0);
   String beginDateStr=TeeStringUtil.getString(request.getParameter("beginDateStr"));
   String endDateStr=TeeStringUtil.getString(request.getParameter("endDateStr"));
   
   String dateDesc="";
   if(selectType==1){
	   dateDesc="今日";
   }else if(selectType==2){
	   dateDesc="昨日";
   }else if(selectType==3){
	   dateDesc="本周";
   }else if(selectType==4){
	   dateDesc="本月";
   }else if(selectType==5){
	   dateDesc="本年";
   }else if(selectType==6){
	   dateDesc=beginDateStr+"~"+endDateStr;
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>电子围栏警报详情</title>
</head>
<script type="text/javascript">
var userId=<%=userId %>;
var userName="<%=userName %>";
var selectType=<%=selectType %>;
var beginDateStr="<%=beginDateStr %>";
var endDateStr="<%=endDateStr %>";



var bStr="";
var eStr="";
var datagrid ;
function doInit(){
	var param={};
	param["userId"]=userId;
	param["selectType"]=selectType;
	param["beginDateStr"]=beginDateStr;
	param["endDateStr"]=endDateStr;
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFootPrintController/getAcrossList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		queryParams:param,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'crTimeStr',title:'时间',width:100},
			{field:'addressDescription',title:'地点',width:200}
		]]
	});

}



</script>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">

   <div id="toolbar" class = "topbar clearfix">
      <div class="fl" style="position:static;">
		<%-- <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_shoujianxiang.png"> --%>
		<span class="title"><%=userName %>电子围栏警报列表</span>
	  </div>
   </div>
   <table id="datagrid" fit="true"></table>

</body>
</html>