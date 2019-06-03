<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String sid = request.getParameter("sid");
	String rootId = request.getParameter("rootId");
	String folder = request.getParameter("folder");
%>
<title>图片浏览</title>
<%@include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
  <link href="<%=request.getContextPath()%>/common/icheck-1.x/skins/all.css?v=1.0.2" rel="stylesheet">
  <script src="<%=request.getContextPath()%>/common/icheck-1.x/icheck.js?v=1.0.2"></script>
<style type="text/css">
	.fileContainor{
		margin:0px;
		padding:0px;
		list-style-type: none;
	}
	#wrapper{
		min-height:520px;
	}
	.imgList{
		float:left;
		width:150px;
		height:150px;
		margin:10px 10px;
		cursor:pointer;
		border:2px solid #f3f3f3;
	}
	
	.imgList:HOVER{
		border:2px solid #2489c5;
	}
	.checked{
		border:2px solid #2489c5;
	}
	.chkbox{
		width:150px;
		height:24px;
		position:absolute;
	}
	.modal-test{
		width: 564px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 120px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>
<script>

var nodeId = "";
var curPage=1;
var sid = "<%=sid%>";
var rootId = "<%=rootId%>";
var folder = "<%=folder%>";

document.onselectstart = new Function("return false"); 

function doInit(){
	openFolderFunc(sid);
	
	if(isPriv("upload")){
		$("#upload").css("display","inline-block");
	}else{
		$("#upload").css("display","none");
	}
	if(isPriv("manager")){
		$("#manager").css("display","inline-block");
	}else{
		$("#manager").css("display","none");
	}
}

function loaded(){
	 myscroll=new iScroll("wrapper",{onScrollEnd:function(){
		 loadMore();
	 	},
	 	vScrollbar:true,
	 	momentum:false
	 });

}

/**
 * 打开文件夹
 * @param sid
 */
function openFolderFunc(sid){
	//clearTimeout(timeout);
	nodeId = sid;
	var sortType = $("#sortType").val();
	var url = contextPath+"/teeImgBaseController/getPictureList.action";
	tools.requestJsonRs(url,{"id":sid,"sortType":sortType,"pageSize":500,"curPage":1},true,function(jsonRs){
		var html ="<ul class='fileContainor'>";
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			var m=0;
			$("#count").html("共（"+data.length+"）张");
			if(data.length>0){
				for(var i= 0;i<data.length;i++){
					var filePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[i].thumbFilePath;
					html+="<li class='imgList' title='"+data[i].fileName+"' onclick=\"openPhone('"+sid+"','"+data[i].thumbFilePath+"');\" id='"+data[i].filePath+"' style='background:url("+filePath+") no-repeat center;'>"
						+"<div class='chkbox' style='display:none;'><input type='checkbox' class='cbx' onclick='window.event.cancelBubble=true'/></div></li>";
					m++;
				}
				html+="</ul>";
				$("#wrapper").html(html);
			}else{
				messageMsg("当前目录下没有图片！","wrapper","info");
			}
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}

		$(".imgList").hover(function(){
			$(this).addClass("checked").children("div").show();
		},function(){
			if(!$(this).find("div input:first").attr("checked")==true){
				$(this).removeClass("checked").children("div").hide();
			}
			
		});
		
		$(".cbx").iCheck({
	        checkboxClass: 'icheckbox_square-blue',
	        radioClass: 'iradio_square-blue',
	        increaseArea: '20%'
	      });
	});
}


function openPhone(sid,thumbFilePath){
	var url = contextPath+"/system/core/base/imgBase/show/show.jsp?sid="+sid+"&thumbFilePath="+thumbFilePath;
	openFullWindow(url);
}

function upload(){
	var url = contextPath+"/system/core/base/imgBase/show/upload/index.jsp?sid="+nodeId;
	bsWindow(url,"图片上传",{height:"300",width:"650",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						openFolderFunc(nodeId);
						return true;
					}else{
						return false;
					}
				}else if(v=="关闭"){
					return  true;
				}
				
			}});
}

