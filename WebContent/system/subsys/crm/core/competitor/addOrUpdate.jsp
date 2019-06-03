<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM_COMPETITOR;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var sid = "<%=sid%>";
var model = '<%=model%>';
function doInit(){
	
	
	getCrmCodeByParentCodeNo("COMPANY_SCALE","companyScale");//公司规模
	getCRMProvince('province');
	if( sid > 0){
		var url = "<%=contextPath%>/teeCrmCompetitorController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			$("#province").val(prc.province);
			getCRMCity('province','city');
			bindJsonObj2Cntrl(prc);

			$("#companyCreateDate").val(prc.companyCreateDate.substring(0,10));
		
			var  attachmodels = prc.attachmodels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			} 
			
		}else{
			alert(json.rtMsg);
		}
	}
	doInitUpload();
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
		post_params:{model:model}//后台传入值，model为模块标志
	});
}
/**
 * 保存
 */
function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = "<%=contextPath%>/teeCrmCompetitorController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"info" ,{timeout:1500});
			location.href= "manager.jsp";
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
		}
	}
}
</script>
</head>
<body onload="doInit();" style="">
<div class="Big3" style="padding:3px 10px">
	<%if(sid > 0){ %>  
		编辑竞争对手
	<%}else{ %>
		新建竞争对手
	<%}%>
</div>
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="90%" align="center">
	    <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">基本信息</span></td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" width="120">公司名称：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		         <input type="text" name="company" id="company" class="easyui-validatebox BigInput" required="true" />&nbsp;
		  	</td>
		    <td nowrap class="TableData" width="120">注册资本：<span style="color:red;"></span></td>
		    <td nowrap class="TableData">
		        <input type="text" name="registerCapital" id="registerCapital" class="easyui-validatebox BigInput"   />&nbsp;
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" >公司地址：</td>
		    <td nowrap class="TableData">
		       	<input type="text" name="companyAddress" id="companyAddress"  maxlength="100" value="" class="BigInput"  />&nbsp;&nbsp;
		    </td>
		    <td nowrap class="TableData" >公司邮箱：</td>
		    <td nowrap class="TableData">
		        
		        <input type="text" name="email" id="email"  maxlength="50" value="" class="BigInput" />&nbsp;&nbsp;
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 公司网址：</td>
		    <td nowrap class="TableData">
		       	<input type="text" name="website" id="website" maxlength="100" class="BigInput easyui-validatebox" />
		    </td>
		    <td nowrap class="TableData" >联系电话：</td>
		    <td nowrap class="TableData">
		    	<input type="text" name="telephone" id="" maxlength="50" value="" class="BigInput easyui-validatebox" maxlength="100"/>&nbsp;&nbsp;
		    </td>
	   </tr> 
	   <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">详细信息</span></td>
	    </tr>
	     <tr>
		   <td nowrap class="TableData" >公司性质：</td>
		   <td nowrap class="TableData">
		      <input type="text" name="companyNature" id="companyNature"  maxlength="100" value="" class="BigInput" />&nbsp;&nbsp;
		   </td>
		   <td nowrap class="TableData" >公司规模：</td>
		   <td nowrap class="TableData">
		  		<select  name="companyScale" id="companyScale" class="BigSelect">
		
		       	</select>
		   </td>
	    </tr>
	     <tr>
		  
		   <td nowrap class="TableData" >销售额（万元）：</td>
		   <td nowrap class="TableData">
		      <input type="text" name="companySales" id="companySales"  validType = "pointTwoNumber[]" maxlength="10"  required="true"   value="0" class="BigInput easyui-validatebox"   />&nbsp;&nbsp;
		   </td>
		     <td nowrap class="TableData" >主要产品：</td>
		   	<td nowrap class="TableData">
		      <input type="text" name="mainProduct" id="mainProduct"  maxlength="100" value="" class="BigInput"   />&nbsp;&nbsp;
		       
		    </td>
	    </tr>
	    <tr>
		 
		    <td nowrap class="TableData" >成立时间：</td>
		    <td nowrap class="TableData">
		  		<input type="text" name="companyCreateDate" id="companyCreateDate" maxlength="10" value="" class="BigInput Wdate" onClick="WdatePicker()"  />&nbsp;&nbsp;
		    </td>
		    <td nowrap class="TableData" >所属区域：</td>
		    <td nowrap class="TableData">
		  		省份：
				<select name="province" id="province" onchange="getCRMCity('province' , 'city');" class='BigSelect'>
					<option>请选择</option>
				</select>
				城市：
				<select name="city" id="city"  class='BigSelect'>
					<option>请选择</option>
				</select>
		    </td>
	    </tr>
	    <tr>
			
		    <td nowrap class="TableData" >备注</td>
		    <td nowrap class="TableData" colspan="3">  
		    	<textarea rows="5" cols="80" name="remark" class="BigTextarea"></textarea>
		    </td>
	    </tr>
	     <tr>
			<td nowrap class="TableData" > 附件：</td>
		    <td nowrap class="TableData" colspan="3">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		    </td>
	     </tr>
	     <tr>
		    <td nowrap  class="TableControl" colspan="4" align="center">
		        <input type="hidden" id="sid" name="sid"  value="0">
		        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="commit()" >&nbsp;&nbsp;
		        <input type="button" value="返回" class="btn btn-primary" title="返回" onClick="window.location.href='manager.jsp'">
		    </td>
		</tr>
	</table>
</form>
<br>
</body>
</html>