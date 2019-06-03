<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
//类型 1暂停  2恢复  3办结
  int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
//当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>暂停申请</title>
</head>
<script>

function save(){
	if(check()){
		var url=contextPath+"/supervisionApplyController/add.action";
		var param=tools.formToJson("#form1");
		
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
	}
	
}

//验证
function check(){
	//获取内容
	var content=$("#content").val();
	if(content===null||content==""){
		$.MsgBox.Alert_auto("请填写申请内容！");
		return false;
	}
	return true;
}
</script>
<body style="background-color: #f2f2f2">
<form id="form1">
<input type="hidden" value="<%=supId%>" id="supId" name="supId"/>
<input type="hidden" value="<%=type%>" id="type" name="type"/>
   <table class="TableBlock">
       <tr>
          <td style="text-indent:10px">申请内容：</td>
          <td>
             <textarea id="content" name="content" style="width: 450px;height:100px"></textarea>
          </td>
       </tr>
   </table>
</form>
</body>
</html>