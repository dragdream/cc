<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%@ include file="/header/upload.jsp" %>
<% 
	String flowId = TeeStringUtil.getString(request.getParameter("flowId"), "");
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>办理中工作</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		query();
	});

	function doPageHandler(){
		query();
	}
	
	function query(){
		var para =  tools.formToJson($("#form")) ;
		var opts = [
				{field:'v.bt',
					title:'公文标题',
					width:100,
					sortable:true,
					formatter:function(a,data,c){
						return "<a href='javascript:void(0)' onclick=\"detail('"+data.uuid+"')\">"+data.bt+"</a>";
					}
				},
				{field:'v.zh',
					title:'文号',
					width:50,
					sortable:true,
					formatter:function(a,data,c){
						return data.zh;
					}
				},
				{field:'v.sendUser',
					title:'传阅人',
					width:50,
					sortable:true,
					formatter:function(a,data,c){
						return data.sendUserName;
					}
				},
				{field:'v.sendTime',
					title:'传阅时间',
					width:50,
					sortable:true,
					formatter:function(a,data,c){
						return getFormatDateStr(data.sendTime,"yyyy-MM-dd HH:mm");
					}
				},{field:'_manage',
					title:'操作',
					ext:'@操作',
					width:60,
					formatter:function(value,rowData,rowIndex){
						var render = "";
						render+="<a href='javascript:void(0)' onclick=\"detail('"+rowData.uuid+"')\" >查看</a>";
						return render;
					}
				}];

		datagrid = $('#datagrid').datagrid({
			url:'<%=contextPath%>/doc/DaiYue.action?type=0&flowId=<%=flowId%>',//待阅
			toolbar : '#toolbar',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			queryParams:para,
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: true,
			singleSelect:true,
			columns:[opts],
			pagination:true,
			onLoadSuccess:function(){
				$('#datagrid').datagrid("unselectAll");
			}
		});
	}
	
	function detail(uuid){
		tools.requestJsonRs(contextPath+"/doc/updateViewFlag.action?uuid="+uuid);
		$("#datagrid").datagrid("reload");
		var url = contextPath+"/system/subsys/doc/daiyue/print/index.jsp?uuid="+uuid;
		openFullWindow(url,"公文详情");
	}
	
	</script>
</head>
<body fit="true">

<table id="datagrid" fit="true"></table>
<div id="toolbar" style="padding-bottom: 5px;padding-top: 5px;">

	<form id="form">
		<table style="width:90%">
			<tr>
				<td width="80px;">公文标题：</td>
				<td width="200px;"><input type="text" name="bt" style="height: 25px;" class="BigInput"/></td>
				<td width="50px;">文号：</td>
				<td width="200px;"><input type="text" name="zh"  style="height: 25px;" class="BigInput"/></td>
				<td><input style="width: 45px;height: 25px;" type="button"  onclick="query()" class="btn-win-white" value="查询"/></td>
			</tr>
		</table>
	</form>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">确认要拒签 <span id="btName"></span> </h4>
      </div>
      <div class="modal-body">
        	<b>拒签意见：</b>
        	<textarea id="remark" class="BigTextarea" style="width:500px;height:100px"></textarea>
        	<input type="hidden" id="uuid" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="doNotRec()">确定</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>