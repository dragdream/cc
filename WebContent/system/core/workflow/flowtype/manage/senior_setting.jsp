<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
	int flowTypeId = TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);

	TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	boolean adminPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_FLOW_TYPE");
	boolean saferPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_SENIOR_MANAGE");
	
	if(!adminPriv&&!saferPriv){
		 response.sendRedirect("/system/core/system/partthree/error.jsp");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/upload.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>高级配置</title>
<script src="<%=contextPath %>/common/js/prototype.js"></script>
<script>
var adminPriv=<%=adminPriv %>;
var saferPriv=<%=saferPriv %>;
var flowTypeId = <%=flowTypeId%>;
function doInit(){
	//获取当前流程是自由流程还是固定流程
	getType();
	
	initManagePriv();
	initFlowTimmerTask();
	initPrintTpl();
	initFlowRunVars();
	initQueryField();
	initNtkoTpl();
	initHtmlTpl();
	//加载文书模板
	initDocTpl();
	
	
	//初始化表单字段
    loadFlowField(flowTypeId);
	//初始化映射关系
	mapping(flowTypeId);
	
	
	if(adminPriv){
		if(!saferPriv){
			$("#collapseTwo").addClass("in");
		}
	}
}


var type=0;
//获取当前流程的类型   判断是自由流程还是固定流程  1=固定流程  2=自由流程
function  getType(){
	var url=contextPath+"/flowType/get.action";
	var json=tools.requestJsonRs(url,{flowTypeId:flowTypeId});
	if(json.rtState){
		type=json.rtData.type;
	}
	
}


/**
 * 初始化管理权限
 */
function initManagePriv(){
	var url = contextPath + '/flowPrivManage/getFlowPrivList.action';
	var json = tools.requestJsonRs(url,{flowTypeId:flowTypeId,page:1,rows:100});
	var list = json.rows;
	var html = "";
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html+="<tr>";
		//渲染权限类型
		html+="<td class='TableData'>";
		if(item.privType == 1){
			html+= "管理";
		}else if(item.privType == 2){
			html+=  "监控";
		}else if(item.privType == 3){
			html+= "查询";
		}else if(item.privType == 4){
			html+= "编辑";
		}else if(item.privType == 5){
			html+= "点评";
		}else if(item.privType == 6){
			html+= "公文分发反馈";
		}else if(item.privType == 7){
			html+= "公文传阅反馈";
		}
		html+="</td>";
		//渲染授权范围
		html+="<td class='TableData'>";
		var privDesc = [];
		if(item.privUsersName && item.privUsersName != ''){
			privDesc.push("<b>用户</b>:" + item.privUsersName);
		}
		if(item.privDeptsName && item.privDeptsName != ''){
			privDesc.push("<b>部门</b>:" + item.privDeptsName);
		}
		if(item.privRolesName && item.privRolesName != ''){
			privDesc.push("<b>角色</b>:" + item.privRolesName);
		}
		html+=privDesc.join("<br/>");
		html+="</td>";
		//渲染管理范围
		html+="<td class='TableData'>";
		privDesc="";
		if(item.privScope == 0){
			privDesc= "所有部门";
		}else if(item.privScope == 1){
			privDesc=  "本部门，包含所有下级部门";
		}else if(item.privScope == 2){
			privDesc= "本部门";
		}else if(item.privScope == 3){//自定义部门
			if(item.deptPostName && item.deptPostName != ''){
				privDesc = privDesc + "<b>部门</b>:" + item.deptPostName;

			}
		}
		html+=privDesc;
		html+="</td>";
		//渲染操作
		html+="<td class='TableData'>";
		html+="<a href='javascript:void(0);' onclick='editManagePriv(\"" +item.sid + "\");'>编辑 </a>";
		html+="&nbsp;";
		html+="<a href='javascript:void(0);' onclick='delManagePriv(\"" +item.sid + "\");'>删除 </a>";
		html+="</td>";
		html+="</tr>";
	}
	$("#managePrivTbody").html(html);
}

function addManagePriv(){
	var url = contextPath+"/system/core/workflow/flowtype/manage/addOrUpdateManagePriv.jsp?flowTypeId=" + flowTypeId + "&sid=" + 0;
	bsWindow(url,"添加管理权限",{width:"800",height:"400",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.doSave()){
			initManagePriv();
			return true;
		}
	}});
}

