<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
</style>
<script>
//高级检索
function ShowPanel(){
	//$("#tab-content")  这个是找id为tab-content的元素
	//$("#tab-content iframe")是找这个元素下面的所有iframe
	//$("#tab-content iframe:visible")这个是找出tab-content下所有显示的iframe
	$("#tab-content iframe:visible")[0].contentWindow.showSearchPanel();//这种方式可以调用到iframe里面的方法

}

</script>
</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
	    <img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/core/base/dam/imgs/icon_dajy.png">
		<p class="title">档案借阅</p>
	    <div class="fl left">  
			<ul id = 'tab' class = 'tab clearfix'>
				
			</ul>
	    </div>
		<div class="fr">
		   <input type="button" class="btn-win-white" value="高级检索" onclick="ShowPanel();" />
		</div>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"档案查询",url:contextPath+"/system/core/base/dam/borrow/query/index.jsp"},
                              {title:"我的借阅",url:contextPath+"/system/core/base/dam/borrow/myBorrow/index.jsp"},
                              ]); 

</script>
</html>