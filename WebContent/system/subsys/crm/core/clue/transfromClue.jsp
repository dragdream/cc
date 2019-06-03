<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0) ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>线索转换</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
		getInfoById(sid);
}


/* 查看详情 */
function getInfoById(sid){
	var url =   "<%=contextPath%>/TeeCrmClueController/getInfoBySid.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			$("#chanceName").val(prc.name);
		}
	} else {
		$.MsgBox.Alert(jsonObj.rtMsg);
	}
}


function checkForm(){
	var companyName = $("#companyName").val();
	if(companyName==""||companyName==null){
		parent.$.MsgBox.Alert_auto("请输入客户名称！");
		return false;	
	}
	var isChecked = $("#transContact").prop("checked");
	if(isChecked){
		$("#transContact").val("1");
		var name = $("#name").val();
		if(name==""||name==null){
			parent.$.MsgBox.Alert_auto("请输入联系人姓名！");
			return false;	
		}
	}else{
		$("#transContact").val("0");
	}
	//验证转换为商机必填
	var isChecked2 = $("#transChances").prop("checked");
	if(isChecked2){
		$("#transChances").val("1");
		var chanceName = $("#chanceName").val();
		var forcastTimeDesc = $("#forcastTimeDesc").val();
		var forcastCost = $("#forcastCost").val();
		var managePersonId = $("#managePersonId").val();
		if(chanceName==""||chanceName==null){
			parent.$.MsgBox.Alert_auto("请输入商机名称！");
			return false;	
		}
		if(forcastTimeDesc==""||forcastTimeDesc==null){
			parent.$.MsgBox.Alert_auto("请选择日期！");
			return false;	
		}
		if(forcastCost==""||forcastCost==null){
			parent.$.MsgBox.Alert_auto("请填写金额！");
			return false;	
		}
		if(managePersonId==""||managePersonId==null){
			parent.$.MsgBox.Alert_auto("请选择负责人！");
			return false;	
		}
	}else{
		$("#transChances").val("0");
	}
	return true;
}

/**
 * 保存数据
 */
function doSaveOrUpdate(callback){
  if(checkForm()){
    var url = "<%=contextPath %>/TeeCrmClueController/transfromClue.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
      callback(jsonRs.rtState);
    }else{
    	$.MsgBox.Alert(jsonRs.rtMsg);
    }
  }
}

</script>
<style>
html{
  background-color: #f2f2f2;
}

.TableBlock tr>td>textarea{
	margin:0;
}

</style>
</head>
<body onload="doInit();" style="overflow-x: hidden;padding: 10px;background-color: #f2f2f2;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<div class="title1" style="line-height:30px;">
    <input type="checkbox" id="transCustomer" name ="transCustomer" value="1" checked="checked" disabled="disabled"/>
    &nbsp;&nbsp;<span style="font-size: 14px;line-height: 14px;color: #0050aa;font-family: 'MicroSoft YaHei';">转换为客户</span>
    <p style="margin-right:20px;font-size:14px;color:#0050aa;cursor:pointer;" class="open1 open fr">收起</p>
</div>
<table align="center" width="100%" class="TableBlock table1 open" style="">
	<tr>
		<td style="text-indent:10px;width:155px;" nowrap class="TableData"><font style='color:red'>*</font>&nbsp;&nbsp;客户名称：</td>
		<td class="TableData" ">
		      <input required id="companyName" name="companyName" required style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData">&nbsp;&nbsp;&nbsp;公司地址：</td>
		<td class="TableData" " >
		      <input id="address" name="address" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData">&nbsp;&nbsp;&nbsp;电话：</td>
		<td class="TableData"" >
		      <input id="telephone" name="telephone" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
		<tr>
		<td style="text-indent:10px;" nowrap class="TableData">&nbsp;&nbsp;&nbsp;网址：</td>
		<td class="TableData" ">
		      <input id="url" name="url" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
</table>
<div class="title2" style='line-height:30px;margin-top: 10px;'>
    <input id='transContact' type="checkbox" name ="transContact"/>
    &nbsp;&nbsp;<span style="font-size: 14px;line-height: 14px;color: #0050aa;font-family: 'MicroSoft YaHei';">同时转换为联系人</span>
     <p style="margin-right:20px;font-size:14px;color:#0050aa;cursor:pointer;" class="open2 fr">展开</p>
