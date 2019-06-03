<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>
<script>
var datagrid;
var params = {};
function doInit(){
	<%
		Map params = request.getParameterMap();
		Enumeration en = request.getParameterNames();
		String key = null;
		String values [] = null;
		while(en.hasMoreElements()){
			key = (String)en.nextElement();
			values = request.getParameterValues(key);
			%>
			params["<%=key%>"] = "<%=values[0]%>";
			<%
		}
	%>
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/humanDocController/datagrid.action",
		pagination:true,
		singleSelect:false,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'personName',title:'员工姓名',width:100},
			{field:'oaUser',title:'系统用户',width:100,formatter:function(e,rowData){
				return rowData.userName;
			}},
			{field:'gender',title:'性别',width:100},
			{field:'dept',title:'所属部门',width:100,formatter:function(e,rowData){
				return rowData.deptIdName;
			}},
			{field:'statusType',title:'状态',width:100},
			{field:'mobileNo',title:'手机号码',width:100},
			{field:'email',title:'电子邮箱',width:100},
			{field:'qqNo',title:'qq号码',width:100},
			{field:'joinDateDesc',title:'入职时间',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData,index){
				return "<a href='#' onclick=\"openDeailInfo("+rowData.sid+",'"+rowData.personName+"')\">完善个人信息</a>&nbsp;&nbsp;<a href='#' onclick=\"edit("+rowData.sid+",'"+rowData.deptIdName+"','"+rowData.deptId+"')\">编辑</a>&nbsp;&nbsp;<a href='#' onclick='showDetail("+index+")'>查看</a>";
			}}
		]]
	});
}


function openDeailInfo(sid,name){
	datagrid.datagrid("unselectAll");
	var url = contextPath + "/system/core/base/pm/manage/detail_index.jsp?sid="+sid+"&personName="+encodeURI(name);
	//parent.window.addTabs("完善个人信息",url,true);
// 	location.href=url;
	openFullWindow(url);
}

function showDetail(index){
	datagrid.datagrid("selectRow",index);
	var row = datagrid.datagrid("getSelected");
	var url =contextPath+"/system/core/base/pm/manage/detail_person.jsp?sid="+row.sid;
// 	top.addNewTabs(row.personName+"_人事档案详情",url);
// 	bsWindow(contextPath+"/system/core/base/pm/manage/detail_person.jsp?sid="+row.sid,"查看详情",{width:"800",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
// 	openFullWindow(url,"查看详情");
	openFullWindow(url,"查看详情");
}

function edit(sid,name,uuid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/core/base/pm/manage/edit_person.jsp?deptName="+encodeURI(name)+"&deptId="+uuid+"&sid="+sid,"修改员工信息",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}});
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			for(var i=0;i<selections.length;i++){
				var sid = selections[i].sid;
				var url = contextPath+"/humanDocController/delHumanDoc.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					$.jBox.tip(json.rtMsg,"success");
				}else{
					$.jBox.tip(json.rtMsg,"error");
				}
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}
	});
}

/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false
			};
		zTreeObj = ZTreeTool.config(config);
		setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}



function importPersonInfo(){
	bsWindow(contextPath+"/system/core/base/pm/manage/import.jsp","批量导入人员信息",{width:"500",height:"80",submit:function(v,h){
		var cw = h[0].contentWindow;
		cw.commit(function(json){
			if(json.rtState){
				$.jBox.tip("添加成功","success");
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
				window.BSWINDOW.modal("hide");
				return true;
			}
		});
	}});
}

function exportHumanDoc(){
	var url =contextPath+"/humanDocController/exportHumanDoc.action";
    document.form1.action=url;
    document.form1.submit();
    return true;
}

function queryContract(){
	var url = contextPath+"/system/core/base/pm/public/contract.jsp";
	location.href = url;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="padding:5px">
<!-- 			<button class="btn btn-primary" onclick="add()">添加员工</button> -->
<!-- 			<button class="btn btn-primary" onclick="importPersonInfo()">导入</button> -->
<!-- 			<button class="btn btn-primary" onclick="exportHumanDoc()">导出</button> -->
			<button class="btn btn-default" onclick="window.location='search.jsp';">返回</button>
			<button class="btn btn-warning" onclick="del()">批量删除</button>
<!-- 			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button> -->
<!-- 			<button class="btn btn-danger" onclick="queryContract()">到期合同</button> -->
		</div>
	</div>
</body>
</html>