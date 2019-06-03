<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>业务建模</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	var opts = [
	            {field:'bisKey',title:'业务编号',width:250},
				{field:'businessName',title:'业务名称',width:250,sortable:true},
				{field:'businessTitle',title:'业务标题',width:250},
				{field:'_oper',title:'操作',width:300,formatter:function(data,rows,index){
					var render ="";
						render = ["<a href='#' onclick=\"update('"+rows.bisKey+"')\">编辑</a>",
						          "<a href='#' onclick=\"addMenuFunc('"+rows.bisKey+"','"+rows.businessName+"')\">菜单定义</a>",
						          "<a href='#' onclick=\"del('"+rows.bisKey+"')\">删除</a>"];
					return render.join("&nbsp;&nbsp;");
				}}
			];
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/businessModelController/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageSize : 30,
		pageList: [30,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:false,//列是否进行自动宽度适应
		remoteSort:true,
		columns:[opts]
	});
}

/**
 * 更新
 */
function update(bisKey){
	
	var  url = contextPath + "/system/subsys/bisengine/business/addOrUpdateModel.jsp?type=update&bisKey=" + bisKey;
    window.location=url;
}

/**
 * 增加
 */
function add(){
	var  url = contextPath + "/system/subsys/bisengine/business/addOrUpdateModel.jsp?type=add";
    window.location=url;
}
//删除业务模型
function del(uuid){
	$.MsgBox.Confirm ("提示", "是否确认删除？", function(){
		    var url = contextPath
		    + "/businessModelController/delete.action";
			var json = tools.requestJsonRs(url, {bisKey : uuid});
			if (json.rtState) {
				$.MsgBox.Alert_auto(json.rtMsg,function(){
					datagrid.datagrid('reload');
					datagrid.datagrid('unselectAll');
				});	
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
			
	  });
	}


/**
 * 菜单自定义
 */
function addMenuFunc(bisKey,menuName){
  var childMenuName = menuName;
  var menuURL = "/system/subsys/bisengine/business/bis.jsp?bisKey=" + encodeURI(bisKey);
  var url = contextPath + "/system/core/menu/addupdatechild.jsp?childMenuName=" + encodeURIComponent(childMenuName) + "&menuURL=" + encodeURIComponent(menuURL);
  bsWindow(url ,"菜单定义指南",{width:"760",height:"320",buttons:
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
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/bisengine/img/icon_ywjmgl.png">
		<span class="title">业务建模管理 </span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white" onclick="add()" value="新建模型"/>
    </div>
</div>
 
</body>
</html>
