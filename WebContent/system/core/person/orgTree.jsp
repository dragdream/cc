<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>组织树</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath %>/bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>

<style type="text/css">
.menuList{
	padding-bottom:5px;
	padding-top:5px;
}
</style>

<script type="text/javascript" src="<%=contextPath%>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript">

/**
 * 马上加载
 */
 var zTreeObj ;
 function doInit(){
	// setMenu();
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		//alert(jsonObj);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/personManager/getOrgTree.action";
				var config = {
					zTreeId:"orgZtree",
					requestURL:url,
					param:{"para1":"111"},
					onClickFunc:personOnClick,
					onAsyncSuccess:onUserAsyncSuccess
						
				};
				zTreeObj = ZTreeTool.config(config);
				//expandNodes(zTreeObj);
			}else{
				alert("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
		//
		//setTimeout('expandNodes()',500);
 }
 
 function onUserAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后
		 expandNodes();
		 
}
 /**
   *第一级展开部门
   */
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("orgZtree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync ) {
		//expandNodes(nodes[0].children);
	}
/* 	alert(nodes[0].id)
	for (var i=0, l=nodes.length; i<l; i++) {
		zTreeObj.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync ) {
			expandNodes(nodes[i].children);
		}
	} */
}


/**
 * 点击节点
 */
function personOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'dept'){
		parent.personinput.location = "<%=contextPath%>/system/core/person/personList.jsp?deptId=" + uuid.split(";")[0] + "&deptName=" + encodeURIComponent(treeNode.name);
	}else if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'personId'){
		//parent.personinput.location = "<%=contextPath%>/system/core/person/personList.jsp?uuid=" + uuid.split(";")[0] ;
		toAddUpdatePerson(uuid.split(";")[0]);
	}
};

/**
 * 跳转至查询
 */
function toQuery(){
	parent.personinput.location = "<%=contextPath%>/system/core/person/query.jsp";
}
/**
 * 跳转导入
 */
function toImport(){
	parent.personinput.location = "<%=contextPath%>/system/core/person/import.jsp";
}
/**
 * 跳转至批量设置
 */
function toMultiSet(){
	parent.personinput.location = "<%=contextPath%>/system/core/person/multiSet.jsp";
}
/**
 * 离职人员或者已被删除人员
 */
function toDeletePerson(){
	parent.personinput.location = "<%=contextPath%>/system/core/person/deletePerson.jsp";
}
</script>
</head>
<BODY onload="doInit()" style="padding-left:3px;">
<table border="0" style="padding-left:10px;padding-bottom:5px;" width="100%"  cellspacing="0" cellpadding="3" class="">
  <tr >
    <td class="Big3" >&nbsp;&nbsp;用户管理
    </td>
    
    </tr>
</table>

<div class="panel-group" id="accordion" style="width:250px;">
  <div class="panel panel-default">
    <div class="panel-heading menuList" align="">
      <h5 class="panel-title" style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
          	用户列表
        </a>
      </h5>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      	<div class="panel-body" style="padding:0px;">
       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:280px; padding:2px;"></ul>
   	    </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading menuList">
      <h5 class="panel-title"  style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="toQuery();">
          	用户查询或导出
        </a>
      </h5>
    </div>
   
  </div>
  
   <div class="panel panel-default">
    <div class="panel-heading menuList">
      <h5 class="panel-title"  style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="toImport();">
          	用户导入
        </a>
      </h5>
    </div>
   
  </div>
  
   <div class="panel panel-default">
    <div class="panel-heading menuList">
      <h5 class="panel-title"  style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="toMultiSet();">
          	 用户批量设置
        </a>
      </h5>
    </div>
  </div>
	 
  <div class="panel panel-default">
    <div class="panel-heading menuList">
      <h5 class="panel-title"  style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="toDeletePerson();">
          	离职/外部人员
        </a>
      </h5>
    </div>
  </div>
</div>

</BODY>
</html>