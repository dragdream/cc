<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String sid = request.getParameter("sid");
sid = sid==null?"":sid;
%>
<!DOCTYPE HTML>
<html>
<head>
<title>日程</title>
<script>
var DING_APPID = "<%=com.tianee.oa.oaconst.TeeModuleConst.MODULE_SORT_DD_APP_ID.get("022")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=request.getContextPath() %>/common/js/jquery.form.min.js"></script>
<style type="text/css" media="all">
</style>

<script type="text/javascript">
var sid = "<%=sid%>";
var ocontent = "";
function doInit(){
	history.forward(1);
	if(sid!=""){
		$.ajax({
			  type: 'POST',
			  url: contextPath+"/mobileCalendarController/getById.action",
			  data: {sid:sid},
			  timeout: 10000,
			  success: function(json){
					json = eval('(' + json + ')');
					$("#calType").val(json.rtData.calType);
					$("#calLevel").val(json.rtData.calLevel);
					
					$("#calTime").val(json.rtData.calTime);
					$("#endTime").val(json.rtData.endTime);
					$("#content").val(json.rtData.content);
					$("#actorNames").val(json.rtData.actorNames);
					$("#actorIds").val(json.rtData.actorIds);
					//Alert(json.rtData.actorIds);
					//$("#actorNames").attr("readonly","readonly");
					/*  if(json.rtData.actorNames){
					  	  $("#actorIds").show();
					  	  $("#actorNames").append(json.rtData.actorNames);
					  }   */
			  },
			  error: function(xhr, type){
			    alert('服务器请求超时!');
			  }
			});
	}
	
	a1.addEventListener("tap",function(){
		if(sid!=""){
			window.location = "detail.jsp?sid="+sid;
		}else{
			window.location = "index.jsp";
		}
		
	});
	a2.addEventListener("tap",function(){
		commit();
	});
}

function commit(){
	
	if($("#calTime").val()==""){
		mui.toast('请选择起始时间');
		$("#calTime").focus();
		return;
	}
	if($("#endTime").val()==""){
		mui.toast('请选择结束时间');
		$("#endTime").focus();
		return;
	}
	if($("#content").val()==""){
		mui.toast('请填写日程内容');
		$("#content").focus();
		return;
	}
	
	mask.show();
	$('#form').ajaxSubmit({
		url:contextPath+"/mobileCalendarController/addOrUpdate.action",
		success:function(data){
			if(sid==""){
				window.location = "index.jsp";
			}else{
				window.location = "detail.jsp?sid="+sid;
			}
		},
		error:function(){
			mask.close();
		}
	});
}

var mask;
mui.ready(function(){
	mask = mui.createMask(function(){});//callback为用户点击蒙版时自动执行的回调
	picker = new mui.DtPicker({"type":"minute"});
});

var picker;

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
		 obj.value = rs.y.text+"-"+rs.m.text+"-"+rs.d.text+" "+rs.h.text+":"+rs.i.text;
	});
}

</script>


</head>
<body onload="doInit()">
<div id="muiContent" class="mui-content">
<form id="form" method="post">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>日程类型</label>
			<select style="font-size:14px;margin:0px;margin-top:3px;height:30px"  id="calType" name="calType">
				<option value="1">工作事务</option>
				<option value="2">个人事务</option>
			</select>
		</div>
		<div class="mui-input-row">
			<label>优先级</label>
			<select style="font-size:14px;margin:0px;margin-top:3px;height:30px"  id="calLevel" name="calLevel">
				<option value="0">未指定</option>
				<option value="1">重要/紧急</option>
				<option value="2">重要/不紧急</option>
				<option value="3">不重要/紧急</option>
				<option value="4">补重要/不紧急</option>
			</select>
		</div>
		<div class="mui-input-row">
			<label>起始时间</label>
			<input type="text" id="calTime" name="startDate" placeholder="选择起始时间" readonly onclick="setDate(this)"/>
		</div>
		<div class="mui-input-row">
			<label>结束时间</label>
			<input type="text" id="endTime" name="endDate" placeholder="选择结束时间" readonly onclick="setDate(this)"/>
		</div>
		
		<div class="mui-input-row">
			<label>参与人</label>
			<input type="hidden" id="actorIds" name="actorIds" placeholder="选择参与人" />
			<input type="text" id="actorNames" readonly name="actorNames" placeholder="选择参与人" onclick="selectUser('actorIds','actorNames');"/>
		</div>
		
		<div class="mui-input-row">
			<label>内容</label>
		</div>
		<div class="mui-input-row" style="height:100px">
			<textarea id="content" name="content" placeholder="请输入内容" ></textarea>
		</div>
	</div>
</div>
<input type="hidden" value="<%=sid%>" name="sid"/>
</form>
<!-- 底部操作栏 -->
<nav class="mui-bar mui-bar-tab">
	<a id="a1" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-undo"></span>
		<span  class="mui-tab-label" >返回</span>
	</a>
 	<a id="a2" class="mui-tab-item mui-active">
		<span class="mui-icon mui-icon-upload"></span>
		<span  class="mui-tab-label" >保存</span>
	</a>
</nav>
</body>
</html>