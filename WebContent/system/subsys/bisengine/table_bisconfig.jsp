<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<% 
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
	int catId = TeeStringUtil.getInteger(request.getParameter("catId"),0);
	int tableEngineId = TeeStringUtil.getInteger(request.getParameter("tableEngineId"),0);
%>
<title>引擎规则配置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.readonly{
background:#e2e2e2;
}
</style>
<script>
var sid = <%=sid%>;
var catId = <%=catId%>;
var tableEngineId = <%=tableEngineId%>;
var curObj ;
var xlist = [];
function doInit(){
	if(sid!=0){
		var url = contextPath+"/bisTableEngine/getBisTableEngine.action";
		var json = tools.requestJsonRs(url,{sid:tableEngineId});
		var bisModel = eval("("+json.rtData.bisModel+")");
		var flowId = json.rtData.flowId;
		if(flowId!=0){//如果存在绑定流程，则初始化数据
			//先初始化字段映射规则
			var detail = bisModel.detail;
			for(var i=0;i<detail.length;i++){
				var item = detail[i];
				var render = [];
				if(item.type=="1"){
					render.push("<div detail=\"{a1:'"+item.a1+"',a2:'"+item.a2+"',b1:'"+item.b1+"',type:'"+item.type+"',vtype:'"+item.vtype+"',etype:'"+item.etype+"'}\">");
					render.push(""+item.b1+"&nbsp;=>&nbsp;"+item.a1+"&nbsp;<img src='"+systemImagePath+"/filetype/defaut.gif' title='详细设置' onclick='detail(this)'/>&nbsp;<img src='"+systemImagePath+"/upload/remove.png' title='移除' onclick='remove0(this)'/>");
					render.push("</div>");
					$("#detailItem").append(render.join(""));
				}else if(item.type=="2"){
					render.push("<div detail=\"{a1:'"+item.a1+"',a2:'"+item.a2+"',b1:'"+item.b1+"',type:'"+item.type+"',rule:'"+item.rule+"',etype:'"+item.etype+"'}\">");
					render.push(""+item.b1+"&nbsp;=>&nbsp;"+item.a1+"&nbsp;<img src='"+systemImagePath+"/filetype/defaut.gif' title='详细设置' onclick='detail(this)'/>&nbsp;<img src='"+systemImagePath+"/upload/remove.png' title='移除' onclick='remove0(this)'/>");
					render.push("</div>");
					$("#detailItem").append(render.join(""));
				}else if(item.type=="3"){
					render.push("<div detail=\"{a1:'"+item.a1+"',a2:'"+item.a2+"',b1:'"+item.b1+"',type:'"+item.type+"',macro:'"+item.macro+"',etype:'"+item.etype+"'}\">");
					render.push(""+item.b1+"&nbsp;=>&nbsp;"+item.a1+"&nbsp;<img src='"+systemImagePath+"/filetype/defaut.gif' title='详细设置' onclick='detail(this)'/>&nbsp;<img src='"+systemImagePath+"/upload/remove.png' title='移除' onclick='remove0(this)'/>");
					render.push("</div>");
					$("#detailItem").append(render.join(""));
				}
			}
			
			var conditionCluster = bisModel.condition;
			
// 			//初始化条件
// 			for(var i=0;i<conditions.length;i++){
			var prcsTo = conditionCluster.prcsTo;
			
			var condition = conditionCluster.condition;
			var exp = conditionCluster.exp;
			$("#exp").attr("value",exp);
			for(var j=0;j<condition.length;j++){
				addCondition0("conditionContainer",condition[j]);
			}
			
// 			}
			
// 			
		}
		
		//初始化业务字段
		loadBisField(sid);
		
		//初始化表单字段
		loadFlowField(flowId);
		
		ZTreeTool.comboCtrl($("#flowId").attr("value",flowId),{url:contextPath+"/workQuery/getFlowType2SelectCtrl.action",callback:function(treeNode){
			loadFlowField(treeNode.id);
			loadProcessList(treeNode.id);
		}});
		
		loadProcessList(flowId);
		loadFormItems(flowId);
		$("#process").attr("value",bisModel.prcsId+"");
		$("#remark").attr("value",json.rtData.remark);
		
		//判断引擎类型
		$("#type0").attr("value",json.rtData.type);
		typeChanged($("#type0")[0]);
		$("#listinfos")[0].value = json.rtData.listTitle;
		listChanged($("#listinfos")[0]);
		$("#status").attr("value",json.rtData.status);
// 		bindJsonObj2Cntrl(json.rtData);
// 		$("#bisPrefix").hide();
// 		$("#tableName").attr("readonly","readonly").addClass("readonly");
	}
// 	dataSourceChange($("#dataSource")[0]);
}

