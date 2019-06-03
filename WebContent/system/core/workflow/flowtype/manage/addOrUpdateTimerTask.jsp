<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "" : request.getParameter("flowTypeId");
	String sid = request.getParameter("sid") == null ? "" : request.getParameter("sid");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
	SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
	SimpleDateFormat sdf4 = new SimpleDateFormat("dd");
	SimpleDateFormat sdf5 = new SimpleDateFormat("E");
	Date date = new Date();
	String time = sdf1.format(date);
	String dateTime = sdf.format(date);
	String year = sdf2.format(date);
	int month = Integer.parseInt(sdf3.format(date));
	int day = Integer.parseInt( sdf4.format(date));
	String week = sdf5.format(date);
	//System.out.println(month);
	//System.out.println(day);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>新加定时任务</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var flowTypeId = '<%=flowTypeId%>';
var sid = '<%=sid%>';
var week = '<%=week%>';
var month = '<%=month%>';
var day = '<%=day%>';
var aff_type="day";
function doInit(){
	
	var url1 = contextPath+"/flowTimerTask/getFlowName.action?flowTypeId="+flowTypeId;
	var jsonRs = tools.requestJsonRs(url1,{});
	var jsonObj = jsonRs.rtData;
		
	if(sid==""){
		$("#span").text("新建定时任务（流程名称："+jsonObj+"）");
		$("#remindDate3 option[id='"+week+"']").attr("selected","selected");
		$("#remindDate4 option[value="+day+"]").attr("selected","selected");
		$("#remindDate5Mon option[value="+month+"]").attr("selected","selected");
		$("#remindDate5Day option[value="+day+"]").attr("selected","selected");
		$("#type option[value=2]").attr("selected","selected");
		document.getElementById(aff_type).style.display="";
	}else{
		$("#span").text("编辑定时任务（流程名称："+jsonObj+"）");
		var url = contextPath+"/flowTimerTask/getFlowTimerTaskById.action?sid="+sid;
		var jsonRs1 = tools.requestJsonRs(url,{});
		var jsonObj1 = jsonRs1.rtData;
		if(jsonObj1){
			if(jsonObj1.sid){
				bindJsonObj2Cntrl(jsonObj1);
				document.getElementById(aff_type).style.display="none";
				var remindModel = eval("("+jsonObj1.remindModel+")");
				var week = remindModel.week+"";
				var year = remindModel.year+"";
				var month = remindModel.month+"";
				var date = remindModel.date+"";
				var hours = remindModel.hours+"";
				var minutes = remindModel.minutes+"";
				var seconds = remindModel.seconds+"";
				
				switch (jsonObj1.type) {
				case 1:
					aff_type = "once";
					$("#remindTime1").val(year+"-"+month+"-"+date+" "+hours+":"+minutes+":"+seconds);
					break;
				case 2:
					aff_type = "day";
					$("#remindTime2").val(hours+":"+minutes+":"+seconds);
					break;
				case 3:
					aff_type = "week";
					$("#remindDate3 option[value="+week+"]").attr("selected","selected");
					$("#remindTime3").val(hours+":"+minutes+":"+seconds);
					break;
				case 4:
					aff_type = "mon";
					$("#remindDate4 option[value="+date+"]").attr("selected","selected");
					$("#remindTime4").val(hours+":"+minutes+":"+seconds);
					break;
				case 5:
					aff_type = "year";
					$("#remindDate5Mon option[value="+month+"]").attr("selected","selected");
					$("#remindDate5Day option[value="+date+"]").attr("selected","selected");
					$("#remindTime5").val(hours+":"+minutes+":"+seconds);
					break;
				}
				document.getElementById(aff_type).style.display="";
			}
		}else{
			alert(jsonRs1.rtMsg);
		}
	}
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/flowTimerTask/addOrUpdateTimerTask.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			return true;
		}else{
			alert(jsonRs.rtMsg);
			return false;
		}
	}
}

function checkForm(){
	
	var userListNames = document.getElementById("userNames");
	    if (!userListNames.value) {
	  	  alert("发起人不能为空！");
	  	  userListNames.focus();
	  	  userListNames.select();
	  	  return false;
	    }

    return $("#form1").form('validate'); 
}

function backIndex(){

	window.location.href = "manageTimerTask.jsp?flowTypeId="+flowTypeId;
}

function sel_change(){
   if(aff_type!="")
      document.getElementById(aff_type).style.display="none";
   if($("#type").val()=="1")
      aff_type="once";
   if($("#type").val()=="2")
      aff_type="day";
   if($("#type").val()=="3")
      aff_type="week";
   if($("#type").val()=="4")
      aff_type="mon";
   if($("#type").val()=="5")
      aff_type="year";
   document.getElementById(aff_type).style.display="";
}



</script>

