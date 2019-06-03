<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	int sid = Integer.parseInt(request.getParameter("sid"));
    String currEnd = request.getParameter("currEnd");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
	.ztree{
		background:white;
		border:1px solid gray;
	}
</style>
<script>
var sid=<%=sid%>;
var currEnd='<%=currEnd%>';
function doInit(){
	//alert(currEnd);
	var url ="<%=contextPath%>/onDutyController/getDutyById.action";
	var para = {sid :sid};
	
	var jsonObj = tools.requestJsonRs(url, para);
	
	//alert(tools.jsonObj2String(jsonObj));
	 if (jsonObj.rtState) {	
		var target = jsonObj.rtData;		
	    bindJsonObj2Cntrl(target);		
	} else {
		
		alert(jsonObj.rtMsg);
	} 
   //判断是否时间已过
	 currEnd = currEnd.replace(/-/g,"/");
	 var currEnd1 = new Date(currEnd);
	 var currDate=new Date();
	 if(currDate>currEnd1){
		 $("#isExpired").text("时间已过");
	 }else{
		 $("#isExpired").text("时间未过"); 
	 }
    
	 
}


</script>

</head>
<body onload="doInit();" style="font-size:12px" class="TableBlock">
<form id="form1" name="form1">
	<table style="width:100%;font-size:12px" class='TableBlock'>
		<tr class="TableHeader" >
			<td colspan="4" style="width:100%;height:36px;">
				基本信息
			</td>
		</tr>
		<tr class='TableData'>
			<td class="TableData">
				值班人员<span style="color:red;font-weight:bold;"></span>：
			</td>
			<td class="TableData" name="userName" id="userName">
			</td>
			<td>
				所属部门：
				</td>
			<td name="deptName" id="deptName">
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				排班类型：
			</td>
			<td name="pbTypeDesc" id="pbTypeDesc">
			</td>
			<td>
				值班类型：
				</td>
			<td name="zbTypeDesc" id="zbTypeDesc">
			</td>
		</tr >
		<tr class='TableData' >
		    <td>
		          值班时间：
		    </td>
			<td colspan="3">
				<span name="beginTimeStr" id="beginTimeStr" ></span>
				  &nbsp;&nbsp; &nbsp;  至 &nbsp;&nbsp; &nbsp;  
				<span name="endTimeStr" id="endTimeStr" ></span>	
			</td>
		</tr>
		<tr class='TableData' >
		    <td>
		           值班要求：
		    </td>
			<td colspan="3" name="demand" id="demand" >
			</td>
		</tr>
		
		<tr class='TableData' >
		    <td>
		           备注：
		    </td>
			<td colspan="3" name="remark" id="remark">
			</td>
		</tr>
		
		<tr class='TableData' >
		    <td>
		            是否已过：
		    </td>
			<td colspan="3" name="isExpired" id="isExpired">
			</td>
		</tr>
		
	</table>
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>