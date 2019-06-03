<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.oa.oaconst.TeeConst" %>
<%@page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
	String isLooked = request.getParameter("isLooked") == null ? "0" : request.getParameter("isLooked") ;
	TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int userId = loginPerson.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新闻详细</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var userId = '<%=userId%>';
var id = '<%=id%>';
var isLooked = "<%=isLooked%>";
var contextPath = "<%=contextPath %>";
var systemImagePath = "<%=systemImagePath%>";
var replayPriv = 0; //0 实名 1 匿名 2 进制评论
function doInit(){
	getNews();
	if(replayPriv && replayPriv == 2){
		$("#commentReplay").hide();
	}else{
		getAllNewsComments();
		
	}
	
}
//获取新闻 zhp 20130302
function getNews(){
	var url = "<%=contextPath%>/teeNewsController/getNews.action?isLooked="+isLooked;
	var para = {"id":id};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		var subject =  data.subject;//标题
		var content = data.content;//内容
		var fromPersonName = data.provider1;//发布人
		var sendTime = getFormatDateStr(data.sendTime , 'yyyy-MM-dd HH:mm');//发布时间
		var fromDeptName = data.fromDeptName;//发布部门
		$("#subject").html(subject);
		$("#content").html(content);
		$("#fromPersonName").html(fromPersonName);
		$("#sendTime").html(sendTime);
		$("#fromDeptName").html(fromDeptName);
		var anonymityYn = data.anonymityYn;
		replayPriv = anonymityYn;
		$.each(data.attachmentsMode,function(i,v){
			v['priv'] = 3;//查看、下载
			var attachElement = tools.getAttachElement(v,{});
			$("#attachList").append(attachElement);
	    });
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
//获取 所有新闻评论 zhp 20140302
function getAllNewsComments(state,count){
	var url = "<%=contextPath%>/teeNewsCommentController/getNewsAllComment.action";
	var para = {"id":id};
	para['state'] = state;
	para['count'] = count;
	var jsonRs = tools.requestJsonRs(url,para);
	var targetDom = $("#comments");
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		//var sendTime = getFormatDateStr(data.sendTime , 'yyyy-MM-dd HH:mm');//发布时间
	    var trTem1 ="<tr style='padding:10px;box-sizing:border-box;'>"+
	      "<td  colspan=\"2\" valign=\"top\" style='padding:5px 0'>"+
		      "<div style='background-color:#fffce9;border-radius:7px;width:100%;' class=\"panel panel-default\">"+
			  "<div class=\"panel-heading\">"+
				  "<table border=\"0\" cellpadding=\"0\" width=\"100%\">"+
				 	"<tr>"+
				 		"<td style='padding:5px;' align=\"left\"> #<span>{sortId}</span>"+
				 		 "&nbsp;评论人：{nickName}&nbsp;&nbsp;"+
					     "时间：{strReTime}"+
				 		"</td>"+
				 		"<td style='padding:5px;' align=\"right\">"+
				 			" <a href='#' style='float:right;margin-right:10px;' value=\"删除\" onclick=\"deleteById('{sid}')\">删除</a>"+
				 		"</td>"+
				 	"</tr>"+
				" </table>"+
			  "</div>"+
				  "<div style='padding:10px;'>"+
				   "{content}"+
				  "</div>"+
			" </div>"+
	      "</td>"+
	    " </tr>";
	 var liArray = new Array();
		if(data){
			$.each(data,function(key, val){
					var	str = FormatModel(trTem1,val);
					liArray.push(str);
			});
		}
		targetDom.html(liArray.join(''));
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

function deleteById(id) {
    var submit = function (v, h, f) {
        if (v == true)
        	delSigle(id);
        return true;
    };
  var delSigle =  $.MsgBox.Confirm ("提示", "确定删除所选新闻评论,删除后将不可恢复！",function(){
	  
    		var url = "<%=contextPath %>/teeNewsCommentController/deleteNewsComment.action";
    		var jsonRs = tools.requestJsonRs(url,{"id":id});
    		if(jsonRs.rtState){
    			$.MsgBox.Alert_auto("删除成功！");
    			 getAllNewsComments();
    		}else{
    			$.MsgBox.Alert_auto(jsonRs.rtMsg);
    		}
    });
  //  $.MsgBox.Confirm("提示","确定删除所选新闻评论,删除后将不可恢复！", "确认删除？", submit, { id:'s123', showScrolling: false, buttons: { '确定': true, '取消': false } });
}
//删除 该 新闻全部评论 zhp
function deleteAllConment() {
    var submit = function (v, h, f) {
        if (v == true)
        	delAllConment(id);
        return true;
    };
  var delAllConment = $.MsgBox.Confirm ("提示", "确定删除所选新闻评论,删除后将不可恢复！",function(){
    		var url = "<%=contextPath %>/teeNewsCommentController/deleteNewsAllComment.action";
    		var jsonRs = tools.requestJsonRs(url,{"id":id});
    		if(jsonRs.rtState){
    			$.MsgBox.Alert_auto("删除成功！");
    			 getAllNewsComments();
    		}else{
    			$.MsgBox.Alert_auto(jsonRs.rtMsg);
    		}
    });
   // jBox.confirm("确定删除新闻全部评论,删除后将不可恢复！", "确认删除？", submit, { id:'s123', showScrolling: false, buttons: { '确定': true, '取消': false } });
}

</script>
 <style>
 	#commentReplay button{
 		margin-right:10px;
 	}
 </style>
</head>
<body onload="doInit()" style="padding: 20px;background-color: #f2f2f2">
<div class="clearfix" style="position:static;background-color: #e8ecf9;border-bottom: 1px solid #e5e5e5;line-height: 35px; padding-left: 10px;" >
	<span class="fl" style="font-size:18px;">新闻标题：<span id="subject"></span></span>
	<%-- <img class="fr" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/xwfb/icon-save.png" style="cursor:pointer;margin:12px 10px 0px 10px;"  title="添加收藏夹" id="addFav"/> --%>
</div>
<div style="margin-top: 10px; overflow:auto;overflow-x:hidden;min-height:330px;">
<table style='border:#dddddd 0px solid;' width='100%'>
   <tr>
   <td > 
	<center style="color:#aaa5a5;">
		发布部门：</span>&nbsp;<span  id="fromDeptName"></span>&nbsp;&nbsp;
		发布人：</span>&nbsp;<span  id="fromPersonName"></span>&nbsp;&nbsp;
		发布于：</span>&nbsp;<span id="sendTime"></span>
	</center>
	<span class="basic_border_grey fl" style="margin:10px 0;border-bottom: 1px solid #d8d8d8;"></span>
	<br/>
 </td>
 </tr>
 <tr style="">
      <td style="padding: 0 10px;"  colspan="2" valign="top" >
      <span id="content" style="font-size:14px;"></span>
	   </td>
    </tr>
    <tr>
      <td  style="text-indent: 10px;"  colspan="2" valign="top">
      	<br/>
      	<fieldset>
      		<legend style="font-size:15px;">附件：</legend>
      		<span class="basic_border_grey fl" style="margin:10px 0;border-bottom: 1px solid #d8d8d8;"></span>
      	</fieldset>
	      <div id="attachList" style="padding-bottom: 10px;" ></div>
      </td>
     </tr>
     <tr>
      <td style="text-indent: 10px;" colspan="2" valign="top">
      	<fieldset>
	   		<legend style="font-size:15px;">评论：</legend>
	   		<span class="basic_border_grey fl" style="margin:10px 0;border-bottom: 1px solid #d8d8d8;"></span>
	   	</fieldset>
      </td>
     </tr>
  </table>
   	
  <!-- 这里 控制 是否可见 评论 是否  进制评论  -->
  <div id="commentReplay">
    <table align="center" style='border:#dddddd 0px solid;' width='100%'>
  	  <tr>
  	  	<td>
	  	  	 <div  style="margin-left: 10px;">
	  	  	 	  <button type="button" class="btn-win-white fl" onclick="getAllNewsComments(0,-1)" >查看自己的评论</button>
	  			  <button type="button" class="btn-win-white fl" onclick="getAllNewsComments(1,-1)">查看其他人的评论</button>
	  			  <button type="button" class="btn-win-white fl" onclick="deleteAllConment();">删除全部评论</button>
				   <div class="btn-group">
				    <button type="button" class="btn-win-white dropdown-toggle btn-menu fl" data-toggle="dropdown">
				      	更多操作
				      <span class="caret"></span>
				    </button>
				    <ul class="dropdown-menu btn-content">
				      <li><a href="#" onclick="getAllNewsComments(-1,5)">查看最新5条</a></li>
				      <li><a href="#" onclick="getAllNewsComments(-1,10)">查看最新10条</a></li>
				      <li><a href="#" onclick="getAllNewsComments(-1,20)">查看最新20条</a></li>
				    </ul>
				  </div>
				  
			</div>
  	  	</td>
  	  </tr>
   </table>
   </div>
  
  
   <table align="center" width='98%' >
   <tbody id="comments" style="margin-left: 10px;margin-right: 10px;">
   
   </tbody>
   
   </table>
   
   </div>
</body>
</html>
 