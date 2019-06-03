<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/validator2.0.jsp"%>
<title>自定义分组</title>
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
<script type="text/javascript">

var uuid = '<%=uuid%>';

function doInit(){
	//添加例子一
	if(uuid != ""){
		var url = "<%=contextPath %>/userGroup/getUserGroupById.action";
		var para = {uuid:uuid};
		//alert(uuid);
		var jsonObj = tools.requestJsonRs(url,para);
		
		if(jsonObj){
			var json = jsonObj.rtData;
			//alert(json.uuid);
			if(json.uuid){
				bindJsonObj2Cntrl(json);
			}
			
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	
}

/**
 * 保存
 */
function doSave(){
	if(checkForm()){
		var url = "<%=contextPath %>/userGroup/addUpdateUserGroup.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
			window.location.href = "<%=contextPath%>/system/core/dept/usergroup/personalgroup.jsp";
			});
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}
/* function check() {
	return $("#form1").form('validate'); 
	
  }
   */
function doReturn() {
	window.location.href = "<%=contextPath%>/system/core/dept/usergroup/personalgroup.jsp";
	
  }

</script>

</head>
<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
     <div class="fl">
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_xzfz.png" alt="" />
         &nbsp;<span class="title">新增分组</span>
     </div>
</div>
  
  <form method="post" name="form1" id="form1">
<table class="TableBlock_page" width="80%" align="center">
			<tr>
				<td style="text-indent: 10px">分组名称：</td>
				<td ><input type="text" name="groupName" id="groupName"  required>&nbsp;</td>
			</tr>

			<tr>
				<td style="text-indent: 10px">排序号：</td>
				<td ><INPUT type=text name="orderNo" id="orderNo"  required size="10" positive_integer="true"></td>
			</tr>
			<tr>
				<td style="text-indent: 10px">分配用户：</td>
				<td><input type="hidden" name="userListIds" id="userListIds" value="">
				 <textarea cols="60" name="userListNames" id="userListNames" rows="6" style="overflow-y: auto;font-family: MicroSoft Yahei;font-size: 12px;" class=" BigTextarea" wrap="yes" readonly></textarea>
					 <span name="addSpan" class='addSpan'>
			              <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/smsManager/add.png" onClick="selectUser(['userListIds', 'userListNames'])"></img>
			              <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/smsManager/clear.png" onClick="clearData('userListIds', 'userListNames')"></img>
		             </span>
		        </td>
			</tr>

			<tr>
				<td style="text-indent: 10px;padding-left: 500px;" colspan="2">
				  <input type="button" value="保存" class="btn-win-white" onclick="doSave();">&nbsp;&nbsp;
				  <input type="button" value="返回" class="btn-win-white" onclick="doReturn();">&nbsp;&nbsp;
				  <input type="text" id="uuid" name="uuid" style="display:none;"/>	
				  <input type="hidden" id="userId" name="userId" value="<%=loginPersonId%>"/>
				  <input type="text" id="userUuid" name="userUuid" style="display:none;"/>				
					</td>
			</tr>
		</table>
		
   </form>
</body>
</html>