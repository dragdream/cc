<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
	<title>印章管理</title>
	<script type="text/javascript" src="<%=contextPath%>/system/core/system/seal/js/sealmanage.js"></script>
	
	<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/src/orgselect.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	$(function() {
		//userForm = $('#form1').form();
	     userForm=tools.formToJson("#form1");
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/sealLogManage/getSealLogList.action?sort=logTime&' ,
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
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			columns : [ [

			     {
				field : 'sealName',
				title : '印章名称',
				width : 250
			  },  {
				field : 'logTypeDesc',
				title : '日志类型',
				width : 100
			  },  
			  {
			     field : 'userName',
				 title : '操作人',
				 width : 100	
			   } , 
			   {
				field : 'logTimeDesc',
				title : '操作时间',
				width : 150	
			  },
			   {field : 'result',
			    title : '描述',
			     width : 250	 
			} ,		
		    {
				field : 'ipAdd',
				title : 'IP/MAC地址',
				width : 200
				
		}
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
		
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData'); 
}


function  toSealDataInfo(sid){
	var url = contextPath +  "/sealManage/selectById.action?sid=" + sid;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sealData = jsonRs.rtData.sealData;
		showInfoStr(sealData);
		
	   $('#sealInfo').dialog({  
		  title: '查看印章',
          width: 400,
   		  height: 340,
          closed: false,
          cache: false,
          modal: true
        });
	}else{
		alert(jsonRs.rtMsg);
	}
	
	
}

function toSettingPriv(sid){
	window.location.href = "<%=contextPath%>/system/core/system/seal/manager/setPriv.jsp?sid=" + sid;
}

/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}

</script>
</head>
<body>

<div id="toolbar">
    <form id="form1" name="form1">
		<table width="100%" align="center" style="font-size: 12px;">
   			<tr>
   				 <td>
					印章名称:
					<input type="text" name="sealName" id="sealName" value="" class="BigInput"  style="height: 23px;width: 170px"/> 
				  </td>
				  <td>
					日志类型:
					<select name="logType"  class="BigSelect" style="height: 23px;">
					 	<option value="">所有日志</option>
      					<option value="1">制作印章</option>
				      	<option value="2">印章授权</option>
				      	<option value="3">加盖印章</option>
				      	<option value="4">删除日志</option>
				      	<option value="5">恢复</option>      
				      	<option value="6">停用印章</option>     
					</select>
				   </td>
				   <td>
					&nbsp;&nbsp;操作者:
					<input type="hidden" name="opUser" id="opUser" value=""> 
					<input type="text" name="opUserName" id="opUserName" value="" class='Static BigInput' style="height: 23px;width: 150px;"> 
			
					<a href="javascript:void(0);" onClick="selectSingleUser(['opUser', 'opUserName'])">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('opUser', 'opUserName')">清空</a>
         		   </td>
				   <td nowrap class="TableData" >
   				        操作时间:
   				       从 <input type="text" name="beginTime" id="beginTime" size="20" maxlength="20" class="BigInput" value="" style="height: 23px;width: 150px;"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})">

       				 至<input type="text" name="endTime" id="endTime" size="20" maxlength="20" class="BigInput" value="" style="height: 23px;width: 150px;"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'endTime\')}'})">
      
  				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" class="btn-win-white" onclick="query()">
  				</td>
  			</tr>
   			</table>
		</form>


</div>
<table id="datagrid"></table>
</body>
</html>