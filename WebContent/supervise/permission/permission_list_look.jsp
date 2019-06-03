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
    <%-- <%@ include file="/supervise/permission/permission_list_index.jsp" %> --%>
    <link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/permission/css/permission.css" />
    <title>查看</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
</head>
<body  onload="doInit();" >
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
        <div id="permission_item_div"  class="easyui-panel" style="overflow-y:auto;overflow-x:auto;border-color: #fff;">
            <input type="hidden" id="id" name = "id" value="${param.id }"/>
            <table class="TableBlock_page lookInfo-lowHeight-table" frame="void" rules="none" style="table-layout: fixed;">
                <tr>
                    <td class="td-title7 font-bold-label-td" nowrap="nowrap">办理事项：&nbsp;
                    </td>
                    <td id="itemName" name="itemName">
                    </td>
                    <td class="td-title7 font-bold-label-td">办理主体：&nbsp;</td>
                    <td class="td-title8" id="subjectName" name="subjectName">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">办件编号：&nbsp;</td>
                    <td class="td-title8" id="listCode" name="listCode">
                    </td>
                    <td class="td-title7 font-bold-label-td">审核类型：&nbsp;</td>
                    <td class="td-title8" id="permissionTypeValue" name="permissionTypeValue">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">行政相对人类型：&nbsp;</td>
                    <td class="td-title8" id="partyTypeValue" name="partyTypeValue">
                    </td>
                    <td class="td-title7 font-bold-label-td">行政相对人名称：&nbsp;</td>
                    <td class="td-title8" id="partyName" name="partyName">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">行政相对人证件类型：&nbsp;</td>
                    <td class="td-title8" id="cardTypeValue" name="cardTypeValue">
                    </td>
                    <td class="td-title7 font-bold-label-td">行政相对人证件号码：&nbsp;</td>
                    <td class="td-title8" id="cardCode" name="cardCode">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">申请日期：&nbsp;</td>
                    <td class="td-title8" name="applyDateStr" id="applyDateStr">
                    </td>
                    <td class="td-title7 font-bold-label-td">申请方式：&nbsp;</td>
                    <td class="td-title8" id="applyWayValue" name="applyWayValue">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">受理日期：&nbsp;</td>
                    <td class="td-title8" name="acceptDateStr" id="acceptDateStr">
                    </td>
                    <td class="td-title7 font-bold-label-td">受理结果：&nbsp;</td>
                    <td class="td-title8" id="acceptResultValue" name="acceptResultValue">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">决定日期：&nbsp;</td>
                    <td class="td-title8" name="decisionDateStr" id="decisionDateStr">
                    </td>
                    <td class="td-title7 font-bold-label-td">决定文书号：&nbsp;</td>
                    <td class="td-title8" id="decisionCode" name="decisionCode">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">送达日期：&nbsp;</td>
                    <td class="td-title8" name="sendDateStr" id="sendDateStr">
                    </td>
                    <td class="td-title7 font-bold-label-td">送达方式：&nbsp;</td>
                    <td class="td-title8" id="sendWayValue" name="sendWayValue">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">许可证书编号：&nbsp;</td>
                    <td class="td-title8" name="certificateCode" id="certificateCode">
                    </td>
                    <td class="td-title7 font-bold-label-td">许可证书名称：&nbsp;</td>
                    <td class="td-title8" name="certificateName" id="certificateName">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">有效期（起）：&nbsp;</td>
                    <td class="td-title8" name="beginValidDateStr" id="beginValidDateStr">
                        </td>
                    <td class="td-title7 font-bold-label-td">有效期（止）：&nbsp;</td>
                    <td class="td-title8" name="endValidDateStr" id="endValidDateStr">
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title7 font-bold-label-td">许可内容：&nbsp;</td>
                    <td  colspan="3" name="matters" id="matters">
                    </td>
                    <td class="td-title4"></td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_list_look.js"></script>
</body>
</html>