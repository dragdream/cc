<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/examine/js/examine.js"></script>

<script>
var datagrid;

/**
 * 初始化
 */
function doInit(){
	//getLogList();
}
var datagrid;

var userForm;

var title ="";
$(function() {
	userForm = $('#userForm').form();

	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamineTaskManage/datagrid.action",
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
		singleSelect:true,
		columns:[[
					{field:'sid',checkbox:true,title:'ID',width:40,hidden:true},
					{field:'taskTitle',title:'考核任务名称',width:200},
					{field:'rankmanNames',title:'考核人',width:200},
					{field:'participantNames',title:'被考核人',width:200,
						formatter:function(e,rowData,index){	
							return "<a href='#' onclick='getExamineTaskParticipantInfo(" + rowData.sid +")'>" + rowData.participantNames+ "</a>";
							
						}
					},
		
					
					
					{field:'groupId',title:'考核指标集Id',width:10,hidden:true},
					{field:'groupName',title:'考核指标集',width:120,
						formatter:function(e,rowData,index){	
							return "<a href='#' onclick='getExamineGroupInfo(" + rowData.groupId +")'>" + rowData.groupName+ "</a>";
						}
					},
					{field:'anonymityDesc',title:'匿名',width:50},
					{field:'taskBeginStr',title:'生效日期',width:80},
					{field:'taskEndStr',title:'终止日期',width:80},
					{field:'state',title:'状态',width:100 , hidden:true},
					
	
					{field:'stateDesc',title:'状态',width:50,
						formatter:function(e,rowData,index){
							//	0-生效 1-终止 2-待生效
							var stateDesc = "已生效";
							if(rowData.state == '1'){
								stateDesc = "已终止";
							}else if(rowData.state == '2'){
								stateDesc = "待生效";
							}
							return stateDesc;
						}
					
					},
					{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
						var render = [];
						render.push("<a href='#' onclick='toExamineDetail("+rowData.sid+")'>考核情况</a>");
						render.push("<a href='#' onclick='weightSetting("+rowData.sid+")'>权重设置</a>");
						render.push("<a href='#' onclick='toAddUpdate("+rowData.sid+")'>编辑</a>");
						render.push("<a href='#' onclick='delById("+rowData.sid+")'>删除</a>");
						render.push("<br/><a href='#' onclick='updateState("+rowData.sid+",1)'>终止</a>");	
						if(rowData.state == '1'){
							render.push("<a href='#' onclick='updateState("+rowData.sid+",0)'>恢复生效</a>");	
						}else if(rowData.state == '2'){
							render.push("<a href='#' onclick='updateState("+rowData.sid+",0)'>立即生效</a>");	
						}
						return render.join("&nbsp;&nbsp;");
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
   
}

function weightSetting(sid){
	var  url = contextPath + "/system/core/base/examine/task/weightSetting.jsp?sid=" + sid;
	bsWindow(url ,title,{width:"700",height:"320",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate();
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function updateState(sid , state){
	var url =contextPath+"/TeeExamineTaskManage/updateState.action";
	var para = {sid:sid , state : state};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		datagrid.datagrid('reload');
		$.jBox.tip('更新成功','info',{timeout:1000});
	} else {
		alert(jsonObj.rtMsg);
	}
}


function delById(sid){

	$.jBox.confirm("确认删除该考核任务吗，删除后将删除所有考核信息，且不可恢复！","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/TeeExamineTaskManage/delById.action";
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
	var title = "新增考核任务";
	if(id > 0){
		 title = "编辑考核任务";
	}
	var  url = contextPath + "/system/core/base/examine/task/addOrUpdate.jsp?sid=" + id;
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
	 	    <input type='button' class='btn btn-primary' value='新增考核任务' onclick='toAddUpdate("0");' >
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

