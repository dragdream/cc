<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String taskId = request.getParameter("taskId");
	String fromTask = request.getParameter("fromTask");
%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="script.js"></script>
<style>

</style>
<script>
var taskId = <%=taskId%>;
var fromTask = <%=fromTask%>;
var userId = <%=loginPerson.getUuid()%>;
var datagrid ;
function doInit(){
	
	loadInfo();
	loadEvents();
	loadDocs();
	loadChildTask();
	
	
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"valuesHolder2",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"coWork"}//后台传入值，model为模块标志
		});
}

function renderOper(priv){
	for(var i=0;i<=8;i++){
		var tmp = Math.pow(2,i);
		if((priv&tmp)==tmp){
			$("#b"+tmp).show();
		}
	}
}

function goback(){
	if(fromTask!=null){
		window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+fromTask;
	}else{
		window.location = contextPath+"/system/subsys/cowork/list.jsp";
	}
	
}

//提交文档
function uploadDoc(){
	var json = tools.requestJsonRs(contextPath+"/coWork/addDoc.action",{taskId:taskId,remark:$("#attachRemark").val(),attachIds:$("#valuesHolder2").val()});
	if(json.rtState){
		alert("保存成功");
		window.location.reload();
	}
}

function loadInfo(){
	$.jBox.tip("正在加载…","loading");
	tools.requestJsonRs(contextPath+"/coWork/getTaskInfo.action?taskId="+taskId,{},true,function(json){
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			//渲染进度条
			$("#progress").progressBar(json.rtData.progress, {showText: true});
			
			if(json.rtData.chargerId!=userId){//如果当前登陆人不是负责人，则不允许显示
				$("#childTaskDiv").hide();
			}
			if(json.rtData.createUserId==userId){//如果当前登陆人是创建人，则显示子任务
				$("#childTaskDiv").show();
			}
			if(json.rtData.status!=4){
				$("#operDiv1").hide();
				$("#operDiv2").hide();
			}
			
			$("#delayDiv").find("[fid=delayTime]:first").attr("onFocus","WdatePicker({minDate:'"+json.rtData.endTimeDesc+"',dateFmt:'yyyy-MM-dd HH:mm:ss'})");
			
			//渲染当前状态
			var statusDesc;
			var statusColor = "";
			var status = json.rtData.status;
			switch(status)
			{
			case 0:
				statusDesc = "等待接收";
				statusColor = "red";
				break;
			case 1:
				statusDesc = "等待审批";
				statusColor = "red";
				break;
			case 2:
				statusDesc = "审批不通过";
				statusColor = "red";
				break;
			case 3:
				statusDesc = "拒绝接收";
				statusColor = "red";
				break;
			case 4:
				statusDesc = "进行中";
				statusColor = "blue";
				break;
			case 5:
				statusDesc = "提交审核";
				statusColor = "red";
				break;
			case 6:
				statusDesc = "审核通过";
				statusColor = "red";
				break;
			case 7:
				statusDesc = "任务撤销";
				statusColor = "red";
				break;
			case 8:
				statusDesc = "已完成";
				statusColor = "green";
				break;
			case 9:
				statusDesc = "已失败";
				statusColor = "red";
				break;
			}
			$("#status").html("<span style='color:"+statusColor+"'>"+statusDesc+"</span>");
			
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
					priv = 4+8+16+32+64;
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
			
			$.jBox.tip("加载完毕","success");
		}
	});
}

function loadEvents(){
	//获取任务事件数据
	tools.requestJsonRs(contextPath+"/coWork/listEvents.action?taskId="+taskId,{},true,function(json){
		var list = json.rtData;
		$("#eventCount").html("&nbsp;["+list.length+"]");
		var arr = [];
		var template = "<tr>";
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
			arr.push(template.replace("{1}","<span style='padding:3px;background:"+color+";color:#fff'>"+item.typeDesc+"</span>")
					.replace("{2}","<span>"+item.title+"</span><br/><span style='color:#bdbdbd'>"+(item.content!=null?item.content:"")+"</span>")
					.replace("{3}","<span>"+item.createUserName+"</span><br/><span style='color:#bdbdbd'>"+item.createTime+"</span>"));
		}
		
		$("#eventTbody").html(arr.join(""));
	});
}

function deleteDoc(docId){
	$.jBox.confirm("是否要删除该文档？","提示",function(v){
		if(v=="ok"){
			var json = tools.requestJsonRs(contextPath+"/coWork/deleteDoc.action",{sid:docId});
			if(json.rtState){
				loadDocs();
			}
		}
	});
}

