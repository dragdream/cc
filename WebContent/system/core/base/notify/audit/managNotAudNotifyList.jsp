<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "0";
		}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>公告审批</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var seqIds = '<%=seqIds%>';
	var groupId = '<%=groupId%>';
	var para = {};
	para['groupId'] = groupId;
	para['seqIds'] = seqIds;
	$(function() {
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeNotifyController/getAudNotify.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		//	toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
			fit : true,
			queryParams:para,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'sid',
			sortOrder: 'desc',
			striped: true,
			singleSelect:true,
			remoteSort: true,
			toolbar: '#toolbar',
			columns : [ [ 
			 {field:'ck',checkbox:false},{
				title : 'id',
				field : 'sid',
				width : 200,
				hidden:true
				//sortable:true
				
			}, {
				field : 'fromPersonName',
				title : '发布人',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				
				return "<B>"+rowData.fromPersonName+"</B>";
				}
			},{
				field : 'typeId',
				title : '类型',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					return rowData.typeDesc;
				}
			}
			,{
				field : 'typeDesc',
				title : '类型描述',
				width : 100,
				hidden : true
			}
			,{
				field : 'top',
				title : '置顶',
				width : 10,
				hidden : true
			},{
				field : 'subject',
				title : '标题',
				width : 300,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					var top = rowData.top;
					var topDesc = "";
					if(top == '1'){
						topDesc = "red";
					}
					return "<B>标题：</B>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=loadDetail('"+rowData.sid+"') style='color:" + topDesc + "'>"+rowData.subject+"</a>";
				}
			},{
				field : 'userId',
				title : '发布范围',
				width : 240,
				formatter:function(value,rowData,rowIndex){
				var deptNames = rowData.toDeptNames;
				var personNames = rowData.toUserNames;
				var toRolesNames = rowData.toRolesNames;
				var renderHtml = "";
				if(deptNames && deptNames != ""){
					renderHtml = renderHtml + "<div title="+deptNames+"><B>部门：</B>&nbsp;&nbsp;"+deptNames+""+"</div>";
				}
				if(personNames && personNames != ""){
					renderHtml = renderHtml + "<div title="+personNames+"><B>人员：</B>&nbsp;&nbsp;"+personNames+""+"</div>";
				}
				if(toRolesNames && toRolesNames != ""){
					renderHtml = renderHtml + "<div title="+toRolesNames+"><B>角色：</B>&nbsp;&nbsp;"+toRolesNames+""+"</div>";
				}
				
				return "<div>"+renderHtml+"</div>";
				}
			},{
				field : 'sendTime',
				title : '发布时间',
				width : 160,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return getFormatDateTimeStr(rowData.sendTime , 'yyyy-MM-dd HH:mm');
				}
			},{
				field : 'beginDate',
				title : '生效日期',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return value;
				}
			},{
				field : 'endDate',
				title : '终止日期',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return value ;
				}
			},{
				field:'_manage',title:'操作',width : 220,formatter:function(value,rowData,rowIndex){
				var opt = '<a href="#"  onclick="pub(' + rowData.sid + ',1)" value="批准" >批准</a>'
					+'&nbsp;&nbsp;<a href="#"  onclick="pub(' + rowData.sid + ',3)" value="不批准" >不批准</a>';
				return opt ;
				}
			}
			] ]
		});
		
	});


	/**
	*审批   批准/不准
	*/
    function pub(seqId , publish){
		var buttons = [{name:"批准",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}];
		if(publish == '3'){
			height = "346px";
			buttons = [{name:"不批准",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}];
		}
		
	
        var url = contextPath + "/system/core/base/notify/audit/audNotify.jsp?isPub="+publish+"&id="+seqId;
        bsWindow(url,"公告审批",{width:"650",height:"320",buttons:buttons,submit:function(v,h){
            if(v=="关闭"){
            	//$('#datagrid').datagrid("reload");
				return true;
            }
			var cw = $(h)[0].contentWindow;
			if(publish == '1'){//同意
				if(cw.doPub()){
					$.MsgBox.Alert_auto("已批准");
					$('#datagrid').datagrid("reload");
					return true;
				}
			}else{
				if(cw.doNoPub()){
					$.MsgBox.Alert_auto("已拒绝");
					$('#datagrid').datagrid("reload");
					return true;
				}
			}
			
			return false;
        }});
     }
    
    //查看公告详情
    function loadDetail(seqId){
        var url = contextPath + "/system/core/base/notify/person/readNotify.jsp?id="+seqId+"&isLooked=0";
        /* top.bsWindow(url,"公告详情",{width:"900px",height:"400px",buttons:[{name:"关闭"}],submit:function(v,h){
            if(v=="关闭"){
				return true;
            }
        }}); */
        openFullWindow(url);
     }
    
	</script>
</head>
<body style="margin:0px;overflow:hidden">
<div id="toolbar" class ="clearfix" style="margin-top: 8px">

</div>
<table id="datagrid" fit="true"></table>

</body>
</html>