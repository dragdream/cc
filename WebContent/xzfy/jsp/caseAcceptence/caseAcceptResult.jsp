<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	
    <link rel="stylesheet" type="text/css" href="/xzfy/css/init1.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/index.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/accept.css" />
    
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
    <title>受理结果</title>
</head>

<body onload="doInit()"> 
    <div id='filling-content'>
        <div class='case-buttons'>
            <div class='btn-left' id="accept" style="display: none;">
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成受理通知书</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>生成答复通知书</span>
                <span class='btn upload-accept'><span class='iconfont ddd'></span>上传受理通知书</span>
                <span class='btn upload-reply'><span class='iconfont ddd'></span>上传答复通知书</span>
            </div>
            <div class='btn-left' id="unaccept" style="display: none;">
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成不予受理决定书</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>生成送达回证</span>
                <span class='btn upload-accept'><span class='iconfont ddd'></span>附件上传</span>
            </div>
            <div class='btn-left' id="correction" style="display: none;">
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成补正通知书</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>生成送达回证</span>
                <span class='btn upload-accept'><span class='iconfont ddd'></span>附件上传</span>
            </div>
            <div class='btn-left' id="inform" style="display: none;">
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成告知书</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>生成送达回证</span>
                <span class='btn upload-accept'><span class='iconfont ddd'></span>附件上传</span>
            </div>
            <div class='btn-left' id="forword" style="display: none;">
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成转送函</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>生成送达回证</span>
                <span class='btn upload-accept'><span class='iconfont ddd'></span>附件上传</span>
            </div>
            <div class='btn-left' id="other" style="display: none;">
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成文书</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>附件上传</span>
            </div>
            
            <div class='btn-right'>
                <span class='btn case-similar'>相似案例</span>
                <span class='btn case-rules'>法律法规</span>
            </div>
        </div>
        <div class='case-fill'>
            <div class='fill-table'>
                <span class='fill-title'>申请人信息：</span>
                <span class='fill-input'>
                    <input type="text" id="applicantList" readonly="readonly"/>
                </span>
            </div>
            <div class='fill-table'>
                <span class='fill-title'>被申请人信息：</span>
                <span class='fill-input'>
                    <input type="text" id="respondentList" readonly="readonly"/>
                </span>
            </div>
            <div class='fill-table'>
                <span class='fill-title'>行政复议请求：</span>
                <span class='fill-input'>
                    <input type="text" id="requestForReconsideration" readonly="readonly"/>
                </span>
            </div>
            <div class='fill-table'>
                <span class='fill-title'>事实和理由：</span>
                <span class='fill-input'>
                    <input type="text" id="factsAndReasons" readonly="readonly"/>
                </span>
            </div>
        </div>
        <div class='module-middle'>
            <div class='stage-title'>
                <span class='establish-stage' onclick='changeFormShow(this,"1")'>登记阶段材料</span>
                <span class='establish-stage active-estab' onclick='changeFormShow(this,"2")'>立案阶段材料</span>
            </div>
            <div class='form-info'>
                <table border="0" cellspacing="0" cellpadding="0" id='apply-table'>
                    <tr>
                        <th>序号</th>
                        <th>文件类型</th>
                        <th>文件名称</th>
                        <th>操作</th>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" id='accept-table'>
                    <tr>
                        <th>序号</th>
                        <th>文件类型</th>
                        <th>文件名称</th>
                        <th>操作</th>
                    </tr>
                </table>
            </div>
            <div class='establish-calendar'>
                <span id="timeTitle"></span>
                <span id="acceptTime"></span>
            </div>
            <div class='establish-advices'>
                <span id="reasonTitle"></span>
                <span id="reason" ></span>
            </div>
            <div class='establish-advices' id="remarkdiv" style="display: none;">
                <span id="remarkTitle">补正材料</span>
                <span id="remark" ></span>
            </div>
        </div>
    </div>         
   
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptcommon.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/caseAcceptResult.js"></script>
</body>

</html>