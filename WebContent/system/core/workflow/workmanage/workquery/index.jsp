<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
   //所属流程  用来区分菜单定义指南
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
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
</style>
<script>
var  flowTypeId=<%=flowId %>;

function doInit(){
	if(flowTypeId>0){
		$("#flowIdTd1").hide();
		$("#flowIdTd2").hide();
		
		//给所属流程赋值
		$("#flowId").val(flowTypeId);
		
		seniorQueryParams = [{"id":"fr.RUN_NAME","name":"工作名称","type":"1"}
	    ,{"id":"fr.RUN_ID","name":"流水号","type":"2"}
	    	,{"id":"fr.BEGIN_TIME","name":"流程开始时间","type":"3"}
	    	,{"id":"fr.END_TIME","name":"流程结束时间","type":"3"}];
  		
         
         	//获取当前流程表单字段
       		var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:flowTypeId});
       		var list = json.rtData;
       		//将表单字段加入到初始opts中
       		for(var i=0;i<list.length;i++){
       			if(list[i].xtype=="xfeedback"){
       				seniorQueryParams.push({"id":list[i].itemId,"name":list[i].title,"type":"100"});
       			}else if(list[i].xtype!="xmobileseal" 
       					&& list[i].xtype!="xvoice"
       					&& list[i].xtype!="ximg"
       					&& list[i].xtype!="xupload"
       					&& list[i].xtype!="xseal"
       					&& list[i].xtype!="xh5hw"){
       				seniorQueryParams.push({"id":"frd."+list[i].name,"name":list[i].title,"type":"1"});
       			}
       		}
          
          $("#SQL_").val("");
		
		
	}
    query();
}

var seniorQueryParams = [{"id":"fr.RUN_NAME","name":"工作名称","type":"1"}
,{"id":"fr.RUN_ID","name":"流水号","type":"2"}
	,{"id":"fr.BEGIN_TIME","name":"流程开始时间","type":"3"}
	,{"id":"fr.END_TIME","name":"流程结束时间","type":"3"}];
	
function seniorQuery(){
	openSeniorSearch(seniorQueryParams);
}

//高级查询回调函数
function advancedSearchSQL(sql){
	$("#SQL_").val(sql);
	query();
}

