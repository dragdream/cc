<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<title>查看阅读情况</title>
<script type="text/javascript">
var id = <%=id%>;
function doInit(){
	var url = "<%=contextPath %>/teeNewsController/getNewsLookDatail.action?id="+id;
	//var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var rtData = jsonRs.rtData;
		var targetDom = $("#tbody");
		targetDom.empty();
		var trTem = '<tr>'+
			'<td nowrap class="TableData">{stateDesc}</td>'+
			'<td nowrap class="TableData">{userName}</td>'+
			'<td nowrap class="TableData">{deptName}</td>'+
			'<td nowrap class="TableData">{roleName}</td>'+
		'</tr>';
		
		var liArray = new Array();
		if(rtData){
			$.each(rtData,function(key, val){	
					var str = FormatModel(trTem,val);
					liArray.push(str);
			});
		}
		targetDom.html(liArray.join(''));
		
		//$("#tbody").append(groupStr);
	}else{
		alert(jsonRs.rtMsg);
	}
}
/**
 * 清空阅读情况
 */
function clearReadInfo(){
	var url = "<%=contextPath %>/teeNewsController/clearReadInfo.action?id="+id;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		$.jBox.tip("清空成功" , 'info' ,{timeout:1500});
		doInit();
	}else{
		alert(jsonRs.rtMsg);
	}
}
</script>
</head>

<body onload="doInit()" style="padding-top: 10px;">
	<center>
	<input type="button" class="btn btn-primary" value="清空阅读" onclick="clearReadInfo()"/>
	</center>
	<div style="padding-top: 10px;">
		<table class="TableBlock" width="90%" align="center">
			<thead>
				<tr class="TableHeader">
					<td>阅读情况</td>
					<td>人员</td>
					<td>部门</td>
					<td>角色</td>
				</tr>
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>
	</div>
</body>
</html>