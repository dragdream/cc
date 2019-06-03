<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String taskId = request.getParameter("taskId");
	String fromTask = request.getParameter("fromTask");
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>任务管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="script.js"></script>
</head>
<body>
<header id="header" class="mui-bar mui-bar-nav">
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="window.location = 'index.jsp'">
	    <span class="mui-icon mui-icon-left-nav"></span>返回
	</button>
	<a class="mui-pull-right app-title" href="#myPopover">操作
				<span class="mui-icon mui-icon-arrowdown"></span>
			</a>
	<h1 class="mui-title">任务详情</h1>
</header>

<div id="myPopover" class="mui-popover" style="width:120px">
		  <ul class="mui-table-view" id='controlDiv'>
		   	<li class="mui-table-view-cell"  onclick='window.location.reload();'>刷新</li>
		  </ul>
</div>

<style>
.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}
</style>
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
			<div class="mui-table-view-cell mui-media">
				<div class="mui-media-object mui-pull-right" >
				<span id='status'></span>
				</div>
				<div class="mui-media-body">
					<span id="taskTitle"></span>
					<p class='mui-ellipsis'><span id='chargerName'></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read"></span></p>
				</div>
			</div>
	</div>
	<!-- 等待接收提示区 -->
	<div class="mui-input-group"  id="alert1" style="display:none">
		<div class="mui-input-row" style='height:32px;line-height:32px;text-align:center;'>
			<font color='red'>您是任务责任人，需要接收该任务！</font>
		</div>
		<div class="app-row-content" style='text-align:center;'>
			<button class="mui-btn mui-btn-primary" onclick="receive(<%=taskId%>)">接收任务</button>
			<button class="mui-btn mui-btn-danger" onclick="noReceive(<%=taskId%>)">拒绝接收</button>
		</div>
	</div>
	<!-- 等待审批提示区 -->
	<div class="mui-input-group"  id="alert2" style="display:none">
		<div class="mui-input-row" style='height:32px;line-height:32px;text-align:center;'>
			<font color='red'>您是任务审批人，需要审批该任务！</font>
		</div>
		<div class="app-row-content" style='text-align:center;'>
			<button class="mui-btn mui-btn-primary" onclick="pass(<%=taskId%>)">审批通过</button>
			<button class="mui-btn mui-btn-danger" onclick="noPass(<%=taskId%>)">不通过</button>
		</div>
	</div>
	<!-- 等待审核提示区 -->
	<div class="mui-input-group"  id="alert3" style="display:none">
		<div class="mui-input-row" style='height:32px;line-height:32px;text-align:center;'>
			<font color='red'>您是任务布置人，需要审核该任务！</font>
		</div>
		<div class="app-row-content" style='text-align:center;'>
			<button class="mui-btn mui-btn-primary" onclick="pass1(<%=taskId%>)">审核通过</button>
			<button class="mui-btn mui-btn-danger" onclick="noPass1(<%=taskId%>)">不通过</button>
		</div>
	</div>
	
	<div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
		<a class="mui-control-item mui-active" href="#taskInfo">
				任务概况
			</a>
		<a class="mui-control-item" href="#childTask">
				子任务<span id="childCount" style="color:red"></span>
			</a>
		<a class="mui-control-item" href="#taskEvent">
				任务事件<span id="eventCount" style="color:red"></span>
			</a>
		<a class="mui-control-item" href="#taskDocument">
				任务汇报<span id="docCount" style="color:red"></span>
			</a>
	</div>
	<!-- <div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div> -->
	<div class="mui-slider-group">
		<div id="taskInfo" class="mui-slider-item mui-control-content mui-active">
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>任务布置时间</label>
				</div>
				<div class="app-row-content" id="createTimeDesc">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>布置开始时间</label>
				</div>
				<div class="app-row-content" id="startTimeDesc">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>布置结束时间</label>
				</div>
				<div class="app-row-content" id="endTimeDesc">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>实际开始时间</label>
				</div>
				<div class="app-row-content" id="relStartTimeDesc">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>实际结束时间</label>
				</div>
				<div class="app-row-content" id="relEndTimeDesc">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>布置人</label>
				</div>
				<div class="app-row-content" id="createUserName">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>参与人</label>
				</div>
				<div class="app-row-content" id="joinerNames">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>任务内容</label>
				</div>
				<div class="app-row-content" id="content">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>奖惩标准 </label>
				</div>
				<div class="app-row-content" id="standard">
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>备注批示</label>
				</div>
				<div class="app-row-content" id="leaderRemark">
				</div>
			</div>
		</div>
		<div id="childTask" class="mui-slider-item mui-control-content">
				<div class="mui-input-group" id='operDiv1'>
					<div class="app-row-content" style='text-align:right;'>
						<button class="mui-btn mui-btn-primary" onclick="loadChildTask()">刷新</button>
	      				<button  class="mui-btn mui-btn-danger" onclick="window.location='addOrUpdate.jsp?parentTaskId=<%=taskId %>'">创建子任务</button>
					</div>
				</div>
				<div class="mui-content-padded" id="taskList" style="padding-bottom: 50px;">
					<center>
						<p class="app-tip">没有找到符合条件的记录！</p>
					</center>
			</div>
		</div>
		<div id="taskEvent" class="mui-slider-item mui-control-content">
			<table  style="width:100%">
	      		<tbody id="eventTbody">
	      			
	      		</tbody>
	      	</table>
		</div>
		<div id="taskDocument" class="mui-slider-item mui-control-content">
			<table class="TableBlock" style="width:100%;margin-top:5px;">
	      		<tbody id="docTbody">
	      			
	      		</tbody>
	      	</table>
		</div>
	</div>
