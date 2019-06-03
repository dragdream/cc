<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
  //获取问题的主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问题办理</title>
</head>
<script>
var sid=<%=sid%>;//问题的主键

//验证
function check(){
	var result=$("#result").val();
	if(result==""||result==null){
		$.MsgBox.Alert_auto("请填写处理结果！");
		return false;
	}
	return true;
}
//提交
function commit(){
	if(check()){
		var result=$("#result").val();
		var url=contextPath+"/projectQuestionController/handle.action";
		var json=tools.requestJsonRs(url,{sid:sid,result:result});
		return json.rtState;
	}
}

</script>

<body style="background-color: #f2f2f2">
   <table class="TableBlock" style="margin-top: 10px">
      
      <tr>
         <td class="TableData" style="text-indent: 10px">处理结果汇报：</td>
         <td class="TableData">
            <textarea rows="8" cols="60" name="result" id="result"></textarea>
         </td>
      </tr>
   </table>
</body>
</html>