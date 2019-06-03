<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

   //所属流程  用来判断是不是菜单定义指南
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>工作监控</title>

<script type="text/javascript" charset="UTF-8">
    var flowId=<%=flowId %>;
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	
	function doOnload(){
		if(flowId>0){
			$("#flowTd1").hide();
			$("#flowTd2").hide();
		}
		
		//getMonitorFlowTypeTree();
	}
	$(function() {
		var para =  tools.formToJson($("#form1")) ;
		if(flowId>0){
			para["flowId"]=flowId;
		}
		
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/flowPrivManage/getMonitorFlowList.action',
			toolbar : '#toolbar',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			title : title,
			iconCls:'',
			pagination : true,
			pageSize : 10,
		//	pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			//idField : 'sid',
			singleSelect:false,
			queryParams:para,
			columns:[[
                {field:'sid',checkbox:true},
				{title:'流水号',field:'runId',width:50},
				{field:'runName',title:'工作名称',width:220,
					formatter:function(a,data,c){
						var url = contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+data.runId+"&view=1&frpSid="+data.frpSid;
						return "<a href='#' onclick=\"openFullWindow('"+url+"','工作详情')\">"+data.runName+"</a>";
					}
				},
				{field:'flowName',title:'所属流程',width:150,
					formatter:function(value, rowData, rowIndex){
						var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+rowData.flowId;
						return "<a href=\"#\" onclick=\"openFullWindow('"+url+"','流程图')\">"+rowData.flowName+"</a>";
					}
				},
				{field:'beginUserName',title:'流程发起人',width:100},
				{field:'prcsUserName',title:'当前办理人',width:100},
				{field:'prcsName',title:'当前步骤',width:160,
					formatter:function(a,data,c){
						var url = contextPath+"/system/core/workflow/flowrun/flowview/index.jsp?runId="+data.runId+"&flowId="+data.flowId;
						return "<a href='#' onclick=\"openFullWindow('"+url+"','流程步骤')\">"+data.prcsName+"</a>";
					}
				},
				{field:'prcsTimeDesc',title:'办理时间',width:190},
				{field:'flowMonitorPrivType',title:'监控权限',width:150,hidden:true},
				{field:'col4',title:'操作',width:150,
					formatter : function(value, rowData, rowIndex) {
						var params = rowData.params;
						var render = "";
						if(params.turnNext==""){
							//render+="<a href=\"javascript:void(0)\" onclick=\"turnNext("+rowIndex+");window.event.cancelBubble = true;\">转交</a>&nbsp;";
							if(rowData.typeFlag==1){//固定流程
								render+="<a href=\"javascript:void(0)\" onclick=\"intervention("+rowIndex+");window.event.cancelBubble = true;\">干预</a>&nbsp;";
							}
							
						}
						if(params.delegate==""){
							render+="<a href=\"javascript:void(0)\" onclick=\"delegate("+rowIndex+");window.event.cancelBubble = true;\">委托</a>&nbsp;";
						}
						if(params.edit==""){
							render+="<a href=\"javascript:void(0)\" onclick=\"edit("+rowData.runId+","+rowData.flowId+")\">编辑</a>";
						}
						if(params.end==""){
							render+="<a href=\"javascript:void(0)\" onclick=\"end("+rowIndex+");window.event.cancelBubble = true;\">结束</a>&nbsp;";
						}
						if(params.del==""){
							render+="<a href=\"javascript:void(0)\" onclick=\"del("+rowIndex+");window.event.cancelBubble = true;\">删除</a>&nbsp;";
						}
						return render;
					}
				}
					
			] ],onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
	            if (data.rows.length > 0) {
	                //循环判断操作为新增的不能选择
	                for (var i = 0; i < data.rows.length; i++) {
	                    //根据operate让某些行不可选
	                    if (data.rows[i].params.del != "") {
	                        $("input[type='checkbox']")[i + 1].disabled = true;
	                        $("input[type='checkbox']")[i + 1].style.display = "none";
	                    }
	                }
	            }
	        },
	        onClickRow: function(rowIndex, rowData){
	            $("input[type='checkbox']").each(function(index, el){
	                if (el.disabled == true) {
	                	datagrid.datagrid('unselectRow', index - 1);
	                }
	            });
	        },
	        onSelectAll:function(rowIndex, rowData){
	        	  $("input[type='checkbox']").each(function(index, el){
	                  if (el.disabled == true) {
	                  	datagrid.datagrid('unselectRow', index - 1);
	                  	el.checked=false;
	                  }
	              });
	        }
		});
	}); 
	
	
	
	//选择所属流程
	function selectFlowType(){
		//获取有权限的流程类型
		var url=contextPath+"/flowPrivManage/getHasMonitorPrivFlowTypeIds.action";
		var json=tools.requestJsonRs(url);
		var privFlowIds=json.rtData;
		
		bsWindow(contextPath+"/system/core/workflow/workmanage/flowPriv/flowTree.jsp?privFlowIds="+privFlowIds,"选择流程",{width:"400",height:"250",buttons:
			[{name:"选择",classStyle:"btn-alert-blue"} ,
		 	 {name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h,f,d){
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
//流程干预
function intervention(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	var currFrpSid = rows[rowIndex].frpSid;
	var runId = rows[rowIndex].runId;
	var flowId = rows[rowIndex].flowId;
	
	var  url=contextPath+"/system/core/workflow/workmanage/flowPriv/intervention.jsp?currFrpSid="+currFrpSid+"&runId="+runId+"&flowId="+flowId;
	bsWindow(url ,"流程干预",{width:"600",height:"200",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("干预成功！");
				datagrid.datagrid('reload');
				return true;
		    }else{
		    	$.MsgBox.Alert_auto("干预失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}


function turnNext(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	var frpSid = rows[rowIndex].frpSid;
	var runId = rows[rowIndex].runId;
	var flowId = rows[rowIndex].flowId;
	var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;

	var url = contextPath+"/flowRun/preTurnHandlerData.action";
	var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
	var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId});
	if(json.rtState){
		var opCode = json.rtData.opCode;
		if(opCode=="1"){
			var url = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFixedNext.jsp?"+para;
			bsWindow(url,"流程转交",{width:"600", height:"300",
				buttons:[{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],
				submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					if(cw.wfCluster.commit()){
						doPageHandler();
						return true;
					}
				}
				if(v=="关闭"){
					return true;
				}
			}});
		}else if(opCode=="2"){
			var url = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFreeNext.jsp?"+para;
			bsWindow(url,"流程转交",{width:"600", height:"300",
				buttons:[{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],
				submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					if(cw.wfCluster.commit()){
						doPageHandler();
						return true;
					}
				}
				if(v=="关闭"){
					return true;
				}
			}});
		}else{
			CloseWindow();
		}
	}
}

function doPageHandler(){
	$('#datagrid').datagrid('unselectAll');
	$('#datagrid').datagrid('reload');
}

function delegate(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	var frpSid = rows[rowIndex].frpSid;
	var runId = rows[rowIndex].runId;
	var flowId = rows[rowIndex].flowId;
	var url = contextPath+"/system/core/workflow/flowrun/prcs/delegate.jsp?frpSid="+frpSid;
	bsWindow(url,"委托",{width:"600", height:"300",
		buttons:[{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],
		submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			if(cw.commit()){
				parent.$.MsgBox.Alert_auto("委托成功！");
				doPageHandler();
				return true;
			}
		}
		if(v=="关闭"){
			return true;
		}
	}});
}

function end(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	var frpSid = rows[rowIndex].frpSid;
	var runId = rows[rowIndex].runId;
	var flowId = rows[rowIndex].flowId;
	$.MsgBox.Confirm("提示", "确认要结束此工作吗？",function(){
		var url = contextPath+"/workflowManage/endRun.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$('#datagrid').datagrid('reload');
	});
}

function del(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	var frpSid = rows[rowIndex].frpSid;
	var runId = rows[rowIndex].runId;
	var flowId = rows[rowIndex].flowId;
	$.MsgBox.Confirm ("提示", "确认要删除此工作吗？",function(){
		var url = contextPath+"/workflowManage/delRun.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		$('#datagrid').datagrid('reload');
	});
}

function edit(runId,flowId){
	var url = contextPath+'/system/core/workflow/workmanage/workquery/form.jsp?runId='+runId+'&flowId='+flowId;
	openFullWindow(url,"编辑流程数据");
}


/**
 * 条件查询
 */
function query(){
	if (checkForm()){
	var para =  tools.formToJson($("#form1")) ;
	if(flowId>0){
		para["flowId"]=flowId;
	}
	datagrid.datagrid('load', para);
	}
}

function checkForm(){
	  return $("#form1").valid(); 

}
/**
 * 获取有监控权限的流程树
 */
function getMonitorFlowTypeTree(){
	var url =  contextPath+"/workQuery/getFlowType2SelectCtrl.action";
	ZTreeTool.comboCtrl($("#flowTypeId"),{url:url});
}


//批量删除
function delBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var runIds = [];
		for(var i=0;i<selections.length;i++){
			runIds.push(selections[i].runId);
			
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
</script>
</head>
<body onload="doOnload();" style="overflow:hidden;font-size:12px;font-family:MicroSoft YaHei; padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzjk/icon_gzjk.png">&nbsp;&nbsp;
		<span class="title">工作监控</span>
	</div>
	<div class="fr" style="position:static;">
		<input type="button" class="btn-del-red" value="批量删除" onclick="delBatch()" />
	</div>
	<span class="basic_border" style="padding-top: 10px;"></span>
	<form id="form1" name="form1" style="padding:5px">
		<table style="width:90%">
			<tr style="height: 30px;">
				<td class="TableData" >
					流水号：
				</td>
				<td class="TableData" >
					<input style="height: 25px;width: 200px;font-family: MicroSoft YaHei;" type="text" id="runId" name="runId"  maxlength=11  positive_integer="true"  value=''>
				</td>
				<td class="TableData" >
					流程标题：
				</td>
				<td class="TableData" >
					<input style="height: 25px;width: 200px;font-family: MicroSoft YaHei;" type="text" id="runName" name="runName" class=" BigInput">
				</td>
			</tr>
			<tr style="height: 30px;">
				<td class="TableData" id="flowTd1">
					流程选择：
				</td>
				<td class="TableData" id="flowTd2">
					<!-- <input style="height: 25px;width: 25px;" id="flowTypeId" name="flowTypeId"  type="hidden" value="0" class="BigInput"/> -->
				    
				    <input readonly style="width: 200px;height:25px;font-family: MicroSoft YaHei;" type="text" id="flowName" name="flowName" onclick="selectFlowType();" />
		            <input type="text" id="flowId" name="flowId" style="display: none;" />
				
				
				</td>
				<td class="TableData">
					人员条件：
				</td>
				<td class="TableData" >
					<select style="width: 200px;height: 25px;font-family: MicroSoft YaHei;" name="userType" id="userType" class="BigSelect">
 						 <option value="1">当前主办人</option>
 						 <option value="2">流程发起人</option>
  					</select>
  					<input type="hidden" name="personId" id="personId" value=""> 
  					<input style="width: 200px;height: 25px;font-family: MicroSoft YaHei;" type="text" name="personName" id="personName" value=""  size="8" class="BigInput" readonly>&nbsp;&nbsp; 
					     <span class='addSpan'>
			                 <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzjk/add.png" onClick="selectSingleUser(['personId', 'personName'],'','')" value="选择"/>
			                &nbsp;
			                 <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzjk/clear.png" onClick="clearData('personId', 'personName')" value="清空"/>
	                     </span>
					
					<input style="width: 45px;height: 25px;margin-left: 10px;" type="button" value="查询" class="btn-win-white" onclick="query()">
				</td>
			</tr>
		</table>
</form>
</div>
<table id="datagrid"></table>
</body>
</html>