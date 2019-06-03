<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>未接收工作</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getHandableFlowType2SelectCtrl.action"});
		query();
	});

	function doPageHandler(){
		query();
	}

	function doHandler(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
		window.openFullWindow(contextPath +'/system/core/workflow/flowrun/prcs/index.jsp?'+para,"流程办理");
	}

	function doDelete(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		if(window.confirm("确认删除此工作吗？")){
			var url = contextPath+"/workflowManage/delRun.action";
			var json = tools.requestJsonRs(url,{runId:runId});
			if(json.rtState){
				query();
			}
		}
	}

	function doDelegate(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		var url = contextPath+"/system/core/workflow/flowrun/prcs/delegate.jsp?frpSid="+frpSid;
		top.$.jBox.open("iframe:"+url,"流程委托",520,270,{buttons:[],closed:function(){
			$('#datagrid').datagrid("reload");
			top.$.jBox.close(true);
		}});
	}

	function doExport(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		$("#frame0").attr("src",contextPath+"/flowRun/exportFlowRun.action?runId="+runId+"&view=1&frpSid="+frpSid);
	}

	function query(){
		var para =  tools.formToJson($("#form")) ;
		var opts = [
			{title:'流水号',
				field:'RUN_ID',
				ext:'@流水号',
				sortable:true,
				formatter:function(a,data,c){
					return data.runId;
				}
			},
			{field:'FLOW_NAME',
				title:'流程类型',
				ext:'@流程类型',
				sortable:true,
				formatter:function(a,data,c){
					var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+data.flowId;
					return "<a href='"+url+"' target='_blank'>"+data.flowName+"</a>";
				}
			},
			{field:'RUN_NAME',
				title:'流程名称',
				ext:'@流程名称',
				sortable:true,
				formatter:function(a,data,c){
					var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+data.runId+"&view=1&frpSid="+data.frpSid;
					return "<a href='"+url+"' target='_blank'>"+data.runName+"</a>";
				}
			},
			{field:'BEGIN_PERSON',
				title:'发起人',
				ext:'@发起人',
				formatter:function(a,data,c){
					return "<a target='_blank'>"+data.beginPerson+"</a>";
				}
			},
			{field:'PRCS_DESC',
				title:'步骤与流程图',
				ext:'@步骤与流程图',
				formatter:function(a,data,c){
					var url = contextPath+"/system/core/workflow/flowrun/flowview/index.jsp?runId="+data.runId+"&flowId="+data.flowId;
					return "<a href='"+url+"' target='_blank'>"+data.prcsDesc+"</a>";
				}
			},
			{field:'frp.CREATE_TIME',
				title:'创建时间',
				ext:'@时间',
				sortable:true,
				formatter:function(a,data,c){
					return "<a>"+data.createTime+"</a>";
				}
			},{field:'_manage',
				title:'操作',
				ext:'@操作',
				formatter:function(value,rowData,rowIndex){
					var render = "";
					if(rowData.prcsHandle){
						render+="<a href='javascript:void(0)' onclick=\"doHandler('"+rowIndex+"')\" ><b>会签</b></a>";
					}
					if(rowData.opHandle){
						render+="&nbsp;<a href='javascript:void(0)' onclick=\"doHandler('"+rowIndex+"')\" ><b>主办</b></a>";
					}
					if(rowData.delegate){
						render+="&nbsp;<a href='javascript:void(0)' onclick=\"doDelegate('"+rowIndex+"')\" >委托</a>";
					}
					if(rowData.doExport){
						render+="&nbsp;<a href='javascript:void(0)' onclick=\"doExport('"+rowIndex+"')\" >导出</a>";
					}
					
					return render;
				}
			}
		];
		
		var columns = [
						
		];

		var flowId = $("#flowId").val();
		//动态拼写列结构
		if(flowId!="0" && flowId!=""){
			var json = tools.requestJsonRs(contextPath+"/flowType/get.action",{flowTypeId:flowId});
			var queryFieldModel = json.rtData.queryFieldModel;
			queryFieldModel = queryFieldModel.substring(1,queryFieldModel.length-1);
			var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:flowId});
			var list = json.rtData;
			var sp = queryFieldModel.split(",");
			for(var i=0;i<sp.length;i++){
				if(sp[i].indexOf("@")!=-1){
					for(var j=0;j<opts.length;j++){
						if(opts[j].ext==sp[i]){
							columns.push(opts[j]);
							break;
						}
					}
				}else{
					for(var j=0;j<list.length;j++){
						if(list[j].title==sp[i]){
							columns.push({
								field:list[j].name,
								title:list[j].title
							});
							break;
						}
					}
				}
			}
		}else{
			for(var j=0;j<opts.length;j++){
				columns.push(opts[j]);
			}
		}
		
		
		datagrid = $('#datagrid').datagrid({
			url:'<%=contextPath%>/workflow/getNoReceivedWorks.action',
			toolbar : '#toolbar',
			queryParams:para,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: true,
			singleSelect:true,
			columns:[columns],
			pagination:true
		});
	}
	
	</script>
</head>
<body fit="true">

<table id="datagrid" fit="true"></table>
<div id="toolbar">
<form id="form" style="padding:10px">
		<table class="TableBlock" style="width:90%">
			<tr>
				<td class="TableData TableBG">所属流程：</td>
				<td class="TableData"><input type="text" id="flowId" name="flowId" class="BigInput"/></td>
				<td class="TableData TableBG">流水号：</td>
				<td class="TableData"><input type="text" name="runId" class="BigInput" style="width:40px"/></td>
				<td class="TableData TableBG">流程名称：</td>
				<td class="TableData">
					<input type="text" name="runName"  class="BigInput"/>
					&nbsp;&nbsp;
					<button type="button"  onclick="query()" class="btn btn-success"><b>查询</b></button>
					<button type="button"  onclick="window.location = '/system/core/workflow/flowrun/createNewWork.jsp';" class="btn btn-primary"><b>新建流程</b></button>
				</td>
			</tr>
		</table>
</form>
</div>
<iframe id="frame0" style="display:none"></iframe>
</body>
</html>