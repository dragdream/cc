<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int userId=loginUser.getUuid();
  int avatar=TeeStringUtil.getInteger(loginUser.getAvatar(),0);
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
    
	<title>微博管理</title>
	<link rel="stylesheet" href="dist/css/blog.css">
	<link rel="stylesheet" href="dist/css/emoji.css">
	<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/api.js"></script>
	
	<style type="text/css">
		html{
			height:auto;
		}
		body{
			height:auto;
			max-height: fit-content;
		}
	</style>
	<script type="text/javascript">
	var page=1;
	var pageSize=5;
	var userId=<%=userId%>;
	var avatar=<%=avatar%>;
	var bF=0;
	var dF=0;
	var countPic=0;
	function doInit(){
		query(0,0);//微博列表
		countNUm();//关注、粉丝、微博（数量）
		HyH();//推荐关注
		allDept();//所有部门
		topicAll(page);//热门话题
		if(avatar>0){
			$("#avatarImg2").show();
		}else{
			$("#avatarImg3").show();
		}
		
		
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
		
		  $("body").on("click",".yunLanPicture",function(){
			  var strArr= $(this).prev().prev().val();
			  strArr= strArr.substring(0,strArr.length-1);
			  var picId= $(this).prev().val();
			  var url = contextPath +"/system/core/attachment/picExplore.jsp?id="+picId+"&pics="+strArr;
			  openFullWindow(url,"在线预览");
		 });
		
		
		
	}
	//缩略图
	function suoLuePic(sid){
		var url = "<%=contextPath%>/attachmentController/picZoom.action";
		var json = tools.requestJsonRs(url,{attachId:sid});
		var html="<li id='pic"+json.rtData+"' style='background: url(<%=contextPath %>/attachmentController/downFile.action?id="+json.rtData+") center no-repeat'>";
		html+="<input type='hidden' value='"+sid+"'/>";
	    html+="<i class='iconfont' action-type='thumb-delete' onclick='deleteFilePic("+json.rtData+")'></i>";
	    html+="</li>";
	    $(".fabuweibo").prepend(html);
	}
	//删除缩略图
	function deleteFilePic(sid){
		var url = "<%=contextPath%>/attachmentController/deleteFile.action";
		var json = tools.requestJsonRs(url,{attachIds:sid});
		$("#pic"+sid).remove();
		countPic=countPic-1;
		$("#remain-count").html(9-countPic);
	}
	//缩略图
	function suoLuePic2(sid){
		var url = "<%=contextPath%>/attachmentController/picZoom.action";
		var json = tools.requestJsonRs(url,{attachId:sid});
		var html="<li id='pic"+json.rtData+"' style='background: url(<%=contextPath %>/attachmentController/downFile.action?id="+json.rtData+") center no-repeat'>";
		html+="<input type='hidden' value='"+sid+"'/>";
		html+="<i class='iconfont' action-type='thumb-delete' onclick='deleteFilePic2("+json.rtData+")'></i>";
	    html+="</li>";
	    $(".zhuanFaSuoLue").prepend(html);
	}
	//删除缩略图
	function deleteFilePic2(sid){
		var url = "<%=contextPath%>/attachmentController/deleteFile.action";
		var json = tools.requestJsonRs(url,{attachIds:sid});
		$("#pic"+sid).remove();
		countPic=countPic-1;
		$("#remain-count2").html(9-countPic);
	}
	//推荐关注
	function HyH(){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/findByPerson.action";
		var json = tools.requestJsonRs(url);
		var data=json.rtData;
		if(data!=null){
			var html="<div class='recommendHead'><div class='recommendTitle'>推荐关注</div><div class='recommendOper mouse' onclick='HyH();'>换一换</div></div>";
			for(var i=0;i<data.length;i++){
				html+="<div class='recommendBody'>";
				html+="<p class='recommendReason'></p>";
				html+="<div class='avatar'>";
				if(data[i].avatar!=null && data[i].avatar!=""){
					   html+="<img style='width: 30px;height: 30px;' class='avatarImg' src='<%=contextPath %>/attachmentController/downFile.action?id="+data[i].avatar+"' alt=''>";
				}else{
						html+="<img class='avatarImg' src='dist/images/replyAvatarDemo.png' alt=''>";
				}
				html+="<div class='avatarInfo'>";
				html+="<div class='recName'>"+data[i].userName+"</div>";
				html+="<div class='recNameSub'>"+data[i].deptIdName+"</div>";	
				html+="</div></div>";
				html+="<div class='toFollow'>";
				html+="<span onclick='guanzhu("+data[i].uuid+")'>关注</span></div></div>";		
			}
			//html+="<div class='recommendFooter'>查看更多></div>";
			$("#HyH").html(html);
		}
	}

	//搜索部门
	function searchDept(){
		var search=$("#search").val();
		if(search!=null && search!=""){
		   var url = "<%=contextPath%>/TeeWeibPublishController/searchDept.action";
		   var json = tools.requestJsonRs(url,{deptName:search});
		   var data=json.rtData;
		   if(data!=null && data.length>0){
		       var html="";
			   for(var i=0;i<data.length;i++){
				   var d=data[i];
				   html+="<li class='searchResultItem'>";
				   html+="<span class='resultItemName mouse' onclick='query(4,"+d.uuid+")'>"+d.deptName+"</span></li>";
				  /*  if(d.gzDept){//关注（部门）
						html+="<span class='follow' onclick='deleteguanzhuDept("+d.uuid+");'><span class='add'>-</span>已关注</span>";
					}else{
						html+="<span class='follow' onclick='addguanzhuDept("+d.uuid+");'><span class='add'>+</span>关注</span>";
					} */
			   }
		   $("#searchList").html(html);
		   }
		}
	}
	//取消关注(部门)
	<%-- function deleteguanzhuDept(deptId){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/deleteGuanZhuDept.action";
		var json = tools.requestJsonRs(url,{deptId:deptId});
		allDept();
		$(".allDepart .name").click(function(event) {
			$(".allDepartWrap").toggleClass('hide');
			event.stopPropagation();
			event.cancelBubble = true;
		});
	} --%>
	//添加关注(部门)
	<%-- function addguanzhuDept(deptId){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/addGuanZhuDept.action";
		var json = tools.requestJsonRs(url,{deptId:deptId});
		allDept();
		$(".allDepart .name").click(function(event) {
			$(".allDepartWrap").toggleClass('hide');
			event.stopPropagation();
			event.cancelBubble = true;
		});
	} --%>
	//所有部门
	function allDept(){
		var url = "<%=contextPath%>/TeeWeibPublishController/allDept.action";
		var json = tools.requestJsonRs(url);
		var data=json.rtData;
		if(data!=null){
			$("#allDepartList").html(data);
		}
	}
	//关注
	function guanzhu(personId){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/addGuanZhu.action";
		var json = tools.requestJsonRs(url,{personId:personId});
		if(json.rtState){
			 $.MsgBox.Alert_auto("关注成功");
		     HyH();
		     countNUm();
		}
	}
	//获取粉丝、关注、微博数量
	function countNUm(){
		var url = "<%=contextPath%>/TeeWeibGuanZhuController/countPerson.action";
		var json = tools.requestJsonRs(url);
		var data=json.rtData;
		if(data!=null){
			$("#userName").html(data.userName);
			$("#countPerson").html(data.countPeson);//关注
			$("#countByPerson").html(data.countByPerson);//粉丝
			$("#countPublish").html(data.countPublish);//微博
		}
	}
	//发布的微博信息
	function query(b,d){
		if(bF!=b){
		   pageSize=5;
		}
		var html="";
		var url = "<%=contextPath%>/TeeWeibPublishController/findCollectAll.action";
		var json = tools.requestJsonRs(url,{meter:b,deptId:d,page:1,rows:pageSize});
		var rows=json.rows;
		if(rows!=null){
			for(var i=0;i<rows.length;i++){
				var row=rows[i];
				html+="<div class='blogItem'>";
				html+="<div class='blogHead clearfix'>"
				if(row.avatar>0){
					html+="<span class='avatar'><img src='<%=contextPath %>/attachmentController/downFile.action?id="+row.avatar+"' alt='#'></span>";
				}else{
					html+="<span class='avatar'><img src='dist/images/replyAvatarDemo.png' alt='#'></span>";
				}
				html+="<div class='userFromInfo'>";		
				html+="<p class='blogUserName'>"+row.userName+"</p>"	
				html+="<p class='blogTime'>"+row.createTime+"</p>";	
				html+="</div>";
				html+="<div class='otherOperation'>";	
				html+="<img src='dist/images/icon_extend.png' alt='' onclick='operationList("+row.sid+");'>";	
				if(userId==row.userId){
					html+="<ul class='operationList hide' id=d"+row.sid+">";
					   html+="<li class='operationItem' onclick='deletePublishById("+row.sid+","+b+","+d+")'>删除该条</li>";
					html+="</ul>";
				}
				/* <ul class="operationList hide">
					<li class="operationItem">删除该条</li>
					<li class="operationItem">取消关注</li>
					<li class="operationItem">其他操作</li>
				</ul> */
				html+="</div></div>";		
				html+="<div class='blogBody'>";	
				html+="<div class='blogText'>";
				html+="<p class='myword'>"+row.content+"</p>";//微博信息
				if(row.zfCotent!=""){
				 html+="<p><span class='atOther'>@"+row.zfCotent+"</span></p>";//转发的微博信息
				}
				html+="</div>";
				
			 	//上传图片的位置
				if(row.img!=null && row.img!=""){
					var str=row.img;
					var imgy=row.imgy;
					html+="<div class='attachImg'>";
					var imgArr=str.split(",");
					imgyArr=imgy.split(",");
					for(var k=0;k<imgArr.length-1;k++){
					   html+="<div class='imgBlock'>";
					        html+="<input type='hidden' value='"+row.imgy+"'/>";
					        html+="<input type='hidden' value='"+imgyArr[k]+"'/>";
					        html+="<img class='yunLanPicture' src='<%=contextPath %>/attachmentController/downFile.action?id="+imgArr[k]+"' alt=''>";
					    html+="</div>";
					} 
				 	html+="</div>";
				} 
				
				html+="</div>";
                html+="<ul class='BlogOperation'>";
                var html1="<li class='collection' id='"+row.sid+"' onclick='findCollect("+row.sid+","+b+","+d+");'>收藏</li>";
                if(row.collect){
                	html1="<li class='collection' style='background-image:url(dist/images/star_active.png);' id='"+row.sid+"' onclick='findCollect("+row.sid+","+b+","+d+");'>收藏</li>";
				} 
                html+=html1;
				html+="<li class='share' onclick='share("+row.sid+","+b+","+d+")'>"+row.number+"</li>";
				html+="<li class='reply' onclick='reply("+row.sid+")'>"+row.num+"</li>";
				html1="<li id='u"+row.sid+"' class='ups' onclick='findDianzan("+row.sid+","+b+","+d+")'>"+row.count+"</li>";
				 if(row.dianzan){
					html1="<li id='u"+row.sid+"' style='background-image:url(dist/images/ups_active.png);' class='ups' onclick='findDianzan("+row.sid+","+b+","+d+")'>"+row.count+"</li>";
				} 
				html+=html1;
				html+="</ul>";
				
				html+="<div id='p"+row.sid+"' style='display:none;' class='commentBlock'><div class='editingComment clearfix'>";
				html+="<div class='avatar'>";
				html+="<img src='dist/images/replyAvatarDemo.png' alt=''>";
				html+="</div>";
				html+="<div class='editContent'>";
				html+="<textarea name='Rcontent' id='r"+row.sid+"' cols='30' class='commentInput' rows='10'></textarea>";
				html+="<div class='insertSpecial'>";
				html+="<span class='emoji'></span>";
				/* html+="<span class='uploadPic'></span>"; */
				html+="<input class='commentBtn' type='button' onclick='addReply("+row.sid+","+b+","+d+");' value='评论'></div></div></div>";
				var ctList=row.ctList;//评论集合
				if(ctList!=null && ctList!=""){
				 	 for(var j=0;j<ctList.length;j++){
						//评论
						html+="<div class='oldComment'>";
						html+="<div class='avatar'>";
						if(ctList[j].avatar>0){
							html+="<img style='width: 36px;height: 36px;' src='<%=contextPath %>/attachmentController/downFile.action?id="+ctList[j].avatar+"' alt=''></div>";
						}else{
							html+="<img src='dist/images/replyAvatarDemo.png' alt=''></div>";
						}
						html+="<div class='oldCommentInfo'>";
						html+="<div class='commentUserName'>";
						html+="<span class='userName'>"+ctList[j].userName+"</span><p>"+ctList[j].content+"</p></div>";
						html+="<div class='commentOper'>";
						if(ctList[j].userId==userId){
							html+="<span style='cursor: pointer;' onclick='deletePingLun("+ctList[j].sid+","+row.sid+")'>删除</span> | ";
						}
						html+="<span class='reply' onclick='huiFu("+ctList[j].sid+");'>回复</span>";
						html+="<span class='divid'>|</span>";
						var html2="<span id='pl"+ctList[j].sid+"' class='ups' onclick='findDianzan2("+ctList[j].sid+","+b+","+row.sid+","+d+")'>"+ctList[j].plNum+"</span>";
						if(ctList[j].pDianzan){
						  html2="<span id='pl"+ctList[j].sid+"' class='ups' style='background-image:url(dist/images/ups_active.png);' onclick='findDianzan2("+ctList[j].sid+","+b+","+row.sid+","+d+")'>"+ctList[j].plNum+"</span>";
						}
						html+=html2+"</div></div>";
						//回复评论
						html+="<div id='h"+ctList[j].sid+"' style='display:none;' class='editingComment clearfix '>";
						html+="<div class='editContent'>";
						html+="<input type='hidden' value='0'/>";
						html+="<textarea name='reply' id='reply"+ctList[j].sid+"' cols='30' class='commentInput' rows='10'></textarea>";
						html+="<div class='insertSpecial'>";
					    html+="<span class='emoji'></span>";
					    /* html+="<span class='uploadPic'></span>"; */
					    html+="<input class='commentBtn' type='button' onclick='tiJiao("+ctList[j].sid+","+b+","+row.sid+","+d+");' value='回复'></div></div></div>";
						html+="</div>";
					if(ctList[j].hfNum!=0){
						var replyList=ctList[j].replyList;
						if(replyList!=null){
								html+="<div class='partReply'>";
								   html+="<ul class='partReplyList' id='rl"+ctList[j].sid+"'>";
							    for(var n=0;n<replyList.length;n++){
								      html+="<li class='partReplyItem'>";
								         html+="<p class='top'>";
								            if(replyList[n].personId>0){
									            html+="<span class='replyName' id='replyName"+replyList[n].sid+"'>"+replyList[n].userName+" 回复  "+replyList[n].personName+"</span>";
								            }else{
								                html+="<span class='replyName' id='replyName"+replyList[n].sid+"'>"+replyList[n].userName+"</span>";
								            }
								           
								            html+="<span class='replyContent'>:"+replyList[n].content+"</span>";
									     html+="</p>";
									     html+="<p class='bottom'>";
									        html+="<span class='left'>"+replyList[n].creTime+"</span>";
									        html+="<span class='right'>";
									        if(replyList[n].userId==userId){
									        	html+="<span onclick='deleteReply("+replyList[n].sid+","+row.sid+","+ctList[j].sid+")'>删除</span> |";
									        }
									           html+="<span class='reply' onclick='huiFuUserId("+ctList[j].sid+","+replyList[n].userId+","+replyList[n].sid+");'>回复</span>|";
									           if(replyList[n].dianZanReply){
									        	  html+="<span class='star' onclick='findDianzan3("+replyList[n].sid+","+row.sid+","+ctList[j].sid+")' style='background-image:url(dist/images/ups_active.png);'>"+replyList[n].countReply+"</span>";
									           }else{
									        	  html+="<span class='star' onclick='findDianzan3("+replyList[n].sid+","+row.sid+","+ctList[j].sid+")'>"+replyList[n].countReply+"</span>";
									           }
									        html+="</span>";
									     html+="</p>";
									  html+="</li>";
									    html+="<div class='editContent' id='endit"+replyList[n].sid+"' style='display:none;'>";
									    html+="<input type='hidden' value='0'/>";
										html+="<textarea name='reply' id='ply"+replyList[n].sid+"' cols='30' class='commentInput' rows='10'></textarea>";
										html+="<div class='insertSpecial'>";
									    html+="<span class='emoji'></span>";
									    /* html+="<span class='uploadPic'></span>"; */
									    html+="<input class='commentBtn' type='button' onclick='tiJiao2("+replyList[n].sid+","+ctList[j].sid+","+row.sid+");' value='回复'></div></div>";
							}
								  html+="</ul>";
							   html+="</div>";
						}
						//回复的具体内容
						html+="<div class='replyList' id='replyList"+ctList[j].sid+"'>";
						html+="<div class='commentReplyHead'>";
						html+="<span class='userNameFont'>"+ctList[j].hfName+"</span>等人<span class='allReply' onclick='findreplyAll("+ctList[j].sid+","+row.sid+")'>共"+ctList[j].hfNum+"条回复</span></div></div>";
						}
					 } 
				}
				
				html+="</div></div>";	
			}
			$(".blogList").html(html);
		}else{
			$(".blogList").html("");
		}
		if(b==0){
			$("#show").attr("class","active");
			$("#coll").attr("class","");
			$("#guangC").attr("class","");
			$("#myDept").css({"border-bottom":"","background-color":""});
		}
		else if(b==1){
			$("#coll").attr("class","active");
			$("#show").attr("class","");
			$("#guangC").attr("class","");
			$("#myDept").css({"border-bottom":"","background-color":""});
		}
		else if(b==2){
			$("#guangC").attr("class","active");
			$("#show").attr("class","");
			$("#coll").attr("class","");
			$("#myDept").css({"border-bottom":"","background-color":""});
		}else if(b==3){
			$("#guangC").attr("class","");
			$("#show").attr("class","");
			$("#coll").attr("class","");
			$("#myDept").css({"border-bottom":"2px solid #99c1dd","background-color":"#99cdec"});
		}
		//给a标签添加点击事件
		 $(".myword a").click(function () {
	         var content=$(this).html();
	         content=content.substring(1,content.length-1);
	         open("topic.jsp?content="+content); 
	     });
	     $(".atOther a").click(function () {
		      var content=$(this).html();
		      content=content.substring(1,content.length-1);
		      open("topic.jsp?content="+content); 
		 });
	     if(rows!=null){
		     if(rows.length==json.total){
		    	 $("#learnMoreQuery").hide();
		    	 $("#learnMoreNOt").show();
		     }else{
		    	 $("#learnMoreQuery").show();
		    	 $("#learnMoreNOt").hide();
		     }
	     }else{
	    	 $("#learnMoreQuery").hide();
	    	 $("#learnMoreNOt").show();
	     }
	     bF=b;
	     dF=d;
	}
	
	//点赞操作(回复)
	function findDianzan3(sid,infoId,plId){//#ff9800
		var url = "<%=contextPath%>/TeeWeibDianZaiController/findDianZan3.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json!=null){
		  var data=json.rtData;
		  if(data.dianzai){
			  deleteDianzan3(sid);
		  }else{
			  addDianzan3(sid);
		  }
		}
		var length=$("#rl"+plId).children().length;
		query(bF,dF);
		if(length>2){
			findreplyAll(plId,infoId);
		}
		$("#p"+infoId).show(); 
	}
	
	//点赞(回复)
	function addDianzan3(sid){
		var url = "<%=contextPath%>/TeeWeibDianZaiController/addDianZan3.action";
		var json = tools.requestJsonRs(url,{sid:sid});
    }
	//取消点赞(回复)
	function deleteDianzan3(sid){
		var url = "<%=contextPath%>/TeeWeibDianZaiController/deleteDianZan3.action";
		var json = tools.requestJsonRs(url,{sid:sid});
	}
	
	
	//删除评论
	function deletePingLun(sid,wbId){
		  $.MsgBox.Confirm("提示","是否确定删除？",function(){
				var url = "<%=contextPath%>/teeWeibCommentController/deletePingLun.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					$.MsgBox.Alert_auto(json.rtMsg);
					query(bF,dF);
					$("#p"+wbId).show();  
				}else{
					$.MsgBox.Alert_auto(json.rtMsg);
				}
			    return true;
		    });
	}
	//删除回复
	function deleteReply(sid,wbId,plId){
		  $.MsgBox.Confirm("提示","是否确定删除？",function(){
				var url = "<%=contextPath%>/TeeWeibReplyController/deleteReply.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					$.MsgBox.Alert_auto(json.rtMsg);
					var length=$("#rl"+plId).children().length;
					query(bF,dF);
					if(length>2){
						findreplyAll(plId,wbId);
					}
					$("#p"+wbId).show(); 
				}else{
					$.MsgBox.Alert_auto(json.rtMsg);
				}
			    return true;
		    });
	}
	
	//回复某人
	function huiFuUserId(sid,uSid,hfId){
	   $("#ply"+hfId).prev().val(uSid);
	   if($("#endit"+hfId).is(":hidden")){
		   $("#endit"+hfId).show();
	   }else{
		   $("#endit"+hfId).hide();
	   }
	}
	//获取某评论所有的回复
	function findreplyAll(sid,wbId){
		var url = "<%=contextPath%>/TeeWeibReplyController/findReplyAll.action";
		var json = tools.requestJsonRs(url,{plId:sid});
		if(json.rtData!=null){
			var html="";
			var data=json.rtData;
			for(var i=0;i<data.length;i++){
				 html+="<li class='partReplyItem'>";
		         html+="<p class='top'>";
		            if(data[i].personId>0){
			            html+="<span class='replyName' id='replyName"+data[i].sid+"'>"+data[i].userName+"</span><span class='replyName'> 回复  "+data[i].personName+"</span>";
		            }else{
		                html+="<span class='replyName' id='replyName"+data[i].sid+"'>"+data[i].userName+"</span>";
		            }
		           
		            html+="<span class='replyContent'>:"+data[i].content+"</span>";
			     html+="</p>";
			     html+="<p class='bottom'>";
			        html+="<span class='left'>"+data[i].creTime+"</span>";
			        html+="<span class='right'>";
			        if(data[i].userId==userId){
			        	html+="<span onclick='deleteReply("+data[i].sid+","+wbId+","+sid+")'>删除</span> |";
			        }
			           html+="<span class='reply' onclick='huiFuUserId("+sid+","+data[i].userId+","+data[i].sid+");'>回复</span>|";
			           if(data[i].dianZanReply){
				        	  html+="<span class='star' onclick='findDianzan3("+data[i].sid+","+wbId+","+sid+")' style='background-image:url(dist/images/ups_active.png);'>"+data[i].countReply+"</span>";
				           }else{
				        	  html+="<span class='star' onclick='findDianzan3("+data[i].sid+","+wbId+","+sid+")'>"+data[i].countReply+"</span>";
				           }
			        html+="</span>";
			     html+="</p>";
			  html+="</li>";
			  html+="<div class='editContent' id='endit"+data[i].sid+"' style='display:none;'>";
			    html+="<input type='hidden' value='0'/>";
				html+="<textarea name='reply' id='ply"+data[i].sid+"' cols='30' class='commentInput' rows='10'></textarea>";
				html+="<div class='insertSpecial'>";
			    html+="<span class='emoji'></span>";
			    /* html+="<span class='uploadPic'></span>"; */
			    html+="<input class='commentBtn' type='button' onclick='tiJiao2("+data[i].sid+","+sid+","+wbId+");' value='回复'></div></div>";
			}
		   $("#rl"+sid).html(html);
		   $("#replyList"+sid).hide();
		}
	}
	//查看更多
	function findLearnMore(){
		pageSize+=5;
		query(bF,dF);
	}
	//点赞操作(评论)
	function findDianzan2(sid,b,infoId,d){//#ff9800
		var url = "<%=contextPath%>/TeeWeibDianZaiController/findDianZan2.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json!=null){
		  var data=json.rtData;
		  if(data.dianzai){
			  deleteDianzan2(sid);
		  }else{
			  addDianzan2(sid);
		  }
		}
		query(b,d);
		$("#p"+infoId).show(); 
	}
	
	//点赞(评论)
	function addDianzan2(sid){
		var url = "<%=contextPath%>/TeeWeibDianZaiController/addDianZan2.action";
		var json = tools.requestJsonRs(url,{sid:sid});
    }
	//取消点赞(评论)
	function deleteDianzan2(sid){
		var url = "<%=contextPath%>/TeeWeibDianZaiController/deleteDianZan2.action";
		var json = tools.requestJsonRs(url,{sid:sid});
	}
	//回复
	function tiJiao(sid,b,infoId,d){
		var content=$("#reply"+sid).val();
		if(content!=null && content!=""){
			while(content.indexOf('\n') >= 0){
				content = content.replace('\n','<br/>');
			}
			var uId=$("#reply"+sid).prev().val();
			var url = "<%=contextPath%>/TeeWeibReplyController/addReply.action";
			var json = tools.requestJsonRs(url,{infoId:sid,content:content,personId:uId});
			query(b,d);
			$("#p"+infoId).show(); 
			$("#h"+sid).show();
		}
	}
	//回复
	function tiJiao2(hfId,sid,infoId){
		var content=$("#ply"+hfId).val();
		if(content!=null && content!=""){
			while(content.indexOf('\n') >= 0){
				content = content.replace('\n','<br/>');
			}
			var uId=$("#ply"+hfId).prev().val();
			var url = "<%=contextPath%>/TeeWeibReplyController/addReply.action";
			var json = tools.requestJsonRs(url,{infoId:sid,content:content,personId:uId});
			query(bF,dF);
			$("#p"+infoId).show(); 
			$("#h"+sid).show();
		}
	}
	//显示回复输入框
	function huiFu(sid){
		if($("#h"+sid).is(":hidden")){
			$("#h"+sid).show();    //如果元素为隐藏,则将它显现
		}else{
			$("#h"+sid).hide();     //如果元素为显现,则将其隐藏
		}
	}
	//添加评论
	function addReply(sid,b,d){
		var content=$("#r"+sid).val();
		if(content!=null && content!=""){
			while(content.indexOf('\n') >= 0){
				content = content.replace('\n','<br/>');
			}
			var url = "<%=contextPath%>/teeWeibCommentController/addComment.action";
			var json = tools.requestJsonRs(url,{infoId:sid,content:content});
			query(b,d);
			$("#p"+sid).show(); 
		}
	}
	//评论
	function reply(sid){
		if($("#p"+sid).is(":hidden")){
			$("#p"+sid).show();    //如果元素为隐藏,则将它显现
		}else{
			$("#p"+sid).hide();     //如果元素为显现,则将其隐藏
		}
	}
	//收藏操作
	function findCollect(sid,b,d){
		var url = "<%=contextPath%>/TeeWeibConllectController/findCollect.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json!=null){
		  var data=json.rtData;
		  if(data.conllect){
			  deleteCollect(sid);
			  $("#"+sid).css({"background-image":"url(dist/images/star.png)"});
		  }else{
			  addCollect(sid);
			  $("#"+sid).css({"background-image":"url(dist/images/star_active.png)"});
		  }
		}
		query(b,d);
	}
	//收藏
	function addCollect(sid){
		var url = "<%=contextPath%>/TeeWeibConllectController/addCollect.action";
		var json = tools.requestJsonRs(url,{sid:sid});
	}
	//取消收藏
	function deleteCollect(sid){
		var url = "<%=contextPath%>/TeeWeibConllectController/deleteCollect.action";
		var json = tools.requestJsonRs(url,{sid:sid});
	}
	
	//点赞操作
	function findDianzan(sid,b,d){//#ff9800
		var url = "<%=contextPath%>/TeeWeibDianZaiController/findDianZan.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json!=null){
		  var data=json.rtData;
		  if(data.dianzai){
			  deleteDianzan(sid);
		  }else{
			  addDianzan(sid);
		  }
		}
		query(b,d);
	}
	
	//点赞
	function addDianzan(sid){
		var url = "<%=contextPath%>/TeeWeibDianZaiController/addDianZan.action";
		var json = tools.requestJsonRs(url,{sid:sid});
	    
    }
	//取消点赞
	function deleteDianzan(sid){
		var url = "<%=contextPath%>/TeeWeibDianZaiController/deleteDianZan.action";
		var json = tools.requestJsonRs(url,{sid:sid});
	}
	
	//显示转发页面
	function share(sid,b,d){
		var url = "<%=contextPath%>/TeeWeibPublishController/findPublish.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		$(".shortIntro").html("@"+json.rtData);
		$("#infoId").val(sid);
		$("#address").val(b);
		$(".bodyWrap").removeClass('hide');
	}
	//发布信息
	function addPublish(){
	    var children=$(".fabuweibo").children();
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
		var content=$("#content").val();
		if(check()){
			while(content.indexOf('\n') >= 0){
			 content = content.replace('\n','<br/>');
			}
			var url = "<%=contextPath%>/TeeWeibPublishController/addPublish.action";
			var json = tools.requestJsonRs(url,{content:content,img:picIdStr,imgy:picIdStry});
			$("#content").val("");
			window.location.reload();
		}
	}
	//验证
	function check(){
		var content=$("#content").val();
		if(content==null || content==""){
			return false;
		}
		return true;
	}
	function check1(){
		var relay=$("#relay").val();
		if(relay==null || relay==""){
			return false;
		}
		return true;
	}
	//转发
	function relay(){
		 var children=$(".zhuanFaSuoLue").children();
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
	//热门话题
	function topicAll(page){
		var url = "<%=contextPath%>/TeeWeibPublishController/findTopicPage.action";
		var json = tools.requestJsonRs(url,{page:page});
		var rows=json.rows;
		if(rows!=null){
			var html="";
			for(var i=0;i<rows.length;i++){
				html+="<li>";
				html+="<span class='clas'><a href='#'>#"+rows[i].topicContent+"#</a></span>";
				html+="<span class='num'>"+rows[i].pCount+"</span>";
				html+="</li>";
			}
			$(".topicList").html(html);
		}
		//给a标签添加点击事件
		 $(".clas a").click(function () {
	            var content=$(this).html();
	            content=content.substring(1,content.length-1);
	            open("topic.jsp?content="+content); 
	            });
	}
	//热门换题（换一换）
	function nextTopic(){
		page+=1;
		var url = "<%=contextPath%>/TeeWeibPublishController/pageCount.action";
		var json = tools.requestJsonRs(url);
		var data=json.rtData;
		if(data!=null){
			if(page>data){
				page=1;
			}
		}
		topicAll(page);
	}
	//显示删除
	function operationList(sid){
	   var display =$("#d"+sid).css('display');
	   if(display == 'none'){
		   $("#d"+sid).show();
	   }else{
		   $("#d"+sid).hide();
	   }
	}
	//删除微博
	function deletePublishById(sid,b,d){
	    $.MsgBox.Confirm("提示","是否确定删除？",function(){
			var url = "<%=contextPath%>/TeeWeibPublishController/deletePublishById.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				query(b,d);
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		    return true;
	    });
	}
	//首页
	function onHomePage(){
		location.href="<%=contextPath%>/system/core/base/weibo/index.jsp";
	}
	//上传图片
	function ImgUpLoad(){
		var atat=$("#attachid");
		console.log(atat);
	}
	//关闭图片页
	function closePic(){
		$(".image-upload-wrapper").hide();
	}
	//跳转到关注页面
	function followsList(){
		window.open("follows.jsp");
	}
	//跳转到粉丝页面
	function fansList(){
		window.open("fans.jsp");
	}
	</script>
</head>
<body onload="doInit();">
<!-- 	<div class="header">
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
	<div class="main clearfix">
		<div class="menuBar">
			<ul class="mainMenu">
				<li onclick="query(0,0)" class="" id="show">首页</li>
				<li onclick="query(1,0)" id="coll">我的收藏</li>
				<li onclick="query(2,0)" id="guangC">广场</li>
			</ul>
			<span class="border"></span>
			<div class="myDepart" onclick="query(3,0)" id="myDept">我的部门</div>
			<span class="border"></span>
			<div class="allDepart">
				<span class="name">所有部门</span>
				<img class="searchDep" src="dist/images/search_white.png" alt="搜索">
				<input id="search" type="text" onkeyup="searchDept();" class="searchDepInput hide">
				<div class="searchResult hide">
					<div class="resultList">搜索结果</div>
					<ul id="searchList" class="searchResultList">
					
					</ul>
				</div>
				<div class="allDepartWrap hide">
					<div class="head">组织机构</div>
					<ul class="allDepartList" id="allDepartList">
					
					</ul>
				</div>
				<ul class="allDepartList">

				</ul>
			</div>
			<span class="border"></span>
		</div>
		<div class="mainContent">
			<div class="addNewMsg">
				<div class="title">有什么新鲜事想告诉大家?</div>
				<div class="newWordWrap">
					<textarea name="content" id="content" cols="30" rows="10"></textarea>
				</div>
				<div class="otherWrap clearfix">
					<ul class="otherMes">
						<li class="otherMes1">表情</li>
						<li class="otherMes2">图片</li>
						<div class="image-upload-wrapper" style="left: -40px;">
			                <div class="tip-triangle tip-triangle-front"></div>
			                <div class="tip-triangle tip-triangle-back"></div>
			                <div class="image-upload-head">
			                    <p>本地上传<span class="btn-close" onclick="closePic();">关闭</span></p>
			                </div>
			                <div class="tip-text">共 <span id="current-count">9</span> 张，还能上传 <span id="remain-count">0</span> 张（按住Ctrl键上传多张图片）</div>
							<ul class="image-upload-body fabuweibo">
								 
								<li id="btn-add" class="btn-add" style="top: 0px;">+
									<span id="virtualBtn" class="webuploader-container">
										<div id="uploadPhotoBtn" class="webuploader-pick" style="position:relative">
												<div id="uploadPhotoContainer"></div>
												<input style="display: none;" type="text" id="uploadPhotoHolder" name="uploadPhotoHolder"/>
										</div>
									</span>
								</li>
							</ul>
						</div>
						<li class="otherMes3">话题</li>
					</ul>
					
					
					<input type="button" class="btn" value="发布" onclick="addPublish();">
				</div>
			</div>
			<div class="blogList" style="margin-top: 100px;">
			</div>
			  <div id="learnMoreQuery" class="learnMore" onclick="findLearnMore();">查看更多></div>
			  <div id="learnMoreNOt" class="learnMore" style="display: none;">没有更多了</div>
			<div class="sideBar">
				<div class="myInfo" style="position: fixed;top:20px;">
					<div class="myInfoHead">
						<!-- 加上背景图 -->
						<div class="avatar">
							<img id="avatarImg2" style="width: 48px;height: 48px;display: none;" src="<%=contextPath %>/attachmentController/downFile.action?id=<%=avatar%>" alt="">
						    <img id="avatarImg3" style="display: none" src="<%=contextPath %>/system/core/base/weibo/dist/images/blog.png" alt="">
						</div>
					</div>
					<p class="userName" id="userName">设计部张小美</p>
					<ul class="myInfoBody">
						<li onclick="followsList();">
							<div class="followNum" id="countPerson">0</div>
							<p class="infoName">关注</p>
						</li>
						<li onclick="fansList();">
							<div class="followNum" id="countByPerson">0</div>
							<p class="infoName">粉丝</p>
						</li>
						<li>
							<div class="followNum" id="countPublish">0</div>
							<p class="infoName">微博</p>
						</li>
					</ul>
				</div>
				<div class="hotTopic" style="margin-top: 100px;">
					<div class="hotTopicHead">
						<p class="topicHead">热门话题</p>
						<p class="refreshTopic" onclick="nextTopic();">换一换</p>
					</div>
					<ul class="topicList">
						<!-- <li class="listItem">
							<span class="clas">#冬至将至</span>
							<span class="num">102</span>
						</li>
						<li>
							<span class="clas">#冬至将至</span>
							<span class="num">2</span>
						</li>
						<li>
							<span class="clas">#冬至将至</span>
							<span class="num">102</span>
						</li>
						<li>
							<span class="clas">#冬至将至</span>
							<span class="num">205</span>
						</li>
						<li>
							<span class="clas">#冬至将至</span>
							<span class="num">102</span>
						</li>
						<li>
							<span class="clas">#冬至将至</span>
							<span class="num">102</span>
						</li>
						<li>
							<span class="clas">#冬至将至</span>
							<span class="num">102</span>
						</li> -->
					</ul>
					<!-- <p class="hotTopicMore">查看更多></p> -->
				</div>
				<div class="recommend" id="HyH">
					
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
				<textarea name="relay" class="repostReason" id="relay" placeholder="请输入转发理由"></textarea>
				<div class="insertSpecial">
					<span class="emoji"></span>
					<span class="uploadPic"></span>
					<input class="commentBtn" onclick="relay();" type="button" value="转发">
				</div>
					<div class="image-upload-wrapper zhuanFa">
		                <div class="tip-triangle tip-triangle-front"></div>
		                <div class="tip-triangle tip-triangle-back"></div>
		                <div class="image-upload-head">
		                    <p>本地上传<span class="btn-close" onclick="closePic();">关闭</span></p>
		                </div>
		                <div class="tip-text">共 <span id="current-count2">9</span> 张，还能上传 <span id="remain-count2">0</span> 张（按住Ctrl键上传多张图片）</div>
						<ul class="image-upload-body zhuanFaSuoLue">
							 
							<li id="btn-add2" class="btn-add" style="top: 0px;">+
								<span id="virtualBtn2" class="webuploader-container virtualBtn">
									<div id="uploadPhotoBtn2" class="webuploader-pick uploadPhotoBtn" style="position:relative">
											<div id="uploadPhotoContainer2"></div>
											<input style="display: none;" type="text" class="uploadPhotoHolder" id="uploadPhotoHolder2" name="uploadPhotoHolder2"/>
									</div>
								</span>
							</li>
						</ul>
					</div>
			</div>
		</div>
	</div>
<!-- 	 <script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script> -->
<script type="text/javascript" src="dist/js/jquery.qqFace.js"></script>
<script>
	$(function(){
		//点击（图片）
		$(".otherMes2").click(function(event){
			$(".image-upload-wrapper").show();
			if($("#content").val()==null || $("#content").val()==""){
			   $("#content").val("分享图片");
			}
		});
		
		$(".uploadPic").click(function(event){
			$(".zhuanFa").show();
			if($("#relay").val()==null || $("#relay").val()==""){
				$("#relay").val("分享图片");
			}
		});
		$(".allDepart .name").click(function(event) {
			$(".allDepartWrap").toggleClass('hide');
			event.stopPropagation();
			event.cancelBubble = true;
		});
		$(".searchDep").click(function(event) {
			$(".searchDepInput").toggleClass('hide');
			$(".searchResult").toggleClass('hide');
			event.stopPropagation();
			event.cancelBubble = true;
		});
		//点击其他位置隐藏
		$("body").click(function(event) {
			$(".allDepartWrap").addClass('hide');
			$(".searchDepInput").addClass('hide');
			$(".searchResult").addClass('hide');
		});
		/* $(".allDepartWrap").click(function(event) {
			event.stopPropagation();
			event.cancelBubble = true;
		}); */
		$(".searchDepInput").click(function(event) {
			event.stopPropagation();
			event.cancelBubble = true;
		});
	
		$(".repost .close").click(function(event) {
			$(".bodyWrap").addClass('hide');
			$(".image-upload-wrapper").hide();
		});
		$('.otherMes1').qqFace({

			id : 'facebox',

			assign:'content',

			path:'dist/arclist/'	//表情存放的路径

		});
		
		$("body").on("click",".emoji",function(){
			var textId = $(this).parent().siblings(".commentInput").attr("id");
			$(this).qqFace({

				id : 'facebox',

				assign:textId,

				path:'dist/arclist/'	//表情存放的路径

			});
		});
		
		$('.emoji').qqFace({

			id : 'facebox',

			assign:'relay',

			path:'dist/arclist/'	//表情存放的路径

		});
	});
	
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
	
	$(".otherMes3").click(function(event) {
		var content=$("#content").val();
		$("#content").val(content+"#在这里输入你想要说的话题#");
		var length = content.length;
		setSelectionRange($("#content")[0],length + 1,length+13);
		
		//content=$("#content");
		/* $("#content").focus();
		$("#content").select(); */
	});
	  function xmTanUploadImg(obj) {  
	        var fl=obj.files.length;  
	    }
</script>
</body>
</html>