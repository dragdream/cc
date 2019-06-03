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
	getDeptTree();
}

function sync(){
	if(window.confirm("是否同步操作到钉钉？")){
// 		$("#btn").attr("disabled","");
// 		tools.requestJsonRs(contextPath+"/dingding/syncOrg.action",{},true,function(json){
// 			$("#btn").removeAttr("disabled");
// 		});
		bsWindow(contextPath+"/dingding/syncOrg.action","同步详情",{});
	}
}

/**
 * 马上加载
 */
 var zTreeObj ;
 function getDeptTree(){
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/deptManager/getOrgDeptTree.action";
				var config = {
						zTreeId:"orgZtree",
						requestURL:url,
						param:{"para1":"111"},
						async:true
					};
				zTreeObj = ZTreeTool.config(config);
			}else{
				alert("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
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
}

 
 
//同步指定部门
function syncDept(){
	var nodes=zTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var deptId=nodes[0].id;
		if(deptId.indexOf("dept")==-1){//组织机构名
			alert("请选择部门！");
		    return  false;
		}else{
			var dId=deptId.split(";")[0];
			bsWindow(contextPath+"/dingding/syncDept.action?deptId="+dId,"部门同步详情",{});	
		}	
	}else{
		alert("请选择部门！");
	    return  false;
	}
}


//删除指定部门(从钉钉删除)
function  delDingDept(){
	var nodes=zTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var deptId=nodes[0].id;
		if(deptId.indexOf("dept")==-1){//组织机构名
			alert("请选择部门！");
		    return  false;
		}else{
			var dId=deptId.split(";")[0];
			bsWindow(contextPath+"/dingding/delDingDept.action?deptId="+dId,"删除部门详情",{});	
		}	
	}else{
		alert("请选择部门！");
	    return  false;
	}
	
}

</script>
</head>
<body onload="doInit();">
	<fieldset>
		<legend>
			<h5 style="font-size:14px;font-weight:bold;font-family:微软雅黑">组织机构同步</h5>
		</legend>
		<span class="help-block">注：将OA系统中的组织机构同步到钉钉通讯录后，钉钉的所有账户全部归属到顶级部门，请重新进行用户同步。</span>
	  
		<button type="button" id="btn" class="btn btn-default" onclick="sync()">同步组织机构</button>
		<button type="button" id="btn" class="btn btn-default" onclick="syncDept()">同步指定部门</button>
		<button type="button" id="btn" class="btn btn-warning" onclick="delDingDept()">删除指定部门</button>
		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:15px;width:100%;min-height:330px; padding:2px;"></ul>
	</fieldset>
</body>
</html>