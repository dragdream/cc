<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>车辆信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">
var sid = <%=sid%>;
var checkVehicle = 0;
function doInit()
{
	if(sid > 0){
		getById(sid);
	}
	
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/vehicleManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			
			var att=prc.attacheModel;
			if(att!=null){
				att['priv']=3;
				var attachElement = tools.getAttachElement(att,{});
				$("#attach").append(attachElement);
			}
			
			
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	var urls = contextPath+"/vehicleUsageManage/getRecords.action";
	var json = tools.requestJsonRs(urls, {vehicleId : id});
	if(json.rtState){
		var data = json.rtData;
		var html ="<table id='tbody' style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>";
		html+="<tr class='TableHeader' style='background-color:#e8ecf9'><td width='25%'>申请人</td><td width='25%'>开始时间</td><td width='25%'>结束时间</td><td width='25%'>目的地</td></tr>";
		if(data.length>0){
			for(var i = 0 ;i<data.length;i++){
				html+="<tr class='TableData'><td>"+data[i].vuProposerName+"</td><td>"+data[i].vuStartStr+"</td><td>"+data[i].vuEndStr+"</td><td>"+data[i].vuDestination+"</td></tr>";
			}
			html+="</table>";
			$("#records").html(html);
		}else{
			html+="<tr><td colspan='4' id='mess'></td></tr></table>";
			$("#records").append(html);
			messageMsg("没有相关申请记录！", "mess","info");
		}
	
	}else{
		//$("#records").html("没有相关申请记录！");
		messageMsg("没有相关申请记录！", "records","info");
	}
	
}
</script>
	
</head>
<body onload="doInit();"> 
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
	<table class="TableBlock" width='100%'>
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">车牌号：<font color='red'>*</font></td>
			<td class="TableData">
				<span type="text" name="vModel" id="vModel" ></span>
				<span id="imageSpan" >  </span>
			</td>
			<td  class="TableData" width="100">厂牌类型：</td>
			<td class="TableData">
				<span type="text" id="" name="vNum" ></span></td>
		</tr>
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">发动机号：</td>
			<td class="TableData">
				<span type="text" id="vEngineNum" name="vEngineNum"></span>
			</td>
			
			<td  class="TableData" width="100">车辆类型：</td>
			<td class="TableData">
				<select name="vType" class="BigSelect" disabled="disabled">
					 <OPTION selected value=""></OPTION>
					  <OPTION value="01">轿车</OPTION> 
					  <OPTION value="02">面包车</OPTION>
					   <OPTION value="03">越野车</OPTION>
					    <OPTION value="04">吉普车</OPTION> 
					    <OPTION value="05">巴士</OPTION>
					     <OPTION value="06">工具车</OPTION> 
					     <OPTION value="07">卡车</OPTION>
				</select>
			</td>
		</tr>
		
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">购买价格：</td>
			<td class="TableData">
				<span type="text" id="" name="vPrice" ></span>
			</td>
			
			<td  class="TableData" width="100">购买日期：</td>
			<td class="TableData">
				<span type="text" size="10" name="buyDateStr" id="buyDateStr" ></span>
			</td>
		</tr>
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">司机：</td>
			<td class="TableData" >
				<span type="text" id="vDriver" name="vDriver"> </span>
			</td>
			<td  class="TableData" width="100">当前状态：</td>
			<td class="TableData" >
				<select name="status" class="BigSelect" disabled="disabled">
					 <OPTION selected value="0">可用</OPTION> 
					 <OPTION value="1">损坏</OPTION>
					 <OPTION value="2">维修中</OPTION> 
					 <OPTION value="3">报废</OPTION>
				</select>
			</td>
		</tr>
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">图片：</td>
			<td class="TableData" colspan="3">
				<!-- <span type="file" name="file"  class="BigInput"  id="vUploadBtn"</span> -->
				<span id="attach"></span>
			</td>
		</tr>
		
			<tr class="TableData" id="">
			<td style="text-indent:10px">申请权限部门：</td>
			<td  align="left" colspan="3">
				<span name="postDeptNames" id="postDeptNames" ></span> &nbsp;&nbsp;
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td style="text-indent:10px">申请权限人员：</td>
			<td  align="left" colspan="3">
				<span name="postUserNames" id="postUserNames"></span> &nbsp;&nbsp;
			</td>

	
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">备注：</td>
			<td class="TableData" colspan="3">
				<span type="text"  name="vRemark" id="vRemark"></span>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="4" style="text-indent:10px">申请记录如下：</td>
		</tr>
		<tr>
			<td class="TableData" colspan="4" style="text-indent:10px" id="records"></td>
		</tr>
	</table>
</form>
</body>
</html>
