<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
<div class="mui-content">
	<ul class="mui-table-view">
	  <li class="mui-table-view-cell" onclick="window.location = '<%=contextPath%>/system/mobile/phone/customer/customInfo/myCustomer.jsp';">
	    <a class="mui-navigate-right">客户</a>
	  </li>
	  <li class="mui-table-view-cell" onclick="window.location = '<%=contextPath%>/system/mobile/phone/customer/contactUser/myContactUser.jsp';">
	    <a class="mui-navigate-right">联系人</a>
	  </li>
	  <li class="mui-table-view-cell" onclick="window.location = '<%=contextPath%>/system/mobile/phone/customer/saleFollow/mySaleFollow.jsp';">
	    <a class="mui-navigate-right">跟单</a>
	  </li>
	  <li class="mui-table-view-cell" onclick="window.location = '<%=contextPath%>/system/mobile/phone/customer/contract/myContract.jsp';">
	    <a class="mui-navigate-right" >合同</a>
	  </li>
	  <li class="mui-table-view-cell" onclick="window.location = '<%=contextPath%>/system/mobile/phone/customer/afterSaleService/myAfterSaleService.jsp';">
	    <a class="mui-navigate-right">售后服务</a>
	  </li>
	  <li class="mui-table-view-cell" onclick="window.location = '<%=contextPath%>/system/mobile/phone/customer/competitor/myCompetitor.jsp';">
	    <a class="mui-navigate-right">竞争对手</a>
	  </li>
	</ul>
</div>
<script>
	
	(function($) {
		//启动加载完毕的逻辑		
	})(mui);
	
</script>
</body>
</html>