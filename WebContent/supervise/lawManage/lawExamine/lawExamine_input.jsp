<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*,java.text.SimpleDateFormat"%>
	<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/common/css/supervise.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/supervise/lawManage/css/lawManage.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/pureText-lookInfo.css" />

</head>
<body onload="doInit()" style="padding-right: 10px;padding-left: 10px;padding-top: 5px">
	<div>
	<input type="hidden" id = "lawAdjustReportid" name = "lawAdjustReportid" value= "<c:out value ='${lawAdjust.id}'/>" /> 
	<input type="hidden" id ="submitlawLevel_value" name = "submitlawLevel" value = "<c:out value ='${lawAdjust.submitlawLevel}'/>"/>
	<input type="hidden" id ="timeliness_value" name = "timeliness" value = "<c:out value ='${lawAdjust.timeliness}'/>"/>
	<input type="hidden" id ="updateLawId_value" name = "updateLawId" value = "<c:out value ='${lawAdjust.updateLawId}'/>"/>
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post">
                <div class="easyui-panel" data-options="fit:true,border:false">
                <table id="adjustReport_type_table" class="TableBlock lookInfo-lowHeight-table" style="width: 100%; background: #fff;">
	                <tr style="border:none!important;">
                        <td class="power-table-label font-bold-label-td" style="text-align:right;width: 180px;">调整类型：</td>
                        <td colspan="3">
                           <c:out value ='${lawAdjust.controlTypeStr}'/>
                        </td>
                    </tr>
                </table>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                	<tr id="modifyLaw_info_tr" style="border:none!important; <c:if test="${lawAdjust.controlType != '02' && lawAdjust.controlType != '03'}">display:none;</c:if>">
                        <td class="power-table-label text-info-area" style="text-align:right;width: 180px;">待修订法律法规：</td>
                        <td colspan="3">
                        	<c:out value ='${lawAdjust.updateLawName}'/>
                        </td>
                    </tr>
                	<tr class="law-baseInfo-tr" >
                        <td class="power-table-label font-bold-label-td" style="text-align:right; width: 180px;">法律法规名称：</td>
                        <td colspan="3">
                            <c:out value ='${lawAdjust.name}'/>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr" >
                    	<td class="power-table-label font-bold-label-td"><div style="text-align:right;">发布机关：</div></td>
                        <td style="width: 250px;">
                            <c:out value ='${lawAdjust.organ}'/>
                        </td>
                        <td class="power-table-label font-bold-label-td" style="text-align:right;">发文字号：</td>
                        <td style="width: 250px;">
                            <c:out value ='${lawAdjust.word}'/>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr">
                        <td class="power-table-label font-bold-label-td"><div style="text-align:right;">发布日期：</div></td>
                        <td style="width: 250px;">
                        	<c:if test="${lawAdjust.promulgationStr != 'null' && lawAdjust.promulgationStr != ''}">
                                 <c:out value="${lawAdjust.promulgationStr}"/>
                            </c:if>
                        </td>
                  		<td class="power-table-label font-bold-label-td"><div style="text-align:right;">生效日期：</div></td>
                        <td style=" width: 250px;">
                        	<c:if test="${lawAdjust.implementationStr != 'null' && lawAdjust.implementationStr != ''}">
                                 <c:out value="${lawAdjust.implementationStr}"/>
                            </c:if>
                   		</td>
                    </tr>
                    <tr class="law-baseInfo-tr">
                    	<td class="power-table-label font-bold-label-td"><div style="text-align:right;">时效性：</div></td>
                        <td style="width: 250px;">
                       		<c:out value ='${lawAdjust.timelinessStr}'/>
                        </td>
                        <td class="power-table-label font-bold-label-td"><div style="text-align:right;">效力级别：</div></td>
                        <td style="width: 250px;">
                       		<c:out value ='${lawAdjust.submitlawLevelStr}'/>
                        </td>
                    </tr>
                    <tr class="law-baseInfo-tr">
                        <td class="power-table-label font-bold-label-td"><div style="text-align:right;">备注：</div></td>
                        <td colspan="3">
						    <c:out value ='${lawAdjust.remark}'/>
						</td>
                    </tr>
                    <tr class="law-baseInfo-tr">
                        <td class="power-table-label font-bold-label-td"><div style="text-align:right;">法律原文：
                        </div></td>
                        <td class="TableData" colspan="3">
 							<div id="attachDiv"></div>
							<div id="fileContainer2"></div>
							<div id="renderContainer2"></div>
							<%-- <a id="uploadHolder2" class="add_swfupload">
								<img src="<%=systemImagePath %>/upload/batch_upload.png"/>快速上传
							</a> --%>
							<input id="attaches" name="attaches" type="hidden"/>
 				
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
            </div>
            </form>
            <br/>
        </div>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/lawAdjustExamine_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
</body>
</html>