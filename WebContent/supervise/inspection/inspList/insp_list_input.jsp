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
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/inspection/inspRecord/css/inspection.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body onload="doInit()">
    <div id="inspectionItem_input_panel" style="width: 100%; padding-top: 10px">
        <input type="hidden" id="listId" value="${param.id }"/>
        <input type="hidden" id="loginSubId" value="${param.loginSubId}"/>
        <input type="hidden" id="loginDeptId" value="${param.loginDeptId}"/> 
        <input type="hidden" id="orgSys" value="${param.orgSys}" />
        <div class="easyui-tabs" id="ins_list_tabs" style="width: 100%; padding-right: 10px; padding-left: 10px; padding-top: 5px">
            <div id="" title="基础信息">
                <form role="form" id="inspection_list_input_form" name="form1" enctype="multipart/form-data" method="post" data-options="novalidate:true">
                    <table class="TableBlock_page " style="table-layout: fixed; width: 95%; margin: 5px 0px; ">
                        <tr class="none-border-tr">
                            <td class="insp-td-right3" >统一社会信用代码<span class="required">*</span>：</td>
                            <td class="insp-td-left2" ><input type="text" class="easyui-textbox" style="width: 100%;" id='code' name='code' disabled="disabled"
                                data-options="validType:'length[0,120]', novalidate:true,required:true, missingMessage:'请数入检查单模版名称'"/></td>
                            <td class="insp-td-right2">行使层级<span class="required">*</span>：</td>
                            <td class="insp-td-left2"><input class="easyui-textbox" style="width: 100%;" disabled="disabled"
                                id="applyHierarchy" name="applyHierarchyStr" 
                                data-options="validType:'length[0,60]', novalidate:true, required:true, missingMessage:'请选择适用层级'"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">事项名称<span class="required">*</span>：</td>
                            <td class="insp-td-left2"><input class="easyui-textbox" style="width: 100%;" id="listName" name="listName"
                                data-options="validType:'length[0,60]', novalidate:true, required:true, missingMessage:'请填写事项名称'"/></td>
                            <td class="insp-td-right2">实施清单编码&nbsp;：</td>
                            <td class="insp-td-left2" ><input class="easyui-textbox" style="width: 100%;"id="inspListCode" name="inspListCode" /></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">检查类别<span class="required">*</span>：</td>
                            <td class="insp-td-left2" ><input class="easyui-combobox" style="width: 100%;" id="inspType" name="inspType"
                                data-options="validType:'length[0,60]', novalidate:true,required:true, missingMessage:'请选择检查单分类'"/></td>
                            <td class="insp-td-right2">检查方式<span class="required">*</span>：</td>
                            <td class="insp-td-left2" ><input class="easyui-combobox" style="width: 100%;"
                                id="inspWay" name="inspWay" 
                                data-options="novalidate:true,required:true, missingMessage:'请选择检查方式'"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">是否纳入双随机<span class="required">*</span>：</td>
                            <td class="insp-td-left2" id="isDoubleRandomTd"></td>
                            <td class="insp-td-right2">是否抽查<span class="required">*</span>：</td>
                            <td class="insp-td-left2" id="isSpotCheckTd"></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr" style="display: none" id="doubleRandomCascade">
                            <td class="insp-td-right3">检查比例&nbsp;：</td>
                            <td class="insp-td-left2" ><input class="easyui-textbox" style="width: 100%;"
                                id="inspProportion" name="inspProportion"
                                data-options="validType:'length[0,60]'"/></td>
                            <td class="insp-td-right2">检查频次&nbsp;：</td>
                            <td class="insp-td-left2" ><input class="easyui-textbox" style="width: 100%;"
                                id="inspFrequency" name="inspFrequency"
                                data-options="validType:'length[0,60]', novalidate:true"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">是否协同<span class="required">*</span>：</td>
                            <td class="insp-td-left2" id="isSynergyTd"></td>
                            <td class="insp-td-right2 synergy-cascade" style="display: none">协同相关机构<span class="required">*</span>：</td>
                            <td class="insp-td-left2 synergy-cascade" style="display: none"><input class="easyui-textbox" style="width: 100%;"
                                id="synergyOrgan" name="synergyOrgan"
                                data-options="validType:'length[0,60]', novalidate:true,"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">事项状态<span class="required">*</span>：</td>
                            <td class="insp-td-left2" id="currentStateTd"></td>
                            <td class="insp-td-right2">事项版本&nbsp;：</td>
                            <td class="insp-td-left2"><input class="easyui-textbox" style="width: 100%;"
                                id="version" name="version" data-options="validType:'length[0,2]', novalidate:true,"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">计划生效时间&nbsp;：</td>
                            <td class="insp-td-left2" ><input class="easyui-datebox" style="width: 100%;" id="planEffectDateStr" name="planEffectDateStr"
                                data-options="validType:'date', novalidate:true"/></td>
                            <td class="insp-td-right2">计划取消时间&nbsp;：</td>
                            <td class="insp-td-left2" ><input class="easyui-datebox" style="width: 100%;" id="planCancelDateStr" name="planCancelDateStr"
                                data-options="validType:'date', novalidate:true"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">监管抽检内容&nbsp;：</td>
                            <td class="insp-td-left7" colspan="3"><input class="easyui-textbox" id="superviseCheckContent" name="superviseCheckContent" style="width: 100%;"
                                data-options="validType:'length[0,60]', novalidate:true"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                        <tr class="none-border-tr">
                            <td class="insp-td-right3">办理流程&nbsp;：</td>
                            <td class="insp-td-left7" colspan="3"><input class="easyui-textbox" id="handleProcess" name="handleProcess" style="width: 100%;"
                                data-options="validType:'length[0,60]', novalidate:true"/></td>
                            <td class="insp-td-left0"></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="insp_list_item" title="检查项" >
                <div class="easyui-panel" style="width: 100%; height:440px" title="检查项" align="center" data-options="tools:'#insp_item_add'">
                    <div id="insp_item_add" >
                        <a href="javascript:void(0);" class="icon-add" onclick="inspItemAdd();" ><i class="fa fa-plus"></i></a>
                    </div>
                    <table fit="true" id="inspItemDatagrid" ></table>
                </div>
            </div>
            <div id="insp_list_power" title="行政职权">
                <div id="setting_panel" class="easyui-panel" title="检查职权" style="width:100%; height:210px;" data-options="tools:'#settingTools'" >
                    <div id="settingTools">
                        <a onclick="findInspPower()" id="settingDatagrid_add_btn" title="添加检查职权"><i class="fa fa-plus"></i></a>
                    </div>
                    <table id="powerDatagrid" fit="true"></table>
                </div>
                <br />
                <div id="gist_panel" class="easyui-panel" title="设定依据" style="width:100%;height:210px;" >
                    <table id="settingDatagrid" fit="true"></table>
                </div>
            </div>
        </div>
    </div>
    <script>
    </script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspList/js/insp_list_input.js"></script>
</body>
</html>