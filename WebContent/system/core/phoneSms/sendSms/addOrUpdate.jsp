<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String phones = request.getParameter("phones")==null?"":request.getParameter("siphonesd");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/validator2.0.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>

<style>
	table{
		width:100%;
		border-collapse:collapse;
	}
	table tr {
		line-height:35px;
		width:100%;
		height:auto;
		border-bottom:1px solid #f2f2f2;
		padding:5px 0;
	}
	table tr input{
		line-height:16px;
	}
	table tr td:first-child{
		width:200px;
		text-align:left;
		text-indent:8px;
	}
	.btns{
		text-align:center!important;
	}
	textarea{
		margin:10px 0!important;
		margin-bottom:0!important;
	}
</style>
<script>
var sid = "<%=sid%>";
var phones = "<%=phones%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeSmsSendPhoneController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}
function commit(){
	if (checkForm()){
		var url = contextPath+"/TeeSmsSendPhoneController/addOrUpdate.action";
		var param = tools.formToJson($("#form1"));
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto("发送成功!",function(){
			window.location.reload();
            });
		}
		// $.MsgBox.Alert("info", "发送成功");
	}
}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}
</script>
</head>
<body onload="doInit();">
<!-- <div style="border-bottom:solid #b0deff" id="toolbar" class = "setHeight clearfix">
   <div  class="fl clearfix">
	   <span style="font-size: 14px;font-weight: bold;color:#124164;">
        	  发送短信
       </span>
   </div> 
</div> -->
<form id="form1" name="form1" method="post">
	<table style="font-size:12px;" class='TableBlock_page' align="center">
		<tr class='TableData' align='left'>
			<td style="width:140px;">
				收信人[内部用户]：
			</td>
			<td>
				<input type="hidden" name="toId" id="toId" required="true" value=""> 
				<textarea style="font-family: MicroSoft YaHei;font-size: 12px;" cols="45" rows="5" name="toUserName" id="toUserName" class="" readonly ></textarea>
				<span name="addSpan" class='addSpan'>
				    <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/message/add.png" onClick="selectUser(['toId', 'toUserName'])"></img>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/message/clear.png" onClick="clearData('toId', 'toUserName')"></img>
			    </span>
			</td>
		</tr>
		<tr class='' align='left'>
			<td>
				收信人[外部号码]：
			</td>
			<td style="line-height:20px;padding:5px 0">
				<input type="text" style="width:250px" class="" name="phone" id="phone" value="<%=phones%>"/>
				<p>(号码之间请用逗号分隔)</p>
			</td>
		</tr>
		<tr class='' align='left'>
			<td >
				发送时间：
			</td>
			<td>
				<input type="text" id='sendTimeDesc' value="<%=TeeDateUtil.getCurDateTimeStr() %>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='sendTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='' align='left'>
			<td >
				短信内容：
				</td>
			<td>
				<textarea  style="font-family: MicroSoft YaHei;font-size: 12px;" id='content' name='content' required class="" cols="45" rows="5"></textarea>
			</td>
		</tr >
		<tr>
			<td colspan='2' style='text-align:center;'>
				<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
<!-- 				<input id="saveInfo" name="saveInfo" type='button' class="btn btn-primary" value="保存" onclick='commit();'/>&nbsp;&nbsp;&nbsp;&nbsp; -->
				<input id="sendInfo" name="sendInfo" type='button' class="btn-win-white" value="发送" onclick='commit();'/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="back" name="back" type='button' class="btn-win-white" value='返回' onclick='history.go(-1)'/>
			</td>
		</tr>
	</table>
	<br/>
</form>
</body>
<script>
	$("#form1").validate();
</script>
</html>