function loadProcessList(flowId){
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessList.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var list = json.rtData;
	if(json.rtState){
		//渲染步骤列表
		var render = [];
		for(var i=0;i<list.length;i++){
			var data = list[i];
			render.push("<option value='"+data.prcsId+"'>"+data.prcsName+"</option>");
		}
		$("#process").html(render.join(""));
	}
}

function loadFormItems(flowId){
	//获取对应表单字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	var html = "<optgroup label=\"表单字段\">";
	for(var i=0;i<items.length;i++){
		var data = items[i];
		html+="<option value='"+data.itemId+"'>"+data.title+"</option>";
	}
	html +="</optgroup>";
	html +="<optgroup label=\"流程实例信息\">";
	html +="<option value=\"[发起人姓名]\">[发起人姓名]</option>";
	html +="<option value=\"[发起人帐号]\">[发起人帐号]</option>";
	html +="<option value=\"[发起人部门]\">[发起人部门]</option>";
	html +="<option value=\"[发起人角色]\">[发起人角色]</option>";
	html +="<option value=\"[主办人会签意见]\">[主办人会签意见]</option>";
	html +="<option value=\"[经办人会签意见]\">[经办人会签意见]</option>";
	html +="<option value=\"[公共附件名称]\">[公共附件名称]</option>";
	html +="<option value=\"[公共附件个数]\">[公共附件个数]</option>";
	html +="<option value=\"[当前步骤序号]\">[当前步骤序号]</option>";
	html +="<option value=\"[当前步骤自增ID号]\">[当前步骤自增ID号]</option>";
	html +="<option value=\"[当前步骤名称]\">[当前步骤名称]</option>";
	html +="<option value=\"[当前主办人姓名]\">[当前主办人姓名]</option>";
	html +="<option value=\"[当前主办人帐号]\">[当前主办人帐号]</option>";
	html +="<option value=\"[当前主办人角色]\">[当前主办人角色]</option>";
	html +="<option value=\"[当前主办人辅助角色]\">[当前主办人辅助角色]</option>";
	html +="<option value=\"[当前主办人部门]\">[当前主办人部门]</option>";
	html +="<option value=\"[当前主办人上级部门]\">[当前主办人上级部门]</option>";
	html +="<option value=\"#流程事件\">#流程事件</option>";
	html +="</optgroup>";

	html +="<optgroup label=\"流程变量\">";
	html +="</optgroup>";
	$("#fields").append(html);
}

function loadFlowField(flowId){
	xlist = [];
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json1 = tools.requestJsonRs(url,{flowId:flowId});
	if(json1.rtState){
		var items = json1.rtData;
		var render = ["<optgroup label='表单字段'>"];
		render.push("<option value='[占位字段]'>[占位字段]</option>");
		for(var i=0;i<items.length;i++){
			var data = items[i];
			if(data.xtype=="xlist"){
				xlist.push(data);
			}
			render.push("<option value='"+data.title+"'>"+data.title+"</option>");
		}
		render.push("</optgroup>");
		$("#flowField").html(render.join(""));
	}
	
	
}

function loadBisField(tableId){
	var url = contextPath+'/bisTableField/datagrid.action?bisTableId='+tableId;
	var json = tools.requestJsonRs(url,{page:1,rows:10000});
	var rows = json.rows;
	var render = ["<optgroup label='业务表字段'>"];
	for(var i=0;i<rows.length;i++){
		var data = rows[i];
		render.push("<option value='"+data.fieldName+"'>"+data.fieldDesc+"</option>");
	}
	render.push("</optgroup>");
	$("#bisField").html(render.join(""));
}

