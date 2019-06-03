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
	<title>选择不予受理理由</title>
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
			<input type="checkbox" name="reason" value="(一)申请人是公民,无申请人身份证明"/>(一)申请人是公民,无申请人身份证明
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(二)申请人是法人或其他组织,无工商营业执照副本复印件或者设立登记证明复印件"/>(二)申请人是法人或其他组织,无工商营业执照副本复印件或者设立登记证明复印件
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(三)申请人是法人或其他组织,无法定代表人或者负责人身份证明"/>(三)申请人是法人或其他组织,无法定代表人或者负责人身份证明
		</td>
	</tr>
	<tr>	
		<td>
			<input type="checkbox" name="reason" value="(四)股份制企业的股东大会,股东代表大会,董事会以企业的名义申请行政复议,无相应的会议决议或者其他证明文件材料"/>(四)股份制企业的股东大会,股东代表大会,董事会以企业的名义申请行政复议,无相应的会议决议或者其他证明文件材料
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(五)合伙企业申请行政复议,无企业核准登记证明复印件和执行合伙事务的合伙人身份证明"/>(五)合伙企业申请行政复议,无企业核准登记证明复印件和执行合伙事务的合伙人身份证明
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(六)其他合伙组织申请行政复议,无全体合伙人身份证明"/>(六)其他合伙组织申请行政复议,无全体合伙人身份证明
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(七)不具备法人资格的其他组织申请行政复议,无该组织主要负责人身份证明"/>(七)不具备法人资格的其他组织申请行政复议,无该组织主要负责人身份证明
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(八)该行政复议申请不属于收到行政复议申请的行政复议机构的职责范围"/>(八)该行政复议申请不属于收到行政复议申请的行政复议机构的职责范围
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(九)不具备法人资格的其他组织申请行政复议,没主要负责人,由共同推选的其他成员代表该组织参加的行政复议,无推选证明文件和被推选着身份证明"/>(九)不具备法人资格的其他组织申请行政复议,没主要负责人,由共同推选的其他成员代表该组织参加的行政复议,无推选证明文件和被推选着身份证明
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十)没有明确的被申请人"/>(十)没有明确的被申请人
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十一)没有合格的被申请人"/>(十一)没有合格的被申请人
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十二)行政复议请求不明确或者不符合法律规定"/>(十二)行政复议请求不明确或者不符合法律规定
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十三)申请人申请行政复议的具体行为不明确"/>(十三)申请人申请行政复议的具体行为不明确
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十四)申请人申请具体行政行为的时间不明确"/>(十四)申请人申请具体行政行为的时间不明确
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十五)申请人对规划行政许可,房屋登记,土地登记等具体行政行为不服申请行政复议,无房屋所有权证复印件,土地征收复印件,派出所证明等证明申请人与该具体行政行为有利害关系的证明材料"/>(十五)申请人对规划行政许可,房屋登记,土地登记等具体行政行为不服申请行政复议,无房屋所有权证复印件,土地征收复印件,派出所证明等证明申请人与该具体行政行为有利害关系的证明材料
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十六)申请人认为被申请人不履行法定责任,未提供曾经要求被申请人履行法定责任而被申请人未履行的证明材料"/>(十六)申请人认为被申请人不履行法定责任,未提供曾经要求被申请人履行法定责任而被申请人未履行的证明材料
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十七)申请行政复议行为时一并提出行政赔偿请求,未提供具体行政行为侵害而造成损害的证明材料"/>(十七)申请行政复议行为时一并提出行政赔偿请求,未提供具体行政行为侵害而造成损害的证明材料
		</td>
	</tr>
	<tr>
		<td>
			<input type="checkbox" name="reason" value="(十八)委托代理人申请行政复议,无授权委托书"/>(十八)委托代理人申请行政复议,无授权委托书
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
	function giveFatherMatial(){
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