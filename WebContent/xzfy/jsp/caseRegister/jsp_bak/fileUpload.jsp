<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<body>
	<!-- 这里使用display:none将input标签隐藏 -->
	<form id="uploadForm" enctype="multipart/form-data">
		<input type="file" name="file" id="file" onchange="select_file"
			multiple="multiple"> <input type="button"
			onclick="fileUpload()()" value="上传">
	</form>
</body>
<script type="text/javascript">
var caseId;
var fileName;
var fileSize;
var fileUrl;
/* # 点击button按钮触发input标签 */
function select_file() {
	$("#file").trigger("click");
}

function fileUpload() {
	/* # 创建formdata对象 */
	var formData = new FormData();
	/* # 给formData对象添加<input>标签,注意与input标签的ID一致 */
	formData.append('file', $("#file")[0].files[0]);
	$.ajax({
		url : '/xzfy/common/upload.action',//这里写你的url
		type : 'POST',
		data : formData,
		contentType : false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
		processData : false,// 是否序列化data属性，默认true(注意：false时type必须是post)
		dataType : 'json',//这里是返回类型，一般是json,text等
		clearForm : true,//提交后是否清空表单数据
		success : function(data) { //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
			caseId = $('#caseNum').val();
			var iframew=iframe.contentWindow;
			iframew.getCatename(caseId);
			fileName = data.rtData.name;
			fileSize = data.rtData.size;
			fileUrl = data.rtData.url;
			window.location.href = "/xzfy/jsp/caseRegister/case_material.jsp";
		},
		error : function(data, status, e) { //提交失败自动执行的处理函数。
			console.error(e);
		}
	});
}
</script>
</html>
