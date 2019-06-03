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
<title>保险设置</title>

	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var title ="";
	$(function() {
		
		getAllAccount('accountId');
		userForm = $('#form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/salaryManage/datagridInsurances.action' ,
			toolbar : '#toolbar',
			title : title,
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : false,
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:false,
			columns : [ [
			    {
				field : 'insuranceName',
				title : '保险套账名称'
			},{
				field : 'pensionPPay',
				title : '养老（个人）'
			},  {
				field : 'pensionUPay',
				title : '养老（公司）'
			},{
				field : 'healthPPay',
				title : '医疗（个人）'
			},{
				field : 'healthUPay',
				title : '医疗（公司）'
			},{
				field : 'unemploymentPPay',
				title : '失业（个人）'
			},{
				field : 'unemploymentUPay',
				title : '失业（公司）'
			},{
				field : 'injuryPPay',
				title : '工伤（个人）'
			},{
				field : 'injuryUPay',
				title : '工伤（公司）'
			},{
				field : 'maternityPPay',
				title : '生育（个人）'
			},{
				field : 'maternityUPay',
				title : '生育（公司）'
			},{
				field : 'housingPPay',
				title : '公积金（个人）'
			},{
				field : 'housingUPay',
				title : '公积金（公司）'
			},{field:'2',title:'操作',width:200,formatter:function(value, rowData, rowIndex){
				 	var str = "";
				 	str += "<a href='javascript:void();' onclick='toAddOrUpdate("+rowData.sid+")'>编辑</a>";
				 	str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+rowData.sid+")'>删除</a>";
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
function del(sid){
	if(window.confirm("确认要删除该社保基数吗？")){
		tools.requestJsonRs(contextPath + '/salaryManage/delHrPara.action',{sid:sid});
		query();
	}
}

/**
 * 条件查询
 */
function query(){
	$('#datagrid').datagrid('reload');	
}

/**
 * 生成
 */
function generate(sid){
	var url = contextPath+"/salDataPersonManage/generate.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		$.jBox.tip("成功生成工资表！","success");
		datagrid.datagrid("reload");
	}else{
		alert(json.rtMsg);
	}
}

/**
 * 编辑
 */
function toAddOrUpdate(sid){
	window.location = "addOrUpdate.jsp?sid="+sid;
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
					<span class="easyui_h1">保险设置</span>
				</td>
				<td align=right>
					<input type="button" class="btn btn-primary" value="新建"  onclick="toAddOrUpdate(0);">
				</td>
			</tr>
		</table>
	</div>
	<br/>
</div>
</body>
</html>
