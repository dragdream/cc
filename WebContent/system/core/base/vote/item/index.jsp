<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
 
    
<%
	int voteId  = TeeStringUtil.getInteger(request.getParameter("voteId"), 0); //投票Id
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<style>
	#group p{
		display:block;
		padding:10px 0;
		background-position:8px center;
		font-size:14px;
		background-repeat:no-repeat;
		cursor:pointer;
		padding-left:25px;
	}
	._active{
		background-color:#eaedf1;
	}
</style>


<script>
var voteId = "<%=voteId%>";
var voteType = "0"; 
function doInit(){
	$("#frame0").attr("src",contextPath+"/system/core/base/vote/item/setVoteItem.jsp?voteType=" + voteType+"&voteId="+voteId);

}


function changePage(voteType){
	
	$("#frame0")[0].contentWindow.addPortlet(voteType);
}

function toReturn(){
	alert(1);
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px;">
<div id="layout">
	<div layout="west" style="width:120px;">
		<p style='font-weight:bold;font-size:16px;border-bottom:2px solid #26aaf4;padding: 5px 0 10px 0px;'>投票类型</p>
		<div id="group" class="list-group">
		  <p href="javascript:void(0)" class="list-group-item" style='background-image:url(/common/zt_webframe/imgs/xzbg/tpgl/icon_checkbox.png);' onclick="changePage(0)" title="新建复选框">
			&nbsp;复选框 &nbsp;&nbsp;
		  </p>
		  <p href="javascript:void(0)" class="list-group-item" style='background-image:url(/common/zt_webframe/imgs/xzbg/tpgl/icon_radio.png);' onclick="changePage(1)" title="新建单选框">
		  	&nbsp;单选框
		  </p>	  
		  <p href="javascript:void(0)" class="list-group-item" style='background-image:url(/common/zt_webframe/imgs/xzbg/tpgl/icon_single.png);' onclick="changePage(2)" title="新建文本框">
		  	&nbsp;文本框
		  </p>   
		  <p href="javascript:void(0)" class="list-group-item" style='background-image:url(/common/zt_webframe/imgs/xzbg/tpgl/icon_textarea.png);' onclick="changePage(3)" title="新建多行文本框">
		  	&nbsp;多行文本框
		  </p>
		  <p href="javascript:void(0)" class="list-group-item" style='background-image:url(/common/zt_webframe/imgs/xzbg/tpgl/icon_slidedown.png);' onclick="changePage(4)" title="新建下拉菜单">
		  	&nbsp;下拉菜单
		  </p>
		</div>
	</div>
	<div layout="center" style="position:absolute;left:150px;right:0;top:5px;bottom:5px;">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%;"></iframe>
	</div>
</div>
</body>
<script>
	$("#group p").on("click",function(){
		$(this).addClass("_active").siblings().removeClass("_active");
	});
</script>
</html>