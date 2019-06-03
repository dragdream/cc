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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>选择菜单</title>
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/index1.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>
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
var ZTreeObj;
function doInit(){
	$("#collapseTwo").addClass("panel-collapse collapse in");
	if(isSingle == '1'){
		single_select = true;
	}
	var sysMenuNameArray = parentWindowObj["sysMenuNameArray"];
	if (sysMenuNameArray && (sysMenuNameArray.length == 2 ||  sysMenuNameArray.length == 3) ) {
	
	    var roleCntrl = sysMenuNameArray[0];
	    var roleDescCntrl = sysMenuNameArray[1];
	    deptId = parentWindowObj.document.getElementById(roleCntrl);
	    deptName = parentWindowObj.document.getElementById(roleDescCntrl);
	  }else {
		deptId = parentWindowObj.document.getElementById("dept");
	    deptName = parentWindowObj.document.getElementById("deptDesc");
	  }
	to_id_field = deptId;
	to_name_field = deptName;

	
    getSysMenuTree();

}


function getSysMenuTree(){
	var url = "<%=contextPath %>/sysMenu/getSysMenuTree.action?checkIds=" + to_id_field.value;
	var config = {
			zTreeId:"selectSysMenuZtree",
			requestURL:url,
			param:{"para1":"111"},
			checkController:{
				enable : true,
				chkboxType: { "Y": "s", "N": "s" }
               },
			async:false,
			asyncExtend:true,
			onAsyncSuccess:onAsyncSuccessFunc,
			onClickFunc:deptOnClick
			
		};
	zTreeObj = ZTreeTool.config(config);
	
}
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
}

/**
 * zNodesLength ：树节点数
 * rtMsg： 返回的json rtMsg 信息
 */
function onAsyncSuccessFunc(zNodesLength , jsonData){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
	/* //获取人员数组
	var personData = jsonData.rtData.personData;
	var currDept = jsonData.rtData.currDept;
	var currDeptName =  "选择人员";
    if(currDept){
    	currDeptName = currDept.deptName;
    }
	dataLoad(personData ,currDeptName); */
}
/**
 * 获取选中的菜单
 */
function setMenuValue(){
	var nodes = ZTreeObj.getCheckedNodes(true);//获取选中节点
	var ids = "";
	var names = "";
	for(var i = 0;i< nodes.length ;i++){
		var node = nodes[i];
		var childrenNodes = node.children;
		if(!childrenNodes ){//是否是叶子节点
			ids = ids + node.extend1 + ",";
			names = names + node.name + ",";
		}
	}
	
	to_id_field.value = ids;
	to_name_field.value = names;
	close_window();
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
          	菜单选择
        </a>
      </h5>
    </div>
    <div id="collapseTwo">
      <div class="panel-body">
          <div>
     		  <ul id="selectSysMenuZtree" class="ztree" style="overflow-x:hidden;overflow-y:auto;border:0px;height:300px;"></ul>
		  </div>
      </div>
    </div>
  </div>
 </div>
    
</div>
</div>
<div align="center" style="padding-top:5px;">
	<input type="button" value="确定" class="btn btn-primary" onclick="setMenuValue();">
</div>
</body></html>