<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String model = TeeAttachmentModelKeys.TRAINING_PLAN;


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<title>新建培训计划信息</title>
<script type="text/javascript">

function doInit(){
	//初始化fck
	CKEDITOR.replace('content',{
		width : 'auto',
		height:300
	});
	//初始化附件
	new TeeSWFUpload({
		fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	//部门
	getDeptParent();
}

function checkForm(){
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/trainingPlanController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      callback();
	      /* setTimeout(function(){
	        //刷新父页面
	        parent.location.reload();
			//return true;
	      },1800); */
	    }else{
	      alert(jsonRs.rtMsg);
	    }
	}
}



/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"hostDepartmentsIdZTree",
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
    	 var node = ZTreeObj.getNodeByParam("id",$("#hostDepartmentsId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'hostDepartmentsId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#hostDepartmentsIdName").val(treeNode.name);
	$("#hostDepartmentsId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}



</script>
</head>
<body onload="doInit()" >
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" >计划编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="planNo" id="planNo" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >计划名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="planName" id="planName" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >培训渠道：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<select name="planChannel" id="planChannel">
				<option value="">请选择</option>
				<option value="0">内部培训</option>
				<option value="1">渠道培训</option>
			</select>
		</td>
		<td nowrap class="TableData"  width="15%;" >培训形式：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<select name="courseTypes" id="courseTypes">
				<option value="">请选择</option>
				<option value="1">面授</option>
				<option value="2">函授</option>
				<option value="3">其它</option>
			</select>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >主办部门：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input id="hostDepartmentsId" name="hostDepartmentsId"  type="text" style="display:none;"/>
			<ul id="hostDepartmentsIdZTree" class="ztree" style="margin-top:0; width:240px; display:none;"></ul>
		</td>
		<td nowrap class="TableData"  width="15%;" >负责人：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type=hidden name="chargePersonId" id="chargePersonId" value="">
			<input  name="chargePersonName" id="chargePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['chargePersonId', 'chargePersonName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#chargePersonId').val('');$('#chargePersonName').val('');">清空</a>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >计划参与培训人数：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="joinNum" id="joinNum" size="" maxlength="10" class="BigInput easyui-validatebox"  required="true"   validType ='number[]'>（人）
		</td>
		<td nowrap class="TableData"  width="15%;" >培训地点：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="address" id="address" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >培训机构名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="institutionName" id="institutionName" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >培训机构联系人：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="institutionContact" id="institutionContact" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >培训课程名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="courseName" id="courseName" size="" class="BigInput  easyui-validatebox" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >总课时：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="courseHours" id="courseHours" size="" class="BigInput  easyui-validatebox" required="true"   validType ='number[]'>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >开课时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="courseStartTimeStr" id="courseStartTimeStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="15%;" >结课时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="courseEndTimeStr" id="courseEndTimeStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" required="true" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >培训预算：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="planCost" id="planCost" size="" class="BigInput  easyui-validatebox" required="true"   validType ='number[]'>
		</td>
		<td nowrap class="TableData"  width="15%;" >审批人：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type=hidden name="approvePersonId" id="approvePersonId" value="">
			<input  name="approvePersonName" id="approvePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');">清空</a>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" >参与培训部门：</td>
		<td class="TableData"  colspan="3">
			<input type='hidden' id='joinDeptIdStr' name='joinDeptIdStr' />
			<textarea cols='70' name='joinDeptNameStr' id='joinDeptNameStr' rows='3' style='overflow-y: auto;' class='SmallStatic BigTextarea' wrap='yes' readonly >
			</textarea>
			<a href='javascript:void(0);' class='orgAdd' onClick="selectDept(['joinDeptIdStr','joinDeptNameStr'],'1')">选择</a>
			&nbsp;&nbsp;<a href='javascript:void(0);' class='orgClear' onClick="clearData('joinDeptIdStr','joinDeptNameStr')">清空</a>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" >参与培训人员：</td>
		<td class="TableData"  colspan="3">
			<input name="joinPersonIdStr" id="joinPersonIdStr" type="hidden"/>
			<textarea name="joinPersonNameStr" id="joinPersonNameStr" class="SmallStatic BigTextarea"  style="overflow-y: auto;" rows="3" cols="70" wrap="yes" readonly="">
			</textarea>
			<a class="orgAdd" onclick="selectUser(['joinPersonIdStr','joinPersonNameStr'],'1')" href="javascript:void(0);">选择</a>
			&nbsp;&nbsp;<a class="orgClear" onclick="clearData('joinPersonIdStr','joinPersonNameStr')" href="javascript:void(0);">清空</a>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训机构相关信息：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="85" id="institutionInfo" name="institutionInfo" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训机构联系人相关信息：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="85" id="instituContactInfo" name="instituContactInfo" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训要求：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="85" id="planRequires" name="planRequires" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">培训说明：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="85" id="description" name="description" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="85" id="remark" name="remark" maxlength="200" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" >附件文档：</td>
		<td class="TableData"  colspan="3">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" width="" >附件上传：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="valuesHolder" name="valuesHolder" type="hidden" />
		</td>
	</tr>
	<tr>
		<td class="TableData" width="" colspan="4">
			培训内容：<font style='color:red'>*</font><br>
			<DIV>
				<textarea  id="content" name="content" class="BigTextarea" required="true" ></textarea>
			</DIV>
		</td>
	</tr>
	
</table>
</form>

</body>
</html>