function editManagePriv(sid){
	var url = contextPath+"/system/core/workflow/flowtype/manage/addOrUpdateManagePriv.jsp?flowTypeId=" + flowTypeId + "&sid=" + sid;
	bsWindow(url,"编辑管理权限",{width:"800",height:"400",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.doSave()){
			initManagePriv();
			return true;
		}
	}});
}

function delManagePriv(sid){
	$.jBox.confirm("确定要删除所选中权限类型？","提示",function(v){
		if(v!="ok") return;
		var url = contextPath
			+ "/flowPrivManage/DelByIds.action?ids="
			+ sid;
		var para = {};
		var jsonRs = tools.requestJsonRs(url, para);
		if (jsonRs.rtState) {
			$.jBox.tip(jsonRs.rtMsg,"info");
			initManagePriv();
		} else {
			$.jBox.tip(jsonRs.rtMsg,"error");
		}
	});
}

function initFlowTimmerTask(){
	var url = contextPath+"/flowTimerTask/getTimerTaskList.action?flowId="+flowTypeId;
	var json = tools.requestJsonRs(url,{flowTypeId:flowTypeId,page:1,rows:100});
	var list = json.rows;
	var html = "";
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html+="<tr>";
		//渲染定时类型
		html+="<td class='TableData'>";
		html+=item.timerType;
		html+="</td>";
		//渲染流程发起人
		html+="<td class='TableData'>";
		html+=item.userNames;
		html+="</td>";
		//渲染发起时间
		html+="<td class='TableData'>";
		html+=item.remindModelDesc;
		html+="</td>";
		//渲染操作
		html+="<td class='TableData'>";
		html+="<a href='javascript:void(0);' onclick=\"editTask("+item.sid+")\">编辑</a>"
		html+="&nbsp;";
		html+="<a href='javascript:void(0);' onclick=\"deleteTask("+item.sid+")\">删除</a>";
		html+="</td>";
		html+="</tr>";
	}
	$("#timmerTaskTbody").html(html);
}

function addTask(){
	var url = contextPath+"/system/core/workflow/flowtype/manage/addOrUpdateTimerTask.jsp?flowTypeId=" + flowTypeId;
	bsWindow(url,"添加定时任务",{width:"800",height:"300",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.doSave()){
			initFlowTimmerTask();
			return true;
		}
	}});
}

function editTask(sid){
	var url = contextPath+"/system/core/workflow/flowtype/manage/addOrUpdateTimerTask.jsp?flowTypeId=" + flowTypeId + "&sid=" + sid;
	bsWindow(url,"编辑定时任务",{width:"800",height:"300",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.doSave()){
			initFlowTimmerTask();
			return true;
		}
	}});
}

function deleteTask(id){
	$.jBox.confirm("确定要删除该定时任务？","提示",function(v){
		if(v!="ok") return;
		var url = "<%=contextPath %>/flowTimerTask/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{'sid':id});
		if(jsonRs.rtState){
			$.jBox.tip("删除成功","success");
			initFlowTimmerTask();
		}else{
			$.jBox.tip(jsonRs.rtMsg,"error");
		}
	});
}

function initPrintTpl(){
	var url = contextPath + "/flowPrintTemplate/selectModulByFlowType.action"  ;
 	var para = {flowTypeId:flowTypeId};
	var rtJson = tools.requestJsonRs(url,para);
	var html="";
    if (rtJson.rtState) {
       var data = rtJson.rtData;
   	   for(var i = 0; i < data.length; i++) {
			var item = data[i];
			html+="<tr>";
			//渲染模版名称
			html+="<td class='TableData'>";
			html+=item.modulName;
			html+="</td>";
			//渲染使用步骤
			html+="<td class='TableData'>";
			html+=item.flowPrcsDescs;
			html+="</td>";
			//渲染操作
			html+="<td class='TableData'>";
			html+="<a href='javascript:void(0);' onclick=\"editTpl("+item.sid+")\">编辑</a>"
			html+="&nbsp;";
			html+="<a href='javascript:void(0);' onclick=\"designTpl("+item.sid+")\">设计</a>"
			html+="&nbsp;";
			html+="<a href='javascript:void(0);' onclick=\"deleteTpl("+item.sid+")\">删除</a>";
			html+="</td>";
			html+="</tr>";
	   } 
   	   
   	   $("#printTplTbody").html(html);
    }else {
       alert(rtJson.rtMsg);
       return false;
   }
}

