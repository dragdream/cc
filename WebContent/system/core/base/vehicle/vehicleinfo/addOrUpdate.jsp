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
<%@ include file="/header/validator2.0.jsp"%>
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
 * 新建或者更新
 */
function doSaveOrUpdate(callback){
	if(checkFrom()){
		var url = contextPath + "/vehicleManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		
		
		 $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 		callback(res);
			 		
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	                $.MsgBox.Alert_auto("保存错误");
	          },
	          dataType: 'json'});
		 
	}
}
/**
 * 校验
 */
function checkFrom(){
	var check = $("#form1").validate();
	if($("#vModel").val()==""||$("#vModel").val()==null){
		$.MsgBox.Alert_auto("请填写车牌号！");
		return false;
	}else{
		if(checkVehicle ==1){
			$.MsgBox.Alert_auto("该车牌号已存在！");
			return false;
		}	
	}
	
	if(!check){
		return false;
	}
	 return true;
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
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 校验名称唯一性
 */
function checkNameFunc(nameStr){
	$("#imageSpan").text("");
	if(nameStr){
		var url = "<%=contextPath %>/vehicleManage/checkName.action";
		var para = {nameStr:nameStr,sid:"<%=sid%>"};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var prc = jsonRs.rtData;
			var imgStr = "correct.gif";
			if(prc.flag == 1){//存在
				imgStr = "error.gif";
			}
			$("#imageSpan").append("<img src='<%=stylePath %>/imgs/" + imgStr + "' ></img>");
			if(prc.flag == 1){//存在
				$("#vModel").focus();
				checkVehicle= 1;
			}else{
				checkVehicle= 0;
			}
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}else{
		
	}
}

</script>
	
</head>
<body onload="doInit();" style="background-color: #f2f2f2"> 
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
	<table class="TableBlock" width="100%">
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">车牌号：<font color='red'>*</font></td>
			<td class="TableData">
				<input type="text" style="height:20px" name="vModel" id="vModel"  class="BigInput" required onblur="checkNameFunc(this.value);">
				<span id="imageSpan" >  </span>
			</td>
			<td  class="TableData" width="100">厂牌类型：</td>
			<td class="TableData">
				<input type="text" style="height:20px" id="" name="vNum" class="BigInput"  maxlength="100"/></td>
		</tr>
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">发动机号：</td>
			<td class="TableData">
				<input type="text" style="height:20px" id="" name="vEngineNum" class="BigInput"  maxlength="100"/>
			</td>
			
			<td  class="TableData" width="100">车辆类型：</td>
			<td class="TableData">
				<select name="vType" class="BigSelect" style="height:20px;width: 170px">
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
				<input type="text"  style="height:20px" id="" name="vPrice" class="BigInput easyui-validatebox"  required="true" validType ='number[]' maxlength="10"/>
			</td>
			
			<td  class="TableData" width="100">购买日期：</td>
			<td class="TableData">
				<input type="text" style="height:20px;width: 170px" size="10" name="buyDateStr" class="BigInput Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			</td>
		</tr>
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">司机：</td>
			<td class="TableData" >
				<input type="text" style="height:20px" id="" name="vDriver" class="BigInput"  maxlength="100"/>
			</td>
			<td  class="TableData" width="100">当前状态：</td>
			<td class="TableData" >
				<select name="status" class="BigSelect" style="height:20px;width: 170px">
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
				<input type="file" name="file"  class="BigInput"  id="vUploadBtn" />
			</td>
		</tr>
		
			<tr class="TableData" id="">
			<td style="text-indent:10px">申请权限部门：</td>
			<td  align="left" colspan="3">
				<input id="postDeptIds" name="postDeptIds" type="hidden" value=''> 
				<textarea name="postDeptNames" id="postDeptNames" class="SmallStatic BigTextarea" rows="5" cols="60" readonly="readonly"></textarea> &nbsp;&nbsp;			    
			    <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectDept(['postDeptIds','postDeptNames'],'')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('postDeptIds','postDeptNames')" value="清空"/>
				</span>
			
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td style="text-indent:10px">申请权限人员：</td>
			<td  align="left" colspan="3">
				<input id="postUserIds" name="postUserIds" type="hidden" value=''> 
				<textarea name="postUserNames" id="postUserNames" class="SmallStatic BigTextarea" rows="5" cols="60" readonly="readonly"></textarea> &nbsp;&nbsp;
			    <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['postUserIds','postUserNames'],'')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('postUserIds','postUserNames')" value="清空"/>
				</span>
			</td>

	
		<tr>
			<td  class="TableData" width="100" style="text-indent:10px">备注：</td>
			<td class="TableData" colspan="3">
				<textarea type="text"  name="vRemark" class="BigTextarea" cols="60" rows="5"></textarea>
			</td>
		</tr>
		
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</body>
</html>
