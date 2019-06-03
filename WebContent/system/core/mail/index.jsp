<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/header/header.jsp"%>
	<%
		String type = request.getParameter("type");
	%>
	<title>内部邮件</title>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"/>
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>
	<script type="text/javascript" charset="UTF-8">
	var type = "<%=type%>";
	var bodyFrame;
	var bodyHeight;
	//获取域名
	var host = window.location.host;

	$(document).bind("contextmenu",function(e){ 
		return false; 
	});
	function doInit(){
		$("[title]").tooltips();
		if("${select}"!=""&&"${select}"!=null&&"${select}"!="null"){
			$("#singleDiv").show();
			$("#readDiv").hide();
		}
		//alert("index:"+"${type}");
		if("${type}"!=""&&"${type}"!=null&&"${type}"!="null"){
			//alert("${type}");
			$("#type").val("${type}");
			var menu = document.getElementById('menu').contentWindow.document;
			$(menu.getElementById("${type}")).trigger("click");
		}
		//初始化肤色
		$("#indexHeader").css("backgroundColor","${color}");
		$("#indexHeader").css("borderColor","${color}");
		$(".bs-docs-nav").css("backgroundColor","${color}");
		$(".bs-docs-nav").css("borderColor","${color}");
		$("#layout").layout({auto:true});
		$(document).click(function(){
			noShowRemarkA();
		});
		$('#moveIcon').click(function (e){ 
			e.stopPropagation();
		});
		$('#moveToReceiveList').click(function (e){ 
			e.stopPropagation();
		});
		$('#moreSingle').click(function (e){ 
			e.stopPropagation();
		});
		$('#moveSingle').click(function (e){ 
			e.stopPropagation();
		});
		$('#colorIcon').click(function (e){ 
			e.stopPropagation();
		});
		//初始化排序图标
		$("#order"+$("#order").val()+"_"+$("#orderRule").val()).show();

	}

	function createMail(){
		window.location = "<%=contextPath %>/mail/createMail.action";
	}
	function readMailBody(mailId,type,ifBox,ifBody,select){
		//alert(mailId+","+type+","+ifBox+","+ifBody+","+select);
		noShowRemarkA();
		$("#type").val(type);
		$("#mailId").val(mailId);
		$("#ifBox").val(ifBox);
		$("#ifBody").val(ifBody);
		$("#select").val(select);
		var url = "";
		SingleMail();
		if(type!="1"&&type!="2"){//不是草稿、已发送
			$("#moveSingle").show();
			$("#deleteSingle").show();
			$("#deleteSingleCaogao").hide();
			$("#deleteSingleSend").hide();
			if(type=="3"){//是已删除
				$("#deleteSingle").hide();
				$("#destroySingle").show();
			}else{
				$("#deleteSingle").show();
				$("#destroySingle").hide();
			}
		}else{
			if(type=="1"){//是草稿
				$("#deleteSingleCaogao").show();
				$("#deleteSingleSend").hide();
			}
			if(type=="2"){//是已发送
				$("#deleteSingleSend").show();
				$("#deleteSingleCaogao").hide();
			}
			$("#moveSingle").hide();
			$("#deleteSingle").hide();
			$("#destroySingle").hide();
			$("#delDivCaogao").hide();
			$("#delDivSend").hide();
		}

		if(select == "select"){//是分类箱
			$("#readDiv").hide();
			$("#singleDiv").hide();
			url = "<%=contextPath %>/mail/selectMailByIdToBody.action?mailId="+mailId+"&ifBody="+ifBody;
		}else{
			//$("#readDiv").show();
			//$("#singleDiv").show();
			url = "<%=contextPath %>/mail/readMailBody.action?mailId="+mailId+"&type="+type+"&ifBox="+ifBox+"&ifBody="+ifBody+"&select="+select;
		}
		//alert(url);
		body.location = url;
	}
	function SingleMail(){
		$("#sub-tabs").hide();
		$("#tabs-content").css("top","0px");
		var height = $("#tabs-content").height()+45;
		bodyHeight = $("#tabs-content").height();
		$("#tabs-content").height(height);
		$("#readDiv").hide();
		$("#singleDiv").show();
	}
	function notSingleMail(){
		
		$("#sub-tabs").show();
		$("#tabs-content").css("top","45px");
		var height = bodyHeight;
		$("#tabs-content").height(height);
		$("#readDiv").show();
		$("#singleDiv").hide();
		var ifBox = $(window.frames["menu"].document).find("#ifBox").val();
		//alert($("#select").val());
		if(($("#menuType").val()=="1")&&(ifBox=="0")){
			$("#readDiv").hide();
			$("#delDivCaogao").show();
		}else if(($("#menuType").val()=="2")&&(ifBox=="0")){
			$("#readDiv").hide();
			$("#delDivSend").show();
		}
		if(ifBox!="0"){
			$("#delDivSend").hide();
			$("#delDivCaogao").hide();
		}
		
		
		
	}
	function changeBody(type){
		//alert(1);
		notSingleMail();
		//$("#tabs-content").css("top","45");
		if(type=="3"){
			$("#destroy").show();
		}else{
			$("#destroy").hide();
		}
		$("#menuType").val(type);
		$("#lookAll").val("0");
		$("#ifBox").val("0");
		$("#order").val("0");
		$("#date").val("0");
		$("#select").val("");
		$("#orderRule").val("1");
		$("#checkBoxAll").attr("checked", false);
		$("#dLabel").html("所有 <span class='caret'></span>");
		$("#dLabel1").html("排序方式 <span class='caret'></span>");
		$("#selectType").val(0);
		var index = document.getElementById('menu').contentWindow.document;
		index.getElementById("selectInput").value="";
		var value = index.getElementById("selectInput").value;
		if(value=="搜索电子邮件"||value==""){
			value = null;
		}
		value = encodeURIComponent(value);
		var url = encodeURI("<%=contextPath %>/mail/body/0/"+value+"/"+type+"/0/0/0/0/1.action");
		body.location = url;
		var t = menu.getTypeByMenu();
		var i = menu.getIfBoxByMenu();
		if(t==1||t==2){
			$("#readDiv").hide();
			if(t==1){
				$("#delDivCaogao").show();
				$("#delDivSend").hide();
			}
			if(t==2){
				$("#delDivSend").show();
				$("#delDivCaogao").hide();
			}
		}else{
			$("#delDivCaogao").hide();
			$("#delDivSend").hide();
			$("#delDiv").show();
			$("#readDiv").show();
			if(t==3){
				$("#delDiv").hide();
			}
			
		}
		//alert(t+","+i);
	}
	function changeBox(boxId){
		//alert(2);
		notSingleMail();
		$("#destroy").hide();
		$("#menuType").val(boxId);
		$("#ifBox").val(1);
		$("#lookAll").val("0");
		$("#order").val("0");
		$("#date").val("0");
		$("#select").val("");
		$("#orderRule").val("1");
		$("#checkBoxAll").attr("checked", false);
		$("#dLabel").html("所有 <span class='caret'></span>");
		$("#dLabel1").html("排序方式 <span class='caret'></span>");
		$("#selectType").val(0);
		var index = document.getElementById('menu').contentWindow.document;
		index.getElementById("selectInput").value="";
		var value = index.getElementById("selectInput").value;
		if(value=="搜索电子邮件"||value==""){
			value = null;
		}
		var url = "<%=contextPath %>/mail/body/0/"+value+"/"+boxId+"/1/0/0/0/1.action";
		body.location = url;
		var t = menu.getTypeByMenu();
		var i = menu.getIfBoxByMenu();
		$("#readDiv").show();
		$("#delDiv").show();
	}
	function menuType(){
		var type = $("#menuType").val();
		return type;
	}
	
	function selectList(){
		var type = $("#menuType").val();
		var ifBox = $("#ifBox").val();
		var lookAll = $("#lookAll").val();
		var order = $("#order").val();
		var orderRule = $("#orderRule").val();
		var date = $("#date").val();
		var selectType = $("#selectType").val();
		
		if(selectType=="select"){
			selectType = "0";
		}
		//alert(selectType);
		var index = document.getElementById('menu').contentWindow.document;
		var value = index.getElementById("selectInput").value;
		if(value=="搜索电子邮件"||value==""){
			value = null;
		}
		value = encodeURIComponent(value);
		var url = "";
		url = encodeURI("<%=contextPath %>/mail/body/"+selectType+"/"+value+"/"+type+"/"+ifBox+"/"+lookAll+"/"+order+"/"+date+"/"+orderRule+".action");
		//alert(url);
		body.location = url;
	}
	
	function changeLookAll(value,text){
		$("#lookAll").val(value);
		$("#dLabel").html(text+" <span class='caret'></span>");
		selectList();
	}
	function changeOrder(value,text){
		$("#order").val(value);
		$("#dLabel1").html(text+" <span class='caret'></span>");
		if($("#orderRule").val()=="0"){
			$("#orderRule").val("1");
		}else{
			$("#orderRule").val("0");
		}
		setOrderRule();
		selectList();
	}
	function changeDate(value){
		$("#date").val(value);
		selectList();
	}
	function returnType(){
		return $("#type").val();
	}
	function getDate(){
		return $("#date").val();
	}
	function checkAll(){
		body.checkAll();
	}
	function showRemark(eventTag,n){
		noShowRemarkA();
		var x = "divRemark0";
		if(n=="13"){
			x ="divRemark13";
		}
		if(document.getElementById(x).style.display !="none"){
			document.getElementById(x).style.display = 'none';
		}else{
		    var event = eventTag || window.event;
		    var top = 45;
		    document.getElementById(x).style.top = top + 'px';//(top + document.documentElement.scrollTop)+'px';
		    var left = event.clientX-38;   
		    if((left+360) > document.body.offsetWidth)
		        document.getElementById(x).style.left = (left + document.documentElement.scrollLeft - 360)+'px';
		    else
		        document.getElementById(x).style.left = (left + document.documentElement.scrollLeft)+'px';
	
		    document.getElementById(x).style.display = '';
		}
	}

	function noShowRemark(){
		//alert("out");
	    document.getElementById("divRemark0").style.display = 'none';
	}
	function checkNull(){
		var body = document.getElementById('body').contentWindow.document;
		var checkValues = body.getElementById("checkValues").value;
		var ifBody = body.getElementById("ifBody").value;
		if(checkValues!=""){
			return true
		}else{
			alert("请至少选择一封邮件进行操作!");
			return false;
		}
	}
	function moveMail(boxId){
		//alert(boxId);
		if(checkNull()){
			var mailIds = body.getMailIdsByBody();
			var ifBody = body.getIfBodyByBody();
			var url = "<%=contextPath %>/mail/moveMail.action?mailIds="+mailIds+"&boxId="+boxId+"&ifBody="+ifBody;
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
		    
		    alert(jsonRs.rtMsg);
		    toIndex();
		    
		}
	}
	
	function moveReceive(){
		//alert(boxId);
		if(checkNull()){
			var mailIds = body.getMailIdsByBody();
			var url = "<%=contextPath %>/mail/moveReceive.action?mailIds="+mailIds;
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
		    
		    alert(jsonRs.rtMsg);
		    toIndex();
		    
		}
		noShowRemarkA();
	}
	
	function delMail(){
		if(checkNull()){
			var mailIds = body.getMailIdsByBody();
			var ifBody = body.getIfBodyByBody();
			var url = "<%=contextPath %>/mail/delMail.action?mailIds="+mailIds+"&ifBody="+ifBody;
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
			alert(jsonRs.rtMsg);
			//window.frames["body"].location.reload();
			//window.frames["menu"].location.reload();
			toIndex();
		}
	}
	//根据id销毁邮件
	function destroyMails(){
		if(checkNull()){
			if(window.confirm('你确定销毁所选邮件吗？')){ 
				var mailIds = body.getMailIdsByBody();
				var ifBody = body.getIfBodyByBody();
				var url = "<%=contextPath %>/mail/destroyMails.action?mailIds="+mailIds+"&ifBody="+ifBody;
				var para =  tools.formToJson($("#form1"));
				var jsonRs = tools.requestJsonRs(url,para);
				alert(jsonRs.rtMsg);
				toIndex();
			}
		}
	}
	
	//根据id销毁单个邮件
	function destroySingleMail(){
		if(window.confirm('你确定销毁所选邮件吗？')){ 
			var mailIds = $("#mailId").val();
			var ifBody = $("#ifBody").val();
			var url = "<%=contextPath %>/mail/destroyMails.action?mailIds="+mailIds+"&ifBody="+ifBody;
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
			alert(jsonRs.rtMsg);
			notSingleMail();
			toIndex();
		}
	}
	
	function delSingleMail(){
		var mailIds = $("#mailId").val();
		var ifBody = $("#ifBody").val();
		var url = "<%=contextPath %>/mail/delMail.action?mailIds="+mailIds+"&ifBody="+ifBody;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		alert(jsonRs.rtMsg);
		toIndex();
	}
	function showRemark1(eventTag,id,name){
		$("#menuId").val(id);
		$("#menuName").val(name);
		if(id.indexOf("_")!=-1){
			showRemark2(eventTag,id);
		}else{
			var x = "divRemark1";
			if(id=="3"){
				x = "divRemark10";
			}else if(id=="1"){
				x = "divRemark11";
			}else if(id=="2"){
				x = "divRemark12";
			}
			if(document.getElementById(x).style.display !="none"){
				document.getElementById(x).style.display = 'none';
			}else{
			    var event = eventTag || window.event;
			    var top = event.clientY+65;  
			    //alert("top:"+top+",document.body.offsetHeight:"+document.body.offsetHeight);
			    if((top+80) > document.body.offsetHeight)
			        document.getElementById(x).style.top = (top + document.documentElement.scrollTop-80)+'px';
			    else   
			    document.getElementById(x).style.top = (top + document.documentElement.scrollTop)+'px';
			    var left = event.clientX-38;   
			    if((left+360) > document.body.offsetWidth)
			        document.getElementById(x).style.left = (left + document.documentElement.scrollLeft - 360)+'px';
			    else
			        document.getElementById(x).style.left = (left + document.documentElement.scrollLeft)+'px';
		
			    document.getElementById(x).style.display = '';
			}
		}
	}
	function noShowRemarkT(n){
		for(var i = 0;i<14;i++){
			if(parseInt(n,10)!=i){
				document.getElementById("divRemark"+i).style.display = 'none';
			}
		}
	}
	function noShowRemarkA(){
		for(var i = 0;i<14;i++){
			document.getElementById("divRemark"+i).style.display = 'none';
		}
	}
	function noShowRemarkS(n){
		document.getElementById("divRemark"+n).style.display = 'none';
	}
	
	function showRemark2(eventTag,id){
		if(document.getElementById("divRemark2").style.display !="none"){
			document.getElementById("divRemark2").style.display = 'none';
		}else{
		    var event = eventTag || window.event;
		    var top = event.clientY+65;  
		    if((top+80) > document.body.offsetHeight)
		        document.getElementById("divRemark2").style.top = (top + document.documentElement.scrollTop-80)+'px';
		    else   
		    document.getElementById("divRemark2").style.top = (top + document.documentElement.scrollTop)+'px';
		    var left = event.clientX-38;   
		    if((left+360) > document.body.offsetWidth)
		        document.getElementById("divRemark2").style.left = (left + document.documentElement.scrollLeft - 360)+'px';
		    else
		        document.getElementById("divRemark2").style.left = (left + document.documentElement.scrollLeft)+'px';
	
		    document.getElementById("divRemark2").style.display = '';
		}
	}
	
	function showRemark3(eventTag){
		if(document.getElementById("divRemark3").style.display !="none"){
			document.getElementById("divRemark3").style.display = 'none';
		}else{
		    var event = eventTag || window.event;
		    var top = 40;  
	        document.getElementById("divRemark3").style.top = top+'px';
		    var left = 10;   
	        document.getElementById("divRemark3").style.left = left+'px';
		    document.getElementById("divRemark3").style.display = '';
		}
	}

	function noShowRemark1(){
		//alert("out");
	    document.getElementById("divRemark1").style.display = 'none';
	}
	function noShowRemark2(){
		//alert("out");
	    document.getElementById("divRemark2").style.display = 'none';
	}
	
	function noShowRemark3(){
		//alert("out");
	    document.getElementById("divRemark3").style.display = 'none';
	}
	function noShowRemark10(){
		//alert("out");
	    document.getElementById("divRemark10").style.display = 'none';
	}
	function markMail(){

		var id = $("#menuId").val();
		//alert(id);
		var url = "<%=contextPath %>/mail/markMail.action?id="+id;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		//noShowRemark1();
		//noShowRemark2();
		//window.frames["body"].location.reload();
		//window.frames["menu"].location.reload();
		toIndex();
	}
	function clearMail(){
		var id = $("#menuId").val();
		var url = "<%=contextPath %>/mail/clearMail.action?id="+id;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs.rtMsg);
		//noShowRemark1();
		//noShowRemark2();
		toIndex();
	}
	function destroyMail(){
		if(window.confirm('你确定销毁所有已删除邮件吗？')){ 
			var id = $("#menuId").val();
			var url = "<%=contextPath %>/mail/destroyMail.action?id="+id;
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
			//alert(jsonRs.rtMsg);
			//noShowRemark1();
			//noShowRemark2();
			//noShowRemark10();
			//window.frames["body"].location.reload();
			//window.frames["menu"].location.reload();
			toIndex();
		}
	}
	function renameBox(){
		var id = $("#menuId").val();
		var name = $("#menuName").val();
		var boxId = id.split("_")[1];
		var index = document.getElementById('menu').contentWindow.document;
		var inputId = "input_"+boxId;
		index.getElementById(id).innerHTML = "<input class = 'form-control' type='text' id=\""+inputId+"\" value=\""+name+"\">";
		menu.renameBox(inputId);
		noShowRemark1();
		noShowRemark2();
	}
	function deleteBox(){
		var id = $("#menuId").val();
		id = id.split("_")[1];
		if(window.confirm("删除分类箱将同时删除分类箱中的邮件，是否继续？")){
			var url = "<%=contextPath %>/mail/deleteBox.action?id="+id;
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
			alert(jsonRs.rtMsg);
			//noShowRemark1();
			//noShowRemark2();
			//window.frames["body"].location.reload();
			//window.frames["menu"].location.reload();
			toIndex();
		}
	}
	function selectMail(selectType){
		var lookAll = $("#lookAll").val();
		var order = $("#order").val();
		var date = $("#date").val();
		var orderRule = $("#orderRule").val();
		//alert(selectType);
		var index = document.getElementById('menu').contentWindow.document;
		var value = index.getElementById("selectInput").value;
		if(value=="搜索电子邮件"||value==""){
			value = null;
		}
		value = encodeURIComponent(value);
		var url = "";
		//alert(selectType);
		var type = $(window.frames["menu"].document).find("#type").val();
		var ifBox = $(window.frames["menu"].document).find("#ifBox").val();
		url = encodeURI("<%=contextPath %>/mail/body/"+selectType+"/"+value+"/"+type+"/"+ifBox+"/"+lookAll+"/"+order+"/"+date+"/"+orderRule+".action");
		body.location = url;
		notSingleMail();
		//$("#menuType").val("select");
		$("#selectType").val(selectType);
		noShowRemark3();
	}
	
	function toIndex(){
		var url = "<%=contextPath%>/mail/mailIndex.action?type=0";
		window.location = url;
	}
	function showRemark4(eventTag){
		
		id = "divRemark4";
		if(document.getElementById(id).style.display !="none"){
			document.getElementById(id).style.display = 'none';
		}else{
			noShowRemarkA();
		    var event = eventTag || window.event;
		    var top = 40;
		    document.getElementById(id).style.top = top + 'px';//(top + document.documentElement.scrollTop)+'px';
		    var right = 0;   
	        document.getElementById(id).style.right = right+'px';
		    document.getElementById(id).style.display = '';
		}
		
	}

	function noShowRemark4(){
		//alert("out");
	    document.getElementById("divRemark4").style.display = 'none';
	}
	function changeColor(value){
		var color = value.replace("#","");
		var url = "<%=contextPath %>/mail/setMailColor.action?color="+color;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		$("#indexHeader").css("backgroundColor",value);
		$("#indexHeader").css("borderColor",value);
		noShowRemark4();
	}
	function moreMenu(checkType){
		var mailId = $("#mailId").val();
		var type = $("#type").val();
		var ifBox = $("#ifBox").val();
		url = "<%=contextPath %>/mail/moreMenu.action?mailId="+mailId+"&type="+type+"&ifBox="+ifBox+"&checkType="+checkType;
		window.location = url;
		
	}
	function moveToReceive(){
		var mailId = $("#mailId").val();
		var url = "<%=contextPath %>/mail/moveToReceive.action?mailId="+mailId;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
	    alert(jsonRs.rtMsg);
	    noShowRemarkSingle();
	    document.frames["menu"].location.reload();
	    
	}
	function moveBox(boxId){
		var mailId = $("#mailId").val();
		var url = "<%=contextPath %>/mail/moveMailBox.action?mailId="+mailId+"&boxId="+boxId;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
	    document.frames["menu"].location.reload();
	    noShowRemark5();
	}
	function showRemarkSingle(eventTag){
		var type =$("#type").val();
		var id = "divRemark6";
		if(type=="0"){
			id = "divRemark6";
			noShowRemarkT(6);
		}else if(type=="1"){
			id = "divRemark7";
			noShowRemarkT(7);
		}else if(type=="2"){
			id = "divRemark8";
			noShowRemarkT(8);
		}else{
			id = "divRemark9";
			noShowRemarkT(9);
		}
		if(document.getElementById(id).style.display !="none"){
			document.getElementById(id).style.display = 'none';
		}else{
		    var event = eventTag || window.event;
		    var top = 45;
	        document.getElementById(id).style.top = top+'px';
		    var left = event.clientX-38;   
		    if((left+360) > document.body.offsetWidth)
		        document.getElementById(id).style.left = (left + document.documentElement.scrollLeft - 360)+'px';
		    else
		        document.getElementById(id).style.left = (left + document.documentElement.scrollLeft)+'px';
	
		    document.getElementById(id).style.display = '';
		}
	}
	function noShowRemarkSingle(){
		//alert("out");
	    document.getElementById("divRemark6").style.display = 'none';
	    document.getElementById("divRemark7").style.display = 'none';
	    document.getElementById("divRemark8").style.display = 'none';
	    document.getElementById("divRemark9").style.display = 'none';
	}
	function showRemark5(eventTag){
		noShowRemarkT(5);
		if(document.getElementById("divRemark5").style.display !="none"){
			document.getElementById("divRemark5").style.display = 'none';
		}else{
		    var event = eventTag || window.event;
		    var top = event.clientY+105;  
		    if((top+80) > document.body.offsetHeight)
		        document.getElementById("divRemark5").style.top = (top + document.documentElement.scrollTop-80)+'px';
		    else   
		        document.getElementById("divRemark5").style.top = (top + document.documentElement.scrollTop)+'px';
		    var left = event.clientX-38;   
		    if((left+360) > document.body.offsetWidth)
		        document.getElementById("divRemark5").style.left = (left + document.documentElement.scrollLeft - 360)+'px';
		    else
		        document.getElementById("divRemark5").style.left = (left + document.documentElement.scrollLeft)+'px';
	
		    document.getElementById("divRemark5").style.display = '';
		}
	}
	function noShowRemark5(){
		//alert("out");
	    document.getElementById("divRemark5").style.display = 'none';
	}
	function setOrderRule(){
		for(var i = 0;i < 3;i++){
			for(var j = 0;j<2;j++){
				$("#order"+i+"_"+j).hide();
			}
		}
		$("#order"+$("#order").val()+"_"+$("#orderRule").val()).show();
	}
	function hideOldDiv(){
		noShowRemark1();
		noShowRemark2();
		noShowRemark10();
		noShowRemark4();
		noShowRemark3();
	}
	
	//清空草稿箱、已发送邮件
	function clearMailT(value){
		var text = "草稿清空后将彻底删除，是否继续？";
		if(value==2){
			text = "已发送邮件清空后将无法找回，是否继续？";
		}
		if(window.confirm(text)){
			var url = "<%=contextPath %>/mail/clearMailT.action?value="+value;
			var para =  {};
			var jsonRs = tools.requestJsonRs(url,para);
			//noShowRemarkT(11);
			//noShowRemarkT(12);
			alert(jsonRs.rtMsg);
			toIndex();
		}
	}
	
	function delSingleMailBody(value){
		var id = $("#mailId").val();
		var text = "草稿删除后将彻底删除，是否继续？";
		if(value==2){
			text = "已发送邮件删除后将无法找回，是否继续？";
		}
		if(window.confirm(text)){
			var url = "<%=contextPath %>/mail/delSingleMailBody.action";
			var para =  {id:id,value:value};
			var jsonRs = tools.requestJsonRs(url,para);
			alert(jsonRs.rtMsg);
			toIndex();
		}
	}
	
	function delSingleMailBodys(value){//删除多个草稿、已发送
		var id = body.getMailIdsByBody();
		var text = "草稿删除后将彻底删除，是否继续？";
		if(value==2){
			text = "已发送邮件删除后将无法找回，是否继续？";
		}
		if(checkNull()){
			if(window.confirm(text)){
				var url = "<%=contextPath %>/mail/delSingleMailBody.action";
				var para =  {id:id,value:value};
				var jsonRs = tools.requestJsonRs(url,para);
				alert(jsonRs.rtMsg);
				toIndex();
			}
		}
	}
	</script>
	<style type="text/css">
	body {
		font-family: "Microsoft Yahei",Verdana,Simsun,"Segoe UI","Segoe UI Web Regular","Segoe UI Symbol","Helvetica Neue","BBAlpha Sans","S60 Sans",Arial,sans-serif;
	}
	.dropdown{
		font-family: "Microsoft Yahei",Verdana,Simsun,"Segoe UI","Segoe UI Web Regular","Segoe UI Symbol","Helvetica Neue","BBAlpha Sans","S60 Sans",Arial,sans-serif;
		font-size: 12px;
	}
	.north{
	    height:40px;
	    background-color: #0072C6;
		border-color: #0072C6;
	}
	.west{
	    width:205px;
	}
	.center{
	
	}
	#sub-tabs{
	    background:#0077be;
	    overflow-y:hidden;
	}
	.navbar {
		min-height: 40px;
		height: 40px;
	}
	.container {
		width: 100%;
		height: 40px;
	}
	.bs-docs-nav {
		text-shadow: 0 -1px 0 rgba(0,0,0,.15);
	    background-color: #0072C6;
		border-color: #0072C6;
		box-shadow: 0 1px 0 rgba(255,255,255,.1);
	}
	.navbar-header {
		width: 180px;
		height: 40px;
		float: left;
		
	}
	.navbar-header :hover{

	}
	.navbar-inverse .navbar-brand {
		color: #fff;
	}
	.navbar-brand {
		padding: 10px 15px;
		
	}
	.navbar-nav>li ,active{
		float: left;
		height: 40px;
	}
	.nav>li>a {
		padding: 10px 15px;
	}
	.bs-docs-nav .navbar-nav > .active > a, .bs-docs-nav .navbar-nav :hover {
		 color: #ffffff;
	  	 background-color: #428bca;
	     border-color: #357ebd;
	}
	.bs-sidebar .nav > .active > a, .bs-sidebar .nav > .active:hover > a, .bs-sidebar .nav > .active:focus > a {
		font-weight: bold;
		color: #563d7c;
		background-color: transparent;
		border-right: 1px solid #563d7c;
	}
	.nav-pills>li+li {
		margin-left: 0px;
		margin-top: 0px;
		-webkit-border-radius: 0;
	    -moz-border-radius: 0;
	    border-radius: 0;
	}
	.nav>li {
		-webkit-border-radius: 0;
	    -moz-border-radius: 0;
	    border-radius: 0;
	}
	.bs-docs-nav .navbar-nav > li > a {
		color: #fff;
	}
	</style>
