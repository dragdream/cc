<%@ page language="java"  import="java.util.*,java.text.SimpleDateFormat" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
String week = dateFormatWeek.format(date);
String dateStr = dateFormat.format(date);
int year = Integer.parseInt(dateStr.substring(0,4));
int year1 = Integer.parseInt(dateStr.substring(0,4));
int month = Integer.parseInt(dateStr.substring(5,7));
int day = Integer.parseInt(dateStr.substring(8,10));
Calendar time=Calendar.getInstance(); 
time.clear(); 
time.set(Calendar.YEAR,year); //year 为 int 
time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0           
int maxDay = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数 
String status = "0";
String yearOnly = request.getParameter("yearOnly");
String yearStr = request.getParameter("year");
String monthStr = request.getParameter("month");
if(yearOnly!=null){
  year1 = Integer.parseInt(yearOnly);
}
if(yearStr!=null){
  year = Integer.parseInt(yearStr);
}
if(monthStr!=null){
  month = Integer.parseInt(monthStr);
}
String weekToDate = request.getParameter("date");
String userId = request.getParameter("userId");
if(weekToDate!=null){
  year = Integer.parseInt(weekToDate.substring(0,4));
  month = Integer.parseInt(weekToDate.substring(5,7));
}
String yearMonth = String.valueOf(year) + "-" + String.valueOf(month);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.time_info{
	line-height:30px;
	border-bottom:1px solid #f2f2f2;
	font-weight:bold;
	border-left:3px solid #6ba6fe;
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
.time_info a img{vertical-align:middle;}
.time_info select{vertical-align:middle;}
</style>
<script>

var year = "<%=year%>";
var month = "<%=month%>";
var userId = "<%=userId%>";
function doInit(){
	getDutyByCondition(year,month , userId);
}
function My_Submit(){
	  var year = document.getElementById("year").value;
	  var month = document.getElementById("month").value;
	  window.location="<%=contextPath%>/system/core/base/attend/duty/query.jsp?year="+year+"&month="+month + "&userId=" + userId;
}

function set_month(index){
	  var year = document.getElementById("year").value;
	  var month = document.getElementById("month").value;
	  if(parseInt(month,10)+index<=0){
	    year = parseInt(year)-1;
	    month = 12;
	  }else if(parseInt(month,10)+index>12){
	    year = parseInt(year)+1;
	    month = 1;
	  }else{
	    month = parseInt(month,10)+index;
	  }
	  window.location="<%=contextPath%>/system/core/base/attend/duty/query.jsp?year="+year+"&month="+month + "&userId=" + userId;
}

function set_year(index){
	  var year = document.getElementById("year").value;
	  var month = document.getElementById("month").value;
	  if(parseInt(year)<=2000){
	    year = parseInt(year);
	  }else if(parseInt(year)>=2049){
	    year = parseInt(year);
	  }else{
	    year = parseInt(year)+parseInt(index);
	  }
	  window.location="<%=contextPath%>/system/core/base/attend/duty/query.jsp?year="+year+"&month="+month + "&userId=" + userId;
}


/**
 * 根据条件获取登记记录
 */
function getDutyByCondition(year,month , userId){
	//设置表头
	var html="<table  width='100%'><tr class='TableHeader'><td>日期</td>";
	html+="<td>第一次登记(上班)</td>";
	html+="<td>第二次登记(下班)</td>";
	html+="<td>第三次登记(上班)</td>";
	html+="<td>第四次登记(下班)</td>";
	html+="<td>第五次登记(上班)</td>";
	html+="<td>第六次登记(下班)</td>";
	html+="</tr>";
	//查出当前用户的上下班登记记录，填充表
	var url = contextPath+"/TeeAttendDutyController/getPersonalDutyByCondition.action?year="+year+"&month="+month+ "&userId=" + userId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		for(var i=0;i<data.length;i++){	
			html+="<tr class='TableData'><td>"+data[i].dutyDate+"(周"+exchangeWeek(data[i].week)+")</td>";
			if(data[i].dutyId==""){//无排班
				html+="<td style='color:gray;'>无排班</td>";
				html+="<td style='color:gray;'>无排班</td>";
				html+="<td style='color:gray;'>无排班</td>";
				html+="<td style='color:gray;'>无排班</td>";
				html+="<td style='color:gray;'>无排班</td>";
				html+="<td style='color:gray;'>无排班</td>";
			}else if(data[i].dutyId=="0"){//休息日
				html+="<td style='color:red;'>公休日</td>";
				html+="<td style='color:red;'>公休日</td>";
				html+="<td style='color:red;'>公休日</td>";
				html+="<td style='color:red;'>公休日</td>";
				html+="<td style='color:red;'>公休日</td>";
				html+="<td style='color:red;'>公休日</td>";
			}else{//正常排班
				if(data[i].dutyTimeDesc1!=null && data[i].dutyTimeDesc1!="" && data[i].dutyTimeDesc1!="null"){
					if(data[i].isNoRegisterUserIds=="true"){
						html+="<td style='color:green;'>免签</td>";
					}else{
						if(data[i].isGeneral=="true"){
							html+="<td style='color:red;'>公休日</td>";
						}else if(data[i].isHoliday=="true"){
							html+="<td style='color:red;'>节假日</td>";
						}
						else if(data[i].isLeave1=="true"){
							html+="<td style='color:red;'>请假</td>";
						}
						else if(data[i].isEvection1=="true"){
							html+="<td style='color:red;'>出差</td>";
						}
						else if(data[i].isOut1=="true"){
							html+="<td style='color:red;'>外出</td>";
						}
						else{
							html+="<td>"+data[i].dutyTimeDesc1+"</td>";
						}
					}
				}else{
					html+="<td></td>";
				}
				if(data[i].dutyTimeDesc2!=null && data[i].dutyTimeDesc2!="" && data[i].dutyTimeDesc2!="null"){
					if(data[i].isNoRegisterUserIds=="true"){
						html+="<td style='color:green;'>免签</td>";
					}else{
						if(data[i].isGeneral=="true"){
							html+="<td style='color:red;'>公休日</td>";
						}else if(data[i].isHoliday=="true"){
							html+="<td style='color:red;'>节假日</td>";
						}
						else if(data[i].isLeave2=="true"){
							html+="<td style='color:red;'>请假</td>";
						}
						else if(data[i].isEvection2=="true"){
							html+="<td style='color:red;'>出差</td>";
						}
						else if(data[i].isOut2=="true"){
							html+="<td style='color:red;'>外出</td>";
						}
						else{
							html+="<td>"+data[i].dutyTimeDesc2+"</td>";
						}
					}
				}else{
					html+="<td></td>";
				}
				if(data[i].dutyTimeDesc3!=null && data[i].dutyTimeDesc3!="" && data[i].dutyTimeDesc3!="null"){
					if(data[i].isNoRegisterUserIds=="true"){
						html+="<td style='color:green;'>免签</td>";
					}else{
						if(data[i].isGeneral=="true"){
							html+="<td style='color:red;'>公休日</td>";
						}else if(data[i].isHoliday=="true"){
							html+="<td style='color:red;'>节假日</td>";
						}
						else if(data[i].isLeave3=="true"){
							html+="<td style='color:red;'>请假</td>";
						}
						else if(data[i].isEvection3=="true"){
							html+="<td style='color:red;'>出差</td>";
						}
						else if(data[i].isOut3=="true"){
							html+="<td style='color:red;'>外出</td>";
						}
						else{
							html+="<td>"+data[i].dutyTimeDesc3+"</td>";
						}
					}
				}else{
					html+="<td></td>";
				}
				if(data[i].dutyTimeDesc4!=null && data[i].dutyTimeDesc4!="" && data[i].dutyTimeDesc4!="null"){
					if(data[i].isNoRegisterUserIds=="true"){
						html+="<td style='color:green;'>免签</td>";
					}else{
						if(data[i].isGeneral=="true"){
							html+="<td style='color:red;'>公休日</td>";
						}else if(data[i].isHoliday=="true"){
							html+="<td style='color:red;'>节假日</td>";
						}
						else if(data[i].isLeave4=="true"){
							html+="<td style='color:red;'>请假</td>";
						}
						else if(data[i].isEvection4=="true"){
							html+="<td style='color:red;'>出差</td>";
						}
						else if(data[i].isOut4=="true"){
							html+="<td style='color:red;'>外出</td>";
						}
						else{
							html+="<td>"+data[i].dutyTimeDesc4+"</td>";
						}
					}
				}else{
					html+="<td></td>";
				}
				if(data[i].dutyTimeDesc5!=null && data[i].dutyTimeDesc5!="" && data[i].dutyTimeDesc5!="null"){
					if(data[i].isNoRegisterUserIds=="true"){
						html+="<td style='color:green;'>免签</td>";
					}else{
						if(data[i].isGeneral=="true"){
							html+="<td style='color:red;'>公休日</td>";
						}else if(data[i].isHoliday=="true"){
							html+="<td style='color:red;'>节假日</td>";
						}
						else if(data[i].isLeave5=="true"){
							html+="<td style='color:red;'>请假</td>";
						}
						else if(data[i].isEvection5=="true"){
							html+="<td style='color:red;'>出差</td>";
						}
						else if(data[i].isOut5=="true"){
							html+="<td style='color:red;'>外出</td>";
						}
						else{
							html+="<td>"+data[i].dutyTimeDesc5+"</td>";
						}
					}
				}else{
					html+="<td></td>";
				}
				if(data[i].dutyTimeDesc6!=null && data[i].dutyTimeDesc6!="" && data[i].dutyTimeDesc6!="null"){
					if(data[i].isNoRegisterUserIds=="true"){
						html+="<td style='color:green;'>免签</td>";
					}else{
						if(data[i].isGeneral=="true"){
							html+="<td style='color:red;'>公休日</td>";
						}else if(data[i].isHoliday=="true"){
							html+="<td style='color:red;'>节假日</td>";
						}
						else if(data[i].isLeave6=="true"){
							html+="<td style='color:red;'>请假</td>";
						}
						else if(data[i].isEvection6=="true"){
							html+="<td style='color:red;'>出差</td>";
						}
						else if(data[i].isOut6=="true"){
							html+="<td style='color:red;'>外出</td>";
						}
						else{
							html+="<td>"+data[i].dutyTimeDesc6+"</td>";
						}
					}
				}else{
					html+="<td></td>";
				}
			}
			
			
			html+="</tr>";
		}
		html+="</table>";
		$("#container")[0].innerHTML=html;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg); 
	}
}


