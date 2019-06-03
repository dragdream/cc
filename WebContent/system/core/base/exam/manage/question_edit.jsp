<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
     	String sid = request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var sid = "<%=sid%>";
function doInit(){
	getExamStore();
    new TeeSimpleUpload({
	 	fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
		showUploadBtn:false,
		form:"form1",
		post_params:{model:"exam"}//后台传入值，model为模块标志
	});
	getQuestionInfo();
}
function commit(){
	if(checkForm()){
		var para =  tools.formToJson($("#form1")) ;
		para["model"] = "exam";
		$("#form1").doUpload({
				url:"<%=contextPath%>/TeeExamQuestionController/editExamQuestion.action",
				success:function(json){
					$.MsgBox.Alert_auto(json.rtMsg);
					parent.datagrid.datagrid('reload');
					//关闭bsWindow
					parent.$("#win_ico").click();
					
				},
				post_params:para
		});
	}
}

function getExamStore(){
	var url =contextPath+"/TeeExamStoreController/datagrid.action"
	var json = tools.requestJsonRs(url);
	var examStores = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<examStores.length;i++){
		html+="<option value=\""+examStores[i].sid+"\">"+examStores[i].storeName+"</option>";
	}
	$("#storeId").html(html);
}

function getQuestionInfo(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeExamQuestionController/getExamQuestion.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			var attaches = json.rtData.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachs").append(fileItem);
			}
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

function checkForm(){
	if($("#storeId").val()=="" || $("#storeId").val()==null || $("#storeId").val()==0){
		$.MsgBox.Alert_auto("题库不能为空,请选择所属题库!");
		return false;
	}
	
	if($("#content").val()==""||$("#content").val()==null){
		$.MsgBox.Alert_auto("请填写题目！");
		return false;
	}
	
	if($("#score").val()==""||$("#score").val()==null){
		$.MsgBox.Alert_auto("请填写分数！");
		return false;
	}
	return true;
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock" style="width:100%;font-size:12px;margin:0 auto;" >
		<tr>
			<td class="TableData" style="text-indent:10px;width: 100px">
				所属题库：
				</td>
			<td  class="TableData">
				<select class="BigSelect"  id="storeId" name="storeId" style="width: 300px;height: 23px" >
				
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				题型：
				</td>
			<td  class="TableData">
				<select class="BigSelect"  id="qType" name="qType" style="width: 300px;height: 23px" >
					<option value="1">单选</option>
					<option value="2">多选</option>
					<option value="3">主观</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				难度：
				</td>
			<td  class="TableData">
				<select class="BigSelect"  id="qHard" name="qHard" style="width: 300px;height: 23px">
					<option value="1">低</option>
					<option value="2">中</option>
					<option value="3">高</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				题目：
				</td>
			<td  class="TableData">
				<textarea class="BigTextarea"  id="content" name="content" style='width:400px;height:60px;' required="true"></textarea>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				分数：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="score" name="score" style="width:300px;height: 23px" required="true"  validType='integeBetweenLength[1,1000]'/>(注：要求录入整数) 
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				附件文档：
				</td>
			<td  class="TableData">
				<div id ='attachs'></div>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				附件上传：
				</td>
			<td  class="TableData">
			<div id="fileContainer"></div> <a id="uploadHolder"class="add_swfupload">附件上传</a> 
			<input id="valuesHolder" type="hidden" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				备选答案A：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="optA" name="optA" style="width:300px;height: 23px"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				备选答案B：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="optB" name="optB" style="width:300px;height: 23px"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				备选答案C：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="optC" name="optC" style="width:300px;height: 23px"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				备选答案D：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="optD" name="optD" style="width:300px;height: 23px"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				备选答案E：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="optE" name="optE" style="width:300px;height: 23px"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				正确答案：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="answer" name="answer" style="width:300px;height: 23px"/><span style="color:red;">注：单个答案直接输入字母，如A；多个答案连续输入字母，如ABC。 </span>
			</td>
		</tr>

				<input type='hidden' class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	</table>
</form>
</body>
</html>