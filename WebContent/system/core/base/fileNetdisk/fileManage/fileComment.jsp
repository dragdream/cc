<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
int fileId=TeeStringUtil.getInteger(request.getParameter("sid"),0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8" />
	<%@ include file="/header/header2.0.jsp"%>
    <%@ include file="/header/easyui2.0.jsp"%>
    <%@ include file="/header/validator2.0.jsp"%>
    <%@ include file="/header/upload.jsp" %>
    
	<title>附件评论</title>
	<link rel="stylesheet" href="dist/css/blog.css">
	<link rel="stylesheet" href="dist/css/emoji.css">
	<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/api.js"></script>
	
	<style type="text/css">
		html{
			height:auto;
			background-color: #dbdbdb;
		}
		body{
			height:auto;
			max-height: fit-content;
		}
	</style>
	<script type="text/javascript">
var fileId=<%=fileId%>;
var page=1;
var pageSize=3;
function doinit(){
	query();
}
function query(){
	var url=contextPath+"/teeFileCommentController/getFileComment.action";
	var json = tools.requestJsonRs(url,{sid:fileId,page:page,rows:pageSize});
	var rows=json.rows;
	if(rows!=null){
		var html="";
		for(var i=0;i<rows.length;i++){
			var row=rows[i];
			html+="<div class='blogItem'>";
			  html+="<div class='blogHead'>";
			    html+="<span class='avatar'>";
			    if(row.avatar>0){
					  html+="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+row.avatar+"' alt='' style='width:50px;height:50px;'>";
				   }else{
			          html+="<img src='dist/images/blog.png' alt='#'>";
				   }
			    html+="</span>";
				html+="<div class='userFromInfo'>";
				   html+="<p class='blogUserName'>"+row.userName+"</p>";
				   html+="<p class='blogTime'>"+row.createTime+"</p>";
				html+="</div>";
			  html+="</div>";
			  html+="<div class='blogBody'>";		
				html+="<div class='blogText' style='padding: 1px'>";
				   html+="<p class='myword'>"+row.content+"</p>";
			    html+="</div>";
			  html+="</div>";
			  
			html+="</div>";
		}
		if(rows.length==json.total){
		    html+="<div style='text-align: center; margin-top: 10px;margin-bottom: 20px;cursor:pointer;'><span>没有更多了</span></div>";
		}else{
		    html+="<div style='text-align: center; margin-top: 10px;margin-bottom: 20px;cursor:pointer;' onclick='getMore();'><span>查看更多>>></span></div>";
		}
		$(".blogList").html(html);
	}
}
function getMore(){
	pageSize=pageSize+3;
	query();
}
//添加评论
function commit(){
	if(check()){
		var content=$("#content").val();
		var url=contextPath+"/teeFileCommentController/addFileComment.action";
		var json = tools.requestJsonRs(url,{sid:fileId,content:content});
		if(json.rtState){
			alert(json.rtMsg);
			window.location.reload();
		}
	}
}
function check(){
	var content=$("#content").val();
	if(content==null || content==""){
		alert("评论内容不能为空");
		return false;
	}
	return true;
}
</script>
</head>
<body onload="doinit();">

	<div class="main">
	
		<div class="mainContent">
			<div class="addNewMsg">
				<div class="title">评论</div>
				<div class="newWordWrap">
					<textarea name="content" id="content" cols="30" rows="10"></textarea>
				</div>
				<div class="otherWrap">
					<input type="button"  onclick="commit();" class="btn" value="评论">
				</div>
			</div>
			<div class="blogList">
			
				
			</div>

			</div>
			
		</div>
	</div>
	<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script>
	$(function(){
		$(".allDepart .name").click(function(event) {
			$(".allDepartWrap").toggleClass('hide');
			event.stopPropagation();
			event.cancelBubble = true;
		});
		$(".searchDep").click(function(event) {
			$(".searchDepInput").toggleClass('hide');
			$(".searchResult").toggleClass('hide');
		});
		//点击其他位置隐藏
		$("body").click(function(event) {
			$(".allDepartWrap").addClass('hide');
		});
		$(".allDepartWrap").click(function(event) {
			event.stopPropagation();
			event.cancelBubble = true;
		});
		$(".share").click(function(event) {
			$(".bodyWrap").removeClass('hide');
		});
		$(".repost .close").click(function(event) {
			$(".bodyWrap").addClass('hide');
		});
	});
</script>
</body>
</html>