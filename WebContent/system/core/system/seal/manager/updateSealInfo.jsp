<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String sid=TeeStringUtil.getString(request.getParameter("sid"));
%>
<!DOCTYPE html>
<html style="background-color: #f2f2f2;"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>重置密码</title>
</head>
<script type="text/javascript" src="<%=contextPath%>/system/core/system/seal/js/sealmanage.js"></script>
<script type="text/javascript">
var sid="<%=sid%>";
var updateSealId = 0;
var failed=false;
function doInit(){
	updateSealId = sid;
	var url = contextPath +  "/sealManage/selectById.action?sid="+sid;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sealData = jsonRs.rtData.sealData;
		updatePwdShowInfoStrForManager(sealData);
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
}

/**
 * 保存信息
 */
function save(){
	if(!failed){
		if(updateSealId && updateSealId > 0){
			if($("#newPassword_manage")[0].value ==""){
				$.MsgBox.Alert_auto("请输入新密码！");
			    return;
			}
			if($("#newPassword_manage")[0].value != $("#newPassword2_manage")[0].value)
			{
			    $.MsgBox.Alert_auto("两次密码不一致，请重新输入！");
			    return;
			}
			var obj = document.getElementById("DMakeSealV62");
			  //把新密码赋值给activex控件
			obj.strOpenPwd = $("#newPassword_manage")[0].value;
			//生成新的base64格式的印章数据，保存到activex控件中，同时赋值给页面的隐藏域SEAL_DATA
			var sealData = obj.SaveData();
			 var url = contextPath +  "/sealManage/addOrUpdateSeal.action" ;
				var para = {sid:updateSealId , sealData : sealData};
				var jsonRs = tools.requestJsonRs(url,para);
				if(jsonRs.rtState){
					$.MsgBox.Alert_auto("保存成功！");
					return true;
				}else{
					
					$.MsgBox.Alert_auto(jsonRs.rtMsg);
					return false;
				}
			 

		}else{
			$.MsgBox.Alert_auto("请选择印章！");
		}
	}else{
		$.MsgBox.Alert_auto("保存失败！");
		return false;
	}
	
	
}




</script>

<body onload="doInit();" style="background-color: #f2f2f2;">
   	
<div id="updateSealInfo" >
  <div id="apply_body_manage" class="body" align=""  style="">
	  <table class="TableBlock" width="100%">
				<tr>
					<td class="TableData" colspan=2 align="center">
					    <div align="center">
						<OBJECT
							id=DMakeSealV62 
							style="left: 0px; top: 0px" classid="clsid:3F1A0364-AD32-4E2F-B550-14B878E2ECB1" VIEWASTEXT
							width="200"
							height="150"
							codebase='<%=contextPath%>/system/core/system/seal/sealmaker/MakeSealV6.ocx#version=1,0,3,4'>
							<PARAM NAME="_Version" VALUE="65536">
							<PARAM NAME="_ExtentX" VALUE="2646">
							<PARAM NAME="_ExtentY" VALUE="1323">
							<PARAM NAME="_StockProps" VALUE="0">
						 </OBJECT>
						 </div>
					 </td>
				</tr>
				<tr>
					<td class="TableContent" width=80 style="text-indent: 10px;">印章ID</td>
					<td class="TableData"><span id="seal_id_manage"></span></td>
				</tr>
				<tr>
					<td class="TableContent" style="text-indent: 10px;">印章名称</td>
					<td class="TableData"><span id="seal_name_manage"></span></td>
				</tr>
				<tr>
					<td class="TableContent" style="text-indent: 10px;">新密码</td>
					<td class="TableData">
						<input type="password" name="newPassword" id="newPassword_manage" maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="TableContent" style="text-indent: 10px;">确认新密码</td>
					<td class="TableData">
						<input type="password" name="newPassword2" id="newPassword2_manage" maxlength="20"/>
					</td>
				</tr>
				
		</table>
  </div>
</div>
</body>
</html>