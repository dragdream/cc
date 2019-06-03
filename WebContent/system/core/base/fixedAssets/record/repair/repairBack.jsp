<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	int fixedAssetsId = TeeStringUtil.getInteger(request.getParameter("fixedAssetsId"), 0);//固定资产Id
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp"%>
<%@include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@include file="/header/upload.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>

<script>
var fixedAssetsId = "<%=fixedAssetsId%>";
var deptName;
function doInit(){
    getAssetsInfo();
    
    getDeptParent();
    
    new TeeSimpleUpload({
	 	fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
		showUploadBtn:false,
		form:"form1",
		post_params:{model:"assets"}//后台传入值，model为模块标志
	});
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
function getAssetsInfo(){
	if(fixedAssetsId!="" && fixedAssetsId!=null && fixedAssetsId!="null"){
		var url = "<%=contextPath%>/TeeFixedAssetsInfoController/getAssetsInfo.action?sid="+fixedAssetsId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			deptName=json.rtData.deptName;	
		}else{
			alert(json.rtMsg);
		}
	}
}

/**
 * 新建或者更新
 */
function doSaveOrUpdate(callback){
	if(checkFrom()){
		var para =  tools.formToJson($("#form1")) ;
		$("#form1").doUpload({
			url:"<%=contextPath%>/teeFixedAssetsRecordController/addOrUpdate.action",
			success:function(json){
				callback(json);
				parent.BSWINDOW.modal("hide");
			},
			post_params:para
		});

	}
	return false;
}

/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").form('validate'); 
	 if(!check  ){
		 return false; 
	 }
	 return true;
}
</script>

</head>
<body onload="doInit();" style="font-size:12px">
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock" style="width:800px;font-size:12px;margin:0 auto;" >
	
		<tr>
			<td class="TableData" nowrap="nowrap">
				<b>资产编号：</b>
				</td>
			<td  class="TableData">
				<div  id="assetCode" ></div>
			</td>
		
			<td class="TableData">
				<b>资产名称：</b>
				</td>
			<td colspan="3" class="TableData">
				<div  id="assetName" ></div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>所属部门：</b>
				</td>
			<td  class="TableData">
				<div id="deptName"></div>
			</td>
			<td class="TableData">
				<b>保管人：</b>
			</td>
			<td class="TableData" style="vertical-align: bottom;">
				<div id="keeperName"></div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>报修返库部门：</b>
				</td>
			<td  class="TableData">
				<input id="deptId" name="deptId"  type="text" style="display:none;"/>
				<ul id="deptIdZTree" class="ztree" style="margin-top:0; width:247px; display:none;"></ul>
				
				<span style="color:red;">(*)</span>
			</td>
			<td class="TableData">
				<b>报修返库人员：</b>
			</td>
			<td class="TableData" style="vertical-align: bottom;">
				<%if(isSuperAdmin){ %>
				<input type='hidden' class="BigInput" id="userId" name="userId" value="userSid"/>
				<input type="text" id="userName" name="userName" style="background:#f0f0f0;" class="BigInput" readonly value="<%=userName %>"/>
				<a href="javascript:void(0)" onclick="selectSingleUser(['userId','userName'])">选择</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="clearData('userId','userName')">清除</a>
				
				<span style="color:red;">(*)</span>
				<%}else{ %>
				    <%=userName%>
				<%} %>
			</td>
		</tr>
		
		<tr>
			<td class="TableData">
				<b>维修结果：</b>
				</td>
			<td colspan="3" class="TableData">
				<textarea required="true" class="easyui-validatebox BigTextarea " id="optReason" name="optReason" style="width:500px;height:50px;"></textarea>
				<span style="color:red;">(*)</span>
			</td>
		</tr>
		
		<tr>
			<td class="TableData">
				<b>备注：</b>
				</td>
			<td colspan="3" class="TableData">
				<textarea class="BigTextarea" id="optRemark" name="optRemark" style="width:500px;height:50px;"></textarea>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>维修凭证：</b>
				</td>
			<td colspan="3" class="TableData">
				<div id ='attachs'></div>
				<div id="fileContainer"></div> <a id="uploadHolder"class="add_swfupload">附件上传</a> 
				<input id="valuesHolder" type="hidden" />
			</td>
		</tr>
	</table>
	
	
	<input type="hidden" name="fixedAssetsId" value="<%=fixedAssetsId %>">
	<input type="hidden" name="optType" value="3">
</form>
</body>
</html>