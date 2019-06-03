<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/docs.min.css"/>
<style>
	.bs-callout-info{
	border-left-color:#30b5ff;
	}
	.bs-callout{
	padding: 0 20px;
	border-left-width:3px;
	}
	.bs-callout.bs-callout-info h4{
		line-height:42px;
		font-weight:bold;
		font-size:15px;
		border-bottom:1px solid #f1f1f1;
		color:#000;
	}
	.bs-callout.bs-callout-info .noContent{
		line-height:30px;
		color: #999999;
		
	}
	.bs-callout.bs-callout-info .tips{
		line-height:30px;
		color: #999999;
	}
	.bs-callout.bs-callout-info .btns{
		margin-bottom:10px;
	}



	.modal-test{
		width: 564px;
		height: 230px;
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
		height: 120px;
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
		float: left;
		width: 300px;
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


<script type="text/javascript">

var list;
var globalFlowId;
function doInit(){
	$("#body").html("<center>正在加载数据……</center>");
	tools.requestJsonRs(contextPath+"/docFlowPriv/listCreatableFlow.action",{},true,function(json){
		var render = [];
		list = json.rtData;
		for(var i=0;i<list.length;i++){
			$("#body").html("");
			render.push("<div class=\"bs-callout bs-callout-info\">");
			render.push("<h4>"+list[i].flowName+"</h4>");
			if(list[i].comment==""){
				render.push("<p class='noContent'>暂无说明</p>");
			}else{
				render.push("<p class='tips'>说明："+list[i].comment+"</p>");
			}2
			render.push("<p class='btns'>");
			render.push("<button class='btn-win-white modal-menu-test' onclick='$(this).modal();createWork("+list[i].sid+")'>新&nbsp;&nbsp;建</button>");
			render.push("&nbsp;&nbsp;&nbsp;&nbsp;");
			render.push("<button class='btn-win-white' onclick='viewForm("+list[i].formId+")'>查看表单</button>");
			render.push("&nbsp;&nbsp;&nbsp;&nbsp;");
			render.push("<button class='btn-win-white' onclick='viewGraph("+list[i].sid+")'>查看流程</button>");
			render.push("</p>");
			render.push("</div>");
		}
		if(list.length!=0){
			$("#body").append(render.join(""));
		}else{
			$("#body").html("");
			messageMsg("暂无可发起的公文","body","info");
		}
		
	});
}

function viewForm(formId){
	bsWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId,"查看表单",{width:"800",height:"320",buttons:
		[{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
			 if(v=="关闭"){
					return true;
				}
		}});
	window.event.cancelBubble = true;
}

function viewGraph(flowId){
	bsWindow(contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+flowId,"查看流程图",{width:"800",height:"320",buttons:
		[{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
			 if(v=="关闭"){
					return true;
				}
		}});
	window.event.cancelBubble = true;
}

function createWork(flowId){
	//$('#myModal').modal('show');
	globalFlowId = flowId;
}

function ok(){
	var url = contextPath+"/flowRun/createNewWork.action";
	var json = tools.requestJsonRs(url,{fType:globalFlowId,runName:$("#runName").val(),validation:0});
	if(json.rtState){
		window.openFullWindow(contextPath+"/system/core/workflow/flowrun/prcs/index.jsp?runId="+json.rtData.runId+"&frpSid="+json.rtData.frpSid+"&flowId="+globalFlowId+"&isNew=1","流程办理");
		//隐藏模态框
		$(".modal-win-close").click();
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
   <div class="topbar clearfix" style="position:static">
        <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gwgl/gwqc/icon_gongwenqicao.png">
		<span class="title">公文起草</span>
   </div>
   <div id="body"  style="padding:10px">
   </div>

 <!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			公文起草
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">
			   <span>公文标题(为空则为默认名称)：</span>
				<input type='text' id='runName' name='runName' class='BigInput' style='width:90%' placeholder=''/>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="ok();" value = '确定'/>
	</div>
</div>



 <!-- Modal -->
<!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">公文起草</h4>
      </div>
      <div class="modal-body">
        	公文标题(为空则为默认名称)：<input type='text' id='runName' name='runName' class='BigInput' style='width:90%' placeholder=''/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="ok()">确定</button>
      </div>
    </div>
  </div>
</div> -->
</body>
</html>