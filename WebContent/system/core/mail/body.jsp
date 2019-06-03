<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/system/core/mail/loading/loading.jsp" %>
	<title>mail</title>
	<link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/system/core/mail/style/skin1/pagination.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"/>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="UTF-8">
		function getHexBackgroundColor(id) { 
			var rgb = $(id).css('background-color'); 
			if(!$.browser.msie){ 
				rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/); 
				function hex(x) { 
					return ("0" + parseInt(x).toString(16)).slice(-2); 
				} 
				rgb= "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]); 
			} 
			return rgb; 
		}
		function doInit(){
			$("[title]").tooltips();
			$(":checkbox").click(function(e){
				checkAbout();
				var tableId = $(this).val().split("x")[0];
				if($(this).val().indexOf("_")!=-1){
					//$('#checkBoxAll', parent.document).attr("checked")!="checked"
			        if($(this).attr("checked")=="checked"){
			        	$("#"+$(this).val().split("_")[0].split("x")[0]+"_"+$(this).val().split("_")[1]+"x"+$(this).val().split("_")[0].split("x")[1]).css("background","${color}"); 
			        	$("#table_"+tableId).addClass("contentTable");
			        	$("#font_"+tableId).addClass("contentTable");
			        }else{
			        	$("#"+$(this).val().split("_")[0].split("x")[0]+"_"+$(this).val().split("_")[1]+"x"+$(this).val().split("_")[0].split("x")[1]).css("background",""); 
			        	$("#table_"+tableId).removeClass("contentTable");
			        	$("#font_"+tableId).removeClass("contentTable");
			        }
				}else{		
					//alert($(this).val());
					if($(this).attr("checked")=="checked"){
			        	$("#"+$(this).val()).css("background","${color}"); //f5f5f5
			        	$("#table_"+tableId).addClass("contentTable");
			        	$("#font_"+tableId).addClass("contentTable");
			        }else{
			        	$("#"+$(this).val()).css("background",""); 
			        	$("#table_"+tableId).removeClass("contentTable");
			        	$("#font_"+tableId).removeClass("contentTable");
			        }
				}
				e.stopPropagation();
			});
			jQuery('.form-group').not($("#checkInput")).bind('click', function(){
				var type = parent.window.menuType();
				//alert(this.id);
				var select = type;
				//alert(select);
				if(this.id.indexOf("_")!=-1){
					parent.window.readMailBody(this.id.split("x")[0].split("_")[0],"box_"+this.id.split("x")[0].split("_")[1],this.id.split("x")[0].split("_")[1],this.id.split("x")[1],select);
				}else{
					parent.window.readMailBody(this.id.split("x")[0],type,0,this.id.split("x")[1],select);
				}
			});
			$("#toDate").val(parent.window.getDate());
		}
		function checkAll(){
		    var value='';
			var ifBody = "0";
			if($('#checkBoxAll', parent.document).attr("checked")!="checked"){
				$("input[name='checkValue']").attr("checked",false);  
				
				$(":checkbox").each(function(){
					var tableId = $(this).val().split("x")[0];
					//alert($(this).val());
					if($(this).val().indexOf("_")!=-1){		   
			        	$("#table_"+tableId).removeClass("contentTable");
			        	$("#font_"+tableId).removeClass("contentTable");
			        	$("#"+$(this).val().split("_")[0].split("x")[0]+"_"+$(this).val().split("_")[1]+"x"+$(this).val().split("_")[0].split("x")[1]).css("background",""); 		
			    	}else{
			    		$("#table_"+tableId).removeClass("contentTable");
			        	$("#font_"+tableId).removeClass("contentTable");

			        	$("#"+$(this).val()).css("background",""); 
			    	}
				});
				value = "";
				ifBody = "";
			}else{
				$("input[name='checkValue']").attr("checked",true);  
				$(":checkbox").each(function(){
					var tableId = $(this).val().split("x")[0];
					//alert($(this).val());
					if($(this).val().indexOf("_")!=-1){		   
				        value+=$(this).val().split("_")[0].split("x")[0]+',';
				        ifBody = $(this).val().split("_")[0].split("x")[1];
			        	$("#"+$(this).val().split("_")[0].split("x")[0]+"_"+$(this).val().split("_")[1]+"x"+$(this).val().split("_")[0].split("x")[1]).css("background","${color}"); 		
			        	$("#table_"+tableId).addClass("contentTable");
			        	$("#font_"+tableId).addClass("contentTable");
					}else{
			        	$("#table_"+tableId).addClass("contentTable");
			        	$("#font_"+tableId).addClass("contentTable");
			    		value+=$(this).val().split("x")[0]+',';
			    		ifBody = $(this).val().split("x")[1];
			        	$("#"+$(this).val()).css("background","${color}"); 
			    	}
				});

			}
			//alert(value);
			$("#ifBody").val(ifBody);
			$("#checkValues").val(value);
		}
		function setDate(date){
			parent.window.changeDate(date);
		}
		function checkAbout(){  //jquery获取复选框值
		    var value='';
			var ifBody = "0";
		    $('input[name="checkValue"]:checked').each(function(){
		    	if($(this).val().indexOf("_")!=-1){
			        value+=$(this).val().split("_")[0].split("x")[0]+',';
			        ifBody = $(this).val().split("_")[0].split("x")[1];
			        /*
			        if($("#"+$(this).val().split("_")[0].split("=")[0]+"_"+$(this).val().split("_")[1]).attr("background")!="f5f5f5"){
			        	$("#"+$(this).val().split("_")[0].split("=")[0]+"_"+$(this).val().split("_")[1]).css("background","#f5f5f5"); 
			        }else{
			        	$("#"+$(this).val().split("_")[0].split("=")[0]+"_"+$(this).val().split("_")[1]).css("background",""); 
			        }
			        */
		    	}else{
		    		value+=$(this).val().split("x")[0]+',';
		    		ifBody = $(this).val().split("x")[1];
		    		/*
		    		if($("#"+$(this).val().split("=")[0]).attr("background")!="f5f5f5"){
			        	$("#"+$(this).val().split("=")[0]).css("background","#f5f5f5"); 
			        }else{
			        	$("#"+$(this).val().split("=")[0]).css("background",""); 
			        }
		    		*/
		    	}
		    });
			$("#ifBody").val(ifBody);
			$("#checkValues").val(value);
		}
		function getMailIdsByBody(){
			return $("#checkValues").val();
		}
		function getIfBodyByBody(){
			return $("#ifBody").val();
		}
		function showContentDiv(){
			alert("显示contentDiv");
			//$("#contentDiv").show();
			document.getElementById("contentDiv").style.display = "";
		}
		function hideContentDiv(){
			alert("隐藏contentDiv");
			//$("#contentDiv").hide();
			document.getElementById("contentDiv").style.display = "none";
		}
		function showListDiv(){
			alert("显示listDiv");
			//$("#listDiv").show();
			document.getElementById("listDiv").style.display = "";
		}
		function hideListDiv(){
			alert("隐藏listDiv");
			document.getElementById("listDiv").style.display = "none";
			//$("#listDiv").hide();
		}

	</script>
	<style type="text/css">
		tr {
			height : 30px;
		}
		.form-group{
			height:30px;
			margin-bottom: 2px;
			margin-top: 2px;
			line-height:30px;
		}
		.form-group:hover{
			cursor:pointer;
		}
		.contentTable{
			color: white;
		}
	</style>
