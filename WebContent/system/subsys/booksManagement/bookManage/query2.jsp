<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String status = TeeStringUtil.getString(request.getParameter("status"), ""); 
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
var status = "<%=status%>";
function doInit(){
	
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/sms/addSms.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			top.$.jBox.tip("发送成功!","info");
			window.location.reload();
		}else{
			top.$.jBox.tip(jsonRs.rtMsg,"error");
		}
	}
}

function checkForm(){
	
	var userListNames = document.getElementById("userListNames");
	    if (!userListNames.value) {
	  	  top.$.jBox.tip("收信人不能为空！","error");
	  	  userListNames.focus();
	  	  userListNames.select();
	  	  return false;
	    }
    return $("#form1").form('validate'); 
}

function backIndex(){

	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}


function search(){
	if(status=="0"){
		$("#title").html("待批借阅");
	}else if(status=="1"){
		$("#title").html("已批借阅");
	}else if(status=="2"){
		$("#title").html("未批借阅");
	}
	
	
	datagrid = $('#datagrid').datagrid({
		url : '<%=request.getContextPath() %>/bookManage/queryBookManage.action?status=<%=status%>',
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
		idField : 'smsBodySid',
		sortOrder: 'desc',
		striped: true,
		remoteSort: false,
		columns : [ [ 
		 {
			 field:'sid',
			 checkbox:false,
		     hidden:true
		},{
			 field:'borrowCount',
		     hidden:true
		},{
			 field:'status',
		     hidden:true
		},{
			 field:'bookStatus',
		     hidden:true
		},{
			 field:'amt',
		     hidden:true
		},{
			field : 'createDeptName',
			title : '部门',
			hidden:true
		},{
			field : 'bookName',
			title : '书名',
			width : 200
		},{
			field : 'bookNo',
			title : '图书编号',
			width : 100
		},{
			field : 'borrowDateStr',
			title : '借书日期',
			width : 100
		},{
			field : 'returnDateStr',
			title : '还书日期',
			width : 100
		},{
			field : 'buserName',
			title : '登记人',
			width : 100
		},{
			field : 'desc',
			title : '状态',
			width : 100
		},{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			var text = "";
			//alert(rowData.status+","+rowData.status);
			if(rowData.status==0 && rowData.bookStatus==0)  //借阅待批
			{

				text = "<a href=\"javascript:delete_manage("+rowData.sid+");\"> 删除 </a>";

			}
			if(rowData.status==1 && rowData.bookStatus==0)  //借阅已准
			{

				text = "<a href=\"javascript:return_manage("+rowData.sid+");\"> 还书</a>"; 
	
			}
			if(rowData.status==2 && rowData.bookStatus==0)  //借阅未准
			{
	
				text = " <a href=\"javascript:delete_manage("+rowData.sid+");\"> 删除 </a>";

			}
			if(rowData.status==1 && rowData.bookStatus==1)  //还书已准
			{

				text = " <a href=\"javascript:delete_flag("+rowData.sid+");\"> 删除 </a>";

			}
			if(rowData.status==2 && rowData.bookStatus==1)  //还书未准
			{
				text = "<a href=\"javascript:return_manage("+rowData.sid+");\"> 还书</a>"; 
			}
			return text;
		}}
		] ]
	});
}

function borrow(sid,bookNo,amt){
	var url1 = "<%=contextPath %>/bookManage/checkNum.action";
	var para1 =  {bookNo:bookNo,amt:amt};
	var jsonRs1 = tools.requestJsonRs(url1,para1);
	if(jsonRs1.rtState){
		if(jsonRs1.rtData <=0){
			alert("已全部借出，请刷新页面！");
			return false;
		}
		var url = "<%=contextPath %>/system/subsys/booksManagement/bookManage/borrow.jsp?sid="+sid+"&bookNo="+bookNo;
		window.open(url,'','height=400,width=550,status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,left=220,top=180,resizable=yes');
	}
}

function detail(sid){
	var url = "<%=contextPath %>/bookManage/getBookDetail.action?sid="+sid;
	var myleft=(screen.availWidth-500)/2;
	window.open(url,"read_notify","height=400,width=500,status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top=150,left="+myleft+",resizable=yes");
}

function toReturn(){
	window.location.reload();
}

function delete_manage(sid){
	var msg = "确定要删除该图书记录么";
	if (window.confirm(msg)){
		var url = "<%=contextPath%>/bookManage/updateManage.action";
		var para = {sid:sid,status:status,flag:1};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert("删除成功");
			window.location.reload();
		}
	}
}

function return_manage(sid){
	var msg = "确定要还该书么";
	if (window.confirm(msg)){
		var url = "<%=contextPath%>/bookManage/updateManage.action";
		var para = {sid:sid,status:status,flag:3};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert("还书成功");
			window.location.reload();
		}
	}
}

function delete_flag(sid){
	var msg = "确定要删除么";
	if (window.confirm(msg)){
		var url = "<%=contextPath%>/bookManage/updateManage.action";
		var para = {sid:sid,flag:2};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert("删除成功");
			window.location.reload();
		}
	}
}
</script>

</head>
<body onload="search();" style="margin:0px;overflow:hidden;">
	<div id="toolbar">
	
	</div>
	<table id="datagrid" fit="true" >
	</table>
</body>
</html>
 