<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;

function doInit(){
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fpInfo = json.rtData;
	bindJsonObj2Cntrl(fpInfo);
	
	ZTreeTool.comboCtrl($("#childFlowId"),{url:contextPath+"/flowProcess/getFlowTree.action",
		callback:function(node){
			renderFlowFields(node.id,"childFields");
		}});
	
	renderFlowFields(flowId,"parentFields");
	renderFlowFields(fpInfo.childFlowId,"childFields");

	var fieldMapping = eval("("+fpInfo.fieldMapping+")");
	for(var key in fieldMapping){
		var left = key;
		var right = fieldMapping[key];
		var item = $("<div value='"+(left+":"+right)+"'>["+(left+"]=&gt;["+right)+"]&nbsp;<img src='"+systemImagePath+"/upload/remove.png' align='absMiddle' style='cursor:pointer;' onclick='remove(this)'/></div>");
		$("#mappingContainer").append(item);
	}

	var fieldReverseMapping = eval("("+fpInfo.fieldReverseMapping+")");
	for(var key in fieldReverseMapping){
		var left = key;
		var right = fieldReverseMapping[key];
		var item = $("<div value='"+(left+":"+right)+"'>["+(left+"]=&gt;["+right)+"]&nbsp;<img src='"+systemImagePath+"/upload/remove.png' align='absMiddle' style='cursor:pointer;' onclick='remove(this)'/></div>");
		$("#reverseMappingContainer").append(item);
	}
}

//数据字段渲染
function renderFlowFields(flowId,ctrlId){
	var ctrl = $("#"+ctrlId);
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	var html = "";
	for(var i=0;i<items.length;i++){
		var item = items[i];
		html+="<option value='"+item.title+"'>"+item.title+"</option>";
	}
	ctrl.html(html);

	$("[title]").tooltips();
}

//提交
function commit(){
	var url = contextPath+"/flowProcess/updateFieldMapping.action";
	var para = tools.formToJson($("#body"));
	para["prcsSeqId"] = prcsId;

	//组合数据
	var fieldMapping = {};
	$("#mappingContainer div").each(function(i,obj){
		var values = $(obj).attr("value").split(":");
		var key = values[0];
		var value = values[1];
		fieldMapping[key] = value;
	});
	para["fieldMapping"] = tools.jsonObj2String(fieldMapping);
	//组合
	var fieldReverseMapping = {};
	$("#reverseMappingContainer div").each(function(i,obj){
		var values = $(obj).attr("value").split(":");
		var key = values[0];
		var value = values[1];
		fieldReverseMapping[key] = value;
	});
	para["fieldReverseMapping"] = tools.jsonObj2String(fieldReverseMapping);
	
	var json = tools.requestJsonRs(url,para);
// 	top.$.jBox.tip(json.rtMsg,"info");
	
}

function mapping(){
	if(parentFields.value=="" || childFields.value==""){
		top.$.jBox.tip("双向都需设置映射字段","error"); 
		return;
	}
	var left = parentFields.value;
	var right = childFields.value;
	var hasExist = false;
	$("#mappingContainer").find("div").each(function(i,obj){
		var value = $(obj).attr("value");
		if(value==(left+":"+right)){
			hasExist = true;
		}
	});

	if(hasExist){
		top.$.jBox.tip("已存在关系映射","info");
		return;
	}

	var item = $("<div value='"+(left+":"+right)+"'>["+(left+"]=&gt;["+right)+"]&nbsp;<img src='"+systemImagePath+"/upload/remove.png' align='absMiddle' style='cursor:pointer;' onclick='remove(this)'/></div>");
	$("#mappingContainer").append(item);
}

function remove(obj){
	$(obj).parent().remove();
}

function reverseMapping(){
	if(parentFields.value=="" || childFields.value==""){
		top.$.jBox.tip("双向都需设置映射字段","error"); 
		return;
	}
	var left = childFields.value;
	var right = parentFields.value;
	var hasExist = false;
	$("#reverseMappingContainer").find("div").each(function(i,obj){
		var value = $(obj).attr("value");
		if(value==(left+":"+right)){
			hasExist = true;
		}
	});

	if(hasExist){
		top.$.jBox.tip("已存在关系映射","info");
		return;
	}

	var item = $("<div value='"+(left+":"+right)+"'>["+(left+"]=&gt;["+right)+"]&nbsp;<img src='"+systemImagePath+"/upload/remove.png' align='absMiddle' style='cursor:pointer;' onclick='remove(this)'/></div>");
	$("#reverseMappingContainer").append(item);
}

</script>

</head>
<body onload="doInit()" id="body">
<table class="TableBlock" width="100%" align="center">
   <tr>
    <td  class="TableData" width="140">子流程定义：<input type="hidden" value="" name="prcsType" /></td>
    <td  class="TableData">
       <input type="hidden" name="childFlowId" id="childFlowId" class="BigInput"/>
    </td>
   </tr>
   <tr>
    <td  class="TableData">是否共享附件：</td>
    <td  class="TableData">
	   	<select id="shareAttaches" name="shareAttaches">
	   		<option value="1">是</option>
	   		<option value="0">否</option>
	   	</select>
    </td>
   </tr>
    <tr>
    <td  class="TableData">是否共享正文：</td>
    <td  class="TableData">
	   	<select id="shareDoc" name="shareDoc">
	   		<option value="1">是</option>
	   		<option value="0">否</option>
	   	</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData">是否允许多实例：</td>
    <td  class="TableData">
    	<select id="multiInst" name="multiInst">
    		<option value="1">是</option>
    		<option value="0">否</option>
    	</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData">是否允许手动选择流程发起人：</td>
    <td  class="TableData">
    	<select id="userLock" name="userLock">
    		<option value="1">是</option>
    		<option value="0">否</option>
    	</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData">是否允许父流程强制转交：</td>
    <td  class="TableData">
    	<select id="prcsWait" name="prcsWait">
    		<option value="1">是</option>
    		<option value="0">否</option>
    	</select>
    </td>
   </tr>
   <tr>
    <td class="TableData" >表单数据字段映射：</td>
    <td class="TableData">
    	父流程字段：
    	<select id="parentFields" class="BigSelect">
    		
    	</select>
    	子流程字段：
    	<select id="childFields" class="BigSelect">
    		
    	</select>
    	<br/><br/>
    	<button type="button" onclick="mapping()" style="color:#3585ca" value="" title="流程发起时，父流程将带入子流程的数据" class="btn btn-one" ><i class="glyphicon glyphicon-arrow-left"></i>正向映射</button>
    	<button type="button" onclick="reverseMapping()" style="color:#3585ca" value="" title="子流程保存数据时，父流程随即更新数据" class="btn btn-one" >反向映射<i class="glyphicon glyphicon-arrow-right"></i></button>
    </td>
   </tr>
   <tr>
    <td class="TableData">父流程=&gt;子流程映射：</td>
    <td class="TableData" id="mappingContainer">
    	
    </td>
   </tr>
   <tr>
    <td class="TableData">子流程=&gt;父流程映射：</td>
    <td class="TableData" id="reverseMappingContainer">
    	
    </td>
   </tr>
</table>

</body>
</html>