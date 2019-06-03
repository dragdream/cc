<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全文检索中心</title>
<style type="text/css">
.c1{
	width:600px;
	margin:0px auto;
	margin-top:100px;
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
	border:1px solid #b6b6b6;
	width:450px;
	height:30px;
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
}
.iput{
	border:0px;
	background:transparent;
	width:450px;
	height:30px;
	font-size:14px;
	color:gray;
}
</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
function doSearch(){
   var term=$("#term").val();
   if(term=="请输入关键字查询"){
	   term="";
   }
   if(term==""){
	   return;
   }
   window.location= "result.jsp?term="+encodeURI(term)+"&currentPage=1";
   
}
</script>
</head>
<body>
	<div class="c1">
		<img src="search_logo.png" />
	</div>
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