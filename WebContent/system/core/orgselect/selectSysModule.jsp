<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String moduleId = request.getParameter("moduleId");
if (moduleId == null) {
  moduleId = "";
}
String objSelectType = request.getParameter("objSelectType");
if (objSelectType == null) {
	objSelectType = "";
}

String isSingle = request.getParameter("isSingle") == null  ? "" : request.getParameter("isSingle");

String callBackPara = request.getParameter("callBackPara") == null ? "" : request.getParameter("callBackPara")  ;
callBackPara = callBackPara.replace("\"", "\\\"");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 
<%@ include file="/header/header.jsp"%>
<title>选择模块</title>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.css"/>
<link rel="stylesheet" href ="<%=cssPath %>/style.css"/>
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/orgselect/orgselect.js"></script>
<style type="text/css">
/*** 定义全部添加和全部删除的样式  **/
li.list-group-item:hover{
	background-color: #f5f5f5;
	cursor: pointer;
}
li.list-group-item.active, li.list-group-item.active:hover, li.list-group-item.active:focus {
	background-color: #fff;
	cursor: pointer;
}
</style>
<script type="text/javascript">
var selectedColor = "rgb(0, 51, 255)";
var RoleId,RoleName;
var moduleId = "<%=moduleId%>";
var single_select = false;//是否是单选择
var isSingleStr = "<%=isSingle%>";
objSelectType = '<%=objSelectType%>';

var callBackPara = "<%=callBackPara%>";
<%-- var privNoFlag = "<%=privNoFlag%>";
var privOp = "<%=privOp%>"; --%>

var parentWindowObj = xparent;
  
var to_id_field ;
var to_name_field ;



function doInit(){
	if(isSingleStr == '1'){
		single_select = true;
	}
	var sysModuleArray = parentWindowObj["sysModuleArray"];
	if (sysModuleArray && (sysModuleArray.length == 2 ||  sysModuleArray.length == 3) ) {
	    var roleCntrl = sysModuleArray[0];
	    var roleDescCntrl = sysModuleArray[1];
	    RoleId = parentWindowObj.document.getElementById(roleCntrl);
	    RoleName = parentWindowObj.document.getElementById(roleDescCntrl);
	}else {
	    RoleId = parentWindowObj.document.getElementById("sysModule");
	    RoleName = parentWindowObj.document.getElementById("sysmModuleDesc");
	}
	to_id_field = RoleId;
	to_name_field = RoleName;
	var url = contextPath +  "/orgSelectManager/getSysModule.action";
	var para = {moduleId:moduleId};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		var selects = document.getElementById("userRoleStr");
		var temp = true;
		for (var property in dataList) {
			var value = dataList[property];  
			var roleId = property;
			var roleName = value;
			$("#dept_item_0").append("<a class='list-group-item active' style='cursor:pointer' item_id='"+roleId+ "' item_name='"+roleName+"'><h6 class='list-group-item-heading'>"+ roleName+"</h6></a>");
			temp = false;
		}
		if(temp){
			 $("#dept_item_0").append("<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关角色！</h6></div>");
		}
	 	load_init();  
	 	load_init_item(); 
	    //默认加载角色选中状态
	    init_item('dept');
	}else{
		alert(jsonRs.rtMsg);
	}
}
</script>
</head>
<body onload="doInit()" >
<div class="main-block" id="block_dept" style="display:block;">
    <div class="right single" align="center" style="margin:10px 10px 10px 10px;left:5px;padding-top:10px" id="dept_item">
	   <div class="block-right" id="dept_item_0">
		   <a href='javascript:void(0);' class='list-group-item-header'>选择模块</a>
			<li href='javascript:void(0);' id="addAll" class="list-group-item" style="text-align:center;">全部添加</li>
			<li href='javascript:void(0);' id="removeAll" class="list-group-item" style="text-align:center;">全部删除</li>
	   </div> 
       <div id="" align="center" style="margin-top:20px;height:40px;">
  	 	   <input type="button" class="btn btn-info" value="确定" onclick="close_window();">&nbsp;&nbsp;
	   </div>
  </div>

  
</div>
</body>
</html>