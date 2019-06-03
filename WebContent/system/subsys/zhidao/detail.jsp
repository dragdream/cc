<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知道</title>
<%@ include file="/header/header2.0.jsp" %>

<style>
body{
	font-family:微软雅黑;
}
ul{
	list-style:none;
	margin:0px;
	padding:0px;
}
li{
	
}
.selected{
	font-weight:bold;
	color:#379ff6;
	border-bottom:2px solod #379ff6;
}
.item01{
	width:130px;
	padding:10px;
	float:left;
}
.childItem{
	padding:2px;
}
.result{
	margin-bottom:40px;
}
.result .title{
	font-size:14px;
	color:#0440de;
	font-weight:normal;
}
.result .content{
	margin-top:5px;
	font-size:12px;
	color:#505050;
	font-weight:normal;
	line-height:18px;
}
.hottable td{
	height:20px;
	color:#043edd;
}
.hot{
	color:#fe6665;
	font-weight:bold;
}
a:link {
COLOR: #000; TEXT-DECORATION: none
}

#navigation span:hover{
   COLOR: #3388ff; TEXT-DECORATION: none
}
</style>
<script>
var sid=<%=sid %>;
var status=0;//0未解决  1已解决
var title="";//问题标题
var loginUserUuid=<%=loginUser.getUuid() %>;
var createUserId=0;//问题创建人
var catId=0;//当前问题的所属分类
//初始化
function doInit(){
	//改变问题的点击量
	updateClick();
	//获取问题详情
	getInfoBySid();	
	//根据问题状态  获取最佳回答或者按钮
	getBest();
	//获取其他回答
	getOtherAnswer();
	//获取相关问题
	getRelations();
	
	
}


//获取相关问题  前10
function getRelations(){
	var url=contextPath+"/zhiDaoQuestionController/getRelationsByTitle.action";
	var json=tools.requestJsonRs(url,{title:title,page:1,pageSize:10,sid:sid});
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				html.push("<tr style=\"cursor:pointer;\" onclick=\"detail("+data[i].sid+")\">");
				if(i==0||i==1||i==2){
					html.push("<td class=\"hot\" width='15px'>"+(i+1)+".</td>");
				}else{
					html.push("<td  width='15px'>"+(i+1)+".</td>");
				}
				
				html.push("<td style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;' nowrap>"+data[i].title+"</td>");
				if(i==0||i==1||i==2){
					html.push("<td width='15px'><img src=\"img/icon4.png\" /></td>");
				}else{
					html.push("<td width='15px'></td>");
				}
				
				html.push("<td width='20px' style='text-align:right'>"+data[i].clickCount+"</td>");
				html.push("</tr>");
			}
		}
		$("#relations").append(html.join(""));
	}
}

//相关问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/zhidao/detail.jsp?sid="+sid;
	openFullWindow(url);
}

//改点点击量
function updateClick(){
	var  url=contextPath+"/zhiDaoQuestionController/updateClick.action";
	var json=tools.requestJsonRs(url,{sid:sid});
}

//获取问题详情
function getInfoBySid(){
	var url=contextPath+"/zhiDaoQuestionController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		//根据分类主键   获取导航栏信息
		getNavigation(data.catId);
		status=data.status;//0=未解决  1=已解决
		var statusDesc="";
		if(status==0){
			statusDesc="<img src=\"img/icon_no.png\" style=\"width:15px;float:left\"/>&nbsp;&nbsp;未解决";
		}else{
			statusDesc="<img src=\"img/icon_yes.png\" style=\"width:15px;float:left\"/>&nbsp;&nbsp;已解决";
		}
		$("#statusDesc").append(statusDesc);
		
		title=data.title;
		createUserId=data.createUserId;
		catId=data.catId;
		
		if(data.attachMentModel.length>0){
			$("#attachDiv").show();
			var attaches = data.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
		//判断问题是否可以删除    （1.分类管理员可以无条件删除   2.未解决的状态  问题的创建人可以删除）
		var isManager=checkIsManager(data.catId);
		if(isManager==1){//是分类管理员
			$("#deleteQuestionSpan").show();
		}else{//不是分类管理员
			if(data.status==0&&loginUserUuid==data.createUserId){
				$("#deleteQuestionSpan").show();
			}else{
				$("#deleteQuestionSpan").hide();
			}
		}
	}
}


