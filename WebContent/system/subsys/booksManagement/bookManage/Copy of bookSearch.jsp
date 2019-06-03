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
		url : '/oaop/bookManage/searchBook.action',
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
			 field:'borrowCount',
		     hidden:true
		},{
			 field:'amt',
		     hidden:true
		},{
			field : 'createDeptName',
			title : '部门',
			width : 100
		},{
			field : 'bookName',
			title : '书名',
			width : 200
			//formatter : function(value, rowData, rowIndex) {
			//	return rowData;
			//}
		},{
			field : 'bookNo',
			title : '编号',
			width : 100
		},{
			field : 'bookTypeName',
			title : '类别',
			width : 100
		},{
			field : 'author',
			title : '作者',
			width : 100
		},{
			field : 'pubHouse',
			title : '出版社',
			width : 100
		},{
			field : 'area',
			title : '存放地点',
			width :100
		},{
			field : 'lendDesc',
			title : '借阅状态',
			width : 200
		},{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			var text = "";
			if((rowData.amt - rowData.borrowCount)>0){
				text += "<a href='#' onclick=\"borrow('"+rowData.sid+"','"+rowData.bookNo+"','"+rowData.amt+"')\">借阅</a>&nbsp;";
			}
			text += "<a href='#' onclick=\"detail('"+rowData.sid+"')\">详情</a>&nbsp;";
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
	window.open(url,"read_notify","height=600,width=800,status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top=150,left="+myleft+",resizable=yes");
}

function toReturn(){
	window.location.reload();
}
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow-x:hidden;">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">图书查询</span>
</div>
<br/>






<form action=""  method="post" name="form1" id="form1">

<table align="center" width="70%" class="TableBlock">
 	<tr>
	    <td nowrap class="TableData" width="50" >图书类别：<font style='color:red'></font></td>
	    <td class="TableData"  colspan="3" >
	    	<select id="bookTypeId" name="bookTypeId" class="BigSelect">
	    		<option value="all">全部</option>
    		    <c:forEach items="${bookTypeList}" var="bookTypeListSort" varStatus="bookTypeListStatus">
			        <option value="${bookTypeList[bookTypeListStatus.index].sid}">${bookTypeList[bookTypeListStatus.index].typeName}</option>
				</c:forEach>
       		 </select>
	     </td>
	  </tr>
	  <tr style="display:none;">
	    <td nowrap class="TableData" width="50" >借阅状态：<font style='color:red'></font></td>
	    <td class="TableData"  colspan="3" >
	    	<select id="lend" name="lend" class="BigSelect">
	    		<option value="all">全部</option>
	    		<option value="0">未借出</option>
	    		<option value="1">已借出</option>
       		 </select>
	     </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData"> 图书编号：<font style='color:red'></font></td>
	    <td class="TableData" colspan="3">
	        <select id="bookNo" name="bookNo" class="BigSelect">
                <option value="">全部</option>
                <c:forEach items="${bookList}" var="bookListSort" varStatus="bookListStatus">
                    <option value="${bookList[bookListStatus.index].bookNo}">${bookList[bookListStatus.index].bookNo}（书名：${bookList[bookListStatus.index].bookName}）</option>
                </c:forEach>
             </select>
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData"> 图书名称：<font style='color:red'></font></td>
	    <td class="TableData" >
	      <input type="text" name="bookName" id="bookName"  maxlength="300" class="BigInput easyui-validatebox" value="" >
	    </td>
	    <td nowrap class="TableData"> 图书作者：<font style='color:red'></font></td>
	    <td class="TableData" >
	      <input type="text" name="author" id="author"  maxlength="300" class="BigInput easyui-validatebox" value="" >
	    </td>
	  	</tr>
	  <tr>
	    <td nowrap class="TableData"> ISBN号：<font style='color:red'></font></td>
	    <td class="TableData" >
	      <input type="text" name="ISBN" id="ISBN"  maxlength="300" class="BigInput easyui-validatebox" value="" >
	    </td>
	    <td nowrap class="TableData"> 出版社：<font style='color:red'></font></td>
	    <td class="TableData" >
	      <input type="text" name="pubHouse" id="pubHouse" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
	    </td>
	  </tr>
 	 <tr>
	    <td nowrap class="TableData"> 存放地点：<font style='color:red'></font></td>
	    <td class="TableData" >
	      <input type="text" name="area" id="area" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
	    </td>
	    <td nowrap class="TableData" width="50" >排序字段：<font style='color:red'></font></td>
	    <td class="TableData" >
	    	<select id="order" name="order" class="BigSelect">
	    		<option value="bumen">部门</option>
				<option value="leibie">类别</option>
				<option value="shuming">书名</option>
				<option value="zuozhe">作者</option>
				<option value="chubanshe">出版社</option>
				<option value="tushubianhao">图书编号</option>
       		 </select>
	     </td>
	 </tr>
     <tr align="center">
      <td nowrap class="TableData" colspan="4" >
		 <input id="button" type="button" value="查询" onclick="search();" class="btn btn-success"/>
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
 