<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
int rootFolderPriv = TeeStringUtil.getInteger(request.getParameter("rootFolderPriv"), 0);
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.returnButton{
        padding:5px 8px;
		/* padding-left:22px; */
		text-align:center; 
		/* text-align:right; 
		background-repeat:no-repeat;
		background-position:6px center; */
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
}


/* .TableBlock_page{
   border:2px solid #f2f2f2;
} */
</style>
<script type="text/javascript">
var sid = <%=sid%>;
function doInit(){
	if(sid > 0){
		signReadFunc(sid);
		getInfoById(sid);
	}
<%-- 	//取消星
	  $("body").on("click",".picStarActive",function(){
		  var url =   "<%=contextPath%>/fileNetdisk/deletePicCount.action";
		  var para = {sid : sid};
		  tools.requestJsonRs(url, para);
		  window.location.reload();
	 });
	//添加星
	  $("body").on("click",".picStar",function(){
		  var url =   "<%=contextPath%>/fileNetdisk/addPicCount.action";
		  var para = {sid : sid};
		  tools.requestJsonRs(url, para);
		  window.location.reload();
	 }); --%>
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/fileNetdisk/getFileNetdiskById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var signReadDesc = "<font color='red'>未签阅</font>";
			if(prc.isSignRead =='1'){
				signReadDesc = "<font color=' #007500 '>已签阅</font>";
			}
			$("#signReadDesc").html(signReadDesc);
			var priv = '<%=rootFolderPriv%>';
			
			if((priv & 4) == 4){//去掉删除浮动菜单
		    	  priv-=4;
			} 
			var attaches = prc.attacheModels;
			if(attaches){
				attaches.priv = priv;
				var fileItem = tools.getAttachElement(attaches);
				$("#fileContainer").append(fileItem);
			}
			countPic(prc.picCount);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
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
/**
 * 签阅
 * @param sid
 */
function signReadFunc(sid){
  var url = contextPath + "/TeeFileNetdiskReaderController/addOrUpdate.action?fileNetdiskId=" + sid;
  var jsonRs = tools.requestJsonRs(url);
  if(jsonRs.rtState){
	  
  }else{
	  $.MsgBox.Alert_auto(jsonRs.rtMsg);
  }
}



function toReturn(){ 
	history.go(-1);
	//window.location.href = "<%=contextPath%>/system/core/base/fileNetdisk/fileManage/fileManage.jsp?sid=<%=folderSid%>";
}
</script>
</head>
<body onload="doInit();" style="padding-right: 10px">
 <!--   <div id="toolbar" class="topbar clearfix">
       <div class="fl" style="position:static;">
		  <span style="font-size: 14px;font-weight: bold;">查看文件详情</span>
	   </div>
	   <div class = "right fr clearfix ">
	      <button type="button"  value="返回" class="returnButton" onclick="toReturn();">返回</button>
       </div>
   </div>
   <br> -->
   <form action=""  method="post" name="form1" id="form1">
   <input type="hidden" name="sid" id="sid" value="<%=sid%>">
   <table class="TableBlock_page" width="60%" align="center">
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">文件：</td>
		<td class="TableData" width="75%;">
			<div id="fileContainer"></div> 
		</td>
	</tr>
	<br/>
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">知识评分：</td>
		<td class="TableData" width="75%;">
		    <div id="countPic"> </div>
		</td>
	</tr> 
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">文件备注内容：</td>
		 <td class="TableData" width="75%">	   
			<div id="content"></div>
		</td>
	</tr>
	
	</table>
</form>
</body>
</html>