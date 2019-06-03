<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
function commit(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeFixedAssetsCategoryController/addAssetsType.action";
		var json = tools.requestJsonRs(url,param);
		$.MsgBox.Alert_auto(json.rtMsg);
		if(json.rtState){
			goBack();
			return;
		}
	}
}

function goBack(){
	window.location = "assetsType.jsp";
}
</script>

</head>
<body style="font-size:12px;overflow: hidden;">
<div id="toolbar" class = "setHeight clearfix" style="margin-bottom: 10px;margin-top: 10px;">
  <div class=" fl">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;margin-left: 10px;">
				 添加类型
				</h4>
  </div>
   <span class="basic_border" style="margin-top: 10px;"></span>
</div>
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock_page">
		<tr>
			<td style="text-indent: 10px;width: 130px;" class="TableData ">
				排序号：
				</td>
			<td colspan="3" class="TableData">
				<input type='text' id="sortNo" name="sortNo" style="width:100px;height: 25px;font-family: MicroSoft YaHei;" positive_integer="true" required maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent: 10px;" class="TableData ">
				资产类别名称：
				</td>
			<td colspan="3" class="TableData">
				<input type='text' id="name" name="name" style="width:300px;height: 25px;font-family: MicroSoft YaHei;" required/>
			</td>
		</tr>
		<tr>
			<td colspan='4' align="center" style="text-align:center;line-height: 30px;">
				<input style="width:45px;height:25px;" type='button' id="addType" name="addType" onclick="commit()" value="保存" class="btn-win-white">&nbsp;&nbsp;
				<input style="width:45px;height:25px;" type="button" id="back" name="back"  value="返回" onclick="goBack()" class="btn-win-white">
			</td>
		</tr>
	</table>
</form>
</body>
</html>