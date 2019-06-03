<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String status = request.getParameter("status");
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
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileManage.js?v=1"></script>

<title>会议上报列表</title>
</head>
<script>
//初始化
var status="<%=status%>";
var rootFolderPriv = "0";
var statusStr="";
function doInit(){
	if(status=="0"){
		statusStr="回复数";
	}else if(status=="1"){
		statusStr="阅读数";
	}else if(status=="2"){
		statusStr="下载数";
	}else if(status=="3"){
		statusStr="评分数";
	}
	getList();
}

//获取分类列表数据
function getList(){
	var i=0;
	var para = {status:0,status2:status} ;
	$("#treeGrid").datagrid({
		    url: contextPath+'/fileNetdisk/getFileNetdiskPage2.action',
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
      				field:'fileName',
      				title:'名称',
      				width:360
      			},{
      				field:'fileSize',
      				title:'大小',
      				width:120,
      				formatter : function(value, rowData, rowIndex) {
      				  if(value){
      				    return value;
      				  }else{
      				    return "-";
      				  }
      				}
      		    },{
      		    	field:'createTimeStr',
      		    	title:'创建时间',
      		    	width:200,
      				formatter : function(value, rowData, rowIndex) {
      					if(rowData.notLogin && rowData.notLogin == '1'){
      						return "<font color='gray'> " + value + "</font>";
      					}else if(rowData.passwordIsNUll == '1'){
      						return "<font color='red'> " + value + "</font>";
      					}else{
      						return value;
      					}
      				}
      		    },{
      		    	field:'createrStr',
      		    	title:'创建人',
      		    	width:100
      		    },{
      		    	field:'countNum',
      		    	title:statusStr,
      		    	width:100
      		    },{
      		    	field:'_optmanage',
      		    	title:'操作',
      		    	width:120,
      				formatter : function(value, rowData, rowIndex) {
      					var optStr = "";
      					rootFolderPriv = getFilePrivValueBySid(rowData.parentFileSid);
      					if(rowData.filetype == '1' ){//文件
      						optStr = "<a href='javascript:void(0);' onclick='showContentFunc(\"" +rowData.sid + "\",\"" + rowData.parentFileSid + "\",\"" + rootFolderPriv + "\");'>详情 </a>&nbsp;";
      					}
      					return "<div align='center'>" + optStr + "</span>";
      				} 
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