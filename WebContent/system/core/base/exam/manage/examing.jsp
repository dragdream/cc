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
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

<script>
var examInfoId="<%=examInfoId%>";
var type="<%=type%>";
var userId = "<%=userId%>";
var SysSecond=1;
var EXAMTIMES=0;

function doInit(){
	getCtrlInfo();
	getQuestionList();
	if(type==1 || type==3){
		getExamInfo();
	}
	if(type==0){
		document.oncontextmenu = function(e){return false};
		document.onkeydown = function (e) {
			var ev = window.event || e;
			var code = ev.keyCode || ev.which;
			if (code == 116) {
			ev.keyCode ? ev.keyCode = 0 : ev.which = 0;
			cancelBubble = true;
			return false;
			}
		}
		if(isSaved(userId,examInfoId)){
			getExamInfo2();
		}
		SysSecond=getStartTimes();
		EXAMTIMES = getExamTimes();
		setInterval('SetRemainTime()',1000);
		setInterval("saveExamData()",30000);
	}
}

function pubFun(str){
	if(str<10){
		return "0"+str;
	}else{
		return str;
	}
	
}
/**
 * 获取试卷信息，并按一定样式显示出来
 */
function getQuestionList(){
	var html="<table class='TableList' width='100%'>";
	if(examInfoId!="" && examInfoId!=null && examInfoId!="null"){
		var url = "<%=contextPath%>/TeeExamDataController/getQuestionList.action?examInfoId="+examInfoId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var data = json.rtData;
			for(var i=0;i<data.length;i++){
				var questionType = data[i].qType;
				if(type==0){
					html+="<tr class='TableHeader'><td align='left' style='word-break:break-all;text-align:left;'>"+(i+1)+".&nbsp;&nbsp;"+data[i].content+"<span style='font-size:12px;'>("+data[i].score+"分)</span></td><td style='width:0px'></td></tr>";
				}else{
					var examData = getExamData(data[i].sid);
					if(questionType=='3'){
						html+="<tr class='TableHeader'><td align='left' style='word-break:break-all;text-align:left;'>"+(i+1)+".&nbsp;&nbsp;"+data[i].content+"<span style='font-size:12px;'>("+data[i].score+"分)</span></td><td style='width:0px'></td></tr>";
					}else{
						if(examData.answer==data[i].answer){
							html+="<tr class='TableHeader'><td align='left' style='word-break:break-all;text-align:left;'>"+(i+1)+".&nbsp;&nbsp;"+data[i].content+"</td><td style='text-align:right;width:90px'><span style='font-size:12px;'>("+data[i].score+"分)</span><img src='<%=contextPath%>/common/images/exam/yes.png'/></td></tr>";
						}else{
							html+="<tr class='TableHeader'><td align='left' style='word-break:break-all;text-align:left;'>"+(i+1)+".&nbsp;&nbsp;"+data[i].content+"</td><td style='text-align:right;width:90px'><span style='font-size:12px;'>("+data[i].score+"分)</span><img src='<%=contextPath%>/common/images/exam/no.png'/></td></tr>";
						}
					}
				}
				html+="<tr><td nowrap='' class='TableData' colspan='2'>";
				if(questionType=='1'){
					if(data[i].optA!="" && data[i].optA!=null && data[i].optA!="null"){
						html+="<input type='radio' name='opt"+data[i].sid+"' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optB!="" && data[i].optB!=null && data[i].optB!="null"){
						html+="<input type='radio' name='opt"+data[i].sid+"' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optB!="" && data[i].optB!=null && data[i].optB!="null"){
						html+="<input type='radio' name='opt"+data[i].sid+"' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optD!="" && data[i].optD!=null && data[i].optD!="null"){
						html+="<input type='radio' name='opt"+data[i].sid+"' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optE!="" && data[i].optE!=null && data[i].optE!="null"){
						html+="<input type='radio' name='opt"+data[i].sid+"' id='opt_E"+data[i].sid+"' value='E'><label for='opt_E"+data[i].sid+"'>"+data[i].optE+"</label><span id='opt_E_"+data[i].sid+"'></span>";
					}
					html+="<br/><div style='display:none;' id='scoreDiv"+data[i].sid+"' name='scoreDiv"+data[i].sid+"'>得分：<span id='score"+data[i].sid+"' name='score"+data[i].sid+"'></span></div>";
					html+="</td></tr>";
				}else if(questionType=='2'){
					if(data[i].optA!="" && data[i].optA!=null && data[i].optA!="null"){
						html+="<input type='checkbox' name='opt_A"+data[i].sid+"' id='opt_A"+data[i].sid+"' value='A'><label for='opt_A"+data[i].sid+"'>"+data[i].optA+"</label><span id='opt_A_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optB!="" && data[i].optB!=null && data[i].optB!="null"){
						html+="<input type='checkbox' name='opt_B"+data[i].sid+"' id='opt_B"+data[i].sid+"' value='B'><label for='opt_B"+data[i].sid+"'>"+data[i].optB+"</label><span id='opt_B_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optB!="" && data[i].optB!=null && data[i].optB!="null"){
						html+="<input type='checkbox' name='opt_C"+data[i].sid+"' id='opt_C"+data[i].sid+"' value='C'><label for='opt_C"+data[i].sid+"'>"+data[i].optC+"</label><span id='opt_C_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optD!="" && data[i].optD!=null && data[i].optD!="null"){
						html+="<input type='checkbox' name='opt_D"+data[i].sid+"' id='opt_D"+data[i].sid+"' value='D'><label for='opt_D"+data[i].sid+"'>"+data[i].optD+"</label><span id='opt_D_"+data[i].sid+"'></span><br>";
					}
					if(data[i].optE!="" && data[i].optE!=null && data[i].optE!="null"){
						html+="<input type='checkbox' name='opt_E"+data[i].sid+"' id='opt_E"+data[i].sid+"' value='D'><label for='opt_E"+data[i].sid+"'>"+data[i].optE+"</label><span id='opt_E_"+data[i].sid+"'></span>";
					}
					html+="<input type='hidden' id='opt"+data[i].sid+"' name='opt"+data[i].sid+"'/>"
					html+="<br/><div style='display:none;' id='scoreDiv"+data[i].sid+"' name='scoreDiv"+data[i].sid+"'>得分：<span id='score"+data[i].sid+"' name='score"+data[i].sid+"'></span></div>"
					html+="</td></tr>";
				}else{
					html+="<textarea id='opt"+data[i].sid+"' name='opt"+data[i].sid+"' class='BigTextarea' style='width:50%;height:100px;'></textarea>";
					html+="<br/><div id='ansDiv_"+data[i].sid+"' style='display:none;'><span style='color:red;font-size:14px;'>得分:</span><input type='text' id='answer"+data[i].sid+"' name='answer"+data[i].sid+"' class='BigInput'/></div>"
					+"<br/><div style='display:none;' id='scoreDiv"+data[i].sid+"' name='scoreDiv"+data[i].sid+"'>得分：<span id='score"+data[i].sid+"' name='score"+data[i].sid+"'></span></div>"
					+"</td></tr>";
				}
			}
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
		html+="</table>";
	}
	$("#container").html(html);
}
/**
 * 页面左侧，考试时间及操作显示
 */
