<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.oa.oaconst.TeeConst" %>
<%@page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String newsId = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
	String isLooked = request.getParameter("isLooked") == null ? "0" : request.getParameter("isLooked") ;
	TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int userId = loginPerson.getUuid();
	String userName = loginPerson.getUserName();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新闻详细</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var userId = '<%=userId%>';
var id = '<%=newsId%>';
var isLooked = "<%=isLooked%>";
var contextPath = "<%=contextPath %>";
var systemImagePath = "<%=systemImagePath%>";
var replayPriv = 0; //0 实名 1 匿名 2 进制评论
function doInit(){
	getNews();
	if(replayPriv && replayPriv == 2){
		//禁止 评论
		$("#commentDiv").hide();
	}else{
		getAllNewsComments(-1,-1);
		if(replayPriv == 1){// 1 匿名
			//replayTitle
			$("#radio2").show();
			$("#radio1").hide();
			$("#radio1").html(" ");
		}else{//0 实名
			$("#radio1").show();
			$("#radio2").hide();
			$("#radio2").html(" ");
		}
		
	}
	
}

function getNews(){
	var url = "<%=contextPath%>/teeNewsController/getNews.action?isLooked="+isLooked;
	var para = {"id":id};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		var subject =  data.subject;//标题
		var content = data.content;//内容
		var fromPersonName = data.provider1;//发布人
		var sendTime = getFormatDateTimeStr(data.newsTime , 'yyyy-MM-dd HH:mm');//发布时间
		var fromDeptName = data.fromDeptName;//发布部门
		
		if(data.format=="2"){
			window.location = data.url;
			return;
		}
		
		$("#subject").html(subject);
		$("#content").html(content);
		$("#fromPersonName").html(fromPersonName);
		$("#sendTime").html(sendTime);
		var anonymityYn = data.anonymityYn;
		replayPriv = anonymityYn;
		$("#fromDeptName").html(fromDeptName);
		$.each(data.attachmentsMode,function(i,v){
			v['priv'] = 3;//查看、下载
			var attachElement = tools.getAttachElement(v,{});
			$("#attachList").append(attachElement);
	    });
		
		$("#addFav").addFav(subject,"/system/core/base/news/person/readNewsReplay.jsp?id="+id+"&isLooked="+isLooked);
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

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
	    var trTem ="<tr>"+
	      "<td  colspan=\"2\" valign=\"top\" >"+
		      "<div class=\"panel panel-default\" style='width:98%;margin:0 auto;border-bottom:1px dashed #e1e1e1;'>"+
			  "<div class=\"panel-heading\">"+
				  "<table border=\"0\" cellpadding=\"0\" width=\"100%\">"+
				 	"<tr style='height:25px;'>"+
				 		"<td align=\"left\" style=\"font-size:12px;padding:5px 0;\">"+
				 		 "评论人：{nickName}&nbsp;&nbsp;"+
					     "时间：{strReTime}"+
				 		"</td>"+
				 		"<td align=\"right\">"+
				 		"</td>"+
				 	"</tr>"+
				" </table>"+
			  "</div>"+
				  "<div style=\"padding:0px;padding:10px 5px;width:97%;margin:0 auto;\">"+
				   "{content}"+
				  "</div>"+
			" </div>"+
	      "</td>"+
	    " </tr>";
	    var trTem1 ="<tr>"+
	      "<td  colspan=\"2\" valign=\"top\" >"+
		      "<div class=\"panel panel-default\" style='width:98%;margin:0 auto;border-bottom:1px dashed #e1e1e1;'>"+
			  "<div class=\"panel-heading\">"+
				  "<table border=\"0\" cellpadding=\"0\" width=\"100%\">"+
				 	"<tr>"+
				 		//"<td align=\"left\" style=\"font-size:12px\"> #<span>{sortId}</span>"+
				 		"<td align=\"left\" style=\"font-size:12px;padding:5px 0;\"> "+
				 		"评论人：{nickName}&nbsp;&nbsp;"+
					     "时间：{strReTime}"+
				 		"</td>"+
				 		"<td align=\"right\" style=\"text-align:right;padding-right:20px;\">"+
				 			"<a href='javascript:void(0)' style='font-size:12px;' onclick=\"deleteById('{sid}')\">删除</a>"+
				 		"</td>"+
				 	"</tr>"+
				" </table>"+
			  "</div>"+
				  "<div  style=\"padding:5px;padding-left:20px;padding-right:20px;\">"+
				   "{content}"+
				  "</div>"+
			" </div>"+
	      "</td>"+
	    " </tr>";
	 var liArray = new Array();
		if(data){
			$.each(data,function(key, val){
					var str = null;
					if(userId == val.userId){
						str = FormatModel(trTem1,val);
					}else{
						str = FormatModel(trTem,val);
					}
					liArray.push(str);
			});
		}
		targetDom.html(liArray.join(''));
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
* 保存
*/
function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/teeNewsCommentController/addNewsComment.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("发表成功！");
			 getAllNewsComments();
			 $("#comContent").val("");
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}

function deleteById(id) {
    var submit = function (v, h, f) {
        if (v == true)
        	delSigle(id);
        return true;
    };
  var delSigle = $.MsgBox.Confirm ("提示", "确定删除所选记录？删除后将不可恢复！",function(){
    		var url = "<%=contextPath %>/teeNewsCommentController/deleteNewsComment.action";
    		var jsonRs = tools.requestJsonRs(url,{"id":id});
    		if(jsonRs.rtState){
    			$.MsgBox.Alert_auto("删除成功！");
    			 getAllNewsComments();
    		}else{
    			$.MsgBox.Alert_auto(jsonRs.rtMsg);
    		}
    });
    //jBox.confirm("确定删除所选记录,删除后将不可恢复！", "确认删除？", submit, { id:'s123', showScrolling: false, buttons: { '确定': true, '取消': false } });
}

