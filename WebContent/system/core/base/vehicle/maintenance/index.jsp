<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆维护保养</title>
<script type="text/javascript">
function doInit(){
  getInfoList();
}
var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/vehicleMaintenanceController/getVehicleMaintenanceList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100,hidden:true},
			{field:'vehicleName',title:'车牌号',width:100},
			{field:'vmType',title:'维护类型',width:100,formatter:function(value, rowData, rowIndex){
				var str = "";
				if(value == 0){
					str = "维修";
				}else if(value == 1){
					str = "加油";
				}else if(value == 2){
					str = "洗车";
				}else if(value == 3){
					str = "年检";
				}else if(value == 4){
					str = "其他";
				}
				return str;
			}},
			{field:'vmReason',title:'维护原因',width:100},
			{field:'vmRequestDateStr',title:'维护日期',width:100,formatter:function(value, rowData, rowIndex){
				return value;
			}},
			{field:'vmPersonName',title:'经办人',width:100},
			{field:'vmFee',title:'维护费用',width:100},
			{field:'vmRemark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='editFunc("+rowData.sid+")'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;";
				str += "<a href='javascript:void();' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}


/**
 * 添加维护信息
 */
function add(){
  var title = "添加车辆维护信息";
  var url = contextPath + "/system/core/base/vehicle/maintenance/addMaintenance.jsp";
  bsWindow(url ,title,{width:"850",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
			  $.MsgBox.Alert_auto("保存成功！");
			  datagrid.datagrid('reload');
			  d.remove();
			});
			//return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
  return;
	bsWindow(contextPath+"/system/core/base/pm/manage/add_person.jsp","添加新人员",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}});
}
/**
 * 编辑维护信息
 */
function editFunc(sid){
  var title = "编辑车辆维护信息";
  var url = contextPath + "/system/core/base/vehicle/maintenance/addMaintenance.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
				$.MsgBox.Alert_auto("保存成功！");
				datagrid.datagrid('reload');
				
			});
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
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
		//alert("至少选择一项");
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	 $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		 var url = contextPath + "/vehicleMaintenanceController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}
	  });
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="clearfix topbar">
		<div class="fl left" >
			<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/clwhgl.png">
			<span class="title">车辆维护保养管理</span>
		</div>
		<div class="fr fight">
			<button class="btn-win-white" onclick="add()">添加维护信息</button>
<!-- 			<button class="btn btn-primary" onclick="batchDeleteFunc()">删除车辆维护信息</button>&nbsp;&nbsp; -->
		</div>
	</div>
</body>
</html>