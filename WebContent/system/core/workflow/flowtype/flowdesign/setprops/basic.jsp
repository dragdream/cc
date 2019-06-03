<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>
var prcsId = <%=prcsId%>;

function doInit(){
	
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fpInfo = json.rtData;
	
	if(fpInfo.prcsType==1||fpInfo.prcsType==6){
		$("#prcsType1").append("<span name='prcsType' id='prcsType2'></span>");
	}else if(fpInfo.prcsType==3||fpInfo.prcsType==4||fpInfo.prcsType==5||fpInfo.prcsType==7){
		$("#prcsType1").append("<select name='prcsType' id='prcsType'>"
			+"<option value='3'>普通节点</option>"
			+"<option value='7'>循环节点</option>"
			+"<option value='4'>并发节点</option>"
			+"<option value='5'>聚合节点</option>"
		);
	}
	
	bindJsonObj2Cntrl(fpInfo);

	
	
	//填充数据
	if(fpInfo.prcsType==1){
		$("#pType").val(1);
		$("#prcsType2").html("开始节点");
	}  else if(fpInfo.prcsType==3){
		$("#prcsType2").html("普通节点");
	}else if(fpInfo.prcsType==5){
		$("#prcsType2").html("聚合节点");
		$("#forceAggregationTr").show();
	}else if(fpInfo.prcsType==4){
		$("#prcsType2").html("并发节点");
		$("#forceParallelTr").show();
	} else if(fpInfo.prcsType==6){
		$("#pType").val(6);
		$("#prcsType2").html("子流程节点");
		$("#nextPrcsTb").hide();
	}  else if(fpInfo.prcsType==7){
		$("#prcsType2").html("循环节点");
	}
	

	//绘制下一步骤
	var html = "";
	var prcsList = fpInfo.params.prcsList;
	var nextPrcsList = fpInfo.params.nextPrcsList;
	var nextPrcsArray = new Array();
	for(var i=0;i<nextPrcsList.length;i++){
		nextPrcsArray.push(nextPrcsList[i].sid);
	}
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		if(tools.findInSet(data.sid,nextPrcsArray)){
			html+="<input type='checkbox' value='"+data.sid+"' id='prcs_"+data.sid+"' checked/>"+data.prcsName+"<br/>";
		}else{
			html+="<input type='checkbox' value='"+data.sid+"' id='prcs_"+data.sid+"' />"+data.prcsName+"<br/>";
		}
	}
	$("#nextPrcs").html(html);
	
	changeFormSelect();
}

//提交
function commit(){
	
	var pType=$("#pType").val();
	var url = contextPath+"/flowProcess/updateBasicInfo.action";
	var para = tools.formToJson($("#body"));
	
	if(pType!=""&&pType!=null){
		para["prcsType"] = pType;
	}
	
	para["prcsSeqId"] = prcsId;

	var chekcedPrcs = $("#nextPrcs input[type=checkbox]");
	var nextPrcsIds = new Array();
	chekcedPrcs.each(function(i,obj){
		if(obj.checked){
			nextPrcsIds.push(obj.value);
		}
	});
	para["nextPrcs"] = nextPrcsIds+"";
	
	var json = tools.requestJsonRs(url,para); 
// 	top.$.jBox.tip(json.rtMsg,"info");
}



//改变表单选择  执行的js
function changeFormSelect(){
	var formSelect=$("#formSelect").val();
	if(formSelect==1){//全局表单
		$("#opeSpan").hide();
	}else{//独立表单
		$("#opeSpan").show();
	}
}

//自定义表单
function customForm(){
	var url=contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/formDesign.jsp?prcsId="+prcsId;
    openFullWindow(url);
}
</script>

</head>
<body onload="doInit()" id="body">
<table class="TableBlock" width="100%" align="center">
   <tr>
    <td nowrap class="TableData" width="120">序号：</td>
    <td nowrap class="TableData">
       <input type="text" name="sortNo" id="sortNo" style="width:40px;" class="BigInput"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">节点类型：<input type="hidden" value="" name="pType" id="pType"/></td>
    <td nowrap class="TableData" id="prcsType1">
        
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">步骤名称：</td>
    <td nowrap class="TableData">
       <input type="text" name="prcsName" id="prcsName" class="BigInput"/>
    </td>
   </tr>
   <tr id="forceParallelTr" style="display:none">
    <td nowrap class="TableData" width="120">强制并发：</td>
    <td nowrap class="TableData">
       <select id="forceParallel" name="forceParallel">
       	<option value="1">是</option>
       	<option value="0">否</option>
       </select>
    </td>
   </tr>
   <tr id="forceAggregationTr" style="display:none">
    <td nowrap class="TableData" width="120">强制合并：</td>
    <td nowrap class="TableData">
        <select id="forceAggregation" name="forceAggregation">
       	<option value="1">是</option>
       	<option value="0">否</option>
       </select>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">外链页面：</td>
    <td nowrap class="TableData" id="outerPageTd">
         <input type="text" name="outerPage" id="outerPage" class="BigInput" style="width:300px"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">内嵌页面：</td>
    <td nowrap class="TableData" id="innerPageTd">
         <input type="text" name="innerPage" id="innerPage" class="BigInput" style="width:300px"/>
    </td>
   </tr>
    <tr>
	    <td nowrap class="TableData" width="120">表单选择：</td>
	    <td nowrap class="TableData">
	        <select id="formSelect" name="formSelect" class="BigSelect" onchange="changeFormSelect();">
	           <option value="1">全局表单</option>
	           <option value="2">独立表单</option>
	        </select> 
	        &nbsp;&nbsp;&nbsp;
	        <span id="opeSpan"><a href="#" onclick="customForm();">自定义表单</a></span>
	    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">节点说明：</td>
    <td nowrap class="TableData">
       <textarea type="text" name="prcsDesc" id="prcsDesc" class="BigTextarea" style="width:410px;height:120px"></textarea>
    </td>
   </tr>
   <tr id="nextPrcsTb">
    <td nowrap class="TableData" width="120">下一步骤：</td>
    <td class="TableData" id="nextPrcs">
    </td>
   </tr>
</table>

</body>
</html>