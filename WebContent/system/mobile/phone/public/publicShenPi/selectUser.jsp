<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//车辆Id
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>用品登记</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>

<script>
var status=<%=status%>;
var sid=<%=sid%>;
function doInit(){
	doPass(sid);
	 $("body").on("tap","#save",function(){
		 save(sid);
	});
}

function doPass(sid){
	//根据billId渲染调度人员
	var url = contextPath+"/officeStockBillController/getOperatorsByBillId.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{billId:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var html = "";
			for(var i=0;i<json.rtData.length;i++){
				var item = json.rtData[i];
				alert(item.userName);
				html+="<option value='"+item.uuid+"'>"+item.userName+"</option>";
			}
			$("#operatorSelect").html(html);
		}
	});
}

//保存
function save(sid){
	var url = contextPath+"/officeStockBillController/doAudit.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{billId:sid,auditType:1,operatorId:$("#operatorSelect").val()},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				window.location.href="list/indexList.jsp?status="+status;
				alert(json.rtMsg);
			}else{
				alert(json.rtMsg);
			}
		}
	});
}

</script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" onclick="javascript:history.back(-1);"></span>
	<h1 class="mui-title">选择审核人员</h1>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>选择人员</label>
		</div>
		<div id="passHtml">
			<select class="BigSelect" id="operatorSelect">
			
			</select>
	   </div>
   </div>

</form>	
</div>
<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li class="mui-table-view-cell" id="save">保存</li>
		</ul>
	</div>
	
<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>