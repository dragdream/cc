<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
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
var sid = "<%=sid%>";
function commit(){
	if($("#form1").valid() && checkForm()){
		console.log($("#general1").prop("checked"));
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeAttendConfigController/setGeneral.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"info");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}
// 		top.$.jBox.tip(json.rtMsg,"error");
		parent.$.MsgBox.Alert_auto(json.rtMsg);
		return false;
	}
}
function checkForm(){
	return true;
}


function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeAttendConfigController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			var general = json.rtData.general;
		    var strs= new Array(); 
		    var str = "";
		    if(general){
			    if(general.trim().length>0){
				      strs = general.trim().split(",");    
				    }
			    for (var i=0;i<strs.length ;i++ ){
			      str = strs[i];
			      if(str != ""){
			        document.getElementById("general" + str).checked = 'checked';
			        }  
			     } 
		    }
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}
</script>

</head>
<body onload='doInit()' style="background:#f4f4f4;">
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class='TableBlock'>
		<tr class='TableData'>
			<td>
				<b>排班名称：</b>
			</td>
			<td>
				<input type="text"   id="dutyName" name="dutyName" required="true" class="easyui-validatebox BigInput" readonly="readonly"/>
				<input type="hidden"   id="sid" name="sid" value="<%=sid%>"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>公休日:</b>
			</td>
			<td>
				<input type="checkbox" id='general2' name='general2' value="2"/>星期一<br/>
				<input type="checkbox" id='general3' name='general3' value="3"/>星期二<br/>
				<input type="checkbox" id='general4' name='general4' value="4"/>星期三<br/>
				<input type="checkbox" id='general5' name='general5' value="5"/>星期四<br/>
				<input type="checkbox" id='general6' name='general6' value="6"/>星期五<br/>
				<input type="checkbox" id='general7' name='general7' value="7"/>星期六<br/>
				<input type="checkbox" id='general1' name='general1' value="1"/>星期日
			</td>
		</tr>
	</table>
</form>
</body>
</html>