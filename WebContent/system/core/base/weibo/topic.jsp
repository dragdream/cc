<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  String content =request.getParameter("content"); 
  int userId=loginUser.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/header/header2.0.jsp" %>
    <%@ include file="/header/easyui2.0.jsp"%>
    <%@ include file="/header/validator2.0.jsp"%>
    <%@ include file="/header/upload.jsp" %>
<title>微博管理</title>
	<link rel="stylesheet" href="dist/css/blog.css">
	<link rel="stylesheet" href="dist/css/topicDetail.css">
	<link rel="stylesheet" href="dist/css/emoji.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/zt_webframe/js/package.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/system/mobile/js/api.js"></script>
    <style type="text/css">
    html{
      background: #dbdbdb;
    }
    </style>
</head>
<script type="text/javascript">
var content='<%=content%>';
var userId=<%=userId%>;
var topicConent;
var page=1;
var pageSize=5;
var countPic=0;
function doInit(){
	topicConent=updateTopic();
	topicContent();//显示当前话题
	readTopicCount();//显示阅读数
	loginUserGzTopic();
	countTopic();//话题关注数量
	$(".titleName").html("#"+topicConent+"#");
	
	//初始化图片上传组件
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"uploadPhotoContainer",//文件列表容器
		uploadHolder:"uploadPhotoBtn",//上传按钮放置容器
		showUploadBtn:false,//不显示上传按钮
		valuesHolder:"uploadPhotoHolder",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		queueComplele:function(){//所有队列上传成功回调函数，可有可无
			
		},
		uploadSuccess:function(file){//单个文件上传成功
			suoLuePic(file.sid);
		},
		uploadStart:function(file,progress){//刚开始上传
			 countPic++;
		     if(countPic>9){
		      $("#remain-count").html(0);
		      countPic--;
			  swfUploadObj.swf.cancelUpload(file.id);//取消对指定文件的上传
		   }else{
			   $("#remain-count").html(9-countPic);
		   }
		},
		renderFiles:false,//渲染附件
		post_params:{model:"workFlow"}//后台传入值，model为模块标志
		});
	
	
	//初始化图片上传组件
	swfUploadObj2 = new TeeSWFUpload({
		fileContainer:"uploadPhotoContainer2",//文件列表容器
		uploadHolder:"uploadPhotoBtn2",//上传按钮放置容器
		showUploadBtn:false,//不显示上传按钮
		valuesHolder:"uploadPhotoHolder2",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		queueComplele:function(){//所有队列上传成功回调函数，可有可无
			
		},
		uploadSuccess:function(file){//单个文件上传成功
			suoLuePic2(file.sid);
		},
		uploadStart:function(file,progress){//刚开始上传
			 countPic++;
		     if(countPic>9){
		      $("#remain-count2").html(0);
		      countPic--;
			  swfUploadObj.swf.cancelUpload(file.id);//取消对指定文件的上传
		   }else{
			   $("#remain-count2").html(9-countPic);
		   }
		},
		renderFiles:false,//渲染附件
		post_params:{model:"workFlow"}//后台传入值，model为模块标志
		});
	//预览图片
	  $("body").on("click",".yunLanPicture",function(){
		  var strArr= $(this).prev().prev().val();
		  strArr= strArr.substring(0,strArr.length-1);
		  var picId= $(this).prev().val();
		  var url = contextPath +"/system/core/attachment/picExplore.jsp?id="+picId+"&pics="+strArr;
		  openFullWindow(url,"在线预览");
	 });
}
function suoLuePic2(sid){
	var url = "<%=contextPath%>/attachmentController/picZoom.action";
	var json = tools.requestJsonRs(url,{attachId:sid});
	var html="<li id='pic"+json.rtData+"' style='background: url(<%=contextPath %>/attachmentController/downFile.action?id="+json.rtData+") center no-repeat'>";
	html+="<input type='hidden' value='"+sid+"'/>";
	html+="<i class='iconfont' action-type='thumb-delete' onclick='deleteFilePic2("+json.rtData+")'></i>";
    html+="</li>";
    $("#zhuanFaSuoLue").prepend(html);
}
//删除缩略图
function deleteFilePic2(sid){
	var url = "<%=contextPath%>/attachmentController/deleteFile.action";
	var json = tools.requestJsonRs(url,{attachIds:sid});
	$("#pic"+sid).remove();
	countPic=countPic-1;
	$("#remain-count2").html(9-countPic);
}
//关闭图片页
function closePic(){
	$(".image-upload-wrapper").hide();
}

