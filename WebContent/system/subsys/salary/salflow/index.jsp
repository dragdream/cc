<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>
<title>工资管理</title>

	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var title ="";
	$(function() {
		
		getAllAccount('accountId');
		userForm = $('#form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeSalFlowController/getManageSalFlowList.action' ,
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
				field : 'sendTime',
				title : '创建时间',
				width : 120,
				formatter:function(value, rowData, rowIndex){
					return getFormatDateTimeStr(value ,'yyyy-MM-dd HH:mm:ss');
				}
			},  {
				field : 'salCreaterName',
				title : '创建人',
				width : 100
			}, {field : 'salMonth',title : '工资月份',width :100,
				formatter:function(value, rowData, rowIndex){
					return rowData.salYear + "年" + value + "月";
				}
			}
			, {field : 'salYear',title : '工资年份',width :100,hidden:true}
			, {field : 'content',title : '备注',width :200}
			,{field : 'issend',title : '状态',width : 80,
				formatter:function(value, rowData, rowIndex){
					var sex = value;
					if(sex == '1'){
						return  "已终止";
					}
					return "执行中";
				}
			 },{field:'2',title:'操作',width:200,formatter:function(value, rowData, rowIndex){
				 	var str = "";
				 	str += "<a href='javascript:void();' onclick='generate("+rowData.sid+")'>生成</a>";
				 	str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='toAddOrUpdate("+rowData.sid+")'>编辑</a>";
					str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='importSal("+rowData.sid + "," + rowData.accountId + "," + rowData.salYear + "," + rowData.salMonth +")'>导入工资</a>";
					str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='salManager("+rowData.sid + "," + rowData.accountId + "," + rowData.salYear + "," + rowData.salMonth +")'>工资管理</a>";
					return str;
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
	$.jBox.confirm("确认移除所选记录，并删除所有相关工资数据？","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/teeSalFlowController/deleteByIds.action";
			var json = tools.requestJsonRs(url,{ids:ids ,accountId:$("#accountId").val() });
			if(json.rtState){
				$.jBox.tip("删除成功！","success"  );
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
}

/**
 * 生成
 */
function generate(sid){
	if(window.confirm("确定要生成并发放工资条吗？")){
		var url = contextPath+"/salDataPersonManage/generate.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			alert("成功生成工资表！");
			datagrid.datagrid("reload");
		}else{
			alert(json.rtMsg);
		}
	}
}

/**
 * 编辑
 */
function toAddOrUpdate(sid){
	var title = "新增";
	if(sid > 0){
		 title = "编辑";
	}
	var  url =   "addOrUpdate.jsp?sid=" + sid;	
	bsWindow(url ,title,{width:"600",height:"200",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(json){
				datagrid.datagrid('reload');
			});
	
			//return ;
		}else if(v=="关闭"){
			return true;
		}
	}});
}
/**
 * 导入
 */
function importSal(sid , accountId  , salYear , salMonth){
	window.location.href = "<%=contextPath%>/system/subsys/salary/manage/import.jsp?accountId=" + accountId + "&salYear = "+ salYear + "&salMonth=" + salMonth + "&flowId=" + sid;
}
function salManager(sid , accountId  , salYear , salMonth){
	
	
	var url  = "<%=contextPath%>/system/subsys/salary/manage/index.jsp?accountId=" + accountId + "&salYear="+ salYear + "&salMonth=" + salMonth+ "&flowId=" + sid;
	//openFullWindow(url,'工资管理');
	window.location.href = url;
}
</script>
</head>
<body >		 
<table id="datagrid"></table>
<div id="toolbar" style=""> 
<div class="base_layout_top" style="position:static">
	<table width="100%">
		<tr>
			<td>
				<span class="easyui_h1">工资管理</span>
			</td>
			<td align=right>
				<button type="button" class="btn btn-danger" onclick="del()">批量删除</button>
		<input type="button" class="btn btn-primary" data-toggle="modal" data-target="#createModal" value="新建工资"  onclick="toAddOrUpdate();">
			</td>
		</tr>
	</table>
</div>
	<form id="form1" name="form1" style="padding:10px;">
		<table class="" width="70%"  >
   			<tr>
   				 <td nowrap class="TableData" colspan="2">
			  		工资账套:
					<select name='accountId' id="accountId" class='BigSelect' onchange="query();">
						<option value="">--请选择帐套--</option>
					</select>
					年份：
					<select name="salYear" class="BigSelect"   onchange="query();" >
		       	  	<%
		       	  	for(int i = 2000 ; i <2100 ; i++){
		       	  		if(year == i){	
		       	  	%>
		       	  	<option value="<%=i %>" selected="selected"><%=i %>年</option>
		       	  	<%}else{ %>
		       	  	<option value="<%=i %>"><%=i %>年</option>
		       	  	<%	}}
		       	  	%>
		       	  </select>
		       	  
		       	  <select name="salMonth"  class="BigSelect"  onchange="query();">
		       	  	<option value=''>--全部--</option>
		       	  	<%
		       	  	for(int i = 1 ; i <13 ; i++){
		       	  		if(month == i){	
		       	  	%>
		       	  		<option value="<%=i %>" ><%=i %>月</option>
		       	  	<%
		       	  		}else{
		       	  	%>       
		       	  		<option value="<%=i %>"><%=i %>月</option>
		       	  	<%	}}
		       	  	%>
		       	  </select>
				</tr>	
   		</table>
	</form>
</div>
</body>
</html>