function addTpl(){
	var url = contextPath+"/system/core/workflow/workmanage/flowprinttpl/index.jsp?flowTypeId=" + flowTypeId;
	bsWindow(url,"添加签批模版",{width:"600",height:"150",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.convertAndSave()){
			initPrintTpl();
			return true;
		}
	}});
}

function editTpl(sid){
	var url = contextPath+"/system/core/workflow/workmanage/flowprinttpl/settingPriv.jsp?flowTypeId=" + flowTypeId+"&sid="+sid+"&type="+type;
	bsWindow(url,"编辑签批模版",{width:"600",height:"150",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.updateModulInfo()){
			initPrintTpl();
			return true;
		}
	}});
}

function designTpl(sid){
// 	top.bsWindow(url,"设计模版",{submit:function(v,h){
// 		var cw = $(h)[0].contentWindow;
// 		if(cw.saveModulDesigner()){
// 			initPrintTpl();
// 			return true;
// 		}
// 	}});
	//openFullDialog(contextPath+"/system/core/workflow/workmanage/flowprinttpl/moduldesigner.jsp?flowTypeId=" + flowTypeId+"&sid="+sid,"设计模版");
	//top.$.jBox.open("iframe:"+contextPath+"/system/core/workflow/workmanage/flowprinttpl/moduldesigner.jsp?flowTypeId=" + flowTypeId+"&sid="+sid,"窗口标题",800,600);
	openFullWindow(contextPath+"/system/core/workflow/workmanage/flowprinttpl/moduldesigner.jsp?flowTypeId=" + flowTypeId+"&sid="+sid, "designTpl");
	
	
}

function deleteTpl(sid){
	if (confirm("确定要删除此打印模版？")) {
		var url = contextPath + "/flowPrintTemplate/delById.action"  ;
	 	var para = {sid : sid};
		var rtJson = tools.requestJsonRs(url,para);
	    if (rtJson.rtState) {
	    	alert("删除成功！");
	    	initPrintTpl();
	    }else{
	    	alert(rtJson.rtMsg);
	    }
	}
}
var model = new Array();
function initFlowRunVars(){
	var url = contextPath+"/flowType/getFlowRunVarsModel.action";
	var json = tools.requestJsonRs(url,{flowId:flowTypeId});
	model = eval("("+json.rtData+")");
	var html = "";
	for(var i=0;i<model.length;i++){
		var item = model[i];
		html+="<tr>";
		html+="<td class='TableData'>";
		html+=item.varKey;
		html+="</td>";
		html+="<td class='TableData'>";
		html+=item.varValue;
		html+="</td>";
		//渲染使用步骤
		html+="<td class='TableData'>";
		if(item.varType==1){
			html+="字符串";
		}else if(item.varType==2){
			html+="数字";
		}else if(item.varType==3){
			html+="日期时间";
		}
		html+="</td>";
		//渲染操作
		html+="<td class='TableData'>";
		html+="<a href='javascript:void(0);' onclick=\"deleteVars("+i+")\">删除</a>";
		html+="</td>";
		html+="</tr>";
	}

	$("#flowRunVarsTbody").html(html);
}

function addVars(){
	if($("#varKey").val()==""){
		alert("变量名称不得为空");
		$("#varKey").focus();
		return;
	}

	var vars = {};
	vars.varKey = $("#varKey").val();
	vars.varValue = $("#varValue").val();
	vars.varType = $("#varType").val();
	model.push(vars);

	var url = contextPath+"/flowType/updateFlowRunVarsModel.action";
	var json = tools.requestJsonRs(url,{flowId:flowTypeId,model:tools.jsonArray2String(model)});
	if(json.rtState){
		initFlowRunVars();
		$("#varKey").val("");
		$("#varValue").val("");
	}else{
		$.jBox.tip(json.rtMsg,"error");
	}
}

