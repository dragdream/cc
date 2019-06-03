<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<title>消息管理</title>

<script>
/**
* 马上加载
*/
var zTreeObj ;
function doInit(){

		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		//alert(jsonObj);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/personManager/getOrgTreeAll.action";
				var config = {
						zTreeId:"orgZtree",
						requestURL:url,
						onClickFunc:personOnClick,
						onAsyncSuccess:onDeptAsyncSuccess
						
					};
				zTreeObj = ZTreeTool.config(config);
			}else{
				$.MsgBox.Alert_auto("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
		//
		//setTimeout('expandNodes()',500);
}
function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后

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
function personOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;

	if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'dept'){
		//parent.personinput.location = "<%=contextPath%>/system/core/person/personList.jsp?deptId=" + uuid.split(";")[0] + "&deptName=" + encodeURIComponent(treeNode.name);
	}else if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'personId'){
		var userId = treeNode.extend1;//扩展字段1
		toPersonDialogue(uuid.split(";")[0] , treeNode.name,userId);
		//parent.personinput.location = "<%=contextPath%>/system/core/person/personList.jsp?uuid=" + uuid.split(";")[0] ;
		//toAddUpdatePerson(uuid.split(";")[0]);
	}
};


/**
* 跳转至发送消息界面
*/
function toAddMessage(){
	parent.brank.location  = "<%=contextPath%>/system/core/base/message/manager/add.jsp";
}

/**
* 跳转至未确认界面
*/
function toComfireNo(){
	parent.brank.location = "<%=contextPath%>/system/core/base/message/manager/comfireNo.jsp";
}


/**
* 跳转至于人员对话界面
*/
function toPersonDialogue (personId , personName , userId){
	$("#frame0")[0].src = "<%=contextPath%>/system/core/base/message/manager/personDialogue.jsp?personId=" + personId + "&personName=" + encodeURIComponent(personName) + "&userId="+ encodeURIComponent(userId);
}

/**
* 跳转至于人员对话界面
*/
function toQuery (){
	parent.brank.location = "<%=contextPath%>/system/core/base/message/manager/query.jsp";
}
</script>
</head>
<body style="overflow:hidden" class="clearfix" onload="doInit()">
	<div class="fl" style="text-align:center;overflow-y:auto;overflow-x:hidden" >
		<br/>
		<ul id="orgZtree" class="ztree" style="overflow:hidden;border:0px;margin-top:0px;width:100%;padding:2px;"></ul>
	</div>
	<div class="fl" style="overflow:hidden;position:absolute;left:165px;right:0;top:0;bottom:0;">
		<iframe id="frame0" frameborder=0 style=" width:100%;height:100%" src="<%=contextPath%>/system/core/base/message/manager/comfireNo.jsp"></iframe>
	</div>
</body>
<!--     <frameset rows="*"  cols="260,*" frameborder="0" border="0" framespacing="0" id="frame2"  scrolling="no"> -->
<%--        <frame name="messagemanage" src="<%=contextPath%>/system/core/base/message/manager/left.jsp" scrolling="auto" noresize /> --%>
<%--        <frame name="brank" src="<%=contextPath%>/system/core/base/message/manager/comfireNo.jsp" scrolling="auto" frameborder="NO"> --%>
<!--     </frameset> -->
</html>

