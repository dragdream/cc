<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.general.model.TeePortletModel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<style>
	.modal-test{
		width: 450px;
		height: 150px;
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
		height: 90px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 400px;
		height: 25px;
		line-height: 5px;
		margin-top: 10px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li p input{
		display: inline-block;
		margin-left:10px;
		width: 280px;
		height: 23px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 50px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:10px;
		float: right;
		margin-right:20px;
	}
</style>
<script type="text/javascript">

/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "<%=contextPath%>";
var imgPath = "<%=imgPath%>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";

var cssPathSecond = "<%=cssPathSecond%>";
var imgPathSecond = "<%=imgPathSecond%>";
var loginOutText = "<%=loginOutText%>";
var uploadFlashUrl = "<%=contextPath%>/common/swfupload/swfupload.swf";
var commonUploadUrl = "<%=contextPath%>/attachmentController/commonUpload.action";
var systemImagePath = "<%=systemImagePath%>";
		var xparent;
		if (window.dialogArguments) {
			xparent = window.dialogArguments;
		} else if (window.opener) {
			xparent = opener;
		} else {
			xparent = window;
		}
		function parseNumber(value, defValue) {
			if (isNaN(value)) {
				return defValue;
			}
			return value * 1;
		}
	</script>
	<style>
body {
	scrollbar-arrow-color: #777777; /*图6,三角箭头的颜色*/
	scrollbar-face-color: #fff; /*图5,立体滚动条的颜色*/
	scrollbar-3dlight-color: red; /*图1,立体滚动条亮边的颜色*/
	scrollbar-highlight-color: #e9e9e9; /*图2,滚动条空白部分的颜色*/
	scrollbar-shadow-color: #c4c4c4; /*图3,立体滚动条阴影的颜色*/
	scrollbar-darkshadow-color: #666; /*图4,立体滚动条强阴影的颜色*/
	scrollbar-track-color: #dfdcdc; /*图7,立体滚动条背景颜色*/
	scrollbar-base-color: #fff; /*滚动条的基本颜色*/
}
</style>
	<title>创建流程</title>
	<link id="skin" rel="stylesheet" type="text/css"
		href="style/index-blue.css">
		<link rel="stylesheet" type="text/css" href="style/general.css">
	
		

<script type="text/javascript">
	$(function() {
		//获取个性化配置数据
		var url = contextPath
				+ "/personalitySettings/getPersonalitySettings.action";
		var json = tools.requestJsonRs(url, {});
		//var settings = eval("(" + json.rtData.wfPanelPosModel + ")");
		//var starModel = eval("(" + json.rtData.wfStarsModel + ")");

		//获取可发起流程信息
		var url = contextPath + "/workflow/getHandleableWorks.action";
		//var loadingPic = $("<center><img style='margin-top:40px;' src='"+systemImagePath+"/other/loading1.gif' /></center>");
		startLoading("#loadingDiv");
		tools.requestJsonRs(url, {}, true, function(json) {
			if (json.rtState) {
				var datas = json.rtData;
				var cols = [$("#left"),$("#middle"),$("#right")];
				if(datas!=null&&datas.length>0){
					for(var i=0;i<datas.length;i++){
						var data = datas[i];
						var sortName = data.sortName;
						var sortId = data.sortId;
						var flows = data.flowTypes;
						if(flows!=null&&flows.length>0){
							var block = [];
							block.push("<div class=\"modular\">");
							block.push("<div class=\"modular_hd\">");
							block.push("<div class=\"title\">"+sortName+"</div>");
							block.push("<div class=\"title_bg\"></div>");
							block.push("<div class=\"toggle\">");
							<%--block.push("<img class=\"img_on\" src='images/on.png'>");--%>
							<%--block.push("<img class=\"img_off\"  src='images/off.png'>");--%>
							block.push("</div>");
							block.push("</div>");
							block.push("<div class=\"modular_bd\">");
							block.push("<ul class=\"workflow_list\" id=\"wf_list_"+sortId+"\">");
							block.push("</ul>");
							block.push("</div>");
							block.push("</div>");
							
							$(block.join("")).appendTo(cols[i%3]);
							
							var item = [];
							for(var j=0;j<flows.length;j++){
								item.push("<li>");
//		 							item.push("<span class=\"xb\">星标</span>");
									item.push("<span class=\"fx\" onclick=\"viewGraph("+ flows[j].flowId + ")\">流程</span>");
									item.push("<span class=\"wjj\" onclick=\"viewForm("+ flows[j].formId + ")\">表单</span>");
									item.push("<a href=\"#\" class=\"model-menu-test\" onclick='$(this).modal();create("+ flows[j].flowId + ",\"" + flows[j].flowName + "\","+flows[j].runNamePriv+")'>"+flows[j].flowName+"</a>");
								item.push("</li>");
							}
							$(item.join("")).appendTo($("#wf_list_"+sortId));
						}
					}
				}
			}
// 			endLoading("#loadingDiv");
			$('.modular').find('.toggle').click(function(){
				$(this).toggleClass('on');
				$(this).parent('.modular_hd').nextAll('.modular_bd').slideToggle();
			});
		});
	});

	function renderContent(flowType, starModel) {
		var star = false;
		for (var i = 0; i < starModel.length; i++) {
			if (starModel[i] == flowType.flowId) {
				star = true;
				break;
			}
		}
		var text = "";
		text += "<div class=\"item  modal-menu-test\" " + (star ? "star=\"star\"" : " ")
				+ " id=\"item" + flowType.flowId + "\" onclick='$(this).modal();create("
				+ flowType.flowId + ",\"" + flowType.flowName + "\","+flowType.runNamePriv+")'>";
		text += "<div style=\"float:left;\">";
		text += "<img align=\"absMiddle\" src=\""
				+ systemImagePath
				+ "/workflow/"
				+ (flowType.hasDoc == 0 ? "document-text.png"
						: "document-html-word.png") + "\" />";
		text += "<a class=\"comment\" title=\"" + flowType.comment
				+ "\" href='javascript:void(0)'>" + flowType.flowName + "</a>";
		text += "<img id=\"starFlag" + flowType.flowId
				+ "\" clazz=\"starFlag\" val=\"" + flowType.flowId
				+ "\" title=\"已标记\" href='javascript:void(0)' "
				+ (star ? "" : "hidden") + " src=\"" + systemImagePath
				+ "/workflow/page_fav2.png\" />";
		text += "</div>";
		text += "<div class=\"item_right\" style=\"float:right;\">";
		text += "<img title=\"查看表单\" src=\"" + systemImagePath
				+ "/workflow/application-document.png\" onclick=\"viewForm("
				+ flowType.formId + ")\"/>";
		text += "&nbsp;";
		if (flowType.type == 1) {//固定流程有流程图
			text += "<img title=\"查看流程图\" src=\"" + systemImagePath
					+ "/workflow/icon-flow.png\" onclick=\"viewGraph("
					+ flowType.flowId + ")\"/>";
			text += "&nbsp;";
		}
		text += "<img title=\"加星标/取消星标\" src=\"" + systemImagePath
				+ "/workflow/page_fav.png\" onclick=\"toggleStar("
				+ flowType.flowId + ")\"/>";
		text += "</div>";
		text += "<div style=\"clear:both;\"></div>";
		text += "</div>";
		return text;
	}

	/**
	 * 切换星标记
	 **/
	function toggleStar(flowId, obj) {
		var flag = $("#starFlag" + flowId);
		var model = [];
		if (flag.is(":hidden")) {
			flag.show();
			$("#item" + flowId).attr("star", "star");
		} else {
			flag.hide();
			$("#item" + flowId).removeAttr("star");
		}

		$("img[clazz=starFlag]:visible").each(function(i, obj) {
			model.push(parseInt($(obj).attr("val")));
		});

		var url = contextPath
				+ "/personalitySettings/updateWfStarsModel.action";
		var para = {
			model : tools.jsonArray2String(model)
		};
		var json = tools.requestJsonRs(url, para);

		window.event.cancelBubble = true;
	}

	function viewForm(formId) {
// 		top.bsWindow(, "查看表单", {});
		openFullWindow(contextPath
				+ "/system/core/workflow/formdesign/printExplore.jsp?formId="
				+ formId,"查看表单");
		window.event.cancelBubble = true;
	}

	function viewGraph(flowId) {
// 		top.bsWindow(, "查看流程图", {});
		openFullWindow(contextPath
				+ "/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="
				+ flowId,"查看流程图");
		window.event.cancelBubble = true;
	}
	
	var flowId;
	var flowName;
	var priv;
	
	//创建流程
	function create(flowId, flowName,priv) {
		window.flowId = flowId;
		window.flowName = flowName;
		window.priv = priv;
		$("#msgTip").hide();
		$("#nameDiv").hide();
		$("#preDiv").hide();
		$("#sufDiv").hide();
		$("#preName").val("");
		$("#sufName").val("");
		$("#runName").val("");
		if(priv==0){
			$("#msgTip").show();
		}else if(priv==1){
			$("#nameDiv").show();
		}else if(priv==2){
			$("#preDiv").show();
		}else if(priv==3){
			$("#sufDiv").show();
		}else if(priv==4){
			$("#preDiv").show();
			$("#sufDiv").show();
		}
		
		/* $("#uploadDiv").modal("show"); */
// 		var html = "<div style='padding:10px;font-size:12px'>工作名称(为空则为默认名称)：<input type='text' id='runName' name='runName' class='BigInput' style='width:90%' placeholder=''/></div>";
// 		var submit = function(v, h, f) {
// 			var url = contextPath + "/flowRun/createNewWork.action";
// 			var json = tools.requestJsonRs(url, {
// 				fType : flowId,
// 				runName : f.runName
// 			});
// 			if (json.rtState) {
// 				window.openFullWindow(contextPath
// 						+ "/system/core/workflow/flowrun/prcs/index.jsp?runId="
// 						+ json.rtData.runId + "&frpSid=" + json.rtData.frpSid
// 						+ "&flowId=" + flowId + "&isNew=1", "流程办理");
// 			} else {
// 				alert(json.rtMsg);
// 			}
// 			return true;
// 		};

// 		$.jBox(html, {
// 			title : "新建工作 [" + flowName + "]",
// 			submit : submit
// 		});
	}
	
	function doCreate(){
			var url = contextPath + "/flowRun/createNewWork.action";
			var json = tools.requestJsonRs(url, {
				fType : window.flowId,
				runName : $("#runName").val(),
				preName:$("#preName").val(),
				sufName:$("#sufName").val()
			});
			if (json.rtState) {
				window.openFullWindow(contextPath
						+ "/workflow/toFlowRun.action?runId="
						+ json.rtData.runId + "&frpSid=" + json.rtData.frpSid
						+ "&flowId=" + flowId + "&isNew=1", "流程办理");
				$(".modal-test").modal("hide");
			} else {
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	}

	function lookStar() {
		$(".item").each(function(i, obj) {
			if (obj.getAttribute("star") != "star") {
				obj.style.display = "none";
			}
		});
	}

	function lookAll() {
		$(".item").each(function(i, obj) {
			obj.style.display = "block";
		});
	}
</script>

</head>
<body style="padding: 5px; ">
	<!-- 桌面开始 -->
	<div class="desktop">
		<div class="workflow_title" style="display:none">
			<!-- 只看星标 -->
			<ul>
				<li class="worka on" onclick="lookStar()" >只看星标</li>
				<li class="workb" onclick="lookAll()">查看全部</li>
			</ul>
		</div>
		<!-- 版块一列 -->
		<div class="desktop_list3" id="left">
			
		</div>
		<!-- 版块二列 -->
		<div class="desktop_list3" id="middle">
			
		</div>
		<!-- 版块三列 -->
		<div class="desktop_list3" id="right">
			
		</div>
	</div>
	<!-- 桌面结束 -->


 <!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			新建工作
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix" id="msgTip" style="display:none">
			  <p  >请点击确定后确认创建工作。</p>
			</li>
			<li id="preDiv" style="display:none">
			  <p  >工作名称前缀：<input type="text" class="BigInput" id="preName" name="preName" /></p>
			</li>
			<li id="nameDiv" style="display:none">
			  <p  >工作名称：<input type="text" class="BigInput" id="runName" name="runName"/></p>
			</li>
			<li id="sufDiv" style="display:none">
			  <p  >工作名称后缀：<input type="text" class="BigInput" id="sufName" name="sufName" /></p>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doCreate();" value = '确定'/>
	</div>
</div>	
	
</body>
</html>
