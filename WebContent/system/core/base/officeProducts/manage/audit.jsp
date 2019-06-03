<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var showFlag=1;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/officeStockBillController/datagridAdmin.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:{showFlag:1},
		nowrap:false,
		columns:[[
			//{field:'id',checkbox:true,title:'ID',width:100},
			{field:'productCode',title:'用品名称/编号',width:100,formatter:function(value,rowData,rowIndex){
				return rowData.productName+"&nbsp;/&nbsp;"+rowData.productCode;
			}},
			{field:'productName',title:'用品类别/用品库',width:100,formatter:function(value,rowData,rowIndex){
				return rowData.categoryName+"&nbsp;/&nbsp;"+rowData.depositoryName;
			}},
			{field:'regTypeDesc',title:'申请类型',width:100},
			{field:'regCount',title:'申请数量/库存量',width:100,formatter:function(value,rowData,rowIndex){
				return rowData.regCount+"&nbsp;/&nbsp;"+rowData.stockCount;
			}},
			{field:'regUserName',title:'登记用户',width:100},
			{field:'regTimeDesc',title:'登记时间',width:100},
			{field:'operatorName',title:'调度人员',width:100},
			{field:'_oper',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				if(showFlag==1){
					return "<a href='javascript:void(0)' onclick='doPass("+rowData.sid+")'>审批通过</a>&nbsp;/&nbsp;<a href='javascript:void(0)' onclick='doNoPass("+rowData.sid+")'>不通过</a>";
				}
				return "---";
			}},
			{field:'_row1',title:'调度开始时间/完成时间',width:100,formatter:function(value,rowData,rowIndex){
				return rowData.operTimeDesc+"&nbsp;/&nbsp;"+rowData.finishTimeDesc;
			}},
			{field:'remark',title:'备注',width:100}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}

