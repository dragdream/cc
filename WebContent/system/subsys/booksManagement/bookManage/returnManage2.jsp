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
<title>还书管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>




<script type="text/javascript">
var datagrid;
function doInit(){
	//datagrid();
	
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
	$("#form1").hide();
	$("#toolbar").show();
	var para =  tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url : '<%=request.getContextPath() %>/bookManage/searchReturn.action',
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
                field:'bookStatus',
                hidden:true
            },{
                field:'status',
                hidden:true
            },{
			   field : 'buserId',
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
               field : 'desc',
               title : '状态',
               width : 100
            },{
			   field : 'borrowRemark',
			   title : '备注',
			   width :100
			},{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
				var text = "";
				//alert(rowData.bookStatus+","+rowData.status);
				if((rowData.bookStatus=='0'&&rowData.status=='1')||(rowData.bookStatus=='1'&&rowData.status=='0')){
					text += "<a href='#' onclick=\"allManage('"+rowData.sid+"','"+rowData.bookNo+"','"+rowData.buserId+"','"+rowData.bookStatus+"',0)\">还书</a>&nbsp;";
				}
				if(rowData.bookStatus=='1'&&rowData.status=='1'){
					text += "<a href='#' onclick=\"allManage('"+rowData.sid+"','"+rowData.bookNo+"','"+rowData.buserId+"','"+rowData.bookStatus+"',5)\">删除记录</a>&nbsp;";
				}
				return text;
			}}
		] ]
	});
}
function allManage(sid,bookNo,userId,bookStatus,flag){
    var msg = "确定要还该书么";
    if(flag==5){
    	msg = "确定要删除记录么";
    }
    if (window.confirm(msg)){
        var url = "<%=contextPath%>/bookManage/allManage.action";
        var para = {sid:sid,bookNo:bookNo,flag:flag};
        var jsonRs = tools.requestJsonRs(url,para);
        if(jsonRs.rtState){
            alert("操作成功");
            $('#datagrid').datagrid('reload');
        }
    }
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
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow:hidden;">
<form action=""  method="post" name="form1" id="form1">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">还书管理</span>
</div>
<br/>
<table class="none-table">
        <tr>
          <td nowrap class="TableData">借书人：</td>
          <td class="TableData" colspan="3">
            <input type="hidden" name="postUserIds" id="postUserIds" value="">
              <input name="postUserNames" id="postUserNames" class="SmallStatic BigInput" wrap="yes" readonly></textarea>
              <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['postUserIds', 'postUserNames']);">添加</a>
              <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserIds').val('')';$('#postUserNames').val('');">清空</a>
          </td>
        </tr> 
   <tr>
    <td nowrap class="TableData" width="120">图书编号：</td>
    <td class="TableData">
        <select id="bookNo" name="bookNo" class="BigSelect">
           <option value="">全部</option>
           <c:forEach items="${bookList}" var="bookListSort" varStatus="bookListStatus">
               <option value="${bookList[bookListStatus.index].bookNo}">${bookList[bookListStatus.index].bookNo}（书名：${bookList[bookListStatus.index].bookName}）</option>
           </c:forEach>
        </select>
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData">借书日期：</td>
    <td nowrap class="TableData">
      从<input type="text" name="borrowDateBegin" id="borrowDateBegin" size="11" maxlength="19" class="BigInput" value="" onClick="WdatePicker()">
    至 <input type="text" name="borrowDateEnd" id="borrowDateEnd" size="11" maxlength="19" class="BigInput" value="" onClick="WdatePicker()">
    
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">状态：</td>
    <td nowrap class="TableData">
        <select id="status" name = "status" class="BigSelect">
            <option value = "all">全部</option>
            <option value = "1">已还</option>
            <option value = "0">未还</option>
        </select>
    </td> 
   </tr>

	<tr>
      <td nowrap class="TableData" colspan="4" >
		 <input id="button" type="button" value="查询" onclick="search();" class="btn btn-primary"/>
	  </td>
   </tr>
  </table>
</form>	
<div id="toolbar" class="datagrid-toolbar" style="display:none;"> 
	<div class="base_layout_top" style="position:static">
		&nbsp;<input type="button" value="返回" onclick="toReturn();" class="btn btn-default"/>
	</div>
	<br/>
</div>
<table id="datagrid" fit="true" ></table>
</body>
</html>
 