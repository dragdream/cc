<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
	td{
		line-height:36px;
		min-height:36px;
	}

</style>

<script>
function commit(callback){
	var para =  tools.formToJson($("#form1")) ;
	var  url = "<%=contextPath %>/humanDocController/importPersonInfo.action";
	var flag=false;
	if(checkFileExt()){
		 $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 	 	if(res.rtState){
			 			$.jBox.tip("导入成功", 'info');
			 			flag= true;
			 		}else{
			 			alert(res.rtMsg);
			 			flag= false;
			 		} 
			 		callback(res);
	            },
	           error: function(arg1, arg2, ex) {
	                alert("导入错误");
	                flag= false;
	          }, 
	          dataType: 'json'}); 
	          
	}
}


function downLoadTemplate(){
	var  url = "<%=contextPath %>/humanDocController/downLoadTemplate.action";
    document.form1.action=url;
    document.form1.submit();
    return true;
}


function checkFileExt(){
	var filepath = document.getElementById("excelFile").value; 
	//为了避免转义反斜杠出问题，这里将对其进行转换
	var re = /(\\+)/g; 
	var filename=filepath.replace(re,"#");
	//对路径字符串进行剪切截取
	var one=filename.split("#");
	//获取数组中最后一个，即文件名
	var two=one[one.length-1];
	//再对文件名进行截取，以取得后缀名
	var three=two.split(".");
	 //获取截取的最后一个字符串，即为后缀名
	var last=three[three.length-1];
	//添加需要判断的后缀名类型
	var tp ="xls,xlsx";
	//返回符合条件的后缀名在字符串中的位置
	var rs=tp.indexOf(last);
	//如果返回的结果大于或等于0，说明包含允许上传的文件类型
	if($("#excelFile").val()==""){
		alert("导入文件为空，请选择文件！");
		return false;
	}
	if(rs>=0){
	 return true;
	 }else{
	 alert("您选择的上传文件格式不对，文件必须是excel文件");
	 return false;
	 }
}
</script>

</head>
<body style="font-size:12px">
<form id="form1" name="form1" method="post" enctype="multipart/form-data" >
	<div id="tip" style="margin-top:10px;">导入的表格必须是Excel2003或Excel2007格式&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='downLoadTemplate()' style='text-decoration: underline;'>点击下载模板</a></div>
	<div>
		<input id="excelFile" name="excelFile" type="file" style="width:260px;margin-top:5px;" size="30" class="BigInput" title="选excel文件">
		
	</div>
</form>
</body>
</html>