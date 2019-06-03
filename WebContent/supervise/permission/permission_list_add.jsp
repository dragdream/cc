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
    <link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
    <link rel="stylesheet" type="text/css" href="/supervise/permission/css/permission.css" />
    <title>新增</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<style>
.datebox-calendar-inner {
    height: 180px;
}
</style>
</head>
<body  onload="doInit();">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
        <div id="bodyDiv"  class="easyui-panel" style="overflow-y:auto;overflow-x:auto;border-color: #fff;">
            <input type="hidden" id="id" name = "id" value="${param.id }"/>
            <input type="hidden" name="xkXdrTypeValue" id="xkXdrTypeValue" value="" />
            <table class="TableBlock" frame="void" rules="none" style="background-color: #FFFFFF;width: 920px;">
                <tr>
                    <td class="td-title9" nowrap="nowrap">办理事项<span class="required">*</span>：&nbsp;
                    </td>
                    <td nowrap="nowrap">
                        <input class="easyui-textbox" id="itemId" name="itemId" style="width: 250px;"
                            data-options="novalidate:true,required:true, missingMessage:'请选择办理事项'"  />
                    </td>
                    <td class="td-title9">办理主体<span class="required">*</span>：&nbsp;</td>
                        <td class="td-title8">
                            <input class="easyui-combobox" id="subjectId" name="subjectId" style="width: 250px;"
                            data-options="novalidate:true,required:true, missingMessage:'请选择办理主体'"  />
                        </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">办件编号<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-textbox" id="listCode" name="listCode"
                            data-options="validType:'length[0,50]',required:true, missingMessage:'请输入办件编号',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title9">审核类型<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-combobox" id="permissionType" name="permissionType" style="width: 250px;"
                            data-options="novalidate:true,required:true, missingMessage:'请选择审核类型'" />
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">行政相对人类型<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-combobox" id="partyType" name="partyType" data-options="novalidate:true,required:true, missingMessage:'请选择证件类型'" style="width: 250px;" />
                    </td>
                    <td class="td-title9">行政相对人名称<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-textbox" id="partyName" name="partyName" data-options="novalidate:true,required:true,validType:'length[0,200]'" style="width: 250px;" />
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">行政相对人证件类型<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-combobox" id="cardType" name="cardType" data-options="novalidate:true,required:true, missingMessage:'请选择证件类型'" style="width: 250px;" />
                    </td>
                    <td class="td-title9">行政相对人证件号码<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-textbox" id="cardCode" name="cardCode" data-options="novalidate:true,required:true,validType:'length[0,18]'" style="width: 250px;" />
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">申请日期<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input name="applyDateStr" id="applyDateStr" value=""
                            class="easyui-datebox" style="width:250px;height:30px;" data-options="novalidate:true,validType:'date',required:true, missingMessage:'请选择申请日期' "/>
                    </td>
                    <td class="td-title9">申请方式<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-combobox" id="applyWay" name="applyWay"
                            data-options="novalidate:true,required:true, missingMessage:'请选择申请方式',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">受理日期<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input name="acceptDateStr" id="acceptDateStr" value=""
                            class="easyui-datebox" style="width:250px;height:30px;" data-options="novalidate:true,validType:'date',required:true, missingMessage:'请选择受理日期' "/>
                    </td>
                    <td class="td-title9">受理结果<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-combobox" id="acceptResult" name="acceptResult"
                            data-options="novalidate:true,required:true, missingMessage:'请选择受理结果',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">决定日期<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input name="decisionDateStr" id="decisionDateStr" value=""
                            class="easyui-datebox" style="width:250px;height:30px;" data-options="novalidate:true,validType:'date',required:true, missingMessage:'请选择许可决定日期' "/>
                    </td>
                    <td class="td-title9">决定文书号<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-textbox" id="decisionCode" name="decisionCode"
                            data-options="validType:'length[0,100]',required:true, novalidate:true,missingMessage:'请输入许可决定文书号',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">送达日期<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input name="sendDateStr" id="sendDateStr" value=""
                            class="easyui-datebox" style="width:250px;height:30px;" data-options="novalidate:true,validType:'date',required:true, missingMessage:'请选择送达日期' "/>
                    </td>
                    <td class="td-title9">送达方式<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title8">
                        <input class="easyui-combobox" id="sendWay" name="sendWay"
                            data-options="novalidate:true,required:true, missingMessage:'请选择送达方式',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">许可证书编号&nbsp;：&nbsp;</td>
                    <td class="td-title8">
                        <input name="certificateCode" id="certificateCode" class="easyui-textbox" style="width: 250px;"
                             data-options="validType:'length[0,50]',required:false"  />
                    </td>
                    <td class="td-title9">许可证书名称&nbsp;：&nbsp;</td>
                    <td class="td-title8">
                        <input name="certificateName" id="certificateName" style="width: 250px;"
                            data-options="validType:'length[0,200]',required:false, panelHeight:'auto'" class="easyui-textbox" />
                    </td>
                </tr>
                <tr>
                    <td class="td-title9">有效期（起）&nbsp;：&nbsp;</td>
                    <td class="td-title8">
                        <input name="beginValidDateStr" id="beginValidDateStr" value="" class="easyui-datebox" onfocus="xkYxqzValidate()"
                            style="width:250px;height:30px;" data-options="validType:['date','beginDate'],novalidate:true"/>
                        </td>
                    <td class="td-title9">有效期（止）&nbsp;：&nbsp;</td>
                    <td class="td-title8">
                        <input name="endValidDateStr" id="endValidDateStr" value="" class="easyui-datebox" onfocus="xkYxqziValidate()"
                            style="width:250px;height:30px;" data-options="validType:['date','endDate'],novalidate:true"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title9">许可内容&nbsp;：&nbsp;</td>
                    <td  colspan="3" nowrap="nowrap">
                        <input class="easyui-textbox" name="matters" id="matters" data-options="validType:'length[0,500]'"
                            style="width:700px;height:30px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
            </table>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_list_add.js"></script>
</body>
</html>