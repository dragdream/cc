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
	var i = 0;
	 function checkEmail(adderss){
         //对电子邮件的验证
	      if (adderss.search(/^([a-zA-Z0-9_-]+[_|_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9_-]+[_|_|.]?)*[a-zA-Z0-9_-]+\.(?:com|cn)$/)!= -1){
	          return true;   
          }
         return false;
     }
	 function showMailInput(){
		 $("#webMail").show();
	 }
	function doInit(){
		$("[title]").tooltips();
		$(document).ready(function(){      
		    $("#webMail").focusin(function() {
		        if($(this).val() =="请输入邮件地址...回车进行保存"){  
		            $(this).val("");
		        }
		    });
		    $("#webMail").focusout(function() {
		        if($(this).val() ==""){ 
		            $(this).val("请输入邮件地址...回车进行保存");
		        }
		    });
		});
		$("#collapseTwo").addClass("panel-collapse collapse in");
		$("#webMail").blur(function (){ 
			$("#webMail").hide();
		});
		$("#webMail").bind('keypress',function(event){
            if(event.keyCode == "13"){
            	var inputValue = $("#externalInput").val();
            	var value = $("#webMail").val();
            	if(value.indexOf(";")>=0){
            		alert("邮箱地址不能包含分号");
            		return ;
            	}
            	if(inputValue.indexOf(value)<0){
            		var count = $('#webmailCount', parent.document).val(); 
            		//alert(count);
            		if(count != "0"){
            			i = count;
            		}
                	i++;
                	$("#webmailCount").val(i);
                	var id = "div_"+i;
                	var aid = "a_"+i;
                	var color = "${color}";
                	var title = "有效邮箱地址，点击可进行修改";
                	if(!checkEmail(value)){
                		color = "red";
                		title = "无效邮件地址，点击可进行修改";
                	}
                	$("#webDiv").append("<div id='"+id+"' onclick='editMailAddress("+i+",\""+value+"\");' "+
                	" title='"+title+"' style='margin-top: 4px;margin-bottom: 4px;margin-left: 2px;cursor:pointer;float:left;position:relative;background-color: "+color+";height:17px;width：auto;max-width:365px;'>"+
                	" <font color='white'>"+value+"&nbsp;</font></div>"+
                	" <div style='cursor:pointer;float:left;position:relative;background-color: red;height:17px;width：auto;margin-right: 2px;margin-top: 4px;margin-bottom: 4px;' "+
                	" id='"+aid+"' onclick='deleteDiv("+i+",\""+value+"\");' title='删除'>"+
                	" <font color='white'>×</font></div>");
                	inputValue = inputValue + value + ";";
                	$("#externalInput").val(inputValue);
                	$("#webMail").val("");
            	}else{
            		alert("外部邮箱地址重复！");
            	}
            	
            }
        });
	}
	
	function editMailAddress(count,valueSingle){
		$("#a_"+count).click(function (e){ 
			e.stopPropagation();
		});
		$("#div_"+count).html("<input id='editInput_"+count+"' title='回车进行保存' name='editInput_"+count+"' value=\""+valueSingle+"\">");
		$("#a_"+count).remove();
		$("#editInput_"+count).click(function (e){ 
			e.stopPropagation();
		});

		$("#editInput_"+count).bind('keypress',function(event){
            if(event.keyCode == "13"){
            	var inputValue = $("#externalInput").val();
            	inputValue = inputValue.replace(valueSingle+";","");
            	var value = $("#editInput_"+count).val();
            	if(value.indexOf(";")>=0){
            		alert("邮箱地址不能包含分号");
            		return ;
            	}
            	if(inputValue.indexOf(value)<0){
                	var id = "div_"+count;
                	var aid = "a_"+count;
                	var color = "${color}";
                	var title = "有效邮箱地址，点击可进行修改";
                	if(!checkEmail(value)){
                		color = "red";
                		title = "无效邮件地址，点击可进行修改";
                	}
                	$("#div_"+count).remove();
                	$("#webDiv").append("<div id='"+id+"' onclick='editMailAddress("+count+",\""+value+"\");' "+
                	" title='"+title+"' style='margin-top: 4px;margin-bottom: 4px;margin-left: 2px;cursor:pointer;float:left;position:relative;background-color: "+color+";height:17px;width：auto;max-width:365px;'> "+
                	" <font color='white'>"+value+"&nbsp;</font></div>"+
                	" <div style='cursor:pointer;float:left;position:relative;background-color: red;height:17px;width：auto;margin-right: 2px;margin-top: 4px;margin-bottom: 4px;' "+
                	" id='"+aid+"' onclick='deleteDiv("+count+",\""+value+"\");' title='删除'> "+
                	" <font color='white'>×</font></div>");
                	inputValue = inputValue + value + ";";
                	$("#externalInput").val(inputValue);
                	$("#webMail").val("");
            	}else{
            		alert("外部邮箱地址重复！");
            	}
            }
		});
	}
	
	function deleteDiv(id,value){
		//alert(value);
		var divId = "div_"+id;
		var aId = "a_"+id;
		$("#"+divId).remove();
		$("#"+aId).remove();
		var inputValue = $("#externalInput").val();
		inputValue = inputValue.replace(value+";","");
		$("#externalInput").val(inputValue);
	}
	function showVariousMail(id){
		if(id==1){
			$("#copyHeader").hide();
			$("#copyMail").show();
		}else if(id==2){
			$("#secretHeader").hide();
			$("#secretMail").show();
		}else{
			$("#externalHeader").hide();
			$("#externalMail").show();
		}
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
<script type="text/javascript" charset="UTF-8">
	/**
	 * 设置外部邮箱
	 * @param 
	 */
	function setWebMail(){
		var url = contextPath + "/mail/setWebMailIndex.action";
		
	 	top.bsWindow(url ,"设置外部邮箱",{width:"600",height:"320",buttons:
			[
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
			
			}else if(v=="关闭"){
				return true;
			}
		}}); 
	}

