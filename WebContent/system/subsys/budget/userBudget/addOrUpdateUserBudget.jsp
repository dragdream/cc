<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int fromUserId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
	int fromYearId = TeeStringUtil.getInteger(request.getParameter("year"), 0);
	String save = TeeStringUtil.getString(request.getParameter("save"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建或编辑费用预算</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/budget/deptBudget/js/deptBudget.js"></script>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript">
var fromUserId = "<%=fromUserId%>";
var fromYearId = "<%=fromYearId%>";
var save = "<%=save%>";
function doInit(){
	if(fromUserId>0){
		getInfoById(fromUserId,fromYearId);
	}
}

/**
 * 查看详情 
 */
function getInfoById(userId,year){
	var url = "<%=contextPath%>/userBudgetController/getInfoById.action";
	var para = {userId : userId,year:year};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			var prcList = prc.monthList;
			var j = 0;
			if(prcList.length>0){
				for(var i=0;i<prcList.length;i++){
					if(i==0){
						$("#userId").val(prcList[i].userId);
						$("#userName").val(prcList[i].userName);
						$("#year").val(prcList[i].year);
					}
					j++;
					var month = "";
					if(j<10){
						month = "0"+j;
					}else{
						month = j;
					}
					$("#month_"+ month).val(prcList[i].amount);
					$("#monthId_"+ month).val(prcList[i].uuid);
					
				}
			}
			nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); 
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}




function checkForm(){
	var userId =$("#userId").val();
	if(!userId){
		alert("请选择人员！");
		return false;
	}
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	var returnPrc = checkExist();
	if(save=="0"){
		returnPrc = '0';
	}
	if(returnPrc == '1'){
		alert("该用户年度预算已存在！");
		return false;
	}
	return true;
}
/**
 * 校验是否已存在
 * returnPrc 0-不存在；1-存在
 */
function checkExist(){
	var url = "<%=contextPath %>/userBudgetController/checkExist.action";
	var para =  tools.formToJson($("#form1"));
	para["userId"] = $("#userId").val();
	para["year"] = $("#year").val();
	var returnPrc="";
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		returnPrc = jsonRs.rtData.existFlag;
	}else{
		alert(jsonRs.rtMsg);
	}
	return returnPrc;
}


/**
 * 提交
 */
function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/userBudgetController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		para["fromUserId"] = "<%=fromUserId%>";
		para["fromYearId"] = "<%=fromYearId%>";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			alert("保存成功!");
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}
/**
 * 返回
 */
function toReturn(){
	window.location.href = contextPath + "/system/subsys/budget/userBudget/userBudgetManage.jsp";
}






</script>
</head>
<body onload="doInit();" style="margin-top: 10px;margin-bottom: 10px;">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="500px" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;"><b >基本信息</b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >人员名称：<font style='color:red'>*</font></td>
		<td class="TableData"   >
			<input type=hidden name="userId" id="userId" value="">
			<input  name="userName" id="userName" class="BigStatic BigInput" size="10"  readonly value=""></input>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userId', 'userName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#userId').val('');$('#userName').val('');">清空</a>
			</span>
		</td>
		<td nowrap class="TableData"   >目标年度：</td>
		<td class="TableData"  >
			<select id="year" name="year" class="BigSelect easyui-validatebox" >
				<%
					int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					int counter = currentYear + 30;
					int defaultYear = 2000;
					for(int i=defaultYear;i<=counter;i++){
				%>
				<option value="<%=i %>"  <%=(i == currentYear)? "selected":"" %> ><%=i %></option>
				<%
					}
				%>
				
			</select>
		</td>
	</tr>
</table>
<br/>

<table align="center" width="500px" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;"><b >费用计划</b></td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"   ></td>
		<td class="TableData"  colspan="3" >
			费用金额(元)
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center"><b>第一季</b></td>
		<td class="TableData"  colspan="3">
			<span id="fistQuarter" style="color:red"></span>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">一月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_01" name="monthId_01"  >
			<input type="text" name="month_01" id="month_01"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]'  size="15" maxlength="10" onblur="jiDuHeJiFunc('month_01','month_02','month_03','fistQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">二月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_02" name="monthId_02"  >
			<input type="text" name="month_02" id="month_02"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_01','month_02','month_03','fistQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">三月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_03" name="monthId_03"  >
			<input type="text" name="month_03" id="month_03"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_01','month_02','month_03','fistQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center"><b>第二季</b></td>
		<td class="TableData"  colspan="3">
			<span id="secondQuarter" style="color:red"></span>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">四月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_04" name="monthId_04"  >
			<input type="text" name="month_04" id="month_04"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_04','month_05','month_06','secondQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">五月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_05" name="monthId_05"  >
			<input type="text" name="month_05" id="month_05"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_04','month_05','month_06','secondQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">六月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_06" name="monthId_06"  >
			<input type="text" name="month_06" id="month_06"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_04','month_05','month_06','secondQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center"><b>第三季</b></td>
		<td class="TableData"  colspan="3">
			<span id="thirdQuarter" style="color:red"></span>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">七月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_07" name="monthId_07"  >
			<input type="text" name="month_07" id="month_07"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_07','month_08','month_09','thirdQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">八月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_08" name="monthId_08"  >
			<input type="text" name="month_08" id="month_08"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_07','month_08','month_09','thirdQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">九月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_09" name="monthId_09"  >
			<input type="text" name="month_09" id="month_09"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_07','month_08','month_09','thirdQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center"><b>第四季</b></td>
		<td class="TableData"  colspan="3">
			<span id="fourthQuarter" style="color:red"></span>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">十月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_10" name="monthId_10"  >
			<input type="text" name="month_10" id="month_10"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_10','month_11','month_12','fourthQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">十一月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_11" name="monthId_11"  >
			<input type="text" name="month_11" id="month_11"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_10','month_11','month_12','fourthQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"    align="center">十二月</td>
		<td class="TableData"  colspan="3">
			<input type="hidden" id="monthId_12" name="monthId_12"  >
			<input type="text" name="month_12" id="month_12"  class="BigInput  easyui-validatebox" validType='pointTwoNumber[]' size="15" maxlength="10" onblur="jiDuHeJiFunc('month_10','month_11','month_12','fourthQuarter'); nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); ">
		</td>
	</tr>
	<tr align="">
		<td nowrap class="TableData" align="center"><b>合计：</b></td>
		<td class="TableData"  colspan="3" align="center">
			<span id="total" style="color:red"></span>
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