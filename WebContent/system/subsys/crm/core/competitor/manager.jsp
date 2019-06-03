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

<title>竞争对手管理</title>

	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userForm;
	var title ="";
	$(function() {
		getCrmCodeByParentCodeNo("CONTRACT_STATUS","contractStatus");// 合同状态
		getCrmCodeByParentCodeNo("CONTRACT_CODE","contractCode");// 合同类型 
		
		userForm = $('#form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeCrmCompetitorController/manager.action' ,
			toolbar : '#toolbar',
			title : title,
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : true,
			pageSize : 15,
			pageList : [ 10, 15,20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:false,

			columns : [ [
			    {field:'sid',checkbox:true},{
				field : 'company',
				title : '公司名称',
				width : 200,
				 formatter : function(value, rowData, rowIndex) {
					return "<center><a href='javascript:void(0);'   onclick=\"toDataInfo("  +rowData.sid   + ");\">" + value + "</a></center>";
				} 
			}  ,  {
				field : 'companyAddress',
				title : '公司地址',
				width : 180
			}, {
				field : 'registerCapital',
				title : '注册资本',
				width : 70
			}, {
				field : 'telephone',
				title : '联系电话',
				width : 100
			}, {
				field : 'email',
				title : '邮箱',
				width : 120
			},  {
				field : 'companyScaleDesc',
				title : '公司规模',
				width : 100
			 }, {
				field : 'provinceName',
				title : '所属区域',
				width : 80,
				align:'center'
			 },{
				field : 'companyCreateDate',
				title : '成立时间',
				width : 80	,
				formatter : function(value, rowData, rowIndex) {
					return value.substring(0,10);
				},
				align:'center'
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
	var title = "查看详情";
	var  url = contextPath + "/system/subsys/crm/core/competitor/detail.jsp?sid=" + sid;
	bsWindow(url ,title,{width:"800",height:"410",buttons:
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
		$.jBox.tip("至少选择一条记录！","info");
		return;
	}
	var ids = "";
	for(var i=0;i<selections.length;i++){
		var sid = selections[i].sid;
		ids = ids + sid + ",";
	}
	$.jBox.confirm("确认删除选中竞争对手吗？删除后将不可恢复","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/teeCrmCompetitorController/delByIds.action";
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
  	  <form id="form1" name="form1">
  	  <div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
				<table class="TableBlock" width="80%" align="center" style="padding:5px;" >
		   			<tr>
		   				 <td nowrap class="TableData" colspan="2">
					  		公司名称:
							<input type="text" name="company"  value="" class="BigInput"> 
							公司地址:
							<input type="text" name="companyAddress" value="" class="BigInput" >
						 </td>						
		  			</tr>
		  			<tr>
		  				 <td nowrap class="TableData" colspan="2">
		  						联系方式：
							<input type="text" name="telephone" value="" class="BigInput" >
							
						&nbsp;&nbsp;<input type="button" value="查询" class="btn btn-primary" onclick="query()">
						<input type="reset" value="重置" class="btn btn-primary" >
		  				</td>
		  				
		  			</tr>	
		   		</table>
   		      </div>
		    </div>
		  </div>
		</div>
	</form>
	
	<div style="margin:10px 0px;">
			<button type="button" class="btn btn-primary" onclick="edit()">编辑</button>
			<button type="button" class="btn btn-danger" onclick="del()">删除</button>
			<button type="button" class="btn btn-primary" onclick="toAddOrUpdate(0)">新建竞争对手</button>
			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
	</div>	
	
</div>
</body>
</html>