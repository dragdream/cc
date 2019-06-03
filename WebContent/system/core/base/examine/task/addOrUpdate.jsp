<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//sId
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>新增/编辑考核任务</title>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/examine/js/examine.js"></script>
	
<script type="text/javascript">
var sid = <%=sid%>;
function doInit()
{
	//获取有权限考核指标集
	getPostExamine("groupId");
	if(sid > 0){
		getById(sid);
	}
	
}

/**
 * 新建或者更新
 */
function doSaveOrUpdate(callback){
	if(checkFrom()){
		var url = contextPath + "/TeeExamineTaskManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url, para);
		if (jsonObj.rtState) {
			callback(jsonObj);
			parent.BSWINDOW.modal("hide");
		} else {
			alert(jsonObj.rtMsg);
		}
	}
	return false;
}
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").form('validate'); 
	 if(!check  ){
		 return false; 
	 }
	 if($("#taskEndStr").val() != '' &&  $("#taskBeginStr").val() != '' && $("#taskEndStr").val() < $("#taskBeginStr").val()){
		 alert("开始时间不能大于结束时间！");
		// $("#taskEndStr").select();
		 return false; 
	 }
	 return true;
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/TeeExamineTaskManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var anonymity = prc.anonymity;
			if(anonymity == '1'){
				$("#anonymity").attr("checked",'true');
			}
			
			var isSelfAssessment = prc.isSelfAssessment;
			if(isSelfAssessment == '1'){
				$("#isSelfAssessment").attr("checked",'true');
			}
			
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}
</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="96%" class="TableBlock">
	  <tr>
	      <td nowrap class="TableData">  考核任务标题：<font style='color:red'>*</font></td>
	      <td class="TableData" colspan="3">
	     	 <input type="text" name="taskTitle" size="40" maxlength="100" class="BigInput easyui-validatebox" value="" required="true">
	      </td>
	  </tr>
	  <tr>
		  <td nowrap class="TableData" width="50" >考核人：</td>
		  <td class="TableData"  colspan="3" >
		      <input type="hidden" name="rankmanIds" id="rankmanIds" value="">
	     	  <textarea cols=50 name="rankmanNames" id="rankmanNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	          <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['rankmanIds', 'rankmanNames']);">添加</a>
	          <a href="javascript:void(0);" class="orgClear" onClick="clearData('rankmanIds', 'rankmanNames');">清空</a>
		  </td>
	  </tr>
	   <tr>
		  <td nowrap class="TableData" width="50" >被考核人：</td>
		  <td class="TableData"  colspan="3" >
		      <input type="hidden" name="participantIds" id="participantIds" value="">
	     	  <textarea cols=50 name="participantNames" id="participantNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	          <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['participantIds', 'participantNames']);">添加</a>
	          <a href="javascript:void(0);" class="orgClear" onClick="clearData('participantIds', 'participantNames');">清空</a>
		  </td>
	  </tr>
	  
	  <tr>
	    <td nowrap class="TableData" width="50" >是否先自评：</td>
	    <td class="TableData"  colspan="3" >
	    	 <input id="isSelfAssessment" name="isSelfAssessment" type="checkbox" value="1"><label for="isSelfAssessment"> 自评</label>
	    	
	     </td>
	  </tr>
	  
	  <tr>
	    <td nowrap class="TableData" width="50" >是否匿名：</td>
	    <td class="TableData"  colspan="3" >
	    	 <input id="anonymity" name="anonymity" type="checkbox" value="1"><label for="anonymity"> 匿名</label>
	    	
	     </td>
	  </tr>
   
		<tr>
		    <td nowrap class="TableData">考核指标集：</td>
		    <td class="TableData" colspan="3">
		  		<select name="groupId" id="groupId" class="BigSelect">
		  			
		  		</select>
		    </td>
		</tr>
    <tr>
        <td nowrap class="TableData">有效日期：</td>
        <td class="TableData" colspan="3">
          	生效日期：<input type="text" name="taskBeginStr" id="taskBeginStr" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput"> 为空为立即生效
         	<br>终止日期：<input type="text" name="taskEndStr" id="taskEndStr" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">  为空为立即生效
        </td>
   </tr>
   
   
   <tr>
	    <td nowrap class="TableData" width="50" >是否事务提醒：</td>
	    <td class="TableData"  colspan="3" >
	    	 <input id="sms" name="sms" type="checkbox" value="1"><label for="sms"> 发送事务提醒消</label>
	    	
	     </td>
	  </tr>
   
   <tr>
      <td nowrap class="TableData">描述：</td>
      <td class="TableData" colspan="3">
      		 <textarea cols=50 name="taskDesc" id="taskDesc" rows=2 class="BigTextarea" wrap="yes" ></textarea>
      </td>
   </tr> 

 
  </table>
  
     <input type="hidden" name="sid" id="sid" value="<%=sid %>">
</form>	
</body>
</html>
