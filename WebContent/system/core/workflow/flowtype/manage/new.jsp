<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@page import="com.tianee.oa.core.org.service.TeePersonService" %>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
	int sortId = TeeStringUtil.getInteger(request.getParameter("sortId"),0);
	if(sortId==-1){
		sortId = 0;
	}
	
	//获取当前登录的用户名
	TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	String userId=loginUser.getUserId();
	//判断当前的用户是不是系统管理员
	boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, userId);
	
	
	boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_FLOW_TYPE");
	if(!hasPriv){//没有权限
		response.sendRedirect("/system/core/system/partthree/error.jsp");
	 }
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>新增流程</title>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowTypeService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowSortService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFormSortService.js"></script>
	<script>

	var contextPath = "<%=contextPath%>";
	var sortId = <%=sortId%>;
	var isAdmin=<%=isAdmin%>;
	function doInit(){
		initExtraData();
		$("#type")[0].value="1";
		changed0($("#type")[0]);
		$("[title]").tooltips();
		
		 //判断当前登录的用户是不是管理员
	    if(!isAdmin){
	    	$("#deptName").hide();
	    	$("#deptId").hide();
	    	$("#selDept").hide();
	    	$("#clearDept").hide();
	    	$("#deptTd").hide();
	    }
		
	}

	//初始化控件数据信息
	function initExtraData(){

		ZTreeTool.comboCtrl($("#formId"),{url:contextPath+"/formSort/getSelectCtrlDataSource.action"});
		

		//初始化流程分类控件
		var flowSortService = new TeeFlowSortService();
		var flowSortList = flowSortService.getList();
		if(flowSortList){
			var html = "<option value='0'>默认分类</option>";
			for(var i=0;i<flowSortList.length;i++){
				html+="<option value='"+flowSortList[i].sid+"'>"+flowSortList[i].sortName+"</option>";
			}
			$("#flowSortId").html(html);
		}

		$("#flowSortId")[0].value = sortId;
	}


	function checkForm(){
		if(!$("#form").form('validate')){
			return false;
		}
		if($("#formId").val()=="0" || $("#formId").val()==""){
			alert("请选择流程表单");
			return false;
		}

		var autoArchive=$("#autoArchive").val();
		if(autoArchive=="1"||autoArchive==1){//开启
			//获取存档信息
			 var content=$(".archiveItems");
			 var autoArchiveValue=0;
			 for(var i=0;i<content.length;i++){
				if($(content[i]).prop("checked")==true){
					autoArchiveValue=autoArchiveValue+parseInt($(content[i]).val());
				}
			}
			if(autoArchiveValue==0){
				alert("请至少选择一项存档信息！");
				return false;
			}
		}	
		return true;
	}
	
	function commit(){
		if(!checkForm()){
			return;
		}
		//获取存档信息
		 var content=$(".archiveItems");
		 var autoArchiveValue=0;
		 for(var i=0;i<content.length;i++){
			if($(content[i]).prop("checked")==true){
				autoArchiveValue=autoArchiveValue+parseInt($(content[i]).val());
			}
		}
		
		//这里是提交表单的方法
		var para = tools.formToJson($("#form"));
		para["autoArchiveValue"]=autoArchiveValue;
		var flowTypeService = new TeeFlowTypeService();
		if(flowTypeService.save(para)){
			alert("保存成功");
			parent.document.getElementById("left").contentWindow.location.reload();
			window.location.reload();
		}
	}

	function getLatestVersionFormId(formId){
		var url = contextPath+"/flowForm/getLatestVersionFormId.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			return json.rtData;
		}
		return 0;
	}
	
	function formPrintExplore(){
		var formId = $("#formId").val();
		formId = getLatestVersionFormId(formId);
// 		window.open("../../formdesign/printExplore.jsp?formId="+formId);
		openFullWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId,"表单预览");
	}
	
	function changed0(obj){
		if(obj.value=="1"){
			$("#prePrcsDiv").hide();
		}else{
			$("#prePrcsDiv").show();
		}
	}
	
	
	
	//改变是否在流程结束的时候自动归档
	function isAutoArchive(){
		var autoArchive=$("#autoArchive").val();
		if(autoArchive=="1"||autoArchive==1){//开启
			$("#archiveItem").show();
		}else{
			$("#archiveItem").hide();
		}
		
	}
	</script>
	
