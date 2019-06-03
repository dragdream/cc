<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String topicSectionId = TeeStringUtil.getString(request.getParameter("topicSectionId"), "");
	String topicId = TeeStringUtil.getString(request.getParameter("uuid"),"");
	//返回标识。1-返回我的帖子;其他值-返回 topicList.jsp;2-返回最新发帖；3-周帖子；4-月帖子 5返回板块
	String returnFlag = TeeStringUtil.getString(request.getParameter("returnFlag"),"");
	
	TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帖子详情</title>
<%@ include file="/header/header2.0.jsp"%>
<%-- <%@ include file="/header/easyui.jsp"%> --%>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath%>/common/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/subsys/topic/css/topic.css"/>
<script type="text/javascript" src="<%=contextPath %>/system/subsys/topic/pagination/pagination-with-styles.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">
var loginPersonId = "<%=loginPerson.getUuid()%>";
var topicId = "<%=topicId%>";
var topicSectionId = "<%=topicSectionId%>";
var editor;
var uEditorObj;//uEditor编辑器
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	
	    var url =   "<%=contextPath%>/TeeTopicSectionController/getInfoById.action";
		var para = {uuid : topicSectionId,isEdit:"1"};
		var jsonObj = tools.requestJsonRs(url, para);
		if(jsonObj.rtData.anonymous==0){
			$("#anonymous1").hide();
		}
	/* //初始化fck
	editor = CKEDITOR.replace('content',{
		width : 'auto',
		height:200
	}); */
	if(topicId){
		getInfoById(topicId);
		getTopicReplyPage(topicId);
		updateClickCount(topicId);
		/* CKEDITOR.on('instanceReady', function (e) {
			editor = e.editor;
			getInfoById(topicId);
			getTopicReplyPage(topicId);
			//getTopicReplyList(topicId);
			updateClickCount(topicId);
			
		}); */
	  }
	});
}
/* 查看话题详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/TeeTopicController/getInfoById.action";
	var para = {uuid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			//bindJsonObj2Cntrl(prc);
			var sysPara = prc;
			
			$("#subject").text(prc.subject);
			$("#topicSectionManager").text(prc.topicSectionManager);
			$("#topicSectionName").text(prc.topicSectionName);
			
			//……………………………………………………………………………………………………………………………………………………………………
			var editStr = "";
			var deleteStr = "";
			var isTopStr = "";
			var IdsArray=sysPara.topicSectionManagerId.split(",");  
			var isManager = false;
			for(var i=0;i<IdsArray.length;i++){
				if(IdsArray[i]==loginPersonId){
					isManager = true;
					break;
				}
			}
			if(sysPara.crUserId == loginPersonId){
				isManager = true;
			}
			
			if(isManager){
				editStr = "<a href='#' onclick=editTopicFunc() ><IMG src='../image/imgs/icon_edit.png'> 编辑</a>&nbsp;&nbsp;";
				deleteStr = "<a href='javascript:void(0);' onclick=deleteTopicFunc()><IMG src='../image/imgs/icon_delete.png'>删除</a>&nbsp;&nbsp;";

				isTopStr = "<A href='javascript:void(0);' onclick=setTopFunc('<%=topicId %>') ><IMG src='../image/imgs/icon_stick.png'><span id='isTopSpan'></span> </A>&nbsp;&nbsp";
				if(prc.isTop ==1){
					$("#isTopSpan").text("取消置顶");
				}else{
					$("#isTopSpan").text("置顶");
				}
			}


			var avatar = sysPara.avatar;
			if(avatar==null || avatar=="" || avatar=="0"){
				//avatar = systemImagePath+"/default_avatar.gif";
				avatar = "../image/imgs/icon_touxiang.png";
			}else{
				avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
			}


	var postDesc = "<div class='b_box_1'>"
				+ "		<div class='b_list'>"
				+ "			<table width='100%' border='0' cellSpacing=0 cellPadding=0>"
				+ "				<tr>"
				+ "					<td style='text-align:left;' class='b_list_L' vAlign=top>"
				+ "						<p style='display:inline-block;float:left;overflow:hidden;'>"
				+ "							<img style='width:50px;height:50px;display:inline-block;margin:20px 15px 0;border-radius:5px;' src='" + avatar + "' border=0>"
				+ "						</p><span style='height:50px;float:left;display:inline-block;;overflow:hidden;margin:25px 15px  0 0'>"
				+ "							<span style='font-weight:bold;margin-top:5px;'>" + sysPara.crUserName +"</span>"
				+ "							<div style='color:#999;margin-top:10px;'>" + sysPara.crTimeStr +"</div>"
				+ "						</span><p style='display:block;clear:left;padding:10px 0;color:#f98339;'><img style='margin-left:15px;margin-right:5px;vertical-align:bottom;' src='../image/imgs/icon_theme.png'>主题：<span id='subject'>" + sysPara.subject + "</span></p>"
				+ "					</td>"
				+ "				</tr>"
				+ "				<tr>"
				+ "					<td  class='b_list_R'>"
				+ "						<div>"
				+ "							<div class='b_list_R_1'>"
				+ "							</div>"
				+ "							<div class='b_list_R_2'>" + sysPara.content + "</div>"
				+ "							<div class='b_list_R_2' id='fileContainer_" + sysPara.uuid + "'></div>"
				+ "							<div class='b_list_R_3'>"
				+ 									editStr
				+ "								<a href='#' onclick=yinYongFunc(1) ><IMG src='../image/imgs/icon_quote.png'> 引用</a>&nbsp;&nbsp;"
				+ "								<a href='#webEdit'><IMG class='reply_img' src='../image/imgs/icon_reply.png'>回复</a>&nbsp;&nbsp;"
				+ 									isTopStr
				+ 									deleteStr
				+ "							</div>"
				+ "						</div>"
				+ "					</td>"
				+ "				</tr>"
				+ "			</table>"
				+ "			<div class='clear'></div>"
				+ "		</div>"
				+ "</div>";

			$("#topicDiv").append(postDesc);
			if(prc.isTop ==1){
				$("#isTopSpan").text("取消置顶");
			}else{
				$("#isTopSpan").text("置顶");
			}
			var attaches = sysPara.attacheModels;
			if(attaches.length>0){
				$("#fileContainer_" +sysPara.uuid ).append("附件（" + attaches.length + "个）：");
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#fileContainer_" +sysPara.uuid ).append(fileItem);
				}
			}


		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
/* 设置查看数 */
function updateClickCount(id){
	var url =   "<%=contextPath%>/TeeTopicController/updateClickCount.action";
	var para = {uuid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}


/**
 * 删除回复信息
 */
function deleteObjFunc(ids){
	 $.MsgBox.Confirm ("提示", "确实要删除当前回复吗？", function(){
		 var url = contextPath + "/TeeTopicReplyController/deleteObjById.action";
			var para = {sids:ids,topicId:'<%=topicId%>'};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				window.location.reload();
			}  
	  });
}
/**
 * 删除话题及回复信息
 */
