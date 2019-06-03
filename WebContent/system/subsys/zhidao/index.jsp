<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   //是否是系统管理员
   boolean isAdmin=TeePersonService.checkIsAdminPriv(loginUser);

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
.listtable{
table-layout: fixed;
}
.listtable td{
	height:20px;
}
.listtable .timetd{
	width:110px;
	text-align:right;
}
.listtable .cattd{
	width:100px;
	text-align:right;
	color:green;
}
.childItem{
	margin-right:5px;
}
a:link {
COLOR: #000; TEXT-DECORATION: none
}
.listtable  a:hover {
COLOR: #3388ff; TEXT-DECORATION: none
}
a:visited {
COLOR: #000; TEXT-DECORATION: none
}
</style>
<script type="text/javascript">
var isAdmin=<%=isAdmin %>;
//初始化
function doInit(){
  //渲染分类
  renderCat();
  //获取我的问题
  renderMyQuestion();
  //渲染我的参与
  myParticipate();
  //获取最近待解决的问题
  renderNoHandledQuestion();
  //获取最近已解决的问题
  renderHandledQuestion();
  //获取待解决和已解决的问题数量
  getHandledCount();
  //判断当前登陆人是不是系统管理员
  if(isAdmin){
	$("#htgl").show();  
  }else{
	$("#htgl").hide();  
  }
}

//后台管理
function toHouTai(){
	window.location.href=contextPath+"/system/subsys/zhidao/houtai/index.jsp";
}

//我要提问
function askQuestion(){
	var url=contextPath+"/system/subsys/zhidao/addQuestion.jsp";
	window.location.href=url;
}

//获取待解决和已解决的问题数量
function getHandledCount(){
	var url=contextPath+"/zhiDaoQuestionController/getHandledAndNoHandledCount.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var handledCount=data.handledCount;
		var noHandledCount=data.noHandledCount;
		$("#handledCount").text(handledCount);
		$("#noHandledCount").text(noHandledCount);
	}
}

//问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/zhidao/detail.jsp?sid="+sid;
	openFullWindow(url);
}

//渲染我的问题（最多五条）
function renderMyQuestion(){
	var url=contextPath+"/zhiDaoQuestionController/getMyQuestion.action";
	var json=tools.requestJsonRs(url,{rows:5,page:1});
	var data=json.rows;
	var html=[];
	if(data!=null&&data.length>0){
		for(var i=0;i<data.length;i++){
			html.push("<tr>");
			html.push("<td style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;' nowrap><a href=\"#\" onclick=\"detail("+data[i].sid+")\" >"+data[i].title+"</a></td>");
			html.push("<td class=\"cattd\">【"+data[i].catName+"】</td>");
			html.push("<td class=\"timetd\">"+data[i].createTimeStr+"</td>");
			html.push("</tr>");
		}
		$("#myQuestion").append(html.join(""));
	}else{
		html.push("<tr>");
		html.push("<td><div id=\"myQuestionMess\" style=\"margin-top:10px\"></div></td>");
		html.push("</tr>");
		
		$("#myQuestion").append(html.join(""));
		messageMsg("暂无数据！","myQuestionMess","info");
	}
}

//我的问题更多
function moreMyQuestion(){
	var url=contextPath+"/system/subsys/zhidao/myQuestion.jsp";
	openFullWindow(url);
}

//渲染我的参与
function myParticipate(){
	var url=contextPath+"/zhiDaoQuestionController/getMyParticipate.action";
	var json=tools.requestJsonRs(url,{rows:5,page:1});
	var data=json.rows;
	var html=[];
	if(data!=null&&data.length>0){
		for(var i=0;i<data.length;i++){
			html.push("<tr>");
			html.push("<td style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;' nowrap><a href=\"#\" onclick=\"detail("+data[i].sid+")\" >"+data[i].title+"</a></td>");
			html.push("<td class=\"cattd\">【"+data[i].catName+"】</td>");
			html.push("<td class=\"timetd\">"+data[i].createTimeStr+"</td>");
			html.push("</tr>");
		}
		$("#myParticipate").append(html.join(""));
	}else{
		html.push("<tr>");
		html.push("<td><div id=\"myParticipateMess\" style=\"margin-top:10px\"></div></td>");
		html.push("</tr>");
		
		$("#myParticipate").append(html.join(""));
		messageMsg("暂无数据！","myParticipateMess","info");
	}
}

