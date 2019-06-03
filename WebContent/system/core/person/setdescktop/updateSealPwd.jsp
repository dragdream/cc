<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>印章密码修改</title>
<script type="text/javascript"
	src="<%=contextPath%>/system/core/system/seal/js/sealmanage.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style>
	.modal-test{
		width: 564px;
		height: 400px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 24px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		background-color:#f4f4f4;
		/* padding-top: 10px */
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 15px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body table tr td input{
		display: inline-block;
		/* float: right; */
		margin-right:50px;
		width: 350px;
		height: 25px;
		border: 1px solid #dadada;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:35px;
	}
</style>
<script type="text/javascript">
	
function doInit(){
	var url = "<%=contextPath %>/sealManage/getHavePrivSealInfo.action";
	//var para =  tools.formToJson($("#form1")) ;
		datagrid = $('#datagrid').datagrid({
		url:url,
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		singleSelect:true,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'sealId',title:'编号',width:120},
			{field:'sealName',title:'印章名称',width:120},
			{field:'userStrDesc',title:'使用人员权限',width:200},
			{field:'flagDesc',title:'使用状态',width:200,formatter:function(value,rowData,rowIndex){
				var flag = rowData.isFlag;
				var flagDesc = "启用";
				if(flag == 1){
					flagDesc = "已停用";
				}
				return flagDesc;
				
			}},
			{field:'createTimeDesc',title:'创建时间',width:200},
			{field:'opt_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
				
				return "<a href='javascript:void(0);' class='modal-menu-test' onclick='$(this).modal();toUpdatePwd(" + rowData.sid + ")'>修改密码  </a>";
			}}
		]]
	});
}

var updateSealId = 0;
function  toUpdatePwd(sid){
	updateSealId = sid;
	var url = contextPath +  "/sealManage/selectById.action?sid=" + sid;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sealData = jsonRs.rtData.sealData;
		updatePwdShowInfoStr(sealData);
 		/*  $('#sealInfo').dialog({
			 title:'修改印章密码',
			 width: 420,
			 height: 340,
			 closed: false,
			 cache: false,
			 modal: true
		 });
	   $('#sealInfo').dialog('open');  */
	   
	  // $('#sealInfo').dialog('close');
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
		//alert(jsonRs.rtMsg);
	}
	
	
}

/**
 * 保存信息
 */
function save(){
	if(updateSealId && updateSealId > 0){
	
		if($("#newPassword")[0].value != $("#newPassword2")[0].value)
		{
			//$.MsgBox.Alert_auto("两次密码不一致，请重新输入！");
		    alert("两次密码不一致，请重新输入！");
		    return;
		}
		var obj = document.getElementById("DMakeSealV61");
		  //把新密码赋值给activex控件
		obj.strOpenPwd = $("#newPassword")[0].value;
		 
		   //生成新的base64格式的印章数据，保存到activex控件中，同时赋值给页面的隐藏域SEAL_DATA
		var sealData = obj.SaveData();
		 var url = contextPath +  "/sealManage/addOrUpdateSeal.action" ;
			var para = {sid:updateSealId , sealData : sealData};
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				
				 
				//关闭模态框
				$(".modal-win-close").click();
				$.MsgBox.Alert_auto("修改成功！");
				
				//alert("保存成功！");
				//$('#sealInfo').dialog('close');
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
				//alert(jsonRs.rtMsg);
			}
		 

	}else{
		$.MsgBox.Alert_auto("请选择印章！");
		//alert("请选择印章！");
	}
	
}
</script>
</head>

<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">

