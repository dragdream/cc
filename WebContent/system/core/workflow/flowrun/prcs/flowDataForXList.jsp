<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%
		int flowId =TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		String mappingStr = request.getParameter("mappingstr");
	    int itemId=TeeStringUtil.getInteger(request.getParameter("itemId"),0);
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>数据选择</title>
<script>

var flowId = <%=flowId%>;
var mappingStr = "<%=mappingStr%>";
var itemId="<%=itemId%>";
var para = {};
var maps;

function doInit(){
	var showFields="";
	//根据mappingStr获取其他显示字段
	var json = tools.requestJsonRs(contextPath+"/flowForm/getMappingFormItemsByFlowId.action",{mappingStr:mappingStr,flowId:flowId});
	maps=json.rtData;
	//获取表单上需要映射的控件
	var mappings=tools.string2JsonObj(mappingStr);
	if(mappings!=null&&mappings.length>0){
		for(var i=0;i<mappings.length;i++){
			showFields+=maps[mappings[i].title1]+",";
		}
	}
	if(showFields!=""&&showFields!=null){
		para["showFields"] = showFields.substring(0, showFields.length-1);
	}else{
		para["showFields"]="";
	}
	
	
	para["flowId"] = flowId;
	para["mappingStr"]=mappingStr;
	var opts = [{
		title : '流水号',
		field : 'RUN_ID',
		ext:'@流水号',
		sortable:true,
		width:50,
		formatter:function(value,rowData,rowIndex){
		var render = rowData.runId;
		return render;
	 	}
	},{
		field : 'RUN_NAME',
		title : '工作名称',
		ext:'@工作名称',
		width:200,
		sortable : true,
		formatter:function(value,rowData,rowIndex){
			var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+rowData.runId+"&view=1";
			return "<a href='javascript:void(0)' onclick=\"openFullWindow('"+url+"','工作详情')\">"+rowData.runName+"</a>";
		}
	}];
	
	//根据mappingStr获取其他显示字段
	var json = tools.requestJsonRs(contextPath+"/flowForm/getMappingFormItemsByFlowType.action",para);
	var mapFormItems=json.rtData;

	if(mapFormItems!=null&&mapFormItems.length>0){
		for(var i=0;i<mapFormItems.length;i++){
			var formItemModel = mapFormItems[i];
			//alert(formItemModel.name);
			opts.push({
				field:formItemModel.name,
				title:formItemModel.title,
				xtype:formItemModel.xtype,
				itemId:formItemModel.itemId,
				width:200,
			});
		}
	}
	
	
	$('#datagrid').datagrid({
		url:contextPath+"/workQuery/query.action?runNameOper=like2",
		queryParams:para,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:false,//列是否进行自动宽度适应
		columns:[opts]
	});
	
}

function selectIt(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		alert("请至少选择一项数据");
		return;
	}
	var mappings=tools.string2JsonObj(mappingStr);
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		for(var k in maps){
			if(selected[maps[k]]){
				if(mappings!=null&&mappings.length>0){
					for(var j=0;j<mappings.length;j++){
						if(mappings[j].title1==k){
							selected[mappings[j].title2]=selected[maps[k]];
						}
					}
				}
				
			}
		}
		xparent.addRow(itemId,selected);
	}
	
	CloseWindow();
	
}

function search(){
	var tmp = tools.formToJson($("#form1"));
	for(var key in tmp){
		para[key] = tmp[key];
	}
	
	$('#datagrid').datagrid("reload",para);
}

</script>
</head>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static;">
			<table style="width:100%">
				<tr>
					<td>
						<span class="easyui_h1">流程数据选择</span>
					</td>
					<td style="text-align:right">
						<button type="button" class="btn btn-info"  onclick="selectIt()">确定</button>
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div style="padding:10px">
			<form id="form1">
			    流水号：<input type="text" name="runId" id="runId" class="BigInput" />
			 &nbsp;&nbsp;
			   工作名称：<input type="text" name="runName" id="runName"  class="BigInput"/>
			   
			   <input type="button"  class="btn btn-default"  value="查询" onclick="search();" />
			</form>
		</div>
	</div>
</body>

</html>