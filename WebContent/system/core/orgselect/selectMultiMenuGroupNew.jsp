<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String moduleId = request.getParameter("moduleId");
if (moduleId == null) {
  moduleId = "";
}
String privNoFlag = request.getParameter("privNoFlag");
if (privNoFlag == null || "".equals(privNoFlag)) {
  privNoFlag = "0";
}
String privOp = request.getParameter("privOp");
if (privOp == null) {
  privOp = "";
}
String objSelectType = request.getParameter("objSelectType");
if (objSelectType == null) {
	objSelectType = "";
}

String deptUuid = request.getParameter("deptUuid");
if (deptUuid == null) {
	deptUuid = "";
}

//回调函数参数
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
<%
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<title>选择菜单组</title>
<link rel="stylesheet" href ="<%=cssPath %>/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.css"/>
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
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
var isSingle = false;//是否是单选择
var uuid = "<%=loginUser.getUuid()%>";
var userId = "<%=loginUser.getUserId()%>";
var deptUuid = "<%=deptUuid%>";
objSelectType = '<%=objSelectType%>';

var callBackPara = "<%=callBackPara%>";
<%-- var privNoFlag = "<%=privNoFlag%>";
var privOp = "<%=privOp%>"; --%>

var parentWindowObj = xparent;
  
var to_id_field ;
var to_name_field ;

var single_select = false;

function doInit(){
	var roleRetNameArray = parentWindowObj["roleRetNameArray"];
	if (roleRetNameArray && (roleRetNameArray.length == 2 ||  roleRetNameArray.length == 3) ) {
	    var roleCntrl = roleRetNameArray[0];
	    var roleDescCntrl = roleRetNameArray[1];
	    RoleId = parentWindowObj.document.getElementById(roleCntrl);
	    RoleName = parentWindowObj.document.getElementById(roleDescCntrl);
	  }else {
	    RoleId = parentWindowObj.document.getElementById("menuGroup");
	    RoleName = parentWindowObj.document.getElementById("menuGroupDesc");
	  }
	 to_id_field = RoleId;
	 to_name_field = RoleName;
	  
	var url = contextPath +  "/teeMenuGroup/getMenuGroupByDeptUuid.action";
	var para = {deptUuid:deptUuid};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
	    for(var i = 0; i < dataList.length; i++){
			var roleId = dataList[i].uuid;
			
			var menuGroupTypeName = "其他"
			if(dataList[i].menuGroupType == '01' ){
				menuGroupTypeName = "执法";
			}else if(dataList[i].menuGroupType == '02'){
				menuGroupTypeName = "监督";
			}
			
			var roleName = dataList[i].menuGroupName + " 【" + menuGroupTypeName + "】" ;
			if(userId!="admin" && roleId==1){
				continue;
			}
		    $("#dept_item_0").append("<a class='list-group-item active' item_id='"+roleId+ "' item_name='"+roleName+"'><h6 class='list-group-item-heading'>"+ roleName+"</h6></a>");
		} 
		if(dataList.length < 0){
			 $("#dept_item_0").append("<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关菜单组！</h6></div>");
		}
	 	load_init();  
	 	load_init_item();
	    //默认加载角色选中状态
	    init_item('dept');
	    
		to_id_field_value = to_id_field.value;
		to_name_field_value = to_name_field.value;
		var dataList = new Array();
		var dataNameList  = new Array();
		if(to_id_field_value != ""){
			dataList = to_id_field_value.split(",");
			dataNameList = to_name_field_value.split(",");
		}
		//转数组对象
		for(var i =0 ; i <dataList.length ; i++){
			if(dataList[i] && dataList[i] != "" ){
				var item = {id:dataList[i] , name:dataNameList[i]};
				id_field_array.push(item);
			}
			
		}
		doInitMenuGroup(); 
	}else{
		alert(jsonRs.rtMsg);
	}

}



function doInitMenuGroup(){
	 for(var i = 0; i < id_field_array.length; i++){
		var item = id_field_array[i];
		var uuid = item.id;
		var deptNameStr = item.name;
		add_item(uuid, deptNameStr);
	}
	jQuery('#dept_item_0 .dept-item').live('click', function(){
		 remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
		 jQuery(this).remove();
		 
	}); 
	
}
</script>
</head>
<body onload="doInit()" >


<div class="main-block" id="block_dept" style="display:block;">
   <div class="right single" align="center" style="margin:0px 10px 10px 10px;left:5px;" id="dept_item">
		<div class="block-right" id="dept_item_0">
			<a href='javascript:void(0);' class='list-group-item-header'>模块权限组</a>
			<li href='javascript:void(0);' id="addAll" class="list-group-item" style="text-align:center;">全部添加</li>
			<li href='javascript:void(0);' id="removeAll" class="list-group-item" style="text-align:center;">全部删除</li>
		</div> 
	
    <div id="" align="center" style="margin-top:20px;height:40px;">
  	 	<input type="button" class="btn btn-info" value="确定" onclick="close_window();">&nbsp;&nbsp;
	</div>
 </div>
</div>
</body></html>