function deleteVars(index){
	model.splice(index,1);
	
	var url = contextPath+"/flowType/updateFlowRunVarsModel.action";
	var json = tools.requestJsonRs(url,{flowId:flowTypeId,model:tools.jsonArray2String(model)});
	if(json.rtState){
		initFlowRunVars();
	}else{
		$.jBox.tip(json.rtMsg,"error");
	}
}

/**
 * 加载查询字段
 */
function initQueryField(){
	var url = contextPath+"/";
	
	
	var opts = ["<option>@流水号</option>"];
	opts.push("<option>@工作名称</option>");
	opts.push("<option>@所属流程</option>");
	opts.push("<option>@发起人</option>");
	opts.push("<option>@步骤与流程图</option>");
	opts.push("<option>@时间</option>");
	opts.push("<option>@状态</option>");
	opts.push("<option>@公共附件</option>");
	opts.push("<option>@正文</option>");
	opts.push("<option>@操作</option>");

	//获取当前流程的列表查询模型
	var json = tools.requestJsonRs(contextPath+"/flowType/get.action",{flowTypeId:flowTypeId});
	var queryFieldModel = json.rtData.queryFieldModel;
	if(queryFieldModel=="" || queryFieldModel==undefined || queryFieldModel==null){
		queryFieldModel = "";
	}else{
		queryFieldModel = queryFieldModel.replace("[","").replace("]","");
	}
	
	
	//获取当前流程表单字段
	var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:flowTypeId});
	var list = json.rtData;
	//将表单字段加入到初始opts中
	for(var i=0;i<list.length;i++){
		opts.push("<option>"+list[i].title+"</option>");
	}

	var left = [];
	var right = [];

	var sp = queryFieldModel.split(",");
	for(var i=0;i<sp.length;i++){
		if(sp[i]==""){
			continue;
		}
		left.push("<option>"+sp[i]+"</option>");
	}

	for(var i=0;i<opts.length;i++){
		if(!left.exist(opts[i])){
			right.push(opts[i]);
		}
	}
	
	$("#selLeft").html(left.join(""));
	$("#selRight").html(right.join(""));
	
}

function saveQueryField(){
	var dispFld = [];
	$("#selLeft option").each(function(i,obj){
		dispFld.push(obj.innerHTML);
	});
	
	var url = contextPath+"/seniorQuery/saveQueryClumn.action";
	var json = tools.requestJsonRs(url,{flowId:flowTypeId,dispFld:dispFld.join(",")});
	if(json.rtState){
		$.jBox.tip("保存成功","info");
	}else{

	}
}

function toLeft(){
	$("#selRight option:selected").each(function(i,obj){
		$("#selLeft").append($(obj));
	});
}

function toRight(){
	$("#selLeft option:selected").each(function(i,obj){
		$("#selRight").append($(obj));
	});
}

function toUp(){
	var selected = $("#selLeft option:selected:first");
	if(selected.length!=0){
		var prev = selected.prev();
		if(prev.length!=0){
			selected.insertBefore(prev);
		}
	}
}

function toDown(){
	var selected = $("#selLeft option:selected:first");
	if(selected.length!=0){
		var next = selected.next();
		if(next.length!=0){
			selected.insertAfter(next);
		}
	}
}

function addNtkoTpl(){
	var url = contextPath+"/system/core/workflow/flowtype/manage/addOrUpdateNtkoPrint.jsp?flowId=" + flowTypeId;
	parent.parent.bsWindow(url,"添加Word打印模版",{width:"500",height:"100",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		cw.doSave(function(json){
			if(json.rtState){
				parent.parent.BSWINDOW.modal("hide");
				initNtkoTpl();
			}
		});
	}});
}

//添加Html打印模板
function addOrUpdateHtmlTpl(sid){
	var title="添加Html打印模版";
	if(sid>0){
		title="编辑Html打印模版";
	}
	
	var url = contextPath+"/system/core/workflow/flowtype/manage/addOrUpdateHtmlPrint.jsp?flowId=" + flowTypeId+"&sid="+sid;
	parent.parent.bsWindow(url,title,{width:"500",height:"70",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		var json=cw.doSave();
		if(json.rtState){
			parent.parent.BSWINDOW.modal("hide");
			initHtmlTpl();
		}
		
	}});	
}



