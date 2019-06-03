<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String examInfoId = request.getParameter("examInfoId");
	String type = request.getParameter("type");
	String userId = request.getParameter("userId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

<script>
var examInfoId="<%=examInfoId%>";
var type="<%=type%>";
var userId = "<%=userId%>";
function doInit(){
	$("#layout").layout({auto:true});
	getCtrlInfo();
	getQuestionList();
}

function getQuestionList(){
	var html="<table class='TableList' width='100%''>";
	if(examInfoId!="" && examInfoId!=null && examInfoId!="null"){
		var url = "<%=contextPath%>/TeeExamDataController/getQuestionList.action?examInfoId="+examInfoId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var data = json.rtData;
			for(var i=0;i<data.length;i++){
				var questionType = data[i].qType;
				if(type==0){
					if(questionType=='1'){
						html+="<tr class='TableHeader'><td align='left' style='word-break:break-all'>"+(i+1)+"."+data[i].content+"</td></tr>";
						html+="<tr><td nowrap='' class='TableData'>"
						+"<input type='radio' name='opt"+data[i].sid+"' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>"
						+"<input type='radio' name='opt"+data[i].sid+"' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>"
						+"<input type='radio' name='opt"+data[i].sid+"' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>"
						+"<input type='radio' name='opt"+data[i].sid+"' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span><br>"
						+"<input type='radio' name='opt"+data[i].sid+"' id='opt_E"+data[i].sid+"' value='E'><label for='opt_E"+data[i].sid+"'>"+data[i].optE+"</label><span id='opt_E_"+data[i].sid+"'></span>"
						+"</td></tr>";
					}else if(questionType=='2'){
						html+="<tr class='TableHeader'><td align='left' style='word-break:break-all'>"+(i+1)+"."+data[i].content+"</td></tr>";
						html+="<tr><td nowrap='' class='TableData'>"
						+"<input type='checkbox' name='opt_A"+data[i].sid+"' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>"
						+"<input type='checkbox' name='opt_B"+data[i].sid+"' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>"
						+"<input type='checkbox' name='opt_C"+data[i].sid+"' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>"
						+"<input type='checkbox' name='opt_D"+data[i].sid+"' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span><br>"
						+"<input type='checkbox' name='opt_E"+data[i].sid+"' id='opt_E"+data[i].sid+"' value='D'><label for='opt_E"+data[i].sid+"'>"+data[i].optE+"</label><span id='opt_E_"+data[i].sid+"'></span>"
						+"<input type='hidden' id='opt"+data[i].sid+"' name='opt"+data[i].sid+"'/>"
						+"</td></tr>";
					}else{
						html+="<tr class='TableHeader'><td align='left' style='word-break:break-all'>"+(i+1)+"."+data[i].content+"</td></tr>";
						html+="<tr><td nowrap='' class='TableData'>"
						+"<textarea id='opt'"+data[i].sid+"' name='opt"+data[i].sid+"' class='BigTextarea' style='width:50%;height:100px;'></textarea>"
						+"</td></tr>";
					}
				}else{
					var examData = getExamData(data[i].sid);
					if(questionType=='1'){
						if(examData.answer==data[i].answer){
							html+="<tr class='TableHeader'><td align='left' style='word-break:break-all'>"+(i+1)+"."+data[i].content+"<img src='<%=contextPath%>/common/images/exam/yes.png'/></td></tr>";
						}else{
							html+="<tr class='TableHeader'><td align='left' style='word-break:break-all'>"+(i+1)+"."+data[i].content+"<img src='<%=contextPath%>/common/images/exam/no.png'/></td></tr>";
						}
						if(examData.answer=="A"){
							html+="<tr><td nowrap='' class='TableData'>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_A"+data[i].sid+"' checked='checked' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span>"
								+"</td></tr>";
						}
						if(examData.answer=="B"){
							html+="<tr><td nowrap='' class='TableData'>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_B"+data[i].sid+"' checked='checked' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span>"
								+"</td></tr>";
						}
						if(examData.answer=="C"){
							html+="<tr><td nowrap='' class='TableData'>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_C"+data[i].sid+"' checked='checked' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span>"
								+"</td></tr>";
						}
						if(examData.answer=="D"){
							html+="<tr><td nowrap='' class='TableData'>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>"
								+"<input type='radio' name='opt"+data[i].sid+"' disabled='' id='opt_D"+data[i].sid+"' checked='checked'  value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span>"
								+"</td></tr>";
						}
					}else if(questionType=='2'){
						
					}else{
						
					}
						
					
				}
			}
		}else{
			alert(json.rtMsg);
		}
		html+="</table>";
	}
	$("#container").html(html);
}

