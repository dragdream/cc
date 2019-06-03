<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<title>图片浏览</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="js/iscroll.js"></script>
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
	}
	.imgList span{
		display:block;
		width:100%;
		height:100%;
	}
	.imgList a{
		display:block;
		width:100%;
		height:100%;
		border:1px solid #CCCCCC;
	}
	
	.imgList a:HOVER{
		width:100%;
		height:100%;
		border:1px solid blue;
	}
	.chkbox{
		width:150px;
		height:24px;
		position:absolute;
		background:#CCCCCC;
	}
	#loadMore{
		display:none;
		height:32px;
		width:100%;
		cursor:pointer;
		text-align:center;
		color:blue;
		line-height:32px;
		margin-bottom:20px;
	}
</style>
<script>

var nodeId = "";
var rootId="";
var curPage=1;

function doInit(){
	getFileTree();
	messageMsg("请选择需要浏览的图片库！","tips","info");
}

function loaded(){
	 myscroll=new iScroll("wrapper",{onScrollEnd:function(){
		 loadMore();
	 	},
	 	vScrollbar:true,
	 	momentum:false
	 });

}

function getFileTree(){
	var url =  "<%=contextPath %>/teeImgBaseController/getImgBaseTree.action";
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree
		};
	zTreeObj = ZTreeTool.config(config); 	
}


/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	var sid = treeNode.id;
	$("#tips").css("display","none");
	$("#loadMore").css("display","inline-block");
	$("#controlArea").css("display","");
	curPage=1;
// 	openFolderFunc(sid);
	rootId = getRoot(sid);
	$("#frame").attr("src","items.jsp?sid="+sid+"&rootId="+rootId+"&folder="+encodeURI(treeNode.name));
}

/**
 * zNodesLength ：树节点数
 * rtMsg： 返回的json rtMsg 信息
 */
function onAsyncSuccessFunc(zNodesLength , jsonData){
	//获取人员数组
	var personData = jsonData.rtData.parentSid;
	if(personData>0){
	  openFolderFunc(personData);
	}
}

/**
 * 获取树对象
 */
function getZTrreObj(){
  zTreeObj = $.fn.zTree.getZTreeObj("selectFolderZtree"); 
  return zTreeObj;
}

/**
 * 更新树节点
 * var nodeObj = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
 */
function updateZTreeNode(nodeObj){
	if(nodeObj && nodeObj.id){
		zTreeObj = getZTrreObj(); 
		var node = zTreeObj.getNodeByParam("id",nodeObj.id,null);
		if(node){
			if(nodeObj.name){
				node.name = nodeObj.name;
			}
			if(nodeObj.iconSkin){
				node.iconSkin = nodeObj.iconSkin;
			}
			zTreeObj.updateNode(node);
		}
		
	}
}

/**
 * 在节点id为parentNodeId的节点下创建树节点newNode
 * var newNode = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
 */
function createZTreeNode(parentNodeId,newNode){
  var zTreeObj = getZTrreObj();
  var parentNode = zTreeObj.getNodeByParam("id",parentNodeId,null);
  zTreeObj.addNodes(parentNode, newNode,false);
}

 /**
  * 根据节点id删除节点（多个节点id用逗号隔开）
  */
 function deledteZTreeNode(nodeIds){
   if(nodeIds){
     nodeIds +="";
     var zTreeObj = getZTrreObj();
     var nodeIdArray = nodeIds.split(",");
     for(var i=0;i<nodeIdArray.length;i++){
       if(nodeIdArray[i]){
         var deleteNode = zTreeObj.getNodeByParam("id",nodeIdArray[i],null);
         if(deleteNode){
           zTreeObj.removeNode(deleteNode);
         }
       }
     }
   }
 }

 /**
 * 刷新 zTree 
 */
