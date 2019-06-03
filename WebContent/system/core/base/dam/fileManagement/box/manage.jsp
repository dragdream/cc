<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>卷盒管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var datagrid;
function  doOnload(){
	getList();
}
/**
 *查询管理
 */
function getList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/damBoxController/datagrid.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'boxNo',title:'盒号',width:120},
			{field:'year',title:'年度',width:100},
			{field:'retentionPeriodStr',title:'保管期限',width:120},
			{field:'mj',title:'密级',width:120},
			{field:'fileNum',title:'文件数',width:120},
			{field:'createUserName',title:'创建人',width:100},
			{field:'ope_',title:'操作',width:160,formatter:function(value,rowData,rowIndex){
				
				var opts = "<a href='javascript:void(0);'  onclick='view("+rowData.sid+")'>查看</a>&nbsp;&nbsp;&nbsp;";
				opts+="<a href='javascript:void(0);' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;&nbsp;";
		      	opts+="<a href='javascript:void(0);' onclick='delBySid("+rowData.sid+")'>删除 </a>";
				
				return opts;
			}},
		]]});
}


//新建卷盒
function add(){
	var url = contextPath + "/system/core/base/dam/fileManagement/box/addOrUpdate.jsp";
	bsWindow(url ,"新建卷盒",{width:"600",height:"260",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var json=cw.doSaveOrUpdate();
			if(json.rtState){
				$.MsgBox.Alert_auto("新建成功！",function(){
					datagrid.datagrid("reload");
				});
				return true;
			}else{
				$.MsgBox.Alert_auto("新建失败！");
				return false;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}



//编辑卷盒
function edit(sid){
	var url = contextPath + "/system/core/base/dam/fileManagement/box/addOrUpdate.jsp?sid="+sid;
	bsWindow(url ,"编辑卷盒",{width:"600",height:"260",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var json=cw.doSaveOrUpdate();
			if(json.rtState){
				$.MsgBox.Alert_auto("编辑成功！",function(){
					datagrid.datagrid("reload");
				});
				return true;
			}else{
				$.MsgBox.Alert_auto("编辑失败！");
				return false;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 删除
 */
function delBySid(sid){
	 $.MsgBox.Confirm ("提示", "是否确认删除该卷盒？", function(){
		  var url = contextPath + "/damBoxController/delBySid.action";
		  var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
					
					datagrid.datagrid('reload');
				});	
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}	  
	  });
}

//查看
function view(boxId){
	var url=contextPath+"/system/core/base/dam/fileManagement/box/view.jsp?boxId="+boxId;
    openFullWindow(url);
}


//档案归档
function archive(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].sid);
		}
		var url=contextPath+"/system/core/base/dam/fileManagement/box/archive.jsp?boxIds="+ids.join(",");
		bsWindow(url ,"档案归档",{width:"600",height:"320",buttons:
			[
             {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
			    var json=cw.commit();
			    if(json.rtState){
			    	$.MsgBox.Alert_auto("归档成功！",function(){
			    		datagrid.datagrid("reload");
			    	});
			    	return true;
			    }else{
			    	$.MsgBox.Alert_auto("归档失败！");
			    }
			}else if(v=="关闭"){
				return true;
			}
		}}); 
		
		
	}
	
}
</script>
</head>
<body class="" onload="doOnload();">
  <div id="toolbar" class = "clearfix">
    <div class="left fl setHeight">
	   <input type="button" value="新建卷盒" class="btn-win-white fr" onclick="add();">
	   &nbsp;
	   <input type="button" value="档案归档" class="btn-win-white fr" onclick="archive();"> 
    </div> 
  </div>
 
  <table id="datagrid" fit="true"></table>
</body>

</html>