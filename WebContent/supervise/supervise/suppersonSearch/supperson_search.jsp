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
                        <td class="case-common-td-class1" style="width:150px">姓名：</td>
                        <td class="case-common-td-class2" >
                            <input id="name" name="name" class="easyui-textbox" data-options="validType:'length[0,50]'" style="width:300px"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">性别：</td>
                        <td class="case-common-td-class2" >
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="sex" id="sex01" value="01" labelWidth="100px" label="男"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="sex" id="sex02" value="02" labelWidth="100px" label="女"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">政治面貌：</td>
                        <td class="case-common-td-class2" >
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="politive" id="politive1" value="1" labelWidth="100px" label="中共党员"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="politive" id="politive2" value="2" labelWidth="100px" label="非中共"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">民族：</td>
                        <td class="case-common-td-class2" >
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="nation" id="nation0" value="0" labelWidth="100px" label="汉族"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="nation" id="nation1" value="1" labelWidth="100px" label="少数民族"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">最高学历：</td>
                        <td class="case-common-td-class2" id="education">
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">职级：</td>
                        <td class="case-common-td-class2" >
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="jobClass" id="jobClassa" value="a" labelWidth="100px" label="办事员"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="jobClass" id="jobClassb" value="b" labelWidth="100px" label="科员"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="jobClass" id="jobClassc" value="c" labelWidth="100px" label="科级"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="jobClass" id="jobClassd" value="d" labelWidth="100px" label="处级"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="jobClass" id="jobClasse" value="e" labelWidth="100px" label="厅局级及以上"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="jobClass" id="jobClassf" value="f" labelWidth="100px" label="其他"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">是否取得过执法证：</td>
                        <td class="case-common-td-class2">
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isGetcodeStr" id="isGetcodeStr1" value="1" labelWidth="100px" label="是"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isGetcodeStr" id="isGetcodeStr0" value="0" labelWidth="100px" label="否"/>
<!-- 	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after"  -->
<!-- 	                            name="isGetcodeStr" id="isGetcode2" value="2" labelWidth="100px" label="未知"/> -->
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">是否公职律师：</td>
                        <td class="case-common-td-class2">
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isLawyerStr" id="isLawyerStr1" value="1" labelWidth="100px" label="是"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isLawyerStr" id="isLawyerStr0" value="0" labelWidth="100px" label="否"/>
                        </td>
                    </tr>
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
            <span class="title">监督人员综合查询</span>
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
<script type="text/javascript" src="<%=contextPath%>/supervise/supervise/suppersonSearch/js/supperson_search.js"></script>
</body>
</html>