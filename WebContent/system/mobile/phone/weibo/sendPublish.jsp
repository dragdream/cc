<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>发微博</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
        <%@ include file="/system/mobile/mui/header.jsp" %>
        <%@ include file="/header/upload.jsp"%>
        <script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script>
		<link rel="stylesheet" href="css/mui.css">
		<style>
			html,
			body {
				background-color: #fff;
			}
			*{
				margin:0;
				padding:0;
				font-family: "Microsoft Yahei";
			}
			li{
				list-style: none;
			}
			#textInput{
				position:fixed;
				top:44px;
				left: 0;
				right: 0;
				bottom: 40px;
				margin-bottom: 0;
				border:none;
				font-size: 15px;
			    height: 50%;
			}
			.footerBar{
				position: fixed;
				bottom: 0;
				left: 0;
				right: 0;
				height:40px;
				z-index: 3;
			}
			.footerBar ul{
				text-align:center;
				padding:10px 0;
			}
			.footerBar li{
				/* float: left; */
				margin-left: 10px;
				display: inline-block;
				margin: 0 20px;
				width: 20px;
				height: 20px;
			}
			.footerBar li img{
				width: 100%;
			}
			#imgList li{
			    width: 26%;
			    float:left;    
			    margin: 0px 8px;
			}
			#imgList li img{
			   width:65px;
			   height:65px;
			   background-color: white;
			}
			#imgs{
			   position: absolute;
			   left: 0;
			   top: 58%;
			   bottom: 46px;
			   right: 0;
			   margin: 2px;
			   /* background-color: #efeeee; */
			   display: none;
			   margin-left: 38px;
			   width:70%;
			   text-align: center;
			}
			.emojiBox{
	position: fixed;
	left: 0;
	right: 0;
	bottom: -120px;
	height: 120px;
	z-index: 3;
	cursor:pointer;
}
.emojiBox{
	background-color: #eaebed;
	overflow: auto;
}
.emojiBox .emoji {
    float: left;
    width: 2em;
    padding: 0.2em;
    border: 1px solid #eaebed;

}
.emojiBox .emoji img{
	vertical-align: middle;
	width:100%;
}


