<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%

  int  fbId=TeeStringUtil.getInteger(request.getParameter("fbId"),0);
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>反馈详情</title>
</head>
<script>
var fbId=<%=fbId%>;
var loginUserUuid=<%=loginUser.getUuid()%>;
//初始化 
function doInit(){
	getInfoBySid();
	getReplyList();
}


//获取详情
function getInfoBySid(){
	var url=contextPath+"/supFeedBackController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:fbId});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}

//获取回复列表
function getReplyList(){
	var url=contextPath+"/supFeedBackReplyController/getReplyListByFbId.action";
	var json=tools.requestJsonRs(url,{fbId:fbId});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				var opt="";
				if(data[i].createrId==loginUserUuid){
					opt="<a href=\"#\" onclick=\"del("+data[i].sid+")\">删除</a>";
				}
				$("#tbody").append("<tr>"
						+"<td style=\"text-indent:10px\">"+data[i].createrName+"</td>"
						+"<td>"+data[i].createTimeStr+"</td>"
						+"<td>"+data[i].content+"</td>"
						+"<td>"+opt+"</td>"
						+"</tr>");
			}
		}else{
			messageMsg("无相关数据！","message","info");
		}
	}
}

//删除回复
function del(sid){
	parent.$.MsgBox.Confirm ("提示", "是否确认删除该回复？", function(){
		  var url = contextPath + "/supFeedBackReplyController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				parent.$.MsgBox.Alert_auto("删除成功！");
				window.location.reload();
			}   
	  });
} 
</script>

<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
   <div class="topbar clearfix">
      <img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/supervise/imgs/icon_fkxq.png">
		<span class="title">反馈详情</span>
   </div> 
   <table class="TableBlock_page" width="100%">
   <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">基本信息</B></TD>
	</tr>
      <tr>
         <td style="text-indent: 10px;width: 20%">反馈标题：</td>
         <td name="title" id="title">
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px;width: 20%">反馈内容：</td>
         <td name="content" id="title">
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px;width: 20%">反馈人：</td>
         <td name="createrName" id=""createrName"">
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px;width: 20%">反馈时间：</td>
         <td name="createTimeStr" id=""createTimeStr"">
         </td>
      </tr>
      
      <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">回复列表</B></TD>
	  </tr>
	  
	  <tr>
	     <td colspan="2">
	        <table id='tbody' style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>
				<tr class='TableHeader' style='background-color:#e8ecf9' >
				    <td width='20%' style="text-indent:10px">回复人员</td>
					<td width='30%'>回复时间</td>
					<td width='40%'>回复内容</td>
					<td width='10%'>操作</td>
		  		</tr>
	        </table>
	        <div id="message" style="margin-top: 10px;margin-bottom: 10px"></div>
	     </td>
	  </tr>
   </table>
</body>
</html>