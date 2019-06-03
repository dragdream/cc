<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>

<title>设置角色权限</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<style type="text/css">
div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
div#rMenu ul li{
	margin: 1px 0;
	padding: 0 5px;
	cursor: pointer;
	list-style: none outside none;
	/*background-color: #DFDFDF;*/
}
	</style>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript">
var zTreeObj ;
var uuid = "<%=uuid%>";
function doInit(){
	var url = "<%=contextPath %>/teeMenuGroup/getMenuGroupAllPriv.action";
	var config = {
			zTreeId:"ztree",
			requestURL:url,
			checkController:{
				enable : true,
				chkboxType: { "Y": "ps", "N": "s" }
               },
				async:false
		};
	zTreeObj = ZTreeTool.config(config);
	
	getMenuGroupAll();
}


/**
 * 保存
 */
function doSubmit(){
    var menuGroupOptType = "0";
   
    if(document.getElementById("menuGroupOptType1").checked == true){
    	menuGroupOptType = "1";
    }
	var menuUuids = getSelectIds();
	
	
	var menuGroupStr = getMenuGroupA();
	if(menuGroupStr == ''){
		alert("请至少选择一个角色权限！");
		return;
	}
	if(menuUuids==''){
		alert("请至少选择一个菜单！");
		return;
	}
	var url = "<%=contextPath %>/teeMenuGroup/setMuiltMenuGroupPriv.action";
	var jsonObj = tools.requestJsonRs(url,{uuids:menuGroupStr,menuIds:menuUuids,opt:menuGroupOptType});
	if(jsonObj.rtState){
		$.messager.show({
			msg : '保存成功！！',
			title : '提示'
		});
	}
	
}
/**
 * 获取选中所有节点Id
 */
function getSelectIds(){
	var id = ZTreeTool.getSelectCheckedNodeIds(true,true);
	return id;
}
/**
 * 获取菜单组
 */
function getMenuGroupAll(){
	var url = "<%=contextPath %>/teeMenuGroup/getMenuGroupAll.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var data = jsonObj.rtData;
		var dataJson = eval('(' + data + ')');
		var menuGroups = document.getElementById("menuGroups");
	
		for(var i = 0;i<dataJson.length ;i++){
			var menuGroup = dataJson[i];
			var input = document.createElement("input");
			input.setAttribute("type", "checkbox");
			input.setAttribute("id", "menuGroup" + menuGroup.uuid);
			input.setAttribute("name", "menuGroup");
		    input.setAttribute("value", menuGroup.uuid);
			  
			var label = document.createElement("label");
			label.setAttribute("for", "menuGroup" + menuGroup.uuid);
			label.innerHTML = menuGroup.menuGroupName + "&nbsp;&nbsp;";
		    //input.appendChild(label); 
			menuGroups.appendChild(input);
			menuGroups.appendChild(label);

		}
	}
}

/**
 *获取所有选中菜单组
 */
function getMenuGroupA(){
	var menuGroups = $("#menuGroups").children(":input");
	var menuGroupStr = "";
    for(var i = 0;i<menuGroups.length;i++){
    	if(menuGroups[i].checked){
    		menuGroupStr = menuGroupStr + menuGroups[i].value   + "," ;
    	}
    }
	if(menuGroupStr != ""){
		menuGroupStr = menuGroupStr.substring(0,menuGroupStr.length-1);
	}
	return menuGroupStr;
}
var check_all_var = true;
function select_all(){
	 var cb  = $("#menuGroups").children(":input");
	  if(!cb || cb.length == 0){
	    return;
	  }
	  for(var i = 0;i < cb.length; i++){
	    if(check_all_var){
	      cb[i].checked = true;
	      
	    }else{
	      cb[i].checked = false;
	
	    }
	  }
	  check_all_var = !check_all_var;
}

/*
 *  滚动条事件
 */
window.onscroll = window.onresize = function(){
	  var opBtn = document.getElementById("OP_BTN");
	  if(!opBtn){
	    return false;
	  } else {
	    opBtn.style.left = (document.documentElement.clientWidth + document.documentElement.scrollLeft - 180) + 'px';
	    opBtn.style.top = (document.documentElement.scrollTop + 10) + 'px';
	  }
	};

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

<div align="" style="padding-top:10px; padding-left:10px;">



<table >
  <tr>
    <td>
  <span class="Big3"> 添加/删除菜单权限</span>&nbsp;&nbsp;
    </td>
    <td align="right">
    <div id="OP_BTN" style="width:150px;top:5px;right:30px;position:absolute;">
    <input type="button" value="保存" class="btn btn-primary" title="保存" name="button" onclick="doSubmit();">&nbsp;&nbsp;
<!--     <input type="button" value="返回" class="BigButtonA" onclick="location='manageGroup.jsp'"> -->
    </div>
    
    </td>
  </tr>
</table>

<br>


<table class="TableBlock" width="100%">
  <tr class="TableData">
    <td width="100px;">
     <b>操作：</b>
    </td>
    <td >
<input type="radio" name="menuGroupOptType" id="menuGroupOptType0" value="0" checked/> 添加菜单
     &nbsp;&nbsp;
     <input type="radio" name="menuGroupOptType" id="menuGroupOptType1" value="1"/> 删除菜单
    
    </td>
  </tr>
    <tr class="TableData">
    <td>
    <b> 权限</b>&nbsp;&nbsp;<a href="javascript:select_all();"><u>全选</u></a>: 
    </td>
    <td id="menuGroups">

    
    </td>
  </tr>

</table>

<ul id="ztree" class="ztree" style="overflow:hidden;border:0px;background:#ffffff;" ></ul>
</div>


</BODY>
</HTML>