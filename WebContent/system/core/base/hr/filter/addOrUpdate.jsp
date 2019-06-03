 <%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<title>新建/编辑招聘筛选</title>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	if(sid > 0){
		getInfoById(sid);
	}
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/hrFilterController/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			if(prc.nextDatetime > 0){
				$("#nextDatetime").val(getFormatDateStr(prc.nextDatetime,'yyyy-MM-dd HH:mm:ss'));
			}else{
				$("#nextDatetime").val("");
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
	
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/hrFilterController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	   	if(jsonRs.rtState){
		   
		   $.MsgBox.Alert_auto("保存成功！",function(){
			   window.location.href = "manager.jsp";
		   });
	   }else{
		   $.MsgBox.Alert_auto(jsonRs.rtMsg);
	   }
	}
}


function toReturn(){
	//window.location.href = "manager.jsp";
	history.go(-1);
}

</script>
</head>
<body onload="doInit()" >
<form action=""  method="post" name="form1" id="form1">

<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock_page" >
  <tr>
     <td>
          <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		   <b style="color: #0050aa">
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
		   
		   </b>
     </td>
  </tr>
  <tr>
    <td nowrap class="TableData" width="15%" style="text-indent: 15px">应聘者姓名：<font color="red">*</font></td>
    <td class="TableData" >
      <INPUT type="text" name="hrPoolName" id="hrPoolName" class="BigInput easyui-validatebox BigStatic" size="15" required="true" value="" readonly="readonly">
      <INPUT type="hidden" name="hrPoolId" value="" id="hrPoolId">
     	 <a href="javascript:void(0);" class="orgAdd" onClick="getHrPool()">选择</a>
         <a href="javascript:void(0);" class="orgAdd" onClick="$('#hrPoolId').val('');$('#hrPoolName').val('')">清空</a>
    </td>
   </tr>
   <tr>
   <td nowrap class="TableData" style="text-indent: 15px">计划名称：<font color="red">*</font></td>
    <td class="TableData">
          <INPUT type="text"name="planName" id="planName" class="BigInput easyui-validatebox BigStatic" required="true" size="15" readonly="readonly">
	      <INPUT type="hidden" name="planId" id="planId" value="">
	      <a href="javascript:void(0);" class="orgAdd" onClick="getRecruitPlan()">选择</a>
	      <a href="javascript:void(0);" class="orgAdd" onClick="$('#planId').val('');$('#planName').val('')">清空</a>
    </td>  
  </tr>
  <tr>
    <td nowrap class="TableData" style="text-indent: 15px">应聘岗位：</td>
    <td class="TableData" >
      <INPUT type="text" name="position" id="position" class=BigInput size="15" value="" maxlength="100">
    </td> 
  </tr>
  <tr>
    <td nowrap class="TableData" style="text-indent: 15px" >所学专业：</td>
    <td class="TableData" >
      <INPUT type="text"name="employeeMajor" id="employeeMajor" maxlength="100" class=BigInput size="15" value="">
    </td>    
  </tr>
  <tr>
    <td nowrap class="TableData" style="text-indent: 15px">联系电话：</td>
    <td class="TableData">
      <INPUT type="text"name="employeePhone" id="employeePhone" class=BigInput maxlength="100" size="15" value="">
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px">发起人：</td>
    <td class="TableData" >
    
     	 <INPUT type="text"  class="BigInput BigStatic" name="" value="<%=userName%>" readonly="readonly">
    </td>     
  </tr>
  <tr>
    <td nowrap class="TableData" style="text-indent: 15px">下一次筛选办理人：<font color="red">*</font></td>
    <td class="TableData">
      <INPUT type="hidden" name="nextTransactorId" id="nextTransactorId" size="15" class="BigStatic BigInput "   readonly value="">
      <INPUT type="text" name="nextTransactorName" id="nextTransactorName" class="BigStatic BigInput easyui-validatebox" value=""   required="true">
      
      	<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['nextTransactorId', 'nextTransactorName']);">添加</a>
		<a href="javascript:void(0);" class="orgClear" onClick="$('#nextTransactorId').val('');$('#nextTransactorName').val('');">清空</a>
    
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px">下一次筛选时间：<font color="red">*</font></td>
    <td class="TableData">
      <input type="text" name="nextDatetime" id="nextDatetime" size="20" maxlength="20" class="BigInput  Wdate" required="true" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    
    </td>    
  </tr>
  <tr style="display: none;">
    <td nowrap class="TableData"> 提醒：</td>
    <td class="TableData" ></td>
  </tr>
	<tr align="center">
	    <td nowrap class="TableData" colspan=2>
	       <div align="right">
	          <input type="button"  value="保存" class="btn-win-white" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
	    	<input type="button"  value="返回" class="btn-win-white" onclick="toReturn()"/>
	       </div>  	
	    </td>
	 
 	 </tr>
	</table>
</form>
<br>

</body>
</html>
 