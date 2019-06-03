<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmSaleFollowController/datagrid.action?shareFlag=1',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
			}},
			{field:'addPersonName',title:'跟踪人',width:100},
			{field:'customerName',title:'客户名称',width:100},
			{field:'contantsName',title:'联系人姓名',width:100},
			{field:'followTypeDesc',title:'跟踪方式',width:100},
			{field:'followDateDesc',title:'跟踪时间',width:100},
			{field:'nextFollowUserName',title:'下次联系人',width:100},
			{field:'nextFollowTimeDesc',title:'下次跟踪时间',width:100},
			{field:'followResultDesc',title:'跟踪状态',width:100},
			{field:'addPersonName',title:'创建人',width:100}
		]]
	});
}
function showDetail(sid){
	var url = contextPath+"/system/subsys/crm/core/saleFollow/detail.jsp?sid="+sid;
	location.href=url;
	//bsWindow(contextPath+"/system/subsys/crm/core/saleFollow/detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}
function add(){
	var url = contextPath+"/system/subsys/crm/core/saleFollow/addOrEditSaleFollow.jsp";
	location.href=url;
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/subsys/crm/core/saleFollow/addOrEditSaleFollow.jsp?sid="+sid;
	location.href=url;
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		top.$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var sids="";
			for(var i=0;i<selections.length;i++){
				 sids += selections[i].sid+",";
			}
			var url = contextPath+"/TeeCrmSaleFollowController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"success");
			}else{
				top.$.jBox.tip(json.rtMsg,"error");
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}
	});
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}


function getContactUser(customerId,customerName){
	var url = contextPath+"/TeeCrmContactUserController/getContactUserList.action";
	var json = tools.requestJsonRs(url,{customerId:customerId});
	if(json.rtState){
		var contactUserList = json.rtData;
		var html = "<option value=\"0\">全部</option>";
		for(var i=0;i<contactUserList.length;i++){
			html+="<option value=\""+contactUserList[i].sid+"\">"+contactUserList[i].name+"</option>";
		}
		$("#contantsId").html(html);
		
	}
	
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="text-align:left;margin-bottom:10px;margin-top: 10px;">
				<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
	<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
		        <table class="SearchTable" style="text-align:left;width:100%;">
						<tr>
							<td class="SearchTableTitle">所属客户：</td>
							<td colspan='3'>
								<input class="BigInput" type="text" id = "customerName" name='customerName' style="width:180px;"/>
								<input class="BigInput" type="hidden" id = "customerId" name='customerId' style="width:180px;"/>
								<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'],'getContactUser')">选择客户</a></a>&nbsp;&nbsp;
								<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">跟踪时间:</td>
							<td>
								<input type="text" id='startDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='startDateDesc' class="Wdate BigInput" />至
								<input type="text" id='endDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='endDateDesc' class="Wdate BigInput" />
							</td>
						</tr>
						<tr>
							<td class="SearchTableTitle">联系人：</td>
							<td colspan="3">
								<select class="BigSelect"  id = "contantsId" name='contantsId' style="width:180px;">
									<option value='0'>全部</option>
								</select>
								<div style='float:right;'>
									<input type="reset" class="btn btn-primary" value="清空" onclick="clearData('customerId','customerName');">
									<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
								</div>
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