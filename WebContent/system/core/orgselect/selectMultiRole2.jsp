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

String isSingle = request.getParameter("isSingle") == null  ? "" : request.getParameter("isSingle");

//回调函数
String callBackPara = request.getParameter("callBackPara") == null ? "" : request.getParameter("callBackPara")  ;
callBackPara = callBackPara.replace("\"", "\\\"");
%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>选择角色</title>
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
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

li.list-dept-item:hover{
	background-color: #f5f5f5;
	cursor: pointer;
}
li.list-dept-item.active, li.list-group-item.active:hover, li.list-group-item.active:focus {
	background-color: #fff;
	cursor: pointer;
}
</style>
<script type="text/javascript">
var selectedColor = "rgb(0, 51, 255)";
var RoleId,RoleName;
var moduleId = "<%=moduleId%>";
var isSingle = "<%=isSingle%>";//是否单个选择选择
objSelectType = '<%=objSelectType%>';

var callBackPara = "<%=callBackPara%>";
<%-- var privNoFlag = "<%=privNoFlag%>";
var privOp = "<%=privOp%>"; --%>

var parentWindowObj = xparent;
var to_id_field ;
var to_name_field ;

var single_select = false;

function doInit(){
	$("#collapseTwo").addClass("panel-collapse collapse in");
	if(isSingle == '1'){
		single_select = true;
	}
	var roleRetNameArray = parentWindowObj["roleRetNameArray"];
	if (roleRetNameArray && (roleRetNameArray.length == 2 ||  roleRetNameArray.length == 3) ) {
	    var roleCntrl = roleRetNameArray[0];
	    var roleDescCntrl = roleRetNameArray[1];
	    RoleId = parentWindowObj.document.getElementById(roleCntrl);
	    RoleName = parentWindowObj.document.getElementById(roleDescCntrl);
	  }else {
	    RoleId = parentWindowObj.document.getElementById("role");
	    RoleName = parentWindowObj.document.getElementById("roleDesc");
	  }
	to_id_field = RoleId;
	to_name_field = RoleName;
    getRoleTree();
    if(isSingle == '1'){//单选模式
    	window.init = true;
    }
  
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
	

}
/**
 * 初始化加载
 */
function doInitDept(){
	 for(var i = 0; i < id_field_array.length; i++){
		var item = id_field_array[i];
		var uuid = item.id;
		var deptNameStr = item.name;
		$("#dept_item_0").append("<a class='list-group-item dept-item active' onclick='removeitem(this)' style='text-align:center;;cursor:pointer' id='select_id_" +uuid + "' item_id='"+uuid+ "' item_name='"+deptNameStr+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptNameStr+"<i class='glyphicon glyphicon-remove' style='text-align:right;'></i></h6></a>");
		add_item(uuid, deptNameStr);
		checkOrNocheckNodesById("role_"+uuid ,true);
	  }
}

/**
 * 初始化加载
 */
function doInitDept0(){
	for(var i = 0; i < id_field_array.length; i++){
		var item = id_field_array[i];
		var uuid = item.id;
		var deptNameStr = item.name;
		$("#dept_item_0").append("<a class='list-group-item dept-item active' onclick='removeitem(this)' style='text-align:center;;cursor:pointer' id='select_id_" +uuid + "' item_id='"+uuid+ "' item_name='"+deptNameStr+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptNameStr+"<i class='glyphicon glyphicon-remove' style='text-align:right;'></i></h6></a>");
		//add_item(uuid, deptNameStr);
		checkOrNocheckNodesById("role_"+uuid ,true);
	  }
}

function getRoleTree(){
	var url = "<%=contextPath %>/orgSelectManager/selectUserPrivTree.action";
	var config = {};
	if(isSingle == '1'){
		 config = {
					zTreeId:"selectDeptZtree",
					requestURL:url,
					param:{"moduleId":moduleId,isSingle:isSingle},
		           	onClickFunc:deptOnClick,
		           	checkController:{
						enable : false,
						chkboxType: { "Y": "", "N": ""}
		               },
					async:false,
					onAsyncSuccess:doInitDept0
					
				};
	}else{
		 config = {
				zTreeId:"selectDeptZtree",
				requestURL:url,
				param:{"moduleId":moduleId,isSingle:isSingle},
				checkController:{
					enable : true,
					chkboxType: { "Y": "", "N": ""}
	               },
	       
	           	onCheckFunc:deptOnCheckFunc,
				async:false,
				onAsyncSuccess:doInitDept
				
			};
	}
	
	zTreeObj = ZTreeTool.config(config);
	
}
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.indexOf("role_")!=-1){
		setCheckStatue(true ,treeNode);
		window.close();
	} 
}
/**
 * 勾选
 */
