<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<%
	String uuid = request.getParameter("uuid");
%>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js?v=2"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="">
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/mui.min.js"></script> 
<title>阅件详情</title>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
	/* if(window.external){
		window.external.setTitle("阅件详情");
	} */
	
	var json = tools.requestJsonRs(contextPath+"/doc/getViewInfo.action?uuid="+uuid);
	data = json.rtData;
	$("#bt").html(data.bt);
	$("#zh").html(data.zh);
	$("#fwdw").html(data.fwdw);
	
	window.runId = data.runId;
	
	var contentPriv=data.contentPriv;
	if((contentPriv&1)==1){//表单
		$("#bdTd").show();
	    $("#bd").bind("click",function(){
	    	window.location = "/system/mobile/phone/workflow/print.jsp?runId="+data.runId+"&view=1";
	    });
	}
	
	if((contentPriv&16)==16){//签批单
		$("#qpdTd").show();
	    $("#qpd").bind("click",function(){
	    	renderTemplate(data.runId);
	    	if(isHasAipFiles==0){
	    		Alert("暂无签批单！");
	    	}else{
	    		mui('#sheet1').popover('toggle');
	    	}
	    	
	    });
	}
	
	if((contentPriv&2)==2 && json.rtData.docId){//正文
		$("#zwTd").show();
	    $("#zw").bind("click",function(){
	    	GetFile(data.docId,data.docId+"_"+data.docFileName,data.docId+"_"+data.docAttachName);
	    });
	}
	if((contentPriv&4)==4 && json.rtData.docAipId){//版式正文
		$("#bszwTd").show();
	    $("#bszw").bind("click",function(){
	    	OpenAipFile(data.docAipId,data.runId,1);
	    });
	}
	if((contentPriv&8)==8){//附件
		$("#attDiv").show();
		loadWorkFlowAttach(data.runId);
	}
	
	if((contentPriv&1)!=1&&(contentPriv&2)!=2&&(contentPriv&4)!=4 ){
		$("#div1").hide();
	}
}


function loadWorkFlowAttach(runId){
	var url = contextPath+"/teeWorkflowAttachmentController/getTeeWorkFlowAttachment.action";
	var para  = {};
	para["runId"] = runId;
	$("#pulicAttachments").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var attachList = json.rtData;
		var attStrHtml = "";
		$(parent.attach_count).html(attachList.length);
		for(var j=0;j<attachList.length;j++){
			var att = attachList[j];
			$("#pulicAttachments").append("<p onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\" style=color:#0080ff;font-size:12px><img src='<%=request.getContextPath() %>/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")</p>");
			$("#attachDiv").show();
		}
	}else{
		alert(json.rtMsg);
	}
}

function viewForm(){
	OpenWindow("表单详情","/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view=1");
}


//渲染和流程相关的签批文件
var isHasAipFiles=0;//标志  代表是否存在签批文件
function renderTemplate(runId){
	$("#templateUl").empty();
	
	var  url=contextPath+"/flowRunAipTemplateController/getListByRunId.action";
	var  json=tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		var  data=json.rtData;
		var html = [];
		if(data && data.length>0){
			isHasAipFiles=1;
			for(var i=0;i<data.length;i++){
				//html.push("<option  value="+data[i].templateId+"  templateName='"+data[i].templateName+"'  attachmentId="+data[i].attachId+">"+data[i].templateName+"</option>");
				html.push("<li class=\"mui-table-view-cell\">");
				html.push("<a href=\"#\" onclick=\"changeTemplate("+data[i].templateId+",'"+data[i].templateName+"',"+data[i].attachId+","+runId+")\">"+data[i].templateName+"</a>");
		        html.push("</li>"); 
			}
		}else{
			isHasAipFiles=0;
		}
		$("#templateUl").append(html.join(""));
	}
}



function changeTemplate(templateId,templateName,attachId,runId){
	if($("#templateId").val()==0){
	
	}else{
	    //关联表单数据
	    var  u=contextPath+"/flowRun/getFlowRunDatasOnTitle.action";
	    var j=tools.requestJsonRs(u,{runId:runId});
	    if(j.rtState){
	    	var d=j.rtData;
	    	OpenAip(attachId,templateName+".aip",1,d);
	    }
	}
	
}
</script>


</head>
<body onload="doInit()" >
   <div align="center" style="border-bottom:1px solid #F5F5F5;padding-top: 10px; ">
      <p style='color:red;font-size:20px;' id="bt"></p>
	  <p>字号：<span id="zh"></span></p>
	  <p>发文单位：<span id="fwdw"></span></p>
   </div>
	
	<div id="div1" style="padding-bottom:10px;padding-top: 10px;border-bottom:1px solid #F5F5F5;width: 100%">
	   <table style="width: 100%">
	       <tr>
	          <td id="bdTd" width="20%" style="text-indent: 5px;display: none"></span><span style="vertical-align:middle;display:inline-block; background:url(images/icon_bd.png) top left no-repeat; width:20px; height:20px;"></span><a style="text-decoration:none;" href="#" id="bd">表单</a></td>
	          <td id="qpdTd" width="25%" style="display: none"></span><span style="vertical-align:middle;display:inline-block; background:url(images/icon_qpd.png) top left no-repeat; width:20px; height:20px;"></span><a style="text-decoration:none;" href="#" id="qpd">签批单</a></td>
	          <td id="zwTd" width="25%" style="display: none"><span style="vertical-align:middle;display:inline-block; background:url(images/icon_zw.png) top left no-repeat; width:20px; height:20px;"></span><a style="text-decoration:none;" href="#" id="zw" >正文</a></td>
	          <td id="bszwTd" width="30%" style="display: none"><span style="vertical-align:middle;display:inline-block; background:url(images/icon_bszw.png) top left no-repeat; width:20px; height:20px;"></span><a style="text-decoration:none;" href="#" id="bszw">版式正文</a></td>
	       </tr>
	   </table>
	</div>
	
	<div  id="attDiv" style="padding-left: 10px;display: none;">
	   <p id="attachDiv" style="font-size:14px;display:none">附件：</p>
	   <div id="pulicAttachments" style=""></div>
	</div>



<div id="sheet1" class="mui-popover mui-popover-bottom mui-popover-action ">
    <!-- 可选择菜单 -->
    <ul class="mui-table-view" id="templateUl">
      <!-- <li class="mui-table-view-cell">
        <a href="#">菜单1</a>
      </li>
      <li class="mui-table-view-cell">
        <a href="#">菜单2</a>
      </li> -->
    </ul>
    <!-- 取消菜单 -->
    <ul class="mui-table-view">
      <li class="mui-table-view-cell">
        <a href="#sheet1"><b>取消</b></a>
      </li>
    </ul>
</div>	
</body>
<!-- <body onload="doInit()" style="text-align:center">
	<p style='color:red;font-size:20px;' id="bt"></p>
	<p style="color:#428bca;font-size:14px" onclick="viewForm()">查看表单</p>
	<p style="color:#428bca;font-size:14px" id="docDiv"></p>
	<p id="attachDiv" style="font-size:14px;display:none">附件：</p>
	<div id="pulicAttachments" style="padding:10px;"></div>
</body> -->
</html>