function getCtrlInfo(){
	var html="";
	if(examInfoId!="" && examInfoId!=null && examInfoId!="null"){
		var url = "<%=contextPath%>/TeeExamInfoController/getExamInfo.action?sid="+examInfoId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var data = json.rtData;
			if(type==0){
				html+="开始时间："
				+""+getStartExamTime()+""
				+"&nbsp;&nbsp;结束时间："
				+""+getEndExamTime()+""
				+"&nbsp;&nbsp;考试计时："
				+"<span id='doingTime' name='doingTime'></span>&nbsp;&nbsp;";
			}
			//+"<tr class=\"TableHeader\" align=\"center\"><td>第 <span style=\"color:red;\">1</span> 页，共 <span style=\"color:red;\">1</span> 页</td></tr>"
			//+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"第一页↑\"></td></tr>"
			//+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"前一页↑\"></td></tr>"
			//+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"下一页↓\"></td></tr>"
			//+"<tr align=\"center\"><td><input type=\"button\" class=\"SmallInput\" value=\"末一页↓\"></td></tr>";
			if(type==1){
				html+="最终得分："+getRealScore()+"&nbsp;&nbsp;";
			}
			if(type==0){
				html+="<input type=\"button\" class=\"btn-win-white\" value=\"交卷\" onclick=\"commit();\">&nbsp;";
			}else if(type==1){
				html+="<input type=\"button\" class=\"btn-win-white\" value=\"返回\" onclick=\"history.go(-1);\">&nbsp;";
			}else{
				html+="<input type=\"button\" class=\"btn-win-white\" value=\"阅卷完毕\" onclick=\"submitCheck();\">&nbsp;";
				html+="<input type=\"button\" class=\"btn-win-white\" value=\"返回\" onclick=\"history.go(-1);\">&nbsp;";
			}
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
	html+="";
	$("#tableDiv").html(html);
}
/**
 * 交卷
 */
function commit(){
	$.MsgBox.Confirm ("提示", "确认交卷吗？交卷以后不能再修改", function(){
		saveCheckValue();
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeExamDataController/addExamData.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg);
			location.href=contextPath+"/system/core/base/exam/manage/myExam.jsp";
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	  });

}
/**
 * 阅卷
 */