function deptOnCheckFunc(event, treeId, treeNode) {
	var uuid = treeNode.id;
	var isChecked = treeNode.checked;
	var deptNameStr = treeNode.name;
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
	// = $.fn.zTree.getZTreeObj("selectDeptZtree");
	if(uuid == 'org_0'){//勾选全部
		var allChildNote = zTreeObj.getNodesByParamFuzzy("name", "", treeNode);
		for(var i= 0  ;i <allChildNote.length ; i ++){
			var tempNote = allChildNote[i];
			setCheckStatue(isChecked ,tempNote);
		}
	}else if(uuid.indexOf("dept_")!=-1){//勾选全部
		var allChildNote = zTreeObj.getNodesByParamFuzzy("name", "", treeNode);
		for(var i= 0  ;i <allChildNote.length ; i ++){
			var tempNote = allChildNote[i];
			setCheckStatue(isChecked ,tempNote);
		}
	}else if(uuid.indexOf("role_")!=-1){//勾选角色类型状态
		var allChildNote = zTreeObj.getNodesByParamFuzzy("name", "", treeNode);
		for(var i= 0  ;i <allChildNote.length ; i ++){
			var tempNote = allChildNote[i];
			setCheckStatue(isChecked ,tempNote);
		}
		setCheckStatue(isChecked ,treeNode);
	}else{
		setCheckStatue(isChecked ,treeNode);
	}
}
/**
 * 点击或者或者不选择状态
 */
function setCheckStatue(isChecked , treeNode){
	var uuid = treeNode.id;
	var deptNameStr = treeNode.name;
	if(uuid.indexOf("role_")!=-1){
		uuid = uuid.split("_")[1];
		if(isChecked){
			if(!existsId(uuid)){
				$("#dept_item_0").append("<a class='list-group-item dept-item active' onclick='removeitem(this)' style='text-align:center;;cursor:pointer' id='select_id_" +uuid + "' item_id='"+uuid+ "' item_name='"+deptNameStr+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptNameStr+"<i class='glyphicon glyphicon-remove' style='text-align:right;'></i></h6></a>");
				add_item(uuid, deptNameStr);
			}
		}else{
			 remove_item($("#select_id_" +uuid).attr("item_id"),$("#select_id_" +uuid).attr("item_name"));
			 $("#select_id_" +uuid).remove();
		}
	}
	checkOrNocheckNodesById(treeNode.id , isChecked);
}

function removeitem(obj){
	 checkOrNocheckNodesById("role_"+obj.getAttribute("item_id") ,false);
	 remove_item(obj.getAttribute("item_id"), obj.getAttribute("item_name"));
	 jQuery(obj).remove();
}
/**
 * 判断是否存在此部门id
 */
function existsId( id ){
	var existsObj = false;
	var obj ;
	if(id.split("_").length != 2){//勾选角色类型状态
		var obj = $("#select_id_" +id)[0];
		if(obj){
			existsObj = true;
		}
	}else{
		existsObj = true;
	}
	return existsObj;
}
/**
 * 批量删除
 */
function removeDeptAll(){
	var zTreeObj = $.fn.zTree.getZTreeObj("selectDeptZtree");
	jQuery('#dept_item_0 .dept-item').each(function(){
		remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
		 jQuery(this).remove();
	});
	zTreeObj.checkAllNodes(false);//取消所有节点
}
/**
 * 取消部门树打勾状态
  id:ID
  check：取消选中
 */
function checkOrNocheckNodesById(id , check){
	var zTreeObj = $.fn.zTree.getZTreeObj("selectDeptZtree");
	var node = zTreeObj.getNodeByParam("id",id,null);
	
	if(node){
		if(check){
			zTreeObj.checkNode(node, true,true); 
		}else{
			zTreeObj.checkNode(node, false,true); 
		}
		
	}
}
</script>
</head>
<body onload="doInit()">
<div style="margin-top:5px;">
<div class="main-block" id="block_dept" style="display:block;top:0px;padding-left: 10px;">
 <div class="panel-group" id="accordion" style="width:242px;display:;">
  <div class="panel panel-default">
    
    <div id="collapseTwo">
      <div class="panel-body">
          <div>
     		  <ul id="selectDeptZtree" class="ztree" style="overflow-x:hidden;border:0px;width:100%;height:300px;"></ul>
		  </div>
      </div>
    </div>
  </div>
 </div>
    <div class="right single" id="dept_item">
		<div class="block-right" id="dept_item_0">
			<a  class='list-group-item-header'  style="padding:8px 15px;">已选角色</a>
				<%if(!isSingle.equals("1")){ %>
			   <!--  <li href='javascript:void(0);' id="addDeptAll" class="list-group-item " style="text-align:center;">全部添加</li> -->
				<li  id="removeDeptAll" onclick="removeDeptAll();" class="list-group-item" style="text-align:center;;cursor:pointer">全部删除</li>
			   <%} %>
			</div> 
		<div id="" align="center" style="margin-top:20px;height:40px;">
		   <input type="button" class="btn btn-info" value="确定" onclick="close_window();">&nbsp;&nbsp;
		</div>
    </div>
</div>
</div>
</body></html>