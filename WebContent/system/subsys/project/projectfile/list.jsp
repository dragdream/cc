<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
   int diskId=TeeStringUtil.getInteger(request.getParameter("diskId"),0);
   String diskName=TeeStringUtil.getString(request.getParameter("diskName"));
   String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页面</title>
</head>
<script>
var diskId=<%=diskId%>;
var diskName="<%=diskName%>";
var projectId="<%=projectId%>";
var datagrid;
//初始化方法
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectFileController/getFileListByDiskId.action?diskId="+diskId+"&&projectId="+projectId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'fileName',title:'文件名',width:200,formatter:function(value,rowData,rowIndex){
			   return "<span class=\"fileItem\" fileName="+rowData.fileName+"   fileExt="+rowData.fileExt+" attchId="+rowData.attchId+"></span>";
			}},
			{field:'createrName',title:'上传者',width:100},
			{field:'createTimeStr',title:'创建时间',width:100}
		]],onLoadSuccess:onLoadSuccessFunc
	});
}


/**
 * 获取最大记录数
 */
function onLoadSuccessFunc(data){
    //处理悬浮菜单
    $(".fileItem").each(function(i,obj){
      var attachModel = {fileName:$(obj).attr("fileName"),
          priv:1+2
          ,ext:$(obj).attr("fileExt"),sid:$(obj).attr("attchId")};
      var fileItem = tools.getAttachElement(attachModel,{});
      $(obj).append(fileItem);
    });
}

</script>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
   <div class="fl" style="position:static;">
		<span class="title"><%=diskName%></span>
	</div>
</div>
<table fit="true" id="datagrid"></table>
</body>
</html>