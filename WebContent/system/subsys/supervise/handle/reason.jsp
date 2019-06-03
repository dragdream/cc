<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
   int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒绝原因</title>
</head>
<script>
var sid=<%=sid%>;
var status=<%=status%>;
//保存
function save(){
	var reason=$("#reason").val();
	  var url = contextPath + "/supervisionApplyController/approve.action";
	  var para = {sid:sid,status:status,reason:reason};
	  var json = tools.requestJsonRs(url,para);
	  return json.rtState;
	
}

</script>
<body style="background-color: #f2f2f2">
   <table class="TableBlock">
      <tr>
         <td style="text-indent: 10px">拒绝理由：</td>
         <td>
            <textarea name="reason" id="reason" style="width:480px;height:100px"></textarea>
         </td>
      </tr>
   </table>
</body>
</html>