<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int attachSid=TeeStringUtil.getInteger(request.getParameter("attachSid"),0);
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成版本</title>
<script type="text/javascript">
var attachSid=<%=attachSid%>;
var sid=<%=sid%>;
function doInit(){
	  $("body").on("click","#rease",function(){
		  if($(this).val()=="在此输入备注"){
			  $(this).val("");
		  }
		  
	 });
}


function doCutSubmit(){
	var rease=$("#rease").val();
    var url = "<%=contextPath %>/teeFileHistoryController/addFileHistory.action";
    var jsonRs = tools.requestJsonRs(url,{rease:rease,sid:sid,attachSid:attachSid});
    return jsonRs.rtState;
}

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
 <textarea id="rease" style="width: 260px;height: 100px;margin-top:5px;">在此输入备注</textarea>
</body>
</html>