<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  //获取分类主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sidStr"), 0);
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作事项办理</title>
<style>
.table{
	border-collapse:  collapse;
    border:  1px  solid  #f2f2f2;
    width:90%;
    margin-left:  0px;
}
.table  tr{
	line-height:35px;
	border-bottom:1px  solid  #f2f2f2;
}
.table  tr  td:first-child{
	text-indent:10px;
}
.table  thead tr:first-child  td{
	font-weight:bold;
}
.table thead tr:first-child{
	border-bottom:2px  solid  #b0deff!important;
	background-color:  #f8f8f8;  
}
</style>
<script>
var sid=<%=sid%>;
function doInit(){
	commit();
}

function goback(){
	window.location = contextPath+"/system/subsys/cowork/list.jsp";
}

//提交
function commit(){
	var para={sid:sid};
	var url="<%=contextPath%>/teePbOnDutyController/findDutyById.action";
	var json=tools.requestJsonRs(url,para);
	var data=json.rtData;
	if(data!=null){
		bindJsonObj2Cntrl(data);
		var map=data.mapList;
		if(map!=null && map.length>0){
			for(var i=0;i<map.length;i++){
			   var html="<tr>";
				   html+="<td style='text-indent: 10px'>"+map[i].childName+"：</td>";
				   html+="<td>";
				       html+="<input class='BigInput  readonly easyui-validatebox' value='"+map[i].userName+"' required name='typeName'  id='typeName' readonly/>";
				   html+="</td>";
			   html+="</tr>";
			   $(".TableBlock").append(html);
			}
		}
	}
	
}

</script>
</head>

<body onload="doInit()" style="margin-left:2%;width:96%;">
<div id="toolbar" class = "topbar clearfix">
	   <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_tkgl.png">
		  <span class="title">安排值班信息</span>
	   </div>
	   <div class = "right fr clearfix">
	   </div>
	</div>
<div style="width:100%;">
<form id="form1">
   <input type="hidden" name="sid" value="<%=sid %>"/>
   <table class="TableBlock" style="width: 100%;background-color: white;" >
      <tr>
         <td style="text-indent: 10px">值班部门：</td>
         <td>
			<input class="BigInput  readonly easyui-validatebox" required name="deptName"  id="deptName" readonly/>
         </td>
      </tr>
       <tr>
         <td style="text-indent: 10px">带班领导：</td>
         <td>
			<input class="BigInput  readonly easyui-validatebox" required name="userName"  id="userName" readonly/>
         </td>
      </tr>
     <tr id="typeIdTr">
         <td style="text-indent: 10px">值班类型：</td>
         <td>
			<input class="BigInput  readonly easyui-validatebox" required name="typeName"  id="typeName" readonly/>
         </td>
      </tr>
      
   </table>
</form>
</div>
</body>
</html>