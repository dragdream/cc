<%@page import="com.tianee.webframe.util.str.TeeUtility,com.tianee.oa.core.general.TeeSysCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int id = TeeStringUtil.getInteger(request.getParameter("id") , 0 ) ;
	String infoType=request.getParameter("infoType");
	String infoTypeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE",infoType);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>添加信息</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>	
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript">
var systemImagePath = "<%=systemImagePath%>";
var id = "<%=id%>";
var editor;
function doInit(){
	var ttAttach = new TeeSimpleUploadRender({
		fileContainer:"upfileList"
	});
	
	//新闻类型
	//var prcs = getSysCodeByParentCodeNo("NEWS_TYPE" , "typeId");
	$("#typeId").val("<%=infoType%>");
	editor = CKEDITOR.replace('content',{
		width : 'auto',
		height:300
	});
	if(id > 0){
			CKEDITOR.on('instanceReady', function (e) {
				editor = e.editor;
				loadData(id);
			});
	}else{
		$("#subject")[0].select();
	}
	
	showPhoneSmsForModule("sms","020",loginPersonId);
}
/*
 * 新增或者更新
 */
function doSave(type){
	if (checkForm()){
	var para =  {};//tools.formToJson($("#form1")) ;
	 $("#form1").ajaxSubmit({
           url: "<%=contextPath%>/teeNewsController/addNews.action?publish=" + type,
           iframe: true,
           data: para,
           success: function(res) {
        	   if(res.rtState){
        		   var msg = "保存成功！";
//         		   if(type == '1'){
//         			     msg = "发布成功！";
//         				//手机短信
// 	       				var toDeptIds=$("#toDeptIds").val();
// 	       				var toRolesIds=$("#toRolesIds").val();
// 	       				var toUserIds=$("#toUserIds").val();
// 	       				var toUserId="";
//        				var urls="<%=contextPath%>/TeeSmsPhonePrivController/getUserIds.action?toDeptIds="+toDeptIds+"&toRolesIds="+toRolesIds+"&toUserIds="+toUserIds;
//         				var jsonObj = tools.requestJsonRs(urls);
//         				if(jsonObj.rtState){
//         					toUserId=jsonObj.rtData;
//         				}
// 	       				var smsContent=userName+"发布了新闻："+$("#subject").val();
// 	       				var sendTime="";
// 	       				sendPhoneSms(toUserId,smsContent,sendTime);
//         		   }
//         		   top.jBox.tip(msg , 'info',  {timeout:1500})
            	   if( id >0){//编辑跳转至管理列表
            		   window.location.href = "<%=contextPath%>/system/core/base/infoPub/manage/manageInfoList.jsp?infoType=<%=infoType%>";
            	   }else{
            		   window.location.href = "manageInfoList.jsp?infoType=<%=infoType%>";
            	   }
    		 			
        	   }else{
        		   alert(res.rtMsg);
        	   }
        	  
                 // ... my success function (never getting here in IE)
            },
        	  error: function(arg1, arg2, ex) {
                 // ... my error function (never getting here in IE)
               alert("添加新闻出错！");
           },
           dataType: 'json'});
	}
}
/**
 * 检查表单校验
 */
function checkForm(){
    var check =  $("#form1").form('validate'); 
    if(!check){
    	return false;
    }
   /*  if($("#toDeptIds")[0].value == '' &&
       $("#toRolesIds")[0].value == '' &&
       $("#toUserIds")[0].value == ''){
    	alert("发布范围是必填项！");
    	return false;
    } */
    
    if(editor.getData() == ""){
    	alert("新闻内容是必填项！");
    	return false;
    }
    return true;
}
/**
 * 加载新闻信息  byId
 */
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
				$("#fileModel").append(attachElement);
		    });
		}
		
		
	}else{
		alert(jsonRs.rtMsg);
	}
}
function setCurrTime(){
	$("#newsTime").val(getFormatDateStr('','yyyy-MM-dd HH:mm:ss'));
}
</script>
 
</head>
<body onload="doInit()">


<div class="base_layout_top">
<table width="100%">
		<tr>
			<td>
				<h4>
				<%if(id == 0){ %>
		        	       新建<%=infoTypeDesc %>
		        <%}else{ %>
		        	      编辑<%=infoTypeDesc %>
		        <%} %>
				</h4>
			</td>
			<td align=right>
			<% if(id > 0){ %>
	 			 <input type="button" value="返回" class="btn btn-default" title="返回" onClick="history.go(-1);"> 
	      		&nbsp;&nbsp;
	      	<%} %>
			<input type="button" value="发布" class="btn btn-info" title="发布" onclick="doSave(1)" >&nbsp;&nbsp;
	        <input type="button" value="保存" class="btn btn-info" title="保存" onclick="doSave(0)" >&nbsp;&nbsp;
	        &nbsp;
			</td>
		</tr>
	</table>
