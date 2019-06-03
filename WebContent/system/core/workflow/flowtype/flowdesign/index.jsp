<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
function doInit(){
	$("#layout").layout({auto:true});
}

function doSaveLayout(){
	$("#frame0")[0].contentWindow.saveLayout(false);
}

function doReload(){
	$("#frame0")[0].contentWindow.location.reload();
}

function doPrint(){
	$("#frame0")[0].contentWindow.print();
}

/**
* 打开属性窗口
*/
function openPropertyWindow(node){
	var id = node.attr("id");
	var prcsName = node.data("data").prcsName;
	var url = contextPath+"/system/core/workflow/flowtype/flowdesign/";
	if(node.attr("start")=="" || node.attr("aggregation")=="" || node.attr("simple")==""){
		top.bsWindow(url+"setprops/index.jsp?prcsId="+id+"&flowId="+flowId,"节点设置-"+prcsName,{width:"900",buttons:[{name:"保存",classStyle:"btn btn-primary"}],submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				cw.commit();
				$("#frame0")[0].contentWindow.location.reload();
			}
		}});
	}else if(node.attr("parallel")==""){
		top.bsWindow(url+"setprops/index_parallel.jsp?prcsId="+id+"&flowId="+flowId,"节点设置-"+prcsName,{width:"900",buttons:[{name:"保存",classStyle:"btn btn-primary"}],submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				cw.commit();
				$("#frame0")[0].contentWindow.location.reload();
			}
		}});
	}else if(node.attr("child")==""){
		top.bsWindow(url+"setprops/index_child.jsp?prcsId="+id+"&flowId="+flowId,"节点设置-"+prcsName,{width:"900",buttons:[{name:"保存",classStyle:"btn btn-primary"}],submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				cw.commit();
				$("#frame0")[0].contentWindow.location.reload();
			}
		}});
	}
}
</script>
</head>
<body onload="doInit()" style="margin:0px;background-image:url(res/bg.png)" oncontextmenu="return false">
<div id="layout">
	<div layout="north" height="40" style="padding:5px">
<!-- 		<input class="btn btn-primary" value="保存布局" type="button" onclick="doSaveLayout()"/> -->
<!-- 		<input class="btn btn-primary" value="刷新" type="button" onclick="doReload()"/> -->
		<input class="btn btn-info" value="打印" type="button" onclick="doPrint()"/>
	</div>
	<div layout="center" >
		<iframe id="frame0" style="width:100%;height:100%" frameborder="0" src="designer.jsp?flowId=<%=flowId %>">
		</iframe>
	</div>
</div>
</body>
</html>