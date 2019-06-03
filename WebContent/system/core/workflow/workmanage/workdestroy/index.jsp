<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%

   //所属流程类型   此处用于判断是否是自定义的菜单
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<style>
	.modal-test{
		/* width: 564px;
		height: 230px; */
		width:auto;
		min-width:564px;
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
		height: 60px;
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

var flowId=<%=flowId %>;

function doInit(){
// 	ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getHandableFlowType2SelectCtrl.action"});
   if(flowId>0){//代表是菜单定义指南进来该页面的
	   $("#flowIdTr").hide();
   }
}

$(function() {
	var para={};
	if(flowId>0){
		para["flowId"]=flowId;
	}
	var url = contextPath+"/workDestroy/query.action";

	var datagrid = $('#datagrid').datagrid({
		url : url,
		toolbar : '#toolbar',
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pageSize : 10,
		checkbox:true,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'runId',
		striped: true,
		remoteSort: false,
		queryParams:para,
		columns : [ [ {
			field : 'runId0',
			checkbox:true
		},
		 {
			title : '流水号',
			field : 'runId',
			width : 30
		},{
			field : 'runName',
			title : '工作名称',
			width : 170,
			formatter:function(data,rowData){
				return "<a href='javascript:void(0)' onclick='detail("+rowData.runId+")'>"+data+"</a>";
			}
		}, {
			field : 'flowName',
			title : '所属流程',
			width : 80
		} ,{
			field : 'beginUserName',
			title : '发起人',
			width : 50
		} ,{
			field : 'beginTimeDesc',
			title : '开始时间',
			width : 100
		},{
			field : 'attaches',
			title : '公共附件',
			width : 100
		},{
			field : 'operations',
			title : '操作',
			width : 50,
			formatter:function(value,rowData,rowIndex){
				var renderStr = [];
				renderStr.push("<a href='javascript:void()' onclick='destroy(\""+rowData.runId+"\")'>销毁</a>");
				
				if(!$("#delFlag").attr("checked")){
					renderStr.push("<a href='javascript:void()' onclick='restore(\""+rowData.runId+"\")'>还原</a>");
				}
				
				return renderStr.join("&nbsp;&nbsp;");
			}
		}
		]]
	});
	
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
	
});

function query(){
	var para =  tools.formToJson($("#form"));
	if(flowId>0){
		para["flowId"]=flowId;
	}
	para["quickSearch"] = $("#quickSearch").val();
	$(".modal-win-close").click();
	modalReset();
	$('#datagrid').datagrid('load', 
		para
    );
	
}

function destroy(runIds){
// 	if(window.confirm("确认要彻底销毁该流程吗？")){
	$.MsgBox.Confirm ("提示", "确认要彻底销毁该流程吗？", function(){
		var url = contextPath+"/workDestroy/destroy.action";
		var json = tools.requestJsonRs(url,{runIds:runIds});
		$.MsgBox.Alert_auto(json.rtMsg);
		$("#datagrid").datagrid("unselectAll");
		query();
	});
}

function restore(runIds){
// 	if(window.confirm("确认要还原该流程吗？")){
	$.MsgBox.Confirm ("提示", "确认要还原该流程吗？", function(){
		var url = contextPath+"/workDestroy/restore.action";
		var json = tools.requestJsonRs(url,{runIds:runIds});
		$.MsgBox.Alert_auto(json.rtMsg);
		query();
	});
}

function deleteBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("请至少选择一项数据");
		return;
	}
	
	var runIds = [];
	for(var i=0;i<selections.length;i++){
		runIds.push(selections[i].runId);
	}
	
	destroy(runIds.join(","));
}

function detail(runId){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1");
}

