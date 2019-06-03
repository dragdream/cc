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

var prcsList;
var items;

function doInit(){
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessList.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	prcsList = json.rtData;
	
	//获取对应表单字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	items = json.rtData;
	
	var url = contextPath+"/flowProcess/getPrcsPriv.action";
	var json = tools.requestJsonRs(url,{prcsId:prcsId});
	var prcsUser = json.rtData.prcsUser;
	var ids = "";
	var names = "";
	for(var i=0;i<prcsUser.length;i++){
		ids+=prcsUser[i].value+",";
		names+=prcsUser[i].name+",";
	}
	$("#prcsUser").attr("value",ids);
	$("#prcsUserDesc").attr("value",names);
	ids="";
	names="";
	
	var prcsDept = json.rtData.prcsDept;
	var data = "";
	for(var i=0;i<prcsDept.length;i++){
		ids+=prcsDept[i].value+",";
		names+=prcsDept[i].name+",";
	}
	$("#prcsDept").attr("value",ids);
	$("#prcsDeptDesc").attr("value",names);
	ids="";
	names="";

	var prcsRole = json.rtData.prcsRole;
	var data = "";
	for(var i=0;i<prcsRole.length;i++){
		ids+=prcsRole[i].value+",";
		names+=prcsRole[i].name+",";
	}
	$("#prcsRole").attr("value",ids);
	$("#prcsRoleDesc").attr("value",names);
	ids="";
	names="";

	//过滤规则
	var prcsSelectRule = json.rtData.prcsSelectRule;
	var data = "";
	for(var i=0;i<prcsSelectRule.length;i++){
		var sp = prcsSelectRule[i].split("-");
		var userType = sp[0];
		var userDept = sp[1];
		var m = eval("("+sp[2]+")");
		data+="<option value='"+(userType+"-"+userDept+"-"+(m.uuid==""?"0":m.uuid))+"'>"+"["+getUserTypeDesc(userType)+"]["+getDeptTypeDesc(userDept)+"]["+(m.uuid==""?"":m.name)+"]"+"</option>";
	}
	$("#selectUserRule").html($(data));
	
	//自动选人规则
	var prcsAutoSelectRule = json.rtData.prcsAutoSelectRule;
	var autoData = "";
	for(var i=0;i<prcsAutoSelectRule.length;i++){
		var sp = prcsAutoSelectRule[i].split("-");
		var autoType = sp[0];
		
		if(autoType == '1'){//自动选择流程发起人
			autoData+="<option value='1'>[自动选择流程发起人]</option>";
		}else if(autoType == '2'){//自动选择本部门主管
			var prcsInfo = getPrcsInfoById(sp[1]);
			var prcsName = prcsInfo==undefined?"当前步骤":prcsInfo.prcsName;
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[自动选择本部门主管]["+prcsName+"]</option>";
		}else if(autoType == '3'){//自动选择本部门分管领导
			var prcsInfo = getPrcsInfoById(sp[1]);
		var prcsName = prcsInfo==undefined?"当前步骤":prcsInfo.prcsName;
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[自动选择本部门分管领导]["+prcsName+"]</option>";
		}else if(autoType == '4'){//自动选择上级部门主管领导
			var prcsInfo = getPrcsInfoById(sp[1]);
			var prcsName = prcsInfo==undefined?"当前步骤":prcsInfo.prcsName;
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[自动选择上级部门主管领导]["+prcsName+"]</option>";
		}else if(autoType == '5'){//自动选择上级部门分管领导
			var prcsInfo = getPrcsInfoById(sp[1]);
			var prcsName = prcsInfo==undefined?"当前步骤":prcsInfo.prcsName;
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[自动选择上级部门分管领导]["+prcsName+"]</option>";
		}else if(autoType == '6'){//选择人员
			var m = eval("("+sp[1]+")");
			while(m.prcsUserIds.indexOf(",")!=-1){
				m.prcsUserIds = m.prcsUserIds.replace(",","^");
			}
			autoData+="<option value='"+(autoType+"-"+ (m.opUserId == "" ? "0" : m.opUserId) +"-"+(m.prcsUserIds==""?"0":m.prcsUserIds))+"'>"+"["+getSelectAutoTypeDesc(autoType)+"][主办人:"+ m.opUserName + ";经办人:" + m.prcsUserNames +"]"+"</option>";	
		}else if(autoType == '7'){//按表单字段选择
			var itemInfo = getItemInfoById(sp[1]);
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[按表单字段选择]["+itemInfo.title+"]</option>";
		}else if(autoType == '8'){//自动选择指定步骤主办人
			var prcsInfo = getPrcsInfoById(sp[1]);
			var prcsName = prcsInfo.prcsName;
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[自动选择指定步骤主办人]["+prcsName+"]</option>";
		}else if(autoType == '9'){//自动选择本部门内有权限的人员
			var prcsInfo = getPrcsInfoById(sp[1]);
			var prcsName = prcsInfo==undefined?"当前步骤":prcsInfo.prcsName;
			autoData+="<option value='"+prcsAutoSelectRule[i]+"'>[自动选择本部门内有权限的人员]["+prcsName+"]</option>";
		}
		
	}
	$("#autoSelectUser").html($(autoData));
	
	var prcsAutoSelectUser1 = json.rtData.prcsAutoSelectRule1;
	prcsAutoSelectUser1 = eval("("+prcsAutoSelectUser1+")");
	if(prcsAutoSelectUser1!=null){
		for(var i=0;i<prcsAutoSelectUser1.length;i++){
			var value = prcsAutoSelectUser1[i].value;
			var regexp = /\[[^\[]+\]/gi;
			var target = regexp.exec(value);
			var dom = "<option value='"+value+"'>";
			if(target=="[关联人员]"){
				var p1 = regexp.exec(value)+"";
				p1 = p1.split("|")[0]+"]";
				var p2 = regexp.exec(value)+"";
				p2 = p2.split("|")[0]+"]";
				dom+="[关联人员]"+p1+p2;
				
			}else if(target=="[关联部门]"){
				var p1 = regexp.exec(value)+"";
				p1 = p1.split("|")[0]+"]";
				var p2 = regexp.exec(value)+"";
				p2 = p2.split("|")[0]+"]";
				dom+="[关联部门]"+p1+p2;
			}else if(target=="[关联角色]"){
				var p1 = regexp.exec(value)+"";
				p1 = p1.split("|")[0]+"]";
				var p2 = regexp.exec(value)+"";
				p2 = p2.split("|")[0]+"]";
				dom+="[关联角色]"+p1+p2;
			}else if(target=="[关联表单字段]"){
				var p1 = regexp.exec(value)+"";
				var p2 = regexp.exec(value)+"";
				var p3 = regexp.exec(value)+"";
				p3 = p3.split("|")[0]+"]";
				dom+="[关联表单字段]"+p1+p2+p3;
			}
			
			dom+="</option>";
			$("#autoSelectUser1").append(dom);
		}
	}
	
	changeToNormal();
	
	$("[title]").tooltips();
}

function getPrcsInfoById(id){
	for(var i=0;i<prcsList.length;i++){
		if((id+"")==(prcsList[i].sid+"")){
			return prcsList[i];
		}
	}
}

function getItemInfoById(id){
	for(var i=0;i<items.length;i++){
		if((id+"")==(items[i].itemId+"")){
			return items[i];
		}
	}
}

function getUserTypeDesc(userType){
	switch(userType){
	case '0':return "无约束";
	case '1':return "流程发起人";
	case '2':return "当前办理人";
// 	case '3':return "该流程所有办理人";
// 	case '4':return "本步骤办理人";
// 	case '5':return "上一步骤办理人";
	}
}

function getDeptTypeDesc(deptType){
	 switch(deptType){
		case '1':return "无约束";
		case '2':return "所属部门";
		case '3':return "所属部门主管领导";
		case '4':return "所属部门分管领导";
		case '5':return "上级部门";
		case '6':return "上级部门主管领导";
		case '7':return "上级部门分管领导";
		case '8':return "下级部门";
		case '9':return "下级部门主管领导";
		case '10':return "下级部门分管领导";
		case '11':return "本部门及上N级递归父部门";
		case '12':return "上N级递归父部门";
		case '13':return "直属上级";
		case '14':return "直属下级";
		} 
}
/**
 * 获取自动选人描述
 */
function getSelectAutoTypeDesc(autoType){
	switch(autoType){
	case '1':return "自动选择流程发起人";
	case '2':return "自动选择本部门主管";
	case '3':return "自动选择本部门分管领导";
	case '4':return "自动选择上级部门主管领导";
	case '5':return "自动选择上级部门分管领导";
	case '6':return "自动选择默认人员";
	case '7':return "按表单字段选择";
	case '8':return "自动选择指定步骤主办人";
	case '9':return "自动选择本部门内有权限的人员";
	} 
}

//提交
function commit(){
	var url = contextPath+"/flowProcess/addPrcsPriv.action";
	var para = {prcsId:prcsId};
	//组合经办人员请求数据
	para["prcsUser"] = $("#prcsUser").val();
	
	//组合经办部门请求数据
	para["prcsDept"] = $("#prcsDept").val();

	//组合经办角色请求数据
	para["prcsRole"] = $("#prcsRole").val();

	//组合经办规则请求数据
	arr = new Array();
	$("#selectUserRule option").each(function(i,obj){
		arr.push(obj.value);
	});
	para["prcsUserSelectRule"] = arr+"";
	
	
	//组合自动选人请求数据
	arr = new Array();
	$("#autoSelectUser option").each(function(i,obj){
		arr.push(obj.value);
	});
	para["prcsAutoSelectUser"] = arr+"";

	//组合高级自动选人请求数据
	var arr = new Array();
	$("#autoSelectUser1 option").each(function(i,obj){
		var o = {};
		o.value=obj.value
		arr.push(o);
	});
	para["prcsAutoSelectUser1"] = tools.jsonArray2String(arr);
	
	var json = tools.requestJsonRs(url,para);
// 	if(json.rtState){
// 		top.$.jBox.tip("保存成功","success");
// 	}else{
// 		top.$.jBox.tip(json.rtMsg,"error");
// 	}
}

function addRuleData(){
	var userType = $("#userType").val();
	var userTypeDesc = $("#userType option:selected").html();
	var deptType = $("#deptType").val();
	var deptTypeDesc = $("#deptType option:selected").html();
	var role = $("#selectRole").val();
	role = role==""?"0":role;
	var roleDesc = $("#selectRoleDesc").val();
	var opts = "<option value='"+userType+"-"+deptType+"-"+role+"'>"+"["+userTypeDesc+"]"+"["+deptTypeDesc+"]"+"["+roleDesc+"]"+"</option>";
	if($("#selectUserRule option[value='"+userType+"-"+deptType+"-"+role+"']").length!=0){
		return;
	}
	$("#selectUserRule").append($(opts));
}

function removeRuleData(){
	$("#selectUserRule option:selected").each(function(i,obj){
		$(obj).remove();
	});
}

function removePrcsUser(){
	$("#prcsUser option:selected").each(function(i,obj){
		$(obj).remove();
	});
}

function removePrcsDept(){
	$("#prcsDept option:selected").each(function(i,obj){
		$(obj).remove();
	});
}

function removePrcsRole(){
	$("#prcsRole option:selected").each(function(i,obj){
		$(obj).remove();
	});
}

/**
 * 改变自动选人类型
    <option value="1">自动选择流程发起人</option>
	<option value="2">自动选择本部门主管</option>
	<option value="3">自动选择本部门分管领导</option>
	<option value="4">自动选择上级部门主管领导</option>
	<option value="5">自动选择上级部门分管领导</option>
	<option value="6">自动选择默认人员</option>
	<option value="7">按表单字段选择</option>
	<option value="8">自动选择指定步骤主办人</option>
	<option value="9">自动选择本部门内有权限的人员</option>
 */
function selectAutoTypeChange(value){
	var selectAutoTypeInfo = $("#selectAutoTypeInfo");
	selectAutoTypeInfo.empty();

	if(value == '1'){//自动选择流程发起人
		
	}else if(value == '2'){//自动选择本部门主管
		renderPrcsSelectCtrl(true);
	}else if(value == '3'){//自动选择本部门分管领导
		renderPrcsSelectCtrl(true);
	}else if(value == '4'){//自动选择上级部门主管领导
		renderPrcsSelectCtrl(true);
	}else if(value == '5'){//自动选择上级部门分管领导
		renderPrcsSelectCtrl(true);
	}else if(value == '6'){//选择人员

		selectAutoTypeInfo.append("<div>主办人:"
				+"<input type='hidden' name='autoOpUser' id='autoOpUser'><input readonly type='text' name='autoOpUserName'  class='BigInput readonly' id='autoOpUserName'>"
				+"<a href=\"javascript:void(0);\" class=\"orgAdd \" onClick=\"selectSingleUser(['autoOpUser', 'autoOpUserName'])\">添加</a>"
				+"&nbsp;&nbsp;<a href=\"javascript:void(0);\" class=\"orgAdd\" onClick=\"clearData('autoOpUser', 'autoOpUserName')\">删除</a>"
				+"</div>"
				+"<div>经办人:"
				+"<input type='hidden' name='autoPrcsUser' id='autoPrcsUser'><textarea  readonly name='autoPrcsUserName' id='autoPrcsUserName'  class='BigTextarea readonly' cols='30' rows='3'/>"
				+"<a href=\"javascript:void(0);\" class=\"orgAdd\" onClick=\"selectUser(['autoPrcsUser', 'autoPrcsUserName'])\">添加</a>"
				+"&nbsp;&nbsp;<a href=\"javascript:void(0);\" class=\"orgAdd\" onClick=\"clearData('autoPrcsUser', 'autoPrcsUserName')\">删除</a>"
				+"</div>");
	}else if(value == '7'){//选择表单字段值
		renderItemsSelectCtrl();
	}else if(value == '8'){//自动选择指定步骤主办人
		renderPrcsSelectCtrl(false);
	}else if(value == '9'){//自动选择本部门内有权限的人员
		renderPrcsSelectCtrl(true);
	}
}

/**
 * 渲染步骤select控件
 */
function renderPrcsSelectCtrl(isCurPrcs){
	var html = "<b>针对对象：</b><br/><select id='prcsSelectCtrl' class='BigSelect'>";
	if(isCurPrcs){
		html+="<option value='0'>当前步骤</option>";
	}
	for(var i=0;i<prcsList.length;i++){
		if(prcsList[i].prcsId==0){
			continue;
		}
		html+="<option value='"+prcsList[i].sid+"'>"+prcsList[i].prcsName+"</option>";
	}
	html+="</select>";
	$("#selectAutoTypeInfo").html(html);
}

function renderItemsSelectCtrl(){
	var html = "<b>表单控件：</b><br/><select id='itemsSelectCtrl' class='BigSelect'>";
	for(var i=0;i<items.length;i++){
		html+="<option value='"+items[i].itemId+"'>"+items[i].title+"</option>";
	}
	html+="</select>";
	$("#selectAutoTypeInfo").html(html);
}

/**
 * 新建自动选人
 */
function addAutoRuleData(){
	var autoType = $("#autoType").val();
	var autoTypeStr = autoType;
	var autoTypeDesc = $("#autoType option:selected").html();
	var opts = "";
	
	if(autoType == '1'){//自动选择流程发起人
		opts = "<option value='1'>[自动选择流程发起人]</option>";
	}else if(autoType == '2'){//自动选择本部门主管
		var prcsSid = $("#prcsSelectCtrl").val();
		var prcsName = $("#prcsSelectCtrl option:selected").html();
		opts = "<option value='2-"+prcsSid+"'>[自动选择本部门主管]["+prcsName+"]</option>";
	}else if(autoType == '3'){//自动选择本部门分管领导
		var prcsSid = $("#prcsSelectCtrl").val();
		var prcsName = $("#prcsSelectCtrl option:selected").html();
		opts = "<option value='3-"+prcsSid+"'>[自动选择本部门分管领导]["+prcsName+"]</option>";
	}else if(autoType == '4'){//自动选择上级部门主管领导
		var prcsSid = $("#prcsSelectCtrl").val();
		var prcsName = $("#prcsSelectCtrl option:selected").html();
		opts = "<option value='4-"+prcsSid+"'>[自动选择上级部门主管领导]["+prcsName+"]</option>";
	}else if(autoType == '5'){//自动选择上级部门分管领导
		var prcsSid = $("#prcsSelectCtrl").val();
		var prcsName = $("#prcsSelectCtrl option:selected").html();
		opts = "<option value='5-"+prcsSid+"'>[自动选择上级部门分管领导]["+prcsName+"]</option>";
	}else if(autoType == '6'){//选择人员
		
		var opUser = $("#autoOpUser").val() == ""  ? "0" :  $("#autoOpUser").val()  ;
		var opUserDesc = $("#autoOpUserName").val();
		var prcsUser = $("#autoPrcsUser").val() == "" ? "0" : $("#autoPrcsUser").val();
		while(prcsUser.indexOf(",")!=-1){
			prcsUser = prcsUser.replace(",","^");
		}
		var prcsUserName = $("#autoPrcsUserName").val() ;
		autoTypeDesc = autoTypeDesc + "[主办人:" + opUserDesc + ";经办人:" + prcsUserName +"]";
		autoTypeStr = autoTypeStr + "-" + opUser + "-" + prcsUser;

		opts = "<option value='"+autoTypeStr+"'>"+"["+autoTypeDesc+"]"+"</option>";
		
	}else if(autoType == '7'){//按表单字段选择
		var itemId = $("#itemsSelectCtrl").val();
		var title = $("#itemsSelectCtrl option:selected").html();
		opts = "<option value='7-"+itemId+"'>[按表单字段选择]["+title+"]</option>";
	}else if(autoType == '8'){//自动选择指定步骤主办人
		var prcsSid = $("#prcsSelectCtrl").val();
		var prcsName = $("#prcsSelectCtrl option:selected").html();
		opts = "<option value='8-"+prcsSid+"'>[自动选择指定步骤主办人]["+prcsName+"]</option>";
	}else if(autoType == '9'){//自动选择本部门内有权限的人员
		var prcsSid = $("#prcsSelectCtrl").val();
		var prcsName = $("#prcsSelectCtrl option:selected").html();
		opts = "<option value='9-"+prcsSid+"'>[自动选择本部门内有权限的人员]["+prcsName+"]</option>";
	}
	
	
	$("#autoSelectUser").append($(opts)); 
}
/**
 * 删除自动选人规则
 */
function removeAutoRuleData(){
	$("#autoSelectUser option:selected").each(function(i,obj){
		$(obj).remove();
	});
}

function changeToSenior(){
	$("#normalRule1").hide();
	$("#normalRule2").hide();
	$("#seniorRule1").show();
	$("#seniorRule2").show();
	//$("body").css({scrollTop:1000});
}

function changeToNormal(){
	$("#normalRule1").show();
	$("#normalRule2").show();
	$("#seniorRule1").hide();
	$("#seniorRule2").hide();
	selectAutoTypeChange1("1");
	//$("body").css({scrollTop:1000});
}


</script>
<style>
.form-control{
font-size:12px;
}
</style>

</head>
<body onload="doInit()" id="body" >
<table class="TableBlock" width="100%" align="center">
   <tr class="TableHeader"><td colspan="2">选人维度权限</td></tr>
   <tr>
    <td  class="TableData" width="120">人员：</td>
    <td  class="TableData">
        <textarea id="prcsUserDesc" class="form-control" readonly style="width:100%;height:100px;background:#f0f0f0"></textarea>
		<input id="prcsUser" type="hidden"></input>
		<a href="javascript:void(0)" onclick="selectUser(['prcsUser','prcsUserDesc'])">添加</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="clearData('prcsUser','prcsUserDesc')">清除</a>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">部门：</td>
    <td  class="TableData" id="prcsType">
    	<textarea id="prcsDeptDesc" class="form-control" readonly style="width:100%;height:100px;background:#f0f0f0"></textarea>
		<input id="prcsDept" type="hidden"></input>
		<a href="javascript:void(0)" onclick="selectDept(['prcsDept','prcsDeptDesc'])">添加</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="clearData('prcsDept','prcsDeptDesc')">清除</a>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="120">角色：</td>
    <td  class="TableData">
    	<textarea id="prcsRoleDesc" class="form-control" readonly style="width:100%;height:100px;background:#f0f0f0"></textarea>
		<input id="prcsRole" type="hidden"></input>
		<a href="javascript:void(0)" onclick="selectRole(['prcsRole','prcsRoleDesc'])">添加</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="clearData('prcsRole','prcsRoleDesc')">清除</a>
    </td>
   </tr>
   <tr class="TableHeader"><td colspan="2">选人规则权限<i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="例：[无约束][无约束][总监]的意思是，选择出角色为总监的用户；<br/>[当前办理人][上级部门][总监]的意思是，<br/>选择出当前办理人上级部门角色为总监的用户。"></i> 
   </td></tr>
   <tr>
    <td nowrap class="TableData" width="120">人员：</td>
    <td nowrap class="TableData">
       <select id="userType" class="BigSelect">
       		<option value="0">无约束</option>
			<option value="1">流程发起人</option>
			<option value="2">当前办理人</option>
<!-- 			<option value="3">该流程所有办理人</option> -->
<!-- 			<option value="4">本步骤办理人</option> -->
<!-- 			<option value="5">上一步骤办理人</option> -->
		</select>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">部门：</td>
    <td nowrap class="TableData">
       <select id="deptType" class="BigSelect">
			<option value="1">无约束</option>
			<option value="2">所属部门</option>
			<option value="3">所属部门主管领导</option>
			<option value="4">所属部门分管领导</option>
			<option value="5">上级部门</option>
			<option value="6">上级部门主管领导</option>
			<option value="7">上级部门分管领导</option>
			<option value="8">下级部门</option>
			<option value="9">下级部门主管领导</option>
			<option value="10">下级部门分管领导</option>
			<option value="11">本部门及上N级递归父部门</option>
			<option value="12">上N级递归父部门</option>
			<option value="13">直属上级</option>
			<option value="14">直属下级</option>
		</select>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">角色：</td>
    <td nowrap class="TableData">
    	<input id="selectRoleDesc" type="text" class="BigInput" readonly/>
		<input type="hidden" id="selectRole"/>
		<a href="javascript:void()" onclick="selectSingleRole(['selectRole','selectRoleDesc'])">选择</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" colspan="2">
    	<select class="form-control"  id="selectUserRule" multiple style="width:100%"></select>
    	<a href="javascript:void(0)" onclick="addRuleData();">添加</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="removeRuleData();">清除</a>
    </td>
   </tr>
   <tr class="TableHeader" id="normalRule1"><td colspan="2">自动选人规则
   <i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="通过一定规则选择指定人员"></i>
   <a href='javascript:void(0)' onclick="changeToSenior()">(切换高级规则设定)</a></td></tr>
   <tr>
   	 <td  class="TableData" colspan="2" id="normalRule2">
   	 	<b>选人类型：</b><br/>
 		<select name="autoType" id="autoType" class="BigSelect" onchange="selectAutoTypeChange(this.value);">
 			<option value="1">自动选择流程发起人</option>
			<option value="2">自动选择本部门主管</option>
			<option value="3">自动选择本部门分管领导</option>
			<option value="4">自动选择上级部门主管领导</option>
			<option value="5">自动选择上级部门分管领导</option>
			<option value="6">自动选择选择默认人员</option>
			<option value="7">按表单字段选择</option>
			<option value="8">自动选择指定步骤主办人</option>
			<option value="9">自动选择本部门内有权限的人员</option>
	 		</select>
	 		
	 	<div id="selectAutoTypeInfo" style="padding:5px 0px 5px 5px;"></div>
	 	
	 	<select class="form-control"  multiple style="width:100%" id="autoSelectUser" name="autoSelectUser"></select>
   		<a href="javascript:void(0)" onclick="addAutoRuleData();">添加</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="removeAutoRuleData();">清除</a>
   	</td>
   	</tr> 
   <tr class="TableHeader" id="seniorRule1"><td colspan="2">高级选人规则 &nbsp;
   <i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="选人规则不受以上候选范围及过滤选人的约束，即可通过一定规则选择指定人员，应对有特殊需求的情况。"></i><a href='javascript:void(0)' onclick="changeToNormal()">(切换普通规则设定)</a></td></tr>
   <tr>
   	 <td  class="TableData" colspan="2" id="seniorRule2">
   	 	<b>关联对象：</b><br/>
 		<select id="selectAuto1" class="BigSelect" onchange="selectAutoTypeChange1(this.value);">
		<option value="1">关联人员</option>
		<option value="2">关联部门</option>
		<option value="3">关联角色</option>
		<option value="4">关联表单字段</option>
 		</select>
 		
 		<div id="selectAutoTypeInfo1" style="padding:5px 0px 5px 5px;"></div>
	 	
	 	<select class="form-control"  multiple style="width:100%" id="autoSelectUser1" name="autoSelectUser1"></select>
   		<a href="javascript:void(0)" onclick="addAutoRuleData1();">添加</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="removeAutoRuleData1();">清除</a>
   	</td>
   	</tr> 
</table>
</body>
<script>
function selectAutoTypeChange1(type){
	var dom = "";
	if(type=="1"){//关联人员
		dom += "<b>满足以下人员条件的话</b><br/><input type='hidden' class='BigInput' id='autoTypeUser'/>";
		dom+="<input type='text' readonly class='BigInput' id='autoTypeUserDesc' style='width:450px'/>";
		dom+="<a href='javascript:void(0)' onclick='selectUser([\"autoTypeUser\",\"autoTypeUserDesc\"])'>选择</a>";
	}else if(type=="2"){//关联部门
		dom += "<b>满足以下部门条件的话</b><br/><input type='hidden' class='BigInput' id='autoTypeUser'/>";
		dom+="<input type='text' readonly class='BigInput' id='autoTypeUserDesc' style='width:450px'/>";
		dom+="<a href='javascript:void(0)' onclick='selectDept([\"autoTypeUser\",\"autoTypeUserDesc\"])'>选择</a>";
	}else if(type=="3"){//关联角色
		dom += "<b>满足以下角色条件的话</b><br/><input type='hidden' class='BigInput' id='autoTypeUser'/>";
		dom+="<input type='text' readonly class='BigInput' id='autoTypeUserDesc' style='width:450px'/>";
		dom+="<a href='javascript:void(0)' onclick='selectRole([\"autoTypeUser\",\"autoTypeUserDesc\"])'>选择</a>";
	}else if(type=="4"){//关联表单字段
		var dom = "<b>满足如下表单控件的值</b><br/><select id='itemsSelectCtrl' class='BigSelect'>";
		for(var i=0;i<items.length;i++){
			dom+="<option value='"+items[i].itemId+"'>"+items[i].title+"</option>";
		}
		dom+="</select>";
		dom+="&nbsp;等于&nbsp;<input class='BigInput' id='itemsSelectCtrlValue'/>";
		
	}
	
	dom+="<br/><b>则自动选择如下办理人</b><br/>";
	dom+= "<input type='hidden' class='BigInput' id='targetTypeUser'/>";
	dom+="<input type='text' readonly class='BigInput' id='targetTypeUserDesc' style='width:450px'/>";
	dom+="<a href='javascript:void(0)' onclick='selectUser([\"targetTypeUser\",\"targetTypeUserDesc\"])'>选择</a>";
	
	$("#selectAutoTypeInfo1").html(dom);
}

function addAutoRuleData1(){
	var type = $("#selectAuto1").val();
	var dom = "";
	if(type=="1"){//关联人员
		dom+="<option value='[关联人员]["+$("#autoTypeUserDesc").val()+"|"+$("#autoTypeUser").val()+"]["+$("#targetTypeUserDesc").val()+"|"+$("#targetTypeUser").val()+"]'>[关联人员]["+$("#autoTypeUserDesc").val()+"]["+$("#targetTypeUserDesc").val()+"]</option>";
	}else if(type=="2"){//关联部门
		dom+="<option value='[关联部门]["+$("#autoTypeUserDesc").val()+"|"+$("#autoTypeUser").val()+"]["+$("#targetTypeUserDesc").val()+"|"+$("#targetTypeUser").val()+"]'>[关联部门]["+$("#autoTypeUserDesc").val()+"]["+$("#targetTypeUserDesc").val()+"]</option>";
	}else if(type=="3"){//关联角色
		dom+="<option value='[关联角色]["+$("#autoTypeUserDesc").val()+"|"+$("#autoTypeUser").val()+"]["+$("#targetTypeUserDesc").val()+"|"+$("#targetTypeUser").val()+"]'>[关联角色]["+$("#autoTypeUserDesc").val()+"]["+$("#targetTypeUserDesc").val()+"]</option>";
	}else if(type=="4"){//关联表单字段
		dom+="<option value='[关联表单字段]["+$("#itemsSelectCtrl option:selected").html()+"]["+$("#itemsSelectCtrlValue").val()+"]["+$("#targetTypeUserDesc").val()+"|"+$("#targetTypeUser").val()+"]'>[关联表单字段]["+$("#itemsSelectCtrl option:selected").html()+"]["+$("#itemsSelectCtrlValue").val()+"]["+$("#targetTypeUserDesc").val()+"]</option>";
	}
	
	$("#autoSelectUser1").append(dom);
}

function removeAutoRuleData1(){
	$("#autoSelectUser1 option:selected").each(function(i,obj){
		$(obj).remove();
	});
}
</script>
</html>