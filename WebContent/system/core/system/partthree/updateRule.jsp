<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>规则修改页面</title>
<script type="text/javascript">
var sid=<%=sid %>;
//初始化
function doInit(){
	//根据主键获取详情
	getInfoBySid();
	
}


//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/teePartThreeRuleController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		
	}
}


//保存
function doSaveOrUpdate(){
	var url=contextPath+"/teePartThreeRuleController/updateRule.action";
	var param=tools.formToJson($("#form1"));
	var json=tools.requestJsonRs(url,param);
	return json;
}
</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
<form id="form1">
  <input type="hidden" name="sid" value="<%=sid %>"/>
   <table class="TableBlock" width="100%">
      <tr>
         <td style="text-indent: 10px">规则描述：</td>
         <td><span id="ruleDesc" name="ruleDesc"></span></td>
      </tr>
      <tr>
         <td style="text-indent: 10px">针对角色：</td>
         <td>
            <select id="operPriv" name="operPriv">
               <option value="1">系统管理员</option>
               <option value="2">系统安全员</option>
               <option value="3">安全审计员</option>
            </select>
         </td>
      </tr>
      <tr>
         <td style="text-indent: 10px">开启状态：</td>
         <td>
            <input type="radio" value="1" name="isOpen"/>开启
            <input type="radio" value="0" name="isOpen"/>停用
         </td>
      </tr>
   </table>
</form>
</body>
</html>