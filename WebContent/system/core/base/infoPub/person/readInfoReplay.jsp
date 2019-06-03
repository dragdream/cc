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
<title>信息详细</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=contextPath %>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
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
		var sendTime = getFormatDateTimeStr(data.newsTime , 'yyyy-MM-dd HH:mm:ss');//发布时间
		var fromDeptName = data.fromDeptName;//发布部门
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
		
	}else{
		alert(jsonRs.rtMsg);
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
		//var sendTime = getFormatDateStr(data.sendTime , 'yyyy-MM-dd HH:mm:ss');//发布时间
	    var trTem ="<tr>"+
	      "<td  colspan=\"2\" valign=\"top\" >"+
		      "<div class=\"panel panel-default\">"+
			  "<div class=\"panel-heading\">"+
				  "<table border=\"0\" cellpadding=\"0\" width=\"100%\">"+
				 	"<tr>"+
				 		"<td align=\"left\" style=\"font-size:12px\">"+
				 		 "评论人：{nickName}&nbsp;&nbsp;"+
					     "时间：{strReTime}"+
				 		"</td>"+
				 		"<td align=\"right\">"+
				 		"</td>"+
				 	"</tr>"+
				" </table>"+
			  "</div>"+
				  "<div style=\"padding:5px;\">"+
				   "{content}"+
				  "</div>"+
			" </div>"+
	      "</td>"+
	    " </tr>";
	    var trTem1 ="<tr>"+
	      "<td  colspan=\"2\" valign=\"top\" >"+
		      "<div class=\"panel panel-default\">"+
			  "<div class=\"panel-heading\">"+
				  "<table border=\"0\" cellpadding=\"0\" width=\"100%\">"+
				 	"<tr>"+
				 		//"<td align=\"left\" style=\"font-size:12px\"> #<span>{sortId}</span>"+
				 		"<td align=\"left\" style=\"font-size:12px\"> "+
				 		"评论人：{nickName}&nbsp;&nbsp;"+
					     "时间：{strReTime}"+
				 		"</td>"+
				 		"<td align=\"right\">"+
				 			"<a href='javascript:void(0)' style='font-size:12px' onclick=\"deleteById('{sid}')\">删除</a>"+
				 		"</td>"+
				 	"</tr>"+
				" </table>"+
			  "</div>"+
				  "<div  style=\"padding:5px;\">"+
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
		alert(jsonRs.rtMsg);
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
			 top.$.jBox.tip("发表成功！");
			 getAllNewsComments();
			 $("#comContent").val("");
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
   return $("#form1").form('validate'); 
}

function deleteById(id) {
    var submit = function (v, h, f) {
        if (v == true)
        	delSigle(id);
        return true;
    };
  var delSigle = function ()
    {
    		var url = "<%=contextPath %>/teeNewsCommentController/deleteNewsComment.action";
    		var jsonRs = tools.requestJsonRs(url,{"id":id});
    		if(jsonRs.rtState){
    			 top.$.jBox.tip("删除成功！");
    			 getAllNewsComments();
    		}else{
    			alert(jsonRs.rtMsg);
    		}
    };
    jBox.confirm("确定删除所选记录,删除后将不可恢复！", "确认删除？", submit, { id:'s123', showScrolling: false, buttons: { '确定': true, '取消': false } });
}
</script>
 
</head>
<body onload="doInit()">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">标题：<span id="subject"></span></span>
	<img class="pull-right" src="<%=systemImagePath %>/favorite_click.png" style="cursor:pointer" title="添加收藏夹" id="addFav"/>
</div>
<br/>

 <table width="90%" align="center" style="font-size:12px;"  >
   <tr>
   <td  width="100%" style="padding:0px"> 
	<center style="">
		发布部门：</span>&nbsp;<u style="cursor:hand" id="fromDeptName"></u>
		发布人：</span>&nbsp;<u  style="cursor:hand" id="fromPersonName"></u>
		发布于：</span>&nbsp;<span id="sendTime"></span>
	</center>
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
	      		<legend>附件：</legend>
	      	</fieldset>
	      <div id="attachList" ></div>
      </td>
     </tr>
     <tr>
      <td  colspan="2" valign="top">
      	<fieldset>
	   		<legend>评论：</legend>
	   	</fieldset>
      </td>
     </tr>
  </table>
   <!-- 这里 控制 是否可见 评论 是否  进制评论  -->
   <div id="commentDiv">
  <table width="90%" align="center" >
  	  <tr>
  	  	<td>
	  	  	 <div class="btn-group">
	  	  	 	  <button type="button" class="btn btn-default" onclick="getAllNewsComments(0,-1)" >查看自己的评论</button>
	  			  <button type="button" class="btn btn-default" onclick="getAllNewsComments(1,-1)">查看其他人的评论</button>
				   <div class="btn-group">
				    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				      	更多操作
				      <i class="caret"></i>
				    </button>
				    <ul class="dropdown-menu">
				      <li><a href="javascript:void(0)" onclick="getAllNewsComments(-1,5)">查看最新5条</a></li>
				      <li><a href="javascript:void(0)" onclick="getAllNewsComments(-1,10)">查看最新10条</a></li>
				      <li><a href="javascript:void(0)" onclick="getAllNewsComments(-1,20)">查看最新20条</a></li>
				    </ul>
				  </div>
				  
			</div>
  	  	</td>
  	  </tr>
   </table>
   
   <table width="90%" align="center" >
   <tbody id="comments">
   </tbody>
   </table>

   <form name="form1" id="form1" method="post" >
   <TABLE class="TableBlock" width="90%" align="center">
		<TBODY>
			<TR>
				<TD class="TableHeader" colSpan="2">发表评论： </TD>
			</TR>
			<TR>
				<TD class="TableData" align="center">内容：</TD>
				<TD class="TableData"><textarea id="comContent" class="BigTextarea" style="width:98%" rows="5"  name="content"></textarea>
				 </TD>
			 </TR>
			<TR>
			<TD class="TableData" align="center">署名：</TD>
				<TD class="TableData">
				<SPAN style="DISPLAY: none" id="radio1">
				<INPUT id="authorName" name="authorName" value="authorName" type="radio" checked="checked"  />  姓名
				<INPUT id="nickName" class="BigInput SmallStatic" name="nickName"  type="text"  readOnly maxLength="25"  size="15" value="<%=userName %>" /> 
				</SPAN>
				<SPAN style="DISPLAY: none" id="radio2">
					<INPUT id="authorName2" name="authorName2" value="nickName" type="radio" checked="checked" />昵称 
					
					<%-- <INPUT id="" class="BigInput SmallStatic" name=""  type="text"  readOnly maxLength="25"  size="10" value="<%=userName %>" />  --%>
					<INPUT id="nickName" name="nickName" maxLength="25" type="text" size="15" class="easyui-validatebox BigInput "  required="true" /> 
				</SPAN>
				</TD>
			</TR>
			<TR class="TableControl" align="center">
				<TD colSpan=2 noWrap>
					<INPUT id="newsId" name="newsId" value=<%=newsId %> type="hidden" /> 
				 	<INPUT id="publish"  class="btn btn-primary" value="发表" type="button" onclick="doSave();"/>
				 </TD>
			 </TR>
		</TBODY>
	</TABLE>
	</form>
   </div>
</body>
</html>
 