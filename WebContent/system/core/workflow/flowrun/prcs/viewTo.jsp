<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
				0);
	    int frpSid = TeeStringUtil.getInteger(request
			.getParameter("frpSid"), 0);	
	%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>流程传阅</title>
</head>
<script>
//流程传阅
function commit(){
	if(check()){
		var url=contextPath+"/flowRun/view.action";
		var param=tools.formToJson("#form1");
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
	}
}

//验证
function check(){
	var viewPersonDesc=$("#viewPersonDesc").val();
	if(viewPersonDesc==""||viewPersonDesc==null){
		$.MsgBox.Alert_auto("请选择传阅人！");
		return false;
	}
	return true;
}
</script>
<body style="background-color: #f2f2f2">
<form id="form1" name="form1">
<input type="hidden" name="runId" value="<%=runId%>"/>
<input type="hidden" name="frpSid" value="<%=frpSid%>"/>
<table class="TableBlock" style="border:0px" style="font-size:12px">
    	<tr>
    		<td style="border:0px;font-size:12px">传阅人：</td>
    		<td style="border:0px;font-size:12px">
    			<textarea id="viewPersonDesc" class="BigTextarea" readonly style="height:100px;width:430px"></textarea>
		    	<input id="viewPerson" type="hidden" name="viewPerson"/>
		    	
		    	&nbsp;<a href="javascript:void(0)" onclick="selectUser(['viewPerson','viewPersonDesc'],'14')">选择</a>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="clearData('viewPerson','viewPersonDesc')">清空</a>
    		</td>
    	</tr>
    </table>

</form>
    
</body>
</html>