<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<title>模块定义</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisModule/datagrid.action',
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'uuid',title:'模块标识',width:100},
			{field:'moduleName',title:'模块名称',width:100},
			{field:'bisTableName',title:'关联业务表',width:100},
			{field:'bisViewName',title:'关联视图',width:100},
			{field:'formName',title:'关联表单',width:100},
			{field:'_manage',title:'管理',width:100,formatter:function(e,rowData){
				var render = [];
				render.push("<a href='#' onclick=\"edit('"+rowData.uuid+"')\">编辑</a>");
				render.push("<a href='javascript:void(0)' onclick=\"addMenuFunc('"+rowData.uuid+"','"+rowData.moduleName+"')\">菜单定义</a>");
				render.push("<a href='javascript:void(0)' onclick=\"del('"+rowData.uuid+"')\">删除</a>");
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
}

function add(){
	window.location = "module_mgr.jsp";
}

function edit(uuid){
	window.location = "module_mgr.jsp?uuid="+uuid;
}

function del(uuid){
	if(window.confirm("是否删除该模块定义？")){
		var json = tools.requestJsonRs(contextPath+"/bisModule/deleteBisModule.action",{uuid:uuid});
		if(json.rtState){
			datagrid.datagrid("reload");
		}
	}
}


/**
 * 菜单自定义
 */
function addMenuFunc(uuid,menuName){
  var childMenuName = menuName;
  var menuURL = "/system/subsys/mymodule/list.jsp?moduleId=" + uuid;
  var url = contextPath + "/system/core/menu/addupdatechild1.jsp?childMenuName=" + encodeURIComponent(childMenuName) + "&menuURL=" + encodeURIComponent(menuURL);
  bsWindow(url ,"菜单定义指南",{width:"760px",height:"360px",buttons:
     [//{name:"关闭",classStyle:"btn btn-primary"}
     ]
  ,submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="修改"){
      
    }else if(v == "删除"){
      
    }else if(v=="关闭"){
      return true;
    }
  }});
}

</script>
</head>
<body onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;模块定义</b>
	</div>
	<div style="text-align:left;">
		<button class="btn btn-primary" onclick="add()">添加</button>
	</div>
</div>
</body>
</html>
