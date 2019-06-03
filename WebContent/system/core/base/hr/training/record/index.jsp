<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>

<title>培训记录管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
	
	getPlanList('planId');
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/trainingRecordController/getRecordList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:true,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planId',title:'培训计划Id',width:10,hidden:true},
			{field:'planName',title:'培训计划名称',width:100,
				formatter:function(value,rowData,rowIndex){
					return "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.planId+")'>"+ rowData.planName +"</a>";
			}},
			{field:'recordUserName',title:'受训人',width:40},
			{field:'recordCost',title:'培训费用',width:50},
			{field:'recordInstitution',title:'培训机构',width:50},
			{field:'createTimeDesc',title:'记录时间',width:50},
			{field:'2',title:'操作',width:40,formatter:function(value, rowData, rowIndex){
	
				var str = "<a href='javascript:void(0)' onclick='lookInfo("+rowData.sid+")'>详细信息 </a>";
				
					str += "<a href='javascript:void(0)' onclick='addOrUpdateInfo("+rowData.sid+")'>编辑</a>";
					str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteById("+rowData.sid+")'>删除</a>";
			
				
				return str;
			}}
		]]
	});
}



//根据条件查询
function doSearch(){
	var queryParams=tools.formToJson($("#form1"));
	datagrid.datagrid('options').queryParams=queryParams; 
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
}


/**
 * 单个删除
 */
function deleteById(sid , type ){
	$.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/trainingRecordController/deleteById.action";
			if(type == 1){
				 url = contextPath + "/trainingRecordController/deleteByIds.action";
			}
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("删除成功！", "info", {timeout: 1800});
				datagrid.datagrid('reload');
			}
		}
	});

}
/**
 * 新建或者更新
 */
function addOrUpdateInfo(sid){
	
	  var title = "新建培训记录";
	  var  height = 180;
	  if(sid > 0){
		  title = "编辑培训记录";
		  height=320;
	  }
	  var url = contextPath + "/system/core/base/hr/training/record/addorupdate.jsp?sid=" + sid;
	  bsWindow(url ,title,{width:"650",height:height,buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				cw.doSaveOrUpdate(function(){
				  $.jBox.tip("保存成功！", "info", {timeout: 1800});
				  datagrid.datagrid('reload');
				  BSWINDOW.modal("hide");
				});
			}else if(v=="关闭"){
				return true;
			}
		}});
	
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		alert("至少选择一项");
		return ;
	}
	var ids = getSelectItem();
	deleteById(ids , 1);
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
 * 查看详情
 */
function lookInfo(sid){
  var title = "查询培训记录详情";
  var  height = 180;

  var url = contextPath + "/system/core/base/hr/training/record/recordDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"650",height:320,buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
			<table width="100%">
				<tr>
					<td>
						<span class="easyui_h1">培训计划管理</span>
					</td>
					<td align=right>
						<input type="button" value="添加培训记录" onclick="addOrUpdateInfo(0);" class="btn btn-info"/>&nbsp;
						<input type="button" value="批量删除" onclick="batchDeleteFunc();" class="btn btn-danger"/>&nbsp;
						<button type="button" onclick="" class="btn btn-success" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-search"></i>&nbsp;高级检索</button>
					</td>
				</tr>
			</table>
		</div>
		<br>
		
		<form id="form1" name="form1" method="post">
			<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title" id="myModalLabel">查询条件</h4>
						</div>
						<div class="modal-body">
							<table class="TableBlock"  style="text-align: left;" align="center" >
								<tr>
									<td class="TableData">培训计划：</td>
									<td class="TableData">
										<select name="planId" id="planId" class="BigSelect">
											<option value="">--全部--</option>
										</select>
				
									</td>
									<td class="TableData">受训人：</td>
									<td class="TableData">
										<input type=hidden name="recordUserId" id="recordUserId" value="">
										<input  name="recordUserName" id="recordUserName"  class="BigStatic BigInput" size="10"  readonly value=""></input>
										<span>
											<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['recordUserId', 'recordUserName']);">添加</a>
											<a href="javascript:void(0);" class="orgClear" onClick="$('#recordUserId').val('');$('#recordUserName').val('');">清空</a>
										</span>
									</td>
								</tr>
							
								<tr>
									<td nowrap class="TableData"  width="15%;" >培训机构：<font style='color:red'>*</font></td>
									<td class="TableData" width="35%;" >	
										<input class="BigInput easyui-validatebox" type="text" size="25" id = "recordInstitution" name="recordInstitution" maxlength="200"  required="true" value=""  />
									</td>
									<td nowrap class="TableData"  width="15%;" >培训费用：<font style='color:red'>*</font></td>
									<td class="TableData" width="60%;" >
										<input class="BigInput easyui-validatebox" type="text" id = "recordCost" name="recordCost" maxlength="8"    required="true" size="9"   validType ='number[]'/>
									</td>
								</tr>
								<tr align="center">
									<td class="TableData" colspan="4">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
										&nbsp;&nbsp;<input type="reset" value="重置" class="btn btn-primary" onclick="$('#recordUserId').val('');">
									</td>
									
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>