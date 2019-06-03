<!-- 所有包含这个页面的页面，将会实现页面正在加载的提示信息，注意必须放在页面的最前面 author:kiral--> 
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.apache.commons.lang3.StringUtils,org.apache.commons.lang3.ObjectUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<link rel="stylesheet" href="<%=contextPath %>/system/core/mail/loading/style.css" type="text/css" />
<script src="<%=contextPath %>/system/core/mail/loading/loading.js" type="text/javascript"></script>
<div id="loading">
	<div class="loading-indicator">
		页面正在加载中...
	</div>
</div>
<script type="text/javascript">
//判断页面是否加载完毕，如果加载完毕，就删除加载信息的DIV
/*
function document.onreadystatechange()
{
	
	try
	{
		if (document.readyState == "complete") 
		{
	     	delNode("loading");
	    }
    }
    catch(e)
    {
    	alert("页面加载失败");
    }
}
*/
duduppp.domReady(delNode);
//删除指定的DIV
function  delNode(){   
	var nodeId = "loading";
  try{   
	  var div =document.getElementById(nodeId);  
	  if(div !==null){
		  div.parentNode.removeChild(div);   
		  div=null;    
		  CollectGarbage(); 
	  }  
  }
  catch(e){   
  	  //alert("删除ID为"+nodeId+"的节点出现异常");
  }   
}

</script>
</HEAD>
</HTML>
