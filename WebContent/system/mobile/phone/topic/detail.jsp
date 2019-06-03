<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String topicId = request.getParameter("uuid");
String topicSectionId = request.getParameter("topicSectionId");

%>


<!DOCTYPE HTML>
<html>
<head>
<title>话题详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/superRaytinpaginationjs/dist/pagination-with-styles.js?v=2"></script>
<style>
body{
background:#fff;
}
.u1{
padding:0px;
margin:0px;
color:gray;
font-size:13px;
}
.avatar{
border-radius:25px;
height:30px;
width:30px;
margin-right:10px;
}
.avatar0{
border-radius:25px;
height:20px;
margin-right:10px;
}
hr{
border:0px;
border-top:1px solid #cdcdcd;
}
p{
color:#000;
}
</style>
</head>
<body>
<div class="mui-content-padded">
	<table style="width:100%">
		<tr>
			<td style="width:40px;">
				<img id="avatar" class="avatar" src="<%=contextPath %>/attachmentController/downFile.action?sid=1" onerror="errorAvatar(this)"/>
			</td>
			<td>
				<p class="u1" id="crUserName"></p>
				<p class="u1" id="crTimeStr"></p>
			</td>
			<td style="text-align:right;width:100px">
				<span class="mui-icon mui-icon-contact" style="font-size:14px;color:#749cda">楼主</span>
			</td>
		</tr>
	</table>
	<div style="margin-top:10px;color: #f09018;font-weight: bold;" id="subject"> </div>
	<div id="content" style="margin-top:10px;font-size:14px;" >
	</div>
	
	<div id="attach" style="margin-top:10px;font-size:14px;" >
	附件：
	</div>
</div>

<!-- 回复 -->

<div id="replyList">
	<div class="mui-content-padded" style="display: none;">
		<hr>
		<table style="width:100%">
			<tr>
				<td style="width:30px;">
					<img class="avatar0" src="<%=contextPath %>/attachmentController/downFile.action?sid=1" onerror="errorAvatar(this)"/>
				</td>
				<td>
					<span style="font-size:12px;color:gray">系统管理员</span>
				</td>
				<td style="text-align:right;width:100px;font-size:14px;color:#749cda">
					1楼
				</td>
			</tr>
		</table>
		<div id="content0" style="margin-top:10px;">
			<div>
				<p>回复！！！</p>
			</div>
			<p class="u1" id="crTimeStr" ></p>
		</div>
	</div>
		
</div>
<div id="pagination-demo1" class="app-pagination" style="display: none;"></div>

<br/>
<br/>
<br/>

<!-- 底部操作栏 -->
<nav class="mui-bar mui-bar-tab">
  <a id="a1" class="mui-tab-item mui-active" >
		<span class="mui-icon mui-icon-undo"></span>
		<span  class="mui-tab-label" >返回</span>
	</a>
  <a id="a2" class="mui-tab-item mui-active" >
		<span class="mui-icon mui-icon-redo"></span>
		<span  class="mui-tab-label" >回复</span>
	</a>
</nav>
<script>
//手机屏幕宽度
var width;	
$(function(){
	getTopicById('<%=topicId%>');
	getPageList('<%=topicId%>');
	updateClickCount('<%=topicId%>');
	a1.addEventListener("tap",function(){
		var  url = contextPath + "/system/mobile/phone/topic/topics.jsp?uuid=<%=topicSectionId%>";
		window.location.href = url;
	});
	a2.addEventListener("tap",function(){
		showInfoFunc('<%=topicId%>');
	});
	
});

function errorAvatar(obj){
	obj.src = contextPath+"/common/images/default_avatar.gif";
}




/**
 * 设置查看数
 */
function updateClickCount(uuid){
	var url =contextPath+"/TeeTopicController/updateClickCount.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{uuid:uuid},
		timeout:10000,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				
			}
		},
		error:function(){
			
		}
	});
}



/**
 * 获取话题信息
 */