//加载html打印模板
function initHtmlTpl(){
	var url = contextPath+"/teeHtmlPrintTemplateController/list.action";
	var json = tools.requestJsonRs(url,{flowTypeId:flowTypeId});
	var list = json.rtData;
	var html = "";
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html+="<tr>";
		html+="<td class='TableData'>";
		html+=item.templateName;
		html+="</td>";
		html+="<td class='TableData'>";
		html+="<a href='javascript:void(0);' onclick=\"addOrUpdateHtmlTpl("+item.sid+")\">编辑</a>&nbsp;&nbsp;";
		html+="<a href='javascript:void(0);' onclick=\"designHtmlTpl("+item.sid+","+item.flowTypeId+")\">设计</a>&nbsp;&nbsp;";
		html+="<a href='javascript:void(0);' onclick=\"delHtmlTpl("+item.sid+")\">删除</a>";
		html+="</td>";
		html+="</tr>";
	}
	$("#htmlPrintTplTbody").html(html);
	
}


//删除Html打印模板
function delHtmlTpl(sid){
	$.jBox.confirm("确定要删除该Html打印模版吗？","提示",function(v){
		if(v!="ok") return;
		var url = "<%=contextPath %>/teeHtmlPrintTemplateController/deleteBySid.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if(jsonRs.rtState){
			$.jBox.tip("删除成功","success");
			initHtmlTpl();
		}else{
			$.jBox.tip(jsonRs.rtMsg,"error");
		}
	});
}

//设计Html模板
function designHtmlTpl(sid,flowTypeId){
	var url="<%=contextPath %>/system/core/workflow/flowtype/manage/designHtmlTpl.jsp?sid="+sid+"&flowTypeId="+flowTypeId;
	openFullWindow(url);
}




function delNtkoTpl(sid){
	$.jBox.confirm("确定要删除该Word打印模版吗？","提示",function(v){
		if(v!="ok") return;
		var url = "<%=contextPath %>/ntkoPrintTemplate/delete.action";
		var jsonRs = tools.requestJsonRs(url,{'sid':sid});
		if(jsonRs.rtState){
			$.jBox.tip("删除成功","success");
			initNtkoTpl();
		}else{
			$.jBox.tip(jsonRs.rtMsg,"error");
		}
	});
}

function initNtkoTpl(){
	var url = contextPath+"/ntkoPrintTemplate/list.action?flowId="+flowTypeId;
	var json = tools.requestJsonRs(url,{flowTypeId:flowTypeId,page:1,rows:100});
	var list = json.rtData;
	var html = "";
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html+="<tr>";
		html+="<td class='TableData'>";
		html+=item.modelName;
		html+="</td>";
		html+="<td class='TableData ntkoTd' fileName='"+item.attach.fileName+"' sid='"+item.attach.sid+"' ext='"+item.attach.ext+"'>";
		html+="</td>";
		html+="<td class='TableData'>";
		html+="<a href='javascript:void(0);' onclick=\"delNtkoTpl("+item.sid+")\">删除</a>";
		html+="</td>";
		html+="</tr>";
	}
	$("#ntkoPrintTplTbody").html(html);
	$(".ntkoTd").each(function(i,obj){
		var attachElement = tools.getAttachElement({fileName:$(obj).attr("fileName"),ext:$(obj).attr("ext"),sid:$(obj).attr("sid"),priv:1+2+8});
		$(obj).append(attachElement);
	});
	
}

//初始化映射关系
function mapping(flowTypeId){
	var url=contextPath+"/flowType/getArchiveMappingById.action";
	var json=tools.requestJsonRs(url,{flowTypeId:flowTypeId});
	if(json.rtState){
		var data=json.rtData;
		$("#mappingDiv").html("");
		var mapping = eval("("+data+")");
		for(var key in mapping){
			$("#mappingDiv").append("<p a='"+key+"' b='"+mapping[key]+"'>"+key+"&nbsp;→&nbsp;"+mapping[key]+"&nbsp;<img onclick='removeParent(this)' style='cursor:pointer' src=<%=request.getContextPath() %>/common/images/upload/remove.png /></p>");
		}
	}
}


//加载表单字段
function loadFlowField(flowTypeId){
	xlist = [];
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json1 = tools.requestJsonRs(url,{flowId:flowTypeId});
	var items = json1.rtData;
	var render = [];
	for(var i=0;i<items.length;i++){
		var data = items[i];
		if(data.xtype=="xlist"){
			xlist.push(data);
		}
		render.push("<option value='"+data.title+"'>"+data.title+"</option>");
	}
	$("#formItems").html(render.join(""));
}

