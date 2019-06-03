<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆申请Id
	int vehicleSid = TeeStringUtil.getInteger(request.getParameter("vehicleSid"), 0);//车辆Id
	String startTime = TeeStringUtil.getString(request.getParameter("startTime"), "");//开始时间
	String endTime = TeeStringUtil.getString(request.getParameter("endTime"), "");//结束时间

	
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	String uuid = loginPerson.getUuid()+"";
	String userName = loginPerson.getUserName();
	boolean isAdmin = false;
	if(TeePersonService.checkIsAdminPriv(loginPerson)){
		isAdmin = true;
		uuid = "";
		userName = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆申请</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/vehicle/js/vehicle.js"></script>
<script type="text/javascript">
var vehicleSid = <%=vehicleSid%>;
var sid = <%=sid%>;

function doInit(){
	showPhoneSmsForModule("phoneSmsSpan","032",'<%=loginPerson.getUuid()%>');
  getVuOperatorId();
  getVehicleList();
  //alert(sid);
  if(sid > 0){
    getInfoById(sid);
  }
}


/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/vehicleUsageManage/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#vuUserIdName").val(prc.vuUserName);
			//$("#vehicleId").text(prc.vehicleName);
			//$("#vuOperatorId").text(prc.vuOperatorName);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
	

function getVuOperatorId(){
//审批人员
	var paramName = "VEHICLE_MANAGER_TYPE";
	//获取参数
	var params = getSysParamByNames(paramName);
	if(params.length > 0){
		var param = params[0];
		if(param.paraValue != "" ){
			var personInfo = getPersonListByUuids(param.paraValue);
			var personOptions = "";
			for(var i =0; i < personInfo.length ; i ++){
				personOptions = personOptions +  "<option value='" + personInfo[i].uuid +  "'>" + personInfo[i].userName + "</option>"; 
			}
			$("#vuOperatorId").append(personOptions);
		}
	}
}
/* 获取车辆 */
function getVehicleList(){
	
	var vehicleObj = selectPostVehicle();
	var optionsStr = "";
	for(var i =0; i < vehicleObj.length ; i ++){
		if(vehicleSid!=vehicleObj[i].sid){
			continue;
		}
		optionsStr += "<option value='" + vehicleObj[i].sid +  "'>" + vehicleObj[i].vModel + "</option>"; 
	}
	$("#vehicleId").append(optionsStr);
	if(vehicleSid > 0){
		$("#vehicleId").val(vehicleSid);
	}
}

function checkForm(){
	var vuUserId = $("#vuUserId").val();
	if(!vuUserId){
		//top.$.jBox.tip("使用人员不能为空！",'info',{timeout:1500});
		$.MsgBox.Alert_auto("使用人员不能为空！");
		return false;
	}
	var vuOperatorId = $("#vuOperatorId").val();
	if(!vuOperatorId){
		//top.$.jBox.tip("调度人员不能为空！",'info',{timeout:1500});
		$.MsgBox.Alert_auto("调度人员不能为空！");
		return false;
	}
	//var check = $("#form1").form('validate'); 
	var check=$("#form1").validate();
	if(!check){
		return false; 
	}
	return true;
}

function doSaveOrUpdate(callback){
  if(checkForm() && isApply()){
    var url = "<%=contextPath %>/vehicleUsageManage/addOrUpdate.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
    	sendPhoneSmsFunc();
    	callback();
      //top.$.jBox.tip("保存成功！", "info");
    }else{
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
      //top.$.jBox.tip(jsonRs.rtMsg, "error");
    }
    return jsonRs.rtState;
  }
}

//发送短信
function sendPhoneSmsFunc(){
	var dateStr = $("#vuStartStr").val() + "至" + $("#vuEndStr").val();
	var smsContent= "你有车辆申请待审批，使用人:" +$("#vuUserIdName").val() + "，使用时间从" + dateStr + "，目的地是：" + $("#vuDestination").val() + "，请审批。";
	var toSmsUserIdStr =  $("#vuOperatorId").val();
	sendPhoneSms(toSmsUserIdStr,smsContent,'');
}

