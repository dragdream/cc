<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>表单管理中心</title>
<script>
	var contextPath = "<%=contextPath%>";
	$(function() {

		$('#datagrid')
				.datagrid(
						{
							url : contextPath
									+ '/bisFormController/getBisFormList.action',
							view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
							queryParams : null,
							pagination : true,
							singleSelect : true,
							toolbar : '#toolbar',//工具条对象
							checkbox : true,
							border : false,
							//idField:'formId',//主键列
							fitColumns : true,//列是否进行自动宽度适应
							columns : [ [
									//{field:'id',checkbox:true,title:'ID',width:100},
									{
										field : 'formName',
										title : '表单名称',
										width : 100
									},
									{
										field : 'bisTableName',
										title : '所属表格',
										width : 100
									},
									{
										field : '_manage',
										title : '管理',
										width : 100,
										formatter : function(value, rowData,
												rowIndex) {
											var render = "";
											render += "<a href='javascript:void(0)' onclick='formDesign("
													+ rowData.sid
													+ ")'>设计</a>&nbsp;&nbsp;";
											/* render += "<a href='javascript:void(0)' onclick='formPrintExplore("
													+ rowData.sid
													+ ")'>预览</a>&nbsp;&nbsp;"; */
											render += "<a href='javascript:void(0)' onclick='edit("
													+ rowData.sid
													+ ")'>编辑</a>&nbsp;&nbsp;";
											render += "<a href='javascript:void(0)' onclick='deleteForm("
													+ rowData.sid + ")'>删除</a>";
											return render;
										}
									} ] ]
						});
	});

	//表单设计
	function formDesign(formId){
		openFullWindow(
				contextPath
						+ "/system/subsys/bisengine/bisform/formDesign.jsp?formId="
						+ formId, "表单设计");
	}

/* //表单预览
function formPrintExplore(formId){
    var formId = getLatestVersionFormId(formId);
    openFullWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId,"表单预览");
 } */
	  

	//编辑表单
	function edit(formId) {
		var url = contextPath
				+ "/system/subsys/bisengine/bisform/editBisForm.jsp?formId="
				+ formId;
		bsWindow(url, "编辑表单", {width : "600",height : "200",
			buttons : [ {name : "保存",classStyle : "btn-alert-blue"},
			            {name : "关闭",classStyle : "btn-alert-gray"} ],
			submit : function(v, h) {
				var cw = h[0].contentWindow;
				if (v == "关闭") {
					return true;
				} else if (v == "保存") {
					var json = cw.commit();
					if (json.rtState) {
						$.MsgBox.Alert_auto("编辑成功！",function(){
							$('#datagrid').datagrid("reload");
						});
						return true;
					}else{
						$.MsgBox.Alert_auto("编辑失败！");
					}
				}
			}
		});

	}
//删除表单
function deleteForm(formId) {
	 $.MsgBox.Confirm ("提示", "是否确认删除该表单？", function(){
		var url = contextPath+ "/bisFormController/deleteBisForm.action";
		var json = tools.requestJsonRs(url, {sid : formId});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				$('#datagrid').datagrid("reload");
			});
		} else {
			$.MsgBox.Alert_auto(json.rtMsg);
		}  
	 });
}

//刷新页面
function refresh() {
	$('#datagrid').datagrid("reload");
}

	//创建新的表单
function add() {
	var url = contextPath
			+ "/system/subsys/bisengine/bisform/createBisForm.jsp";
	bsWindow(url, "新建表单", {width : "600",height : "200",
		buttons : [ {name : "保存",classStyle : "btn-alert-blue"},
		            {name : "关闭",classStyle : "btn-alert-gray"} ],
		submit : function(v, h) {
			var cw = h[0].contentWindow;
			if (v == "关闭") {
				return true;
			} else if (v == "保存") {
				var json = cw.commit();
				if (json.rtState) {
					$.MsgBox.Alert_auto("新建成功",function(){
						$('#datagrid').datagrid("reload");
					});
					return true;
				}else{
					$.MsgBox.Alert_auto("新建失败");
				}
			}
		}
	});
}
</script>
</head>
<body style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>

	<!-- 声明工具条 -->
	<div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/bisengine/img/icon_ywbdgl.png">
			<span class="title">业务表单管理</span>
		</div>
		<div class = "right fr clearfix">
			<input type="button" class="btn-win-white fl" onclick="add()" value="新建表单"/>
	        <input type="button" class="btn-win-white fl" onclick="refresh()" value="刷新"/>
	    </div>
   </div>
	

	<!-- 下载专用 -->
	<!-- <iframe id="frame0" style="display: none"></iframe>

	<form id="uploadForm" onsubmit="return doImport()" target="frame"
		action="" name="uploadForm" method="post"
		enctype="multipart/form-data">
		<div class="modal fade" id="uploadDiv">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">表单导入</h4>
					</div>
					<div class="modal-body">
						导入专用
						<span style="color: red">请上传HTML格式的文件</span><br /> <br /> <input
							type="file" name="file" id="file" />
						<iframe id="frame" name="frame" style="display: none" src=""></iframe>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="submit" class="btn btn-primary" id="uploadBtn">上传</button>
					</div>
				</div>
				/.modal-content
			</div>
			/.modal-dialog
		</div>
		/.modal
	</form> -->
</body>

</html>