<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    //反馈主键
    int fbId=TeeStringUtil.getInteger(request.getParameter("fbId"),0);
	//任务主键
	int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
	String option=TeeStringUtil.getString(request.getParameter("option"),"wjs");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>反馈回复</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<link href="/system/mobile/phone/style/style.css" rel="stylesheet" type="text/css" />
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	<h1 class="mui-title">反馈回复</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1"  >
    <input type="hidden" name="feedBackId" value="<%=fbId %>" id="feedBackId"/>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回复内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="content" id="content" placeholder="回复内容" ></textarea>
		</div>
	</div>
</form>	
</div>


<script>
var supId=<%=supId%>;

var option="<%=option%>";
//验证
function check(){
	var content=$("#content").val();
	if(content==""||content==null){
		alert("请填写反馈内容！");
		return false;
	}
	return true;
}



//保存/提交
function save(){
	if(check()){
		
		var url=contextPath+"/supFeedBackReplyController/addOrUpdate.action";
			var param=formToJson("#form1");
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
				   alert("回复成功！");
				   window.location="feedBackRecords.jsp?supId="+supId+"&&option="+option;
				}
			}
		});
   }	
}





mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
	   window.location="feedBackRecords.jsp?supId="+supId+"&&option="+option;
	});
});


</script>

</body>
</html>