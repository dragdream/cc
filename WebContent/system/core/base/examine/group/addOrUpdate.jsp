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
<title>新增/编辑指标集</title>
<script type="text/javascript">
var sid = <%=sid%>;
function doInit()
{
	if(sid > 0){
		getById(sid);
	}
	
}

/**
 * 新建或者更新
 */
function doSaveOrUpdate(callback){
	if(checkFrom()){
		var url = contextPath + "/TeeExamineGroupManage/addOrUpdate.action";
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
	 return true;
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/TeeExamineGroupManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var examineRefer = prc.examineRefer;
			if(examineRefer){
				var examineReferArray = examineRefer.split(",");
				if(examineReferArray[0] == '1'){
					$("#examineReferDiary").attr("checked",'true');
				}
				if(examineReferArray[1] == '1'){
					$("#examineReferCalendar").attr("checked",'true');
				}
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
	    <td nowrap class="TableData"> 考核指标集名称：<font style='color:red'>*</font></td>
	    <td class="TableData" colspan="3">
	      <input type="text" name="examineName" size="40" maxlength="100" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	  	</tr>
		 <tr>
	    <td nowrap class="TableData" width="50" >考核指标集描述：</td>
	    <td class="TableData"  colspan="3" >
	    	<textarea rows="5" cols="60"  name="examineDesc" class="BigTextarea"></textarea>
	     </td>
	  </tr>
	  
	 <tr style="display:none;">
	    <td nowrap class="TableData" width="50" >设定考核依据模块：</td>
	    <td class="TableData"  colspan="3" >
	    	 <input id="examineReferDiary" name="examineReferDiary" type="checkbox" value="1"><label for="anonymity"> 日志</label>
	    	 &nbsp;&nbsp; <input id="examineReferCalendar" name="examineReferCalendar" type="checkbox" value="1"><label for="anonymity">  日程</label>
	     </td>
	  </tr>
	 
	  
   
		<tr>
		    <td nowrap class="TableData">考核指标集使用范围（部门）：</td>
		    <td class="TableData" colspan="3">
		   		 <input type="hidden" name="postDeptIds" id="postDeptIds" value="">
		  		 <textarea cols=50 name="postDeptNames" id="postDeptNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
		  		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptIds', 'postDeptNames']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#postDeptIds').val('');$('#postDeptNames').val('');">清空</a>
		    </td>
		</tr>
    <tr>
      <td nowrap class="TableData">考核指标集使用范围（角色）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="postUserRoleIds" id="postUserRoleIds" value="">
	      <textarea cols=50 name="postUserRoleNames" id="postUserRoleNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['postUserRoleIds', 'postUserRoleNames']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserRoleIds').val('');$('#postUserRoleNames').val('');">清空</a>
      </td>
   </tr>
   <tr>
      <td nowrap class="TableData">考核指标集使用范围（人员）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="postUserIds" id="postUserIds" value="">
	      <textarea cols=50 name="postUserNames" id="postUserNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['postUserIds', 'postUserNames']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserIds').val('');$('#postUserNames').val('');">清空</a>
      </td>
   </tr> 

 
  </table>
  
     <input type="hidden" name="sid" id="sid" value="<%=sid %>">
</form>	
</body>
</html>
