<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<title>分类设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var treeGrid;
/**
   初始化列表
 */
function doInit(){
	treeGrid=$("#treeGrid").treegrid({
		url: contextPath + "/zhiDaoCatController/getAllCat.action",
		method: 'post',
        idField: 'sid',
        treeField: 'catName',
        pagination:false,
        border:false,
        columns:[[
      			{field:'catName',title:'分类名称',width:350},
      			{field:'managerNames',title:'分类管理员',width:550},
      			{field:'opt_',title:'操作',width:250,formatter:function(value,rowData,rowIndex){
      			   var opt="<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
      			   opt+="<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>";
      			   return opt;
      			}}
      		]]
        
	});
}


function del(sid){
	 $.MsgBox.Confirm ("提示", "删除后不可恢复，是否确认删除？", function(){
		 var url=contextPath+"/zhiDaoCatController/delBySid.action";
			var json=tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				treeGrid.treegrid('reload');
				$.MsgBox.Alert_auto("删除成功！");
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}  
	  });
}


/**
 *新增分类
 */
function addOrUpdate(id){
	var title="";
	if(id>0){
		title="编辑分类";
	}else{
		title="新建分类";
	}
	var url = contextPath + "/system/subsys/zhidao/houtai/addOrUpdateCat.jsp?sid=" + id;
	bsWindow(url ,title,{width:"600",height:"200",buttons:[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){	
			var isStatus = cw.doSaveOrUpdate();
			if(isStatus){
				$.MsgBox.Alert_auto("操作成功！");
				treeGrid.treegrid('reload');
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}


</script>
</head>
<body style="margin:0px;overflow:hidden;padding-bottom: 30px" onload="doInit();">
<div id="toolbar" class = "clearfix">
   <div class="left fl setHeight">
	   <input type="button" value="新增分类" class="btn-win-white fr" onclick="addOrUpdate(0);">
   </div> 
</div>
 
    <table id="treeGrid" class="easyui-treegrid" fit="true" ></table>

</body>

</html>