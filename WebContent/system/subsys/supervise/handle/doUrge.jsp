<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务催办</title>
</head>
<script>
var supId=<%=supId%>;//任务主键

//提交
function save(){
	if(check()){
		var url=contextPath+"/supUrgeController/add.action";
		var param=tools.formToJson("#form1");
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
	}
}

//验证
function check(){
	var content=$("#content").val();
	if(content==""||content==null){
		$.MsgBox.Alert_auto("请填写催办内容！");
		return false;
	}
	return true;
}
</script>
<body style="background-color: #f2f2f2">
  <form id="form1">
  <input type="hidden" name="supId" id="supId" value="<%=supId %>" />
     <table class="TableBlock" width="100%">
        <tr>
           <td style="text-indent: 10px">催办内容：</td>
           <td>
              <textarea style="width: 400px;height: 80px" id="content" name="content"></textarea>
           </td>
        </tr>
        
        <tr>
           <td style="text-indent: 10px">是否包含下级任务：</td>
           <td>
              <input type="radio" name="isIncludeChildren" value="1"/>是
              &nbsp;&nbsp;&nbsp;
              <input type="radio" name="isIncludeChildren" value="0" checked="checked"/>否
           </td>
        </tr>
     </table>
  </form>
   
</body>
</html>