</head>
<body  onload="doInit()" style="margin:0px;padding:0px;overflow:hidden">
<div class="base_layout_top">
	&nbsp;&nbsp;<input type="button" class="btn btn-success" value="保存" onclick="commit()"/>
</div>
<div class="base_layout_center">
<br/>
<form id="form">
		<center>
		<table class="TableBlock" style="width:90%;font-size:12px;background:white;text-align:left;">
			<tr>
				<td nowrap class="TableData TableBG" style="width:170px">流程名称<font color="red">*</font>：</td>
				<td class="TableData"style="">
					<input type="text" name="flowName" class="BigInput easyui-validatebox" size="40" maxlength="100" required="true" />
				</td>
				<td nowrap class="TableData TableBG" style="width:170px">排序号：</td>
				<td class="TableData"style="">
					<input title="控制同一分类下流程的排序" class="BigInput easyui-validatebox" type="text" name="orderNo" id="orderNo" validType ='number[]' size="4" maxlength="100" value="1" />
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG">流程类型：</td>
				<td class="TableData">
					<select name="type"  id="type" class="BigSelect" onchange="changed0(this)">
				      <option value="1" >固定流程</option>
				      <option value="2" >自由流程</option>
				    </select>
				    <span id="prePrcsDiv">
					    允许步骤预设：
						<select name="freePreset" class="BigSelect">
					     <option value="1">是</option>
					     <option value="0" selected>否</option>
					    </select>
				    </span>
				</td>
				<td nowrap class="TableData TableBG">委托设置：
				</td>
				<td class="TableData">
					<select name="delegate" id="delegate" class="BigSelect">
					        <option value="2" >允许委托</option>
					        <option value="0" >禁止委托</option>
<!-- 					        <option value="3" >按步骤办理权限委托</option> -->
<!-- 					        <option value="1" >委托当前步骤经办人</option> -->
					    </select>