function reloadZTreeNodeFunc(){
  var zTreeObj = getZTrreObj();
  zTreeObj.refresh();
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
	var jsonRs = tools.requestJsonRs(url,{"id":sid,"sortType":sortType,"pageSize":15,"curPage":curPage});
	var html ="<ul class='fileContainor'>";
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		var m=0;
		if(data.length>0){
			for(var i= 0;i<data.length;i++){
				var filePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[i].thumbFilePath;
				html+="<li class='imgList'><a title='"+data[i].fileName+"' href='javascript:void(0)' onclick=\"openPhone('"+sid+"','"+data[i].thumbFilePath+"');\" id='"+data[i].filePath+"'><span style='background:url("+filePath+") no-repeat center;'>"
					+"<div class='chkbox' style='display:none;'><input type='checkbox' onclick='window.event.cancelBubble=true'/></div></span></a></li>";
				m++;
			}
			html+="</ul>";
			$("#wrapper").html(html);
		}else{
			messageMsg("当前目录下没有图片！","wrapper","info");
		}
		if(m<15){
			$("#loadMore").css("display","none");
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}

	
  	$(".imgList").find("a").each(function(i, item) {
  		
  		/**
  		*鼠标经过
  		*
  		*/
		$(item).mouseover(function() {
			$(this).children('span').children("div").attr("style","display:block;");
			$(this).attr('style',"border:1px solid blue;");
		});
		
		/**
		*鼠标移开
		*/
		$(item).mouseout(function() {
			 if($(this).children('span').children("div").children("input").attr("checked")!="checked"){
				$(this).children('span').children("div").attr("style","display:none;");
				$(this).attr('style',"border:1px solid #cccccc;");
			 }else{
					$(this).attr('style',"border:1px solid blue;");
			 }
		});
	}); 
  	
  	//loaded();
}


function openPhone(sid,thumbFilePath){
	var url = contextPath+"/system/core/base/imgBase/show/show.jsp?sid="+sid+"&thumbFilePath="+thumbFilePath;
	window.open(url);
}

function upload(){
	var url = contextPath+"/system/core/base/imgBase/show/upload/index.jsp?sid="+nodeId;
	bsWindow(url,"图片上传",{height:"300",width:"650",submit:function(v,h){
				var cw = h[0].contentWindow;
				var json = cw.commit();
				if(json.rtState){
					openFolderFunc(nodeId);
					return true;
				}else{
					return false;
				}
			}});
}

