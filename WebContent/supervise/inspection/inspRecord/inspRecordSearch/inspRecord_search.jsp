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
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCaseSearch/css/case.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<style>
.panList{padding-top:20px;position: absolute;top:34px;left:0px;z-index: 999;display: none;width:600px;height:250px;text-align: left;border:1px solid #ccc;background-color: #fff;}

</style>
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
<div id="divSearch" name="divSearch" style="width:100%">
    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form">
		<input type="hidden" id="id" name = "id" value=""/>
		<div class="easyui-panel" style="width: 100%;" align="center" border="false;">
                <table class="TableBlock_page" fit="true" style="width: 100%;"  align="center">
                	<tr class="common-tr-border">
                        <td class="case-common-td-class1">执法机关：</td>
                        <td class="case-common-td-class2">
                        	<input id="departmentId" name="departmentId" prompt="请选择"  data-options="panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
                    </tr>
                	<tr class="common-tr-border">
                        <td class="case-common-td-class1">执法主体：</td>
                        <td class="case-common-td-class2">
                        	<input id="subjectId" name="subjectId" prompt="请选择"  data-options="panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">行政区划：</td>
                        <td class="case-common-td-class2">
                        	<input id="area" name="area" prompt="请选择"  data-options="panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">所属领域：</td>
                        <td class="case-common-td-class2">
                        	<input id="orgSys" name="orgSys" prompt="请选择"  data-options="panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">当事人类型：</td>
                        <td class="case-common-td-class2" id="partyType">
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1" style="width:150px">当事人名称：</td>
                        <td class="case-common-td-class2" >
                            <input id="partyName" name="partyName" class="easyui-textbox" data-options="validType:'length[0,200]'" style="width:300px"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">当事人证件类型：</td>
                        <td class="case-common-td-class2" id="cardType">
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1" style="width:150px">执法人员姓名：</td>
                        <td class="case-common-td-class2" >
                            <input id="officeName" name="officeName" class="easyui-textbox" data-options="validType:'length[0,200]'" style="width:300px"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1" style="width:150px">执法证号码：</td>
                        <td class="case-common-td-class2" >
                            <input id="cardCode" name="cardCode" class="easyui-textbox" data-options="validType:'length[0,30]'" style="width:300px"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">检查结果：</td>
                        <td class="case-common-td-class2" >
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isInspectionPass" id="isInspectionPass1" value="01" labelWidth="100px" label="合格"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isInspectionPass" id="isInspectionPass2" value="02" labelWidth="100px" label="不合格"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">行政行为：</td>
                        <td class="case-common-td-class2" id="act">
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">检查日期：</td>
                        <td class="case-common-td-class2" >
	                    	<input  class="easyui-datebox" id='inspectionDateBeginStr'  name='inspectionDateBeginStr' style="width:130px;" data-options="editable:false,panelHeight:'auto'" />
	                    	&nbsp;&nbsp;至&nbsp;&nbsp;
	                    	<input  class="easyui-datebox" id='inspectionDateEndStr'  name='inspectionDateEndStr' style="width:130px;" data-options="editable:false,panelHeight:'auto'" />
                        </td>
                    </tr>
                    <!-- <tr class="common-tr-border">
                        <td class="case-common-td-class1">检查单：</td>
                        <td class="case-common-td-class2">
                        	<input id="inspectionNumber" name="inspectionNumber" prompt="请选择"  data-options="panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">检查项：</td>
                        <td class="case-common-td-class2">
                        	<input id="inspectionItem" name="inspectionItem" prompt="请选择"  data-options="panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
                    </tr> -->
                </table>
            </div>
        <div style="text-align: center; width: 100%;height: 50px;"></div>
        <div class="easyui-panel" style="text-align: center; padding-top: 10px;" id="buttonDiv">
                <button class="easyui-linkbutton" title="查询" onclick="doFilingSave();return false;" id="btn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查询</span>
                </button>
                <button class="easyui-linkbutton" title="重置" onclick="refresh()" id="rebtn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-refresh"></i> 重置</span>
                </button>
        </div>
    </form>
</div>
<div id="divIndex" align="center" name="divIndex" style="display:none;width:100%;height:100%" >
	<div id="toolbar" class="titlebar clearfix">
       <div id="outwarp">
        <div class="fl left">
            <img id="img1" class='title_img'
                src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png" />
            <span class="title">行政检查综合查询</span>
        </div>
        <div class="fr">
			<button class="easyui-linkbutton" onclick="exportDept()"><i class="fa fa-download"></i>&nbsp;导&nbsp;出</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="back()"><i class="fa fa-reply"></i>&nbsp;返&nbsp;回</button>
		</div>
		</div>
				<span class="basic_border"></span>
				<div class="" style="padding-top: 5px;display:inline-block;">
                    <span style="float:left;height: 26px;line-height: 26px;margin:4px;">&nbsp;已选条件：</span>
                    <span id="condition"></span>
                </div>
                <div id="optionCom" >
                    <span class="isshow"><i class="optional fa fa-bars fa-lg"></i>可选列  </span> 
                    <span class="tip">可根据需要"隐藏/显示"所选列</span> 
                    <ul class="panList"></ul>
                </div>
    </div>
    <table id="datagrid" fit="true" ></table>
    <div id="toolbar" class="titlebar clearfix"></div>
</div>
<script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspRecord/inspRecordSearch/js/inspRecord_search.js"></script>
</body>
</html>