function query(){
	var params = tools.formToJson($("#form"));
	if(flowTypeId>0){
		params["flowId"]=flowTypeId;
	}
	var opts = [
	 {
		title : '流水号',
		field : 'RUN_ID',
		ext:'@流水号',
		sortable:true,
		width:50,
		formatter:function(value,rowData,rowIndex){
		var render = rowData.runId;
		return render;
	 	}
	},{
		field : 'RUN_NAME',
		title : '工作名称',
		ext:'@工作名称',
		width:200,
		sortable : true,
		formatter:function(value,rowData,rowIndex){
			var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+rowData.runId+"&view=1";
			return "<a href='javascript:void(0)' onclick=\"openFullWindow('"+url+"','工作详情')\">"+rowData.runName+"</a>";
		}
	}, {
		field : 'FLOW_NAME',
		title : '所属流程',
		ext:'@所属流程',
		width:150,
		sortable : true,
		formatter:function(value,rowData,rowIndex){
			var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+rowData.flowId;
			var render = "<a href='javascript:void(0)' onclick=\"openFullWindow('"+url+"','流程图')\">"+rowData.flowName+"</a>";
			return render;
		}
	} ,{
		field : 'BEGIN_TIME',
		title : '开始时间',
		ext:'@时间',
		width:150,
		sortable : true,
		formatter:function(value,rowData,rowIndex){
			var render = rowData.beginTimeDesc;
			return render;
		}
	},{
		field : 'PRCS_USER',
		title : '流程发起人',
		ext:'@发起人',
		width:100,
		formatter:function(value,rowData,rowIndex){
			var render = rowData.prcsUser;
			return render;
		}
	},{
		field : 'END_TIME',
		title : '状态',
		ext:'@状态',
		width:100,
		sortable : true,
		formatter:function(value,rowData,rowIndex){
			var render = "";
			if(rowData.endTimeDesc!=""){
				render = "<span style=\"color:red\">已结束</span>";
			}else{
				render = "<span style=\"color:green\">执行中</span>";
			}
			return render;
		}
	}, {
		title : '当前步骤',
		field : 'CURRENT_STEP',
		ext:'@步骤与流程图',
		width:200,
		/* sortable:true, */
		formatter:function(value,rowData,rowIndex){
		var render = rowData.CURRENT_STEP;
		return render;
	 }
	},{
		field : 'operations',
		title : '操作',
		ext:'@操作',
		width:200,
		formatter:function(value,rowData,rowIndex){
			var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+rowData.runId+"&view=1";
			var render = "<a href='javascript:void(0)' onclick=\"openFullWindow('"+url+"','工作详情')\">详情</a>&nbsp;&nbsp;";
			
			var flowviewUrl = contextPath+'/system/core/workflow/flowrun/flowview/index.jsp?runId='+rowData.runId+'&flowId='+rowData.flowId;
			if(rowData.viewGraph){//流程图
				render+='<a href=\'javascript:void(0)\' onclick=\'openFullWindow("'+flowviewUrl+'","流程步骤")\'>流程图</a>&nbsp;&nbsp;';
			}
			if(rowData.doUrge){//催办
				render+='<a href=\'javascript:void(0)\' onclick=\'doUrge("'+rowData.runId+'")\'>催办</a>&nbsp;&nbsp;';
			}
			if(rowData.concern){//关注
				render+='<a href=\'javascript:void(0)\' onclick=\'concern("'+rowData.runId+'")\'>关注</a>&nbsp;&nbsp;';
			}else if(rowData.cancelConcern){
				render+='<a href=\'javascript:void(0)\' onclick=\'cancelConcern("'+rowData.runId+'")\'>取消关注</a>&nbsp;&nbsp;';
			}
			if(rowData.doEdit){//编辑
				render+='<a href=\'javascript:void(0)\' onclick=\'edit('+rowData.runId+','+rowData.flowId+')\' target=\'_blank\'>编辑</a>&nbsp;&nbsp;';
			}
			if(rowData.end){//结束
				render+='<a href=\'javascript:void(0)\' onclick=\'endRun("'+rowData.runId+'")\'>结束</a>&nbsp;&nbsp;';
			}
			
			if(rowData.recover){
				render+='<a href=\'javascript:void(0)\' onclick=\'recoverRun("'+rowData.runId+'")\'>恢复执行</a>&nbsp;&nbsp;';
			}
			if(rowData.doDelete){//删除
				render+='<a href=\'javascript:void(0)\' onclick=\'delRun("'+rowData.runId+'")\'>删除</a>&nbsp;&nbsp;';
			}
			return render;
		}
	}];
	
	var columns = [{
		field : 'runId',
		checkbox : true
	}];

	var flowId = $("#flowId").val();
	//动态拼写列结构
	if(flowId!="0" && flowId!=""){
		var json = tools.requestJsonRs(contextPath+"/flowType/get.action",{flowTypeId:flowId});
		var queryFieldModel = json.rtData.queryFieldModel;
		//alert(queryFieldModel);
		queryFieldModel = queryFieldModel.substring(1,queryFieldModel.length-1);
		var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:flowId});
		var list = json.rtData;
		var sp = queryFieldModel.split(",");
		for(var i=0;i<sp.length;i++){
			if(sp[i].indexOf("@")!=-1){
				for(var j=0;j<opts.length;j++){
					if(opts[j].ext==sp[i]){
						columns.push(opts[j]);
						break;
					}
				}
			}else{
				for(var j=0;j<list.length;j++){
					if(list[j].title==sp[i]){
						columns.push({
							field:list[j].name,
							title:list[j].title,
							xtype:list[j].xtype,
							itemId:list[j].itemId,
							sortable:true,
							formatter:function(value,rowData,rowIndex){
								value=value||"";
								rowData["jsonData"] = value;
								
								if(this.xtype=="xlist"){//列表控件
									return "<a class='modal-menu-test' field="+list[j].name+" href='javascript:void(0)' onclick='$(this).modal(); showDetail("+rowIndex+")'>明细查看</a>";
								}else if(this.xtype=="xfeedback"){//会签控件
									return "<a class='modal-menu-test'  href='javascript:void(0)' onclick='showFeedBackDetail("+this.itemId+","+rowData.runId+")'>查看详情</a>";
								}else{
									return value;
								}
							}
						});
						break;
					}
				}
			}
		}
	}else{
		for(var j=0;j<opts.length;j++){
			columns.push(opts[j]);
		}
	}
	
	var url = contextPath+"/workQuery/query.action?runNameOper=like2";

	var datagrid = $('#datagrid').datagrid({
		url : url,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : false,
		nowrap : true,
		border : false,
		checkbox:true,
		idField : 'runId',
		striped: true,
		remoteSort: true,
		queryParams:params,
		singleSelect:false,
		columns : [columns]
	});
	
	
}
	

	
	


