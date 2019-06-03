<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);

	
	 TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	 boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "WORKFLOW_USER_PRIV");
	  

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;

function doInit(){
	$("#layout").layout({auto:true});
	
	//激活组样式
	$("#group").group();
	changePage(1);
}

function changePage(sel){
	$("iframe").hide();
	var iframe = $("#frame"+sel).show();
	if(sel==1){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/basic.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==2){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/prcspriv.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==3){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/fieldctrl.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==7){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/formValid.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==4){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/prcsset.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==5){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/condition.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==6){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/ext.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==8){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/mobileTemplate.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}
}


function commit(){
	$("iframe").each(function(i,obj){
		if($(obj).attr("src")){
			obj.contentWindow.commit();
		}
	});
	alert("保存成功");
}

</script>

</head>
<body id="body" onload="doInit()" style="overflow:hidden;font-size:12px;">
<div id="layout">
<div layout="west" width="170">
	<div id="group" class="list-group">
	  <a href="#" class="list-group-item active" onclick="changePage(1)">
	  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	基本信息
	  </a>
	  
	  <%
	     if(hasPriv){
	    	 
	    	 %>
	    	 
	  <a href="#" class="list-group-item" onclick="changePage(2)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	办理权限
	  </a>
	    	 
	    	 	 <%
	     }
	  %>
	  
	  <a href="#" class="list-group-item" onclick="changePage(3)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	字段控制
	  </a>
	  <a href="#" class="list-group-item" onclick="changePage(7)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	表单校验
	  </a>
	  <a href="#" class="list-group-item" onclick="changePage(4)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	流转设置
	  </a>
	  <a href="#" class="list-group-item" onclick="changePage(5)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	转交条件
	  </a>
	  <a href="#" class="list-group-item" onclick="changePage(6)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	扩展功能
	  </a>
	  <a href="#" class="list-group-item" onclick="changePage(8)">
	  <i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	移动表单模板
	  </a>
	</div>
</div>
<div layout="center" style="padding-left:10px;">
	<iframe id="frame1" frameborder=0 style="width:100%;height:100%"></iframe>
	<iframe id="frame2" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
	<iframe id="frame3" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
	<iframe id="frame4" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
	<iframe id="frame5" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
	<iframe id="frame6" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
	<iframe id="frame7" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
	<iframe id="frame8" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
</div>
</div>
</body>
</html>