<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String documentId = request.getParameter("documentId");
	String channelId = request.getParameter("channelId");
	String siteId = request.getParameter("siteId");
	String isNew = request.getParameter("isNew");//是否为新建
	String model = TeeAttachmentModelKeys.cms;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script>
var editor;
var documentId = <%=documentId%>;
var channelId = <%=channelId%>;
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
	
	doInitUpload();
	editor = CKEDITOR.replace('content');		
	editor.on( 'instanceReady', function(event){		
		var editor = event.editor;
		editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'content' ).getParent().$.offsetHeight );
// 		setInterval(function()			
// 		{						
// 			editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'content' ).getParent().$.offsetHeight );
// 		},5000);
		$(window).resize(function(){
			editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'content' ).getParent().$.offsetHeight );
		});
		
		if(isNew!=true){//加载数据
			var json = tools.requestJsonRs(contextPath+"/cmsDocument/getDocument.action",{docId:documentId});
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
				
				$("input[clazz=cb]").each(function(i,obj){
					var val = parseInt(obj.value);
					if((json.rtData.category & val) == val){
						$(obj).attr("checked","checked");
					}
				});
				
				if(json.rtData.thumbnail!=0){
					$("#thumbImg").attr("src",contextPath+"/attachmentController/downFile.action?id="+json.rtData.thumbnail);
				}
				
				
				editor.setData(json.rtData.htmlContent);
				/* var newsTime = getFormatDateStr(json.rtData.writeTime , 'yyyy-MM-dd HH:mm:ss');
				$("#writeTimeDesc").attr("value",newsTime); */
				
				if(json.rtData.attachMentModel.length>0){
					$("#attachTr").show();
					var attaches = json.rtData.attachMentModel;
					for(var i=0;i<attaches.length;i++){
						var fileItem = tools.getAttachElement(attaches[i]);
						$("#attachments").append(fileItem);
					}
				}
// 				var json = tools.requestJsonRs(contextPath+"/cmsDocument/getChnlDoc.action",{chnlId:channelId,docId:documentId});
// 				$("#top").attr("value",json.rtData.top);
			}
		}
		
		}, null,null,9999);
}

