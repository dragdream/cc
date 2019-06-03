<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;
var itemIndex = {};
var nextPrcsList;
function doInit(){
// 	$("body").layout({
// 		north:{
// 			size:45,
// 			slidable:false,
// 			resizable:false,
// 			spacing_open:0,
// 			spacing_closed:0
// 		}
// 	});
	
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fp = json.rtData;
	if(json.rtState){
		nextPrcsList = fp.params.nextPrcsList;
		//渲染步骤列表
		for(var i=0;i<nextPrcsList.length;i++){
			var data = nextPrcsList[i];
			var html = "<div class=\"panel panel-default\">";
			html += "<div class=\"panel-heading\">路径：[步骤"+fp.prcsId+"]"+fp.prcsName+"&nbsp;<i class=\"glyphicon glyphicon-arrow-right\"></i>&nbsp;[步骤"+data.prcsId+"]"+data.prcsName+"&nbsp;&nbsp;<b><a href=\"javascript:void(0)\" onclick=\"addCondition('c"+data.prcsId+"')\">添加条件</a></b></div>";
			html+="<div class=\"panel-body\" style=\"padding:5px;\">";
			html+="<div id=\"c"+data.prcsId+"\" style=\"min-height:20px\"></div>";
			html+="<br/><b>路径描述：</b>";
			html+="<div style=\"margin-top:5px;\"><input id=\"label"+data.prcsId+"\" placeholder=\"为路径添加一个个性化标签说明\" type=\"text\" class=\"form-control\" title=\"为路径添加一个个性化标签说明\"></div>";
			html+="<br/><b>条件表达式：</b>";
			html+="<div style=\"margin-top:5px;\"><input id=\"ci"+data.prcsId+"\" placeholder=\"请输入条件表达式，例如   {1} and {2} or {3}\" type=\"text\" class=\"form-control\" title=\"请输入条件表达式，例如   {1} and {2} or {3}\"></div>";
			html+="</div>";
			html+="</div>";
			$("#renderBody").append(html);
		}

		//渲染条件数据
		var url = contextPath+"/flowProcess/getCondition.action";
		var json = tools.requestJsonRs(url,{prcsId:prcsId});
		var conditions;
		if(json.rtData!="" && json.rtData && json.rtData!=null){
			conditions = eval("("+json.rtData+")");
		}
		if(conditions==null || !conditions){
			conditions = [];
		}
		
		for(var i=0;i<conditions.length;i++){
			var prcsTo = conditions[i].prcsTo;
			var condition = conditions[i].condition;
			var exp = conditions[i].exp;
			var label = conditions[i].label;
			for(var j=0;j<condition.length;j++){
				addCondition0("c"+prcsTo,condition[j]);
			}
			$("#ci"+prcsTo).attr("value",exp);
			$("#label"+prcsTo).attr("value",label);
		}
	}

	
	//获取对应表单字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	var html = "<optgroup label=\"表单字段\">";
	for(var i=0;i<items.length;i++){
		var data = items[i];
		itemIndex[data.itemId]=data.title;
		html+="<option value='"+data.itemId+"'>"+data.title+"</option>";
	}
	html +="</optgroup>";
	html +="<optgroup label=\"流程实例信息\">";
	html +="<option value=\"[发起人姓名]\">[发起人姓名]</option>";
	html +="<option value=\"[发起人帐号]\">[发起人帐号]</option>";
	html +="<option value=\"[发起人部门]\">[发起人部门]</option>";
	html +="<option value=\"[发起人辅助部门]\">[发起人辅助部门]</option>";
	html +="<option value=\"[发起人角色]\">[发起人角色]</option>";
	html +="<option value=\"[发起人角色排序号]\">[发起人角色排序号]</option>";
	html +="<option value=\"[发起人辅助角色]\">[发起人辅助角色]</option>";
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
	html +="<option value=\"[当前主办人角色排序号]\">[当前主办人角色排序号]</option>";
	html +="<option value=\"[当前主办人辅助角色]\">[当前主办人辅助角色]</option>";
	html +="<option value=\"[当前主办人部门]\">[当前主办人部门]</option>";
	html +="<option value=\"[当前主办人上级部门]\">[当前主办人上级部门]</option>";
	html +="<option value=\"#流程事件\">#流程事件</option>";
	html +="</optgroup>";

	html +="<optgroup label=\"流程变量\">";
	html +="</optgroup>";
	$("#fields").append(html);
	
	$("[title]").tooltips();
}

