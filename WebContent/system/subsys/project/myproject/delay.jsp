<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<title>项目延期</title>
</head>
<style>
input{
   height:23px;
   }
</style>
<script>
var uuid="<%=uuid%>";
function doInit(){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;	
		//渲染计划结束时间
		$("#planEndTime").html(data.endTime);
		$("#endTimeOld").val(data.endTime);
	}
	
}

function commit(){
	
		 if(check()){
				var endTime=$("#endTime").val();
				var url=contextPath+"/projectController/delay.action";
				var json=tools.requestJsonRs(url,{uuid:uuid,endTime:endTime});
				return json.rtState;
				/* if(json.rtState){
					$.MsgBox.Alert_auto("已延期！");
					//刷新父页面datagrid
					parent.datagrid.datagrid("reload");
					return true;
				} */
		 }
	
}

//验证
function  check(){
	var endTime=$("#endTime").val();
	if(endTime==""){
		$.MsgBox.Alert_auto("请选择延期日期！");
		return false;
	}
	return true;
	
}
</script>
<body onload="doInit()" style="background-color: #f2f2f2">
   <table class="TableBlock" width="100%">
       <tr>
          <td style="text-indent: 10px">计划结束日期</td>
          <td id="planEndTime">
             
          </td>
       </tr>
       <tr>
          <td style="text-indent: 10px">延期日期</td>
          <td>
             <input type="hidden" id='endTimeOld' name='endTimeOld' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput" style="width: 170px" />
             <input type="text" id='endTime' name='endTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTimeOld\')}'})" class="Wdate BigInput" style="width: 170px" />
          </td>
       </tr>
   </table>
</body>
</html>