function submitCheck(){
	$.MsgBox.Confirm ("提示", "确认提交阅卷信息吗？", function(){
		var url = "<%=contextPath%>/TeeExamDataController/getQuestionList.action?examInfoId="+examInfoId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var data = json.rtData;
			for(var i=0;i<data.length;i++){
				var questionType = data[i].qType;
				var answer=getExamData(data[i].sid).answer;
				if(questionType=='3'){
					if(!updateExamData(data[i].sid)){
						return ;
					}
				}
			}
			updateExamRecord();
			history.go(-1);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	  });
}

function updateExamData(questionId){
	//var param =null;
	var score=$("#answer"+questionId).attr("value");
/* 	param["userId"]=userId;
	param["examInfoId"]=examInfoId;
	param["questionId"]=questionId;
	param["score"]=score; */
	var url = contextPath+"/TeeExamDataController/updateExamData.action?userId="+userId+"&examInfoId="+examInfoId+"&questionId="+questionId+"&score="+score;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		return true;
	}else{
		$("#answer"+questionId).focus();
		$.MsgBox.Alert_auto(json.rtMsg);
		return false;
	}
}
/**
 * 跟新当前考试人的试卷为已阅
 */
function updateExamRecord(){
	var url = contextPath+"/TeeExamDataController/updateExamRecord.action?userId="+userId+"&examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		return true;
	}else{
		return false;
	}
}
/**
 * 获取当前考生答题情况
 */
function getExamData(questionId){
	var url =contextPath+"/TeeExamDataController/getExamData.action?userId="+userId+"&examInfoId="+examInfoId+"&questionId="+questionId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}
/**
 * 获取当前考生实际得分
 */
