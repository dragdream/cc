<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
<%
	String date = TeeDateUtil.format(new Date(), "yyyy-MM");
%>
<title>加班审批管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
#form td{
	padding:3px;
}
.duty_item{
	padding:5px;
	text-align:center;
	float:left;
	margin-right:10px;
	cursor:pointer;
	color:white;
	font-weight:bold;
	cursor:pointer;
}
.duty_none_select{
	background:white;
	color:#000;
}
#th td{
border:1px solid gray;
padding:2px;
background:#f0f0f0;
}
#tb td{
border:1px solid gray;
padding:2px;
}
</style>
<script type="text/javascript">
document.onselectstart = function(){return false;};

function  doOnload(){
	renderDuty();
	renderTable();
}

function renderDuty(){
	var json = tools.requestJsonRs(contextPath+"/TeeAttendConfigController/getConfig.action");
	$("#dutys").append("<div sid='-1' onclick=\"clickDuty(this)\" class='duty_item duty_none_select' style='border:1px solid #f0f0f0'>无排班</div>");
	$("#dutys").append("<div sid='0' onclick=\"clickDuty(this)\" class='duty_item duty_none_select' style='border:1px solid #b9c7d2'>休息</div>");
	for(var i=0;i<json.rtData.length;i++){
		$("#dutys").append("<div sid='"+json.rtData[i].sid+"' onclick=\"clickDuty(this)\" id='duty"+json.rtData[i].sid+"' index='"+i+"' class='duty_item duty_none_select dutyitem'>"+json.rtData[i].dutyName+"</div>");
	}
	
	var chlds = $("#dutys .dutyitem");
	for(var i=0;i<chlds.length;i++){
		$(chlds[i]).css({border:"1px solid "+getColor($(chlds[i]).attr("sid"))});
	}
}

var globalDuty = -1;
function clickDuty(obj){
	globalDuty = parseInt($(obj).attr("sid"));
	$("#dutys div").addClass("duty_none_select").css("backgroundColor","#fff");
	$(obj).removeClass("duty_none_select").css({backgroundColor:$(obj).css("borderColor")});
}

function getLastDay(year,month)
{
	 var new_year = year;  //取当前的年份
	 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
	 if(month>12)      //如果当前大于12月，则年份转到下一年
	 {
	 new_month -=12;    //月份减
	 new_year++;      //年份增
	 }
	 var new_date = new Date(new_year,new_month,1);//取当年当月中的第一天
	 return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期
}

function renderTable(){
	var date = $("#date").val();
	var year = parseInt(date.split("-")[0]);
	var month = parseInt(date.split("-")[1]);
	var maxDays = getLastDay(year,month);
	
	var headerStr = "<tr><td style='font-weight:bold;'>姓名</td>";
	for(var i=0;i<maxDays;i++){
		var tmpD = new Date(year,month-1,i+1).getDay();
		var tmpDesc = ["日","一","二","三","四","五","六"];
		headerStr+="<td class='dutyitemtd' onclick='clickColumn("+(i+1)+")' style='font-weight:bold;cursor:pointer;width:25px;text-align:center;height:25px;color:"+((tmpD==0 || tmpD==6)?"red":"")+"'>"+(i+1)+"<br/>"+tmpDesc[tmpD]+"</td>";
	}
	headerStr+="<td>操作</td>";
	headerStr+"</tr>";
	
	$("#th").html(headerStr);
	
	//拼接body
	var bodyStr = "";
	var json = tools.requestJsonRs(contextPath+"/attendDutyTable/getDutyTablesByDate.action",{date:date});
	var datas = json.rtData;
	for(var i=0;i<datas.length;i++){
		bodyStr+="<tr sid='"+datas[i].sid+"'>";
		bodyStr+="<td>"+datas[i].name+"</td>";
		for(var j=0;j<maxDays;j++){
			var val = datas[i].jsonData[(j+1)+""];
			if(val){
				if(val=="0"){
					bodyStr+="<td class='dutyitemtd' sid='"+val+"' onclick='clickItem(this)' style='cursor:pointer;text-align:center;width:25px;;height:25px;background:#b9c7d2;color:white'>休</td>";
				}else if(val==""){
					bodyStr+="<td class='dutyitemtd' sid='"+val+"' onclick='clickItem(this)' style='cursor:pointer;text-align:center;width:25px;;height:25px;color:white'></td>";
				}else{
					bodyStr+="<td class='dutyitemtd' sid='"+val+"' onclick='clickItem(this)' style='cursor:pointer;text-align:center;width:25px;;height:25px;background:"+getColor(val)+";color:white'>"+getFirstText(val)+"</td>";
				}
			}else{
				bodyStr+="<td class='dutyitemtd' sid='' onclick='clickItem(this)' style='cursor:pointer;text-align:center;width:25px;;height:25px;color:white'></td>";
			}
		}
		bodyStr+="<td sid='' style='cursor:pointer;text-align:center;width:25px;;height:25px;color:gray' onclick=\"delItem(this,"+datas[i].sid+")\">移除</td>";
		bodyStr+="</tr>";
	}
	$("#tb").html(bodyStr);
}

