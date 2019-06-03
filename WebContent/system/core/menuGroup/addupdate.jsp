<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp" %>

<%
  String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>菜单功能</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">

var uuid = '<%=uuid%>';
var parentWindowObj = window.dialogArguments;
function doInit(){
	//添加例子一
	if(uuid != ""){
		var url = "<%=contextPath %>/teeMenuGroup/getMenuGroup.action";
		var para = {uuid:uuid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj){
			var json = jsonObj.rtData;
			if(json.uuid){
				bindJsonObj2Cntrl(json);
			}
			
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	
}

/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath %>/teeMenuGroup/addOrUpdateMenuGroup.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){

	       xparent.window.location = xparent.window.location ;
			/* $.messager.show({
				msg : '保存成功！！',
				title : '提示'
			}); */
			
			CloseWindow();
			
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}
function closeWindow(){
	//parentWindowObj.window.location = parentWindowObj.window.location ;
	CloseWindow();
}
function check() {
	return $("#form1").form('validate'); 
}
</script>

</head>
<body onload="doInit()" style="padding-top:10px;">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big">
      
        <%
         if(TeeUtility.isNullorEmpty(uuid)){
        	
        	  %>
        	  <span class="big3">
        	  新增模块权限
        	  </span>
        	  <%
         }else{
        	 %>	 
        	  <span class="big3">
        	 编辑模块权限
        	  </span>
        	 
         <%}
        %></td>
    </tr>
  </table><br>
  <center>
  <form   method="post" name="form1" id="form1">
    <table class="TableBlock"  width="90%">
	  <tr class="TableData">
		<td>权限类型</td>
		<td>
	       <select class="BigSelect" id="menuGroupType" name="menuGroupType">
	         <option value="00">其他</option>
	         <option value="01">执法</option>
	         <option value="02">监督</option>
	       </select>
	    </td>
	  </tr>
	  <tr class="TableData">
		<td>权限名称</td>
		<td><input type="text" name="menuGroupName" class="easyui-validatebox BigInput" required="true"  maxlength="100"/></td>
	  </tr>
	  <tr  class="TableData">
	    <td>权限排序</td> 
		<td><input  type="text" name="menuGroupNo" class="easyui-validatebox BigInput"  required="true"   maxlength="3" maxlengthvalidType ='positivIntege[]'/>整数类型</td>
	  </tr>
      <tr  class="TableData">
	    <td colspan="2" align="center"><input type="button" value="保存" class="btn btn-primary" onclick="doSave();">&nbsp;&nbsp;
		  <input type="text" id="uuid" name="uuid" style="display:none;" value='0'/>
	      <input type="button" value="关闭" class="btn btn-primary" onclick="closeWindow();">&nbsp;&nbsp;
	    </td>
	   </tr>
	 </table>
   </form>
   
   </center>
</body>
</html>