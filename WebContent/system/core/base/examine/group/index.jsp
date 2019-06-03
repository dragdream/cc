<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script>
var datagrid;

/**
 * 初始化
 */
function doInit(){
	//getLogList();
}
var datagrid;
var userDialog;
var userForm;
var passwordInput;
var userRoleDialog;
var userRoleForm;
var title ="";
$(function() {
	userForm = $('#userForm').form();

	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamineGroupManage/datagrid.action",
		toolbar : '#toolbar',
		title : title,
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		idField : 'sid',
		singleSelect:false,
		columns:[[
					{field:'sid',checkbox:true,title:'ID',width:100},
					{field:'examineName',title:'考核指标集名称',width:300},
					{field:'examineDesc',title:'考核指标集描述',width:360},
					{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
						var tempStr = "&nbsp;&nbsp;<a href='#' onclick='toItem("+rowData.sid+")'>考核指标集明细</a>"+
							"&nbsp;&nbsp;<a href='#' onclick='toAddUpdate("+rowData.sid+")'>编辑</a>"
								+ "&nbsp;&nbsp;<a href='#' onclick='delById("+rowData.sid+")'>删除</a>";					
						return tempStr;
					}}
				]],onLoadSuccess:onLoadSuccessFunc
	});
	
	
});
/**
 * 获取最大记录数
 */
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
	$("#totalPerson").empty();
	//改变列表样式左边线
	$("#totalPerson").append( data.total );
    $(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'});
    $(".datagrid-toolbar").css({"padding":"0px"});
   
}

function exportSysLogInfo(){
	//var param = tools.formToJson($("#form1"));
	var url =contextPath+"/sysLogManage/exportLogInfo.action";
    document.form1.action=url;
    document.form1.submit();
    return true;
	//var json = tools.requestJsonRs(url,param);
}


function delById(sid){

	$.jBox.confirm("确认删除该指标集信息吗，","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/TeeExamineGroupManage/delById.action";
			var para =  {sid:sid} ;
			var jsonObj = tools.requestJsonRs(url, para);
			if (jsonObj.rtState) {
				datagrid.datagrid('reload');
				$.jBox.tip('删除成功','info',{timeout:1000});
			} else {
				alert(jsonObj.rtMsg);
			}
		}
	});
}


/**
 * 新增或者更新
 */
function toAddUpdate(id){
	var title = "新增指标集";
	if(id > 0){
		 title = "编辑指标集";
	}
	var  url = contextPath + "/system/core/base/examine/group/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,title,{width:"700",height:"320",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(json){
				datagrid.datagrid('reload');
			});
	
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 跳转至考核项目
 */
function toItem(groupId){
	window.location.href = "<%=contextPath%>/system/core/base/examine/item/index.jsp?groupId=" + groupId;
}
</script>

</head>

<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
	
	<div id="toolbar" class="datagrid-toolbar" style="height: auto;background: #ffffff;"> 
		<!-- <div id="toolbar" style="height:auto"> -->
	 	<div style='width:100%;text-align: left;padding:5px 0px;' class="Big3">
	 	    <input type='button' class='btn btn-primary' value='新增指标集' onclick='toAddUpdate("0");' >
   	     </div>
      <div>
	    </div>
	  <!--   <div style="padding-top:10px;padding-left:10px;">

	       </div> -->
	</div>

	<table id="datagrid"></table>
</div>
</body>
</html>

