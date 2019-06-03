<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<title>共享合同回款</title>

	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	
	$(function() {
		userForm = $('#form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/crmContractRecvPaymentsController/manager.action?shareFlag=1' ,
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
				
			     {field:'sid',checkbox:true},
			     {field : 'contractId',title : '合同Id',width : 100  , hidden:true},
			     {field : 'contractNo',title : '合同编号',width : 100,
				   	formatter : function(value, rowData, rowIndex) {
						return "<center><a href='javascript:void(0);'   onclick=\"toDataInfo("  +rowData.contractId   + ");\">" + value + "</a></center>";
				  	}
			     },  {
				field : 'contractName',
				title : '合同名称',
				width : 190
			}, {
				field : 'planRecvDate',
				title : '预计回款时间',
				width : 80,
				formatter : function(value, rowData, rowIndex) {
					return getFormatDateTimeStr(value ,'yyyy-MM-dd');
				}
			}, {
				field : 'recvPayAmount',
				title : '回款金额',
				width : 70,
				align:"center"
			}, {
				field : 'recvPaymentFlag',
				title : '是否回款',
				width : 60,
				align:'center',
				formatter : function(value, rowData, rowIndex) {
					var makeInviceDesc = "<h5><span class='label label-warning'>否</span></h5>";
					if(value == '1'){
						makeInviceDesc = "<h5><span class='label label-success'>是</span></h5>";
					}
					return makeInviceDesc;
				}
			 }
			, {
				field : 'managerUserName',
				title : '收款人',
				width : 80,
				align:'center'
			 } ,  {
				field : 'remark',
				title : '备注',
				width : 120
			 }, {
				field : 'makeInvice',
				title : '是否开发票',
				width : 70,
				align:'center',
				formatter : function(value, rowData, rowIndex) {
					var makeInviceDesc = "否";
					if(value == '1'){
						makeInviceDesc = "是";
					}
					return makeInviceDesc;
				}
			 } , {field:'contractCreateUser',title:'合同创建人',width:80},
			 {
				field : 'createTime',
				title : '新建时间',
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
	$.jBox.confirm("确认删除选中合同回款记录吗？删除后将不可恢复","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/crmContractRecvPaymentsController/deleteByIds.action";
			var json = tools.requestJsonRs(url,{ids:ids});
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

//批量设置回款
function batchRecvPayment(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("至少选择一条记录！","info");
		return;
	}
	var ids = "";
	for(var i=0;i<selections.length;i++){
		var sid = selections[i].sid;
		ids = ids + sid + ",";
	}
	$.jBox.confirm("确认将选中合同回款记录批量设置回款吗？","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/crmContractRecvPaymentsController/batchRecvPayment.action";
			var json = tools.requestJsonRs(url,{ids:ids});
			if(json.rtState){
				$.jBox.tip("批量设置回款成功！","success"  );
				datagrid.datagrid("reload");
			}else{
				alert(json.rtMsg);
			}
		}
	});
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
 * 查询
 */
function toDetail(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
	   $.jBox.tip("至少选择一条记录！","info");
		return;
	}
	if(selections.length>1){
		$.jBox.tip("一次只能查看一条数据！","info");
		return;
	}
	sid = selections[0].sid;
	var title = "查看合同回款详情";
	var  url = contextPath + "/system/subsys/crm/core/contact/recvPayment/detail.jsp?sid=" + sid;
	bsWindow(url ,title,{width:"800",height:"370",buttons:
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
</script>
</head>
<body >		 
<table id="datagrid"></table>
<div id="toolbar" style=""> 
  	  <form id="form1" name="form1">
  	  	<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		        </div>
		       <div class="modal-body">
					<table class="TableBlock" width="100%" align="center" style="padding:5px;" >
			   			<tr>
			   				 <td nowrap class="TableData">
						  		合同编号:
						  	</td>
								<td class="TableData">
								<input type="text" name="contractNo"  value="" class="BigInput"> 
								</td>
								<td class="TableData">
								合同名称:
								</td>
								<td class="TableData">
								<input type="text" name="contractName" value="" class="BigInput" > 
			  			</tr>	
			  			<tr>
			   				 <td nowrap class="TableData">
						  		预计回款时间:
						  		</td>
								<td class="TableData">
								<input type="text" name="planRecvDate"  onClick="WdatePicker()"  size="12" value="" class="BigInput"> 
								</td>
								<td class="TableData">
								收款人：
								</td>
								<td class="TableData">
								 <input type="text" name="recvPayPerson" id="recvPayPerson" value="" class="BigInput" />&nbsp;&nbsp;
					       		</td>
			  			</tr>	
			  			<tr>
			  				<td nowrap class="TableData">
			  						是否回款:
			  					</td>
								<td class="TableData">
								<select id="" name="recvPaymentFlag" class="BigSelect">
									<option value=''>全部</option>
						       		<option value='0'>否</option>
						       		<option value='1'>是</option>
					       		</select>
			  				</td>
			  				<td nowrap class="TableData" colspan="2" align="center">
					       		&nbsp;&nbsp;<input type="button" value="查询" class="btn btn-primary" onclick="query()">
					       		&nbsp;&nbsp;<input type="reset" value="重置" class="btn btn-primary" >
			  				</td>
			  			</tr>
			   		</table>
	   		     </div>
			    </div>
			  </div>
			</div>
	</form>
	<div style="margin-bottom:10px;margin-top:10px;">
		<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
	</div>
</div>
</body>
</html>