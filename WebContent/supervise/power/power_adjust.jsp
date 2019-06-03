<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.*"%>
<%@page import="com.tianee.oa.core.workflow.flowrun.bean.FlowRunToken"%>
<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/power/js/power_adjust.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
<script type="text/javascript">
<% 
String tokenInfo = "";
JSONObject json = new JSONObject();
FlowRunToken token = null;
String prcsName = "";
if(TeeStringUtil.getString(request.getParameter("token"), "") != "") {
    token = new FlowRunToken(request);

    json.put("primaryId", token.getVars().get("PRIMARY_ID"));
    json.put("runId", token.getRunId());
    json.put("frpSid", token.getFrpSid());
    json.put("flowId", token.getFlowId());
    json.put("goBack", token.getGoBack());
    json.put("prcsId", token.getPrcsId());
    json.put("isFinished", token.isFinished());
    json.put("prcsName", token.getPrcsName());
    
    prcsName = token.getPrcsName(); 
    
    if (token.isPrcsUser()) {
        if(token.isFinished()) {
            json.put("readOnly", true);
        } else {
            json.put("readOnly", false);
        }
    } else {
        json.put("readOnly", true);
    }
    tokenInfo = json.toString();
} else {
    prcsName = "未开始审核";
}
%>
</script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />


<title>职权变更单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()" style="font-size:14px;">
    <input type="hidden" id="token" name="token" value='<%=tokenInfo %>' />
    <div class="easyui-layout" style="width: 100%; height: 100%;">
        <div data-options="region:'north'" style="height:200px; padding-top: 5px;">
            <div class="titlebar clearfix">
                <div class="fl left" style="padding-right: 10px; padding-left: 10px;">
                    <img id="img1" class='title_img'
                        src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">职权变更单</span>
                </div>
                <div class="fr" style="padding-right: 10px;">
                    <button id="saveTache" style="display: none;" class="easyui-linkbutton" onclick="saveTache();"><i class="fa fa-save"></i> 保存</button>
                    &nbsp;&nbsp;
                    <button id="turnNext" style="display: none;" class="easyui-linkbutton" onclick="turnNext();"><i class="fa fa-mail-forward "></i> 发送</button>
                    <button id="closedFlow" style="display: none;" class="easyui-linkbutton" onclick="closedFlow();"><i class="fa fa-check"></i> 办结</button>
                    &nbsp;&nbsp;
                    <button id="turnBack" style="display: none;" class="easyui-linkbutton" onclick="turnBack();"><i class="fa fa-mail-reply"></i> 退回</button>
                    &nbsp;&nbsp;
                    <button id="returnSubmit" style="display: none;" class="easyui-linkbutton" onclick="returnSubmitPage();"><i class="fa fa-mail-reply"></i> 返回列表</button>
                    &nbsp;&nbsp;
                    <button id="submitPower" style="display: none;" class="easyui-linkbutton" onclick="doSubmitPower();"><i class="fa fa-check "></i> 提交审批</button>
                </div>
                <span class="basic_border"></span> 
                <div class="" style="padding-top: 5px; padding-bottom: 5px">
                    <!-- form表单 -->
                    <form id="adjustForm">
                        <input type="hidden" id="baseId" name="baseId" value="<c:out value='${adjustModel.id}'/>"/>
                        <input type="hidden" id="selectedPowerId" name="selectedPowerId" value="<c:out value='${selectedPowerId}'/>"/>
                        <input type="hidden" id="adjust" name="adjust" value="<c:out value='${adjust}'/>"/>
                        <input type="hidden" id="departmentId" name="departmentId" value="<c:out value='${adjustModel.departmentId}'/>"/>
                        <table class="TableBlock" style="width: 100%; background: #fff;">
                            <tr>
                                <td class="power-table-label">变更批次号：</td>
                                <td class="power-table-medium-label" name="batchCode">
                                    <c:out value='${adjustModel.batchCode}'/>
                                </td>
                                <td class="power-table-label">变更职权总数：</td>
                                <td colspan='3' name="examineSum">
                                    <c:out value='${adjustModel.examineSum}'/>
                                </td>
                            </tr>
                            <tr>
                                <td class="power-table-label">办理人：</td>
                                <td class="power-table-medium-label" name="userName">
                                    <c:out value='${adjustModel.userName}'/>
                                </td>
                                <td class="power-table-label">提交部门：</td>
                                <td class="power-table-medium-label" name="departmentName">
                                    <c:out value='${adjustModel.departmentName}'/>
                                </td>
                                <td class="power-table-label">创建日期：</td>
                                <td class="power-table-medium-label" name="createDateStr"><c:out value='${adjustModel.createDateStr}'/></td>
                            </tr>
                            <tr>
                                <td class="power-table-label">变更原因：</td>
                                <td colspan='5' name="adjustReasonShow">
                                    <textarea rows="3" class="power-textarea" id="adjustReason" name="adjustReason" style="width: 99%; display: none;" type="text"
                                            newMaxLength="1000"></textarea>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <div data-options="region:'east',split:true,collapsible:false" title="审核记录" style="width: 30%">
            <table class="TableBlock" style="width: 100%; background: #fff;">
                <input type="hidden" name="tacheId" id="tacheId" />
                <input type="hidden" name="examinePersonId" id="examinePersonId" />
                <tr>
                    <td class="power-table-sm-label">审核阶段：</td>
                    <td class="power-table-medium-label" name="tacheName"></td>
                </tr>
                <tr>
                    <td class="power-table-sm-label">审核人：</td>
                    <td class="power-table-medium-label" name="examinePersonName"></td>
                </tr>
                <tr>
                    <td class="power-table-sm-label">审核日期：</td>
                    <td class="power-table-medium-label"  name="examineDateStr"></td>
                </tr>
                <tr>
                    <td class="power-table-sm-label" valign="top">审核意见：</td>
                    <td class="power-table-medium-label">
                        <textarea rows="15" class="power-textarea"  disabled="disabled"
                                id="examineView" name="examineView" style="width: 90%;" type="text"
                                newMaxLength="1000"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right; padding-right:15px;">
                        <a href="javascript: void(0);" onclick="showExamineHis()">查看历史审核记录</a>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'west',split:true,collapsible:false" title="待审核职权列表" style="width: 70%;">
            <table id="datagrid" fit="true"></table>
        </div>
    </div>
</body>
</html>