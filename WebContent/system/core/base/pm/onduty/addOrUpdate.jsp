<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Date curDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	Calendar c = Calendar.getInstance();
	String curDateStr = dateFormat.format(curDate);
	String  currHour = curDateStr.substring(11,13);
	String currSecond = curDateStr.substring(14, 16);
	
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>外出申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>


<script type="text/javascript">
var sid = <%=sid%>;
var curDateStr="<%=curDateStr%>";
$.extend($.fn.validatebox.defaults.rules, {
	checkTimeComp: {   
	 	validator: function(value, param){   
  			return  value >=  param ; 
 	    },  
  	   message: '开始时间不能大于结束时间！'
    } 
});


function doInit(){
	//排班类型动态加载
	getSysCodeByParentCodeNo("PBLX","pbType");
	//值班类型动态加载
	getSysCodeByParentCodeNo("ZBLX","zbType");
	
	if(sid>0){
		var url ="<%=contextPath%>/onDutyController/getDutyById.action";
		var para = {sid :sid};
		
		var jsonObj = tools.requestJsonRs(url, para);
		
		//alert(jsonObj);
		if (jsonObj.rtState) {	
			var target = jsonObj.rtData;		
		    bindJsonObj2Cntrl(target);		
		} else {
			
			alert(jsonObj.rtMsg);
		}	
	
	}
	
	
}


//保存
function save(){
	if(checkForm()){
		var url = "<%=contextPath %>/onDutyController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//关闭div   刷新父页面
			parent.parent.$('#myModal').modal('hide');		
			top.$.jBox.tip("保存成功!",'success',{timeout:3000});
			window.parent.parent.renderCalendar();
			
		}else{
			top.$.jBox.tip("保存失败！",'error',{timeout:1500});
		}
	}
}

//验证
function checkForm(){
		var check = $("#form1").form('validate'); 
		if(!check){
			return false; 
		}
		return true;
	}

</script>

</head>
<body onload="doInit();">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" style="margin-top:10px;">
       <input type="hidden" name="uuid" id="uuid" value="<%=sid%>"/>
		<tr class="TableData" id="userIdTr">
			<td nowrap>值班人员：</td>
			<td nowrap align="left" >
				<input id="userUuid" name="userUuid" type="hidden" value=''> 
				<textarea name="userName" id="userName" class="SmallStatic BigTextarea easyui-validatebox" rows="1" cols="14"  readonly="readonly" required="true"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userUuid','userName'],'19')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('userUuid','userName')">清空</a>
			</td>
			<td nowrap class="TableData" width="100">排班类型：</td>
			<td class="TableData">
			   <select id="pbType" name="pbType"></select>
			</td>
		</tr> 
		<tr>
			<td nowrap class="TableData" width="100">值班类型：</td>
			<td class="TableData" colspan="3">
			   <select id="zbType" name="zbType"></select>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">值班日期：</td>
			<td class="TableData"  colspan="3">
			   <input type="text" id="beginTimeStr"
				name="beginTimeStr" size="18" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="BigInput easyui-validatebox" required="true"
				value="<%=curDateStr%>">
				 &nbsp;至&nbsp;
					<input type="text" id="endTimeStr"
				name="endTimeStr" size="18" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="BigInput easyui-validatebox" required="true" validType="checkTimeComp[$('#beginTimeStr').val()]"
				value="<%=curDateStr%>">
			</td>
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">值班要求：</td>
			<td class="TableData"  colspan="3">
				<textarea type="text" id="demand" name="demand" class="BigTextarea easyui-validatebox"  cols="50" rows="4"></textarea></td>
		</tr><tr>
			<td nowrap class="TableData" width="100">备注：</td>
			<td class="TableData"  colspan="3">
				<textarea type="text" id="remark" name="remark" class="BigTextarea easyui-validatebox"  cols="50" rows="4"></textarea></td>
		</tr>
		<tr>
			<td colspan="4" align="center">
			  <input type="button" value="保存" class="btn btn-primary" onclick="javascript:save();"/>&nbsp;&nbsp;&nbsp;&nbsp;
			  &nbsp;&nbsp;&nbsp;&nbsp;
			  <input type="button" value="关闭" class="btn btn-default" onclick="parent.parent.$('#myModal').modal('hide');"/>
			</td>
		</tr>
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</body>

</html>
