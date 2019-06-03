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
<link rel="stylesheet" type="text/css" href="/supervise/officials/officialsSearch/css/css.css" />
<!-- <style>
 .panList{padding-top:20px;position: absolute;top:30px;z-index: 999;left:0px;display: none;width:600px;height:250px;text-align: left;border:1px solid #ccc;background-color: #fff;}
.case-common-td-class2{
    text-align: left!important; 
    width: 200px!important;
    height: 40px!important;
}
.textbox-label{
	width: auto;
}
.textbox-label-a label{
	margin-right: 20px;
}
.tagbox-label{
    margin: 5px;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
    background-color: #f5f5f5;
    height: 26px; 
    line-height: 26px;
    padding-left:8px;
}
</style> -->
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px; overflow-x:hidden">
    <div id = "searchPage">
    <form method="post" id="officials_search_form" class="easyui-form" style="width: 100%;" data-options="novalidate:true">
<%--         <input type="hidden" id="comm_case_add_filing_caseId" value="${caseId}"/>
        <input type="hidden" id="common_case_add_filing_editFlag" value="${editFlag}"/>
        <input type="hidden" id="common_case_add_filing_isNext" value="${isNext}"/>
        <input type="hidden" id="common_case_add_filing_modelId" value="${modelId}"/>
        <input type="hidden" id="common_case_add_filing_subjectId" value="${cbModel.subjectId}"/> --%>
        <!-- <div align="center"  style="width: 99%;height:33px;line-height: 33px;padding: 0 12px;" ><span style="font-size:15px;" class="if_title fl">基本信息</span></div> -->
        <div class="easyui-panel" title="基本信息" style="width: 99%;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 99%;" align="center" >
                <tr class="common-tr-border"> </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">姓名：</td>
                    <td class="case-common-td-class2" colspan="2">
                        <input class="easyui-textbox" data-options="validType:'length[0,50]'" name="name" id="name" style="width: 300px;" />
                    </td>
                    <td class="case-common-td-class1" ></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">性别：</td>
                    <td class="case-common-td-class2" nowrap id="citizenSex_td" colspan="3">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="sex" id="sex1" value="01" label="男" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="sex" id="sex2" value="02" label="女"/>
                    </td>
                </tr>
                <!-- 第三行 -->
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">政治面貌：</td>
                    <td class="case-common-td-class2" nowrap id="politive" colspan="3">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="politive" id="politive1" value="01" label="中共党员" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="politive" id="politive2" value="02,03,04" label="非中共"/>
                    </td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">民族：</td>
                    <td class="case-common-td-class2" nowrap id="nation" colspan="3">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="nation" id="nation1" value="01" label="汉族" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="nation" id="nation2" value="02" label="少数民族"/>
                    </td>
                </tr>
                <tr class="common-tr-border"> 
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">学历：</td>
                    <td class="case-common-td-class2" nowrap id="education" colspan="3">
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="education" id="education1" value="01,02" label="研究生" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="education" id="education2" value="03" label="大学"/></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="education" id="education3" value="04" label="大专" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="education" id="education4" value="05" label="高中"/></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="education" id="education5" value="06" label="初中" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px; display:inline-block" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="education" id="education6" value="99" label="其他"/></div>
                    </td>
                </tr>
                <tr class="common-tr-border"> 
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">职级：</td>
                    <td class="case-common-td-class2" nowrap id="jobClass" colspan="3" >
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="jobClass" id="job1" value="10" label="办事员" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="jobClass" id="job2" value="09" label="科员"/></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="jobClass" id="job3" value="07,08" label="科级" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="jobClass" id="job4" value="05,06" label="处级"/></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="jobClass" id="job5" value="03,04" label="厅局级及以上" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="jobClass" id="job6" value="99" label="其他"/></div>
                    </td>
                </tr>
                <tr class="common-tr-border"> 
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">执法证件类型：</td>
                    <td class="case-common-td-class2 " nowrap id="codeType" colspan="3">
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="codeType" id="codeType1" value="001" label="仅地方证件" /></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="codeType" id="codeType2" value="002" label="仅行业证件"/></div>
                        <div style="display:inline-block"><input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="codeType" id="codeType3" value="000" label="双证"/></div>
                    </td>
                </tr>
                <tr class="common-tr-border"> 
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">是否已分配账号：</td>
                    <td class="case-common-td-class2 " nowrap id="isGetCode" colspan="3">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox" 
                            name="isGetCode" id="isGetCode1" value="1" label="是" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-checkbox"
                            name="isGetCode" id="isGetCode2" value="0" label="否"/>
                    </td>
                    <!-- 
                    <td class="case-common-td-class1" colspan="2"></td> -->
                </tr>
                <tr class="common-tr-border"> </tr>
            </table>
        </div>
        <div class="easyui-panel" style="width: 99%; height:15px; border:none" align="center"></div>
        <div class="easyui-panel" title="办案信息" style="width: 99%;" align="center">
            <table class="TableBlock_page" fit="true" style="width: 99%;" align="center">
                <tr class="common-tr-border"> </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1"></td>
                    <td class="case-common-td-class1">案件年份：</td>
                    <td class="case-common-td-class3" colspan="3" id="year">
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-radiobutton" 
                             checked="checked" name="year" id="year1" value="1" label="本年度" />
                        <input style="width: 15px; height: 15px;" labelAlign="left" labelPosition="after" labelWidth="120" class="easyui-radiobutton"
                            name="year" id="year2" value="2" label="上年度"/>
                           <span style="font-size:12px">&nbsp;&nbsp; 注：案件年份指行政检查日期、行政处罚立案日期、行政强制批准日期所属年份</span>
                    </td>
                    
                </tr>
                <!-- 第二行 -->
                <tr class="common-tr-border">
                    <td class="case-common-td-class1" colspan="2">经办案件量(行政检查)：</td>
                    <td class="case-common-td-class2" colspan="2">
                        <input name="beginJcl" id="beginInspNum" 
                            class="easyui-textbox" style="width:15%;height:30px;"/>
                        &nbsp;-&nbsp;
                        <input name="endJcl" id="endInspNum" 
                            class="easyui-textbox" style="width:15%;height:30px;"/>
                    </td>
                    <td class="case-common-td-class1" ></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1" colspan="2">经办案件量(行政处罚)：</td>
                    <td class="case-common-td-class2" colspan="2">
                        <input name="beginCfl" id="beginCaseNum" 
                            class="easyui-textbox" style="width:15%;height:30px;"/>
                        &nbsp;-&nbsp;
                        <input name="endCfl" id="endCaseNum" 
                            class="easyui-textbox" style="width:15%;height:30px;"/>
                    </td>
                    <td class="case-common-td-class1"></td>
                </tr>
                <tr class="common-tr-border">
                    <td class="case-common-td-class1" colspan="2">经办案件量(行政强制)：</td>
                    <td class="case-common-td-class2" colspan="2">
                        <input name="beginQzl" id="beginCoercionNum" 
                            class="easyui-textbox" style="width:15%;height:30px;"/>
                        &nbsp;-&nbsp;
                        <input name="endQzl" id="endCoercionNum" 
                            class="easyui-textbox" style="width:15%;height:30px;"/>
                    </td>
                    <td class="case-common-td-class1" ></td>
                </tr> 
            </table>
        </div>
        <br/>
        <div style="text-align: center; padding-top: 10px;">
                <button class="easyui-linkbutton"  onclick="officialsSearch();return false;" id="btn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-search"></i> 查询</span>
                </button>
                <button class="easyui-linkbutton" onclick="officialsReSet();return false;" id="btn">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-trash-o"></i> 重置</span>
                </button>
        </div>
        <div style="height:20px"></div>
    </form>
    </div>

    <!-- 查询页 -->
    <div id="indexPage"  style="height:99%;width:100%;display:none" >

        <div id="toolbar" class="titlebar clearfix">
                <div id="outwarp">
                    <div class="fl left">
                        <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                        <span class="title">执法人员综合查询</span>
                    </div>
                    <div class="fr">
                        <button class="easyui-linkbutton" onclick="officialsExport()"><i class="fa fa-download"></i>&nbsp;导&nbsp;出</button>
                        <button class="easyui-linkbutton" onclick="officialsReturn(0)"><i class="fa fa-reply"></i>&nbsp;返&nbsp;回</button>
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
                    <ul class="panList" ></ul>
                </div>
            </div>
     
          <table fit="true"  id="datagrid" ></table>
     </div>
 <script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/officials/officialsSearch/js/officials_search.js"></script>
</body>

</html>