function doMapping(){
	var bisField = $("#bisField option:selected");
	var flowField = $("#flowField option:selected");
	var render = [];
	render.push("<div detail=\"{a1:'"+bisField.html()+"',a2:'"+bisField.attr("value")+"',b1:'"+flowField.html()+"',type:'1',vtype:'1',etype:'2'}\">");
	render.push(""+flowField.html()+"&nbsp;=>&nbsp;"+bisField.html()+"&nbsp;<img src='"+systemImagePath+"/filetype/defaut.gif' title='详细设置' onclick='detail(this)'/>&nbsp;<img src='"+systemImagePath+"/upload/remove.png' title='移除' onclick='remove0(this)'/>");
	render.push("</div>");
	$("#detailItem").append(render.join(""));
}

function remove0(cur){
	$(cur).parent().remove();
}

function detail(cur){
	var item = $(cur).parent();
	var detail = eval("("+item.attr("detail")+")");
	var type = detail.type;
	var vtype = detail.vtype;
	var etype = detail.etype;
	var rule = detail.rule;
	var macro = detail.macro;
	swit(type+"");
	$("#type").attr("value",type);
	$("#vtype").attr("value",vtype);
	$("#etype").attr("value",etype);
	$("#rule").attr("value",rule);
	$("#macro").attr("value",macro);
	curObj = item;
	$("#detailConfig").modal("show");
}

function swit(type){
	if(type=="1"){
		$("#vtypeDiv").show();
		$("#ruleDiv").hide();
		$("#macroDiv").hide();
	}else if(type=="2"){
		$("#ruleDiv").show();
		$("#vtypeDiv").hide();
		$("#macroDiv").hide();
	}else if(type=="3"){
		$("#macroDiv").show();
		$("#ruleDiv").hide();
		$("#vtypeDiv").hide();
	}
}

function setConfig(){
	var type = $("#type").val();
	var vtype = $("#vtype").val();
	var etype = $("#etype").val();
	var rule = $("#rule").val();
	var macro = $("#macro").val();
	var item = $(curObj);
	var detail = eval("("+item.attr("detail")+")"); 
	if(type=="1"){//键值对应
		detail.type=type;
		detail.etype=etype;
		detail.vtype=vtype;
	}else if(type=="2"){//键值规则
		detail.type=type;
		detail.etype=etype;
		detail.rule=rule;
	}else if(type=="3"){//宏
		detail.type=type;
		detail.etype=etype;
		detail.macro=macro;
	}
	$(curObj).attr("detail",tools.jsonObj2String(detail));
	$("#detailConfig").modal("hide");
}

function addCondition(){
	var field = $("#fields option:selected");
	var oper = $("#oper option:selected");
	var val = $("#value");
	var content = "{"+field.html()+"} "+oper.html()+" {"+val.val()+"}";
	var model = "{name:'"+field.html()+"',value:'"+val.val()+"',oper:'"+oper.html()+"'}";
	addCondition0("conditionContainer",eval("("+model+")"));
}

function addCondition0(div,model){
	var modelStr = "{name:'"+model.name+"',value:'"+model.value+"',oper:'"+model.oper+"'}";
	var order = getOrder($("#"+div));
	//$("#"+div).append("<div model=\""+tools.jsonObj2String(model)+"\"><span>{"+(order+1)+"}</span>'"+model.name+"'&nbsp"+model.oper+"&nbsp;'"+model.value+"'<a href='javascript:void()' onclick='removeConditionItem(this,\""+div+"\")'>[x]</a></div>");
	$("#"+div).append("<div model=\""+ modelStr +"\"><span>{"+(order+1)+"}</span>'"+model.name+"'&nbsp"+model.oper+"&nbsp;'"+model.value+"'<a href='javascript:void()' onclick='removeConditionItem(this,\""+div+"\")'>[x]</a></div>");
}

function getOrder(container){
	return $(container).children().length;
}

function recountOrder(div){
	$("#"+div).children().each(function(i,obj){
		$(obj).find("span:first").html("{"+(i+1)+"}");
	});
}

function removeConditionItem(cur,div){
	$(cur).parent().remove();
	recountOrder(div);
}