function deleteTopicFunc(){
	 $.MsgBox.Confirm ("提示", "确实要删除当前话题吗？", function(){
		 var url = contextPath + "/TeeTopicController/deleteObjById.action";
			var para = {sids:'<%=topicId%>'};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				toReturn();
			}  
	  });
}

/**
 * 设置置顶
 */
function setTopFunc(id){
	var url = "<%=contextPath%>/TeeTopicController/setTopById.action";
	var para = {uuid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		var isTopFlag = prc.isTopFlag;
		if(isTopFlag ==1){
			$("#isTopSpan").text("取消置顶");
			$.MsgBox.Alert_auto("设置置顶贴成功！");
		}else{
			$("#isTopSpan").text("置顶");
			$.MsgBox.Alert_auto("取消置顶贴成功！");
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}





/* 获取话题回复列表 */
function getTopicReplyList(id){
	var url =   "<%=contextPath%>/TeeTopicReplyController/getTopicReplyList.action";
	var para = {topicId : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		if(prcs.length>0){
			jQuery.each(prcs,function(i,sysPara){
				var editStr = "";
				var deleteStr = "";
				if(sysPara.crUserId == loginPersonId){
					editStr = "<a href='#' onclick=toAddOrUpdate('" +sysPara.uuid + "','0') ><IMG src='../image/imgs/icon_edit.png'> 编辑</a>&nbsp;&nbsp;";
					deleteStr = "<a href='javascript:void(0);' onclick=deleteObjFunc('" +sysPara.uuid + "')><IMG src='../image/imgs/icon_delete.png'>删除</a>&nbsp;&nbsp;";
				}


				var avatar = sysPara.avatar;
				if(avatar==null || avatar=="" || avatar=="0"){
					//avatar = systemImagePath+"/default_avatar.gif";
					avatar = "../image/imgs/icon_touxiang.png";
				}else{
					avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
				}


	var postDesc = "<div class='b_box_1'>"
				+ "		<div class='b_list'>"
				+ "			<table width='100%' border='0' cellSpacing=0 cellPadding=0>"
				+ "				<tr>"
				+ "					<td style='text-align:left;' class='b_list_L' vAlign=top>"
				+ "						<p style='display:inline-block;float:left;overflow:hidden;'>"
				+ "							<img style='width:50px;height:50px;display:inline-block;margin:20px 15px 0;border-radius:5px;' src='" + avatar + "' border=0>"
				+ "						</p><span style='height:50px;float:left;display:inline-block;;overflow:hidden;margin:25px 15px  0 0'>"
				+ "							<span style='font-weight:bold;margin-top:5px;'>" + sysPara.crUserName +"</span>"
				+ "							<div style='color:#999;margin-top:10px;'>" + sysPara.crTimeStr +"</div>"
				+ "						</span>"
				+ "					</td>"
				+ "				</tr>"
				+ "				<tr>"
				+ "					<td  class='b_list_R'>"
				+ "						<div>"
				+ "							<div class='b_list_R_1' style='border:none' >"
				+ "							</div>"
				+ "							<div class='b_list_R_2'>" + sysPara.content + "</div>"
				+ "							<div class='b_list_R_2' id='fileContainer_" + sysPara.uuid + "'></div>"
				+ "							<div class='b_list_R_3'>"
				+ 									editStr
				+ "								<a href='#' onclick=yinYongFunc(1) ><IMG src='../image/imgs/icon_quote.png'> 引用</a>&nbsp;&nbsp;"
				+ "								<a href='#webEdit'><IMG class='reply_img' src='../image/imgs/icon_reply.png'>回复</a>&nbsp;&nbsp;"
				+ 									deleteStr
				+ "							</div>"
				+ "							<span style='width:98%;margin:0 auto;display:block;border-bottom:1px dashed #cdcdcd;position:relative;margin-top:25px;'></span>"
				+ "						</div>"
				+ "					</td>"
				+ "				</tr>"
				+ "			</table>"
				+ "			<div class='clear'></div>"
				+ "		</div>"
				+ "</div>";

				$("#contentList").append(postDesc);
				var attaches = sysPara.attacheModels;
				if(attaches.length>0){
					$("#fileContainer_" +sysPara.uuid ).append("附件（" + attaches.length + "个）：");
					for(var i=0;i<attaches.length;i++){
						var fileItem = tools.getAttachElement(attaches[i]);
						$("#fileContainer_" +sysPara.uuid ).append(fileItem);
					}
				}


			});

		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}



/**
 * 获取分页数据
 */
 var counter = 0;
function getTopicReplyPage(uuid){
	$('#pagination-demo1').pagination({
		dataSource: contextPath+'/TeeTopicReplyController/getTopicReplyPage.action?topicId=' + uuid,
		pageSize: 10,
		goPage:function(number,attributes){
			//alert(number + ">>>" + tools.jsonObj2String(attributes));
			counter = (parseInt(number) - 1) * parseInt(attributes.pageSize);
		},
		showNavigator:true,
		callback: function(data,pagination){
			var list = data.rows;
			if(list.length>0){
				var postDesc = "";
				jQuery.each(list,function(i,sysPara){
					counter ++;
					var editStr = "";
					var deleteStr = "";
					if(sysPara.crUserId == loginPersonId){
						editStr = "<a href='#' onclick=toAddOrUpdate('" +sysPara.uuid + "','0') ><IMG src='../image/imgs/icon_edit.png'> 编辑</a>&nbsp;&nbsp;";
						deleteStr = "<a href='javascript:void(0);' onclick=deleteObjFunc('" +sysPara.uuid + "')><IMG src='../image/imgs/icon_delete.png'>删除</a>&nbsp;&nbsp;";
					}

					var avatar = sysPara.avatar;
					if(avatar==null || avatar=="" || avatar=="0"){
						//avatar = systemImagePath+"/default_avatar.gif";
						avatar = "../image/imgs/icon_touxiang.png";
					}else{
						avatar = contextPath+"/attachmentController/downFile.action?id="+avatar+"&model=person";
					}

					postDesc += "<div class='b_box_1'>"
							+ "		<div class='b_list'>"
							+ "			<table width='100%' border='0' cellSpacing=0 cellPadding=0>"
							+ "				<tr>"
							+ "					<td style='text-align:left;' class='b_list_L' vAlign=top>"
							+ "						<p style='display:inline-block;float:left;overflow:hidden;'>"
							+ "							<img style='width:50px;height:50px;display:inline-block;margin:20px 15px 0;border-radius:5px;' src='" + avatar + "' border=0>"
							+ "						</p><span style='height:50px;float:left;display:inline-block;;overflow:hidden;margin:25px 15px  0 0'>"
							+ "							<span style='font-weight:bold;margin-top:5px;'>" + sysPara.crUserName +"</span>"
							+ "							<div style='color:#999;margin-top:10px;'>" + sysPara.crTimeStr +"</div>"
							+ "						</span>"
							+ "					</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td  class='b_list_R'>"
							+ "						<div>"
							+ "							<div class='b_list_R_1' style='border:none' >"
							+ "							</div>"
							+ "							<div class='b_list_R_2'>" + sysPara.content + "</div>"
							+ "							<div class='b_list_R_2' id='fileContainer_" + sysPara.uuid + "'></div>"
							+ "							<div class='b_list_R_3'>"
							+ 									editStr
							+ "								<a href='#' onclick=yinYongFunc(1) ><IMG src='../image/imgs/icon_quote.png'> 引用</a>&nbsp;&nbsp;"
							+ "								<a href='#webEdit'><IMG class='reply_img' src='../image/imgs/icon_reply.png'>回复</a>&nbsp;&nbsp;"
							+ 									deleteStr
							+ "							</div>"
							+ "							<span style='width:98%;margin:0 auto;display:block;border-bottom:1px dashed #cdcdcd;position:relative;margin-top:25px;'></span>"
							+ "						</div>"
							+ "					</td>"
							+ "				</tr>"
							+ "			</table>"
							+ "			<div class='clear'></div>"
							+ "		</div>"
							+ "</div>";
				});
				$("#contentList").html(postDesc); 
				jQuery.each(list,function(i,sysPara){
					var attaches = sysPara.attacheModels;
					if(attaches.length>0){
						$("#fileContainer_" +sysPara.uuid ).append("附件（" + attaches.length + "个）：");
						for(var i=0;i<attaches.length;i++){
							var fileItem = tools.getAttachElement(attaches[i]);
							$("#fileContainer_" +sysPara.uuid ).append(fileItem);
						}
					}
				});
				
			}
		}
	});
}







function checkForm(){
	if(uEditorObj.getContent()==""||uEditorObj.getContent()==null){
		$.MsgBox.Alert_auto("请输入帖子内容！");
	/* 	alert("请输入帖子内容！"); */
		return false; 
	}
	return true;
}

function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath%>/TeeTopicReplyController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		para["content"] = uEditorObj.getContent();
		
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("保存成功!");
			var returnFlag = "<%=returnFlag%>";
			
			if(returnFlag ==""||returnFlag ==1 || returnFlag ==2||returnFlag ==4||returnFlag ==3||returnFlag ==5){
				window.location.reload();
			}else{
				toReturn();
			} 
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

/**
 * 新建编辑回复信息
 */
function toAddOrUpdate(sid,yinYongFlag,topicSectionId){
	var  url = contextPath + "/system/subsys/topic/topicManage/addOrUpdateTopicReply.jsp?topicSectionId=<%=topicSectionId%>&topicId=<%=topicId%>&uuid=" + sid + "&yinYongFlag=" + yinYongFlag;
	window.location.href = url;
}
/**
 * 新建编辑话题信息
 */
function editTopicFunc(){
	var  url = contextPath + "/system/subsys/topic/topicManage/addOrUpdateTopic.jsp?topicSectionId=<%=topicSectionId%>&uuid=<%=topicId%>&returnFlag=<%=returnFlag%>";
	window.location.href = url;
	
}


/**
 * 楼主引用
 */
function yinYongFunc(yinYongFlag){
	var  url = contextPath + "/system/subsys/topic/topicManage/addOrUpdateTopicReply.jsp?topicId=<%=topicId%>&topicSectionId=<%=topicSectionId%>&topicOptFlag=1&yinYongFlag=" + yinYongFlag;;
	window.location.href = url;
}



function toReturn(){
	var returnFlag = "<%=returnFlag%>";
	if(returnFlag ==1){//我的帖子
		location.href = contextPath + "/system/subsys/topic/index.jsp?uuid=<%=topicSectionId%>&&option=我的帖子";
	}else if(returnFlag ==2){//最新帖子
		location.href = contextPath + "/system/subsys/topic/index.jsp?uuid=<%=topicSectionId%>&&option=最新发帖";
	}else if(returnFlag ==3){//周帖子
		location.href = contextPath + "/system/subsys/topic/index.jsp?uuid=<%=topicSectionId%>&&option=周热门贴";
	}else if(returnFlag ==4){//月帖子
		location.href = contextPath + "/system/subsys/topic/index.jsp?uuid=<%=topicSectionId%>&&option=月热门贴";
	}else if(returnFlag ==5){//板块
		location.href = contextPath + "/system/subsys/topic/index.jsp?uuid=<%=topicSectionId%>&&option=板块";
	}else{
		location.href = contextPath + "/system/subsys/topic/topicManage/topicList.jsp?uuid=<%=topicSectionId%>";
	}
	
}
</script>
	<style>
	.b_list_R_3 img{
		vertical-align:bottom;
	}
	.reply_img {
		margin-right:3px;
	}
	</style>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
	<div  id=""  class="topbar clearfix">
	<div class="fl left">
	<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/tlq/icon_bankuaishezhi.png">
	<span class="title">论坛浏览</span>
	</div>
	<div class="fr right">
	<input type="button" class="btn-win-white" onclick="toAddOrUpdate('','0')" value="回帖"/>
	
	<input type="button" class="btn-win-white" onclick="toReturn();"  value="返回">

	</div>
	</div>

	<!-- 人员回复 -->
	<div class="photo" style="margin-top: 5px;" >
	<DIV class=s_sear_1>
	<P>
	<A href="<%=contextPath%>/system/subsys/topic/index.jsp">论坛浏览</A>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;
	<A href="#" onclick="toReturn();"><span id="topicSectionName"></span></A>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;
	帖子阅读
	</P>
	</DIV>
	<DIV class=clear></DIV>
	<div id="topicDiv"></div>
	<div id="contentList"></div>
	<DIV class="tb_tools no_mar">
	<DIV class=bbs_bot_L>
	<SPAN class=yellow_1>本版版主：</SPAN><SPAN class=blue id="topicSectionManager"></SPAN>
	</DIV>
	<DIV class="paging">
	</DIV>
	<div id="pagination-demo1" class="app-pagination" style="float: right;" ></div>
	</DIV>
	<DIV class=clear></DIV>
	</div>

<!-- 快速回复 -->
<div class="topbar clearfix" style="margin: 10px">
  <div class="fl left">
     <span style="font-size: 12px;font-weight: bold;">快速回复</span>
  </div>
  <div class="fr right">
     <input type="button" class="btn-win-white" onclick="doSaveOrUpdate();" value="保存回复"  />
  </div>
</div>

 <form action="" method="post" name="form1" id="form1">
   <input type="hidden" name="topicId" id="topicId" value="<%=topicId%>">
   <input type="hidden" name="topicSectionId" id="topicSectionId" value="<%=topicSectionId%>">
   <table align="center" width="98%" class="TableBlock" style='margin:0 auto;'>
	 <tr>
	    <td class="TableData" colspan="2">
	    <A name=webEdit></A>
	    <textarea rows="5" cols="83"id="content" name="content" class="BigTextarea"></textarea>
	    </td>
	 </tr>
	 <tr>
	    <td>
	    <div id="anonymous1"><input type="checkbox" name="anonymous" id="anonymous" value="1"/>&nbsp;&nbsp;匿名发布</div>
	    </td>
	 </tr>
  </table>
</form>


<%-- <form action="" method="post" name="form1" id="form1">
   <input type="hidden" name="topicId" id="topicId" value="<%=topicId%>">
   <input type="hidden" name="topicSectionId" id="topicSectionId" value="<%=topicSectionId%>">
   <table align="center" width="98%" class="TableBlock" style='margin:0 auto;'>
	 <tr class="TableHeader">
	    <td nowrap class=""  colspan="3" >快速回复</td>
	 </tr>
	 <tr>
	    <td class="TableData" colspan="2">
	    <A name=webEdit></A>
	    <textarea rows="5" cols="83"id="content" name="content" class="BigTextarea"></textarea>
	    </td>
	 </tr>
	 <tr>
	    <td>
	    <div id="anonymous1"><input type="checkbox" name="anonymous" id="anonymous" value="1"/>匿名发布</div>
	    </td>
	 </tr>
	 <tr>
	    <td><input type="button" class="btn-win-white" onclick="doSaveOrUpdate();" value="保存"  /></td>
	 </tr>
  </table>
</form> --%>
<div class="mui-content" id="topicList">

</body>
</html>