</script>

<body>
<input id="webmailCount" name="webmailCount" type="hidden">
  <div style="background-color:#f5f5f5;">	
	 <div class="teemenutop" style="height: 40px;background-color:#f5f5f5;padding-left: 10px;">
		 <h5 style="height: 40px;">${person.userName}<br>
		 <span id="loginType">${webMail.loginType}</span>  <small>&nbsp;<a href="javascript:setWebMail();">设置外部发信箱</a></small></h5>
	 </div>
	 <div class="tab-content" style="padding-left: 10px;">
	  	<table style="width:95%;">
	  		<tr>
	  			<td><h6><a href="javascript:void(0);" onClick="selectUser(['userListIds', 'userListNames'])">内部联系人</a>&nbsp;<a href="javascript:void(0);" class="orgClear" onClick="clearData('userListIds', 'userListNames')">清空</a></h5></td>
	  			<td style="float: right;"><h6><a href="javascript:void(0);" id="copyHeader" onclick="showVariousMail(1);">抄送</a>&nbsp;&nbsp;<a href="javascript:void(0);" id="secretHeader" onclick="showVariousMail(2);">密送</a>&nbsp;&nbsp;<a href="javascript:void(0);" id="externalHeader" onclick="showVariousMail(3);">外部邮箱</a><h6></td>
	  		</tr>
	  		<tr>
	  			<td colspan="2">
	  				<input type="hidden" name="userListIds" id="userListIds" required="true" value=""> 
	  				<textarea cols="58" name="userListNames" id="userListNames" rows="4" style="overflow-y: auto;"  class="SmallStatic BigTextarea" wrap="yes" readonly ></textarea>
	  				<!-- 
	  				<div contentEditable=false style="width: 375px;height:100px;background-color: white;overflow-y: scroll;">
	  					<li class="btn btn-primary" style="padding: 5px;margin: 2.5px;">联系人<a class="glyphicon glyphicon-remove" style="float: right;color: white;padding-left: 5px;"></a></li>
  					</div>
  					-->
	  			</td>
	  		</tr>
	  		<tbody id="copyMail" style="display: none;">
	  			<tr>
	  				<td><h6><a href="javascript:void(0);" onClick="selectUser(['copyUserListIds', 'copyUserListNames'])">抄送</a>&nbsp;<a href="javascript:void(0);" class="orgClear" onClick="clearData('copyUserListIds', 'copyUserListNames')">清空</a></h5></td>
	  			</tr>
		  		<tr>
		  			<td colspan="2">
		  				<input type="hidden" name="copyUserListIds" id="copyUserListIds" required="true" value=""> 
	  					<textarea cols="58" name="copyUserListNames" id="copyUserListNames" rows="4" style="overflow-y: auto;"  class="SmallStatic BigTextarea" wrap="yes" readonly ></textarea>
		  			</td>
		  		</tr>
	  		</tbody>
	  		<tbody id="secretMail"  style="display: none;">
	  			<tr>
	  				<td><h6><a href="javascript:void(0);" onClick="selectUser(['secretUserListIds', 'secretUserListNames'])"> 密送</a>&nbsp;<a href="javascript:void(0);" class="orgClear" onClick="clearData('secretUserListIds', 'secretUserListNames')">清空</a></h5></td>
	  			</tr>
		  		<tr>
		  			<td colspan="2">
		  				<input type="hidden" name="secretUserListIds" id="secretUserListIds" required="true" value=""> 
	  					<textarea cols="58" name="secretUserListNames" id="secretUserListNames" rows="4" style="overflow-y: auto;"  class="SmallStatic BigTextarea" wrap="yes" readonly ></textarea>
		  			</td>
		  		</tr>
	  		</tbody>
	  		<tbody id="externalMail"  style="display: none;">
	  			<tr>
	  				<td><h6><a href="javascript:showMailInput();">添加外部邮箱</a></h5></td>
	  			</tr>
		  		<tr>
		  			<td colspan="2"><div id="webDiv" style="overflow-x:hidden;width:380px;height:100px;background-color: white;border:1px solid black;"><input type="text" size="55%"  placeholder="请输入邮件地址...回车进行保存" value="请输入邮件地址...回车进行保存" id="webMail" name="webMail" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;"></div></td>
		  		</tr>
		  			<input id = "externalInput" name="externalInput" type="hidden">
	  		</tbody>
	  	</table>
     </div>
 </div>
</body>
</html>
