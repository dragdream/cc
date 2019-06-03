<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>添加表单分类</title>
	<script>
		var parentWindow = window.dialogArguments;
		function commit(){
			if(!$("#form").form('validate')){
				return false;
			}
			
			var url = "<%=contextPath%>/formSort/save.action";
			var para = tools.formToJson($("#form"));
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg, 'success');
			}else{
				top.$.jBox.tip(json.rtMsg, 'error');
			}
			return true;
		}
		
	</script>
</head>
<body>
<br/>
<form id="form">

<table class="TableBlock" width="95%" align="center">
 
   <tr>
    <td nowrap class="TableHeader" colspan="2" style='vertical-align: middle;display:none'>
	<span  class="imgMiddleSpan">添加分类</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120"><font color="red">*</font>分类名称：</td>
    <td nowrap class="TableData">
       <input type="text" name="sortName" id="sortName" class="BigInput easyui-validatebox" required="true" validType="length[1,20]"/>
    </td>
   </tr>
    <tr>
    <td nowrap  class="TableControl" colspan="2" align="center"  style="display:none">
       <input type="button" class="btn btn-default" value="确定" onclick="commit()"/>
    </td>
 	</tr>
</table>
</form>
</body>

</html>