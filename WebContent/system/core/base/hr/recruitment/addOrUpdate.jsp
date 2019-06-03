 <%@page import="com.tianee.oa.core.base.hr.TeeHrCodeManager"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/ztree.jsp" %>

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<title>新建/编辑招聘录用</title>
<script type="text/javascript">
var sid = "<%=sid%>";
var deptName;
function doInit(){
	getDeptParent();
	getHrCode();
	if(sid > 0){
		getInfoById(sid);
	}
}

/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false,
				onAsyncSuccess:setDeptParentSelct
			};
		zTreeObj = ZTreeTool.config(config);
		//setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}


/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/recruitmentController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			if(prc.deptId){
				deptName = prc.deptName;
				setTimeout('setDeptName()',500);
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
function setDeptName(){
	$("#deptIdName").val(deptName);
}

function checkForm(){
	var check = $("#form1").valid(); 
	if(!check){
		return false; 
	}

	if($("#planId").val() == ''){
		$.MsgBox.Alert_auto("招聘计划不能为空！");
		return false;
	}
	
	return true;
}


/**
 * 获取所有代码
 */
function getHrCode(){
	//员工类型 
	getHrCodeByParentCodeNo("STAFF_OCCUPATION" , "employeeType");
	//职称
	getHrCodeByParentCodeNo("PRESENT_POSITION" , "presentPosition");
}





function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/recruitmentController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	   	if(jsonRs.rtState){
		   toReturn();
		   $.MsgBox.Alert_auto("保存成功!");
	   }else{
		   $.MsgBox.Alert_auto(jsonRs.rtMsg);
	   }
	}
}





function toReturn(){
	//window.location.href = contextPath + "/system/core/base/hr/recruitment/recruitmentManage.jsp";
    history.go(-1);
}

</script>
</head>
<body onload="doInit()" >

<form action=""  method="post" name="form1" id="form1">

<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock_page" >
	
	  <tr>
	     <td>
	          <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
			   <b style="color: #0050aa">
			   <%
				if(sid > 0){	
				%>	
				编辑
				<%
					}else{
				%>
				新建
				<%
				}
				%>
			   
			   </b>
	     </td>
    </tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px" >计划名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="hidden" name="planId" id="planId">
			<input type="text" name="planName" id="planName" size="" class="BigInput" required="true" size="15" readonly="readonly">
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="getRecruitPlan()" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#planId').val('');$('#planName').val('')" value="清空"/>
			</span>
		
		</td>
		<td nowrap class="TableData"  width="15%;" >应聘者姓名：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="hidden" name="hrPoolId" value="" id="hrPoolId">
			<input type="text" name="hrPoolName" id="hrPoolName" class="BigInput" size="15" required="true" value="" readonly="readonly">
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="getHrPool()" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#hrPoolId').val('');$('#hrPoolName').val('')" value="清空"/>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px" >招聘岗位：</td>
		<td class="TableData" width="35%;" >
			<input type="text" name="position" id="position" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
		<td nowrap class="TableData"  width="15%;" >OA中用户名 ：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="oaName" id="oaName" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent: 15px">录用负责人：</td>
		<td class="TableData" width="" >
			<input type=hidden name="recordPersonId" id="recordPersonId" value="">
			<input type="text" name="recordPersonName" id="recordPersonName" class="BigInput" size="15"  readonly value=""></input>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectSingleUser(['recordPersonId', 'recordPersonName']);" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#recordPersonId').val('');$('#recordPersonName').val('');" value="清空"/>
			</span>
		</td>
		<td nowrap class="TableData"  width="">录入日期：</td>
		<td class="TableData" >
			<input type="text" name="recordTimeStr" id="recordTimeStr" size="" class="Wdate BigInput" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'recordTimeStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width=""  style="text-indent: 15px">招聘部门：</td>
		<td class="TableData"  colspan="3">
			<!-- <input id="deptId" name="deptId"  type="text" style="display:none;"/>
			<ul id="deptIdZTree" class="ztree" style="margin-top:0; width:247px; display:none;"></ul> -->
			<input name="deptId" id="deptId" type="hidden"/>
			<input type="text" name="deptName" id="deptName" readonly="readonly"/>
			<span class="addSpan">
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptId','deptName')" value="清空"/>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">员工类型：</td>
		<td class="TableData" width="35%;" >
		<select name="employeeType" id="employeeType"   class="BigSelect"  title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
			<option value="">请选择</option>
		</select
		</td>
		<td nowrap class="TableData"  width="15%;" >行政等级 ：</td>
		<td class="TableData" width="60%;" >
			<input type="text" name="administrationLevel" id="administrationLevel" size="" class="BigInput"  value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">职务：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="jobPosition" id="jobPosition" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
		<td nowrap class="TableData"  width="15%;" >职称：</td>
		<td class="TableData" width="60%;" >
		<select name="presentPosition" id="presentPosition"   class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
			<option value="">请选择</option>
		</select
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">正式入职时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="onBoardingTimeStr" id="onBoardingTimeStr" size="" class="Wdate BigInput" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'onBoardingTimeStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >正式起薪时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="startingSalaryTimeStr" id="startingSalaryTimeStr" size="" class="Wdate BigInput" onClick="WdatePicker({startingSalaryTime:'#F{$dp.$D(\'startingSalaryTimeStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent: 15px">备　　注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="60" id="remark" name="remark" maxlength="200" class="BigTextarea"></textarea>
		</td>
	</tr>
	<tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
		    <div align="right">
		       <input type="button"  value="保存" class="btn-win-white" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
			   <input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>
		    </div>
		</td>
	</tr>
</table>
</form>
<br>
<script>
  $("#form1").validate();
</script>
</body>
</html>
 