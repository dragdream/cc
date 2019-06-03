<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"), 0);
    int reportId=TeeStringUtil.getInteger(request.getParameter("reportId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>上传/编辑报表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript">
var parentId="<%=parentId%>"; //父文件夹主键
var reportId="<%=reportId%>";//当前报表的主键
//初始化方法
function  doInit(){
	//获取文件夹详情
	if(reportId>0){
		getFolderInfoBySid(reportId);
	}else{
		$("#resetRaq").hide();//隐藏重新上传的按钮
		$("#resetPara").hide();//隐藏重新上传的按钮
	}
	
}

//获取文件夹详情
function getFolderInfoBySid(reportId){
	var url =  contextPath+"/quieeReportController/getBySid.action";
	var para = {sid:reportId};
	var jsonObj = tools.requestJsonRs(url,para);
	bindJsonObj2Cntrl(jsonObj.rtData);
	
	if(jsonObj.rtData.isExistRaqFile==1){//报表文件已经存在
		$("#raqFile").hide();//隐藏上传的框
	}else{
		$("#resetRaq").hide();//隐藏重新上传的按钮
	}
	
	if(jsonObj.rtData.isExistRaqParamFile==1){//参数文件已经存在
		$("#paraFile").hide();//隐藏上传的框
	}else{
		$("#resetPara").hide();//隐藏重新上传的按钮
	}
	
	
	
	//点击两个重新上传事件
	$("#resetRaq").bind("click",function(){
		$("#resetRaq").hide();
		$("#raqFile").show();
	});
    $("#resetPara").bind("click",function(){
    	 $("#resetPara").hide();
    	 $("#paraFile").show();
	});
}

//保存
function doSaveOrUpdate(callback){
	if(check()){
		if(reportId>0){//编辑文件		
			var sortNo=$("#sortNo").val();
			var reportName=$("#reportName").val();
			var raqFile=$("#raqFile").val();
			var paraFile=$("#paraFile").val();
			var url =  contextPath+"/quieeReportController/editReport.action?sid="+reportId+"&reportType=2&sortNo="+sortNo+"&reportName="+reportName+"&raqFile="+raqFile+"&paraFile="+paraFile;
			$("#form1").ajaxSubmit({
	            type: 'post', // 提交方式 get/post
	            dataType: 'json', // 提交方式 get/post
	            url: url, // 需要提交的 url
	            data: {},
	            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	                // 此处可对 data 作相关处理
	                callback(data);
	            }
	        });	
		}else{
			var sortNo=$("#sortNo").val();
			var reportName=$("#reportName").val();
			var raqFile=$("#raqFile").val();
			var paraFile=$("#paraFile").val();
			var url =  contextPath+"/quieeReportController/addReport.action?parentId="+parentId+"&sortNo="+sortNo+"&reportName="+reportName+"&raqFile="+raqFile+"&paraFile="+paraFile;
			
			$("#form1").ajaxSubmit({
	            type: 'post', // 提交方式 get/post
	            dataType: 'json', // 提交方式 get/post
	            url: url, // 需要提交的 url
	            data: {},
	            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	                // 此处可对 data 作相关处理
	                callback(data);
	            }
	        });
		}
	}	
}


//检验文件夹名称不能为空
function check(){
	var reportName=$("#reportName").val();
	var sortNo=$("#sortNo").val();
	var reg = /^(0|[1-9]\d*)$/;//非负整数
	if(reportName==null||reportName==""){
		alert("请填写报表名称！");
		return false;
	}
	if(!reg.test(sortNo)){
		alert("排序号格式错误，应为非负整数！");
		return false;
	}
	    return true;
}


</script>

</head>
<body onload="doInit();">
<form id="form1" name="form1" method="post" enctype="multipart/form-data" target="frame">
	<table class="TableBlock" width="100%" align="center" style="margin-top:10px;">
		<tr>
			<td nowrap class="TableData" width="100">报表名称：</td>
			<td class="TableData">
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="text" name="reportName" id="reportName" class="BigInput" style="width: 300px"/>		
			</td>	
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">排序号：</td>
			<td class="TableData">
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="text" name="sortNo" id="sortNo" class="BigInput" style="width: 300px"/>		
			</td>	
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">模板文件：</td>
			<td class="TableData">
			
			   <input style="width: 310px;margin-left: 43px;" type="file" name="raqFile" id="raqFile" />
			   <button style="margin-left: 43px;" type='button' name="resetRaq" id="resetRaq" class='btn btn-default' value="重新上传">重新上传</button>
			</td>	
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">参数模板：</td>
			<td class="TableData">			
			   <input style="width: 310px;margin-left: 43px;" type="file" name="paraFile" id="paraFile" />
	           <button style="margin-left: 43px;" type='button'  name="resetPara" id="resetPara" class='btn btn-default' value="重新上传">重新上传</button>		
			</td>	
		</tr>
		
		
	</table>
	
</form>
</body>

</html>