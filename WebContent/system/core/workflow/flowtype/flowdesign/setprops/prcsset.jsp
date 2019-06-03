<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;
function doInit(){
	
	
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fpInfo = json.rtData;

	//加载回退步骤下拉框
    loadProcessList(fpInfo.flowTypeId);
	//alert(fpInfo.backTo);
    if(fpInfo.goBack==3){ 	
    	//显示指定回退步骤那一行
      	 $("#backToTr").show(); 
    	
    }else{    	
    	//隐藏指定回退步骤那一行
   	 $("#backToTr").hide(); 
    	
    }
	
	bindJsonObj2Cntrl(fpInfo);
	
	var beginUserAlert = fpInfo.beginUserAlert;
	var allPrcsUserAlert = fpInfo.allPrcsUserAlert;
	var nextPrcsAlert = fpInfo.nextPrcsAlert;
	var sealRules = fpInfo.sealRules;
	
	for(var i=1;i<=2;i++){
		if((i & beginUserAlert) == i){
			$("#beginUserAlert"+i).attr("checked","checked");
		}
		if((i & allPrcsUserAlert) == i){
			$("#allPrcsUserAlert"+i).attr("checked","checked");
		}
		if((i & nextPrcsAlert) == i){
			$("#nextPrcsAlert"+i).attr("checked","checked");
		}
	}
	
	if((4 & beginUserAlert) == 4){
		$("#beginUserAlert"+3).attr("checked","checked");
	}
	if((4 & allPrcsUserAlert) == 4){
		$("#allPrcsUserAlert"+3).attr("checked","checked");
	}
	if((4 & nextPrcsAlert) == 4){
		$("#nextPrcsAlert"+3).attr("checked","checked");
	}

	var officePriv = fpInfo.officePriv;
	
	$("#officePrivDiv input[type=checkbox]").each(function(i,obj){
		var priv = obj.value;
		if((priv & officePriv) == priv){
			obj.checked = "checked";
		}
	});
	
	//附件权限渲染
	var attachPriv=fpInfo.attachPriv;
	$("#attachPrivTd input[type=checkbox]").each(function(i,obj){
		var priv = obj.value;
		if((priv & attachPriv) == priv){
			obj.checked = "checked";
		}
	});
	
	//加载盖章规则
	var json = tools.requestJsonRs(contextPath + '/sealManage/getSealList.action?sort=createTime');
	for(var i=0;i<json.rows.length;i++){
		$("#sealRuleSelect").append("<option value='"+json.rows[i].sealId+"'>"+json.rows[i].sealName+"</option>");
	}
	
	//加载签批单
	var json = tools.requestJsonRs(contextPath+"/flowPrintTemplate/selectModulByFlowType.action?flowTypeId="+flowId);
	var list = json.rtData;
	for(var i=0;i<list.length;i++){
		var item = list[i];
		$("#aipSelect").append("<option value='"+item.sid+"'>"+item.modulName+"</option>");
	}
	
	//加载规则
	var rules = eval("("+sealRules+")");
	for(var i=0;i<rules.length;i++){
		var aipId = rules[i].aipId;
		var aipName = rules[i].aipName;
		var sealId = rules[i].sealId;
		var sealName = rules[i].sealName;
		
		var html = ["<tr aipId='"+aipId+"' aipName='"+aipName+"' sealId='"+sealId+"' sealName='"+sealName+"'>"];
		html.push("<td>"+aipName+"</td>");
		html.push("<td>"+sealId+"</td>");
		html.push("<td>"+sealName+"</td>");
		html.push("<td><a href='javascript:void(0)' onclick='$(this).parent().parent().remove()'>删除</a></td>");
		html.push("</tr>");
		
		$("#sealRulesTb").append(html.join(""));
	}

}

