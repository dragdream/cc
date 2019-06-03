<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  projectId=TeeStringUtil.getString(request.getParameter("projectId"));
//费用类型主键
  int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"), 0);
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>费用明细</title>
</head>
<script>
var projectId="<%=projectId%>";
var typeId=<%=typeId%>;
//初始化
function doInit(){
	
	var url=contextPath+"/projectCostController/getCostDetail.action";
	var json=tools.requestJsonRs(url,{projectId:projectId,typeId:typeId});
	
	if(json.rtState){
		
		var data=json.rtData;
		if(data.length>0){
			
			$("#tableList").append("<table id='tbody'  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>"
					+"<tr class='TableHeader' style='background-color:#e8ecf9'>"
					+"<td style=\"text-indent:10px;width:30%\" >申请人</td>"
					+"<td style=\"width:30%\">申请费用</td>"
					+"<td style=\"width:40%\">说明</td>"
		  		+ "</tr>"); 
			jQuery.each(data,function(i,sysPara){
				
			$("#tbody").append("<tr class='TableData'>"
								+"<td style=\"text-indent:10px;width:30%\" >" + sysPara.createrName+ "</td>"
								+"<td style=\"width:30%\" >" + sysPara.amount + "</td>"
								+"<td style=\"width:30%\">" + sysPara.description + "</td>"
					  		+ "</tr>"); 
			});
			$("#tableList").append("</table>");
		}else{
			messageMsg("无相关数据！","tableList","info");
		}
	}
}

</script>
<body onload="doInit()" style="padding-top: 10px;background-color: #f2f2f2">
   <div id="tableList"></div>
</body>
</html>