//判断是否满足申请条件 1.车辆申请时间段是否在使用中，2.车辆申请时间段是否车辆正在进行维护
function isApply(){
	var vehicleId = $("#vehicleId").val();
	var vuStartStr = $("#vuStartStr").val();
	var url = "<%=contextPath %>/vehicleUsageManage/isApply.action?vehicleId="+vehicleId+"&vuStartStr="+vuStartStr;
	var jsonRs = tools.requestJsonRs(url);
    if(jsonRs.rtState){
    	if(jsonRs.rtData.type=='maintenancing'){
    		//top.$.jBox.tip(jsonRs.rtMsg,'info',{timeout:1500});
    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
    		$("#vuStartStr").focus();
    		return false;
    	}
    	if(jsonRs.rtData.type=='using'){
    		//top.$.jBox.tip(jsonRs.rtMsg,'info',{timeout:1500});
    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
    		return false;
    	}
    }
    return true;
}

</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid %>">
<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent:10px">司机：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;" >
			<input type="text" name="vuDriver" id="vuDriver" size="" maxlength="100" class="BigInput easyui-validatebox" value="" required="true" style="height: 23px">
		</td>
		<td nowrap class="TableData" width="15%;" >使用人：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<input type=hidden name="vuUserId" id="vuUserId" value="<%=uuid%>">
			<input  type="text" name="vuUserIdName" id="vuUserIdName" class="BigInput" size="10"  readonly value="<%=userName%>" style="height: 23px;width:170px"></input>
			<span style="<%=isAdmin?"":"display:none"%>">
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['vuUserId', 'vuUserIdName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#vuUserId').val('');$('#vuUserIdName').val('');">清空</a>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">起始时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="" >
			<input type="text" name="vuStartStr" id="vuStartStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" required="true" value="<%=startTime %>" style="height: 23px">
		</td>
		<td nowrap class="TableData" width="" >结束时间：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<input type="text" name="vuEndStr" id="vuEndStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" required="true" value="<%=endTime%>" style="height: 23px">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">目 的 地：<font style='color:red'>*</font></td>
		<td class="TableData" width="" >
			<input type="text" name="vuDestination" id="vuDestination" size="" maxlength="100" class="BigInput easyui-validatebox" value="" required="true" style="height: 23px">
		</td>
		<td nowrap class="TableData" width="" >里　　程：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<input type="text" name="vuMileage" id="vuMileage" size="" maxlength="100" class="BigInput easyui-validatebox"  required="true" maxlengtd='3'  validType ='number[]' style="height: 23px">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="display: none;">部门审批人：</td>
		<td class="TableData" width="" style="display: none;" colspan="3">
			<input type=hidden name="deptManagerId" id="deptManagerId" value="">
			<input  name="deptManagerIdName" id="deptManagerIdName" class="BigStatic BigInput" size="10"  readonly style="height: 23px"></input>
			<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['deptManagerId', 'deptManagerIdName']);">添加</a>
			<a href="javascript:void(0);" class="orgClear" onClick="$('#deptManagerId').val('');$('#deptManagerIdName').val('');">清空</a>
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">选择车辆：<font style='color:red'>*</font></td>
		<td class="TableData" width="">
			<select class='BigSelect' id="vehicleId" name="vehicleId" style="height: 23px" >
				
			</select>
		</td>
		<td nowrap class="TableData" width="" >调 度 员：<font style='color:red'>*</font></td>
		<td class="TableData"  >
			<select class="BigSelect easyui-validatebox" required="true" id="vuOperatorId" name="vuOperatorId" style="height: 23px">
				<option value="">请选择</option>
			</select>(注：负责审批) 
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">内部短信提醒：</td>
		<td class="TableData" width="" colspan="3">
			<input type="checkbox" name="smsRemind" id="smsRemind" size="" maxlength="100"  value="1" checked="checked">提醒调度人
			&nbsp;&nbsp;<span id="phoneSmsSpan"></span>&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">事　　由：<font style='color:red'>*</font></td>
		<td class="TableData" width="" colspan="3">
			<textarea id="vuReason" name="vuReason" rows="5" cols="60" class="BigTextarea  easyui-validatebox"  required="true" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">备　　注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="5" cols="60" id="vuRemark" name="vuRemark" class="BigTextarea" ></textarea>
		</td>
	</tr>
</table>
</form>

</body>
</html>