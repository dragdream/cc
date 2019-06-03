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
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<title>新建/编辑人才库</title>
<script type="text/javascript">
var sid = "<%=sid%>";
var ckedit ;
function doInit(){
	
	//初始化fck
/* 	ckedit = CKEDITOR.replace('resume',{
		width : 'auto',
		height:200
	}); */
	//初始化附件
<%-- 	new TeeSWFUpload({
		fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"attacheIds",//附件主键返回值容器，是个input
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	}); --%>
	
	
	<%-- //单附件简单上传组件
	new TeeSingleUpload({
		uploadBtn:"uploadBtn",
		callback:function(json){//回调函数，json.rtData
			
		},
		ext:["bmp","gif","jpg","png","jpeg"],
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	}); --%>
	
	getHrCode();
	if(sid > 0){
		getInfoById(sid);
	}
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
			$("#resume").html(prc.resume)
			var attaches = prc.attachesModels;
			for(var i=0;i<attaches.length;i++){
				var attach = attaches[i];
				attach["priv"] = 3;
				var fileItem = tools.getAttachElement(attach);
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
		alert(jsonObj.rtMsg);
	}
}
function checkForm(){
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}

	if($("#planId").val() == ''){
		alert("培训计划不能为空！");
		return false;
	}
	if(!ckedit.getData()){
		alert("请输入培训内容！");
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
	    para["resume"] = ckedit.getData();
	   // var jsonRs = tools.requestJsonRs(url,para);
	    $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 		//callback(res);
			 		if(res.rtState){
			 			top.$.jBox.tip("保存成功！" , 'info', {timeout:1500});
			 			history.go(-1);
			 		}else{
			 			alert(res.rtMsg);
			 		}
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	                alert("保存错误");
	          },
	          dataType: 'json'});
	}
}


function toReturn(){
	window.location.href = "manager.jsp";
}

</script>
</head>
<body onload="doInit()" >
<%-- <div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;
		<%
			if(sid > 0){	
		%>	
		编辑
		<%
			}else{
		%>
		新建	
		<%
			}
		%>
		人才库</b>
