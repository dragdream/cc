<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<title>个人日程导入/导出</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
function checkForm(){
  var sendTimeMin = document.getElementById("startTime");
  var sendTimeMax = document.getElementById("endTime");
  if(sendTimeMin.value!=""&&sendTimeMax.value!=""){
    if(compareDate(sendTimeMin , sendTimeMax)){
      alert("起始日期不能大于结束日期!");
      sendTimeMax.focus();
      sendTimeMax.select();
      return false;
    }  
  } 
  return true;
}
function compareDate(beginDate , endDate) {
	d1Arr=beginDate.value.split('-');
    d2Arr=endDate.value.split('-');
    v1=new Date(d1Arr[0],d1Arr[1],d1Arr[2]);
    v2=new Date(d2Arr[0],d2Arr[1],d2Arr[2]);
    return v1>v2;
}


function doOnload(){

}


/**
 * 导出
 */
function extportCalendar(){
 	// var pars = Form.serialize($('form1'));
 	
 	var para = $("#form1").serialize() ;
 //	var para =  tools.formToJson($("#form1")) ;
 	if(checkForm()){
 		var url = "<%=contextPath %>/calendarManage/exportCalendarToCsv.action?"+para;
 		window.location.href = url;
 	}
 	
}
/**
 * 导入
 */
function importCalendar(){
	if(check()){
		 var para =  tools.formToJson($("#form2")) ;
		 $("#form2").ajaxSubmit({
			  url :"<%=contextPath %>/calendarManage/importCalendar.action",
	          iframe: true,
	          data: para,
	          success: function(res) {
	        	  if(res.rtState){
	        		  //var data = res.rtData;
	        		//  var dataJson = eval('(' + data + ')');
	        		 // importInfo( dataJson ,res.rtMsg );
	        			 $("#importExtport").hide();
	        		  //  $("#importDiv").show();
	        		    
	        		//	$("#importDiv").append(tableStr + "<br>");
	        			messageMsg("总共导入 " +  res.rtMsg + " 条数据", "importDiv" ,'' ,280);
	        			 $("#importDiv").append("<div align='center' ><imput type='button' value='返回' onclick='window.location.reload();'/></div>");
	        	  }else{
	        		  alert(res.rtMsg);
	        	  }
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	                alert("导入错误");
	          },
	          dataType: 'json'});
	}
}
function check() {
	return checkSuppotCsvFile('importFile');
}
</script>
<style>
td{
padding:10px;
}
</style>
</head>
<body class="" style="padding-top:5px;" onload="doOnload();">
<fieldset>
	<legend style="font-size:14px; font-weight:bold; width:1680px;" class = "topbar clearfix">日程数据导出</legend>
    <div class="control-group ">
	    <form action="<%=contextPath%>/calendarManage/leaderQuery.action"  method="post" id="form1" name="form1" onsubmit="return checkForm();">
		 <table class="TableBlock" style="font-family:微软雅黑 width:100%;background-color:#fff;">
		    <tr>
		        <td nowrap class="TableData" style="width:100px;text-indent: 15px;"> 日期：</td>
		        <td class="TableData">
		      	    <input type="text" id="startTime" name="startDate" size="15" maxlength="10" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="BigInput Wdate" value="">
		       	</td>
           		<td class="TableData">		
					 至
				</td>
				<td class="TableData">
		            <input type="text" id="endTime" name="endDate" size="15" maxlength="10" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="BigInput Wdate" value="">
		        </td>
		      <td nowrap class="TableData"> 事务类型：</td>
		      <td class="TableData">
		        <select id="calType" name="calType" class="BigSelect">
		          <option value="">所有</option>
		          <option value="1">工作事务</option>
		          <option value="2">个人事务</option>
		        </select>
		      </td>
		      <td nowrap align="center">
		        <input type="button" value="导出" class="btn-win-white" onclick="extportCalendar();" style="margin-right:1000px;">&nbsp;&nbsp;
		      </td>
		    </tr>
		  </table>
		</form>
    </div>
</fieldset>
<br/><br/>
<fieldset>
	<legend style="font-size:14px; font-weight:bold; width:1680px;" class = "topbar clearfix">日程数据导入</legend>
    <div class="control-group ">
	<form enctype="multipart/form-data"   method="post" id="form2" name="form2" onsubmit="return checkForm();">
		 <table  class="TableBlock" align=""  style="font-family:微软雅黑 width:100%;background-color:#fff;">
		    <tr style="border-bottom:none;">
		      <td nowrap class="TableData" style="width:100px;text-indent: 15px;"> 导入文件：</td>
		      <td class="TableData">
		       	 <input type="file" name="importFile" id="importFile" style="width:500px;"/>
		       	 <font color='red' style='float:left;display:inline-block;'>请选择CSV格式文件（点击上面导出即可得到CSV导入模板）</font>
		      </td>
		      <td colspan="2" align="center" nowrap>
		        <input type="button" value="导入" class="btn-win-white" onclick="importCalendar();" style="margin-right:1030px;">&nbsp;&nbsp;
		      </td>
		    </tr>
		  </table>
		</form>
    </div>
</fieldset>

<div id="importExtport">
	
</div>

<div id="importDiv">

</div>
</body>
</html>