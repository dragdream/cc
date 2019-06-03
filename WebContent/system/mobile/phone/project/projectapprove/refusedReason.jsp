<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

    //项目主键
	String uuid = request.getParameter("uuid");
   
%>
<!DOCTYPE HTML>
<html>
<head>
<title>项目审批</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="commit()"></span>
	
	<h1 class="mui-title">拒绝理由</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>拒绝原因</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="refusedReason" id="refusedReason" placeholder="拒绝原因" ></textarea>
		</div>
	</div>
</form>	
</div>


<script>
//项目主键
var uuid="<%=uuid%>";
//初始化
mui.ready(function() {

		backBtn.addEventListener("tap",function(){
			history.go(-1);
		});//返回
		
		
		
	});

//保存
function commit(){
	//获取拒绝原因
	var refusedReason=$("#refusedReason").val();
	var url=contextPath+"/projectController/approveProject.action";
	if(window.confirm("是否确认拒绝该项目？")){
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{uuid:uuid,status:6,refusedReason:refusedReason},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("已拒绝！");
					window.location.href="projectDetail.jsp?uuid="+uuid+"&status=1";
				}else{
					alert("操作失败！");
				}
			}
		});	
	}
}

</script>
</div>

</body>
</html>