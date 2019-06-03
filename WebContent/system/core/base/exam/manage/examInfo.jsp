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
		url:contextPath+"/TeeExamInfoController/datagrid.action",
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
			{field:'examName',title:'考试名称',width:200},
			{field:'attendUserNames',title:'参加考试人',width:300},
			//{field:'scoreTypeDesc',title:'考试时长',width:150},
			//{field:'scoreAll',title:'满分',width:200},
			{field:'startTimeDesc',title:'生效时间',width:150},
			{field:'endTimeDesc',title:'终止时间',width:150},
			{field:'checkDays',title:'查卷推迟天数',width:100},
			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				var html="<a href='#' onclick='edit("+index+")'>修改</a>";
				if(rowData.endTimeDesc==null || rowData.endTimeDesc=="" || rowData.endTimeDesc=="null"){
					html+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='stop("+index+")'>立即终止</a>"
				}
				if(isCheckUser(rowData.sid) && isHasSubjective(rowData.sid)){
					html+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='checkExam("+index+")'>阅卷</a>"
				}
				html+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='allScore("+index+")'>成绩统计</a>"
				html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+index+")'>删除</a>";
				return html;
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
	var url = contextPath+"/system/core/base/exam/manage/examInfo_edit.jsp?sid="+sid;
	//location.href=url;
	bsWindow(url ,"修改考试信息",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commitExamInfo();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("修改成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("修改失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}


//新建考试信息
function add(){
	var url = contextPath+"/system/core/base/exam/manage/examInfo_add.jsp";
	bsWindow(url ,"新建考试信息",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commitExamInfo();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("新建成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("新建失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}



function del(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要删除的考试信息");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm ("提示", "确认删除选中信息吗？", function(){
		var url = contextPath+"/TeeExamInfoController/delExamInfo.action";
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

function stop(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要终止考试信息");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm ("提示", "确认终止该考试信息吗？", function(){
		var url = contextPath+"/TeeExamInfoController/stopExamInfo.action";
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
function sendExamInfo(){
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要发布的考试信息");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm ("提示", "确认发布该考试信息吗？", function(){
		var url = contextPath+"/TeeExamInfoController/sendExamInfo.action";
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
function checkExam(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要查阅的考试信息");
		return;
	}
	var sid = selection.sid;
	location.href= contextPath+"/system/core/base/exam/manage/singleExamList.jsp?sid="+sid;
}
function isCheckUser(sid){
	var url = contextPath+"/TeeExamInfoController/isCheckUser.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		if(json.rtData.isCheckUser){
			return true;
		}
	}else{
		return false;
	}
}
function allScore(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要统计成绩的考试信息");
		return;
	}
	var sid = selection.sid;
	location.href= contextPath+"/system/core/base/exam/manage/userScoresList.jsp?sid="+sid;
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
			var url = contextPath+"/TeeExamInfoController/delExamInfo.action";
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

function isHasSubjective(sid){
	var url = contextPath+"/TeeExamDataController/isHasSubjective.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		if(json.rtData.flag){
			return true;
		}
	}else{
		return false;
	}
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_ksxxgl.png">
			<span class="title">考试信息管理</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl" onclick="add()" value="新建考试信息"/>
			<input type="button" class="btn-del-red fl" onclick="delAll()" value="批量删除"/>
	    </div>
	 </div>
	
</body>
</html>