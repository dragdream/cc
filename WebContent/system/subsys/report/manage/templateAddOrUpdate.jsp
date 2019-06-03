<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flowId = request.getParameter("flowId");
	String templateId = request.getParameter("templateId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/zt_webframe/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/datagrid-groupview.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/gray/easyui.css">

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
var flowId = "<%=flowId%>";
var templateId = "<%=templateId%>";

function doInit(){
	//根据流程获取表单字段
	var formItems = loadItems();
	loadConditions();
	if(templateId!="null"){
		var json = tools.requestJsonRs(contextPath+"/report/getTemplate.action",{templateId:templateId});
		bindJsonObj2Cntrl(json.rtData);
	}
	
	var json = tools.requestJsonRs(contextPath+"/report/getTemplateItems.action",{templateId:templateId});
	var rows = json.rtData;
	var opts = $("#preCols option");
	for(var i=0;i<opts.length;i++){
		var obj = $(opts[i]);
		for(var j=0;j<rows.length;j++){
			if(obj.attr("value")==rows[j].item){
				rows[j].title = obj.html();
				break;
			}
		}
	}
	

	$('#datagrid').datagrid({
		data:rows,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		onClickCell: onClickCell,
		columns:[[
			{field:'title',title:'控件名称',width:50},
			{field:'defName',title:'自定义列名称',width:100,editor:'text'},
			{field:'width',title:'宽度',width:20,editor:'text'},
			{field:'_oper',title:'操作',width:30,formatter:function(data,row,index){
				var render = ["<a href='javascript:void(0)' onclick='edit("+index+")'>编辑</a>"];
// 				var render = [];
				render.push("<a href='javascript:void(0)' onclick='delRow("+index+")'>删除</a>");
				return render.join("&nbsp;");
			}}
		]]
	});
	
	
}

function loadConditions(){
	var url = contextPath+"/report/datagridCondition.action?flowId="+flowId;
	var json = tools.requestJsonRs(url,{rows:1000,page:1});
	var rows = json.rows;
	var render = ["<option value=''>无</option>"];
	for(var i=0;i<rows.length;i++){
		render.push("<option value='"+rows[i].sid+"'>"+rows[i].conditionName+"</option>");
	}
	$("#conditionId").html(render.join(""));
}

function loadItems(){
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	var render = ["<option class='ui-state-default' value='RUN_ID'>流水号</option>",
	              "<option class='ui-state-default' value='RUN_NAME'>流程名称</option>",
	              "<option class='ui-state-default' value='RUN_STATUS'>流程状态</option>",
	              "<option class='ui-state-default' value='RUN_USER'>发起人</option>",
	              "<option class='ui-state-default' value='RUN_START'>发起时间</option>",
	              "<option class='ui-state-default' value='RUN_END'>结束时间</option>"];
	for(var i=0;i<items.length;i++){
		render.push("<option class='ui-state-default' value='DATA_"+items[i].itemId+"'>"+items[i].title+"</option>");
	}
	
	$("#preCols").html(render.join(""));
	$("#groupBy").append(render.join(""));
	$("#sortBy").append(render.join(""));
	
	return items;
}

function commit(){
	var para = tools.formToJson($("#toolbar"));
	//组合列表数据
	var rows = $("#datagrid").datagrid("getRows");
	
	var listJson = tools.jsonArray2String(rows);
	para["listJson"] = listJson;
	
	var url = "";
	if(templateId=="null"){//保存
		url = contextPath+"/report/saveTemplate.action";
	}else{
		url = contextPath+"/report/updateTemplate.action";	
	}
	
	var json = tools.requestJsonRs(url,para);
	alert("保存成功");
	
	if(templateId=="null"){//新增
		window.location = "templateAddOrUpdate.jsp?flowId="+flowId+"&templateId="+json.rtData;
	}
}

function addToShow(){
	var opt = $("#preCols option:selected:first");
	var row = {};
	row["title"] = opt.html();
	row["defName"] = opt.html();
	row["item"] = opt.attr("value");
	row["width"] = 100;
	
	$('#datagrid').datagrid('appendRow',row);
}

function delRow(rowIndex){
	$('#datagrid').datagrid('deleteRow',rowIndex);
}

var target;
function edit(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	target = rows[rowIndex];
	var colModel = rows[rowIndex].colModel;
	
	if(colModel=="" || !colModel){
		colModel = {};
	}else{
		colModel = eval("("+colModel+")");
	}
	
	var conditons = colModel.conditons;
	if(!conditons){
		conditons = [];
	}
	
	var fix = colModel.fix||"";
	var fixName = colModel.fixName||"";
	var cal = colModel.cal||"";
	var decimal = colModel.decimal||"";

	$("#fix").attr("value",fix);
	$("#fixName").attr("value",fixName);
	$("#cal").attr("value",cal);
	$("#decimal").attr("value",decimal);
	$("#detailDiv").html("");
	
	for(var i=0;i<conditons.length;i++){
		addRows(conditons[i]);
	}
	
	//填充数据
	$("#detailConfig").modal("show");
}

function addRows(data){
	var render = ["<tr>"];
	render.push("<td clazz='c1'><select>"+renderCondition(data.c1)+"</select></td>");
	render.push("<td clazz='v1'><input style='width:70px' value='"+(data.v1||"")+"' /></td>");
	render.push("<td clazz='l'><select>"+renderLogical(data.l)+"</select></td>");
	render.push("<td clazz='c2'><select>"+renderCondition(data.c2)+"</select></td>");
	render.push("<td clazz='v2'><input style='width:70px' value='"+(data.v2||"")+"' /></td>");
	render.push("<td clazz='color'><select style='width:50px'>"+renderColor(data.color||"")+"</select></td>");
	render.push("<td clazz='content'><input style='width:70px' value='"+(data.content||"")+"' /></td>");
	render.push("<td><a href='javascript:void(0)' onclick=\"$(this).parent().parent().remove()\">-</a></td>");
	render.push("</tr>");
	$("#detailDiv").append(render.join(""));
}

function renderCondition(val){
	var render = [];
	render.push("<option value='小于' "+(val=="小于"?"selected":"")+">小于</option>");
	render.push("<option value='大于' "+(val=="大于"?"selected":"")+">大于</option>");
	render.push("<option value='等于' "+(val=="等于"?"selected":"")+">等于</option>");
	render.push("<option value='大于等于' "+(val=="大于等于"?"selected":"")+">大于等于</option>");
	render.push("<option value='小于等于' "+(val=="小于等于"?"selected":"")+">小于等于</option>");
	render.push("<option value='包含' "+(val=="包含"?"selected":"")+">包含</option>");
	render.push("<option value='开始于' "+(val=="开始于"?"selected":"")+">开始于</option>");
	render.push("<option value='结束于' "+(val=="结束于"?"selected":"")+">结束于</option>");
	render.push("<option value='不包含' "+(val=="不包含"?"selected":"")+">不包含</option>");
	return render.join("");
}

function renderLogical(val){
	var render = [];
	render.push("<option value='或' "+(val=="或"?"selected":"")+">或</option>");
	render.push("<option value='并' "+(val=="并"?"selected":"")+">并</option>");
	return render.join("");
}

function renderColor(val){
	var render = [];
	render.push("<option value='#000000' "+(val=="#000000"?"selected":"")+" style='background:#000000'></option>");
	render.push("<option value='#993300' "+(val=="#993300"?"selected":"")+" style='background:#993300'></option>");
	render.push("<option value='#333300' "+(val=="#333300"?"selected":"")+" style='background:#333300'></option>");
	render.push("<option value='#003300' "+(val=="#003300"?"selected":"")+" style='background:#003300'></option>");
	render.push("<option value='#003366' "+(val=="#003366"?"selected":"")+" style='background:#003366'></option>");
	render.push("<option value='#000080' "+(val=="#000080"?"selected":"")+" style='background:#000080'></option>");
	render.push("<option value='#333399' "+(val=="#333399"?"selected":"")+" style='background:#333399'></option>");
	render.push("<option value='#333333' "+(val=="#333333"?"selected":"")+" style='background:#333333'></option>");
	render.push("<option value='#800000' "+(val=="#800000"?"selected":"")+" style='background:#800000'></option>");
	render.push("<option value='#FF6600' "+(val=="#FF6600"?"selected":"")+" style='background:#FF6600'></option>");
	render.push("<option value='#808000' "+(val=="#808000"?"selected":"")+" style='background:#808000'></option>");
	render.push("<option value='#008000' "+(val=="#008000"?"selected":"")+" style='background:#008000'></option>");
	render.push("<option value='#008080' "+(val=="#008080"?"selected":"")+" style='background:#008080'></option>");
	render.push("<option value='#0000FF' "+(val=="#0000FF"?"selected":"")+" style='background:#0000FF'></option>");
	render.push("<option value='#666699' "+(val=="#666699"?"selected":"")+" style='background:#666699'></option>");
	render.push("<option value='#808080' "+(val=="#808080"?"selected":"")+" style='background:#808080'></option>");
	render.push("<option value='#FF0000' "+(val=="#FF0000"?"selected":"")+" style='background:#FF0000'></option>");
	render.push("<option value='#FF9900' "+(val=="#FF9900"?"selected":"")+" style='background:#FF9900'></option>");
	render.push("<option value='#99CC00' "+(val=="#99CC00"?"selected":"")+" style='background:#99CC00'></option>");
	render.push("<option value='#339966' "+(val=="#339966"?"selected":"")+" style='background:#339966'></option>");
	render.push("<option value='#33CCCC' "+(val=="#33CCCC"?"selected":"")+" style='background:#33CCCC'></option>");
	render.push("<option value='#3366FF' "+(val=="#3366FF"?"selected":"")+" style='background:#3366FF'></option>");
	render.push("<option value='#800080' "+(val=="#800080"?"selected":"")+" style='background:#800080'></option>");
	render.push("<option value='#969696' "+(val=="#969696"?"selected":"")+" style='background:#969696'></option>");
	render.push("<option value='#FF00FF' "+(val=="#FF00FF"?"selected":"")+" style='background:#FF00FF'></option>");
	render.push("<option value='#FFCC00' "+(val=="#FFCC00"?"selected":"")+" style='background:#FFCC00'></option>");
	render.push("<option value='#FFFF00' "+(val=="#FFFF00"?"selected":"")+" style='background:#FFFF00'></option>");
	render.push("<option value='#00FF00' "+(val=="#00FF00"?"selected":"")+" style='background:#00FF00'></option>");
	render.push("<option value='#00FFFF' "+(val=="#00FFFF"?"selected":"")+" style='background:#00FFFF'></option>");
	render.push("<option value='#00CCFF' "+(val=="#00CCFF"?"selected":"")+" style='background:#00CCFF'></option>");
	render.push("<option value='#993366' "+(val=="#993366"?"selected":"")+" style='background:#993366'></option>");
	render.push("<option value='#C0C0C0' "+(val=="#C0C0C0"?"selected":"")+" style='background:#C0C0C0'></option>");
	render.push("<option value='#FF99CC' "+(val=="#FF99CC"?"selected":"")+" style='background:#FF99CC'></option>");
	render.push("<option value='#FFCC99' "+(val=="#FFCC99"?"selected":"")+" style='background:#FFCC99'></option>");
	render.push("<option value='#FFFF99' "+(val=="#FFFF99"?"selected":"")+" style='background:#FFFF99'></option>");
	render.push("<option value='#CCFFCC' "+(val=="#CCFFCC"?"selected":"")+" style='background:#CCFFCC'></option>");
	render.push("<option value='#CCFFFF' "+(val=="#CCFFFF"?"selected":"")+" style='background:#CCFFFF'></option>");
	render.push("<option value='#99CCFF' "+(val=="#99CCFF"?"selected":"")+" style='background:#99CCFF'></option>");
	render.push("<option value='#CC99FF' "+(val=="#CC99FF"?"selected":"")+" style='background:#CC99FF'></option>");
	render.push("<option value='#FFFFFF' "+(val=="#FFFFFF"?"selected":"")+" style='background:#FFFFFF'></option>");
	return render.join("");
}

function setConfig(){
	var fix = $("#fix").val();
	var fixName = $("#fixName").val();
	var cal = $("#cal").val();
	var decimal = $("#decimal").val();
	
	var jsonObj = {fix:fix,fixName:fixName,cal:cal,decimal:decimal};
	var arr = [];
	var trs = $("#detailDiv tr");
	for(var i=0;i<trs.length;i++){
		var c1 = $(trs[i]).find("td[clazz=c1]:first select:first");
		var v1 = $(trs[i]).find("td[clazz=v1]:first input:first");
		var l = $(trs[i]).find("td[clazz=l]:first select:first");
		var c2 = $(trs[i]).find("td[clazz=c2]:first select:first");
		var v2 = $(trs[i]).find("td[clazz=v2]:first input:first");
		var color = $(trs[i]).find("td[clazz=color]:first select:first");
		var content = $(trs[i]).find("td[clazz=content]:first input:first");
		
		arr.push({
			c1:c1.val(),
			v1:v1.val(),
			l:l.val(),
			c2:c2.val(),
			v2:v2.val(),
			color:color.val(),
			content:content.val()
		});
	}
	jsonObj["conditons"] = arr;
	
	target.colModel = tools.jsonObj2String(jsonObj);
	$("#detailConfig").modal("hide");
}
</script>
</head>
<body onload="doInit()">
<div id="toolbar" >
	<input name="flowId" type="hidden" value="<%=flowId %>" />
	<input name="templateId" type="hidden" value="<%=templateId %>" />
	<table id="table" style="font-size:12px;width:800px">
		<tr>
			<td>模板名称：</td>
			<td>
				<input type="text" class="BigInput" name="tplName" id="tplName"/>
			</td>
			<td>绑定条件：</td>
			<td>
				<select id="conditionId" name="conditionId" class="BigSelect">
					
				</select>
			</td>
		</tr>
		<tr>
			<td>分组：</td>
			<td>
				<select id="groupBy" name="groupBy" class="BigSelect">
					<option value="">无</option>
				</select>
			</td>
			<td>排序类型：</td>
			<td>
				<select id="groupByOrder" name="groupByOrder" class="BigSelect">
					<option value="">无</option>
					<option value="asc">升序</option>
					<option value="desc">降序</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>排序：</td>
			<td>
				<select id="sortBy" name="sortBy" class="BigSelect">
					<option value="">无</option>
				</select>
			</td>
			<td>排序类型：</td>
			<td>
				<select id="sortByOrder" name="sortByOrder" class="BigSelect">
					<option value="">无</option>
					<option value="asc">升序</option>
					<option value="desc">降序</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				分页：
			</td>
			<td>
				<select id="pageSize" name="pageSize" class="BigSelect">
					<option value="0">无限制</option>
					<option value="10">10条数据</option>
					<option value="20">20条数据</option>
					<option value="30">30条数据</option>
					<option value="40">40条数据</option>
					<option value="50">50条数据</option>
					<option value="60">60条数据</option>
					<option value="70">70条数据</option>
					<option value="80">80条数据</option>
					<option value="90">90条数据</option>
					<option value="100">100条数据</option>
					<option value="110">110条数据</option>
					<option value="120">120条数据</option>
				</select>
			</td>
			<td>
				分组合并：
			</td>
			<td>
				<select id="mergeGroup" name="mergeGroup" class="BigSelect">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>人员权限：</td>
			<td>
				<input type="hidden" id="userPrivIds" name="userPrivIds" />
				<textarea class="BigTextarea readonly" id="userPrivNames" style="height:50px;width:250px;" readonly></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['userPrivIds','userPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('userPrivIds','userPrivNames')">清空</a>
			</td>
			<td>部门权限：</td>
			<td>
				<input type="hidden" id="deptPrivIds" name="deptPrivIds" />
				<textarea class="BigTextarea readonly" id="deptPrivNames" style="height:50px;width:250px;" readonly></textarea>
				<a href="javascript:void(0)" onclick="selectDept(['deptPrivIds','deptPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('deptPrivIds','deptPrivNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td>
				预选列：
			</td>
			<td>
				<select id="preCols" class="BigSelect"></select>
				<a href="javascript:void(0)" onclick="addToShow()">添加到显示列</a>
			</td>
			<td>
			<button class="btn btn-primary" type="button" onclick="commit()">保存</button>
			</td>
			<td>
				
			</td>
		</tr>
	</table>
</div>
<table id="datagrid" style="font-size:12px;" fit="true"></table>

<div class="modal fade" id="detailConfig">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">编辑列</h4>
      </div>
      <div class="modal-body" style="font-size:12px">
		<table style="width:100%;font-size:12px">
			<tr>
				<td>前后缀设置：</td>
				<td>
					<select id="fix" class="BigSelect">
						<option value="0">无</option>
						<option value="1">前缀</option>
						<option value="2">后缀</option>
					</select>
				</td>
				<td>缀名称：</td>
				<td>
					<input type="text" class="BigInput" id="fixName"/>
				</td>
			</tr>
			<tr>
				<td>计算类型：</td>
				<td>
					<select id="cal" class="BigSelect">
						<option value="0">无</option>
						<option value="1">平均</option>
						<option value="2">总和</option>
						<option value="3">最大值</option>
						<option value="4">最小值</option>
						<option value="5">数量</option>
					</select>
				</td>
				<td>保留位数：</td>
				<td>
					<input type="text" class="BigInput" id="decimal"/>
				</td>
			</tr>
		</table>
		<table style="width:100%;font-size:12px">
			<tr>
				<td><b>条件</b></td>
				<td><b>值</b></td>
				<td><b>逻辑</b></td>
				<td><b>条件</b></td>
				<td><b>值</b></td>
				<td><b>颜色</b></td>
				<td><b>显示</b></td>
				<td><b>操作</b><a href="javascript:void(0)" onclick="addRows({})">+</a></td>
			</tr>
			<tbody id="detailDiv">
			</tbody>
		</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="setConfig()">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
<script type="text/javascript">
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#datagrid').datagrid('validateRow', editIndex)){
        $('#datagrid').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell(index, field){
    if (endEditing()){
        $('#datagrid').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}
</script>
</html>