function save(flag){
	//获取文档标题
	var docTitle=$("#docTitle").val();
	if(docTitle==""||docTitle==null){
		alert("文档标题不能为空！");
		return;
	}
    
	
	var param = tools.formToJson($("#form"));
	param["htmlContent"] = editor.getData();
	param["content"] = editor.document.getBody().getText();
	var categoryVal = 0;
	$("input[clazz=cb]").each(function(i,obj){
		if(obj.checked){
			categoryVal+=Number(obj.value);
		}
	});
	param["category"] = categoryVal;
	
	if(flag==1){//保存
		if(isNew==true){//新建
			tools.requestJsonRs(contextPath+"/cmsDocument/addDocument.action",param,true,function(json){
				if(json.rtState){
					try{
						opener.datagrid.datagrid("reload");
					}catch(e){
						
					}
					window.location = contextPath+"/system/subsys/cms/docmgr.jsp?documentId="+json.rtData.sid+"&channelId="+json.rtData.chnlId;
				}
			});
		}else{//保存
			tools.requestJsonRs(contextPath+"/cmsDocument/updateDocument.action",param,true,function(json){
				if(json.rtState){
					try{
						opener.datagrid.datagrid("reload");
					}catch(e){
						
					}
					window.location.reload();
				}
			});
		}
	}else if(flag==2){//保存并关闭
		if(isNew==true){//新建
			tools.requestJsonRs(contextPath+"/cmsDocument/addDocument.action",param,true,function(json){
				if(json.rtState){
					try{
						opener.datagrid.datagrid("reload");
					}catch(e){
						
					}
					CloseWindow();
				}
			});
		}else{//保存
			tools.requestJsonRs(contextPath+"/cmsDocument/updateDocument.action",param,true,function(json){
				if(json.rtState){
					try{
						opener.datagrid.datagrid("reload");
					}catch(e){
						
					}
					CloseWindow();
				}
			});
		}
	}else if(flag==3){//保存并新建
		if(isNew==true){//新建
			tools.requestJsonRs(contextPath+"/cmsDocument/addDocument.action",param,true,function(json){
				if(json.rtState){
					try{
						opener.datagrid.datagrid("reload");
					}catch(e){
						
					}
					window.location = contextPath+"/system/subsys/cms/docmgr.jsp?isNew=true&channelId="+channelId;
				}
			});
		}else{//保存
			tools.requestJsonRs(contextPath+"/cmsDocument/updateDocument.action",param,true,function(json){
				if(json.rtState){
					try{
						opener.datagrid.datagrid("reload");
					}catch(e){
						
					}
					window.location = contextPath+"/system/subsys/cms/docmgr.jsp?isNew=true&channelId="+channelId;
				}
			});
		}
	}
	
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
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
}
</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;background:#f0f0f0;font-size:12px;">
<div id="layout">
<form id="form" enctype="multipart/form-data" method="post">
	<div layout="north" height="60" style="padding:8px">
		文档标题：<input id="docTitle" name="docTitle" type="text" class="BigInput" style="width:500px"/>
		<p id="catDiv">所属标签：</p>		
	</div>
	<div layout="south" height="40" style="padding:4px">
		<center>
			<button class="btn btn-success" type="button" onclick="save(1)">保存</button>
			<button class="btn btn-default" type="button" onclick="save(2)">保存并关闭</button>
			<button class="btn btn-default" type="button" onclick="save(3)">保存并新建</button>
			<button class="btn btn-default" type="button" onclick="window.close();">关闭</button>
		</center>
	</div>
	<div layout="east" width="300" style="padding:5px;">
		<div class="panel-group" id="accordion">
		  <div class="panel panel-default">
		    <div class="panel-heading">
		      <h4 class="panel-title">
		        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
		          	基本属性
		        </a>
		      </h4>
		    </div>
		    <div id="collapseOne" class="panel-collapse collapse in">
		      <div class="panel-body" style="padding:5px;">
		      	<table style="font-size:12px;">
		      		<tr>
		      			<td>主标题：</td>
		      			<td><input class="BigInput" type="text" id="mainTitle" name="mainTitle"/></td>
		      		</tr>
		      		<tr>
		      			<td>副标题：</td>
		      			<td><input class="BigInput" type="text" id="subTitle" name="subTitle"/></td>
		      		</tr>
		      		<tr>
		      			<td>关键字：</td>
		      			<td><input class="BigInput" type="text" id="keyWords" name="keyWords"/></td>
		      		</tr>
		      		<tr>
		      			<td>摘要：</td>
		      			<td>
		      				<textarea class="BigTextarea" style="width:100%;height:100px" id="abstracts" name="abstracts"></textarea>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td>来源：</td>
		      			<td><input class="BigInput" type="text" id="source" name="source"/></td>
		      		</tr>
		      		<tr>
		      			<td>作者：</td>
		      			<td><input class="BigInput" type="text" id="author" name="author"/></td>
		      		</tr>
		      		<tr>
		      			<td>撰写时间：</td>
		      			<td><input class="BigInput" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" name="writeTimeDesc" id="writeTimeDesc"/></td>
		      		</tr>
		      		<tr>
		      			<td>置顶设置：</td>
		      			<td>
		      				<select class="BigSelect" id="top" name="top">
		      					<option value="0">不置顶</option>
		      					<option value="1">永久置顶</option>
		      				</select>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td>缩略图：</td>
		      			<td>
		      				<input type="hidden" id="thumbnail" name="thumbnail"/>
		      				<img id="thumbImg" style="width:120px;height:80px;"/>
		      				<br/>
		      				<button type="button" onclick="$('#thumbImg').removeAttr('src');$('#thumbnail').val('0');" class="btn btn-default">清除</button>
<!-- 		      				<button type="button" class="btn btn-default">上传</button> -->
		      			</td>
		      		</tr>
		      	</table>
		      </div>
		    </div>
		  </div>
		  <div class="panel panel-default">
		    <div class="panel-heading">
		      <h4 class="panel-title">
		        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
		          	附件管理
		        </a>
		      </h4>
		    </div>
		    <div id="collapseTwo" class="panel-collapse collapse">
		      <div class="panel-body">
		      		<div style="min-height:200px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
		      </div>
		    </div>
		  </div>
		<!--   <div class="panel panel-default">
		    <div class="panel-heading">
		      <h4 class="panel-title">
		        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
		        	相关文档
		        </a>
		      </h4>
		    </div>
		    <div id="collapseThree" class="panel-collapse collapse">
		      <div class="panel-body">
		      	
		      </div>
		    </div>
		  </div>
		  <div class="panel panel-default">
		    <div class="panel-heading">
		      <h4 class="panel-title">
		        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapse4">
		        	扩展字段
		        </a>
		      </h4>
		    </div>
		    <div id="collapse4" class="panel-collapse collapse">
		      <div class="panel-body">
		      	
		      </div>
		    </div>
		  </div> -->
		</div>
	</div>
	<div layout="center">
		<textarea style="" id="content" name="htmlContent" class="BigTextarea" ></textarea>
	</div>
	
	<input type="hidden" name="chnlId" value="<%=channelId %>" />
	<input type="hidden" name="docId" value="<%=documentId %>" />
</form>
</div>
</body>
</html>