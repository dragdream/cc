<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
int rootFolderPriv = TeeStringUtil.getInteger(request.getParameter("rootFolderPriv"), 0);
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
int userId=loginUser.getUuid();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileManage.js?v=3"></script>
<style type="text/css">
.returnButton{
        padding:5px 8px;
		/* padding-left:22px; */
		text-align:center; 
		/* text-align:right; 
		background-repeat:no-repeat;
		background-position:6px center; */
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
}


.TableBlock_page{
   border:2px solid #f2f2f2;
}
</style>
<script type="text/javascript">
var sid = <%=sid%>;
var folderSid=<%=folderSid%>;
var rootFolderPriv=<%=rootFolderPriv%>;
var userId=<%=userId%>;
var flag=0;
function doInit(){
	getInfoById(sid);
	addTabTitle();
	
}
function getInfoById(sid){
	var url =   "<%=contextPath%>/fileNetdisk/getFileNetdiskById.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			$(".title").html(prc.fileName);
			/* var attaches = prc.attacheModels;
			if(attaches){
				attaches.priv = priv;
				var fileItem = tools.getAttachElement(attaches);
				$("#fileContainer").append(fileItem);
			} */
            var html="<li style='margin-left: 10px;' onclick='reNameFunc("+prc.sid+",\""+prc.fileTypeExt+"\")'><a href='javascript:void(0);'>重命名 </a></li>";
			if(prc.createrId == userId || (rootFolderPriv & 64)==64){//上传者或者管理权限
			   html+="<li style='margin-left: 10px;' onclick='contentFunc2("+prc.sid+","+sid+","+rootFolderPriv+");'><a href='javascript:void(0);'>编辑</a></li>";
			}
			html+="<li style='margin-left: 10px;' onclick='zhiShiPingFei("+prc.picCount+");'><a href='javascript:void(0);'>知识评分</a></li>";
			
			if(prc.fileTypeExt=="doc" || prc.fileTypeExt=="docx" || prc.fileTypeExt=="xls" || prc.fileTypeExt=="xlsx" || prc.fileTypeExt=="ppt" || prc.fileTypeExt=="pptx"){
				html+="<li style='margin-left: 10px;' onclick='shengChengBanb("+prc.attachSid+");'><a href='javascript:void(0);'>生成版本</a></li>";
				flag=1;
			}
			/* if(prc.isSignRead !='1'){
				html += "<li style='margin-left: 10px;' onclick='signReadFunc(" + prc.sid + ");'><a href='javascript:void(0);'>签阅 </a></li>";
			} */
			$(".btn-content").html(html);
		}
	}
}

//生成版本
function shengChengBanb(attachSid){
	 var url = contextPath + "/system/core/base/fileNetdisk/fileManage/banBen.jsp?attachSid="+attachSid+"&sid="+sid;
	  bsWindow(url,"生成版本",{width:"300",height:"160",buttons:[{name:"保存",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
	      if(cw.doCutSubmit()){
	    	  bianji();
	    	  window.location.reload();
	    	  return true;
	      }
	    }else if(v=="关闭"){
	      return true;
	    }
	    
	  }});
}

//编辑
function bianji(){
	var url =   "<%=contextPath%>/fileNetdisk/getFileNetdiskById.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	var prc = jsonObj.rtData;
	if(prc!=null){
		if(prc.fileTypeExt=="doc" || prc.fileTypeExt=="xlsx" || prc.fileTypeExt=="xls" || prc.fileTypeExt=="pptx" || prc.fileTypeExt=="ppt" || prc.fileTypeExt=="docx"){
			window.open("<%=contextPath%>/system/core/ntko/indexNtko.jsp?attachmentId="+prc.attachSid+"&attachmentName="+prc.fileName+"&moudle=workFlow&op=4");
		}
	}
}

function contentFunc2(sid,folderSid,rootFolderPriv){
	var url = contextPath + "/system/core/base/fileNetdisk/fileManage/editContent.jsp?sid=" + sid + "&folderSid=" + folderSid + "&rootFolderPriv=" + rootFolderPriv;
	location.href= url;
}
function moreWorkList(){
	if($(".moreWork").is(":hidden")){
		$(".moreWork").show();
	}else{
		$(".moreWork").hide();
	}
}

//知识评分
function zhiShiPingFei(picCount){
	 var url = contextPath + "/system/core/base/fileNetdisk/fileManage/zhishiPingfei.jsp?picCount="+picCount+"&sid="+sid;
	  bsWindow(url,"知识评分",{width:"300",height:"160",buttons:[{name:"保存",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h){
	    var cw = h[0].contentWindow;
	    if(v=="保存"){
	      if(cw.doCutSubmit()){
	    	  window.location.reload();
	    	  return true;
	      }
	    }else if(v=="关闭"){
	      return true;
	    }
	    
	  }});
}
function addTabTitle(){
	 if(flag==1){
		 $.addTab("tab","tab-content",[{title:"文件详情",url:contextPath+"/system/core/base/fileNetdisk/fileManage/showContent.jsp?sid="+sid+"&folderSid="+folderSid+"&rootFolderPriv="+rootFolderPriv},
		                               {title:"相关评论",url:contextPath+"/system/core/base/fileNetdisk/fileManage/fileComment.jsp?sid="+sid},
		                              {title:"历史版本",url:contextPath+"/system/core/base/fileNetdisk/fileManage/historyList.jsp?sid="+sid},
		                              {title:"操作记录",url:contextPath+"/system/core/base/fileNetdisk/fileManage/showHistoryList.jsp?sid="+sid},
		                              {title:"签阅情况",url:contextPath+"/system/core/base/fileNetdisk/fileManage/showSignRead.jsp?sid="+sid},
		                              ]); 
			 
		 }else{
			 $.addTab("tab","tab-content",[{title:"文件详情",url:contextPath+"/system/core/base/fileNetdisk/fileManage/showContent.jsp?sid="+sid+"&folderSid="+folderSid+"&rootFolderPriv="+rootFolderPriv},
			                               {title:"相关评论",url:contextPath+"/system/core/base/fileNetdisk/fileManage/fileComment.jsp?sid="+sid},
			                              {title:"操作记录",url:contextPath+"/system/core/base/fileNetdisk/fileManage/showHistoryList.jsp?sid="+sid},
			                              {title:"签阅情况",url:contextPath+"/system/core/base/fileNetdisk/fileManage/showSignRead.jsp?sid="+sid},
			                              ]); 
				 
		 }
}
</script>
</head>
<body onload="doInit();" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	<div class="titlebar clearfix">
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/core/base/fileNetdisk/fileManage/img/icon_xq.png">
		<p class="title"> </p>
		
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	 <div class="btn-group fr" style="margin-right: 10px;margin-top: -35px;">
		  <button type="button" class="btn-win-white btn-menu" onclick="moreWorkList();">
		    更多操作<span class="caret-down"></span>
		  </button>
		  <ul class="btn-content" style="background-color: #ffffff;border: 1px solid #cccccc;display: none;">
		  </ul>
	</div>
	
</body>
<script>


</script>
</html>