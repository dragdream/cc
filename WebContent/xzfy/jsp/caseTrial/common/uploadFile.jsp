<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
	String fileTypeCode = request.getParameter("fileTypeCode");
	String fileType = request.getParameter("fileType");
	String id = request.getParameter("id");//调查管理自身id
	String caseId = request.getParameter("caseId");//所挂案件Id
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>
<script type="text/javascript" src="<%=contextPath%>/beidasoft/caseReport/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<title>文件上传</title>
<script type="text/javascript">
var fileTypeCode = <%= fileTypeCode%>;
var fileType = "<%= fileType%>";
$(function() {
	$("#fileTypeCode").val(fileTypeCode);
	$("#fileType").val(fileType);
});

//文件上传
function fileUpload() {
	var result;
	var option = {
		url:"/caseFileController/add.action",//上传的url
		dataType:"text",//回调值的数据类型
		async:false,
		success:function(responseText){
			result = responseText;//json数组
			return;
		},
		error:function(){
			alert("错误,请联系管理员!");
		}
	}
	//使用ajax方式提交表单，上传文件
	$("#ajaxForm").ajaxSubmit(option);
	return result;
}
</script>
</head>
<body style="overflow:hidden;font-size:16px">
		<form action="/caseFileController/add.action" enctype="multipart/form-data" id="ajaxForm" method="post">
			<table id="table_report" class="TableBlock">
				<input type="hidden" name="id" id="id" value="<%= id%>">
				<input type="hidden" name="caseId" id="caseId" value="<%= caseId%>">
				<tr>
					<td align='center' class="TableData">案件材料：</td>
					<td>
						<input type="file" name="file" id="file" />
						<input type="hidden" name="fileTypeCode" id="fileTypeCode" />
						<input type="hidden" name="fileType" id="fileType" />
						
					</td>
				</tr>
			</table>
		</form>
</body>
</html>