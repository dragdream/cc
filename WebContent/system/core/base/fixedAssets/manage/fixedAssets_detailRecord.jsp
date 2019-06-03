<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
     	String sid = request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/fixedAssets/js/assets.js"></script>
<style>
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}
</style>

<script>
var sid = "<%=sid%>";
var deptName;
function doInit(){
    getAssetsInfo();
}


function getAssetsInfo(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeFixedAssetsInfoController/getAssetsInfo.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			deptName=json.rtData.deptName;
			
			var url = contextPath+"/teeFixedAssetsRecordController/selectDeprecRecordsByAssetsId.action?assetId="+sid;
			var json = tools.requestJsonRs(url);
			var list = json.rtData;
			desc="";
			var tbody = "<tr style='border-bottom:1px solid #000'><td>折旧开始时间</td><td>资产原值</td><td>折旧年月</td><td>本月折旧值</td><td>本月净值</td><td>操作日期</td><td>操作类型</td><td>操作人</td></tr>";
			for(var i =0; i <list.length ; i++){
				var prc = list[i];
				var tr  = "<tr >"
				 + "<td class='TableData'>" + getFormatDateStr(prc.startTime,"yyyy-MM-dd") + " </td>"
				 + "<td class='TableData'>" + prc.original + " </td>"
				 + "<td class='TableData'>" + getFormatDateStr(prc.deprecTime,"yyyy-MM-dd")  + " </td>"
				 + "<td class='TableData'>" +  prc.deprecValue  + " </td>"
				 + "<td class='TableData'>" +  prc.deprecRemainValue  + " </td>"
				 + "<td class='TableData'>" +  getFormatDateStr(prc.crTime,"yyyy-MM-dd")  + " </td>"
				 + "<td class='TableData'>" +  (prc.flag==1?"自动":"手动")  + " </td>"
				 + "<td class='TableData'>" +  prc.userName  + " </td>"
				+ "</tr>";
				tbody = tbody + tr;
			}
			desc = desc + tbody;
			$("#deprecDesc").append(desc);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}



</script>

</head>
<body onload="doInit();" style="font-size:12px;padding:10px">
	<table>
	   <tbody id="deprecDesc">
	   </tbody>
    </table>
</body>
</html>