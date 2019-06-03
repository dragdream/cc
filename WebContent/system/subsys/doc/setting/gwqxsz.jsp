<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/ztree.jsp" %>
<style>
</style>
<script>
var list;
var ctrlTree;
var privUuid;
function doInit(){
	//初始化的时候隐藏取消编辑的按钮
	$("#cancelEdit").hide();
	
	messageMsg("该页面用来定义公文流程的使用与发起权限，并设置公文字段映射","msg","info");
	
	ctrlTree = ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getFlowType2SelectCtrl.action"});
	
	tools.requestJsonRs(contextPath+"/docFlowPriv/list.action",{},true,function(json){
		var render = [];
		list = json.rtData;
		for(var i=0;i<list.length;i++){
			render.push("<tr>");
			render.push("<td class='TableData' >"+list[i].flowName+"</td>");
			render.push("<td class='TableData'>"+list[i].privDeptNames+"</td>");
			render.push("<td class='TableData'>"+list[i].privRoleNames+"</td>");
			render.push("<td class='TableData'>"+list[i].privUserNames+"</td>");
			render.push("<td class='TableData'><a href='javascript:void(0);' onclick=\"mapping("+i+")\">字段映射</a>&nbsp;&nbsp;");
			render.push("<a href='javascript:void(0);' onclick=\"edit("+i+")\">编辑</a>&nbsp;&nbsp;");
			render.push("<a href='javascript:void(0);' onclick=\"del("+i+")\">删除</a></td>");
			render.push("</tr>");
		}
		$("#tBody").append(render.join(""));
	});
	
}

function commit(){
	
/* 	$("body").animate({scrollTop:$("#editTable").offset().top});
	return; */
	if($("#flowId").val()=="0" || $("#flowId").val()==""){
		alert("请选择具体一种公文流程");
		return;
	}
	if($("#privDeptIds").val()==""&&$("#privRoleIds").val()==""&&$("#privUserIds").val()==""){
		alert("部门，角色，人员不允许全部为空，请选择！");
		return;
	}
	
	var privRoleIds=$("#privRoleIds").val();
	var privUserIds=$("#privUserIds").val();
	
	var json = tools.requestJsonRs(contextPath+"/docFlowPriv/addOuUpdate.action",{flowId:$("#flowId").val(),privDeptIds:$("#privDeptIds").val(),privUserIds:privUserIds,privRoleIds:privRoleIds,privUuid:privUuid});
	if(json.rtState){
		alert("保存成功");
		window.location.reload();
	}else{
		alert(json.rtMsg);
	}
	
}

function del(index){
	if(window.confirm("是否要删除该项？")){
		var uuid = list[index].uuid;
		var json = tools.requestJsonRs(contextPath+"/docFlowPriv/delete.action",{uuid:uuid});
		if(json.rtState){
			window.location.reload();
		}else{
			alert(json.rtMsg);
		}
	}
}

