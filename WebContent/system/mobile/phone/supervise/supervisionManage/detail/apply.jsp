<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  //任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
  //类型 1暂停  2恢复  3办结
  int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
  String title="";
  if(type==1){
	  title="暂停申请";
  }else if(type==2){
	  title="恢复申请";
  }else if(type==3){
	  title="办结申请";
  }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<link href="/system/mobile/phone/style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1"  >
    <input type="hidden" value="<%=supId%>" id="supId" name="supId"/>
    <input type="hidden" value="<%=type%>" id="type" name="type"/>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>申请内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <textarea rows="6" style="width: 550px;" name="content" id="content" placeholder="申请内容" ></textarea>
		</div>
	</div>
</form>	
</div>

<script>
var supId=<%=supId%>;
function save(){
	if(check()){
		var url=contextPath+"/supervisionApplyController/add.action";
		var param=formToJson("#form1");
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					 alert("申请成功！");
					 window.location="index.jsp?sid="+supId;
				}
			}
		});
	}
	
}

//验证
function check(){
	//获取内容
	var content=$("#content").val();
	if(content===null||content==""){
		alert("请填写申请内容！");
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