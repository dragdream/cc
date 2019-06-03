 <%@page import="com.tianee.oa.core.base.hr.TeeHrCodeManager"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String model = TeeAttachmentModelKeys.hrPool;
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@include file="/header/upload.jsp"%>

<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<title>新建/编辑人才库</title>
<script type="text/javascript">
var sid = "<%=sid%>";
var ckedit ;
var uEditorObj;//uEditor编辑器
function doInit(){
	
	uEditorObj = UE.getEditor('resume');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(230);
	/* //初始化fck
	ckedit = CKEDITOR.replace('resume',{
		width : 'auto',
		height:200
	}); */
	//初始化附件
	new TeeSWFUpload({
		fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"attacheIds",//附件主键返回值容器，是个input
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	
	
	getHrCode();
	if(sid > 0){
		getInfoById(sid);
	}
  });
}
/**
 * 获取所有代码
 */
function getHrCode(){
	//整治面貌
	var prcs = getHrCodeByParentCodeNo("STAFF_POLITICAL_STATUS" , "employeePoliticalStatus");
	var prcs = getHrCodeByParentCodeNo("JOB_CATEGORY" , "jobCategory");//期望工作性质
	var prcs = getHrCodeByParentCodeNo("POOL_POSITION" , "position");//岗位
	var prcs = getHrCodeByParentCodeNo("POOL_EMPLOYEE_MAJOR" , "employeeMajor");//专业
	var prcs = getHrCodeByParentCodeNo("EMPLOYEE_HIGHEST_DEGREE" , "employeeHighestDegree");//学位
	var prcs = getHrCodeByParentCodeNo("STAFF_HIGHEST_SCHOOL" , "employeeHighestSchool");//学历
	
	
	var prcs = getHrCodeByParentCodeNo("AREA" , "employeeNativePlace");///籍贯
	var prcs = getHrCodeByParentCodeNo("PLAN_DITCH" , "recruChannel");///招聘渠道
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/hrPoolController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			//ckedit.setData(prc.resume);
			uEditorObj.setContent(prc.resume);
			var attaches = prc.attachesModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#fileContainer").append(fileItem);
			}
			var att = prc.attachemntModel;
			if(att && att.sid){
				 //获取附件
				 var url = "<%=request.getContextPath() %>/attachmentController/downFile.action?id=" + att.sid + "&model=<%=model%>";
				
				 $("#attacheImg").append("<img width='130' height='170' src='"+ url + "'/>");
			}else{
				 $("#attacheImg").append("暂无照片");
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
function checkForm(){
	var check = $("#form1").valid(); 
	if(!check){
		return false; 
	}

	if($("#planId").val() == ''){
		$.MsgBox.Alert_auto("招聘计划不能为空！");
		return false;
	}
	if(!uEditorObj.getContent()){
		$.MsgBox.Alert_auto("请输入简历内容！");
		return false;
	}
	
	if($("#uploadBtn").val()){
		return checkSuppotPictureFile('uploadBtn');
	}
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/hrPoolController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    para["resume"] = uEditorObj.getContent();
	   // var jsonRs = tools.requestJsonRs(url,para);
	    $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 		//callback(res);
			 		if(res.rtState){
			 			$.MsgBox.Alert_auto("保存成功！" ,function(){
			 				history.go(-1);
			 			});
			 			
			 		}else{
			 			$.MsgBox.Alert_auto(res.rtMsg);
			 		}
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	        	  $.MsgBox.Alert_auto("保存错误！");
	          },
	          dataType: 'json'});
	}
}


function toReturn(){
	//window.location.href = "manager.jsp";
	history.go(-1);
}

