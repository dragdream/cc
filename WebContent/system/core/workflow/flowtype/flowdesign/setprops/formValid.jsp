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
	//获取对应表单字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	items = json.rtData;
	var html = "";
	for(var i=0;i<items.length;i++){
		var data = items[i];
		itemIndex[data.itemId]=data.title;
		html+="<option value='"+data.itemId+"'>"+data.title+"</option>";
	}
	$("#fields").append(html);
	$("#fields1").append(html);
	
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fp = json.rtData;
	if(json.rtState){
		var models = eval("("+json.rtData.formValidModel+")");
		for(var i=0;i<models.length;i++){
			addCondition0(models[i]);
		}
	}
}

function addCondition(){
	var model = {};
	var fields = $("#fields");
	var tType = $("#tType");
	var oType = $("#oType");
	var oper = $("#oper");
	var fields1 = $("#fields1");
	var value = $("#value");
	var info = $("#info");
	
	model["itemId"] = fields.val();
	model["tType"] = tType.val();
	model["oType"] = oType.val();
	model["oper"] = oper.val();
	model["info"] = info.val();
	if(tType.val()=="1"){//输入值
		model["val"] = value.val();
	}else{//表单字段
		model["val"] = fields1.val();
	}
	addCondition0(model);
}

function addCondition0(model){
	var fields = $("#fields");
	var tType = $("#tType");
	var oType = $("#oType");
	var oper = $("#oper");
	var fields1 = $("#fields1");
	var value = $("#value");
	var info = $("#info");
	
	var showVal = "";
	if(model["tType"]=="1"){//输入值
		showVal = model["val"];
	}else{//表单字段
		showVal = getItemTitle(model["val"]);
	}
	
	var div = $("<div><span style='color:blue'>["+getItemTitle(model["itemId"])+"]</span>&nbsp;<span style='color:green'>["+tType.find("option[value='"+model["tType"]+"']").html()+"]</span>&nbsp;["+oType.find("option[value='"+model["oType"]+"']").html()+"]&nbsp;<span style='color:red'>["+oper.find("option[value='"+model["oper"]+"']").html()+"]</span>&nbsp;<span style='color:blue'>["+showVal+"]</span>&nbsp;<span style='color:orange'>["+model["info"]+"]</span></div>").data("model",model);
	div.append("<img src='"+systemImagePath+"/upload/remove.png' onclick='removeConditionItem(this)' />");
	$("#renderBody").append(div);
}

function getItemTitle(itemId){
	for(var i=0;i<items.length;i++){
		if((items[i].itemId+"")==(""+itemId)){
			return items[i].title;
		}
	}
	return "";
}

function removeConditionItem(cur){
	$(cur).parent().remove();
}

//提交
function commit(){
	var url = contextPath+"/flowProcess/updateFormValid.action";
	var para = {};
	para["prcsId"] = prcsId;

	//组装请求数据
	para["requestDataModel"] = compositRequestData();
// 	alert(para["requestDataModel"]);
	var json = tools.requestJsonRs(url,para);
// 	top.$.jBox.tip(json.rtMsg,"info");
}

//组装请求数据
function compositRequestData(){
	var arr = new Array();
	var divs = $("#renderBody div");
	for(var i=0;i<divs.length;i++){
		arr.push($(divs[i]).data("model"));
	}
	return tools.jsonArray2String(arr);
}

function change1(val){
	if(val=="1"){
		$("#fields1").hide();
		$("#value").show();
	}else{
		$("#fields1").show();
		$("#value").hide();
	}
}
</script>

</head>
<body onload="doInit()" id="body" style="overflow:hidden;scrollbar:no;overflow-x:hidden;overflow-y:hidden;">
<div style="">
	<div class="panel panel-default">
	  <div class="panel-body" style="padding:5px;font-size:12px">
	    <b>条件设置：</b>
	    <br/>
		<select id="fields" class="BigSelect">
		</select>
		<select id="tType" class="BigSelect" onchange="change1(this.value)">
			<option value="1">输入值</option>
			<option value="2">表单字段</option>
		</select>
		<select id="oType" class="BigSelect">
			<option value="1">字符串</option>
			<option value="2">数字</option>
			<option value="3">日期</option>
		</select>
		<br/>
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
		<select id="fields1" class="BigSelect" style="display:none">
		</select>
		<input id="value" class="BigInput" />
		<br/>
		提示信息：<input id="info" class="BigInput" />
		<button class="btn btn-one" onclick="addCondition()">添加</button>
	  </div>
	</div>
</div>

<!-- 渲染主体 -->
<div id="renderBody" style="font-size:12px">

</div>

</body>
</html>