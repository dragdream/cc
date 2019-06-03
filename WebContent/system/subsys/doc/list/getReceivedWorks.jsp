<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%@ include file="/header/upload.jsp" %>
<% 
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>办理中工作</title>
	
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
		query();
	});

	function doPageHandler(){
		query();
	}
	
	function doHandler(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
		window.openFullWindow(contextPath +'/system/core/workflow/flowrun/prcs/index.jsp?'+para,"流程办理");
	}

	function doDelete(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		
		 $.MsgBox.Confirm ("提示", "确认删除此工作吗？", function(){
			 var url = contextPath+"/workflowManage/delRun.action";
				var json = tools.requestJsonRs(url,{runId:runId});
				if(json.rtState){
					$.MsgBox.Alert_auto("删除成功！");
					query();
				}   
		  });
	}

	function doDelegate(rowIndex){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		var url = contextPath+"/system/core/workflow/flowrun/prcs/delegate.jsp?frpSid="+frpSid;
		bsWindow(url,"委托",{width:"500",height:"200",buttons:
			[ {name:"保存",classStyle:"btn-alert-blue"},
			  {name:"关闭",classStyle:"btn-alert-gray"}
			 ],submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				if(cw.commit()){
					$.MsgBox.Alert_auto("委托成功！");
					doPageHandler();
					return true;
				}
			}
			if(v=="关闭"){
				return true;
			}
		}});
	}

	function doExport(rowIndex,obj){
		var rows = $('#datagrid').datagrid('getRows');
		var frpSid = rows[rowIndex].frpSid;
		var runId = rows[rowIndex].runId;
		var flowId = rows[rowIndex].flowId;
		$("#frame0").attr("src",contextPath+"/flowRun/exportFlowRun.action?runId="+runId+"&view=1&frpSid="+frpSid);
		/* $(obj).removeAttr("href").html("<span title='刷新可重新下载' style='color:green'>导出中请等待</span>")[0].onclick=function(){return false;}; */
	}

	function query(){
		var para =  tools.formToJson($("#form")) ;
		var opts = [ {field:'FLOW_ID',
			title:'流水号',
			ext:'@流水号',
			sortable:true,
			width:50,
			formatter:function(a,data,c){
				return data.runId;
			}
		},{field:'FLOW_NAME',
					title:'公文类型',
					ext:'@所属流程',
					sortable:true,
					width:50,
					formatter:function(a,data,c){
						return "<a href='javascript:void(0)' onclick='detailFlow("+data.flowId+")'>"+data.flowName+"</a>";
					}
				},
				{field:'RUN_NAME',
					title:'公文标题',
					ext:'@工作名称',
					sortable:true,
					width:200,
					formatter:function(a,data,c){
						var render = "";
						if(data.level==1 || data.level==0){
							render = "<span style='color:green'>【普通】</span>";
						}else if(data.level==2){
							render = "<span style='color:orange'>【紧急】</span>";
						}else if(data.level==3){
							render = "<span style='color:red'>【加急】</span>";
						}
						return render+"<a href='javascript:void(0)' onclick=\"doHandler('"+c+"')\" style='"+(data.timeoutFlag=="1"?"color:red":"")+"'>"+data.runName+"</a>";
					}
				},
				{field:'BEGIN_PERSON',
					title:'发起人',
					ext:'@发起人',
					width:50,
					formatter:function(a,data,c){
						return ""+data.beginPerson+"";
					}
				},
				{field:'PRCS_DESC',
					title:'办理步骤',
					ext:'@步骤与流程图',
					width:80,
					formatter:function(a,data,c){
						return "<a href='javascript:void(0)' onclick='detailRunFlow("+data.runId+","+data.flowId+")'>"+data.prcsDesc+"</a>";
					}
				},
				{field:'fr.END_TIME',
					title:'状态',
					ext:'@状态',
					width:50,
					sortable:true,
					formatter:function(a,data,c){
						if(data.endFlag==null){
							return "<span style='color:green'>进行中</span>";
						}
						return "<span style='color:red'>已结束</span>";
					}
				},{field:'_manage',
					title:'操作',
					ext:'@操作',
					width:100,
					formatter:function(value,rowData,rowIndex){
						var render = "";
						if(rowData.prcsHandle){
							render+="<a href='javascript:void(0)' onclick=\"doHandler('"+rowIndex+"')\" ><b>会签</b></a>";
						}
						if(rowData.opHandle){
							render+="&nbsp;<a href='javascript:void(0)' onclick=\"doHandler('"+rowIndex+"')\" ><b>主办</b></a>";
						}
						if(rowData.delegate){
							render+="&nbsp;<a href='javascript:void(0)' onclick=\"doDelegate('"+rowIndex+"')\" >委托</a>";
						}
						if(rowData.doExport){
							render+="&nbsp;<a href='javascript:void(0)' onclick=\"doExport('"+rowIndex+"',this)\" >导出</a>";
						}
						if(rowData.doDelete){
							render+="&nbsp;<a href='javascript:void(0)' onclick=\"doDelete('"+rowIndex+"')\" >删除</a>";
						}
						
						return render;
					}
				}];

		datagrid = $('#datagrid').datagrid({
			url:'<%=contextPath%>/doc/getReceivedWorks.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			toolbar : '#toolbar',
			queryParams:para,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: true,
			singleSelect:true,
			columns:[opts],
			pagination:true,
			onLoadSuccess:function(){
				$(".attach").each(function(i,obj){
					var att = {priv:1+2,fileName:obj.getAttribute("fileName"),ext:obj.getAttribute("ext"),sid:obj.getAttribute("sid")};
					var attach = tools.getAttachElement(att,{});
					$(obj).append(attach);
				});
			}
		});
	}
	
	function detailRun(runId,frpSid){
		var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1&frpSid="+frpSid;
		openFullWindow(url,"工作详情");
	}
	
	function detailFlow(flowId){
		var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+flowId;
		openFullWindow(url,"所属流程");
	}
	
	function detailRunFlow(runId,flowId){
		var url = contextPath+"/system/core/workflow/flowrun/flowview/index.jsp?runId="+runId+"&flowId="+flowId;
		openFullWindow(url,"步骤与流程图");
	}
	
	</script>
</head>
<body fit="true">

<table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix">
   <div class="left fl setHeight">
      <form id="form">
		<table class="SearchTable">
			<tr>
				<td class="SearchTableTitle">公文标题：</td>
				<td><input type="text" name="runName"  class="BigInput" style="width:150px;height: 23px"/></td>
				<td>&nbsp;&nbsp;<button type="button"  onclick="query()" class="btn-win-white">查询</button></td>
			</tr>
		</table>
   </form>
   </div>
</div>
   <iframe id="frame0" style="display:none"></iframe>
</body>
</html>