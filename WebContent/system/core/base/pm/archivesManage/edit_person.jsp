<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

	String sid = request.getParameter("sid");
    String deptId = request.getParameter("deptId");
	String deptName = request.getParameter("deptName");
    String model = TeeAttachmentModelKeys.humanDoc;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
var deptId = "<%=deptId%>";
<%-- var deptName = "<%=deptName%>"; --%>
var deptName;
function doInit(){
 	//getDeptParent();
 	getHrCodeByParentCodeNo("PM_STATUS_TYPE","statusType");
    doInitUpload();
	getHrCodeByParentCodeNo("PM_EMPLOYEE_TYPE","employeeType");
	getHrCodeByParentCodeNo("PM_MARRIAGE","marriage");
	getHrCodeByParentCodeNo("PM_HOUSEHOLD","household");//户口类型
	getHrCodeByParentCodeNo("PM_POLITICS","politics");
	getHrCodeByParentCodeNo("PM_EDUCATIONDEGREE","educationDegree");
	getHrCodeByParentCodeNo("PM_DEGREE","degree");
	getInsurances();
	renderCustomerField();
	var url = contextPath+"/humanDocController/getById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		//alert(tools.jsonObj2String(json.rtData));
		 if(json.rtData.filedType=="下拉列表"){
				$("#optionNameTr").show();
				$("#optionValueTr").show();  
			$("#codeTypeTr").show();
			 if(json.rtData.codeType=="HR系统编码"){
				$("#sysCodeTr").show();
				$("#optionNameTr").hide();
				$("#optionValueTr").hide();
			}else if(json.rtData.codeType=="自定义选项"){
				$("#sysCodeTr").hide();
			} 
		 }else{ 
			 $("#codeTypeTr").hide();
			$("#sysCodeTr").hide(); 
		 	$("#optionNameTr").hide();
			$("#optionValueTr").hide();
			
		} 
		 
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
		
		
		deptName=json.rtData.deptName;
		setTimeout('setDeptName()',500);
		
		if($("#isOaUser").val()=="true"){
			$("#isOaUserFlag").prop('checked',true);
		}
	 	var flag = $("#isOaUserFlag").prop('checked');
		if(flag==true){
			$("#userName").attr("disabled",false);
			$("#addSpan").css("display",'');
			$("#addSpan2").css("display",'');
		}else{
			$("#userName").attr("disabled",true);
			$("#addSpan").css("display",'none');
			$("#addSpan2").css("display",'none');
		}
		if(json.rtData.attachmodels.length>0){
			$("#attachTr").show();
			var attaches = json.rtData.attachmodels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
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
	$("#deptName").val(deptName);
}
function commit(){
	if(check()&&$("#form1").valid()){
		var flag = $("#isOaUserFlag").prop('checked');
		if(flag==true && $("#roleId").val()==""){
			$.MsgBox.Alert_auto("请选择OA用户角色");
			return;
		}
		
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/humanDocController/updateHumanDoc.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg);
			opener.datagrid.datagrid("unselectAll");
			opener.datagrid.datagrid('reload');
			CloseWindow();
			return true;
		}
		$.MsgBox.Alert_auto(json.rtMsg);
		return false;
	}
}

/**
 * 验证
 */
function check(){
	if($("#personName").val()=="" || $("#personName").val()=="null" || $("#personName").val()==null){
		$.MsgBox.Alert_auto("请输入人员姓名！");
		return false;
	}
	return true;
}

