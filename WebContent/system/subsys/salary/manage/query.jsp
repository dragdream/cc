<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<title>员工自助查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script>
/**
 * 初始化
 */
function doInit(){
	querySalary();
}

function querySalary(){
	var url = contextPath+"/teeSalItemController/queryPersonalSalary.action";
	var json = tools.requestJsonRs(url,{});
	var accounts = json.rtData;
	var render = [];
	for(var i=0;i<accounts.length;i++){
		var account = accounts[i];
		var tableHeaderName = account.tableHeaderName;
		var valueList = account.valueList;
		
		render = ["<h4>"+account.accountName+"</h4><hr>"];
		render.push("<table class='TableBlock'  style='margin-top:5px;'>");
		render.push("<tr class='TableHeader'>");
		var headers = tableHeaderName.tableHeaderName.split(",");
		for(var n=0;n<headers.length;n++){
			if(n==0){
// 				render.push("<td nowrap ><input id='allbox_for' name='allbox' type='checkbox' onclick='checkAll(this)'/>全选</td>");
			}else{
				render.push("<td nowrap align='center' style='padding:5px;'>"+headers[n]+"</td>");
			}
		}
		
		render.push("<td nowrap align='center' style='padding:5px;'>操作</td>");
		render.push("</tr>");
		
		//数据行
		for(var j = 0;j<valueList.length;j++){
			render.push("<tr class='TableData'>");
			var values = valueList[j].split("*");
			for(var m=0;m<values.length;m++){
				if(m==0){
// 					render.push("<td nowrap width='50'><input name='deleteFlag' type='checkbox' value='"+values[m]+"'/></td>");
				}else{
					render.push("<td nowrap>"+values[m]+"</td>");
				}
			}
			render.push("<td nowrap><a href='detail.jsp?sid="+values[0]+"'>详情</a></td>");
			render.push("</tr>");
		}
		render.push("</table>");
		$("#dataList").html(render.join(""));
	}
	
	if(accounts.length==0){
		messageMsg("暂无工资发放信息","dataList","info");
	}
	
}

</script>

</head>
<body onload="doInit();">
<div class="base_layout_top">
	<span class="easyui_h1">工资自助查询</span>
</div>
<div class="base_layout_center" id='dataList' style="padding:10px">
	
</div>
</body>
</html>