//提交
function commit(){
	var url = contextPath+"/flowProcess/updatePrcsSet.action";
	var para = tools.formToJson($("#body"));
	para["prcsSeqId"] = prcsId;
	para["nextPrcsAlert"] = getNextPrcsAlert();
	para["beginUserAlert"] = getBeginUserAlert();
	para["allPrcsUserAlert"] = getAllPrcsUserAlert();
	para["officePriv"] = getOfficePriv();
	para["alarmUserIds"] = $("#alarmUserIds").val();
	para["alarmDeptIds"] = $("#alarmDeptIds").val();
	para["alarmRoleIds"] = $("#alarmRoleIds").val();
	
	//组合盖章规则
	var arr = [];
	$("#sealRulesTb tr").each(function(i,obj){
		var obj = $(obj);
		arr.push({aipId:obj.attr("aipId"),aipName:obj.attr("aipName"),sealId:obj.attr("sealId"),sealName:obj.attr("sealName")});
	});
	para["sealRules"] = tools.jsonArray2String(arr);
	
	
	//附件权限控制
	var attachPriv = 0;
	$("#attachPrivTd input[type=checkbox]:checked").each(function(i,obj){
		attachPriv+=parseInt(obj.value);
	});
	//alert(attachPriv);
	para["attachPriv"]=attachPriv;
	
	var json = tools.requestJsonRs(url,para);
	
}



//获取下一步经办人提醒权限值
function getNextPrcsAlert(){
	var priv = 0;
	if($("#nextPrcsAlert1").attr("checked")){
		priv+=1;
	}

	if($("#nextPrcsAlert2").attr("checked")){
		priv+=2;
	}

	if($("#nextPrcsAlert3").attr("checked")){
		priv+=4;
	}
	return priv;
}
//获取发起人提醒权限值
function getBeginUserAlert(){
	var priv = 0;
	if($("#beginUserAlert1").attr("checked")){
		priv+=1;
	}

	if($("#beginUserAlert2").attr("checked")){
		priv+=2;
	}

	if($("#beginUserAlert3").attr("checked")){
		priv+=4;
	}
	return priv;
}
//获取所有经办人提醒权限值
function getAllPrcsUserAlert(){
	var priv = 0;
	if($("#allPrcsUserAlert1").attr("checked")){
		priv+=1;
	}

	if($("#allPrcsUserAlert2").attr("checked")){
		priv+=2;
	}

	if($("#allPrcsUserAlert3").attr("checked")){
		priv+=4;
	}
	return priv;
}

function getOfficePriv(){
	var priv = 0;
	$("#officePrivDiv input[type=checkbox]:checked").each(function(i,obj){
		priv+=parseInt(obj.value);
	});
	return priv;
}

//回退选项的onchange事件
function showStep(){
   //获取回退选项的值  
   var goBack=$("#goBack").val();
   if(goBack=="3"){
	   $("#backToTr").show();  
   }else{
	   $("#backToTr").hide();   
   }
}


//获取步骤的列表  排除当前的步骤
function loadProcessList(flowId){
	
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessList.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var list = json.rtData;
	if(json.rtState){
		
		//渲染步骤列表
		var render = [];
		for(var i=0;i<list.length;i++){
			var data = list[i];
			if(data.sid!=prcsId&&data.prcsId!=0){
				render.push("<option value='"+data.prcsId+"'>"+data.prcsName+"</option>");			
			}else{
				render.push("");
			}
		}
		//alert(render.length);
		$("#backTo").html(render.join(""));
	}
}

/**
 * 添加盖章规则
 */
function addSealRule(){
	var aipId = $("#aipSelect").val();
	var aipName = $("#aipSelect option:selected").html();
	var sealId = $("#sealRuleSelect").val();
	var sealName = $("#sealRuleSelect option:selected").html();
	
	var html = ["<tr aipId='"+aipId+"' aipName='"+aipName+"' sealId='"+sealId+"' sealName='"+sealName+"'>"];
	html.push("<td>"+aipName+"</td>");
	html.push("<td>"+sealId+"</td>");
	html.push("<td>"+sealName+"</td>");
	html.push("<td><a href='javascript:void(0)' onclick='$(this).parent().parent().remove()'>删除</a></td>");
	html.push("</tr>");
	
	$("#sealRulesTb").append(html.join(""));
}
</script>