//选择所属流程
function selectFlowType(){
	bsWindow(contextPath+"/system/core/workflow/flowrun/list/flowTree.jsp","选择流程",{width:"400",height:"220",buttons:
		[
         {name:"选择",classStyle:"btn-alert-blue"} ,
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="选择"){
			if(isNaN(parseInt(h.contents().find("#flowId").val()))){//不是数字
  		    	$.MsgBox.Alert_auto("请选择流程！");
  		        return;
  		    }else{
  		    	//获取弹出页面的流程的名称和流程的id
                 $("#flowName").val(h.contents().find("#flowName").val());
                 $("#flowId").val(h.contents().find("#flowId").val());  	
  		    }
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//清空重置modal
function modalReset(){
	$('#form')[0].reset();
	$("#beginUser").val("0");
}
</script>

</head>
<body onload="doInit()"  style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix" style=""> 
	<!-- <div class="base_layout_top" style="position:static;">
		<table style="width:100%">
			<tr>
				<td>
					<span class="easyui_h1">工作销毁</span>
				</td>
				<td style="text-align:right">
					<input name="quickSearch" id="quickSearch" type="text" class="BigInput" style="height:25px;" placeholder="请输入工作名称/流水号" />
					<input type="button" value="查询" onclick="query()" class="btn-win-white"/>
					<button type="button" class='modal-menu-test btn-win-white' onclick="$('.modal-menu-test').modal();" ><i class="glyphicon glyphicon-search"></i>&nbsp;高级检索</button>
					<input type="button" value="批量删除" onclick="deleteBatch()" class="btn-del-red"/>
				</td>
			</tr>
		</table>
	</div> -->
	
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/icon_工作销毁.png">
		<span class="title">工作销毁 </span>
	</div>
	<div class = "right fr clearfix">
	    <input name="quickSearch" id="quickSearch" type="text" class="BigInput" style="height:25px;" placeholder="请输入工作名称/流水号" />
		<input type="button" value="查询" onclick="query()" class="btn-win-white fl"/>
		<button type="button" class='modal-menu-test btn-win-white fl' onclick="$('.modal-menu-test').modal();" ><i class="glyphicon glyphicon-search"></i>&nbsp;高级检索</button>
		<input type="button" value="批量删除" onclick="deleteBatch()" class="btn-del-red fl"/>
	</div>
</div>
<table id="datagrid"></table>

<!-- Modal -->
<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> -->
  <div class="modal-test">
      <div class="modal-header">
<!--         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
<!--         <h4 class="modal-title" id="myModalLabel">高级查询</h4> -->
           <p class="modal-title">
			            高级查询
		        </p>
		   <span class="modal-win-close">×</span>
      </div>
      <div class="modal-body">
		<form id="form">
       	<table class="TableBlock" style="width:100%">
			<tr id="flowIdTr">
				<td class="TableData TableBG" style="text-indent:15px;">
					所属流程：
				</td>
				<td class="TableData">
				    <input type="text" id="flowName" name="flowName" style="width: 150px;height:20px" onclick="selectFlowType();"/>
					<input type="text" id="flowId" name="flowId" style="display:none;"/>
				</td>
			</tr>
			<tr>
				<td class="TableData TableBG" style="text-indent:15px;">
					发起人：
				</td>
				<td class="TableData">
					<input type="text" id="beginUserDesc" readonly name="beginUserDesc" style="width:70px;height:20px;" class="BigInput readonly"/>
					<input type="hidden" id="beginUser" name="beginUser"/>
					<a href="javascript:void(0)" onclick="selectSingleUser(['beginUser','beginUserDesc'])">选择</a>
					<a href="javascript:void(0)" onclick="clearData('beginUser','beginUserDesc')">清空</a>
				</td>
			</tr>
			<tr>
				<td class="TableData TableBG" style="text-indent:15px;">
					开始时间：
				</td>
				<td class="TableData">
					<input type="text"  name="start" style="width:80px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput" />
					~
					<input type="text"   name="end" style="width:80px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput" />
				</td>
			</tr>
			<tr>
				<td class="TableData TableBG" style="text-indent:15px;">
					流水号：
				</td>
				<td class="TableData">
					<input type="text" name="runId" class="BigInput" style="height:20px;"/>
				</td>
			</tr>
			<tr>
				<td class="TableData TableBG" style="text-indent:15px;">
					流程标题：
				</td>
				<td class="TableData">
					<input type="text" name="runName" class="BigInput" style="height:20px;"/>
				</td>
			</tr>
			<tr style="border-bottom:none;">
				<td class="TableData TableBG" style="text-indent:15px;">
					查询未删除的流程：
				</td>
				<td class="TableData">
					<input type="checkbox" id="delFlag" name="delFlag" value="1"/>
				</td>
			</tr>
		</table>
		</form>
      </div>
      <div class="modal-footer clearfix">
        <button type="button" class="modal-btn-close btn-alert-gray fr" data-dismiss="modal" style="margin-right:10px;">关闭</button>
        <button type="button" class="btn-alert-blue fr" onclick="modalReset()" style="margin-right:10px;">重置</button>
        <button type="button" class="btn-alert-blue fr" onclick="query();" style="margin-right:10px;">查询</button>
      </div>
  </div>
<!-- </div> -->

</body>
</html>