function loadDocs(){
	tools.requestJsonRs(contextPath+"/coWork/listDocs.action?taskId="+taskId,{},true,function(json){
		var list = json.rtData;
		var template = "";
		$("#docCount").html("&nbsp;["+list.length+"]");
		for(var i=0;i<list.length;i++){
			var item = list[i];
			template+="<tr>";
			template+="<td>";
			var attaches = item.attaches;
			for(var j=0;j<attaches.length;j++){
				var file = attaches[j];
				template+="<div clazz='fileItem' sid='"+file.sid+"' fileName='"+file.fileName+"' fileSize='"+file.fileSize+"' ext='"+file.ext+"' userId='"+file.userId+"'></div>";
			}
			template+="</td>";
			template+="<td>"+item.remark+"</td>";
			template+="<td>"+item.createUserName+"<br/>"+item.createTimeDesc+"</td>";
			if(item.createUserId==userId){
				template+="<td>";
				template+="<a href='javascript:void(0)' onclick='deleteDoc("+item.sid+")'>删除</a>";
				template+="</td>";
			}else{
				template+="<td></td>";
			}
			template+="</tr>";
		}
		$("#docTbody").html(template);
		
		$("div[clazz=fileItem]").each(function(i,obj){
			var fileJson = {sid:$(obj).attr("sid"),fileName:$(obj).attr("fileName"),fileSizeDesc:$(obj).attr("fileSize"),ext:$(obj).attr("ext")};
			fileJson.priv = 1+2;
			var fileItem = tools.getAttachElement(fileJson,{});
			$(obj).append(fileItem);
		});
		
	});
}

/**
 * 加载子任务
 */
function loadChildTask(){
	tools.requestJsonRs(contextPath + '/coWork/datagrid.action',{page:1,rows:1000,parentTaskId:taskId},true,function(json){
		var rows = json.rows;
		var html = [];
		for(var i=0;i<rows.length;i++){
			var rowData = rows[i];
			html.push("<tr>");
			html.push("<td>"+renderTitle(rowData)+"</td>");
			html.push("<td>"+renderStatus(rowData)+"</td>");
			html.push("<td>"+rowData.chargerName+"</td>");
			html.push("<td>"+rowData.createUserName+"</td>");
			html.push("<td>"+rowData.startTimeDesc+"</td>");
			html.push("<td>"+rowData.endTimeDesc+"</td>");
			html.push("<td><a href='javascript:void(0)' onclick='lookup("+rowData.sid+")'></a></td>");
			html.push("</tr>");
		}
		$("#childTaskTbody").html(html.join(""));
		$(".progress_bar").each(function(i,obj){
			$(obj).progressBar(obj.getAttribute("value"), {showText: true});
		});
		$("#childCount").html("&nbsp;["+rows.length+"]");
	});
	
}

function lookup(sid){
	window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+sid+"&fromTask="+taskId;
}

function renderTitle(rowData){
	var render = "";
	render+="<b>"+rowData.taskTitle+"</b>"
	if(rowData.status==1){//等待审批
		if(rowData.auditorId==userId){//如果当前登陆人是审批人员
			render+="<br/><span style='color:red'>你是任务审批人，需要审批</span>";
		}else{
			render+="<br/><span style='color:red'>等待审批</span>";
		}
	}else if(rowData.status==0){//等待接收
		if(rowData.charger==userId){//
			render+="<br/><span style='color:red'>你是任务负责人，需要接收</span>";
		}
	}else if(rowData.status==2){//审批不通过
		if(rowData.createUserId==userId){//
			render+="<br/><span style='color:red'>你是任务布置人，请修改</span>";
		}
	}else if(rowData.status==3){//拒绝接收
		if(rowData.createUserId==userId){//
			render+="<br/><span style='color:red'>你是任务布置人，请修改</span>";
		}
	}else if(rowData.status==4){//进行中
	}else if(rowData.status==5){//等待审核
		if(rowData.createUserId==userId){//
			render+="<br/><span style='color:red'>你是任务布置人，需要审核</span>";
		}
	}
	return render;
}

function renderStatus(rowData){
	var status = rowData.status;
	var render = "";
	switch(status)
	{
	case 0:
		render+="等待接收";
		break;
	case 1:
		render+="等待审批";
		break;
	case 2:
		render+="审批不通过";
		break;
	case 3:
		render+="拒绝接收";
		break;
	case 4:
		render+="进行中";
		break;
	case 5:
		render+="提交审核";
		break;
	case 6:
		render+="审核通过";
		break;
	case 7:
		render+="任务撤销";
		break;
	case 8:
		render+="已完成";
		break;
	case 9:
		render+="任务失败";
		break;
	}
	
	render+="<div class='progress_bar' value='"+rowData.progress+"'></div>";
	return render;
}

function addChildTask(){
	bsWindow(contextPath+"/system/subsys/cowork/addChildTask.jsp?parentTaskId="+taskId,"创建子任务",{width:"800",submit:function(v,h){
		var contentWindow = h[0].contentWindow;
		if(contentWindow.commit()){
			loadChildTask();
			return true;
		}
	}});
	
}

