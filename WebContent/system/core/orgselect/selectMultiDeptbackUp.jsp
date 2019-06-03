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
<title>选择部门</title>
<link rel="stylesheet" href ="<%=cssPath %>/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.css"/>
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
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
var deptId,deptName;
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
	var deptRetNameArray = parentWindowObj["deptRetNameArray"];
	if (deptRetNameArray && (deptRetNameArray.length == 2 ||  deptRetNameArray.length == 3) ) {
	    var roleCntrl = deptRetNameArray[0];
	    var roleDescCntrl = deptRetNameArray[1];
	    deptId = parentWindowObj.document.getElementById(roleCntrl);
	    deptName = parentWindowObj.document.getElementById(roleDescCntrl);
	  }else {
		deptId = parentWindowObj.document.getElementById("dept");
	    deptName = parentWindowObj.document.getElementById("deptDesc");
	  }
	to_id_field = deptId;
	to_name_field = deptName;
    getDeptTree();
    getDeptStr('');
 

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
 * 获取部门select字符串
 */
function getDeptStr(uuid){
	var url = contextPath +  "/orgSelectManager/getSelectDept.action";
	var para = {uuid:uuid};
	var jsonRs = tools.requestJsonRs(url,para);
	
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		$("a").remove(".list-group-item");
	    for(var i = 0; i < dataList.length; i++){
			var roleId = dataList[i].uuid;
			var roleName = dataList[i].deptName;
			var deptNameStr = dataList[i].deptNameStr;//处理后部门名称
			var deptLevel = dataList[i].deptLevel;//级别
			var deptParentId = dataList[i].deptParentId;
			var roleIdObj = null;
			if(deptParentId){
				 roleIdObj = document.getElementById("select_id_" + deptParentId);
			}
			if(roleIdObj){

				var deptLevelTemp =  $(roleIdObj).attr("deptLevel");//获取级别
				//获取上级部门  and 获取同辈之后的所有元素 and 级别是同一级别的
				var temp = $(roleIdObj).nextAll("[deptLevel='"+ deptLevelTemp+"']").first();
				if(temp){
					temp.before("<a class='list-group-item active' href='javascript:void(0);' style='text-align:left;padding-left:20px;' id='select_id_" +roleId + "' item_id='"+roleId+ "' item_name='"+roleName+"'  deptLevel='"+deptLevel+ "'><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
				}else{//如果没有则在上级后面添加
					$(roleIdObj).after("<a class='list-group-item active' href='javascript:void(0);' style='text-align:left;padding-left:20px;' id='select_id_" +roleId + "' item_id='"+roleId+ "' item_name='"+roleName+"'><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
						
				} 
				 
			}else{
			    $("#dept_item_0").append("<a class='list-group-item active' href='javascript:void(0);' style='text-align:left;padding-left:20px;' id='select_id_" +roleId + "' item_id='"+roleId+ "' item_name='"+roleName+"' deptLevel='"+deptLevel+ "'><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
			}
		} 
	    
		if(dataList.length < 0){
			 $("#dept_item_0").append("<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关部门！</h6></div>");
		}
		
		load_init();  
		load_init_item();  
	    //默认加载角色选中状态
	    init_item('dept');
	    
		
		$(".list-group-item").unbind( "click" ,remove_item);
		$(".list-group-item").unbind( "click" ,add_item);
	}else{
		alert(jsonRs.rtMsg);
	} 
}

function getDeptTree(){
	var url = "<%=contextPath %>/orgSelectManager/getSelectDeptTree.action";
	var config = {
			zTreeId:"selectDeptZtree",
			requestURL:url,
			param:{"para1":"111"},
			async:false,
			onClickFunc:deptOnClick
			
		};
	zTreeObj = ZTreeTool.config(config);
	
}
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 1){
		getDeptStr(uuid);
	}
	
}


</script>
</head>
<body onload="doInit()">
<div style="margin-top:5px;">
<div class="main-block" id="block_dept" style="display:block;top:0px;padding-left: 10px;">
 <div class="panel-group" id="accordion" style="width:242px;display:;">
  <div class="panel panel-default">
    <div class="panel-heading" style="padding:7px 15px;">
      <h5 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
          	按部门选择
        </a>
      </h5>
    </div>
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
			<a href='javascript:void(0);' class='list-group-item-header'  style="padding:8px 15px;">部门</a>
				<%if(!isSingle.equals("1")){
			
					
				 %>
			    <li href='javascript:void(0);' id="addAll" class="list-group-item" style="text-align:center;">全部添加</li>
				<li href='javascript:void(0);' id="removeAll" class="list-group-item" style="text-align:center;">全部删除</li>
			   <%} %>
			</div> 
		<div id="" align="center" style="margin-top:20px;height:40px;">
		   <input type="button" class="btn btn-info" value="确定" onclick="close_window();">&nbsp;&nbsp;
		</div>
    </div>
</div>
</div>
</body></html>