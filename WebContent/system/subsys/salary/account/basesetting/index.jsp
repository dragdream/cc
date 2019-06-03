<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String type=request.getParameter("type")==null?"":request.getParameter("type");
	

	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	int accountId = TeeStringUtil.getInteger(request.getParameter("accountId"), 0);//账套
	int salYear =  TeeStringUtil.getInteger(request.getParameter("salYear"), year);//年份
	int salMonth = TeeStringUtil.getInteger(request.getParameter("salMonth"),month);//月份
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>

<script>
var zTreeObj ;
var accountId = <%=accountId%>;//账套
function doInit(){
	getAllAccount('accountId');
	$("#accountId").val(accountId);
	var url = "<%=contextPath %>/deptManager/getOrgDeptTree.action";
	var config = {
			zTreeId:"deptTree",
			requestURL:url,
			param:{},
			onClickFunc:getSalary,
			async:true,
			onAsyncSuccess:onDeptAsyncSuccess
			
		};
	zTreeObj = ZTreeTool.config(config);
	
}

function getSalary(event, treeId, treeNode){
	onChangSelect(treeNode)
	//$("#frame0").attr("src",contextPath+"/system/subsys/salary/manage/manager.jsp?deptId="+deptId + "&accountId=<%=accountId %>&salYear=<%=salYear %>&salMonth=<%=salMonth%>");
}

 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后
	//expandNodes();
	 
 }
 /**
   *第一级展开部门
   */
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("deptTree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
	}
}

 

function onChangSelect(treeNode){
	 var deptId = 0;
	 if(!treeNode){
		 var treeObj = $.fn.zTree.getZTreeObj("deptTree");
		 var nodes = treeObj.getSelectedNodes();
		 if(nodes.length > 0){
			 var treeNode = nodes[0];
			 var id = treeNode.id.split(";");
			 if(id.length>1 && id[1]=="dept"){
					deptId = id[0];
			 }
		 }
	 }else{
		 var id = treeNode.id.split(";");
		 if(id.length>1 && id[1]=="dept"){
				deptId = id[0];
		 }
	 }
	 var accountId = $("#accountId").val();
	 var salYear = $("#salYear").val();
	 var salMonth = $("#salMonth").val();
	 $("#frame0").attr("src",contextPath+"/system/subsys/salary/account/basesetting/manager.jsp?deptId="+deptId + "&accountId=" + accountId + "&salYear=" + salYear + "&salMonth=" + salMonth );

}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px 0px 0px 5px;">
<div class="base_layout_top">
	<span class="easyui_h1">薪酬基数设置&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			工资账套：
			 <select name="accountId" id="accountId" class="BigSelect" onchange="onChangSelect()">
			 	<option value=""></option>
			 </select>
	</span>
</div>
<div style="position:absolute;left:0px;bottom:0px;width:240px;top:40px;overflow-x:hidden;overflow-y:auto;">
	<div id="deptList">
		<ul id="deptTree" class="ztree" style="border:0px;margin-top:0px;width:100%;height:450px; padding:2px;"></ul>
	</div>
</div>
<div style="position:absolute;left:241px;bottom:0px;right:0px;top:40px;">
	<iframe id="frame0" frameborder="0" style="width:100%;height:100%" src="<%=contextPath %>/system/subsys/salary/account/basesetting/manager.jsp?accountId=<%=accountId %>&salYear=<%=salYear %>&salMonth=<%=salMonth%> "></iframe>
</div>
</body>
</html>