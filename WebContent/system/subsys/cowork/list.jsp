<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="script.js"></script>
<style>
</style>
<script>
var datagrid;
var userId = <%=loginPerson.getUuid()%>;
function doInit(){
	
}

$(function() {
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/coWork/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
// 		queryParams:para,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		sortOrder: 'desc',
		striped: false,
		singleSelect:true,
		remoteSort: true,
		toolbar: '#toolbar',
		columns : [ [ 
		 {
			field : 'task.taskTitle',
			title : '任务标题',
			width : 100,
			sortable : true,
			formatter:function(value,rowData,rowIndex){
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
		},
		{
			field : 'task.status',
			title : '状态',
			width : 100,
			sortable : true,
			formatter:function(value,rowData,rowIndex){
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
					render+="审核不通过";
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
		},
		{
			field : '_charger',
			title : '负责人',
			width : 100,
			formatter:function(value,rowData,rowIndex){
				return rowData.chargerName;
			}
		},
		{
			field : '_createUser',
			title : '布置人',
			width : 100,
			formatter:function(value,rowData,rowIndex){
				return rowData.createUserName;
			}
		},
		{
			field : 'task.startTime',
			title : '开始时间',
			width : 100,
			sortable : true,
			formatter:function(value,rowData,rowIndex){
				return rowData.startTimeDesc;
			}
		},
		{
			field : 'task.endTime',
			title : '结束时间',
			width : 100,
			sortable : true,
			formatter:function(value,rowData,rowIndex){
				return rowData.endTimeDesc;
			}
		},
		{
			field : '_oper',
			title : '操作',
			width : 100,
			formatter:function(value,rowData,rowIndex){
				var priv = 0;
				if(rowData.status==0){//等待接收
					if(rowData.createUserId==userId){//布置人操作
						priv = 1+2+4+8+64;
					}else{//负责人操作
						priv = 8;
					}
				}else if(rowData.status==1){//等待审批
					if(rowData.createUserId==userId){//布置人操作
						priv = 1+2+4+8+64;
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
						priv = 4+8+16+32+64;
					}else if(rowData.chargerId==userId){
						priv = 8+16+32+64+128+256;
					}else{
						priv = 8;//查看
					}
				}else if(rowData.status==5){//提交审核
					if(rowData.createUserId==userId){//布置人操作
						priv = 4+8+16+32+64;
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
				return renderOper(priv,rowData.sid,rowData.endTimeDesc);
			}
		}
		] ],
		onDblClickRow:function(rowIndex){
		    $('#datagrid').datagrid('selectRow',rowIndex);  //指定行选中
		    var currentRow =$("#datagrid").datagrid("getSelected");
			window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+currentRow.sid;
		},
		onLoadSuccess:function(){
			$(".progress_bar").each(function(i,obj){
				$(obj).progressBar(obj.getAttribute("value"), {showText: true});
			});
		}
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
	
});

function addTask(){
	window.location = contextPath+"/system/subsys/cowork/addTask.jsp";
}

function edit(taskId){
	window.location = contextPath+"/system/subsys/cowork/editTask.jsp?taskId="+taskId+"&from=list";
}

/*
 * 1-修改 2-删除 4-撤销 8-查看 16-督办 32-延期 64-失败 128-汇报进度 256-完成任务
 */
function renderOper(priv,taskId,endTimeDesc){
	var render = "";
	var edit = "";
	if((priv&1)==1){
		edit = "<a href='#' onclick='edit("+taskId+")'>修改</a>";
	}else{
		edit = "<span style='color:#e2e2e2'>修改</span>";
	}
	
	var del = "";
	if((priv&2)==2){
		del = "<a href='javascript:void(0)' onclick='del("+taskId+")'>删除</a>";
	}else{
		del = "<span style='color:#e2e2e2'>删除</span>";
	}
	
	var redo = "";
	if((priv&4)==4){
		redo = "<a href='javascript:void(0)' onclick='redo("+taskId+")'>撤销</a>";
	}else{
		redo = "<span style='color:#e2e2e2'>撤销</span>";
	}
	
	var detail = "";
	if((priv&8)==8){
		detail = "<a href='#' onclick='detail("+taskId+")'>查看</a>";
	}else{
		detail = "<span style='color:#e2e2e2'>查看</span>";
	}
	
	var urge = "";
	if((priv&16)==16){
		urge = "<a href='javascript:void(0)' onclick='urge("+taskId+")'>督办</a>";
	}else{
		urge = "<span style='color:#e2e2e2'>督办</span>";
	}
	
	var delay = "";
	if((priv&32)==32){
		delay = "<a href='javascript:void(0)' onclick='delay0("+taskId+",\""+endTimeDesc+"\")'>延期</a>";
	}else{
		delay = "<span style='color:#e2e2e2'>延期</span>";
	}
	
	var failed = "";
	if((priv&64)==64){
		failed = "<a href='javascript:void(0)' onclick='failed("+taskId+")'>失败</a>";
	}else{
		failed = "<span style='color:#e2e2e2'>失败</span>";
	}
	
	render+=edit+"&nbsp;"+del+"&nbsp;"+redo+"&nbsp;"+detail;
	render+="<br/>"+urge+"&nbsp;"+delay+"&nbsp;"+failed+"&nbsp;<a href='javascript:void(0)' onclick='graphics("+taskId+")'>汇总</a>";
	return render;
}

function delay0(taskId,time){
	$("#delayDiv").find("[fid=delayTime]:first").attr("onFocus","WdatePicker({minDate:'"+time+"',dateFmt:'yyyy-MM-dd HH:mm:ss'})");
	delay(taskId);
}

function graphics(taskId){
	openFullWindow(contextPath+"/system/subsys/cowork/taskNetGraphics.jsp?taskId="+taskId,"任务跟踪");
}

function search(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);
}
</script>
</head>
<body onload="doInit();" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = " clearfix" style="margin-top: 5px">
	<div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title">任务中心 </span>
	</div>
	<div class = "right fr clearfix">
	    <button class="btn-win-white fl" onclick="datagrid.datagrid('reload')"><i class="glyphicon glyphicon-refresh"></i>&nbsp;刷新</button>
		<button class="btn-win-white fl" onclick="addTask()">创建任务</button>
	</div>
	<span class="basic_border_grey" style="margin-top: 10px"></span>
	<div class="setHeight">
	<form id="form1" style="">
	 <table class="none_table" width="100%">
	 	<tr>
	 		<td class="TableData TableBG">任务名称：</td>
	 		<td class="TableData"><input class="BigInput" type="text" name="taskTitle" id="taskTitle"/></td>
	 		<td class="TableData TableBG" >状态：</td>
	 		<td class="TableData">
	 			<select class="BigSelect" id="status" name="status">
					<option value="-1">全部</option>
					<option value="0">等待接收</option>
					<option value="1">等待审批</option>
					<option value="2">审批不通过</option>
					<option value="3">拒绝接收</option>
					<option value="4">进行中</option>
					<option value="5">等待审核</option>
					<option value="6">审核不通过</option>
					<option value="7">任务撤销</option>
					<option value="8">已完成</option>
					<option value="9">任务失败</option>
				</select>
	 		</td>
	 		<td class="TableData TableBG">布置人：</td>
	 		<td class="TableData">
	 			<input type="hidden" class="BigInput" name="createUserId" id="createUserId"/>
	 			<input type="text" style="width:80px" class="BigInput readonly" readonly name="createUserName" id="createUserName"/>
				<a href="javascript:void(0)" onclick="selectSingleUser(['createUserId','createUserName'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('createUserId','createUserName')">清空</a>
				&nbsp;&nbsp;
	 		</td>
	 		<td class="TableData TableBG">负责人：</td>
	 		<td class="TableData">
	 			<input type="hidden" class="BigInput" name="chargerId" id="chargerId"/>
				<input type="text" style="width:80px" class="BigInput readonly" readonly name="chargerName" id="chargerName"/>
				<a href="javascript:void(0)" onclick="selectSingleUser(['chargerId','chargerName'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('chargerId','chargerName')">清空</a>
				&nbsp;&nbsp;
	 		</td>
	 		<td class="TableData"><button class="btn-win-white" type="button" onclick="search()">查询</button></td>
	 	</tr>
	 </table>
	</form>
	</div>
</div>
<table id="datagrid"></table>

<!-- 撤销任务区 -->
<div id="redoDiv" style="display:none">
	<div style="padding:5px;">
		撤销理由：<br/>
		<textarea fid="redoTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 督办任务区 -->
<div id="urgeDiv" style="display:none">
	<div style="padding:5px;">
		督办内容：<br/>
		<textarea fid="urgeTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 任务失败区 -->
<div id="failedDiv" style="display:none">
	<div style="padding:5px;">
		失败原因：<br/>
		<textarea fid="failedTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 任务延期区 -->
<div id="delayDiv" style="display:none">
	<div style="padding:5px;">
		延期日期：<input type="text" class="BigInput easyui-validatebox Wdate" required fid="delayTime"/><br/>
		延期原因：<br/>
		<textarea fid="delayTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 进度汇报区 -->
<div id="reportDiv" style="display:none">
	<div style="padding:5px;">
		进度：<input type="text" class="BigInput easyui-validatebox" fid="progress" required validType='integeBetweenLength[0,100]'/><br/>
		汇报内容：<br/>
		<textarea fid="reportTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 任务完成区 -->
<div id="finishDiv" style="display:none">
	<div style="padding:5px;">
		任务评分：<input type="text" class="BigInput easyui-validatebox" fid="score" required validType='integeBetweenLength[0,100]'/><br/>
		确认工时：<input type="text" class="BigInput easyui-validatebox" fid="relTimes" required validType='integeBetweenLength[0,100]'/><br/>
		任务总结：<br/>
		<textarea fid="finishTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

</body>
</html>