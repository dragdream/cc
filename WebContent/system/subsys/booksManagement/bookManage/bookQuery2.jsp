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
	booList();
	bookType();
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
	 // $('#searchDiv').modal('hide');
}
function booList(){
	var url = "<%=contextPath%>/bookManage/booList.action";
	var jsonRs = tools.requestJsonRs(url);
	var html="<option value=''>请选择</option>";
	if(jsonRs.rtState){
		var data=jsonRs.rtData;
		console.info(data);
		for(var i=0;i<data.length;i++){
			html+="<option value='"+data[i].bookNo+"'>"+data[i].bookNo+"("+data[i].bookName+")</option>";
		}
		$("#bookNo").html(html);
	}
}
function bookType(){
	var url = "<%=contextPath%>/bookManage/bookType.action";
	var jsonRs = tools.requestJsonRs(url);
	var html="<option value='all'>全部</option>";
	if(jsonRs.rtState){
		var data=jsonRs.rtData;
		console.info(data);
		for(var i=0;i<data.length;i++){
			html+="<option value='"+data[i].sid+"'>"+data[i].typeName+"</option>";
		}
		$("#bookTypeId").html(html);
	}
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
	var url = "<%=contextPath %>/system/subsys/booksManagement/bookInfo/addOrUpdate2.jsp?sid="+sid;
	bsWindow(url,"编辑图书",{width:"650", height:"400",buttons:[{name:"保存",classStyle:"btn-alert-gray"},{name:"关闭",classStyle:"btn-alert-gray"}],
		submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
		    var a=cw.doSaveOrUpdate();
		    if(a){
		       return true;
		    }
		    //window.location.reload();
	     }
	    else if(v=="关闭"){
	    	return true;
	    }
	  }}); 
	//window.location.href = url;
}

function toReturn(){
	window.location.reload();
}

function newBook(){
	var url = contextPath + "/system/subsys/booksManagement/bookInfo/addOrUpdate2.jsp?sid=0";
	bsWindow(url,"新建图书",{width:"650", height:"400",buttons:[{name:"保存",classStyle:"btn-alert-gray"},{name:"关闭",classStyle:"btn-alert-gray"}],
		submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
		    var a=cw.doSaveOrUpdate();
		    if(a){
		       return true;
		    }
		    //window.location.reload();
	     }
	    else if(v=="关闭"){
	    	return true;
	    }
	  }}); 
	<%-- window.location.href = '<%=contextPath %>/bookManage/addBookInfo.action'; --%>
}
</script>

</head>
<body onload="doInit()" style="margin:10px;overflow-x:hidden;">
<table id="datagrid" fit="true" ></table>
<div id="toolbar">
   <div class="fl left">
         <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/icon_我的工作.png">
		 <span class="title">图书管理 </span>	 
      </div>
      <div class="fr right" style="margin-right: 10px">
		<input type="button" value="添加图书" onclick="newBook();" class="btn-win-white fr"/>
	 </div>
         <span class="basic_border"></span>
         <br/>
<div>
	<form id="form1" name="form1">
						<table align="center" class="">
						
						
						  <tr>
						     <td nowrap class="TableData"> 图书名称：</td>
							    <td class="TableData" >
							      <input type="text" name="bookName" id="bookName" style="width: 150px;height:20px" class="BigInput">&nbsp;&nbsp;&nbsp;&nbsp;
							    </td>
							    <td nowrap class="TableData"> 图书作者:</td>
							    <td class="TableData" >
							      <input type="text" name="author" id="author" style="width: 150px;height:20px" class="BigInput">&nbsp;&nbsp;&nbsp;&nbsp;
							    </td>
							  
							    <td nowrap class="TableData"> ISBN号:</td>
							    <td class="TableData" >
							      <input type="text" name="ISBN" id="ISBN" style="width: 150px;height:20px" class="BigInput" >&nbsp;&nbsp;&nbsp;&nbsp;
							    </td>
							    <td nowrap class="TableData"> 出版社：</td>
							    <td class="TableData" >
							      <input type="text" name="pubHouse" id="pubHouse" style="width: 150px;height:20px" class="BigInput">&nbsp;&nbsp;&nbsp;&nbsp;
							    </td>
						    </tr>
						 	<tr>
						 	
							    <td nowrap class="TableData"> 存放地点：</td>
							    <td class="TableData" >
							      <input type="text" name="area" id="area" style="width: 150px;height:20px" class="BigInput">
							    </td>
							    <td nowrap class="TableData">图书类别：</td>
							    <td class="TableData">
							    	<select id="bookTypeId" name="bookTypeId" style="width: 150px;height:20px" class="BigSelect">
							    		
						       		 </select>
							     </td>
							 
							    
							    
							    <td nowrap class="TableData" >排序字段：</td>
							    <td class="TableData" >
							    	<select id="order" name="order" style="width: 150px;height:20px" class="BigSelect" >
							    		<option value="bumen">部门</option>
										<option value="leibie">类别</option>
										<option value="shuming">书名</option>
										<option value="zuozhe">作者</option>
										<option value="chubanshe">出版社</option>
										<option value="tushubianhao">图书编号</option>
						       		 </select>
							     </td>
							  
							    <td nowrap class="TableData"> 图书编号：</td>
							    <td class="TableData" >
							        <select id="bookNo" name="bookNo" style="width: 150px;height:20px" class="BigSelect">
						             </select>
							    </td>
							    <td>
							       <input id="button" type="button" value="查询" onclick="doSearch();" class="btn-win-white fr"/></td><td>
		                           <input type="reset" class="btn-win-white fr" value="清空">
							    </td>
						   </tr>
						  </table>
	</form>
	</div>
	</div>
</div>
</body>
</html>
 