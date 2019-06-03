<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.tianee.oa.oaconst.TeeModuleConst"%>
<% 
String optType = request.getParameter("optType");
optType = optType==null?"":optType;
String sid = request.getParameter("sid");
sid = sid==null?"":sid;
%>
<!DOCTYPE HTML>
<html>
<head>
<title>写邮件</title>
<script>
var DING_APPID = "<%=TeeModuleConst.MODULE_SORT_DD_APP_ID.get("019")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=request.getContextPath() %>/common/js/jquery.form.min.js"></script>
<style type="text/css" media="all">
</style>

<script type="text/javascript">
var optType = "<%=optType%>";
var sid = "<%=sid%>";
var ocontent = "";
function doInit(){
	if(optType!=""){
		$.ajax({
		  type: 'POST',
		  url: contextPath+"/mobileEmailController/getById.action",
		  data: {sid:sid},
		  timeout: 10000,
		  success: function(json){
			  json = eval("("+json+")");
			  var data = json.rtData;
			  
			 if(optType=="0"){//回复
				 $("#userListIds").val(data.fromUserId);
				 $("#userListNames").val(data.fromUserName);
				 $("#subject").val("回复:"+data.subject);
				 SetTitle("回复");
			 }else{
				 $("#subject").val("转发:"+data.subject);
				 SetTitle("转发");
			 }
			 $("#content0").html(data.content);
			 ocontent = data.content;
		  },
		  error: function(xhr, type){
		    //alert('服务器请求超时!');
		  }
		});
	}
	
	a1.addEventListener("tap",function(){
		if(sid!=""){
			window.location = "emailInfo.jsp?sid="+sid;
		}else{
			window.location = "index.jsp";
		}
		
	});
	a2.addEventListener("tap",function(){
		commit();
	});
}

function commit(){
	
	if($("#userListIds").val()==""){
		mui.toast('请选择收件人');
		return;
	}
	if($("#subject").val()==""){
		mui.toast('请填写邮件主题');
		$("#subject").focus();
		return;
	}
	if($("#content").val()==""){
		mui.toast('请填写邮件内容');
		$("#content").focus();
		return;
	}
	
	mask.show();
	var content = $("#content").val();
	$("#content").val(content+"<p>---------原始邮件内容--------</p>"+ocontent);
	$('#form').ajaxSubmit({
		url:contextPath+"/mobileEmailController/addOrUpdate.action",
		success:function(data){
			window.location = "index.jsp";
		},
		error:function(){
			mask.close();
		}
	});
}

var mask;
mui.ready(function(){
	mask = mui.createMask(function(){});//callback为用户点击蒙版时自动执行的回调
});

</script>


</head>
<body onload="doInit()">
<div id="muiContent" class="mui-content">
<form id="form" enctype="multipart/form-data" method="post">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>收件人</label>
			<input type="hidden" id="userListIds" name="userListIds" placeholder="选择收件人" />
			<input type="text" id="userListNames" readonly name="userListNames" placeholder="选择收件人" onclick="selectUser('userListIds','userListNames');"/>
		</div>
		<div class="mui-input-row">
			<label>抄送人</label>
			<input type="hidden" id="copyUserListIds" name="copyUserListIds" placeholder="选择抄送人" />
			<input type="text" id="copyUserListNames" readonly name="copyUserListNames" placeholder="选择抄送人"  onclick="selectUser('copyUserListIds','copyUserListNames');"/>
		</div>
		<div class="mui-input-row">
			<label>外部邮箱</label>
			<input type="text" id="externalInput" name="externalInput" placeholder="外部邮箱（选）" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>主题</label>
			<input type="text" id="subject" name="subject" placeholder="请输入主题" />
		</div>
		<div class="mui-input-row">
			<label>内容</label>
		</div>
		<div class="mui-input-row" style="height:100px">
			<textarea style="height:100%;width:100%" id="content" name="content" placeholder="请输入内容" ></textarea>
		</div>
	</div>
</div>
<div class="mui-input-group">
	<div class="mui-input-row">
		<label>原内容</label>
	</div>
	<div id="content0" class="mui-input-row" style="overflow:auto;padding:10px;height:200px">
		
	</div>
</div>
<br/>
<br/>
<br/>
<input type="hidden" value="<%=optType%>" name="optType"/>
</form>
<!-- 底部操作栏 -->
<nav class="mui-bar mui-bar-tab">
	<a id="a1" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-undo"></span>
		<span  class="mui-tab-label" >返回</span>
	</a>
 	<a id="a2" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-upload"></span>
		<span  class="mui-tab-label" >发送</span>
	</a>
</nav>
</body>
</html>