<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String siteId = request.getParameter("siteId");
	String channelId = request.getParameter("channelId");
	String sid = request.getParameter("sid");
	String model = TeeAttachmentModelKeys.cmsChannel;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var siteId = <%=siteId%>;
var channelId = <%=channelId%>;
var sid = <%=sid%>;
function doInit(){
	doInitUpload();
	//获取当前站点些模板列表
	var render = [];
	var json = tools.requestJsonRs(contextPath+"/cmsSiteTemplate/listTemplates.action",{siteId:siteId});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		render.push("<option value=\""+list[i].sid+"\">"+list[i].tplName+"</option>");
	}
	
	$("#indexTpl").html(render.join(""));
	$("#detailTpl").html(render.join(""));
	
	//加载栏目扩展字段
	var json = tools.requestJsonRs(contextPath+"/cmsChannelExt/list.action");
	for(var i=0;i<json.rows.length;i++){
		$("#extTr").after("<tr><td style=\"text-indent:10px\">"+json.rows[i].fieldTitle+"：</td><td colspan='3'><input type=\"text\" class=\"BigInput\" name=\"#EXT_"+json.rows[i].fieldName+"\" id=\""+json.rows[i].fieldName+"\" style=\"height: 23px;width: 90%\"/></td></tr>");
	}
	
	if(sid!=null){
		var json = tools.requestJsonRs(contextPath+"/cmsChannel/getChannelInfo.action",{sid:sid});
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			bindJsonObj2Cntrl(json.rtData.ext);
			$("#editContentSpan").show();
			if(json.rtData.attachMentModel.length>0){
				$("#attachTr").show();
				var attaches = json.rtData.attachMentModel;
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#attachments").append(fileItem);
				}
			}
		}
	}
}

function commit(){
	var url;
	if(sid!=null){//更新
		url = contextPath+"/cmsChannel/updateChannelInfo.action";
	}else{//创建
		url = contextPath+"/cmsChannel/addChannelInfo.action";
	}
	var param = tools.formToJson($("#form"));
	var json = tools.requestJsonRs(url,param);
	return json;
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"channelImg",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		file_types:"*.jpg;*.png;*.gif;",
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}

function editContent(){
	openFullWindow("/system/subsys/cms/chnldocmgr.jsp?channelId="+sid);
}

</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit();" style="margin:0px;">
<form id="form">
	<table style="width:100%;font-size:12px;" class="TableBlock">
		<tr>
			<td class="TableHeader" colspan="4" nowrap>
		      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">基础信息</b>
		    </td>
		</tr>
		<tr>
			<td style="text-indent:10px">栏目标识：</td>
			<td>
				<input type="text" class="BigInput" name="chnlIdentity" id="chnlIdentity" style="height: 23px;width: 170px"/>
			</td>
			<td>显示名称：</td>
			<td>
			<input type="text" class="BigInput" name="chnlName" id="chnlName" style="height: 23px;width: 170px"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">排序号：</td>
			<td>
			<input type="text" class="BigInput" name="sortNo" id="sortNo" style="height: 23px;width: 170px"/>
			</td>
			<td>存放位置：</td>
			<td>
			<input type="text" class="BigInput" name="folder" id="folder" style="height: 23px;width: 170px"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">栏目分页大小：</td>
			<td>
			<input type="text" class="BigInput" name="pageSize" id="pageSize" style="height: 23px;width: 170px"/>
			</td>
			<td>栏目文档</td>
			<td>
				<a id="editContentSpan" style="display:none" href="#" onclick="editContent()">点击进行编辑</a>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">栏目图片：</td>
			<td colspan="3">
					<div style="min-height:30px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="channelImg" name="channelImg" type="hidden"/>
		      		</div>
			</td>
		</tr>
		<tr id="extTr">
			<td class="TableHeader" colspan="4" nowrap="">
		      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">扩展字段</b>
		    </td>
		</tr>
		<tr>
			<td class="TableHeader" colspan="4" nowrap="">
		      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">发布相关</b>
		    </td>
		</tr>
		<tr>
			<td style="text-indent:10px">首页模板：</td>
			<td>
			<select class="BigSelect" id="indexTpl" name="indexTpl" style="height: 23px;"></select>
			</td>
			<td>细览模板：</td>
			<td>
			<select class="BigSelect" id="detailTpl" name="detailTpl" style="height: 23px;"></select>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">状态：</td>
			<td>
			<select class="BigSelect" id="status" name="status" style="height: 23px;">
				<option value="1">允许发布</option>
				<option value="2">禁止发布</option>
			</select>
			</td>
			<td>审核发布：</td>
			<td>
			    <input type="radio" name="checkPub" value="1" />是
			    &nbsp;&nbsp;
			    <input type="radio" name="checkPub" value="0" checked="checked"/>否
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">审核人：</td>
			<td colspan="3">
			    <input name="checkUserId" id="checkUserId" type="hidden"/>
				<input id="checkUserName" type="text" name="checkUserName"  class="BigInput" readonly style="height: 23px;width: 170px"/>
				<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['checkUserId','checkUserName'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('checkUserId','checkUserName')" value="清空"/>
				</span>
			</td>
			
		</tr>
		<tr>
			<td style="text-indent:10px">发布权限：</td>
			<td colspan="3">
			    <input name="privUserIds" id="privUserIds" type="hidden"/>
				<textarea id="privUserNames" name="privUserNames" class="BigTextarea readonly" style="height:70px;width:350px" ></textarea>
				
				<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['privUserIds','privUserNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('privUserIds','privUserNames')" value="清空"/>
				</span>
			</td>
		</tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid==null?0:sid%>"/>
	<input type="hidden" id="parentChnl" name="parentChnl" value="<%=channelId==null?0:channelId%>"/>
	<input type="hidden" id="siteId" name="siteId" value="<%=siteId==null?0:siteId%>"/>
</form>
</body>
</html>