function commit(){
	var condition = compositRequestData();
	//组合detail
	var divs = $("#detailItem div");
	var details = [];
	divs.each(function(i,obj){
		var detail = eval("("+obj.getAttribute("detail")+")");
		details.push(detail);
	});
	var detail = tools.jsonArray2String(details);
	var requestData = "{'detail':"+detail+",'condition':"+condition+",'prcsId':'"+$("#process").val()+"'}";
	var json = tools.requestJsonRs(contextPath+"/bisTableEngine/updateBisTableEngine.action",{bisModel:requestData,sid:tableEngineId,flowId:$("#flowId").val(),bisTableId:sid,type:$("#type0").val(),listTitle:$("#listinfos").val(),status:$("#status").val(),remark:$("#remark").val()});
	if(json.rtState){
		alert("保存成功");
	}
}

//组装请求数据
function compositRequestData(){
	var item = {};
	item["prcsTo"]=$("#process").val();
	var conditionArr = new Array();
	var conditionItems = $("#conditionContainer").children();
	for(var j=0;j<conditionItems.length;j++){
		var obj = eval("("+conditionItems[j].getAttribute("model")+")");
		conditionArr.push(obj);
	}
	item["condition"]=conditionArr;
	item["exp"] = $("#exp").val();
	return tools.jsonObj2String(item);
}


function typeChanged(obj){
	if(obj.value==1){//表单字段
		$("#listinfos").html("");
		loadFlowField($("#flowId").val());
	}else if(obj.value==2){//明细表
		$("#listinfos").html("");
		var o = $("<option value=\""+0+"\"></option>");
		o.data("data",item);
		$("#listinfos").append(o);
		for(var i=0;i<xlist.length;i++){
			var item = xlist[i];
			var o = $("<option value=\""+item.title+"\">"+item.title+"</option>");
			o.data("data",item);
			$("#listinfos").append(o);
		}
	}else if(obj.value==3){//字段分隔
		$("#flowField").html("");
	
		var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
		var json1 = tools.requestJsonRs(url,{flowId:$("#flowId").val()});
		var items = json1.rtData;
		var render = ["<optgroup label='业务表字段'>"];
		for(var i=0;i<items.length;i++){
			var data = items[i];
			if(data.xtype=="xlist"){
				xlist.push(data);
			}
			render.push("<option value='"+data.title+"'>"+data.title+"</option>");
		}
		render.push("</optgroup>");
		$("#listinfos").html(render.join(""));
		
		
		$("#flowField").append("<option value='关联字段'>关联字段</option>");
		$("#flowField").append("<option value='[占位字段]'>[占位字段]</option>");
		
	}
}

function listChanged(obj){
	if(obj.value==0){
		return;
	}
	if($("#type0").val()=="3"){
		return;
	}
	var selected = $(obj).find("option:selected");
	var data = selected.data("data");
	var model = eval("("+data.model+")");
	
	$("#flowField").html("<option value='bisKey'>bisKey</option><option value='[占位字段]'>[占位字段]</option>");
	
	
	//渲染模型
	for(var i=0;i<model.length;i++){
		$("#flowField").append("<option value=\""+model[i].title+"\">"+model[i].title+"</option>");
	}
}

</script>
</head>
<body style="padding:5px" onload="doInit()" >
<div class="moduleHeader">
	<b>引擎规则配置</b>
