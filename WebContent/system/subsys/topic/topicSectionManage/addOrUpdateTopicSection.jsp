<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建/编辑版块</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
	crPrivChange(1);
	viewPrivChange(1);
	if(uuid){
		getInfoById(uuid);
	}
	$("[title]").tooltips();
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/TeeTopicSectionController/getInfoById.action";
	var para = {uuid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			
			if(prc.crPriv=='ALL'){
				$("#crPrivSpan").hide();
			}else{
				$("#crPrivSpan").show();
			}
			if(prc.viewPrivAllFlag=='1'){
				$("#viewPrivSpan").hide();
			}else{
				$("#viewPrivSpan").show();
			}
			
			/* $("#vmPersonId").val(prc.vmPersonId);
			$("#vmPersonIdName").val(prc.vmPersonName); */
			//$("#vuUserIdName").val(prc.vuUserName);
			//$("#vehicleId").text(prc.vehicleName);
			//$("#vuOperatorId").text(prc.vuOperatorName);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

function crPrivChange(id){
	if(id==1){
		$("#crPrivSpan").hide();
		$("#crPriv").val("ALL");
		$("#crPrivName").val("全体人员");
	}else if(id==0){
		$("#crPrivSpan").show();
		$("#crPriv").val("");
		$("#crPrivName").val("");
	}
}
function viewPrivChange(id){
	if(id==1){
		$("#viewPrivSpan").hide();
		$("#viewPriv").val("ALL");
		$("#viewPrivName").val("全体人员");
	}else if(id==0){
		$("#viewPrivSpan").show();
		$("#viewPriv").val("");
		$("#viewPrivName").val("");
	}
}

function checkForm(){
	var check = $("#form1").valid(); 
	if(!check){
		return false; 
	}
	if($("#manager").val() == ""){
		$.MsgBox.Alert_auto("请选择版主！");
		return false;
	}
	if($("#manager").val()){
		var managerStr = $("#manager").val().split(",");
		if(managerStr.length>5){
			$.MsgBox.Alert_auto("版主最多5人！");
			return false;
		}
	}
	return true;
}

function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/TeeTopicSectionController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			$.MsgBox.Alert_auto("保存成功!");
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
}



function toReturn(){
	location.href = contextPath + "/system/subsys/topic/index.jsp?option=板块管理";
}
</script>
<style>
	.TableBlock_page select{
		position:absolute;
	}
</style>
</head>
<body  onload="doInit();" style="padding-left: 10px;padding-right: 10px">
  <div id="toolbar" class="topbar clearfix">
     <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/tlq/icon_bankuaishezhi.png">
		<span class="title">论坛版块设置</span>
	</div>
	<div class="fr right">
	    <input type="button"  value="保存" class="btn-win-white" onclick="doSaveOrUpdate();"/>
        <input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>
	</div>
  </div>
<!-- 表单内容 -->
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>">
<table class="TableBlock_page" >
	<tr>
		<td nowrap class="TableData" style="text-indent:15px" >版块名称：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<input type="text" name="sectionName" id="sectionName" size="80" class="BigInput" required />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px" >版主（最多5人）：<font style='color:red'>*</font></td>
		<td class="TableData" >
			<input name="manager" id="manager" type="hidden"/>
			<textarea name="managerName" id="managerName" class="SmallStatic BigTextarea"  style="overflow-y: auto;" rows="4" cols="70" wrap="yes" readonly="">
			</textarea>
			<span class='addSpan'>
				<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['manager','managerName'],'1')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('manager','managerName')" value="清空"/>
			</span>
			&nbsp;&nbsp;
			<br>
			<span style="margin:5px 0px;display: inline-block;">
			（1、有权限浏览该板块相关帖子&nbsp;&nbsp;2、在该板块中有权限新建帖子）</span>
				      		
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">可发帖人员 ：</td>
		<td class="TableData" >
			<input name="crPriv" id="crPriv" type="hidden"/>
			<textarea name="crPrivName" id="crPrivName" class="SmallStatic BigTextarea"  style="overflow-y: auto;" rows="4" cols="70" wrap="yes" readonly="">
			</textarea>
			<select id="crPrivAllFlag" name="crPrivAllFlag" class="BigSelect" onchange="crPrivChange(this.value);">
				<option value="1" selected="selected" >全体人员</option>
				<option value="0">指定人员</option>
			</select>
			
			<span class='addSpan' id="crPrivSpan" style="display: none;">
				<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['crPriv','crPrivName'],'1')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('crPriv','crPrivName')" value="清空"/>
			</span>

			&nbsp;&nbsp;
			<br>
			<span style="margin:5px 0px;display: inline-block;">
			   （1、全体人员：所有人员在有预览权限的情况下可以新建帖子&nbsp;&nbsp;2、指定人员：指定的人员在有预览权限的情况下可以新建帖子）
			</span>
				      			
			
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">版块可见人员 ：</td>
		<td class="TableData" >
			<input name="viewPrivId" id="viewPrivId" type="hidden"/>
			<textarea name="viewPrivName" id="viewPrivName" class="SmallStatic BigTextarea"  style="overflow-y: auto;" rows="4" cols="70" wrap="yes" readonly="">
			</textarea>
			<select id="viewPrivAllFlag" name="viewPrivAllFlag" class="BigSelect" onchange="viewPrivChange(this.value);" >
				<option value="1" selected="selected" >全体人员</option>
				<option value="0">指定人员</option>
			</select>
			
			<span class='addSpan' id="viewPrivSpan" style="display: none;">
				<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['viewPrivId','viewPrivName'],'1')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('viewPrivId','viewPrivName')" value="清空"/>
			</span>
			&nbsp;&nbsp;
			<br>
			<span style="margin:5px 0px;display: inline-block;">
			  （1、全体人员：该板块所有人员可见&nbsp;&nbsp;2、指定人员：该板块指定人员可见）
			</span>
				      			
			
		</td>
	</tr>
	
	<tr>
	  <td style="text-indent:15px">是否允许匿名发布：</td>
	  <td>
	     <input type="radio" name="anonymous" id="anonymous" value="1" checked="checked"/>是&nbsp;&nbsp;
	     <input type="radio" name="anonymous" id="anonymous1" value="0"/>否
	  </td>
	</tr>
	
	
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">新发贴时给可见人员发送系统消息 ：</td>
		<td class="TableData" >
			<input type="radio" name="newTopicSmsPriv" id="newTopicSmsPriv1" value="1" size="" > <label for="newTopicSmsPriv1">是 &nbsp;&nbsp;</label>
			<input type="radio" name="newTopicSmsPriv" id="newTopicSmsPriv2" value="0" size="" checked="checked"> <label for="newTopicSmsPriv2">否 &nbsp;&nbsp;</label>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">有跟贴时给楼主发送系统消息  ：</td>
		<td class="TableData" >
			<input type="radio" name="replySmsPriv" id="replySmsPriv1" value="1" size="" > <label for="replySmsPriv1">是 &nbsp;&nbsp;</label>
			<input type="radio" name="replySmsPriv" id="replySmsPriv2" value="0" size="" checked="checked"> <label for="replySmsPriv2">否 &nbsp;&nbsp;</label>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">排序 ：</td>
		<td class="TableData" >
			<input type="text" name="orderNo" id="orderNo" size="80" class="BigInput"  no_negative_number="true" required="true" value="999" maxlength="8">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent:15px">说明 ：</td>
		<td class="TableData" >
			<textarea rows="5" cols="83" id="remark" name="remark" class="BigTextarea" ></textarea>

		</td>
	</tr>
</table>
</form>
</body>

</html>