<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/supervise/lawList/js/law_add.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

</head>
<body  onload="doInit();" style="padding: 10px;background-color: #f2f2f2;">
        <div title="新增">
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:center;">法律法规名称：</td>
                        <td style="width: 250px;">
                            <input class="easyui-textbox" id="name" name="name" style="width: 91%;" value="" data-options="required:true, missingMessage:'请输入法律法规名称'"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:center;">发文字号：</td>
                        <td style="width: 250px;">
                            <input name="word" id="word" class="easyui-textbox"  style="width: 89%;"/>
                        </td>
                    </tr>
                    
                    
                    
                    <tr style="border:none!important;">
                        <td class="power-table-label"><div style="text-align:center;">发布机关：</div></td>
                        <td style="width: 300px;">
                            <input name="organ" id="organ" class="easyui-textbox" data-options="multiple:true" style="width: 89%;" />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label"><div style="text-align:center;">法律类别：</div></td>
                        <td style="width: 300px;">
                       		<select name="submitlawLevel" id="submitlawLevel" style="width: 89%;" class="easyui-combobox" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'" >
		    				<option value="">请选择</option>
		    				</select>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label"><div style="text-align:center;">时效性：</div></td>
                        <td style="width: 300px;">
                       		<select name="timeliness" id="timeliness" style="width: 89%;" class="easyui-combobox" panelMaxHeight="150px" data-options="editable:false,panelHeight:'auto'" >
		    				<option value="">请选择</option>
							<option value="01">现行有效</option>
							<option value="02">失效</option>
		    				</select>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label"><div style="text-align:center;">颁布日期：</div></td>
                        <td style="width: 300px;">
                        	<input  class="easyui-datebox" id='promulgation'  name='promulgation' style="width: 89%;"  />
                        </td>
                        
                    </tr>
                    <tr style="border:none!important;">
                  		<td class="power-table-label"><div style="text-align:center;">实施日期：</div></td>
                        <td style=" width: 300px;">
                        	<input  class="easyui-datebox" id='implementation'  name='implementation' style="width: 89%;"  />
                   		</td>
                    </tr>
                    <tr style="border:none!important;">
                        <td style="width: 180px;" class="power-table-label"><div style="text-align:center;">上传文件：
                        <span style="color:red;font-weight:bold;">*</span></div></td>
                        <td class="TableData" style="width: 180px;">
 							<div id="attachDiv"></div>
							<div id="fileContainer2"></div>
							<div id="renderContainer2"></div> 
							<a id="uploadHolder2" class="add_swfupload">
								<img src="<%=systemImagePath %>/upload/batch_upload.png"/>快速上传
							</a>
							<input id="attaches" name="attaches" type="hidden"/>
 				
 						 </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td style="width: 180px;" class="power-table-label"><div style="text-align:center;">内容：</div></td>
                        <td style="width: 180px;">
						    <input class="easyui-textbox" id="remark" name="remark" style="width:89%;height: 100px;" />
						</td>
                    </tr>
                    </table>
            </form>
        </div>
</body>
</html>