<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>会议室管理</title>

<style type="text/css">
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
	background: #f4f4f4;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
</style>



<script type="text/javascript">

var sid = <%=sid%>;


function doInit()
{
	
	if(sid > 0){
		getById(sid);
	}
}


/**
 * 新建或者更新
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/meetRoomManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("保存成功！");
			return true;
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	return false;
}
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid(); 
	 if(check ){
		 return true; 
	 }
	 if($("#managerId").val == ""){
		 return false;
	 }
	 return false;
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/meetRoomManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
	

</script>

</head>
<body onload="doInit();">
<div class="time_info">
<form id="form1" name="form1" method="post">
	<table class="none-table">
		<tr>
			<td nowrap class="TableData" width="100">会议室名称&nbsp;<font color="red">*</font>：</td>
			<td class="TableData">
				<input type="text" name="mrName"  class="BigInput easyui-validatebox" required="true">
				
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">会议室描述：</td>
			<td class="TableData">
				<textarea type="text" id="" name="mrdesc" class="BigTextarea easyui-validatebox"  cols="50" rows="3"></textarea></td>
		</tr>
		<tr class="TableData">
			<td nowrap>会议室管理员：</td>
			<td nowrap align="left">
				<input type="hidden" name="" id="" />
				<input id="managerIds" name="managerIds" type="hidden" value=''> 
				<textarea name=""managerNames"" id="managerNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['managerIds','managerNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('managerIds','managerNames')">清空</a>
			
			</td>
		</tr>
			<tr class="TableData" id="">
			<td nowrap>申请权限部门：</td>
			<td nowrap align="left">
				<input id="postDeptIds" name="postDeptIds" type="hidden" value=''> 
				<textarea name="postDeptNames" id="postDeptNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptIds','postDeptNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('postDeptIds','postDeptNames')">清空</a>
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td nowrap>申请权限人员：</td>
			<td nowrap align="left">
				<input id="postUserIds" name="postUserIds" type="hidden" value=''> 
				<textarea name="postUserNames" id="postUserNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['postUserIds','postUserNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('postUserIds','postUserNames')">清空</a>
			</td>
		</tr>
		<tr class="TableData">
			<td nowrap>可容纳人数：</td>
			<td nowrap align="left">
				<input type="text" id="" name="mrCapacity"   maxlength="50" class="BigInput easyui-validatebox"  >
		    </td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">会议室设备：</td>
			<td class="TableData">
				<input id="equipmentIds" name="equipmentIds" type="hidden" value=''> 
				<textarea name="equipmentNames" id="equipmentNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectMeetingEquipment(['equipmentIds','equipmentNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('equipmentIds','equipmentNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">设备情况：</td>
			<td class="TableData">
				<textarea type="text"  name="mrDevice" class="BigTextarea easyui-validatebox" cols="50" rows="3"></textarea></td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">地址&nbsp;<font color="red">*</font>：</td>
			<td class="TableData">
				<input type="text"  size="40"  maxlength="150" name="mrPlace" class="BigInput easyui-validatebox" required="true" ></textarea></td>
		</tr>
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</div>
</body>

</html>
