<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String sids=TeeStringUtil.getString(request.getParameter("sids"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邮件内容</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var sids="<%=sids %>";
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
		uEditorObj.setWidth(200);
	});
	
}


function sendEmail(){
	var param=tools.formToJson($("#form1"));
	
	var url=contextPath+"/hrPoolController/sendEmail.action";
	var json=tools.requestJsonRs(url,param);	
	return json;
	
}
</script>
</head>
<body onload="doInit();"  style="background-color: #f2f2f2;overflow: hidden">
    <form id="form1">
           <input type="hidden" name="sids" id="sids" value="<%=sids %>"/>
	       <table class="TableBlock">
	        <tr>
	           <td width="10%">标题：</td>
	           <td>
	              <input type="text" name="title" id="title" style="width: 200px;height: 23px"/>
	           </td>
	        </tr>
	           
	        <tr>
	          <td>内容：</td>
	           <td>
	               <textarea rows="5" cols="30" id="content" name="content" style="width: 600px"></textarea>
	           </td>
	        </tr>
	        
	        <tr>
	           <td>注意：</td>
	           <td>
	              <span style="color:red">{EmployeeName}可代替招聘者姓名</span>
	           </td>
	        </tr>
	    </table>
    
    </form>
    
</body>
</html>