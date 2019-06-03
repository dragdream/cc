<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/codemirror/codemirror.css">
	<script src="<%=contextPath %>/common/codemirror/codemirror.js"></script>
	<script src="<%=contextPath %>/common/codemirror/sql.js"></script>
<%
	String identity = TeeStringUtil.getString(request.getParameter("identity"));
%>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
/* BASICS */
 
.CodeMirror {
  /* Set height, width, borders, and global font properties here */
    font-family: monospace;
    height: 150px;
    width:300px;
    border: 0px solid #ccc; /*add by jackqqxu*/
    font-family: Monaco, Menlo, Consolas, 'COURIER NEW', monospace;/*add by jackqqxu*/
    font-size: 12px;
    border:1px solid #e2e2e2;
}
.CodeMirror-scroll {
  /* Set scrolling behaviour here */
  overflow: auto;
}
</style>
<script>
var bisTableId = 1;
var identity = "<%=identity%>";

function doInit(){
	//获取数据源列表
	var json = tools.requestJsonRs(contextPath+"/bisDataSource/datagrid.action");
	var rows = json.rows;
	var render = [];
	for(var i=0;i<rows.length;i++){
		render.push("<option value="+rows[i].sid+">"+rows[i].dsName+"</option>");
	}
	$("#bisDataSourceId").html(render.join(""));
	
	if(identity!=""){//
		var json = tools.requestJsonRs(contextPath+"/bisView/getBisView.action?identity="+identity);
		bindJsonObj2Cntrl(json.rtData);
	}
	initEditor();
	
	$('#datagrid').datagrid({
		url:contextPath+"/bisView/listBisViewListItem.action?identity="+identity,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'title',title:'字段标题',width:100},
			{field:'field',title:'字段原名',width:100},
			{field:'searchField',title:'查询字段',width:100},
			{field:'fieldType',title:'字段类型',width:50},
			{field:'orderNo',title:'排序号',width:50},
			{field:'width',title:'宽度',width:50},
			{field:'isSearch',title:'是否为查询字段',formatter:function(data){
				if(data==0){
					return "否";
				}else{
					return "是";
				}
			}},
			{field:'model',title:'数据模型',width:50},
			{field:'_',title:'管理',width:50,formatter:function(data,rowData){
				var render = [];
				render.push("<a href='javascript:void(0)' onclick='editItem("+rowData.sid+")'>编辑</a>");
				render.push("<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
}

function initEditor() {
	window.editor = CodeMirror.fromTextArea(document.getElementById('sql'), {
	    mode: "text/x-mysql",
	    indentWithTabs: true,
	    smartIndent: true,
	    lineNumbers: true,
	    matchBrackets : true,
	    autofocus: true,
	    extraKeys: {"Ctrl-Space": "autocomplete"},
	    hintOptions: {tables: {
	      users: {name: null, score: null, birthDate: null},
	      countries: {name: null, population: null, size: null}
	    }}
	  });
	
	window.editor1 = CodeMirror.fromTextArea(document.getElementById('countSql'), {
	    mode: "text/x-mysql",
	    indentWithTabs: true,
	    smartIndent: true,
	    lineNumbers: true,
	    matchBrackets : true,
	    autofocus: true,
	    extraKeys: {"Ctrl-Space": "autocomplete"},
	    hintOptions: {tables: {
	      users: {name: null, score: null, birthDate: null},
	      countries: {name: null, population: null, size: null}
	    }}
	  });
}

function updateBisView(){
	var param = tools.formToJson($("#form"));
	param["sql"] = editor.getValue();
	param["countSql"] = editor1.getValue();
	var json = tools.requestJsonRs(contextPath+"/bisView/updateBisView.action",param);
	alert("更新成功");
}

function goBack(){
	window.location = "list.jsp";
}

function del(sid){
	if(window.confirm("是否删除该视图项")){
		var json = tools.requestJsonRs(contextPath+"/bisView/delBisViewListItem.action",{sid:sid});
		$("#datagrid").datagrid("reload");
	}
}

function createItem(){
	bsWindow(contextPath+"/system/subsys/bisengine/view/view_item_mgr.jsp?bisViewId="+identity,"创建视图项",{width:"600px",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(v=="ok"){
			var json = cw.commit();
			if(!json.rtState){
				alert(json.rtMsg);
				return false;
			}
			$("#datagrid").datagrid("reload");
			return true;
		}
	}});
}

function editItem(sid){
	bsWindow(contextPath+"/system/subsys/bisengine/view/view_item_mgr.jsp?bisViewId="+identity+"&sid="+sid,"编辑视图项",{width:"600px",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(v=="ok"){
			var json = cw.commit();
			if(!json.rtState){
				alert(json.rtMsg);
				return false;
			}
			$("#datagrid").datagrid("reload");
			return true;
		}
	}});
}


//视图预览
function preview(){
	var url=contextPath+"/system/subsys/bisengine/view/preview.jsp?identity="+"<%=identity%>";
	bsWindow(url ,"视图预览",{width:"600",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}}); 
}
</script>
</head>
<body onload="doInit()">
<div id="toolbar">
<div id="tips" style="border:1px solid #e2e2e2;font-size:12px;width:500px;height:400px;overflow:auto;background:white;padding:5px;position:fixed;z-index:10000;right:0px;top:0px;display:none">
	<div style="background:#f0f0f0;padding:5px;">SQL语句特殊变量说明：</div>
	<div>
		$userId = 当前用户ID<br/>
		$userSid = 当前用户主键ID<br/>
		$userName = 当前用户名<br/>
		$deptId = 部门ID<br/>
		$roleId = 角色ID<br/>
		@XXXX = 传入的参数值
	</div>
	<div style="background:#f0f0f0;padding:5px;">特殊函数段说明：</div>
	<div>
		[DATE2CHAR_Y_M_D|field] => 将field日期字段转换成格式为2012-02-02的字符串格式<br/>
		[DATE2CHAR_Y_M|field] => 将field日期字段转换成格式为2012-02的字符串格式<br/>
		[DATE2CHAR_Y|field] => 将field日期字段转换成格式为2012的字符串格式<br/>
		[DATE2CHAR_M|field] => 将field日期字段转换成格式为01的字符串格式<br/>
		[GET_DAY_OF_MONTH_STR] => 返回本月日期格式串  2014-05-01,2014-05-02,...,2014-05-31<br/>
		[GET_DAY_OF_MONTH_STR,2014,5] => 返回2014年5月的日期格式串  2014-05-01,2014-05-02,...,2014-05-31<br/><br/>
		[GET_MONTH_OF_YEAR_STR] => 返回本年的月份格式串  2014-01,2014-02,...,2014-12<br/>
		[GET_MONTH_OF_YEAR_STR,2014] => 返回2014年的月份格式串  2014-01,2014-02,...,2014-12<br/>
		[DATE2CHAR_Y_M|field] => 将field日期字段转换成格式为2012-02的字符串格式<br/>
		[GET_LONG_MONTH_BETWEEN_STR|field|1] => 返回本年度一月份的LONG整型区间   (field&lt;xxx and field&gt;xxx)<br/>
		[GET_LONG_MONTH_FIRST_STR|1] => 返回本年度一月份第一天的LONG整型 <br/>
		[GET_LONG_MONTH_LAST_STR|1] => 返回本年度一月份最后一天的LONG整型 <br/>
	</div>
</div>
<table style="font-size:12px;" id="form">
	<tr>
		<td class="TableData" width="80" >唯一标识：</td>
		<td class="TableData">
			<input type="text" id="identity" name="identity" readonly class="BigInput readonly"/>
		</td>
		<td class="TableData"  width="80">视图名称：</td>
		<td class="TableData">
			<input type="text" id="name" name="name" class="BigInput"/>
		</td>
	</tr>
	<tr>
		<td class="TableData"  width="80">数据源：</td>
		<td class="TableData">
			<select id="bisDataSourceId" name="bisDataSourceId" class="BigSelect">
				
			</select>
			<input name="type" id="type" type="hidden"/>
		</td>
	</tr>
</table>
<table style="font-size:12px">
	<tr>
		<td class="TableData" width="80">SQL：</td>
		<td class="TableData" colspan="3">
			<textarea id="sql" name="sql" class="BigTextarea" style=""></textarea>
		</td>
		<td class="TableData" width="80">总数SQL：</td>
		<td class="TableData" colspan="3">
			<textarea id="countSql" name="countSql" class="BigTextarea" style=""></textarea>
		</td>
	</tr>
</table>
<button class="btn btn-default" onclick="goBack()">返回</button>
&nbsp;&nbsp;
<button class="btn btn-primary" onclick="updateBisView()">保存</button>
&nbsp;&nbsp;
<button class="btn btn-primary" onclick="preview()">视图预览</button>
&nbsp;&nbsp;
<button class="btn btn-primary" onclick="createItem()">添加视图项</button>
&nbsp;<a href="javascript:void(0)" onclick="$('#tips').toggle()">显示/隐藏 SQL提示</a>
</div>
<table id="datagrid"  fit="true"></table>
</body>
</html>
