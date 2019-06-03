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
	search();
	
}

function backIndex(){
	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}

function search(){
	//$("#form1").hide();
	//$("#newButton").hide();
	//$("#toolbar").show();
	var para =  tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url : '<%=request.getContextPath() %>/bookManage/searchBook.action',
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
			text += "<a href='#' onclick=\"edit('"+rowData.sid+"','"+rowData.bookNo+"','"+rowData.amt+"')\">编辑</a>&nbsp;";
			text += "<a href='#' onclick=\"deleteBook('"+rowData.sid+"')\">删除</a>&nbsp;";
			return text;
		}}
		] ]
	});
}

function deleteBook(sid,bookNo,amt){
	var url1 = "<%=contextPath %>/bookManage/deleteBook.action";
	var para1 =  {sid:sid};
	var msg = "确定删除该书么";
    if (window.confirm(msg)){
		var jsonRs1 = tools.requestJsonRs(url1,para1);
		if(jsonRs1.rtState){
			alert("删除成功");
			$('#datagrid').datagrid('reload');
		}
    }
}

function edit(sid){
	var url = "<%=contextPath %>/bookManage/updateBookInfo.action?sid="+sid;
	window.location.href = url;
}

function toReturn(){
	window.location.reload();
}

function newBook(){
	window.location.href = '<%=contextPath %>/bookManage/addBookInfo.action';
}
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow-x:hidden;">
<table id="datagrid" fit="true" ></table>

<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1">图书管理</span>
				</td>
				<td align=right>
					<input type="button" value="新建图书" onclick="newBook();" class="btn btn-info"/>&nbsp;
					<input type="button" value="高级查询" onclick="" class="btn btn-info" data-toggle="modal" data-target="#searchDiv"/>&nbsp;
				</td>
			</tr>
		</table>
		
	</div>
	<br>
	<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title" id="myModalLabel">查询条件</h4>
					</div>
					<div class="modal-body">
						<table align="center" width="70%" class="TableBlock" id = "searchInput">
						 	<tr>
							    <td nowrap class="TableData" width="50" >图书类别：<font style='color:red'></font></td>
							    <td class="TableData"  colspan="" >
							    	<select id="bookTypeId" name="bookTypeId" class="BigSelect">
							    		<option value="all">全部</option>
						    		    <c:forEach items="${bookTypeList}" var="bookTypeListSort" varStatus="bookTypeListStatus">
									        <option value="${bookTypeList[bookTypeListStatus.index].sid}">${bookTypeList[bookTypeListStatus.index].typeName}</option>
										</c:forEach>
						       		 </select>
							     </td>
							     <td nowrap class="TableData" width="50" >借阅状态：<font style='color:red'></font></td>
							    <td class="TableData"  colspan="" >
							    	<select id="lend" name="lend" class="BigSelect">
							    		<option value="all">全部</option>
							    		<option value="0">未借出</option>
							    		<option value="1">已借出</option>
						       		 </select>
							     </td>
							  </tr>
							  <tr>
							    <td nowrap class="TableData"> 图书名称：<font style='color:red'></font></td>
							    <td class="TableData" colspan="">
							      <input type="text" name="bookName" id="bookName" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
							    </td>
							    <td nowrap class="TableData"> 图书编号：<font style='color:red'></font></td>
							    <td class="TableData" colspan="">
							      <input type="text" name="bookNo" id="bookNo" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
							    </td>
							  </tr>
							  <tr>
							    <td nowrap class="TableData"> 图书作者：<font style='color:red'></font></td>
							    <td class="TableData" colspan="">
							      <input type="text" name="author" id="author" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
							    </td>
							    <td nowrap class="TableData"> ISBN号：<font style='color:red'></font></td>
							    <td class="TableData" colspan="">
							      <input type="text" name="ISBN" id="ISBN" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
							    </td>
							  </tr>
							  <tr>
							    <td nowrap class="TableData"> 出版社：<font style='color:red'></font></td>
							    <td class="TableData" colspan="">
							      <input type="text" name="pubHouse" id="pubHouse" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
							    </td>
							    <td nowrap class="TableData"> 存放地点：<font style='color:red'></font></td>
							    <td class="TableData" colspan="">
							      <input type="text" name="area" id="area" size="" maxlength="300" class="BigInput easyui-validatebox" value="" >
							    </td>
							  </tr>
						     <tr>
							    <td nowrap class="TableData" width="50" >排序字段：<font style='color:red'></font></td>
							    <td class="TableData"  colspan="3" >
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
							  <tr >
						      <td  class="TableData" colspan="4" align="center">
								 <input id="button" type="button" value="查询" onclick="doSearch();" class="btn btn-primary"/>
						      	<input type="reset" class="btn btn-primary" value="清空">
							  </td>
						   </tr>
						  </table>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

</body>
</html>
 