<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*,java.text.SimpleDateFormat"%>
	<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/lawManage/css/lawManage.css" />
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<div>
	<input type="hidden" id = "lawAdjustReportid" name = "lawAdjustReportid" value= "<c:out value ='${lawAdjust.id}'/>" /> 
	<input type="hidden" id ="submitlawLevel_value" name = "submitlawLevel" value = "<c:out value ='${lawAdjust.submitlawLevel}'/>"/>
	<input type="hidden" id ="timeliness_value" name = "timeliness" value = "<c:out value ='${lawAdjust.timeliness}'/>"/>
	<input type="hidden" id ="updateLawId_value" name = "updateLawId" value = "<c:out value ='${lawAdjust.updateLawId}'/>"/>
            <form role="form" id= "lawReportForm" name = "lawReportForm"  enctype="multipart/form-data" method="post" data-options="novalidate:true">
                <table id="adjustReport_type_table" class="TableBlock" style="width: 100%; background: #fff;">
                	<tr class="law-baseInfo-tr" style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 160px;">调整类型<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td  id="adjustReport_type_td"colspan="3">
                            <input class="easyui-radiobutton adjust-type-radio" <c:if test="${lawAdjust.controlType == '01'}"> checked </c:if> name="controlType" value="01" label="新颁" labelPosition ="after" labelAlign="left"/>
                            <input class="easyui-radiobutton adjust-type-radio" <c:if test="${lawAdjust.controlType == '02'}"> checked </c:if> name="controlType" value="02" label="修订" labelPosition ="after" labelAlign="left"/>
                            <input class="easyui-radiobutton adjust-type-radio" <c:if test="${lawAdjust.controlType == '03'}"> checked </c:if> name="controlType" value="03" label="废止" labelPosition ="after" labelAlign="left"/>
                        </td>
                    </tr>
                    
                </table>
                <table class="TableBlock" frame="void" style="width: 100%; background: #fff;" rules="none">
                	<tr id="modifyLaw_info_tr" style="border:none!important; <c:if test="${lawAdjust.controlType != '02' && lawAdjust.controlType != '03'}">display:none;</c:if>">
                        <td class="power-table-label text-info-area" style="text-align:right;width: 160px;">待修订法律法规<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="3">
                        	<select class="easyui-combobox" style="width: 99%;" id="modifyLaw_select" name="updateLawId">
                        	</select>
                        </td>
                    </tr>
                	<tr class="law-baseInfo-tr" style="border:none!important;" >
                        <td class="power-table-label" style="text-align:right;width:160px;">法律法规名称<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="name" name="name" style="width: 99%;" value="<c:out value ='${lawAdjust.name}'/>" data-options="validType:'length[0,200]'"/>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr" style="border:none!important;" >
                    	<td class="power-table-label" style="text-align:right;width: 100px;">发布机关<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td >
                            <input name="organ" id="organ" class="easyui-textbox" data-options="validType:'length[0,100]'" value='<c:out value ='${lawAdjust.organ}'/>' data-options="multiple:true" style="width: 250px;" />
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 100px;">发文字号<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td >
                            <input name="word" id="word" class="easyui-textbox" data-options="validType:'length[0,30]'" value='<c:out value ='${lawAdjust.word}'/>' style="width: 100%;"/>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr" style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">发布日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td >
                        	<input  class="easyui-datebox" id='promulgation'  name='promulgation'
                        		<c:if test="${lawAdjust.promulgationStr != 'null' && lawAdjust.promulgationStr != ''}">
                                 	value='<c:out value="${lawAdjust.promulgationStr}"/>'
                                </c:if>
                            style="width: 100%;" editable="false" panelHeight="230px"/>
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 100px;">生效日期<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td >
                        	<input  class="easyui-datebox" id='implementation'  name='implementation'
                        		<c:if test="${lawAdjust.implementationStr != 'null' && lawAdjust.implementationStr != ''}">
                                 	value='<c:out value="${lawAdjust.implementationStr}"/>'
                                </c:if>
                            style="width: 100%;" editable="false" panelHeight="230px"/>
                   		</td>
                    </tr>
                    <tr class="law-baseInfo-tr" style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;width: 100px;">时效性<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td >
                       		<select name="timeliness" id="timeliness" style="width: 100%;" class="easyui-combobox">
		    				</select>
                        </td>
                        <td class="power-table-label" style="text-align:right;width: 100px;">效力级别<span style="color:red;font-weight:bold;">*</span>：</td>
                        <td >
                       		<select name="submitlawLevel" id="submitlawLevel" style="width: 100%;" class="easyui-combobox" >
		    				</select>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr" style="border:none!important;">
                        <td  class="power-table-label" style="text-align:right;width: 100px;">备注&nbsp;&nbsp;：</td>
                        <td colspan="3">
                            <input class="easyui-textbox" id="remark" name="remark" style="width: 100%;" value="<c:out value ='${lawAdjust.remark}'/>" data-options="validType:'length[0,400]'"/>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr" style="border:none!important;">
                        <td style="text-align:right;width: 100px;" class="power-table-label">法律原文&nbsp;&nbsp;：
                        </td>
                        <td style="width: 180px;">
                        	<a id="uploadHolder2" class="add_swfupload">
								<img src="<%=systemImagePath %>/upload/batch_upload.png" />浏览
								<span class="tip">只能上传doc或docx格式的附件</span>
							</a>
							<input id="attaches" name="attaches" type="hidden"/>
						</td>
                        <td class="power-table-label" colspan="2">
 							<div id="attachDiv"></div>
							<div id="fileContainer2"></div>
							<div id="renderContainer2" style="display:none"></div>
 						</td>
                    </tr>
                    </table>
            <!-- 	<table id="stopLaw_info_table" class="TableBlock" style="width: 100%; background: #fff;display:none;">
                	<tr class="law-baseInfo-tr">
                        <td class="power-table-label" style="text-align:center;">调整类型：</td>
                        <td style="width: 300px;">
                            <input class="easyui-radiobutton adjust-type-radio" name="adjust-type" value="1" label="新法" labelPosition ="after" labelAlign="left"/>
                            <input class="easyui-radiobutton adjust-type-radio" name="adjust-type" value="2" label="修订" labelPosition ="after" labelAlign="left"/>
                            <input class="easyui-radiobutton adjust-type-radio" name="adjust-type" value="3" label="废止" labelPosition ="after" labelAlign="left"/>
                        </td>
                    </tr>
                </table> -->
            </form>
            <br/>
        </div>
        <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
        <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
        <script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawAdjustReport/js/lawAdjustReport_input.js"></script>
</body>
</html>