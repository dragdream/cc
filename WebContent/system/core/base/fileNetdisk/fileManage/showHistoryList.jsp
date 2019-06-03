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
		    url: contextPath+'/teeFileOptRecordController/getHistorys.action',
		    idField: 'sid',
	        toolbar:"#toolbar",
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	        pagination : true,
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
      				title:'操作人',
      				width:120
      		    },{
      		    	field:'month',
      		    	title:'操作类型',
      		    	width:100,
      		  	    formatter:function(value,rowData,rowIndex){
					var optType = "";
					if(rowData.optType =='1'){
						optType="新建";
					}else if(rowData.optType =='2'){
						optType="下载";
					}else if(rowData.optType =='3'){
						optType="重命名";
					}else if(rowData.optType =='4'){
						optType="删除";
					}else if(rowData.optType =='5'){
						optType="复制";
					}else if(rowData.optType =='6'){
						optType="移动";
					}else if(rowData.optType =='7'){
						optType="签阅";
					}else if(rowData.optType =='8'){
						optType="编辑";
					}
					return optType;
				}
      		    },{
      		    	field:'optContent',
      		    	title:'操作内容',
      		    	width:560
      		    },{
      		    	field:'createTimeStr',
      		    	title:'操作时间',
      		    	width:120
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