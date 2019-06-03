<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	String personName = request.getParameter("personName");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var datagrid;
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_CERT_TYPE" , "certType");
	getHrCodeByParentCodeNo("PM_CERT_ATTR" , "certAttr");
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanCertController/datagrid.action?humanDocSid=<%=humanDocSid%>',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'certCode',title:'证书编号',width:100,formatter:function(e,rowData,index){
				return "<a href='#' onclick=\"showDetail("+rowData.sid+")\">"+rowData.certCode+"</a>";
			}},
			{field:'certTypeDesc',title:'证书类型',width:100},
			{field:'certName',title:'证书名称',width:100},
			{field:'getTimeDesc',title:'获取证书日期',width:100},
			{field:'validTimeDesc',title:'证书生效日期',width:100},
			{field:'endTimeDesc',title:'证书结束日期',width:100},
			{field:'certAttrDesc',title:'证书状态',width:100},
			{field:'certOrg',title:'发证部门',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void();' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+rowData.sid+")'>删除</a>";
			}}
		]]
	});
}

function showDetail(sid){
	var title="查看详情";
	var url = contextPath+"/system/core/base/pm/archivesManage/zs_detail.jsp?sid="+sid+"&personName="+personName;
	bsWindow(url,title,{width:"700",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
	     if(v=="关闭"){
	    	datagrid.datagrid("unselectAll");
			return true;
		}
	}});
}

function add(){
	var title="添加证书信息";
	var url = contextPath+"/system/core/base/pm/archivesManage/zs_add.jsp?humanDocSid="+humanDocSid+"&personName="+personName;
	bsWindow(url ,title,{width:"700",height:"300",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			cw.commit(function(){
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			    window.location.reload();
			});
		}else if(v=="关闭"){
			return true;
		}
	}});

}

function edit(sid){
	var title="修改证书信息";
	var url = contextPath+"/system/core/base/pm/archivesManage/zs_edit.jsp?sid="+sid+"&humanDocSid="+humanDocSid+"&personName="+personName;;
	bsWindow(url ,title,{width:"700",height:"300",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			cw.commit(function(){
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			    window.location.reload();
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function del(sid){
	$.MsgBox.Confirm ("提示", "确认删除选中信息嘛？删除后不可恢复！",function(){
					var url = contextPath+"/TeeHumanCertController/delHumanCert.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						datagrid.datagrid("unselectAll");
						datagrid.datagrid("reload");
						$.MsgBox.Alert_auto(json.rtMsg);
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
	});
}

</script>

</head>
<body onload="doInit();" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
   <div class="fl">
      <input type="button" value="添加证书" class="btn-win-white" onclick="add();"/>
   </div>
</div>
</body>
</html>