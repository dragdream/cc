<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
int planStatus = TeeStringUtil.getInteger(request.getParameter("planStatus"), 1);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<title>批准/不批准</title>
<script type="text/javascript">
var sid = "<%=sid%>";


function doApproval(callback){
	if(checkFrom()){
		var url = contextPath + "/trainingPlanController/setPlanStatus.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url, para);
		if (jsonObj.rtState) {
			callback(jsonObj);
			parent.BSWINDOW.modal("hide");
		} else {
			alert(jsonObj.rtMsg);
		}
	}
	return false;
}

function checkFrom(){
	 return $("#form1").form('validate'); 

}
</script>
</head>
<body >
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<input type="hidden" name="planStatus" id="planStatus" value="<%=planStatus%>">

	<div>
		<%if(planStatus == 1){%>
		 <font color='red'>确认要审批通过此计划申请吗？请填写审批意见： </font>
	    <%}else{%>
			<font color='red'>确认要驳回此计划申请吗？请填写驳回理由： </font>
		<% } %>
		<textarea rows="5" cols="60" id="approveComment" name="approveComment" maxlength="400" class="BigTextarea easyui-validatebox" required="true"  ></textarea>
	</div>
</form>

</body>
</html>