//映射
function doMappingItem(){
	var formItem = $("#formItems").val();
	var archiveItem = $("#archiveItem").val();
	$("#mappingDiv").append("<p a='"+formItem+"' b='"+archiveItem+"'>"+formItem+"&nbsp;→&nbsp;"+archiveItem+"&nbsp;<img onclick='removeParent(this)' style='cursor:pointer' src=<%=request.getContextPath() %>/common/images/upload/remove.png /></p>");
}

//取消映射
function removeParent(obj){
	$(obj).parent().remove();
}


//保存归档映射关系
function saveArchiveMapping(){
	var fieldMapping = {};
	$("#mappingDiv").children().each(function(i,obj){
		fieldMapping[obj.getAttribute("a")] = obj.getAttribute("b");
	});
	fieldMapping = tools.jsonObj2String(fieldMapping);
	
	//alert(fieldMapping);
	tools.requestJsonRs(contextPath+"/flowType/updateArchiveMapping.action",{flowTypeId:flowTypeId,fieldMapping:fieldMapping});
	alert("保存成功");
	window.location.reload();
	
}



//添加文书模板
function addDocTpl(){
	var url = contextPath+"/system/core/workflow/workmanage/flowdoctpl/addOrUpdate.jsp?flowTypeId=" + flowTypeId;
	bsWindow(url,"添加文书模版",{width:"600",height:"150",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.doSave()){
			initDocTpl();
			return true;
		}
	}});
}


//加载文书模板
function initDocTpl(){
	var url = contextPath + "/teeFlowDocTemplateController/getTemplateListByFlowType.action"  ;
 	var para = {flowTypeId:flowTypeId};
	var json = tools.requestJsonRs(url,para);
	var html="";
    if (json.rtState) {
       var data = json.rtData;
   	   for(var i = 0; i < data.length; i++) {
			var item = data[i];
			html+="<tr>";
			//渲染模版id
			html+="<td class='TableData'>";
			html+=item.sid;
			html+="</td>";
			//渲染模版名称
			html+="<td class='TableData'>";
			html+=item.templateName;
			html+="</td>";
			//渲染插件类路径
			html+="<td class='TableData'>";
			html+=item.pluginClassPath;
			html+="</td>";
			//渲染附件
			html+="<td class='TableData'>";
			html+="<div id=\"tpl_"+item.sid+"\"></div>";
			html+="</td>";
			//渲染操作
			html+="<td class='TableData'>";
			html+="<a href='javascript:void(0);' onclick=\"editDocTpl("+item.sid+")\">编辑</a>"
			html+="&nbsp;&nbsp;";
			html+="<a href='javascript:void(0);' onclick=\"deleteDocTpl("+item.sid+")\">删除</a>";
			html+="</td>";
			html+="</tr>";
	   } 
   	   $("#docTplTbody").html(html);
   	   
   	   
   	   //渲染附件对象
   	    for(var j = 0; j < data.length; j++) {
   	    	var attModel=data[j].attachModel;
   	    	attModel["priv"]=3+8;
   	    	var attElement=tools.getAttachElement(attModel,null);
   	    	$("#tpl_"+data[j].sid).append(attElement);
   	    }
    }else {
       alert(json.rtMsg);
       return false;
   }
}


//删除文书模板
function deleteDocTpl(sid){
	$.jBox.confirm("确定要删除该文书模板吗？","提示",function(v){
		if(v!="ok") return;
		var url = "<%=contextPath %>/teeFlowDocTemplateController/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if(jsonRs.rtState){
			$.jBox.tip("删除成功","success");
			initDocTpl();
		}else{
			$.jBox.tip(jsonRs.rtMsg,"error");
		}
	});
}