function edit(runId,flowId){
	var url = 'form.jsp';
	openPostWindow(url,{runId:runId,flowId:flowId},"_target");
}

function doUrge(runId){
	  $.MsgBox.Confirm ("提示", "是否进行催办提醒？", function(){
		  var url = contextPath+"/flowRun/flowRunUrge.action";
		  var json = tools.requestJsonRs(url,{runId:runId});
		  $.MsgBox.Alert_auto(json.rtMsg);
		  if(json.rtState){
			datagrid.datagrid("reload");
		  } 
	  });
}

function concern(runId){
	$.MsgBox.Confirm ("提示", "确认要关注此工作吗？", function(){
		var url = contextPath+"/flowRunConcern/concern.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$.MsgBox.Alert_auto("关注成功！");
		$('#datagrid').datagrid('reload');
	  });
}

function cancelConcern(runId){
	$.MsgBox.Confirm ("提示", "确认要取消关注此工作吗？", function(){
		var url = contextPath+"/flowRunConcern/cancelConcern.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$.MsgBox.Alert_auto("已取消关注！");
		$('#datagrid').datagrid('reload');
	  });
}

function endRun(runId){
	$.MsgBox.Confirm ("提示", "确认要结束此工作吗？", function(){
		var url = contextPath+"/workflowManage/endRun.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$.MsgBox.Alert_auto("已结束！");
		$('#datagrid').datagrid('reload');
	  });
}

function recoverRun(runId){
	/* $.MsgBox.Confirm ("提示", "确认要恢复此工作吗？", function(){
		var url = contextPath+"/workflowManage/recoverRun.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$.MsgBox.Alert_auto("已恢复！");
		$('#datagrid').datagrid('reload');
	  }); */
	var url=contextPath+"/system/core/workflow/workmanage/workquery/recover.jsp?runId="+runId;
	bsWindow(url ,"流程恢复",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}}); 
}

function delRun(runId){
	$.MsgBox.Confirm ("提示", "确认要删除此工作吗？", function(){
		var url = contextPath+"/workflowManage/delRun.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$.MsgBox.Alert_auto("删除成功！");
		$('#datagrid').datagrid('reload');
	  });
}

/* function query(){
	var para =  tools.formToJson($("#form")) ;
	$('#datagrid').datagrid('load', 
		para
    );
	
} */

function seniorQueryPage(){
	if(flowTypeId>0){
		window.location=contextPath+"/seniorQuery/toFlowQuery.action?flowId="+flowTypeId+"&isCustom=1";
	}else{
		//跳转页面，springMVC 返回对象
		window.location = contextPath + "/seniorQuery/flowList.action";
	}
	
}

function typeChanged(obj){
	if(obj.value!=6){
		$("#beginUserDiv").hide();
		return;
	}
	$("#beginUserDiv").show();
	$("#beginUser").val("");
	$("#beginUserName").val("");
}

function exportBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("请至少选择一项数据");
		return;
	}
	var runIds = [];
	for(var i=0;i<selections.length;i++){
		runIds.push(selections[i].runId);
	}
	
	$("#iframe0").attr("src",contextPath+"/flowRun/exportFlowRunBatch.action?runIds="+runIds.join(","));
}



//导出excel
function exportExcel(){
	
	
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Confirm ("提示", "是否确认导出所有查询结果？", function(){
			
			//获取查询参数
			var params = tools.formToJson($("#form"));
			params["runIds"]="0";
			if(flowTypeId>0){
				params["flowId"]=flowTypeId;
			}
			$("#iframe0").attr("src",contextPath+"/workQuery/exportExcel.action?params="+encodeURIComponent(tools.jsonObj2String(params)));
		  });
		/* if(confirm("是否确认导出所有查询结果？")){
			//导出所有的查询结果
			params["runIds"]="0";
			$("#iframe0").attr("src",contextPath+"/workQuery/exportExcel.action?params="+tools.jsonObj2String(params));
		}else{
			return;
		} */
	}else{
		var runIds = [];
		for(var i=0;i<selections.length;i++){
			runIds.push(selections[i].runId);
		}
		var params = tools.formToJson($("#form"));
		if(flowTypeId>0){
			params["flowId"]=flowTypeId;
		}
		params["runIds"]=runIds.join(",");
		//导出所勾选的数据
		$("#iframe0").attr("src",contextPath+"/workQuery/exportExcel.action?params="+encodeURIComponent(tools.jsonObj2String(params)));
	}
}



