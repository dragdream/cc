<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	getAssetsType();
// 	getDeptParent();
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeFixedAssetsInfoController/datagrid.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'assetCode',title:'资产编号',width:100},
			{field:'assetName',title:'资产名称',width:200},
			{field:'typeName',title:'资产类别',width:150},
			{field:'deptName',title:'所属部门',width:200},
			{field:'assetVal',title:'资产原值',width:200},
			{field:'valideTimeDesc',title:'启用时间',width:200},
			{field:'keeperName',title:'保管人',width:100},
			{field:'useState',title:'状态',width:60 ,
				formatter:function(e,rowData,index){
					return rowData.useStateDesc;
				}
			},
			{field:'useStateDesc',title:'状态',width:10,hidden:true},

			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				return "<a href='#' onclick='edit("+index+")'>修改</a>"
						+ "&nbsp;&nbsp;<a href='#' onclick='detail("+index+")'>详情</a>"
						+ "&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function edit(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		top.$.jBox.tip("请选择需要修改的内容","info");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_edit.jsp?sid="+sid;
	location.href=url;
}

function del(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		top.$.jBox.tip("请选择需要删除的信息","info");
		return;
	}
	var sid = selection.sid;
	top.$.jBox.confirm("确认删除该资产信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/TeeFixedAssetsInfoController/delAssetsInfo.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"success");
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				top.$.jBox.tip(json.rtMsg,"error");
			}
		}
	});
}

function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		top.$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			for(var i=0;i<selections.length;i++){
			    var sid = selections[i].sid;
					var url = contextPath+"/TeeFixedAssetsInfoController/delAssetsInfo.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						top.$.jBox.tip(json.rtMsg,"success");
					}else{
						top.$.jBox.tip(json.rtMsg,"error");
					}
				}
			 datagrid.datagrid("unselectAll");
			 datagrid.datagrid("reload");
		}
	});
}
function detail(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		top.$.jBox.tip("请选择需要删除的信息","info");
		return;
	}
	var sid = selection.sid;
	openFullWindow(contextPath+"/system/core/base/fixedAssets/manage/fixedAssets_detail.jsp?sid="+sid,"资产详情",900,900);
}

function optDetail(sid){
	window.location.href = "<%=contextPath%>/system/core/base/fixedAssets/record/recordDetail.jsp?fixedAssetsId=" + sid; 
}


function getAssetsType(){
	var url =contextPath+"/TeeFixedAssetsCategoryController/datagrid.action"
	var json = tools.requestJsonRs(url);
	var typeNames = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<typeNames.length;i++){
		html+="<option value=\""+typeNames[i].sid+"\">"+typeNames[i].name+"</option>";
	}
	$("#typeId").html(html);
}
/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false,
				onAsyncSuccess:setDeptParentSelct
			};
		zTreeObj = ZTreeTool.config(config);
		//setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}

//根据条件查询
function doSearch(){
	/* var queryParams =datagrid.datagrid('options').queryParams; 
	  queryParams.asstesCode=$("#assetsCode").val();
	  queryParams.assetsName=$("#assetsName").val();
	  queryParams.deptId=$("#deptId").val();
	  queryParams.typeId=$("#typeId").val(); */
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $("#myModal").modal("hide");
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
			<span class="easyui_h1">固定资产管理</span>
		</div>
		<div style="padding:10px">
			<button class="btn btn-danger" onclick="delAll()">批量删除</button>
			<button class="btn btn-primary" onclick="$('#myModal').modal('show');"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
	 </div>
	<table id="datagrid" fit="true"></table>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">公文起草</h4>
      </div>
      <div class="modal-body">
      	<form id="form1" name="form1">
      	<table class="SearchTable" style="">
			<tr>
				<td>资产编号：</td>
				<td>
					<input class="BigInput" type="text" id = "assetsCode" name='assetsCode'/>
				</td>
				<td >资产名称：</td>
				<td>
					<input class="BigInput" type='text' id='assetsName' name='assetsName'/>
				</td>
			</tr>
			<tr>
				<td>资产类别：</td>
				<td colspan="3">
					<select class="BigSelect" id='typeId' name='typeId'></select>
<!-- 					&nbsp;&nbsp; -->
<!-- 					<input type="button" class="btn btn-primary" onclick="" value="查询"> -->
				</td>
			</tr>
		</table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="doSearch();">确定</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>