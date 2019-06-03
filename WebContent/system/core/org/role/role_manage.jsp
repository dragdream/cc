<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script>
var deptId = "<%=request.getParameter("deptId")==null?"":request.getParameter("deptId")%>";
var deptName = "<%=request.getParameter("deptName")==null?"":request.getParameter("deptName")%>";


function doInit(){
	if(deptId=="0" || deptId==""){
		$("#title0").html("全局角色管理");
	}else{
		$("#title0").html(deptName+"--角色管理");
	}
	
	var column=[{
					field : 'roleName',
					title : '角色名称',
					width : 220
				  }, {
					field : 'roleNo',
					title : '角色排序号',
					width : 100
				  }, {
						field : 'statistics',
						title : '主角色用户数/辅助角色用户数',
						width : 200
					  }];
	//获取当前登录人员的管理范围
	var json = tools.requestJsonRs(contextPath+"/personManager/getPostDeptIds.action");
	var postIds = ","+json.rtData+",";
	if(postIds==",0," || postIds.indexOf(deptId)!=-1){//有管理权限
	   column.push({
			field : '操作',
			title : '操作',
			width : 300,
			formatter:function(data,rowData){
				var opts = [];
				if(rowData.uuid==1){
				  opts.push("不可删除");
				}else{
					opts.push("<a href='javascript:void(0)' onclick='toAddUpdateRole("+rowData.uuid+")'>编辑</a>&nbsp;<a href='javascript:void(0)' onclick='deleteRole("+rowData.uuid+")'>删除</a>");
				
				}
			    //关联用户
			    opts.push("<a href=\"#\" onclick=\"viewStatistics("+rowData.uuid+")\">关联用户</a>");
				return opts.join("&nbsp;&nbsp;&nbsp;");
			}
		  });
	   $("#addBtn").show();
	}	
	var datagridOpt = {
			url:contextPath+'/userRoleController/datagrid.action?deptId='+deptId,
			pagination:true,
			singleSelect:true,
			remoteSort:false,
			striped: true,
			border: false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			fitColumns:false,//列是否进行自动宽度适应
			columns:[column]
		};
	
	$('#datagrid').datagrid(datagridOpt);
	
}


/**
 * 查看统计详情
 */
function viewStatistics(roleId){
	var  url=contextPath+"/system/core/org/role/statistics.jsp?roleId="+roleId;
	openFullWindow(url);
}

/**
 * 新增或者更新
 */
function toAddUpdateRole(id){
	var title = "新建角色";
	if(id > 0){
		 title = "编辑角色";
	}
	var  url = contextPath + "/system/core/org/role/editRole.jsp?uuid=" + id+"&deptId="+deptId+"&deptName="+deptName;
	bsWindow(url ,title,{width:"500",height:"120",buttons:
		[
		 {name:"保存（Save）",classStyle:"btn-win-white"},
	 	 {name:"关闭",classStyle:"btn-win-white"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存（Save）"){
			cw.doSaveOrUpdate(function(json){
				window.location.reload();
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/*删除角色*/
function deleteRole(id){

	if(confirm("确定要删除此角色吗？删除后将不可恢复！")){
		var url = "<%=contextPath %>/userRoleController.action?del";
		var jsonRs = tools.requestJsonRs(url,{'ids':id});
		if(jsonRs.rtState){
			$('#datagrid').datagrid("reload");
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

</script>
</head>
<body onload="doInit()" >
<table id="datagrid" fit="true"></table>
<div id="toolbar" class="topbar clearfix datagrid-toolbar">
	<div class="fl" style="position:static;">
		<img id="img1" class="title_img" src="/common/zt_webframe/imgs/grbg/gryj/icon_shoujianxiang.png">
		<span class="title" id="title0"></span>
	</div>
	<div class="right fr clearfix" style="padding-right:10px">
		<input type="button" class="fr btn-win-white"  value="新增角色"  onclick="toAddUpdateRole(0);" id="addBtn" style="display:none">
		<div style="clear:both;"></div>
	</div>
</div>
</body>
</html>