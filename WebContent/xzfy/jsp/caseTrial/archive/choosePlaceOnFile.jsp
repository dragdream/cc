<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String caseInfoId=request.getParameter("id");//案件id
	String type=request.getParameter("type");//上传方式  
	String cfType=request.getParameter("num");//归档类型
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/cmstpls/2/js/script.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<title>Insert title here</title>
<script>
var file='';
var type=<%=type%>;//案件id
var cfType=<%=cfType%>;//上传方式  
var caseInfoId='<%=caseInfoId%>';//归档类型
var fileId="";//文件id
var path=[];//返回 文件名称 文件id串
$(function(){
	if(type==1){
		$("#datagrid").show();
		$("#add").hide();
	}else{
		$("#datagrid").remove();
		$("#add").show();
	}
	getSysCodeByParentCodeNo('FY_DATE' , 'select_deadline');
	getSysCodeByParentCodeNo('FY_SECURITY' , 'select_security');
})

var fid = 1;
function fileUpload(){
	//累加tr的编号
	fid++; 
	var uploadHtml = $("#fileUpload1").html(); 
	// 套上tr标签，定义唯一id
	uploadHtml = "<tr id='fileUpload"+fid+"' >"+uploadHtml+"</tr>";
	//用正则替换name值，并赋予唯一name
	uploadHtml = uploadHtml.replace(/security1/g, "security"+fid);
	uploadHtml = uploadHtml.replace(/deadline1/g, "deadline"+fid);
	uploadHtml = uploadHtml.replace(/fileType1/g, "fileType"+fid);
	uploadHtml = uploadHtml.replace(/uploadFile1/g, "uploadFile"+fid);
	uploadHtml = uploadHtml.replace(/fileId1/g, "fileId"+fid);
	uploadHtml = uploadHtml.replace(/uuid1/g, "uuid"+fid);
	// 追加到table中 
	$("#upload").append(uploadHtml);
	// 回显fileId fid
	$("#fid").val(fid);
	$("#uploadFile"+fid).removeAttr("title");
	$("#fileId"+fid).attr("fileId","");
	$("#fileUpload"+fid).removeAttr("display");
	$("#fileUpload"+fid).find("select").removeAttr("display");
	//document.getElementById("fileUpload"+fid).removeAttribute("disabled");
	$("#uploadFile"+fid).removeAttr("readonly");
	//$("#uploadFile"+fid).css("border","none");
	document.getElementById("uploadFile"+fid+"").type="file"; 
}

// 删除文件tr行
function removeFileUpload(obj){
	var id = $(obj).parent().parent().attr('id');
	if(id == "fileUpload1"){
		$("#fileUpload1").css("display","none");  
		$("#fileUpload1").find("select[type=file]").val("");
		$("#fileUpload1").find("select").val("");
		var fileId = $(obj).attr("fileId");
		var para = {'fileId':fileId};
	    var url = "<%=contextPath%>/wjxxController/deleteById.action";
	    tools.requestJsonRs(url,para);
		return;
	}
	if(delFile(obj)){
		$(obj).parent().parent().remove();
	}
}

//文件删除方法
function delFile(obj){
	var fileId = $(obj).attr("fileId");
	if(fileId != ""){
		 if(confirm("是否删除这个文件")){
			var para = {'fileId':fileId};
		    var url = "<%=contextPath%>/wjxxController/deleteById.action";
		    tools.requestJsonRs(url,para);
			return true;
		 }
		 return false;
	}else{
		return true;
	}
}

function download(obj){
	var url = $(obj).attr("fileUrl");
}

//回显案件
$(function() {
	var para = {'caseId':caseInfoId};
	var url = "<%=contextPath%>/register/getFileInfo.action";//getFileInfo
	datagrid = $('#datagrid').datagrid({
		url : '<%=contextPath%>/register/getFileInfo.action' ,
		toolbar : '#toolbar',
		queryParams:para,
		singleSelect : false,
		checkbox : true,
		pagination : true,
		fit : true,
		//fitColumns : true,
		idField : 'uuid',
		columns : [ [
			{
				field : 'uuid',
				checkbox : true,
				title : 'ID',
				width:30,
				align:'center'
			},
  		 /* {
				field : 'security',
				title : '密级',
				width : 170,
				align:'center',
		 },
		  {
			field : 'deadline',
			title : '期限',
			align:'center',
			width : 120	 
		  } , */
			{
			field : 'fileType',
			title : '文件类型',
			align:'center',
			width : 240
		  }, 
		  {
			field : 'fileName',
			title : '文件名称',
			align:'center',
			width : 450
		   },
	] ]
});
}); 

var selection =null;
//验证是否选中案件
function isSelected(){
	selection = datagrid.datagrid("getSelected");
	
	if(selection==null){
		alert("请选择至少一条案件");
		return ;
	}
	return true;
}