function exchangeWeek(week){
	var weekName="";
	switch(week)
	{
	case '1':
		weekName= "日";
		break;
	case '2':
		weekName= "一";
		break;
	case '3':
		weekName= "二";
		break;
	case '4':
		weekName= "三";
		break;
	case '5':
		weekName= "四";
		break;
	case '6':
		weekName= "五";
		break;
	case '7':
		weekName= "六";
		break;
	}
	return weekName;
}


//申诉
function complain(remarkTimeStr,registerNum){
	var url=contextPath+"/system/core/base/attend/duty/complain.jsp?remarkTimeStr="+remarkTimeStr+"&registerNum="+registerNum;
	bsWindow(url ,"申诉",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSave();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("申诉已提交！",function(){
		    		My_Submit();
		    	});
		    	
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto(json.rtMsg);
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


//查看申诉
function viewComplain(remarkTimeStr,registerNum,userId){
	var url=contextPath+"/system/core/base/attend/duty/complaintDetail.jsp?remarkTimeStr="+remarkTimeStr+"&registerNum="+registerNum+"&userId="+userId;
	bsWindow(url ,"申诉详情",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}});
}
</script>

</head>
<body class=""onload="doInit();">
   <div class="time_info" style="margin-left: 5px">
          <span style="margin-right: 20px;">个人上下班记录</span>
          <a href="javascript:set_year(-1)";  title="上一年"><img  src="<%=contextPath%>/common/zt_webframe/imgs/grbg/grkq/previous.gif"></img></a>
	   <select id="year" name="year" style="height:22px;FONT-SIZE: 11pt;" onchange="My_Submit();">
	     <%
	       for(int i = 2000; i < 2050; i++){
	         if(i == year){
	     %>
	     <option value="<%=i %>" selected="selected"><%=i %>年</option>
	       <%}else{ %>
	     <option value="<%=i %>"><%=i %>年</option>
	       <%
	           }
	        }
	       %>
	   </select><a href="javascript:set_year(1);" class="ArrowButtonR" title="下一年"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/grkq/next.gif"></img></a>
	   <a href="javascript:set_month(-1);" class="ArrowButtonL" title="上一月"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/grkq/previous.gif"></img></a><select id="month"  style="height:22px;FONT-SIZE: 11pt;"  name="month" onchange="My_Submit();">
	     <%
	       for(int i = 1; i < 13; i++){
	         if(i >= 10){
	          if(i == month){
	     %>
	     <option value="<%=i %>" selected="selected"><%=i %>月</option>
	        <%}else{ %>
	     <option value="<%=i %>"><%=i %>月</option>
	       <%
	          }    
	        }else{
	          if(i == month){
	       %>
	       <option value="0<%=i %>" selected="selected">0<%=i %>月</option>
	        <%}else{ %>
	     <option value="0<%=i %>">0<%=i %>月</option>
	       <%
	        }
	      }
	    }
	       %>
	   </select><a href="javascript:set_month(1);" class="ArrowButtonR" title="下一月"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/grkq/next.gif"></img></a><span class="big3"></span>
   </div>

   <div id="container" style="margin-top: 20px;margin-bottom: 20px;">
</body>
</html>