<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>共享计划</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/scheduleShared/datagrid.action',
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
		nowrap:false,
		columns:[[
			{field:'scheduleName',title:'标题',width:300},
			{field:'scheduleTime',title:'计划时间',width:330},
			{field:'scheduleFlag',title:'状态',width:100,formatter:function(data,rows,index){
				if(data==0){
					return "<span style='color:green'>进行中</span>";
				}else if(data==1){
					return "<span style='color:red'>已完成</span>";
				}else{
					return "<span style='color:orange'>超时完成</span>";
				}
			}},
			{field:'scheduleRangeType',title:'周期类型',width:100,formatter:function(data,rows,index){
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
			{field:'scheduleType',title:'计划类型',width:100,formatter:function(data,rows,index){
				if(data==1){
					return "个人计划";
				}else if(data==2){
					return "部门计划";
				}else if(data==3){
					return "公司计划";
				}
			}},
			{field:'readFlag',title:'阅读状态',width:100,formatter:function(data,rows,index){
				if(data==1){
					return "<span style='color:green'>已读</span>";
				}else{
					return "<span style='color:gray'>未读</span>";
				}
			}},
			{field:'scheduleUser',title:'创建人',width:100},
			{field:'_oper',title:'操作',width:100,formatter:function(data,rows,index){
				var render = ["<a href='javascript:void(0)' onclick=\"detail('"+rows.uuid+"','"+rows.scheduleId+"')\">查看</a>"];
				
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}


function detail(uuid,scheduleId){
	tools.requestJsonRs(contextPath+"/scheduleShared/read.action",{uuid:uuid},true,function(){
		datagrid.datagrid("reload");
		datagrid.datagrid("unselectAll");
	});
	openFullWindow(contextPath+"/system/subsys/schedule/manage/detail.jsp?scheduleId="+scheduleId);
}

</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_个人计划.png">
		<span class="title">共享计划 </span>
	</div>
	
<!-- <div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;共享计划</span>
				</td>
				<td align=right>
					
				</td>
			</tr>
		</table>
	</div> -->
</div>
</body>
</html>
