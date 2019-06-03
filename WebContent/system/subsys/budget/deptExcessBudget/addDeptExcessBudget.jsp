<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
	int year = TeeStringUtil.getInteger(request.getParameter("year"), 0);
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
var deptId = "<%=deptId%>";
var year = "<%=year%>";
var save = "<%=save%>";
var contextPath="<%=contextPath %>";
function doInit(){
	getDeptList();
	if(deptId>0){
		getInfoById(deptId,year);
	}
}

/**
 * 查看详情 
 */
function getInfoById(deptId,year){
	var url = "<%=contextPath%>/deptBudgetController/getInfoById.action";
	var para = {deptId : deptId,year:year};
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
						$("#deptId").val(prcList[i].deptId);
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
	var deptId =$("#deptId").val();
	if(!deptId){
		top.$.jBox.tip("请选择公司部门！",'info',{timeout:1500});
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
		top.$.jBox.tip("该公司部门年度预算已存在！",'info',{timeout:1500});
		return false;
	}
	return true;
}
/**
 * 校验是否已存在
 * returnPrc 0-不存在；1-存在
 */
function checkExist(){
	var url = "<%=contextPath %>/deptBudgetController/checkExist.action";
	var para =  tools.formToJson($("#form1"));
	para["deptId"] = $("#deptId").val();
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
	//if(checkForm()){
		var deptId=$("#deptId").val();
	//	alert(deptId);
		var url = contextPath+"/deptExcessBudgetController/addDeptExcess.action?deptId="+deptId;
		var para =  tools.formToJson($("#form1"));
		<%-- para["fromDeptId"] = "<%=deptId%>";
		//para["fromYearId"] = "<%=year%>"; --%>
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//toReturn();
			//top.$.jBox.tip("保存成功!",'info',{timeout:1500});
			alert("保存成功！");
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	//}
}
/**
 * 返回
 */
function toReturn(){
	window.location.href = contextPath + "/system/subsys/budget/deptBudget/deptBudgetManage.jsp";
}

</script>
</head>
<body onload="doInit();" style="margin-top: 10px;margin-bottom: 10px;">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="500px" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left; "><b >基本信息</b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >公司部门：<font style='color:red'>*</font></td>
		<td class="TableData"   >
			
			<input type="text" name="deptId" id=deptId size="" class="BigInput  easyui-validatebox"  style="display:none;" size="15" maxlength="50" >
		
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
	<tr>
	<td class="TableData">申请金额：</td>
	<td class="TableData"><input type="text" class="BigStatic BigInput" id="excessAmount" name="excessAmount" /></td>
	<td class="TableData">月份：</td>
	<td class="TableData">
	<select id="month" name="month" >
	<option value="01">1月</option>
	<option value="02">2月</option>
	<option value="03">3月</option>
	<option value="04">4月</option>
	<option value="05">5月</option>
	<option value="06">6月</option>
	<option value="07">7月</option>
	<option value="08">8月</option>
	<option value="09">9月</option>
	<option value="10">10月</option>
	<option value="11">11月</option>
	<option value="12">12月</option>
	</select>
	</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="保存" class="btn btn-primary" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
	
</table>
<br/>

<!-- <table align="center" width="500px" class="TableBlock" >
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
	
</table> -->

</form>

</body>
</html>