</div>
<table  align="center" width="100%" class="TableBlock table2" style="display:none;">
    <tr>
		<td style="text-indent:10px;width:155px;" class="TableData" ><font style='color:red'>*</font>&nbsp;&nbsp;姓名：</td>
		<td class="TableData" >
		      <input required id="name" name="name" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData" >&nbsp;&nbsp;&nbsp;部门：</td>
		<td class="TableData" ">
		      <input id="department" name="department" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData" >&nbsp;&nbsp;&nbsp;职务：</td>
		<td class="TableData" >
		      <input id="duties" name="duties" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData" >&nbsp;&nbsp;&nbsp;手机：</td>
		<td class="TableData" >
		      <input id="mobilePhone" name="mobilePhone" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData">&nbsp;&nbsp;&nbsp;邮件：</td>
		<td class="TableData">
		      <input id="email" name="email" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
</table>
<div class="title3" style='line-height:30px;margin-top: 10px;'>
    <input id='transChances' type="checkbox" name ="transChances"/>
    &nbsp;&nbsp;<span style="font-size: 14px;line-height: 14px;color: #0050aa;font-family: 'MicroSoft YaHei';">同时转换为商机</span>
     <p style="margin-right:20px;font-size:14px;color:#0050aa;cursor:pointer;" class="open3 fr">展开</p>
</div>
<table  align="center" width="100%" class="TableBlock table3" style="display:none;">
    <tr>
		<td style="text-indent:10px;width:155px;" class="TableData" ><font style='color:red'>*</font>&nbsp;&nbsp;商机名称：</td>
		<td class="TableData" >
		      <input required id="chanceName" name="chanceName" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData" ><span style="color:red;font-weight:bold;">*</span>&nbsp;&nbsp;预计成交日期 ：</td>
		<td class="TableData" ">
		      <input required id="forcastTimeDesc" name="forcastTimeDesc"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData" ><span style="color:red;font-weight:bold;">*</span>&nbsp;&nbsp;金额：</td>
		<td class="TableData" >
		      <input required integer="true" id="forcastCost" name="forcastCost" style="font-family: MicroSoft YaHei; font-size: 12px;width: 300px;height: 23px;border: 1px solid #dadada;"></textarea>
		</td>
	</tr>
	<tr>
		 <td class="TableData" style="text-indent:10px"><span style="color:red;font-weight:bold;">*</span>&nbsp;&nbsp;负责人：</td>
			<td class="TableData">
				<input type="hidden" name="managePersonId" id="managePersonId"> 
				<input required name="managePersonName" id="managePersonName" style="width: 300px;height: 23px;border: 1px solid #dadada;"   class="BigInput" wrap="yes" readonly />
				   <span class='addSpan'>
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectSingleUser(['managePersonId', 'managePersonName'],'14')" value="选择"/>
				           &nbsp;&nbsp;
				           <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('managePersonId', 'managePersonName')" value="清空"/>
			       </span>
			</td>
	</tr>
</table>
</form>
</body>
<script>
	/* $("#form1").validate(); */
	$(".open1").click(function(){
		if($(this).hasClass("open")){
			$(this).removeClass("open");
			$(this).text("展开");
			$(".table1").hide();
		}else{
			$(this).addClass("open");
			$(this).text("收起");
			$(".table1").show();
		}
	});
	$(".open2").click(function(){
			if($(this).hasClass("open")){
				$(this).removeClass("open");
				$(this).text("展开");
				$(".table2").hide();
			}else{
				$(this).addClass("open");
				$(this).text("收起");
				$(".table2").show();
			}
	});	
	$(".open3").click(function(){
		if($(this).hasClass("open")){
			$(this).removeClass("open");
			$(this).text("展开");
			$(".table3").hide();
		}else{
			$(this).addClass("open");
			$(this).text("收起");
			$(".table3").show();
		}
     });	
	$("#transContact").click(function(){
		$(this).siblings(".fr").addClass("open");
		$(this).siblings(".fr").text("收起");
		$(".table2").show();
	});
	$("#transChances").click(function(){
		$(this).siblings(".fr").addClass("open");
		$(this).siblings(".fr").text("收起");
		$(".table3").show();
	});
	
	
</script>
</html>