<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>表单分类管理</title>
	<style>
	.modal-test{
		/* width: 564px;
		height: 230px; */		
		width:auto;
		min-width:500px;
		height: auto;
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
		height: auto;
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
		height: 40px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
	.form-control{
	    display:none;
	}
</style>
	<script>
	var contextPath = "<%=contextPath%>";
	var datagrid = $('#datagrid');
	function doInit(){
		initDataGrid();
	}

	function initDataGrid(){
		$('#datagrid').datagrid({
			url:'<%=contextPath%>/formSort/datagrid.action',
			pagination:true,
			singleSelect:false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			border:false,
			fitColumns : true,
			idField:'sid',//主键列
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns:[[
				{field:'sid',checkbox:true,title:'ID'},
				{field:'sortName',title:'分类名称',width : 200,sortable : true},
				{field:'orderNo',title:'排序号',width : 200,sortable : true},
				{field:'_manage',title:'管理',width : 200,formatter:function(value,rowData,rowIndex){
					return "<a href='#' class='modal-menu-test' onclick='edit("+rowData.sid+");'>编辑</a>&nbsp;<a href='#' onclick='toPrev("+rowData.sid+")'>上移</a>&nbsp;<a href='#' onclick='toNext("+rowData.sid+")'>下移</a>";
				}}
			]]
		});
		$(".datagrid-header-row td div span").each(function(i,th){
			var val = $(th).text();
			 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
		});
	}

	function add(){
		//window.openWindow("","",371,200);
// 		$.jBox.open("iframe:"+contextPath+"/system/core/workflow/formsort/manage/add.jsp", "添加表单分类", 480, 210, {buttons: { '确定': 'ok','取消':'cancel' },submit:function (v, h, f) { 
// 			if(v=="ok"){
// 				var cw = $(h).find("iframe:first")[0].contentWindow;
// 				if(cw.commit()){
// 					$('#datagrid').datagrid("reload");
// 					$('#datagrid').datagrid("unselectAll");
// 					return true;
// 				}
// 				return false;
// 			}
// 		}});
// 		window.event.cancelBubble=true;
// 		$("#myModal").modal("show");
//         $("modal-menu-test").modal();
		$("#myModalLabel").html("添加分类");
		$("#sortName").attr("value","");
		$("#formSortId").val(0);
	}

	function edit(formSortId){
// 		$("#myModal").modal("show");
        $(".modal-menu-test").modal();
		$("#myModalLabel").html("编辑分类");
		var url = contextPath+"/formSort/get.action";
		var json = tools.requestJsonRs(url,{formSortId:formSortId});
		if(json.rtState){
// 			$("#sortName").attr("value",json.rtData.sortName);
			$("#sortName").val(json.rtData.sortName);
		}
		
		$("#formSortId").val(formSortId);
	}

	function toPrev(formSortId){
		var url = contextPath+"/formSort/doSortOrder.action";
		var json = tools.requestJsonRs(url,{type:1,formSortId:formSortId});
		$('#datagrid').datagrid('reload');
		$('#datagrid').datagrid("unselectAll");
		window.event.cancelBubble=true;
	}

	function toNext(formSortId){
		var url = contextPath+"/formSort/doSortOrder.action";
		var json = tools.requestJsonRs(url,{type:2,formSortId:formSortId});
		$('#datagrid').datagrid('reload');
		$('#datagrid').datagrid("unselectAll");
		window.event.cancelBubble=true;
	}

	function deleteBatch(){
		var selections = $('#datagrid').datagrid('getSelections');
		if(selections.length==0){
// 			$.jBox.tip("至少选择一项", 'info');
			$.MsgBox.Alert_auto("至少选择一项");
		}else{
			var ids = "";
			for(var i=0;i<selections.length;i++){
				ids+=selections[i].sid;
				if(i!=selections.length-1){
					ids+=",";
				}
			}

// 			$.jBox.confirm("删除后所属表单分类自动转为默认分类，确定删除该表单分类吗？", "提示", function(v, h, f){
			$.MsgBox.Confirm("提示","删除后所属表单分类自动转为默认分类，确定删除该表单分类吗？",function(){
// 				if (v != 'ok') return;

				var url = contextPath+"/formSort/deleteBatch.action";
				var json = tools.requestJsonRs(url,{sortIds:ids});
				if(json.rtState){
// 					top.$.jBox.tip("删除成功", 'success');
					$.MsgBox.Alert_auto("删除成功");
					$('#datagrid').datagrid("reload");
					$('#datagrid').datagrid("unselectAll");
				}else{
// 					top.$.jBox.tip(json.rtMsg, 'error');
					$.MsgBox.Alert_auto(json.rtMsg);
				}
			});
			
		}
	}
	
	function commit(){
		if(!$("#form").valid()){
			return false;
		}
		var url ;
		if($("#formSortId").val()=="0"){//增加
			url = contextPath+"/formSort/save.action";
		}else{//更新
			url = contextPath+"/formSort/update.action";
		}
		
		var para = tools.formToJson($("#form"));
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg, 'success');
            $.MsgBox.Alert_auto(json.rtMsg);
			$('#datagrid').datagrid('reload');
			$('#datagrid').datagrid("unselectAll");
// 			$("#myModal").modal("hide");
			$(".modal-win-close").click();
		}else{
// 			top.$.jBox.tip(json.rtMsg, 'error');
			$.MsgBox.Alert_auto(json.rtMsg);
		}
		
		
	}
	</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	
	<div id="toolbar" class="topbar clearfix" style="">
		<!-- <div class="base_layout_top" style="position:static">
			<table style="width:100%">
			<tr>
				<td>
					<span class="easyui_h1">表单分类管理</span>
				</td>
				<td style="text-align:right">
					<button class="btn btn-success" onclick="add()">添加</button>
					<button class="btn btn-warning" onclick="deleteBatch()">批量删除</button>
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</div> -->
		
		<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/icon_表单分类管理.png">
		<span class="title">表单分类管理 </span>
	    </div>
	    <div class = "right fr clearfix">
			<button class="modal-menu-test btn-win-white fl" onclick="add();$(this).modal();">添加</button>
			<button class="btn-del-red fl" onclick="deleteBatch()">批量删除</button>
	    </div>
	</div>
	
	
	
<!-- Modal -->
<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> -->
<!--   <div class="modal-dialog"> -->
    <div class="modal-test">
      <div class="modal-header">
<!--         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
<!--         <h4 class="modal-title" id="myModalLabel"></h4> -->
            <p class="modal-title" id="myModalLabel">
			           
		        </p>
		   <span class="modal-win-close">×</span>
      </div>
      <div class="modal-body" id="" style="text-indent:10px;">
      	<form method="post" name="form" id="form">
      	分类名称：
        <input type="text" name="sortName" id="sortName" value="" class="easyui-validatebox BigInput" style="height:20px;margin-top:15px;" required="true" maxlength="20"/>
      	<input type="hidden" name="formSortId" id="formSortId" value="0"/>
        </form>
      </div>
      <div class="modal-footer clearfix">
        <button type="button" class="modal-btn-close btn-alert-gray fr" data-dismiss="modal" style="margin-right:10px;">关闭</button>
        <button type="button" class="btn-alert-blue fr" onclick="commit()" style="margin-right:10px;">保存</button>
      </div>
    </div>
<!--   </div> -->
<!-- </div> -->

</body>

</html>