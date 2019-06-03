<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//获取Id
%>
<!DOCTYPE HTML>
<html>
<head>
<title>日志详情</title>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/js/util.js"></script>

<style type="text/css" media="all">
</style>

<script type="text/javascript">
/**
 * 页面加载是即可
 */
var sid = <%=sid%>;

//手机屏幕宽度
var width;
function doInit(){
	document.getElementById('data_info').style.left = '0';
	//loadData(0);//加载数据
	var param = {sid:sid};
	//获取所有已发布的新闻
	var url = "<%=contextPath%>/mobileDiaryController/getDiaryById.action";
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: param,
	  timeout: 6000,
	  success: function(data){
		//获取手机屏幕的宽度
		  width=parseInt(window.innerWidth);
		  
		 // alert(data)
		  var dataJson = eval('(' + data + ')');
      	  var rtData = dataJson.rtData;
      	  var rtMsg = dataJson.rtMsg;
      	  var rtState = dataJson.rtState;
		  if(rtState){
			  $("#subject").append(rtData.title);
			  $("#creater").append(rtData.createUserName);
			  var writeTime = getFormatDateStr(rtData.writeTime , 'yyyy-MM-dd');//日志时间
				
			  $("#publish_time").append(writeTime);
			  var createTime = getFormatDateStr(rtData.createTime , 'yyyy-MM-dd HH:mm:ss');//创建时间
			  $("#last_update_time").append(createTime);
			  $("#content").append(rtData.content);
			  var diaryType = rtData.type;
			  var diaryTypeDesc = "个人日志";
			  if(diaryType == '2'){
				  diaryTypeDesc = "工作日志";
			  }
			  $("#diaryType").append(diaryTypeDesc);
			  var  attachments = rtData.attacheModels;//附件
			  if(attachments.length > 0){
				  $("#attathList").show();
				  $.each(attachments, function(index, item){  
					 // $("#attathList").append("<div ><a href='<%=contextPath%>/attachmentController/downFile.action?id="+ item.sid + "' target='black'  item_id="+item.sid+">"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
					  $("#attathList").append("<div ><a href='javascript:void(0);'  onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
				});
			
			  }
			  //clickCount
			  //改变图片的宽度  使图片自适应手机的宽度
			  $("#content img").each(function(i,obj){
				  obj.onload=function(){		
					  if(this.width > width){
						  this.width = width-40; 	  
					  }
					  
				  }
			  });
		  }else{
			  
		  } 
		  loadData(1);//加载数据
	  },
	  error: function(xhr, type){
	    alert('服务器请求超时!');
	  }
	});
	
}

function toAddOrUpdateDiary(sid){
	window.location.href = mobilePath + "/phone/diary/addOrUpdate.jsp?sid=" + sid;
}
</script>


</head>
<body onload="doInit();">


<div id="data_info" style="background-color:#fff;">
	<div class="read_title_info" id="subject" style="padding-top:20px;"></div>
	
	<div class="content_info" id="diaryType">日志类型：</div>
	<div class="content_info" id="creater">创建人：</div>
	<div class="content_info" id="publish_time">日志时间：</div>
	
	<div class="content_info" id="last_update_time">最后修改时间：</div>
	<div class="content_info"  style="border:0px;padding-top:10px;position: relative;" id="content"></div>
	
	
	<div class="content_attath_list"  style="margin-top:10px;display:none;" id="attathList"></div>
</div>

</body>
</html>