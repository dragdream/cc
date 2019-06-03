<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style='overflow:hidden;'>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>督办任务管理</title>
<script>


</script>
<style>
	.ui-layout-west{
	    position:absolute;
		top: 41px;
		left: 10px;
		bottom: 0px;
		width: 200px;
	}
	.ui-layout-center{
		position:absolute;
		top:41px;
		left:201px;
		bottom:0px;
		right:10px;
		border-left:2px solid #e2e2e2;
	}
	
	.ui-layout-top{
	   position: absolute;
	   top:5px;
	   left: 10px;
	   right: 10px;
	   height: 35px;
	   border-bottom:2px solid #74c5ff;
	}
	
</style>
</head>
<script>
//新建督办任务
function add(){
	var url=contextPath+"/system/subsys/supervise/supervisionManage/addOrUpdate.jsp";
	bsWindow(url ,"新建督办任务",{width:"800",height:"380",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
		 {name:"发布",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.save(0,function(flag){
		    	if(flag){
		    		d.hide();
		    		$.MsgBox.Alert_auto("新建成功");
		    		$("#file_main")[0].contentWindow.location.reload();
		    	}else{
		    		$.MsgBox.Alert_auto("新建失败！");
		    	}
		    	
		    });
		}else if(v=="发布"){
			cw.save(7,function(flag){
				if(flag){
		    		d.hide();
		    		$.MsgBox.Alert_auto("发布成功");
		    		$("#file_main")[0].contentWindow.location.reload();
		    	}else{
		    		$.MsgBox.Alert_auto("发布失败！");
		    	}
		    });
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

</script>

<body onload="" style="">
    <div class="ui-layout-top">
       <div class="fl" style="position:static;">
		   <img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/supervise/imgs/icon_dubanrenwuguanli.png">
		   <span class="title">督办任务管理</span>
	   </div>
	   <div class = "right fr clearfix">
		   <input type="button" class="btn-win-white" onclick="add()" value="新建督办任务"/>
       </div>
    </div>
    <div>
        <div class="ui-layout-west">
		  <iframe id="file_tree" src="left.jsp" frameborder=0 style="width:100%;height:100%"></iframe>
	    </div>
	    <div class="ui-layout-center">
		  <iframe id="file_main" src="comfireNo.jsp" frameborder=0 style="width:100%;height:100%"></iframe>
	    </div>
    </div>
	
</body>