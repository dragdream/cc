<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>

<title>合同管理</title>

	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var title ="";
	$(function() {
		getCrmCodeByParentCodeNo("CONTRACT_STATUS","contractStatus");// 合同状态
		getCrmCodeByParentCodeNo("CONTRACT_CODE","contractCode");// 合同类型 
		
		userForm = $('#form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeCrmContractController/manager.action?shareFlag=1' ,
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

			columns : [ [
			     {field:'sid',checkbox:true},{
				field : 'contractNo',
				title : '合同编号',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return "<center><a href='javascript:void(0);'   onclick=\"toDataInfo("  +rowData.sid   + ");\">" + value + "</a></center>";
				}
			},  {
				field : 'contractName',
				title : '合同名称',
				width : 150
			}, {
				field : 'customerInfoName',
				title : '客户名称',
				width : 150
			}, {
				field : 'contractStatusDesc',
				title : '合同状态',
				width : 70
			},  {
				field : 'paymentMethodDesc',
				title : '支付方式',
				width : 80
			 }, {
				field : 'contractAmount',
				title : '合同金额',
				width : 70,
				align:'center'

			 }, {
				field : 'responsibleUserName',
				title : '负责人',
				width : 80,
				align:'center'
			 } ,{
					field : 'contractSignDate',
					title : '签订日期',
					width : 80	,
					formatter : function(value, rowData, rowIndex) {
						return getFormatDateTimeStr(value ,'yyyy-MM-dd');
					},
					align:'center'
			 }, {field:'createUserName',title:'创建人',width:80},
			 {
					field : 'createTime',
					title : '创建时间',
					width : 120	,
					formatter : function(value, rowData, rowIndex) {
						return getFormatDateTimeStr(value ,'yyyy-MM-dd HH:mm:ss');
					}
				}
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
		
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
  
}
/**
 * 查看合同详细信息
 */
function toDataInfo(sid){
	var title = "查看合同详情";
	var  url = contextPath + "/system/subsys/crm/core/contact/manage/detail.jsp?sid=" + sid;
	bsWindow(url ,title,{width:"1000",height:"450",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 删除
 */
function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("少选择一条记录！","info");
		return;
	}
	var ids = "";
	for(var i=0;i<selections.length;i++){
		var sid = selections[i].sid;
		ids = ids + sid + ",";
	}
	$.jBox.confirm("确认删除选中合同吗？删除后将不可恢复","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/teeCrmContractController/deleteByIds.action";
			var json = tools.requestJsonRs(url,{sid:ids});
			if(json.rtState){
				$.jBox.tip("删除成功！","success"  );
				datagrid.datagrid("reload");
			}else{
				alert(json.rtMsg);
			}
		}
	});
}
//编辑
function edit(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("至少选择一条记录！","info");
		return;
	}else{
		if(selections.length > 1){
			$.jBox.tip("请选择一条记录进行编辑！","info");
			return;
		}
	}
	var sid = selections[0].sid;
	toAddOrUpdate(sid);
}

/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
	$('#searchDiv').modal('hide');
}

function toAddOrUpdate(sid){
	window.location.href = "addOrUpdate.jsp?sid=" + sid;
}

</script>
</head>
<body >		 
<table id="datagrid"></table>
<div id="toolbar" style=""> 
	
	<div style="margin-bottom:10px;margin-top:10px;">
		<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
  	  <form id="form1" name="form1" style="display:inline-block;text-valign:bottom;">
  	  	<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
				<table class="TableBlock" width="80%" align="center"  >
		   			<tr>
		   				 <td nowrap class="TableData" >
					  		合同编号:
							<input type="text" id="contractNo" name="contractNo"  value="" class="BigInput"> 
							合同名称:
							<input type="text" id="contractName" name="contractName" value="" class="BigInput" >
						 </td>
					</tr>
					<tr>
						  <td nowrap class="TableData" >
							合同类型：
							<select id="contractCode" name="contractCode" class="BigSelect">
				       			<option value=""> 全部</option>
				       		</select> 
				       		合同状态：
				       		<select id="contractStatus" name="contractStatus" class="BigSelect">
				     	  		<option value=""> 全部</option>
				     	  	</select>
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" class="btn btn-primary" onclick="query()">
						&nbsp;<input type="reset" class="btn btn-primary" value="重置" onclick="">
						</td>
		  			</tr>	
		   		</table>
   		      </div>
		    </div>
		  </div>
		</div>
	</form>
	
	</div>
</div>
</body>
</html>