<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>微信用户同步</title>

<script type="text/javascript" charset="UTF-8">
function doInit(){
	//var json = tools.requestJsonRs(contextPath+"/dingding/getBasicParam.action");
	getPersonTree();
}

function sync(oper){
	var nodes = zTreeObj.getCheckedNodes(true);
	var ids = [];
	var deptIds = [];
	for(var i in nodes){
		var sp = nodes[i].id.split(";");
		var check = nodes[i].getCheckStatus();
		if(check.half){
			continue;
		}
		if(sp[1]=="personId"){
			ids.push(sp[0]);
		}else if(sp[1]=="dept"){
			deptIds.push(sp[0]);
		}
	}
	
	if(window.confirm("是否将用户同步操作到微信企业号？")){
// 		$("#btn").attr("disabled","");
// 		$("#btn1").attr("disabled","");
// 		$("#btn2").attr("disabled","");
		
		bsWindow(contextPath+"/weixin/syncPerson.action?oper="+oper+"&ids="+ids.join(",")+"&deptIds="+deptIds.join(","),"同步详情",{});
		
// 		tools.requestJsonRs(contextPath+"/weixin/syncPerson.action",{oper:oper,ids:ids.join(","),deptIds:deptIds.join(",")},true,function(json){
// 			$("#btn").removeAttr("disabled");
// 			$("#btn1").removeAttr("disabled");
// 			$("#btn2").removeAttr("disabled");
// 		});
	}
	
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
				var url = "<%=contextPath %>/personManager/getOrgTree.action";
				var config = {
					zTreeId:"orgZtree",
					requestURL:url,
					param:{"para1":"111"},
					onAsyncSuccess:onUserAsyncSuccess,
					checkController:{
						enable : true,
						chkboxType: { "Y": "s", "N": "s" }
		             }
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
}



</script>
</head>
<body onload="doInit();">
	<fieldset>
		<legend>
			<h5 style="font-size:14px;font-weight:bold;font-family:微软雅黑">用户同步</h5>
		</legend>
		<span class="help-block">注：将OA系统中的用户信息同步到微信企业号之前，请务必保证该OA用户设置了手机号。</span>
		<button type="button" id="btn" class="btn btn-default" onclick="sync(0)">同步所有用户</button>
		<button type="button" id="btn1" class="btn btn-default" onclick="sync(1)">同步选中用户</button>
		<button type="button" id="btn2" class="btn btn-warning" onclick="sync(2)">删除选中用户</button>
		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:15px;width:98%;min-height:330px; padding:2px;"></ul>
	</fieldset>
</body>
</html>