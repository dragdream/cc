<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String sid = request.getParameter("sid");
sid = sid==null?"":sid;
%>
<!DOCTYPE HTML>
<html>
<head>
<title>撰写日志</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_DIARY_APPID")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=request.getContextPath() %>/common/js/jquery.form.min.js"></script>
<style type="text/css" media="all">
</style>

<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	
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
	
	if($("#title").val()==""){
		mui.toast('请填写标题');
		return;
	}
	if($("#writeTimeDesc").val()==""){
		mui.toast('请填写撰写时间');
		$("#writeTimeDesc").focus();
		return;
	}
	if($("#content").val()==""){
		mui.toast('请填写内容');
		$("#content").focus();
		return;
	}
	
	mask.show();
	$('#form').ajaxSubmit({
		url:contextPath+"/mobileDiaryController/addOrUpdate.action",
		success:function(data){
			window.location = "index.jsp";
		},
		error:function(){
			mask.close();
		}
	});
}

var mask;
var picker;
mui.ready(function(){
	mask = mui.createMask(function(){});//callback为用户点击蒙版时自动执行的回调
	picker = new mui.DtPicker({"type":"date"});
});

function setDate(obj){
	 picker.show(function(rs) {
		/*
		 * rs.value 拼合后的 value
		 * rs.text 拼合后的 text
		 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
		 * rs.m 月，用法同年
		 * rs.d 日，用法同年
		 * rs.h 时，用法同年
		 * rs.i 分（minutes 的第二个字母），用法同年
		 */
		 obj.value = rs.y.text+"-"+rs.m.text+"-"+rs.d.text;
	});
}

</script>


</head>
<body onload="doInit()">
<div id="muiContent" class="mui-content">
<form id="form" enctype="multipart/form-data" method="post">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>标题</label>
			<input type="text" id="title" name="title" placeholder="请输入标题" />
		</div>
		<div class="mui-input-row">
			<label>类型</label>
			<select style="font-size:14px;margin:0px;margin-top:3px;height:30px"  id="type" name="type">
				<option value="1">工作日志</option>
				<option value="2">个人日志</option>
			</select>
		</div>
		<div class="mui-input-row">
			<label>撰写时间</label>
			<input type="text" id="writeTimeDesc" readonly name="writeTimeDesc" placeholder="点击选择撰写时间" onclick="setDate(this)"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>内容</label>
		</div>
		<div class="mui-input-row" style="height:150px">
			<textarea id="content" name="content" placeholder="请输入内容" ></textarea>
		</div>
	</div>
</div>
<br/>
<br/>
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