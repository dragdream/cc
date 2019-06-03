<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>任务催办</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<link href="/system/mobile/phone/style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	
	<h1 class="mui-title">任务催办</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1"  >
 <input type="hidden" name="supId" id="supId" value="<%=supId %>" />
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>催办内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <textarea rows="6" style="width: 550px;" name="content" id="content" placeholder="催办内容" ></textarea>
		</div>
	</div>
	
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>包含下级任务</label>
		</div>
		<div class="mui-input-row" >
		   <div style="padding-left: 15px">
		      <input type="radio" name="isIncludeChildren" value="1"/>是
              &nbsp;&nbsp;&nbsp;
              <input type="radio" name="isIncludeChildren" value="0" checked="checked"/>否
		   </div>    
		</div>
	</div>
</form>	
</div>

<script>
var supId=<%=supId%>;//任务主键

//提交
function save(){
	if(check()){
		var url=contextPath+"/supUrgeController/add.action";
		var param=formToJson("#form1");
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					 alert("催办成功！");
					 window.location="index.jsp?sid="+supId;
				}
			}
		});
	}
}


//验证
function check(){
	var content=$("#content").val();
	if(content==""||content==null){
		alert("请填写催办内容！");
		return false;
	}
	return true;
}
	
mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){		
	   history.go(-1);	
	});
	
});
</script>

</body>
</html>