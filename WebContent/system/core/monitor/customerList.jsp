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
	<title>下属客户</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var userId = "<%=userId%>";
	$(function(){
		query();
	});

	function query(){
		var param = {createTimeStrMin:'<%=createTimeStrMin%>',createTimeStrMax:'<%=createTimeStrMax%>'};
		
		$('#datagrid').datagrid({
			url : contextPath+'/monitor/customerList.action?userId='+userId,
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
				field : 'customerName',
				title : '客户名称',
				width:200,
				formatter:function(data,rowData){
					return "<a href='javascript:void(0)' onclick=\"detail('"+rowData.sid+"')\">"+data+"</a>";
				}
			}, {
				field : 'companyAddress',
				title : '公司地址',
				width:150,
				
			} ,{
				field : 'companyFax',
				title : '传真',
				width:150,
			},{
				field : 'companyUrl',
				title : '网址',
				width:150,
			},{
				field : 'companyPhone',
				title : '联系电话',
				width:120,
			},{
				field : 'companyMobile',
				title : '移动电话',
				width:120,
			},{
				field : 'companyEmail',
				title : '邮箱',
				width:120,
			},{
				field : 'companyQQ',
				title : 'QQ',
				width:120,
			},{
				field : 'addTime',
				title : '创建时间',
				width:150,
			}
			] ],
			onLoadSuccess:function(){
				$('#datagrid').datagrid("unselectAll");
			}
		});
	}
	
	function detail(id){
		openFullWindow(contextPath+"/system/subsys/crm/core/customInfo/view.jsp?sid="+id);
	}
	
	
	</script>
</head>
<body style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class ='title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_work.png">
		<span class="title">客户</span>
	</div>
	<div class = "right fr clearfix">
		<input  type="button"  class="btn-win-white fl"  onclick="window.location='index.jsp'" value="返回"/>
    </div>
</div>
</body>
</html>