<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本信息</title>
</head>
<script>
var sid=<%=sid%>;
function doInit(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		
		bindJsonObj2Cntrl(json.rtData);
		$("#days").html(json.rtData.days+"天");
		$("#progress").html(json.rtData.progress+"%");
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
	
}

</script>
<body onload="doInit()" style="padding-top: 10px">
<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="100%" >
    <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">任务基本信息</B></TD>
	</tr>
	<tr>
    <td  class="TableData" width="100" style="text-indent:15px">任务序号：</td>
    <td  class="TableData" align="left" id="groupSelect" name="taskNo" id="taskNo">
      
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">任务名称：</td>
    <td  class="TableData" align="left" name="taskName" id="taskName">
        
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">任务级别：</td>
    <td  class="TableData" align="left" id="taskLevel" name="taskLevel">
      
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">负责人：</td>
    <td  class="TableData" align="left" id="managerName" name="managerName">
       
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">上级任务：</td>
    <td  class="TableData" align="left" id="higherTaskName" name="higherTaskName">
        
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">前置任务：</td>
    <td  class="TableData" align="left" id="preTaskName" name="preTaskName">
        
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">创建时间：</td>
     <td  class="TableData" align="left" id="createTimeStr" name="createTimeStr">
     </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">开始时间：</td>
     <td  class="TableData" align="left" id="beginTimeStr" name="beginTimeStr">
     </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">结束时间：</td>
     <td  class="TableData" align="left" id="endTimeStr" name="endTimeStr">
     </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">任务周期：</td>
     <td  class="TableData" align="left" id="days" name="days">
     </td>
   </tr>
   <tr>
    <td  class="TableData" width="100" style="text-indent:15px">任务进度：</td>
     <td  class="TableData" align="left" id="progress" name="progress">
     </td>
   </tr>
    <tr>
		<td  class="TableData" width="100" style="text-indent:15px">相关流程：</td>
		<td  class="TableData" id="flowTypeNames" name="flowTypeNames">
		   
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="100" style="text-indent:15px">任务描述：</td>
		<td  class="TableData" name="description" id="description">
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="100" style="text-indent:15px">备注：</td>
		<td  class="TableData" name="remark" id="remark">
		   
		</td>
	</tr>
</table>

  </form>
</body>
</html>