<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%@ include file="/header/upload.jsp" %>
<% 
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
    TeePerson loginUser=(TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>办理中工作</title>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var loginUserUuid=<%=loginUser.getUuid()%>;
	var flowId = <%=flowId%>;
	$(function() {
		query();
	});

	function doPageHandler(){
		query();
	}
	
	function query(){
		var para =  tools.formToJson($("#form")) ;
		var opts = [
				{field:'v.bt',
					title:'公文标题',
					width:120,
					formatter:function(a,data,c){
						return "<a href='javascript:void(0)' onclick=\"lookup('"+data.runId+"')\">"+data.runName+"</a>";
					}
				},{field:'v.zh',
					title:'文号',
					width:60,
					formatter:function(a,data,c){
						return data.zh;
					}
				},
				{field:'noRead',
					title:'待阅数',
					width:50
				},
				{field:'read',
					title:'已阅数',
					width:50
				},
				{field:'createUser',
					title:'传阅人',
					width:50
				},
				{field:'createTime',
					title:'传阅时间',
					width:70
				},
				{field:'_manage',
					title:'操作',
					ext:'@操作',
					width:100,
					formatter:function(value,rowData,rowIndex){
						var render = "";
						if(loginUserUuid==rowData.createUserUuid){
							render+="<a href='javascript:void(0)' onclick=\"detail('"+rowData.uuid+"')\" >查看详情</a>&nbsp;&nbsp;";
							render+="<a href='javascript:void(0)' onclick=\"deleteSingleFunc('"+rowData.uuid+"')\" >删除</a>&nbsp;&nbsp;";
							render+="<a href='javascript:void(0)' onclick=\"takeBackFunc('"+rowData.uuid+"')\" >收回</a>&nbsp;&nbsp;";
							render+="<a href='javascript:void(0)' onclick=\"reSendFunc('"+rowData.uuid+"');\" >重新下发</a>&nbsp;&nbsp;";
						}else{
							render+="<a href='javascript:void(0)' onclick=\"detail('"+rowData.uuid+"')\" >查看详情</a>&nbsp;&nbsp;";
						}
						
						return render;
					}
				}];

		datagrid = $('#datagrid').datagrid({
			url:'<%=contextPath%>/doc/myDocView.action',
			toolbar : '#toolbar',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			queryParams:para,
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: true,
			singleSelect:true,
			columns:[opts],
			pagination:true,
			onLoadSuccess:function(){
				$('#datagrid').datagrid("unselectAll");
			}
		});
	}
	
	

	/**
	 * 单个删除维护信息
	 */
	function deleteSingleFunc(sid){
		deleteObjFunc(sid);
	}
	/**
	 * 批量删除
	 */
	function batchDeleteFunc(){
		var ids = getSelectItem();
		if(ids.length==0){
			$.MsgBox.Alert_auto("至少选择一项！");
			return ;
		}
		deleteObjFunc(ids);
	}
	/**
	 * 删除信息
	 */
	function deleteObjFunc(ids){
		$.MsgBox.Confirm ("提示", "确定要删除所选中记录？",function(){
				var url = contextPath + "/doc/deleteDocViewById.action";
				var para = {sids:ids};
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){
					parent.$.MsgBox.Alert_auto("删除成功！");
					datagrid.datagrid('reload');
				}
		});
	}
	
	
	
	/**
	 * 收回信息
	 */
	function takeBackFunc(ids){
		$.MsgBox.Confirm ("提示", "确定要收回所选中记录？",function(){
				var url = contextPath + "/doc/takeBackDocViewById.action";
				var para = {sids:ids};
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){
					parent.$.MsgBox.Alert_auto("收回成功！");
					datagrid.datagrid('reload');
				}
		});
	}
	
	
	/**
	 * 重发
	 */
	function reSendFunc(id){
		 var url = contextPath + "/system/subsys/doc/feedback/reSendDocView.jsp?uuid=" + id;
		  bsWindow(url ,"公文传阅",{width:"600",height:"300",buttons:
		     [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
		  ,submit:function(v,h,f,d){
		    var cw = h[0].contentWindow;
		    if(v=="确定"){
		    	cw.doSaveOrUpdate(function(){
		    		$.MsgBox.Alert_auto("公文已成功传阅！");
	 			    datagrid.datagrid('reload');
	 			    d.remove();
	 			});
		    }
		    if(v=="关闭"){
		      return true;
		    }
		  }});
	}
	
	
	
	
	
	function lookup(runId){
		var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1";
		openFullWindow(url,"流程详情");
	}
	
	function detail(id){
		var url = contextPath+"/system/subsys/doc/feedback/view_detail.jsp?uuid="+id;
		openFullWindow(url,"反馈详情");
	}
	
	</script>
</head>
<body style="overflow:hidden;font-size:12px;font-family:MicroSoft YaHei; padding-left: 10px;padding-right: 10px;">

<table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gwgl/cyfk/icon_cyfk.png">&nbsp;&nbsp;
		<span class="title">传阅反馈</span>
</div>
	<span class="basic_border" style="padding-top: 10px;"></span>
<form id="form" name="form1" style="padding:2px">
	<table style="width:100%">
		<tr style="height: 30px;">
			<td class="TableData" width="80px;">公文标题：</td>
			<td class="TableData" style="width: 250px;"><input type="text" name="bt" style="height: 25px;width: 200px;font-family: MicroSoft YaHei;" class="BigInput"/></td>
			<td class="TableData" width="50px;">文号：</td>
			<td class="TableData" style="width: 220px;"><input type="text" name="zh"  style="height: 25px;width: 200px;font-family: MicroSoft YaHei;" class="BigInput"/></td>
			<td><input type="button"  onclick="query()" class="btn-win-white"  value="查询"/></td>
		</tr>
	</table>
</form>
</div>
</body>
</html>