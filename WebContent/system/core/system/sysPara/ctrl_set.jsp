<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>控件设置</title>
<script type="text/javascript">


function doInit(){
	var ntkoDom = getSysParamByNames("NTKO_DOM");
	$("#ntkoDom").val(ntkoDom[0].paraValue);
	
	var webSignOpen = getSysParamByNames("WEBSIGN_OPEN");
	$("#webSignOpen").val(webSignOpen[0].paraValue);
	
	var webSignVer = getSysParamByNames("WEBSIGN_VER");
	$("#webSignVer").val(webSignVer[0].paraValue);
	
	var aipVer = getSysParamByNames("AIP_VER");
	$("#aipVer").val(aipVer[0].paraValue);
}

/**
 * 保存
 */
function doSave(){
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"NTKO_DOM",paraValue:$("#ntkoDom").val()});
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"WEBSIGN_OPEN",paraValue:$("#webSignOpen").val()});
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"WEBSIGN_VER",paraValue:$("#webSignVer").val()});
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"AIP_VER",paraValue:$("#aipVer").val()});
	$.MsgBox.Alert_auto("保存成功");
}

</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/icon-kjsz.png" alt="">
	   &nbsp;<span class="title">控件设置</span>
	   
	   <button class="btn-win-white fr" onclick="doSave();">保存</button>	   
</div>

<form   method="post" name="form1" id="form1">
<table class="TableBlock_page" width="100%">
	<tr class="TableLine1">
		<td nowrap style="text-indent:10px">签章开关：</td>
		<td nowrap>
			<select id="webSignOpen" name="webSignOpen">
				<option value="0">关闭</option>
				<option value="1">开启</option>
			</select>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent:10px">签章版本：</td>
		<td nowrap>
			<select id="webSignVer" name="webSignVer">
				<option value="0">标准版</option>
				<option value="1">网络版</option>
			</select>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent:10px">AIP版本：</td>
		<td nowrap>
			<select id="aipVer" name="aipVer">
				<option value="0">标准版</option>
				<option value="1">网络版</option>
			</select>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent:10px">WebOffice节点描述：</td>
		<td nowrap>
			<textarea id="ntkoDom" class="BigTextarea" style="width:700px;height:200px;"></textarea>
		</td>
	</tr>
</table>
</form>
</body>
</html>