</div>
<center>
<form id="form1">
	<table class="TableBlock" style="width:800px">
		<tr>
			<td class="TableData">绑定流程：</td>
			<td class="TableData" align="left">
				<input type="text" id="flowId" name="flowId" class="BigInput"/>
			</td>
		</tr>
		<tr>
			<td class="TableData">引擎类型：</td>
			<td class="TableData" align="left">
				<select type="text" id="type0" name="type" class="BigInput" onchange="typeChanged(this)">
					<option value="1">表单</option>
					<option value="2">明细表</option>
					<option value="3">字段分隔</option>
				</select>
				关联字段：
				<select id="listinfos" class="BigSelect"  onchange="listChanged(this)">
					
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2" align=center>
				<select multiple style="height:160px;width:140px;" id="flowField">
				</select>
				&nbsp;&nbsp;
				<select multiple style="height:160px;width:140px;" id="bisField">
				</select>
				<br/>
				<button class="btn btn-primary" onclick="doMapping()" type="button">映射</button>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2" id="detailItem">
				
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2" >
				针对步骤：<select id="process"></select>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2" >
				<b>条件设置：</b>
				<select id="fields" class="BigSelect">
				</select>
				<select id="oper" class="BigSelect">
					<option value="1">等于</option>
					<option value="2">不等于</option>
					<option value="3">大于</option>
					<option value="4">大于等于</option>
					<option value="5">小于</option>
					<option value="6">小于等于</option>
					<option value="7">以字符开头</option>
					<option value="8">以字符结尾</option>
					<option value="9">包含</option>
					<option value="10">不包含</option>
				</select>
				<input id="value" class="BigInput"/>
				<a href='javascript:void(0)' onclick="addCondition()">添加</a>
				
				<div id="conditionContainer"></div>
				
				条件表达式：<input id="exp" type="text" class="BigInput" style="width:600px;"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" >
				规则描述：
				<br/>
				<textarea style="width:80%;height:100px" name="remark" id="remark"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="button" class="btn btn-primary" onclick="commit()">提交</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-default" onclick="window.location='table_bis_engines.jsp?sid=<%=sid %>&catId=<%=catId %>'">返回</button>
			</td>
		</tr>
	</table>
	<input type="hidden" name="sid" value="<%=sid %>" />
	<input type="hidden" name="bisCatId" id="bisCatId" value="<%=catId %>" />
	<input type="hidden" name="gen" id="gen"/>
	<input type="hidden" name="status" id="status"/>
</form>
</center>

<div class="modal fade" id="detailConfig">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">详细设置</h4>
      </div>
      <div class="modal-body" style="font-size:12px">
        <div style="margin-bottom:5px">
		映射类别：
		<select id="type" onchange="swit(this.value)">
			<option value="1">键值对应</option>
			<option value="2">键值规则</option>
			<option value="3">宏</option>
		</select>
		</div>
		<div id="vtypeDiv" style="display:none;margin-bottom:5px">
		映射值设置：
		<select id="vtype">
			<option value="1">表单原值</option>
			<option value="2">表单扩展值</option>
		</select>
		</div>
		<div id="ruleDiv" style="display:none;margin-bottom:5px">
			规则设置：<input type="text" id="rule" />
		</div>
		<div id="macroDiv" style="display:none;margin-bottom:5px">
			宏设置：<input type="text" id="macro" style="width:90%"/>
			<br/>
			$userId => 当前用户ID<br/>
			$userSid => 当前用户主键ID<br/>
			$userName => 当前用户名<br/>
			$deptId => 当前人部门ID<br/>
			$deptName => 当前人部门名称<br/>
			$deptFullName => 当前人部门全名称<br/>
			$roleId => 当前人角色ID<br/>
			$roleName => 当前人角色名称<br/>
			$beginUserId => 流程发起人ID<br/>
			$beginUserSid => 流程发起人用户主键ID<br/>
			$beginUserName => 流程发起人用户名<br/>
			$beginUserDeptId => 流程发起人部门ID<br/>
			$beginUserDeptName => 流程发起人部门名称<br/>
			$beginUserRoleId => 流程发起人角色ID<br/>
			$beginUserRoleName => 流程发起人角色名称<br/>
			$runId => 当前流水号<br/>
			$curDate => 当前日期<br/>
			$docId => 正文附件ID<br/>
			$attachIds => 公共附件ID字符串<br/>
			#DATA_表单控件名称 => 表单控件原值<br/>
			#EXTRA_表单控件名称 => 表单控件扩展值<br/>
			#DATA_表单控件名称|键1^值1,键2^值2 => 表单控件原值，并加入键值对应<br/>
			#VAR_流程变量名称 => 流程变量值<br/>
			#BIS_业务表名 => 对应业务表的bisKey<br/>
		</div>
		<div style=";margin-bottom:5px">
		表单字段类型：<select id="etype">
			<option value="1">整型</option>
			<option value="2">字符串</option>
			<option value="3">日期时间</option>
			<option value="4">时间戳</option>
			<option value="5">负整型</option>
			<option value="6">空值(NULL)</option>
			<option value="7">双精度</option>
		</select>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="setConfig()">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
