<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
int userId=loginUser.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最新文档</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileManage.js?v=3"></script>
<style type="text/css">
.returnButton{
        padding:5px 8px;
		/* padding-left:22px; */
		text-align:center; 
		/* text-align:right; 
		background-repeat:no-repeat;
		background-position:6px center; */
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
}


.TableBlock_page{
   border:2px solid #f2f2f2;
}
</style>
<script type="text/javascript">
var userId=<%=userId%>;
function doInit(){
	
}

</script>
</head>
<body onload="doInit();" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	<div class="titlebar clearfix">
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/core/base/fileNetdisk/fileManage/img/icon_neword.png">
		<p class="title">最新文档</p>
		
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"全部",url:contextPath+"/system/core/base/fileNetdisk/fileManage/newWordList.jsp?status=0"},
                               {title:"今天",url:contextPath+"/system/core/base/fileNetdisk/fileManage/newWordList.jsp?status=1"},
                              {title:"本周",url:contextPath+"/system/core/base/fileNetdisk/fileManage/newWordList.jsp?status=2"},
                              {title:"本月",url:contextPath+"/system/core/base/fileNetdisk/fileManage/newWordList.jsp?status=3"},
                              {title:"本季",url:contextPath+"/system/core/base/fileNetdisk/fileManage/newWordList.jsp?status=4"},
                              {title:"本年",url:contextPath+"/system/core/base/fileNetdisk/fileManage/newWordList.jsp?status=5"},
                              ]); 

</script>
</html>