<%-- 					    <i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="[委托类型说明：]<br> --%>
<!-- 					    <b>1.自由委托：</b>用户可以在工作委托模块中设置委托规则,可以为委托给任何人。<br> -->
<!-- 					    <b>2.按步骤设置的经办权限委托：</b>仅能委托给流程步骤设置中经办权限范围内的人员<br> -->
<!-- 					    <b>3.按实际经办人委托：</b>仅能委托给步骤实际经办人员。<br> -->
<!-- 					    <b>4.禁止委托：</b>办理过程中不能使用委托功能。<br> -->
<%-- 					    <b>注意：</b>只有自由委托才允许定义委托规则，委托后更新自己步骤为办理完毕，主办人变为经办人"></i> --%>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG"style="">流程分类：</td>
				<td class="TableData"style="">
					<select name="flowSortId" id="flowSortId" class="BigSelect" ></select>
				</td>
				<td nowrap class="TableData TableBG"style="">流程附件：</td>
				<td class="TableData"style="">
					<select name="attachPriv"  id="attachPriv" class="BigSelect">
				      <option value="1" >开启</option>
				      <option value="0" >关闭</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG">流程表单：</td>
				<td class="TableData">
					<input type="text" id="formId" name="formId" class="BigInput"/><a href="javascript:void(0);" onclick="formPrintExplore()">预览表单</a>
				</td>
				<td nowrap class="TableData TableBG">
				传阅设置&nbsp;<i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="
				      			开启并设置后，当流程结束时，将该流程信息传阅给预设的指定人员。
				      		"></i>：
				</td>
				<td class="TableData">
				是否传阅:<input type="checkbox" name="viewPriv" value='1'>
			    <br>
				<textarea  class="BigTextarea" name="viewPersonNames" id="viewPersonNames" rows="3" cols="25" class="SmallStatic" wrap="yes" readonly></textarea>
				<input type="hidden" name="viewPersonIds" id="viewPersonIds" />
				<br>
				  <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['viewPersonIds','viewPersonNames'])">选择</a>
				  <a href="javascript:void(0);" class="orgClear" onClick="clearData('viewPersonIds','viewPersonNames')">清空</a>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">文档类型：</td>
				<td class="TableData" style="">
					<select class="BigSelect" id="hasDoc" name="hasDoc">
						<option value="0">表单</option>
						<option value="1">表单+正文</option>
						<option value="2">表单+版式正文</option>
						<option value="3">表单+正文+版式正文</option>
					</select>	
				</td>
				<td nowrap class="TableData TableBG" style="">会签：</td>
				<td class="TableData" style="">
				    <select name="feedbackPriv"  id="feedbackPriv" class="BigSelect">
				      <option value="1" >开启</option>
				      <option value="0" >关闭</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG">工作名称设置：</td>
				<td class="TableData">
					表&nbsp;&nbsp;达&nbsp;&nbsp;式：
				      		<input class="BigInput" type="text" name="fileCodeExpression" id="fileCodeExpression" />
				      		<i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="
				      			<b>{年}</b>：当前年份<br/>
				      			<b>{月}</b>：当前月份<br/>
				      			<b>{日}</b>：当前日期<br/>
				      			<b>{时}</b>：当前小时<br/>
				      			<b>{分}</b>：当前分钟<br/>
				      			<b>{秒}</b>：当前秒数<br/>
				      			<b>{流程名称}</b>：该流程定义的名称<br/>
				      			<b>{用户姓名}</b>：当前发起人的用户姓名<br/>
				      			<b>{部门}</b>：当前发起人的所在部门<br/>
				      			<b>{角色}</b>：当前发起人的所属角色<br/>
				      			<b>{流水号}</b>：当前流程实例的流水号<br/>
				      			<b>{编号}</b>：当前流程实例的编号<br/>
				      		"></i>
				      		<br/>
				      		当前编号：
				      		<input class="BigInput readonly" type="text" name="numbering" id="numbering" readonly value="1" />
				      		<br/>
				      		编号位数：
				      		<input class="BigInput" type="text" name="numberingLength" id="numberingLength" value="0" />
				</td>
				<td nowrap class="TableData TableBG" style="">流程描述：</td>
				<td class="TableData" style="">
					<textarea name="comment" id="comment" style="width:300px;height:100px" class="BigTextarea"></textarea>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">工作名称权限：</td>
				<td class="TableData" style="">
					新建时
					<select class="BigSelect" id="runNamePriv" name="runNamePriv">
						<option value="0">不允许修改</option>
						<option value="1">允许修改</option>
						<option value="2">仅允许输入前缀</option>
						<option value="3">仅允许输入后缀</option>
						<option value="4">仅允许输入前缀和后缀</option>
					</select>
				</td>
				<td nowrap class="TableData TableBG" style="" ><font id="deptTd">所属部门：</font></td>
				<td class="TableData" style="">
				   <input type="hidden" name="deptId" id="deptId"/>
		           <input  name="deptName" id="deptName" class="readonly BigInput" readonly />
		           <a href="javascript:void(0)" id="selDept" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
		           &nbsp;
		           <a href="javascript:void(0)" id="clearDept" onclick="clearData('deptId','deptName')">清除</a>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">流程结束自动归档：</td>
				<td class="TableData" style="" colspan="3">
				    <select class="BigSelect"  name="autoArchive" id="autoArchive"  onchange="isAutoArchive();">
				        <option value="0">关闭</option>
				        <option value="1">开启</option>
				    </select>
				    <span id="archiveItem" style="margin-left: 20px;display: none;">
				                        请选择存档信息：<input type="checkbox" class="archiveItems"  value="1" />表单&nbsp;
				        <input type="checkbox" class="archiveItems" value="2" />签批单&nbsp;
				        <input type="checkbox" class="archiveItems" value="4" />正文&nbsp;
				        <input type="checkbox" class="archiveItems" value="8" />版式正文&nbsp;
				        <input type="checkbox" class="archiveItems" value="16" />公共附件
				    </span>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData TableBG" style="">外部页面：</td>
				<td class="TableData" style="" colspan="3">
					<input type="text" name="outerPage" class="BigInput" style="width:70%" id="outerPage" />
				</td>
			</tr>
		</table>
		</center>
		</form>

</div>
</body>

</html>