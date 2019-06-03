<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%
	String entrustStatus = request.getParameter("entrustStatus") == null ? "" : request.getParameter("entrustStatus");
    int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>工作规则管理</title>
	<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	var flowId=<%=flowId %>;
	
	var entrustStatus = '<%=entrustStatus%>'; 
	//alert(userName);
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		//alert(entrustStatus);
        var para={};
        if(flowId>0){
        	para["flowId"]=flowId;
        }
		var url = contextPath+"/flowRule/getFlowList.action";

		datagrid = $('#datagrid').datagrid({
			url : url,
			toolbar : '#toolbar',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			singleSelect:true,
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			queryParams:para,
			onLoadSuccess:function(){
				$('#datagrid').datagrid("unselectAll");
			},
			columns : [ [ {
				title : 'id',
				field : 'sid',
				width : 50,
				hidden:true
				//sortable:true
				
			},
			 {
				title : '状态',
				field : 'flowStatus',
				width : 50,
				sortable:true,
				formatter : function(value, rowData, rowIndex) {

					switch (value) {
					case "0":
						value = "<font color='red'>失效</font>";  
						break;
					case "1":
						value = "<font color='green'>生效</font>";  
						break;
					case "2":
						value = "<font color='green'>生效</font>";  
						break;
					default:
						break;
					}
					return value;
				}
				
			}, {
				field : 'flowName',
				title : '流程名称',
				width : 150,
				sortable : true
			},{
				field : 'prcsName',
				title : '委托人',
				width : 100,
				sortable : true
				//formatter : function(value, rowData, rowIndex) {
				//	return rowData;
				//}
			},{
				field : 'userName',
				title : '被委托人',
				width : 100,
				sortable : true
				//formatter : function(value, rowData, rowIndex) {
				//	return rowData;
				//}
			} ,{
				field : 'validTime',
				title : '有效期',
				width : 250,
				sortable : true
				
			} ,{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				return "<a href='javascript:void(0);' onclick=\"edit('"+rowData.sid+"')\">编辑</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"delete0('"+rowData.sid+"')\">删除</a>&nbsp;";
			}}
			] ]
		});
		
	});

	
    function delete0(id){
    	 parent.$.MsgBox.Confirm ("提示", "是否删除该规则？",function(){
    		var url = contextPath+"/flowRule/deleteRule.action";
    		var json = tools.requestJsonRs(url,{ruleId:id});
    		if(json.rtState){
    			datagrid.datagrid('reload');
    			return true;
    		}
    	});
    }
    
    function edit(id){
    	window.location = "editRule.jsp?sid="+id;
    }
    
    function changeStatus(select){
    	var para =  tools.formToJson($("#form1")) ;
    	$('#datagrid').datagrid('load', 
    		para
        );
    }
    function query(){
    	var para =  tools.formToJson($("#form1")) ;
    	if(flowId>0){
    		para["flowId"]=flowId;
    	}
    	
    	$('#datagrid').datagrid('load', 
    		para
        );
    	
    }
    
    function deleteBatch(){
    	parent.$.MsgBox.Confirm ("提示", "是否删除所有委托规则？",function(){
    		var url = contextPath+"/flowRule/delAll.action?toUserId="+$("#toUserId").val()+"&flowId="+flowId;
    		var json = tools.requestJsonRs(url);
    		if(json.rtState){
    			datagrid.datagrid('reload');
    		    return true;
    		}
    	});
    }
	</script>
</head>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
	String uuid = loginPerson.getUuid()+"";
	String userName = loginPerson.getUserName();
// 	if(isAdmin){
// 		uuid = "";
// 		userName = "";
// 	}
%>
<body>
<table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix">
<div class="setHeight <%=!isAdmin?"fl":"fr" %>" style="<%=!isAdmin?"":"margin-right: 10px;" %>">
   <input style="width:65px;" class="btn-del-red" type="button" onclick="deleteBatch()" value="全部删除"/>
</div>
<div class="setHeight fl">
<form id="form1" name="form1">
<table>
	<tr style="<%=!isAdmin?"display:none":"" %>">
		<td style="display:none">
			委托状态：
			<select id="status" name="status" onChange="changeStatus(this);" class="BigSelect">
				<option value="0" id="value1">委托</option>
				<option value="1" id="value2">被委托</option>
			</select>
		</td>
		<td style="width: 290px;" >按人员查询<span style="color: #999;">（可查看其他人员所设置的委托规则）：</span></td>
		<td>
		  <input type="hidden" name="toUserId" id="toUserId" required="true" value="<%=uuid %>">
		  <input name="toUser" id="toUser" rows="1" style="overflow-y: auto;width: 200px;height: 25px;border: 1px solid #dadada;font-family: MicroSoft YaHei;" wrap="yes" readonly  value="<%=userName %>"/> 
		  <span class='addSpan'>
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/add.png" onClick="selectSingleUser(['toUserId', 'toUser'])" value="选择"/>
			     &nbsp;
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/clear.png" onClick="clearData('toUserId', 'toUser')" value="清空"/>
	        </span>
		  <input style="width: 45px;height: 25px;margin-left: 10px;" type='button' class='btn-win-white' value='查询' onclick='query();'/>
		</td>
	</tr>
</table>
</form>
</div>
</div>
</body>
</html>