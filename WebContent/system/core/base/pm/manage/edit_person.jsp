<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

	String sid = request.getParameter("sid");
    String model = TeeAttachmentModelKeys.humanDoc;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
	.ztree{
		background:white;
		border:1px solid gray;
	}
</style>
<script>
var sid='<%=sid%>';
var deptName;
function doInit(){
// 	getDeptParent();
// 	getHrCodeByParentCodeNo("PM_STATUS_TYPE","statusType");
    doInitUpload();
	getHrCodeByParentCodeNo("PM_EMPLOYEE_TYPE","employeeType");
	getHrCodeByParentCodeNo("PM_MARRIAGE","marriage");
	getHrCodeByParentCodeNo("PM_HOUSEHOLD","household");//户口类型
	getHrCodeByParentCodeNo("PM_POLITICS","politics");
	getHrCodeByParentCodeNo("PM_EDUCATIONDEGREE","educationDegree");
	getHrCodeByParentCodeNo("PM_DEGREE","degree");
	getInsurances();
	var url = contextPath+"/humanDocController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		
		var salaryLevelModel = json.rtData.salaryLevelModel;
		var rr = [];
		if(salaryLevelModel && salaryLevelModel!=null && salaryLevelModel!="null" && salaryLevelModel!="" && salaryLevelModel!="undefined"){
			var list = eval("("+salaryLevelModel+")");
			for(var i=0;i<list.length;i++){
				rr.push("<option value='"+(i+1)+"'>"+list[i].a+"</option>");
			}
		}
		$("#salaryLevel").html(rr.join(""));
		$("#salaryLevel").val(json.rtData.salaryLevel);
		
		
		deptName=json.rtData.deptIdName;
		setTimeout('setDeptName()',500);
		if($("#isOaUser").val()=="true"){
			$("#isOaUserFlag").attr('checked',true);
		}
	 	var flag = $("#isOaUserFlag").attr('checked');
		if(flag=="checked"){
			$("#userName").attr("disabled",false);
			$("#addSpan").css("display",'');
		}else{
			$("#userName").attr("disabled",true);
			$("#addSpan").css("display",'none');
		}
		if(json.rtData.attachMentModel.length>0){
			$("#attachTr").show();
			var attaches = json.rtData.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}else{
		alert(json.rtMsg);
	}
}
function getInsurances(){
	var url = contextPath+"/salaryManage/datagridInsurances.action";
	var json = tools.requestJsonRs(url,{});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$("#insuranceId").append("<option value='"+list[i].sid+"'>"+list[i].insuranceName+"</option>");
	}
}

