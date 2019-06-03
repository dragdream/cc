<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String userId = TeeStringUtil.getString(request.getParameter("userId"), ""); 
	String userName = TeeStringUtil.getString(request.getParameter("userName"), ""); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<title>图书查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var datagrid;
function doInit(){
	search();
}
function search(){
	var para =  tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url : '<%=contextPath%>/bookManage/bookType2.action',
		queryParams : para,
		toolbar : '#toolbar',
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		sortOrder: 'desc',
		striped: true,
		remoteSort: false,
		columns : [ [ 
		 {
			 field:'sid',
			 checkbox:false,
		     hidden:true
		},{
			field : 'typeName',
			title : '图书类别',
			width : 600
		},{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			var text = "";
			text += "<a href='#' onclick=\"addBookType('"+rowData.sid+"')\">编辑</a>&nbsp;";
			text += "<a href='#' onclick=\"toDelete('"+rowData.sid+"')\">删除</a>&nbsp;";
			return text;
		}}
		] ]
	});
}
function toDelete(id){
	var url = "<%=contextPath%>/bookManage/deleteType.action";
	var para =  {sid:id};
	if(confirm('确实要删除该图书分类吗?注：删除后会级联删除该分类下的所有图书，慎重！')){	
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			location.reload();
		}
	}
}
function addBookType(sid){
	var url = contextPath + "/system/subsys/booksManagement/bookType/bookType.jsp?sid="+sid;
	var title;
	if(sid>0){
		title="修改图书类别";
	}else{
		title="添加图书类别";
	}
	bsWindow(url,title,{width:"550", height:"150",buttons:[{name:"保存",classStyle:"btn-alert-gray"},{name:"关闭",classStyle:"btn-alert-gray"}],
		submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
		    cw.doSave();
		    window.location.reload();
		    return true;
	     }
	    else if(v=="关闭"){
	    	return true;
	    }
	  }}); 
}
</script>

</head>
<body onload="doInit()" style="margin:10px;overflow-x:hidden;">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
 <div class="fl left">
         <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/icon_我的工作.png">
		 <span class="title">图书管理 </span>	 
      </div>
	 <div class="fr right" style="margin-right: 10px">
		<input type="button" value="添加" onclick="addBookType(0);" class="btn-win-white fr"/>
	 </div>
         <span class="basic_border"></span>
         <br/>
</div>
</body>
</html>
 