<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
    <%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>桌面模块设置</title>

	<script type="text/javascript" charset="UTF-8">
	//alert(userName);
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		var url = contextPath+"/portlet/datagrid.action";
		datagrid = $('#datagrid').datagrid({
			url : url,
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : false,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			singleSelect : true,
			/**
				这个id不能乱写，只能用sid
			*/
			idField : 'sid',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns : [ [ 
			 {
				title : '模块名称',
				field : 'modelTitle',
				width : 50,
				sortable:true
				
			},
			{
				title : '备注',
				field : 'remark',
				width : 100,
				sortable:true
				
			},
			{
				title : '状态',
				field : 'viewType',
				width : 50,
				sortable:true,
				formatter : function(value, rowData, rowIndex) {

					switch (value) {
					case 0:
						value = "<font color='red'>已停用</font>";  
						break;
					case 1:
						value = "<font color='green'>已启用</font>";  
						break;
					}
					return value;
				}
			},
			{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				return "<a href='#' onclick=\"addOrUpdate('"+rowData.sid+"')\">编辑</a>&nbsp;"+
					   "<a href='#' onclick=\"updateStatus('"+rowData.sid+"',1)\">启用</a>&nbsp;"+
					   "<a href='#' onclick=\"updateStatus('"+rowData.sid+"',0)\">停用</a>&nbsp;"+
					   "<a href='#' onclick=\"exportXml('"+rowData.sid+"')\">导出</a>&nbsp;"+
					   "<a href='#' onclick=\"del('"+rowData.sid+"')\">删除</a>&nbsp;";
			}}
			] ]
		});
		
	});

    function doInit(){
    	var result = '${result}';
    	//alert(aaa);
    	if(result=='1'){
    		alert("导入成功");
    	  	window.location = contextPath + "/system/core/system/sysPorlet/index.jsp";
    	}
      }
    
    function addOrUpdate(sid){
    	window.location = "addupdate.jsp?sid="+sid;
    }
    
    function updateStatus(sid,flag){
    	var json = tools.requestJsonRs(contextPath+"/portlet/updateStatus.action",{sid:sid,flag:flag});
    	window.location.reload();
    }
    
    function del(sid){
    	if(window.confirm("是否删除该模块？")){
    		var json = tools.requestJsonRs(contextPath+"/portlet/delete.action",{sid:sid});
        	window.location.reload();
    	}
    }
    
    function exportXml(sid){
    	$("#frame0").attr("src",contextPath+"/portlet/export.action?sid="+sid);
    }
    
    function doImport(obj){
		if(document.getElementById("file").value.indexOf(".xml")==-1){
			alert("仅能上传xml后缀名模板文件！");
			return false;
		}
		$("#uploadBtn").attr("value","上传中").attr("disabled","");
		return true;
	}
	
	function uploadSuccess(){
		alert("导入成功！");
		window.location.reload();
	}

	</script>
</head>
<body onload="doInit();">
	<div id="toolbar"> 
		 <div style="padding:10px">
		 	<button class="btn btn-success" type="button" onclick="addOrUpdate(0)">添加模块</button>
		 	<input id="importBtn" type="button" class="btn btn-default" value="导入" onclick="$('#uploadDiv').modal('show')"/>
		 </div>
	</div>
<table id="datagrid"></table>
<iframe style="display:none" id="frame0" name="frame0"></iframe>


<form id="uploadForm" onsubmit="return doImport()" target="frame0" action="<%=contextPath %>/portlet/import.action" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal fade" id="uploadDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">桌面模块导入</h4>
      </div>
      <div class="modal-body">
        <!-- 导入专用 -->
        	<span style="color:red">1.导入的格式为*.xml，且必须是兼容本系统的桌面模块文件格式。</span><br/><br/>
			<input style="border:0px" type="file" name="file" id="file"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" id="uploadBtn" >上传</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</body>
</html>