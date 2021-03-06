<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />

	<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
	<!-- 中腾按钮框架 -->
	<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
	<title>选择补正理由</title>
	<style> 
	tr:nth-child(even){
		 background-color:#F0F0F0;
	}
	textarea.textstyle {
		font-size: 13px;
	    color: #555555;
	    /* border: 1px solid #C0BBB4; */
    	border: 1px solid #cccccc;
    	border-radius: 3px;
	}
	</style>
</head>
<body style="padding-left: 10px;padding-right: 10px;">

	<div class="easyui-panel" title="不予受理理由" style="width: 100%;" align="center" id="baseDiv">
	<table align="center" width="100%" class="TableBlock">
		<tr>
		<td>
			<input type="checkbox" name="reason" value="行政复议申请书有遗漏"/>行政复议申请书有遗漏
		</td>
		<td>
			<input type="checkbox" name="reason" value="无明确的被申请人"/>无明确的被申请人
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="无必要的事实根据"/>无必要的事实根据
		</td>
		<td>
			<input type="checkbox" name="reason" value="无明确的复议请求"/>无明确的复议请求
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
	function giveFatherReason(){
		//获取所有选中的复选框的值
		var checkbox = document.getElementsByName('reason');
		var reason = "";
        for(var i = 0; i < checkbox.length; i++){
	         if(checkbox[i].checked){
	        	 reason+=checkbox[i].value+";"+"\n";
	         }
        }
        reason = reason.substring(0,reason.length-1);
        return reason;
	}
</script>
</body>
</html>