//刪除問題
function deleteQuestion(){
	$.MsgBox.Confirm ("提示", "删除后不可恢复，是否确认删除该问题？", function(){
		var url=contextPath+"/zhiDaoQuestionController/delBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！",function(){
				window.opener.location.reload();
				window.close();
				
			});
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}   
	 });
}

//删除回复
function deleteAnswer(sid){
	$.MsgBox.Confirm ("提示", "删除后不可恢复，是否确认该回答？", function(){
		var url=contextPath+"/zhiDaoAnswerController/delBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！",function(){
				window.location.reload();
			});
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}   
	 });
}




//判断当前登陆人是不是当前问题所属分类的管理人员
function checkIsManager(catId){
	var url=contextPath+"/zhiDaoCatController/isManager.action";
	var json=tools.requestJsonRs(url,{catId:catId});
	if(json.rtState){
		var data=json.rtData; //1=管理员  0=不是管理员
		return data;
	}
	
}


//根据问题状态  获取最佳回答或者按钮
function getBest(){
	if(status==0){//未解决
		$("#answerButtonDiv").show();
	}else{//已解决
		$("#bestDiv").show();
	    //获取最佳答案
	    var url=contextPath+"/zhiDaoAnswerController/getBestAnswer.action";
	    var json=tools.requestJsonRs(url,{questionSid:sid});
	    if(json.rtState){
	    	var data=json.rtData;
	    	var html=[];
	    	html.push("<table style=\"width:100%\">");
	    	html.push("<tr>");
	    	html.push("<td rowspan=\"2\" style=\"width:50px\">");
	    	if(data.avatar){
				html.push("<img style=\"width:36px;height:36px;\" src=\"/attachmentController/downFile.action?id="+data.avatar+"\" />");
			}else{//默认头像
				html.push("<img src=\"img/icon_default.png\" />");
			}
	    	
	    	html.push("</td>");
	    	html.push("<td>"+data.createUserInfo+"</td>");
	    	html.push("<td rowspan=\"2\" style=\"text-align:right\">");
	    	html.push("<span  style=\"line-height:30px;color:red;font-weight:bold\">满意答案&nbsp;&nbsp;</span><img style=\"float:right\" src=\"img/icon_best.png\" />");
	    	html.push("</td>");
	    	html.push("</tr>");
	    	html.push("<tr>");
	    	var isManager=checkIsManager(catId);
	    	if(isManager==1){//是分类管理员
	    		html.push("<td style=\"color:#9b9b9b\">"+data.createTimeStr+"&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"cursor:pointer;\"  onclick=\"deleteAnswer("+data.sid+")\">删除</span></td>");
	    	}else{
	    		html.push("<td style=\"color:#9b9b9b\">"+data.createTimeStr+"</td>");
	    	}
	    	
	    	html.push("</tr>");
	    	html.push("</table>");	
			html.push("<div>");	
			html.push(data.content);	
			html.push("</div>");		
			var spanId="attach_"+data.sid;
			if(data.attachMentModel.length>0){
				html.push("<div style='padding-top:10px'>");
				html.push("<span id="+spanId+" >");
				html.push("</span>");
				html.push("</div>");
			}
			
		    $("#bestDiv").append(html.join(""));
		    
		    if(data.attachMentModel.length>0){
            	var attaches = data.attachMentModel;
				for(var n=0;n<attaches.length;n++){
					var fileItem = tools.getAttachElement(attaches[n]);
					$("#"+spanId).append(fileItem);
				}
            }
	    }
	}
	
}

