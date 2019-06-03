<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<% 
	String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "0");
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>反馈详情</title>
		<style>
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:90%;
    margin-left: 65px;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}
</style>
	<script type="text/javascript" charset="UTF-8">
	
	var contextPath = '<%=contextPath%>';
	$(function() {
		tools.requestJsonRs(contextPath+"/doc/getDocViewListByRunId.action?uuid=<%=uuid%>",{},true,function(json){
			var render = [];
			var list = json.rows;
			if(list.length>0){
				$("#tableList").show();
			render.push("<tr>");
			render.push("<td class='TableData' width='30%'>接收人</td>");
			render.push("<td class='TableData' width='30%'>接收时间</td>");
			render.push("<td class='TableData' width='30%'>状态</td>");
			render.push("</tr>");
				for(var i=0;i<list.length;i++){
					render.push("<tr>");
					render.push("<td class='TableData'>"+list[i].recUserName+"</td>");
					
					if(list[i].recTime!=null){
						render.push("<td class='TableData'>"+getFormatDateStr(list[i].recTime,"yyyy-MM-dd HH:mm")+"</td>");
					}else{
						render.push("<td class='TableData'></td>");
					}
					
					if(list[i].flag==0){
						render.push("<td class='TableData'><span style='color:red'>未接收</span></td>");
					}else if(list[i].flag==1){
						render.push("<td class='TableData'><span style='color:blue'>已接收</span></td>");
					}
					
					render.push("</tr>");
				}
				$("#tbody").html(render.join(""));
			}else{
				messageMsg("无相关数据！","remindDiv","info");
			}
			
		});
	});

	</script>
</head>
<body>
<br/>
	<table id="tableList" align="center">
			
		<tbody id="tbody">
			
		</tbody>
	</table>
	<div id="remindDiv"></div>
</body>
</html>