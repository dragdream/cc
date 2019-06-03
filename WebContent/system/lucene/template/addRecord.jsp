<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tianee.lucene.TeeLuceneApiExecutor"%>
<%@page import="com.tianee.lucene.entity.*"%>
<%@page import="java.util.*"%>

<%
//创建单条索引数据

//创建执行对象，构造参数需要传入ip地址和key密钥
TeeLuceneApiExecutor executor = new TeeLuceneApiExecutor("http://127.0.0.1","C5F06742ESAD234DBBHG");

//创建记录
Map record = new HashMap();
record.put("title", "这是标题");
record.put("content", "这是文章");

//执行添加索引记录的方法，test为索引库名称，record为单条记录
boolean success = executor.addRecord("test", record);

if(success){
	out.print("创建索引成功");
}else{
	out.print("失败！请联系管理员");
}

%>