<table id="datagrid" fit="true"></table>

	<div id="toolbar" class="topbar clearfix">
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_yzgl.png" alt="" />
         &nbsp;<span class="title">印章管理</span>
     </div>
     
     
        
   <div class="modal-test"  >
		<div class="modal-header">
			<p class="modal-title">
				修改密码
			</p>
			<span class="modal-win-close">×</span>
		</div>
		<div class="modal-body" >
			<table class="TableBlock" width="100%" align="center" >
				<tr id="picTr" style="display: none">
					<td class="TableData" colspan=2 style="text-align: center;">
					<OBJECT style="padding-top: 10px;padding-bottom: 10px;z-index:1;position: relative;"
							id=DMakeSealV61 style="left: 0px; top: 0px"
							classid="clsid:3F1A0364-AD32-4E2F-B550-14B878E2ECB1" VIEWASTEXT
							width="200" height="150"
							codebase='<%=contextPath%>/system/core/system/seal/sealmaker/MakeSealV6.ocx#version=1,0,3,4'>
							<param name="wmode" value="opaque">  
							<PARAM NAME="_Version" VALUE="65536">
							<PARAM NAME="_ExtentX" VALUE="2646">
							<PARAM NAME="_ExtentY" VALUE="1323">
							<PARAM NAME="_StockProps" VALUE="0">
						</OBJECT></td>
				</tr>
				<tr>
					<td style="text-indent: 15px;" class="TableData" width="100">印章ID：</td>
					<td class="TableData"><span id="seal_id"></span></td>
				</tr>
				<tr>
					<td style="text-indent: 15px;" class="TableData">印章名称：</td>
					<td class="TableData"><span id="seal_name"></span></td>
				</tr>
				<tr>
					<td style="text-indent: 15px;" class="TableData">新密码：</td>
					<td class="TableData">
						<input type="password" name="newPassword" id="newPassword" newMaxLength="20"/>
					</td>
				</tr>
				<tr>
					<td style="text-indent: 15px;" class="TableData">确认新密码：</td>
					<td class="TableData">
						<input type="password" name="newPassword2" id="newPassword2" newMaxLength="20"/>
					</td>
				</tr>
				</table>
		</div>
		<div class="modal-footer clearfix" >
		<input class = "modal-btn-close btn-alert-gray" type="button" value = "关闭"/>
			<input type="button"  class='modal-save btn-alert-blue' value="保存" onclick="save();"/>
		</div>
	</div>
     
     
	<%-- <table border="0" width="100%" cellspacing="0" cellpadding="3" align="">
		<tr>
			<td class="Big3">&nbsp;&nbsp; <span class="big3"> 印章管理 </span></td>
		</tr>
	</table>

	<div style="padding-top: 10px;" id="sealList"></div>
	<div id="sealInfo" class="easyui-dialog" closed="true">
		<div id="apply_body" class="body" align="" style="display: none;">

			<div class="" style="padding: 8px 0px 5px 10px;">
				<span id="title" class=""><b>印章信息</b></span>
			</div>
			<table class="TableList" width="" align="center">
				<tr>
					<td class="TableData" colspan=2 align="center"><OBJECT
							id=DMakeSealV61 style="left: 0px; top: 0px"
							classid="clsid:3F1A0364-AD32-4E2F-B550-14B878E2ECB1" VIEWASTEXT
							width="200" height="150"
							codebase='<%=contextPath%>/system/core/system/seal/sealmaker/MakeSealV6.ocx#version=1,0,3,4'>

							<PARAM NAME="_Version" VALUE="65536">
							<PARAM NAME="_ExtentX" VALUE="2646">
							<PARAM NAME="_ExtentY" VALUE="1323">
							<PARAM NAME="_StockProps" VALUE="0">
						</OBJECT></td>
				</tr>
				<tr>
					<td class="TableData" width=80>印章ID</td>
					<td class="TableData"><span id="seal_id"></span></td>
				</tr>
				<tr>
					<td class="TableData">印章名称</td>
					<td class="TableData"><span id="seal_name"></span></td>
				</tr>
				<tr>
					<td class="TableData">新密码</td>
					<td class="TableData">
						<input type="text" name="newPassword" id="newPassword" newMaxLength="20"/>
					</td>
				</tr>
				<tr>
					<td class="TableData">确认新密码</td>
					<td class="TableData">
						<input type="text" name="newPassword2" id="newPassword2" newMaxLength="20"/>
					</td>
				</tr>
				<tr>
					<td class="TableData" colspan="2" align='center'>
					
						<input type="button" class='btn btn-primary' value="保存" onclick="save();">
					</td>
					
				</tr>
			</table>
		</div>

	</div> --%>

</body>
</html>