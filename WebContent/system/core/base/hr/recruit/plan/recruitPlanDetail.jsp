<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int optType = TeeStringUtil.getInteger(request.getParameter("optType"), 0);//操作类型 1-申请初试 0-查看详情
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<title>招聘需求详细信息</title>
<style>
  .TableList {border:1px solid #F2f2f2} 
</style>
<script type="text/javascript">
var optType = '<%=optType%>' ;
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
			$("#planStatus").text(planStatusFunc(prc.planStatus));
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var attach = attaches[i];
				attach["priv"] = 3;
				var fileItem = tools.getAttachElement(attach);
				$("#attachment").append(fileItem);
			}
			

			//招聘需求
			var recruitRequirementsModelList = prc.recruitRequirementsModelList;
			for(var i=0;i<recruitRequirementsModelList.length;i++){
				addHrRecruiRequItem(recruitRequirementsModelList[i]);
			}
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

/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘需求详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/requirements/recruitDetail.jsp?sid=" + sid;
  top.bsWindow(url ,title,{width:"1000",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
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
		showInfoFunc(sid);
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
	 
	var delTd=$("<td width='100px;'>").append($("<button name='"+sid+"'>申请面试</button>"
	).addClass("btn-primary").addClass("btn").click(function(){
		applyInterview($(this).attr("name"));
	}));
	
	
	var tr=$("<tr>");
	tr.append(requNo);
	tr.append(requJob);
	tr.append(createUserName);
	tr.append(requNum);
	tr.append(requDeptStrName);
	tr.append(requTimeStr);
	if(optType == 1){
		tr.append(delTd);
	}
	$("#hrList").append(tr);
}

function toReturn(){
	window.location.href = contextPath + "/system/core/base/hr/recruit/plan/index.jsp";
}

/**
 * 申请面试
 */
function applyInterview( sid ,callBackFunc) {
	var url = contextPath + "/system/core/base/hr/recruit/plan/applyInterview.jsp?sid=" + sid + "&planId=<%=sid%>" ;
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 1000, 500);
}
</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">

<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="10%;" style="text-indent: 10px">名称：</td>
		<td class="TableData" width="30%;"  id="planName">
		</td>
		<td nowrap class="TableData" width="10%;" > 发起人 ：</td>
		<td class="TableData" id="createUserName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">招聘渠道：</td>
		<td class="TableData" width="" id="planDitch">
		</td>
		<td nowrap class="TableData" width="" >预算费用 ：</td>
		<td class="TableData" id="planCost">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">招聘人数：</td>
		<td class="TableData" width="" id="planRecrNum">
		</td>
		<td nowrap class="TableData" width="" >  登记时间  ：</td>
		<td class="TableData" id="createTimeStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">开始日期：</td>
		<td class="TableData" width="" id="startDateStr">
		</td>
		<td nowrap class="TableData" width="" >  结束日期  ：</td>
		<td class="TableData" id="endDateStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">招聘说明：</td>
		<td class="TableData" width="" colspan="3" id="recruitDescription">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">招聘备注：</td>
		<td class="TableData" width="" colspan="3" id="recruitRemark">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">关联需求：</td>
		<td class="TableData" width="" colspan="3">
		<div style="padding:0px;">
   			 <table id="" class="TableList" style="width:100%" align="center" >
    		 	<tr>
    		 		<td class="TableData" align="center">需求编号</td>
    		 		<td  class="TableData" align="center">需求岗位</td>
    		 		<td  class="TableData" align="center">提交人</td>
    		 		<td class="TableData" align="center">需求人数</td>
    		 		<td class="TableData" align="center">需求部门</td>
    		 		<td class="TableData" align="center">用工日期</td>
    		 		<%if(optType == 1){ %>
    		 		<td class="TableData" align='center' style="padding-left:5px;">操作</td>
    		 		<%} %>
    		 	</tr>
    		 	<tbody id="hrList">
    		 	</tbody>
   		 	</table>
   		 </div>	
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">附件文档：</td>
		<td class="TableData" width="" colspan="3" id="attachment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">审批人：</td>
		<td class="TableData" width="" id="approvePersonName">
		</td>
		<td nowrap class="TableData" width="" >审批日期  ：</td>
		<td class="TableData" id="approveDateStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">审批意见：</td>
		<td class="TableData" width="" colspan="3" id="approveComment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">计划状态：</td>
		<td class="TableData" width="" colspan="3" id="planStatus">
		</td>
	</tr>
	
</table>
		
</body>
</html>