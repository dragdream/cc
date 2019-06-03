<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/system/core/mail/loading/loading.jsp" %>
	<%@include file="/header/upload.jsp" %>
	<title>mail</title>
	<link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/system/core/mail/style/skin1/pagination.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"/>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="UTF-8">
		function doInit(){
			$("[title]").tooltips();
			var sid = "${mailModel.sid}";
			var ifBody = "${mailModel.ifBody}";
			var url = "<%=contextPath%>/mail/getAttachModel.action?sid="+sid+"&ifBody="+ifBody;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				var attaches = json.rtData;
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#attachments").append(fileItem);
				}
			}else{
				alert(json.rtMsg);
			}
		}
		function returnTo(){
			parent.window.history.go(-1);
			parent.window.notSingleMail();
			parent.window.noShowRemarkA();
		}
	</script>
	<style type="text/css">

	</style>
</head>
<body  style="overflow-x:hidden;" onload="doInit()">
<form role="form" id="form1" name="form1">
<input id="select" name="select" type="hidden" value="${select}">
<input id="mailId" name="mailId" type="hidden" value="${mailModel.sid }">
<input id="ifBody" name="ifBody" type="hidden" value="${mailModel.ifBody }">
	<table style="width:100%;">
		<tr>
			<td>
				<h3 style="font-size: 24px;">${mailModel.subject}</h3>
			</td>
			<td style="float: right;">
			    <div style="height:100%;width:100%;"><h4>
					<!-- <li class="glyphicon glyphicon-arrow-up"><a href="javascript:void(0);" onclick=""></a></li> -->
					<!-- <li class="glyphicon glyphicon-arrow-down"><a href="javascript:void(0);" onclick=""></a></li> -->
					<li class="glyphicon glyphicon-remove" style="cursor: pointer;width:15px;height:15px;" title="返回" onclick="returnTo();"></li>
				</h4></div>
			</td>
		</tr>
	</table>
	<hr style="width:100%;margin-top: 5px;margin-bottom: 0px;">
	<table style="width:100%;">
		<tr>
			<td style="width:6%;"><img style="height:60px;width:60px;" alt="照片" src="<%=contextPath %>/system/core/mail/style/images/photo.png"></td>
			<td style="width:94%;float:left;">
				<font size="2px"><strong>
					<c:choose>  
						<c:when test="${mailModel.ifWebMail == 0}"> 
							${mailModel.fromId.userName}
						</c:when>
						<c:otherwise> 
							${mailModel.fromWebMail}
						</c:otherwise>
					</c:choose>
				</strong></font>
				<c:if test="${!empty mailModel.mailAttachMent}">&nbsp;
					<li class="glyphicon glyphicon-paperclip"></li>&nbsp;
				</c:if>
				<font size="2px">${fn:substring(mailModel.sendTime, 0, 19)}</font>
				<h6>
					<c:if test="${!empty mailModel.users}">
						收件人：
						<c:choose>
							<c:when test="${mailModel.ifWebMail == 0}">
								<c:forEach items="${mailModel.users}" var="usersSort" varStatus="usersStatus">
									${mailModel.users[usersStatus.index][1]} 
									<c:choose>
										<c:when test="${mailModel.users[usersStatus.index][2] eq '0'}">
											<li title="未读" class="glyphicon glyphicon-eye-close"></li>
										</c:when>
										<c:otherwise>
											<li title="已读" style= "color: ${color};"  class="glyphicon glyphicon-eye-open"></li>
										</c:otherwise>
									</c:choose>
									，
								</c:forEach>
							</c:when>
							<c:otherwise>
								${mailModel.toWebMail} 
							</c:otherwise>
						</c:choose>

					</c:if><br>
						<c:if test="${!empty mailModel.copyUsers}">
						抄送人：
						<c:forEach items="${mailModel.copyUsers}" var="copyUsersSort" varStatus="copyUsersStatus">
							${mailModel.copyUsers[copyUsersStatus.index][1]} 
							<c:choose>
								<c:when test="${mailModel.copyUsers[copyUsersStatus.index][2] eq '0'}">
									<li title="未读" class="glyphicon glyphicon-eye-close"></li>
								</c:when>
								<c:otherwise>
									<li title="已读" style= "color: ${color};"  class="glyphicon glyphicon-eye-open"></li>
								</c:otherwise>
							</c:choose>
							，
						</c:forEach>
					</c:if>
				</h6>
			</td>
		</tr>
	</table>
	<c:if test="${!empty mailModel.mailAttachMentModel}">&nbsp;
		<hr style="width:100%;margin-top: 0px;margin-bottom: 2px;">
		<table style="width:100%;">
			<tr>
				<td><li class="glyphicon glyphicon-paperclip"></li>&nbsp; | &nbsp;${fn:length(mailModel.mailAttachMent)}个附件</td>
			</tr>
			<tr>
				<td>
					<div id='attachments'></div>
				</td>
			</tr>
		</table>
	</c:if>
	<hr style="width:100%;margin-top: 2px;margin-bottom: 0px;">
	<table style="width:100%;">
		<tr>
			<td>${mailModel.content }</td>
		</tr>
	</table>
</form>
</body>

</html>
