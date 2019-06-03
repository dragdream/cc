<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tianee.lucene.TeeLuceneApiExecutor"%>
<%@page import="com.tianee.lucene.entity.*"%>
<%@page import="java.util.*"%>

<%
//清空索引库所有数据

//创建执行对象，构造参数需要传入ip地址和key密钥
TeeLuceneApiExecutor executor = new TeeLuceneApiExecutor("http://127.0.0.1","C5F06742ESAD234DBBHG");

//清空执行索引库所有数据
boolean success = executor.clear("test");

if(success){
	out.print("清空成功");
}else{
	out.print("失败！请联系管理员");
}

%>