function getTopicById(uuid){
	var url =contextPath+"/TeeTopicController/getInfoById.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{uuid:uuid},
		timeout:10000,
		success:function(text){
			//获取手机屏幕的宽度
			  width=parseInt(window.innerWidth);
			
			var json = eval("("+text+")");
			if(json.rtState){
				var prc = json.rtData;
				$("#crUserName").text(prc.crUserName);
				$("#crTimeStr").text(prc.crTimeStr);
				$("#subject").text(prc.subject);
				$("#content").html(prc.content);
				
				var attacheModels = prc.attacheModels;
				for(var i=0;i<attacheModels.length;i++){
					var item = attacheModels[i];
					$("#attach").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "</a></div>");
				}
				
				var avatar = prc.avatar;
				if(avatar==null || avatar=="" || avatar=="0"){
					avatar = systemImagePath+"/default_avatar.gif";
				}else{
					avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
				}
				$("#avatar").attr("src",avatar);
				
				 //改变图片的宽度  使图片自适应手机的宽度
				  $("#content img").each(function(i,obj){
					  obj.onload=function(){		
						  if(this.width > width){
							  this.width = width-40; 	  
						  }
						  
					  }
				  });
			}
		},
		error:function(){
			
		}
	});
}



/**
 * 获取回复数据
 */
 var counter = 0;
 function getPageList(uuid){
	//获取手机屏幕的宽度
	  width=parseInt(window.innerWidth);
	 
	$('#pagination-demo1').pagination({
		dataSource: contextPath+'/TeeTopicReplyController/getTopicReplyPage.action?topicId=' + uuid,
		pageSize: 10,
		goPage:function(number,attributes){
			//alert(number + ">>>" + tools.jsonObj2String(attributes));
			counter = (parseInt(number) - 1) * parseInt(attributes.pageSize);
		},
		callback: function(data){
			//Alert(data.rows);
			var list = data.rows;
			if(list.length>0){
				$("#pagination-demo1").show();
				document.getElementById("replyList").innerHTML="";
					var html = "";
					 jQuery.each(list,function(i,sysPara){
						 counter ++;
						
						var avatar = sysPara.avatar;
						if(avatar==null || avatar=="" || avatar=="0"){
							avatar = systemImagePath+"/default_avatar.gif";
						}else{
							avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
						}
						html += "<hr/>";
						html+="<div class=\"mui-content-padded\">"
								+"		<table style='width:100%'> "
								+"			<tr> "
								+"				<td style='width:30px;'> "
								+"					<img class='avatar' src='" + avatar + "' onerror='errorAvatar(this)'/>"
								+"				</td>"
								+"				<td> "
								+"					<span style='font-size:12px;color:gray'>" + sysPara.crUserName + "</span>"
								+"				</td>"
								+"				<td style='text-align:right;width:100px;font-size:14px;color:#749cda;'>"
								+					counter + "楼"
								+"				</td>"
								+"			</tr> "
								+"		</table> "
								+"		<div class='margin-top:10px;'>"
								+"			<div style='font-size:14px;' class='content'>"
								+				sysPara.content
								+"			</div><div style='font-size:14px;margin-top:10px'>附件：</div>";
								
								
						var attacheModels = sysPara.attacheModels;
						for(var j=0;j<attacheModels.length;j++){
							var item = attacheModels[j];
							html+="<p><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "</a></p>";
						}
								
						html+="<p class='u1' id='crTimeStr' >"+sysPara.crTimeStr+"</p>"
						+"		</div>"
						+"</div>";
					});
					$("#replyList").append(html);
					
					
					//改变图片的宽度  使图片自适应手机的宽度
					//document.write( $("div[class='content']").length);
					  $("div[class='content'] img").each(function(i,obj){
						  obj.onload=function(){		
							  if(this.width > width){
								  this.width = width-40; 	  
							  }
							  
						  }
					  });
			}
		}
	});
}
 

//进入话题界面
 function showInfoFunc(sid){
 	var url = contextPath + "/system/mobile/phone/topic/reply.jsp?topicSectionId=<%=topicSectionId%>&uuid=" + sid;
 	window.location.href = url;
 }
</script>
</body>
</html>