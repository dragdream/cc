<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0); 
    int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拒绝原因</title>
</head>
<script>
//费用申请主键
var sid=<%=sid%>;
var status=<%=status%>;
//保存
function commit(){
	//获取拒绝原因
	var refusedReason=$("#refusedReason").val();
	var url=contextPath+"/projectCostController/approve.action";
    var json=tools.requestJsonRs(url,{sid:sid,status:status,refusedReason:refusedReason});
	return json.rtState;
}

</script>
<body   style="background-color: #f2f2f2">
  <form>
    <table class="TableBlock" >
       <tr>
          <td>拒绝原因：</td>
          <td>
              <textarea rows="8" cols="50" id="refusedReason" name="refusedReason"></textarea>
          </td>
       </tr>
    </table> 
  </form>
</body>
</html>