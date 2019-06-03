<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<!-- <link rel="stylesheet" type="text/css" href="/supervise/caseCheck/batch/css/batch.css" /> -->
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>抽取案卷</title>
<style type="text/css">
.datebox-calendar-inner {
    height: 180px;
}
.btn-alert-blue{
    letter-spacing: 0 !important;
    text-indent: 0 !important;
}
.panel-header {
    height: 40px;
}

.panel-title {
    font-size: 18px;
    font-weight: bold;
    position: absolute;
    top: 20%;
}
.panel-icon, .panel-tool {
    position: absolute;
    top: 30%;
    height: auto;
    overflow: hidden;
}

.right button {
    opacity: 0.5 !important;
    background-color: rgb(119,119,119);
}
.1-btn {
    width: 100px;
    height: 30px;
    color: white;
    background-color: #4290db;
}
</style>
</head>
<body onload="doInit();" style="padding: 10px;background-color: #fff;">
        <div class="easyui-panel" style="width: 100%;height: 100%;" align="center" data-options="border:false">
            <input type="hidden" id="missionId" name = "missionId" value="${tempModel.id }"/>
            <div style="height: 20%;">
                <img src="<%=systemImagePath %>/upload/ceshi.png"/>
            </div>
            <div class="easyui-panel" title="抽取案卷" style="width: 100%;height: 70%;" align="center" data-options="tools:'#case_add_button'" >
                <div id="case_add_button" class="right fr t_btns">
                                <%-- <button class="easyui-linkbutton" onclick="autoChoose()" style="margin-right: 5px;color: #777; background-color: #cce5fd;border-color: #777;">
                                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-plus"></i> 智能抽选</span>
                                </button> --%>
                                <button class="easyui-linkbutton" onclick="autoChoose()" style="margin-right: 5px;">
                                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-plus"></i> 智能抽选</span>
                                </button>
                                <button class="easyui-linkbutton" onclick="manualChoose()" style="margin-right: 5px;">
                                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-check"></i> 手动抽卷</span>
                                </button>
                                <button class="easyui-linkbutton" onclick="exportAnalysis()" style="margin-right: 5px;">
                                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-reply"></i> 导出案卷抽取统计</span>
                                </button>
                                <button class="easyui-linkbutton" onclick="exportCase()">
                                    <span style="padding-right: 2px; width: 30px;"><i class="fa fa-reply"></i> 导出案卷目录</span>
                                </button>
                        </div>
                <div id="toolbar" class="titlebar clearfix">
                    <div id="outwarp">
                        <div style="margin: 5px;">
                                <div style="float: left;">
                                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" />
                                <span class="title">已选案卷</span></div>
                                <div style="float: left;width: 80%"><hr style="position: absolute;top: 20%;width: 85%;color: red" /></div>
                                <div style="float: right;">
                                <span style="padding-right: 2px; width: 30px;"><i class="fa fa-trash-o"></i> 批量删除</span>
                                </div>
                        </div>
                        
                    </div>
                    <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                    <div class="" style="padding-top: 7px; padding-bottom: 7px">
                        <!-- form表单 -->
                        <input type="hidden" id="caseType" name="caseType" value="${tempModel.caseType }"/>
                        <input type="hidden" id="common_case_index_subIdExists" value="${subIdExists}"/>
                        <input type="hidden" id="common_case_index_supDeptIdExists" value="${supDeptIdExists}"/>
                        <input type="hidden" id="common_case_index_menuGroupStrNames" name="menuNames" value='${menuGroupStrNames}'/>
                        &nbsp;案卷名称：
                        <input name="name" id="name" class="easyui-textbox" style="width: 120px; height:30px;"
                            data-options="validType:'length[0,50]'" />
                        &nbsp;&nbsp;
                        <a class="easyui-linkbutton" onclick="commonCaseSearch()"><i class="fa fa-search"></i> 查 询</a>
                    </div>
                    </form>
                </div>
                <table id="common_case_index_datagrid" fit="true"></table>
            </div>
            <div style="height: 10%;">
            <div id="batchAddBtn" align="center"  style="border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;position:fixed; bottom:0;">
                <button class="easyui-linkbutton" title="保存" onclick="save()">
                <span style="padding-right: 2px; width: 40px;"><i class="fa fa-save"></i>&nbsp;保存</span>
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" title="返回" onclick="back()">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-reply"></i>&nbsp;返回</span>
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" title="上一步" onclick="before()">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-backward"></i>&nbsp;上一步</span>
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" title="下一步" onclick="next()">
                    <span style="padding-right: 2px; width: 40px;"><i class="fa fa-forward"></i>&nbsp;下一步</span>
                </button>
            </div>
            </div>
        </div>

    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/batch/js/batch.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/caseCheck/batch/js/batch_add_2_choose_case.js"></script>
</body>
</html>