//动态渲染自定义字段
function renderCustomerField(){
	$("#customTbody").html("");
	var url=contextPath+"/humanDocController/getListFieldByHuman.action";
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			var name="EXTRA_"+data[i].sid;
			if(data[i].filedType=="单行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\">"
						   +    "<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" />"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="多行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\">"
						   +    "<textarea  type=\"text\" rows=\"6\" name='"+name+"' id='"+name+"' style=\"width: 550px\" ></textarea>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="下拉列表"){
				/* var fieldCtrModel=data[i].fieldCtrModel;
				var j=tools.strToJson(fieldCtrModel); */
				if(data[i].codeType=="HR系统编码"){
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\">"
							   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
							   +"</td>"
							   +"</tr>");
					getHrCodeByParentCodeNo(data[i].sysCode,name);
					//getSysCodeByParentCodeNo(j.value,name);
				}else if(data[i].codeType=="自定义选项"){
					//var values=j.value;
					var optionNames=data[i].optionName.split(",");
					var optionValues=data[i].optionValue.split(",");
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\">"
							   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
							   +"</td>"
							   +"</tr>");
					for(var j=0;j<optionNames.length;j++){
						$("#"+name).append("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
					}
					
				}
				
			}
			
		}
		
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
	var flag = $("#isOaUserFlag").prop('checked');
	if(flag==true){
		$("#isOaUser").val("true");
		$("#addSpan").show();
		$("#addSpan2").show();
	}else{
		$("#isOaUser").val("false");
		$("#addSpan").hide();
		$("#addSpan2").hide();
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

//取消
function back(){
	opener.datagrid.datagrid("unselectAll");
	opener.datagrid.datagrid('reload');
	CloseWindow();
}
</script>

</head>
<body onload="doInit();" style="font-size:12px;padding-left: 10px;padding-right: 10px;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_dabj.png">
		<span class="title">档案编辑</span>
	</div>
   <div class="fr right">
      <input type="button" value="确定" class="btn-win-white" onclick="commit();"/>
      <input type="button" value="取消" class="btn-win-white" onclick="back();"/>
   </div>
</div>

<form id="form1" name="form1">
	<table style="width:100%;font-size:12px" class='TableBlock_page'>
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				人员姓名<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input type="text" required  name="personName" id="personName" style="height: 23px;width: 350px" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				身份证号：
				</td>
			<td>
				<input class="BigInput" id="idCard" name="idCard" type="text" style="width: 350px;height: 23px;" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				籍贯：
				</td>
			<td>
				<input type="text" name="nativePlace" id="nativePlace" style="height: 23px;width: 350px"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				档案编号：
				</td>
			<td>
				<input class="BigInput" id="codeNumber" name="codeNumber" type="text" style="width: 350px;height: 23px;"/>
			</td>
		</tr >
		<tr class='TableData' style="display:none">
			<td colspan="4">
				<input type="hidden" id="isOaUser" name="isOaUser" value="false"/>
				<input type="checkbox" id="isOaUserFlag" name="isOaUserFlag" onClick="changeCheckStatus()"/>
				是否为oa用户
			</td>
		</tr>
		<tr id="addSpan">
			<td class="TableData" width="150" style="text-indent:15px">
				关联角色<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class="TableData">
				<input type="hidden" name="roleId" id="roleId" required value=""> 
				<input name="roleName" id="roleName" class="BigInput" style="height:23px;width:350px;border: 1px solid #dadada;" wrap="yes" readonly />
				 <span class='addSpan'>
			          <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_select.png" onClick="selectSingleRole(['roleId', 'roleName'])" value="选择"/>
			     </span>
			</td>
	   </tr>
	   <tr id="addSpan2">
			<td class="TableData" width="150" style="text-indent:15px;">
				关联用户：
			</td>
			<td class="TableData">
				<input type="hidden" name="userId" id="userId" required value=""> 
				<input name="userName" id="userName" style="height:23px;width:350px;border: 1px solid #dadada;" class="BigInput"  wrap="yes" readonly="readonly"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td class='TableData' style="text-indent: 15px;">
				所在部门：
			</td>
			<td>
				<input id="deptId" name="deptId"  type="hidden" value="<%=request.getParameter("deptId")%>"/>
				<input style="width: 350px;height: 23px;" id="deptName" name="deptName"  type="text" value="<%=request.getParameter("deptName")%>" class="BigInput" required readonly="readonly"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class='TableData' width="150" style="text-indent: 15px;">
				工号：
			</td>
			<td>
				<input class="BigInput" id="workNumber" name="workNumber" type="text" style="width: 350px;height: 23px;"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class='TableData' width="150" style="text-indent: 15px;">
				员工状态：
			</td>
			<td>
				<select style="height: 23px;" class="BigSelect"  name="statusType" id="statusType" >
				</select>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				员工类型：
				</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="employeeType" name="employeeType">
					
				</select>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td style="text-indent: 15px;" width="150" class="TableData">
				英文名：
				</td>
			<td>
				<input style="width:350px;height: 23px;" class="BigInput" id="englishName" name="englishName" type="text"/>
			</td>
		</tr>
		<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				性别：
			</td>
			<td>
				<select style="height: 23px;"  class="BigSelect" id="gender" name="gender">
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				出生日期：
				</td>
			<td>
				<input readonly="readonly" style="width: 200px;height: 23px;" type="text" id='birthdayDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc' class="Wdate BigInput" />
		   </td>
		 </tr>
		 <tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				民族：
			</td>
			<td>
				<input style="width: 350px;height: 23px;" type="text" class="BigInput" name="ethnicity" id="ethnicity" />
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				默认年假天数：
				</td>
			<td>
				<input style="height: 23px;width: 350px;" positive_integer='true' type="text" class="BigInput" id="defaultAnnualLeaveDays" name="defaultAnnualLeaveDays" />
			</td>
		</tr>
		<tr class="TablrData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				职务：
				</td>
			<td>
				<input style="width: 350px;height: 23px;" class="BigInput" id="postState" name="postState" type="text" />
			</td>
		</tr>
		<tr class='TableData' align="left">
		   <td class="TableData" width="150" style="text-indent: 15px;">
				婚姻状况：
				</td>
			<td>
					<select style="height: 23px;" class="BigSelect" id="marriage" name="marriage">
				</select>
		   </td>
		   </tr>
		   <tr class="TableData" align="left">
		   <td class="TableData" width="150" style="text-indent: 15px;">
				毕业学校：
				</td>
			<td >
				<input style="width: 350px;height: 23px;" class="BigInput" id="graduateSchool" name="graduateSchool" type="text" />
			</td>
		</tr>
			<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				户口类型：
				</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="household" name="household">
				</select>
			</td>
			</tr>
			<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				健康状况：
				</td>
			<td>
				<input style="width: 350px;height: 23px;" class="BigInput" id="health" name="health" type="text"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				户口所在地：
				</td>
			<td>
				<input style="width: 350px;height: 23px;" class="BigInput" id="householdPlace" name="householdPlace" type="text"/>
			</td>
			</tr>
			<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				入职时间：
				</td>
			<td>
				<input readonly="readonly" style="width: 200px;height: 23px;" type="text" id='joinDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				政治面貌：
				</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="politics" name="politics">
				</select>
			</td>
		</tr>
		<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				入党（团）时间：
				</td>
			<td>
				<input readonly="readonly" style="width: 200px;height: 23px;" type="text" id='joinPartyDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinPartyDateDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				专业：
				</td>
			<td>
				<input style="width: 350px;height: 23px;" class="BigInput" id="major" name="major"  type="text"/>
			</td>
		</tr>
		<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				毕业时间：
				</td>
			<td>
				<input style="width: 200px;height: 23px;" type="text" id='graduateDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='graduateDateDesc' class="Wdate BigInput" readonly="readonly" />
			</td>
		</tr>
			<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				学历：
				</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="educationDegree" name="educationDegree" >
				</select>
				
			</td>
		</tr>
		<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				学位：
				</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="degree" name="degree" >
				</select>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				保险套账：
			</td>
			<td>
				<select style="height: 23px;" class="BigSelect" id="insuranceId" name="insuranceId" >
					<option value="0"></option>
				</select>
			</td>
		</tr>
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">联系信息</B>
		   </td>
	   </tr>
			<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				手机号码：
				</td>
			<td>
				<input style="width: 350px;height: 23px;" class="BigInput" telephone="true" type="text" id="mobileNo" name="mobileNo" />
			</td>
		</tr>
		<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				电话号码：
				</td>
			<td>
				<input style="width:350px;height: 23px;" class="BigInput" type="text" id='telNo' name="telNo"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				电子邮件：
				</td>
			<td>
				<input class="BigInput" email="true" type="text" id="email" name="email" style='width:350px;height: 23px;' />
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				QQ号码：
				</td>
			<td class="TableData">
				<input class="BigInput" id="qqNo" name="qqNo" type="text" style="width: 350px;height: 23px;" />
			</td>
		</tr>
		<tr class="TableData" align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				MSN：
				</td>
			<td class="TableData">
				<input class="BigInput" type="text" id='msn'  name='msn'  type="text" style="width: 350px;height: 23px;"/>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				家庭地址：
				</td>
			<td class="TableData">
				<input class="BigInput" id="address" name="address" style='width:350px;height: 23px;'type="text" />
			</td>
		</tr>
			<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				其他联系地址：
				</td>
			<td class="TableData">
				<input class="BigInput" id="otherAddress" name="otherAddress" style='width:350px;height: 23px;' type="text" />
			</td>
		</tr>
	  <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">相关附件</B>
		   </td>
	   </tr>
		<tr class='TableData' align="left">
		<td class="TableData" width="150" style="text-indent:15px"> 附件：</td>
			<td class="TableData">
				<div style="min-height:50px;text-indent: 15px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
			</td>
		</tr>
	</table>
<table class="TableBlock_page" width="100%" align="center" id="customTable">
  <thead>
     <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">自定义字段</B></TD>
     </tr>
  </thead>
  <tbody id="customTbody" >
     
  </tbody>
</table> 
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>