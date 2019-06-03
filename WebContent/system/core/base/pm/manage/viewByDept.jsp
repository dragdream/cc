<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%
String uuid = request.getParameter("uuid");
String name = request.getParameter("name");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>
<script>
var datagrid;
var uuid = "<%=uuid%>";
var name = "<%=name%>";
function doInit(){
	getDeptParent();
	getHrCodeByParentCodeNo("PM_STATUS_TYPE","statusType");
	getHrCodeByParentCodeNo("PM_HOUSEHOLD","household");//户口类型
	getHrCodeByParentCodeNo("PM_MARRIAGE","marriage");
	getHrCodeByParentCodeNo("PM_POLITICS","politics");
	getHrCodeByParentCodeNo("PM_EMPLOYEE_TYPE","employeeType");
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/humanDocController/datagrid.action?deptId="+uuid+"&searchType=<%=request.getParameter("searchType")%>",
		pagination:true,
		singleSelect:false,
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
				return "<a href='#' onclick=\"openDeailInfo("+rowData.sid+",'"+rowData.personName+"')\">完善个人信息</a>&nbsp;&nbsp;<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='showDetail("+index+")'>查看</a>";
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

function add(){
	bsWindow(contextPath+"/system/core/base/pm/manage/add_person.jsp?deptName="+encodeURI(name)+"&deptId="+uuid,"添加新员工",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}});
}

function edit(sid){
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
	<br/>
		<div style="text-align:left;margin-bottom:5px;">
			<button class="btn btn-primary" onclick="add()">添加员工</button>
<!-- 			<button class="btn btn-primary" onclick="importPersonInfo()">导入</button> -->
<!-- 			<button class="btn btn-primary" onclick="exportHumanDoc()">导出</button> -->
			<button class="btn btn-danger" onclick="del()">批量删除</button>
<!-- 			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button> -->
<!-- 			<button class="btn btn-danger" onclick="queryContract()">到期合同</button> -->
		</div>
	<form id="form1" name="form1" method="post">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
		       	<table class="SearchTable" style="text-align:left;">
						<tr>
							<td class="SearchTableTitle">员工姓名：</td>
							<td>
								<input class="BigInput" type="text" id = "personName" name='personName' style="width:160px;"/>
							</td>
							<td class="SearchTableTitle">性别：</td>
							<td>
								<select  class="BigSelect" id="gender" name="gender" style="width:170px;">
								    <option value="全部">全部</option>
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">所属部门：</td>
							<td>
								<input id="deptId" name="deptId"  type="text" style="display:none;"/>
								<ul id="deptIdZTree" class="ztree" style="margin-top:0; width:160px; display:none;"></ul>
							</td>
							<td class="SearchTableTitle">状态：</td>
							<td>
									<select class="BigSelect"  name="statusType" id="statusType" style="width:170px;">
										<option value="全部">全部</option>
									</select>
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">政治面貌：</td>
							<td>
								<select class="BigSelect"  name="politics" id="politics" style="width:170px;" >
										<option value="全部">全部</option>
									</select>
							</td>
							<td class="SearchTableTitle">婚姻状态：：</td>
							<td>
									<select class="BigSelect"  name="marriage" id="marriage" style="width:170px;">
										<option value="全部">全部</option>
									</select>
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">员工类型：</td>
							<td>
								<select class="BigSelect"  name="employeeType" id="employeeType" style="width:170px;" >
										<option value="全部">全部</option>
									</select>
							</td>
							<td class="SearchTableTitle">户口类型：</td>
							<td>
									<select class="BigSelect"  name="household" id="household" style="width:170px;" >
										<option value="全部">全部</option>
									</select>
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">入职时间：</td>
							<td colspan='3'>
								<input type="text" id='startDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startDateDesc' class="Wdate BigInput" />至
								<input type="text" id='endDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endDateDesc' class="Wdate BigInput" />
							</td>
						</tr>
							<tr>
									<td colspan='4' align='right'>
										<br/>
										<input type="reset" class="btn btn-primary" value="清空">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
									</td>
								</tr>
					</table>
		      </div>
		    </div>
		  </div>
		</div>
	</form>
	</div>
</body>
</html>