<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@page import="com.tianee.oa.core.org.service.TeePersonService" %>
<%
	int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);

   //获取当前登录的用户名
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   String userId=loginUser.getUserId();
   //判断当前的用户是不是系统管理员
   boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, userId);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<link  href="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<style type="text/css">
	.title{
		positon:relative;
		height:26px;
		line-height:26px;
		margin:5px 0;
		display:block;
		padding:0 5px;
		background:#f0f0f0;
		color:black;
		font-size:12px;
		text-align:center;
	}
	.columnStyle{
		min-height:450px;
		 width: 100%;
		float:left;
		border:1px solid #CCCCCC;
		margin:0 10px;
	}
	
	 #sortable, #sortable1, #sortable2,#sortable3,#sortable4 {
	    border: 1px solid #eee;
	    min-height: 450px;
	    list-style-type: none;
	    margin: 0;
	    padding: 5px 0 0 0;
	    float: left;
	    margin-right: 10px;
	     width: 470px;
	  }
	  #sortable li,#sortable1 li, #sortable2 li ,#sortable3 li,#sortable4 li{
	    margin: 0 5px 5px 5px;
	    padding: 5px;
	    font-size:12px;
	    width: 100%;
	    cursor:move;
	  }
</style>
	<script>
	var contextPath = "<%=contextPath%>";
	var formId = <%=formId%>;
	var isAdmin=<%=isAdmin%>;
	function doInit(){
		//首先隐藏部门选择和删除的超链接
		$("#selDept").hide();
		$("#clearDept").hide();
		
		initSort();
		//initFieldList();

		var form = getFlowForm();
		if(!form){
			alert("无表单数据");
			return ;
		}

		$("#formName").attr("value",form.formName);
		$("#sortId")[0].value = form.formSortId;
		$("#deptName").attr("value",form.deptName);
		$("#deptId").attr("value",form.deptId);
		
		
		//判断是否是超级管理员  如果是超级管理员    显示选择和清空超链接
		if(isAdmin){
			$("#selDept").show();
			$("#clearDept").show();
		}
	}

	//初始化分类
	function initSort(){
		var url = contextPath+"/formSort/getSortList.action";
		var json = tools.requestJsonRs(url,{});
		var html = "<option value='0'>默认分类</option>";

		//获取流程所属分类
		if(json.rtState){
			var datas = json.rtData;
			for(var i=0;i<datas.length;i++){
				html+=("<option value='"+datas[i].sid+"'>"+datas[i].sortName+"</option>");
			}
			$("#sortId").html(html);
		}
	}
	
	function initFieldList(){
		var url = contextPath+"/flowForm/getFormItemsByForm.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		var data = json.rtData;
		var html = "<span class='title'>移动端表单字段排序设置</span><ul id='sortable' class=\"connectedSortable\">";
		for(var i=0;i<data.length;i++){
			html+="<li class='ui-state-default' id='"+data[i].sid+"'>"+data[i].title+"</li>";
		}
		html+="</ul>";
		$("#fieldListDiv").html(html);

		$("#sortable").sortable({
		      connectWith: ".connectedSortable"
		}).disableSelection();
		
	}

	//获取表单实体
	function getFlowForm(){
		var url = contextPath+"/flowForm/getPlain.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			return json.rtData;
		}else{
			return undefined;
		}
	}

	function commit(){
		if(!$("#form").form("validate")){
			return;
		}
		
		//组合字段顺序
		var sortFieldModel = [];
		
		$("#fieldListDiv li").each(function(i,obj){
			sortFieldModel.push({id:obj.getAttribute("id"),sort:i});
		});
		
		var url = contextPath+"/flowForm/updateItemsSortNo.action";
		var json = tools.requestJsonRs(url,{sortFieldModel:tools.jsonArray2String(sortFieldModel)});
		
		
		var url = contextPath+"/flowForm/updateFormPlain.action";
		var para = tools.formToJson($("#form"));
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			$.jBox.tip(json.rtMsg,"success");
			parent.document.getElementById("left").contentWindow.location.reload();
		}else{
			$.jBox.tip(json.rtMsg,"error");
		}
	}

	function formDesign(){
		var formId = getLatestVersionFormId();
		openFullWindow(contextPath+"/system/core/workflow/formdesign/index.jsp?formId="+formId,"表单设计器");
	}

	function mobileFormDesign(){
		var formId = getLatestVersionFormId();
		openFullWindow(contextPath+"/system/core/workflow/flowform/design/design.jsp?formId="+formId,"移动端表单设计");
	}
	
	function formPrintExplore(){
		var formId = getLatestVersionFormId();
		openFullWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId,"表单预览");
	}

	function exportForm(){
		var formId = getLatestVersionFormId();
		$("#frame0").attr("src",contextPath+"/flowForm/export.action?formId="+formId);
	}

	function getLatestVersionFormId(){
		var url = contextPath+"/flowForm/getLatestVersionFormId.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			return json.rtData;
		}
		return 0;
	}

	function versions(){
		window.location="versions.jsp?formId="+formId;
	}

	function importForm(){
		$("#fileBtn").click();
	}

	function doImport(obj){
		if(document.getElementById("file").value.indexOf(".html")==-1){
			alert("仅能上传html后缀名模板文件！");
			return false;
		}
		return true;
	}
	
	function uploadSuccess(){
		alert("导入表单成功！");
		window.location.reload();
	}
	
	</script>
