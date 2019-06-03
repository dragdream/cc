<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<%@ include file="/header/header.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		String itemId = request.getParameter("itemId");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>文号生成</title>
<script>

var contextPath = "<%=contextPath%>";
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var itemId = "<%=itemId%>";

function doInit(){
	var url = contextPath+"/docNumController/getDocNumListByPriv.action";
	var json = tools.requestJsonRs(url,{rows:100,page:1});
	var docNumList = json.rtData;
	for(var i=0;i<docNumList.length;i++){
		var flowIds = docNumList[i].flowIds;
		
		//判断是否有全流程
		var show = false;
		var sp = flowIds.split(",");
		var isAllFlow = false;
		for(var j=0;j<sp.length;j++){
			if(sp[j]=="0"){
				isAllFlow = true;
				break;
			}
		}

		//如果没有全流程，则判断是否在该流程之内
		if(!isAllFlow){
			for(var j=0;j<sp.length;j++){
				if(sp[j]==(flowId+"")){
					show = true;
					break;
				}
			}
		}else{
			show = true;
		}

		if(show){
			$("<div class='item' sid='"+docNumList[i].sid+"'>"+docNumList[i].docName+"</div>").click(function(){
				var divs = $("#content div");
				for(var i=0;i<divs.length;i++){
					$(divs[i]).removeClass("focus");
				}
				$(this).addClass("focus");
			}).appendTo($("#content"));
		}
	}
}

function generate(){
	var item = $("#content .focus:first");
	if(item.length==0){
		alert("请选择所要生成的文号");
		return false;
	}

	if(window.confirm("确定要生成文号吗？","确认")){
		var url = contextPath+"/docNumController/generateDocNum.action";
		var json = tools.requestJsonRs(url,{runId:runId,flowId:flowId,sid:item.attr("sid")});
		parent.$("#"+itemId).val(json.rtData);
		parent.$('#mobileSealFrm').hide();
	}
	
}

</script>
<style>
.item{
padding:5px;
border-bottom:1px dotted gray;
cursor:pointer;
text-align:center;
font-size:16px;
}
.item:hover{
background:#9dc2db;
}
.focus{
background:#9dc2db;
}
</style>
</head>
<body onload="doInit()" style="padding:10px">
<div id="toolbar" style="position:absolute;top:0px;height:38px;">
	<div style="float:left"><form id="form1"></form></div>
		<div style="float:right">
		<button type="button"  onclick="parent.$('#mobileSealFrm').hide();">关闭</button>
		&nbsp;&nbsp;
		<button type="button"  onclick="generate();">确定</button>
		</div>
		<div style="clear:both"></div>
</div>
<div id="content" style="position:absolute;top:40px;left:0px;right:0px;bottom:0px;">
	
</div>
</body>

</html>