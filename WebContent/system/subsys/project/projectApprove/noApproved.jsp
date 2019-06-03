<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待审批</title>
</head>
<script>
var  datagrid;
function doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath + "/projectController/getNoApprovedProjectList.action",
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				/* {field:'sid',checkbox:true,title:'ID',width:100}, */
				{field:'projectName',title:'项目名称',width:100,formatter:function(value,rowData,rowIndex){
					return "<a onclick=\"detail('"+rowData.uuid+"')\" href=\"#\">"+rowData.projectName+"</a>";
					
				}},
			    {field:'projectNum',title:'项目编码',width:100},
			    {field:'endTime',title:'计划结束日期',width:100},
			    {field:'managerName',title:'项目负责人',width:100},
			    {field:'projectMemberNames',title:'项目成员',width:400},
			    /* {field:'progress',title:'进度',width:100,formatter:function(value,rowData,rowIndex){
				    return rowData.progress+"%";
			    }}, */
			    {field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				    return "<a href='#' onclick=\"approve('"+rowData.uuid+"')\">同意</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick=\"noApprove('"+rowData.uuid+"')\">拒绝</a>";
			    }},
			    
	            
			]]
		});
		
}

//详情
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/info.jsp?uuid="+uuid;
	openFullWindow(url);
}

//同意
function approve(uuid){
	
	  $.MsgBox.Confirm ("提示", "是否确认同意该项目？", function(){
		  var url=contextPath+"/projectController/approveProject.action";
			var json=tools.requestJsonRs(url,{uuid:uuid,status:3});
			if(json.rtState){
				$.MsgBox.Alert_auto("已同意！");
				datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto("操作失败！");
			}
	  });
}





//拒绝
function noApprove(uuid){ 

	var url=contextPath+"/system/subsys/project/projectApprove/refusedReason.jsp?uuid="+uuid;
	bsWindow(url ,"拒绝原因",{width:"500",height:"133",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"},
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			$.MsgBox.Confirm ("提示", "是否确认拒绝该项目？", function(){
		       var state=cw.commit();
		       if(state){
		    	    $.MsgBox.Alert_auto("已拒绝！");
					datagrid.datagrid("reload");
					d.hide();
		       }else{
		    	   $.MsgBox.Alert_auto("操作失败！");
		       }
		      
	        });
	 }else if(v=="关闭"){
			return true;
		}
	}});
	
}

</script>

<body onload="doInit()" style="padding-right: 10px;padding-left: 10px">
   <table id="datagrid" fit="true"></table>
</body>
</html>