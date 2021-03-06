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
		<div class="easyui-panel" title="基本信息" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 99%;"  align="center">
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1" style="width:150px">主体名称：</td>
                        <td class="case-common-td-class2" >
                            <input class="easyui-textbox" data-options="validType:'length[0,300]'" id="subName" name="subName" style="width:300px"/>
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">所属地区：</td>
                        <td class="case-common-td-class2">
                        	<input name="area" id="area" prompt="请选择"  data-options="editable:false,panelHeight:'auto'" style="width:300px" class="easyui-textbox" />
                        </td>
<!--                         <td class="case-common-td-class1"></td> -->
<!--                         <td class="case-common-td-class1"></td> -->
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">主体层级：</td>
                        <td class="case-common-td-class2" id="subLevel">
                        </td>
                    </tr>
<!--                     <tr class="common-tr-border"> -->
<!--                         <td class="case-common-td-class1">职权类别：</td> -->
<!--                         <td class="case-common-td-class2" id="subjectPower"> -->
<!--                         </td> -->
<!--                     </tr> -->
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">所属领域：</td>
                        <td class="case-common-td-class2">
                            <input name="orgSys" id="orgSys" prompt="请选择" style="width:300px" data-options="editable:false,panelHeight:'auto',multiple:true" class="easyui-combobox" />
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">主体性质：</td>
                        <td class="case-common-td-class2" id="nature" >
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">统一社会信用代码：</td>
                        <td class="case-common-td-class2">
                            <input class="easyui-textbox" data-options="validType:'length[0,18]'" style="width:300px" id="code" name="code" />
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">执法人员数量：</td>
                        <td class="case-common-td-class2">
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
	                        <br />
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="personNo" id="personNo6" value="6" label="自定义"/>
	                        <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="powerNoStart" name="powerNoStart" style="width:80px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="powerNoEnd" name="powerNoEnd" style="width:80px"/>
                        
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">委托组织数量：</td>
                        <td class="case-common-td-class2">
                            <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="orgNo" id="orgNo1" value="1" labelWidth="100px" label="无"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="orgNo" id="orgNo2" value="2" labelWidth="100px" label="1-3"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="orgNo" id="orgNo3" value="3" labelWidth="100px" label="4-7"/>
	                        <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="orgNo" id="orgNo4" value="4" labelWidth="100px" label="大于7"/>
                            <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="personNo" id="personNo6" value="6" label="自定义"/>
	                        <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="powerNoStart" name="powerNoStart" style="width:80px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="powerNoEnd" name="powerNoEnd" style="width:80px"/>
                        
                        </td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">是否已分配账号：</td>
                        <td class="case-common-td-class2">
                        	<input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
	                            name="isUserStr" id="isUserStr1" value="1" labelWidth="100px" label="是"/>
                            <input style="width: 15px; height: 15px;" class="easyui-checkbox" labelAlign="left" labelPosition="after" 
                            	name="isUserStr" id="isUserStr0" value="0" labelWidth="100px" label="否"/>
                        </td>
                    </tr>
                </table>
            </div>
            <br />
            <div class="easyui-panel" title="职权信息" style="width: 100%;" align="center">
                <table class="TableBlock_page" fit="true" style="width: 99%;"  align="center">
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">职权总数：</td>
                        <td class="case-common-td-class2">
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="powerNoStart" name="powerNoStart" style="width:80px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="powerNoEnd" name="powerNoEnd" style="width:80px"/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">行政处罚职权数量：</td>
                        <td class="case-common-td-class2">
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="punishNoStart" name="punishNoStart" style="width:80px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="punishNoEnd" name="punishNoEnd" style="width:80px"/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">行政检查职权数量：</td>
                        <td class="case-common-td-class2">
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="inspectNoStart" name="inspectNoStart" style="width:80px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="inspectNoEnd" name="inspectNoEnd" style="width:80px"/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">行政许可职权数量：</td>
                        <td class="case-common-td-class2">
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="permitNoStart" name="permitNoStart" style="width:80px"/>&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="permitNoEnd" name="permitNoEnd" style="width:80px"/>
                        </td>
                        <td class="case-common-td-class1"></td>
                    </tr>
                    <tr class="common-tr-border">
                        <td class="case-common-td-class1">行政强制职权数量：</td>
                        <td class="case-common-td-class2">
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="forceNoStart" name="forceNoStart" style="width:80px" />&nbsp;&nbsp;至&nbsp;&nbsp;
                            <input class="easyui-textbox" data-options="validType:'length[0,6]'" id="forceNoEnd" name="forceNoEnd" style="width:80px" />
                        </td>
                        <td class="case-common-td-class1"></td>
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
            <span class="title">执法主体综合查询</span>
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
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/subjectSearch/js/subject_search.js"></script>
</body>
</html>