</head>
<body onload="doInit()" style="margin:5px;">
<center style="width:100%;">

	<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="60%" align="center">
   	<tr>
				<td nowrap class="TableData TableBG" width="100">发起人：</td>
				<td class="TableData" style="text-align: left;"><input type="hidden"
					name="users" id="users" required="true" value=""> <textarea cols="45"
						name="userNames" id="userNames" rows="8"
						style="overflow-y: auto;"  class="BigTextarea readonly" wrap="yes" readonly></textarea>
					<a href="javascript:void(0);" class="orgAdd"
					onClick="selectUser(['users', 'userNames'])">添加</a> <a
					href="javascript:void(0);" class="orgClear"
					onClick="clearData('users', 'userNames')">清空</a></td>
			</tr>
   <tr >
	    <td nowrap class="TableData TableBG">发起频率：</td>
	    <td class="TableData" style="text-align: left;">
	   
	   		<select id="type" name = "type" onChange="sel_change();" class="BigSelect">
	   			<option value="1">仅此一次</option>
	   			<option value="2">按日发起</option>
	   			<option value="3">按周发起</option>
	   			<option value="4">按月发起</option>
	   			<option value="5">按年发起</option>
	   		</select>
	   	</td>
   </tr>
   <tr id="day">
      <td nowrap class="TableData TableBG">  发起时间：</td>
      <td class="TableData" style="text-align: left;">
      
        <input type="text" id = "remindTime2" name="remindTime2" size="10" class="BigInput" value="<%=time %>" onClick="WdatePicker({dateFmt:'HH:mm:ss'})">
        &nbsp;&nbsp;为空为当前时间
      </td>
    </tr>
    <tr id="once" style="display:none;">
      <td nowrap class="TableData">  发起时间：</td>
      <td class="TableData" style="text-align: left;">
        <input type="text" id= "remindTime1" name="remindTime1" size="20" class="BigInput" value="<%=dateTime %>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">&nbsp;&nbsp;为空为当前时间
      </td>
    </tr>
    <tr id="week" style="display:none;">
      <td nowrap class="TableData"> 发起时间：</td>
      <td class="TableData" style="text-align: left;">
        <select id = "remindDate3" name="remindDate3" class="BigSelect">
          <option value="2" id = "星期一">星期一</option>
          <option value="3" id = "星期二">星期二</option>
          <option value="4" id = "星期三">星期三</option>
          <option value="5" id = "星期四">星期四</option>
          <option value="6" id = "星期五">星期五</option>
          <option value="7" id = "星期六">星期六</option>
          <option value="1" id = "星期日">星期日</option>
        </select>&nbsp;&nbsp;
        <input type="text" id = "remindTime3" name="remindTime3" size="10" class="BigInput" value="<%=time %>" onClick="WdatePicker({dateFmt:'HH:mm:ss'})">
        &nbsp;&nbsp;为空为当前时间
      </td>
    </tr>
     <tr id="mon" style="display:none;">
      <td nowrap class="TableData">发起时间：</td>
      <td class="TableData" style="text-align: left;">
        <select id="remindDate4" name="remindDate4" class="BigSelect">
		<%
			for(int i=1;i<=31;i++)
			{
		%>
          		<option value="<%=i %>" ><%=i%>日</option>
		<%
			}
		%>
        </select>&nbsp;&nbsp;
        <input type="text" name="remindTime4" id ="remindTime4" size="10" class="BigInput" value="<%=time %>" onClick="WdatePicker({dateFmt:'HH:mm:ss'})">
        &nbsp;&nbsp;为空为当前时间
      </td>
    </tr>
     <tr id="year" style="display:none;">
      <td nowrap class="TableData">发起时间：</td>
      <td class="TableData" style="text-align: left;">
        <select name="remindDate5Mon" id="remindDate5Mon" class="BigSelect">
			<%
				for(int i=1;i<=12;i++)
				{
			%>
		          	<option value="<%=i %>" ><%=i %>月</option>
			<%
				}
			%>
        </select>&nbsp;&nbsp;
        <select name="remindDate5Day" id="remindDate5Day" class="BigSelect">
			<%
				for(int i=1;i<=31;i++)
				{
			%>
		          	<option value="<%=i %>" ><%=i %>日</option>
			<%
				}
			%>
        </select>&nbsp;&nbsp;
        <input type="text" name="remindTime5" id = "remindTime5" size="10" class="BigInput" value="<%=time %>" onClick="WdatePicker({dateFmt:'HH:mm:ss'})">
        &nbsp;&nbsp;为空为当前时间
      </td>
    </tr>
   <tr style="display:none">
	    <td nowrap  class="TableControl" colspan="2" align="center">
	     	<input type="hidden" id = "sid" name = "sid" value="<%=sid%>"/>
	    	<input type="hidden" id = "flowTypeId" name="flowTypeId" value="<%=flowTypeId%>"/>
	        <input type="button" value="保存" class="btn btn-default" title="新加定时任务" onclick="doSave()" >&nbsp;&nbsp;
	        <input type="button" value="返回" class="btn btn-default" title="返回" onClick="backIndex();">
	    </td>
   </tr>
  
</table>
  </form>
  </center>
</body>
</html>