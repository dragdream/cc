<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>档案文件管理</title>
<script>
var zTreeObj ;
var selectId;
function doInit(){
	
	var url = contextPath+"/TeeStoreHouseController/getHouseAndBoxTree.action";
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			param:{},
			onClickFunc:function(event, treeId, treeNode){
				var id= treeNode.id;
				clickIt(id);
			},
			async:true
			
		};
	zTreeObj = ZTreeTool.config(config);
}

function clickIt(id){
	selectId=id;
	$("#frame").attr("src",contextPath+"/system/core/base/dam/damManage/files.jsp?id="+id);

}

function refreshSiteNode(id){
	var node = zTreeObj.getNodeByParam("id",id,null);
	zTreeObj.reAsyncChildNodes(node, "refresh");
}


function ShowPanel(){
 		if(selectId==null||selectId==""){
 			$.MsgBox.Alert_auto("请点击左侧选择卷库或卷盒！");
 			return;
 		}else{
			$("#frame")[0].contentWindow.showSearchPanel();//这种方式可以调用到iframe里面的方法

 		}
}


</script>
</head>
<body onload="doInit();" style="overflow:hidden;padding-left: 10px;padding-right: 10px;padding-top: 8px;">
<div id="layout">
	<div height="40" style="position: absolute;height: 40px;width: 100%;top:15px" >
		<div style="vertical-align: middle;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_dawjzl.png">
		    <span class="title">档案文件管理</span> 
		    <input type="button"  value="高级检索" class="btn-win-white fr" style="margin-right: 20px;" onclick="ShowPanel()"/>
		    <span class="basic_border_grey" style="margin-top: 10px;margin-right: 20px" ></span>  
	</div>	
</div>
	
	<div  style="overflow:auto;position: absolute;top:60px;width: 210px;height: 90%">
		<ul id="ztree" class="ztree" style="padding:0px;margin:0px"></ul>
	</div>
	<div style="overflow:hidden;position: absolute;top:53px;right: 0px;bottom: 0px;left: 220px;border-left:solid 2px #f2f2f2;">
		<iframe id="frame" frameborder="0" style="width:100%;height:100%" src="comfireNo.jsp"></iframe>
	</div>
</div>
</body>
</html>