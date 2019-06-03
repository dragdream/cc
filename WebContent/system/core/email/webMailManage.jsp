<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>设置外部邮箱</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/emailController/getWebEmailList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID'},
			{field:'loginType',title:'邮箱',width:100},
			{field:'isDefault',title:'默认外部邮箱',align:'center', width:50,formatter:function(value,rowData,rowIndex){
				if(value =="0"){
					value = "否";
				}else{
					value = "是";
				}
				return value;
			}},
			{field:'2',title:'操作',width:50,formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='editFunc("+rowData.sid+")'>修改</a>&nbsp;&nbsp;";
				str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}


function editFunc(sid){
	addInfo(sid);
}

/**
 * 添加维护信息
 */
function addInfo(sid){
  var title = "新建外部邮箱";
  if(sid){
	  title = "编辑外部邮箱";
  }else{
	  sid=0;
  }
  var url = contextPath + "/system/core/email/webMailSetting.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"650",height:"350",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
				
				var b = $.MsgBox.Alert_auto("保存成功！");
				d.remove();
					datagrid.datagrid('reload');
					//$(".iframeDiv_close").click();
					 /*  BSWINDOW.modal("hide"); */
				
				return b;
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项 ！");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除信息
 */
function deleteObjFunc(ids){
	$.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		var url = contextPath + "/emailController/deleteWebMailById.action";
		var para = {sids:ids};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！");
			datagrid.datagrid('reload');						
		}
	});
}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
}
/**
 * 点击高级查询
 */
function doSearchFunc(){
	datagrid.datagrid("reload");
	$('#searchDiv').toggle();
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true" ></table>
	
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_youxiangshezhi.png">
		<span class="title">外部邮箱设置</span>
	</div>
	<div class = "right fr clearfix">
	    <input type="button" class="btn-win-white" onclick="addInfo()" value="添加"/>
		<input type="button" class="btn-del-red" onclick="batchDeleteFunc()" value="批量删除"/>
	
	</div>
</div>	
</body>
</html>