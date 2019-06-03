<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");
	
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建或编辑申请预算</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/budget/regBudget/js/regBudget.js"></script>
<script type="text/javascript">
var uuid = "<%=uuid%>";
var isAdmin = <%=isAdmin%>;
var budgetCost = 0;
function doInit(){
	getPersonDeptList('<%=loginPerson.getUuid()%>','opDeptId');
	getSysCodeByParentCodeNo("BUDGET_REG_REASON" , "reason");
	showBudgetCost("1");
	if(uuid){
		getInfoById(uuid);
	}
}




/**
 * 查看详情 
 */
function getInfoById(uuid){
	var url = "<%=contextPath%>/budgetRegController/getInfoById.action";
	var para = {uuid : uuid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			regTypeFunc(prc.regType);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}

function checkForm(){
	var regType1 = $("input[name='regType']:checked").val();
	if(regType1 =='1'){
		var opUserId =$("#opUserId").val();
		if(!opUserId){
			$.jBox.tip("请选择人员！",'info',{timeout:1500});
			return false;
		}
		budgetCost = getUserBudgetCost(opUserId);
	}else{
		var opDeptId =$("#opDeptId").val();
		if(!opDeptId){
			$.jBox.tip("请选择部门！",'info',{timeout:1500});
			return false;
		}
		budgetCost = getDeptBudgetCost(opDeptId);
	}
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	var amount = $("#amount").val();
	if(!amount){
		amount = 0;
	}
	if(amount ==0){
		$.jBox.tip("请您输入不为0的金额数！",'info',{timeout:1500});
		$("#amount").focus();
		return false;
	}
	
	var allBudgetCost = parseFloat(budgetCost) - parseFloat(amount);
	if(allBudgetCost<0){
		$.jBox.tip("您的剩余预算金额为"+budgetCost+"元！",'info',{timeout:1500});
		$("#amount").focus();
		return false;
	}
	return true;
}
//申请类型触发事件
function showBudgetCost(str){
	if(str =='1'){//个人
		var userId =0;
		if(isAdmin){
			userId = $("#opUserId").val();
		}else{
			userId = <%=loginPerson.getUuid()%>;
		}
		budgetCost = getUserBudgetCost(userId);
	}else {//部门
		if(isAdmin){
			var userId = $("#opUserId").val();
			getPersonDeptList(userId,'opDeptId');
		}
		
		var deptId = $("#opDeptId").val();
		budgetCost = getDeptBudgetCost(deptId);
	}
	//$("#budgetCostSpan").html("<font color='red'>可申请金额：" + budgetCost + "元</font>");
}
//部门选择触发事件
function setBudgetCostFunc(){
	var deptId = $("#opDeptId").val();
	budgetCost = getDeptBudgetCost(deptId);
	$("#budgetCostSpan").html("<font color='red'>可申请金额：" + budgetCost + "元</font>");
}
//人员选择触发事件
function setBudgetUserCostFunc(){
	var userId = $("#opUserId").val();
	budgetCost = getUserBudgetCost(userId);
	$("#budgetCostSpan").html("<font color='red'>可申请金额：" + budgetCost + "元</font>");
}




/**
 * 提交
 */
function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/budgetRegController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		para["uuid"] = "<%=uuid%>";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			$.jBox.tip("保存成功!",'info',{timeout:1500});
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}
/**
 * 返回
 */
function toReturn(){
	window.location.href = contextPath + "/system/subsys/budget/regBudget/regBudgetManage.jsp";
}

</script>
</head>
<body onload="doInit();" style="margin-top: 10px;margin-bottom: 10px;">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="700px" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;"><b >申请预算</b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >申请类型：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  >
			<input type="radio" name="regType" id="regType1" size="" checked="checked" value="1" onclick="regTypeFunc(this.value);showBudgetCost(this.value);"> 
			<label for="regType1">个人预算</label>&nbsp;&nbsp;
			<br/>
			<input type="radio" name="regType" id="regType2" size="" value="2" onclick="regTypeFunc(this.value);showBudgetCost(this.value);"> 
			<label for="regType2">部门预算</label>&nbsp;&nbsp;
		</td>
		<td nowrap class="TableData"  width="15%;" >申请金额：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  >
			<input type="text" name="amount" id="amount"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' required="true"  size="15" maxlength="10" value="0">
			<span id="budgetCostSpan"></span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >记录类型：</td>
		<td class="TableData" width="30%;"  >
			<input type="radio" name="type" id="type1" size="" checked="checked"  value="1" > 
			<label for="type1">预算申请</label>&nbsp;&nbsp;
			<br/>
			<input type="radio" name="type" id="type2" size="" value="2" > 
			<label for="type2">报销</label>&nbsp;&nbsp;
		</td>
		<td nowrap class="TableData"  width="15%;" >申请原由：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  >
			<select class="BigSelect easyui-validatebox" id="reason" name="reason" required="true">
			</select>
		</td>
	</tr>
	<tr id="personTr" >
		<td nowrap class="TableData"  width="15%;" >人员名称：<font style='color:red'>*</font></td>
		<td class="TableData" colspan="3"  >
			<%=isAdmin == true? "":loginPerson.getUserName() %>
			<span style="<%=!isAdmin?"display:none":"" %>">
				<input type=hidden name="opUserId" id="opUserId" value="<%=loginPerson.getUuid()%>">
				<input  name="opUserName" id="opUserName" class="BigStatic BigInput" size="20"  readonly value="<%=loginPerson.getUserName()%>" ></input>
				<span >
					<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['opUserId', 'opUserName'],'','','',{});">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="$('#opUserId').val('');$('#opUserName').val('');">清空</a>
				</span>
			</span>
			
		</td>
	</tr>
	<tr id="deptTr"  style="display: none;">
		<td nowrap class="TableData"  width="15%;" >部门名称：<font style='color:red'>*</font></td>
		<td class="TableData" colspan="3"  >
			<select id="opDeptId" name="opDeptId" class="BigSelect easyui-validatebox" onchange="">
			</select>
		</td>
	</tr>
	
	
	<tr >
		<td nowrap class="TableData"  width="15%;" >说明：<font style='color:red'>*</font></td>
		<td class="TableData" colspan="3"  >
			<textarea rows="5" cols="90" id="remark" name="remark" maxlength="200" class="BigInput  easyui-validatebox"  required="true" ></textarea>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="保存" class="btn btn-primary" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>

</form>

</body>
</html>