</div>
<div id="delete" class="mui-popover mui-popover-action mui-popover-bottom">
	<ul class="mui-table-view">
		<li class="mui-table-view-cell">
			<a href="#" style="color: #FF3B30;" onclick="del(<%=taskId%>)">确定删除</a>
		</li>
	</ul>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell">
			<a href="#delete"><b>取消</b></a>
		</li>
	</ul>
</div>

<script>
	var taskId = <%=taskId%>;
	var fromTask = <%=fromTask%>;
	var userId = <%=loginPerson.getUuid()%>;
	(function($) {
		//启动加载完毕的逻辑	
		getTaskInfo();
		loadEvents();
		loadDocs();
		loadChildTask();
	})(mui);
	
	
	function getTaskInfo(){
		var url = contextPath+"/coWork/getTaskInfo.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{taskId:taskId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					document.getElementById("taskTitle").innerHTML = json.rtData.taskTitle;
					document.getElementById("createTimeDesc").innerHTML = json.rtData.createTimeDesc;
					document.getElementById("startTimeDesc").innerHTML = json.rtData.startTimeDesc;
					document.getElementById("endTimeDesc").innerHTML = json.rtData.endTimeDesc;
					document.getElementById("relStartTimeDesc").innerHTML = json.rtData.relStartTimeDesc;
					document.getElementById("relEndTimeDesc").innerHTML = json.rtData.relEndTimeDesc;
					document.getElementById("createUserName").innerHTML = json.rtData.createUserName;
					document.getElementById("joinerNames").innerHTML = json.rtData.joinerNames;
					document.getElementById("content").innerHTML = json.rtData.content;
					document.getElementById("standard").innerHTML = json.rtData.standard;
					document.getElementById("leaderRemark").innerHTML = json.rtData.leaderRemark;
					
					document.getElementById("chargerName").innerHTML = "负责人："+json.rtData.chargerName;
					//渲染当前状态
					if(json.rtData.status==0){
						status="<span class=\"mui-badge mui-badge-primary\">等待接收</span>";
					}else if(json.rtData.status==1){
						status="<span class=\"mui-badge mui-badge-primary\">等待审批</span>";
					}else if(json.rtData.status==2){
						status="<span class=\"mui-badge mui-badge-danger\">审批不通过</span>";
					}else if(json.rtData.status==3){
						status="<span class=\"mui-badge mui-badge-danger\">拒绝接收</span>";
					}else if(json.rtData.status==4){
						status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
					}else if(json.rtData.status==5){
						status="<span class=\"mui-badge mui-badge-purple\">提交审核</span>";
					}else if(json.rtData.status==6){
						status="<span class=\"mui-badge mui-badge-danger\">审核不通过</span>";
					}else if(json.rtData.status==7){
						status="<span class=\"mui-badge  mui-badge-danger\">任务撤销</span>";
					}else if(json.rtData.status==8){
						status="<span class=\"mui-badge mui-badge-warning\">已完成</span>";
					}else if(json.rtData.status==9){
						status="<span class=\"mui-badge mui-badge-danger\">任务失败</span>";
					}
					document.getElementById("status").innerHTML = status;
					
					if(json.rtData.chargerId!=userId){//如果当前登陆人不是负责人，则不允许显示
						$("#operDiv1").hide();
					}
					if(json.rtData.createUserId==userId){//如果当前登陆人是创建人，则显示子任务
						$("#operDiv1").show();
					}
					if(json.rtData.status!=4){
						$("#operDiv1").hide();
					}
					
					//权限运算
					var priv = 0;
					rowData = json.rtData;
					if(rowData.status==0){//等待接收
						if(rowData.createUserId==userId){//布置人操作
							priv = 1+2+4+8+64;
							if(rowData.chargerId==userId){
								$("#alert1").show();
							}
						}else if(rowData.chargerId==userId){//负责人操作
							priv = 8;
							$("#alert1").show();
						}else{
							priv = 8;
						}
					}else if(rowData.status==1){//等待审批
						if(rowData.createUserId==userId){//布置人操作
							priv = 1+2+4+8+64;
							if(rowData.auditorId==userId){
								$("#alert2").show();
							}
						}else if(rowData.auditorId==userId){
							priv = 8;//查看
							$("#alert2").show();
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==2){//审批不通过
						if(rowData.createUserId==userId){
							priv = 1+2+4+8+64;
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==3){//拒绝接收
						if(rowData.createUserId==userId){
							priv = 1+2+4+8+64;
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==4){//进行中
						if(rowData.createUserId==userId){//布置人操作
							priv = 4+8+16+32+64+128+256;
						}else if(rowData.chargerId==userId){
							priv = 8+16+32+64+128+256;
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==5){//等待审核
						if(rowData.createUserId==userId){//布置人操作
							priv = 4+8+16+32+64;
							$("#alert3").show();
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==6){//审核不通过
						if(rowData.createUserId==userId){//布置人操作
							priv = 4+8+16+32+64+128+256;
						}else if(rowData.chargerId==userId){//责任人操作
							priv = 8+16+32+64+128+256;
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==7){//任务撤销
						if(rowData.createUserId==userId){//布置人操作
							priv = 2+8;
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==8){//已完成
						if(rowData.createUserId==userId){//布置人操作
							priv = 2+8;
						}else{
							priv = 8;//查看
						}
					}else if(rowData.status==9){//失败
						if(rowData.createUserId==userId){//布置人操作
							priv = 2+8;
						}else{
							priv = 8;//查看
						}
					}
					//渲染权限
					renderOper(priv);
				}
			},
			error:function(){
				
			}
		});
	}
	
	
	function loadEvents(){
		//获取任务事件数据
		var url = contextPath+"/coWork/listEvents.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{taskId:taskId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				var list = json.rtData;
				$("#eventCount").html("&nbsp;["+list.length+"]");
				var arr = [];
				var template = "<tr style='height:58px;font-size:14px;'>";
				template+="<td>{1}</td>";
				template+="<td>{2}</td>";
				template+="<td>{3}</td>";
				template+="</tr>";
				var color = "";
				for(var i=0;i<list.length;i++){
					var item = list[i];
					if(item.type==1 || item.type==2 || item.type==10){
						color = "green";
					}else if(item.type==3 || item.type==4 || item.type==5){
						color = "orange";
					}else if(item.type==6 || item.type==7 || item.type==9){
						color = "blue";
					}else {
						color = "red";
					}
					arr.push(template.replace("{1}","<span class='mui-ellipsis' style='padding:3px;background:"+color+";color:#fff'>"+item.typeDesc+"</span>")
							.replace("{2}","<span>"+item.title+"</span><br/><span style='color:#bdbdbd;'>"+(item.content!=null?item.content:"")+"</span>")
							.replace("{3}","<span class='mui-ellipsis'>"+item.createUserName+"</span><br/><span class='mui-ellipsis' style='color:#bdbdbd'>"+item.createTime+"</span>"));
				}
				
				$("#eventTbody").html(arr.join(""));
				if(arr.length<1){
					$("#eventTbody").html("<center>没有相关信息！</center>");
				}
			},
			error:function(){
				
			}
		});
	}
	
	
	function loadDocs(){
		
		//获取任务事件数据
		var url = contextPath+"/coWork/listDocs.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{taskId:taskId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				var list = json.rtData;
				$("#docCount").html("&nbsp;["+list.length+"]");
				
				var html = "<ul class='mui-table-view'>";
				for(var i=0;i<list.length;i++){
					var item = list[i];
					var attachs = item.attaches;
					var template="";
					var delStr="";
					if(item.createUserId==userId){
						delStr="<a href='javascript:void(0)' onclick='deleteDoc("+item.sid+")'>删除</a>";
					}
					for(var j = 0 ;j<attachs.length;j++){
						var attach = attachs[j];
						template += "<div style='height:32px;line-height:32px;'><a href='javascript:void(0);' onclick=\"GetFile('"+attach.sid+"','"+attach.fileName+"','"+attach.attachmentName+"')\">"+attach.fileName + "</a></div>";
					}
					
					html+="<li class=\"mui-table-view-cell mui-media\">"
						+"<div class=\"mui-media-object mui-pull-right\" >"
						+"<span>"+delStr+"</span>"
						+"</div>"
						+"<div class=\"mui-media-body\">"
						+ template
						+"<p class='mui-ellipsis'><span>"+item.createUserName+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
						+"<p class='mui-ellipsis'><span>"+item.createTimeDesc+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
						+"<p class='mui-ellipsis'><span>备注："+item.remark+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
						+"</div>"
						+"</li>";
					
				}
				html+="</ul>";
				$("#docTbody").html(html);
				if(list.length<1){
					$("#docTbody").html("<center>没有相关信息！</center>");
				}
			},
			error:function(){
				
			}
		});
	}
	
	function deleteDoc(docId){
		//获取任务事件数据
		var url = contextPath+"/coWork/deleteDoc.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{sid:docId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){
					loadDocs();
				}
			},
			error:function(){
				Alert("异常错误！");
			}
		});
		
	}
	
	function renderOper(priv){
		for(var i=0;i<=8;i++){
			var tmp = Math.pow(2,i);
			if((priv&tmp)==tmp){
				if(tmp==1){
					$("#controlDiv").html($("#controlDiv").html()+"<li  class=\"mui-table-view-cell\"  id=\"b1\" onclick=\"edit(<%=taskId%>)\">修改任务</li>");
				}else if(tmp==2){
					$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id=\"b2\" ><a class=\"mui-tab-item\" href=\"#delete\">删除任务</a></li>");
				}else if(tmp==4){
					$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id=\"b4\" onclick=\"redo(<%=taskId%>)\">撤销任务</li>");
				}else if(tmp==16){
					$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id=\"b16\" onclick=\"urge(<%=taskId%>)\">督办任务</li>");
				}else if(tmp==32){
					$("#controlDiv").html($("#controlDiv").html()+"<li  class=\"mui-table-view-cell\"  id=\"b32\" onclick=\"delay(<%=taskId%>)\">任务延期</li>");
				}else if(tmp==64){
					$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id=\"b64\" onclick=\"failed(<%=taskId%>)\">任务失败</li>");
				}else if(tmp==128){
					$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id=\"b128\" onclick=\"report(<%=taskId%>)\">汇报进度</li>");
				}else if(tmp==256){
					$("#controlDiv").html($("#controlDiv").html()+"<li class=\"mui-table-view-cell\"  id=\"b256\" onclick=\"finish(<%=taskId%>)\">完成任务</li>");
				}
			}
		}
	}
	
	
	function loadChildTask(){
		var url = contextPath + '/coWork/datagrid.action?page=1&rows=100000000';
		mui.ajax(url,{
			type:"GET",
			dataType:"JSON",
			data:{parentTaskId:taskId},
			timeout:10000,
			success:function(text){
				var json = eval("("+text+")");
				var data = json.rows;
				document.getElementById("taskList").innerHTML="";
				var html = "<ul class='mui-table-view'>";
				for(var i = 0 ;i<data.length;i++){
					var status = "";
					if(data[i].status==0){
						status="<span class=\"mui-badge mui-badge-primary\">等待接收</span>";
					}else if(data[i].status==1){
						status="<span class=\"mui-badge mui-badge-primary\">等待审批</span>";
					}else if(data[i].status==2){
						status="<span class=\"mui-badge mui-badge-danger\">审批不通过</span>";
					}else if(data[i].status==3){
						status="<span class=\"mui-badge mui-badge-danger\">拒绝接收</span>";
					}else if(data[i].status==4){
						status="<span class=\"mui-badge mui-badge-success\">进行中</span>";
					}else if(data[i].status==5){
						status="<span class=\"mui-badge mui-badge-purple\">提交审核</span>";
					}else if(data[i].status==6){
						status="<span class=\"mui-badge mui-badge-danger\">审核不通过</span>";
					}else if(data[i].status==7){
						status="<span class=\"mui-badge  mui-badge-danger\">任务撤销</span>";
					}else if(data[i].status==8){
						status="<span class=\"mui-badge mui-badge-warning\">已完成</span>";
					}else if(data[i].status==9){
						status="<span class=\"mui-badge mui-badge-danger\">任务失败</span>";
					}
				
					html+="<li class=\"mui-table-view-cell mui-media\">"
					+"<a href=\"detail.jsp?taskId="+data[i].sid+"\">"
					+"<div class=\"mui-media-object mui-pull-right\" >"
					+"<span>"+status+"</span>"
					+"</div>"
					+"<div class=\"mui-media-body\">"
					+ data[i].taskTitle
					+"	<p class='mui-ellipsis'><span>负责人："+data[i].chargerName+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"read\"></span></p>"
					+"</div>"
					+"</a>"
					+"</li>";
					
				}
				html+="</ul>";
				if(data.length<1){
					html="<center><p class=\"app-tip\">没有找到符合条件的记录！</p></center>";
				}
				document.getElementById("taskList").innerHTML+=html;
				$("#childCount").html("&nbsp;["+data.length+"]");
			},
			error:function(){
				
			}
		});
		
	}
</script>
</body>
</html>