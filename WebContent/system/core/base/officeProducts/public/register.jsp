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
<style>
	.modal-test{
		width: 564px;
		height: 500px;
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
		height: 390px;
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
		width: 100px;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		/* float: right; */
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
var showFlag=1;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/officeStockBillController/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:{showFlag:1},
		columns:[[
			//{field:'id',checkbox:true,title:'ID',width:100},
			{field:'productCode',title:'用品编码',width:100},
			{field:'productName',title:'用品名称',width:100},
			{field:'regTypeDesc',title:'申请类型',width:100},
			{field:'regCount',title:'申请/归还数量',width:100},
			{field:'auditorName',title:'审核用户',width:100},
			{field:'operatorName',title:'调度员',width:100},
			{field:'operFlagDesc',title:'调度状态',width:100},
			{field:'_oper',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				if(showFlag==2 || showFlag==4){
					if(rowData.obtainFlag==1){//已领取
						var render = "已领取";
						return render;
					}else if(rowData.obtainFlag==0){//未领取
						if(rowData.operFlag==1){
							return "<a href='javascript:void(0)' onclick='doObtain("+rowData.sid+")'>确认领取</a>";
						}else{
							return "---";
						}
					}else if(rowData.obtainFlag==3){//归还中的话
						if(rowData.operFlag==1){
							return "<a href='javascript:void(0)' onclick='doObtain("+rowData.sid+")'>确认归还</a>";
						}else{
							return "---";
						}
					}else if(rowData.obtainFlag==4){
						return "已归还";
					}else{
						return "---";
					}
				}else{
					return "---";
				}
			}},
			{field:'regTimeDesc',title:'登记时间',width:130},
			{field:'verTimeDesc',title:'审核时间',width:130},
			{field:'_manage1',title:'管理',width:80,formatter:function(value,rowData,rowIndex){
				if(showFlag==1){
					return "<a href='javascript:void(0)' onclick='doInvalid("+rowData.sid+")'>作废</a>";
				}else{
					return "";
				}
			}}
		]]
	});
	
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});

}

function returnObj(sid){
	
}

