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
<script>
var showFlag=1;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/officeStockBillController/datagridOperates.action',
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:{showFlag:1},
		columns:[[
			//{field:'id',checkbox:true,title:'ID',width:100},
			{field:'depositoryName',title:'用品库',width:100},
			{field:'categoryName',title:'用品类别',width:100},
			{field:'productName',title:'用品名称',width:100},
			{field:'regTypeDesc',title:'类型',width:100},
			{field:'regCount',title:'数量',width:100},
			{field:'_oper',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				if(rowData.operFlag==0){
					return "<a href='javascript:void(0)' onclick='doOperated("+rowData.sid+")'>开始调度</a>";
				}else if(rowData.operFlag==1){
					return "<span style='color:green'>正在调度中</span>";
				}else{
					return "调度完毕";
				}
			}},
			{field:'regUserName',title:'物品登记员',width:100},
			{field:'status',title:'登记员状态',width:100,formatter:function(value,rowData,rowIndex){
				if(rowData.obtainFlag==1){
					return "已领取";
				}else if(rowData.obtainFlag==0){
					return "未领取";
				}else if(rowData.obtainFlag==3){
					return "归还中";
				}else if(rowData.obtainFlag==4){
					return "已归还";
				}
			}},
			{field:'finishTimeDesc',title:'完成时间',width:100}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}

function doOperated(sid){
	var url = contextPath+"/officeStockBillController/doOperated.action";
	var json = tools.requestJsonRs(url,{billId:sid});
	if(json.rtState){
		$('#datagrid').datagrid('unselectAll');
		$('#datagrid').datagrid('reload');
// 		$.jBox.tip(json.rtMsg,"info");
		$.MsgBox.Alert_auto(json.rtMsg);
	}else{
// 		$.jBox.tip(json.rtMsg,"error");
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

function query(showFlag){
	window.showFlag = showFlag;
	$('#datagrid').datagrid('load',{showFlag:showFlag});
}

function reg(){
	var url = contextPath+"/system/core/base/officeProducts/public/reg_product.jsp";
	bsWindow(url,"个人用品登记",{width:"500",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			$('#datagrid').datagrid('unselectAll');
			$('#datagrid').datagrid('reload');
			return true;
		}
		return false;
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
	
	$.jBox('id:passHtml',{title:'选择调度人员',submit:function(v,h,f){
		if(operatorSelect.val()=="" ||　operatorSelect.val()=="0"){
			$.jBox.tip("请选调度人员","warning");
			return false;
		}
		var url = contextPath+"/officeStockBillController/doAudit.action";
		var json = tools.requestJsonRs(url,{billId:billId,auditType:1,operatorId:operatorSelect.val()});
		if(json.rtState){
			$('#datagrid').datagrid('unselectAll');
			$('#datagrid').datagrid('reload');
			$.jBox.tip(json.rtMsg,"info");
			return true;
		}else{
			$.jBox.tip(json.rtMsg,"error");
			return false;
		}
	}});
}

function doNoPass(billId){
	$("#remark").attr("value","");
	$.jBox('id:noPassHtml',{title:'填写原因',submit:function(v,h,f){
		var url = contextPath+"/officeStockBillController/doAudit.action";
		var json = tools.requestJsonRs(url,{billId:billId,auditType:0,remark:$("#remark").val()});
		if(json.rtState){
			$('#datagrid').datagrid('unselectAll');
			$('#datagrid').datagrid('reload');
			$.jBox.tip(json.rtMsg,"info");
			return true;
		}else{
			$.jBox.tip(json.rtMsg,"error");
			return false;
		}
	}});
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
						<img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/ypgl/icon_调度管理.png">
			            <p class="title">用品调度管理</p>
						<!-- <div class="btn-group" data-toggle="buttons">
						  <label class="btn btn-default active" onclick="query(1)">
						    <input type="radio" name="options" id="option1">领用类列表
						  </label>
						  <label class="btn btn-default" onclick="query(2)">
						    <input type="radio" name="options" id="option2">借用类列表
						  </label>
						</div> -->
						<ul id = 'tab' class = 'tab clearfix'>
				<li id="option1" class='select' onclick='query(1)'>领用类列表</li>
				<li id="option2" onclick='query(2)'>借用类列表</li>
		     </ul>
			 <span class="basic_border_grey fl"></span>
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