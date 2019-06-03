<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/datagrid-groupview.js"></script>
<script>
function doInit(){
	
	var datagridOpt = {
			url:contextPath+'/teeUserRoleTypeController/datagrid.action',
			pagination:false,
			singleSelect:true,
			pageSize:10000000,
			remoteSort:false,
			striped: true,
			border: false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			fitColumns:false,//列是否进行自动宽度适应
			columns:[[
					    {
							field : 'typeName',
							title : '分类名称',
							width : 220
						  },  {
							field : 'typeSort',
							title : '分类排序号',
							width : 100
						  },  {
							field : '操作',
							title : '操作',
							width : 100,
							formatter:function(data,rowData){
								return "<a href='javascript:void(0)' onclick=\"toAddOrUpdateType("+rowData.sid+","+rowData.typeSort+",'"+rowData.typeName+"')\">编辑</a>&nbsp;<a href='javascript:void(0)' onclick='deleteRoleType("+rowData.sid+")'>删除</a>";
							}
						  }
					] ]
		};
	
	$('#datagrid').datagrid(datagridOpt);
	
}

/**
 * 创建或者新建类型
 */
function toAddOrUpdateType(sid , typeSort,typeName){
	if(sid > 0){
		$('#productTypeModal').modal('toggle');
		$("#sid").val(sid);
		$("#typeSort").val(typeSort);
		$("#typeName").val(typeName);
	}else{
		//重置
		$("#sid").val(0);
		$("#form1")[0].reset(); 
	}
}

/*删除角色类型*/
function deleteRoleType(id){
	if(confirm("确定要删除此角色类型吗？删除后将不可恢复！")){
		var url = "<%=contextPath %>/teeUserRoleTypeController/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{'sid':id});
		if(jsonRs.rtState){
			$('#datagrid').datagrid("reload");
		}else{
			alert(jsonRs.rtMsg(confirm("当前管理层下有人员，不可以删除！")));
		}
	}
}

/**
 * 保存角色类型
 */
function doSaveType(){
	var url = "<%=contextPath %>/teeUserRoleTypeController/addOrUpdate.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		$('#productTypeModal').modal('toggle');
		$('#datagrid').datagrid("reload");
	}else{
		alert(jsonRs.rtMsg);
	}
}

</script>
</head>
<body onload="doInit()" >
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div style="padding:10px">
	<input type="button" class="btn btn-default" data-toggle="modal" data-target="#productTypeModal" value="新增分类"  onclick="toAddOrUpdateType(0);">
	</div>
</div>


<form id="form1" name="form1" method="post">
		<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width:550px;">
		    <div class="modal-content">
		    	 <div class="modal-header">
		        	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		       		<h4 class="modal-title" id="myModalLabel">新建/编辑角色类型</h4>
		   	
		      	</div> 
		      <div class="modal-body">
		         <table align="center" width="60%" class="TableBlock">
				      <tr>
				          <td nowrap class="TableData"> 角色类型名称：<font style='color:red'>*</font></td>
				          <td class="TableData" colspan="3">
				              <input type="text" name="typeName" id="typeName" size="20" maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
				          </td>
				      </tr>
				  
				    <tr>
					    <td nowrap class="TableData">排序号：</td>
					    <td nowrap class="TableData">
					    	<input type="text" name="typeSort" id="typeSort" class="BigInput easyui-validatebox "  size="8" maxlength="9" required="true" validType ='integeZero[]'></input>
					    </td>
				   </tr>
				   
				   </tr>
				     <tr>
				        <td nowrap class="TableData" colspan="4" align="center">
				            <button type="button" class="btn btn-primary" onclick="doSaveType();">保存</button>
				            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				        	<input type="hidden" name='sid' id='sid' value="0"></input> 
				        </td>
				     </tr>
				    </table>
		      </div>

		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div>
	 </form>
</body>
</html>