<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/font-awesome/4.7.0/css/font-awesome.min.css">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{
	font-size:12px;
}
.c{
	width:150px;
	float:left;
	margin-left:5px;
	margin-right:5px;
	padding:5px;
}
.cc{
	margin-left:10px;
	padding:2px;
}
td{
	border:1px solid #66ccff;
	background:#f0f0f0;
}

.zTreeBackground{
	width:225px;
	height:462px;
	float:left;
	margin-left:5px;
	margin-right:5px;
	padding:5px;
}

ul.ztree {
	margin-top: 10px 0 0 0;
	border: 1px solid #617775;
	background: #f0f6e4;
	width:220px;
	height:460px;
	overflow-y:scroll;
	overflow-x:auto;
    padding: 5px;
    color: #333;
}

/* 字体图标 */
.ztree li a span.button {font-family: FontAwesome;background-image: none;margin: 0 3px 0;line-height: 16px;}
.ztree li span.unit_ico_open:before, .ztree li span.unit_ico_close:before, .ztree li span.unit_ico_docu:before {content: "\f19c";font-size:120%;} 
.ztree li span.dept_ico_open:before, .ztree li span.dept_ico_close:before, .ztree li span.dept_ico_docu:before {content: "\f0c0";font-size:120%;} 

</style>
<script>

var uuid = "<%=request.getParameter("uuid") == null ? "" : request.getParameter("uuid")%>";
function doInit(){
	$.MsgBox.Loading();
	var width = 0;
	
	var url = contextPath +  "/teeMenuGroup/getOrgTreeByLoginPerson.action";
	var para = {groupId:uuid};
	var jsonRs = tools.requestJsonRs(url,para);
	
	var setting = {
			check: {
				enable: true,
				chkboxType: { "Y": "", "N": ""}
			},			
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	var zNodes = jsonRs.rtData;

	$.fn.zTree.init($("#tree"), setting, zNodes);
	
	$.MsgBox.CloseLoading();

}

/**
 * 保存
 */
function doSubmit(){
	
	var checkedIdArr = new Array();	
	var zTreeObj = $.fn.zTree.getZTreeObj("tree");  
	var checkedNodes = zTreeObj.getCheckedNodes();
	for(var j=0 ; j < checkedNodes.length ; j++){
		checkedIdArr.push(checkedNodes[j].id);
	}
	//var checkedIds = checkedIdArr.toString();
	
	var url = contextPath+"/teeMenuGroup/setDeptPriv.action";
	var jsonObj = tools.requestJsonRs(url,{groupId:uuid,deptsIdStr:checkedIdArr.join(",")});
	if(jsonObj.rtState){
		location='manageGroup.jsp';
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}

}
</script>

</head>
<body onload="doInit()" style="overflow:hidden">
<div id="toolbar" class="topbar clearfix" style="position:absolute;top:0px;left:0px;right:0px;height:20px">
   <div class="fl">
   	&nbsp;&nbsp;<button type="button" class="btn-win-white"  onclick="doSubmit();">保存</button>&nbsp;&nbsp;<button type="button" class="btn-win-white" onclick="location='manageGroup.jsp'">返回</button>
   	&nbsp;&nbsp;&nbsp;&nbsp;
   </div>
</div>
<div id="mainDiv" style="position:absolute;top:60px;left:0px;right:0px;bottom:0px;overflow:auto">
	<div class='zTreeBackground'>
		<ul id='tree' class='ztree'></ul>
	</div>
</div>
</body>
</html>