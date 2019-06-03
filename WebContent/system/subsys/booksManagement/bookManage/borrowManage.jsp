<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<title>借书确认</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>




<script type="text/javascript">
var datagrid;


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


function doInit(){
    datagrid = $('#datagrid').datagrid({
        url : '<%=request.getContextPath() %>/bookManage/borrowManage.action',
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
            field : 'buserName',
            title : '借书人',
            width : 100
        },{
            field : 'bookName',
            title : '书名',
            width : 200
        },{
            field : 'bookNo',
            title : '编号',
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
            field : 'ruserName',
            title : '登记人',
            width : 100
        },{
            field : 'borrowRemark',
            title : '备注',
            width :100
        },{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
            var text = "";
        
             text += "<a href='#' onclick=\"allManage('"+rowData.sid+"','"+rowData.bookNo+"',0)\">还书</a>&nbsp;";
            return text;
        }}
        ] ]
    });
}

function allManage(sid,bookNo,flag){
    var msg = "确定要还该书么";
    if (window.confirm(msg)){
        var url = "<%=contextPath%>/bookManage/allManage.action";
        var para = {sid:sid,bookNo:bookNo,flag:flag};
        var jsonRs = tools.requestJsonRs(url,para);
        if(jsonRs.rtState){
            alert("操作成功");
            window.location.reload();
        }
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

function borrowSign(){
    window.location.href = "<%=contextPath %>/bookManage/borrowSign.action";
}
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow:hidden;">
<div id="toolbar" class="datagrid-toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">借书管理</span>
	</div>
	 <div style="padding:10px">
	 	&nbsp;<input type="button" value="借书登记" onclick="borrowSign();" class="btn btn-primary"/>
	 </div>
</div>
<table id="datagrid" fit="true" ></table>
</body>
</html>
 