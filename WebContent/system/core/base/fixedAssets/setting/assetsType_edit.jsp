<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/validator2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style>
.ztree{
margin-top:0;
height:200px; 
width:150px; 
display:none;
background:white;
border:1px solid gray;
overflow:auto;
}

</style>
<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeFixedAssetsCategoryController/getAssetsType.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}
function commitInfo(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeFixedAssetsCategoryController/editAssetsType.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg);
			location.href=contextPath+"/system/core/base/fixedAssets/setting/assetsType.jsp";
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

</script>

</head>
<body onload="doInit();" style="font-size:12px;overflow: hidden;">
<div id="toolbar" class = "setHeight clearfix" style="margin-bottom: 10px;margin-top: 10px;">
  <div class=" fl">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;margin-left: 10px;">
				 编辑类型
				</h4>
  </div>
  <!-- <div class="left fr">
	 	    <input type="button" value="返回" class="btn-win-white" title="返回" onClick="history.go(-1);"> 
	      		&nbsp;&nbsp;
			<input type="button" id="sub_pub" value="发布" class="btn-win-white" title="发布" onclick="doSave(1)" />&nbsp;&nbsp;
	        <input type="button" id="sub_save" value="保存" class="btn-win-white" title="保存" onclick="doSave(0)" />
  </div>  -->
   <span class="basic_border" style="margin-top: 10px;"></span>
</div>
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock_page">
		<tr>
			<td style="text-indent: 10px;width: 130px;" class="TableData ">
				排序号：
				</td>
			<td colspan="3" class="TableData">
				<input type='text' required id="sortNo" name="sortNo" style="width:100px;height: 25px;font-family: MicroSoft YaHei;" positive_integer="true"  maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 10px;" class="TableData ">
				资产类别名称：
				</td>
			<td colspan="3" class="TableData">
				<input type='text' id="name" name="name" style="width:300px;height: 25px;font-family: MicroSoft YaHei;"required/>
			</td>
		</tr>
		<tr>
			<td colspan='4' align="center" style="text-align:center;line-height: 30px;">
				<input style="width:45px;height:25px;" type='button' id="addType" name="addType" onclick="commitInfo();" value="保存" class="btn-win-white">&nbsp;&nbsp;
				<input type='hidden' class="BigInput" id="sid" name="sid" value="<%=sid %>>"/>
				<input style="width:45px;height:25px;" type="reset" id="reset" name="reset"  value="重填" class="btn-win-white">&nbsp;&nbsp;
				<input style="width:45px;height:25px;" type="button" id="back" name="back"  value="返回" onclick="history.go(-1);" class="btn-win-white">&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</form>
<br/><br/>

</body>
</html>