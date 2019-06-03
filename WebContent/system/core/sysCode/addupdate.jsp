<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>系统编码</title>

<script type="text/javascript">

var sid = '<%=sid%>';

function doInit(){
	//添加例子一
	if(sid > 0){
		var url = "<%=contextPath %>/sysCode/getById.action";
		var para = {sid:sid};
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			bindJsonObj2Cntrl(json);
		/* 	$("#oldCodeNo").val(json.codeNo); */
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	
}

/**
 * 保存
 */
function doSave(){
	if(check()){
		var url = "<%=contextPath %>/sysCode/addOrUpdateSysPara.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			
			parent.location.reload();
		    $.jBox.tip("保存成功", 'info' , {timeout:1500});
		}else{
			alert(jsonRs.rtMsg);
			var codeValue = document.getElementById("codeNo");
			codeValue.focus();
			codeValue.select();
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
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big3"><img src="<%=imgPath%>/sys_config.gif" align="absmiddle"><span class="big3">
      
        <%
         if(sid == 0){
        	
        	  %>
        	  主编码新增
        	  
        	  <%
         }else{
        	 %>	 
        	 主编码编辑
        	 
         <%}
        %></span></td>
    </tr>
  </table><br>
  <form   method="post" name="form1" id="form1">
<table class="TableBlock" width="80%" align="center">
			<tr class="TableLine1">
				<td nowrap>代码编号：</td>
				<td nowrap>
					<INPUT type=text name="codeNo" id="codeNo" class="easyui-validatebox BigInput" required="true"  maxlength="30" >
					<!-- <INPUT type=hidden name="oldCodeNo" id="oldCodeNo" class="easyui-validatebox BigInput"  > -->
				</td>
			</tr>
			
			<tr class="TableLine2">
				<td nowrap>代码名称：</td>
				<td nowrap><input type="text" name="codeName" id="codeName" class="easyui-validatebox BigInput" required="true" >&nbsp;</td>
			</tr>
			<tr class="TableLine1" style="display:none;">
				<td nowrap>代码排序：</td>
				<td nowrap><INPUT type=text name="codeOrder" id="codeOrder" class="easyui-validatebox BigInput" required="true" size="10" maxlength="30"  value='0' validType="integeZero[]"></td>
			</tr>
			<tr class="TableControl">
				<td colspan="2" align="center">
					<input type="button" value="保存" class="btn btn-primary" onclick="doSave();">&nbsp;&nbsp;
					<input type="hidden" id="sid" name="sid" value='<%=sid %>'/>
				</td>
			</tr>
		</table>
   </form>
</body>
</html>