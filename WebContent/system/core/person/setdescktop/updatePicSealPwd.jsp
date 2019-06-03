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
<title>图片签章密码修改</title>
<style>
	.modal-test{
		width: 564px;
		height: 230px;
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
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
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
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/core/system/seal/js/sealmanage.js"></script>
<script type="text/javascript">
var datagrid;
function doInit(){
	var url = "<%=contextPath %>/mobileSeal/myMobileSeals.action";
	
	datagrid = $('#datagrid').datagrid({
		url:url,
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'sealName',title:'签章名称',width:120},
			{field:'userName',title:'签章使用人',width:200},
			{field:'crTime',title:'创建时间',width:200},
			{field:'opt_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
				return "<a href='javascript:void(0);'  class='modal-menu-test' onclick='$(this).modal();updatePwd(\""+rowData.uuid+"\")'>修改密码  </a>";
			}}
		]]
	});
}
//原密码
var oldPwd;
var uid;
function  updatePwd(uuid){
	var url = contextPath +  "/mobileSeal/get.action?uuid=" + uuid;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		oldPwd=jsonRs.rtData.pwd;
		uid=jsonRs.rtData.uuid;
		bindJsonObj2Cntrl(jsonRs.rtData);
		/*  $('#sealInfo').dialog({
			 title:'修改签章密码',
			 width: 300,
			 height: 233,
			 closed: false,
			 cache: false,
			 modal: true
		 });
	   $('#sealInfo').dialog('open').find(':input').val(''); */
	   
	  // $('#sealInfo').dialog('close');
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
		
}

/**
 * 保存信息
 */
function save(){
	    //判断原密码是否输入正确
	    if($("#oldPwd").val()!=oldPwd){
	    	$.MsgBox.Alert_auto("原密码输入错误，请重新输入！");
	    	return;
	    }
		if($("#newPassword").val() != $("#newPassword2").val())
		{
			$.MsgBox.Alert_auto("两次密码不一致，请重新输入！");
		    return;
		}
		 var url = contextPath +  "/mobileSeal/updatePwd.action" ;
			var para = {uuid:uid, pwd : $("#newPassword").val()};
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				$.MsgBox.Alert_auto("保存成功！",function(){
				$("#oldPwd").val("");
				$("#newPassword").val("");
				$("#newPassword2").val("");
				$(".modal-btn-close").click();
				});
				//$('#sealInfo').dialog('close');
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}

}
</script>
</head>

<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
    <table id="datagrid" fit="true"></table>

	<div id="toolbar" class="topbar clearfix">
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_qzgl.png" alt="" />
         &nbsp;<span class="title">签章管理</span>
     </div>
     
     
   <div class="modal-test" >
		<div class="modal-header">
			<p class="modal-title">
				修改密码
			</p>
			<span class="modal-win-close">×</span>
		</div>
		<div class="modal-body">
			<ul>
				<li>
					<span>签章名称:</span>
					<input style="font-family: MicroSoft YaHei; font-size: 12px;"  type="text" id="boxInput" name="sealName" />
				</li>
				<li >
					<span >原密码:</span>
					<input  type="password" name="oldPwd" id="oldPwd" newMaxLength="20"  AUTOCOMPLETE="off"/>
				</li>
				<li >
					<span>新密码:</span>
					<input type="password" name="newPassword" id="newPassword" newMaxLength="20"/>
				</li>
				<li>
					<span>确认新密码:</span>
					<input type="password" name="oldPwd" id="newPassword2" newMaxLength="20"  AUTOCOMPLETE="off"/>
				</li>
				
			</ul>
		</div>
		<div class="modal-footer clearfix" >
		    <input style="width: 45px;height: 25px;" class = "modal-btn-close btn-alert-gray" type="button" value = "关闭"/>
			<input style="width: 45px;height: 25px;" type="button"  class='modal-save btn-alert-blue' value="保存" onclick="save();"/>
		</div>
	</div>
	

</body>
</html>