</div>
<div class="base_layout_center">

<form  method="post" name="form1" id="form1" enctype="multipart/form-data"  >
<table class="none-table">

	<tr>
    <td nowrap class="TableData" width="180" >
      	<input type="hidden" id="typeId" name="typeId"/>
	   <!-- <select name="typeId"  title="新闻类型可在“系统管理”->“系统编码”模块设置。" id="typeId" class="BigSelect">
	    	<option value="">选择新闻类型</option>
	    </select> -->
	    标题：
    </td>
    <td nowrap class="TableData" align="left">
       	 <input type='text' name="subject" id="subject" class="easyui-validatebox BigInput"  size='50' required="true"  maxlength="150" />
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="180" >
	   摘要：
    </td>
    <td nowrap class="TableData" align="left">
    	<textarea id="abstracts" name="abstracts" class="BigTextarea" title="新闻摘要" rows="5" cols="60"></textarea>
    </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
    <td nowrap class="TableData"  >按部门发布：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="toDeptIds" id="toDeptIds" value=""/>
        <textarea cols=60 name="toDeptNames" id="toDeptNames" rows=3 class="SmallStatic BigTextarea" wrap="yes" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['toDeptIds','toDeptNames'],'<%=TeeModelIdConst.NEWS_SEND_PRIV%>')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('toDeptIds','toDeptNames')">清空</a>
    </td>
   </tr>
   
 
   <tr title="发布范围取部门、人员和角色的并集" >
    <td nowrap class="TableData" >按角色发布：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="toRolesIds" id="toRolesIds" value="">
        <textarea cols=60 name="toRolesNames" id="toRolesNames" rows=3 class="SmallStatic BigTextarea" wrap="yes" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['toRolesIds','toRolesNames'],'<%=TeeModelIdConst.NEWS_SEND_PRIV%>','1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('toRolesIds','toRolesNames')">清空</a>
    </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
    <td nowrap class="TableData"  title="发布范围取部门、人员和角色的并集">按人员发布：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="toUserIds" id="toUserIds" value="">
        <textarea cols=60 name="toUserNames" id="toUserNames" rows=3 class="SmallStatic BigTextarea" wrap="yes" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['toUserIds', 'toUserNames'],'<%=TeeModelIdConst.NEWS_SEND_PRIV%>' , '1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('toUserIds','toUserNames')">清空</a>
    </td>
   </tr>
      <tr>
      	<td>提示：</td>
   		<td class="TableData"><font color='red'>
   			当用户，部门，角色都为空时，默认全部门发布
   		</font></td>
   </tr>
   	<tr>
		<td nowrap class="TableData" >发布时间：</td>
		<td nowrap class="TableData"><input type="text" name="newsTime"
			id="newsTime" size="20"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate BigInput">
		<a herf="javascript:void(0);"  onclick="setCurrTime();" style="cursor:pointer;" /> 默认为当前时间</a></td>
	</tr>
	<tr>
   		<td nowrap class="TableData"  >评论：</td>
    	<td nowrap class="TableData" align="left">
    		<select name="anonymityYn" class="BigSelect">
    			 <option value="0">实名评论</option> 
    			 <option selected value="1">匿名评论</option> 
    			 <option value="2">禁止评论</option>
    		</select>
   	    </td>
   </tr>
   <tr>
   		<td nowrap class="TableData"  >重要：</td>
    	<td nowrap class="TableData" align="left">
    		<input type="checkbox" name="top" value="1"/>使新闻显示为重要 
   	    </td>
   </tr>

   <tr>
   		<td nowrap class="TableData">附件选择：</td>
    	<td nowrap class="TableData" align="left">
    	<span id="fileModel"></span>
    	<div id="upfileList"></div>
   	    </td>
   </tr>
    <tr>
   		<td nowrap class="TableData">提醒：</td>
    	<td nowrap class="TableData" align="left" id="sms">
    	<input name="mailRemind" id="mailRemind" type="checkbox" value="1"/>
    	使用内部短信提醒   
   	    </td>
   </tr>
   <tr>
    	<td  class="TableData" align="left" colspan="2" width="">
	    	<DIV style="">
					<textarea style="" id="content" name="content" class="BigTextarea" ></textarea>
			</DIV>
   	    </td>
   </tr>
   <tr>
	    <td nowrap>
	      
	        <input type='hidden' value='<%=id %>' name='sid'/>
	    </td>
   </tr>
   
</table>
  </form>
</div>
</body>
</html>
 