<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header.jsp" %>
	<%
		String flowId = request.getParameter("flowId") == null ? "" : request.getParameter("flowId");
	%>
	<title>Tenee办公自动化智能管理平台</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.min_cxt.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
	<script src="<%=contextPath%>/system/core/workflow/workmanage/workquery/seniorQuery/js/seniorQuery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript">
		var flowId = "<%=flowId%>";
		$(function() {
			
			//alert('${fn:length(queryList)}');
		});
		function seniorQuery(){
			//跳转页面，springMVC 返回对象
			window.location = contextPath + "/seniorQuery/flowList.action";
		}
		function toNewTpl(flowId){
			//跳转页面，springMVC 返回对象
			window.location = contextPath + "/seniorQuery/toOrderTpl.action?flowId="+flowId;
		}
		function toEditTpl(flowId,tplId){
			//跳转页面，springMVC 返回对象
			window.location = contextPath + "/seniorQuery/toOrderTpl.action?flowId="+flowId+"&tplId="+tplId;
		}
		function deleteTpl(tplId){
			var url = "<%=contextPath%>/seniorQuery/deleteTpl.action?tplId="+tplId;
			var para =  {};
			var jsonRs = tools.requestJsonRs(url,para);
			alert(jsonRs.rtMsg);
			window.location.reload();
		}
	</script>
	
</head>
<body style="margin:5px 0 0 1px;">
	<table style="border='0'; width='95%'; cellspacing='0'; cellpadding:'3'" class="small">
	  	<tr>
		    <td class="Big"><img src="<%=imgPath%>/notify_new.gif" style="text-align:'absmiddle'"><span class="big3"> 新建模板（流程名称：${flow.flowName}）</span><br>
		    </td>
	  	</tr>
	</table>
	<br>
	<div align="center">
	  	<input type="button"  value="新建模板" class="SmallButtonB" onClick="toNewTpl(${flow.sid});" title="新建模板" />
	</div>
	<br>
	<table style="border='0'; width='95%'; cellspacing='0'; cellpadding:'3'" class="small">
	  	<tr>
		    <td class="Big"><img src="<%=imgPath%>/notify_open.gif" style="text-align:'absmiddle'"><span class="big3">管理模板</span><br>
		    </td>
	  	</tr>
	</table>
  	<table width="99%" class="TableBlock">
	    <tr class="TableHeader">
		      <td nowrap align="center" width="30%">流程名称</td>
		      <td nowrap align="center" width="50%">模板名称</td>
		      <td nowrap align="center" width="20%">管理</td>
	    </tr>
	    <c:forEach items="${queryList}" var="queryListSort" varStatus="queryListStatus">
	        <tr class="TableLine">
      			<td align="center" width="30%">${flow.flowName}<input type="hidden" id="id_${queryListStatus.count}" value = "${queryList[queryListStatus.index].sid}"/></td>
      			<td align="center" width="50%">${queryList[queryListStatus.index].tplName}</td>
     			<td align="center" nowrap width="20%">
     	    		<a href="javascript:toEditTpl('${flow.sid}','${queryList[queryListStatus.index].sid}');">编辑</a>
          			<a href="javascript:deleteTpl('${queryList[queryListStatus.index].sid}');">删除</a>
		      	</td>
		    </tr>
		</c:forEach>
 	</table>
	<div align="center" id="noData" style="display:none;">
	  	无模板！
	</div>
</body>
</html>
