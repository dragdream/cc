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
	String flowId = TeeStringUtil.getString(request.getParameter("flowId"), "");
%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>办理中工作</title>
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
		height: 400px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		/* height: 80px; */
		line-height: 30px;
		margin-top: 15px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		/* float: right; */
		width: 400px;
		height: 23px;
	}
	
	.modal-test .modal-body ul li select{
		display: inline-block;
		/* float: right; */
		width: 200px;
		height: 23px;
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
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		tools.requestJsonRs(contextPath+"/docFlowPriv/listCreatableFlow.action",{},true,function(json){
			var render = [];
			list = json.rtData;
			$("#flowId").html("");
			for(var i=0;i<list.length;i++){
				render.push("<option value='"+list[i].sid+"'>"+list[i].flowName+"</option>");
			}
			if(list.length!=0){
				$("#flowId").append(render.join(""));
			}else{
				$("#flowId").html("");
			}
			query();
		});
	});

	function doPageHandler(){
		query();
	}
	
	function query(){
		var para =  tools.formToJson($("#form")) ;
		var opts = [{field:'d.fwdw',
					title:'发文单位',
					sortable:true,
					width:50,
					formatter:function(a,data,c){
						return data.fwdw;
					}
				},
				{field:'d.recDept',
					title:'收文单位',
					sortable:true,
					width:50,
					formatter:function(a,data,c){
						return data.recDeptName;
					}
				},
				{field:'d.bt',
					title:'公文标题',
					width:200,
					sortable:true,
					formatter:function(a,data,c){
						return "<a href='javascript:void(0)' onclick=\"detail('"+data.uuid+"')\">"+data.bt+"</a>";
					}
				},
				{field:'d.zh',
					title:'字号',
					width:70,
					sortable:true,
					formatter:function(a,data,c){
						return data.zh;
					}
				},
				{field:'d.recUser',
					title:'签收人',
					width:50,
					sortable:true,
					formatter:function(a,data,c){
						return data.recUserName;
					}
				},
				{field:'d.recTime',
					title:'签收时间',
					width:70,
					sortable:true,
					formatter:function(a,data,c){
						return getFormatDateStr(data.recTime,"yyyy-MM-dd HH:mm");
					}
				},
				{field:'d.hasToDoc',
					title:'转公文状态',
					width:70,
					sortable:true,
					formatter:function(a,data,c){
						if(data.hasToDoc!=0){
							return "<span style='color:green'>已转</span>";
						}
						return "<span style='color:red'>未转</span>";
					}
				},{field:'_manage',
					title:'操作',
					ext:'@操作',
					width:60,
					formatter:function(value,rowData,rowIndex){
						var render = "";
						render+="<a href='javascript:void(0)' class=\"modal-menu-test\" onclick=\"$(this).modal();createFlow('"+rowIndex+"')\" >转公文</a>&nbsp;&nbsp;<a href='javascript:void(0)'  onclick=\"view('"+rowData.runId+"',"+rowData.contentPriv+")\">传阅</a>";
						return render;
					}
				}];

		datagrid = $('#datagrid').datagrid({
			url:'<%=contextPath%>/doc/DaiShou.action?type=1&flowId=<%=flowId%>',//已签收
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
				$('#datagrid').datagrid("unselectAll");
			}
		});
	}
	
	function detail(uuid){
		var url = contextPath+"/system/subsys/doc/daishou/print/index.jsp?uuid="+uuid;
		openFullWindow(url,"公文详情");
	}
	
	
	function createFlow(rowIndex){
		//先把所有选项隐藏
		$("#bdLi").hide();
		$("#qpdLi").hide();
		$("#zwLi").hide();
		$("#bswjLi").hide();
		$("#fjLi").hide();
		
		var rows = $('#datagrid').datagrid('getRows');
		var uuid = rows[rowIndex].uuid;
		var runName = rows[rowIndex]["bt"];
		var zh=rows[rowIndex]["zh"];
		var fwdw=rows[rowIndex]["fwdw"];
		$("#runName").val(runName);
		$("#zh").val(zh);
		$("#fwdw").val(fwdw);
		$("#uuid").val(uuid);
		
		
		var  contentPriv=rows[rowIndex]["contentPriv"];
		if((contentPriv&1)==1){//表单
			$("#bdLi").show();
		    $("#formPriv").val(1);
		}else{
			$("#formPriv").val(0);
		}
		
		if((contentPriv&16)==16){//签批单
			$("#qpdLi").show();
		    $("#qpdPriv").val(1);
		}else{
			$("#qpdPriv").val(0);
		}
		
		
		if((contentPriv&2)==2){//正文
			$("#zwLi").show();
			$("#docPriv").val(1);
		}else{
			$("#docPriv").val(0);
		}
		if((contentPriv&4)==4){//版式文件
			$("#bswjLi").show();
			$("#aipPriv").val(1);
		}else{
			$("#aipPriv").val(0);
		}
		if((contentPriv&8)==8){//附件
			$("#fjLi").show();
			$("#attachPriv").val(1);
		}else{
			$("#attachPriv").val(0);
		}
		
	}
	
	function createNewWork(){
		var json = tools.requestJsonRs(contextPath+"/doc/createFlow.action?uuid="+$("#uuid").val(),{flowId:$("#flowId").val(),docPriv:$("#docPriv").val(),attachPriv:$("#attachPriv").val(),formPriv:$("#formPriv").val(),aipPriv:$("#aipPriv").val(),qpdPriv:$("#qpdPriv").val()});
		var initDatas = json.rtData;
		initDatas["runName"] = $("#runName").val();
		var json = tools.requestJsonRs(contextPath+"/flowRun/createNewWork.action",initDatas);
		if(json.rtState){
			window.openFullWindow(contextPath+"/system/core/workflow/flowrun/prcs/index.jsp?runId="+json.rtData.runId+"&frpSid="+json.rtData.frpSid+"&flowId="+initDatas.fType+"&isNew=1","流程办理");
			//隐藏模态框
			$('.modal-win-close').click();
			$('#datagrid').datagrid("reload");
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
	
	
	
	//公文传阅
	function view(runId,contentPriv){
		var url=contextPath+"/system/subsys/doc/daishou/view.jsp?runId="+runId+"&&contentPriv="+contentPriv;
		top.bsWindow(url ,"传阅公文",{width:"600",height:"320",buttons:
			[
             {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
			   var a=cw.doViewDoc();
			   if(a){
				   $.MsgBox.Alert_auto("已成功传阅！");
				   return true;
			   }
			}else if(v=="关闭"){
				return true;
			}
		}});
	}
	</script>
</head>
<body fit="true">

<table id="datagrid" fit="true"></table>
<div id="toolbar" class="clearfix">
   <div class="fl left setHeight">
      <form id="form">
		<table class="SearchTable">
			<tr>
				<td class="SearchTableTitle">公文标题：</td>
				<td><input type="text" name="bt" style="width:150px;height: 23px" class="BigInput"/></td>
				<td class="SearchTableTitle">&nbsp;&nbsp;发文单位：</td>
				<td><input type="text" name="fwdw"  style="width:150px;height: 23px" class="BigInput"/></td>
				<td class="SearchTableTitle">&nbsp;&nbsp;文号：</td>
				<td><input type="text" name="zh"  style="width:150px;height: 23px" class="BigInput"/></td>
				<td>&nbsp;&nbsp;<button type="button"  onclick="query()" class="btn-win-white">查询</button></td>
			</tr>
		</table>
	</form> 
   </div>
	
</div>

 <!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			转公文
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
	   <ul>
	      <li>
	          <span style="width: 90px">公文标题：</span>
	          <input  type="text"  class="BigInput" id="runName" />
	      </li> 
	      <li>
	          <span style="width: 90px">公文文号：</span>
	          <input  type="text"  class="BigInput" id="zh"  readonly="readonly"/>
	      </li> 
	      <li>
	          <span style="width: 90px">发/来文单位：</span>
	          <input  type="text"  class="BigInput" id="fwdw"  readonly="readonly"/>
	      </li>
	      <li>
	          <span style="width: 90px">公文类型：</span>
	          <select class="BigSelect" id="flowId">
        	  </select>
	      </li>
	      <li id="bdLi" style="display: none" >
	          <span style="width: 90px">表单签收：</span>
	          <select class="BigSelect" id="formPriv" name="formPriv">
	            <option value="0">拒收</option>
        		<option value="1">签收</option>
        	  </select>
	      </li>
	      <li id="qpdLi" style="display: none" >
	          <span style="width: 90px">签批单签收：</span>
	          <select class="BigSelect" id="qpdPriv" name="qpdPriv">
	            <option value="0">拒收</option>
        		<option value="1">签收</option>
        	  </select>
	      </li>
	      <li id="zwLi" style="display: none" >
	          <span style="width: 90px">正文签收：</span>
	          <select class="BigSelect" id="docPriv" name="docPriv">
	            <option value="0">拒收</option>
        		<option value="1">签收作为正文</option>
        		<option value="2">签收作为附件</option>
        	  </select>
	      </li>
	      <li id="fjLi" style="display: none" >
	          <span style="width: 90px">附件签收：</span>
	          <select class="BigSelect" id="attachPriv" name="attachPriv">
	            <option value="0">拒收</option>
        		<option value="1">签收</option>
        		
        	</select>	
        	<input type="hidden" id="uuid" />
	      </li>
	      <li id="bswjLi" style="display: none" >
	          <span style="width: 90px">版式文件签收：</span>
	          <select class="BigSelect" id="aipPriv" name="aipPriv">	
        		<option value="0">拒收</option>
        		<option value="1">签收作为版式文件</option>
        		<option value="2">签收作为附件</option>
        	  </select>
	      </li>
	      
	       
	   </ul> 
        	
			
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="createNewWork();" value = '确定'/>
	</div>
</div>




<!-- Modal -->
<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">转公文</h4>
      </div>
      <div class="modal-body">
        	公文类型：
        	<select class="BigSelect" id="flowId">
        		
        	</select>
        	<br/><br/>
        	公文标题：
        	<input  class="BigInput" id="runName" />
        	<br/><br/>
			公文签收：
			<select class="BigSelect" id="docPriv">
        		<option value="1">签收作为正文</option>
        		<option value="2">签收作为附件</option>
        	</select>
        	<br/><br/> 
        	附件签收：
			<select class="BigSelect" id="attachPriv">
        		<option value="1">签收</option>
        		<option value="0">拒收</option>
        	</select>	
        	<input type="hidden" id="uuid" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="createNewWork()">确定</button>
      </div>
    </div>
  </div>
</div> -->
</body>
</html>