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
.label{
cursor:pointer;
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
	
	var designModel = json.rtData.designModel;
	if(designModel){
		designModel = eval("("+designModel+")");
		var tbs = designModel.tables;
		for(var i=0;i<tbs.length;i++){
			if(i==tbs.length-1){
				globalIndex = tbs[i].index+1;
			}
			appendTableItem(tbs[i]);
		}
		
		var rls = designModel.rels;
		for(var i=0;i<rls.length;i++){
			appendFieldItem(rls[i]);
		}
		
		var cons = designModel.conditions;
		for(var i=0;i<cons.length;i++){
			appendConditionItem(cons[i]);
		}
	}
	
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

function updateBisView(){
	var param = tools.formToJson($("#form"));
	param["type"] = "1";
	param["designModel"] = tools.jsonObj2String({tables:tables,rels:rels,conditions:conditions});
	
	var from = [""];
	var select = [];
	var where = [""];
	
	if(rels.length==0){//如果只有一张表，则拼接这一张表的
		from.push(tables[0].name+" "+tables[0].name+"_"+tables[0].index);
	}else{
		for(var i=0;i<tables.length;i++){
			from.push(tables[i].name+" "+tables[i].name+"_"+tables[i].index);
			if(i!=tables.length-1){
				from.push(",");
			}
		}
		
		if(rels.length!=0){
			where.push("");
			//获取表连接子句
			for(var i=0;i<rels.length;i++){
				var t1name = rels[i].t1.split("|")[0];
				var t1index = rels[i].t1.split("|")[1];
				var t2name = rels[i].t2.split("|")[0];
				var t2index = rels[i].t2.split("|")[1];
				where.push(t1name+"_"+t1index+"."+rels[i].l1+"="+t2name+"_"+t2index+"."+rels[i].l2);
				if(i!=rels.length-1){
					where.push(" and ");
				}
			}
		}
	}
	
	var rows = $("#datagrid").datagrid("getRows");
	if(rows.length!=0){
		select.push("");
		for(var i=0;i<rows.length;i++){
			if(rows[i].searchFieldWrap){
				select.push(rows[i].searchFieldWrap+" as \""+rows[i].field+"\"");
			}else{
				select.push(rows[i].searchField+" as \""+rows[i].field+"\"");
			}
			
			if(i!=rows.length-1){
				select.push(",");
			}
		}
	}else{
		select.push("select * ");
	}
	
	var whereSql = "";
	for(var i=0;i<conditions.length;i++){
		whereSql += " and "+conditions[i].exp+" ";
	}
	
	if(where.join("")==""){
		where.push(" 1=1 ");
	}
	
	param["sql"] = "select "+select.join("")+" from "+from.join("")+" where "+where.join("")+whereSql;
	param["countSql"] = "select count(1) "+" from "+from.join("")+" where "+where.join("")+whereSql;
	param["selectSql"] = select.join("");
	param["fromSql"] = from.join("");
	param["whereSql"] = where.join("")+whereSql;
// 	param["orderBySql"] = where.join("");
	var json = tools.requestJsonRs(contextPath+"/bisView/updateBisView.action",param);
	alert("保存成功");
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
	bsWindow(contextPath+"/system/subsys/bisengine/view/view_item_mgr_senior.jsp?bisViewId="+identity,"创建视图项",{width:"600px",submit:function(v,h){
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
	bsWindow(contextPath+"/system/subsys/bisengine/view/view_item_mgr_senior.jsp?bisViewId="+identity+"&sid="+sid,"编辑视图项",{width:"600px",submit:function(v,h){
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

function addTable(){
	dialog("select_table.jsp?dataSource="+$("#bisDataSourceId").val(),800,600);
}

function addRel(){
	if(tables.length<=1){
		return alert("单表无法进行关联");
	}
	dialog("rel_add.jsp",800,600);
}

function addCondition(){
	dialog("condition_add.jsp",800,600);
}

var globalIndex = 1;
var tables = [];
var rels = [];
var conditions = [];
function appendTableItem(table){
	$("#tableDiv").append("<span title='点击删除业务表' onclick='delTable(this)' class=\"label label-success\">"+table.desc+"("+table.name+"_"+table.index+")</span>");
	tables.push(table);
}

function appendFieldItem(obj){
	$("#relDiv").append("<span title='点击删除关联关系' class=\"label label-success\" onclick='delField(this)'>"+obj.desc+"</span>");
	rels.push(obj);
}

function appendConditionItem(obj){
	$("#conditionDiv").append("<span title='点击删除条件设置' class=\"label label-success\" onclick='delField1(this)'>"+obj.desc+"</span>");
	conditions.push(obj);
}

function delTable(obj){
	var index = $("#tableDiv span").index(obj);
	$(obj).remove();
	tables.splice(index,1);
	if(tables.length==1){
		$("#relDiv .label").remove();
		$("#conditionDiv .label").remove();
		rels = [];
		conditions = [];
	}
}

function delField(obj){
	var index = $("#relDiv span").index(obj);
	$(obj).remove();
	rels.splice(index,1);
}


function delField1(obj){
	var index = $("#conditionDiv span").index(obj);
	$(obj).remove();
	conditions.splice(index,1);
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
<style>
td{
padding:5px;
}
.label{
margin:5px;
font-size:12px;
}
</style>
</head>
<body onload="doInit()">
<div id="toolbar">
<table style="font-size:12px;margin:5px;" id="form">
	<tr>
		<td class="TableData" width="80" >唯一标识：</td>
		<td class="TableData">
			<input type="text" id="identity" name="identity" readonly class="BigInput readonly"/>
		</td>
		<td class="TableData"  width="80">视图名称：</td>
		<td class="TableData">
			<input type="text" id="name" name="name" class="BigInput"/>
		</td>
		<td class="TableData"  width="80">数据源：</td>
		<td class="TableData">
			<select id="bisDataSourceId" name="bisDataSourceId" class="BigSelect">
				
			</select>
			<input name="type" id="type" type="hidden"/>
		</td>
	</tr>
	<tr>
		<td class="TableData" width="80" >数据连接：</td>
		<td class="TableData" colspan="5">
			<span id="tableDiv">
				
			</span>
			<img style="cursor: pointer;" onclick="addTable()" src="/common/images/other/plus.png"/>
		</td>
	</tr>
	<tr>
		<td class="TableData" width="80" >关联关系：</td>
		<td class="TableData" colspan="5">
			<span id="relDiv">
			
			</span>
			<img style="cursor: pointer;" onclick="addRel()" src="/common/images/other/plus.png"/>
		</td>
	</tr>
	<tr>
		<td class="TableData" width="80" >条件设置：</td>
		<td class="TableData" colspan="5">
			<span id="conditionDiv">
				
			</span>
			<img style="cursor: pointer;" onclick="addCondition()" src="/common/images/other/plus.png"/>
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
<!-- &nbsp;<a href="javascript:void(0)" onclick="$('#tips').toggle()">显示/隐藏 SQL提示</a> -->
</div>
<table id="datagrid"  fit="true"></table>
</body>
</html>