//列表明细查看
function showDetail(rowIndex){
	var rows=$("#datagrid").datagrid("getRows");
	var value=tools.strToJson(rows[rowIndex].jsonData);
	if(value.length==0){
		$(".modal-body").html("<br><div id=\"message\"></div>");
		messageMsg("暂无数据！", "message","info" ); 
		//$('#detail').modal('show');
		return;
	}
	var html="<br><table class='TableBlock_page' style='border:#dddddd 2px solid;width:95%;margin-left:12px' ><tr class='TableHeader' style='background-color:#e8ecf9'>";
		for(var m in value[0]){
			if(m=='bisKey'){
				continue;
			}
			html+="<td style='text-indent:15px'>"+m+"</td>";
		}
 	html+="</tr>";
 	
		for(var i = 0 ;i<value.length;i++){
			html+="<tr class='TableData'>";
			for(var m in value[i]){
				if(m=='bisKey'){
	 				continue;
	 			}
	 			html+="<td style='text-indent:15px'>"+value[i][m]+"</td>";
	 		}
			html+="</tr>";
	} 
	$(".modal-body").html(html);
	//$('#detail').modal('show');
	 
}


//选择所属流程
function selectFlowType(){
	//获取有权限的流程类型
	var url=contextPath+"/workQuery/getHasQueryPrivFlowTypeIds.action";
	var json=tools.requestJsonRs(url);
	var privFlowIds=json.rtData;
	bsWindow(contextPath+"/system/core/workflow/workmanage/workquery/flowTree.jsp?privFlowIds="+privFlowIds,"选择流程",{width:"400",height:"250",buttons:
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
		    	$("#flowId").val(h.contents().find("#flowId").val());
			    $("#flowName").val(h.contents().find("#flowName").val());  	
			    
			    seniorQueryParams = [{"id":"fr.RUN_NAME","name":"工作名称","type":"1"}
			    ,{"id":"fr.RUN_ID","name":"流水号","type":"2"}
			    	,{"id":"fr.BEGIN_TIME","name":"流程开始时间","type":"3"}
			    	,{"id":"fr.END_TIME","name":"流程结束时间","type":"3"}];
          		
                  if($("#flowId").val()!="" && $("#flowId").val()!="0"){
                 	//获取当前流程表单字段
               		var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:$("#flowId").val()});
               		var list = json.rtData;
               		//将表单字段加入到初始opts中
               		for(var i=0;i<list.length;i++){
               			if(list[i].xtype=="xfeedback"){
               				seniorQueryParams.push({"id":list[i].itemId,"name":list[i].title,"type":"100"});
               			}else if(list[i].xtype!="xmobileseal" 
               					&& list[i].xtype!="xvoice"
               					&& list[i].xtype!="ximg"
               					&& list[i].xtype!="xupload"
               					&& list[i].xtype!="xseal"
               					&& list[i].xtype!="xh5hw"){
               				seniorQueryParams.push({"id":"frd."+list[i].name,"name":list[i].title,"type":"1"});
               			}
               		}
                  }
                  $("#SQL_").val("");
                  query();
		    }
		    
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}


//批量刪除
function delBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var runIds = [];
		for(var i=0;i<selections.length;i++){
			if(selections[i].doDelete==1){
				runIds.push(selections[i].runId);
			}else{
				$.MsgBox.Alert_auto("选中的数据中包含不可删除的数据，请重新选择！");
				return;
			}
		}
		
		  $.MsgBox.Confirm ("提示", "是否确认删除选中的工作？", function(){
			  var json = tools.requestJsonRs(contextPath+"/workflowManage/delRunBatch.action",{runIds:runIds.join(",")});
				if(json.rtState){
					$.MsgBox.Alert_auto("删除成功！");
					$('#datagrid').datagrid('reload');
					$('#datagrid').datagrid('unselectAll');
				}   
		  });
	}
}

function archives(){
	window.location = "archives_index.jsp?flowId="+flowTypeId;
}

//选择人员后  调用回掉函数
function showUserTab(itemId,itemName,itemIds,itemNames,obj,optType){
		query();
}

window.timmer = 0;
function doTimmer(){
	if(timmer==1){
		timmer = 0;
		var params = tools.formToJson($("#form"));
		if(flowTypeId>0){
			params["flowId"]=flowTypeId;
		}
		$('#datagrid').datagrid("reload",params);
	}
	setTimeout(doTimmer,1000);
}
doTimmer();




//查看会签控件详情
function showFeedBackDetail(itemId,runId){
	var url=contextPath+"/system/core/workflow/flowrun/list/FbDetail.jsp?itemId="+itemId+"&&runId="+runId;
	bsWindow(url ,"会签数据详情",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}});
}