function getRealScore(){
	var url =contextPath+"/TeeExamDataController/getRealScore.action?userId="+userId+"&examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.realScore;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}


/**
 * 获取当前考试的考试时长
 */
function getExamTimes(){
	var url =contextPath+"/TeeExamDataController/getExamTimes.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.examTimes;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

/**
 * 获取已经考试了多长时间
 */
function getStartTimes(){
	var url =contextPath+"/TeeExamDataController/getStartTimes.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.startTimes;
	}else{
	   $.MsgBox.Alert_auto(json.rtMsg);
	}
}

function SetRemainTime() { 
	  SysSecond = SysSecond + 1; 
	  var second = Math.floor(SysSecond % 60);             // 计算秒     
	  var minite = Math.floor((SysSecond / 60) % 60);      //计算分 
	  var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时 
	  var day = Math.floor((SysSecond / 3600) / 24);  
	  if(EXAMTIMES-minite<=5){
		  $.MsgBox.Alert_auto("考试剩余五分钟，请尽快交卷");
	  }
	  if(SysSecond-(EXAMTIMES*60)>=0){//时间到，自动提交考试
			saveCheckValue();
			var param = tools.formToJson($("#form1"));
			var url = contextPath+"/TeeExamDataController/addExamData.action";
			var json = tools.requestJsonRs(url,param);
			if(json.rtState){
				 $.MsgBox.Alert_auto(json.rtMsg);
				location.href=contextPath+"/system/core/base/exam/manage/myExam.jsp";
			}else{
				 $.MsgBox.Alert_auto(json.rtMsg);
			}
	  }
	  $("#doingTime")[0].innerHTML="&nbsp;"+ (pubFun(day) + ":" + pubFun(hour) + ":" + pubFun(minite) + ":" + pubFun(second) + ""); 
}
function getStartExamTime(){
	var url =contextPath+"/TeeExamDataController/getStartExamTime.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.startExamTime;
	}else{
		 $.MsgBox.Alert_auto(json.rtMsg);
	}
}

function getEndExamTime(){
	var url =contextPath+"/TeeExamDataController/getStartExamTime.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.endExamTime;
	}else{
		 $.MsgBox.Alert_auto(json.rtMsg);
	}
}
/**
 * 查卷时调用，主要是查出考试者的答案及答案对错，以及页面显示效果的改变
 */
function getExamInfo(){
	var url = "<%=contextPath%>/TeeExamDataController/getQuestionList.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		for(var i=0;i<data.length;i++){
			var questionType = data[i].qType;
			$("#scoreDiv"+data[i].sid).css("display","block");
			var answer=getExamData(data[i].sid).answer;
			if(questionType=='1'){
				$("#opt_A"+data[i].sid).attr("disabled",true);
				$("#opt_B"+data[i].sid).attr("disabled",true);
				$("#opt_C"+data[i].sid).attr("disabled",true);
				$("#opt_D"+data[i].sid).attr("disabled",true);
				$("#opt_E"+data[i].sid).attr("disabled",true);
				if(answer=='A'){
					$("#opt_A"+data[i].sid).attr("checked",true);
				}
				if(answer=='B'){
					$("#opt_B"+data[i].sid).attr("checked",true);
				}
				if(answer=='C'){
					$("#opt_C"+data[i].sid).attr("checked",true);
				}
				if(answer=='D'){
					$("#opt_D"+data[i].sid).attr("checked",true);
				}
				if(answer=='E'){
					$("#opt_E"+data[i].sid).attr("checked",true);
				}
				if(answer==data[i].answer){
					$("#score"+data[i].sid)[0].innerHTML=data[i].score;
				}else{
					$("#score"+data[i].sid)[0].innerHTML=0+"<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正确答案为："+data[i].answer+"</b>";
				}
			}else if(questionType=='2'){
				$("#opt_A"+data[i].sid).attr("disabled",true);
				$("#opt_B"+data[i].sid).attr("disabled",true);
				$("#opt_C"+data[i].sid).attr("disabled",true);
				$("#opt_D"+data[i].sid).attr("disabled",true);
				$("#opt_E"+data[i].sid).attr("disabled",true);
				if(answer.indexOf("A")>=0){
					$("#opt_A"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("B")>=0){
					$("#opt_B"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("C")>=0){
					$("#opt_C"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("D")>=0){
					$("#opt_D"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("E")>=0){
					$("#opt_E"+data[i].sid).attr("checked",true);
				}
				$("#opt"+data[i].sid).val(answer);
				if(answer==data[i].answer){
					$("#score"+data[i].sid)[0].innerHTML=data[i].score;
				}else{
					$("#score"+data[i].sid)[0].innerHTML=0+"<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正确答案为："+data[i].answer+"</b>";
				}
			}else{
				$("#opt"+data[i].sid).val(answer);
				$("#opt"+data[i].sid).attr("disabled",true);
				if(type==3){
					$("#ansDiv_"+data[i].sid).css("display","block");
					$("#scoreDiv"+data[i].sid).css("display","none");
					$("#answer"+data[i].sid).val(getExamData(data[i].sid).score);
				}
				if(type==1){
					$("#score"+data[i].sid)[0].innerHTML=getExamData(data[i].sid).score;
				}
			}
		}
	}
}

/**
 * 交卷时调用，主要是针对多选题型，设置其答案
 */
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
			}
		}
	}
}