//根据分类主键  获取小导航栏信息
function getNavigation(catId){
	var url=contextPath+"/zhiDaoCatController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:catId});
	if(json.rtState){
		var data=json.rtData;
		var html="<span style=\"cursor:pointer\" onclick=\"window.location.href='index.jsp'\">首页</span> > ";
		if(data.parentCatId!=0){
			html+="<span oclick=\"catSearch("+data.parentCatId+",'"+data.parentCatName+"');\"  style=\"cursor:pointer\" >"+data.parentCatName+"</span> > <span onclick=\"catSearch("+data.sid+",'"+data.catName+"')\" style=\"cursor:pointer\">"+data.catName+"</span>";
		}else{
			html+="<span onclick=\"catSearch("+data.sid+",'"+data.catName+"')\"  style=\"cursor:pointer\">"+data.catName+"</span>";
		}
		$("#navigation").append(html);
	}
}

//根据分类进行检索
function catSearch(catId,catName){
	window.location.href="search.jsp?catId="+catId+"&&catName="+catName;
}


//获取其他回答的信息
function getOtherAnswer(){
	var url=contextPath+"/zhiDaoAnswerController/getOtherAnswer.action";
	var json=tools.requestJsonRs(url,{questionSid:sid});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				var html=[];
				html.push("<div style=\"margin-top:20px;border-top:1px solid #9b9b9b;padding-top:10px;\">");
				html.push("<table style=\"width:100%\">");
				html.push("<tr>");
				html.push("<td rowspan=\"2\" style=\"width:50px\">");
				
				if(data[i].avatar){
					html.push("<img style=\"width:36px;height:36px;\" src=\"/attachmentController/downFile.action?id="+data[i].avatar+"\" />");
				}else{//默认头像
					html.push("<img src=\"img/icon_default.png\" />");
				}
				
				html.push("</td>");
				html.push("<td>"+data[i].createUserInfo+"</td>");
				html.push("<td rowspan=\"2\" style=\"text-align:right\">");
				if(status==0 && loginUserUuid==createUserId){//未解决  并且当前登录人是问题的创建人
					html.push("<span onclick=\"adopt("+data[i].sid+");\"><span  style=\"line-height:26px;color:green;font-weight:bold\">采纳</span><img style=\"float:right\" src=\"img/icon_caina.png\" /></span>");
				}
				html.push("</td>");
				html.push("</tr>");
				html.push("<tr>");
				
				//判断该回答是否可以删除
			    var  isManager=checkIsManager(catId);
				if(isManager==1){
					html.push("<td style=\"color:#9b9b9b\">"+data[i].createTimeStr+"&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"cursor:pointer;\" onclick=\"deleteAnswer("+data[i].sid+")\">删除</span></td>");
				}else{
					if(status==0 && loginUserUuid==data[i].createUserId){
						html.push("<td style=\"color:#9b9b9b\">"+data[i].createTimeStr+"&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"cursor:pointer;\" onclick=\"deleteAnswer("+data[i].sid+")\">删除</span></td>");
					}else{
						html.push("<td style=\"color:#9b9b9b\">"+data[i].createTimeStr+"</td>");
					}
				}
				
				
				
				
				
				
				html.push("</tr>");
				html.push("</table>");
				html.push("<div>");
				html.push(data[i].content);
				html.push("</div>");
				
				var spanId="attach_"+data[i].sid;
				if(data[i].attachMentModel.length>0){
					//alert(1);
					html.push("<div style='padding-top:10px'>");
					html.push("<span id="+spanId+" >");
					html.push("</span>");
					html.push("</div>");
				}
				
				
				html.push("</div>");
				$("#otherAnswer").append(html.join(""));
				
                if(data[i].attachMentModel.length>0){
                	var attaches = data[i].attachMentModel;
    				for(var n=0;n<attaches.length;n++){
    					var fileItem = tools.getAttachElement(attaches[n]);
    					$("#"+spanId).append(fileItem);
    				}
                }
				
				
			}
			
		}else{
			$("#otherAnswerMess").show();
			messageMsg("暂无其他相关回答！","otherAnswerMess","info");
		}
		
		
	}
}