</div> --%>
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="85%" class="TableBlock" >
	 <tr>
	    <td nowrap class="TableHeader" colspan="6" ><b>&nbsp;基本信息</b></td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">计划名称：<font color="red">*</font></td>
	    <td class="TableData" width="180" name="planName" id="planName" >
	    	    </td>
	    <td nowrap class="TableData" width="100">应聘人姓名：<font color="red">*</font></td>
	    <td class="TableData" width="180" colspan="2"  name="employeeName">
	    </td>
	    <td class="TableData" rowspan="7" colspan="1" align="center">
	     <center  id="attacheImg">
	     </center>   </td>
	  </tr>
	  <tr>
	   <td nowrap class="TableData">性别：</td>
	   <td class="TableData"  name="employeeSexDesc" >
	   </td>
	   <td nowrap class="TableData" width="100">出生日期：</td>
	    <td class="TableData" width="180" colspan="2"  name="employeeBirthStr">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">民族：</td>
	    <td class="TableData"  width="180"  name="employeeNationality" >
	    </td>
	    <td nowrap class="TableData" width="100">现居住城市：</td>
	    <td class="TableData" width="180" colspan="2" name="residencePlace">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">联系电话：</td>
	    <td class="TableData"  width="180" name="employeePhone">
	    </td>
	    <td nowrap class="TableData" >E-MAIL：</td>
	     <td class="TableData"  width="180" colspan="2"  name="employeeEmail">
	     </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData">籍贯：</td>
	    <td class="TableData" nowrap="nowrap" name="employeeNativePlace2">
	    	  <div id="employeeNativePlaceDesc"></div>
	      <div id="employeeNativePlace2"></div>
	    </td>
	     <td nowrap class="TableData" width="100">户口所在地：</td>
	    <td class="TableData"  width="180" colspan="2" name="employeeDomicilePlace">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData">婚姻状况：</td>
	    <td class="TableData" name="employeeMaritalStatusDesc">
	    </td>
	    <td nowrap class="TableData">政治面貌：</td>
	    <td class="TableData" colspan="2" name="employeePoliticalStatusDesc">
	    </td>
	  </tr>
	  <tr>
	  	<td nowrap class="TableData" width="100">健康状况：</td>
	    <td class="TableData"  width="180"  name="employeeHealth" >
	    </td>
	     <td nowrap class="TableData" width="100">参加工作时间：</td>
	    <td class="TableData"  width="180" colspan="2" name="jobBeginningStr" >
	    </td>
	  </tr>
	 <tr>
	  <td nowrap class="TableData">期望工作性质：</td>
	    <td class="TableData" name="jobCategoryDesc">
	    </td>
	    <td nowrap class="TableData" width="100">照片上传：</td>
	    <td class="TableData" colspan="3">
	    
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData">期望从事行业：</td>
	    <td class="TableData"  name="jobIndustry">
	    </td>
	    <td nowrap class="TableData">期望从事职业：</td>
	    <td class="TableData" name="jobIntension">
	    </td>
	    <td nowrap class="TableData">期望工作城市：</td>
	    <td class="TableData" name="workCity" >
	    </td>
	  </tr>
	    <tr>
	    <td nowrap class="TableData" width="110">期望薪水(税前)：</td>
	    <td class="TableData"  width="180"  name="expectedSalary">
	    </td>
	    <td nowrap class="TableData" width="100">岗位：</td>
	    <td class="TableData" name="positionDesc">
	    </td>
	    <td nowrap class="TableData" width="100">到岗时间：</td>
	    <td class="TableData"  width="180" name="startWorkingDesc">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">毕业时间：</td>
	    <td class="TableData"  width="180"  name="graduationDateStr" >
	    </td>
	    <td nowrap class="TableData" width="100">毕业学校：</td>
	    <td class="TableData"  width="180" colspan="3"  name="graduationSchool">
	    </td>
	  </tr>
	    <tr>
	    <td nowrap class="TableData" width="100">所学专业：</td>
	    <td class="TableData"  width="180" name="employeeMajorDesc">
	    </td>
	    <td nowrap class="TableData" width="100">学历：</td>
	    <td class="TableData"  width="180" name="employeeHighestSchoolDesc">
	    </td>
	    <td nowrap class="TableData" width="100">学位：</td>
	    <td class="TableData"  width="180" name="employeeHighestDegreeDesc">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">外语语种1：</td>
	    <td class="TableData"  width="180" name="foreignLanguage1"></td>
	    <td nowrap class="TableData" width="100">外语语种2：</td>
	    <td class="TableData"  width="180" name="foreignLanguage2" ></td>
	    <td nowrap class="TableData" width="100">外语语种3：</td>
	    <td class="TableData"  width="180"  name="foreignLanguage3"></td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">外语水平1：</td>
	    <td class="TableData"  width="180" name="foreignLevel1"></td>
	    <td nowrap class="TableData" width="100">外语水平2：</td>
	    <td class="TableData"  width="180" name="foreignLevel2"></td>
	    <td nowrap class="TableData" width="100">外语水平3：</td>
	    <td class="TableData"  width="180"  name="foreignLevel3"></td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">计算机水平：</td>
	    <td class="TableData"  width="180" name="computerLevel">
	    </td>
	    
	    <td nowrap class="TableData">年龄：</td>
	    <td class="TableData" name="employeeAge">
	    </td>
	    
	    
	    <td nowrap class="TableData">招聘渠道：</td>
	    <td class="TableData" width="180" name="recruChannelDesc">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">特长：</td>
	    <td class="TableData"  width="180" colspan="5"  name="employeeSkills" >
	   </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">职业技能：</td>
	    <td class="TableData"  width="180" colspan="5" name="chreerSkills" id="chreerSkills">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">工作经验：</td>
	    <td class="TableData"  width="180" colspan="5" name="wordExperience"  id="wordExperience">
	   </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">项目经验：</td>
	    <td class="TableData"  width="180" colspan="5"  name="projectExperience" id="projectExperience">
	   </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="100">备注：</td>
	    <td class="TableData"  width="180" colspan="5" name="remark">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableHeader" colspan="6"><b>&nbsp;附件简历</b></td>
	  </tr>
	 
	
	<tr>
		<td nowrap class="TableData" width="" >附件上传：</td>
		<td class="TableData"  colspan="6">
				<div id="fileContainer"></div> 
			<%-- <a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" /> --%>
		</td>
	</tr>
	<tr>
		<td class="TableData" width="" colspan="6">
			培训内容：<font style='color:red'>*</font><br>
			<DIV  id="resume" name="resume" id="resume">
			</DIV>
		</td>
	</tr>
	<!-- 
		<tr>
		<td class="TableHeader"width="" colspan="6" align="center">
			&nbsp;&nbsp;
			<input type="button" class="btn btn-primary" value="返回" onclick="toReturn();">
		</td>
	</tr> -->
	</table>
</form>
<br>

</body>
</html>
 