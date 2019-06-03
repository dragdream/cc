<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamPaperController/datagrid.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'title',title:'试卷标题',width:100},
			{field:'paperDesc',title:'试卷说明',width:120},
			{field:'scoreTypeDesc',title:'计分类型',width:150},
			{field:'scoreAll',title:'总分',width:200},
			{field:'qCount',title:'试题数量',width:200},
			//{field:'content',title:'出题日期',width:300},
			{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
				return "<a href='#' onclick='edit("+index+")'>修改</a>"
						+"&nbsp;&nbsp;<a href='javascript:void(0)' onclick='autoSelectQuestion("+index+")'>选题</a>"
// 						+"&nbsp;&nbsp;<a href='#' onclick='handleSelectQuestion("+index+")'>手动选题</a>"
				        +"&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function edit(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要修改的内容");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/system/core/base/exam/manage/paper_edit.jsp?sid="+sid;
	//location.href=url;
	bsWindow(url ,"修改试卷",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("修改成功！");
		    	datagrid.datagrid("reload");
		    	return  true;
		    }else{
		    	$.MsgBox.Alert_auto("修改失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function del(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要删除的试卷");
		return;
	}
	var sid = selection.sid;
	
	 $.MsgBox.Confirm ("提示", "确认删除该试卷信息吗？", function(){
		 var url = contextPath+"/TeeExamPaperController/delExamPaper.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	  });
}

function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.MsgBox.Alert_auto("请选择需要删除项目");
		return;
	}
	
	 $.MsgBox.Confirm ("提示", "确认删除选中信息吗？", function(){
		 for(var i=0;i<selections.length;i++){
			    var sid = selections[i].sid;
				var url = contextPath+"/TeeExamPaperController/delExamPaper.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					$.MsgBox.Alert_auto(json.rtMsg);
				}else{
					$.MsgBox.Alert_auto(json.rtMsg);
				}
			}
					datagrid.datagrid("unselectAll");
					datagrid.datagrid("reload");
	  });
}


function autoSelectQuestion(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要选题的试卷");
		return;
	}
	var sid = selection.sid;
	var qCount = selection.qCount;
	var url = contextPath+"/TeeExamPaperController/autoSelectQuestion.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		$.MsgBox.Alert_auto(json.rtMsg);
		var url = contextPath+"/system/core/base/exam/manage/examPaper_select.jsp?paperId="+sid+"&qCount="+qCount;
		bsWindow(url ,"自动选题",{width:"600",height:"320",buttons:
			[
             {name:"保存",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
			    var json=cw.save();
			    if(json.rtState){
			    	$.MsgBox.Alert_auto(json.rtMsg);
			    	return true;
			    }else{
			    	$.MsgBox.Alert_auto(json.rtMsg);
			    }
			}else if(v=="关闭"){
				return true;
			}
		}}); 
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}



function handleSelectQuestion(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要选题的试卷");
		return;
	}
	var sid = selection.sid;
	var qCount = selection.qCount;
	var url = contextPath+"/system/core/base/exam/manage/examPaper_select.jsp?paperId="+sid+"&qCount="+qCount;
	//location.href=url;
	bsWindow(url ,"手动选题",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.save();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto(json.rtMsg);
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto(json.rtMsg);
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


//新建试卷
function add(){

	var url = contextPath+"/system/core/base/exam/manage/paper_add.jsp";
	//location.href=url;
	bsWindow(url ,"新建试卷",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("新建成功！");
		    	datagrid.datagrid("reload");
		    	return  true;
		    }else{
		    	$.MsgBox.Alert_auto("新建失败！");
		    }
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
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_sjgl.png">
			<span class="title">试卷管理</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl" onclick="add()" value="新建试卷"/>
			<input type="button" class="btn-del-red fl" onclick="delAll()" value="批量删除"/>
		</div>
	</div>

</body>
</html>