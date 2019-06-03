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
var salYear = <%=salYear%>;//年份
var salMonth = <%=salMonth%>;//月份
function doInit(){
	$("#layout").layout({auto:true});
	getAllAccount('accountId');
	$("#accountId").val(accountId);
	var url = "<%=contextPath %>/deptManager/getOrgDeptTree.action";
	var config = {
			zTreeId:"deptTree",
			requestURL:url,
			param:{},
			onClickFunc:getSalary,
			async:false,
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
	 $("#frame0").attr("src",contextPath+"/system/subsys/salary/manage/manager.jsp?deptId="+deptId + "&accountId=" + accountId + "&salYear=" + salYear + "&salMonth=" + salMonth );

}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px 0px 0px 5px;">
<div id="layout">
	<div layout="north" height="50px" style="">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;员工工资管理</b>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			工资账套：
			 <select name="accountId" id="accountId" class="BigSelect" onchange="onChangSelect()">
			 	<option value=""></option>
			 </select>
			工资月份：
			 <select name="salYear" id="salYear" class="BigSelect" onchange="onChangSelect()">
       	  	<%
       	  	for(int i = 2000 ; i <2100 ; i++){
       	  		if(salYear == i){	
       	  	%>
       	  		<option value="<%=i %>" selected><%=i %>年</option>
       	  	<%}else{ %>
       	  		<option value="<%=i %>"><%=i %>年</option>
       	  	<%	}
       	  	}
       	  	%>
       	  </select>
       	  
       	  <select name="salMonth" id="salMonth" class="BigSelect" onchange="onChangSelect()">
       	  	<%
       	  	for(int i = 1 ; i <13 ; i++){
       	  		if(salMonth == i){	
       	  	%>
       	  		<option value="<%=i %>" selected="selected"><%=i %>月</option>
       	  	<%
       	  		}else{
       	  	%>       
       	  		<option value="<%=i %>"><%=i %>月</option>
       	  	<%	}}
       	  	%>
       	  </select>
       	  
       	  &nbsp;&nbsp;
    	  <button class="btn btn-primary" onclick="window.location.href='<%=contextPath %>/system/subsys/salary/salflow/index.jsp'">返回</button> 
		</div>
	
	</div>

	<div layout="west" width="240" style="margin-top:5px;">
		<div id="deptList">
			<ul id="deptTree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:450px; padding:2px;"></ul>
		</div>
	</div>
	<div layout="center" style="padding-left:10px;padding-bottom:10px;">
		<iframe id="frame0" frameborder="0" style="width:100%;height:100%" src="<%=contextPath %>/system/subsys/salary/manage/manager.jsp?accountId=<%=accountId %>&salYear=<%=salYear %>&salMonth=<%=salMonth%> "></iframe>
	</div>
</div>
</body>
</html>