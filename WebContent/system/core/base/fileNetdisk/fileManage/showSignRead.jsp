<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>会议上报列表</title>
</head>
<script>
//初始化
var sid="<%=sid%>";
function doInit(){
	getList(sid);
}

//获取分类列表数据
function getList(sid){
	var i=0;
	var para =  {sid:sid} ;
	$("#treeGrid").datagrid({
		    url: contextPath+'/TeeFileNetdiskReaderController/showSignReadDetail.action',
		    idField: 'sid',
	        toolbar:"#toolbar",
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	        pagination : false,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
	        border:false,
			queryParams:para,
			fit : true,
			fitColumns : false,
			nowrap : true,
			sortOrder: 'desc',
			striped: true,
			remoteSort: true,
			/* rownumbers: true, */
			singleSelect:false,
            columns:[[
                {
      				field:'count',
      				title:'序号',
      				width:50,
      				formatter:function(value,rowData,rowIndex){
      					i++;
						var render = "";
						render = i;
						return render;
					}
      			},{
      				field:'userName',
      				title:'姓名',
      				width:160
      		    },{
      		    	field:'deptName',
      		    	title:'所属部门',
      		    	width:160
      		    },{
      		    	field:'roleName',
      		    	title:'角色',
      		    	width:160
      		    },{
      		    	field:'signState',
      		    	title:'签阅状态',
      		    	width:160,
      		    	formatter:function(value,rowData,rowIndex){
      		    		var signState = "<font color='red'>未签阅</font>";
      					if(rowData.signState =='1'){
      						signState = "<font color=' #007500 '>已签阅</font>";
      					}
      					return signState;
      		    	}
      		    },{
      		    	field:'signTime',
      		    	title:'签阅时间',
      		    	width:200
      		    }
      		]]
        
	});
}

</script>

<body onload="doInit()">
<table id="treeGrid" fit="true"></table>
<div id="toolbar">
<form id="form" style="padding:5px">
	
</form>
</div>
<iframe id="frame0" style="display:none"></iframe>
</body>
</html>