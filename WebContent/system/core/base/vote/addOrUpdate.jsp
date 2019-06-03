<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//投票Id	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>新增/编辑投票</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">

var sid = <%=sid%>;



function doInit()
{
	if(sid > 0){
		getById(sid);
	}
	
	//多附件简单上传组件，随表单提交
	new TeeSimpleUpload({
		fileContainer:"attachments",//文件列表容器
		uploadHolder:"uploadHolder1",//上传按钮放置容器
		valuesHolder:"meetAttachmentIds",//附件主键返回值容器，是个input
		form:"form1"//随form表单提交
		});
	
	
}


/**
 * 新建或者更新
 */
function doSaveOrUpdate(callback){
	if(checkFrom()){	
		var url = contextPath + "/voteManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		para['model'] = 'vote';
		$("#form1").doUpload({
			url:url,
			success:function(){
				callback();
				//parent.BSWINDOW.modal("hide");
			},
			post_params:para
		});
		return true;
	}
	return false;
}
/**
 * 校验
 */
function checkFrom(){
   return $("#form1").valid();
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/voteManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form action=""  method="post" name="form1" id="form1" enctype="multipart/form-data">
<table class="TableBlock" width="100%" align="center">
	 <tr>
	    <td nowrap class="TableData" width="130px" style="text-indent:15px"> 主题：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="subject" id="subject" style="height:20px;width: 350px" class="" value="" required>
	    </td>
	 </tr>
	 <tr>
	    <td nowrap class="TableData"  width="130px" style="text-indent:15px">查看投票结果：<font style='color:red'>*</font></td>
	    <td class="TableData"  >
	    	<select id="viewPriv" name="viewPriv" class="BigSelect">
          		<option value="0">投票后允许查看</option>
         		<option value="1">投票前允许查看</option>
          		<option value="2">不允许查看</option>
       		 </select>
	     </td>
	  </tr>  
	  <tr>
	     <td nowrap class="TableData" width="130px" style="text-indent:15px">是否匿名投票：</td>
	     <td class="TableData"   >
	    	 <input id="anonymity" name="anonymity" type="checkbox" value="1"><label for="anonymity">  允许匿名投票</label>
	     </td>
	  </tr>
	  <tr id="time_status0">
		  <td nowrap class="TableData" width="130px" style="text-indent:15px"> 有效时间：</td>
		  <td class="TableData" >
		                       开始
		    	<input type="text" name="beginDateStr" id="beginDateStr" size="10" style="width: 130px;height: 20px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDateStr\')}'})" value="">
		     	终止
		  	   <input type="text" name="endDateStr" id="endDateStr" size="10" style="width: 130px;height: 20px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDateStr\')}'})" value="">
		 	      （日期24小时内有效）
		  </td>
  	 </tr> 
	 <tr>
		  <td nowrap class="TableData" width="130px" style="text-indent:15px">发布范围（部门）：</td>
		  <td class="TableData" >
		   	<input type="hidden" name="postDeptIds" id="postDeptIds" value="">
		  	<textarea cols=48 name="postDeptNames" id="postDeptNames" rows=5  wrap="yes" readonly></textarea>
		  	<span class="addSpan">
		  	   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectDept(['postDeptIds', 'postDeptNames'],'11');" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#postDeptIds').val('');$('#postDeptNames').val('');" value="清空"/>
	        </span>
	     </td>
	</tr>
    <tr>
      <td nowrap class="TableData" width="130px" style="text-indent:15px">发布范围（角色）：</td>
      <td class="TableData" >
        <input type="hidden" name="postUserRoleIds" id="postUserRoleIds" value="">
	      <textarea cols=48 name="postUserRoleNames" id="postUserRoleNames" rows=5  wrap="yes" readonly></textarea>
	      <span class="addSpan">
		  	   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectRole(['postUserRoleIds', 'postUserRoleNames'],'11');" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#postUserRoleIds').val('');$('#postUserRoleNames').val('');" value="清空"/>
	      </span>
      </td>
   </tr>
   <tr>
      <td nowrap class="TableData" width="130px" style="text-indent:15px">发布范围（人员）：</td>
      <td class="TableData" >
        <input type="hidden" name="postUserIds" id="postUserIds" value="">
	      <textarea cols=48 name="postUserNames" id="postUserNames" rows=5  wrap="yes" readonly></textarea>
	     <span class="addSpan">
		  	   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['postUserIds', 'postUserNames'],'11');" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#postUserIds').val('');$('#postUserNames').val('');" value="清空"/>
	     </span>
      </td>
   </tr> 
   <tr>
      <td nowrap class="TableData" width="130px" style="text-indent:15px">附件：</td>
      <td  class="TableData" >
   		 <div id="attachments" style="margin-bottom:10px;"></div>
		 <input id="meetAttachmentIds" type="hidden"/>
		 <a id="uploadHolder1" class="add_swfupload"  >附件上传</a>
	  </td>
   </tr>
   <tr>
       <td nowrap class="TableData" width="130px" style="text-indent:15px"><span id="ATTACH_LABEL">投票描述：</span></td>
       <td class="TableData" width="100">
           <textarea name="content" id="content" class="BigTextarea"  cols="48" rows="5"></textarea>
		   <input type="hidden" name="sid" id="sid" value="<%=sid %>">
 	   </td>
   </tr>
 </table>
</form>	
</body>
</html>