</head>
<body  style="overflow-x:hidden;" onload="doInit()">
<form role="form" id="form1" name="form1">
<input id="toDate" name="toDate" type="hidden" />
<input id="checkValues" name="checkValues" type="hidden" value=""/>
<input id="ifBody" name="ifBody" type="hidden" value="0"/>
<div id="listDiv" style="display:;">
	<div>
	<c:choose>
	<c:when test="${page.totalCount != 0}">
	<div style="border-color: transparent;min-height: 500px;">
		<c:forEach items="${page.result }" var="page" varStatus="pageStatus">
			<c:choose>  
				<c:when test="${!empty page.mailBox}"> 
					<div class="form-group" id="${page.sid}_${page.mailBox.sid}x${page.ifBody}" title="${page.subject }">
				</c:when>
				<c:otherwise> 
					<div class="form-group" id="${page.sid}x${page.ifBody}" title="${page.subject }">
				</c:otherwise>
			</c:choose>
			
   				<table style="width:100%;" id="table_${page.sid}">
   					<tr>
						<c:choose>  
							<c:when test="${page.readFlag == 0}"> 
								<c:choose>  
									<c:when test="${!empty page.mailBox}"> 
										<td style="width:1%" id="checkInput"><input type="checkbox" name = "checkValue" value="${page.sid}x${page.ifBody}_${page.mailBox.sid}"></td>
									</c:when>
									<c:otherwise> 
										<td style="width:1%" id="checkInput"><input type="checkbox" name = "checkValue" value="${page.sid}x${page.ifBody}"></td>
									</c:otherwise>
								</c:choose>
								
								<td style="width:30%"><strong><font size="2px">
								<c:choose>  
									<c:when test="${page.ifWebMail == 0}"> 
										${page.fromId.userName}
									</c:when>
									<c:otherwise> 
										${fn:substring(page.fromWebMail,0,30)}
									</c:otherwise>
								</c:choose>
								</font></strong></td>
   								<td style="width:40%"><strong><font color="${color}" id="font_${page.sid}" size="2px">${fn:substring(page.subject,0,28)}
   								<c:if test="${!empty page.mailAttachMent}">
   									&nbsp;<li class="glyphicon glyphicon-paperclip" title="有附件"></li>
   								</c:if>
   								</font></strong></td>
   								<td style="width:15%;"><font size="2px">${fn:substring(page.sendTime, 0, 19)}</font></td>
							</c:when>
							<c:otherwise> 
								<c:choose>  
									<c:when test="${!empty page.mailBox}"> 
										<td style="width:1%" id="checkInput"><input type="checkbox" name = "checkValue" value="${page.sid}x${page.ifBody}_${page.mailBox.sid}"></td>
									</c:when>
									<c:otherwise> 
										<td style="width:1%" id="checkInput"><input type="checkbox" name = "checkValue" value="${page.sid}x${page.ifBody}"></td>
									</c:otherwise>
								</c:choose>
								<td style="width:30%"><font size="2px">
								<c:choose>  
									<c:when test="${page.ifWebMail == 0}"> 
										${page.fromId.userName}
									</c:when>
									<c:otherwise> 
										${fn:substring(page.fromWebMail,0,30)}
									</c:otherwise>
								</c:choose>
								</font></td>
   								<td style="width:40%"><font size="2px">${fn:substring(page.subject,0,28)}
   									<c:if test="${!empty page.mailAttachMent}">
   										&nbsp;<li class="glyphicon glyphicon-paperclip" title="有附件"></li>
   									</c:if>
   								</font></td>
   								<td style="width:15%;"><font size="2px">${fn:substring(page.sendTime, 0, 19)}</font></td>
							</c:otherwise>
						</c:choose>
   					</tr>
   				</table>
 			</div>
		</c:forEach>
	</div>
	</c:when>
	<c:otherwise> 
		<div style="border-color: transparent;min-height: 500px;">
			<div style="text-align: center;margin-top: 100px;"><font size="4px" color="${color }">无相关邮件</font></div>
		</div>
	</c:otherwise>
	</c:choose>
	<hr style="margin-top: 10px;margin-bottom: 0px;border-top: 1px solid #EEE9E9;">
		<div style="height:30px;">
			<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
		</div>
	<hr style="margin-top: 0px;margin-bottom: 20px;border-top: 1px solid #EEE9E9;">
</div>

</form>
</body>

</html>
