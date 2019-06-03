<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的手机短信</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeSmsSendPhoneController/datagridForSelf.action',
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'toName',title:'收信人',width:100},
			{field:'phone',title:'手机号码',width:100},
			{field:'content',title:'短信内容',width:400},
			{field:'sendTime',title:'发送时间',width:100,formatter:function(data){
				return getFormatDateStr(data,"yyyy-MM-dd hh:mm");
			}},
			{field:'sendFlag',title:'发送状态',width:100,formatter:function(data){
				if(data==0){
					return "未发送";
				}else if(data==1){
					return "<span style='color:green'>发送成功</span>";
				}else{
					return "<span style='color:red'>发送失败</span>";
				}
			}}
		]],
	});
}
function showDetail(sid){
	var url = contextPath+"/system/core/phoneSms/sendSms/detail.jsp?sid="+sid;
	location.href=url;
}
function add(){
	var url = contextPath+"/system/core/phoneSms/sendSms/addOrUpdate.jsp";
	location.href=url;
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/phoneSms/sendSms/addOrUpdate.jsp?sid="+sid;
	location.href=url;
}
function send(sid){
	
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.MsgBox.Alert_auto("至少选择一项!");
		return;
	}
	$.MsgBox.Confirm("提示","确定要删除所选中消息？",function(){
			var sids="";
			for(var i=0;i<selections.length;i++){
				 sids += selections[i].sid+",";
			}
			var url = contextPath+"/TeeSmsSendPhoneController/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				parent.$.MsgBox.Alert_auto(json.rtMsg);
					datagrid.datagrid("unselectAll");
					datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
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
</script>

</head>
<body onload="doInit()">
     <div id="toolbar" class="clearfix">
	  <div class="setHeight fl" >
		<input style="width:50px;" class="btn-del-red" type="button" onclick="del()" value="删除"/>
	 </div>
	</div>
	<table id="datagrid" fit="true"></table>
<!-- 		<div class="moduleHeader"> -->
<!-- 			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;我的手机短信</b> -->
<!-- 		</div> -->
<!-- 		<div style="text-align:left;"> -->
<!-- 			<button class="btn btn-primary" onclick="add()">添加</button> -->
<!-- 			<button class="btn btn-danger" onclick="del()">删除</button> -->
<!-- 			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button> -->
<!-- 		</div> -->
<div>
	<form hidden id="form1" name="form1" method="post">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
		        <table class="SearchTable" style="text-align:left;">
								<tr>
									<td class="SearchTableTitle">发送人：</td>
									<td>
										<input class="BigInput" type="text" id = "fromId" name='fromId' style="width:160px;"/>
									</td>
									<td class="SearchTableTitle">接收人手机号：</td>
									<td>
										<input type='text' class="BigInput" id='phone' name='phone' style="width:160px;" />
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">发送时间：</td>
									<td colspan='3'>
										<input type="text" id='startTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='startTimeDesc' class="Wdate BigInput" />
										至<input type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='endTimeDesc' class="Wdate BigInput" />
									</td>
								</tr>
								<tr>
									<td colspan='4' align='right'>
										<input type="reset" class="btn btn-primary" value="清空">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
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