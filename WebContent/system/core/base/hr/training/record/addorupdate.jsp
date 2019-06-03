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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>

<title>新建/编辑培训记录</title>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	getPlanList('planId');
	if(sid > 0){
		$("#tbody").show();
		getInfoById(sid);
	}
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/trainingRecordController/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			
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
	
	return true;
}


function doSaveOrUpdate(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/trainingRecordController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      callback();
	      /* setTimeout(function(){
	        //刷新父页面
	        parent.location.reload();
			//return true;
	      },1800); */
	    }else{
	      alert(jsonRs.rtMsg);
	    }
	}
}




</script>
</head>
<body onload="doInit()" >
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td class="TableData">培训计划：</td>
		<td class="TableData" colspan="3">
		
			<select name="planId" id="planId" class="BigSelect">
			</select>
	
		</td>
		
	</tr>
	<tr>
		<td class="TableData">受训人员：</td>
		<td class="TableData"  colspan="3">
		
			<%
				if(sid > 0){
			%>
			<input type="hidden" name="recordUserId"   class="BigStatic BigInput" readonly id="recordUserId" value="">
			<input type="text" name="recordUserName"   class="BigStatic BigInput" readonly id="recordUserName" value="">
			<%
				}else{
			%>
			<input type=hidden name="recordUserId" id="recordUserId" value="">
			<textarea  name="recordUserName" id="recordUserName" cols="50" rows="3" class="BigStatic BigTextarea  easyui-validatebox"   required="true" size="10"  readonly value=""></textarea>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['recordUserId', 'recordUserName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#recordUserId').val('');$('#recordUserName').val('');">清空</a>
			</span>
			
			<%} %>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="15%;" >培训机构：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >	
			<input class="BigInput easyui-validatebox" type="text" size="25" id = "recordInstitution" name="recordInstitution" maxlength="200"  required="true" value=""  />
		</td>
		<td nowrap class="TableData"  width="15%;" >培训费用：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input class="BigInput easyui-validatebox" type="text" id = "recordCost" name="recordCost" maxlength="8"    required="true" size="9"  value="0"   validType ='number[]'/>
		</td>
	</tr>
	
	<tbody id="tbody" style="display:none;">
		<tr>
		<td nowrap class="TableData"  width="15%;" >培训考核成绩：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input class="BigInput easyui-validatebox" type="text" id = "examResults" name="examResults"  required="true" maxlength="9" size="9"  value="0"  validType ='number[]' />
		</td>
		<td nowrap class="TableData"  width="15%;" >培训考核等级：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input class="BigInput easyui-validatebox" type="text" id = "examLevel" name="examLevel" maxlength="8" value="0"    size="9"  validType ='integeZero[]'/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">出勤情况：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="70" id="dutySituation" name="dutySituation" maxlength="200" class="BigTextarea"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">总结完成情况：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="70" id="trainningSituation" name="trainningSituation" maxlength="200" class="BigTextarea"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">评论：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="70" id="recordComment" name="recordComment"  class="BigTextarea"></textarea>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  width="">备注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="3" cols="70" id="remark" name="remark" class="BigTextarea"></textarea>
		</td>
	</tr>
		
	</tbody>
</table>
</form>

</body>
</html>