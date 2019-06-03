<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>审理列表</title>
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

<body style="height:94%">
   <!--  	
   	<div class='tab-container' id="container">
	   	<span id="needDeal" class='actived-tables' onclick="change(this,'0')">待办</span>
	   	<span id="alreadyDeal" class='case-tab' onclick="change(this,'1')">已办</span>
   	</div>
    -->
    <iframe id="exportIframe" style="width:100%;height:100%" src="/xzfy/jsp/caseTrial/casehandle/agency.jsp"></iframe>

    <script type="text/javascript">
	    //tab页切换
		function change(that,type){
	    	$(that).siblings().removeClass().addClass("case-tab");
			$(that).removeClass().addClass("actived-tables");
			//赋值
			if(type==0){
				$("#exportIframe").attr("src","/xzfy/jsp/caseTrial/casehandle/agency.jsp");
			}
			if(type==1){
				$("#exportIframe").attr("src","/xzfy/jsp/caseTrial/casehandle/done.jsp");
			}
			$("#exportIframe").show();
		}
     </script>
</body>
</html>