<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String type=request.getParameter("type")==null?"":request.getParameter("type");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath %>/bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>

<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script>
var zTreeObj ;
/**
 * 城市规则：
 --省级
 select * from china_city where city_code like '__0000'
 --市级
 select *  from china_city where city_code like '11__00' and city_code<>'110000'
 --县级
 select *  from china_city where city_code like '1101__' and city_code<>'110100'
 */
 
function doInit(){
	$("#layout").layout({auto:true});
	var url = "<%=contextPath %>/chinaCityController/getProvinceTree.action";
	var config = {
			zTreeId:"provinceTree",
			requestURL:url,
			param:{"para1":"111"},
			onClickFunc:getCustomerList,
			async:true,
			onAsyncSuccess:onDeptAsyncSuccess
			
		};
	zTreeObj = ZTreeTool.config(config);
}

function getCustomerList(event, treeId, treeNode){
	var cityCode = treeNode.id;
	var cityName = treeNode.name;
	$("#frame0").attr("src",contextPath+"/system/subsys/crm/core/customInfo/province/customerList.jsp?cityCode="+cityCode+"&cityName="+cityName+"&type=<%=type%>");
}

 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后
	//expandNodes();
	 
 }
 /**
   *第一级展开部门
   */
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("provinceTree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
	}
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px 0px 0px 5px;">
<div id="layout">
	<div layout="west" width="150">
		<div id="provinceList">
			<ul id="provinceTree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:450px; padding:2px;"></ul>
		</div>
	</div>
	<div layout="center" style="padding-left:10px;padding-bottom:10px;">
		<iframe id="frame0" frameborder="0" style="width:100%;height:100%" src="<%=contextPath %>/system/subsys/crm/core/customInfo/province/customerList.jsp?type=<%=type%>"></iframe>
	</div>
</div>
</body>
</html>