<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	
    <link rel="stylesheet" type="text/css" href="/xzfy/css/init1.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/index.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/accept.css" />
    <link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
	<!-- 中腾按钮框架 -->
	<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
    
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
    <title>补正</title>
    <style type="text/css">
    html, body{
	margin:0;
	padding:0;
	border:0;
	outline:0;
	font-weight:inherit;
	font-style:inherit;
	font-size:100%;
	font-family:inherit;
	vertical-align:baseline;
	-webkit-text-size-adjust:90%;
    overflow: auto;
	}
    </style>
</head>

<body onload="doInit()"> 
    <div id='filling-content'>
        <div class='case-buttons'>
            <div class='btn-left'>
                <span class='btn generate-accept'><span class='iconfont ddd'></span>生成补正通知书</span>
                <span class='btn generate-reply'><span class='iconfont ddd'></span>生成送达回证</span>
                <span class='btn upload-accept'><span class='iconfont ddd'></span>附件上传</span>
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
                <table border="0" cellspacing="0" cellpadding="0" id='apply-table' style="display: none;">
                    <tr>
                        <th>序号</th>
                        <th>文件类型</th>
                        <th>文件名称</th>
                        <th>操作</th>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" id='accept-table' style="display: none;">
                    <tr>
                        <th>序号</th>
                        <th>文件类型</th>
                        <th>文件名称</th>
                        <th>操作</th>
                    </tr>
                </table>
            </div>
            <div class='establish-calendar'>
                <span>补正时间：</span>
                <input type="text" id="acceptTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput" style="width:172px;height: 20px;border: 1px solid #E2E2E2 !important;" />
            </div>
            <div class='establish-advices'>
                <span>补正理由：</span>
                <span>
                    <input type="text" id="reason" class='estab-input' placeholder="请输入受理理由" />
                	<span class="btn case-save" onclick="chooseReason()">选择</span>
                </span>
            </div>
            <div class='establish-advices'>
                <span>补正材料 ：</span>
                <span>
                	<input type="text" id="remark" class='estab-input' placeholder="请输入受理理由" />
                	<span class="btn case-save" onclick="chooseMatial()">选择</span>
                </span>
            </div>
           
        </div>
        <div class='module-bottom'>
            <div class='operate-container'>
                <span class='btn case-save' onclick="submit()">保存</span>
                <span class='btn case-approval' onclick="approval()">立案审批</span>
                <span class='btn case-hearing-person' onclick="chooseNext()">选择审理人</span>
            </div>
        </div>
    </div>         
   
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/acceptcommon.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseAcceptence/caseCorrection.js"></script>
</body>

</html>