<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>部门树</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath %>/bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>

<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>

<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>

<script type="text/javascript">

/**
 * 马上加载
 */
 var zTreeObj ;
 function doInit(){
	//setMenu();
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		//alert(jsonObj);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/deptManager/getOrgDeptTree.action";
				var config = {
						zTreeId:"orgZtree",
						requestURL:url,
						param:{"para1":"111"},
						onClickFunc:deptOnClick,
						async:false,
						onAsyncSuccess:onDeptAsyncSuccess
						
					};
				zTreeObj = ZTreeTool.config(config);
			}else{
				alert("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
		//
		//setTimeout('expandNodes()',500);
 }
 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后

	// expandNodes();
	 
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
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
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
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2){
		parent.deptinput.location.href = "<%=contextPath%>/system/core/dept/addupdate.jsp?uuid=" + uuid.split(";")[0] ;
	}
};

function userGroup() {

	parent.deptinput.location = "<%=contextPath%>/system/core/dept/usergroup/personGroup.jsp";
	
};




/**
 * 设置菜单
 */
function setMenu(){
	$('#teemenulist ul li a').click(function(even){
	    if($(this).parent().children("div").is(':hidden')){//上级（LI）下面存在UL标签且为隐藏的 ，都展开，否则反之
			$(this).parent().children("div").show('slow');//显示   
			this.className = "aMenuVisited";
	     }else{
	    	$(this) .parent().children("div").hide('slow');//隐藏	
	    	this.className = "aMenulink";//css({'class':'aMenuVisited'});
	      } 
	   
	}).css({'cursor':'pointer'});
	 isFirst = true;
	//$("#teemenu ul li div").hide();//隐藏下级所有节点
}
</script>
</head>
<BODY onload="doInit();" style="padding-left:3px;">

<div class="panel-group" id="accordion" style="width:250px;">
  <div class="panel panel-default">
    <div class="panel-heading" align="">
      <h5 class="panel-title" style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
          	部门列表
        </a>
      </h5>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      	<div class="panel-body" style="padding:0px;">
       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:330px; padding:2px;"></ul>
   	    </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title"  style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="userGroup();">
         	公共自定义组
        </a>
      </h5>
    </div>
   
  </div>
  
</div>

</BODY>
</html>