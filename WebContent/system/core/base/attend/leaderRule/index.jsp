<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<title>考勤审批规则管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function  doOnload(){
	queryLeaderRule();
}
/**
 *查询管理
 */
function queryLeaderRule(){
	var url =   "<%=contextPath %>/attendLeaderRuleManage/selectLeaderRule.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableList' width='98%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
		    	 +"<td nowrap  width='30%'  align='center'>审批人员</td>" 	
		    	 + "<td width='20%' align='center'>管辖部门</td>"
			      	 +"<td nowrap width='30%' align='center'>管辖人员  </td>"
			      	 +"<td nowrap  width='8%' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
				var prc = prcs[i];
				var id = prc.sid;
				var  fontStr = "";
				tableStr = tableStr +"<tr class=''>"
		     			 +"<td align='center'><font color='" + fontStr + "'>" + prc.leaderNames + "</font></td>"
				      	 + "<td width='' align='center'><font color='" + fontStr + "'>"+ prc.deptNames +"</font></td>"
				      	 +"<td align='center'><font color='" + fontStr + "'>" + prc.userNames + "</font></td>"
				      	 +"<td nowrap align='center'>"
				      	 +"<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myAffairModal\" onclick='getAttendRuleById(\"" + id + "\");'> 编辑 </a>"
				      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteAttend(\"" + id + "\",this);'>删除 </a>"
				      	 +"</td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";
				
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("没有相关考勤审批规则信息", "listDiv" ,'' ,380);
		}
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 新建或者更新外出
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/attendLeaderRuleManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("保存成功！");
			window.location.reload();
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
	 if($("#deptIds").val() == "" && $("#userIds").val() == ""){
		 $.MsgBox.Alert_auto("管辖部门和管辖人员至少有一项为必填项！");
		 return false;
	 }
	 if($("#leaderIds").val() == "" && $("#leaderIds").val() == ""){
		 $.MsgBox.Alert_auto("审批人员为必填项！");
		 return false;
	 }
	 return true;
}
/**
 * 删除考勤审批
 */
function deleteAttend(sid){
	var msg = "是否删除此考勤审批规则，删除后不可恢复！";
	/* if(confirm(msg)){
		var url = contextPath + "/attendLeaderRuleManage/deleteById.action";
		var para = {sid:sid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	} */
	$.MsgBox.Confirm("提示", msg, function(){
		var url = contextPath + "/attendLeaderRuleManage/deleteById.action";
		var para = {sid:sid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	});
}


/**
 * 获取考勤审批规则
 */
function getAttendRuleById(id){
	var url =   "<%=contextPath%>/attendLeaderRuleManage/getById.action";
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
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
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
</head>
<body class="" style="overflow-y:auto;overflow-x:hidden;padding-top:25px;" onload="doOnload();">
	<div id="time_info">
	<table border="0" width="60%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td class="Big"><span class="Big3">新建考勤审批规则</span><br>
			</td> 
		</tr>
	</table>
	<form id="form1" name="form1" method="post">
	<table class="none-table" width="60%" align="center" style="margin-top:10px;">
		<tr class="TableData" id="">
			<td nowrap>管辖部门：</td>
			<td nowrap align="left">
				<input id="deptIds" name="deptIds" type="hidden" value=''> 
				<textarea name="deptNames" id="deptNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['deptIds','deptNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('deptIds','deptNames')">清空</a>
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td nowrap>管辖人员：</td>
			<td nowrap align="left">
				<input id="userIds" name="userIds" type="hidden" value=''> 
				<textarea name="userNames" id="userNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userIds','userNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('userIds','userNames')">清空</a>
			</td>
		</tr> 
			<tr class="TableData" id="">
			<td nowrap>审批人员：</td>
			<td nowrap align="left">
				<input id="leaderIds" name="leaderIds" type="hidden" value=''> 
				<textarea name="leaderNames" id="leaderNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['leaderIds','leaderNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('leaderIds','leaderNames')">清空</a>
			</td>
		</tr> 
		</tr> 
			<tr class="TableData" id="" style="border-bottom:none;">
			<td nowrap colspan="2" align="center">
				<input type="button" value="保存" class="btn-alert-blue fr" onclick="doSaveOrUpdate();">
				<input type="hidden" name="sid"  id="sid" value="0"> 
			</td>
		</tr>
	</table>
	</form>
	
	
	<table border="0" width="60%" cellspacing="0" cellpadding="3" class="small" style="padding-top:20px;">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td class="Big"><span class="Big3">考勤审批规则管理</span><br>
			</td> 
		</tr>
	</table>
	</div>
	<div id='listDiv'></div>
</body>

</html>