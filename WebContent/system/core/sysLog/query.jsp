<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;

/**
 * 初始化
 */
function doInit(){
	//getLogType();
	getSysLogTable();
}


/**
 * 获取日志类型
 */
function getLogType(){
	var url =contextPath+"/sysLogManage/getLogType.action"
	var json = tools.requestJsonRs(url);
	var html = "<option value=\"0\"></option>";
	if(json.rtState){
		var list=json.rtData;
		for(var i=0;i<list.length;i++){
			html+="<option value=\""+list[i].typeId+"\">"+list[i].typeName+"</option>";
		}
	}
	$("#logType").html(html);
}

/**
 * 确定
 */
function doSubmit(){
	var conType=$("input[name='conType']:checked").val();
	// 判断当前操作 0：查询    1:导出 
	if(conType==0){
		getLogList();
		$("#condition").css("display","none");
		$("#logList").css("display","");
	}else{
		exportSysLogInfo();
	}
}


function getLogList(){
	var param = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/sysLogManage/datagrid.action",
		pagination:true,
		singleSelect:false,
		queryParams:param,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'uuid',checkbox:true,title:'ID',width:100},
			{field:'userName',title:'用户名称',width:100},
			{field:'ip',title:'ip地址',width:120},
			{field:'type',title:'日志类型',width:150},
			{field:'timeDesc',title:'时间',width:200},
			{field:'remark',title:'描述',width:200},
			/* {field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
				return "&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}} */
		]]
	});
}

function exportSysLogInfo(){
	//var param = tools.formToJson($("#form1"));
	var url =contextPath+"/sysLogManage/exportLogInfo.action";
    document.form1.action=url;
    document.form1.submit();
    return true;
	//var json = tools.requestJsonRs(url,param);
}


function del(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		top.$.jBox.tip("请选择需要删除的题库","info");
		return;
	}
	var sid = selection.uuid;
	top.$.jBox.confirm("确认删除该日志信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/sysLogManage/delLogInfo.action";
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

/**
 * 批量删除
 */
function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		top.$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			for(var i=0;i<selections.length;i++){
			    var sid = selections[i].uuid;
				var url = contextPath+"/sysLogManage/delLogInfo.action";
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

/**
 * 取出所有的日志表
 */
function getSysLogTable(){
	var url =contextPath+"/sysLogManage/getSysLogTable.action";
	var json = tools.requestJsonRs(url);
	var html = "<option value=\"0\">默认表(当月表)</option>";
	if(json.rtState){
		var list=json.rtData;
		for(var i=0;i<list.length;i++){
			html+="<option value=\""+list[i].tableName+"\">"+list[i].tableName+"</option>";
		}
	}
	$("#sysLogTable").html(html);
}

function doExportTomcat(){
	
}
</script>

</head>
<body onload="doInit()"  style="overflow:hidden;background:#f6f7f9">
	<center>
		<div id="container" style="width:100%;height:auto;margin:0 quto;">
			<div id="condition" style="width:500px;">
				<form method="post" name="form1" id="form1">
					<table class="TableBlock" width="100%">
							<tr class="TableHeader"><td colspan="2">系统日志查询条件</td></tr>
							<tr class="TableData">
								<td width="30%" align="left">日志表：</td>
								<td align="left">
									<select id='sysLogTable' name="sysLogTable" class="BigSelect">
										<option value="0">无</option>
									</select>
								</td>
							</tr>
							<tr class="TableData">
								<td width="30%" align="left">日志类型：</td>
								<td align="left"><select id="logType" name="logType" class="BigSelect" style="width:250px;">
									<option value="">所有日志</option>
									<option value="000">运行日志</option>
									<optgroup label="部门管理">
										<option value="002A">添加部门</option>
										<option value="002B">修改部门</option>
										<option value="002C">删除部门</option>
									</optgroup>
									<optgroup label="人员管理">
										<option value="003A">添加人员</option>
										<option value="003B">修改人员</option>
										<option value="003C">删除人员</option>
										<option value="003D">清空密码</option>
										<option value="003E">登录操作</option>
										<option value="003F">注销操作</option>
									</optgroup>
									<optgroup label="角色管理">
										<option value="004A">添加角色</option>
										<option value="004B">修改角色</option>
										<option value="004C">删除角色</option>
									</optgroup>
									<option value="006">工作流</option>
									<option value="012">印章管理</option>
									<option value="018">工作日志</option>
									<option value="024">公共文件柜</option>
								</select></td>
							</tr>
							<tr class="TableData">
							<td width="30%" align="left">用户：</td>
							<td align="left">
								<input type='hidden' class="BigInput" id="userIds" name="userIds"/>
								<textarea id="userNames" name="userNames" style="width:240px;height:60px;background:#f0f0f0;padding:5px" class="BigTextarea" readonly></textarea>
								<a href="javascript:void(0)" onclick="selectUser(['userIds','userNames'])">选择</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="clearData('userIds','userNames')">清除</a>
							</td>
							</tr>
							<tr class="TableData">
							<td width="30%" align="left">开始时间：</td>
							<td align="left">
								<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='startTimeDesc' class="Wdate BigInput easyui-validatebox" style="width:240px;"/>
							</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">截止时间：</td>
							<td align="left">
								<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='endTimeDesc' class="Wdate BigInput easyui-validatebox" style="width:240px;"/>
							</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">ip地址：</td>
							<td align="left">
								<input type="text" id = "ip" name="ip" class="BigInput" style="width:240px;"/>
							</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">备注：</td>
							<td align="left">
								<input type="text" id = "remark" name="remark" class="BigInput" style="width:240px;"/>
							</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">操作类型：</td>
							<td align="left">
								<input type="radio" id="conType1" name="conType" value="0" checked='checked'/>查询&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" id="conType2" name="conType" value="1"/>导出&nbsp;&nbsp;&nbsp;&nbsp;
							</td></tr>
							<tr class="TableData" style="text-align:center"><td colspan="2">
							<input onclick="doSubmit();" type="button" value="确定" class="btn btn-primary"/>
<!-- 							<input onclick="doExportTomcat();" type="button" value="导出Tomcat日志文件" class="btn btn-primary"/> -->
							</td></tr>
					</table>
				</form>
			</div>
		</div>
</center>
<div id="logList" style="display:none;width:100%;height:100%;">
	<table id="datagrid" fit="true"></table>
		<div id="toolbar">
			<div style="text-align:left;">
				<!-- <button class="btn btn-one" onclick="delAll()">批量删除</button> -->
				<button class="btn btn-one" onclick="$('#condition').show();$('#logList').hide();">返回</button>
			</div>
		</div>
</div>
</body>
</html>

