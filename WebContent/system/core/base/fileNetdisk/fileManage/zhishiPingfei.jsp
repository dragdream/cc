<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int picCount=TeeStringUtil.getInteger(request.getParameter("picCount"),0);
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识评分</title>
<script type="text/javascript">
var starCount=<%=picCount%>;
var sid=<%=sid%>;
function doInit(){
	countPic(starCount);
	//取消星
	  $("body").on("click",".picStarActive",function(){
		  starCount=starCount-1;
		  countPic(starCount);
	 });
	//添加星
	  $("body").on("click",".picStar",function(){
		  starCount=starCount+1;
		  countPic(starCount);
	 });
	
	  $("body").on("click","#adviseFile",function(){
		  if($(this).val()=="在此输入相关意见和建议"){
			  $(this).val("");
		  }
		  
	 });
}


function doCutSubmit(){
	var adviseFile=$("#adviseFile").val();
    var url = "<%=contextPath %>/fileNetdisk/updatePicCount.action";
    var jsonRs = tools.requestJsonRs(url,{adviseFile:adviseFile,sid:sid,picCount:starCount});
    return jsonRs.rtState;
}
function check(){
	var adviseFile=$("#adviseFile").val();
	if(adviseFile==null || adviseFile==""){
		alert("请输入意见和建议");
		return false;
	}
	return true;
}
//判断有几颗星
function countPic(starCount){
	var html="";
	if(starCount==5){
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>";
	}else if(starCount==4){
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>";
	}else if(starCount==3){
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>";
	}else if(starCount==2){
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>";
	}else if(starCount==1){
		html+="<img src='img/star_active.png' class='picStarActive'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>";
	}else{
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>&nbsp;";
		html+="<img src='img/star.png' class='picStar'>";
	}
   $("#countPic").html(html);
}

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
 <br/>
 <div id="countPic" style="margin-top:5px;"></div>
 <textarea id="adviseFile" style="width: 260px;height: 100px;margin-top:5px;">在此输入相关意见和建议</textarea>
</body>
</html>