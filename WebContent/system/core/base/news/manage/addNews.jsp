<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int id = TeeStringUtil.getInteger(request.getParameter("id") , 0 ) ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>添加新闻</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/validator2.0.jsp"%>
	<%@ include file="/header/userheader.jsp" %>	
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="/common/ckeditor/ckeditor.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
<%
String typeId = request.getParameter("typeId");
typeId=typeId==null?"":typeId;
%>

var systemImagePath = "<%=systemImagePath%>";
var id = "<%=id%>";
var typeId = '<%=typeId%>';
var editor;
var uEditorObj;//uEditor编辑器
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
	uEditorObj.setHeight(200);

	var ttAttach = new TeeSimpleUploadRender({
		fileContainer:"upfileList",
	});
	
	//新闻类型
	var prcs = getSysCodeByParentCodeNo("NEWS_TYPE" , "typeId");
	/* uEditorObj = CKEDITOR.replace('content',{
		width : 'auto',
		height:300,
	});  */
	if(id > 0){
		loadData(id);
	}else{
		$("#subject")[0].select();
	}

	changeFormat(document.getElementById("format"));
	
	showPhoneSmsForModule("sms","020",loginPersonId);
	
	if(typeId!=""){
		$("#typeId option").each(function(i,obj){
			if(typeId!=$(obj).attr("value")){
				$(obj).remove();
			}
		});
	}
	
	//初始化字数
	wordStatic();
	 });
}
/*
 * 新增或者更新
 */
function doSave(type){
	if (checkForm()){
	var para =  {};//tools.formToJson($("#form1")) ;
	var abstractVal = $("#abstracts").val();
	abstractVal = abstractVal.replace(/\n/g,"");
	abstractVal = abstractVal.replace(/\"/g,"'");
	$("#abstracts").val(abstractVal);
	
	var subjectVal = $("#subject").val();
	subjectVal = subjectVal.replace(/\n/g,"");
	subjectVal = subjectVal.replace(/\"/g,"'");
	$("#subject").val(subjectVal);
	
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
            		   window.location.href = "<%=contextPath%>/system/core/base/news/manage/manageNewsList.jsp?typeId="+typeId;
            	   }else{
            		   $.MsgBox.Alert_auto("保存成功！",function(){
            				window.location.reload();
            	            });
            	   }
    		 			
        	   }else{
        		   $.MsgBox.Alert_auto(res.rtMsg);
        	   }
        	  
                 // ... my success function (never getting here in IE)
            },
        	  error: function(arg1, arg2, ex) {
                 // ... my error function (never getting here in IE)
              $.MsgBox.Alert_auto("添加新闻出错！");
           },
           dataType: 'json'});
	}
}
/**
 * 检查表单校验
 */
 
