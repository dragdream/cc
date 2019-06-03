<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/header/header.jsp"%>

	<link href="<%=cssPath%>/bootstrap.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/system/core/mail/style/skin1/mail.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/system/core/mail/style/skin1/heightLight.css" rel="stylesheet"/>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>	
	<script type="text/javascript" charset="UTF-8">
	//取消默认的右键菜单
	$(document).bind("contextmenu",function(e){ 
		return false; 
	});
	function doInit(){
		$("[title]").tooltips();
		$("#collapseTwo").addClass("panel-collapse collapse in");
		
	    $("a.list-group-item.active, a.list-group-item.active:hover, a.list-group-item.active:focus").css("background","${color}");
	    $("#0 .badge").css("background","white");
	    jQuery('a.list-group-item').live('click', function(){
	    	jQuery("span.badge").css("background","${color}");
	        jQuery(this).siblings().removeClass('active');
	        jQuery(this).siblings().css("background","");
	        if(jQuery(this).attr('class').indexOf('active') < 0){
	        	
	        	jQuery("#"+this.id+" .badge").css("background","white");
	            jQuery(this).addClass('active');
	            jQuery(this).css("background","${color}");
	        }else{
	        	jQuery("#"+this.id+" .badge").css("background","white");
	        }
	    });
	    
		$('#boxInput').blur(function (){ 
			saveMailBox();
		});
		$('#boxInput').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	saveMailBox();
            }
        });

		$('.list-group-item').mousedown(function(e){ 
	        if(3 == e.which){
	            parent.window.noShowRemarkA();
	        	parent.window.showRemark1(event,this.id,this.name);
	        	window.cancelBubble = true;
	        }
	    });
		$('#selectInput').click(function(e){
	        if($('#selectInput').val() =="搜索电子邮件"){  
	            $('#selectInput').val("");
	        }
       	    parent.window.showRemark3(e);
       	    window.cancelBubble = true;
	    });
		$(document).click(function(){
			parent.window.noShowRemarkT(3);
		});

	}

	function saveMailBox(){
		if($('#boxInput').val()==""){
			$("#mailBox").hide();
		}else{
			var url = "<%=contextPath %>/mail/saveOrUpdateMailBox.action";
			var para =  tools.formToJson($("#form1"));
			var jsonRs = tools.requestJsonRs(url,para);
		    window.location.reload();

		}
	}
	function changeBody(type){
		$("#type").val(type);
		$("#ifBox").val(0);
		parent.window.changeBody(type);
	}
	function changeBox(boxId){
		$("#type").val(boxId);
		$("#ifBox").val(1);
		parent.window.changeBox(boxId);
	}
	function createMailBox(){
		$("#mailBox").show();
	}
	function getTypeByMenu(){
		return $("#type").val();
	}
	function getIfBoxByMenu(){
		return $("#ifBox").val();
	}
	function renameBox(id){
		var boxId = id.split("_")[1];
		
		$("#"+id).click(function (e){ 
			$("#"+id).select();
			e.stopPropagation();
		});
		$("#"+id).blur(function (){ 
			updateBoxName(boxId);
		});
		$("#"+id).bind('keypress',function(event){
            if(event.keyCode == "13"){
            	updateBoxName(boxId);
            }
        });
	}
	function updateBoxName(id){
		var boxName = $("#input_"+id).val();
		if(boxName==""){
			alert("分类项名称不能为空！");
			$("#input_"+id).focus();
			return ;
		}
		boxName = encodeURIComponent(boxName);
		var url = encodeURI("<%=contextPath %>/mail/renameBox.action?id="+id+"&name="+boxName);
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs.rtMsg);
		window.location.reload();
	}
	</script>
	<style type="text/css">
		#mailmenu{
		    position: absolute;
		    top: 40px;
		    bottom: 0;
		    width: 100%;
		    overflow: auto;
		}
		.mailmenu {
		    font-size:14px; 
		    color:#555;
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
			background-color: #DC4FAD;
			border-color: #DC4FAD;
			box-shadow: 0 1px 0 rgba(255,255,255,.1);
		}
		.navbar-header {
			width: 180px;
			height: 40px;
			float: left;
		}
		.navbar-header :hover{
			color: #fff;
			background-color: #ddd;
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
		/*菜单选中*/

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
		.list-group-item{
			background-color:;
		}
	</style>
</head>
<body onload="doInit()" style=";overflow-x:hidden; overflow-y:auto;height: 100%;background-color:#f5f5f5">
<form role="form" id="form1" name="form1">
<input type="hidden" id="type" name = "type" value="0">
<input type="hidden" id="ifBox" name = "ifBox" value="0">
  <div  style="background-color:#f5f5f5; " >
  	
	 <div class="teemenutop" id="123123" style="height: 40px;background-color:#f5f5f5;">
	 <table>
		 <tr>
		 	 <td>
				 <div style="height: 30px;padding-top: 5px;padding-left: 10px;width:160px;">
				 	 <input class = "form-control" id="selectInput" type="text" placeholder="搜索电子邮件" value="搜索电子邮件">
			 	 </div>
		 	 </td>
	 	 	 <td>
			 	 <div style="height: 30px;padding-top: 15px;width:35px;float: right;">
			 	 	 <li class="glyphicon glyphicon-zoom-in" ></li>
			 	 </div>
		 	 </td>
		 </tr>
	 </table>
	 </div>
	
	<div class="tab-content">
	    <div  id="mailmenu" class="mailmenu"  >
	    		  <div class="panel-group" id="accordion" style="width:195px;">
	  <div class="panel panel-default">
	    <div class="panel-heading" style="border: none;padding-top: 0px;padding-bottom: 0px;">
	      <h5 class="panel-title">
	        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
	          	<h4>文件夹<font size="2px">已用${mailAttachSize}MB,共${max}MB</font></h4>
	        </a>
	      </h5>
	    </div>
	    <div id="collapseTwo">

	      <div class="panel-body" style="border:none;">
	           <div class="list-group" id="list-group">
				  <a class='list-group-item active' id="0" href='javascript:void(0);' onclick='changeBody(0);' style='text-align:center;'><span class="badge pull-right" style="margin-right: 30px;background-color:${color};">${receiveCount }</span><h6 class='list-group-item-heading' style="float">收件箱</h6></a>
			  	  <a class='list-group-item' id="1" href='javascript:void(0);' onclick='changeBody(1);' style='text-align:center;' ><span class="badge pull-right" style="margin-right: 30px;background-color:${color};">${saveCount }</span><h6 class='list-group-item-heading'>草稿</h6></a>
			  	  <a class='list-group-item' id="2" href='javascript:void(0);' onclick='changeBody(2);' style='text-align:center;' ><span class="badge pull-right" style="margin-right: 30px;background-color:${color};">${sendCount }</span><h6 class='list-group-item-heading'>已发送邮件</h6></a>
			  	  <a class='list-group-item' id="3" href='javascript:void(0);' onclick='changeBody(3);' style='text-align:center;' ><span class="badge pull-right" style="margin-right: 30px;background-color:${color};">${deleteCount }</span><h6 class='list-group-item-heading'>已删除邮件</h6></a>
			  	  <c:forEach items="${boxList}" var="boxSort" varStatus="boxStatus">
			  	  	  <a class='list-group-item' id="box_${boxList[boxStatus.index].sid}" name="${boxList[boxStatus.index].boxName}" href='javascript:void(0);' onclick='changeBox(${boxList[boxStatus.index].sid});' style='text-align:center;' ><span class="badge pull-right" style="margin-right: 30px;background-color:${color};">${boxList[boxStatus.index].mailCount}</span><h6 class='list-group-item-heading'>${boxList[boxStatus.index].boxName}</h6></a>
				  </c:forEach>
			  	  <a href='javascript:void(0);' onclick='createMailBox();' style='text-align:center;' ><h6 class='list-group-item-heading'>新建分类箱</h6></a>
			  </div>
	      </div>
	    </div>
	  </div>
	   <div id="mailBox" name = "mailBox" style="display:none;">
	   		 <table>
				 <tr>
				 	 <td>
						 <div style="height: 30px;padding-top: 5px;padding-left: 10px;width:160px;">
						 	 <input class = "form-control" id="boxInput" name="boxInput" type="text" placeholder="分类箱名称">
					 	 </div>
				 	 </td>
				 </tr>
		 	</table>
	   </div>
	  </div>
      </div>
    </div>
 </div>
 </form>
</body>
</html>
