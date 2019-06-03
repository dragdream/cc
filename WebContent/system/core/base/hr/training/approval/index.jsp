<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>

<title>培训计划审批</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/trainingPlanController/getPlanApprovalList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:true,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planNo',title:'培训计划编号',width:50},
			{field:'planName',title:'培训计划名称',width:100,
				formatter:function(value,rowData,rowIndex){
					return "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>"+ rowData.planName +"</a>";
			}},
			{field:'planChannelName',title:'培训渠道',width:40,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'courseTypesName',title:'培训形式',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'createTimeStr',title:'创建日期',width:50},
			{field:'planStatus',title:'计划状态',width:40,formatter:function(value, rowData, rowIndex){
				return planStatusFunc(value);
			}},
			{field:'2',title:'操作',width:40,formatter:function(value, rowData, rowIndex){
				var planStatus = rowData.planStatus
				var str = "";//"<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
				if(planStatus ==0){
					str += "<a href='javascript:void(0)' onclick='approval("+rowData.sid+",1)'>批准</a>";
					str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='approval("+rowData.sid+",2)'>不批准</a>";
				}
				
				return str;
			}}
		]]
	});
}




//根据条件查询
function queryApproval(index){
	  var queryParams=tools.formToJson($("#form1"));
	  queryParams["planStatus"] = index;
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
}
function doSearchFunc(){
	  datagrid.datagrid("reload");
	  $('#searchDiv').toggle()
}

/**
 * 审批
 */
function approval(sid , planStatus){
	var title = "审批培训计划";
	var url = contextPath + "/system/core/base/hr/training/approval/approval.jsp?sid=" + sid + "&planStatus=" + planStatus;
	bsWindow(url ,title,{width:"500",height:"120",buttons:
			[
			 {name:"确定",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					cw.doApproval(function(json){
						$.jBox.tip("审批成功!",'info' ,{timeout:1500});
						datagrid.datagrid('reload');
					});
			
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
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;审批培训计划</b>
			
			<div class="btn-group pull-right" data-toggle="buttons">
		  <label class="btn btn-default active" onclick="queryApproval(0);">
		    <input type="radio" name="options" id="option1" >待审批
		  </label>
		  <label class="btn btn-default"  onclick="queryApproval(1);">
		    <input type="radio" name="options" id="option2">已审批
		  </label>
		   <label class="btn btn-default"  onclick="queryApproval(2);">
		    <input type="radio" name="options" id="option3">未准审批
		  </label>
		</div>
		<div style="clear:both"></div>
			
		</div>
	<!-- 	<div style="text-align:left;">
			<button class="btn btn-primary" onclick="addInfo()">添加培训计划</button>&nbsp;&nbsp;
			<button class="btn btn-primary" onclick="batchDeleteFunc()">删除培训计划</button>&nbsp;&nbsp;
			<button class="btn btn-primary" onclick="doSearchFunc();"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div> -->
		<form id="form1" name="form1" method="post">
			<div style="margin-top:10px;display:none" id="searchDiv">
				<table class="TableBlock"  style="text-align: left;" align="center" >
					<tr>
						<td class="TableData">计划编号：</td>
						<td class="TableData">
							<input class="BigInput" type="text" id = "planNo" name='planNo' size="20"/>
						</td>
						<td class="TableData">名称：</td>
						<td class="TableData">
							<input class="BigInput" type="text" id = "planName" name='planName' size="20"/>
						</td>
					</tr>
					<tr>
						<td class="TableData">计划状态：</td>
						<td class="TableData">
							<select id="planStatus" name="planStatus" class="BigSelect">
								<option value="">请选择</option>
								<option value="0">待审批</option>
								<option value="1">已批准</option>
								<option value="2">未批准</option>
							</select>
						</td>
						<td class="TableData">审批人：</td>
						<td class="TableData">
							<input type=hidden name="approvePersonId" id="approvePersonId" value="">
							<input  name="approvePersonName" id="approvePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
							<span>
								<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);">添加</a>
								<a href="javascript:void(0);" class="orgClear" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');">清空</a>
							</span>
						</td>
					</tr>
					<tr>
						<td class="TableData">招聘说明：</td>
						<td class="TableData">
							<input class="BigInput" type="text" id = "recruitDescription" name='recruitDescription' size="20"/>
						</td>
						<td class="TableData">招聘备注：</td>
						<td class="TableData">
							<input class="BigInput" type="text" id = "recruitRemark" name='recruitRemark' size="20"/>
						</td>
					</tr>
					<tr>
						<td class="TableData">招聘开始日期：</td>
						<td colspan="3" class="TableData">
							<input class="BigInput" type="text" id = "startDateStrMin" name='startDateStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
							至
							<input class="BigInput" type="text" id = "startDateStrMax" name='startDateStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
						</td>
					</tr>
					<tr>
						<td class="TableData">招聘结束日期：</td>
						<td colspan="3" class="TableData">
							<input class="BigInput" type="text" id = "endDateStrMin" name='endDateStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="" />
							至
							<input class="BigInput" type="text" id = "endDateStrMax" name='endDateStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value=""/>
						</td>
					</tr>
					<tr align="center">
						<td class="TableData" colspan="4">
							<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
							&nbsp;&nbsp;<input type="reset" value="重置" class="btn btn-primary" >
						</td>
						
					</tr>
					
				</table>
			</div>
		
		
		</form>
	</div>
</body>
</html>