<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sortId = request.getParameter("sortId");
	String sortName = request.getParameter("sortName");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Tenee办公自动化智能管理平台</title>
	<script>
	var contextPath = "<%=contextPath%>";
	var formId = 0;
	var sortId = "<%=sortId%>";
		$(function(){
			var url = "<%=request.getContextPath() %>/formSort/get.action";
			var json = tools.requestJsonRs(url,{formSortId:sortId});
			var sortName = json.rtData.sortName;
			$("#title1").html("表单分类："+sortName);
			
			$('#datagrid').datagrid({
				url:contextPath+'/flowForm/datagrid.action',
				queryParams:{sortId:sortId},
				pagination:true,
				singleSelect:true,
				toolbar:'#toolbar',//工具条对象
				checkbox:true,
				border:false,
				//idField:'formId',//主键列
				fitColumns:true,//列是否进行自动宽度适应
				columns:[[
					//{field:'id',checkbox:true,title:'ID',width:100},
					{field:'formName',title:'表单名称',width:100,formatter:function(value,rowData,rowIndex){
						var render = "";
						render+="<a href='edit.jsp?formId="+rowData.sid+"' >"+value+"</a>";
						return render;
					}},
					{field:'bundledFlowType',title:'已绑定流程',width:100},
					{field:'_manage',title:'管理',width:100,formatter:function(value,rowData,rowIndex){
						var render = "";
						render+="<a href='javascript:void(0)' onclick='formDesign("+rowData.sid+")'>设计</a>&nbsp;";
						render+="<a href='javascript:void(0)' onclick='formPrintExplore("+rowData.sid+")'>预览</a>&nbsp;";
						render+="<a href='javascript:void(0)' onclick='exportForm("+rowData.sid+")'>导出</a>&nbsp;";
						render+="<a href='javascript:void(0)' onclick='importForm("+rowData.sid+")'>导入</a>&nbsp;";
						render+="<a href='javascript:void(0)' onclick='toHistoryPage("+rowData.sid+")'>历史版本</a>&nbsp;";
						render+="<a href='javascript:void(0)' onclick='deleteForm("+rowData.sid+")'>删除</a>";
						return render;
					}}
				]]
			});
		});
		
		function getLatestVersionFormId(formId){
			var url = contextPath+"/flowForm/getLatestVersionFormId.action";
			var json = tools.requestJsonRs(url,{formId:formId});
			if(json.rtState){
				return json.rtData;
			}
			return 0;
		}
		
		function formPrintExplore(formId){
			var formId = getLatestVersionFormId(formId);
			openFullWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId,"表单预览");
		}

		function deleteForm(formId){
			var json = tools.requestJsonRs(contextPath+"/flowForm/checkExistFlow.action?formId="+formId);
			if(json.rtData){
				alert("该表单已被流程绑定，无法删除。如要删除，请先解除该表单的流程绑定关系。");
				return;
			}
			
			$.jBox.confirm("删除该表单会级联删除所绑定的流程及流程数据<br/>确认是否要删除该表单吗？","确认",function(v){
				if(v=="ok"){
					$.jBox.confirm("删除有风险！确认是否要删除该表单吗？","确认",function(v){
						if(v=="ok"){
							var url = contextPath+"/flowForm/deleteForm.action";
							var json = tools.requestJsonRs(url,{formId:formId});
							if(json.rtState){
								$.jBox.tip(json.rtMsg,"info");
								parent.document.getElementById("left").contentWindow.location.reload();
								window.location.reload();
							}else{
								$.jBox.tip(json.rtMsg,"error");
							}
						}	
					});
				}	
			});
		}
		
		function refresh(){
			$('#datagrid').datagrid("reload");
		}

		function add(){
			window.location.href="new.jsp?sortId="+sortId;
		}

		function formDesign(formId){
			var formId = getLatestVersionFormId(formId);
			openFullWindow(contextPath+"/system/core/workflow/formdesign/index.jsp?formId="+formId,"表单设计");
		}

		function exportForm(formId){
			var formId = getLatestVersionFormId(formId);
			$("#frame0").attr("src",contextPath+"/flowForm/export.action?formId="+formId);
		}

		function importForm(formId){
			$("#uploadForm").attr("action",contextPath+"/flowForm/importForm.action?formId="+formId);
			$('#uploadDiv').modal('show');
		}

		
		function uploadSuccess(){
			alert("导入表单成功！");
			window.location.reload();
		}
		
		function doImport(){
			if(document.getElementById("file").value.indexOf(".html")==-1){
				alert("仅能上传html后缀名模板文件！");
				return false;
			}
			return true;
		}

		function toHistoryPage(formId){
			window.location = "versions.jsp?formId="+formId;
		}
	</script>
</head>
<body style="margin:0px;padding:0px">
	<table id="datagrid" fit="true"></table>
	
	<!-- 声明工具条 -->
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1" id="title1"></span>
				</td>
				<td align=right>
					<button class="btn btn-success" onclick="add()">新建表单</button>
					<button class="btn btn-default" onclick="refresh()">刷新</button>
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<br/>
	</div>
	
	<!-- 下载专用 -->
<iframe id="frame0" style="display:none"></iframe>

<form id="uploadForm" onsubmit="return doImport()" target="frame" action="" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal fade" id="uploadDiv">
 <div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
       <h4 class="modal-title">表单导入</h4>
     </div>
     <div class="modal-body">
       <!-- 导入专用 -->
       <span style="color:red">请上传HTML格式的文件</span><br/><br/>
		<input type="file" name="file" id="file"/>
	<iframe id="frame" name="frame" style="display:none" src="" ></iframe>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="submit" class="btn btn-primary" id="uploadBtn" >上传</button>
      </div>
  </div><!-- /.modal-content -->
</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</body>

</html>