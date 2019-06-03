<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String timelineUuid = request.getParameter("timelineUuid")==null?"0":request.getParameter("timelineUuid");
	String title = request.getParameter("title")==null?"":request.getParameter("title");
	String type = request.getParameter("type")==null?"0":request.getParameter("type");
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var timelineUuid = "<%=timelineUuid%>";
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeTimelineEventController/datagrid.action?timelineUuid='+timelineUuid,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'title',title:'标题',width:100},
			{field:'startTimeDesc',title:'开始时间',width:100},
			{field:'endTimeDesc',title:'结束时间',width:100},
			{field:'content',title:'内容 ',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='#' onclick='showDetail(\""+rowData.uuid+"\")'>查看</a>&nbsp;&nbsp;<a href='#' onclick='edit(\""+rowData.uuid+"\")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='del(\""+rowData.uuid+"\")'>删除</a>";
			}},
		]]
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/subsys/timeline/manage/eventDetail.jsp?sid="+sid,
			"事件详情",{width:"600",height:"300",buttons:[{name:"关闭",classStyle:"btn-alert-gray"}],
		     submit:function(v,h){
		    	 var cw = h[0].contentWindow;
			        if(v=="关闭"){
			        	$('#datagrid').datagrid("reload");
						return true;
			        }
		    	 }});
}
function addEvent(){
	bsWindow(contextPath+"/system/subsys/timeline/manage/addOrEditEvent.jsp?timelineUuid="+timelineUuid ,"添加事件",{width:"600",height:"300",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				var json = cw.commit();
				if(json.rtState){
					
					$("#datagrid").datagrid("reload");
					return true;
				}
			}else if(v=="关闭"){
				return true;
			}
	}});
}
function edit(sid){
	bsWindow(contextPath+"/system/subsys/timeline/manage/addOrEditEvent.jsp?sid="+sid+"&timelineUuid="+timelineUuid,"编辑事件",{width:"600",height:"300",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				var json = cw.commit();
				if(json.rtState){
					$("#datagrid").datagrid("reload");
					return true;
				}
			}else if(v=="关闭"){
				return true;
			}
	}});
}

function del(uuid){
	$.MsgBox.Confirm ("提示", "确认删除该事件吗？",function(){
		var sids="'"+uuid+"'";
		var url = contextPath+"/TeeTimelineEventController/deleteById.action";
		var json = tools.requestJsonRs(url,{sids:sids});
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success");
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
		}
		datagrid.datagrid("unselectAll");
		datagrid.datagrid("reload");
	});
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}


function back(){
	if("<%=type%>"==1){
		location.href=contextPath+"/system/subsys/timeline/manage/index.jsp";
	}else{
		location.href=contextPath+"/system/subsys/timeline/show/timeline.jsp";
	}
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px；;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<!-- <img id="img1" class = 'title_img' src="/common/zt_webframe/imgs/xzbg/tpgl/icon_toupiaochaxun.png"> -->
		<span class="title">事件管理&nbsp;&nbsp;/&nbsp;<%=title %></span>
	</div>
	<div class = "right fr">
	        <button class="btn-win-white" onclick="addEvent()">新建事件</button>
			&nbsp;
			<button class="btn-win-white" onclick="back()">返回</button>
    </div>
</div>
</body>
</html>