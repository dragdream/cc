<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%
String uuid = request.getParameter("uuid");
String name = request.getParameter("name");
%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Enumeration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>
<script>
var datagrid;
var deptId = "<%=uuid%>";
var deptName = "<%=name%>";
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
	var columns = [
	   	   		{field:'sid',checkbox:true,title:'ID',width:100},
	   			{field:'personName',title:'员工姓名',width:100,formatter:function(e,rowData,index){
	   				return "<a href='#' onclick=\"showDetail("+rowData.sid+",'"+rowData.personName+"')\">"+rowData.personName+"</a>";
	   			}},
	   			{field:'userName',title:'系统用户',width:120,formatter:function(e,rowData){
	   				return rowData.userName;
	   			}},
	   			{field:'gender',title:'性别',width:50},
	   			{field:'deptName',title:'所属部门',width:100,align:'center',formatter:function(e,rowData){
	   				return rowData.deptName;
	   			}},
	   			{field:'statusTypeDesc',title:'状态',width:100,align:'center'},
	   			{field:'mobileNo',title:'手机号码',width:100,align:'center'},
	   			{field:'email',title:'电子邮箱',width:100,align:'center'},
	   			{field:'qqNo',title:'qq号码',width:100,align:'center'},
	   			{field:'joinDateDesc',title:'入职时间',width:100,align:'center'}
	   	   		];
	   	
	   			//获取自定义字段在列表中显示的值
	   		    var url1=contextPath+"/humanDocController/getShowFieldListById.action";
	   		    var json1=tools.requestJsonRs(url1,null);
	   		    if(json1.rtState){
	   		  	  var data1=json1.rtData;
	   		  	  if(data1.length>0){
	   		  		  for(var i=0;i<data1.length;i++){
	   		  			  columns.push({
	   								field:"EXTRA_"+data1[i].sid,
	   								title:data1[i].extendFiledName});
	   		  		  }
	   		  	  }     	  
	   		    }
	   		    
	   		    columns.push({field:'2',title:'操作',align: 'center',width:150,formatter:function(value,rowData,rowIndex){
	   		    	return "<a href='#' onclick=\"openDeailInfo("+rowData.sid+",'"+rowData.personName+"')\">完善个人信息</a>&nbsp;&nbsp;<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='del("+rowData.sid+")'>删除</a>";
	   			}});
	   		    
	   		    
	   			datagrid = $('#datagrid').datagrid({
	   				url:contextPath+"/humanDocController/datagrid2.action",
	   		  		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	   		  		pagination:true,
	   		  		singleSelect:false,
	   		  		queryParams:params,
	   		  		toolbar:'#toolbar',//工具条对象
	   		  		checkbox:false,
	   		  		border:false,
	   		  		idField:'formId',//主键列
	   		  		fitColumns:true,//列是否进行自动宽度适应
	   		  		columns:[columns]
	   		  	});
}


function openDeailInfo(sid,name){
	datagrid.datagrid("unselectAll");
	var url = contextPath + "/system/core/base/pm/archivesManage/detail_index.jsp?sid="+sid+"&personName="+encodeURI(name);
	openFullWindow(url);
}

function showDetail(sid,name){
	datagrid.datagrid("unselectAll");
	var url = contextPath + "/system/core/base/pm/archivesManage/basic_person.jsp?sid="+sid+"&personName="+encodeURI(name);
	openFullWindow(url,"查看详情");
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/pm/archivesManage/edit_person.jsp?deptName="+deptName+"&deptId="+deptId+"&sid="+sid;
	openFullWindow(url,"修改员工信息");
}

function del(sid){
	 $.MsgBox.Confirm ("提示", "是否删除选中信息嘛？删除后不可恢复！",function(){
					var url = contextPath+"/humanDocController/delHumanDoc.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						$.MsgBox.Alert_auto(json.rtMsg);
						datagrid.datagrid("unselectAll");
						datagrid.datagrid("reload");
						return true;				
						}
						$.MsgBox.Alert_auto(json.rtMsg);
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
function except(){
	//var selections = $("#datagrid").datagrid("getSelections");
	var selections = datagrid.datagrid("getSelections");
	if (selections == null || selections == undefined
			|| selections.length <= 0) {
		if(confirm("确定全部导出吗")){
			 $("#frame0").attr("src","<%=contextPath%>/humanDocController/exportExcel.action");
		}
			}
	else{
		   if(confirm("确定导出选中的吗")){
				var sids=[];
				for(var i = 0; i < selections.length; i++){
					sids.push(selections[i].sid);	
				}
			 $("#frame0").attr("src","<%=contextPath%>/humanDocController/exportExcel.action?ids="+sids);
		   }

	}
}
</script>

</head>
<body onload="doInit();" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
<div class="topbar clearfix" id="toolbar">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_detail.png">
		&nbsp;&nbsp;<span class="title">人员列表</span>
	</div>
	<div class="fr">
	   	<button class="btn-win-white" onclick="window.location='search.jsp?uuid=<%=uuid%>&name=<%=name%>'">返回</button>
	   	<button class="btn-win-white" onclick="except();">导出</button>
	<!-- <button class="btn btn-warning" onclick="del()">批量删除</button> -->
	</div>
</div>
<iframe id="frame0" style="display:none"></iframe>
</body>
</html>