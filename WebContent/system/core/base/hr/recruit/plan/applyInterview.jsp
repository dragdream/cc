<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//面试申请Id
	int planId = TeeStringUtil.getInteger(request.getParameter("planId"), 0);//计划id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>

<title>申请面试</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript">
function doInit(){
	getInfoById("<%=sid%>");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/recruitRequirementsController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			//editor.setData(prc.requRequires);
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var attach = attaches[i];
				attach["priv"] = 3;
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachment").append(fileItem);
			}
			
			var hrPoolsModelList = prc.hrPoolsModel;
			for(var i=0;i<hrPoolsModelList.length;i++){
				var hrPoolsModel = hrPoolsModelList[i];
				addHrPoolsItem(hrPoolsModel);
			}
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}


/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘需求详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/requirements/recruitDetail.jsp?sid=" + sid;
  top.bsWindow(url ,title,{width:"1000",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
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
function addHrPoolsItem(item ){
	var sid = item.sid;
	//姓名
	
	// 需求编号
	var employeeNameA =  $("<a href='javascript:void();'>").append(item.employeeName).click(function(){
		showDetailFunc(sid);
	});
	var employeeName=$("<td  id='"+sid+"'>").append(employeeNameA);
	var sexValue = "男";
	if(item.sex == '1'){
		sexValue = "女";
	}
	//性别
	var sex = $("<td>").append(sexValue);
	//联系电话
	var employeePhone=$("<td >").append(item.employeePhone);
	//邮箱
	var employeeEmail =$("<td>").append(item.employeeEmail);
	//薪水
	var expectedSalary=$("<td>").append(item.expectedSalary);
	var STAFF_HIGHEST_SCHOOL=$("<td>").append(item.employeeHighestSchoolDesc);
	//岗位
	var POOL_POSITION =$("<td>").append(item.positionDesc);
	//专业
	var POOL_EMPLOYEE_MAJOR=$("<td style='display:none;'>").append(item.employeeMajorDesc);
	var time = $("<input style='width:150px;' name='time' class='Wdate BigInput' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" >").addClass("BigInput easyui-validatebox");
	var interviewTime=$("<td>").append(time);
	
	var optTd=$("<td width='100px;'>").append($("<button name='"+sid+"'>申请面试</button>"
	).addClass("btn-primary").addClass("btn").click(function(){
		//applyInterview($(this).attr("name"));
	}));
	var tr=$("<tr>");
	tr.append(employeeName);
	tr.append(sex);
	tr.append(employeePhone);
	tr.append(employeeEmail);
	tr.append(expectedSalary);
	tr.append(POOL_POSITION);
	tr.append(POOL_EMPLOYEE_MAJOR);
	tr.append(interviewTime);
	$("#hrPoolList").append(tr);
}

function showDetailFunc(sid){
	  var title = "人才简历库详情";
	  var url = contextPath + "/system/core/base/hr/pool/detail.jsp?sid=" + sid;
	  top.bsWindow(url ,title,{width:"950",height:"400",buttons:
			[
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,submit:function(v,h){
			if(v=="关闭"){
				return true;
			}
		}});
	}
/**
 * 批量发送面试申请
 */
function batchSendInterview(){
	
	var hrPoolsItem = new Array();
	var itemList = $("#hrPoolList").find("tr");
	var isExist  = false;
	for(var i =0; i <itemList.length ; i++){
		var itemTemp = itemList[i];
		var sid = $(itemTemp.cells[0]).attr("id");
		var employeePhone = $(itemTemp.cells[2]).html();
		var positionDesc = $(itemTemp.cells[5]).html();
		var employeeMajorDesc = $(itemTemp.cells[6]).html();
		var interviewTime = $(itemTemp.cells[7]).find("input").val();
		if(!interviewTime || interviewTime == ""){
			break;
		}
		isExist = true;
		var itemp = {sid : sid ,employeePhone :employeePhone, employeeMajorDesc:employeeMajorDesc ,positionDesc:positionDesc,interviewTime : interviewTime};
		hrPoolsItem.push(itemp);
	}
	if(isExist){
		var url =   "<%=contextPath%>/recruitRequirementsController/batchSendInterview.action";
		var batchSendInterviewInfo = tools.jsonArray2String(hrPoolsItem);
		var para = {batchSendInterviewInfo : batchSendInterviewInfo , sid:"<%=sid%>" , planId:"<%=planId%>"};
		var jsonObj = tools.requestJsonRs(url, para);
		if (jsonObj.rtState) {
			alert("申请面试成功！");
		}else{
			alert(jsonObj.rtMsg);
		}
	}else{
		alert("至少选中一个发送面试申请的人员！");
	}
	
}
</script>
</head>
<body onload="doInit();">
<table align="center" width="96%" class="TableBlock" style="margin-top:20px;" >
	<tr>
		<td nowrap class="TableData"  width="12%;">需求编号：</td>
		<td class="TableData" width="30%;"  id="requNo">
		</td>
		<td nowrap class="TableData" width="12%;" >需求部门：</td>
		<td class="TableData" id="requDeptStrName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">需求岗位：</td>
		<td class="TableData" width="" id="requJob">
		</td>
		<td nowrap class="TableData" width="" >需求人数：</td>
		<td class="TableData" id="requNum">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">用工日期：</td>
		<td class="TableData" width="" id="requTimeStr">
		</td>
		<td nowrap class="TableData" width="" > 申请人 ：</td>
		<td class="TableData" id="createUserName">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="">关联人才库：</td>
		<td class="TableData" width="" colspan="3">
	
	
	<div style="padding:0px;">
	
	 <input type="button" value="批量发送面试申请" class="btn btn-warning btn-xs" title="批量发送面试申请" onClick="batchSendInterview();">&nbsp;&nbsp;<font color="red">（为空不发送面试申请）</font>
   			
	    			 <table id="" class="TableList" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">姓名</td>
		    		 		<td  class="TableData" align="center">性别</td>
		    		 		<td class="TableData" align="center">联系方式</td>
		    		 		<td class="TableData" align="center">邮箱</td>
		    		 		<td class="TableData" align="center">期望薪水</td>
		    		 		<td class="TableData" align="center">应聘岗位</td>
		    		 		<td class="TableData" align="center" style="display:none;">专业</td>
		    		 		<td class="TableData" align="center" width="60px;">面试时间</td>
		    	<!-- 	 		<td class="TableData" align="center">操作</td> -->
		    		 	</tr>
		    		 	<tbody id="hrPoolList">
		    		 	
		    		 	</tbody>
		    		 
	    		 	</table>
	    		 </div>	
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">岗位要求：</td>
		<td class="TableData" width="" colspan="3" id="requRequires">
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备　　注：</td>
		<td class="TableData" width="" colspan="3" id="remark">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="">附件文档：</td>
		<td class="TableData" width="" colspan="3" id="attachment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">登记时间：</td>
		<td class="TableData" width="" colspan="3" id="createTimeStr">
		</td>
	</tr>
</table>
		
</body>
</html>