function doObtain(sid){
	var url = contextPath+"/officeStockBillController/doObtain.action";
	var json = tools.requestJsonRs(url,{billId:sid});
	if(json.rtState){
		$('#datagrid').datagrid('unselectAll');
		$('#datagrid').datagrid('reload');
		$.MsgBox.Alert_auto(json.rtMsg);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}
function doInvalid(sid){
	$.MsgBox.Confirm("提示","是否作废此登记单？",function(){
// 		if(v=="ok"){
			var url = contextPath+"/officeStockBillController/doInvalid.action";
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
	$("#form1")[0].reset();
	window.showFlag = showFlag;
	var param = tools.formToJson($("#form1"));
	param["showFlag"] = showFlag;
	$('#datagrid').datagrid('load',param);
	$("#myModal").hide();
	
}

function doQuery(showFlag){
	window.showFlag = showFlag;
	var param = tools.formToJson($("#form1"));
	param["showFlag"] = showFlag;
	$('#datagrid').datagrid('load',param);
	//$("#myModal").hide();
	$(".modal-win-close").click();
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
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
	    <div class="clearfix">
	        <img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/ypgl/icon_用品登记.png">
			<p class="title">办公用品登记</p>
			<ul id = 'tab' class = 'tab clearfix'>
				<li id="option1" class='select' onclick='query(1)'>待审批列表</li>
				<li id="option2" onclick='query(2)'>审批通过列表</li>
				<li id="option3" onclick='query(3)'>审批未通过列表</li>
				<li id="option4" onclick='query(4)'>调度中列表</li>
				<li id="option5" onclick='query(5)'>作废列表</li>
		     </ul>
			<span class="basic_border_grey fl"></span>
		<!-- <div class="base_layout_top" style="position:static">
			<table width="100%">
				<tr>
					<td>
						<span class="easyui_h1"><i class="glyphicon glyphicon-user"></i>&nbsp;办公用品登记</span>
					</td>
					<td align=right>
						<div class="btn-group" data-toggle="buttons">
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
						</div>
					</td>
					
				</tr>
			</table>
			
			<br/>
		</div> -->
		</div>
		<div class="setHeight clearfix">
			<button class="btn-win-white fr modal-menu-test " onclick="$(this).modal();"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
			<button class="btn-win-white fr" onclick="reg()" style="margin-right:10px;">登记用品</button>
		</div>
		
	</div>



<!-- Modal -->
<form id="form1" name="form1">
<div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			高级查询
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" >
		<ul>
			<li class="clearfix">
				<span>用品名称：</span>
				<input type="text"  id="productName" name="productName" />
			</li>
			<li class="clearfix">
				<span>登记开始时间：</span>
				<input type="text" class="Wdate" style="width:190px"  name="regBeginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> ~ <input type="text" class="Wdate"  style="width:190px" name="regEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</li>
			<li class="clearfix">
				<span>登记人：</span>
				<input type="hidden" class="BigInput" style="width:90px" name="regUserId" id="regUserId"/>
				<input type="text" readonly class="BigInput readonly" style="width:330px" name="regUserDesc" id="regUserDesc"/>
				<a href="javascript:void(0)" onclick="selectSingleUser(['regUserId','regUserDesc'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('regUserId','regUserDesc')">清除</a>
			</li>
			<li class="clearfix">
				<span>调度开始时间：</span>
				<input type="text" class="BigInput Wdate"  style="width:190px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="operBeginTime"/>~<input type="text" class="BigInput Wdate"  style="width:190px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="operEndTime"/>
			</li>
			<li class="clearfix">
				<span>调度员：</span>
				<input type="hidden" class="BigInput" style="width:90px" name="operatorId" id="operatorId"/>
				<input type="text" readonly class="BigInput readonly" style="width:330px" name="operatorDesc" id="operatorDesc"/>
				<a href="javascript:void(0)" onclick="selectSingleUser(['operatorId','operatorDesc'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('operatorId','operatorDesc')">清除</a>
			</li>
			<li class="clearfix">
				<span>库单类型：</span>
				<select name="billType" id="billType" class="BigSelect" style="width:330px;height: 23px">
					<option value="0"></option>
					<option value="1">领用单</option>
					<option value="2">借用单</option>
				</select>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doQuery(window.showFlag);" value = '查询'/>
	</div>
</div>
<form id="form1" name="form1">
	
<!-- Modal -->
<!-- <form id="form1" name="form1">
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">高级查询</h4>
      </div>
      <div class="modal-body">
      	<div style="" id="searchDiv">
			<center>
			<table class="SearchTable" style="text-align:left">
				<tr>
					<td width="80px">用品名称：</td>
					<td>
						<input type="text" class="BigInput" style="width:90px" name="productName"/>
					</td>
					<td width="100px">登记开始时间：</td>
					<td>
						<input type="text" class="Wdate BigInput"  style="width:90px" name="regBeginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>~<input type="text" class="Wdate BigInput"  style="width:90px" name="regEndTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>
					<td >登记人：</td>
					<td>
						<input type="hidden" class="BigInput" style="width:90px" name="regUserId" id="regUserId"/>
						<input type="text" readonly class="BigInput readonly" style="width:90px" name="regUserDesc" id="regUserDesc"/>
						<br/>
						<a href="javascript:void(0)" onclick="selectSingleUser(['regUserId','regUserDesc'])">选择</a>
						&nbsp;
						<a href="javascript:void(0)" onclick="clearData('regUserId','regUserDesc')">清除</a>
					</td>
					<td >调度开始时间：</td>
					<td>
						<input type="text" class="BigInput Wdate"  style="width:90px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="operBeginTime"/>~<input type="text" class="BigInput Wdate"  style="width:90px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="operEndTime"/>
					</td>
				</tr>
				<tr>
					<td >调度员：</td>
					<td>
						<input type="hidden" class="BigInput" style="width:90px" name="operatorId" id="operatorId"/>
						<input type="text" readonly class="BigInput readonly" style="width:90px" name="operatorDesc" id="operatorDesc"/>
						<br/>
						<a href="javascript:void(0)" onclick="selectSingleUser(['operatorId','operatorDesc'])">选择</a>
						&nbsp;
						<a href="javascript:void(0)" onclick="clearData('operatorId','operatorDesc')">清除</a>
					</td>
					<td>库单类型：</td>
					<td>
						<select name="billType" id="billType" class="BigSelect">
							<option value="0"></option>
							<option value="1">领用单</option>
							<option value="2">借用单</option>
						</select>
					</td>
				</tr>
			</table>
			</center>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn-alert-gray" data-dismiss="modal">关闭</button>
        <button type="button" class="btn-alert-blue" onclick="doQuery(window.showFlag);">确定</button>
      </div>
    </div>
  </div>
</div>
</form> -->
<script>
	$(".tab li").click(function(){
		$('.tab li').removeClass('select');
		$(this).addClass('select');
	});
</script>
</body>
</html>