<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String documentId = request.getParameter("documentId");
	String channelId = request.getParameter("channelId");
	String chnlDocId = request.getParameter("chnlDocId");
	String siteId = request.getParameter("siteId");
	String isNew = request.getParameter("isNew");//是否为新建
	String model = TeeAttachmentModelKeys.cms;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>

<script type="text/javascript">
var editor;
var documentId = <%=documentId%>;
var channelId = <%=channelId%>;
var chnlDocId = <%=chnlDocId%>;
var siteId = <%=siteId%>;
var isNew = <%=isNew%>;
var cats = [];

function doInit(){
	$("#layout").layout({auto:true});
	
	var json = tools.requestJsonRs(contextPath+"/cmsDocCat/listCats.action",{});
	cats = json.rtData;
	for(var i=0;i<cats.length;i++){
		$("#catDiv").append("<input type='checkbox' clazz='cb' value="+cats[i].priv+" id='cb"+cats[i].sid+"'/><label for='cb"+cats[i].sid+"'>"+cats[i].name+"</label>&nbsp;&nbsp;");
	}

	
	$(function(){
		//内容
		var htmlContent="";
        if(isNew!=true){//加载数据
			var json = tools.requestJsonRs(contextPath+"/cmsDocument/getDocument.action",{docId:documentId});
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
				var top=json.rtData.top;
				if(top==0){
					$("#topDesc").val("不置顶");
				}else if(top==1){
					$("#topDesc").val("永久置顶");
				}
				$("input[clazz=cb]").each(function(i,obj){
					var val = parseInt(obj.value);
					if((json.rtData.category & val) == val){
						$(obj).attr("checked","checked");
					}
				});
				if(json.rtData.thumbnail!=0){
					$("#thumbImg").attr("src",contextPath+"/attachmentController/downFile.action?id="+json.rtData.thumbnail);
				}
				
				if(json.rtData.attachMentModel.length>0){
					$("#attachTr").show();
					var attaches = json.rtData.attachMentModel;
					for(var i=0;i<attaches.length;i++){
						var fileItem = tools.getAttachElement(attaches[i]);
						$("#attachments").append(fileItem);
					}
				}
				htmlContent=json.rtData.htmlContent;
				var json = tools.requestJsonRs(contextPath+"/cmsDocument/getChnlDoc.action",{chnlId:channelId,docId:documentId});
					$("#top").attr("value",json.rtData.top);
				}
		}
       
    });
}


</script>
</head>
<body onload="doInit()" style="margin-bottom:20px;">
<div  style="width:90%;margin:0 auto;text-align:center;margin-top: 20px">
	<span id="docTitle" title="信息标题" style="font-size:18px;"></span>
	
</div>
<br/>
 <table width="90%" align="center"  style="max-width:1000px;margin:0 auto;">
   <tr>
   <td  width="100%" style="padding:0px;height:50px;"> 
	<center style="font-size:12px;padding-bottom:10px;position:relative;">
		来源：<span id="source" name="source"></span>&nbsp;&nbsp;&nbsp;&nbsp;
		作者：<span id="author" name="author"></span>&nbsp;&nbsp;&nbsp;&nbsp;
		撰写时间：<span id="writeTimeDesc" name="writeTimeDesc"></span>
	</center>
	<hr color="#d6d6d6"/>
 </td>
 </tr>
    <tr>
      <td  colspan="2" valign="top">
      <span id="content"  name="htmlContent" style="font-size:14px;"></span>
	   </td>
    </tr>
    
    
     <tr>
      <td  colspan="2" valign="top" >
      <br/>
      	<fieldset>
      		<legend style="font-size: 14px">附件：</legend>
      		<hr color="#d6d6d6">
      	</fieldset>
      	<span id="attachments"></span>
		<div id="fileContainer2"></div>
      </td>
     </tr>
  </table>
  <center id="oper" style="margin-top:10px;">
  	<button class="btn-alert-gray" onclick="CloseWindow()">关闭</button>
  </center>
  
  
    <input type="hidden" name="chnlId" value="<%=channelId %>" />
	<input type="hidden" name="chnlDocId" value="<%=chnlDocId %>" />
	<input type="hidden" name="docId" value="<%=documentId %>" />
  
</body>

</html>