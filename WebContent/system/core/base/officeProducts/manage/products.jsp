<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/style.css"/> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style>
.datagrid-header-check input
{
    vertical-align:top;
}
</style>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/officeProductController/datagrid.action',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		nowrap:false,
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'proCode',title:'用品编码',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href='#' onclick=loadDetail(\""+rowData.sid+"\",\""+rowData.proCode+"\") >"+rowData.proCode+"</a>";
			}},
			{field:'proName',title:'用品名称',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href='#' onclick=loadDetail(\""+rowData.sid+"\",\""+rowData.proCode+"\") >"+rowData.proName+"</a>";
			}},
			{field:'depositoryName',title:'用品库',width:100},
			{field:'categoryName',title:'用品类别',width:100},
			{field:'norms',title:'规格',width:100},
			{field:'curStock',title:'当前库存',width:100},
			{field:'minStock',title:'最低警戒库存',width:100,formatter:function(value,rowData,rowIndex){
				if(rowData.curStock<=rowData.minStock){
					return "<span style='color:red;font-weight:bold;'>"+value+"&nbsp;(最低库存警报)</span>";
				}
				return value;
			}},
			{field:'maxStock',title:'最高警戒库存',width:100,formatter:function(value,rowData,rowIndex){
				if(rowData.curStock>=rowData.maxStock){
					return "<span style='color:red;font-weight:bold;'>"+value+"&nbsp;(最高库存警报)</span>";
				}
				return value;
			}},
			{field:'auditorsNames',title:'审批权限(用户)',width:100},
			{field:'regUsersNames',title:'登记权限(用户)',width:100},
			{field:'regDeptsName',title:'登记权限(部门)',width:100}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}

function add(){
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/add_product.jsp","添加用品信息",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	var url = contextPath+"/system/core/base/officeProducts/manage/add_product.jsp";
	var title = "添加用品信息";
	bsWindow(url,title,{width:"800",height:"400",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function edit(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
// 		top.$.jBox.tip("至少选择一条记录！","info");
        $.MsgBox.Alert_auto("至少选择一条记录！");
		return;
	}else{
		if(selections.length > 1){
// 			top.$.jBox.tip("请选择一条记录进行编辑！","info");
            $.MsgBox.Alert_auto("请选择一条记录进行编辑！");
			return;
		}
	}
	var sid = selections[0].sid;
	var url = contextPath+"/system/core/base/officeProducts/manage/edit_product.jsp?sid="+sid;
	var title = "修改用品信息";
	bsWindow(url,title,{width:"800",height:"400",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/edit_product.jsp?sid="+sid,"修改用品信息",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
// 		top.$.jBox.tip("少选择一条记录！","info");
        $.MsgBox.Alert_auto("至少选择一条记录！");
		return;
	}
	var ids = "";
	for(var i=0;i<selections.length;i++){
		var sid = selections[i].sid;
		ids = ids + sid + ",";
	}
	
	$.MsgBox.Confirm("提示","是否要删除选中的用品信息？",function(){
// 		if(v=="ok"){
			var url = contextPath+"/officeProductController/delProducts.action";
			var json = tools.requestJsonRs(url,{sids:ids});
			if(json.rtState){
// 				top.$.jBox.tip(json.rtMsg,"info");
                $.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
				return true;
			}
// 			top.$.jBox.tip(json.rtMsg,"error");
			$.MsgBox.Alert_auto(json.rtMsg);
			return false;
// 		}
	});
}

function reg(){
	/* bsWindow(contextPath+"/system/core/base/officeProducts/manage/reg_product.jsp","库存登记",{width:"500",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	var url = contextPath+"/system/core/base/officeProducts/manage/reg_product.jsp";
	var title = "库存登记";
	bsWindow(url,title,{width:"500",height:"199",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
}


function exportProducts(){
	var url = contextPath+"/TeeCrmCustomerInfoController/exportCustomerInfo.action";
	document.form1.action=url;
	document.form1.submit();
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
	return true;
}


/**
 *查看详情
 */
 function loadDetail(seqId,proCode){
     var url = contextPath + "/system/core/base/officeProducts/manage/detail.jsp?sid="+seqId+"&proCode="+proCode;
     bsWindow(url,"用品详情",{width:"900", height:"400", buttons:[{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
         if(v=="关闭"){
				return true;
         }
     }});
  }
</script>

</head>
<body onload="doInit()" style="padding:0 10px;">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="" style="height:40px;">
		<div class="base_layout_top" style="position:static;margin-top:8px;">
<!-- 			<i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;用品信息管理 -->
		    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/ypgl/icon_用品信息管理.png">
			<p class="title">用品信息管理</p>
		</div>
<!-- 		<span class="basic_border fl"></span> -->
		<div style="padding:10px;margin-top:-18px;">
		    <button class="btn-win-white fr" onclick="reg()" style="margin-top: -12px;margin-right:20px;">库存登记</button>
			<button class="btn-del-red fr" onclick="del()"  style="margin-right:10px;margin-top: -12px;">删除</button>
			<button class="btn-win-white fr" onclick="edit()"  style="margin-right:10px;margin-top: -12px;">修改用品</button>
			<button class="btn-win-white fr" onclick="add()"  style="margin-right:10px;margin-top: -12px;">添加用品</button>
			<!-- <button class="btn btn-primary" onclick="exportProducts()">导出</button> -->
			
			
		</div>
	</div>
	<form id="form1" name="form1">
	</form>
</body>
</html>