//批量打印
function printFormBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("请至少选择一项数据");
		return;
	}
	var runIds = [];
	for(var i=0;i<selections.length;i++){
		runIds.push(selections[i].runId);
	}
	var url=contextPath+"/system/core/workflow/workmanage/workquery/printFormBatch.jsp?runIds="+runIds.join(",");
    openFullWindow(url);
}
</script>

</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
   <div id="toolbar" class="titlebar clearfix">
      <div class="fl left">
         <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png">
		 <span class="title">流程查询 </span>	 
      </div>
      <div class="fr right" style="margin-top: 5px">
			 <button type="button" onclick="printFormBatch()" class="btn-win-white">批量打印</button>
			 <button type="button" onclick="exportBatch()" class="btn-win-white">批量导出</button>
			 <button type="button" onclick="exportExcel()" class="btn-win-white">导出EXCEL</button>
			  <button type="button" onclick="seniorQuery()" class="btn-win-white">高级查询</button>
			 <button type="button" onclick="seniorQueryPage()" class="btn-win-white">进阶查询</button>
			 <button type="button" onclick="archives()" class="btn-win-white">归档查询</button>
			 <button type="button" onclick="delBatch()" class="btn-del-red">批量删除</button>
	 </div>
         <span class="basic_border"></span>
         <br/>
      <div class="" style="padding-top: 5px;padding-bottom: 5px">
         <!-- form表单 -->
         <form id="form">
         <table>
         	<tr>
         		<td nowrap id="flowIdTd1">
         			<span>所属流程：</span>
         		</td>
         		<td nowrap id="flowIdTd2">
         			<input type="text" id="flowName" name="flowName" style="width: 150px;height:20px" onclick="selectFlowType();"/>
	                <input type="text" id="flowId" name="flowId" style="display: none;" />
	                <input type="text" id="SQL_" name="SQL_" style="display:none;"/>
         		</td>
         		<td nowrap>
         			<span>查询范围：</span>
         		</td>
         		<td nowrap>
         			<select name="type" onchange="typeChanged(this);query();" style="width:147px;height:20px">
						<option value="0">所有范围</option>
						<option value="1">我发起的</option>
						<option value="2">我经办的</option>
						<option value="3">我管理的</option>
						<option value="4">我关注的</option>
						<option value="5">我查阅的</option>
						<option value="6">指定发起人</option>
					</select>
					<span id="beginUserDiv" style="display:none;margin-left: 10px">
						<input type="hidden" name="beginUser" id="beginUser"/>
						<input type="text" readonly class="BigInput readonly" style="width:100px;height: 20px;" name="beginUserName" id="beginUserName"/>
					   <span class='addSpan'>
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_select.png" onclick="selectSingleUser(['beginUser','beginUserName'],undefined,undefined,undefined,'showUserTab')" value="选择"/>
			               &nbsp;
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_cancel.png" onclick="clearData('beginUser','beginUserName');query();" value="清空"/>
		                </span>
				 </span>
         		</td>
         		<td nowrap>
         			 <span>流程状态：</span>
         		</td>
         		<td nowrap>
         			<select name="status" style="width:145px;height:20px" onchange="query();" >
						<option value="0">所有状态</option>
						<option value="1">执行中</option>
						<option value="2">已结束</option>
					</select>
         		</td>
         	</tr>
         	<tr>
         		<td>
         			<span>流水号：</span>
         		</td>
         		<td>
         			<input onkeyup="window.timmer=1" type="text" id="runId" name="runId" style="width: 150px;height:20px"/>
         		</td>
         		<td>
         			<span>工作名称：</span>
         		</td>
         		<td>
         			<input onkeyup="window.timmer=1" type="text" id="runName" name="runName" style="width: 150px;height:20px"/>
         		</td>
         		<td>
         			
         		</td>
         		<td>
         			
         		</td>
         	</tr>
         </table>
	  	</form>
      </div>
   </div>
   
   
   <table id="datagrid" fit="true" ></table>
   <iframe style="display:none" id="iframe0" name="iframe0"></iframe>
   
 
 
  <!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			明细详情
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<!-- <ul>
			
		</ul> -->
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<!-- <input class = "modal-save btn-alert-blue" type="button" onclick="submitNewFolder();" value = '保存'/> -->
	</div>
</div>  
   
</body>



<!-- <body onload="doInit()">

<div class="modal fade" id="detail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">明细详情</h4>
		      </div>
		      <div class="modal-body" style='text-align:center;height:300px;overflow-y:scroll;'>
		       		
		      </div>
		</div>
	</div>
</div>
</body> -->
</html>