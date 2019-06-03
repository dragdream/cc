<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>权限管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		userForm = $('#userForm').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeMenuGroup/getMenuGroupList.action?sort',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'uuid',
			singleSelect:true,
			frozenColumns : [ [ {
				title : 'UUID',
				field : 'uuid',
				hidden:true,
				sortable : true
				
			},{
                field : 'deptsIdStr',
                hidden:true
            },{
                field : 'deptsNameStr',
                hidden:true
            },{
                field : 'menuGroupType',
                title : '模块权限类型',
                width : 100,
                formatter : function(value, rowData, rowIndex) {
                    var val = ""
                    if('00' == value){
                        val = "其他";
                    }else if('01' == value){
                        val = "执法";
                    }else if('02' == value){
                        val = "监督";                     
                    }
                    return val;
                }
            },{
				field : 'deptsIdStr',
				hidden:true
			},{
				field : 'deptsNameStr',
				hidden:true
			},{
				field : 'menuGroupType',
				title : '模块权限类型',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					var val = ""
					if('00' == value){
						val = "其他";
					}else if('01' == value){
						val = "执法";
					}else if('02' == value){
						val = "监督";						
					}
					return val;
				}
			},{
				field : 'menuGroupName',
				title : '模块权限名称',
				width : 200,
				sortable : true
			},{
				field : 'deptName',
				title : '创建部门',
				width : 150,
				sortable : true
			},{
				field : 'unitName',
				title : '所属单位',
				width : 150,
				sortable : true
			}] ],
			columns : [ [ {
				field : 'menuGroupNo',
				title : '模块权限排序',
				sortable : true,
				width : 100
			},{
				field : 'userNum',
				title : '拥有权限组用户数',
				width : 100 
			 },{
				field : '_optmanage',
				title : '操作',
				width : 400,
				formatter : function(value, rowData, rowIndex) {
					var render = ["<a href='#' onclick='viewStatistics(\"" +rowData.uuid + "\");'> 关联用户 </a>&nbsp;&nbsp;"];
					render.push("<a href='#' onclick='setMenuGroup(\"" +rowData.uuid + "\");'> 设置权限 </a>&nbsp;&nbsp;");
					render.push("<a href='#' onclick='setDepts(\"" +rowData.uuid + "\");'> 设置部门 </a>&nbsp;&nbsp;");
					render.push("<a href='javascript:void(0);' onclick='toAddUpdate(\"" +rowData.uuid + "\");'> 编辑 </a>&nbsp;&nbsp;");
			     	if(rowData.uuid!=1){
			     		render.push("<a href='javascript:void(0);' onclick='remove1(\"" +rowData.uuid + "\");'> 删除 </a>");
			     	}
					return render.join("");
			}} ] ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				//$(this).datagrid('unselectAll');
				//$(this).datagrid('selectRow', rowIndex);
				//$('#menu').menu('show', {
				//	left : e.pageX,
				//	top : e.pageY
				//});
				//alert("dd");
			}
		})
});

//查看关联用户情况
function viewStatistics(menuGroupId){
	var url=contextPath+"/system/core/menuGroup/statistics.jsp?menuGroupId="+menuGroupId;
	openFullWindow(url);
}
/**
*删除菜单组
*/
function remove1(id){
	//alert();
	var rows = datagrid.datagrid('getSelections');
<%-- 	if (rows.length != 1) {
		alert("至少选择一条记录！");
		return;
	}else{
		if(confirm("确认要删除所选记录吗，删除后将不可恢复？")){
			var url = "<%=contextPath %>/teeMenuGroup/delMenuGroup.action";
			var jsonRs = tools.requestJsonRs(url,{uuids:rows[0].uuid});
			$.messager.show({
				msg : jsonRs.rtMsg,
				title : '提示'
			});
			datagrid.datagrid('reload');
		}
	} --%>
	
	
	if(confirm("确认要删除所选记录吗，删除后将不可恢复？")){
		var url = "<%=contextPath %>/teeMenuGroup/delMenuGroup.action";
		var jsonRs = tools.requestJsonRs(url,{uuids:id});
		$.messager.show({
			msg : jsonRs.rtMsg,
			title : '提示'
		});
		datagrid.datagrid('reload');
	}
}	
/**
 * 跳转至设置权限页面
 */
function setMenuGroup(uuid){
	 window.location.href = "<%=contextPath %>/system/core/menuGroup/setMenuGroupPrivNew.jsp?uuid=" + uuid;
}

function setDepts(uuid){
	window.location.href = "<%=contextPath %>/system/core/menuGroup/setMenuGroupDepts.jsp?uuid=" + uuid;
}

function selectDept(retArray , moduleId, privNoFlag , noAllDept, callBackFunc) {
	deptRetNameArray = retArray;
	objSelectType  = retArray[2] || "";
	var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType;
	var has = false;
	if (moduleId) {
		url += "&moduleId=" + moduleId ;
	}
	if (privNoFlag) {
		url += "&privNoFlag=" + privNoFlag ;
	}
	if (noAllDept) {
		url += "&noAllDept=" + noAllDept ;
	}
	if(callBackFunc){
		url += "&callBackPara=" + callBackFunc ;
	}
	var IM_OA;
	
	try{
		IM_OA = window.external.IM_OA;
	}catch(e){}

	if(window.showModelDialog || IM_OA){
		dialogChangesize(url, 560, 400);
	}else{
		openWindow(url,"选择人员", 560, 400);
	}
	
}

function selectDeptCallBack(){
	
	//alert($("#deptsIdStr").val());
	
	var url = contextPath+"/teeMenuGroup/setDeptPriv.action";
	var jsonObj = tools.requestJsonRs(url,{groupId:$("#groupId").val(),deptsIdStr:$("#deptsIdStr").val()});
	if(jsonObj.rtState){
		//location='manageGroup.jsp';
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	
}
function toAddUpdate(uuid){
	var url =  "<%=contextPath%>/system/core/menuGroup/addupdate.jsp?uuid=" + uuid;
	dialogChangesize(url , 400, 260);
}


</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;">
		
			<div>
				<a class="easyui-linkbutton" iconCls="icon-add" onclick="toAddUpdate('');" plain="true" href="javascript:void(0);">增加模块权限</a>
				<!-- <a class="easyui-linkbutton" iconCls="icon-remove" onclick="remove();" plain="true" href="javascript:void(0);">删除</a> -->
				<!-- <a class="easyui-linkbutton" iconCls="icon-undo" onclick="datagrid.datagrid('unselectAll');" plain="true" href="javascript:void(0);">取消选中</a> -->
		 	</div>
		</div>
		<table id="datagrid"></table>
		<input type="hidden" id="groupId" value=""/>
		<input type="hidden" id="deptsIdStr" value=""/>
		<input type="hidden" id="deptsNameStr" value=""/>
	</div>

</body>
</html>


