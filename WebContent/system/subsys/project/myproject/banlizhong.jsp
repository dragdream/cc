<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办理中</title>
</head>
<script>
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectController/getMyProjectListByStatus.action?status=3",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'projectName',title:'项目名称',width:100},
			{field:'projectNum',title:'项目编码',width:100},
			{field:'endTime',title:'计划结束日期',width:80},
			{field:'managerName',title:'项目负责人',width:80},
			{field:'projectMemberNames',title:'项目成员',width:400},
			{field:'progress',title:'进度',width:60,formatter:function(value,rowData,rowIndex){
				return rowData.progress+"%";
			}},
			{field:'opt_',title:'操作',width:220,formatter:function(value,rowData,rowIndex){
				var isManagerOrCreater=rowData.isManagerOrCreater;
				if(isManagerOrCreater==1){
					return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"delay('"+rowData.uuid+"')\">延期</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"report('"+rowData.uuid+"')\">汇报</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"finish('"+rowData.uuid+"')\">完成</a>"
					+"&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"hang('"+rowData.uuid+"')\">挂起</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"task('"+rowData.uuid+"')\">任务</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"chat('"+rowData.uuid+"')\">交流</a>";
				}else{
					return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"chat('"+rowData.uuid+"')\">交流</a>";
				}
				
			}}
		]]
	});
	
}
//详情
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+uuid;
    openFullWindow(url);
}
//延期
function delay(uuid){
	var url=contextPath+"/system/subsys/project/myproject/delay.jsp?uuid="+uuid;
	bsWindow(url ,"项目延期",{width:"500",height:"110",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){	
			var state=cw.commit();
			if(state){
				$.MsgBox.Alert_auto("已延期！");
				//刷新父页面datagrid
				datagrid.datagrid("reload");
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//完成
function finish(uuid){
	$.MsgBox.Confirm ("提示", "是否确认完成该项目？", function(){
		 var url=contextPath+"/projectController/finish.action";
			var param={uuid:uuid};
			var json=tools.requestJsonRs(url,param);
			
			if(json.rtState){
				$.MsgBox.Alert_auto("已完成！");
				datagrid.datagrid('reload');
			} 
	  });
}

//挂起
function hang(uuid){
	$.MsgBox.Confirm ("提示", "是否确认挂起该项目？", function(){
		 var url=contextPath+"/projectController/hang.action";
			var param={uuid:uuid,status:4};
			var json=tools.requestJsonRs(url,param);
			
			if(json.rtState){
				$.MsgBox.Alert_auto("挂起成功！");
				datagrid.datagrid('reload');
			} 
	  });
}

//汇报
function report(uuid){
	var url=contextPath+"/system/subsys/project/myproject/report.jsp?uuid="+uuid;
	bsWindow(url ,"项目汇报",{width:"450",height:"120",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			var state=cw.commit();
			if(state){
				$.MsgBox.Alert_auto("已汇报！");
				datagrid.datagrid("reload");
				return true;
			}
			return cw.commit();
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//任务
function task(uuid){
	var url=contextPath+"/system/subsys/project/myproject/taskList.jsp?projectId="+uuid;
	openFullWindow(url);
}

//交流
function chat(uuid){
	var url=contextPath+"/system/subsys/project/myproject/communication.jsp?uuid="+uuid;
	openFullWindow(url);
}
</script>
<body onload="doInit()">
    <table id="datagrid" fit="true"></table>
</body>
</html>