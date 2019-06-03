<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int deptId = TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
	int year = TeeStringUtil.getInteger(request.getParameter("year"), 0);
	String queryOptFlag = TeeStringUtil.getString(request.getParameter("optFlag"), "0");
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
<script type="text/javascript">
var deptId = "<%=deptId%>";
var year = "<%=year%>";
function doInit(){
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
					if(i =='0'){
						$("#deptName").html(prcList[i].deptName);
						$("#year").html(prcList[i].year);
					}
					j++;
					var month = "";
					if(j<10){
						month = "0"+j;
					}else{
						month = j;
					}
					$("#month_"+ month).html(prcList[i].amount);
					
				}
			}
			nianHeJiFunc('fistQuarter','secondQuarter','thirdQuarter','fourthQuarter','total'); 
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}




/**
 * 返回
 */
function toReturn(queryOptFlag){
	if(queryOptFlag ==1){
		window.location.href = contextPath + "/system/subsys/budget/deptBudget/deptBudgetQuery.jsp";
	}else{
		window.location.href = contextPath + "/system/subsys/budget/deptBudget/deptBudgetManage.jsp";
	}
	
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
		<td nowrap class="TableData"  width="15%;" >公司部门：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  id="deptName">
		</td>
		<td nowrap class="TableData"   width="15%;">目标年度：</td>
		<td class="TableData"  id="year" >
		</td>
	</tr>
</table>
<br/>
<table align="center" width="500px" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left; "><b >费用计划</b></td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;" ></td>
		<td class="TableData" width="30%;" colspan="3" >
			费用金额(元)
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center"><b>第一季</b></td>
		<td class="TableData" width="30%;" colspan="3" id="fistQuarter" style="color:red">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">一月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_01">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">二月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_02">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">三月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_03">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center"><b>第二季</b></td>
		<td class="TableData" width="30%;" colspan="3" id="secondQuarter" style="color:red" >
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">四月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_04">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">五月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_05">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">六月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_06">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center"><b>第三季</b></td>
		<td class="TableData" width="30%;" colspan="3" id="thirdQuarter" style="color:red">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">七月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_07">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">八月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_08">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">九月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_09">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center"><b>第四季</b></td>
		<td class="TableData" width="30%;" colspan="3" id="fourthQuarter" style="color:red">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">十月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_10">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">十一月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_11">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData"  width="15%;"  align="center">十二月</td>
		<td class="TableData" width="30%;" colspan="3" id="month_12">
		</td>
	</tr>
	<tr align="">
		<td nowrap class="TableData" align="center"><b>合计：</b></td>
		<td id="total" class="TableData"  colspan="3" align="center" style="color:red">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn('<%=queryOptFlag%>');"/>&nbsp;&nbsp;
		</td>
	</tr>
	
</table>
</form>
</body>
</html>