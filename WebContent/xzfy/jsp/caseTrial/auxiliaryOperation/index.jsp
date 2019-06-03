<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%
	String caseId = request.getParameter("caseId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>调查取证</title>
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	.tab-container{
   	    width: 100%;
	    height: 30px;
	    border-bottom: 1px solid blue;
	    position: relative;
   	}
   	
   	.case-tab{
	   	float:left;
	   	margin-right:5px;
   	    /* display: inline-block; */
	    text-align: center;
	    line-height: 30px;
	    width: 55px;
	    height: 30px;
	    border: 1px solid blue;
	    border-radius: 3px 3px 0 0;
   	}
   	.actived-tables{
   		float:left;
	   	margin-right:5px;
   	   /*  display: inline-block; */
	    text-align: center;
	    line-height: 30px;
	    width: 55px;
	    height: 30px;
	    border: 1px solid blue;
	    border-radius: 3px 3px 0 0;
	    border-bottom: none;
	    position: relative;
	   /*  bottom: -2px; */
	    z-index: 3;
	    background: white;
	    border-bottom-radius: 0px;
   	}
	</style>
</head>

<body style="padding-left: 10px;padding-right: 10px">

   <%-- <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title" id="title">调查管理</span>
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
   	</div> --%>
   	
   	<div class='tab-container' id="container">
	   	<span id="needDeal" class='actived-tables' onclick="change(this,'0')">中止</span>
	   	<span id="alreadyDeal" class='case-tab' onclick="change(this,'1')">恢复</span>
	   	<span id="lastDeal" class='case-tab' onclick="change(this,'2')">延期</span>
   	</div>
    
    <iframe id="exportIframe" style="width:100%;height:90%" src="/xzfy/jsp/caseTrial/auxiliaryOperation/break/index.jsp?caseId=<%=caseId %>"></iframe>

    <script type="text/javascript">
    var caseId = "<%=caseId %>";
	    //tab页切换
		function change(that,type){
	    	$(that).siblings().removeClass().addClass("case-tab");
			$(that).removeClass().addClass("actived-tables");
			//赋值
			if(type==0){
				$("#exportIframe").attr("src","/xzfy/jsp/caseTrial/auxiliaryOperation/break/index.jsp?caseId="+caseId);
			}
			if(type==1){
				$("#exportIframe").attr("src","/xzfy/jsp/caseTrial/auxiliaryOperation/recover/index.jsp?caseId="+caseId);
			}
			if(type==2){
				$("#exportIframe").attr("src","/xzfy/jsp/caseTrial/auxiliaryOperation/delay/index.jsp?caseId="+caseId);
			}
			$("#exportIframe").show();
		}
     </script>
</body>
</html>