function getCtrlInfo(){
	var html="<table border=\"0\" cellspacing=\"1\" width=\"100%\" class=\"small\" cellpadding=\"3\" align=\"center\"><tbody id=\"tbody\">";
	if(examInfoId!="" && examInfoId!=null && examInfoId!="null"){
		var url = "<%=contextPath%>/TeeExamInfoController/getExamInfo.action?sid="+examInfoId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var data = json.rtData;
			html+="<tr class=\"TableHeader\" align=\"center\"><td>开始时间</td></tr>"
			+"<tr align=\"center\"><td style=\"color:red;\">"+data.startTimeDesc+"</td></tr>"
			+"<tr class=\"TableHeader\" align=\"center\"><td>结束时间</td></tr><tr align=\"center\">"
			+"<td style=\"color:red;\">"+data.endTimeDesc+"<input type=\"hidden\" id=\"endTime\" name=\"endTime\" value=\"2013-12-07 10:37:48\"></td></tr>"
			+"<tr class=\"TableHeader\" align=\"center\"><td>第 <span style=\"color:red;\">1</span> 页，共 <span style=\"color:red;\">1</span> 页</td></tr>"
			+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"第一页↑\"></td></tr>"
			+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"前一页↑\"></td></tr>"
			+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"下一页↓\"></td></tr>"
			+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"末一页↓\"></td></tr>";
			if(type==0){
				html+="<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"交    卷 \" onclick=\"commit();\"></td></tr>";
			}else{
				html+="<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"返   回  \" onclick=\"history.go(-1);\"></td></tr>";
			}
			
		}else{
			alert(json.rtMsg);
		}
	}
	html+="</tbody></table>";
	$("#tableDiv").html(html);
}


function commit(){
	top.$.jBox.confirm("确认交卷吗？交卷以后不能再修改","确认",function(v){
		if(v=="ok"){
			saveCheckValue();
			var param = tools.formToJson($("#form1"));
			var url = contextPath+"/TeeExamDataController/addExamData.action";
			var json = tools.requestJsonRs(url,param);
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"info");
				location.href=contextPath+"/system/core/base/exam/manage/myExam.jsp";
			}else{
				top.$.jBox.tip(json.rtMsg,"error");
			}
		}
	});
}


function getExamData(questionId){
	var url =contextPath+"/TeeExamDataController/getExamData.action?userId="+userId+"&examInfoId="+examInfoId+"&questionId="+questionId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data;
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
}

function saveCheckValue(){
	var url = "<%=contextPath%>/TeeExamDataController/getQuestionList.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		for(var i=0;i<data.length;i++){
			var questionType = data[i].qType;
			var answer="";
			if(questionType=='2'){
				if($("#opt_A"+data[i].sid).attr("checked")){
					answer+="A";
				}
				if($("#opt_B"+data[i].sid).attr("checked")){
					answer+="B";
				}
				if($("#opt_C"+data[i].sid).attr("checked")){
					answer+="C";
				}
				if($("#opt_D"+data[i].sid).attr("checked")){
					answer+="D";
				}
				if($("#opt_E"+data[i].sid).attr("checked")){
					answer+="E";
				}
				$("#opt"+data[i].sid).val(answer);
				alert(data[i].sid);
				alert($("#opt"+data[i].sid)[0].value);
				//$("#opt"+data[i].sid)[0].value=answer;
			}
		}
	}
}
</script>

</head>
<body onload="doInit()"  style="margin:0px;overflow:hidden">
<div id="layout">
	<div layout="west" width="180">
		<div id="tableDiv" style='margin-top:50px;'>
		</div>
	</div>
	<div layout="center" style="padding-left:10px;overflow:auto;">
		<form id="form1" name="form1">
			<div style="margin-top:50px;">
			<%
				if(type.equals("0")){
					
			%>
				<img src="<%=contextPath %>/common/images/exam/add.png"/>&nbsp;&nbsp;&nbsp;&nbsp;<span><b>参加考试</b></span>
			<%
				}else{
			%>
				<img src="<%=contextPath %>/common/images/exam/add.png"/>&nbsp;&nbsp;&nbsp;&nbsp;<span><b>查看考试结果</b></span>
			<%
				}
			%>
			</div>
			<div id="container" style="margin-bottom:20px;">
			
			</div>
			<input type="hidden" id="examInfoId" name="examInfoId" value="<%=examInfoId %>"/>
		</form>
	</div>
</div>
</body>
</html>

