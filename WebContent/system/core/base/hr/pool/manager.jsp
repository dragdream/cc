<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<title>人才库管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/hrPoolController/getHrPoolList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planName',title:'计划名称',width:100,
				formatter:function(value,rowData,rowIndex){
					return "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.planId+")'>"+ rowData.planName +"</a>";
			}},
			{field:'employeeName',title:'应聘者姓名',width:40},
			{field:'employeeBirthStr',title:'出生日期',width:40,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'employeePhone',title:'联系电话',width:40},
			{field:'employeeHighestSchoolDesc',title:'学历',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'employeeMajorDesc',title:'专业',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'positionDesc',title:'岗位',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'expectedSalary',title:'期望薪水',width:50
			},
			{field:'employeeStatusDesc',title:'状态',width:50
			},
			{field:'employeeStatus',title:'状态',width:50,hidden:true
			},
			{field:'createTimeStr',title:'创建日期',width:80,formatter:function(value,rowData,rowIndex){
				//return getFormatDateStr(value , 'yyyy-MM-dd HH:mm:ss');
				return value;
			}},
			{field:'2',title:'操作',width:80,formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='showDetailFunc("+rowData.sid+")'>详细信息 </a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>修改</a>";
				str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteObjFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}

/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘计划详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/plan/recruitPlanDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
}

function showDetailFunc(sid){
	  var title = "人才简历库详情";
	  var url = contextPath + "/system/core/base/hr/pool/detail.jsp?sid=" + sid;
	  bsWindow(url ,title,{width:"950",height:"400",buttons:
			[
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			if(v=="关闭"){
				return true;
			}
		}});
	}

/**
 * 编辑信息
 */
function toAddOrUpdate(sid){
    var url = contextPath + "/system/core/base/hr/pool/addOrUpdate.jsp?sid=" + sid;
 	window.location.href = url;
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
		  var url = contextPath + "/hrPoolController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
					datagrid.datagrid('reload');	
				});			
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

/**
 * 发送offer
 */
function batchOffer(type){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	var typeDesc = "确定要将选中的人员发Offer？";
	if(type == 1){
		typeDesc = "确定要取消选中的人员Offer？";
	}
	
	 $.MsgBox.Confirm ("提示", typeDesc, function(){
		    var url = contextPath + "/hrPoolController/sendoffer.action";
			var para = {sids:ids  , type : type};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("操作成功！",function(){
					datagrid.datagrid('reload');
				});
			} else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	  });
}



//发送offer
function sendOfferBatch(type){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	$.MsgBox.Confirm ("提示", "是否确定给选中的人员标记为已发offer?", function(){
	    var url = contextPath + "/hrPoolController/sendoffer.action";
	    var para = {sids:ids  , type : type};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			$.MsgBox.Confirm ("提示", "是否通过外网邮箱发送Email形式的Offer?", function(){
				var url=contextPath+"/system/core/base/hr/pool/emailInfo.jsp?sids="+ids;
				bsWindow(url ,"设置Email信息",{width:"700",height:"400",buttons:
					[
                     {name:"发送",classStyle:"btn-alert-blue"},
				 	 {name:"取消",classStyle:"btn-alert-gray"}
					 ]
					,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="发送"){
					    var json=cw.sendEmail();
					    if(json.rtState){
					    	$.MsgBox.Alert_auto("邮件发送成功！",function(){
					    		datagrid.datagrid("reload");
					    	});
					    	
					    	return true;
					    }else{
					    	$.MsgBox.Alert_auto("邮件发送失败！");
					    }
					}else if(v=="取消"){
						return true;
					}
				}}); 
			});
		} else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}  
	});
	
	
}

//批量导入
function importBatch(){
	var url=contextPath+"/system/core/base/hr/pool/import.jsp";
	window.location.href=url;
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div style="text-align:left; margin: 10px;">
			<button class="btn-win-white" onclick="toAddOrUpdate(0);">添加人才库</button>&nbsp;&nbsp;
			<button class="btn-win-white" onclick="importBatch()">批量导入</button>&nbsp;&nbsp;
			<button class="btn-del-red" onclick="batchDeleteFunc();">批量删除</button>&nbsp;&nbsp;
			<button class="btn-win-white" onclick="sendOfferBatch(4);">发Offer</button>&nbsp;&nbsp;
			<button class="btn-del-red" onclick="batchOffer(1);">取消Offer</button>&nbsp;&nbsp;
			
			</div>

	</div>
</body>
</html>