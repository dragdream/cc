<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>大事记</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
	.modal-test{
		width: 500px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 120px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		/* float: left; */
		width: 400px;
		height: 25px;
		
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>	


<script>
var datagrid;
function doInit(){
	getSysCodeByParentCodeNo("TIMELINE_TYPE","type");
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeTimelineController/datagrid.action',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'title',title:'标题',width:150},
			{field:'typeDesc',title:'事件类型',width:50},
			{field:'content',title:'内容 ',width:100},
			{field:'crTimeDesc',title:'创建时间',width:100},
			{field:'tag',title:'标签',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				 var str = "<a href='#' onclick='showDetail(\""+rowData.uuid+"\")'>查看</a>&nbsp;&nbsp;";
				if(managePriv(rowData.uuid)){
					return str+"<a href='#' onclick='edit(\""+rowData.uuid+"\")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='del(\""+rowData.uuid+"\")'>删除</a>&nbsp;&nbsp;<a href='#' onclick='eventList(\""+rowData.uuid+"\",\""+rowData.title+"\")'>事件管理</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick='showEvent(\""+rowData.uuid+"\")'>事件查看</a>&nbsp;&nbsp;";
				}else{
					return str+"&nbsp;&nbsp;&nbsp;<a href='#' onclick='showEvent(\""+rowData.uuid+"\")'>事件查看</a>";
				}
			}},
		]]
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/subsys/timeline/manage/detail.jsp?sid="+sid,
			"查看大事记详情",{width:"600",height:"300",buttons:[{name:"关闭",classStyle:"btn-alert-gray"}],
		     submit:function(v,h){
		    	 var cw = h[0].contentWindow;
			        if(v=="关闭"){
			        	$('#datagrid').datagrid("reload");
						return true;
			        } 
		     }});
}
function add(){
	var url = contextPath+"/system/subsys/timeline/manage/addOrEditTimeline.jsp";
	location.href=url;
}
function eventList(timelineUuid,title){
	var url = contextPath+"/system/subsys/timeline/manage/eventList.jsp?timelineUuid="+timelineUuid+"&title="+encodeURI(title);
	location.href=url;
}
function edit(sid){
	bsWindow(contextPath+"/system/subsys/timeline/manage/addOrEditTimeline.jsp?sid="+sid ,"编辑大事记",{width:"650", height:"350",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				var json = cw.commit();
				if(json.rtState){
					 $.MsgBox.Alert_auto("保存成功！",function(){
					 window.location.reload();
					//return true;
					 });
				}
			}else if(v=="关闭"){
				return true;
			}
	}});
}

function del(sid){
	 $.MsgBox.Confirm ("提示", "确认删除选中信息吗？",function(){
	//top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
			var sids="";
			sids +="'"+ sid+"'"+",";
			var url = contextPath+"/TeeTimelineController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
	});
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  $(".modal-win-close").click();
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");

	  //$('#searchDiv').modal('hide');
}

function visitPriv(sid){
	var url = contextPath+"/TeeTimelineController/getVisitPriv.action?uuid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		if(json.rtData){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}

function managePriv(sid){
	var url = contextPath+"/TeeTimelineController/getManagePriv.action?uuid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		if(json.rtData){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}


function showEvent(sid){
	openFullWindow(contextPath+"/system/subsys/timeline/show/index.jsp?timelineUuid="+sid);
}
</script>
<style>
	.t_btns>button{
		padding:5px 8px;
		padding-left:22px;
		text-align:right;
		background-repeat:no-repeat;
		background-position:6px center;
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
	}
</style>


</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="/common/zt_webframe/imgs/xzbg/dsj/icon_dsj.png">&nbsp;&nbsp;
		<span class="title">大事记</span>
	</div>
	<div class = "right fr t_btns">
		<button type="button" onclick="$(this).modal();" value="高级检索" class="modal-menu-test"
		style="background-image:url(/common/zt_webframe/imgs/xzbg/dsj/icon_search1.png);cursor: pointer;"
		>&nbsp;高级查询</button>
    </div>
	
</div>
	
	<form id="form1" name="form1">
		<div class="modal-test">
		     <div class="modal-header">
		             <p class="modal-title">
			                            查询条件
		             </p>
		          <span class="modal-win-close">×</span>
	        </div>

		      <div class="modal-body">
		        <table class="TableBlock" width="100%">
								<tr>
									<td style="text-indent:10px;font-size: 12px;" class="TableData">标题：</td>
									<td class="TableData" >
										<input  type='text' id='title' name='title'  style="height: 20px">
									</td>
								</tr>
								<tr>
									<td style="text-indent:10px;font-size: 12px;" class="TableData">事件类型：</td>
									<td class="TableData">
										<select id="type" name="type" class="BigSelect">
											<option value="0">全部</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="TableData" style="text-indent:10px;font-size: 12px;">标签</td>
									<td class="TableData" colspan='3'>
										<input type='text' class="BigInput" id='tag' name='tag'  style="height: 20px"/>
									</td>
								</tr>
							</table>
		      </div>
		      <div class="modal-footer clearfix">
			        <input type="reset" class="btn-alert-gray" value="重置" onclick=""/>
			        <input type="button" class="modal-save btn-alert-blue" onclick="doSearch();" value="查询" />
			  </div>
		</div>
		</form>
</body>
</html>