.pictureLi{
	width: 28%;
	max-height: 100px;
	margin:5px;
	display: inline-block;
	position: relative;
	overflow: hidden;
}
.deleteIcon{
	position: absolute;
	display: block;
	width: 40px;
	height: 40px;
	line-height: 54px;
	text-align: left;
	text-indent: 4px;
	border-radius: 50%;
	background-color: #7c7c80;
	color: #fff;
	right: -20px;
	top: -20px;
	font-size: 20px;
}
.pictureLi img{
	width: 100%;
}
		</style>
	</head>

	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">发微博</h1>
		</header>
		<div class="footerBar">
			<ul>
				<li id="picture" class="pictureList">
				 <!-- <input type="hidden" id="imgInput" value=""/> -->
					<img src="images/picture.png">
				</li>
				<!-- <li>
					<img src="images/link.png">
				</li> -->
				<li id="topic">
					<img src="images/topic.png">
				</li>
				<li id="" class="emojiBtn">
					<img src="images/emoji.png">
				</li>
			</ul>
		</div>
		
		<div>
			<div class="mui-content">
				<textarea name="" id="textInput" placeholder="分享新鲜事"></textarea>
			</div>
	      <!--   <div id="imgs">
	            <input type="hidden" id="imgInput"/>
	            <ul id="imgList">
	            
	            </ul>
	        </div> -->
		</div>
		<ul class="emojiBox pictureBox" id="avaterUl">
	
        </ul>
		<script type="text/javascript" src="dist/js/jquery.qqFace.js?v=2"></script>
		<script type="text/javascript">
		var imgsId="";
		var imgNum=0;
		$(function(){
			$('#emoji').qqFace({

				id : 'facebox',

				assign:'textInput',

				path:'dist/arclist/'	//表情存放的路径

			});
			//picture
			  $("body").on("tap","#picture",function(){
				  if($(".pictureLi").length<9){
					  var textInput=$("#textInput").val();
					  if(textInput==null || textInput==""){
						  $("#textInput").val("分享图片");
					  }
					  doTakePhotob(); 
				  }else{
					  alert("您最多只能上传9张图片");
				  }
				  
			 });
			 //发微博
			  $("body").on("tap",".sendPublish",function(){
				  var content=$("#textInput").val();
				  if(content!=null && content!=""){
					  while(content.indexOf('\n') >= 0){
							 content = content.replace('\n','<br/>');
						}
					  var pictureLis=$(".pictureLi");
					  var picIdStry="";
					  var picIdStr="";
					  for(var i=0;i<pictureLis.length;i++){
						 var picId= $(pictureLis[i]).attr("id");
						  picId=picId.substring(3,picId.length);
				    	  picIdStr+=picId+",";
						  picIdStry+=$(pictureLis[i]).children(":first").val()+",";
					  }
					  $.ajax({
						  type: 'POST',
						  url: "<%=contextPath%>/TeeWeibPublishController/addPublish.action",
						  data: {content:content,img:picIdStr,imgy:picIdStry},
						  timeout: 10000,
						  success: function(json){
							  //json = eval("("+json+")");
						  },
						  error: function(xhr, type){
						    //alert('服务器请求超时!');
						  }
					});
					  window.location.replace('index.jsp'); 
				  }
			 });
			 
				//删除图片deleteIcon
			  $("body").on("tap",".deleteIcon",function(){
				if(window.confirm("确定删除吗?")){
					$(this).parent().remove();
					imgNum--;
					pictureLists();
				}
			});
		});
		//发布微博信息
	
		/* 	mui.init({
				swipeBack:true //启用右滑关闭功能
			}); */
			creatNav();
			function creatNav(){
				var header = document.getElementById("header");
				 //左侧图标+文字按钮
				var leftbtn = document.createElement('button');
				leftbtn.className = 'mui-action-back mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left';
				var span = document.createElement('span');
			    span.className = 'mui-icon mui-icon-left-nav';
			    leftbtn.appendChild(span);
				var text = document.createTextNode('取消');
				//leftbtn.appendChild(text);
				leftbtn.style.marginLeft = "2px";
				leftbtn.onclick = function () {  
					window.location.replace('index.jsp'); 
                };
				remove('.mui-pull-left');
				
				header.appendChild(leftbtn);
				 //右侧文字按钮
				var rightbtn = document.createElement('button');
				rightbtn.className = 'mui-btn mui-btn-blue mui-btn-link mui-pull-right sendPublish';
				rightbtn.innerText = '发送';
				remove('.mui-pull-right');
				rightbtn.onclick=function(){
					sendPublish();
				};
				header.appendChild(rightbtn);
				
			}

			
			function check(){
				var textInput=$("#textInput").val();
				if(textInput==null || textInput==""){
					return false;
				}
				return true;
			}
			 //删除原先存在的节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}

			function setSelectionRange(input, selectionStart, selectionEnd) {
				  if (input.setSelectionRange) {
				    input.focus();
				    input.setSelectionRange(selectionStart, selectionEnd);
				  }
				  else if (input.createTextRange) {
				    var range = input.createTextRange();
				    range.collapse(true);
				    range.moveEnd('character', selectionEnd);
				    range.moveStart('character', selectionStart);
				    range.select();
				  }
				}

				function setCaretToPos (input, pos) {
				  setSelectionRange(input, pos, pos);
				}
			
			$("#topic").click(function(event) {
				var content=$("#textInput").val();
				$("#textInput").val(content+"#在这里输入你想要说的话题#");
				var length = content.length;
				setSelectionRange($("#textInput")[0],length + 1,length+13);
			});

			function doTakePhotob(){
				TakePhoto(function(files){
				    if(files.length!=0){
						var attachIds = [];
						for(var i=0;i<files.length;i++){
							imgNum++;
							if(imgNum<=9){
							  attachIds.push(files[i].path);
							}
						}
						UploadPhoto(function(files0){
							for(var j=0;j<files0.length;j++){
								var picId=suoLuePic(files0[j].id);
								var html="<li class='pictureLi' id='pic"+picId+"'><input type='hidden' value='"+files0[j].id+"'/><img src='<%=contextPath%>/attachmentController/downFile.action?id="+picId+"'><span class='deleteIcon'>×</span></li>";
								//imgsId+=picId+",";
							    $(".pictureBox").prepend(html);
							}
							//$("#imgInput").val(imgsId);
							pictureLists();
						},attachIds,"imgupload","");
					}
				},1,9); 
			}
	/*点击显示表情*/
	$(".emojiBtn").on("tap",toggleEmoji);
	function toggleEmoji(commend){
		var repostInputWrapBottom = 0;
		var emojiBoxBottom = 0;
		$(".pictureLi").hide();
		$(".emoji").show();
		if(commend && commend=="hide"){
			repostInputWrapBottom = 0;
			emojiBoxBottom = "-120px";
			$(this).removeClass('open');
			$(".footerBar").css({
				bottom: repostInputWrapBottom
			});
			$(".emojiBox").css({
				bottom: emojiBoxBottom
			});
			return;
		}
		if($(".emojiBtn").hasClass('open')){
			repostInputWrapBottom = 0;
			emojiBoxBottom = "-120px";
			$(this).removeClass('open');
		}else{
			repostInputWrapBottom = "120px";
			emojiBoxBottom = "0";
			$(this).addClass('open');
		}
		$(".footerBar").css({
			bottom: repostInputWrapBottom
		});
		$(".emojiBox").css({
			bottom: emojiBoxBottom
		});
	}
	renderAvatar();
	function renderAvatar(){
		if(!$(".emojiBox").find(".emoji").length){
			var temp = [];
			for(var i=1; i<=90; i++){
				var labFace = '[emo_'+(i-1)+']';
				temp.push('<li class="emoji" avatarName="[emo_'+(i - 1)+']"><img src="'+'dist/arclist/'+"emo_"+(i-1)+'.png"/></li>');
			}
			$(".emojiBox").append(temp.join(""));
		}
	}
	/*点击表情插入到文本框中*/
	$(".emojiBox .emoji").on("tap",function(){
		var name = $(this).attr("avatarName");
		$("#textInput").val($("#textInput").val() + name);
		toggleEmoji("hide");
	});