//缩略图
function suoLuePic(sid){
	var url = "<%=contextPath%>/attachmentController/picZoom.action";
	var json = tools.requestJsonRs(url,{attachId:sid});
	var html="<li id='pic"+json.rtData+"' style='background: url(<%=contextPath %>/attachmentController/downFile.action?id="+json.rtData+") center no-repeat'>";
	html+="<input type='hidden' value='"+sid+"'/>";
	html+="<i class='iconfont' action-type='thumb-delete' onclick='deleteFilePic("+json.rtData+")'></i>";
    html+="</li>";
    $("#faweibo").prepend(html);
}
//删除缩略图
function deleteFilePic(sid){
	var url = "<%=contextPath%>/attachmentController/deleteFile.action";
	var json = tools.requestJsonRs(url,{attachIds:sid});
	$("#pic"+sid).remove();
	countPic=countPic-1;
	$("#remain-count").html(9-countPic);
}
//阅读此话题的次数
function readTopicCount(){
	//var tContent=updateTopic();
	var url = "<%=contextPath%>/TeeWeibPublishController/readTopicCount.action";
	var json = tools.requestJsonRs(url,{content:topicConent});
	var data=json.rtData;
	if(data!=null){
		$("#readCount").html(data);
	}
}
//话题
function updateTopic(){
	var img=content.indexOf("<img alt");
	var tContent="";
	if(img<0){
		tContent=content;
	}else{
		var split=content.split("<img alt");
		for(var i=0;i<split.length;i++){
			var emo=split[i].indexOf("dist/arclist/emo");
			if(emo<0){
				tContent+=split[i];
			}else{
				var emo2=split[i].indexOf(".png");
				var emo3=split[i].substring(emo+13,emo2);
				tContent+="["+emo3+"]";
			}
		}
		//tContent="#"+tContent+"#";
	}
	return tContent;
}
//参与话题讨论
function topicContent(){
	//var tContent=updateTopic();
	$("#commentInput").val("#"+topicConent+"#");
	//tContent=tContent.substring(1,tContent.length-1);
	var url = "<%=contextPath%>/TeeWeibPublishController/findTopicAll.action";
	var json = tools.requestJsonRs(url,{content:topicConent,page:page,pageSize:pageSize});
	var data=json.rows;
	var count=json.total;
	if(data!=null){
		$(".topicTimes").html("当前已讨论"+count+"次");
		var html="";
		for(var i=0;i<data.length;i++){
			html+="<div class='topicItem'>";
			    html+="<div class='avatar'>";
			       if(data[i].avatar>0){
				        html+="<img style='width:30px;height:30px;' src='<%=contextPath %>/attachmentController/downFile.action?id="+data[i].avatar+"' alt=''>";
			       }else{
			            html+="<img src='dist/images/replyAvatarDemo.png' alt=''>";
			       }
			    html+="</div>";
			    html+="<div class='nameAndContent'>";
			        html+="<p class='name'>"+data[i].userName+"</p>";
			        html+="<p class='topicTime'>"+data[i].createTime+"</p>";
			        html+="<p class='topicContent'>"+data[i].content+"</p>";
			    html+="</div>";
			    
				//上传图片的位置
				if(data[i].img!=null && data[i].img!=""){
					var str=data[i].img;
					var imgy=data[i].imgy;
					html+="<div class='attachImg' style='margin-left: 40px;'>";
					var imgArr=str.split(",");
					var imgyArr=imgy.split(",");
					for(var k=0;k<imgArr.length-1;k++){
					   html+="<div class='imgBlock'>";
					        html+="<input type='hidden' value='"+data[i].imgy+"'/>";
				            html+="<input type='hidden' value='"+imgyArr[k]+"'/>";
					        html+="<img class='yunLanPicture' src='<%=contextPath %>/attachmentController/downFile.action?id="+imgArr[k]+"' alt=''>";
					    html+="</div>";
					} 
				 	html+="</div>";
				}
			    
			    html+="<div class='topicInfo'>";
			      /*   html+="<span class='topicTime'>"+data[i].createTime+"</span>"; */
			        html+="<p class='topicOper'>";
			            html+="<span class='spanBlock' onclick='share("+data[i].sid+")'>转发</span>";
			            if(userId==data[i].userId){
			              html+="|<span class='spanBlock' onclick='deletePublish("+data[i].sid+")'>删除</span>";
			            }
			        html+="</p>";
		        html+="</div>";
			    html+="<div class='topicDivid'></div>";
			html+="</div>";
		}
		$(".topicList").html(html);
	}
	$("a").click(function () {
	      var content=$(this).html();
	      content=content.substring(1,content.length-1);
	      open("topic.jsp?content="+content); 
	 });
	if(count==data.length){
		$("#findGgetMore").hide();
		$("#getMoreNot").show();
	}else{
		$("#findGgetMore").show();
		$("#getMoreNot").hide();
	}
}
//点击更多
function findGetMore(){
	pageSize+=5;
	topicContent();
}
//删除参与话题讨论的微博信息
function deletePublish(sid){
	    $.MsgBox.Confirm("提示","是否确定删除？",function(){
		var url = "<%=contextPath%>/TeeWeibPublishController/deletePublish.action";
		var json = tools.requestJsonRs(url,{content:topicConent,sid:sid});
		if(json.rtState){
			topicContent();
			$.MsgBox.Alert_auto(json.rtMsg);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	    return true;
});
}
	
//发布信息
function addPublish(){
	  var children=$("#faweibo").children();
	    var picIdStr="";
	    var picIdStry="";
	    if(children.length>1){
	    	for(var i=0;i<children.length-1;i++){
	    		var picId=$(children[i]).attr("id");
	    		picId=picId.substring(3,picId.length);
	    		picIdStr+=picId+",";
	    		picIdStry+=$(children[i]).children(":first").val()+",";
	    	}
	    }
	var commentInput=$("#commentInput").val();
	if(check()){
		while(commentInput.indexOf('\n') >= 0){
			commentInput = commentInput.replace('\n','<br/>');
		}
		var url = "<%=contextPath%>/TeeWeibPublishController/addPublish.action";
		var json = tools.requestJsonRs(url,{content:commentInput,img:picIdStr,imgy:picIdStry});
		location.reload();
	}
}
//验证
function check(){
	var commentInput=$("#commentInput").val();
	if(commentInput==null || commentInput==""){
		return false;
	}
	return true;
}
//显示转发页面
function share(sid){
	var url = "<%=contextPath%>/TeeWeibPublishController/findPublish.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	$(".shortIntro").html("@"+json.rtData);
	$("#infoId").val(sid);
	//$("#address").val(0);
	$(".bodyWrap").removeClass('hide');
    $("div .title").show();
	$("#relayPublish").show();
	$("#addPublish").hide(); 
}
//转发
function relay(){
	 var children=$("#zhuanFaSuoLue").children();
	    var picIdStr="";
	    var picIdStry="";
	    if(children.length>1){
	    	for(var i=0;i<children.length-1;i++){
	    		var picId=$(children[i]).attr("id");
	    		picId=picId.substring(3,picId.length);
	    		picIdStr+=picId+",";
	    		picIdStry+=$(children[i]).children(":first").val()+",";
	    	}
	    }
	var infoId=$("#infoId").val();
	var relay=$("#relay").val();
    if(check1()){
		while(relay.indexOf('\n') >= 0){
			 relay = relay.replace('\n','<br/>');
		}
		var url = "<%=contextPath%>/TeeWeibRelayController/addRelay.action";
		var json = tools.requestJsonRs(url,{infoId:infoId,relay:relay,img:picIdStr,imgy:picIdStry});
	    $("#infoId").val(0);
	    $("#relay").val("");
	    $("#repost").hide();
	    window.location.reload();
	}
}
//验证
function check1(){
	var relay=$("#relay").val();
	if(relay==null || relay==""){
		return false;
	}
	return true;
}
//关注话题
function guanZhuTopic(){
	<%-- var url = "<%=contextPath%>/TeeWeibRelayController/addRelay.action";
	var json = tools.requestJsonRs(url,{infoId:infoId,relay:relay}); --%>
}
//判断当前登录人是否已经关注此话题
function loginUserGzTopic(){
	//var tContent=updateTopic();
	var url = "<%=contextPath%>/TeeWeibGuanZhuController/loginUserGzTopic.action";
	var json = tools.requestJsonRs(url,{topicName:topicConent});
	var data=json.rtData;
	if(data!=null && data==true){
		$("#qxGzTopic").show();
		$("#gzTopic").hide();
	}else{
		$("#gzTopic").show();
		$("#qxGzTopic").hide();
	}
}
//关注话题
function gzTopic(){
	var url = "<%=contextPath%>/TeeWeibGuanZhuController/addGzTopic.action";
	var json = tools.requestJsonRs(url,{topicName:topicConent});
	loginUserGzTopic();
	countTopic();
}
//取消关注话题
function qxGzTopic(){
	var url = "<%=contextPath%>/TeeWeibGuanZhuController/deleteGzTopic.action";
	var json = tools.requestJsonRs(url,{topicName:topicConent});
	loginUserGzTopic();
	countTopic();
}
//关注话题（数量）
function countTopic(){
	var url = "<%=contextPath%>/TeeWeibGuanZhuController/countTopic.action";
	var json = tools.requestJsonRs(url,{topicName:topicConent});
	var data=json.total;
	$("#guanZhuCount").html(data);
	var rows=json.rows;
	if(rows!=null){
		var html="";
		for(var i=0;i<rows.length;i++){
			html+="<li>";
			   html+="<div class='avatar'>";
			   if(rows[i].avatar>0){
				  html+="<img style='width: 48px;height: 48px;' src='<%=contextPath %>/attachmentController/downFile.action?id="+rows[i].avatar+"' alt=''>";
			   }else{
			      html+="<img src='dist/images/blog.png' alt=''>";
			   }
			   html+="</div>";
			   html+="<p class='personName'>"+rows[i].userName+"</p>";
			html+="</li>";
		}
		$("#gzPersonCount").html(html);
	}
	
}
//发微博
function sendPublish(){
	$(".bodyWrap").removeClass('hide');
	$("div .title").hide();
	$(".shortIntro").html("");
	$("#relayPublish").hide();
	$("#addPublish").show();
}
//发微博
function addPublish2(){
	 var children=$("#zhuanFaSuoLue").children();
	    var picIdStr="";
	    var picIdStry="";
	    if(children.length>1){
	    	for(var i=0;i<children.length-1;i++){
	    		var picId=$(children[i]).attr("id");
	    		picId=picId.substring(3,picId.length);
	    		picIdStr+=picId+",";
	    		picIdStry+=$(children[i]).children(":first").val()+",";
	    	}
	    }
	var relay=$("#relay").val();
	if(relay!=null && relay!=""){
		while(relay.indexOf('\n') >= 0){
			relay = relay.replace('\n','<br/>');
		}
		var url = "<%=contextPath%>/TeeWeibPublishController/addPublish.action";
		var json = tools.requestJsonRs(url,{content:relay,img:picIdStr,imgy:picIdStry});
		if(json.rtState){
			 $("#relay").val("");
			 window.location.reload();
			 $.MsgBox.Alert_auto(json.rtMsg);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}
//首页
function onHomePage(){
	location.href="<%=contextPath%>/system/core/base/weibo/index.jsp";
}
</script>
<body onload="doInit();">
	<!-- <div class="header">
		<div class="headerWrap">
			<div class="left">
				<span class="logo">社区微博</span>
				<input class="searchInput" type="text">
			</div>
			<div class="addNew">
				<img src="dist/images/icon_addNew.png" alt="">
			</div>
			<div class="right">
				<span class="homePage" onclick="onHomePage();">首页</span>
				<span class="userName">admin</span>
			</div>
		</div>
	</div> -->
	<div class="main" style="padding-top: 20px;">
		<div class="mainHeader">
			<div class="bigAvatar">
				<img src="dist/images/avatarBig.png">
			</div>
			<div class="topicTitle">
				<p class="titleName">#话题名称#</p>
				<div class="titleBtns">
					<input type="button" value="发微博" class="newBlog" onclick="sendPublish();">
					<input type="button" style="display: none" value="取消关注" id="qxGzTopic" class="follow" onclick="qxGzTopic();">
					<input type="button" style="display: none" value="关注" id="gzTopic" class="follow" onclick="gzTopic();">
				</div>
			</div>
		</div>
		<div class="mainContent">
			<div class="blogList">
				<div class="blogItem">
					<div class="topicBlock">
						<div class="editingComment">
							<div class="avatar">
								<img src="dist/images/blog.png" alt="">
							</div>
							<div class="editContent">
								<textarea name="" id="commentInput" cols="30" class="commentInput" rows="10"></textarea>
								<div class="insertSpecial clearfix">
									<span class="emoji emoji1"></span>
									<span class="uploadPic"></span>
									<div class="image-upload-wrapper" id="canyuPinglun" style="">
		                                <div class="tip-triangle tip-triangle-front"></div>
		                                <div class="tip-triangle tip-triangle-back"></div>
		                                <div class="image-upload-head">
		                                   <p>本地上传<span class="btn-close" onclick="closePic();">关闭</span></p>
		                                </div>
		                                <div class="tip-text">共 <span id="current-count">9</span> 张，还能上传 <span id="remain-count">0</span> 张（按住Ctrl键上传多张图片）</div>
						                <ul class="image-upload-body" id="faweibo">
							                 <li id="btn-add" style="top: 0px;">+
								                  <span id="virtualBtn" class="webuploader-container">
									              <div id="uploadPhotoBtn" class="webuploader-pick" style="position:relative">
											         <div id="uploadPhotoContainer"></div>
											         <input style="display: none;" type="text" id="uploadPhotoHolder" name="uploadPhotoHolder"/>
									              </div>
								                  </span>
							                </li>
						               </ul>
					               	</div>
									<input class="commentBtn" type="button" value="参与评论" onclick="addPublish();">
								</div>
								  
							</div>
						</div>
					</div>
					<div class="topicTimes">
						当前已讨论5次
					</div>
					<div class="topicList">
						
					</div>
					<div class="getMore">
		              <span id="findGgetMore" onclick="findGetMore();">查看更多></span>
		              <span id="getMoreNot" style="display: none;">没有更多了</span>
		            </div>	
				</div>
			</div>
			</div>
			<div class="sideBar">
				<div class="myInfo">
					<ul class="myInfoBody">
						<li>
							<div class="followNum" id="guanZhuCount">0</div>
							<p class="infoName">关注</p>
						</li>
						<li>
							<div class="followNum" id="readCount">0</div>
							<p class="infoName">阅读</p>
						</li>
					</ul>
				</div>
				<div class="recentParticipater">
					<ul id="gzPersonCount">
						
						<!-- <li>
							<div class="avatar">
								<img src="dist/images/blog.png" alt="">
							</div>
							<p class="personName">张小凡</p>
						</li>
						<li>
							<div class="avatar">
								<img src="dist/images/blog.png" alt="">
							</div>
							<p class="personName">不会说话</p>
						</li> -->
					</ul>
				</div>
			</div>
		</div>

	</div>
	<div class="bodyWrap hide">
		<div class="repost" id="repost">
				<div class="head">
					<div class="title">转发微博</div>
					<div class="close">&times;</div>
				</div>
				
				<p class="shortIntro"></p>
				<input type="hidden" value="0" name="infoId" id="infoId"/>
				<input type="hidden" value="0" name="address" id="address"/>
				<textarea name="relay" class="repostReason" id="relay" placeholder=""></textarea>
				<div class="insertSpecial">
					<span class="emoji newBlogEmoji"></span>
					<span class="uploadPic" id="second"></span>
					<div class="image-upload-wrapper" id="zhuanFa" style="">
		                <div class="tip-triangle tip-triangle-front"></div>
		                <div class="tip-triangle tip-triangle-back"></div>
		                <div class="image-upload-head">
		                    <p>本地上传<span class="btn-close" onclick="closePic();">关闭</span></p>
		                </div>
		                <div class="tip-text">共 <span id="current-count2">9</span> 张，还能上传 <span id="remain-count2">0</span> 张（按住Ctrl键上传多张图片）</div>
						<ul class="image-upload-body" id="zhuanFaSuoLue">
							<li id="btn-add2" class="btn-add" style="top: 0px;">+
								<span id="virtualBtn2" class="webuploader-container virtualBtn">
									<div id="uploadPhotoBtn2" class="webuploader-pick uploadPhotoBtn" style="position:relative">
											<div id="uploadPhotoContainer2"></div>
											<input style="display: none;" type="text" id="uploadPhotoHolder2" name="uploadPhotoHolder2"/>
									</div>
								</span>
							</li>
						</ul>
					</div>
					<input class="commentBtn" onclick="relay();" type="button" value="转发" id="relayPublish">
					<input class="commentBtn" onclick="addPublish2();" type="button" value="发布" id="addPublish">
				</div>
				
				
				
			</div>
			</div>
<!-- <script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script> -->
<script type="text/javascript" src="dist/js/jquery.qqFace.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".repost .close").click(function(event) {
				$(".bodyWrap").addClass('hide');
				$(".image-upload-wrapper").hide();
			});
			$(".uploadPic").click(function(event) {
				
				/* var pos = $(this).position(); */
				/* $("#canyuPinglun").css({
					"position":"absolute",
					"top":pos.top,
					"left":pos.left
				}); */
				$("#canyuPinglun").show();
			});
			
			$("#second").click(function(event) {
				$("#zhuanFa").show();
				$("#canyuPinglun").hide();
				if($("#relay").val()==null || $("#relay").val()==""){
					$("#relay").val("分享图片");
				}
			});
			
			
			$('.emoji1').qqFace({

				id : 'facebox',

				assign:'commentInput',

				path:'dist/arclist/'	//表情存放的路径

			});
			$('.newBlogEmoji').qqFace({

				id : 'facebox',

				assign:'relay',

				path:'dist/arclist/'	//表情存放的路径

			});
		})
	</script>
</body>
</html>