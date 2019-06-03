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
table tr td{
	width:200px;
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
			//bindJsonObj2Cntrl(json.rtData);
			deptName=json.rtData.deptName;
			
			var prcs = getAssetsRecordByAssetsId(sid , '');
			var desc = "";
			if(prcs.length > 0){
				var tbody = "<tr style='border-bottom:1px solid #000'><td>日期</td><td>业务类型</td><td>负责人</td><td>负责人部门</td><td></td></tr>";
				for(var i =0; i <prcs.length ; i++){
					var prc = prcs[i];
					var optType = prc.optType;
					var statusColor = "green";
					if(optType == '0'){
						
					}else if(optType == '1'){
						statusColor = "blue";
					}else if(optType == '2'){
						statusColor = "orange";
					}else if(optType == '3'){
						statusColor = "orange";
					}else if(optType == '4'){
						statusColor = "red";
					}
					var optTypeDesc = 	"<span style='padding:3px;background:"+statusColor+";color:#fff;'>"+prc.optTypeDesc+"</span>";
					
					var tr  = "<tr >"
					 + "<td class='TableData'>" + prc.optDateStr + " </td>"
					 + "<td class='TableData'>" + optTypeDesc + " </td>"
					 + "<td class='TableData'>" + prc.userName  + " </td>"
					 + "<td class='TableData'>" +  prc.deptName  + " </td>"
					 + "<td></td>"
					+ "</tr>";
					tbody = tbody + tr;
				}
				desc = desc + tbody;
			}
			
			$("#recordDesc").append(desc);
		
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}



</script>

</head>
<body onload="doInit();" style="font-size:12px;padding:10px">
	<table>
	   <tbody id="recordDesc">
	   </tbody>
    </table>
</body>
</html>