<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userId = request.getParameter("userId");
	String createTimeStrMin = request.getParameter("createTimeStrMin");
	String createTimeStrMax = request.getParameter("createTimeStrMax");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>下属任务</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var userId = "<%=userId%>";
	$(function(){
		query();
	});

	function query(){
		var param = {createTimeStrMin:'<%=createTimeStrMin%>',createTimeStrMax:'<%=createTimeStrMax%>'};
		
		$('#datagrid').datagrid({
			url : contextPath+'/monitor/taskList.action?userId='+userId,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar : '#toolbar',
			title : '',
			queryParams:param,
			singleSelect:true,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			/* pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns : [ [ {
				field : 'taskTitle',
				title : '任务名称',
				width : 250,
				formatter:function(data,rowData){
					return "<a href='javascript:void(0)' onclick=\"detail('"+rowData.sid+"')\">"+data+"</a>";
				}
			}, {
				field : 'rangeTimes',
				title : '评估工时',
				width : 125,
			} ,{
				field : 'relTimes',
				title : '实际工时',
				width : 125,
			},{
				field : 'status',
				title : '状态',
				width : 200,
				formatter:function(data,rowData){
					//渲染当前状态
					var statusDesc;
					var statusColor = "";
					var status = data;
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
					return "<span style='color:"+statusColor+"'>"+statusDesc+"</span>";
				}
			},{
				field : 'progress',
				title : '当前进度',
				width : 200,
				formatter:function(data,rowData){
					return data+"%";
				}
			},{
				field : 'score',
				title : '任务得分',
				width : 200,
				formatter:function(data,rowData){
					return data;
				}
			},{
				field : 'createTime',
				title : '创建时间',
				width:200,
			}
			] ],
			onLoadSuccess:function(){
				$('#datagrid').datagrid("unselectAll");
			}
		});
	}
	
	function detail(id){
		openFullWindow(contextPath+"/system/subsys/cowork/view.jsp?taskId="+id);
	}
	
	
	</script>
</head>


<body style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class ='title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_work.png">
		<span class="title">任务</span>
	</div>
	<div class = "right fr clearfix">
		<input  type="button"  class="btn-win-white fl"  onclick="window.location='index.jsp'" value="返回"/>
    </div>
</div>
</body>
</html>