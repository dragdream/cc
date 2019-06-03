<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);//线索主键
   int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
%>
<!DOCTYPE HTML>
<html>
<head>
<title>线索跟进</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn" onclick="history.go(-1)"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="commit()"></span>
	
	<h1 class="mui-title">已联系继续跟进</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>处理结果</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="dealResult" id="dealResult" placeholder="处理结果" ></textarea>
		</div>
	</div>
</form>
</div>

<script>
var sid="<%=sid%>"; 
var type="<%=type%>"; 
//验证
function checkForm(){
	var dealResult = $("#dealResult").val();
	if(dealResult==""||dealResult==null){
		alert("请输入处理结果！");
		return false;	
		}
	return true;
}


//保存
function commit() {
	if (checkForm()) {
		var url=contextPath+"/TeeCrmClueController/followUpClue.action?sid="+sid;
		var param = formToJson("#form1");
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if (json.rtState) {
					alert("操作成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/clue/clueInfo.jsp?sid="+sid+"&type="+type;
				}
			}
		});

	}
}

mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(sid>0){
			history.go(-1);
		}else{
			window.location.href=contextPath+"/system/mobile/phone/crm/clue/index.jsp?type="+type;
		}
	});
	
});

</script>


</body>
</html>