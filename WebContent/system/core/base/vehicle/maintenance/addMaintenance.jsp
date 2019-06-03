<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆维护Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/vehicle/js/vehicle.js"></script>
<title>新建车辆维护记录</title>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
  getVehicleList();
  if(sid > 0){
    getInfoById(sid);
  }
}

/* 获取车辆 */
function getVehicleList(){
	var vehicleObj = selectPostVehicle();
	var optionsStr = "";
	for(var i =0; i < vehicleObj.length ; i ++){
		optionsStr += "<option value='" + vehicleObj[i].sid +  "'>" + vehicleObj[i].vModel + "</option>"; 
	}
	$("#vehicleId").append(optionsStr);
	/* if(vehicleSid > 0){
		$("#vehicleId").val(vehicleSid);
	} */
}


function checkForm(){
	var check = $("#form1").validate(); 
	if(!check){
		return false; 
	} 
	if(!$("#vehicleId").val()){
		$.MsgBox.Alert_auto("请选择车辆！");
		return false; 
	}
	if(!$("#vmType").val()){
		$.MsgBox.Alert_auto("请选择车辆维护类型！");
		return false; 
	}
	if(!$("#vmPersonId").val()){
		$.MsgBox.Alert_auto("请选择经 办人！");
		return false; 
	}
	return true;
}

function doSaveOrUpdate(callback){
  if(checkForm()){
    var url = "<%=contextPath %>/vehicleMaintenanceController/addOrUpdate.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
      callback();
      /* setTimeout(function(){
        //刷新父页面
        parent.location.reload();
		//return true;
      },1800); */
    }else{
      $.MsgBox.Alert_auto(jsonRs.rtMsg);
    }
  }
}




/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/vehicleMaintenanceController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#vmPersonId").val(prc.vmPersonId);
			$("#vmPersonIdName").val(prc.vmPersonName);
			//$("#vuUserIdName").val(prc.vuUserName);
			//$("#vehicleId").text(prc.vehicleName);
			//$("#vuOperatorId").text(prc.vuOperatorName);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}






</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="">
<table class="TableBlock" width="100%">
	<tr>
		<td nowrap class="TableData"  width="15%;"  style="text-indent:10px">车 牌 号：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" colspan="3">
			<select class='BigSelect' id="vehicleId" name="vehicleId" >
				<option value="" >请选择车辆</option>
			</select>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">维护日期：<font style='color:red'>*</font></td>
		<td class="TableData" width="" >
			<input type="text" style="height: 20px" name="vmRequestDateStr" id="vmRequestDateStr"  class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		<td nowrap class="TableData" width="" >维护类型：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<select id="vmType" name="vmType" class="BigSelect" style="height: 20px;width:170px">
				<option value="0">维修</option>
				<option value="1">加油</option>
				<option value="2">洗车</option>
				<option value="3">年检</option>
				<option value="4">其他</option>
			</select>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" style="text-indent:10px" >经 办 人：<font style='color:red'>*</font></td>
		<td class="TableData"  >
			<input type=hidden name="vmPersonId" id="vmPersonId" value="">
			<input  type="text" style="height: 20px;width: 170px" name="vmPersonIdName" id="vmPersonIdName" class="BigStatic BigInput" size="10"  readonly ></input>
			<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['vmPersonId', 'vmPersonIdName']);">添加</a>
			<a href="javascript:void(0);" class="orgClear" onClick="$('#vmPersonId').val('');$('#vmPersonIdName').val('');">清空</a>
		</td>
		<td nowrap class="TableData" width="" >维护费用：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<input type="text" style="height: 20px" name="vmFee" id="vmFee" size="" maxlength="10" class="BigInput easyui-validatebox"  required="true"   validType ='number[]'>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">维护原因：<font style='color:red'>*</font></td>
		<td class="TableData" width="" colspan="3">
			<textarea id="vmReason" name="vmReason" rows="5" cols="84" maxlength="200" class="BigTextarea easyui-validatebox"  required="true"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">备　　注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="5" cols="84" id="vmRemark" name="vmRemark" maxlength="200" class="BigTextarea"></textarea>
		</td>
	</tr>
</table>
</form>



</body>
</html>