<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int parentId = TeeStringUtil.getInteger(request.getParameter("parentId"), 0);//父Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>编码功能</title>
<script type="text/javascript">

var sid = '<%=sid%>';
var parentId = "<%=parentId%>";//父级UUID


function doInit(){
	getManSysCode();
	//添加例子一
	if(sid > 0){
		var url = "<%=contextPath %>/sysCode/getById.action";
		var para = {sid:sid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			bindJsonObj2Cntrl(json);
			/* $("#oldCodeNo").val(json.codeNo); */
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	$("#parentId").val(parentId);
}

function getManSysCode(){
	var url = "<%=contextPath %>/sysCode/getSysPara.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		var parentIdDesc = "";
		jQuery.each(json, function(i, code) {
			parentIdDesc = parentIdDesc + "<option value='"+code.sid+"'>" + code.codeName + "</option>";
		});
		$("#parentId").append(parentIdDesc);
	}
}

/**
 * 保存
 */
function doSaveOrUpdate(callback){
	if (check()){
		var url = "<%=contextPath %>/sysCode/addOrUpdateSysPara.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			callback(jsonRs);
		}else{
			alert(jsonRs.rtMsg);
			var codeValue1 = document.getElementById("codeNo");
			codeValue1.focus();
			codeValue1.select();
		}
	}
	
}


function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function check() {
	return $("#form1").form('validate'); 
}

</script>

</head>
<body onload="doInit()">
 
  <form   method="post" name="form1" id="form1">
<table class="TableBlock" width="80%" align="center">
            <tr class="TableLine1">
				<td nowrap>主分类：</td>
				<td>
            		<input type="hidden" id="oldParentId" name="oldParentId" />
            		<select name="parentId" id="parentId" class="BigSelect">
            		</select>
				</td>
			</tr>
			<tr class="TableLine1">
				<td nowrap>代码编号：</td>
				<td nowrap>
					<INPUT type=text name="codeNo" id="codeNo" class="easyui-validatebox BigInput" required="true" size="10" maxlength="30" >
					<!-- <INPUT type=hidden name="oldCodeNo" id="oldCodeNo"> -->
					<br>在同一父级下的平级代码，该编号不能重复 
				</td>
			</tr>
			
			<tr class="TableLine1" style="display:none;">
				<td nowrap>排序号：</td>
				<td nowrap>
					<INPUT type=text name="codeOrder" id="codeOrder" class="easyui-validatebox BigInput" size="10" maxlength="10" value ="0" validType="integeZero[]" >

				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>代码名称：：</td>
				<td nowrap>
					<input type='text' name="codeName" id="codeName" maxlength="200" class="easyui-validatebox BigInput" required="true">&nbsp;
				</td>
			</tr>
<!-- 
			<tr class="TableControl">
				<td colspan="2" align="center" style="padding-top:20px;">
					<input type="button" value="保存" class="btn btn-primary" onclick="doSave();">&nbsp;&nbsp;
					<input type="button" value="返回" class="btn btn-primary" onclick="history.go(-1);">&nbsp;&nbsp;
					

				</td>
			</tr> -->
		</table>
		
		<input type='hidden' id="sid" name="sid"/>
   </form>
</body>
</html>