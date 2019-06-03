<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	String  ids=TeeStringUtil.getString(request.getParameter("ids"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>档案移交</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript">
var ids = "<%=ids%>";

function doInit(){
	renderDamBox();
}

//渲染预归档分类
function renderDamBox(){
	var url=contextPath+"/damBoxController/getAllBoxByLoginUser.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			 for(var i=0;i<data.length;i++){
				 $("#boxId").append("<option value="+data[i].sid+">"+data[i].boxNo+"</option>");		 
			 }
		}
	}
}
/**
 * 分配卷盒
 */
function distributeBox(){
	if(checkFrom()){
		var url = contextPath + "/TeeDamFilesController/distributeBox.action";
		var para =  tools.formToJson($("#form1")) ;
		var json = tools.requestJsonRs(url,para);
		return json;
	}
}
/**
 * 校验
 */
function checkFrom(){
	 var boxId=$("#boxId").val();
	 if(boxId==""||boxId==null||boxId==0){
		 $.MsgBox.Alert_auto("请选择所属卷盒！");
		 return false;
	 }
	 return true;
}


	


</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData"  style="width: 100px;text-indent: 5px;">所属卷盒：</td>
			<td class="TableData">
				<select id="boxId" name="boxId">
				   <option value="">请选择</option>
				</select>
			</td>
		</tr>
		
	</table>
	<input id="ids" name="ids" type="hidden" value="<%=ids %>"> 
</form>
</body>
<script>
$("#form1").validate();
</script>
</html>
