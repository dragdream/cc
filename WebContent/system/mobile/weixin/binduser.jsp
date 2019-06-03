<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>桌面模块设置</title>

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
	
	if(window.confirm("是否同步操作到钉钉？")){
		$("#btn").attr("disabled","");
		$("#btn1").attr("disabled","");
		$("#btn2").attr("disabled","");
		tools.requestJsonRs(contextPath+"/dingding/syncPerson.action",{oper:oper,ids:ids.join(","),deptIds:deptIds.join(",")},true,function(json){
			$("#btn").removeAttr("disabled");
			$("#btn1").removeAttr("disabled");
			$("#btn2").removeAttr("disabled");
		});
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
				var url = "<%=contextPath %>/weixin/getDeptInfos.action";
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
var nodeId ;
var dingIdG;
 /**
  * 点击节点
  */
 function deptOnClick(event, treeId, treeNode) {
 	var uuid = treeNode.id;
 	nodeId = uuid;
 	refresh();
 }
 
 function refresh(){
	 var json = tools.requestJsonRs(contextPath+"/weixin/getPersonsByDept.action?id="+nodeId);
	 	var render = [];
	 	for(var i=0;i<json.rtData.length;i++){
	 		render.push("<tr>");
	 		render.push("<td class='TableData'>"+json.rtData[i].name+"</td>");
	 		render.push("<td class='TableData'>"+json.rtData[i].userid+"</td>");
	 		if(json.rtData[i].mobile=="true"){
	 			render.push("<td class='TableData' style='color:green'>已绑定("+json.rtData[i].email+")</td>");
	 		}else{
	 			render.push("<td class='TableData' style='color:gray'>未绑定</td>");
	 		}
	 		if(json.rtData[i].mobile=="true"){
	 			render.push("<td class='TableData'><button class='btn btn-warning' onclick=\"calcelBind('"+json.rtData[i].userid+"','"+json.rtData[i].errcode+"')\">解绑</button></td>");
	 		}else{
	 			render.push("<td class='TableData'><button class='btn btn-success' onclick=\"bind('"+json.rtData[i].userid+"','"+json.rtData[i].errcode+"')\">绑定</button></td>");
	 		}
	 		render.push("</tr>");
	 	}
	 	
	 	$("#tbody").html(render.join(""));
 }
 
 function calcelBind(dingId,userId){
	 if(window.confirm("确认要解绑该账号吗？")){
		 var json = tools.requestJsonRs(contextPath+"/weixin/bindUser.action?type=cancel",{dingId:dingId,userId:userId});
		 refresh();
	 }
	 
 }
 
 function bind(dingId,userId){
	 dingIdG = dingId;
	 selectSingleUser(["id_","name_"]);
 }
 
 function ORG_SELECT_ADD_BACH_FUNC(a,b,c,d,e,f,g){
	 var userId = c;
	 $("#id_").val("");
	 $("#name_").val("");
	 
	 if(window.confirm("确认要绑定["+d+"]到微信账号吗？")){
		 var json = tools.requestJsonRs(contextPath+"/weixin/bindUser.action?type=bind",{dingId:dingIdG,userId:c});
		 refresh();
	 }
 }
 
 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后

		expandNodes(); 
	 }
	function expandNodes() {
		 if(!zTreeObj){
			zTreeObj = $.fn.zTree.getZTreeObj("orgZtree"); 
		 }
		var nodes = zTreeObj.getNodes();
		zTreeObj.expandNode(nodes[0], true, false, false);
		if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
			expandNodes(nodes[0].children);
		}
	}



</script>
</head>
<body onload="doInit();">
	<fieldset>
		<legend>
			<h5 style="font-size:14px;font-weight:bold;font-family:微软雅黑">用户绑定</h5>
		</legend>
		<ul id="orgZtree" class="ztree" style="position:absolute;left:0px;top:40px;bottom:0px;width:250px;overflow:auto;border:0px">
		</ul>
		<div style="position:absolute;left:252px;top:40px;right:0px;bottom:0px;">
			<table class="TableBlock" style="width:80%;text-align:center">
				<thead class="TableHeader">
					<tr>
						<td>微信用户</td>
						<td>微信用户ID</td>
						<td>状态</td>
						<td>选项</td>
					</tr>
				</thead>
				<tbody id="tbody"></tbody>
			</table>
		</div>
	</fieldset>
	<input type="hidden" id="id_" />
	<input type="hidden" id="name_" />
</body>
</html>