function pictureLists(){
		$(".pictureLi").show();
		$(".emoji").hide();
		var repostInputWrapBottom = 0;
		var emojiBoxBottom = 0;
	    if($(".pictureLi").length==0){
	    	repostInputWrapBottom = 0;
			emojiBoxBottom = "-120px";
			$(this).removeClass('open');
	    }
	/* 	if($(".emojiBtn").hasClass('open')){
			repostInputWrapBottom = 0;
			emojiBoxBottom = "-120px";
			$(this).removeClass('open');
		}*/else{
			repostInputWrapBottom = "120px";
			emojiBoxBottom = "0";
			$(this).addClass('open');
		}
		$(".footerBar").css({
			bottom: repostInputWrapBottom
		});
		$(".emojiBox").css({
			bottom: emojiBoxBottom
		});
}

//缩略图
function suoLuePic(sid){
	var data=0;
	$.ajax({
		  type: 'POST',
		  url: "<%=contextPath%>/attachmentController/picZoom.action",
		  data: {attachId:sid},
		  timeout: 10000,
		  async:false,
		  success: function(json){
			  json = eval("("+json+")");
			  data=json.rtData;
		  },
		  error: function(xhr, type){
		    //alert('服务器请求超时!');
		  }
		});
	return data;
}
		</script>

	</body>

</html>