function addCondition(div){
	var field = $("#fields option:selected");
	var oper = $("#oper option:selected");
	var val = $("#value");
	var content = "{"+field.html()+"} "+oper.html()+" {"+val.val()+"}";
	var model = "{name:'"+field.html()+"',value:'"+val.val()+"',vtype:'"+$("#vtype").val()+"',oper:'"+oper.html()+"'}";
	addCondition0(div,eval("("+model+")"));
}

function addCondition0(div,model){
	var order = getOrder($("#"+div));
	$("#"+div).append($("<div><span>{"+(order+1)+"}</span>'"+model.name+"'&nbsp"+model.oper+"&nbsp;'"+model.value+"'&nbsp;<img onclick='removeConditionItem(this,\""+div+"\")' style='cursor:pointer' src=<%=request.getContextPath() %>/common/images/upload/remove.png /></div>").attr("model",tools.jsonObj2String(model)));
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

//提交
function commit(){
	var url = contextPath+"/flowProcess/updateCondition.action";
	var para = {};
	para["prcsId"] = prcsId;

	//组装请求数据
	para["requestDataModel"] = compositRequestData();

	var json = tools.requestJsonRs(url,para);
// 	top.$.jBox.tip(json.rtMsg,"info");
}

//组装请求数据
function compositRequestData(){
	var arr = new Array();
	for(var i=0;i<nextPrcsList.length;i++){
		var data = nextPrcsList[i];
		var item = {};
		item["prcsTo"]=data.prcsId;
		var conditionArr = new Array();
		var conditionItems = $("#c"+data.prcsId).children();
		for(var j=0;j<conditionItems.length;j++){
			var obj = eval("("+conditionItems[j].getAttribute("model")+")");
			conditionArr.push(obj);
		}
		item["condition"]=conditionArr;
		item["exp"] = $("#ci"+data.prcsId).val();
		item["label"] = $("#label"+data.prcsId).val();
		arr.push(item);
	}
	return tools.jsonArray2String(arr);
}
function change000(obj){
	var value = obj.value;
	if(value=="1"){
		$("#oper").append("<option class=\"s1\" value=\"7\">以字符开头</option><option class=\"s1\" value=\"8\">以字符结尾</option><option class=\"s1\" value=\"9\">包含</option><option class=\"s1\" value=\"10\">不包含</option>");
	}else{
		$(".s1").remove();
	}
}

</script>

</head>
<body onload="doInit()" id="body" style="overflow:hidden;scrollbar:no;overflow-x:hidden;overflow-y:hidden;">
<div style="top:0px;position:absolute;left:0px;right:0px;">
	<div class="panel panel-default">
	  <div class="panel-body" style="padding:5px;">
	    <b>条件设置：</b>
		<select id="fields" class="BigSelect">
		</select>
		<select id="vtype" class="BigSelect" onchange="change000(this)">
			<option value="1">字符串</option>
			<option value="2">数字</option>
			<option value="3">日期时间</option>
		</select>
		<select id="oper" class="BigSelect">
			<option value="1">等于</option>
			<option value="2">不等于</option>
			<option value="3">大于</option>
			<option value="4">大于等于</option>
			<option value="5">小于</option>
			<option value="6">小于等于</option>
			<option class="s1" value="7">以字符开头</option>
			<option class="s1" value="8">以字符结尾</option>
			<option class="s1" value="9">包含</option>
			<option class="s1" value="10">不包含</option>
		</select>
		<input id="value" class="BigInput"/>
	  </div>
	</div>
</div>

<!-- 渲染主体 -->
<div id="renderBody" style="position:absolute;top:50px;left:0px;right:0px;bottom:0px;overflow-y:auto;overflow-x:hidden">

</div>

</body>
</html>