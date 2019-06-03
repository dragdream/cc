<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String sid = request.getParameter("sid");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签阅情况</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var sid = '<%=sid%>';
function doInit(){
	getInfoTable(sid);
}

/* 获取数据列表 */
function getInfoTable(sid){
	
	var url = "<%=contextPath%>/teeFileOptRecordController/getHistorys.action";
	var para = {sid:sid};
	var jsonObj = tools.requestJsonRs(url,para);
	var prcs = jsonObj.rows;
	var counter = 0;
		if(prcs.length>0){
			$("#tableList").append("<table id='tbody'  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9'>"
					+"<td style='text-indent:5px'>序号</td>"
					+"<td >操作人</td>"
					+"<td >操作类型</td>"
					+"<td >操作内容</td>"
					+"<td >操作时间</td>"
		  		+ "</tr>"); 
			jQuery.each(prcs,function(i,sysPara){
				counter++;
				var optType = "";
				if(sysPara.optType =='1'){
					optType="新建";
				}else if(sysPara.optType =='2'){
					optType="下载";
				}else if(sysPara.optType =='3'){
					optType="重命名";
				}else if(sysPara.optType =='4'){
					optType="删除";
				}else if(sysPara.optType =='5'){
					optType="复制";
				}else if(sysPara.optType =='6'){
					optType="移动";
				}else if(sysPara.optType =='7'){
					optType="签阅";
				}else if(sysPara.optType =='8'){
					optType="编辑";
				}
			$("#tbody").append("<tr class='TableData'>"
								+"<td width='40px' style='text-indent:5px'>" + counter + "</td>"
								+"<td width='80px'>" + sysPara.userName+ "</td>"
								+"<td width='80px'>" + optType + "</td>"
								+"<td width='200px' style='padding-right:15px'>" + sysPara.optContent + "</td>"
								+"<td width='110px'>" + sysPara.createTimeStr+ "</td>"
					  		+ "</tr>"); 
			});
			$("#tableList").append("</table>");
		}else{
			messageMsg("无相关数据！","tableList","info");
		}
}


</script>
</head>
<body onload="doInit();" style="padding-top: 10px;background-color: #f2f2f2">

<div style="padding: 10px;">
		<div id="tableList"></div>
</div>

<br>

</body>
</html>