</script>
<style>
	#comments tr:first-child > td > div:first-child{padding-top:15px;}
	#comments tr:last-child > td > div:first-child{padding-bottom:10px;border-bottom:none!important;}
</style>
</head>
<body onload="doInit()" style="margin-bottom:20px;">
<div  style="width:90%;margin:0 auto;text-align:center;margin-top: 20px">
	<span class="easyui_h1" style="font-size:18px;">新闻标题：<span id="subject"></span></span>
</div>
<br/>

 <table width="90%" align="center"  style="max-width:1000px;margin:0 auto;">
   <tr>
   <td  width="100%" style="padding:0px"> 
	<center style="font-size:12px;padding-bottom:10px;position:relative;">
		发布部门：</span>&nbsp;<u style="cursor:hand" id="fromDeptName"></u>
		发布人：</span>&nbsp;<u  style="cursor:hand" id="fromPersonName"></u>
		发布于：</span>&nbsp;<span id="sendTime"></span>
		<img class="pull-right" src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/xwfb/icon-save.png" style="cursor:pointer;position:absolute;right:0;"  title="添加收藏夹" id="addFav"/>
	</center>
	<hr color="#d6d6d6"/>
	<br/>
 </td>
 </tr>
    <tr>
      <td  colspan="2" valign="top">
      	<span id="content" style="font-size:14px;"></span>
		</td>
    </tr>
    <tr>
      <td  colspan="2" valign="top">
      	 <br/>
	      	<fieldset>
	      		<legend style="font-size: 16px;margin: 10px 0;">附件：</legend>
	      		<hr color="#d6d6d6" style="width:1000px;margin-bottom: 10px;">
	      	</fieldset>
	      <div id="attachList" ></div>
      </td>
     </tr>
     <tr>
      <td  colspan="2" valign="top">
      	<fieldset style='margin-top:3px;'>
	   		<legend style="font-size: 16px;margin: 10px 0;">评论：</legend>
	   		<hr color="#d6d6d6" style="width:1000px;margin-bottom: 10px;">
	   	</fieldset>
      </td>
     </tr>
  </table>
   <!-- 这里 控制 是否可见 评论 是否  进制评论  -->
   <div id="commentDiv">
  <table width="90%" align="center"  style="max-width:1000px;margin:0 auto;">
  	  <tr>
  	  	<td>
	  	  	 <div>
	  	  	 	  <button type="button" class="btn-win-white fl" onclick="getAllNewsComments(0,-1)" >查看自己的评论</button>
	  			  <button type="button" class="btn-win-white fl" onclick="getAllNewsComments(1,-1)">查看其他人的评论</button>
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
   
   <table width="90%" align="center"  style="max-width:1000px;margin:0 auto;border-collapse:collapse;margin-top:15px;">
   <tbody id="comments" style='border:1px solid #eee;text-indent:20px;'>
   </tbody>
   </table>

   <form name="form1" id="form1" method="post" >
   <TABLE class="TableData" width="90%" align="center"  style="max-width:1000px;margin:10px auto;">
		<TBODY>
			<TR>
			<td align="center" colspan="2" valign="top">
      	       <fieldset>
	   		      <legend align="center" style="font-size: 18px;margin-top: 10px;margin-bottom: 10px;">发表评论</legend>
	   		      <hr color="#d6d6d6" style="width:1000px;margin-bottom: 10px;">
	        	</fieldset>
            </td>
				
			</TR>
			<TR>
				<TD class="TableData" align="center" style="font-size: 14px;width: 100px;">内容：</TD>
				<TD class="TableData"><textarea id="comContent" style="width:98%;margin: 10px;font-family: MicroSoft YaHei;" rows="5"  name="content"></textarea>
				 </TD>
			 </TR>
			<TR>
			<TD class="TableData" align="center" style="font-size: 14px;margin: 10px 0;">署名：</TD>
				<TD class="TableData">
				<SPAN style="DISPLAY: none" id="radio1">
				<INPUT style="line-height:25px;" id="authorName" name="authorName" value="authorName" type="radio" checked="checked"  />  姓名
				<INPUT style="line-height:25px;font-family: MicroSoft YaHei;" id="nickName"  name="nickName"  type="text"  readOnly maxLength="25"  size="15" value="<%=userName %>" /> 
				</SPAN>
				<SPAN style="DISPLAY: none" id="radio2">
					<INPUT style="line-height:25px;" id="authorName2" name="authorName2" value="nickName" type="radio" checked="checked" />昵称 
					
					<%-- <INPUT id="" class="BigInput SmallStatic" name=""  type="text"  readOnly maxLength="25"  size="10" value="<%=userName %>" />  --%>
					<INPUT style="line-height:25px;" id="nickName" name="nickName" maxLength="25" type="text" size="15" required /> 
				</SPAN>
				</TD>
			</TR>
			<TR class="TableControl" align="center" >
				<TD class="TableData" colSpan=2 noWrap style="padding-bottom: 10px;padding-top: 10px;">
					<INPUT id="newsId" name="newsId" value=<%=newsId %> type="hidden" /> 
				 	<INPUT id="publish" style='display:block;margin:0 auto;' class="btn-win-white" value="发表" type="button" onclick="doSave();"/>
				 </TD>
			 </TR>
		</TBODY>
	</TABLE>
	</form>
   </div>
</body>
</html>
 