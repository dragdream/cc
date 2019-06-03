 <%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//筛选ID
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/filter.js"></script>

<title>筛选</title>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	getHrCode();
	if(sid > 0){
		getInfoById(sid);
	}
}

function getHrCode(){
	//整治面貌
	var prcs = getHrCodeByParentCodeNo("HR_RECRUIT_FILTER" , "filterMethod");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/hrFilterController/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var endFilterState = prc.filterState;
			var filterStateDesc = "(待筛选)";
			if(endFilterState == '1'){
				filterStateDesc = "(筛选已通过)";
			}else if(endFilterState == '2'){
				filterStateDesc = "(筛选未通过)";
			}
			$("#filterStateDesc").append(filterStateDesc);
			$("#nextDatetimeStr").empty();
			if(prc.nextDatetime > 0){
				$("#nextDatetimeStr").append(getFormatDateStr(prc.nextDatetime,'yyyy-MM-dd HH:mm:ss'));
			}
			
			var itemModelList = prc.itemModelList;
			var type= 0;
			if(itemModelList.length > 0 ){
				for(var i =0 ;i<itemModelList.length ; i++){
					var filterItem = itemModelList[i];
					var filterState = filterItem.filterState;
					var filterStateDesc = "<font color='red'>未通过</font>";
					if(filterState == '1'){
						filterStateDesc =  "<font color='#009100'>已通过</font>";
					}
					var nextHandleStr = ' <td nowrap class="TableData">下一步骤办理人：</td>'
						   +' <td class="TableData">'+filterItem.nextTransactorStepName+ '</td>'
						   +'<td nowrap class="TableData">下一次筛选时间：</td>'
						   +'<td class="TableData">'+getFormatDateStr(filterItem.nextFilterDatetime,'yyyy-MM-dd HH:mm:ss')+' </td>'
						   +'</tr>';
					
					if(i== (itemModelList.length-1)){//最一个
						nextHandleStr = '';
					}
					
					var tableStr = '<table class="TableBlock" width="80%" align="center" id="table1">'
					  +'<tr class="TableHeader">'
					  +' <td nowrap colspan="4" align="center" >筛选步骤'+filterItemType[i].itemId+'</td>'
					  +' </tr>'
					  +' <tr>'
					   +'  <td nowrap class="TableData">'+filterItemType[i].itemName+'时间：</td>'
					   +'  <td class="TableData">'+getFormatDateStr(filterItem.filterDatetime,'yyyy-MM-dd HH:mm:ss')+'</td>'
					   +' <td nowrap class="TableData">'+filterItemType[i].itemName+'方式：</td>'
					   +' <td class="TableData" >'+filterItem.filterMethodDesc +'</td> '
					   +' </tr>'
					   +'<tr>'
					   +'  <td nowrap class="TableData">'+filterItemType[i].itemName+'内容：</td>'
					   +'  <td class="TableData" colspan=3>'+filterItem.filterContent+'</td>'
					   +' </tr>'
					   +'<tr>'
					   +' <td nowrap class="TableData">'+filterItemType[i].itemName+'意见：</td>'
					   +' <td class="TableData" colspan=3>'+filterItem.filterView+' </td>'
					   +' </tr>  '
					   +' <tr>'
					   +'<td nowrap class="TableData">'+filterItemType[i].itemName+'办理人：</td>'
					   +' <td class="TableData" >'+filterItem.transactorStepName+ '</td>'
					   +' <td nowrap class="TableData">是否通过：</td>'
					   +' <td class="TableData">'+filterStateDesc+'</td>'
					   +' </tr>'
					   +'<tr>'
					   + nextHandleStr
					   +'</table>';
					$("#filterInfo").append(tableStr + "<br>");
				}
				type = itemModelList.length ;
			}
			
			$("#filterTypeDesc").append(filterItemType[type].itemId);
			$("#filterDatetimeContent").append(filterItemType[type].itemName);
			$("#filterMethodContent").append(filterItemType[type].itemName);
		
			$("#filterContentContent").append(filterItemType[type].itemName);
			$("#filterViewContent").append(filterItemType[type].itemName);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}

/**
 * 返回
 */
function toReturn(){
	window.location.href = "manager.jsp";
}


</script>
</head>
<body onload="doInit()" >
<div class="moduleHeader">
	<!-- 	<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;
		筛选办理</b> -->
</div>

<table align="center" width="80%" class="TableBlock" style="margin-top: 10px;" >
  <tr class="TableHeader">
    <td nowrap  colspan="4" align="center">筛选准备 &nbsp;&nbsp;<font color="red" id="filterStateDesc"></font></td>
  </tr>
  <tr>
    <td nowrap class="TableData" width="20%" >应聘者姓名：</td>
    <td class="TableData" id="hrPoolName"></td>
   <td nowrap class="TableData" width="20%" >计划名称：</td>
    <td class="TableData"  id="planName" ></td>  
  </tr>
  <tr>
    <td nowrap class="TableData"  >应聘岗位：</td>
    <td class="TableData" id="position">  </td> 
    <td nowrap class="TableData" >所学专业：</td>
    <td class="TableData" id="employeeMajor" ></td>    
  </tr>
  <tr>
    <td nowrap class="TableData"  >联系电话：</td>
    <td class="TableData" id="employeePhone">  </td>
    <td nowrap class="TableData">发起人：</td>
    <td class="TableData" id="sendPersonId" >
    </td>     
  </tr>
  <tr>
    <td nowrap class="TableData">下一次筛选办理人：</td>
    <td class="TableData" id="nextTransactorName" > </td>
    <td nowrap class="TableData" >下一次筛选时间：</td>
    <td class="TableData" id="nextDatetimeStr" > </td>    
  </tr>
</table>
<br>
<div id="filterInfo"></div>
<br>

</body>
</html>
 