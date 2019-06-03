<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	String formId = request.getParameter("formId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%
		String contextPath = request.getContextPath();
	    String systemImagePath = contextPath+"/common/images";
	%>
	<title>表单预览</title>
	<meta charset="utf-8">
	<script src="<%=contextPath %>/common/ckeditor/jquery.js"></script>
	<script src="<%=contextPath %>/common/js/tools.js"></script>
	<script src="<%=contextPath%>/common/js/sys.js"></script>
	<script src="<%=contextPath%>/common/js/src/orgselect.js"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js?v=2"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	
	<!-- jQuery 布局器 -->
	<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript"  src="/common/js/address_cascade.js"></script>
	<script>
	var contextPath = "<%=contextPath%>";
	var systemImagePath = "<%=systemImagePath%>";
	var formId = <%=formId%>;
	var rand = 123;
	function doInit(){
		var json = tools.requestJsonRs(contextPath+"/flowRun/getFormPrintExplore.action",{formId:formId});
		if(json.rtState){
			$("#form").html(json.rtData.html+"<style>"+json.rtData.css+"<\/style>");
		}
		initialize();
		
		$("[xtype=xfeedback]").each(function(i,obj){
			var item = obj.getAttribute("id");
			
			var template = $("#"+item).attr("template");
			template = tools.unicode2String(template);
			template = template.replace("{C}","<span id=\"FEEDBACK_CTRL_DEMO_CONTENT_"+item+"\">{会签内容}</span>")
			.replace("{U}","{当前用户}")
			.replace("{D}","{部门名称}")
			.replace("{DD}","{部门全名}")
			.replace("{R}","{用户角色}")
			.replace("{T}","{会签时间}")
			.replace("{O}","{操作}")
			.replace("{P}","<span id=\"SIGN_POS_CTRL_"+item+"_"+rand+"\" class='SIGN_POS_CTRL_"+item+"' style='position:absolute;color:gray'>{签章基准位置}</span>");
			$("#"+item).before("<span id=\"FEEDBACK_CTRL_DEMO_"+item+"\">"+template+"</span>");
		});
		
	}
	
	function addSeal(ctrlName){
		var targetObject = $("#CTRL_PICSEAL_IMG_"+ctrlName);
		  if(targetObject.length==0){
			  targetObject = $("<img onerror=\"this.style.display='none';\" id=\"CTRL_PICSEAL_IMG_"+ctrlName+"\" target=\""+ctrlName+"\" />").appendTo($(".SIGN_POS_CTRL_"+ctrlName));
		  }
		  targetObject.css({opacity:1,"position":"absolute",left:0,top:0}).show();
		  
		  targetObject.attr("src","data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE8AAAAjCAYAAADVAlenAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAD2EAAA9hAag/p2kAAAH1SURBVGhD7ZrNTQQxDIVTBkdKoARKoRQ6oZQtgRI40gWQRM8jxxMnTmJ2F5RPPK3/d/TEcCJsFvkO4TnqdaupJ9hVgub3VlMvsKskNrJ5XyFcEG9B0ZP3+Nk3L32itAHRvLdfNS/tIlwGz+F2jzNz2808zMSfklptFu07VqG7o7eXzEO9K4wPI/dlvoK8M3P7KuZxYdWE3JH5LHSH35K5hbt+belmTxg3U9ur1Xq4m9cSRk3I3Z6wZkLuUM5rFobNQ+ym/CUVav1abQa6YxXWTiyZlwcqaH2qc6FV0KprPSvaDa3ewuW1TX2EGcwfNZknZM6R85S3hNEu2uzoncSyeeiZhJUmcmdEOKHCZ2RMQsmEm3lIM7Im8xY0y3dkTVM+YEDu9YS1E3f3m1eD7/N4FrphFdZO3Oxv3gh8n8de0M3Ru+7mYbZ4EJmPIHdl7sXMXTfzLMK4mdquzD2gm6N3XcxLpBmEBVq9R9ojoZTh9VYPpQPeswhr1T20fM1LQnqg1TVongutA9mvCaMZ2VsVzvq+tkgLqKf1CT7HhfYJOceFkYNev4bcIaGdWTIP9dPRGpZZywyHz3Oh7YZ22+21vTXpGRFejX9j3i3Y5i2wzVtgm7eA2bw4+BF12Sr0aTJvqynVvMeo9J9SW7oeYNdms/mrhPADmo4R1FVhll8AAAAASUVORK5CYII=");
	}
	
	
	
	//地理位置
	function selectPosition(data,extra,positionType){
		if(positionType=="1"||positionType==1){
			alert("PC端暂不支持自动获取当前位置！");
		}else{
			var url=contextPath+"/system/core/workflow/flowrun/prcs/map.jsp?data="+data+"&extra="+extra+"&positionType="+positionType;
			openFullWindow(url);
		}
	}
	
	
	//地图预览
	function  previewPosition(spanId){
		//获取坐标点
		var points=document.getElementById(spanId).nextSibling.nextSibling.value;
		var lng=points.split(",")[0];
		var lat=points.split(",")[1];
		
		var url=contextPath+"/system/core/workflow/flowrun/prcs/mapPreview.jsp?lng="+lng+"&lat="+lat;
		
		dialog(url,"600px","300px");
	}
	</script>
<style>
p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol
{
	font-size:14px;
	margin:0px;
	padding:0px;
}
table{
border-collapse:collapse;
font-size:12px;
}
</style>
</head>
<body style="margin:0px" onload="doInit()">
<div id="form" class="wf">
	
</div>
</body>
</html>