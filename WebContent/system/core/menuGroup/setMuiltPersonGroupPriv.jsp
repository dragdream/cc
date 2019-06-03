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
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<title>批量设置权限</title>

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

<script type="text/javascript">
var zTreeObj ;
var uuid = "<%=uuid%>";
function doInit(){
	getMenuGroupAll();
	
	messageMsg("为所选人员添加/删除权限组", "menugroupPrivHelp" ,'help' ,380);
}

/**
 * 保存
 */
function doSubmit(){
    var menuGroupOptType = "0";//0-添加  ； 1-删除
    if(document.getElementById("menuGroupOptType1").checked == true){
    	menuGroupOptType = "1";
    }
	var menuGroupStr = getMenuGroupA();
	if(menuGroupStr == ''){
		alert("请至少选择一个权限组！");
		return;
	}
	
	if($("#personIds").val() == ''){
		alert("您未选择人员！");
		return;
	}
	var url = "<%=contextPath %>/teeMenuGroup/setMuiltPersonMenuGroupPriv.action";
	var jsonObj = tools.requestJsonRs(url,{uuids:menuGroupStr,personIds:$("#personIds").val(),opt:menuGroupOptType});
	if(jsonObj.rtState){
		/* $.messager.show({
			msg : '保存成功！！',
			title : '提示'
		}); */
		$.jBox.tip("保存成功！",'info',{timeout:1500});
	}else{
		alert(jsonObj.rtMsg);
	} 
	
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
			menuGroups.appendChild(label );

		}
	}
}

/**
 *获取所有选中菜单权组
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
/**
 * 全部选中或者取消
 */
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


</SCRIPT>


</HEAD>

<BODY onload="doInit()">

<div  style="padding-top:10px; padding-left:10px;">



<table >
  <tr>
    <td>
  <span class="Big3"> 添加/删除权限组</span>&nbsp;&nbsp;
    </td>
  
  </tr>
</table>



<table class="TableBlock" width="60%" align="center">
  <tr class="TableData">
    <td width="100px;">
     <b>操作：</b>
    </td>
    <td >
		<input type="radio" name="menuGroupOptType" id="menuGroupOptType0" value="0" checked/> 添加权限组
    	 &nbsp;&nbsp;
     	<input type="radio" name="menuGroupOptType" id="menuGroupOptType1" value="1"/> 删除权限组
    
    </td>
    </tr>
    <tr class="TableData">
    <td>
		  <b>  人员</b>
    </td>
      <td>
 
   	   <input  type="hidden" id="personIds" name="personIds" style="width:100%"/>
	    <textarea cols="45" name="personNames" id="personNames" rows="4"
	 		style="overflow-y: auto;" class="SmallStatic BigTextarea" wrap="yes" readonly></textarea>
			<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['personIds', 'personNames'])">添加</a>
			 <a href="javascript:void(0);" class="orgClear" onClick="clearData('personIds', 'personNames')">清空</a>
     </td>
  </tr>
    <tr class="TableData">
    <td>
   	 <b> 权限</b>&nbsp;&nbsp;<a href="javascript:select_all();"><u>全选</u></a>: 
    </td>
    <td id="menuGroups">

    
    </td>
  </tr>
  
  <tr align="center">
  	<td colspan="2">
		<input type="button" value="保存" class="btn btn-primary" title="保存" name="button" onclick="doSubmit();">&nbsp;&nbsp;
  	</td>
  </tr>

</table>

<div id="menugroupPrivHelp">
 
</div>
</div>


</BODY>
</HTML>