</head>
<body onload="doInit()" style="overflow:hidden;">
<input type="hidden" id="menuType" name = "menuType" value="0">
<input type="hidden" id="type" name = "type" value="0">
<input type="hidden" id="ifBox" name = "ifBox" value="0">
<input type="hidden" id="lookAll" name = "lookAll" value="0">
<input type="hidden" id="order" name = "order" value="0">
<input type="hidden" id="date" name = "date" value="0">
<input type="hidden" id="menuId" name = "menuId" value="0">
<input type="hidden" id="menuName" name = "menuName" value="0">
<input type="hidden" id="selectType" name = "selectType" value="0">
<input type="hidden" id="color" name = "color" value="0">
<input type="hidden" id="ifBody" name = "ifBody">
<input type="hidden" id="select" name = "select">
<input type="hidden" id="mailId" name = "mailId">
<input type="hidden" id="orderRule" name = "orderRule" value="1">
<div id="layout" style="">
	<div layout="north" height="40" class="north" id="indexHeader">
		<header  class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner" style="height: 40px;">
		  <div class="container">
		    <div class="navbar-header">
		      <a href="javascript:toIndex();" class="navbar-brand"><font color="white"><i class="glyphicon glyphicon-envelope"></i> TeeneMail</font></a>
		    </div>
		    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
		      <ul class="nav navbar-nav">
		        <li>
		           <a href="javascript:createMail();"><font color="white"><i class="glyphicon glyphicon-plus-sign"></i> 新建</font></a>
		        </li>
		      </ul>
   				  <ul class="nav navbar-nav" id="delDivCaogao" style="display:none;">
			        <li>
			           <a href="javascript:delSingleMailBodys(1);"><font color="white"><i class="glyphicon glyphicon-remove"></i> 删除</font></a>
			        </li>
			      </ul>
			      
			      <ul class="nav navbar-nav" id="delDivSend" style="display:none;">
			        <li>
			           <a href="javascript:delSingleMailBodys(2);"><font color="white"><i class="glyphicon glyphicon-remove"></i> 删除</font></a>
			        </li>
			      </ul>
  		      <div id="readDiv">
			       <ul class="nav navbar-nav" id="delDiv">
			        <li>
			           <a href="javascript:delMail();"><font color="white"><i class="glyphicon glyphicon-remove"></i> 删除</font></a>
			        </li>
			      </ul>


			      
				  <ul class="nav navbar-nav" id="destroy" style="display:none;">
			        <li >
			           <a href="javascript:void(0);" onclick="destroyMails();"><font color="white"><i class="glyphicon glyphicon-trash"></i> 销毁</font></a>
			        </li>
			      </ul>  
			      
			      <c:if test="${type!=1&&type!=2}">
			       <ul class="nav navbar-nav">
			        <li >
			           <a href="javascript:void(0);" id="moveIcon" onclick="showRemark(event,0);"><font color="white"><i class="glyphicon glyphicon-circle-arrow-right"></i> 移至</font></a>
			        </li>
			      </ul>  
			      </c:if>
			      
			      <ul class="nav navbar-nav" id="moveToReceiveList" style="display:;">
			        <li >
			           <a href="javascript:void(0);" onclick="showRemark(event,13);"><font color="white"><i class="glyphicon glyphicon-chevron-down"></i> 更多</font></a>
			        </li>
			      </ul>  
		      </div>
		      <div id="singleDiv" style="display:none">
			       <ul class="nav navbar-nav" id="deleteSingle">
			        <li>
			           <a href="javascript:delSingleMail();"><font color="white"><i class="glyphicon glyphicon-remove"></i> 删除</font></a>
			        </li>
			      </ul>
   			       <ul class="nav navbar-nav" id="deleteSingleCaogao">
			        <li>
			           <a href="javascript:delSingleMailBody(1);"><font color="white"><i class="glyphicon glyphicon-remove"></i> 删除</font></a>
			        </li>
			      </ul>
   			       <ul class="nav navbar-nav" id="deleteSingleSend">
			        <li>
			           <a href="javascript:delSingleMailBody(2);"><font color="white"><i class="glyphicon glyphicon-remove"></i> 删除</font></a>
			        </li>
			      </ul>
			      <ul class="nav navbar-nav" id="destroySingle" style="display:none;">
			        <li >
			           <a href="javascript:void(0);" onclick="destroySingleMail();"><font color="white"><i class="glyphicon glyphicon-trash"></i> 销毁</font></a>
			        </li>
			      </ul>  
			       <ul class="nav navbar-nav" id="moveSingle">
			        <li >
			           <a href="javascript:void(0);" onclick="showRemark5(event);"><font color="white"><i class="glyphicon glyphicon-circle-arrow-right"></i> 移至</font></a>
			        </li>
			      </ul>
     		       <ul class="nav navbar-nav" id="moreSingle">
			        <li>
			           <a href="javascript:void(0);" onclick="showRemarkSingle(event);"><font color="white"><i class="glyphicon glyphicon-chevron-down"></i> 更多</font></a>
			        </li>
			      </ul>
		       </div>
		            <div  id="divRemark0" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:100px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				        <c:forEach items="${boxList}" var="boxSort" varStatus="boxStatus">
			  	  	  		<a id="box_${boxList[boxStatus.index].sid}" href='javascript:void(0);' onclick='moveMail(${boxList[boxStatus.index].sid});' style='text-align:center;color:${color};' ><h6>${boxList[boxStatus.index].boxName}</h6></a>
				  		</c:forEach>
				    </div>
   		            <div  id="divRemark13" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:30px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
						<a id="move6" href='javascript:void(0);' onclick='moveReceive(0);' style='text-align:center;color:${color};' ><h6>移至收信箱</h6></a>
				    </div>
				     <div  id="divRemark4" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 5px 5px 5px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
						<table>
							<tr>
								<td>
									<div onclick = "changeColor('#DC4FAD')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #DC4FAD;cursor: pointer;" title="粉红色"></div>
								</td>	
								<td>
									<div onclick = "changeColor('#AC193D')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #AC193D;cursor: pointer;" title="深红色"></div>
								</td>	
								<td>
									<div onclick = "changeColor('#D24726')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #D24726;cursor: pointer;" title="深橙色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#FF8F32')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #FF8F32;cursor: pointer;" title="橙色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#82BA00')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #82BA00;cursor: pointer;" title="浅绿色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#008A17')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #008A17;cursor: pointer;" title="绿色"></div>
								</td>
							</tr>
							<tr>
								<td>
									<div onclick = "changeColor('#03B3B2')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #03B3B2;cursor: pointer;" title="浅青色"></div>
								</td>	
								<td>
									<div onclick = "changeColor('#008299')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #008299;cursor: pointer;" title="青色"></div>
								</td>	
								<td>
									<div onclick = "changeColor('#5DB2FF')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #5DB2FF;cursor: pointer;" title="浅蓝色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#0072C6')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #0072C6;cursor: pointer;" title="蓝色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#4617B4')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #4617B4;cursor: pointer;" title="深紫色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#8C0095')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #8C0095;cursor: pointer;" title="紫色"></div>
								</td>
							</tr>
							<tr>
								<td>
									<div onclick = "changeColor('#004B8B')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #004B8B;cursor: pointer;" title="中度深蓝"></div>
								</td>	
								<td>
									<div onclick = "changeColor('#001940')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #001940;cursor: pointer;" title="深蓝"></div>
								</td>	
								<td>
									<div onclick = "changeColor('#570000')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #570000;cursor: pointer;" title="棕色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#380000')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #380000;cursor: pointer;" title="深棕色"></div>
								</td>
								<td>
									<div onclick = "changeColor('#585858')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #585858;cursor: pointer;" title="中灰"></div>
								</td>
								<td>
									<div onclick = "changeColor('#000000')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #000000;cursor: pointer;" title="黑色"></div>
								</td>
							</tr>
						</table>
					</div>
		      		 <div  id="divRemark5" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:100px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				        <c:forEach items="${boxList}" var="boxSort1" varStatus="boxStatus1">
			  	  	  		<a id="box_single_${boxList[boxStatus1.index].sid}" href='javascript:void(0);' onclick='moveBox(${boxList[boxStatus1.index].sid});' style='text-align:center;color:${color};' ><h6>${boxList[boxStatus1.index].boxName}</h6></a>
				  		</c:forEach>
				    </div>
	    		    <div  id="divRemark6" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
						<a id="markMenu6" href='javascript:void(0);' onclick='moreMenu(0);' style='text-align:center;color:${color};' ><h6>回复发件人</h6></a>
						<a id="clearMenu6" href='javascript:void(0);' onclick='moreMenu(1);' style='text-align:center;color:${color};' ><h6>回复所有人</h6></a>
						<a id="clearMenu6" href='javascript:void(0);' onclick='moreMenu(2);' style='text-align:center;color:${color};' ><h6>转发邮件</h6></a>
					</div>
	    		    <div  id="divRemark7" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
						<a id="markMenu7" href='javascript:void(0);' onclick='moreMenu(3);' style='text-align:center;color:${color};' ><h6>继续编辑</h6></a>
					</div>
	    		    <div  id="divRemark8" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
						<a id="markMenu8" href='javascript:void(0);' onclick='moreMenu(4);' style='text-align:center;color:${color};' ><h6>再次发送</h6></a>
					</div>
	    		    <div  id="divRemark9" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
						<a id="markMenu9" href='javascript:void(0);' onclick='moveToReceive();' style='text-align:center;color:${color};' ><h6>移至收信箱</h6></a>
					</div>
		      <ul class="nav navbar-nav navbar-right" >
		        <li>
		          <a href="javascript:void(0);" id="colorIcon" onclick="showRemark4(event);"><font color="white"><i class="glyphicon glyphicon-cog"></i>肤色</font></a>
		        </li>
		      </ul>
		    </nav>
		  </div>
		</header>
	</div>  
	<div layout="west" width="195" style="border-right:">
		    <div  id="divRemark1" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				<a id="markMenu1" href='javascript:void(0);' onclick='markMail();' style='text-align:center;color:${color};' ><h6>全部标记已读</h6></a>
				<a id="clearMenu1" href='javascript:void(0);' onclick='clearMail();' style='text-align:center;color:${color};' ><h6>清空邮件</h6></a>
			</div>
			<div  id="divRemark10" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				<a id="markMenu10" href='javascript:void(0);' onclick='markMail();' style='text-align:center;color:${color};' ><h6>全部标记已读</h6></a>
				<a id="clearMenu10" href='javascript:void(0);' onclick='destroyMail();' style='text-align:center;color:${color};' ><h6>销毁已删除邮件</h6></a>
			</div>
			<div  id="divRemark11" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				<a id="clearMenu11" href='javascript:void(0);' onclick='clearMailT(1);' style='text-align:center;color:${color};' ><h6>清空草稿箱</h6></a>
			</div>
			<div  id="divRemark12" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				<a id="clearMenu12" href='javascript:void(0);' onclick='clearMailT(2);' style='text-align:center;color:${color};' ><h6>清空已发送邮件</h6></a>
			</div>
		    <div  id="divRemark2" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:100px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				<a id="markMenu2" href='javascript:void(0);' onclick='markMail();' style='text-align:center;color:${color};' ><h6>全部标记已读</h6></a>
				<a id="clearMenu2" href='javascript:void(0);' onclick='clearMail();' style='text-align:center;color:${color};' ><h6>清空邮件</h6></a>
				<a id="renameMenu2" href='javascript:void(0);' onclick='renameBox();' style='text-align:center;color:${color};' ><h6>重命名</h6></a>
				<a id="deleteMenu2" href='javascript:void(0);' onclick='deleteBox();' style='text-align:center;color:${color};' ><h6>删除</h6></a>
			</div>
		    <div  id="divRemark3" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 15px 0 15px; min-height:100px;min-width:150px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
				<a id="mailContent" href='javascript:void(0);' onclick='selectMail(0);' style='text-align:center;color:${color};' ><h5>邮件内容</h5></a>
				<a id="mailSubject" href='javascript:void(0);' onclick='selectMail(1);' style='text-align:center;color:${color};' ><h5>邮件主题</h5></a>
				<a id="fromUser" href='javascript:void(0);' onclick='selectMail(2);' style='text-align:center;color:${color};' ><h5>发件人</h5></a>
			</div>
		<iframe src="<%=contextPath %>/mail/menu.action" id="menu" frameborder=0 style="width:100%;height:100%; "></iframe>
	</div>
	<div id="center_panel" layout="center"  border="false" style="overflow-y:hidden;">
		<div id="sub-tabs" layout="north" height="45px;" style="background-color: transparent;min-width: 919px;">
			 <table style="width:100%;">
				 <tr>
					 <td>
						 <div class="dropdown" style="padding-left:10px;padding-right:20px;padding-top:10px;">
						 <label>
			      			 <input id="checkBoxAll" type="checkbox" onclick="checkAll();"> 
			    		 </label> 
							  查看：
						     <a id="dLabel" role="button" data-toggle="dropdown" data-target="#" style="cursor:pointer;">
						    	  所有 <span class="caret"></span>
						     </a>
							 <ul id="query" class="dropdown-menu" role="menu" aria-labelledby="dLabel" style="font-size:10px;border-radius: 0px;position:fixed; _position:absolute;_top:expression(eval(document.documentElement.scrollTop));z-index:1200;left:200px;top:72px; ">
							     <li role="presentation"><a href="javascript:void(0);" onclick="changeLookAll(0,'所有');" tabindex="-1" role="menuitem">所有</a></li>
					             <li role="presentation"><a href="javascript:void(0);" onclick="changeLookAll(1,'未读');" tabindex="-1" role="menuitem">未读</a></li>
							 </ul>
						 </div>
					 </td>
					 <td style="float:right;">
						 <div class="dropdown" style="padding-left:20px;padding-right:20px;padding-top:10px;">
						 	 <a id="dLabel1" role="button" data-toggle="dropdown" data-target="#" style="cursor:pointer;float:right;">
						    	 排序方式 <span class="caret"></span>
						     </a>
							 <ul id="order" class="dropdown-menu" role="menu" aria-labelledby="dLabel" style="font-size:10px;border-radius: 0px;position:fixed; _position:absolute;_top:expression(eval(document.documentElement.scrollTop));z-index:1200;right:0px;left:auto;top:72px; ">
							     <li role="presentation">
								     <a href="javascript:void(0);" onclick="changeOrder(0,'日期');" tabindex="-1" role="menuitem">
								     	&nbsp;日期&nbsp;<span id="order0_0" style="display:none;">&nbsp;↑&nbsp;</span><span id="order0_1" style="display:none;">&nbsp;↓&nbsp;</span>
								     </a>
							     </li>
							     <li role="presentation">
								     <a href="javascript:void(0);" onclick="changeOrder(1,'发件人');" tabindex="-1" role="menuitem">
								     	&nbsp;发件人&nbsp;<span id="order1_0" style="display:none;">&nbsp;↑&nbsp;</span><span id="order1_1" style="display:none;">&nbsp;↓&nbsp;</span>
								     </a>
							     </li>
							     <li role="presentation">
								     <a href="javascript:void(0);" onclick="changeOrder(2,'主题');" tabindex="-1" role="menuitem">
								     	&nbsp;主题&nbsp;<span id="order2_0" style="display:none;">&nbsp;↑&nbsp;</span><span id="order2_1" style="display:none;">&nbsp;↓&nbsp;</span>
								     </a>
							     </li>
					             <!-- <li role="presentation"><a href="javascript:void(0);" onclick="changeOrder(3,'大小');" tabindex="-1" role="menuitem">大小</a></li>
					             <li role="presentation"><a href="javascript:void(0);" onclick="changeOrder(4,'会话');" tabindex="-1" role="menuitem">会话</a></li> -->
							 </ul>
						 </div>
					 </td>
				 </tr>
			 </table>
			<hr style="margin-top: 10px;margin-bottom: 0px;width:98%;">
        </div>
		<div id="tabs-content" layout="center" style="padding-left:10px;padding-top:0px;padding-right:-10px;min-width: 919px;">
			<iframe src="<%=contextPath %>/mail/body/0/null/0/0/0/0/0/1.action" id="body" frameborder=0 style="width:100%;height:100%"></iframe>
		</div>	
	</div>
</div>
</body>

</html>
