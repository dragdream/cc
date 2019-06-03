<%@page import="java.util.GregorianCalendar"%>
<%@page import="com.tianee.oa.core.base.weekActive.service.TeeCalendarUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat  dateFormat2 = new SimpleDateFormat("yyyy年-MM月-w周-dd日");
SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat dateFormat4 = new SimpleDateFormat("MM/dd");
SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");

Calendar c = Calendar.getInstance();
String week = dateFormatWeek.format(date);
String dateStr = dateFormat.format(date);
int year = Integer.parseInt(dateStr.substring(0,4));
int month = Integer.parseInt(dateStr.substring(5,7));
int day = Integer.parseInt(dateStr.substring(8,10));
c.setTime(date);
c.add(Calendar.DATE,-1) ;
int weekthInt = c.get(Calendar.WEEK_OF_YEAR);//当前是当年的第几周
c.add(Calendar.DATE,+1) ;

//本身跳过来的
String yearStr= request.getParameter("year");
String weekStr = request.getParameter("week");
if(yearStr!=null){
  year = Integer.parseInt(yearStr);
}
if(weekStr!=null){
  weekthInt = Integer.parseInt(weekStr);
}



TeeCalendarUtil tca  = new TeeCalendarUtil();
Date beginDate ;
Date endDate ;
//获取一周的开始日期和结束日期
Calendar[] darr = tca.getStartEnd(year,weekthInt);
//周开始日期
beginDate = dateFormat1.parse(tca.getFullTimeStr(darr[0]));
//周结束日期
endDate = dateFormat1.parse(tca.getFullTimeStr(darr[1]));
//周开始日历
Calendar calendar = new GregorianCalendar();  

calendar.setTime(beginDate);
calendar.add(Calendar.DATE,+1) ;
Date dateTemp2 = calendar.getTime();

String date1 = dateFormat3.format(beginDate);
//星期一日期
String dateTempStr1 = dateFormat4.format(beginDate);
String date2 = dateFormat3.format(dateTemp2);
//星期二日期
String dateTempStr2 = dateFormat4.format(dateTemp2);
calendar.setTime(beginDate);
calendar.add(Calendar.DATE,+2) ;
//星期三日期
Date dateTemp3 = calendar.getTime();
String date3 = dateFormat3.format(dateTemp3);
String dateTempStr3 = dateFormat4.format(dateTemp3);
calendar.setTime(beginDate);
calendar.add(Calendar.DATE,+3) ;
Date dateTemp4 = calendar.getTime();
String date4= dateFormat3.format(dateTemp4);
//星期四日期
String dateTempStr4 = dateFormat4.format(dateTemp4);
calendar.setTime(beginDate);
calendar.add(Calendar.DATE,+4) ;
Date dateTemp5 = calendar.getTime();
String date5= dateFormat3.format(dateTemp5);
//星期五日期
String dateTempStr5 = dateFormat4.format(dateTemp5);
calendar.setTime(beginDate);
calendar.add(Calendar.DATE,+5) ;
Date dateTemp6 = calendar.getTime();
String date6= dateFormat3.format(dateTemp6);
//星期六日期
String dateTempStr6 = dateFormat4.format(dateTemp6);
calendar.setTime(beginDate);
calendar.add(Calendar.DATE,+6) ;
Date dateTemp7 = calendar.getTime();
String date7= dateFormat3.format(dateTemp7);
//星期日日期
String dateTempStr7 = dateFormat4.format(dateTemp7); 

//当年最大周
Calendar currentYear = Calendar.getInstance();
currentYear.set(Calendar.YEAR, year);
int maxWeek = currentYear.getActualMaximum(Calendar.WEEK_OF_YEAR);

//out.print(date1+">>"+weekthInt + ">>" + maxWeek + " year>>" + year );

  
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>周活动安排</title>
<script type="text/javascript">