//更多我的参与
function moreMyParticipate(){
	var url=contextPath+"/system/subsys/zhidao/myParticipate.jsp";
	openFullWindow(url);
}

//最近待解决问题
function renderNoHandledQuestion(){
	var url=contextPath+"/zhiDaoQuestionController/getNoHandledQuestion.action";
	var json=tools.requestJsonRs(url,{rows:5,page:1});
	var data=json.rows;
	var html=[];
	if(data!=null&&data.length>0){
		for(var i=0;i<data.length;i++){
			html.push("<tr>");
			html.push("<td style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;' nowrap><a href=\"#\"  onclick=\"detail("+data[i].sid+")\">"+data[i].title+"</a></td>");
			html.push("<td class=\"cattd\">【"+data[i].catName+"】</td>");
			html.push("<td class=\"timetd\">"+data[i].createTimeStr+"</td>");
			html.push("</tr>");
		}
		$("#noHandledQuestion").append(html.join(""));
	}else{
		html.push("<tr>");
		html.push("<td><div id=\"noHandledQuestionMess\" style=\"margin-top:10px\"></div></td>");
		html.push("</tr>");
		
		$("#noHandledQuestion").append(html.join(""));
		messageMsg("暂无数据！","noHandledQuestionMess","info");
	}
}

//更多待解决的问题
function moreNoHandledQuestion(){
	var url=contextPath+"/system/subsys/zhidao/noHandledQuestion.jsp";
	openFullWindow(url);
}

//最近已解决问题
function renderHandledQuestion(){
	var url=contextPath+"/zhiDaoQuestionController/getHandledQuestion.action";
	var json=tools.requestJsonRs(url,{rows:5,page:1});
	var data=json.rows;
	var html=[];
	if(data!=null&&data.length>0){
		for(var i=0;i<data.length;i++){
			html.push("<tr>");
			html.push("<td style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;' nowrap><a href=\"#\"  onclick=\"detail("+data[i].sid+")\" >"+data[i].title+"</a></td>");
			html.push("<td class=\"cattd\">【"+data[i].catName+"】</td>");
			html.push("<td class=\"timetd\">"+data[i].createTimeStr+"</td>");
			html.push("</tr>");
		}
		$("#handledQuestion").append(html.join(""));
	}else{
		html.push("<tr>");
		html.push("<td><div id=\"handledQuestionMess\" style=\"margin-top:10px\"></div></td>");
		html.push("</tr>");
		
		$("#handledQuestion").append(html.join(""));
		messageMsg("暂无数据！","handledQuestionMess","info");
	}
}


//更多已解决的问题
function moreHandledQuestion(){
	var url=contextPath+"/system/subsys/zhidao/handledQuestion.jsp";
	openFullWindow(url);
}


//渲染分类
function renderCat(){
	var url=contextPath+"/zhiDaoCatController/getAllCat1.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			
			for(var i=0;i<data.length;i++){
				var parent=data[i].parent;
				var children=data[i].children;
				
				html.push("<div style=\"margin-bottom:7px;\">");
				html.push("<p onclick=\"catSearch("+parent.sid+",'"+parent.catName+"')\" style=\"font-weight:bold;font-size:12px;cursor:pointer;\">"+parent.catName+"</p>");
			    if(children!=null&&children.length>0){
			    	for(var j=0;j<children.length;j++){
			    	    html.push("<a onclick=\"catSearch("+children[j].sid+",'"+children[j].catName+"')\" style=\"cursor:pointer;\" class=\"childItem\">"+children[j].catName+"</a>");
			    	}
			    }
			    html.push("</div>");
			}
		}
		$("#catDiv").append(html.join(""));
	}
}



