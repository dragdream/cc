<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>当事人</title>
		<link rel="stylesheet" href="../../css/common.css">
			<script src="../../js/base/jquery-1.9.1.min.js"></script>
			<script src="../../js/base/juicer-min.js"></script>
</head>

<style>
body {
	background: #fff;
	padding: 5px 10px 0 10px;
}

.partyDiv {
	display: inline-block;
	width: 100%;
	border: 1px solid #c3c3c3;
}

.party-head {
	margin: 0;
	height: 30px;
	line-height: 30px;
	background: #f3f3f3;
}

.party-body {
	padding: 20px;
}

hr {
	border: 1px dotted #d8dfe2;
	margin: 10px 0;
}

.party-button {
	width: 140px;
	height: 30px;
}
</style>

<body>
	<div class="partyDiv">
		<p class="party-head">申请人</p>
		<div class="party-body">
			<div>
				<label>申请人类别：</label> <span><input type="radio"
					name="optradio" /> 公民</span> <span><input type="radio"
					name="optradio" /> 法人或其他组织</span> <span><input type="radio"
					name="optradio" /> 个体工商户</span>
			</div>

			<div class="edit-table-div applicant-div">
				<table id="applicant-table" class="edit-table"></table>
				<p class="edit-table-add" onclick="applicant_insertRow();">添加</p>
			</div>
			<hr />
			<!-- 用button控制添加的表格 -->
			<button class="party-button" onclick="add_agent();">添加申请人代理人</button>
			<div class="edit-table-div applicant-agent-div">
				<table id="applicant-agent-table" class="edit-table"></table>
			</div>
		</div>

		<p class="party-head">被申请人</p>
		<div class="party-body">
			<div class="edit-table-div applicant-div">
				<table id="respondent-table" class="edit-table"></table>
				<p class="edit-table-add" onclick="respondent_insertRow();">添加</p>
			</div>
			<hr />
			<!--  用button控制添加的表格 -->
			<button class="party-button" onclick="add_respondent_agent();">添加被申请人代理人</button>
			<div class="edit-table-div applicant-agent-div">
				<table id="respondent-agent-table" class="edit-table"></table>
			</div>
		</div>

		<p class="party-head">第三人</p>
		<div class="party-body">
			<div class="edit-table-div applicant-div">
				<table id="thirdParty-table" class="edit-table"></table>
				<p class="edit-table-add" onclick="thirdParty_insertRow();">添加</p>
			</div>
			<hr />
			<!-- 用button控制添加的表格 -->
			<button class="party-button" onclick="add_thirdParty_agent();">添加第三人代理人</button>
			<div class="edit-table-div applicant-agent-div">
				<table id="thirdParty-agent-table" class="edit-table"></table>
			</div>
		</div>
	</div>
	<script src="../../js/caseRegister/clientTemplate.js"></script>
	<script src="../../js/caseRegister/clientInfoRegister.js"></script>
</body>
</html>