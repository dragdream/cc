<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<title>行政区划维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit()" style="padding-right: 10px; padding-left: 10px; padding-top: 5px">
    <div id="toolbar" class="titlebar clearfix">
        <div id="outwarp">
            <div class="fl left">
                <img id="img1" class='title_img' src="<%=contextPath%>/common/zt_webframe/imgs/xzbg/clgl/clsqcx.png" /> <span class="title">行政区划维护</span>
            </div>
            <div class=" fr">
                <button class="easyui-linkbutton" onclick="add()">
                    <i class="fa fa-plus"></i>&nbsp;新增
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" onclick="examine()">
                    <i class="fa fa-check"></i>&nbsp;审核
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" onclick="account()">
                    <i class="fa fa-user"></i>&nbsp;分配账号
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" onclick="releaseAccount()">
                    <i class="fa fa-recycle"></i>&nbsp;回收账号
                </button>
                &nbsp;&nbsp;
                <button class="easyui-linkbutton" onclick="reSetAccount()">
                    <i class="fa fa-refresh"></i>&nbsp;密码重置
                </button>
            </div>
        </div>
        <span class="basic_border"></span>
        <div id="adminDivision_search_div" class="" style="padding-top: 5px; padding-bottom: 5px">
            <!-- form表单 -->
            <form id="adminDivision_search_form">
                <!-- <table style="width: 100%;">
                    <tr>
                        <td style="width: 130px; text-align: right;">行政区划名称：</td>
                        <td style="width: 180px; text-align: left;"></td>
                        <td style="width: 100px; text-align: right;"></td>
                        <td style="width: 180px; text-align: left;"></td>
                        <td class="fl left" style="width: 300px; text-align: center;"></td>
                        <td style="width: 120px;"></td>
                    </tr>
                    <tr>
                        <td colspan="4"></td>
                        <td colspan="4" class="fl right" style="width: 300px; text-align: center;">/td>
                    </tr>
                    <tr>
                        <td style="width: 130px; text-align: right;">所属省（直辖市）：</td>
                        <td style="width: 180px; text-align: left;">
                            <input type="text" name='provincialCode' id="provincialCode" 
                                class="easyui-combobox" style="width:180px; height:30px;"/>
                        </td>
                        <td style="width: 100px; text-align: right;">所属市（州）：</td>
                        <td style="width: 180px; text-align: left;">
                            <input type="text" name='cityCode' id="cityCode"
                                class="easyui-combobox" style="width:180px; height:30px;"/>
                        </td>
                        <td style="width: 100px; text-align: right;">所属区（县）：</td>
                        <td style="width: 180px; text-align: left;">
                            <input name="districtCode" id="districtCode" class="easyui-combobox" style="width: 180px; height:30px;"/>
                        </td>
                        <td style="width: 120px; "></td>
                    </tr>
                </table> -->
                <div class="left">
                    <span>行政区划名称：</span>
                    <input type="text" name="adminDivisionName" id="adminDivisionName" class="easyui-textbox" style="width: 180px; height: 30px;" />
                    &nbsp;&nbsp;
                    <span>行政区划代码：</span>
                    <input type="text" name="adminDivisionCode" id="adminDivisionCode" class="easyui-textbox" style="width: 180px; height: 30px;" />
                    &nbsp;&nbsp;审核状态：
		            <select size="1" name='isExamine' id='isExamine' class="easyui-combobox" style="width: 145px" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'">
		                            <option value="">全部</option>
		                            <option value="0">未审核</option>
		                            <option value="1">已审核</option>
		            </select>
                    <a class="easyui-linkbutton" onclick="adminDivisionSearch()"><i class="fa fa-search"></i> 查询</a>
<!--                     <span>审核状态：</span> -->
<!--                     <span> -->
<!--                     <input type="radio" class="easyui-radiobutton" name="isSubmit" value="1" label="未审核" labelPosition="after" checked="true" labelWidth="50px;"/> -->
<!--                     <input type="radio" class="easyui-radiobutton" name="isSubmit" value="2" label="已审核" labelPosition="after"/> -->
<!--                     &nbsp;&nbsp;&nbsp;&nbsp; -->
                    </span>
                </div>
            </form>
        </div>
    </div>
    <table id="adminDivisionManage_base_table" fit="true"></table>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/Department/js/adminDivision_manage_base.js"></script>
</body>
</html>