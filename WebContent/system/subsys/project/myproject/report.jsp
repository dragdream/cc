<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script src="/common/rangesliderjquery/js/jquery-1.11.0.min.js"></script> 
<script src="/common/rangesliderjquery/js/ion.rangeSlider.js"></script> 
<link rel="stylesheet" href="/common/rangesliderjquery/css/normalize.min.css" />
<link rel="stylesheet" href="/common/rangesliderjquery/css/ion.rangeSlider.css" />
<link rel="stylesheet" href="/common/rangesliderjquery/css/ion.rangeSlider.skinNice.css" id="styleSrc"/>

<title>项目汇报</title>
</head>

<script>
var uuid="<%=uuid%>";
function doInit(){
	//获取项目进度
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		$("#range").ionRangeSlider({
			min: 0,
			max: 100,
			from:data.progress,
			prettify:0,
			type: 'single',//设置类型
			step: 1,
			prefix: "",//设置数值前缀
			postfix: "%",//设置数值后缀
			prettify: true,
			hasGrid: true
		});
		
	}

}

function commit(){
	var url=contextPath+"/projectController/report.action";
	var progress=$('#range').val();
	var json=tools.requestJsonRs(url,{uuid:uuid,progress:progress});
	return json.rtState;
	
	/* if(json.rtState){
		parent.datagrid.datagrid("reload");
		return true;
	} */
}
</script>

<body onload="doInit()" style="background-color: #f2f2f2;overflow: hidden;">
   <div style="position:relative; width:250px;margin:0 auto;font-size:12px;margin-top: 20px">
       <input type="text" id="range" />
   <br/>
   </div>
</body>
</html>