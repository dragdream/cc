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

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	 // $('#searchDiv').modal('hide');
}


function search(){
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
		bsWindow(url,"借书登记",{width:"460", height:"260",buttons:[{name:"确定",classStyle:"btn-alert-gray"},{name:"关闭",classStyle:"btn-alert-gray"}],
			submit:function(v,h){
		    var cw = h[0].contentWindow;
		    if(v=="确定"){
			    cw.doSave();
			    $.MsgBox.Alert_auto("登记成功");
			    window.location.reload();
			    return true;
		     }
		    else if(v=="关闭"){
		    	return true;
		    }
		  }}); 
		//window.open(url,'','height=400,width=550,status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,left=220,top=180,resizable=yes');
	}
}

function detail(sid){
	var url = "<%=contextPath %>/bookManage/getBookDetail.action?sid="+sid;
	var myleft=(screen.availWidth-500)/2;
	openFullWindow(url,"read_notify","height=600,width=800,status=1,toolbar=no,menubar=no,location=no,scrollbars=yes,top=150,left="+myleft+",resizable=yes");
}

function toReturn(){
	window.location.reload();
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
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow-x:hidden;">
<table id="datagrid" fit="true" ></table>

<div id="toolbar">
	
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
							  
							 
						      <td nowrap class="TableData"  >
								 <input id="button" type="button" value="查询" onclick="doSearch();" class="btn-win-white fr"/></td><td>
							  	<input type="reset" class="btn-win-white fr" value="清空">
							  </td>
						   </tr>
						  </table>
	</form>
</div>
</body>
</html>
 