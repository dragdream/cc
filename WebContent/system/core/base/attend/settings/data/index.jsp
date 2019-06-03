<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
/**
 * 初始化
 */
function doInit(){
	//getLogType();
}

/**
 * 确定
 */
function doSubmit(){
	
	if($("#form1").valid() && checkForm()){
		/* if(confirm("确定删除满足以上条件的考勤数据吗！")){
			var param = tools.formToJson($("#form1"));
			var url = contextPath+"/TeeAttendDutyController/delAttendData.action";
			var json = tools.requestJsonRs(url,param);
			if(json.rtState){
// 				top.$.jBox.tip(json.rtMsg,"success");
				parent.$.MsgBox.Alert_auto(json.rtMsg);
			}else{
// 				top.$.jBox.tip("删除失败！","error");
				parent.$.MsgBox.Alert_auto("删除失败！");
			}
		} */
		$.MsgBox.Confirm("提示", "确定删除满足以上条件的考勤数据吗！", function(){
			var param = tools.formToJson($("#form1"));
			var url = contextPath+"/TeeAttendDutyController/delAttendData.action";
			var json = tools.requestJsonRs(url,param);
			if(json.rtState){
// 				top.$.jBox.tip(json.rtMsg,"success");
				parent.$.MsgBox.Alert_auto(json.rtMsg);
			}else{
// 				top.$.jBox.tip("删除失败！","error");
				parent.$.MsgBox.Alert_auto("删除失败！");
			}
		});
	}
}

function checkForm(){
	 if($("#startDateDesc").val() && $("#endDateDesc").val()){
		    if(document.getElementById("startDateDesc").value > document.getElementById("endDateDesc").value){
		    	$.MsgBox.Alert_auto("开始时间不能大于结束时间！");
		      return false;
			 }
	 }
	return true;
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
<body onload="doInit()"  style="overflow-y:auto;overflow-x:hidden;padding-top:25px;">
	<center>
		<div id="time_info">
				<form method="post" name="form1" id="form1">
					<table class="none-table" style="width:60%;font-size:12px;margin:0 auto;">
							<tr class="TableHeader" style="font-size:16px;border-bottom: 2px solid #b0deff;"><td colspan="2">考勤数据删除</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">用户：</td>
							<td align="left">
								<input type='hidden' class="BigInput" id="userIds" name="userIds"/>
								<textarea id="userNames" name="userNames" style="width:240px;height:60px;background:#f0f0f0;padding:5px" class="BigTextarea" readonly></textarea>
								<a href="javascript:void(0)" onclick="selectUser(['userIds','userNames'])">选择</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="clearData('userIds','userNames')">清除</a>
								<br/>
								<span>说明：为空默认删除所有用户的的考勤记录</span>
							</td>
							</tr>
							<tr class="TableData">
							<td width="30%" align="left">开始时间：</td>
							<td align="left">
								<input type="text" id='startDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startDateDesc' class="Wdate BigInput easyui-validatebox" style="width:240px;"/>
							</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">截止时间：</td>
							<td align="left">
								<input type="text" id='endDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endDateDesc' class="Wdate BigInput easyui-validatebox" style="width:240px;"/>
							</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">删除项目：</td>
							<td align="left">
								<input type="checkbox" id="registerType1" name="registerType1" value="0" />上下班登记&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="registerType2" name="registerType2" value="0" />外出登记&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="registerType3" name="registerType3" value="0"/>请假登记&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="registerType4" name="registerType4" value="0"/>加班登记&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" id="registerType5" name="registerType5" value="0" />出差登记&nbsp;&nbsp;&nbsp;&nbsp;
							</td></tr>
							<tr class="TableData" style="border-bottom:none;"><td colspan="2" align="center"><input onclick="doSubmit();" type="button" value="确定" class="btn-win-white fr"/></td></tr>
					</table>
				</form>
		</div>
</center>
<div id="logList" style="display:none;width:100%;height:100%;">
	<table id="datagrid" fit="true"></table>
		<div id="toolbar">
			<div style="text-align:left;">
				<button class="btn-del-red" onclick="delAll()">批量删除</button>
				<button class="btn-alert-gray" onclick="$('#condition').show();$('#logList').hide();">返回</button>
			</div>
		</div>
</div>
</body>
</html>

