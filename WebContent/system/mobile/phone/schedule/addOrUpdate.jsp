<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String scheduleId = request.getParameter("scheduleId");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>计划</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="window.location = 'index.jsp'">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='save()'>
	    保存
	</button>
	<%
		if(null==scheduleId || scheduleId.equals("0") || scheduleId.equals("")){
	%>
	<h1 class="mui-title">新建计划</h1>
	<%
		}else{
			
	%>
	<h1 class="mui-title">编辑计划</h1>
	<%
		}
	%>
</header>
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<input type="text" placeholder="计划名称" name="title" id="title">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划类型</label>
		</div>
		<div class="mui-input-row">
			<select style="font-size:14px"  id="type" name="type">
				<option value="1">个人计划</option>
				<option value="2">部门计划</option>
				<option value="3">公司计划</option>
			</select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划周期</label>
		</div>
		<div class="mui-input-row">
			<select style="font-size:14px" id="rangeType" name="rangeType" onchange="changed();">
				<option value="1">日计划</option>
				<option value="2">周计划</option>
				<option value="3">月计划</option>
				<option value="4">季度计划</option>
				<option value="5">半年计划</option>
				<option value="6">年计划</option>
			</select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>添加时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id="crTimeDesc" name="crTimeDesc" placeholder="选择时间" onchange="changed();"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="managerUserName" readonly placeholder="选择负责人" />
			<input type="hidden" id=managerUserId />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea name="content" id='content' rows="5" placeholder="请输入计划内容"></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>批示人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea name="reportedRangesNames" id='reportedRangesNames' rows="5" placeholder="请选择批示人"></textarea>
			<input type="hidden" id=reportedRangesIds />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>分享人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<textarea name="sharedRangesNames" id='sharedRangesNames' rows="5" placeholder="请选择分享人"></textarea>
			<input type="hidden" id=sharedRangesIds />
			
			<input type="hidden" id=time1Desc />
			<input type="hidden" id=time2Desc />
			<input type="hidden" id=rangeDesc />
			
			
			
		</div>
	</div>
</div>


<script>
	var scheduleId = "<%=scheduleId%>";
	(function($) {
		//启动加载完毕的逻辑	
		var date = new Date();
		document.getElementById("crTimeDesc").value=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		changed();
		crTimeDesc.addEventListener('tap', function() {
			/*
			 * 首次显示时实例化组件
			 * 示例为了简洁，将 options 放在了按钮的 dom 上
			 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
			 */
			var picker = new $.DtPicker({type:"date"});
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
				//result.innerText = '选择结果: ' + rs.text;
				crTimeDesc.value = rs.text;
				/* 
				 * 返回 false 可以阻止选择框的关闭
				 * return false;
				 */
				
				/*
				 * 释放组件资源，释放后将将不能再操作组件
				 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
				 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
				 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实实。
				 */
				picker.dispose(); 
			});
		}, false);
		
		managerUserName.addEventListener('tap', function() {
			selectSingleUser("managerUserId","managerUserName");
		}, false);
		reportedRangesNames.addEventListener('tap', function() {
			selectUser("reportedRangesIds","reportedRangesNames");
		}, false);
		sharedRangesNames.addEventListener('tap', function() {
			selectUser("sharedRangesIds","sharedRangesNames");
		}, false);
		
		if(scheduleId!="" && scheduleId!="null"){
			getScheduleInfo();
		}
	})(mui);
	
	
	function save(){
		var title = document.getElementById("title").value;// $("#title").val();
		var type= document.getElementById("type").value;//$("#type").val();
		var rangeType= document.getElementById("rangeType").value;//$("#rangeType").val();
		var crTimeDesc= document.getElementById("crTimeDesc").value;//$("#crTimeDesc").val();
		var managerUserId= document.getElementById("managerUserId").value;//$("#managerUserId").val();
		var content= document.getElementById("content").value;//$("#content").val();
		var managerUserName= document.getElementById("managerUserName").value;
		var reportedRangesIds= document.getElementById("reportedRangesIds").value;
		var reportedRangesNames= document.getElementById("reportedRangesNames").value;
		var sharedRangesIds= document.getElementById("sharedRangesIds").value;
		var sharedRangesNames= document.getElementById("sharedRangesNames").value;
		var time1Desc= document.getElementById("time1Desc").value;
		var time2Desc= document.getElementById("time2Desc").value;
		var rangeDesc= document.getElementById("rangeDesc").value;
		var url = contextPath+"/schedule/save.action";
		if(scheduleId!="" && scheduleId!="null"){
			 url = contextPath+"/schedule/update.action?uuid="+scheduleId;
		}
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{title:title,type:type,rangeType:rangeType,crTimeDesc:crTimeDesc,managerUserId:managerUserId,
				content:content,managerUserName:managerUserName,reportedRangesIds:reportedRangesIds,reportedRangesNames:reportedRangesNames,
				sharedRangesIds:sharedRangesIds,sharedRangesNames:sharedRangesNames,time2Desc:time2Desc,time1Desc:time1Desc,rangeDesc:rangeDesc},
			timeout:10000,
			success:function(data){
				Alert("保存成功！");
				window.location = 'index.jsp';
			},
			error:function(){
				
			}
		});
	}
	
	
	function changed(){
		var rangeType= document.getElementById("rangeType").value;
		var crTimeDesc= document.getElementById("crTimeDesc").value;
		var url = contextPath+"/schedule/getRangeTypeInfo.action";
		mui.ajax(url,{
			type:"GET",
			dataType:"JSON",
			data:{rangeType:rangeType,time:crTimeDesc},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				document.getElementById("time1Desc").value = json.rtData.time1Desc;
				document.getElementById("time2Desc").value = json.rtData.time2Desc;
				document.getElementById("rangeDesc").value = json.rtData.rangeDesc;
			},
			error:function(){
				
			}
		});
	}
	
	
	function getScheduleInfo(){
		var url = contextPath+"/schedule/get.action";
		mui.ajax(url,{
			type:"GET",
			dataType:"JSON",
			data:{uuid:scheduleId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					document.getElementById("title").value = json.rtData.title;
					document.getElementById("type").value = json.rtData.type;
					document.getElementById("rangeType").value = json.rtData.rangeType;
					document.getElementById("crTimeDesc").value = json.rtData.crTimeDesc;
					document.getElementById("managerUserName").value = json.rtData.managerUserName;
					document.getElementById("managerUserId").value = json.rtData.managerUserId;
					document.getElementById("content").value = json.rtData.content;
					document.getElementById("reportedRangesNames").value = json.rtData.reportedRangesNames;
					document.getElementById("reportedRangesIds").value = json.rtData.reportedRangesIds;
					document.getElementById("sharedRangesNames").value = json.rtData.sharedRangesNames;
					document.getElementById("sharedRangesIds").value = json.rtData.sharedRangesIds;
					var attachs = json.rtData.attachMentModel;
					for(var i = 0 ;i<attachs.length;i++){
						var attach = attachs[i];
						var att = "<div style='height:32px;line-height:32px;'><a href='javascript:void(0);' onclick=\"GetFile('"+attach.sid+"','"+attach.fileName+"','"+attach.attachmentName+"')\">"+attach.fileName + "</a></div>";
						document.getElementById("attachments").innerHTML+=att;
					}
				}
				if($("#managerUserId").val()=="0"){
					$("#managerUserId").val("");
				}
			},
			error:function(){
				
			}
		});
	}
</script>
</body>
</html>