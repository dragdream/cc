<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>新建仓库</title>
	<script type="text/javascript" charset="UTF-8">
	var sid = <%=sid%>;
	$(function() {
		if(sid!=0){
			var json = tools.requestJsonRs(contextPath+"/repDepository/get.action",{sid:sid});
			bindJsonObj2Cntrl(json.rtData);
		}
	});
	
	function save(){
		if(!$("#form1").form("validate")){
			return;
		}
		
		var para = tools.formToJson($("#form1"));
		var url = "";
		if(sid!=0){
			url = contextPath+"/repDepository/update.action";
		}else{
			url = contextPath+"/repDepository/save.action";
		}
		var json = tools.requestJsonRs(url,para);
		alert(json.rtMsg);
		if(json.rtState){
			window.location = "depos_list.jsp";
		}
	}
	</script>
</head>
<body >
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">仓库管理</span>
</div>
<br/>
<table class="TableBlock"  id="form1" style='margin:0 auto;width:60%;'>
  <tr>
	<td nowrap align="left" width="20%" class="TableContent">仓库名称<span style='color:red'>*</span>：</td>
    <td nowrap align="left" class="TableData" width="80%">
    	<input type="text" class="BigInput easyui-validatebox" required id="name" name="name"/>
    </td>  
  </tr>
  <tr>
  	<td nowrap align="left" width="120" class="TableContent">仓库编码<span style='color:red'>*</span>：</td>
    <td class="TableData" width="180">
    	<input type="text" class="BigInput easyui-validatebox" required id="code" name="code"/>
    </td>  	
  </tr>

  <tr>

  	<td nowrap align="left" width="120" class="TableContent">库管员：</td>

    <td class="TableData" width="180">
    	<input type="text" class="BigInput readonly" id="managerName" name="managerName" readonly/>
    	<input type="hidden" id="managerId" name="managerId"/>
    	<br/>
    	<a href="javascript:void(0)" onclick="selectSingleUser(['managerId','managerName'])">选择</a>
    	&nbsp;
    	<a href="javascript:void(0)" onclick="clearData('managerId','managerName')">清空</a>
    </td>
  </tr>
  <tr>
  	<td colspan="2"  align='center'>
  		<button class="btn btn-primary" onclick="save()">保存</button>
  		&nbsp;&nbsp;
  		<button class="btn btn-default" onclick="window.location = 'depos_list.jsp'">返回</button>
  		<input type="hidden" name="sid" value="<%=sid %>" />
  	</td>
  </tr>
</table>
</body>
</html>