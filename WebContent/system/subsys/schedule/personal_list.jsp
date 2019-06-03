<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>个人计划</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/schedule/datagrid.action?type=1',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageSize : 30,
		pageList: [30,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		remoteSort:true,
		nowrap:false,
		columns:[[
			{field:'title',title:'标题',width:'400',sortable:true},
			{field:'rangeDesc',title:'计划时间',width:330},
			{field:'flag',title:'状态',width:100,sortable:true,formatter:function(data,rows,index){
				if(data==0){
					return "<span style='color:green'>进行中</span>";
				}else if(data==1){
					return "<span style='color:red'>已完成</span>";
				}else{
					return "<span style='color:orange'>超时完成</span>";
				}
			}},
			{field:'rangeType',title:'类型',width:150,sortable:true,formatter:function(data,rows,index){
				if(data==1){
					return "日计划";
				}else if(data==2){
					return "周计划";
				}else if(data==3){
					return "月计划";
				}else if(data==4){
					return "季度计划";
				}else if(data==5){
					return "半年计划";
				}else if(data==6){
					return "年计划";
				}
			}},
			{field:'_oper',title:'操作',width:200,formatter:function(data,rows,index){
				var render ="";
				if(loginPersonId==rows.managerUserId){
					render = ["<a href='#' onclick=\"detail('"+rows.uuid+"')\">查看</a>"];
				}
				if(loginPersonId==rows.userId){
					render = ["<a href='#' onclick=\"detail('"+rows.uuid+"')\">查看</a>",
					              "<a href='#' onclick=\"edit('"+rows.uuid+"')\">编辑</a>",
					              "<a href='#' onclick=\"del('"+rows.uuid+"')\">删除</a>"];
				}
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});

}

function add(){
	window.location = "manage/addOrUpdate.jsp?type=1";
}

function edit(uuid){
	window.location = "manage/addOrUpdate.jsp?type=1&scheduleId="+uuid;
}

function del(uuid){
	$.MsgBox.Confirm("提示", "确认要删除该计划吗？", function(){
// 	if(window.confirm("确认要删除该计划吗？")){
		var json = tools.requestJsonRs(contextPath+"/schedule/delete.action",{uuid:uuid});
// 		$("#datagrid").datagrid("reload");
// 		$("#datagrid").datagrid("unselectAll");
		if(json.rtState){
//				top.$.jBox.tip(json.rtMsg,"success");
			$.MsgBox.Alert_auto("删除成功！");
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}else{
//				top.$.jBox.tip(json.rtMsg,"error");
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	});
}

function detail(uuid){
	openFullWindow(contextPath+"/system/subsys/schedule/manage/detail.jsp?scheduleId="+uuid);
}

</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_个人计划.png">
		<span class="title">个人计划 </span>
	</div>
	<div class = "right fr clearfix">
	    <button class="btn-win-white fl" onclick="add()">添加</button>
	</div>
	<!--<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;个人计划</span>
				</td>
				<td align=right>
					<button class="btn btn-primary" onclick="add()">添加</button>&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<br/>-->
</div>
</body>
</html>
