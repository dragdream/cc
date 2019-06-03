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
<title>添加新闻</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>	
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 其他工具库类 -->
	<script src="<%=contextPath %>/common/js/tools.js"></script>
	<script src="<%=contextPath %>/common/js/sys.js"></script>
	<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var systemImagePath = "<%=systemImagePath%>";
var id = '<%=id%>';
var editor ;
function doInit(){
	
	editor = CKEDITOR.replace('content',{
			width : 'auto',
			height:300
		});
		CKEDITOR.on('instanceReady', function (e) {
			editor = e.editor;
			loadData(id);
		});
		
}

function loadData(id){
	var url = "<%=contextPath%>/teeNewsController/getNews.action?isLooked=1";
	var jsonRs = tools.requestJsonRs(url,{"id":id});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			try {
				bindJsonObj2Cntrl(data);
			} catch (e) {
				// TODO: handle exception
			}
			var newsTime = getFormatDateStr(data.newsTime , 'yyyy-MM-dd HH:mm:ss');
			$("#newsTime").val(newsTime);
			editor.setData(data.content);
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
		var url = "<%=contextPath%>/teeNewsController/updateNews.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			 top.$.jBox.tip("更新成功！");
			window.location.href ="<%=contextPath%>/system/core/base/news/manage/manageNewsList.jsp";
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	 if($("#toDeptIds")[0].value == '' &&
		       $("#toRolesIds")[0].value == '' &&
		       $("#toUserIds")[0].value == ''){
		    	alert("发布范围是必填项！");
		    	return false;
	  }
	 if(editor.getData() == ""){
	    alert("新闻内容是必填项！");
	    return false;
	 }
	 
    return $("#form1").form('validate'); 
}
</script>
 
</head>
<body onload="doInit()">
 <table border="0" width="100%" cellspacing="0" cellpadding="3"  align="">
    <tr>
      <td class="Big">
        
        <span class="Big3">
        	  编辑新闻
         </span>
       </td>
    </tr>
  </table><br>

	<form  method="post" name="form1" id="form1">
<table class="TableBlock" width="85%" align="center">

	<tr>
    <td nowrap class="TableData" width="180" >
	   <select name="typeId" title="新闻类型可在“系统管理”->“分类码管理”模块设置。" id="typeId" class="BigSelect">
	    	<option value="">选择新闻类型</option>
	    </select>
    </td>
    <td nowrap class="TableData" align="left">
       	 <input type='text' name="subject" id="subject" class="easyui-validatebox BigInput"  size='35' required="true"  maxlength="150" />
    </td>
   </tr>
    <tr title="发布范围取部门、人员和角色的并集" >
    <td nowrap class="TableData"  >按部门发布：</td>
    <td nowrap class="TableData" align="left">
    	<div id="toDeptNames"></div>
    </td>
   </tr>
    <tr title="发布范围取部门、人员和角色的并集" >
    <td nowrap class="TableData"  >按角色发布：</td>
    <td nowrap class="TableData" align="left">
    	<div id="toRolesNames"></div>
    </td>
   </tr>
     <tr title="发布范围取部门、人员和角色的并集" >
    <td nowrap class="TableData"  >按人员发布：
	</td>
    <td nowrap class="TableData" align="left">
    <div id="toUserNames"></div>
    </td>
   </tr>
   	<tr>
		<td nowrap class="TableData" width="120">发布时间：</td>
		<td nowrap class="TableData"><input type="text" name="newsTime" id="newsTime" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate BigInput">
		<a herf="javascript:void(0)" style="cursor: hand" /> 默认为当前时间</a></td>
	</tr>
	<tr>
   		<td nowrap class="TableData" width="220" >评论：</td>
    	<td nowrap class="TableData" align="left">
    		<select name="anonymityYn" class="BigSelect">
    			 <option value="0">实名评论</option> 
    			 <option selected value="1">匿名评论</option> 
    			 <option value="2">禁止评论</option>
    		</select>
   	    </td>
   </tr>
	<tr>
   		<td nowrap class="TableData" width="220" >附件：</td>
    	<td nowrap class="TableData" align="left">
    	 <div id="attachList" style="max-width: 200px;">
			 </div>
   	    </td>
   </tr>
   <tr>
   		<td nowrap class="TableData" width="220" >附件选择：</td>
    	<td nowrap class="TableData" align="left">
    	<div id="upfileList"></div>
   	    </td>
   </tr>
    <tr>
   		<td nowrap class="TableData" width="220" >提醒：</td>
    	<td nowrap class="TableData" align="left">
    	<input name="mailRemind" id="mailRemind" type="checkbox" value="on"/>
    	使用内部短信提醒   
   	    </td>
   </tr>
   <tr>
    	<td nowrap class="TableData" align="left" colspan="2">
	    	<DIV style="width:800px;">
					<textarea style="" id="content" name="content" ></textarea>
					 <script type="text/javascript">
					 </script>
			</DIV>
   	    </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2" align="center">
	     <input type="hidden" name="id" value="<%=id %>" />
	        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="doSave()" >&nbsp;&nbsp;
	        <input type="button" value="返回" class="btn btn-primary" title="返回" onClick="history.go(-1);">
	        <input type='hidden' value='<%=id %>' name='sid'/>
	    </td>
   </tr>
   
</table>
  </form>

</body>
</html>
 