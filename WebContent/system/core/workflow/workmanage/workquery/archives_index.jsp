<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
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
var flowTypeId=<%=flowId %>;
function doInit(){
	if(flowTypeId>0){
		$("#flowIdSpan").hide();
	}
	
	var json = tools.requestJsonRs("/flowArchiveController/getArchiveList.action");
	for(var i=0;i<json.rows.length;i++){
		$("#archives").append("<option value='"+json.rows[i].crTimeStr+"'>"+json.rows[i].crTimeStr+"</option>");
	}
	
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
			var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+rowData.runId+"&view=1"+'&thread_local_archives='+$("#archives").val();
			return "<a href='javascript:void(0)' onclick=\"openFullWindow('"+url+"','工作详情')\">"+rowData.runName+"</a>";
		}
	}, {
		field : 'FLOW_NAME',
		title : '所属流程',
		ext:'@所属流程',
		width:150,
		sortable : true,
		formatter:function(value,rowData,rowIndex){
			var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+rowData.flowId+'&thread_local_archives='+$("#archives").val();
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
		field : 'operations',
		title : '操作', 
		ext:'@操作',
		width:200,
		formatter:function(value,rowData,rowIndex){
			var render = '';
			var flowviewUrl = contextPath+'/system/core/workflow/flowrun/flowview/index.jsp?runId='+rowData.runId+'&flowId='+rowData.flowId+'&thread_local_archives='+$("#archives").val();
			if(rowData.viewGraph){//流程图
				render+='<a href=\'javascript:void(0)\' onclick=\'openFullWindow("'+flowviewUrl+'","流程步骤")\'>流程图</a>&nbsp;&nbsp;';
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
		var json = tools.requestJsonRs(contextPath+"/flowType/get.action",{flowTypeId:flowId,thread_local_archives:$("#archives").val()});
		var queryFieldModel = json.rtData.queryFieldModel;
		//alert(queryFieldModel);
		queryFieldModel = queryFieldModel.substring(1,queryFieldModel.length-1);
		var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByFlowType.action",{flowId:flowId,thread_local_archives:$("#archives").val()});
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
							sortable:true,
							field:list[j].name,
							title:list[j].title,
							formatter:function(value,rowData,rowIndex){
								value = value||"";
								if(value.indexOf("[")!=-1){
									return "<a class='modal-menu-test' href='javascript:void(0)' onclick='$(this).modal();showDetail("+value+")'>明细查看</a>";
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
	
	var url = contextPath+"/workQuery/query.action?runNameOper=like2&thread_local_archives="+$("#archives").val();
	if($("#archives").val()==""){
		params["runId"] = -1;
	}

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


function seniorQuery(){
	//跳转页面，springMVC 返回对象
	window.location = contextPath + "/seniorQuery/flowList.action";
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
	//获取查询参数
	var params = tools.formToJson($("#form"));
	
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Confirm ("提示", "是否确认导出所有查询结果？", function(){
			params["runIds"]="0";
			$("#iframe0").attr("src",contextPath+"/workQuery/exportExcel.action?params="+tools.jsonObj2String(params));
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
		params["runIds"]=runIds.join(",");
		//导出所勾选的数据
		$("#iframe0").attr("src",contextPath+"/workQuery/exportExcel.action?params="+tools.jsonObj2String(params));
	}
}



//列表明细查看
function showDetail(value){
	if(value.length==0){
		$(".modal-body").html("没有数据！");
		//$('#detail').modal('show');
		return;
	}
	var html="<table class='TableBlock' width='100%'><tr class='TableHeader'>";
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
	bsWindow(contextPath+"/system/core/workflow/workmanage/workquery/flowTree.jsp","选择流程",{width:"400",height:"250",buttons:
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
		    }
		    
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});

}


//返回
function back(){
	
	window.location=contextPath+"/system/core/workflow/workmanage/workquery/index.jsp?flowId="+flowTypeId;
}
</script>

</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
   <div id="toolbar" class="titlebar clearfix">
      <div class="fl left">
         <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png">
		 <span class="title">归档查询</span>	 
      </div>
      <div class="fr right" style="margin-top: 5px">
			 <button type="button" onclick="back();" class="btn-win-white">返回</button>
			 <button type="button" onclick="query()" class="btn-win-white">查询</button>
<!-- 			 <button type="button" onclick="exportBatch()" class="btn-win-white">批量导出</button> -->
<!-- 			 <button type="button" onclick="exportExcel()" class="btn-win-white">导出EXCEL</button> -->
	 </div>
         <span class="basic_border"></span>
         <br/>
      <div class="" style="padding-top: 5px;padding-bottom: 5px">
         <!-- form表单 -->
         <form id="form">
          <div>
          	<span>归档日期：&nbsp;&nbsp;&nbsp;</span>
          	<select name="archives" id="archives" style="width:90px;height:20px">
				<option></option>
			</select>
            <span>流水号：&nbsp;&nbsp;&nbsp;</span><input type="text" name="runId"  style="width: 50px;height:20px"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <span>流程标题：</span><input type="text" name="runName" style="width: 100px;height:20px"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <span>日期范围：</span><input  type="text"  name="start1" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput" style="width:80px"/>
				        至
		           <input type="text"   name="start2" readonly  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput"  style="width:80px"/>&nbsp;
           </div>
           <div style="margin-top: 5px">
            <span id="flowIdSpan"><span>所属流程：</span><input type="text" id="flowName" name="flowName" style="width: 150px;height:20px" onclick="selectFlowType();"/>
                <input type="text" id="flowId" name="flowId" style="display: none;" />
                 &nbsp;&nbsp;&nbsp;</span>
            <span>查询范围：</span>
                  <select name="type" onchange="typeChanged(this)" style="width:90px;height:20px">
					<option value="0">所有范围</option>
					<option value="1">我发起的</option>
					<option value="2">我经办的</option>
					<option value="3">我管理的</option>
					<option value="4">我关注的</option>
					<option value="5">我查阅的</option>
					<option value="6">指定发起人</option>
				</select>
				<span id="beginUserDiv" style="display:none;margin-left: 19px">
					<input type="hidden" name="beginUser" id="beginUser"/>
					<input type="text" readonly class="BigInput readonly" style="width:100px" name="beginUserName" id="beginUserName"/>
				   <span class='addSpan'>
		               <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_select.png" onclick="selectSingleUser(['beginUser','beginUserName'])" value="选择"/>
		               &nbsp;&nbsp;
		               <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/wenhao/icon_cancel.png" onclick="clearData('beginUser','beginUserName')" value="清空"/>
	                </span>
				 </span>&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
          	
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