function newActive2(index){
		  var date = dateWeek[index];
		 // alert(date);
		  return;
		  var requestURL = "<%=contextPath%>/subsys/system/active/addactive.jsp?date="+date;
		  window.open(requestURL,'oa_sub_window','height=350,width=520,status=0,toolbar=no,menubar=no,location=no,left=300,top=200,scrollbars=yes,resizable=yes');
}
var date1 = '<%=date1%>';
var date2 = '<%=date2%>';
var date3 = '<%=date3%>';
var date4 = '<%=date4%>';
var date5 = '<%=date5%>';
var date6= '<%=date6%>';
var date7 = '<%=date7%>';
var dateWeek = [date1,date2,date3,date4,date5,date6,date7];

function changed(){
	var year = document.getElementById("year").value;
	var week = document.getElementById("week").value;
	window.location = "<%=contextPath%>/system/core/base/weekActive/index.jsp?year="+year+"&week="+week;
}

function set_week(index){
	var yearW = document.getElementById("year").value;
	var weekW = document.getElementById("week").value;
	var maxWeek = '<%=maxWeek%>';
	if(parseInt(weekW,10)+index>maxWeek){
		yearW = parseInt(yearW)+1;
		weekW = 1;
	}else if(parseInt(weekW,10)+index<1){
		yearW = parseInt(yearW)-1;
		weekW = '<%=TeeCalendarUtil.getMaxWeekOfYear(year-1)%>';
	}else{
		var weekW = parseInt(weekW)+index;
	}
	window.location = "<%=contextPath%>/system/core/base/weekActive/index.jsp?year="+yearW+"&week="+weekW;
}

function set_year(index){
  var year = document.getElementById("year").value;
  var week = document.getElementById("week").value;
  if(parseInt(year)<=2000){
    year = parseInt(year);
  }else if(parseInt(year)>=2049){
    year = parseInt(year);
  }else{
    year = parseInt(year)+index;
  }
  window.location = "<%=contextPath%>/system/core/base/weekActive/index.jsp?year="+year+"&week="+week;
}
function today(){
  window.location = "<%=contextPath%>/system/core/base/weekActive/index.jsp";
}

function getWeekInt(week,date){
  var temp = 1;
  for(var i=0;i<week.length;i++){
    if(date==week[i]){
      temp = i+1;
    }
  }
  return temp;
}
function getTimeOfDay(timeInt){
  var temp = 1 ;
  if(timeInt>43200){
     temp = 2;
  }
  return temp;
}
function str_int(str){
	  var min = 0;
	  var max = 24*3600;
	  var strInt;
	  var strInt1=0;
	  var strInt2=0;
	  var strInt3=0;
	  var strArray = str.split(":");
	  for(var i = 0 ; i<strArray.length; i++){
	    if(i==0){
	      strInt1 = parseInt(strArray[i]*3600,10);
	    }else if(i==1){
	      strInt2 = parseInt(strArray[i]*60,10); 
	    }else{
	      strInt3 = parseInt(strArray[i],10);
	    }  
	  }
	  strInt = strInt1+strInt2+strInt3;
	  return strInt;
	}



/**
 * 初始化数据
 */
function doOnload(){
	//alert(33);
	getWeekActiveList();
	
}

