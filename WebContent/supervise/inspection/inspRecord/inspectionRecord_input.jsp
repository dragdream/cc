<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspRecord/js/inspectionRecord_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspModule/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()">
<div class="easyui-panel tabs-header-border" style="width: 99%; overflow: auto;">
    <div id="inspRecordInput_base_panel" title="基础信息" class="easyui-panel " style="width: 100%; overflow: auto;" >
        <input type="hidden" id="recordId" value="${param.id }" />
        <input type="hidden" id="orgSysCtrl" value="${param.orgSys}" />
        <input type="hidden" id="loginSubId" value="${param.subjectId}" />
        <input type="hidden" id="loginDeptId" value="${param.departmentId}" />
        <input type="hidden" id="ctrlType" value="${param.ctrlType}" />
        <form role="form" id="inspRecordInput_base_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
            <table class="TableBlock_page" style="width: 95%; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="insp-td-right2">检查单号<span class="required">*</span>：</td>
                    <td class="insp-td-left10" style="border:0px;cellpadding:0px;padding:0;">
                        <table class="TableBlock_page">
                            <tr class="none-border-tr">
                                <td class="insp-td-left4"><input type="text" id='inspectionNumber' name='inspectionNumber' maxlength="50"
                                    data-options="validType:'length[0,50]', novalidate:true,required:true, missingMessage:'请填写检查单号' "
                                    class="easyui-textbox" style="width: 100%;" /></td>
                                <td class="insp-td-right4">检查日期<span class="required">*</span>：</td>
                                <td class="insp-td-left4" ><input type="text" class="easyui-datebox" name="inspectionDateStr" id="inspectionDateStr" onblur="checkTime()"
                                    data-options="validType:'length[0,60]', novalidate:true,required:true, missingMessage:'请填写检查日期' "
                                    style="width: 100%;" /></td>
                            </tr>
                        </table>
                    </td> 
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2">执法主体<span class="required">*</span>：</td>
                    <td class="insp-td-left10" style="border:0px;cellpadding:0px;padding:0;">
                        <table class="TableBlock_page">
                            <tr class="none-border-tr">
                                <td class="insp-td-left4"><input type="text" id='subjectId' name='subjectId' 
                                    data-options="novalidate:true,required:true, missingMessage:'请选择执法主体' "
                                    class="easyui-textbox" style="width: 100%;" /></td>
                                <td class="insp-td-right4">检查单模版<span class="required">*</span>：</td>
                                <td class="insp-td-left14" >
                                    <input data-options="novalidate:true,required:true, missingMessage:'请选择检查单模版' "
                                        id="inspListModel" name="inspListId" style="width: 100%;"/>
                                </td>
                            </tr>
                        </table>
                    </td> 
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2">检查地点<span class="required">*</span>：</td>
                    <td class="insp-td-left10" >
                        <input type="text" class="easyui-textbox" style="width: 100%;" id='inspectionAddr' name='inspectionAddr' maxlength="255" 
                         data-options="validType:'length[0,255]', novalidate:true,required:true, missingMessage:'请填写检查地点' "/></td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2">当事人类型<span class="required">*</span>：</td>
                    <td class="insp-td-left10" style="border:0px;cellpadding:0px;padding:0;">
                        <table class="TableBlock_page">
                            <tr class="none-border-tr">
                                <td class="insp-td-left4"><input name="partyType" id="partyType" class="easyui-combobox" 
                                        style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择当事人类型' "/></td>
                                <td class="insp-td-right4">当事人名称<span class="required">*</span>：</td>
                                <td class="insp-td-left4" ><input type="text" class="easyui-textbox" name="partyName" id="partyName" maxlength="200"
                                        style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', required:true, novalidate:true, missingMessage:'请填写当事人名称' "/></td>
                            </tr>
                        </table>
                    </td> 
                </tr>
                <tr class="common-tr-border">
                    <td class="insp-td-right2" >证件类型<span class="required">*</span>：</td>
                    <td class="insp-td-left10" style="border:0px;cellpadding:0px;padding:0;">
                        <table class="TableBlock_page">
                            <tr class="none-border-tr">
                                <td class="insp-td-left4" >
                                    <input name="cardType" id="cardType" class="easyui-combobox"
                                        style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请选择证件类型' "/>
                                </td>
                                <td class="insp-td-right4" >证件号码<span class="required">*</span>：</td>
                                <td class="insp-td-left4" >
                                    <input type="text" name="cardCode" id="cardCode" class="easyui-textbox" maxlength="18"
                                        style="width: 100%; height: 30px; max-width: 250px;" data-options="required:true, novalidate:true, missingMessage:'请填写证件号码' "/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr class="common-tr-border">
                    <td class="insp-td-right2">联系电话&nbsp;：</td>
                    <td class="insp-td-left10" style="border:0px;cellpadding:0px;padding:0;">
                        <table class="TableBlock_page">
                            <tr class="none-border-tr">
                                <td class="insp-td-left4">
                                    <input type="text" name="contactPhone" id="contactPhone" class="easyui-textbox" maxlength="50"
                                        style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,50]', novalidate:true "/>
                                </td>
                                <td class="insp-td-right4">住所（住址）&nbsp;：</td>
                                <td class="insp-td-left4">
                                    <input type="text" name="address" id="address" class="easyui-textbox" maxlength="200"
                                        style="width: 100%; height: 30px; max-width: 250px;" data-options="validType:'length[0,200]', novalidate:true, prompt:'具体到街道（乡镇）、小区、楼门牌号' " />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr> 
            </table>
        </form>
    </div>
    <br />
    <div class="easyui-panel" style="width: 100%;min-height:130px; height:auto;" title="检查项" align="center" id="inspRecordInput_item_panel" data-options="tools:'#inspRecordInput_item_panel_hand'" >
        <div id="inspRecordInput_item_panel_hand">
            <a href="javascript:void(0);" class="icon-add" onclick="openInspItemInput()"><i class="fa fa-plus"></i></a>
        </div>
        <table class="TableBlock" style="width: 100%; margin: 5px 0px;" id="record_items_table"></table>
    </div>
    
    <br />
    <div id="inspRecordInput_result_panel" title="检查结果" class="easyui-panel" style="width: 100%; overflow: auto;">
        <form role="form" id="inspRecordInput_result_form" name="form1" enctype="multipart/form-data" method="post">
            <table class="TableBlock_page" style="width: 95%; margin: 5px 0px;">
                <tr class="none-border-tr">
                    <td class="insp-td-right2" >检查结果<span class="required">*</span>：</td>
                    <td class="insp-td-left10" id ="isInspectionPassTd">
                    </td>
                </tr>
                <tr class="none-border-tr">
                    <td class="insp-td-right2" >结果说明&nbsp;：</td>
                    <td class="insp-td-left10"><input type="text" class="easyui-textbox" id="resultDiscribe" name="resultDiscribe" maxlength="2000"
                        data-options="multiline:true" style="width: 100%; height: 60px;"></td>
                </tr>
            </table>
        </form>
    </div>
    <br />
    <div class="easyui-panel" style="width: 100%; height: 130px;" title="执法人员" align="center"  id="insp_add_person_div" data-options="tools:'#common_case_add_person'">
        <div id="common_case_add_person">
            <a href="javascript:void(0);" class="icon-add" onclick="inspFindPerson();" ><i class="fa fa-plus"></i></a>
        </div>
        <table class="TableBlock" style="width: 100%; margin: 5px 0px;" id="insp_add_person_datagrid"></table>
    </div>
    <br />
</div>
<script>
    $(function () {
    	$('#inspectionNumber').textbox('textbox').attr('maxlength', 50);
        $('#inspectionAddr').textbox('textbox').attr('maxlength', 255);
        $('#partyName').textbox('textbox').attr('maxlength', 200);
        $('#cardCode').textbox('textbox').attr('maxlength', 18);
        $('#contactPhone').textbox('textbox').attr('maxlength', 50);
        $('#address').textbox('textbox').attr('maxlength', 100);
        $('#resultDiscribe').textbox('textbox').attr('maxlength', 2000);
    });
</script>
</body>
</html>