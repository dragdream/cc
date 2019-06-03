<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

 <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
  padding: 5px;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
</head>
<body onload=""  style="overflow:hidden;">

<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right: 10px;" src="<%=contextPath%>/system/subsys/crm/img/icon_khxx.png">
		<p class="title">客户</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"  style="width: 98%"></span>
</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px;"></div>
	
</body>
<script type="text/javascript">
var customerId = "<%=sid%>";
var customerName = "<%=customerName%>";
 $.addTab("tab","tab-content",[{title:"客户信息",url:contextPath+"/system/subsys/crm/core/customer/customerInfo.jsp?sid=<%=sid%>"},
                              {title:"线索",url:contextPath+"/system/subsys/crm/core/customer/customerClues/clueList.jsp?customerId="+customerId},
                              {title:"联系人",url:contextPath+"/system/subsys/crm/core/customer/linkman/linkmanList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"商机",url:contextPath+"/system/subsys/crm/core/customer/chances/chanceList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"订单",url:contextPath+"/system/subsys/crm/core/customer/order/orderList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"退货单",url:contextPath+"/system/subsys/crm/core/customer/returnOrder/retOrderList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"合同",url:contextPath+"/system/subsys/crm/core/customer/contracts/contractsList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"回款",url:contextPath+"/system/subsys/crm/core/customer/payback/paybackList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"退款",url:contextPath+"/system/subsys/crm/core/customer/drawback/drawbackList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"开票信息",url:contextPath+"/system/subsys/crm/core/customer/invoice/invoiceList.jsp?customerId="+customerId+"&customerName="+customerName},
                              {title:"拜访记录",url:contextPath+"/system/subsys/crm/core/customer/visit/visitRecordList.jsp?customerId="+customerId+"&customerName="+customerName}
                              ]); 

</script>
</html>
