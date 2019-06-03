<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/examine/js/examine.js"></script>
<script>


/**
 * 初始化
 */
function doInit(){
	//getLogList();
	//获取有权限考核指标集
	getPostExamine("groupId");
}
var datagrid;

function getLogList(){
	var param = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamineTaskManage/queryDatagrid.action",
		pagination:true,
		singleSelect:true,
		queryParams:param,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
				//{field:'sid',checkbox:true,title:'ID',width:40},
				{field:'taskTitle',title:'考核任务名称',width:150},
				{field:'rankmanNames',title:'考核人',width:200},
				{field:'participantNames',title:'被考核人',width:200,
					formatter:function(e,rowData,index){	
						return "<a href='#' onclick='getExamineTaskParticipantInfo(" + rowData.sid +")'>" + rowData.participantNames+ "</a>";
						
					}
				},
	
				
				
				{field:'groupId',title:'考核指标集Id',width:10,hidden:true},
				{field:'groupName',title:'考核指标集',width:100,
					formatter:function(e,rowData,index){	
						return "<a href='#' onclick='getExamineGroupInfo(" + rowData.groupId +")'>" + rowData.groupName+ "</a>";
					}
				},
				{field:'anonymityDesc',title:'匿名',width:50},
				{field:'taskBeginStr',title:'生效日期',width:60},
				{field:'taskEndStr',title:'终止日期',width:60},
				{field:'state',title:'状态',width:40 , hidden:true},
				

				{field:'stateDesc',title:'状态',width:50,
					formatter:function(e,rowData,index){
						//	0-生效 1-终止 2-待生效
						var stateDesc = "已生效";
						if(rowData.state == '1'){
							stateDesc = "已终止";
						}else if(rowData.state == '2'){
							stateDesc = "待生效";
						}
						return stateDesc;
					}
				
				},
				{field:'2',title:'操作',width:190,formatter:function(e,rowData,index){
					var tempStr = "&nbsp;&nbsp;<a href='#' onclick='toLookDetail("+rowData.sid+")'>查阅</a>"+
					"&nbsp;&nbsp;<a href='#' onclick='exportTotal("+rowData.sid+")' title='导出总分'>导出总分</a>"
					+ "&nbsp;&nbsp;<a href='#' onclick='exportDetail("+rowData.sid+")' title='导出分数明细'>导出分数明细</a>";
			
				return tempStr;
			}}
				
			]]
	});
}

/**
 * 新增或者更新
 */
function toAddUpdate(id){
	var title = "新增考核任务";
	if(id > 0){
		 title = "编辑考核任务";
	}
	var  url = contextPath + "/system/core/base/examine/task/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,title,{width:"700",height:"320",buttons:
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

/**
 * 跳转至考核项目
 */
function toItem(groupId){
	window.location.href = "<%=contextPath%>/system/core/base/examine/item/index.jsp?groupId=" + groupId;
}


/**
 * 确定
 */
function doSubmit(){
	getLogList();
	$("#condition").css("display","none");
	$("#logList").css("display","");
}



/**
 * 查阅
 * @param sid
 */
function toLookDetail(sid){
	var title = "考核查阅";
	var  url = contextPath + "/system/core/base/examine/task/queryExamineDetail.jsp?sid=" + sid;
	bsWindow(url ,title,{width:"900",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/*
 * 导出总分
 */
function exportTotal(sid){
 	window.location.href = "<%=contextPath %>/TeeExamineTaskManage/exportTotalToCsv.action?sid="+sid;
}

/*
 * 导出明细
 */
function exportDetail(sid){
 	window.location.href = "<%=contextPath %>/TeeExamineTaskManage/exportDetailToCsv.action?sid="+sid;
}
</script>

</head>
<body onload="doInit()"  style="overflow:hidden;">

<div id="container" style="width:100%;height:auto;margin:0 quto;">

<div align="center" style="margin-top:10px;">
			<div id="condition" style="width:700px;">
				<form method="post" name="form1" id="form1">
					<table class="TableBlock" align="left" width="100%">
						 <tr>
						      <td nowrap class="TableData">  考核任务标题：</td>
						      <td class="TableData" colspan="3" align="left">
						     	 <input type="text" name="taskTitle" size="40" maxlength="100" class="BigInput " value="" >
						      </td>
						  </tr>
						  <tr>
							  <td nowrap class="TableData" width="50" >考核人：</td>
							  <td class="TableData"  colspan="3"  align="left">
							      <input type="hidden" name="rankmanIds" id="rankmanIds" value="">
						     	  <textarea cols=50 name="rankmanNames" id="rankmanNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
						          <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['rankmanIds', 'rankmanNames']);">添加</a>
						          <a href="javascript:void(0);" class="orgClear" onClick="$('#rankmanIds').val('');$('#rankmanNames').val('');">清空</a>
							  </td>
						  </tr>
						   <tr>
							  <td nowrap class="TableData" width="50" >被考核人：</td>
							  <td class="TableData"  colspan="3" align="left" >
							      <input type="hidden" name="participantIds" id="participantIds" value="">
						     	  <textarea cols=50 name="participantNames" id="participantNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
						          <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['participantIds', 'participantNames']);">添加</a>
						          <a href="javascript:void(0);" class="orgClear" onClick="$('#participantIds').val('');$('#participantNames').val('');">清空</a>
							  </td>
						  </tr>						  
							<tr>
							    <td nowrap class="TableData">考核指标集：</td>
							    <td class="TableData" colspan="3" align="left">
							  		<select name="groupId" id="groupId" class="BigSelect">
							  			<option value="">全部</option>
							  		</select>
							    </td>
							</tr>
					    <tr>
					        <td nowrap class="TableData">有效日期：</td>
					        <td class="TableData" colspan="3" align="left">
					          	生效日期：
					          		<input type="text" name="taskBeginStr" id="taskBeginStr" size="15" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'taskBeginStr2\',{d:0});}',dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput"> 至
					          		<input type="text" name="taskBeginStr2" id="taskBeginStr2" size="15" onClick="WdatePicker({minDate:'#F{$dp.$D(\'taskBeginStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput"> 
					         	<br>终止日期：
					         		<input type="text" name="taskEndStr" id="taskEndStr" size="15" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'taskEndStr2\',{d:0});}',dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">  至
					         		<input type="text" name="taskEndStr2" id="taskEndStr2" size="15" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'taskEndStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput"> 
					        </td>
					        
					  
					        
					   </tr>					   
					   <tr>
					      <td nowrap class="TableData">描述：</td>
					      <td class="TableData" colspan="3" align="left">
					      		 <textarea cols=50 name="taskDesc" id="taskDesc" rows=2 class="BigTextarea" wrap="yes" ></textarea>
					      </td>
					   </tr> 
							<tr class="TableData">
								<td colspan="4" align="center">
									<input onclick="doSubmit();" type="button" value="确定" class="btn btn-primary"/>
									&nbsp;
									<input type="reset" value="重置" class="btn"/>
								</td>
							</tr>
									
					</table>
				</form>
			</div>
		</div>
</div>
<div id="logList" style="display:none;width:100%;height:100%;">
	<table id="datagrid" fit="true"></table>
		<div id="toolbar" >
			<div style="text-align:left;">
			<!-- 	<button class="btn btn-one" onclick="delAll()">批量删除</button> -->
				<button class="btn btn-one" onclick="$('#condition').show();$('#logList').hide();" style="margin-bottom:10px;">返回</button>
			</div>
		</div>
</div>
</body>
</html>

