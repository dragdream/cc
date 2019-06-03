<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />

<title>文书模板维护</title>
</head>
<body style="padding-left: 10px;padding-right: 10px;">
<div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
	<span class="title" style="padding-top: 4px;">文书模板维护</span>
	<span style="float:right">
			<input type="button" value="添加模版" class="btn btn-mini btn-primary" onclick="toAdd()" style="margin-right:10px;margin-top:5px">
	</span>
</div>

<div class="easyui-panel case-common-panel-body1" title="模板列表" style="width: 100%; margin-bottom: 10px; height: 490px!important;" id="common_case_add_person_div"
         align="center" data-options="tools:'#common_case_add_person'">
	<table class="TableBlock" id="datagrid"></table>
</div>

 <iframe id="exportIframe" style="display:none"></iframe>
 <script type="text/javascript" charset="UTF-8">
	var title = "";
	 $(function() {
		datagrid = $('#datagrid').datagrid({
			url : '<%=contextPath%>/templateController/getAll.action' ,
			toolbar : '#toolbar',
			title : title,
			singleSelect : true,
			checkbox : true,
			pagination : true,
			fit : true,
			fitColumns : true,
			idField : 'id',
			columns : [ [
				 {
					field : 'id',
					checkbox : true,
					title : 'ID',
					align:'center'
				}, 
				{
      				field:'count',
      				title:'序号',
      				width:40,
      				align:'center',
      				formatter:function(value,rowData,rowIndex){
						return rowIndex+1;
					}
      			},
      		 {
   				field : 'documentNo',
   				title : '编码',
   				width : 170,
   				align:'center'
    		 },
    		 {
   				field : 'documentName',
   				title : '文件类型名称',
   				width : 170,
   				align:'center'
     		 },
     		{
 				field : 'storagePath',
 				title : '模版路径',
 				width : 400,
 				align:'center',
 				formatter:function(value,rowData,rowIndex){
 					if(value == null){
 						return "";
 					}
					return "<span title='"+value+"'>"+value+"</span>";
				}
      		 },
      		{
  				field : 'typesOf',
  				title : '模版类型',
  				width : 170,
  				align:'center'
       		 },
     		{
 				field : 'mbmc',
 				title : '模版名称',
 				width : 170,
 				align:'center', 
 				formatter:function(value,rowData,rowIndex){
 					return "<a href='/templateController/downFile.action?fileurl="+rowData.storagePath+"&&fileName="+rowData.mbmc+"' target='_blank' title='点击下载文件' >"
					+ rowData.mbmc + "</a>"
				}
      		 },
      		{field:'2',title:'操作',align:'center',width:150,formatter:function(e,rowData,index){
				return "<button style='height:25px;width:50px;padding:0;'  class='btn btn-mini btn-danger' href='javascript:void(0);' onclick='del("+index+")'>删除</button>&nbsp;&nbsp;"
				
			}
		  }	
		] ]
	});
}); 

/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}


$(function(){
	getSysCodeByParentCodeNo('FY_CASE_STATUS' , 'caseTypeSelect');
});

//添加
function toAdd() {
	bsWindow("edit.jsp", "模版维护", {
		width : "550",
		height : "300",
			buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,
		submit : function(v, h) {
			var result = h[0].contentWindow;
			if(v == "保存"){
				var state = result.doSave();
				if(state){
					window.location.reload();
					return true;
				}else{
					return false;
				}
			}
			if(v =="关闭"){
				return true;
			}
		}
	});
}
 
 // 删除模版信息
function del(index){
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)){ 
		var id = $('#datagrid').datagrid('getRows')[index].id;
		var para = {'id':id};
		var url = "<%=contextPath%>/templateController/del.action";
		if(id != null){
			tools.requestJsonRs(url,para);
		}else{
			$.jBox.tip("删除失败,请联系管理员", 'success');
		}
		$('#datagrid').datagrid('clearSelections');
		$("#datagrid").datagrid("reload");
	}
}
</script>
</body>

</html>