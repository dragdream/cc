<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page trimDirectiveWhitespaces="true" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="/header/header2.0.jsp"%>
    <%@ include file="/header/easyui2.0.jsp"%>
    <%@ include file="/header/validator2.0.jsp" %>
    <%@ include file="/header/upload.jsp" %>
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/case.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
</head>
<body onload="initSurveyInfo()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <form method="post" id="common_case_survey_info" style="width: 100%;" class="easyui-form" data-options="novalidate:true">
        <input type="hidden" id="id" name="id" value="${cSurveyInfo.id}"/>
        <input type="hidden" id="createDateStr" name="createDateStr" value="${cSurveyInfo.createDateStr}"/>
        <div class="easyui-panel case-common-panel-body tabs-header-border" style="width: 99%;" align="center" >
            <table class="TableBlock_page" fit="true" style="width: 100%; margin-top:20px;margin-bottom: 65px">
                <tr class="common-tr-border">
                    <td class="case-common-td-class27">调查日期<span class="required">*</span>：</td>
                    <td class="case-common-td-class18">
                        <input  name="surveyDateStr" id="surveyDateStr" value="${cSurveyInfo.surveyDateStr}"
                            class="easyui-datebox" style="width:100%;height:30px;" data-options="validType:'date', required:true, novalidate:true, missingMessage:'请选择调查日期'"/>
                    </td> 
                    <td class="case-common-td-class28">调查人&nbsp;：</td>
                    <td class="case-common-td-class18">
                        <input  name="surveyPerson" id="surveyPerson" value="${cSurveyInfo.surveyPerson}"
                            class="easyui-textbox" style="width:100%;height:30px;" data-options="novalidate:true, validType:'length[0,50]', missingMessage:'请输入少于50字'"/>
                    </td>
                    <td class="case-common-td-class28">调查对象&nbsp;：</td>
                    <td class="case-common-td-class18">
                        <input  name="surveyObject" id="surveyObject" value="${cSurveyInfo.surveyObject}"
                            class="easyui-textbox" style="width:100%;height:30px;" data-options="validType:'length[0,100]'"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class27" valign="top">调查地点&nbsp;：</td>
                    <td class="case-common-td-class16" colspan="5">
                        <input class="easyui-textbox case-textarea" name="address" id="address" 
                            data-options="validType:'length[0,100]',"
                            style="width: 100%; " value="${cSurveyInfo.address}" />
                    </td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class27" valign="top">调查笔录<span class="required">*</span>：</td>
                    <td class="case-common-td-class16" colspan="5">
                        <input class="easyui-textbox case-textarea" name="surveyRecord" id="surveyRecord" 
                            data-options="validType:'length[0,1000]', required: true, novalidate:true, missingMessage:'请填写调查笔录', multiline:true"
                             style="width: 100%; height:120px" value="${cSurveyInfo.surveyRecord}" />
                    </td>
                    <td class="case-common-td-class17"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class27" valign="top">采取的现场措施&nbsp;：</td>
                    <td class="case-common-td-class16" colspan="5">
                        <input class="easyui-textbox case-textarea" name="sceneMeasures" id="sceneMeasures" 
                            data-options="validType:'length[0,100]', missingMessage:'请输入少于4000字'"
                            style="width: 100%; " value="${cSurveyInfo.sceneMeasures}" />
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseManager/commonCase/js/common_case_add_2_research_survey.js"></script>
</body>
</html>