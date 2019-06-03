<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = TeeStringUtil.getString(request.getParameter("id"), "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/upload.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.diaryTable {
	border-collapse: collapse;
}

.diaryTable td {
	border: 1px solid #e2e2e2;
	font-size: 14px;
	padding: 5px;
}
</style>
<script>
var id='<%=id%>';
function doInit(){
	if(id!=""){
		var json = tools.requestJsonRs(contextPath+"/job/getById.action?id="+id);
		$("#id").val(json.rtData.id).attr("readonly","");
		$("#type").val(json.rtData.type);
		$("#body1").val(json.rtData.body1);
		$("#body2").val(json.rtData.body2);
		$("#status").val(json.rtData.status);
		$("#runNode").val(json.rtData.runNode);
		
		
		var expModel = eval("("+json.rtData.expModel+")");
		
		document.getElementById("expType").value = expModel.type;
		changeFunc(document.getElementById("expType"));
		
		if(expModel.type==1){//间隔（秒）
			$("#ta1").val(expModel.a);
		}else if(expModel.type==2){//天
			$("#tb1").val(expModel.a);
		}else if(expModel.type==3){//周
			$("#tc1").val(expModel.a);
			$("#tc2").val(expModel.b);
		}else if(expModel.type==4){//月
			$("#td1").val(expModel.a);
			$("#td2").val(expModel.b);
		}else if(expModel.type==5){//年
			$("#te1").val(expModel.a);
			$("#te2").val(expModel.b);
			$("#te3").val(expModel.c);
		}else if(expModel.type==6){//分
			$("#tf1").val(expModel.a);
		}else if(expModel.type==7){//时
			$("#tg1").val(expModel.a);
		}
	}else{
		changeFunc(document.getElementById("expType"));
	}
}

function changeFunc(obj){
	var type = obj.value;
	
	$("#ta").hide();
	$("#tb").hide();
	$("#tc").hide();
	$("#td").hide();
	$("#te").hide();
	$("#tf").hide();
	$("#tg").hide();
	
	if(type=="1"){
		$("#ta").show();
	}else if(type=="2"){
		$("#tb").show();
	}else if(type=="3"){
		$("#tc").show();
	}else if(type=="4"){
		$("#td").show();
	}else if(type=="5"){
		$("#te").show();
	}else if(type=="6"){
		$("#tf").show();
	}else if(type=="7"){
		$("#tg").show();
	}
}

function doSave(){
	
	if($("#id").val()==""){
		alert("请输入ID标识");
		return;
	}
	
	if($("#body1").val()==""){
		alert("请输入类路径或URL");
		return;
	}
	
	var params = {};
	params["id"] = $("#id").val();
	params["type"] = $("#type").val();
	params["body1"] = $("#body1").val();
	params["body2"] = $("#body2").val();
	params["status"] = $("#status").val();
	params["runNode"] = $("#runNode").val();
	
	var expTypeVal = $("#expType").val();
	var model = {type:expTypeVal};
	var expDesc = "";
	if(expTypeVal==1){//间隔
		model["a"] = $("#ta1").val();
		expDesc = "每隔"+$("#ta1").val()+"秒";
	}else if(expTypeVal==2){//天
		model["a"] = $("#tb1").val();
		expDesc = "每天["+$("#tb1").val()+"]";
	}else if(expTypeVal==3){//周
		model["a"] = $("#tc1").val();
		model["b"] = $("#tc2").val();
		expDesc = "每周"+$("#tc1 option:selected").html()+"["+$("#tc2").val()+"]";
	}else if(expTypeVal==4){//月
		model["a"] = $("#td1").val();
		model["b"] = $("#td2").val();
		expDesc = "每月"+$("#td1").val()+"号["+$("#td2").val()+"]";
	}else if(expTypeVal==5){//年
		model["a"] = $("#te1").val();
		model["b"] = $("#te2").val();
		model["c"] = $("#te3").val();
		expDesc = "每年"+$("#te1").val()+"月"+$("#te2").val()+"号["+$("#te3").val()+"]";
	}else if(expTypeVal==6){//间隔
		model["a"] = $("#tf1").val();
		expDesc = "每隔"+$("#tf1").val()+"分钟";
	}else if(expTypeVal==7){//间隔
		model["a"] = $("#tg1").val();
		expDesc = "每隔"+$("#tg1").val()+"小时";
	}
	
	params["expModel"] = tools.jsonObj2String(model);
	params["expDesc"] = expDesc;
	
	var json = tools.requestJsonRs(contextPath+"/job/addOrUpdate.action",params);
	window.location = "index.jsp";
	
}