//编辑
function edit(index){
	//显示取消编辑按钮
	$("#cancelEdit").show();
	
	var uuid=list[index].uuid;
	var json=tools.requestJsonRs(contextPath+"/docFlowPriv/get.action",{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		//赋值
		ctrlTree.setVal(data.flowId);
		privUuid=uuid;
		$("#privDeptIds").val(data.privDeptIds);
		$("#deptNames").val(data.privDeptNames);
		$("#privRoleIds").val(data.privRoleIds);
		$("#roleNames").val(data.privRoleNames);
		$("#privUserIds").val(data.privUserIds);
		$("#userNames").val(data.privUserNames);
		//滚动
		$(window).scrollTop($("#editTable").offset().top);
	}else{
		alert(json.rtMsg);
	}
	
}
//取消编辑
function cancelEdit(){
	ctrlTree.setVal(0);
	$("#flowId").val("");
	privUuid="";
	$("#privDeptIds").val("");
	$("#deptNames").val("");
	$("#privRoleIds").val("");
	$("#roleNames").val("");
	$("#privUserIds").val("");
	$("#userNames").val("");
	
	//隐藏取消编辑的按钮
	$("#cancelEdit").hide();
	
}
function mapping(index){
	var data = list[index];
	$("#mappingDiv").html("");
	$("#uuid").val(data.uuid);
	loadFlowField(data.flowId);
	var mapping = eval("("+data.fieldMapping+")");
	for(var key in mapping){
		$("#mappingDiv").append("<p a='"+key+"' b='"+mapping[key]+"'>"+key+"&nbsp;→&nbsp;"+mapping[key]+"&nbsp;<img onclick='removeParent(this)' style='cursor:pointer' src=<%=request.getContextPath() %>/common/images/upload/remove.png /></p>");
	}
	
	$("#showDiv").modal("show");
}

function loadFlowField(flowId){
	xlist = [];
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json1 = tools.requestJsonRs(url,{flowId:flowId});
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

function doMappingItem(){
	var formItem = $("#formItems").val();
	var docItem = $("#docItems").val();
	$("#mappingDiv").append("<p a='"+formItem+"' b='"+docItem+"'>"+formItem+"&nbsp;→&nbsp;"+docItem+"&nbsp;<img onclick='removeParent(this)' style='cursor:pointer' src=<%=request.getContextPath() %>/common/images/upload/remove.png /></p>");
}

function removeParent(obj){
	$(obj).parent().remove();
}

function saveMapping(){
	var fieldMapping = {};
	$("#mappingDiv").children().each(function(i,obj){
		fieldMapping[obj.getAttribute("a")] = obj.getAttribute("b");
	});
	fieldMapping = tools.jsonObj2String(fieldMapping);
	tools.requestJsonRs(contextPath+"/docFlowPriv/updateMapping.action",{uuid:$("#uuid").val(),fieldMapping:fieldMapping});
	alert("保存成功");
	window.location.reload();
}


</script>
</head>
<body onload="doInit()" style="margin:5px;">
<div class="moduleHeader">
	<b>公文权限设置</b>
</div>
<center id="msg">
</center>
<center>
	<table id="editTable" style="width:700px;font-size:12px" class="TableBlock">
		<tr>
			<td class="TableData" style="background:#f0f0f0">选择流程</td>
			<td class="TableData">
				<input name="flowId" id="flowId" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="background:#f0f0f0">选择部门</td>
			<td class="TableData">
				<input type="hidden" name="privDeptIds" id="privDeptIds"/>
				<textarea  name="deptNames" id="deptNames" class="readonly BigTextarea" readonly style="width:500px;height:80px"></textarea>
				<a href="javascript:void(0)" onclick="selectDept(['privDeptIds','deptNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('privDeptIds','deptNames')">清除</a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="background:#f0f0f0">选择角色</td>
			<td class="TableData">
				<input type="hidden" name="privRoleIds" id="privRoleIds"/>
				<textarea  name="roleNames" id="roleNames" class="readonly BigTextarea" readonly style="width:500px;height:80px"></textarea>
				<a href="javascript:void(0)" onclick="selectRole(['privRoleIds','roleNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('privRoleIds','roleNames')">清除</a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="background:#f0f0f0">选择人员</td>
			<td class="TableData">
				<input type="hidden" name="privUserIds" id="privUserIds"/>
				<textarea  name="userNames" id="userNames" class="readonly BigTextarea" readonly style="width:500px;height:80px"></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['privUserIds','userNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('privUserIds','userNames')">清除</a>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2">
				<center>
					<button class="btn btn-default" onclick="commit()">保存</button>
					<button class="btn btn-default" id="cancelEdit" onclick="cancelEdit();">取消编辑</button>
				</center>
			</td>
		</tr>
	</table>
	
	<hr/>
	
	<table class="TableBlock" style="width:1150px;font-size:12px">
		<thead>
			<tr>
				<td class="TableData" style="background:#f0f0f0;width:100px"><b>流程</b></td>
				<td class="TableData" style="background:#f0f0f0;width:400px" ><b>部门权限</b></td>
				<td class="TableData" style="background:#f0f0f0;width:400px"><b>角色权限</b></td>
				<td class="TableData" style="background:#f0f0f0;width:400px"><b>人员权限</b></td>
				<td class="TableData" style="background:#f0f0f0;width:150px"><b>操作</b></td>
			</tr>
		</thead>
		<tbody id="tBody">
			
		</tbody>
	</table>
	
</center>

<div class="modal fade" id="showDiv" tabindex="200" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:1000000">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">字段映射</h4>
      </div>
      <div class="modal-body">
      	<table class="TableBlock" width="60%" align="center">
		   <tr>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;'>表单字段</td>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;'>操作</td>
		   		 <td nowrap class="TableHeader" colspan="" style='vertical-align: middle;' >公文字段</td>
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
		     	 	<select id = 'docItems' style='width:180px' class='form-control' >
		    			<option value="公文标题">公文标题</option>
		    			<option value="字号">字号</option>
		    			<option value="编号">编号</option>
		    			<option value="秘密等级">秘密等级</option>
		    			<option value="紧急程度">紧急程度</option>
		    			<option value="发文单位">发文单位</option>
		    			<option value="抄送">抄送</option>
		    			<option value="主送">主送</option>
		    			<option value="共印份数">共印份数</option>
		    			<option value="附件">附件</option>
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
      	<input type="hidden" id="uuid" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="saveMapping()">确定</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>