//编辑文书模板
function editDocTpl(sid){
	var url = contextPath+"/system/core/workflow/workmanage/flowdoctpl/addOrUpdate.jsp?flowTypeId=" + flowTypeId+"&&sid="+sid;
	bsWindow(url,"编辑文书模版",{width:"600",height:"150",submit:function(v,h){
		var cw = $(h)[0].contentWindow;
		if(cw.doSave()){
			initDocTpl();
			return true;
		}
	}});
}
</script>
</head>
<body  onload="doInit()" style="padding:10px">
<div class="panel-group" id="accordion">
<%
   if(saferPriv){
	   %>  
	<div class="panel panel-default">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
	         	 管理权限
	        </a>
	      </h4>
	    </div>
	    <div id="collapseOne" class="panel-collapse collapse in">
	      <div class="panel-body" style="padding:10px;">
	      	<button class="btn btn-primary" onclick="addManagePriv(0)">添加</button>
	      	<table class="TableBlock" style="width:100%;margin-top:10px">
	      		<tr style="font-weight:bold;color:#0177bf">
	      			<td class="TableData" style="width:75px">权限类型</td>
	      			<td class="TableData" >授权范围</td>
	      			<td class="TableData">管理范围</td>
	      			<td class="TableData" style="width:90px">操作</td>
	      		</tr>
	      		<tbody id="managePrivTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	   
	   
	   <% 
   }