//获取文件名称
function fileName(obj){
	
	var explorer = window.navigator.userAgent.toLowerCase() ;
	//ie
		if (explorer.indexOf("msie") >= 0) {
		path =$(obj).val();
	}
	//firefox
	else if (explorer.indexOf("firefox") >= 0) {
		path =$(obj).val();
	}
	//Chrome
	else {
		var path11 =$(obj).val();
		var pos1 = path11.lastIndexOf('/');
		var pos2 = path11.lastIndexOf('\\');
		var pos = Math.max(pos1, pos2);
		path=path.substring(pos+1);
	}
	 
	return path;
} 
//选择文件
function choose(obj){
	var result=[];
	var rows = datagrid.datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		var map={};//创建map集合
		map['id']=rows[i].uuid;
	    map['name']=rows[i].fileName;
	    result.push(map);
	}
	return result; 
}

//添加文件
function addFile(obj){
	var num=false;
	//上传文件
	   $("#form1").ajaxSubmit({
	        type: 'post', // 提交方式 get/post
	        async:false,
	        url: "<%=contextPath%>/XZFYFileCaseController/addFile.action?caseId="+caseInfoId, // 需要提交的 url
	        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	        	
	        	var json = eval("("+data+")"); 
	        	console.log(json.rtData[0].fileName);
	        	fileId=json.rtData;
	        	num=true;
	        }
	    }); 
	//
	
	/*
	//兼容浏览器
	var map={};//创建map集合
	var fileId="1231233213123123,312312312312312";
	var arr=fileId.split(",");
	for(var i=0;i<arr.length;i++){
		map['id']=arr[i];
		var explorer = window.navigator.userAgent.toLowerCase() ;
		//ie
			if (explorer.indexOf("msie") >= 0) {
			map['name']=$("#uploadFile"+(i+1)).val();
		}
		//firefox
		else if (explorer.indexOf("firefox") >= 0) {
			map['name']=$("#uploadFile"+(i+1)).val();
		}
		//Chrome
		else {
			var path11 =$("#uploadFile"+(i+1)).val();
			alert(path11);
			alert((i+1));
			var pos1 = path11.lastIndexOf('/');
			var pos2 = path11.lastIndexOf('\\');
			var pos = Math.max(pos1, pos2);
		    map['name']=path11.substring(pos+1);
		    
		}
			path.push(map);
	} */
	
	return fileId;
}

//获取
function changg(obj){
	$('#testSelect option:selected').text();//选中的文本
}

//保存文件
function saveFile(){
	var num=false;
	  $("#form1").ajaxSubmit({
	        type: 'post', // 提交方式 get/post
	        async:false,
	        url: "<%=contextPath%>/XZFYFileCaseController/addFile.action?caseId="+caseInfoId, // 需要提交的 url
	        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	        	fileId=data.rtData;
	        	num=true;
	        }
	    }); 
	 return true;
}
</script>
</head>
<body>
	<div id="add">
		<form action=""  method="post" name="form1" id="form1">
			<table id="upload" align="center" width="100%" class="TableBlock">
			<!-- 文件计数的fid -->
			<input type="hidden" name="fid" id="fid" value="1"/>
			<!-- caseId -->
				<tr>
					<!-- <td class="TableData">密级</td>
					<td class="TableData">期限</td> -->
					<td class="TableData">文件类型</td>
					<td class="TableData">文件名称</td>
					<td class="TableData"><a class="glyphicon glyphicon-plus" style="color:green;" href="javascript:void(0);" onclick="fileUpload()" ></a></td>
				</tr>
				<tr id="fileUpload1">
				    <select class="BigSelect" name="security1Code" id="select_security" style="float: left; width: 150px;display:none;" onchange="changg(this);" >
							
					</select> 
					
					<input name="security1" id="security1" type="hidden" value="非密"/>
				    <select class="BigSelect" name="deadline1Code" id="select_deadline" style="float: left; width: 150px;display:none;" onchange="changg(this);"  style="display:none">
							
					</select> 
					<input name="deadline1" id="deadline1" type="hidden" value="永久"/> 
						<!-- <select class="BigSelect" name="fileType1Code" id="select_file_type" style="float: left; width: 150px;" onchange="changg(this);">
							<option value="04">听证材料</option>
					</select>  --> 
					<td class="TableData" width="40%">
					<input name="fileType1Code" class="easyui-validatebox BigInput" value="100" id="fileType1Code" type="hidden" />
					<input name="fileType1" class="easyui-validatebox BigInput" value="归档材料" id="fileType1" type="text" onchange="changg(this);" />
					
					</td>
					<td class="TableData" width="40%">
					
						<input type="file" style="width: 300px"  name="uploadFile1" id="uploadFile1" onclick="download(this)" /><!-- //onchange="saveFile();" -->
						<!-- 文件信息表 uuid -->
						<input type="hidden" id="uuid1" name="uuid1"/>
					</td> 
					<td class="TableData" width="5%">
					    <a id="fileId1" class="glyphicon glyphicon-minus" style="color:red;" href="javascript:void(0);" onclick="removeFileUpload(this)" ></a>
					 </td>
				</tr>
			</table>
		</form>
	</div>
	
	<table class="TableBlock" id="datagrid"></table>
</body>
</html>