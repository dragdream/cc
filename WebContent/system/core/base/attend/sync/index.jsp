<%@page import="java.io.DataOutputStream"%>
<%@page import="com.tianee.oa.core.base.attend.tcp.AttendsSync"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>考勤同步</title>
<script>
//初始化
function doInit(){
	getCurrentMonth();
}
//本月
function getCurrentMonth(){
	  var now = new Date();                    //当前日期
	  var nowMonth = now.getMonth();           //当前月
	  var nowYear = now.getYear();             //当前年
	  nowYear += (nowYear < 2000) ? 1900 : 0;  //
	
	//获得本月的开始日期
    var MonthStartDate = new Date(nowYear, nowMonth, 1);
    var MonthStartDate =  formatDate(MonthStartDate);

    //获得本月的结束日期
    var MonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear,nowMonth));
    var MonthEndDate =  formatDate(MonthEndDate);

    $("#beginTime").val(MonthStartDate);
    $("#endTime").val(MonthEndDate);
}

//上个月
function getPreMonth(){
	  var now = new Date();                    //当前日期
	  var nowYear = now.getYear();             //当前年
	  nowYear += (nowYear < 2000) ? 1900 : 0;  //

	  var lastMonthDate = new Date();  //上月日期
	  lastMonthDate.setDate(1);
	  lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
	  var lastMonth = lastMonthDate.getMonth();
	
	  //获得上月开始时间
	  var LastMonthStartDate = new Date(nowYear, lastMonth, 1);
	  var LastMonthStartDate = formatDate(LastMonthStartDate);

	  //获得上月结束时间
	  var LastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(nowYear,lastMonth));
	  var LastMonthEndDate = formatDate(LastMonthEndDate);

      $("#beginTime").val(LastMonthStartDate);
      $("#endTime").val(LastMonthEndDate);
}

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
}

//获得某月的天数
function getMonthDays(nowYear,myMonth){
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var   days   =   (monthEndDate   -   monthStartDate)/(1000   *   60   *   60   *   24);
    return   days;
}

//验证
function check(){
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	if(beginTime==""){
		$.MsgBox.Alert_auto("请选择开始时间！");
		return false;
	}
	if(endTime==""){
		$.MsgBox.Alert_auto("请选择结束时间！");
		return false;
	}
	return true;
}
//开始同步
function sync(){
	 $.MsgBox.Confirm ("提示", "是否确认进行签到数据同步？", function(){
		 if(check()){
				var url=contextPath+"/TeeAttendDutyController/attendSync.action";
				var param=tools.formToJson("#form1");
				var json=tools.requestJsonRs(url,param);
				if(json.rtState){
					$.MsgBox.Alert_auto("同步成功！");
				}else{
					$.MsgBox.Alert_auto("同步失败！");
				}
			} 
	  });
	
}
	
	
	
</script>
</head>
<body  onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/grkq/icon_kqtb.png">&nbsp;&nbsp;
		<span class="title">考勤同步
			<%
				DataOutputStream out1 = null;
				try{
					out1 = new DataOutputStream(AttendsSync.socket.getOutputStream()); 
					out1.writeUTF("TEST");
					%>
					<span style="color:green">（已连接）</span>
					<%
				}catch(Exception ex){
					%>
					<span style="color:red">（未连接）</span>
					<%
				}finally{
					
				}
			%>
		</span>
	</div>
	
	<div class="fr" style="position:static;">
		<button class="btn-win-white" onclick="sync()">开始同步</button>
	</div>
</div>

<div class="base_layout_center" style="padding:10px">
<form id="form1" name="form1">

<table class="TableBlock_page" style="width:100%;">
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			开始时间：
		</td>
		<td class="TableData">
			<input style="width:300px;" type="text" name="beginTime" id="beginTime" size="20" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput">
		</td>
	</tr>
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			结束时间：
		</td>
		<td class="TableData">
			<input style="width:300px;" type="text" name="endTime" id="endTime" size="20" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" class="Wdate BigInput">
		</td>
	</tr>
	<!-- <tr>
		<td colspan="2">
				<button class="btn-win-white" onclick="">同步</button>
		</td>
	</tr> -->
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			快速选择：
		</td>
		<td class="TableData">
			<input type="button" value="本月" class="btn-win-white" id="currentMonth" onclick="getCurrentMonth()"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="上个月" class="btn-win-white" id="preMonth" onclick="getPreMonth()"/>
		</td>
	</tr>
	<tr>
		<td class="TableHeader" colspan="2" style="width:100px;text-indent: 15px;">
			系统已集成部分中控考勤机的数据同步接口（带LAN口连接的考勤机，例如中控U160系列），具体配置规则可查看配置文档&nbsp;&nbsp;&nbsp;&nbsp;<a href="考勤机同步配置说明.docx">下载说明文档</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://pan.baidu.com/s/1b9jqay" target="_blank">下载同步程序</a></br>
		</td>
	</tr>
</table>
</form>	
</div>
</body>

</html>