function downAll(){
	  var paths="";
	  $(".imgList").each(function(i, item) {
		  if($(this).find("div input:first").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	  var url = contextPath+"/teeImgBaseController/zipDownFile.action?paths="+paths;
	  location.href=url;
}

/**
 * 删除选中文件
 */
function deleteAll(){
	  var paths="";
	  $(".imgList").each(function(i, item) {
		  if($(this).find("div input:first").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	  
	  $.MsgBox.Confirm ("提示", "确认要删除选中的图片吗？删除后不可恢复", function(){
		  var url = contextPath+"/teeImgBaseController/deleteAll.action";
		  var jsonRs = tools.requestJsonRs(url,{"paths":paths});
		  if(jsonRs.rtState){
			  top.$.jBox.tip(jsonRs.rtMsg,"success");
			  openFolderFunc(nodeId);
		  }else{
			  top.$.jBox.tip(jsonRs.rtMsg,"error"); 
		  }
	  });
}


/**
 * 创建文件夹
 */
function createFolder(){
	  var folderName = $("#dirName").val();
	  if(folderName==""||folderName==null){
		  $.MsgBox.Alert_auto("请填写文件夹名称！");
		  return;
	  }
	  var url = contextPath+"/teeImgBaseController/createFolder.action";
	  var jsonRs = tools.requestJsonRs(url,{"sid":nodeId,"folderName":folderName});
	  if(jsonRs.rtState){
		  $.MsgBox.Alert_auto(jsonRs.rtMsg);
		 // $('#folderName').modal('hide');
		  $(".modal-win-close").click();
		  parent.getFileTree();
	  }else{
		  $.MsgBox.Alert_auto(jsonRs.rtMsg);
	  }
}

/**
 * 创建文件夹
 */
function deleteFolder(){

	if(sid==rootId){
		$.MsgBox.Alert_auto("当前文件夹为根目录，不能删除！");
		return;
	}
	
	 $.MsgBox.Confirm ("提示", "确认要删除当前文件目录吗？会同时删除当前目录下所有文件。", function(){
		 var url = contextPath+"/teeImgBaseController/deleteFolder.action";
		  var jsonRs = tools.requestJsonRs(url,{"sid":nodeId});
		  if(jsonRs.rtState){
			  $.MsgBox.Alert_auto(jsonRs.rtMsg);
			  parent.getFileTree();
		  }else{
			  $.MsgBox.Alert_auto(jsonRs.rtMsg);
		  }
	  });
}

/**
 * 获取树根几点id;
 */
function getRoot(sid){
	zTreeObj = getZTrreObj(); 
	var node = zTreeObj.getNodeByParam("id",sid,null);
	if(node.getParentNode()==null){
		return node.id;
	}else{
		return getRoot(node.getParentNode().id);
	}
}

 function isPriv(privType){
	  var url = contextPath+"/teeImgBaseController/isPriv.action";
	  var jsonRs = tools.requestJsonRs(url,{"rootId":rootId,"privType":privType});
	  if(jsonRs.rtState){
		  return jsonRs.rtData;
	  }else{
		  return false;
	  }
	 
 }
 
 /**
 *
 *复制文件
 */
 function copyFiles(){
	 var paths="";
	  $(".imgList").each(function(i, item) {
		  if($(this).find("div input:first").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	var url = contextPath+"/system/core/base/imgBase/show/folderTree.jsp?paths="+paths+"&type=copy"+"&rootId="+rootId;
	bsWindow(url,"复制目标选择",{height:"300",width:"450",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],
		submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						openFolderFunc(nodeId);
						return true;
					}else{
						return false;
					}
				}else if(v=="关闭"){
					return  true;
				}
				
			}});
}
 
/**
*剪切文件
*
*/
function cutFiles(){
	 var paths="";
	  $(".imgList").each(function(i, item) {
		  if($(this).find("div input:first").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	var url = contextPath+"/system/core/base/imgBase/show/folderTree.jsp?paths="+paths+"&type=cut"+"&rootId="+rootId;
	bsWindow(url,"剪切目标选择",{height:"300",width:"450",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],
		submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						openFolderFunc(nodeId);
						return true;
					}else{
						return false;
					}
				}else if(v=="关闭"){
					return true;
				}
				
			}});
}

function sort(){
	curPage=1;
	$("#loadMore").css("display","inline-block");
	openFolderFunc(nodeId);
}


 
</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit();" style="overflow:hidden">
	<div style="position:absolute;left:0px;right:0px;top:0px;height:80px;border-bottom:1px solid #d4d4d4">
		<h5 style="font-family:微软雅黑;font-weight:bold;font-size:18px;margin-left:10px;margin-top: 10px"><%=folder %>&nbsp;&nbsp;<span style="font-size:12px;color:gray;font-weight:normal;" id="count"></span></h5>
		<div id="tips" style='margin:0 auto;font-size:16px;color:red;text-align:center;'></div>
			<div id="controlArea" style="margin:10px 10px;">
			    <select id='sortType' name='sortType' class="BigSelect" onchange='sort()'  style="height: 23px">
			    	<option value=''>请选择排序方式</option>
			    	<option value='fileName'>按文件名称</option>
			    	<option value='lastModifiedTime'>按文件时间</option>
			    	<option value='fileSize'>按文件大小</option>
			    </select>
				<button class="btn-win-white" onclick="downAll()">批量下载</button>
				<div id="upload" style='display:none;'>
					<button class="btn-win-white" onclick="upload()">批量上传</button>
				</div>
				<div id="manager" style='display:none;'>
					<button class="btn-win-white modal-menu-test"  onclick="$(this).modal();">新建文件夹</button>
					<button class="btn-del-red" onclick="deleteAll()">文件删除</button>
					<button class="btn-del-red" onclick="deleteFolder()">文件夹删除</button>
					<button class="btn-win-white" onclick="copyFiles()">复制</button>
					<button class="btn-win-white" onclick="cutFiles()">剪切</button>
				</div>
				
				
			</div>
	</div>
	<div style='position:absolute;left:0px;right:0px;top:81px;bottom:0px;overflow:auto;background:#f3f3f3'>
		<div id="imgBaseContainor">
			<div id="wrapper" style="padding-top: 10px">
			
			</div>
			<div id="loadMore" onclick='loadMore();'></div>
			
		
  <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			新建文件夹
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">
				<span>文件夹名称:</span>
				<input type="text" id="dirName" name="dirName" />
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="createFolder();" value = '确定'/>
	</div>
</div>

	</div>
</body>
</html>