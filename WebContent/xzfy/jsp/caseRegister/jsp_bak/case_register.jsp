<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    //int id=TeeStringUtil.getInteger(request.getParameter("id"), 0);
    String id = request.getParameter("id");
    /* String type = request.getParameter("type"); */
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>

<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.tab {
    width: 100%;
    height: 40px;
    border: 1px solid #d1d1d1;
	border-radius:5px;
}
.tab ul li {
    width: 25%;
    line-height: 40px;
    text-align: center;
    float: left;
    border-right: 1px solid #d1d1d1;
    cursor: pointer;
}
#rp_btn {
    position: fixed;
    bottom: 0px;
    z-index: 999;
    background: white;
    width: 100%;
    height: 40px;
    text-align: right;
    padding: 6px 30px 6px;
    border-top: 1px #e6e9ed solid;
    /* margin-right: 10px; */
    /* margin: 0 auto; */
}
#rp_btn input {
    width: 80px;
    height: 28px;
    margin-left:10px;
    border-radius:5px;
    font-size: 14px;
    color: #fff;
    background-color: #3379b7;
}
</style>
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
</head>
<!-- <body onload="doInit();" style="background: radial-gradient(circle at 20%, #80FFFF, #FFEB3B);text-align:center; overflow-y:hidden;overflow-x:hidden; padding-left: 10px; padding-right: 10px;"> -->
<body onload="doInit();" style=" overflow: hidden; padding-left: 10px; padding-right: 10px;">
    <div class="tab">
        <ul>
            <li onclick="showpage(0);">来件\接待信息</li>
            <li onclick="showpage(1);">当事人信息</li>
            <li onclick="showpage(2);">复议事项</li>
            <li onclick="showpage(3);">案件材料</li>
        </ul>
    </div>
    <div class="content" id="d1">0</div>
    <div class="content" id="d1">1</div>
    <div class="content" id="d1">2</div>
    <div class="content" id="d1">
         <ul class="material">
             <li><span class="material-title">申请书</span><span class="matertial-content">未上传</span><button>上  传</button></li>
             <li><span class="material-title">收件等登记材料</span><span class="matertial-content"></span><button>上  传</button></li>
             <li><span class="material-title">申请人提交的其他材料</span ><span class="matertial-content">未上传</span><button>上  传</button></li>
             <li><span class="material-title">阅卷笔录、阅卷意见及材料</span><span class="matertial-content">未上传</span><button>上  传</button></li>
             <li><span class="material-title">阅卷笔录、阅卷意见及材料</span><span class="matertial-content"></span><button>上  传</button></li>
             <li><span class="material-title">被申请人答复书</span><span class="matertial-content"></span><button>上  传</button></li>
             <li><span class="material-title">被申请人证据材料</span><span class="matertial-content">未上传</span><button>上  传</button></li>
             <li><span class="material-title">被申请人补充材料</span><span class="matertial-content"></span><button>上  传</button></li>
             <li><span class="material-title">第三人有关材料</span><span class="matertial-content"></span><button>上  传</button></li>
             <li><span class="material-title">复议机构制作获取的材料</span><span class="matertial-content"></span><button>上  传</button></li>
             <li><span class="material-title">审批表</span><span class="matertial-content"></span><button>上  传</button></li>
         </ul>
    </div>
    <div id="rp_btn" class="fr">
        <input id="back" class="btn-win-white" type="button" value="上一步" title="上一步" onclick="back();"  /> 
        <input id="save" class="btn-win-white" type="button" value="暂  存" title="暂存" onclick="save();"  />
        <input id="forward" class="btn-win-white" type="button" value="下一步" title="下一步" onclick="forward();"  />
    </div>
<script type="text/javascript" src="../../js/caseRegister/case_register.js"></script>
</body>
</html>