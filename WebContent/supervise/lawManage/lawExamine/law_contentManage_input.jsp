<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String lawId = request.getParameter("lawId") == null ? "0" : request.getParameter("lawId");
			String id = request.getParameter("id") == null ? "0" : request.getParameter("id");
			String lawName = request.getParameter("lawName") == null ? "0" : request.getParameter("lawName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/lawManage/css/lawManage.css" />
<script type="text/javascript">
var id = '<%=id%>';
var lawId = '<%=lawId%>';
var lawName = '<%=lawName%>';
</script>

</head>
<body onload="doInit();" style="font-size: 14px; padding-left: 10px; padding-right: 10px;">
    <div style="width: 100%; overflow: auto;">
        <form role="form" id="form1" name="form1" enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
            <table class="TableBlock" style="padding: 5px 5px; background-color: #fff;margin-top: 5px;">
                <tr class="law-baseInfo-tr" style="line-height: 20px!important;">
                    <td class="short-width-label-td"></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>编</span></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>章</span></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>节</span></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>条</span></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>款</span></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>项</span></td>
                    <td class="short-width-td"><span class="red-star-flag-span">*</span><span>目</span></td>
                </tr>
                <tr class="law-baseInfo-tr">
                    <td class="short-width-label-td"></td>
                    <td class="short-width-td"><input id="detailSeries" name="detailSeries" class="easyui-textbox small-width-input" /></td>
                    <td class="short-width-td"><input id="detailChapter" name="detailChapter" class="easyui-textbox small-width-input" /></td>
                    <td class="short-width-td"><input id="detailSection" name="detailSection" class="easyui-textbox small-width-input" /></td>
                    <td class="short-width-td"><input id="detailStrip" name="detailStrip" class="easyui-textbox small-width-input" /></td>
                    <td class="short-width-td"><input id="detailFund" name="detailFund" class="easyui-textbox small-width-input" /></td>
                    <td class="short-width-td"><input id='detailItem' name='detailItem' class="easyui-textbox small-width-input" /></td>
                    <td class="short-width-td"><input id="detailCatalog" name="detailCatalog" class="easyui-textbox small-width-input" /></td>
                </tr>
            </table>
            <table class="TableBlock" style="padding: 5px 5px; margin: 5px 5px; background-color: #fff;">
                <tr class="law-baseInfo-tr">
                    <td class="short-width-label-td"><span>内容</span><span class="red-star-flag-span">*</span>:&nbsp;&nbsp;</td>
                    <td><input id="content" name="content" class="easyui-textbox" style="width: 460px; height: 140px;" /></td>
                </tr>
            </table>
        </form>
    </div>
         <script>
           $(function () {
               $('#detailSeries').textbox('textbox').attr('maxlength', 5);
               $('#detailChapter').textbox('textbox').attr('maxlength', 5);
               $('#detailSection').textbox('textbox').attr('maxlength', 5);
               $('#detailStrip').textbox('textbox').attr('maxlength', 5);
               $('#detailFund').textbox('textbox').attr('maxlength', 5);
               $('#detailItem').textbox('textbox').attr('maxlength', 5);
               $('#detailCatalog').textbox('textbox').attr('maxlength', 5);
               $('#content').textbox('textbox').attr('maxlength', 4000);
           });
         </script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/law_contentManage_input.js"></script>
</body>
</html>