/**
 * 当其没有正常交卷时，获取已经答过的题的情况
 */
function getExamInfo2(){
	var url = "<%=contextPath%>/TeeExamDataController/getQuestionList.action?examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		for(var i=0;i<data.length;i++){
			var questionType = data[i].qType;
			var answer=getExamData(data[i].sid).answer;
			if(questionType=='1'){
				if(answer=='A'){
					$("#opt_A"+data[i].sid).attr("checked",true);
				}
				if(answer=='B'){
					$("#opt_B"+data[i].sid).attr("checked",true);
				}
				if(answer=='C'){
					$("#opt_C"+data[i].sid).attr("checked",true);
				}
				if(answer=='D'){
					$("#opt_D"+data[i].sid).attr("checked",true);
				}
				if(answer=='E'){
					$("#opt_E"+data[i].sid).attr("checked",true);
				}
			}else if(questionType=='2'){
				if(answer.indexOf("A")>=0){
					$("#opt_A"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("B")>=0){
					$("#opt_B"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("C")>=0){
					$("#opt_C"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("D")>=0){
					$("#opt_D"+data[i].sid).attr("checked",true);
				}
				if(answer.indexOf("E")>=0){
					$("#opt_E"+data[i].sid).attr("checked",true);
				}
				$("#opt"+data[i].sid).val(answer);
			}else{
				$("#opt"+data[i].sid).val(answer);
			}
		}
	}
}

/*
 * 保存信息，主要用于定时的保存答题信息
 */
function saveExamData(){
	saveCheckValue();
	var param = tools.formToJson($("#form1"));
	var url = contextPath+"/TeeExamDataController/saveExamData.action";
	var json = tools.requestJsonRs(url,param);
	if(json.rtState){
		//top.$.jBox.tip(json.rtMsg,"info");
		//location.href=contextPath+"/system/core/base/exam/manage/myExam.jsp";
	}else{
	    $.MsgBox.Alert_auto(json.rtMsg);
	}
}


function isSaved(userId,sid){
	var flag=0;
	var url =contextPath+"/TeeExamDataController/isSaved.action?userId="+userId+"&sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		flag=json.rtData.isSaved;
	}else{
		 $.MsgBox.Alert_auto(json.rtMsg);
	}
	return flag;
}
</script>

</head>
<body onload="doInit()"  style="padding-left: 10px;padding-right: 10px;overflow: hidden">

<div class="fl" style="position:absolute;left:5px;right:5px;top:5px;height:60px;">
	<img id="img1" class = 'title_img' src="/system/subsys/footprint/img/icon_zujichaxun.png">
	<span class="title">
		<%
			if(type.equals("0")){
		%>
			参加考试
		<%
			}else{
		%>
			查看考试结果
		<%
			}
		%>
	</span>
	<div class = "right fr clearfix" style="">
	    <div id="tableDiv" style="text-align:right">
	    </div>
	</div>
</div>


<div layout="center" style="overflow:auto;position:absolute;top:50px;left:10px;right:10px;bottom:10px">
	<form id="form1" name="form1">
		<div id="container" style="">
		
		</div>
		<input type="hidden" id="examInfoId" name="examInfoId" value="<%=examInfoId %>"/>
	</form>
</div>
</body>
</html>

