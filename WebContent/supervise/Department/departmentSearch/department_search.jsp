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
.case-common-td-class2{
    text-align: left!important; 
    width: 200px;
    height: 40px!important;
}
</style>
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px;">
<div id="divSearch" name="divSearch" style="width:100%">
    <form method="post" id="form1" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
        <div class="easyui-panel" style="width: 99%;" align="center" border="false;">
            <table class="TableBlock_page" fit="true" style="width: 100%;">
            	<tr class="common-tr-border" style="height: 20px;"></tr>
            	<tr class="common-tr-border">
<!--             		<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1">部门名称：</td>
                    <td class="case-common-td-class2" id="area_td" colspan="4" >
                        <input class="easyui-textbox" id="name" name="name" style="width: 50%;"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--             		<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1" >职权名称：</td>
                    <td class="case-common-td-class2" colspan="4">
                        <input class="easyui-textbox" id="powerName" name="powerName" style="width: 50%;"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                	<td class="case-common-td-class1" >执法主体名称：</td>
                    <td class="case-common-td-class2" colspan="4">
                        <input class="easyui-textbox" id="subName" name="subName" style="width: 50%;"/>
                    </td>
				</tr>
                <tr class="common-tr-border">
<!--             		<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1" nowrap>委托组织名称：</td>
                    <td class="case-common-td-class2" colspan="4">
                        <input class="easyui-textbox" id="orgName" name="orgName" style="width: 50%;"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1">部门地区：</td>
                    <td class="case-common-td-class2" id="area_td" colspan="4">
                        <input name="administrativeDivision" id="administrativeDivision" prompt="请选择" style="width: 50%;" data-options="editable:false,panelHeight:'auto'" class="easyui-textbox" />
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1">所属领域：</td>
                    <td class="case-common-td-class2" colspan="4">
                        <input name="orgSys" id="orgSys" prompt="请选择" style="width: 50%;" data-options="editable:false,panelHeight:'auto',multiple:true" class="easyui-combobox" />
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                	<td class="case-common-td-class1">部门性质：</td>
                    <td class="case-common-td-class2" id="nature" colspan="7">
                    </td>
               	</tr>
                <tr class="common-tr-border">
<!--                     <td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1">部门层级：</td>
                    <td class="case-common-td-class2" id="deptLevel" colspan="7">
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1" >执法人员数量：</td>
                    <td class="case-common-td-class2" colspan="5">
                    	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="personNo" id="personNo1" value="1" labelWidth="100px" label="小于10"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="personNo" id="personNo2" value="2" labelWidth="100px" label="10-50"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="personNo" id="personNo3" value="3" labelWidth="100px" label="51-100"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="personNo" id="personNo4" value="4" labelWidth="100px" label="101-200"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="personNo" id="personNo5" value="5" labelWidth="100px" label="大于200"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1" >委托组织个数：</td>
                    <td class="case-common-td-class2" colspan="5">
                    	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="orgNo" id="orgNo1" value="1" labelWidth="100px" label="无"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="orgNo" id="orgNo2" value="2" labelWidth="100px" label="1-3"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="orgNo" id="orgNo3" value="3" labelWidth="100px" label="4-7"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="orgNo" id="orgNo4" value="4" labelWidth="100px" label="大于7"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1" >职权个数：</td>
                    <td class="case-common-td-class2" nowrap id="citizenSub_td" colspan="5">
                    	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="powerNo" id="powerNo1" value="1" labelWidth="100px" label="小于100"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="powerNo" id="powerNo2" value="2" labelWidth="100px" label="101-300"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="powerNo" id="powerNo3" value="3" labelWidth="100px" label="301-500"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="powerNo" id="powerNo4" value="4" labelWidth="100px" label="501-1000"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="powerNo" id="powerNo5" value="5" labelWidth="100px" label="大于1000"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                	<td class="case-common-td-class1" >执法主体个数：</td>
                    <td class="case-common-td-class2" nowrap id="citizenSex_td" colspan="5">
                    	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="subNo" id="subNo0" value="0" labelWidth="100px" label="无主体"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="subNo" id="subNo1" value="1" labelWidth="100px" label="单主体"/>
                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            name="subNo" id="subNo2" value="2" labelWidth="100px" label="多主体"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class1" >是否垂管：</td>
                    <td class="case-common-td-class2" nowrap id="citizenSex_td" colspan="3">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" class="easyui-checkbox" 
                            name="isManubriumStr" id="isManubriumStr1" value="1" labelWidth="100px" label="是"/>
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" class="easyui-checkbox" 
                            name="isManubriumStr" id="isManubriumStr0" value="0" labelWidth="100px" label="否"/>
                    </td>
<!--                 	<td class="case-common-td-class1"></td> -->
                    <td class="case-common-td-class2"></td>
                	<td class="case-common-td-class1"></td>
                    <td class="case-common-td-class2"></td>
                </tr>
            </table>
        </div>
        <div style="text-align: center; width: 100%;height: 50px;"></div>
        <div id="buttonDiv" class="easyui-panel" style="text-align: center; padding-top: 10px;">
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
            <span class="title">执法机关综合查询</span>
        </div>
        <div class="fr">
			<button class="easyui-linkbutton" onclick="back()"><i class="fa fa-reply"></i>&nbsp;返&nbsp;回</button>
			&nbsp;&nbsp;
			<button class="easyui-linkbutton" onclick="exportDept()"><i class="fa fa-download"></i>&nbsp;导&nbsp;出</button>
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
        <table id="datagrid" fit="true"></table>
</div>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/supervise/Department/departmentSearch/js/department_search.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
</body>
</html>