/* 获取数据列表 */
function getWeekActiveList(){
	var url = "<%=contextPath %>/weekActiveController/getWeekActiveList.action";
	var para = {beginDate:date1,endDate:date7};
	var jsonObj = tools.requestJsonRs(url,para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prcs[0].sid + ">>" + prcs[0].activeUserName);
		if(prcs.length>0){
			jQuery.each(prcs,function(i,sysPara){
				
				var sid = sysPara.sid;
				var activeStart = sysPara.activeStart;
				var activeContent = sysPara.activeContent;
				var activeContentTitle = activeContent;
				var createUserName = sysPara.createUserName;
				var activeUserName = sysPara.activeUserName;
				if(activeContent.length>10){
					activeContent = activeContent.substr(0,10) + "...";
				}
				if(activeUserName.length>10){
					//activeUserName = activeUserName.substr(0,10) + "...";
				}
				var activeTimeStr = activeStart.substr(11,8);
				var date = activeStart.substr(0,10);
				var weekTemp = getWeekInt(dateWeek,date);
				var activeTimeInt = str_int(activeTimeStr);
				var dayTemp = getTimeOfDay(activeTimeInt);
				
				var menuData = new Array();
				menuData.push({name:"查看",action:function(sid){
					showInfo(sid);
				} ,extData:[sid]});
				
				
				
				var render = "<div  style=\"font-size:12px\">";
				render += "<div><span id='"+sid+"'  style='color:#0f92d8'>"+ activeContent+"</span></div>";
				render += "</div>";
				
				var divStr = "<div title='" + activeContentTitle +"--"+createUserName + "'>" 
							+	"<span>" +activeStart.substr(11,5) + "</span>"
							+	"&nbsp" + "<span id='"+sid+"'  style='color:#0f92d8'>"+ activeContent+"</span>"
							+ "</div>";
				
				divStr = $(divStr);
				var activeUserNameStr = "<div title='" + activeUserName+ "'>" 
							+	"<span>" +activeStart.substr(11,5) + "</span>"
							+	"&nbsp" + activeUserName
							+ "</div>";
				$("#td_content_"+weekTemp+"_"+dayTemp).append(divStr);
				$("#td_userDesc_"+weekTemp+"_"+dayTemp).append(activeUserNameStr);
				
				var attachElement = divStr;
				attachElement.TeeMenu({menuData:menuData,eventPosition:false});
				
			});
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	
}


//详情
function showInfo(sid){
	var url = contextPath + "/system/core/base/weekActive/weekActiveDetail.jsp?sid="+sid;
	openFullWindow(url);
	
}

function print(){
	var year = document.getElementById("year").value;
	var week = document.getElementById("week").value;
	var url = contextPath + "/system/core/base/weekActive/printWeekActive.jsp?year="+year+"&week="+week;
	openFullWindow(url);
}

</script>
<style type="">
		.TableBlock_page{
			border:1px solid #f2f2f2;
		}
		.TableBlock_page td{
			border:1px solid #f2f2f2;
			text-align:center;
		}
</style>
</head>
<body onload="doOnload();" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
   <img class="title_img" src="<%=contextPath %>/common/zt_webframe/imgs/grbg/zhdap/icon_zhdap.png" alt="" />
   &nbsp;<span class="title">周活动安排<span id="totalMail"></span></span>
</div>

<div style="clear:both; margin: 10px;">
	<div class="fl">
		<form name="form1" id="form1" action="#" style="margin-bottom:5px;">
			<input type="hidden" value="" name="BTN_OP">
			<input type="hidden" value="" name="OVER_STATUS">
			<input type="hidden" value="02" name="MONTH">
			<input type="hidden" value="25" name="DAY">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="今日" class="btn-win-white" title="今天" onclick="today();" style="margin-right: 100px;">
			<a href="javascript:set_year(-1)";  title="上一年"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/zhdap/icon_left1.png"></img></a>&nbsp;&nbsp;&nbsp;
			<a href="javascript:set_week(-1);" class="ArrowButtonR" title="上一周"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/zhdap/icon_left.png"></img></a>&nbsp;&nbsp;
			<select id="year" name="year"   style="height:22px;FONT-SIZE: 11pt;outline:none;"  onchange="changed()">
			<%
			for(int i=2000;i<2050;i++){
				if(i==year){
			%>
			<option value="<%=i %>" selected="selected"><%=i %>年</option>
				<%}else{ %>
			<option value="<%=i %>"><%=i %>年</option>
			<%
				}
			}
			%>
			</select>
			<!-- 周 -->
			<select id="week" name="week"   style="height:22px;FONT-SIZE: 11pt;"  onchange="changed()">
			<%
			for(int i=1;i<=maxWeek;i++){
				if(i==weekthInt){
					%>
			<option value="<%=i %>" selected="selected">第<%=i %>周</option>
				<%}else{ %>
			<option value="<%=i %>">第<%=i %>周</option>
			<%
				}
			}
			%>
			</select>&nbsp;&nbsp;
			<a href="javascript:set_week(1);" class="ArrowButtonR" title="下一周"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/zhdap/icon_right.png"></img></a>&nbsp;&nbsp;&nbsp;
			<a href="javascript:set_year(1);" class="ArrowButtonRR" title="下一年"><img src="<%=contextPath%>/common/zt_webframe/imgs/grbg/zhdap/icon_right1.png"></a>
	</div>
	<div class="fr">
			<input type="button" value="打印" class="btn-win-white" title="打印单" onclick="print();">
		</div>
</form>
</div>
<br>
<br>

<table id="cal_table" class="TableBlock_page" width="80%" align="center">
	<tr style="background-color:#f9fdff;">
		<td rowspan="2" class="TableContent" align="center" nowrap width="10%">日（星期）</td>
		<td class="TableContent" align="center" colspan="2" nowrap>上午</td>
		<td class="TableContent" align="center" colspan="2" nowrap>下午</td>
	</tr>
	<tr style="background-color:#f9fdff;">
		<td class="TableContent" align="center">内容</td>
		<td class="TableContent" align="center">出席</td>
		<td class="TableContent" align="center">内容</td>
		<td class="TableContent" align="center">出席</td>
	</tr>
	<tr id="tbl_header" align="center" class="">
		<td id="td_week_1"  class="TableContent" nowrap>
			<%=dateTempStr1 %>(星期一)
		</td>
		<td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_1_1" class="TableData"  width="15%" height="30" >      
		</td>
		<td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_1_1" class="TableData" width="15%" height="30"   >  </td>	
		<td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_1_2" class="TableData"  width="15%" height="30"  >      
		</td>
		<td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_1_2" class="TableData"  width="15%" height="30"  >
		</td>	
	</tr> 
	
	<tr id="tbl_header" align="center" >
      <td id="td_week_2"  class="TableContent" nowrap>
          <%=dateTempStr2 %>(星期二)
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_2_1" class="TableData"  width="15%" height="30"  >
    </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_2_1" class="TableData"  width="15%" height="30"   >
       </td>	
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_2_2" class="TableData"  width="15%" height="30"  >
    </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_2_2" class="TableData" width="15%" height="30"  >
      </td>	
     </tr> 
     <tr id="tbl_header" align="center">
      <td id="td_week_3"  class="TableContent" nowrap>
          <%=dateTempStr3 %>(星期三)
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_3_1" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_3_1" class="TableData"  width="15%" height="30"   >
      	      </td>	
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_3_2" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_3_2" class="TableData"  width="15%" height="30"  >
      	      </td>	
     </tr>
     <tr id="tbl_header" align="center">
      <td id="td_week_4"  class="TableContent" nowrap>
      <%=dateTempStr4 %>(星期四)
     </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_4_1" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_4_1" class="TableData"  width="15%" height="30"   >
      	      </td>	
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_4_2" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_4_2" class="TableData"  width="15%" height="30"  >
      	      </td>	
     </tr> 
     
     
     <tr id="tbl_header" align="center">
      <td id="td_week_5"  class="TableContent" nowrap>
      <%=dateTempStr5 %>(星期五)
    </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_5_1" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_5_1" class="TableData"  width="15%" height="30"  >
      	      </td>	
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_5_2" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_5_2" class="TableData" width="15%" height="30"  >
      	      </td>	
     </tr> 
     
     <tr id="tbl_header" align="center">
      <td id="td_week_6"  class="TableContent" nowrap>
      <%=dateTempStr6 %>(星期六)
  </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_6_1" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_6_1" class="TableData"  width="15%" height="30"  >
      	      </td>	
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_6_2" class="TableData" width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_6_2" class="TableData"  width="15%" height="30"  >
      	      </td>	
     </tr> 
     
     <tr id="tbl_header" align="center">
      <td id="td_week_7"  class="TableContent" nowrap>
      <%=dateTempStr7 %>(星期日)
    </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_7_1" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_7_1" class="TableData" width="15%" height="30"  >
      	      </td>	
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top nowrap id="td_content_7_2" class="TableData"  width="15%" height="30"  >
                
      </td>
      <td style="text-align:left;padding-left: 10px;padding-right: 5px;" valign=top id="td_userDesc_7_2" class="TableData" width="15%" height="30"  >
      	      </td>	
     </tr> 
</table>

</body>
</html>