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
<%@ include file="/header/validator2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var sid='<%=sid%>';
function doInit(){
	getExamPaper();
	getExamInfo();
}
function commitExamInfo(){
	if($("#form1").valid() && checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeExamInfoController/editExamInfo.action";
		var json = tools.requestJsonRs(url,param);
		/* if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg);
			location.href=contextPath+"/system/core/base/exam/manage/examInfoList.jsp";
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		} */
		return json;
	}
}

function getExamPaper(){
	var url =contextPath+"/TeeExamPaperController/datagrid.action"
	var json = tools.requestJsonRs(url);
	var examPapers = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<examPapers.length;i++){
		html+="<option value=\""+examPapers[i].sid+"\">"+examPapers[i].title+"</option>";
	}
	$("#paperId").html(html);
}

function getExamInfo(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeExamInfoController/getExamInfo.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}
function sendExamInfo(){
	alert();
}

function checkForm(){
	if($("#paperId").val()=="" || $("#paperId").val()==null || $("#paperId").val()==0){
		alert("试卷不能为空空,请选择试卷!");
		return false;
	}
	return true;
}
</script>

</head>
<body onload="doInit();" style="font-size:12px">
<form method="post" name="form1" id="form1"  enctype="multipart/form-data">
	<table class="TableBlock" style="width:100%;font-size:12px;margin:0 auto;" >
		<tr>
			<td class="TableData" style="text-indent:10px;width: 100px">
				考试名称：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="examName" name="examName" style="width:300px;height: 23px;" required/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				参加考试人：
				</td>
			<td  class="TableData">
				<input type='hidden' class="BigInput" id="attendUserIds" name="attendUserIds"/>
				<textarea id="attendUserNames" name="attendUserNames" style="width:400px;height:100px;" class="BigTextarea" readonly></textarea>
				<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['attendUserIds','attendUserNames'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('attendUserIds','attendUserNames')" value="清空"/>
				</span>
				
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				试卷：
				</td>
			<td  class="TableData">
				<select class="BigSelect"  id="paperId" name="paperId" style="height: 23px;width: 300px;" >
				
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				主观题阅卷人：
				</td>
			<td  class="TableData" style="vertical-align: bottom;">
				<input type='hidden' class="BigInput" id="subExaminerIds" name="subExaminerIds"/>
				<textarea id="subExaminerNames" name="subExaminerNames" style="width:400px;height:100px;" class="BigTextarea" readonly></textarea>
				<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['subExaminerIds','subExaminerNames'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('subExaminerIds','subExaminerNames')" value="清空"/>
				</span>
				
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				开始时间：
				</td>
			<td  class="TableData">
				<input type="text" id='startTimeDesc' style="width: 170px;height: 23px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='startTimeDesc' class="Wdate BigInput"/>为空为立即生效<span style="color:red;">(默认当天)</span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				结束时间：
				</td>
			<td  class="TableData">
				<input type="text" id='endTimeDesc' style="width: 170px;height: 23px"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='endTimeDesc' class="Wdate BigInput" />为空为手动终止 
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				查卷推迟天数：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="checkDays" name="checkDays" style="width: 50px;height: 23px" no_negative_number="true"/>天<span style="color:gray;">（注：为0时考试结束后即可查卷，如果有主观题，阅卷完毕后可查卷。）  </span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:10px">
				信息描述：
				</td>
			<td   class="TableData">
				<textarea class="BigTextarea" id="infoDesc" name="infoDesc" style="width:400px;height:100px;"></textarea>
			</td>
		</tr>
		<input type='hidden' id="sid" name="sid" value="<%=sid %>" />
		
	</table>
</form>
</body>
<script>
$("#form1").validate();
</script>
</html>