function checkForm(){
	var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
   /*  if($("#toDeptIds")[0].value == '' &&
       $("#toRolesIds")[0].value == '' &&
       $("#toUserIds")[0].value == ''){
    	alert("发布范围是必填项！");
    	return false;
    } */
  //判断摘要的长度
    var abstracts=$("#abstracts").val();
    if(abstracts.length>255){
    	$.MsgBox.Alert_auto("新闻摘要字数长度不能超过255！");
    	return false;
    }
    
    
   if(uEditorObj.getContent() == "" && $("#format").val()=="1"){
	   $.MsgBox.Alert_auto("新闻内容是必填项！");
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
			
			var newsTime = getFormatDateStr(data.newsTime , 'yyyy-MM-dd HH:mm');
			$("#newsTime").val(newsTime);
			uEditorObj.setContent(data.content);
			//editor.setData(data.content);
			$.each(data.attachmentsMode,function(i,v){
				var attachElement = tools.getAttachElement(v,{});
				$("#fileModel").append(attachElement);
		    });
			changeFormat(document.getElementById("format"));
		}
		
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
function setCurrTime(){
	$("#newsTime").val(getFormatDateStr('','yyyy-MM-dd HH:mm'));
}

function changeFormat(obj){
	if(obj.value=="1"){
		$("#contentDiv").show();
		$("#urlDiv").hide();
	}else{
		$("#contentDiv").hide();
		$("#urlDiv").show();
	}
}



function wordStatic() { 
	
     var abstracts=$("#abstracts").val();
     abstracts = abstracts.replace(/\n|\r/gi,"");  
     if(abstracts.length>255){
    	 $("#num").css("color","red");   
    	 $("#num").html(abstracts.length);   
     }else{
    	 $("#num").css("color","black");   
    	 $("#num").html(abstracts.length);   
     }
     
} 
</script>
 
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;font-size: 12px;">
<div id="toolbar" class = "clearfix setHeight" style="">
<div class=" fl">
   <h4 style="font-size: 16px;font-family:MicroSoft YaHei;padding-left: 10px;">
				<%if(id == 0){ %>
		        	       新建新闻
		        <%}else{ %>
		        	      编辑新闻
		        <%} %>
				</h4>
</div>
   <div class=" fr">
			<input type="button" id="sub_pub" value="发布" class="btn-win-white" title="发布" onclick="doSave(1)" style="width:45px;height:25px;"/>&nbsp;
	        <input type="button" id="sub_save" value="保存" class="btn-win-white" title="保存" onclick="doSave(0)" style="width:45px;height:25px;" />
     <% if(id > 0){ %>
	 			 <input type="button" value="返回" class="btn-win-white" title="返回" onClick="history.go(-1);" style="width:45px;height:25px;"> 
	      		&nbsp;
	      	<%} %>
			      
   </div> 
   <span class="basic_border"></span>
</div>

<form  method="post" name="form1" id="form1" enctype="multipart/form-data">
<div>

<table class="TableBlock_page">
	<tr>
    <td class="TableData" style="text-indent:10px;width: 180px;font-size: 12px;">
	   新闻格式：
    </td>
    <td class="TableData" align="left">
    	<select style="font-family:MicroSoft YaHei;font-size: 12px;" name="format"  title="" id="format" class="BigSelect" onchange="changeFormat(this)">
	    	<option value="1">普通</option>
	    	<option value="2">超链接</option>
	    </select>
    </td>
   </tr>
	<tr>
    <td style="text-indent:10px;"  class="TableData">
	   <select style="font-family:MicroSoft YaHei;font-size: 12px;" name="typeId"  title="新闻类型可在“系统管理”->“系统编码”模块设置。" id="typeId" class="BigSelect">
	    	<option value="">选择新闻类型</option>
	    </select>
    </td>
    <td class="TableData" align="left">
       	 <input style="font-family:MicroSoft YaHei;font-size: 12px;" type='text' name="subject" id="subject"  size='50' required  maxlength="150" />
    </td>
   </tr>
   <tr>
    <td style="text-indent:10px;" class="TableData">
	   新闻摘要：
    </td>
    <td class="TableData" align="left">
    	<textarea style="font-family:MicroSoft YaHei;font-size: 12px;" id="abstracts" name="abstracts" class="BigTextarea" title="新闻摘要" rows="5" cols="60"  onkeyup="wordStatic();"></textarea>
        <span id="num"></span>/255
    </td>
   </tr>
   <tr id="urlDiv" style="display:none">
    <td style="text-indent:10px;"  class="TableData">
	   链接地址：
    </td>
    <td class="TableData" align="left">
    	<input type='text' name="url" id="url" class="BigInput"  size='50' value="http://"/>
    </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
    <td  style="text-indent:10px;" class="TableData">按部门发布：</td>
    
     <td  class="TableData" align="left" >
       <input type="hidden" name="toDeptIds" id="toDeptIds" value=""/>
        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" readonly cols=60 name="toDeptNames" id="toDeptNames" rows='3'></textarea>
        <span class='addSpan'>
			<img src="/common/zt_webframe/imgs/xzbg/xwfb/add.png" onClick="selectDept(['toDeptIds','toDeptNames'],'<%=TeeModelIdConst.NEWS_SEND_PRIV%>')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="/common/zt_webframe/imgs/xzbg/xwfb/clear.png" onClick="clearData('toDeptIds','toDeptNames')" value="清空"/>
		</span>
      </td>
    
   </tr>
 
   <tr title="发布范围取部门、人员和角色的并集" >
    <td  style="text-indent:10px;" class="TableData" >按角色发布：</td>
    <td   class="TableData" align="left">
     <input type="hidden" name="toRolesIds" id="toRolesIds" value=""/>
        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" readonly cols=60 name="toRolesNames" id="toRolesNames" rows='3'></textarea>
        <span class='addSpan'>
			<img src="/common/zt_webframe/imgs/xzbg/xwfb/add.png" onClick="selectRole(['toRolesIds','toRolesNames'],'<%=TeeModelIdConst.NEWS_SEND_PRIV%>','1')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="/common/zt_webframe/imgs/xzbg/xwfb/clear.png" onClick="clearData('toRolesIds','toRolesNames')" value="清空"/>
		</span>
    </td>
   </tr>
   <tr title="发布范围取部门、人员和角色的并集" >
    <td style="text-indent:10px;"  class="TableData" title="发布范围取部门、人员和角色的并集">按人员发布：</td>
    <td   class="TableData" align="left">
     <input type="hidden" name="toUserIds" id="toUserIds" value=""/>
        <textarea style="font-family:MicroSoft YaHei;font-size: 12px;" readonly cols=60 name="toUserNames" id="toUserNames" rows='3'></textarea>
        <span class='addSpan'>
			<img src="/common/zt_webframe/imgs/xzbg/xwfb/add.png" onClick="selectUser(['toUserIds', 'toUserNames'],'<%=TeeModelIdConst.NEWS_SEND_PRIV%>' , '1')" value="选择"/>
			 &nbsp;&nbsp;
			 <img src="/common/zt_webframe/imgs/xzbg/xwfb/clear.png" onClick="clearData('toUserIds','toUserNames')" value="清空"/>
		</span>
    </td>
   </tr>
      <tr>
      	<td style="text-indent:10px;">提示：</td>
   		<td class="TableData"><font color='red'>
   			当用户，部门，角色都为空时，默认全部门发布
   		</font></td>
   </tr>
   	<tr>
		<td  style="text-indent:10px;" class="TableData" >发布时间：</td>
		<td   class="TableData">
		<input type="text" name="newsTime" id="newsTime" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate BigInput">
		<a herf="javascript:void(0);"  onclick="setCurrTime();" style="cursor:pointer;" /> 默认为当前时间</a></td>
	</tr>
	<tr>
   		<td style="text-indent:10px;"  class="TableData"  >评论：</td>
    	<td class="TableData" align="left">
    		<select style="font-family:MicroSoft YaHei;font-size: 12px;" name="anonymityYn" class="BigSelect">
    			 <option value="0">实名评论</option> 
    			 <option selected value="1">匿名评论</option> 
    			 <option value="2">禁止评论</option>
    		</select>
   	    </td>
   </tr>
   <tr>
   		<td style="text-indent:10px;"  class="TableData">重要：</td>
    	<td   class="TableData" align="left">
    		<input type="checkbox" name="top" value="1"/>使新闻显示为重要 
   	    </td>
   </tr>
   <tr>
   		<td style="text-indent:10px;"  class="TableData">附件选择：</td>
    	<td   class="TableData" align="left">
    	<span id="fileModel"></span>
    	<div id="upfileList"></div>
   	    </td>
   </tr>
    <tr>
   		<td style="text-indent:10px;"  class="TableData">提醒：</td>
    	<td   class="TableData" align="left" id="sms">
    	<input name="mailRemind" id="mailRemind" type="checkbox" value="1" checked="checked" />
    	使用内部短信提醒   
   	    </td>
   </tr>
   <tr style="display:none">
   		<td style="text-indent:10px;"  class="TableData">是否发送到微信企业号：</td>
    	<td   class="TableData" align="left">
    		<input type="checkbox" name="wechat" value="1"/>选择发送
   	    </td>
   </tr>
   <tr>
    	<td class="TableData" align="left" style="text-indent:10px" >内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容：
    	</td>
    	<td>
	    	<textarea style="width: 900px;font-family:MicroSoft YaHei;font-size: 12px;" id="content" name="content"  required></textarea>
   	    </td>
   </tr>
   
   
 <!--   <tr id="contentDiv" style="display:none">
    	<td style="text-indent:10px;" class="TableData" align="left" colspan="2" width="">
	    	<DIV style="">
					<textarea style="" id="content" name="content" class="BigTextarea" ></textarea>
			</DIV>
   	    </td>
   </tr> -->
   <tr>
	    <td style="text-indent:10px;">
	      
	        <input type='hidden' value='<%=id %>' name='sid'/>
	    </td>
   </tr>
   
</table>
</div>
  </form>
</body>
<script>
	$("#form1").validate();
</script>
</html>
 