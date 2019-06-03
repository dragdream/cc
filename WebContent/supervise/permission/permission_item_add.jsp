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
</head>
<body onload="doInit();">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
        <div class="easyui-tabs" style="width:100%;height:500px;">
        <div id="bodyDiv" title="基本信息">
            <input type="hidden" id="id" name = "id" value="${param.id }"/>
            <table class="TableBlock" frame="void" rules="none" id="tableFirst" >
                <tr class="tr-title1"></tr>
                <tr>
                    <td class="td-title1" >事项名称<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2">
                        <input class="easyui-textbox" id="name" name="name" value="" style="width: 250px;"
                            data-options="validType:'length[0,200]',required:true, missingMessage:'请输入事项名称'" />
                    </td>
                    <td class="td-title1">许可主体<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2">
                        <input class="easyui-combobox" id="subjectId" name="subjectId" style="width: 250px;"
                            data-options="novalidate:true,validType:'length[0,300]',required:true, missingMessage:'请选择许可主体'"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1">办件类型<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2">
                        <input class="easyui-combobox" id="listType" name="listType" style="width: 250px;"
                            data-options="novalidate:true,required:true,panelHeight:'auto', missingMessage:'请选择办件类型'"/>
                    </td>
                    <td class="td-title1"></td>
                    <td class="td-title2"></td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1">法定办结时限<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2" nowrap="nowrap">
                        <input name="statutoryTimeLimit" id="statutoryTimeLimit" value="" class="easyui-textbox" style="width:250px;height:30px;"
                            data-options="novalidate:true,required:true,validType:['number','length[0,4]'], missingMessage:'请输入法定办结时限'"/>
                        <!-- <span style="position: absolute;z-index:999;top:150px;left:390px;">天</span> -->
                    </td>
                    <td class="td-title1">承诺办结时限<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2">
                        <input class="easyui-textbox" id="promisedTimeLimit" name="promisedTimeLimit" 
                            data-options="novalidate:true,required:true,validType:['number','length[0,4]'], missingMessage:'请输入承诺办结时限'" style="width: 250px;" />
                        <!-- <span style="position: absolute;z-index:999;top:150px;left:805px;">天</span> -->
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr>
                    <td class="td-title1">服务对象<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2">
                        <input class="easyui-combobox" id="partyType" name="partyType"
                            data-options="novalidate:true,required:true, missingMessage:'请选择服务对象',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title1">办理形式<span class="required">*</span>：&nbsp;</td>
                    <td class="td-title2">
                        <input class="easyui-combobox" id="handleWay" name="handleWay"   
                            data-options="novalidate:true,required:true, missingMessage:'请选择办理形式',panelHeight:'auto'" style="width: 250px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr class="tr-title1"></tr>
                <tr>
                    <td class="td-title1">申请材料&nbsp;：&nbsp;</td>
                    <td  colspan="3" nowrap="nowrap">
                        <input class="easyui-textbox" name="applicationMaterials" id="applicationMaterials" data-options="multiline:true,validType:'length[0,500]',required:false"
                            style="width:660px;height:60px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                <tr class="tr-title2">
                    <td class="td-title1">是否收费&nbsp;：&nbsp;</td>
                    <td class="td-title2">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="60" class="easyui-radiobutton" 
                                name="isCollectFees" id="isCollectFees1" value="1" label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="60" class="radiobutton"
                                name="isCollectFees" id="isCollectFees0" value="0" label="否"/>
                    </td>
                    <td class="td-title1"></td>
                    <td class="td-title2"></td>
                    <td class="td-title4"></td>
                </tr>
                <tbody id="isCollectFees_tbody" style="display: none;">
                <tr>
                    <td class="td-title1">收费标准&nbsp;：&nbsp;</td>
                    <td colspan="3" nowrap="nowrap">
                        <input class="easyui-textbox" name="collectFeesRates" id="collectFeesRates" data-options="multiline:true,validType:'length[0,500]',required:false"
                            style="width:660px;height:60px;"/>
                    </td>
                    <td class="td-title4"></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="power" title="关联职权及依据" style="padding:10px">
            <div id="setting_panel" class="easyui-panel" title="许可职权" style="width:100%; height:210px;" data-options="tools:'#settingTools'" >
                <div id="settingTools">
                    <a onclick="findPower()" id="settingDatagrid_add_btn" title="添加许可职权"><i class="fa fa-plus"></i></a>
                </div>
                <table id="powerDatagrid" fit="true"></table>
            </div>
            <br />
            <div id="gist_panel" class="easyui-panel" title="设定依据" style="width:100%;height:210px;" >
                <table id="settingDatagrid" fit="true"></table>
            </div>
        </div>
        </div>
    </form>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/permission/js/permission_item_add.js"></script>
</body>
</html>