</script>
</head>
<body onload="doInit()" style="overflow-x: hidden;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="75%" class="TableBlock_page" >
	 <tr>
	    <td nowrap class="TableHeader" colspan="6" >
	        <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">基本信息</b>
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">计划名称：<font color="red">*</font></td>
	    <td class="TableData" width="180">
	      <INPUT type="text"name="planName" id="planName" class="BigInput BigStatic" required="true" size="15" readonly="readonly">
	      <INPUT type="hidden" name="planId" id="planId" value="">
	      <a href="javascript:void(0);" class="orgAdd" onClick="getRecruitPlan()">选择</a>
	    </td>
	    <td nowrap class="TableData" width="100">应聘人姓名：<font color="red">*</font></td>
	    <td class="TableData" width="180" colspan="2">
	    	<input type="text" name="employeeName" class="BigInput" id="employeeName" value="" maxlength="30" required="true" >
	    </td>
	    <td class="TableData" rowspan="7" colspan="1" align="center">
	     <center  id="attacheImg">
	     
	     </center>   </td>
	  </tr>
	  <tr>
	   <td nowrap class="TableData" style="text-indent: 15px">性别：</td>
	   <td class="TableData">
	    <select name="employeeSex" class="BigSelect">
	         <option value="0">男</option>
	         <option value="1">女</option>
	     </select>
	   </td>
	   <td nowrap class="TableData" width="100">出生日期：</td>
	    <td class="TableData" width="180" colspan="2">
	       <input type="text" name="employeeBirthStr" size="10" maxlength="10" class="BigInput Wdate" value=""  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">民族：</td>
	    <td class="TableData"  width="180">
	      <input type="text" name="employeeNationality" id="employeeNationality" maxlength="50" class="BigInput"  value="">
	    </td>
	    <td nowrap class="TableData" width="100">现居住城市：</td>
	    <td class="TableData" width="180" colspan="2">
	    	<input type="text" name="residencePlace" id="residencePlace"  maxlength="50" class="BigInput " value="">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">联系电话：</td>
	    <td class="TableData"  width="180">
	    	<input type="text" name="employeePhone" id="employeePhone"  maxlength="50" class="BigInput" >
	    </td>
	    <td nowrap class="TableData" >E-MAIL：</td>
	     <td class="TableData"  width="180" colspan="2">
	     	<input type="text" name="employeeEmail" id="employeeEmail" class="BigInput" maxlength="50">
	     </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" style="text-indent: 15px">籍贯：</td>
	    <td class="TableData" nowrap="nowrap">
	    	<select name="employeeNativePlace" id="employeeNativePlace" class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
	      </select>
	      <input type="text" name="employeeNativePlace2" size=10 class="BigInput">
	    </td>
	     <td nowrap class="TableData" width="100">户口所在地：</td>
	    <td class="TableData"  width="180" colspan="2">
	    	<input type="text" name="employeeDomicilePlace" id="employeeDomicilePlace" size="35" class="BigInput">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" style="text-indent: 15px">婚姻状况：</td>
	    <td class="TableData">
	      <select name="employeeMaritalStatus" class="BigSelect">
	        <option value="">请选择</option>
	        <option value="0">未婚&nbsp;&nbsp;</option>
	        <option value="1">已婚</option>
	        <option value="2">离异</option>
	        <option value="3">丧偶</option>
	      </select>
	    </td>
	    <td nowrap class="TableData">政治面貌：</td>
	    <td class="TableData" colspan="2">
	        <select name="employeePoliticalStatus" id="employeePoliticalStatus" class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
	          <option value="">请选择</option>
	        </select>
	    </td>
	  </tr>
	  <tr>
	  	<td nowrap class="TableData" width="100" style="text-indent: 15px">健康状况：</td>
	    <td class="TableData"  width="180">
	    	<input type="text" name="employeeHealth" id="employeeHealth" class="BigInput" value=>
	    </td>
	     <td nowrap class="TableData" width="100">参加工作时间：</td>
	    <td class="TableData"  width="180" colspan="2">
	      <input type="text" name="jobBeginningStr" size="13" maxlength="10" class="BigInput" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
	    </td>
	  </tr>
	 <tr>
	  <td nowrap class="TableData" style="text-indent: 15px">期望工作性质：</td>
	    <td class="TableData">
	    	<select name="jobCategory" id="jobCategory" class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
			<option value="">请选择 </option>
	       </select>
	    </td>
	    <td nowrap class="TableData" width="100">照片上传：</td>
	    <td class="TableData" colspan="3">
	    
	    <input type="file"  name="attachement" id="uploadBtn" size="50"  class="BigInput" title="选择附件文件" >
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" style="text-indent: 15px">期望从事行业：</td>
	    <td class="TableData">
	    	<input type="text" name="jobIndustry" id="jobIndustry" class="BigInput">
	    </td>
	    <td nowrap class="TableData">期望从事职业：</td>
	    <td class="TableData">
	    	<input type="text" name="jobIntension" id="jobIntension" class="BigInput">
	    </td>
	    <td nowrap class="TableData">期望工作城市：</td>
	    <td class="TableData">
	    	<input type="text" name="workCity" id="workCity" class="BigInput">
	    </td>
	  </tr>
	    <tr>
	    <td nowrap class="TableData" width="110" style="text-indent: 15px">期望薪水(税前)：</td>
	    <td class="TableData"  width="180">
	    	<input type="text" name="expectedSalary" id="expectedSalary"  class="BigInput " validType='pointTwoNumber[]' size="10" maxlength="10"">&nbsp;元   
	    </td>
	    <td nowrap class="TableData" width="100">岗位：</td>
	    <td class="TableData">
	     <select name="position" id="position"  class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
				<option value="">请选择</option>

	     </select>
	    </td>
	    <td nowrap class="TableData" width="100">到岗时间：</td>
	    <td class="TableData"  width="180">
	      <select name="startWorking" class="BigSelect">
	        <option value="" >请选择</option>
	        <option value="0" selected>1周以内</option>
	        <option value="1" >1个月内</option>
	        <option value="2" >1~3个月</option>
	        <option value="3" >3个月后</option>
	        <option value="4" >随时到岗</option>
	      </select>
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">毕业时间：</td>
	    <td class="TableData"  width="180">
	      <input type="text" name="graduationDateStr" size="10" maxlength="10" class="BigInput" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
	    </td>
	    <td nowrap class="TableData" width="100">毕业学校：</td>
	    <td class="TableData"  width="180" colspan="3">
	    	<input type="text" name="graduationSchool" size="60" id="graduationSchool" class="BigInput">
	    </td>
	  </tr>
	    <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">所学专业：</td>
	    <td class="TableData"  width="180">
	    	<select name="employeeMajor" id="employeeMajor" class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
				<option value="">请选择</option>
	     </select>
	    </td>
	    <td nowrap class="TableData" width="100">学历：</td>
	    <td class="TableData"  width="180">
	        <select name="employeeHighestSchool" id="employeeHighestSchool"  class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
	        	<option value="">请选择</option>
	        </select>
	    </td>
	    <td nowrap class="TableData" width="100">学位：</td>
	    <td class="TableData"  width="180">
	    	<select name="employeeHighestDegree" id="employeeHighestDegree"  class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
	    		<option value="">请选择</option>
	      </select>
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">外语语种1：</td>
	    <td class="TableData"  width="180"><input type="text" name="foreignLanguage1" id="FOREIGN_LANGUAGE1" class="BigInput"></td>
	    <td nowrap class="TableData" width="100">外语语种2：</td>
	    <td class="TableData"  width="180"><input type="text" name="foreignLanguage2" id="FOREIGN_LANGUAGE2" class="BigInput"></td>
	    <td nowrap class="TableData" width="100">外语语种3：</td>
	    <td class="TableData"  width="180"><input type="text" name="foreignLanguage3" id="FOREIGN_LANGUAGE3" class="BigInput"></td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">外语水平1：</td>
	    <td class="TableData"  width="180"><input type="text" name="foreignLevel1" id="FOREIGN_LEVEL1" class="BigInput"></td>
	    <td nowrap class="TableData" width="100">外语水平2：</td>
	    <td class="TableData"  width="180"><input type="text" name="foreignLevel2" id="FOREIGN_LEVEL2" class="BigInput"></td>
	    <td nowrap class="TableData" width="100">外语水平3：</td>
	    <td class="TableData"  width="180"><input type="text" name="foreignLevel3" id="FOREIGN_LEVEL3" class="BigInput"></td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">计算机水平：</td>
	    <td class="TableData"  width="180">
	    	<input type="text" name="computerLevel" id="computerLevel" class="BigInput">
	    </td>
	    
	    <td nowrap class="TableData">年龄：</td>
	    <td class="TableData">
	    	<input type="text" name="employeeAge" id="employeeAge" size="4" maxlength="3" class="BigInput easyui-validatebox"     validType ='positivIntege[]'  >岁
	    </td>
	    
	    
	    <td nowrap class="TableData">招聘渠道：</td>
	    <td class="TableData" width="180">
	    	<select name="recruChannel" id="recruChannel" style="background: white;" class="BigSelect" title="<%=TeeHrCodeManager.hrCodeSettingPath%>"> 
					<option value="">请选择</option>
			</select> 
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">特长：</td>
	    <td class="TableData"  width="180" colspan="5">
	    	<textarea name="employeeSkills" cols="100" rows="3" class="BigTextarea" value=""></textarea>
	   </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">职业技能：</td>
	    <td class="TableData"  width="180" colspan="5">
	    	<textarea name="chreerSkills" cols="100" rows="3" class="BigTextarea" value=""></textarea>
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">工作经验：</td>
	    <td class="TableData"  width="180" colspan="5">
	    	<textarea name="wordExperience" cols="100" rows="3" class="BigTextarea" value=""></textarea>
	   </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">项目经验：</td>
	    <td class="TableData"  width="180" colspan="5">
	    	<textarea name="projectExperience" cols="100" rows="3" class="BigTextarea" value=""></textarea>
	   </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100" style="text-indent: 15px">备注：</td>
	    <td class="TableData"  width="180" colspan="5"><textarea name="remark" cols="100" rows="3" class="BigTextarea" value=""></textarea>
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableHeader" colspan="6">
	        <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">附件简历</b>
	    </td>
	  </tr>
	 
	
	<tr>
		<td nowrap class="TableData" width="" style="text-indent: 15px">附件上传：</td>
		<td class="TableData"  colspan="6">
				<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" />
		</td>
	</tr>
	<tr>
		<td class="TableData" width="" style="text-indent: 15px">
			简历内容：<font style='color:red'>*</font>
	    </td>
	    <td colspan="5">
			<DIV>
				<textarea  id="resume" name="resume" class="BigTextarea" required="true" ></textarea>
			</DIV>
		</td>
	</tr>
	
		<tr>
		<td width="" colspan="6" align="right">
		   <div align="right">
		       <input type="button" class="btn-win-white" value="保存" onclick="doSaveOrUpdate();">
			   &nbsp;&nbsp;
			   <input type="button" class="btn-win-white" value="返回" onclick="toReturn();">
		   </div>	
		</td>
	</tr>
	</table>
</form>
<br>

</body>
</html>
 