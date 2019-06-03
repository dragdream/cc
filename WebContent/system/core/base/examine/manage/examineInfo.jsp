<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>     
<%

	List<Map> list = (List<Map>)request.getAttribute("data");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<title >考核情况</title>

</head>

<body onload="">


	<% 
		if(list.size() > 0){
	%>
		<table class="TableList" width="100%">
			<tr class="TableHeader">
				<td>考核人员</td>
				<td>已考核人员</td>
				<td>未考核</td>
			</tr>
		
	<%
		for(int i =0; i < list.size(); i ++ ){
			Map map = list.get(i);
			String userName = (String)map.get("userName");//考核人
			List<TeePerson> noExaminePerson = (List<TeePerson>)map.get("noExamine");//未被考核人
			List<TeePerson> examinePerson = (List<TeePerson>)map.get("examine");//已被考核的
			String noExaminePersonNames = "";
			String examinePersonNames = "";
			//System.out.println(noExaminePerson)
		 	for(int j=0; j < noExaminePerson.size(); j++ ){
				noExaminePersonNames = noExaminePersonNames + noExaminePerson.get(j).getUserName() + ",";
			} 
			
			for(int j=0; j < examinePerson.size(); j ++ ){
				examinePersonNames = examinePersonNames + examinePerson.get(j).getUserName() + ",";
			} 
	%>
			
			<tr class="TableData">
				<td><%=userName %></td>
				<td><%=examinePersonNames %></td>
				<td><%=noExaminePersonNames %></td>
			</tr>
	<%
		}
	%>
		</table>
	<%
		}else{
	%>
	
	
	<%
		}
	%>
</body>
</html>


