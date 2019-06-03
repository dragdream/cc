<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<title>新增角色</title>
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css"/>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>


<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>

<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/orgselect.js"></script>




<script type="text/javascript">

function doInit(){
	
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/userRoleController.action?add";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
		//	var data = jsonRs.rtData;
		$.messager.show({
			msg : '角色添加成功!',
			title : '提示'
		});
		window.location.href = "<%=contextPath%>/system/core/org/role/manageRole.jsp";
	
		}else{
			if(jsonRs.rtData == 1){
				$("#roleNo").select();
				$("#roleNo").focus();
			}
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	    return $("#form1").form('validate'); 
}

function backIndex(){

	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}



</script>

</head>
<body onload="doInit()">
<table border="0" width="100%" cellspacing="0" cellpadding="3"
	class="small" align="center">
	<tr>
		<td class="Big">&nbsp;&nbsp; <span class="big3"> 新增角色</span></td>
	</tr>
</table>
<br>
<center style="width:60%;">

	
	
	
	
	<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="95%" align="center">

   <tr>
    <td nowrap class="TableHeader" colspan="2">角色基本信息</td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">角色名称：<span style=""></span></td>
    <td nowrap class="TableData">
        <input type="text" name="roleName" class="easyui-validatebox"  required="true"  size="10" maxlength="20" >&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">角色排序号：</td>
    <td nowrap class="TableData">
    <input type="text" name="roleNo" id="roleNo" size="10"   validType ='number[]' maxlength="30" class="easyui-validatebox"  required="true">
        &nbsp;整数类型 </td>
   </tr>
   <tr>
	    <td nowrap  class="" colspan="2" align="center">
	        <input type="button" value="保存" class="btn btn-primary" title="保存用户" onclick="doSave()" >&nbsp;&nbsp;
	        <input type="button" value="返回" class="btn btn-primary" title="返回" onClick="backIndex();">
	    </td>
   </tr>
   
</table>
  </form>
  </center>
</body>
</html>
 