function doInvalid(){
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
// 		top.$.jBox.tip("请选择一项","info");
        $.MsgBox.Alert_auto("请选择一项");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.confirm("是否作废此登记单？","提示",function(v){
// 		if(v=="ok"){
			var url = contextPath+"/officeStockBillController/doInvalidAdmin.action";
			var json = tools.requestJsonRs(url,{billIds:sid});
			if(json.rtState){
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
				$.MsgBox.Alert_auto(json.rtMsg);
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
// 		}
		return true;
	});
	
}

function query(showFlag){
	window.showFlag = showFlag;
	$('#datagrid').datagrid('load',{showFlag:showFlag});
// 	$("#myModal").modal('hide');
    $("#myModal").hide();
}

function reg(){
	var url = contextPath+"/system/core/base/officeProducts/public/reg_product.jsp";
	var title = "个人用品登记";
	bsWindow(url,title,{width:"500",height:"300",buttons:
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

function doPass(billId){
	//根据billId渲染调度人员
	var url = contextPath+"/officeStockBillController/getOperatorsByBillId.action";
	var json = tools.requestJsonRs(url,{billId:billId});
	var operatorSelect = $("#operatorSelect");
	var html = "";
	for(var i=0;i<json.rtData.length;i++){
		var item = json.rtData[i];
		html+="<option value='"+item.uuid+"'>"+item.userName+"</option>";
	}
	operatorSelect.html(html);
	var _html = $("#passHtml").html();
	bsWindow(_html,'选择调度人员',{width:"500",height:"50",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		if(v == "确定"){
			if(operatorSelect.val()=="" ||operatorSelect.val()=="0"){
				$.MsgBox.Alert_auto("请选调度人员");
				return false;
			}
			var url = contextPath+"/officeStockBillController/doAudit.action";
			var json = tools.requestJsonRs(url,{billId:billId,auditType:1,operatorId:operatorSelect.val()});
			if(json.rtState){
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
				$.MsgBox.Alert_auto(json.rtMsg);
				return true;
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
				return false;
			}
		}else if(v=="关闭"){
			return true;
		}
	}},"html");
	
	
/* 	$.jBox('id:passHtml',{title:'选择调度人员',submit:function(v,h,f){
		if(operatorSelect.val()=="" ||operatorSelect.val()=="0"){
			$.MsgBox.Alert_auto("请选调度人员");
			return false;
		}
		var url = contextPath+"/officeStockBillController/doAudit.action";
		var json = tools.requestJsonRs(url,{billId:billId,auditType:1,operatorId:operatorSelect.val()});
		if(json.rtState){
			$('#datagrid').datagrid('unselectAll');
			$('#datagrid').datagrid('reload');
			$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
			return false;
		}
	}}); */
}

function doNoPass(billId){
	$("#remark").attr("value","");
	var _html = $("#noPassHtml").html();
	bsWindow(_html,'填写原因',{width:"500",height:"120",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		if(v == "确定"){
			var url = contextPath+"/officeStockBillController/doAudit.action";
			var json = tools.requestJsonRs(url,{billId:billId,auditType:0,remark:$("#remark").val()});
			if(json.rtState){
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
				$.MsgBox.Alert_auto(json.rtMsg);
				return true;
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
				return false;
			}
		}else if(v=="关闭"){
			return true;
		}
	}},"html");
	/* $.jBox('id:noPassHtml',{title:'填写原因',submit:function(v,h,f){
		var url = contextPath+"/officeStockBillController/doAudit.action";
		var json = tools.requestJsonRs(url,{billId:billId,auditType:0,remark:$("#remark").val()});
		if(json.rtState){
			$('#datagrid').datagrid('unselectAll');
			$('#datagrid').datagrid('reload');
			$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
			return false;
		}
	}}); */
}

</script>
<style>
	.tab li{
		float: left;
	    margin-right: 20px;
	    line-height: 25px;
	    cursor: pointer;
	}
</style>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
	    <div class="clearfix">
					    <img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/ypgl/icon_用品审核.png">
			            <p class="title">办公用品审核</p>
<!-- 						<span class="easyui_h1"><i class="glyphicon glyphicon-user"></i>&nbsp;办公用品审核</span> -->
						<!-- <div class="btn-group" data-toggle="buttons">
						  <label class="btn btn-default active" onclick="query(1)">
						    <input type="radio" name="options" id="option1">待审批列表
						  </label>
						  <label class="btn btn-default" onclick="query(2)">
						    <input type="radio" name="options" id="option2">审批通过列表
						  </label>
						  <label class="btn btn-default" onclick="query(3)">
						    <input type="radio" name="options" id="option3">审批未通过列表
						  </label>
						  <label class="btn btn-default" onclick="query(4)">
						    <input type="radio" name="options" id="option4">调度中列表
						  </label>
						  <label class="btn btn-default" onclick="query(5)">
						    <input type="radio" name="options" id="option4">作废列表
						  </label>
						</div> -->
				<ul id = 'tab' class = 'tab clearfix'>
				<li id="option1" class='select' onclick='query(1)'>待审批列表</li>
				<li id="option2" onclick='query(2)'>审批通过列表</li>
				<li id="option3" onclick='query(3)'>审批未通过列表</li>
				<li id="option4" onclick='query(4)'>调度中列表</li>
				<li id="option5" onclick='query(5)'>作废列表</li>
		     </ul>
			 <span class="basic_border_grey fl"></span>
					
			</div>
		<div style="display:none" class="setHeight">
			<button class="btn btn-primary" onclick="$('#myModal').modal('show');"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
	</div>
	
	<div id="passHtml" style="display:none;">
		<div style="padding:15px;">
			<select class="BigSelect" style="width:100%" id="operatorSelect">
			</select>
		</div>
	</div>
	
	<div id="noPassHtml" style="display:none;">
		<div style="padding:15px;">
			<textarea id="remark" style="width:100%;height:100px" class="BigTextarea"></textarea>
		</div>
	</div>
	



<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">高级查询</h4>
      </div>
      <div class="modal-body">
      	<div style="" id="searchDiv">
			<table class="SearchTable" style="text-align:left;">
				<tr>
					<td class="">用品名称：</td>
					<td>
						<input class="BigInput" style="width:90px"/>
					</td>
					<td class="">登记开始时间：</td>
					<td>
						<input class="BigInput Wdate"  style="width:90px"/>~<input class="BigInput Wdate"  style="width:90px"/>
					</td>
				</tr>
			</table>
			<table class="SearchTable" style="text-align:left;">
				<tr>
					<td class="" >登记人：</td>
					<td>
						<input class="BigInput" style="width:90px"/>
					</td>
					<td class="">调度开始时间：</td>
					<td>
						<input class="BigInput Wdate"  style="width:90px"/>~<input class="BigInput Wdate"  style="width:90px"/>
					</td>
				</tr>
				<tr>
					<td class="" >调度员：</td>
					<td>
						<input class="BigInput"  style="width:90px"/>
					</td>
					<td class="">库单类型：</td>
					<td>
						<input class="BigInput"  style="width:90px"/>
					</td>
				</tr>
			</table>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="query(window.showFlag);">确定</button>
      </div>
    </div>
  </div>
</div>
<script>
	$(".tab li").click(function(){
		$('.tab li').removeClass('select');
		$(this).addClass('select');
	});
</script>
</body>
</html>