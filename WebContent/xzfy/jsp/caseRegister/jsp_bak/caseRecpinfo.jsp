<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String caseId =
            request.getParameter("caseId") == null ? "" : request.getParameter("caseId");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>接待信息</title>
<link rel="stylesheet" href="../../css/common.css">
	<script src="../../js/base/jquery-1.9.1.min.js"></script>
	<script src="../../js/base/juicer-min.js"></script>
	<style>
body {
	background: #fff;
	padding: 5px 10px 0 10px;
}

.getcase {
	background: #47A1FF;
	color: #fff;
	width: 80px;
	height: 30px;
	display: block;
	border: none;
}

.fyform {
	text-align: left;
}

.fyleft,.fyright {
	width: 48%;
	display: inline-block;
}

.inputlable {
	text-align: right;
	height: 30px;
	line-height: 30px;
	font-weight: normal;
	width: 21%;
	display: inline-block;
	margin-bottom: 5px;
}

.fyinput {
	height: 28px;
	border-radius: 0px;
	width: 75%;
	display: inline-block;
	border: 1px solid #bdbcbc;
	padding: 0;
}

.fyselect {
	height: 30px;
	border-radius: 0px;
	width: 75%;
	display: inline-block;
	border: 1px solid #bdbcbc;
}

.rowlable {
	width: 10%;
	text-align: right;
	vertical-align: top;
	display: inline-block;
}

.rowinput {
	width: 84%;
}

label:after {
	content: "*";
	color: red;
}

.other-one,.other-two {
	margin-left: 6% ；;
	/* display: inline-block; */
	display: inline-block;
	width: 100%;
	margin-left: 6%;
}

.recpinfo {
	width: 90%;
	border: 1px solid #EFEFEF;
	margin: 10px auto;
}
/*表格部分*/
.recp-title {
	display: inline-block;
	height: 30px;
	width: 100%;
	text-align: left;
	background: #F6F6F6;
	margin: 0;
}
/*表格部分结束*/
</style>
</head>

<body>
	<button class="getcase" type="button" onclick="getCaseInfo_btn()">案件提取</button>
	<div class="fyform">
		<div class="fyleft">
			<label class="inputlable">申请方式：</label> <select id="operationType"
				class="fyselect">
				<option values="1">1</option>
				<option values="2" selected="selected">2</option>
			</select> <label class="inputlable">接待地点：</label> <input type="text"
				class="fyinput" id="place" values="${place}" placeholder="" /> <label
				class="inputlable">第二接待人：</label> <input type="text" class="fyinput"
				id="dealMan2Id" values="${dealMan2Id}" placeholder="" /> <label
				class="inputlable">处理结果：</label> <select id="dealResultCode"
				value="${dealResultCode}" class="fyselect">
				<option values="1">1</option>
				<option values="2">2</option>
				<option values="3">3</option>
				<option values="4">4</option>
				<option values="5">5</option>
			</select>
		</div>

		<div class="fyright">

			<label class="inputlable">接待日期：</label> <input type="text"
				class="fyinput" id="receptionDate" values="${receptionDate}"
				placeholder="" />

			<!--            <label class="inputlable">接待人：</label>
            <select id="dealMan1Id" class="fyselect">
                <option values="1">1</option>
                <option values="2">2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
            </select> -->
			<label class="inputlable">接待人：</label> <input type="text"
				class="fyinput" id="dealMan1Id" values="${dealMan1Id}"
				placeholder="" /> <label class="inputlable  control-label">复议请求：</label>
			<select id="recertionTypeCode" class="fyselect">
				<option values="1">1</option>
				<option values="2">2</option>
				<option values="3">3</option>
				<option values="4">4</option>
				<option values="5">5</option>
			</select> <label class="inputlable  control-label">被接待人数：</label> <input
				type="text" class="fyinput" id="" placeholder="" />
		</div>

		<div class="recpinfo">
			<p class="recp-title">被接待人信息</p>
			<div class="edit-table-div">
				<table id="recp-table" class="edit-table"></table>
				<p class="edit-table-add" onclick="recp_insertRow();">添加</p>
			</div>
		</div>

		<div class="fyrow">
			<div class="text-area">
				<label class="rowlable">接待情况：</label>
				<textarea id="receptionDetail" values="${receptionDetail}"
					class="rowinput" rows="3"></textarea>
			</div>
			<div class="other-one">
				<label>其他复议机关\法院受理同一复议申请：</label> 
				<span><input type="radio" name="optradio" /> 是</span> 
				<span><input type="radio" name="optradio" /> 否 </span> 
				<label>复议机关名称：</label> 
				<select id="reconsiderOrganName" values="reconsiderOrganName" class="">
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
				</select>
			</div>
			<div class="other-two">
				<label>接收材料：</label>
				<span><input type="radio" name="optradio" onclick="showTable();" /> 是</span> 
				<span><input type="radio" name="optradio" onclick="hideTable();" /> 否</span>
			</div>
		</div>

		<div class="edit-table-div material-div">
			<table id="material-table" class="edit-table"></table>
			<p class="edit-table-add" onclick="material_insertRow();">添加</p>
		</div>
	</div>

	<script src="/xzfy/js/caseRegister/receptionTemplate.js"></script>
	<script src="/xzfy/js/caseRegister/letterrReception.js"></script>
</body>
</html>