</script>
</head>
<body onload="doInit();" style="margin:5px">
	<div class="moduleHeader">
		<div>
			<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;[任务]&nbsp;<span id="taskTitle"></span></b>
		</div>
	</div>
	
	<div class="panel panel-default">
	  <div class="panel-heading"><b><a href="#"><i class="glyphicon glyphicon-barcode"></i>&nbsp;基本信息</a></b></div>
	  <div class="panel-body">
	      	<div style="background:#e2e2e2;padding:10px;font-weight:bold;font-size:14px;">信息</div>
	      	<div style="background:#f0f0f0;padding:10px;">
	      		<table style="font-size:12px;">
		      		<tr>
		      			<td align="right" style="width:105px">当前状态：</td>
		      			<td id="status"></td>
		      			<td align="right" style="width:105px">任务布置时间：</td>
		      			<td id="createTimeDesc"></td>
		      			<td style="width:105px"></td>
		      			<td></td>
		      		</tr>
		      		<tr>
		      			<td align="right">布置开始时间：</td>
		      			<td id="startTimeDesc"></td>
		      			<td align="right">布置结束时间：</td>
		      			<td id="endTimeDesc"></td>
		      			<td align="right">任务评分：</td>
		      			<td id="score"></td>
		      		</tr>
		      		<tr>
		      			<td align="right">实际开始时间：</td>
		      			<td id="relStartTimeDesc"></td>
		      			<td align="right">实际完成时间：</td>
		      			<td id="relEndTimeDesc"></td>
		      			<td align="right">任务进度：</td>
		      			<td id="progress">
		      				
		      			</td>
		      		</tr>
		      		<tr>
		      			<td align="right">布置人：</td>
		      			<td id="createUserName"></td>
		      			<td align="right">负责人：</td>
		      			<td id="chargerName"></td>
		      			<td align="right">审批人：</td>
		      			<td id="auditorName"></td>
		      		</tr>
		      		<tr>
		      			<td align="right">参与人：</td>
		      			<td id="joiners"></td>
		      			<td align="right">评估工时：</td>
		      			<td id="rangeTimes"></td>
		      			<td align="right">确认工时：</td>
		      			<td id=""></td>
		      		</tr>
		      	</table>
	      	</div>
	      	<div style="background:#e2e2e2;padding:10px;font-weight:bold;font-size:14px;">任务内容</div>
	      	<div style="background:#f0f0f0;padding:10px;" id="content"></div>
	      	<div style="background:#e2e2e2;padding:10px;font-weight:bold;font-size:14px;">奖惩标准</div>
	      	<div style="background:#f0f0f0;padding:10px;" id="standard"></div>
	      	<div style="background:#e2e2e2;padding:10px;font-weight:bold;font-size:14px;">备注批示</div>
	      	<div style="background:#f0f0f0;padding:10px;" id="leaderRemark"></div>
	  </div>
	</div>
	
	<div class="panel-group" id="accordion">
	  <div class="panel panel-default">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
	          	<i class="glyphicon glyphicon-file"></i>&nbsp;任务事件<span id="eventCount" style="color:red"></span>
	        </a>
	      </h4>
	    </div>
	    <div id="collapseTwo" class="panel-collapse collapse">
	      <div class="panel-body" style="padding:10px;">
	      	<table class="TableBlock" style="width:100%">
	      		<thead>
		      		<tr class="TableHeader">
		      			<td style="width:40px">类型</td>
		      			<td>事件标题/内容</td>
		      			<td style="width:140px">操作人/时间</td>
		      		</tr>
	      		</thead>
	      		<tbody id="eventTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	  <div class="panel panel-default" id="childTaskDiv">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
	          	<i class="glyphicon glyphicon-log-in"></i>&nbsp;子任务<span id="childCount" style="color:red"></span>
	        </a>
	      </h4>
	    </div>
	    <div id="collapseThree" class="panel-collapse collapse">
	      <div class="panel-body" style="padding:10px">
	      	<table class="TableBlock" style="width:100%;margin-top:5px;">
	      		<thead>
		      		<tr class="TableHeader">
		      			<td>任务标题</td>
		      			<td>状态</td>
		      			<td>负责人</td>
		      			<td>布置人</td>
		      			<td>开始时间</td>
		      			<td>结束时间</td>
		      			<td>操作</td>
		      		</tr>
	      		</thead>
	      		<tbody id="childTaskTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	  <div class="panel panel-default">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
	          	<i class="glyphicon glyphicon-th"></i>&nbsp;相关文档<span id="docCount" style="color:red"></span>
	        </a>
	      </h4>
	    </div>
	    <div id="collapseFour" class="panel-collapse collapse">
	      <div class="panel-body" style="padding:10px;">
	      	<table class="TableBlock" style="width:100%;margin-top:5px;">
	      		<thead>
		      		<tr class="TableHeader">
		      			<td style="width:300px">文档</td>
		      			<td>备注</td>
		      			<td style="width:190px">发表人/时间</td>
		      			<td style="width:80px">操作</td>
		      		</tr>
	      		</thead>
	      		<tbody id="docTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>