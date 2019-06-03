<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//会议申请Id
	int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);//会议申请Id
	
	String dataStr = TeeUtility.getDateTimeStr(new Date());

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<title>招聘需计划细信息</title>
<script type="text/javascript">

function doInit(){
	getInfoById("<%=sid%>");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/recruitPlanController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#planDitch").text(planDitchFunc(prc.planDitch));
			$("#approveDateStr").val('<%=dataStr%>');
			$("#planStatus").val('<%=status%>');
			
			//招聘需求
			var recruitRequirementsModelList = prc.recruitRequirementsModelList;
			for(var i=0;i<recruitRequirementsModelList.length;i++){
				addHrRecruiRequItem(recruitRequirementsModelList[i]);
			}
			
			//$("#planStatus").text(planStatusFunc(prc.planStatus));
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

function planDitchFunc(ids){
	var str ="";
	if(ids == "01"){
		str = "网络招聘";
	}else if(ids == "02"){
		str = "招聘会招聘";
	}else if(ids == "03"){
		str = "人才猎头推荐";
	}
	return str;
}


function planStatusFunc(ids){
	var str = "";
	if(ids == "0"){
		str = "待审批";
	}else if(ids == "1"){
		str = "已批准";
	}else if(ids == "2"){
		str = "未批准";
	}
	return str;
}

function checkForm(){
	var check = $("#form1").validate(); 
	if(!check){
		return false; 
	}
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/recruitPlanController/setPlanStatus.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    return jsonRs;
	    /* if(jsonRs.rtState){
	      callback();
	    }else{
	      $.MsgBox.Alert_auto(jsonRs.rtMsg);
	    } */
	}
}

/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 */
function addHrRecruiRequItem(item ){
	var sid = item.sid;
	// 需求编号
	var requNoA =  $("<a href='javascript:void();'>").append(item.requNo).click(function(){
		//showInfoFunc(sid);
	});
	var requNo=$("<td width='100px' nowrap>").append(requNoA);
	//需求岗位
	var requJob = $("<td>").append(item.requJob);
	//创建者名称
	var createUserName = $("<td>").append(item.createUserName);
	//人人数
	var requNum = $("<td>").append(item.requNum);
	//需求部门
	var requDeptStrName = $("<td>").append(item.requDeptStrName);
	//用户日期
	var requTimeStr = $("<td>").append(item.requTimeStr);
	 
	
	
	var tr=$("<tr>");
	tr.append(requNo);
	tr.append(requJob);
	tr.append(createUserName);
	tr.append(requNum);
	tr.append(requDeptStrName);
	tr.append(requTimeStr);
	$("#hrList").append(tr);
}

</script>
</head>
<body onload="doInit();">
<form action="" method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<input type="hidden" name="planStatus" id="planStatus" value="">

<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent:10px;">名称：</td>
		<td class="TableData" width="35%;"  id="planName">
		</td>
		<td nowrap class="TableData" width="15%;" > 发起人 ：</td>
		<td class="TableData" id="createUserName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">招聘渠道：</td>
		<td class="TableData" width="" id="planDitch">
		</td>
		<td nowrap class="TableData" width="" >预算费用 ：</td>
		<td class="TableData" id="planCostStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">招聘人数：</td>
		<td class="TableData" width="" id="planRecrNum">
		</td>
		<td nowrap class="TableData" width="" >  登记时间  ：</td>
		<td class="TableData" id="createTimeStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">开始日期：</td>
		<td class="TableData" width="" id="startDateStr">
		</td>
		<td nowrap class="TableData" width="" >  结束日期  ：</td>
		<td class="TableData" id="endDateStr">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">关联需求：</td>
		<td class="TableData" width="" colspan="3">
		<div style="padding:0px;">
<!--    		 	 <input type="button" value="选择招聘需求" class="btn btn-warning btn-xs" title="人才库查询" onClick="queryHrRecruitRequ([],'queryHrRecruiRequCallBackFunc');">&nbsp;&nbsp; -->
   			 <table id="" class="TableList" style="width:100%" align="center" >
    		 	<tr>
    		 		<td class="TableData" align="center">需求编号</td>
    		 		<td  class="TableData" align="center">需求岗位</td>
    		 		<td  class="TableData" align="center">提交人</td>
    		 		<td class="TableData" align="center">需求人数</td>
    		 		<td class="TableData" align="center">需求部门</td>
    		 		<td class="TableData" align="center">用工日期</td>
    		 	</tr>
    		 	<tbody id="hrList">
    		 	</tbody>
   		 	</table>
   		 </div>	
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">招聘说明：</td>
		<td class="TableData" width="" colspan="3" id="recruitDescription">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">招聘备注：</td>
		<td class="TableData" width="" colspan="3" id="recruitRemark">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">附件文档：</td>
		<td class="TableData" width="" colspan="3" id="attachment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">审批人：</td>
		<td class="TableData" width="" id="approvePersonName">
		</td>
		<td nowrap class="TableData" width="" >审批日期  ：</td>
		<td class="TableData">
			<input type="text" name="approveDateStr" id="approveDateStr" size="19" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px;">审批意见：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="4" cols="85" id="approveComment" name="approveComment" maxlength="150" class="BigTextarea"></textarea>
		</td>
	</tr>
</table>
</form>
</body>
</html>