%>
  
  
  <%
    if(adminPriv){
    	
    	%> 
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
          	定时任务
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      	<button class="btn btn-primary" onclick="addTask()">添加</button>
      	<table class="TableBlock" style="width:100%;margin-top:10px">
      		<tr style="font-weight:bold;color:#0177bf">
      			<td class="TableData" style="width:75px">定时类型</td>
      			<td class="TableData" >流程发起人</td>
      			<td class="TableData">发起时间</td>
      			<td class="TableData" style="width:90px">操作</td>
      		</tr>
      		<tbody id="timmerTaskTbody">
      			
      		</tbody>
      	</table>
      </div>
    </div>
  </div>
  
  
  
   <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree3">
                     文书模板
        </a>
      </h4>
    </div>
    <div id="collapseThree3" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      	<button class="btn btn-primary" onclick="addDocTpl()">添加</button>
      	<table class="TableBlock" style="width:100%;margin-top:10px">
      		<tr style="font-weight:bold;color:#0177bf">
      		    <td class="TableData" style="width:50px">模版ID</td>
      			<td class="TableData" style="width:200px">模版名称</td>
      			<td class="TableData">插件类路径</td>
      			<td class="TableData" style="width:200px">附件</td>
      			<td class="TableData" style="width:70px">操作</td>
      		</tr>
      		<tbody id="docTplTbody">
      			
      		</tbody>
      	</table>
      </div>
    </div>
  </div>
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
          	AIP签批模版
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      	<button class="btn btn-primary" onclick="addTpl()">添加</button>
      	<table class="TableBlock" style="width:100%;margin-top:10px">
      		<tr style="font-weight:bold;color:#0177bf">
      			<td class="TableData" style="width:200px">模版名称</td>
      			<td class="TableData">使用步骤</td>
      			<td class="TableData" style="width:90px">操作</td>
      		</tr>
      		<tbody id="printTplTbody">
      			
      		</tbody>
      	</table>
      </div>
    </div>
  </div>
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree1">
          	Word打印模版
        </a>
      </h4>
    </div>
    <div id="collapseThree1" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      	<button class="btn btn-primary" onclick="addNtkoTpl()">添加</button>
      	<table class="TableBlock" style="width:100%;margin-top:10px">
      		<tr style="font-weight:bold;color:#0177bf">
      			<td class="TableData" style="width:75px">模版名称</td>
      			<td class="TableData" style="width:75px">模版文件</td>
      			<td class="TableData" style="width:90px">操作</td>
      		</tr>
      		<tbody id="ntkoPrintTplTbody">
      			
      		</tbody>
      	</table>
      </div>
    </div>
  </div>
  
  <!-- html打印模板 -->
   <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree2">
          	Html打印模版
        </a>
      </h4>
    </div>
    <div id="collapseThree2" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      	<button class="btn btn-primary" onclick="addOrUpdateHtmlTpl(0)">添加</button>
      	<table class="TableBlock" style="width:100%;margin-top:10px">
      		<tr style="font-weight:bold;color:#0177bf">
      			<td class="TableData" style="width:75px">模版名称</td>
      			<td class="TableData" style="width:90px">操作</td>
      		</tr>
      		<tbody id="htmlPrintTplTbody">
      			
      		</tbody>
      	</table>
      </div>
    </div>
  </div>
  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
          	流程变量
        </a>
      </h4>
    </div>
    <div id="collapseFour" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px;font-size:12px">
      	变量名称：
      	<input type="text" class="BigInput" style="width:90px" id="varKey"/>
      	初始值：
      	<input type="text" class="BigInput" style="width:90px" id="varValue"/>
      	变量类型：
      	<select class="BigSelect" id="varType">
      		<option value="1">字符串</option>
      		<option value="2">数字</option>
      		<option value="3">日期时间</option>
      	</select>	
      	<button class="btn btn-primary" onclick="addVars()">添加</button>
      	
      	<table class="TableBlock" style="width:100%;margin-top:10px">
      		<tr style="font-weight:bold;color:#0177bf">
      			<td class="TableData" style="width:75px">变量名称</td>
      			<td class="TableData" style="width:75px">初始值</td>
      			<td class="TableData">类型</td>
      			<td class="TableData" style="width:90px">操作</td>
      		</tr>
      		<tbody id="flowRunVarsTbody">
      			
      		</tbody>
      	</table>
      </div>
    </div>
  </div>
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
          	列表显示字段
        </a>
      </h4>
    </div>
    <div id="collapseFive" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      <button class="btn btn-primary" onclick="saveQueryField()">保存</button>
      	<table style="width:400px;font-size:12px">
      		<tr>
      			<td style="text-align:center">
      				<b>显示字段</b><br/>
      				<select multiple id="selLeft" style="height:160px;width:150px" class="BigSelect"></select>
      			</td>
      			<td style="vertical-align:center;text-align:center">
      				<button class="btn btn-default" onclick="toLeft()"><i class="glyphicon glyphicon-chevron-left"></i></button>
      				<br/>
      				<button class="btn btn-default" onclick="toRight()"><i class="glyphicon glyphicon-chevron-right"></i></button>
      				<br/><br/>
      				<button class="btn btn-default" onclick="toUp()"><i class="glyphicon glyphicon-chevron-up"></i></button>
      				<br/>
      				<button class="btn btn-default" onclick="toDown()"><i class="glyphicon glyphicon-chevron-down"></i></button>
      			</td>
      			<td  style="text-align:center">
      				<b>非显示字段</b><br/>
      				<select multiple id="selRight" style="height:160px;width:150px" class="BigSelect"></select>
      			</td>
      		</tr>
      	</table>
      </div>
    </div>
  </div>
  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#archiveMappingDiv">
          	归档映射
        </a>
      </h4>
    </div>
    <div id="archiveMappingDiv" class="panel-collapse collapse">
      <div class="panel-body" style="padding:10px">
      <button class="btn btn-primary" onclick="saveArchiveMapping();">保存</button>
      	   <table class="TableBlock" width="60%" align="center">
		   <tr>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;'>表单字段</td>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;'>操作</td>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;' >归档字段</td>
		   </tr>
		   <tr align="center" >
		    	<td nowrap class="TableData" >
		    		<select id = 'formItems' style='width:180px;' class='form-control' >
		    			
		    		</select> 
		    	</td>
		    	<td nowrap class="TableData" align="center">
		    		<button class="btn btn-warning" onclick="doMappingItem()">映射</button>
		    	</td>
		     	 <td nowrap class="TableData" align="center">
		     	 	<select id = 'archiveItem' style='width:180px' class='form-control' >
		    			<option value="组织机构代码">组织机构代码</option>
		    			<option value="全宗号">全宗号</option>
		    			<option value="件号">件号</option>
		    			<option value="年份">年份</option>
		    			<option value="文件标题">文件标题</option>
		    			<option value="发/来文单位">发/来文单位</option>
		    			<option value="文件编号">文件编号</option>
		    			<option value="密级">密级</option>
		    			<option value="缓急">缓急</option>
		    			<option value="主题词">主题词</option>
		    			<option value="备注">备注</option>
		    		</select> 
		     	 </td>
		   </tr>
		   <tr>
		   	<td colspan="3">
		   		<div id="mappingDiv"></div>
		   	</td>
		   </tr>
	     </table>
      </div>
    </div>
  </div>
    	
    	
    	 <%	
    	
    }
  %>

  
  
  
  
</div>
</body>

</html>