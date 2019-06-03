<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title>销售简报</title>
	
<style>

</style>
	
<script type="text/javascript" >
var sid = "<%=sid%>";
var xparent;
/**
   初始化列表
 */
function doInit(){
	getSalesInfo();
}

function getSalesInfo(){
	var url=contextPath+"/TeeCrmCustomerController/salesSummary.action";
	var para =  tools.formToJson($("#form1"));
	var json=tools.requestJsonRs(url,para);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		//alert(tools.jsonObj2String(data));
		}
}


</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;" onload="doInit();">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_bjkh.png">
		<span class="title">销售简报</span>
	</div>
</div>

<div style="margin-top: 10px;">
    <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">条件</span>
</div>
<form method="post" name="form1" id="form1" >
	<table width="100%" align="center" style="margin-top: 10px;">
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				月份：
			</td>
		    <td class="TableData">
                <select type='text' name="year" id="year" style="height: 23px;width: 150px">
	            <%
					int currentYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					int counter = currentYear + 30;
					int defaultYear = 2000;
					for(int i=defaultYear;i<=counter;i++){
				%>
				<option value="<%=i %>"  <%=(i == currentYear)? "selected":"" %> ><%=i %>年</option>
				<%
					}
				%>
                </select>
                <select type="text" name="month" id="month" style="height: 23px;width: 150px">
                    <%
					int currentMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
					int defaultMonth = 1;
					for(int i=defaultMonth;i<=12;i++){
				     %>
				    <option value="<%=i %>"  <%=(i == currentMonth)? "selected":"" %> ><%=i %>月</option>
				     <%
					}
				     %>
                </select>
                 <input type="button" value="查询" class="btn-win-white" onclick="getSalesInfo();"/>
            </td>
		</tr>
</table>
</form>
	<table style="width: 100%;margin-top: 10px;">
		<tr>
		   <td colSpan=2 noWrap>
		    <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">数据</span>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				新增客户：
			</td>
		    <td name ="customerCount" id="customerCount">
            </td>
		</tr>
		<tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">新增联系人：</td>
            <td align="left"  name="contactsCount" id="contactsCount">
           </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">新增商机：</td>
		   <td id="chancesCount" name="chancesCount">
		   </td>
        </tr>
        <tr>
            <td width="150px;" style="text-indent: 15px;line-height: 30px;">拜访客户：</td>
			<td id="visitsCount" name ="visitsCount">
			</td>
        </tr>
	 </table>
	  </br>
    <table style="width: 100%;">
      <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">指标</span>
		   </td>
	 </tr>
	 <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">目标：
			</td>
		    <td name="targetCount" id="targetCount">
            </td>
	 </tr>
	 <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">预测：
			</td>
		    <td name="forecast" id="forecast">
            </td>
	 </tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">销售订单：
			</td>
		    <td name="orderCount" id="orderCount">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">回款：
			</td>
		    <td name="payBackCount" id="payBackCount">
            </td>
	</tr>
    <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">业绩完成：
			</td>
		    <td name=achievement id="achievement">
            </td>
	</tr>
</table>
 </br>
</br>
</body>

</html>