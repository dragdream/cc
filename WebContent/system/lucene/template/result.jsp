<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tianee.lucene.TeeLuceneApiExecutor"%>
<%@page import="com.tianee.lucene.entity.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";

	//当前页码
	int currentPage=Integer.parseInt(request.getParameter("currentPage"));
	//指定检索库名称
	String tableName = "pubnetdisk";//此处pubnetdisk为公共网盘的检索库名称
	//关键字
	String term = request.getParameter("term");
	
	//创建检索条件对象
	SearchModel model=new SearchModel();
	model.setCurPage(currentPage);//设置当前页码
	model.setPageSize(20);//设置一页显示多少条
	model.setSpace(tableName);//设置检索库
	model.setTerm(term);//设置检索关键词
	model.setDefaultSearchField(new String[]{"title","body"});//设置默认检索字段，也就是关键词在这些字段中出现就算命中
	model.setLightedField(new String[]{"body","title"});//设置被高亮显示的字段
	model.setReturnField(new String[]{"title","body","attachSid","fileNetDiskSid"});//设置所返回的字段
	model.setReturnFieldWordCount(new int[]{100,300});//设置所返回字段的字数限制，title取前100个文字，body取前300个文字
	
	//创建执行对象，构造参数需要传入ip地址和key密钥
	TeeLuceneApiExecutor executor = new TeeLuceneApiExecutor("http://127.0.0.1","C5F06742ESAD234DBBHG");
	//执行queryParserSearch方法，返回结果集
	DocumentRecords documentRecords = executor.queryParserSearch(model);
	

	//计算总页数
	int totalPages=0;
	if(documentRecords.getTotalHits()%model.getPageSize()==0){
		totalPages=documentRecords.getTotalHits()/model.getPageSize();
	}else{
		totalPages=documentRecords.getTotalHits()/model.getPageSize()+1;
	}
%>
<title>检索结果</title>
<link type="text/css" rel="stylesheet" href="js/bootstrap.min.css"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqPaginator.js"></script>
<script type="text/javascript">
var term='<%=term%>';

function doSearch(){
   var term=$("#term").val();
   if(term=="请输入关键字查询"){
	   term="";
   }
   if(term==""){
	   window.location= "search.jsp";
	   return;
   }
   window.location= "result.jsp?term="+encodeURI(term)+"&currentPage=1";
   
}

$(function(){
	try{
		$.jqPaginator('#pagination1', {
		    totalPages: <%=totalPages%>,
		    visiblePages: 10,
		    currentPage: <%=model.getCurPage()%>,
		    onPageChange: function (num, type) {
		    	window.location.href= contextPath+"/system/lucene/result.jsp?&term="+encodeURI('<%=term%>')+"&currentPage="+num; 
		    }
		});
	}catch(e){
		
	}
});
</script>

<style>
.inputWrap{
	border:1px solid #b6b6b6;
	width:400px;
	height:30px;
	position:relative;
	display:inline-block;
	overflow:hidden;
	padding:0px;
	margin:0px;
	vertical-align:top;
}
.iput{
	border:0px;
	background:transparent;
	width:400px;
	height:30px;
	font-size:14px;
	color:gray;
}
.btnWrap{
	width:80px;
	height:30px;
	display:inline-block;
	vertical-align:top;
	margin:0px;
	padding:0px;
	background:#3385ff;
	border:1px solid #2d78f4;
}
.btn{
	border:1px solid #b6b6b6;
	padding:0px;
	margin:0px;
	height:100%;
	width:100%;
	border:0px;
	background:transparent;
	color:white;
}
.c1{
	font-size:12px;
	font-color:#000;
	margin-bottom:20px;
	margin-top:20px;
}

</style>



</head>
<body>

<table style="width:800px;margin:0px auto;margin-top:15px;font-size:0px">
	<tr>
		<td>
			<img src="search_logo.png" />
		</td>
		<td>
			<div>
				<span class="inputWrap">
					<input onkeydown="if (event.keyCode == 13){doSearch();}" type="text" class="iput" name="term" id="term" value="<%=term%>"/>
				</span>
				<span class="btnWrap">
					<button type="button" class="btn" onclick="doSearch()">搜 索</button>
				</span>
			</div>
		</td>
	</tr>
</table>

<div style="width:800px;margin:auto;font-size:12px;text-align:right">
	<a href="search.jsp">返回首页</a> -- 搜索到&nbsp;<%=documentRecords.getTotalHits() %>&nbsp;条记录
</div>

<div style="width:800px;margin:auto;margin-top:20px;margin-bottom:20px">
   <!-- 循环遍历结果集 -->
   <c:forEach var="result" items="<%=documentRecords.getRecordList()%>">
	   <div class="c1">
	    <p style="cursor:pointer;font-size:14px;color:blue" title="${result.title }" attachId="${result.attachSid }" fileNetDiskSid="${result.fileNetDiskSid }">${result.title}</p>
		<p>${result.body}...</p>
	   </div>
	</c:forEach>
</div>

<!-- 分页组件 -->
<div align="right" style="width:800px;margin:auto">
   <p id="p1"></p>
   <ul class="pagination" id="pagination1"></ul>
</div>

</body>
</html>