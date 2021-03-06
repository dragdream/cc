<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
String cityCodeStr = TeeStringUtil.getString(request.getParameter("cityCodeStr"), "0");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建或编辑县</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/setting/chinaCity/js/chinaCity.js"></script>
<script type="text/javascript">
function doInit(){
	var sid = "<%=sid%>";
	if(sid>0){
		getInfoById(sid);
	}else{
		getAutoNumber('<%=cityCodeStr%>','3','cityCodeStart');
	}
	$("#cityName").focus();
}



/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/chinaCityController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var cityCode = prc.cityCode;
			if(cityCode){
				var cityCodeStart = cityCode.substring(4,6);
				$("#cityCodeStart").val(cityCodeStart);
			}
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}




/**
 * 新建或编辑
 */
function doSaveOrUpdate(callback){
	if(checkForm()){
		var url = "<%=contextPath %>/chinaCityController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			callback();
		}else{
			alert(jsonRs.rtMsrg);
		}
	}
}


function checkForm(){
	var cityCodeTemp = $("#cityCodeTemp").val();
	var cityCodeStart = $("#cityCodeStart").val();
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	var checkFlag = checkCityCode(cityCodeTemp + cityCodeStart);
	if(checkFlag==1){
		return false;
	}
	$("#cityCode").val(cityCodeTemp + cityCodeStart);
	return true;
}

/**
 * 校验城市代码
 */
 function checkCityCode(cityCode){
		var url = "<%=contextPath %>/chinaCityController/checkCityCode.action?sid=<%=sid%>";
		var para = {cityCode:cityCode};
		var jsonRs = tools.requestJsonRs(url,para);
		var checkFlag= 0;
		if(jsonRs.rtState){
			checkFlag = jsonRs.rtData.countFlag;
		}else{
			alert(jsonRs.rtMsrg);
		}
		return checkFlag;
	}

function checkCityCodefunc(){
	var cityCodeTemp = $("#cityCodeTemp").val();
	var cityCodeStart = $("#cityCodeStart").val();
	if(cityCodeStart.length<2){
		$.jBox.tip("请输入两位整数！", "info", {timeout: 1800});
		$("#cityCodeStart").focus();
	}
	var cityCode = cityCodeTemp + cityCodeStart;
	var checkFlag = checkCityCode(cityCode);
	if(checkFlag==1){
		$("#cityCodeSpan").html("<font color='red'>该县编号已经存在！</font>");
		$("#cityCodeStart").focus();
	}else{
		$("#cityCodeSpan").text("");
	}
}

</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="90%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" >县编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="hidden" name="cityCode" id ="cityCode" value=""/>
			
			<input type="text" name="cityCodeTemp" id="cityCodeTemp" class="BigInput  easyui-validatebox" disabled="disabled" size="2" value="<%=cityCodeStr.substring(0,4) %>"  maxlength="2">
			<input type="text" name="cityCodeStart" id="cityCodeStart" size="2" class="BigInput  easyui-validatebox" validType ='integeZero[]' required="true" value=""  maxlength="2" onblur="checkCityCodefunc();">
			<span id="cityCodeSpan"></span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >县名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="cityName" id="cityName" size="" class="BigInput  easyui-validatebox" required="true" value="" maxlength="150">
		</td>
	</tr>
</table>
</form>





</body>
</html>