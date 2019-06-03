<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var datagrid;
function doInit(){
	getHrCodeByParentCodeNo("PM_SANCTION_TYPE","sanType");
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanSanctionController/datagrid.action?humanDocSid=<%=humanDocSid%>',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'sanTypeDesc',title:'奖惩项目',width:100},
			{field:'sanDateDesc',title:'获奖日期',width:100},
			{field:'validDateDesc',title:'工资生效月份',width:100},
			{field:'pays',title:'奖惩金额',width:100},
			{field:'content',title:'说明',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void();' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='showDetail("+rowData.sid+")'>查看</a>";
			}}
		]]
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/core/base/pm/manage/jc_detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}
function add(){
	var url = contextPath+"/system/core/base/pm/manage/jc_add.jsp?humanDocSid=<%=humanDocSid%>";
	bsWindow(url,"添加奖惩信息",{width:"700",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/pm/manage/jc_edit.jsp?sid="+sid;
	bsWindow(url,"修改奖惩信息",{width:"700",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		top.$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			for(var i=0;i<selections.length;i++){
				var sid = selections[i].sid;
					var url = contextPath+"/TeeHumanSanctionController/delHumanSanction.action";
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
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;奖惩管理</b>
		</div>
		<div style="text-align:left;margin-bottom:5px;">
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
							<button class="btn btn-primary" onclick="$('#searchDiv').toggle();datagrid.datagrid('resize');"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
	<form id="form1" name="form1">
		<div style="margin-top:10px;display:none" id="searchDiv">
					<table class="SearchTable" style="text-align:left;">
						<tr>
							<td class="SearchTableTitle">奖惩项目：</td>
							<td>
								<select class="BigSelect"  id = "sanType" name='sanType' style="width:150px;">
									<option value="全部">全部</option>
								</select>
							</td>
							<td class="SearchTableTitle">奖惩日期：</td>
							<td>
								从<input type="text" id='startTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='startTime' class="Wdate BigInput" />
								到<input type="text" id='endTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='endTime' class="Wdate BigInput" />
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">奖惩金额：</td>
							<td>
								从<input class="BigInput" id="from" name="from"  type="text" style="width:80px;"/>
								到<input class="BigInput" id="to" name="to"  type="text" style="width:80px;"/>
							</td>
							<td align="right" colspan="2">
								<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
							</td>
						</tr>
					</table>
		</div>
		</form>
	</div>
</body>
</html>