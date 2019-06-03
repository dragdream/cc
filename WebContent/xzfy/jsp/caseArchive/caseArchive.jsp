<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>归档</title>
	
	<link rel="stylesheet" type="text/css" href="/xzfy/css/caseClose/caseClose.css" />
	
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
   	html, body{
		margin:0;
		padding:0;
		border:0;
		outline:0;
		font-weight:inherit;
		font-style:inherit;
		font-size:100%;
		font-family:inherit;
		vertical-align:baseline;
		-webkit-text-size-adjust:90%;
	    overflow: auto;
	}
	.tab-container{
   	    width: 100%;
	    height: 30px;
	    border-bottom: 1px solid blue;
	    position: relative;
   	}
   	.case-tab{
   	    display: inline-block;
	    text-align: center;
	    line-height: 30px;
	    width: 100px;
	    height: 30px;
	    border: 1px solid blue;
	    border-radius: 3px 3px 0 0;
   	}
   	.actived-tables{
   	     display: inline-block;
	    text-align: center;
	    line-height: 30px;
	    width: 100px;
	    height: 30px;
	    border: 1px solid blue;
	    border-radius: 3px 3px 0 0;
	    border-bottom: none;
	    position: relative;
	    bottome: -2px;
	    z-index: 3;
	    background: white;
	    border-bottom-radius: 0px;
   	}
	</style>
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

   <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <span class="title" id="title"></span>
	    </div>
	    <div class = "right fr clearfix" id="accept" >
	        <input type="button" class="btn-win-white" onclick="mergeCase();" value="归档"/>&nbsp;
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class='tab-container' id="container">
	   	<span id="needDeal" class='actived-tables' onclick="change(this,'1')">正卷归档</span>
	   	<span id="alreadyDeal" class='case-tab' onclick="change(this,'0')">副卷归档</span>
   		</div>
	    <div class="setHeight" id="content">
	        
		</div>
   	</div>
   	
    <iframe id="exportIframe" style="display:none"></iframe>
    <script type="text/javascript" src="/xzfy/js/caseArchive/caseArchive.js"></script>
    
</body>
</html>