</head>
<body onload="doInit()" id="body">
<table class="TableBlock" width="100%" align="center">
   <tr class="TableHeader"><td colspan="2"><b>基本设置</b></td></tr>
   <tr>
    <td  class="TableData" width="120">步骤事件定义：</td>
    <td  class="TableData">
       <input type="text" id="prcsEventDef" name="prcsEventDef" class="BigInput"/>&nbsp;例：同意,拒绝,退回
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">办理方式：</td>
    <td  class="TableData">
       <select id="opFlag" name="opFlag" class="BigSelect">
			<option value="1">指定主办人</option>
			<option value="2">无主办人办理会签</option>
			<option value="3">先接收的人为主办</option>
			<option value="4">抢占式办理</option>
		</select>
		<br/>
		<b>是否允许修改办理方式：</b>
		<select id="userLock" name="userLock" class="BigSelect">
			<option value="1">不允许</option>
			<option value="0">允许</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">串签设置：</td>
    <td  class="TableData">
       <select id="seqOper" name="seqOper" class="BigSelect">
			<option value="0">关闭</option>
			<option value="1">开启</option>
		</select>
		<br/>
    	开启串签后，根据转交时所选择的人员先后顺序进行串签。
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">会签设置：</td>
    <td  class="TableData">
       <b>是否允许会签：</b>
		<select id="feedback" name="feedback" class="BigSelect">
			<option value="1">允许</option>
			<option value="0">不允许</option>
			<option value="2">强制会签</option>
		</select>
		<br/>
		<b>会签数据权限：</b>
		<select id="feedbackViewType" name="feedbackViewType" class="BigSelect">
			<option value="1">所有步骤皆可见</option>
			<option value="2">本步骤经办人之间不可见</option>
			<option value="3">本步骤可见</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">强制转交：</td>
    <td  class="TableData">
       <b>经办人未办理完毕时是否允许主办人强制转交： </b>
		<select id="forceTurn" name="forceTurn" class="BigSelect">
			<option value="1">允许</option>
			<option value="0">不允许</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">回退选项：</td>
    <td  class="TableData">
       <select id="goBack" name="goBack" class="BigSelect"  onchange="showStep();">
			<option value="0">不允许</option>
			<option value="1">允许回退上一步</option>
			<option value="2">允许回退之前步骤</option>
			<option value="3">允许回退到指定步骤</option>
		</select>
    </td>
   </tr>
   
	<tr id="backToTr" >
	    <td  class="TableData" width="120">指定回退步骤：</td>
	    <td  class="TableData">
	       <select id="backTo" name="backTo" class="BigSelect">
				
			</select>
	    </td>
	</tr>
  



   <tr>
    <td  class="TableData" width="120">是否允许归档：</td>
    <td  class="TableData">
       <select id="archivesPriv" name="archivesPriv" class="BigSelect">
			<option value="0">不允许</option>
			<option value="1">允许</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">加签权限：</td>
    <td  class="TableData">
       <select id="addPrcsUserPriv" name="addPrcsUserPriv" class="BigSelect">
			<option value="1">本步骤办理人员</option>
			<option value="2">全部人员</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">是否允许修改流程名称：</td>
    <td  class="TableData">
    	<select name="runNamePriv" id="runNamePriv" class="BigSelect">
    		<option value="0">不允许</option>
    		<option value="1">允许</option>
    	</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">自动选人：</td>
    <td  class="TableData">
    	<select name="autoSelect" id="autoSelect" class="BigSelect">
    		<option value="0">不允许</option>
    		<option value="1">允许</option>
    	</select>
    	<br/>
    	开启自动选人后，如果存在相关办理权限的人员，则系统自动在转交步骤时回填相关有权限的人员，否则不进行回填（可与一键转交功能配合使用）
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">多人只选第一个：</td>
    <td  class="TableData">
    	<select name="autoSelectFirst" id="autoSelectFirst" class="BigSelect">
    		<option value="0">关闭</option>
    		<option value="1">开启</option>
    	</select>
    	<br/>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">一键转交：</td>
    <td  class="TableData">
    	<select name="autoTurn" id="autoTurn" class="BigSelect">
    		<option value="0">不允许</option>
    		<option value="1">允许</option>
    	</select>
    	<br/>
    	开启一键转交后，当仅过滤出一个人并且办理方式为任意三种情况，都可以进行自动转交，无需选人。<br/>
    	当过滤出多个人并且办理方式为“无主办会签”和“先接收为主办”时，也可进行自动转交，无需选人。<br/>
    	当过滤出多个人并且办理方式为“指定主办人”时，此时无法自动转交，需要手动人后方可转交。
    </td>
   </tr>
   <tr class="TableHeader"><td colspan="2"><b>超时办理</b></td></tr>
   <tr>
    <td  class="TableData" width="120">办理超时时限：</td>
    <td  class="TableData">
    	<input type="text" id="timeout" name="timeout" class="BigInput"/>小时（0为永不超时）
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">转交时可修改超时时限：</td>
    <td  class="TableData">
    	<select id="timeoutFlag" name="timeoutFlag"  class="BigSelect">
			<option value="1">允许</option>
			<option value="0">不允许</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">超时时限计算规则：</td>
    <td  class="TableData">
    	<select id="timeoutType" name="timeoutType" class="BigSelect">
			<option value="1">本步骤办理人接收后开始计时</option>
			<option value="2">上一步骤办理完并且转交后开始计时</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">办理时是否排除双休日：</td>
    <td  class="TableData">
    	<select id="ignoreType" name="ignoreType" class="BigSelect">
			<option value="1">是</option>
			<option value="0">否</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">超时后是否允许继续办理：</td>
    <td  class="TableData">
    	<select id="timeoutHandable" name="timeoutHandable"  class="BigSelect">
			<option value="1">允许</option>
			<option value="0">不允许</option>
		</select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">超时预警：</td>
    <td  class="TableData">
    	提前 <input type="text" id="timeoutAlarm" name="timeoutAlarm" class="BigInput"/> 分钟提醒（0为不预警）
    </td>
   </tr>
   <tr class="TableHeader"><td colspan="2"><b>附件权限</b></td></tr>
   <tr>
    <td class="TableData" width="120">正文操作权限：</td>
    <td class="TableData" id="officePrivDiv">
    	<input type="checkbox" value="1"/>查看
    	<input type="checkbox" value="2"/>创建
    	<input type="checkbox" value="4"/>保存(人工)
    	<!-- <input type="checkbox" value="8"/>保留痕迹(人工)
    	<input type="checkbox" value="16"/>不留痕迹(人工) -->
    	<input type="checkbox" value="32"/>显示痕迹(人工)
    	<br/>
    	<input type="checkbox" value="64"/>隐藏痕迹(人工)
    <!-- 	<input type="checkbox" value="128"/>保留痕迹(自动)
    	<input type="checkbox" value="256"/>不留痕迹(自动) -->
    	<input type="checkbox" value="512"/>显示痕迹(自动)
    	<input type="checkbox" value="1024"/>隐藏痕迹(自动)
    	<input type="checkbox" value="2048"/>只读(人工)
    	<br/>
    	<input type="checkbox" value="4096"/>只读(自动)
    	<input type="checkbox" value="8192"/>取消只读(自动)
    	<input type="checkbox" value="16384"/>套红(人工)
    	<input type="checkbox" value="32768"/>严禁拷贝(自动)
    	<br/>
    	
    	<input type="checkbox" value="65536"/>接受修订(人工)
    	<input type="checkbox" value="131072"/>接受修订(自动)
    	<input type="checkbox" value="262144"/>转版式正文
    	<br/>
    	
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">附件权限控制：</td>
    <td  class="TableData" id="attachPrivTd">
    	<!-- <select name="" id="attachPriv" class="BigSelect">
    		<option value="0">不允许</option>
    		<option value="1">允许</option>
    	</select> -->
    	<input  class="attPriv" type="checkbox"   value="1" />查看&nbsp;&nbsp;
    	<input  class="attPriv" type="checkbox"   value="2" />下载&nbsp;&nbsp;
    	<input  class="attPriv" type="checkbox"   value="4" />删除&nbsp;&nbsp;
    	<input  class="attPriv" type="checkbox"   value="8" />编辑&nbsp;&nbsp;
    	<input  class="attPriv" type="checkbox"   value="16" />上传
    </td>
   </tr>
    <tr>
    <td  class="TableData" width="120">是否允许修改非本人创建的公共附件：</td>
    <td  class="TableData">
    	<select name="attachOtherPriv" id="attachOtherPriv" class="BigSelect">
    		<option value="0">不允许</option>
    		<option value="1">允许</option>
    	</select>
    </td>
   </tr>
   <tr class="TableHeader"><td colspan="2"><b>提醒设置</b></td></tr>
   <tr>
   	<td  class="TableData" width="120">事务提醒：</td>
    <td  class="TableData">
			提醒下一步办理人：
			<input id="nextPrcsAlert1" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/sms.png" />
			<input id="nextPrcsAlert2" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/mobile_sms.png" />
			<input id="nextPrcsAlert3" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/email.png" />
			<br/>
			提醒流程发起人：
			<input id="beginUserAlert1" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/sms.png" />
			<input id="beginUserAlert2" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/mobile_sms.png" />
			<input id="beginUserAlert3" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/email.png" />
			<br/>
			提醒全部经办人：
			<input id="allPrcsUserAlert1" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/sms.png" />
			<input id="allPrcsUserAlert2" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/mobile_sms.png" />
    		<input id="allPrcsUserAlert3" type="checkbox" /><img src="<%=contextPath %>/common/images/workflow/email.png" />
    </td>
   </tr>
    <tr>
   	<td  class="TableData" width="120">转交时内部邮件通知指定人员：</td>
    <td  class="TableData">
    	通知范围（人员）<br/>
    	<textarea class="BigTextarea readonly" id="alarmUserNames" name="alarmUserNames" style="width:300px;height:50px;" readonly></textarea>
    	<input type="hidden" id="alarmUserIds" name="alarmUserIds"/>
    	<a href="javascript:void(0)" onclick="selectUser(['alarmUserIds','alarmUserNames'])">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('alarmUserIds','alarmUserNames')">清空</a>
    	<br/><br/>
    	通知范围（部门）<br/>
    	<textarea class="BigTextarea readonly" id="alarmDeptNames" name="alarmDeptNames" style="width:300px;height:50px;" readonly></textarea>
    	<input type="hidden" id="alarmDeptIds" name="alarmDeptIds"/>
    	<a href="javascript:void(0)" onclick="selectDept(['alarmDeptIds','alarmDeptNames'])">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('alarmDeptIds','alarmDeptNames')">清空</a>
    	<br/><br/>
    	通知范围（角色）<br/>
    	<textarea class="BigTextarea readonly" id="alarmRoleNames" name="alarmRoleNames" style="width:300px;height:50px;" readonly></textarea>
    	<input type="hidden" id="alarmRoleIds" name="alarmRoleIds"/>
    	<a href="javascript:void(0)" onclick="selectRole(['alarmRoleIds','alarmRoleNames'])">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('alarmRoleIds','alarmRoleNames')">清空</a>
    </td>
   </tr>
   <tr class="TableHeader"><td colspan="2"><b>服务端盖章规则</b></td></tr>
   <tr>
    <td  class="TableData" width="120">使用规则：</td>
    <td  class="TableData">
    	签批单模板：<select id="aipSelect" class="BigSelect">
    	</select>&nbsp;&nbsp;
    	签章规则：<select id="sealRuleSelect" class="BigSelect">
    	</select>
    	&nbsp;&nbsp;
    	<a href="javascript:void(0)" onclick="addSealRule()">添加规则</a>
    	<div>
    		<table style="width:100%" class="TableBlock">
    			<tr class="TableHeader">
    				<td>签批单</td>
    				<td>规则号</td>
    				<td>规则名称</td>
    				<td>操作</td>
    			</tr>
    			<tbody id="sealRulesTb">
    				
    			</tbody>
    		</table>
    	</div>
    </td>
   </tr>
</table>
</body>
</html>