</head>
<body onload="doInit()">
<div class="base_layout_top">
	 &nbsp;
	<input type="button" class="btn btn-success" value="保存" onclick="commit()" />
	<input type="button" class="btn btn-default" value="表单设计器" onclick="formDesign()" />
	<input type="button" class="btn btn-default" value="预览表单" onclick="formPrintExplore()" />
	<input type="button" class="btn btn-default" value="历史版本" onclick="versions()" />
	<input type="button" class="btn btn-default" value="导入" onclick="$('#uploadDiv').modal('show')" />
	<input type="button" class="btn btn-default" value="导出" onclick="exportForm()" />
	<input type="button" class="btn btn-default" value="移动端表单设计" onclick="mobileFormDesign()" />
</div>
<div class="base_layout_center">
<br/>
<form id="form">
<table class="TableBlock" width="500px" align="center">
   <tr>
    <td class="TableData TableBG" width="120"><font color="red">*</font>表单名称：</td>
    <td class="TableData">
        <input type="text" style="width:300px" id="formName" name="formName" class="BigInput easyui-validatebox" required="true"/>
    </td>
   </tr>
  <tr id="deptTr">
    <td nowrap class="TableData TableBG">&nbsp;所属部门：</td>
    <td nowrap class="TableData" >
    	<input type="hidden" name="deptId" id="deptId"/>
		<input  name="deptName" id="deptName" class="readonly BigInput" readonly />
		<a  id="selDept" href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
		&nbsp;
		<a id="clearDept" href="javascript:void(0)" onclick="clearData('deptId','deptName')">清除</a>
    </td>
   </tr>

   
   
   
   <tr>
    <td class="TableData TableBG"><font color="red">*</font>所属分类：</td>
    <td class="TableData">
    	<select id="sortId" name="sortId" class="BigSelect"></select>
    </td>
   </tr>
</table>
<input type="hidden" name="formId" value="<%=formId %>" />
</form>

<br/>

<div id="fieldListDiv" style="margin:auto;width:500px">
	
</div>

</div>
<!-- 下载专用 -->
<iframe id="frame0" style="display:none"></iframe>

<form id="uploadForm" onsubmit="return doImport()" target="frame" action="<%=contextPath %>/flowForm/importForm.action?formId=<%=formId %>" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal fade" id="uploadDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">表单导入</h4>
      </div>
      <div class="modal-body">
        <!-- 导入专用 -->
        	<span style="color:red">请上传HTML格式的文件</span><br/><br/>
			<input type="file" name="file" id="file"/>
			<iframe id="frame" name="frame" style="display:none" src="" ></iframe>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" id="uploadBtn" >上传</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

</body>
</html>