//根据分类进行检索
function catSearch(catId,catName){
	window.location.href="search.jsp?catId="+catId+"&&catName="+catName;
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

<body style="padding:10px" onload="doInit();">
	<div style="border-bottom:2px solid #73c5ff;padding:4px">
		<div style="float:left">
			<img src="img/icon_zhidao.png" />
		</div>
		<div style="font-size:15px;font-family:微软雅黑;float:left;margin-left:7px;font-weight:bold;">
			知道
		</div>
		<div style="float:right">
			<input id="keyWord" name="keyWord" style="width:250px;border:1px solid #b8b8b8;height:24px;margin:0px;padding:0px;border-right:0px;" /><button style="border:1px solid #3388ff;background:#3388ff;padding:5px;width:80px;font-size:12px;color:#fff;font-weight:bold" onclick="search();">检&nbsp;&nbsp;索</button>
			<button style="border:1px solid #317df4;background:white;padding:5px;width:80px;font-size:12px;color:#317df4;font-weight:bold" onclick="askQuestion();">我要提问</button>
			<a href="#" onclick="toHouTai();" id="htgl" style="display: none;">后台管理</a>
		</div>
		<div style="clear:both"></div>
	</div>
	<div style="position:absolute;top:60px;left:10px;right:270px">
		<div style="padding:10px;border:1px solid #e5e5e5">
			<div style="background-image:url('img/topbg.png');background-repeat:no-repeat;height:32px;line-height:32px;color:#fff;padding-left:10px;border-bottom:2px solid #2165b8"><span>我的问题</span><span style="float:right;color:#3389ff;cursor: pointer;" onclick="moreMyQuestion();">更多&gt;</span></div>
			<table style="width:100%" class="listtable" id="myQuestion">
			</table>
		</div>
		<br/>
		<div style="padding:10px;border:1px solid #e5e5e5">
			<div style="background-image:url('img/topbg.png');background-repeat:no-repeat;height:32px;line-height:32px;color:#fff;padding-left:10px;border-bottom:2px solid #2165b8"><span>我的参与</span><span style="float:right;color:#3389ff;cursor:pointer;" onclick="moreMyParticipate();">更多&gt;</span></div>
			<table style="width:100%" class="listtable" id="myParticipate">
				
			</table>
		</div>
		<br/>
		<div style="padding:10px;border:1px solid #e5e5e5">
			<div style="background-image:url('img/topbg.png');background-repeat:no-repeat;height:32px;line-height:32px;color:#fff;padding-left:10px;border-bottom:2px solid #2165b8"><span>最近待解决问题</span><span style="float:right;color:#3389ff;cursor: pointer;" onclick="moreNoHandledQuestion();">更多&gt;</span></div>
			<table style="width:100%" class="listtable" id="noHandledQuestion">
				
			</table>
		</div>
		<br/>
		<div style="padding:10px;border:1px solid #e5e5e5">
			<div style="background-image:url('img/topbg.png');background-repeat:no-repeat;height:32px;line-height:32px;color:#fff;padding-left:10px;border-bottom:2px solid #2165b8"><span>最近已解决问题</span><span style="float:right;color:#3389ff;cursor: pointer;" onclick="moreHandledQuestion();">更多&gt;</span></div>
			<table style="width:100%" class="listtable" id="handledQuestion">
				
			</table>
		</div>
	</div>
	<div style="position:absolute;width:250px;top:60px;right:10px;">
		<div style="border:1px solid #e5e5e5;padding:10px;background-repeat:no-repeat;background-image:url('img/bg1.png')">
			<div style="font-weight:bold;font-size:14px"><img src="img/icon1.png" style="width:15px"/>&nbsp;&nbsp;问题分类</div>
			<div style="border-bottom:1px solid #e5e5e5;height:75px;margin-top:30px;margin-bottom:15px">
				<div style="margin-left:25px;float:left;height:70px;width:70px;text-align:center;cursor: pointer;" onclick="moreHandledQuestion();">
					<p style="color:green;font-weight:bold;font-size:16px" id="handledCount"  >233</p>
					<p style="font-size:14px;margin-top:5px">已解决</p>
				</div>
				<div style="margin-right:25px;float:right;height:70px;width:70px;text-align:center;cursor: pointer;" onclick="moreNoHandledQuestion();">
					<p style="color:#fe1213;font-weight:bold;font-size:16px" id="noHandledCount" >521</p>
					<p style="font-size:14px;margin-top:5px">待解决</p>
				</div>
				<div style="clear:both"></div>
			</div>
			<div id="catDiv">
				
			</div>
		</div>
	</div>
</body>

</html>