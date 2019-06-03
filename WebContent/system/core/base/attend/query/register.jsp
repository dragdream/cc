<%@ page language="java"  import="java.util.*,java.text.SimpleDateFormat" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String deptIds=request.getParameter("deptIds");
String startDateDesc=request.getParameter("startDateDesc");
String endDateDesc=request.getParameter("endDateDesc");
String countMonth=request.getParameter("countMonth");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.diaryTable {
	border-collapse: collapse;
}

.diaryTable td {iu
	border: 1px solid #e2e2e2;
	font-size: 14px;
	padding: 5px;
}
.TableList tr{
	border:1px solid #f2f2f2;
}
.TableList tr td{
	border:1px solid #f2f2f2;
}
#container{
	width:98%;
	height:90%;
	overflow-x: auto;
}
</style>
<script>
var dayNum;

function doInit(){
	var startDateDesc = "<%=startDateDesc %>";
	var endDateDesc = "<%=endDateDesc%>";
	
	var countMonth="<%=countMonth %>";
	
	dayNum = getDays("<%=startDateDesc%>","<%=endDateDesc%>");
	$("#dayNum").val(dayNum);
	
	//document.getElementById("dayNum").value = dayNum;
	var d = new Date();
	var year = startDateDesc.substring(0,4);//年
	var month = startDateDesc.substring(5,7);//月
	var date = startDateDesc.substring(8,10);//日
	d.setFullYear(year,month-1,date);
    
	var endYear = endDateDesc.substring(0,4);
  	var endMonth = endDateDesc.substring(5,7);
	var url = "<%=contextPath %>/TeeAttendDutyRecordController/getRegisterRecordInfo.action?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>&countMonth=<%=countMonth %>";
    $.MsgBox.Alert_auto("正在加载……");
	var jsonObj = tools.requestJsonRs(url);
	tools.requestJsonRs(url,{},true,function(json){
		if(json.rtState){
			var prcs = json.rtData;
			$("#container").empty();
			if(prcs.length > 0){
				var tableStr = "<table id='register' class='TableList' width='98%' style='border:1px solid #f2f2f2;border-collapse:collapse;' align='center'><tbody id='listTbody'>";
			    	 tableStr = tableStr + "<tr class='TableHeader'>"
					      	 + "<td nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >部门</td>"
					      	 + "<td nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >姓名</td>"
					      	 + "<td nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >全勤天</td>"
					      	 + "<td nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >上班时长</td>"
					      	 + "<td nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >上班未登记</td>"
					      	 + "<td nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >迟到</td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >下班未登记 </td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >早退</td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >申诉次数</td>"
					    	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >请假天数</td>"
					    	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >外出天数</td>"
					    	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >出差天数</td>"
					    	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >加班时长</td>"
					    	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >外勤天数</td>"
					    	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' align='center' >外勤次数</td>"
					      	// +"<td nowrap nowrap style='text-align:left;text-indent:20px;height:25px;padding:0 10px;width:100px;' align='center' colspan="+dayNum+">日历</td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:0 10px;width:100px;' width=''  align='center' >操作</td>"
				         +"</tr>";
				       

				var registerNum2 = "";
				var startYear = null;
				var startMonth = null;
				for(var k = 0;k<prcs.length ; k++){
					var prc = prcs[k];
					var  fontStr = "";
					tableStr = tableStr +"<tr class='TableData' style='height:40px;'>"
					      	 + "<td nowrap style='text-align:center;padding:5px 0px;' align='center' text-align><font color='" + fontStr + "'>"+ prc.deptName +"</font></td>"
					      	 + "<td nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>"+ prc.userName +"</font></td>"
					      	 + "<td nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>"+ prc.perfectCount +"</font></td>"

					      	 + "<td nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>"+ prc.hours +"</font></td>"
					      	 + "<td nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>"+ prc.workOnNoRegisters +"</font></td>"
					      	 + "<td nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>"+ prc.lateNums +"</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.workOutNoRegisters + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.leaveEarlyNums + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.complainNum + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.leaveDays + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.outDays + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.evectionDays + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.overHours + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.attendAssignDays + "</font></td>"
					      	 +"<td nowrap nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'>" + prc.attendAssignNums + "</font></td>";
                            
					      	tableStr +="<td nowrap style='text-align:center;padding:5px 0px;' align='center'><font color='" + fontStr + "'><a href='javascript:void(0)' onclick='getDetail("+prc.userId+")'>详细记录</a></font></td>"
					        +"</tr>";
					        
					      	$("#registerNum").val(registerNum2);
				}
				tableStr = tableStr + "</tbody></table>";

				//alert(tableStr);
				$("#container").append(tableStr);
				$.MsgBox.Alert_auto("加载完毕");
			}else{
			 	messageMsg("没有相关上下班信息", "container" ,'' ,380);
			}
		}else{
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
		
		
	});

}


function getDays(strDateStart,strDateEnd){
	var strSeparator = "-"; //日期分隔符
	var oDate1;
	var oDate2;
	var iDays;
	oDate1= strDateStart.split(strSeparator);
	oDate2= strDateEnd.split(strSeparator);
	var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
	var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
	iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数
	return iDays + 1 ;
}

function getDetail(userId){
	var url = contextPath+"/system/core/base/attend/query/record/register.jsp?userId="+userId+"&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>";
	openFullWindow(url);
}

function back(){
	parent.location.href=contextPath+"/system/core/base/attend/query/index.jsp";
}

function exportRegister(){
      	
<%-- 	  var startDateDesc = "<%=startDateDesc %>";
	var endDateDesc = "<%=endDateDesc%>";  --%>
   var url = contextPath+"/TeeAttendDutyRecordController/exportRegister.action?deptIds=<%=deptIds%>&startDateDesc=<%=startDateDesc%>&endDateDesc=<%=endDateDesc%>&countMonth=<%=countMonth %>";
	document.form1.action=url;
	document.form1.submit(); 
	
	<%--//method1("register");--%>
}

function zh (day){
	switch(day){
		case 0:return "日";break;
		case 1:return "一";break;
		case 2:return "二";break;
		case 3:return "三";break;
		case 4:return "四";break;
		case 5:return "五";break;
		case 6:return "六";break;
	}
};

function date2string(date){
    var year = date.getFullYear();
    var month =(date.getMonth() + 1).toString();
    var day = (date.getDate()).toString();
    if (month.length == 1) {
        month = "0" + month;
    }
    if (day.length == 1) {
        day = "0" + day;
    }
    var dateTime = year + "-" + month + "-" + day;
    return dateTime;
}

</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
<form id="form1" name='form1' method="post">
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big">
				<div class = "right fl clearfix">
					<%-- <b><i class="glyphicon glyphicon-user"></i>&nbsp;上下班登记结果</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(<%=startDateDesc %>~<%=endDateDesc %>)
					<span style="float:right;"> --%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>(<%=startDateDesc %>~<%=endDateDesc %>)</span>
					<input type="button" id='exportRegisters' name="exportRegisters" class="btn-win-white fr" value="导出" onclick="exportRegister();"/>
					<input type="button" value="返回" class="btn-win-white fr" onclick="back();"> </span>
				</div>
			</td>

		</tr>
	</table>
	<input type="hidden" id="dayNum" name="dayNum" >
	<input type="hidden" id="registerNum" name="registerNum" >
	</form>
	</div>
	<div id="sss"></div>
<div id="container" style="padding-top: 10px;">

</div>
</body>
</html>