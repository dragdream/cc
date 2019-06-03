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
	<title>下属计划</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var userId = "<%=userId%>";
	$(function(){
		query();
	});

	function query(){
		var param = {createTimeStrMin:'<%=createTimeStrMin%>',createTimeStrMax:'<%=createTimeStrMax%>'};
		
		$('#datagrid').datagrid({
			url : contextPath+'/monitor/scheduleList.action?userId='+userId,
			toolbar : '#toolbar',
			title : '',
			queryParams:param,
			singleSelect:true,
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
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
				field : 'title',
				title : '计划名称',
				width :150,
				formatter:function(data,rowData){
					return "<a href='javascript:void(0)' onclick=\"detail('"+rowData.uuid+"')\">"+data+"</a>";
				}
			}, {
				field : 'type',
				title : '计划类型',
				width :60,
				formatter:function(data,rowData){
					if(data==1){
						return "个人计划";
					}else if(data==2){
						return "部门计划";
					}else if(data==3){
						return "公司计划";
					}
				}
			} ,{
				field : 'rangeType',
				title : '时间范围',
				width :60,
				formatter:function(data,rowData){
					if(data==1){
						return "日计划";
					}else if(data==2){
						return "周计划";
					}else if(data==3){
						return "月计划";
					}else if(data==4){
						return "季度计划";
					}else if(data==5){
						return "半年计划";
					}else if(data==6){
						return "年计划";
					}
				}
			},{
				field : 'timeRangeDesc',
				title : '时间',
			    width :260,
			},{
				field : 'flag',
				title : '状态',
				width :70,
				formatter:function(data,rowData){
					if(data==0){
						return "进行中";
					}else if(data==1){
						return "已完成";
					}else if(data==2){
						return "超时完成";
					}
				}
			},{
				field : 'crTime',
				title : '创建时间',
				width:100
			}
			] ],
			onLoadSuccess:function(){
				$('#datagrid').datagrid("unselectAll");
			}
		});
	}
	
	function detail(scheduleId){
		openFullWindow(contextPath+"/system/subsys/schedule/manage/view.jsp?scheduleId="+scheduleId);
	}
	
	</script>
</head>
<body style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_work.png">
		<span class="title">工作计划</span>
	</div>
	<div class = "right fr clearfix">
		<input  type="button"  class="btn-win-white fl"  onclick="window.location='index.jsp'" value="返回"/>
    </div>
</div>
</body>
</html>