function downAll(){
	  var paths="";
	  $(".imgList").find("a").each(function(i, item) {
		  if($(this).children('span').children("div").children("input").attr("checked")=="checked"){
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
	  $(".imgList").find("a").each(function(i, item) {
		  if($(this).children('span').children("div").children("input").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	  if(window.confirm("确认要删除选中的图片吗？删除后不可恢复","提示")){
		  var url = contextPath+"/teeImgBaseController/deleteAll.action";
		  var jsonRs = tools.requestJsonRs(url,{"paths":paths});
		  if(jsonRs.rtState){
			  top.$.jBox.tip(jsonRs.rtMsg,"success");
			  openFolderFunc(nodeId);
		  }else{
			  top.$.jBox.tip(jsonRs.rtMsg,"error"); 
		  }
	  }
}


/**
 * 创建文件夹
 */
function createFolder(){
	  var folderName = $("#dirName").val();
	  var url = contextPath+"/teeImgBaseController/createFolder.action";
	  var jsonRs = tools.requestJsonRs(url,{"sid":nodeId,"folderName":folderName});
	  if(jsonRs.rtState){
		  top.$.jBox.tip(jsonRs.rtMsg,"success");
		  $('#folderName').modal('hide');
		  getFileTree();
	  }else{
		  top.$.jBox.tip(jsonRs.rtMsg,"error"); 
	  }
}

/**
 * 创建文件夹
 */
function deleteFolder(){
	if(getRoot(nodeId)==nodeId){
		 $.MsgBox.Alert_auto("当前文件夹为根目录，不能删除！");
		return;
	}
	 if(window.confirm("确认要删除当前文件目录吗？会同时删除当前目录下所有文件。","提示")){
		  var url = contextPath+"/teeImgBaseController/deleteFolder.action";
		  var jsonRs = tools.requestJsonRs(url,{"sid":nodeId});
		  if(jsonRs.rtState){
			  top.$.jBox.tip(jsonRs.rtMsg,"success");
			  location.reload();
		  }else{
			  top.$.jBox.tip(jsonRs.rtMsg,"error"); 
		  }
	 }
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
	  $(".imgList").find("a").each(function(i, item) {
		  if($(this).children('span').children("div").children("input").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	var url = contextPath+"/system/core/base/imgBase/show/folderTree.jsp?paths="+paths+"&type=copy";
	bsWindow(url,"复制目标选择",{height:"300",width:"450",submit:function(v,h){
				var cw = h[0].contentWindow;
				var json = cw.commit();
				if(json.rtState){
					openFolderFunc(nodeId);
					return true;
				}else{
					return false;
				}
			}});
}
 
/**
*剪切文件
*
*/
function cutFiles(){
	 var paths="";
	  $(".imgList").find("a").each(function(i, item) {
		  if($(this).children('span').children("div").children("input").attr("checked")=="checked"){
			  paths+=$(this).attr("id")+",";
		  }
	  });
	  if(paths==""){
		  $.MsgBox.Alert_auto("至少选中一项！");
		  return;
	  }
	var url = contextPath+"/system/core/base/imgBase/show/folderTree.jsp?paths="+paths+"&type=cut";
	bsWindow(url,"剪切目标选择",{height:"300",width:"450",submit:function(v,h){
				var cw = h[0].contentWindow;
				var json = cw.commit();
				if(json.rtState){
					openFolderFunc(nodeId);
					return true;
				}else{
					return false;
				}
			}});
}

function sort(){
	curPage=1;
	$("#loadMore").css("display","inline-block");
	openFolderFunc(nodeId);
}


function loadMore(){
	curPage = curPage+1;
	var sortType = $("#sortType").val();
	var url = contextPath+"/teeImgBaseController/getPictureList.action";
	var jsonRs = tools.requestJsonRs(url,{"id":nodeId,"sortType":sortType,"pageSize":15,"curPage":curPage});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		var m = 0;
		if(data.length>0){
			for(var i= 0;i<data.length;i++){
				var filePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data[i].thumbFilePath;
				var li=$("<li class='imgList'></li>");
				var a=$("<a href='javascript:void(0)' onclick=\"openPhone('"+nodeId+"','"+data[i].thumbFilePath+"');\" id='"+data[i].filePath+"'></a>");
				var span=$("<span style='background:url("+filePath+") no-repeat center;'></span>");
				var div = $("<div class='chkbox' style='display:none;'></div>");
				var input = $("<input type='checkbox' onclick='window.event.cancelBubble=true'/>");
				input.appendTo(div);
				div.appendTo(span);
				span.appendTo(a);
				a.appendTo(li);
				$("#wrapper").children("ul").append(li);
				m++;
			}
		}else{
			 $.MsgBox.Alert_auto("已经全部加载完！");
		}
		if(m<15){
			$("#loadMore").css("display","none");
		}
	}
  	$(".imgList").find("a").each(function(i, item) {
  		
  		/**
  		*鼠标经过
  		*
  		*/
		$(item).mouseover(function() {
			$(this).children('span').children("div").attr("style","display:block;");
			$(this).attr('style',"border:1px solid blue;");
		});
		
		/**
		*鼠标移开
		*/
		$(item).mouseout(function() {
			 if($(this).children('span').children("div").children("input").attr("checked")!="checked"){
				$(this).children('span').children("div").attr("style","display:none;");
				$(this).attr('style',"border:1px solid #cccccc;");
			 }else{
					$(this).attr('style',"border:1px solid blue;");
			 }
		});
	}); 
}
 
</script>
<style>

</style>
</head>
<body onload="doInit();" style="margin:0px;padding:0px;overflow:hidden;background:#f6f7f9">
	
	<div class="base_layout_left fl" style="position:absolute;top:0;left:0;bottom:1px;text-align:center;width:180px;font-size: 16px;border-left: 1px" >
	<div class="left_title" style="width: 100%;height:80px;background: #d9dee4;line-height:80px;vertical-align: middle;">
		<img src="<%=contextPath %>/system/core/base/imgBase/img/tpk.png" style="display:inline-block;float:left;margin-left:15px;vertical-align: middle;margin-top: 20px;"/>
		<span class='fl' style ="margin-left:8px;" class='titleName'>图片库</span>
	</div>
	<div class="forScoll" style="margin-top: 10px">
	    <ul id="selectFolderZtree" class="ztree" style="border:0px;width:100%;height:atuo;"></ul>
	</div>
</div>
<div class="base_layout_right fl" style="position:absolute;top:0;left:180px;bottom:0;right:0;overflow:hidden;">
	<iframe id="frame" style="width:100%;height:100%" frameborder="no" src="comfireNo.jsp"></iframe>
</div>
</body>
</html>