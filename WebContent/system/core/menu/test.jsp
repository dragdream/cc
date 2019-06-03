<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单树</title>

<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/demo.css" type="text/css"/>
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
}
	</style>

<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>

<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/ZTreeSync.js"></script>

<script type="text/javascript">

 // ZTreeSyns();
 var zTreeObj ;
 var rMenu ;
function doInit(){
	var url = "<%=contextPath %>/sysMenu/getMenuTree.action";
	var config = {
			zTreeId:"treeDemo",
			requestURL:url,
			param:{"para1":"111"}
			
		};
	zTreeObj = ZTreeTool.config(config);

	rMenu = $("#rMenu");


}
function getSelectIds(){
	var id = ZTreeTool.getSelectCheckedNodeIds(true,true);
	alert(id);
}

function addNote(){
	
	var newNodes = [{id:"new_1",name:"newNode1"}, {id:"new_2",name:"newNode2"}, {id:"new_3",name:"newNode3"}];
	
	var nodes = zTreeObj.getSelectedNodes();
    alert(nodes.length)
    if(nodes.length < 0){
    	alert("请选中一个父节点！");
    	return;
    }
	newNodes = 	zTreeObj.addNodes(nodes[0], newNodes);

}


function OnRightClick(event, treeId, treeNode) {




       alert(treeNode ? treeNode.tId + ", " + treeNode.name : "isRoot");
       if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
    	   zTreeObj.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTreeObj.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}

  }

function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	if (type=="root") {
		$("#m_del").hide();
		$("#m_check").hide();
		$("#m_unCheck").hide();
	} else {
		$("#m_del").show();
		$("#m_check").show();
		$("#m_unCheck").show();
	}
	rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

	$("body").bind("mousedown", onBodyMouseDown);
}


function hideRMenu() {
	if (rMenu) rMenu.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		rMenu.css({"visibility" : "hidden"});
	}
}
var addCount = 1;
function addTreeNode() {
	hideRMenu();
	var newNode = { name:"增加" + (addCount++)};
	if (zTree.getSelectedNodes()[0]) {
		newNode.checked = zTreeObj.getSelectedNodes()[0].checked;
		zTreeObj.addNodes(zTree.getSelectedNodes()[0], newNode);
	} else {
		zTreeObj.addNodes(null, newNode);
	}
}
function removeTreeNode() {
	hideRMenu();
	var nodes = zTreeObj.getSelectedNodes();
	if (nodes && nodes.length>0) {
		if (nodes[0].children && nodes[0].children.length > 0) {
			var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
			if (confirm(msg)==true){
				zTree.removeNode(nodes[0]);
			}
		} else {
			zTree.removeNode(nodes[0]);
		}
	}
}
function checkTreeNode(checked) {
	var nodes = zTreeObj.getSelectedNodes();
	if (nodes && nodes.length>0) {
		zTreeObj.checkNode(nodes[0], checked, true);
	}
	hideRMenu();
}
function resetTree() {
	hideRMenu();
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
}


</SCRIPT>
	<style type="text/css">
.ztree li span.button.pIcon01_ico_open{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.pIcon01_ico_close{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.pIcon02_ico_open, .ztree li span.button.pIcon02_ico_close{margin-right:2px; background: url<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/2.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon01_ico_docu{margin-right:2px; background: url<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon02_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/4.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon03_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/5.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon04_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/6.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon05_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/7.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.icon06_ico_docu{margin-right:2px; background: url(<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/img/diy/8.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	</style>

</HEAD>

<BODY onload="doInit()">
<input type="button" value="获取勾选Id串" onclick="getSelectIds();">
<div>
<input type="button" value="新增节点" onclick="addNote();">
</div>
<h1>自定义图标 -- iconSkin 属性</h1>
<h6>[ 文件路径: core/custom_iconSkin.html ]</h6>


<div class="content_wrap">
<div class="zTreeDemoBackground left">
<ul id="treeDemo" class="ztree"></ul>
</div>
<div class="right">
<ul class="info">
	<li class="title"><h2>1、setting 配置信息说明</h2>
		<ul class="list">
		<li>自定义图标不需要对 setting 进行特殊配置</li>
		</ul>
	</li>
	<li class="title"><h2>2、treeNode 节点数据说明</h2>
		<ul class="list">
		<li>利用 节点数据的 iconSkin 属性 配合 css 实现自定义图标</li>
		<li class="highlight_red">详细请参见 API 文档中的相关内容</li>
		</ul>
	</li>
	<li class="title"><h2>3、其他说明</h2>
		<ul class="list">
		<li class="highlight_red">由于时间关系，例子直接采用 png 图片，如果需要解决 ie6 下 png 图片的透明问题，请针对 ie6 制作特殊的 gif 图片或者利用 css filter 解决</li>
		</ul>
	</li>
</ul>
</div>



</div>

<div id="rMenu">
	<ul>
		<li id="m_add" onclick="addTreeNode();">增加节点</li>
		<li id="m_del" onclick="removeTreeNode();">删除节点</li>
		<li id="m_check" onclick="checkTreeNode(true);">Check节点</li>
		<li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li>
		<li id="m_reset" onclick="resetTree();">恢复zTree</li>
	</ul>
</div>
</BODY>
</HTML>