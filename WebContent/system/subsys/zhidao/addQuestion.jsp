<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String model=TeeAttachmentModelKeys.zhiDaoQuestion;
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
   String title="添加问题";
   if(sid>0){
	   title="编辑问题";
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我要提问</title>
<%@ include file="/header/header2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="/system/lucene/js/bootstrap.min.css">
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="/system/lucene/js/jqPaginator.js"></script>

<style type="text/css">
a:link {
COLOR: #000; TEXT-DECORATION: none
}
#result a:hover {
COLOR: #3388ff; TEXT-DECORATION: none
}
a:visited {
COLOR: #000; TEXT-DECORATION: none
}
</style>
<script type="text/javascript">
var sid=<%=sid %>;
function doInit(){
	renderCat();
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	
	if(sid>0){//编辑
		getInfoBySid();
	}else{//新建
		$("#title").bind("change",getRelations);
	}
}

//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/zhiDaoQuestionController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		if(data.attachMentModel.length>0){
			$("#attachTr").show();
			var attaches = data.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
		bindJsonObj2Cntrl(data);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	
}

function  back(){
	 /*  var url=contextPath+"/system/subsys/zhidao/index.jsp";
	  window.location.href=url; */
	  history.go(-1);
}

//获取相关问题
function getRelations(){
	var title=$("#title").val();
	if(title!=""&&title!=null&&title!="null"){
		$("#relationQuestionDiv").show();
		//获取相关问题的总页数
		var allPages=0;
		var keyWords="";
		var url=contextPath+"/zhiDaoQuestionController/getRelationsTotalPages.action";
		var json=tools.requestJsonRs(url,{title:title});
		if(json.rtState){
			var data=json.rtData;
			allPages=data.allPage;
			keyWords=data.keyWords;
		    $("#label").val(keyWords);
		}
		//alert(allPages);
		if(allPages==0){
			$("#result").html("暂无相关问题！");
			$("#pagination1").hide();
		}else{
			$("#result").html("");
			$("#pagination1").show();
			getRelationData(title,1);
			$.jqPaginator('#pagination1', {
			    totalPages: allPages,
			    visiblePages: 5,
			    currentPage: 1,
			    onPageChange: function (num,type) {
			    	getRelationData(title,num);
			    }
			});
		}
		
	}else{
		$("#relationQuestionDiv").hide();
		$("#result").empty();
	}
}


//根据标题  页码获取相关问题
function getRelationData(title,num){
	var url=contextPath+"/zhiDaoQuestionController/getRelationsByTitle.action";
	var json=tools.requestJsonRs(url,{title:title,page:num,pageSize:5});
	if(json.rtState){
		$("#result").html("");
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			html.push("<table style='width:100px'>");
			for(var i=0;i<data.length;i++){
				html.push("<tr><td style='width:100px'><a onclick=\"detail("+data[i].sid+")\" href=\"#\" style='display:block;max-width:350px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis'>"+data[i].title+"</a></td></tr>");
			}
			html.push("</table>");
		}
		$("#result").append(html.join(""));
	}
}

//相关问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/zhidao/detail.jsp?sid="+sid;
	openFullWindow(url);
}

//保存
function addOrUpdate(){
	if(checkForm()){
		var param=tools.formToJson($("#form1"));
		var url=contextPath+"/zhiDaoQuestionController/addOrUpdate.action";
		var json=tools.requestJsonRs(url,param);
		if(json.rtState){
			$.MsgBox.Alert_auto("提交成功！",function(){
				if(sid>0){
					window.opener.location.reload();
				}
				back();
			});
		}
	}
}


//验证
function checkForm(){
	var title=$("#title").val();
	var catId=$("#catId").val();
	if(title==""||title==null||title=="null"){
		$.MsgBox.Alert_auto("请填写问题！");
		return false;
	}
	if(catId==0||catId=="0"){
		$.MsgBox.Alert_auto("请选择所属分类！");
		return false;
	}
	return true;
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
				html.push("<option style=\"font-size:14px;font-weight:bold\" value="+parent.sid+">"+parent.catName+"</option>");
			    if(children!=null&&children.length>0){
			    	for(var j=0;j<children.length;j++){
			    		html.push("<option style=\"font-size:12px;\" value="+children[j].sid+">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+children[j].catName+"</option>");
			    	}
			    }
			}
		}
		$("#catId").append(html.join(""));
	}
}

</script>
</head>
<body style="padding-left: 10px;padding-right: 10px" onload="doInit()">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/zhidao/img/icon_add.png">
		<span class="title"><%=title %></span>
	</div>
   <div class="fr right">
      <input type="button" value="提交" class="btn-win-white" onclick="addOrUpdate();"/>
      <input type="button" value="返回" class="btn-del-red" onclick="back();"/>
   </div>
</div>
   <form id="form1" name="form1" enctype="multipart/form-data" method="post" >
   <input type="hidden" name="sid" id="sid" value="<%=sid %>"/>
   <table class="TableBlock_page">
       <tr>
          <td class="TableData" style="text-indent: 10px;width:150px;">问题：</td>
          <td class="TableData">
             <input type="text" name="title" id="title"  class="BigInput" />
          </td>
       </tr>
       
       <tr id="relationQuestionDiv" style="display:none">
          <td class="TableData" style="text-indent: 10px;">相关问题：</td>
          <td class="TableData" id="relationTd">
          	 <div id="result">
          	 
          	 </div>
          	 <div>
          	 	<ul class="pagination" id="pagination1"></ul>
          	 </div>
          </td>
       </tr>
       <tr>
          <td class="TableData" style="text-indent: 10px;">描述：</td>
          <td class="TableData">
             <textarea rows="6" cols="10" name="description" id="description" class="BigTextArea"></textarea>
          </td>
       </tr>
       <tr>
          <td class="TableData" style="text-indent: 10px;">标签：</td>
          <td class="TableData">
             <input type="text" name="label" id="label" class="BigInput" />
          </td>
       </tr>
       <tr>
          <td class="TableData" style="text-indent: 10px;">分类：</td>
          <td class="TableData">
          	<select id="catId" name="catId">
          		<option value="0">请选择所属分类</option>
          	</select>
          </td>
       </tr>
       <tr style="display: none;" id="attachTr">
          <td class="TableData" style="text-indent: 10px;">附件：</td>
          <td  class="TableData" align="left">
    		<span id="attachments"></span>
   	      </td>
       </tr>
        <tr>
          <td class="TableData" style="text-indent: 10px;">附件上传：</td>
          <td  class="TableData" align="left">
    		<div id="fileContainer2"></div>
				<a id="uploadHolder2" class="add_swfupload">
					<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
				</a>
				<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
   	      </td>
       </tr>
   </table>
   </form>
</body>
</html>