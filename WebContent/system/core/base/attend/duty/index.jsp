<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	border-bottom:1px solid #f2f2f2;
	font-weight:bold;
	border-left:3px solid #6ba6fe;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}
</style>
<script>
var date = new Date();
function doInit(){
	getTimesTips();
	getServerTime();
	setTimes();
	var url = contextPath+"/TeeAttendConfigController/getAttendConfigByUserAndDate.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		if(data==null){
			messageMsg("当前用户没有设置排班类型，请联系管理员进行设置", "container" ,'info' ,300);
			return;
		}else{
			if(data.sid==0){//休息
				messageMsg("当前用户今天为休息日，无需签到！", "container" ,'info' ,300);
				return;
			}else{
				var html = "<table  align='center' width='90%'><tr class='TableHeader'><td>登记次序</td><td>登记类型</td><td>规定时间</td><td>登记时间</td><td>操作</td></tr>";
				if(data.dutyTimeDesc1!=null && data.dutyTimeDesc1!="" && data.dutyTimeDesc1!="null"){
					html+="<tr  align=''><td>第1次登记</td><td>上班登记</td><td>"+data.dutyTimeDesc1.substring(0,data.dutyTimeDesc1.length-3)+"</td><td>"+getRegisterTime(data.dutyTimeDesc1,1)+"</td><td>"+opt(data.dutyTimeDesc1,1)+"</td></tr>";
				}
				if(data.dutyTimeDesc2!=null && data.dutyTimeDesc2!="" && data.dutyTimeDesc2!="null"){
					html+="<tr  align=''><td>第2次登记</td><td>下班登记</td><td>"+data.dutyTimeDesc2.substring(0,data.dutyTimeDesc2.length-3)+"</td><td>"+getRegisterTime(data.dutyTimeDesc2,2)+"</td><td>"+opt(data.dutyTimeDesc2,2)+"</td></tr>";
				}
				if(data.dutyTimeDesc3!=null && data.dutyTimeDesc3!="" && data.dutyTimeDesc3!="null"){
					html+="<tr  align=''><td>第3次登记</td><td>上班登记</td><td>"+data.dutyTimeDesc3.substring(0,data.dutyTimeDesc3.length-3)+"</td><td>"+getRegisterTime(data.dutyTimeDesc3,3)+"</td><td>"+opt(data.dutyTimeDesc3,3)+"</td></tr>";
				}
				if(data.dutyTimeDesc4!=null && data.dutyTimeDesc4!="" && data.dutyTimeDesc4!="null"){
					html+="<tr  align=''><td>第4次登记</td><td>下班登记</td><td>"+data.dutyTimeDesc4.substring(0,data.dutyTimeDesc4.length-3)+"</td><td>"+getRegisterTime(data.dutyTimeDesc4,4)+"</td><td>"+opt(data.dutyTimeDesc4,4)+"</td></tr>";
				}
				if(data.dutyTimeDesc5!=null && data.dutyTimeDesc5!="" && data.dutyTimeDesc5!="null"){
					html+="<tr  align=''><td>第5次登记</td><td>上班登记</td><td>"+data.dutyTimeDesc5.substring(0,data.dutyTimeDesc5.length-3)+"</td><td>"+getRegisterTime(data.dutyTimeDesc5,5)+"</td><td>"+opt(data.dutyTimeDesc5,5)+"</td></tr>";
				}
				if(data.dutyTimeDesc6!=null && data.dutyTimeDesc6!="" && data.dutyTimeDesc6!="null"){
					html+="<tr  align=''><td>第6次登记</td><td>下班登记</td><td>"+data.dutyTimeDesc6.substring(0,data.dutyTimeDesc6.length-3)+"</td><td>"+getRegisterTime(data.dutyTimeDesc6,6)+"</td><td>"+opt(data.dutyTimeDesc6,6)+"</td></tr>";
				}
				html+="</table>";
				$("#container").html(html);
				$("#dutyName")[0].innerHTML=data.dutyName;	
			}
	
		}
				
	}else{
		$.MsgBox.Alert_auto(json.rtMsg); 
	}
}

/**
 * 判断当前时间是否可以登记 -1:不在登记时间内 ； 0:在登记时间内，1：当前用户属于免签人，2：当前日期为免签节日；3：当前日期为公休日
 */
function opt(registerTime,on){
	var url = contextPath+"/TeeAttendConfigController/isRegister.action?registerTime="+registerTime+"&on="+on;
	
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var flag = json.rtData;
		if(flag==0){
			return "<a href='javascript:void(0)' onclick=\"register('"+registerTime+"',"+on+")\">登记</a>";
		}else if(flag==1){
			return "<span>免签</span>";
		}else if(flag==2){
			return "<span>免签日</span>";
		}else if(flag==3){
			return "<span>公休日</span>";
		}else if(flag==4){
			return "<span>已登记</span>";
		}
	}
	return "<span>不在登记时间内</span>";
}


function register(registerTime,on){
	 
	//alert(registerTime);
	
	var url = contextPath+"/TeeAttendDutyController/addDuty.action?on="+on+"&registerTime="+registerTime;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		$.MsgBox.Alert_auto(json.rtMsg);
		location.reload();
		
		
	}else{
		$.MsgBox.Alert_auto(json.rtMsg); 
	}
}


function getRegisterTime(registerTime,on){
	var url = contextPath+"/TeeAttendConfigController/getRegisterTime.action?registerTime="+registerTime+"&on="+on;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var flag = json.rtData;
		if(flag!="" && flag !=null && flag!="null"){
			return "<a href='javascript:void(0)' onclick=''>"+flag.substring(10,flag.length)+"</a>";
		}
	}
	return "<span>未登记</span>";
}


/**
 * 获取登记时间段提示
 */
function getTimesTips(){
	var url = contextPath+"/TeeAttendConfigController/getAttendTimes.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		html="规定时间之前 "+json.rtData.workonBeforeMin+" 分钟到之后 "+json.rtData.workonAfterMin+"分钟这段时间可进行上班登记，规定时间之前 "+json.rtData.workoutBeforeMin+" 分钟到之后 "+json.rtData.workoutAfterMin+" 分钟这段时间可进行下班登记";
		$("#timesTips").html(html);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg); 
	}
}


function getServerTime(){
	var url = contextPath+"/TeeAttendConfigController/getServerTime.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		date.setTime(json.rtData);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg); 
	}
}




//将时间减去1秒，计算天、时、分、秒 
function setTimes(){ 
	 window.setTimeout("setTimes()", 1000);
	 timestr=date.toLocaleString();
	 date.setSeconds(date.getSeconds()+1);
	 $("#header")[0].innerHTML=timestr;
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;">
<div class="time_info" style="margin-left:5px">
         上下班登记 
   <span id="dutyName" name="dutyName" style='margin-left:30px;'></span>
   <span style='margin-left:15px;'>当前时间：</span>
   <span id="header" name="header" style=''></span>
</div>
<div id="toolbar" class = "clearfix">
  <form action="">
    <div class="info setHeight">
      <img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/tip.png" alt="" /><span id="timesTips"></span>
    </div> 
    <div id="container">		
    </div>
  </form>
</div>

</body>
</html>