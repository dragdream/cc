<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
	<title>消息查询</title>
	<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
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
			url : contextPath + '/messageManage/getQueryMessage.action?sort=sendTime' ,
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
			    
			    {
					field : 'messageType',
					title : '消息类型',
					width : 60,
					hidden:true
				},
			    {
					field : 'fromId',
					title : '发送人Id',
					width : 10,
					hidden:true
				},
			    {
				field : 'toIdName',
				title : '收信人',
				width : 70
			    },
			    {
					field : 'fromIdName',
					title : '发送人',
					width : 70
				    }
			     ,{
				field : 'content',
				title : '发送内容',
				width : 350
			 },  {
				field : 'sendTimeDesc',
				title : '发送时间',
				width : 150
			 }   , 	
			{
			field : 'remindFlag',
			title : '提醒',
			width : 80,
			formatter : function(value, rowData, rowIndex) {
				var flagDesc = "未阅读";
				if(value == 1){
					flagDesc = "未阅读已弹出";
				}else if(value == 2){
					flagDesc = "已阅读";
				}
			     return flagDesc;
			} 
		}
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
		
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
	//$("#totalPerson").empty();
	//改变列表样式左边线
//$("#totalPerson").append( data.total );
	$(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'});
    $(".datagrid-toolbar").css({"padding":"0px"});   
}


/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}


/**
*删除菜单组
*/
function deleteMessage(){
	var rows = datagrid.datagrid('getSelections');
	if (rows.length <=0) {
		alert("至少选择一条记录！");
		return;
	}else{
		if(confirm("确认要删除所选记录吗?")){
			var ids = "";
			for(var i=0;i<rows.length;i++){
				ids+=rows[i].sid;
				if(i!=rows.length-1){
					ids+=",";
				}
			}
			
			var url = "<%=contextPath %>/messageManage/updateDeleteFlag.action";
			var para = {ids:ids};
			var jsonRs = tools.requestJsonRs(url,para);
		    if(jsonRs.rtState){
		    	datagrid.datagrid('reload');
		    }else{
		    	alert(jsonRs.rtMsg);
		    }
		}
	}
}


</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
	
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;background: #ffffff"> 
			<!-- <div id="toolbar" style="height:auto"> -->
  
		  <div> 
		 <div >
 			<span>
 			
 			</span>
		<%-- <table class="" width="60%" align="" >
   			<tr>
   				 <td nowrap class="TableData" colspan="2" style='vertical-align: middle;font-weight:bolder;'>				
				<img src="<%=stylePath%>/imgs/message.gif" style="padding:10px 5px 0px 0px;" >  消息查询
				</td>	
  			</tr>
   			</table> --%>
		<form id="form1" name="form1">

		<table class="TableBlock" style="width:85%" align="center" >
		
   			<tr>
   				 <td nowrap class="TableData" colspan="2" style='vertical-align: middle;'>	
					消息内容:
					<input type="text" name="content"  class="BigInput" id="content" value="" size="25"  > 
						消息类型:
					<select name="messageType"  class="BigSelect">
					 	<option value="">所有类型</option>
      					<option value="0">网页消息</option>    
					</select>
					对话人:
					<input type="hidden" name="fromId" id="fromId" value=""> 
					<input type="text" name="fromIdName" id="fromIdName" value="" class="BigStatic BigInput"> 
			
					<a href="javascript:void(0);" onClick="selectSingleUser(['fromId', 'fromIdName'])">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('fromId', 'fromIdName')">清空</a>
         		</td>
				
  			</tr>
  			<tr>
   			<td nowrap class="TableData" colspan="2" style='vertical-align: middle;'>
   				操作时间: 
   				
   			
   				 从 <input type="text" name="beginTime" id="beginTime" size="20" maxlength="20" class="BigInput" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
 
       				 至<input type="text" name="endTime" id="endTime" size="20" maxlength="20" class="BigInput" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
      
  				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" class="btn btn-primary" onclick="query()">
  				</td>
  			
   			</table>
   				</form>
		
		<br>
		</div>
		
		<div style="background: #FAFAFA;border-top:1px solid #d3d3d3;border-right:1px solid #d3d3d3;border-left:1px solid #d3d3d3;padding:3px 2px;">	
			&nbsp;<input type="button" class="btn btn-danger btn-xs" onclick="deleteMessage()" value="批量删除"/> 
		  	<font color="red"> (删除对方未读的微讯后，对方将不会接收到)</font>
	
		</div>
		
		
	   </div>
		 
		</div>
		<table id="datagrid"></table>
		
		
		
	</div>
	

</body>
</html>