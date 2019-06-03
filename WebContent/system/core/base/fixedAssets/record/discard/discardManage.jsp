<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script>
var datagrid;
function doInit(){
// 	getAssetsType();
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/teeFixedAssetsRecordController/getRecordDatagrid.action?optType=4",
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'assetCode',title:'资产编号',width:100,formatter:function(data,rowData){
				return "<a href='javascript:void(0)' onclick='detail("+rowData.runId+")'>"+data+"</a>";
			}},
			{field:'assetName',title:'资产名称',width:200},
			{field:'typeName',title:'资产类别',width:150},
			{field:'optReason',title:'报废原因',width:200},
			{field:'optDateDesc',title:'报废日期',width:200}
		]]
	});
}

function detail(runId){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=31","");
}

/**
 * 报修
 */
function apply(sid){
	 $.MsgBox.Confirm ("提示","是否发起资产报废申请？",function(){
		 createNewWork(42);
		});
}

/**
 * 领用
 */
function discardDetails(sid){
	var title = "固定资产报废";
	var url = contextPath + "/system/core/base/fixedAssets/record/discard/discard.jsp?fixedAssetsId=" + sid;
	bsWindow(url ,title,{width:"800",height:"300",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(json){
				datagrid.datagrid('reload');
			});
	
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
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
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
  <table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="/common/zt_webframe/imgs/xzbg/gdzc/icon_zcbf.png">&nbsp;&nbsp;
		<span class="title">资产报废</span>
	</div>
	<span class="basic_border" style="padding-top: 10px;"></span>
	<form  method="post" name="form1" id="form1" style="padding:5px;">
	 <div style="padding:0px;" id="searchDiv">
	 <table>
	  <tr>
	    <td class="TableData TableBG">资产编号：</td>
	    <td nowrap class="TableData" style="width: 180px;">
		   <input type="text" id = "assetCode" name='assetCode' style="height:25px;font-family: MicroSoft YaHei;"/>
	    </td>
	     <td class="TableData TableBG">资产名称：</td>
	    <td nowrap class="TableData" style="width: 180px;">
			<input class="BigInput" type='text' id='assetName' name='assetName' style="height:25px;font-family: MicroSoft YaHei;"/>
		</td>
	  
	     <td class="TableData TableBG">报废日期范围：</td>
		<td class="TableData">
		  <input type='text' id='time1' name='time1' readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'time2\')}'})" class="Wdate BigInput" style="height:25px"/>&nbsp;至&nbsp;
		  <input type='text' id='time2' name='time2' readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'time1\')}'})" class="Wdate BigInput"  style="height:25px"/>
		 </td>
		 <td style="padding-left: 10px;">
		  <input type="button" class="btn-win-white" onclick="doSearch();" value="查询" style="width:45px;height:25px;">&nbsp;
		  <input type="button" class="btn-win-white" onclick="apply();" value="新建" style="width:45px;height:25px;">
		</td>
	</tr>
	</table>
	</div>
	 <input type="hidden" name="optType" value="0">
 </form>
</div>

</body>

</html>