//我来回答
function answer(){
	var url=contextPath+"/system/subsys/zhidao/answer.jsp?questionSid="+sid;
	bsWindow(url ,"我来回答",{width:"900",height:"400",buttons:
		[
         {name:"提交",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="提交"){
		    var json=cw.save();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("提交成功！",function(){
		    		window.location.reload();
		    	});
		    }else{
		    	$.MsgBox.Alert_auto("提交失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 采纳
 */
function adopt(answerSid){
	 $.MsgBox.Confirm ("提示", "是否确认采纳该回答？", function(){
		 var url=contextPath+"/zhiDaoQuestionController/adopt.action";
		 var json=tools.requestJsonRs(url,{sid:sid,answerSid:answerSid});
		 if(json.rtState){
			 $.MsgBox.Alert_auto("采纳成功！",function(){
				 window.location.reload();
			 });
		 }
	  });
}

//检索
function search(){
	var keyWord=$("#keyWord").val();
	if(keyWord!=null&&keyWord!=""){//输入关键字则进入检索页面
		window.location.href="search.jsp?keyWord="+encodeURI(keyWord);
	}
}
</script>
</head>

<body onload="doInit();" style="padding:10px;overflow-y:auto ">
	<div style="border-bottom:2px solid #73c5ff;padding:4px">
		<div style="float:left">
			<img src="img/icon_zhidao.png" />
		</div>
		<div style="font-size:15px;font-family:微软雅黑;float:left;margin-left:7px;font-weight:bold;">
			知道
		</div>
		<div style="float:right">
			<input name="keyWord" id="keyWord" style="width:250px;border:1px solid #b8b8b8;height:24px;margin:0px;padding:0px;border-right:0px;" /><button onclick="search();" style="border:1px solid #3388ff;background:#3388ff;padding:5px;width:80px;font-size:12px;color:#fff;font-weight:bold">检&nbsp;&nbsp;索</button>
			<button style="border:1px solid #317df4;background:white;padding:5px;width:80px;font-size:12px;color:#317df4;font-weight:bold" onclick="window.location.href='addQuestion.jsp'">我要提问</button>
		</div>
		<div style="clear:both"></div>
	</div>
	<div style="margin-left:10px;margin-top:5px;">
		<span style="color:#999999;margin-right:5px;" id="navigation" name="navigation"></span>
	</div>
	<div style="position:absolute;top:70px;left:10px;right:290px;">
		<div style="padding:10px;border:1px solid #bfbfbf;">
			<div id="statusDesc" name="statusDesc"></div>
			<div style="font-size:16px;margin-top:10px;font-weight: bold;" id="title" name="title"></div>
			<div style="font-size:12px;margin-top:5px;" id="description" name="description">
			</div>
			<div id="attachDiv" style="display: none;padding-top: 10px">
			<span id="attachments"></span>
			</div>
			<div style="margin-top:5px;color:#9b9b9b">提问者：<span id="createUserInfo" name="createUserInfo"></span>&nbsp;&nbsp;&nbsp;&nbsp;提问时间：<span id="createTimeStr" name="createTimeStr"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span id="deleteQuestionSpan"   style="display: none;cursor: pointer;"  onclick="deleteQuestion();">删除</span></div>
		
			<div style="display:none;margin-top:20px;border-top:1px solid #9b9b9b;padding-top:10px;" id="bestDiv">
				
			</div>
			<div style="display:none;margin-top:20px;border-top:1px solid #9b9b9b;padding-top:10px;" id="answerButtonDiv">
				
			    <img alt="" src="/system/subsys/zhidao/img/icon_answer.png" onclick="answer();">
			</div>
		</div>
		
		
		
		<div style="margin-top:10px;padding:10px;border:1px solid #bfbfbf; " id="otherAnswer">
			<div><img src="img/icon_other.png" style="width:15px;float:left"/>&nbsp;&nbsp;其他回答</div>
			<div id="otherAnswerMess" style="display: none;"></div>
		</div>
	</div>
	<div style="position:absolute;width:250px;top:70px;right:10px;padding:10px;border:1px solid #bfbfbf;">
		<div style="font-size:12px;font-weight:bold;margin-bottom:10px;"><img src="img/icon5.png" style="width:15px;float:left"/>&nbsp;&nbsp;相关问题</div>
		<table style="width:100%;table-layout: fixed;" class="hottable" id="relations">
			
		</table>
	</div>
</body>

</html>