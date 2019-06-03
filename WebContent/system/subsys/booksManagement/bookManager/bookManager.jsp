<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>管理员设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">

function doInit(){
	search();
}
function search(){
	var para =  tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url : '<%=contextPath%>/bookManage/bookManagerList2.action',
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
			field : 'postUserNames',
			title : '管理员',
			width : 300
		},{
			field : 'postDeptNames',
			title : '所管部门',
			width : 500
		},{field:'_manage',title:'操作',width : 80,formatter:function(value,rowData,rowIndex){
			var text = "";
			text += "<a href='#' onclick=\"doSaveOrUpdate('"+rowData.sid+"')\">编辑</a>&nbsp;";
			text += "<a href='#' onclick=\"toDelete('"+rowData.sid+"')\">删除</a>&nbsp;";
			return text;
		}}
		] ]
	});
}
<%-- function setManager(){
	var url = "<%=contextPath%>/system/subsys/booksManagement/bookManager/editBookManager.jsp";
	location.href = url;
} --%>
function doSaveOrUpdate(sid){
	var url = contextPath + "/system/subsys/booksManagement/bookManager/editBookManager.jsp?sid="+sid;
	var title;
	if(sid>0){
		title="修改管理员权限";
	}else{
		title="添加管理员权限";
	}
	bsWindow(url,title,{width:"550", height:"300",buttons:[{name:"保存",classStyle:"btn-alert-gray"},{name:"关闭",classStyle:"btn-alert-gray"}],
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
function toDelete(id){
	var url = "<%=contextPath%>/bookManage/deleteBookManager.action";
	var para =  {sid:id};
	if(confirm('确实要删除该管理信息吗?')){	
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			location.reload();
		}
	}
}

function toEdit(id){
	var url = "<%=contextPath%>/system/subsys/booksManagement/bookManager/editBookManager.jsp?sid="+id;
	location.href = url;
}
</script>

</head>
<body onload="doInit();" style="margin:10px;overflow-x:hidden;">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
 <div class="fl left">
         <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/icon_我的工作.png">
		 <span class="title">管理员权限</span>	 
      </div>
	 <div class="fr right" style="margin-right: 10px">
		<input type="button" value="设置管理员权限" onclick="doSaveOrUpdate(0);" class="btn-win-white fr"/>
	 </div>
         <span class="basic_border"></span>
         <br/>
</div>
<%-- <form action=""  method="post" name="form1" id="form1">
<div id="toolbar">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">图书管理员</span>
</div>
</div>
<br>
	<table align="center" width="60%" >
		<tr>
			<td align="center"><input id="button" type="button" value="设置管理员" onclick="setManager();" class="btn btn-primary"/></td>
		</tr>
	    
    </table>
<br>
</form>
	<table align="center" width="60%" class="TableBlock">
	 	<tr class="TableHeader">
		    <td nowrap align="center" width="40%">管理员</td>
		    <td nowrap align="center" width="40%">所管部门</td>
		    <td nowrap align="center" width="20%">操作</td>
	    </tr>
	    <c:forEach items="${bookManagerList}" var="bookManagerListSort" varStatus="bookManagerListStatus">
	        <tr class="TableLine">
      			<td align="center" width="40%">${bookManagerList[bookManagerListStatus.index].postUserNames}</td>
      			<td align="center" width="40%">${bookManagerList[bookManagerListStatus.index].postDeptNames}</td>
     			<td align="center" nowrap width="20%">
     	    		<a href="javascript:toEdit('${bookManagerList[bookManagerListStatus.index].sid}');">编辑</a>
          			<a href="javascript:toDelete('${bookManagerList[bookManagerListStatus.index].sid}');">删除</a>
		      	</td>
		    </tr>
		</c:forEach>
    </table> --%>
</body>
</html>
