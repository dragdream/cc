<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
      int fileId=TeeStringUtil.getInteger(request.getParameter("sid"),0);
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/header/header2.0.jsp" %>
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
			html+="<div style='background-color: #eae5e5;margin-left: 10%;width: 80%;overflow: hidden;margin-top:20px;'>";
			   html+="<div style='float:left;margin:6px'>";
			   if(row.avatar>0){
				  html+="<img src='<%=contextPath %>/attachmentController/downFile.action?id="+row.avatar+"' alt='' style='width:50px;height:50px;'>";
			   }else{
			      html+="<img src='img/replyAvatarDemo.png' alt='' style='width:50px;height:50px;'>";
			   }
			   html+="</div>";
			   html+="<div>";
			      html+="<p style='margin-top: 6px;'>"+row.userName+"</p><br/>";
			      html+="<p >"+row.createTime+"</p><br/>";
			      html+="<p style='margin-left: 60px;margin-bottom: 11px;margin-right: 20px;'>"+row.content+"</p>";
			   html+="</div>";
			html+="</div>";
			
		}
		if(rows.length==json.total){
		    html+="<div style='text-align: center; margin-top: 10px;margin-bottom: 20px;'><span>没有更多了</span></div>";
		}else{
		    html+="<div style='text-align: center; margin-top: 10px;margin-bottom: 20px;' onclick='getMore();'><span>查看更多>>></span></div>";
		}
		$(".commentList").html(html);
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
 <div style="text-align: center;">
   <textarea id="content" style="width: 80%;height: 60px;margin-top: 2%;"></textarea>
   <input type="button" onclick="commit();" value="提交" style="position: absolute;top: 90px;right: 10%;margin: auto;height: 36px;width: 90px;"/>
 </div>
 <div style="width:85%;margin-left:7.5%;background-color: #bbb9b9;margin-top: 56px;height: 2px;"></div>

 <div class="commentList">
	
	
</div>
</body>
</html>