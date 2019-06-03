<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
   Date currDate=new Date();
   String currDateStr=sdf.format(currDate);
%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>流程归档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript">
var currDateStr="<%=currDateStr%>";
function doInit(){
	//给时间范围  和   归档描述设置默认范围
	$("#archiveDateStr").val(currDateStr);
	$("#archiveDesc").val(currDateStr+"之前工作流程的归档数据");
	
	//渲染数据源列表
	renderDataSource();
}






//点击确定按钮
function commit(){
	if(check()){
		var archiveDateStr=$("#archiveDateStr").val();
		var archiveDesc=$("#archiveDesc").val();	
		parent.window.location.href=contextPath+"/system/core/workflow/flowrun/archive/info.jsp?archiveDateStr="+archiveDateStr+"&&archiveDesc="+archiveDesc;
		
	}	
}

//验证
function check(){
	var archiveDateStr=$("#archiveDateStr").val();
	if(archiveDateStr==""||archiveDateStr==null){
		$.MsgBox.Alert_auto("请选择归档日期！");
		return false;
	}else{//验证同一个归档日期不能进行两次归档操作
		var url=contextPath+"/flowArchiveController/getArchiveListByDate.action";
	    var json=tools.requestJsonRs(url,{archiveDateStr:archiveDateStr});
	    if(json.rtState){
	    	var data=json.rtData;
	    	if(data==true||data=="true"){
	    		return true;
	    	}else{
	    		$.MsgBox.Alert_auto("同一个归档日期不能进行两次归档操作，请重新选择！");
	    		return  false;
	    	}
	    }
	}
	return true;
}


//归档日期改变的时候改变归档描述
function dateChange(){
	var archiveDateStr=$("#archiveDateStr").val();
	$("#archiveDesc").val(archiveDateStr+"之前工作流程的归档数据");
}



</script>

</head>
<body onload="doInit();" style="background-color:#f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">归档日期：</td>
			<td class="TableData">
				  <input
					id="archiveDateStr" type='text' name="archiveDateStr"
					style="width: 170px;"
					class="Wdate" required
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
					value="" onchange="dateChange();"/>
				</span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">归档描述：</td>
			<td class="TableData">
				<textarea  id="archiveDesc" name="archiveDesc" class="" required cols="50" rows="4"></textarea></td>
		</tr>
		<tr class="TableData">
			<td nowrap style="text-indent: 10px">友好提示：</td>
			<td nowrap align="left" id="sms">
				<span style="color:red;font-size: 12px;">(1)该操作具有一定危险性，非OA管理员严禁操作</span><br>
				<span style="color:red;font-size: 12px;">(2)在做此操作之前，请您联系技术支持了解工作流数据归档的具体要求及注意事项</span><br>
				<span style="color:red;font-size: 12px;">(3)在做此操作之前，请您做好数据库备份工作</span><br>
				<span style="color:red;font-size: 12px;">(4)仅支持归档工作流程相关的数据，不支持级联归档关联工作流程的模块数据<br/>（例如公文、用品、合同等）。归档后可能出现上述模块中无法查到已归档的流程数据</span>
		    </td>
		</tr>
	</table>
</form>
</body>

</html>
