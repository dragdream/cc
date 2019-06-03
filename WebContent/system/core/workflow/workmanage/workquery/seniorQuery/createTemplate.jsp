<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
   int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>新建查询模板信息</title>
<script type="text/javascript">
var flowId=<%=flowId %>;
//保存
function doSave(){
	if($("#form1").valid()){
		var param=tools.formToJson($("#form1"));
		param["flowId"]=flowId;
		param["formInfo"]=parent.getFormInfo();
		param["basicInfo"]=parent.getBasicInfo();
		param["statisticInfo"]=parent.getStatisticInfo();
		var url=contextPath+"/flowSeniorQueryTemplateController/addOrUpdate.action";
		var json=tools.requestJsonRs(url,param);
		return json;
	}
}




</script>
</head>


<body style="background-color: #f2f2f2">
   <form id="form1">
      <table calss="TableBlock" style="padding-top: 10px" >
         <tr>
            <td>模板名称：</td>
            <td>
                <input type="text" name="templateName" id="templateName" style="height:23px;width: 250px" required/>
            </td>
         </tr>
     </table>
   </form>   
</body>

<script type="text/javascript">
$("#form1").validate();

</script>
</html>