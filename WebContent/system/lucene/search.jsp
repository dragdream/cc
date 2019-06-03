<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute("LOGIN_USER");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>全文检索中心</title>
<style type="text/css">
.c1{
	width:600px;
	margin:0px auto;
	margin-top:200px;
	text-align:center;
}
.c2{
	width:600px;
	margin:0px auto;
	text-align:center;
	font-size:12px;
}
.c3{
	width:600px;
	margin:0px auto;
	text-align:center;
	font-size:0px;
	margin-top:15px;
}
.inputWrap{
	width:450px;
	height:32px;
	position:relative;
	display:inline-block;
	overflow:hidden;
	padding:0px;
	margin:0px;
	vertical-align:top;
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
	font-family:"Microsoft YaHei";
	cursor:pointer;
}
.iput{
	border:0px;
	background:transparent;
	padding:0 0 0 10px;
	width:450px;
	height:30px;
	font-size:14px;
	color:gray;
	font-family:"Microsoft YaHei";
}
</style>
<script type="text/javascript">
function doSearch(){
   var space=$('input:radio:checked').val();
   var term=$("#term").val();
   if(term=="请输入关键字查询"){
	   term="";
   }
   if(term==""){
	   return;
   }
   window.location= "result.jsp?space="+space+"&term="+encodeURI(term)+"&currentPage=1";
   
}
</script>
</head>
<body>
	<%
		if(TeePersonService.checkIsAdminPriv(loginPerson)){
			%>
			<div style="position:absolute;left:0px;right:50px;top:10px;height:30px;text-align:right;font-size:12px;">
				<img style='vertical-align: middle;' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/qwjszx/iocn_sysz.png" />&nbsp;&nbsp;<a style='vertical-align: middle;' href="create.jsp">索引管理</a>
			</div>
			<%
		}
	%>
	<div class="c1">
		<img style="vertical-align: bottom;" src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/qwjszx/icon_qwjszx.png" /><span style="line-height: 30px;font-size: 18px;">&nbsp;&nbsp;全文检索中心</span>
		&nbsp;&nbsp;&nbsp;&nbsp;<input id="ggwp" name="space" class="radio" type="radio" value="pubnetdisk" checked="checked"><label for="ggwp">&nbsp;&nbsp;公共网盘</label>
	</div>
	<br />
	<br />
	<div class="c3">
		<span class="inputWrap">
			<input type="text" onkeydown="if (event.keyCode == 13){doSearch();}" class="iput" name="term" id="term" 
			onblur="if(this.value == '')this.value='请输入关键字查询';"
			onfocus="if(this.value == '请输入关键字查询')this.value='';" value="请输入关键字查询"/>
		</span>
		<span class="btnWrap">
			<button type="button" class="btn" onclick="doSearch()">搜 索</button>
		</span>
	</div>
</body>
</html>