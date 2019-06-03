<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<style>
	.modal-test{
		width: 564px;
		height: 350px;
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
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 150px;
		background-color:#f4f4f4;
		overflow: auto;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 300px;
		line-height: 30px;
		margin-top: 25px;
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
		width: 400px;
		height: 25px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>

<script>

function doInit(){
	$('#datagrid').datagrid({
		url:contextPath+'/mobileSeal/list.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sealName',title:'签章名称',width:150},
			{field:'userName',title:'签章使用人',width:150},
			{field:'flag',title:'签章状态',width:150,formatter:function(value,rowData,rowIndex){
				if(value==0){
					return "<span style='color:red'>停用</span>";
				}
				return "<span style='color:green'>启用</span>";
			}},
			{field:'deviceNo',title:'绑定设备号',width:150},
			{field:'sealData',title:'签章预览',width:150,formatter:function(value,rowData,rowIndex){
				
				return "<img src=\""+value+"\" class='modal-menu-test' style='height:50px;cursor:pointer' onclick=\"sealView("+rowIndex+",this)\"/>";
			}},
			{field:'_manage',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				var render = [];
				if(rowData.flag==1){
					render.push("<a href='javascript:void(0)' onclick=\"setFlag('"+rowData.uuid+"',0)\">停用</a>");
				}else{
					render.push("<a href='javascript:void(0)' onclick=\"setFlag('"+rowData.uuid+"',1)\">启用</a>");
				}
				render.push("<a href='javascript:void(0)' title='将签章的设备号和密码重置，可重新绑定' onclick=\"reset('"+rowData.uuid+"',1)\">重置</a>");
				render.push("<a href='javascript:void(0)' onclick=\"edit("+rowIndex+")\">编辑</a>");
				render.push("<a href='javascript:void(0)' onclick=\"del('"+rowData.uuid+"')\">删除</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});
}

function reset(uuid){
	 $.MsgBox.Confirm ("提示", "确定要重置该签章的设备号和密码吗？", function(){
		 var json=tools.requestJsonRs(contextPath+"/mobileSeal/reset.action?uuid="+uuid);
			if(json.rtState){
				$.MsgBox.Alert_auto("重置成功！");
				$("#datagrid").datagrid("reload");
			}
	  });
}

function del(uuid){
	 $.MsgBox.Confirm ("提示", "确定要删除该签章吗？", function(){
		  var json=tools.requestJsonRs(contextPath+"/mobileSeal/delete.action?uuid="+uuid);
		  if(json.rtState){
			  $.MsgBox.Alert_auto("删除成功！");
			  $("#datagrid").datagrid("reload");   
		  }
	  });
}

function createSeal(){
	var url=contextPath+"/system/core/system/mobileseal/addOrUpdate.jsp";
	bsWindow(url ,"创建签章",{width:"600",height:"250",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.addOrUpdate();
		    
		    
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


function edit(rowIndex){
	/* var rows = $('#datagrid').datagrid('getRows');
	var data = rows[rowIndex];
	$("#myModalLabel").html("编辑签章");
	$("#form1")[0].reset();
	$("#sealDiv").modal("show");
	$("#userName").val(data.userName);
	$("#userId").val(data.userId);
	$("#uuid").val(data.uuid);
	$("#sealName").val(data.sealName);
	$("#deviceNo").val(data.deviceNo);
	$("#pwd").val(data.pwd);
	$("#tishi").show(); */
	var rows = $('#datagrid').datagrid('getRows');
	var data = rows[rowIndex];
	var url=contextPath+"/system/core/system/mobileseal/addOrUpdate.jsp?uuid="+data.uuid;
	bsWindow(url ,"编辑签章",{width:"600",height:"250",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.addOrUpdate();
		    
		    
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}






function setFlag(uuid,flag){
	var alermStr = "";
	if(flag==1){
		alermStr = "启用";
	}else{
		alermStr = "停用";
	}
	 $.MsgBox.Confirm ("提示", "确认要"+alermStr+"该签章吗？", function(){
			var json=tools.requestJsonRs(contextPath+"/mobileSeal/setFlag.action?uuid="+uuid+"&flag="+flag);
			if(json.rtState){
				$.MsgBox.Alert_auto(alermStr+"成功！");
				$("#datagrid").datagrid("reload");
			}
			
	  });
}

function sealView(rowIndex,obj){
	var rows = $('#datagrid').datagrid('getRows');
	$("#sealImg").attr("src",rows[rowIndex]["sealData"]);
	$(obj).modal();
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px" >
<div id="toolbar" class = "topbar clearfix" >

     <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/tpl/img/tpqz.png">
		<span class="title">图片签章</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white" onclick="createSeal()" value="创建签章"/>
	</div>
</div>
<table id="datagrid" fit="true"></table>


<!-- <form id="form1" name="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" id="uuid" name="uuid" />
	<div class="modal fade" id="sealDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body">
					<table class="none-table">
						<tr>
							<td class="TableData" >签章名称：</td>
							<td class="TableData">
								<input type="text" name="sealName" id="sealName" class="BigInput" value="">
							</td>
						</tr>
						<tr>
							<td class="TableData" >签章使用人：</td>
							<td class="TableData">
								<input type=hidden name="userId" id="userId" value="">
								<input type="text" name="userName" id="userName" class="BigInput readonly" readonly value="">
								<span>
									<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userId', 'userName']);">添加</a>
									<a href="javascript:void(0);" class="orgClear" onClick="$('#userId').val('');$('#userName').val('');">清空</a>
								</span>
							</td>
						</tr>
						<tr>
							<td class="TableData">设备号：</td>
							<td class="TableData"  >
								<input type='text' class="BigInput readonly" readonly id='deviceNo' name='deviceNo' style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td class="TableData">签章密码：</td>
							<td class="TableData"  >
								<input type='password' class="BigInput" id='pwd' name='pwd' style="width:180px;" />
							</td>
						</tr>
						<tr>
							<td class="TableData">签章图片：<br/>支持JPG,GIF,PNG格式</td>
							<td class="TableData">
								<input type='file' class="" id='file' name='file' style="width:180px;" /><br/>
                                                                <div id="tishi" style="display:none;"><font color="red">如果印章没有变化，无需重新上传。</font></div>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			        <button type="button" class="btn btn-primary" onclick="addOrUpdate()">确定</button>
			    </div>
			</div>
		</div>
	</div>
</form> -->



<!-- <div class="modal fade" id="sealViewDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">签章预览</h4>
			</div>
			<div class="modal-body" style="text-align:Center">
				<img id="sealImg" />
			</div>
			<div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		    </div>
		</div>
	</div>
</div> -->

 <!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			签章预览
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">
				 <img id="sealImg" />
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
	</div>
</div>
</body>
</html>