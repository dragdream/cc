<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<% 
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
	int flowSortId = TeeStringUtil.getInteger(request.getParameter("flowSortId"), 0);//流程分类类型sid
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>关注的工作</title>
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
	.datagrid-header-check input{
	    vertical-align:top;
	}
</style>
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var flowId = <%=flowId%>;
	$(function() {
		if(flowId!=0){
			$("#flowId").attr("value",flowId);
			$("#flowIdSpan1").hide();
			$("#flowIdSpan2").hide();
		}
<%-- 		ZTreeTool.comboCtrl($("#flowId"),{url:contextPath+"/workQuery/getHandableFlowType2SelectCtrl.action?flowSortId=<%=flowSortId%>"}); --%>
		query();
	});

	function doPageHandler(){
		query();
	}
	
	function doExport(rowIndex,obj){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		$("#frame0").attr("src",contextPath+"/flowRun/exportFlowRun.action?runId="+runId+"&view=1&frpSid="+frpSid);
// 		$(obj).removeAttr("href").html("<span title='刷新可重新下载' style='color:green'>导出中请等待</span>")[0].onclick=function(){return false;};
	}
	
	function cancelConcern(runId){
// 		if(window.confirm("确认要取消关注此工作吗？")){
		$.MsgBox.Confirm ("提示", "确认要取消关注此工作吗？", function(){
			var url = contextPath+"/flowRunConcern/cancelConcern.action";
			var json = tools.requestJsonRs(url,{runId:runId});
			$.MsgBox.Alert_auto("取消成功！");
			$('#datagrid').datagrid('reload');
		});
	}

	function query(){
		var para =  tools.formToJson($("#form")) ;

		var flowId = $("#flowId").val();

		datagrid = $('#datagrid').datagrid({
			url:'<%=contextPath%>/workflow/getViewsWorks.action?flowSortId=<%=flowSortId%>',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar : '#toolbar',
			queryParams:para,
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'runId',
			sortOrder: 'desc',
			striped: true,
			//remoteSort: true,
			singleSelect:false,
			columns:[[{
				field : 'runId',
				checkbox : true
			},{title:'流水号',
				field:'fr.runId',
				width:60,
// 				sortable:true,
				formatter:function(a,data,c){
					return data.runId;
				}
			},
			{field:'fr.runName',
				title:'工作名称',
				width:300,
// 				sortable:true,
				formatter:function(a,data,c){
					return "<a title='"+data.runName+"' href='javascript:void(0)' onclick='detailRun("+data.runId+")' >"+data.runName+"</a>";
				}
			},
			{field:'viewFlag',
				title:'查阅状态',
				width:70,
// 				sortable:true,
				formatter:function(a,data,c){
					if(a==1){
						return "<span style='color:green'>已查阅</span>";
					}else{
						return "<span style='color:red'>未查阅</span>";
					}
				}
			},
			{field:'fr.flowType.sid',
				title:'所属流程',
				width:150,
// 				sortable:true,
				formatter:function(a,data,c){
					return "<a title='"+data.flowName+"' href='javascript:void(0)' onclick='detailFlow("+data.flowId+")'>"+data.flowName+"</a>";
				}
			},
			{field:'fr.beginPerson.uuid',
				title:'发起人',
				width:100,
// 				sortable:true,
				formatter:function(a,data,c){
					return data.beginPerson;
				}
			},
			{field:'fr.beginTime',
				title:'开始时间',
				width:150,
// 				sortable:true,
				formatter:function(a,data,c){
					return data.beginTime.slice(0,-3);
				}
			},
			{field:'etime',
				title:'流程状态',
				width:60,
// 				sortable:true,
				formatter:function(a,data,c){
					if(data.endFlag==null){
						return "<span style='color:green'>进行中</span>";
					}
					return "<span style='color:red'>已结束</span>";
				}
			},
			{field:'attaches',
				title:'公共附件',
				ext:'@公共附件',
				width:180,
				formatter:function(a,data,c){
					var render = [];
					for(var i=0;i<data.attaches.length;i++){
						render.push("<p class='attach' fileName='"+data.attaches[i].fileName+"' ext='"+data.attaches[i].ext+"' sid='"+data.attaches[i].sid+"'></p>");
					}
					return render.join("");
				}
			},
			{field:'doc',
				title:'正文',
				ext:'@正文',
				width:150,
				formatter:function(a,data,c){
					if(data.doc!=null){
						return "<p class='attach' fileName='"+data.doc.fileName+"' ext='"+data.doc.ext+"' sid='"+data.doc.sid+"'></p>";
					}
					return "";
				}
			},{field:'_manage',
				title:'操作',
				width:80,
				formatter:function(value,rowData,rowIndex){
					var render = "";
// 					render+='<a href=\'javascript:void(0)\' onclick=\'cancelConcern("'+rowData.runId+'")\'>取消关注</a>';
					if(rowData.doExport){
						render+="&nbsp;<a href='javascript:void(0)' onclick=\"doExport('"+rowIndex+"',this)\" >导出</a>";
					}
					return render;
				}
			}]],
			pagination:true,
			onLoadSuccess:function(){
				$(".attach").each(function(i,obj){
					var att = {priv:1+2,fileName:obj.getAttribute("fileName"),ext:obj.getAttribute("ext"),sid:obj.getAttribute("sid")};
					var attach = tools.getAttachElement(att,{});
					$(obj).append(attach);
				});
			}
		});
		$(".datagrid-header-row td div span").each(function(i,th){
			var val = $(th).text();
			 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
		});
	}
	
	function detailRun(runId){
		var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1";
		openFullWindow(url,"工作详情");
	}
	
	function detailFlow(flowId){
		var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+flowId;
		openFullWindow(url,"所属流程");
	}
	
	
	//导出
	function exportExcel(){
		//获取查询参数
		var params = tools.formToJson($("#form"));
		params["flowSortId"]=<%=flowSortId%>;
		params["type"]="3";//用来区分
		var selections = $("#datagrid").datagrid("getSelections");
		if(selections.length==0){
// 			if(confirm("是否确认导出所有查询结果？")){
	        $.MsgBox.Confirm ("提示", "是否确认导出所有查询结果？", function(){
				//导出所有的查询结果
				params["runIds"]="0";
				$("#frame0").attr("src",contextPath+"/workflow/exportExcel.action?params="+tools.jsonObj2String(params));
			});
// 				return;
// 			}
		}else{
			var runIds = [];
			for(var i=0;i<selections.length;i++){
				runIds.push(selections[i].runId);
			}
			params["runIds"]=runIds.join(",");
			//导出所勾选的数据
			$("#frame0").attr("src",contextPath+"/workflow/exportExcel.action?params="+tools.jsonObj2String(params));
		}
	}
	//选择所属流程
	function selectFlowType(){
		//获取当前登陆人待办流程的所有的flowType
		var url=contextPath+"/workflow/getPrivFlowTypeIds.action?type=3";
		var json=tools.requestJsonRs(url,null);
		var privFlowIds=json.rtData;
		bsWindow(contextPath+"/system/core/workflow/flowrun/list/flowTree.jsp?privFlowIds="+privFlowIds,"选择流程",{width:"400",height:"220",buttons:
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
	</script>
</head>
<body fit="true">

<table id="datagrid" fit="true"></table>
<div id="toolbar">
<form id="form" style="padding:10px">
		<table class="none_table" style="width:100%">
			<tr>
				<td id="flowIdSpan1" class="TableData TableBG">所属流程：</td>
				<td id="flowIdSpan2" class="TableData">
				<input type="text" id="flowName" name="flowName" style="width: 150px;height:20px" onclick="selectFlowType();"/>
				<input type="text" id="flowId" name="flowId" class="BigInput" style="display:none;"/></td>
				<td class="TableData TableBG" style="width:50px;">流水号：</td>
				<td class="TableData" style="width:190px;"><input type="text" name="runId" class="BigInput" style="height:20px;"/></td>
				<td class="TableData TableBG" style="width:70px;">流程名称：</td>
				<td class="TableData">
					<input type="text" name="runName"  class="BigInput" style="height:20px;"/>
					&nbsp;&nbsp;
					<button type="button"  onclick="query()" class="btn-win-white fr">查询</button>
					<button type="button"  onclick="exportExcel()" class="btn-win-white fr" style="margin-right:10px;">导出</button>
					<!-- <button type="button"  onclick="window.location = '/system/core/workflow/flowrun/createNewWork.jsp';" class="btn-win-white fr" style="margin-right:10px;">新建流程</button> -->
				</td>
			</tr>
		</table>
</form>
</div>
<iframe id="frame0" style="display:none"></iframe>
</body>
</html>