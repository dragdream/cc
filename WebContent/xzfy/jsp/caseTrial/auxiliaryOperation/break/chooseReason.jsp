<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<%
	String nowReason = request.getParameter("nowReason");
%>

<title>中止</title>
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

<div class="easyui-panel" title="中止理由" style="width: 100%;" align="center" id="baseDiv">
<table align="center" width="100%" class="TableBlock">
	<tr>
		<td>
			<input type="checkbox" name="reason" value="作为申请人的自然人死忙，其近亲属尚未确定是否参加行政复议的" >作为申请人的自然人死忙，其近亲属尚未确定是否参加行政复议的
		</td>
		<td>
			<input type="checkbox" name="reason" value="申请人、被申请人因不可抗力，不能参加行政复议的 ">申请人、被申请人因不可抗力，不能参加行政复议的
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="作为申请人的自然人丧失参加行政复议的能力，尚未确定法定代理人参加行政复议的" >作为申请人的自然人丧失参加行政复议的能力，尚未确定法定代理人参加行政复议的
		</td>
		<td>
			<input type="checkbox" name="reason" value="案件涉及及法律适用问题，需要有权机关作出解释或者确认的" >案件涉及及法律适用问题，需要有权机关作出解释或者确认的
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="作为申请人的法人或者其他组织终止，尚未确定权利义务承受人的" >作为申请人的法人或者其他组织终止，尚未确定权利义务承受人的
		</td>
		<td>
			<input type="checkbox" name="reason" value="案件审理需要以其他案件的审理结果为依据，而其他案件尚未审结的" >案件审理需要以其他案件的审理结果为依据，而其他案件尚未审结的
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="作为申请人的自然人下落不明或者宣告失踪的" >作为申请人的自然人下落不明或者宣告失踪的
		</td>
		<td>
			<input type="checkbox" name="reason" value="其他需要中止行政复议的情形" >其他需要中止行政复议的情形
		</td>
	</tr>
	
</table>
</div>
<script type="text/javascript">
	var nowReason = "<%= nowReason%>";
	$(function(){
		if(nowReason!=""&&nowReason!=null){
			//将字符串拆分为数组并回显
			var reasonArray = nowReason.split(",");
			var checkbox = document.getElementsByName('reason');
			for(var i = 0;i<reasonArray.length;i++){
				for(var j = 0; j < checkbox.length; j++){
					if(reasonArray[i]==checkbox[j].value){
						checkbox[i].checked = true;
					}
				}
			}
		}
	});
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