</script>
</head>
<body onload="doInit();" style="overflow-x:hidden">
	<form method="post" name="form1" id="form1" class="tableForm" style="padding-bottom:5px;">
		<table class="TableBlock_page" width="100%" align="center">
            <tr>
               <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; 
                   <span> 添加/编辑定时任务</span> 
               </td>
            </tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">ID标识：</td>
				<td nowrap class="TableData"><input type="text" name="id"
					id="id" class="easyui-validatebox BigInput" required="true" size="10" />
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">任务名称：</td>
				<td nowrap class="TableData">
					<input type="text" name="body2"
					id="body2" class="easyui-validatebox BigInput" required size="25" >&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">任务类型：</td>
				<td nowrap class="TableData">
					<select class="BigSelect" name="type" id="type" >
						<option value="1">Java类</option>
						<option value="2">Http接口</option>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">类路径或URL：</td>
				<td nowrap class="TableData">
					<input type="text" name="body1"
					id="body1" class="easyui-validatebox BigInput" required size="25" >&nbsp;
					<span style="color:red">
					<br/>
					注：当任务类型为<b>普通Java类</b>时，此处是具体的类路径，例如：com.xxx.xxx.xx.ClassA
					<br/>
					当任务类型为<b>Http接口</b>时，此处需要填写URL全路径，例如：http://192.168.3.1/xxxx/xxxx
					</span>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">状态：</td>
				<td nowrap class="TableData">
					<select class="BigSelect" name="status" id="status" >
						<option value="0">停止</option>
						<option value="1">启用</option>
					</select>
				</td>
			</tr>
			<tr >
				<td nowrap class="TableData" style="text-indent: 10px">定时周期：</td>
				<td nowrap class="TableData">
					<select class="BigSelect" id="expType" onchange="changeFunc(this)">
						<option value="1">间隔（秒）</option>
						<option value="6">间隔（分）</option>
						<option value="7">间隔（时）</option>
						<option value="2">天</option>
						<option value="3">周</option>
						<option value="4">月</option>
						<option value="5">年</option>
					</select>
				</td>
			</tr>
			<tr id="ta" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td nowrap class="TableData">
					每隔&nbsp;&nbsp;<input id="ta1"  name="ta1" style="width:40px"/>&nbsp;&nbsp;秒
				</td>
			</tr>
			<tr id="tf" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td nowrap class="TableData">
					每隔&nbsp;&nbsp;<input id="tf1"  name="tf1" style="width:40px"/>&nbsp;&nbsp;分钟
				</td>
			</tr>
			<tr id="tg" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td nowrap class="TableData">
					每隔&nbsp;&nbsp;<input id="tg1"  name="tg1" style="width:40px"/>&nbsp;&nbsp;小时
				</td>
			</tr>
			<tr id="tb" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td class="TableData">
					每天&nbsp;&nbsp;
					<input id="tb1" name="tb1" class="Wdate" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" value="00:00:00"/>
               	</td>
			</tr>

			<tr id="tc" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td nowrap class="TableData">
					每周&nbsp;&nbsp;
					<select id="tc1" name="tc1">
						<option value="MON">一</option>
						<option value="TUES">二</option>
						<option value="WED">三</option>
						<option value="THUR">四</option>
						<option value="FRI">五</option>
						<option value="SAT">六</option>
						<option value="SUN">日</option>
					</select>
					&nbsp;&nbsp;
					<input id="tc2" name="tc2" class="Wdate" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" value="00:00:00"/>
				</td>
			</tr>
			<tr id="td" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td nowrap class="TableData">
					每月&nbsp;&nbsp;
					<select id="td1" name="td1">
						<%
							for(int i=1;i<=31;i++){
								out.print("<option value='"+i+"'>"+i+"</option>");
							}
						%>
					</select>
					&nbsp;号&nbsp;
					<input id="td2" name="td2" class="Wdate" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" value="00:00:00"/>
				</td>
			</tr>
			<tr id="te" style="display:none">
				<td nowrap class="TableData" style="text-indent: 10px">时间设置：</td>
				<td nowrap class="TableData">
					每年&nbsp;&nbsp;
					<select id="te1" name="te1">
						<%
							for(int i=1;i<=12;i++){
								out.print("<option value='"+i+"'>"+i+"</option>");
							}
						%>
					</select>
					&nbsp;月&nbsp;
					<select id="te2" name="te2">
						<%
							for(int i=1;i<=31;i++){
								out.print("<option value='"+i+"'>"+i+"</option>");
							}
						%>
					</select>
					&nbsp;号&nbsp;
					<input id="te3" name="te3" class="Wdate" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" value="00:00:00"/>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">执行节点：</td>
				<td nowrap class="TableData">
					<input type="text" name="runNode"
					id="runNode" class="easyui-validatebox BigInput" size="25" >&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2">
				 <div  align="left" style="padding-left:10px">
				 	<input id="saveBtn" type="button" value="返回" class="btn-win-white" onclick="window.location = 'index.jsp';">&nbsp;&nbsp;  
					<input id="saveBtn" type="button" value="保存" class="btn-win-white" onclick="doSave();">&nbsp;&nbsp;  
				  </div>
				</td>
			</tr>
		</table>
	</form>
	
	<script>
		$("#form1").validate();
	</script>
</body>

</html>