<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>编辑公告</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>
	
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script>window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/lang/zh-cn/zh-cn.js"></script>
	
	<!-- 其他工具库类 -->
	<script src="<%=contextPath %>/common/js/tools.js"></script>
	<script src="<%=contextPath %>/common/js/sys.js"></script>

<script type="text/javascript">
var systemImagePath = "<%=systemImagePath%>";
var id = '<%=id%>';
var editor ;

function doInit(){
	//var ttAttach = new TeeSimpleUploadRender({
	//	fileContainer:"upfileList"
	//});
	 
	 editor= new UE.ui.Editor({
	      toolbars : [ [  'source', '|', 'undo', 'redo', '|', 'bold',
	                     'italic', 'underline', 'fontborder', 'strikethrough', 'superscript',
	                     'subscript', 'removeformat', 'formatmatch', 'autotypeset',
	                     'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor',
	                     'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc',
	                     '|', 'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	                     'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	                     'directionalityltr', 'directionalityrtl', 'indent', '|', 'print',
	                     'preview', 'searchreplace', 'help' ] ]
	,elementPathEnabled : false,				            
	//初始化全屏
   fullscreen : false,
   //关闭字数统计
   wordCount:false
   });
	  editor.render("content");
	loadData(id);
}

function loadData(id){
	var url = "<%=contextPath%>/teeNotifyController/getNotifyById.action";
	var jsonRs = tools.requestJsonRs(url,{"id":id});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			try {
				bindJsonObj2Cntrl(data);
			} catch (e) {
				// TODO: handle exception
			}
			var beginDate = getFormatDateStr(data.beginDate , 'yyyy-MM-dd HH:mm:ss');
			var endDate = getFormatDateStr(data.endDate , 'yyyy-MM-dd HH:mm:ss');
			$("#beginDate").val(beginDate);
			$("#endDate").val(endDate);
			
			 editor.ready(function() {
					editor.setContent(data.content);
			 });
			$.each(data.attachmentsMode,function(i,v){
				var attachElement = tools.getAttachElement(v,{});
				$("#attachList").append(attachElement);
		    });
		}
		
		
	}else{
		alert(jsonRs.rtMsg);
	}
}
/**
 * 保存
 */
function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/teeNotifyController/updateNotify.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			 top.$.jBox.tip("更新成功！");
			window.location.href ="<%=contextPath%>/system/core/base/notify/manage/manageNotifyList.jsp";
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	if($("#beginDate").val()==""){
    	alert("请选择公告有效开始时间");
    	return false;
    }
	if(editor.getData() == ""){
    	alert("公告内容是必填项！");
    	return false;
    }
    return $("#form1").form('validate'); 
}
</script>
 
</head>
<body onload="doInit()">
<div class="moduleHeader">
	<b>编辑公告</b>
</div>

<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="80%" align="center">

	<tr>
    <td nowrap class="TableData" width="160" >
	    <select name="typeId" class="BigSelect">
	    	<option value="">选择公告类型</option>
	    </select>
    </td>
    <td nowrap class="TableData" align="left">
       	 <input type='text' name="subject" id="subject" class="easyui-validatebox BigInput"  size='35' required="true"  maxlength="150" />
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="220" >按部门发布：</td>
    <td nowrap class="TableData" align="left">
    	<div id="toDeptNames"></div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="220" >按角色发布：</td>
    <td nowrap class="TableData" align="left">
    	<div id="toRolesNames"></div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="220" >按人员发布：
	</td>
    <td nowrap class="TableData" align="left">
    <div id="toUserNames"></div>
    </td>
   </tr>
   	<tr>
		<td nowrap class="TableData" width="120">有效期：</td>
		<td nowrap class="TableData">
		开始时间:<input type="text" name="beginDate" id="beginDate" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate BigInput">
		终止时间：<input type="text" name="endDate" id="endDate" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate BigInput">
		<input type="checkBox" name="status" id="status" /> 一直生效</td>
	</tr>

   <tr>
   		<td nowrap class="TableData" width="220" >附件选择：</td>
    	<td nowrap class="TableData" align="left">
    		<div id="attachList" style="max-width: 200px;">
			 </div>
			<div id="upfileList"></div>
   	    </td>
   </tr>
    <tr>
   		<td nowrap class="TableData" width="220" >短信提醒：</td>
    	<td nowrap class="TableData" align="left">
    	<input name="mailRemind" id="mailRemind" type="checkbox" value="on"/>
    	使用内部短信提醒   
   	    </td>
   </tr>
       <tr>
   		<td nowrap class="TableData" width="220" >置顶：</td>
    	<td nowrap class="TableData" align="left">
    	<input name="" id="" type="checkbox" value="on"/>使公告通知置顶，显示为重要 <input size="2" />天后结束置顶，0表示一直置顶 
   	    </td>
   </tr>
     <tr>
    	<td nowrap class="TableData" align="left" colspan="2">
	    	<DIV style="width:800px;">
					<textarea id="content"  name="content" ></textarea>
					 <script type="text/javascript">
					
					
								</script>
			</DIV>
   	    </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2" align="center">
	    <input type="hidden" name="id" value="<%=id %>" />
	        <input type="button" id="sub_save" value="保存" class="btn btn-primary" title="保存" onclick="doSave()" >&nbsp;&nbsp;
	        <input type="button" value="返回" class="btn btn-primary" title="返回" onClick="history.go(-1);">
	    </td>
   </tr>
   
</table>
  </form>
<br>
</body>
</html>
 