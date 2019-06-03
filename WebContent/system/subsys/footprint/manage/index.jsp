<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- jQuery 布局器 -->
<script>

function doInit(){
	$("#group").group();
	changePage("<%=contextPath%>/system/subsys/footprint/manage/comfireNo.jsp");
	getPersonTree();
	
$(".dom").click(function(){
	$(".dom").removeClass("li_active");
	$(this).addClass("li_active");
})
			$(".panel-heading").click(function(){
				if($(this).siblings().find('.panel-body').length==0){
					return false;
				}
				var $span = $(this).find('span');
				var isOpen = $span.hasClass("caret-down");
				if(isOpen){
					//$('.panel-body ul').slideUp();
					$(this).siblings('.collapse').slideUp(200);
					$span.attr("class","caret-right");
				}else{
					$(this).siblings('.collapse').slideDown(200);
					$span.attr("class","caret-down");
				}
		})
}
function  changePage(url){
	$("#frame0").attr("src", url);
}
function refreshTargetNode(tid){
	var treeObj = $.fn.zTree.getZTreeObj("orgZtree");
	var node = treeObj.getNodeByParam("id", tid, null);
	if(node!=null){
		if(!node.isParent){
			treeObj.reAsyncChildNodes(null, "refresh");
		}else{
			treeObj.reAsyncChildNodes(node, "refresh");
		}
	}
}
/**
 * 马上加载
 */
 var zTreeObj ;
 function getPersonTree(){
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/personManager/getOrgTreeByViewPriv.action";
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
				$.MsgBox.Alert_auto("单位信息未录入，请您先填写单位信息！");
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
		changePage("<%=contextPath%>/system/subsys/footprint/manage/comfireNo.jsp");
	}else if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'personId'){
		changePage(contextPath + "/system/subsys/footprint/manage/query.jsp?personId="+uuid.split(";")[0]);
	}
};

/**
 * 跳转至查询
 */
function toQuery(){
	changePage("<%=contextPath%>/system/core/person/query.jsp");
}
/**
 * 跳转导入
 */
function toImport(){
	changePage("<%=contextPath%>/system/core/person/import.jsp");
}
/**
 * 跳转至批量设置
 */
function toMultiSet(){
	changePage("<%=contextPath%>/system/core/person/multiSet.jsp");
}
/**
 * 离职人员或者已被删除人员
 */
function toDeletePerson(){
	changePage("<%=contextPath%>/system/core/person/deletePerson.jsp");
}
</script>
<style type="">
		body{
			background-color:#eaedf2;
		}
		.panel-heading > span{
			position:absolute;
		}
		.panel-heading{
		padding: 10px 5px;
		font-size: 14px;
		text-align: left;
		text-indent:20px;
		box-sizing: border-box;
		}
		.panel-title {
			margin-left:15px;
			display:inline;
		}
		.panel-title a{
		color:#000;
		}
		.groupContent li{
		height: 30px;
		line-height: 30px;
		font-size: 12px;
		text-align: left;
		text-indent:60px;
		cursor:pointer;
		}
		.groupContent li:hover{
		background-color:#fff;
		color:#fff;
		}
		.groupContent li a{
		color:#000;
		}
		.groupContent li a:hover{
		color:#000;
		}
		.li_active{
			background-color:#fff;
		}
		#orgZtree{
			padding-left:45px!important;
		}
</style>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
<div layout="west" width="280px" style="overflow-y:auto;overflow-x:hidden;position:absolute;left:0px;top:0px;bottom:0px;width:280px">
<div id="group" class="list-group">
<div class="panel-group" id="list-group">
  <div class="panel panel-default">
    <div class="panel-heading menuList">
    <span class='caret-down' ></span>
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
       	  用户列表
        </a>
      </h4> 
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      <div class="panel-body">
      
      <ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:100%;min-height:210px; padding-left:2px;"></ul>
	  
	  
      </div>
    </div>
  </div>
</div>
</div>
</div>
	<div layout="center" style="padding-left:2px;position:absolute;left:281px;top:0px;bottom:0px;right:0px;">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</body>
<script>

</script>
</html>