function setDeptName(){
	$("#deptIdName").val(deptName);
}
function commit(){
	if($("#form1").form("validate")){
		var flag = $("#isOaUserFlag").attr('checked');
		if(flag=="checked" && $("#roleId").val()==""){
			alert("请选择OA用户角色");
			return;
		}
		
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/humanDocController/updateHumanDoc.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"info");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
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



function changeCheckStatus(){
	var flag = $("#isOaUserFlag").attr('checked');
	if(flag=="checked"){
		$("#isOaUser").val("true");
		$("#addSpan").show();
	}else{
		$("#isOaUser").val("false");
		$("#addSpan").hide();
		clearData('userId', 'userName');
		clearData('roleId', 'roleName');
	} 
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
        swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}
</script>

</head>
<body onload="doInit();" style="font-size:12px" class="TableBlock">
<form id="form1" name="form1">
	<table style="width:100%;font-size:12px" class='TableBlock'>
		<tr class="TableHeader" >
			<td colspan="4" style="width:100%;height:36px;">
				基本信息
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				人员姓名<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input type="text" required="true" class="easyui-validatebox BigInput" name="personName" id="personName" />
			</td>
			<td>
				身份证号：
				</td>
			<td>
				<input class="BigInput " id="idCard" name="idCard" type="text" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				籍贯：
				</td>
			<td>
				<input type="text" class="BigInput" name="nativePlace" id="nativePlace" />
			</td>
			<td>
				档案编号：
				</td>
			<td>
				<input class="BigInput" id="codeNumber" name="codeNumber" type="text"/>
			</td>
		</tr >
		<tr class='TableData' style="display:none">
			<td colspan="4">
				<input type="hidden" id="isOaUser" name="isOaUser" value="false"/>
				<input type="checkbox" id="isOaUserFlag" name="isOaUserFlag" onClick="changeCheckStatus()"/>
				是否为oa用户
			</td>
		</tr>
		<tr class='TableData' id="addSpan">
			<td>
				关联角色<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input type="hidden" name="roleId" id="roleId" required="true" value=""> 
				<input cols="45" name="roleName" id="roleName" rows="1" style="overflow-y: auto;"  class="BigInput readonly" wrap="yes" readonly />
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleRole(['roleId', 'roleName'])">添加</a>
			</td>
			<td>
				关联用户：
			</td>
			<td>
				<input type="hidden" name="userId" id="userId" required="true" value=""> 
				<input cols="45" name="userName" id="userName" rows="1" style="overflow-y: auto;"  class="BigInput readonly" wrap="yes" readonly />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				所在部门<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input id="deptId" name="deptId"  type="hidden" />
				<input id="deptIdName" name="deptIdName"  type="text" class="BigInput easyui-validatebox readonly" required readonly/>
				<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptIdName'])">选择</a>
			</td>
			<td>
				工号：
			</td>
			<td>
				<input class="BigInput" id="workNumber" name="workNumber" type="text"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				员工状态：
			</td>
			<td>
				<select class="BigSelect"  name="statusType" id="statusType" >
					<option value="在职">在职</option>
					<option value="离职">离职</option>
					<option value="退休">退休</option>
				</select>
			</td>
			<td>
				员工类型：
				</td>
			<td>
				<select class="BigSelect" id="employeeType" name="employeeType">
					
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				英文名：
				</td>
			<td>
				<input class="BigInput" id="englishName" name="englishName" type="text"/>
			</td>
			<td>
				性别：
			</td>
			<td>
				<select  class="BigSelect" id="gender" name="gender">
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				出生日期：
				</td>
			<td>
				<input type="text" id='birthdayDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc' class="Wdate BigInput" />
		   </td>
			<td>
				民族：
			</td>
			<td>
				<input type="text" class="BigInput" name="ethnicity" id="ethnicity" />
			</td>
			
		</tr>
		<tr class='TableData'>
			<td>
				默认年假天数：
				</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox"  id="defaultAnnualLeaveDays" name="defaultAnnualLeaveDays" />
			</td>
			<td >
				职务：
				</td>
			<td>
				<input class="BigInput" id="postState" name="postState" type="text" />
			</td>
		</tr>
		<tr class='TableData'>
			
		   <td >
				婚姻状况：
				</td>
			<td>
					<select class="BigSelect" id="marriage" name="marriage">
				</select>
		   </td>
		   <td>
				毕业学校：
				</td>
			<td >
				<input class="BigInput" id="graduateSchool" name="graduateSchool" type="text" />
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				户口类型：
				</td>
			<td>
				<select class="BigSelect" id="household" name="household">
				</select>
			</td>
			<td>
				健康状况：
				</td>
			<td>
				<input class="BigInput" id="health" name="health" type="text"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				户口所在地：
				</td>
			<td>
				<input class="BigInput" id="householdPlace" name="householdPlace" type="text"/>
			</td>
			<td >
				入职时间：
				</td>
			<td>
				<input type="text" id='joinDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				政治面貌：
				</td>
			<td>
				<select class="BigSelect" id="politics" name="politics">
				</select>
			</td>
			<td >
				入党（团）时间：
				</td>
			<td>
				<input type="text" id='joinPartyDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinPartyDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				专业：
				</td>
			<td>
				<input class="BigInput" id="major" name="major"  type="text"/>
			</td>
			<td >
				毕业时间：
				</td>
			<td>
				<input type="text" id='graduateDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='graduateDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				学历：
				</td>
			<td>
				<select class="BigSelect" id="educationDegree" name="educationDegree" >
				</select>
				
			</td>
			<td >
				学位：
				</td>
			<td>
				<select class="BigSelect" id="degree" name="degree" >
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				保险套账：
			</td>
			<td>
				<select class="BigSelect" id="insuranceId" name="insuranceId" >
					<option value="0"></option>
				</select>
			</td>
			<td >
				薪资职级：
			</td>
			<td>
				<select class="BigSelect" id="salaryLevel" name="salaryLevel" >
					
				</select>
			</td>
		</tr>
		<tr class='TableHeader'>
			<td colspan="4" >
				<div class="">
					联系信息：
				</div>
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				手机号码：
				</td>
			<td>
				<input class="BigInput" validType='mobile' type="text" id="mobileNo" name="mobileNo" />
			</td>
			<td >
				电话号码：
				</td>
			<td>
				<input class="BigInput" validType='tel[]' type="text" id='telNo' name="telNo"/>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				电子邮件：
				</td>
			<td colspan="3">
				<input class="BigInput easyui-validatebox" validType='email[]' type="text" id="email" name="email" style='width:522px;' />
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				QQ号码：
				</td>
			<td>
				<input class="BigInput easyui-validatebox" validType='QQ[]' id="qqNo" name="qqNo" type="text" />
			</td>
			<td >
				MSN：
				</td>
			<td>
				<input class="BigInput" type="text" id='msn'  name='msn'  type="text"/>
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				家庭地址：
				</td>
			<td colspan="3">
				<input class="BigInput" id="address" name="address" style='width:522px;'type="text" />
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>相关附件：</b>
			</td>
			<td colspan="3">
				<div style="min-height:50px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				其他联系地址：
				</td>
			<td colspan="3" >
				<input class="BigInput" id="otherAddress" name="otherAddress" style='width:522px;' type="text" />
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>