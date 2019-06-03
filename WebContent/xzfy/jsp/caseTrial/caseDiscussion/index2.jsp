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
<%
	String caseId = request.getParameter("caseId");
	caseId="2209fb25c8a147c7b347457049305358";//测试用例
%>

<title>集体讨论会</title>
<style> 
	tr:nth-child(even){
		 background-color:#F0F0F0;
	}
</style>

</head>
<body style="padding-left: 10px;padding-right: 10px;" onload="doInit();">
<!-- 菜单栏 -->

<div class="content" id="d1">
        <table class="material">
            <tr>
                <td class="material-title mtr-head">归档目录</td>
                <td class="material-content mtr-head">归档文件</td>
                <td class="material-func mtr-head">
                    <button onclick="insertMtr();">增加</button>
                </td>
            </tr>
        </table>
    </div>
    <script src="juicer-min.js"></script>
    <script src="file_manage.js"></script>
</body>
</html>