function delItem(obj,id){
	if(confirm("确认要移除该项吗？")){
		tools.requestJsonRs(contextPath+"/attendDutyTable/deleteDutyTable.action?id="+id);
		$(obj).parent().remove();
	}
}

function getColor(dutyId){
	var colors = ["red","blue","green","orange","yellow"];
	return colors[parseInt($("#duty"+dutyId).attr("index"))];
}

function getFirstText(dutyId){
	return $("#duty"+dutyId).text().charAt(0);
}

function clickItem(obj){
	if(globalDuty==-1){//无排班
		$(obj).html("");
		$(obj).css("background","none").attr("sid","");
	}else if(globalDuty==0){//休息
		$(obj).html("休");
		$(obj).css("background","#b9c7d2").attr("sid","0");
	}else{
		$(obj).html(getFirstText(globalDuty));
		$(obj).css("background",getColor(globalDuty)).attr("sid",globalDuty);
	}
}

function clickColumn(index){
	var trs = $("#tb tr");
	for(var i=0;i<trs.length;i++){
		var td = $(trs[i]).children()[index];
		clickItem(td);
	}
}

function addPersonFunc(){
	var ids = $("#a").val();
	var json = tools.requestJsonRs("/attendDutyTable/addDutyTable.action",{userIds:ids,date:$("#date").val()});
	renderTable();
	doSave();
}

function doSave(){
	var trs = $("#tb tr");
	var datas = [];
	for(var i=0;i<trs.length;i++){
		var sid = $(trs[i]).attr("sid");
		var dutyitemtd = $(trs[i]).find(".dutyitemtd");
		var jsonData = {};
		for(var j=0;j<dutyitemtd.length;j++){
			jsonData[(j+1)] = $(dutyitemtd[j]).attr("sid");
		}
		datas.push({sid:sid,jsonData:jsonData});
	}
// 	alert(tools.jsonArray2String(datas));
	tools.requestJsonRs(contextPath+"/attendDutyTable/updateDutyTable.action",{datas:tools.jsonArray2String(datas)});
	alert("保存成功");
}
</script>
</head>
<body class="" topmargin="5" onload="doOnload();" style="padding-left: 10px;padding-right: 10px">
	<table id="form" class="none-table">
		<tr>
			<td align="right" class="TableData">
				选择日期：
			</td>
			<td class="TableData">
				<input type="text" value="<%=date %>" id="date" required class="BigInput Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM',onpicked:function(){renderTable()}})" required name="title" style="height:20px;"  readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="TableData">
				签到类型：
			</td>
			<td class="TableData" id="dutys">
				
			</td>
		</tr>
		<tr>
			<td align="right" class="TableData">
				操作：
			</td>
			<td class="TableData" id="dutys">
				<input type="hidden" id="a" />
				<input type="hidden" id="b" />
				<button class="btn-win-white" onclick="selectUser(['a' , 'b'],'','','0','','addPersonFunc')">添加人员</button>
				<button class="btn-win-white" onclick="doSave()">保存</button>
			</td>
		</tr>
	</table>
	<table class="none-table" style="border-collapse:collapse">
		<thead id="th">
			
		</thead>
		<tbody id="tb">
			
		</tbody>
	</table>
</body>
</html>