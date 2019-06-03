<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //获取问题的主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>项目问题</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	<h1 class="mui-title">问题办理</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>处理结果汇报</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="8" cols="60" name="result" id="result" placeholder="处理结果" ></textarea> 
		</div>
	</div>
	
	
</form>	
</div>


<script>
var sid=<%=sid%>;
//验证
function check(){
	var result=$("#result").val();
	if(result==""||result==null){
		alert("请填写处理结果！");
		return false;
	}
	return true;
}


//保存
function save(){
	if(check()){
		var result=$("#result").val();
		var url=contextPath+"/projectQuestionController/handle.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{sid:sid,result:result},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					history.go(-1);
				}	
			}
		});	
		
	}

}


mui.ready(function() {
	
	backBtn.addEventListener("tap",function(){
		history.go(-1);
	});//返回
	
});
</script>


</body>
</html>