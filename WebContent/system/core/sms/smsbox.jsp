<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/2/js/sms.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>消息盒子</title>
	<style>
	.smsbox{
		position:absolute;
		background:#f0f0f0;
		top:0px;
		left:0px;
		right:0px;
		bottom:0px;
		margin:0px;
		height:auto;
		width:auto;
		border:0px solid #c6c6c6;
		font-size:12px;
	}
	.smsbox .content{
	heigh:320px; 
	overflow:auto;
	overflow-x:hidden;
	}
	</style>
	<script type="text/javascript" charset="UTF-8">
	var loader;
	function doInit(){
		smsAlert();
	}
	
	function smsAlert(){
	    var url = contextPath+"/sms/popup.action";
	    tools.requestJsonRs(url,{},true);
	    if(!loader){
	    	loader = new lazyLoader({
				url:contextPath+'/sms/getSmsBoxDatas.action',
				placeHolder:'loadMore',
				contentHolder:'smsBoxContent',
				rowRender:function(rowData){
					var render = [];
					render.push("<div class=\"item\" onclick=\"openDetail('"+rowData.remindUrl+"','"+rowData.smsSid+"',this)\">");
					render.push("<div>");
					render.push("<span class=\"left0\">"+(rowData.fromUser==""?"系统":rowData.fromUser)+"</span>");
					render.push("<span class=\"right0\">"+(rowData.moduleNoDesc?"["+rowData.moduleNoDesc+"]":"")+"&nbsp;&nbsp;"+rowData.remindTimeDesc+"</span>");
					render.push("<div style=\"clear:both\"></div>");
					render.push("</div>");
					render.push("<div style=\"margin-top:5px\">"+rowData.content+"</div>");
					render.push("</div>");
					
					return render.join("");
				},
				onLoadSuccess:function(){
					$("#smsBoxContent").append($("#loadMore").show());
					$("#smsbox").show();
				},
				onNoData:function(){
					$("#loadMore").hide();
				}
			});
	    }else{
	    	loader.reload();
	    }
	    //$("#smsbox").css({top:($(window).height()-$("#smsbox").height())/2,left:($(window).width()-$("#smsbox").width())/2}).show();
	    
// 	    var sound = $("#__sound1");
// 		if(sound.length==0){
// 			sound = $("<embed id='__sound1' src='"+contextPath+"/system/frame/inc/alert.mp3' autostart=true ></embed>").hide();
// 			$("body").append(sound);
// 		}else{
// 			document.getElementById("__sound1").play();
// 		}
	}
	
	function openDetail(url,sid,obj){
		$(obj).remove();
		tools.requestJsonRs(contextPath+"/sms/updateReadFlag.action?ids="+sid);
		
		//获取数据
		var total = loader.getTotal();
		if(total==0){
			window.external.IM_HideForm();
		}
		if(url==undefined || url==null || url==""){
			return;
		}
		window.external.IM_OpenNavigation("事务详情",url,1024,700);
	}
	
	function smsDetails(){
		addNewTabs('消息事务', contextPath+'/system/core/sms/index.jsp');
		$("#smsbox").hide();
	}

	function smsViewAlls(){
		var url = contextPath+"/sms/viewAll.action";
	    tools.requestJsonRs(url,{},true);
	    window.external.IM_HideForm();
	    window.location.reload();
	}
	</script>
</head>
<body style="margin:0px;overflow:hidden" onload="doInit()">
<!-- 短消息面板 -->
<div class="smsbox" id="smsbox" style="position:relative">
<!-- 	<div class="title"> -->
<!-- 	<span style="float:left;">消息提醒</span> -->
<!-- 	<span style="float:right;cursor:pointer" onclick="window.external.IM_HideForm();">×</span> -->
<!-- 	<span style="float:right;cursor:pointer" class="smsbox_icon2" onclick="smsDetails()">消息列表</span> -->
<!-- 	<span style="float:right;cursor:pointer" class="smsbox_icon1" onclick="smsViewAlls()">全部已阅</span> -->
<!-- 	<div style="clear:both"></div> -->
<!-- 	</div> -->
	<div class="content" id="smsBoxContent">
		<div class="smsbox_loadmore" id="loadMore">
			点击加载更多
		</div>
	</div>
</div>
</body>
</html>