<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/datagrid-groupview.js"></script>
<script>
function doInit(){
	
	var datagridOpt = {
			url:contextPath+'/userRoleController/datagrid.action',
			pagination:false,
			singleSelect:true,
			pageSize:10000000,
			remoteSort:false,
			striped: true,
			border: false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			fitColumns:false,//列是否进行自动宽度适应
			groupFormatter: function(value,rows){
				return value + ' 共' + rows.length + '项';
			},
			columns:[[
					    {
							field : 'roleName',
							title : '角色/岗位',
							width : 220
						  },{
								field : 'salary',
								title : '岗位薪资',
								width : 220
							},{
							field : '操作',
							title : '操作',
							width : 100,
							formatter:function(data,rowData,rowIndex){
								return "<a href='javascript:void(0)' onclick=\"toAddUpdateRole("+rowIndex+")\">编辑</a>";
							}
						  }
					] ]
		};
	
	$('#datagrid').datagrid(datagridOpt);
	
}

function toAddUpdateRole(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	var uuid = rows[rowIndex].uuid;
	var salary = rows[rowIndex].salary;
	var salaryLevelModel = rows[rowIndex].salaryLevelModel;
	
	$("#uuid").val(uuid);
	$("#salaryTbody").html("");
	$("#salary").val(salary);
	if(salaryLevelModel && salaryLevelModel!=null && salaryLevelModel!="null" && salaryLevelModel!="" && salaryLevelModel!="undefined"){
		var list = eval("("+salaryLevelModel+")");
		for(var i=0;i<list.length;i++){
			addRow(list[i].a,list[i].b);
		}
	}
	$("#productTypeModal").modal("show");
}

function addRow(a,b){
	$("#salaryTbody").append("<tr class='TableData'><td clazz='level'></td><td><input type='text' clazz='salName' class='BigInput' value='"+a+"' /></td><td ><input type='text' clazz='salData' class='BigInput' value='"+b+"' style='width:50px'/><img src='"+contextPath+"/common/images/other/devide.png' onclick='delRow(this)'/></td></tr>");
	recalseq();
}

function delRow(obj){
	$(obj).parent().parent().remove();
	recalseq();
}

function recalseq(){
	$("td[clazz=level]").each(function(i,obj){
		$(obj).html(i+1);
	});
}

function doSave(){
	var url = contextPath+"/userRoleController/updateRoleSalary.action";
    var para =  {}
    para["uuid"] = $("#uuid").val();
    para["salary"] = $("#salary").val();
    var arr = [];
    var as = $("input[clazz=salName]");
    var bs = $("input[clazz=salData]");
    for(var i=0;i<as.length;i++){
    	arr.push({a:as[i].value,b:bs[i].value});
    }
    para["salaryLevelModel"] = tools.jsonArray2String(arr);
    tools.requestJsonRs(url,para);
    $("#productTypeModal").modal("hide");
    $("#datagrid").datagrid("reload");
}

</script>
</head>
<body onload="doInit()" >
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1">岗位薪酬设定</span>
				</td>
				<td align=right>
				</td>
			</tr>
		</table>
	</div>
	<br/>
</div>





<!-- Modal -->
   	<form id="form1" name="form1" method="post">
		<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width:600px;">
		    <div class="modal-content">
		    	 <div class="modal-header">
		        	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		       		<h4 class="modal-title" id="myModalLabel">薪资设定</h4>
		      	</div> 
		      <div class="modal-body">
		         <table align="center" width="60%" class="TableBlock">
				      <tr>
				          <td nowrap class="TableData">岗位工资：</td>
				          <td class="TableData" colspan="3">
				              <input type="text" name="salary" id="salary" size="40" maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
				          </td>
				      </tr>
				    <tr>
				    <td nowrap class="TableData">职级工资：</td>
				    <td nowrap class="TableData">
				         <table class="TableBlock" style="width:50%">
				         	<tr class="TableHeader">
				         		<td>职级&nbsp;<img src="<%=contextPath %>/common/images/other/plus.png" onclick="addRow('',0)"/></td>
				         		<td>名称</td>
				         		<td>工资</td>
				         	</tr>
				         	<tbody id="salaryTbody">
				         	</tbody>
				         </table>
				    </td>
				    </tr>
				     <tr>
				        <td nowrap class="TableData" colspan="4" align="center">
				            <button type="button" class="btn btn-primary" onclick="doSave();">保存</button>
				            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				        	<input type="hidden" name='uuid' id='uuid' value="0"></input> 
				        </td>
				     </tr>
				    </table>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div>
		<input type="hidden" id="uuid" name="uuid"/>
	 </form>
</body>
</html>