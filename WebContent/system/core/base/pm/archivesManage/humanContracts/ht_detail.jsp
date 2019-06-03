<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

	String sid = request.getParameter("sid");
	String model = TeeAttachmentModelKeys.hrContract;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>
<script>
var sid='<%=sid%>';
function doInit(){
	doInitUpload();
	var url = contextPath+"/TeeHumanContractController/getModelById.action?sid="+sid+"&type=1";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		if(json.rtData.attachMentModel.length>0){
			$("#attachTr").show();
			var attaches = json.rtData.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
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
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_detail.png">
		<span class="title"> 合同信息</span>
	</div>
</div>
	<table class="TableBlock" width="100%" align="center">
	    <tr>
		   <td class=TableHeader colSpan=2 noWrap style="padding-top: 10px;">
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">基本信息</span>
		       <span style="padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同标题：
			</td>
			<td>
				<div id="conTitle" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同编号：
			</td>
			<td>
				<div  id="conCode"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同类型：
			</td>
			<td>
				<div id="conTypeDesc"  >
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同属性：
			</td>
			<td>
				<div id="conAttrDesc">
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同状态：
			</td>
			<td>
				<div id="conStatusDesc">
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同生效时间：
			</td>
			<td>
				<div id='validTimeDesc'></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同结束时间：
			</td>
			<td>
				<div  id='endTimeDesc' ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同解除时间：
			</td>
			<td>
				<div id='invalidTimeDesc' ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				签约次数：
			</td>
			<td>
				<div id="signCount" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				续签日期：
			</td>
			<td>
				<span id='renewDateDesc' name='renewDateDesc' ></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				最后提醒时间：
			</td>
			<td>
				<span id='lastRemindDateDesc'  name='lastRemindDateDesc' ></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				相关附件：
			</td>
			<td>
				<div style="min-height:50px;">
		      			<span id="attachments"></span>
			      		<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				备注：
			</td>
			<td>
				<div id="remark" style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
</body>
</html>