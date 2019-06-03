<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String industryType = request.getParameter("industryType")==null?"":request.getParameter("industryType");
	String cityCode = request.getParameter("cityCode")==null?"":request.getParameter("cityCode");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
var industryType="<%=industryType%>";
var cityCode="<%=cityCode%>";
function doInit(){
	getProvince();
	getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType");
	getCrmCodeByParentCodeNo("INDUSTRY_TYPE","industry");
	getCrmCodeByParentCodeNo("COMPANY_SCALE","companyScale");
	getCrmCodeByParentCodeNo("CUSTOMER_SOURCE","customerSource");
	getCrmCodeByParentCodeNo("SOURCE_OF_INVESTMENT","sourcesOfInvestment");
	getCrmCodeByParentCodeNo("RELATION_LEVEL","relationLevel");
	getCrmCodeByParentCodeNo("IMPORTANT_LEVEL","importantLevel");
	getCrmCodeByParentCodeNo("TRUST_LEVEL","trustLevel");
	getCrmCodeByParentCodeNo("SALES_MARKET","salesMarket");
	getCrmCodeByParentCodeNo("UNIT_TYPE","unitType");//单位性质
	if(industryType!=""){
		$("#industry").val(industryType);
	}
	if(cityCode!=""){
		if(cityCode.substring(cityCode.length-4, cityCode.length)=="0000"){
			$("#province").val(cityCode);
			getCity();
		}else if(cityCode.substring(cityCode.length-2, cityCode.length)=="00" && !cityCode.substring(cityCode.length-4, cityCode.length)=="0000"){
			$("#province").val(cityCode.substring(0, 2)+"0000");
			getCity();
			$("#city").val(cityCode);
		}else{
			$("#province").val(cityCode.substring(0, 2)+"0000");
			getCity();
			$("#city").val(cityCode.substring(0, 4)+"00");
		}
	}
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName);
	
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeCrmCustomerInfoController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			$("#province").val(json.rtData.province);
			getCity();
			bindJsonObj2Cntrl(json.rtData);
			getContactUerList(sid);
		}
	}
}

function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var temp = getContactUserItem();
		var str = tools.jsonArray2String(temp.arrayTemp);
		var sids = temp.ids;
		param["contactUserList"]=str;
		param["idsTemp"]=sids;
		var url = contextPath+"/TeeCrmCustomerInfoController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success");
			goBack();
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
		}
		
	}
}

function getContactUerList(customerId){
	var url = "<%=contextPath%>/TeeCrmContactUserController/getContactUserList.action?customerId="+customerId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var contactUserList = json.rtData;
		$.each( contactUserList, function(i, item){
			addContactUser(item);
		});
	}
}

function addContactUser(item){
	var contactUserSid ="0";
	var contactUserName = "";
	var contactUserTel = "";
	var contactUserMobile = "";
	var contactUserDept = "";
	var contactUserEmail = "";
	if(item){
		 contactUserSid=item.sid;
		 contactUserName = item.name;
		 contactUserTel =item.telephone;
		 contactUserMobile = item.mobilePhone;
		 contactUserDept = item.department;
		 contactUserEmail = item.email;
	}
	var contactUserSId=$("<input>",{
		type:'hidden'
	}).addClass("BigInput").val(contactUserSid);
	
	var contactUserNameTd=$("<td>").append(contactUserSId).append($("<input>",{
		type:'text'
	}).addClass("BigInput easyui-validatebox").validatebox({ 
		required:true 
	}).val(contactUserName));

	var contactUserTelTd=$("<td>").append($("<input>").addClass("BigInput easyui-validatebox").validatebox({validType:'maxLength[100]'}).val(contactUserTel));
	var contactUserMobileTd=$("<td>").append($("<input>").addClass("BigInput easyui-validatebox").validatebox({validType:'maxLength[100]'}).val(contactUserMobile));
	var contactUserCompanyTd=$("<td>").append($("<input>",{
		maxLength:'100'
	}).addClass("BigInput easyui-validatebox").val(contactUserDept));
	var contactUserEmailTd=$("<td>").append($("<input>").addClass("BigInput easyui-validatebox").validatebox({validType:'maxLength[100]'}).val(contactUserEmail));
	var delTd=$("<td>").append($("<input>",{
		type:'button',
		value:'-'
	}).addClass("btn btn-primary").click(function(){
		$(this).parent("td").parent("tr").remove();
	}));
	var tr=$("<tr>");
	tr.append(contactUserNameTd);
	tr.append(contactUserTelTd);
	tr.append(contactUserMobileTd);
	tr.append(contactUserCompanyTd);
	tr.append(contactUserEmailTd);
	tr.append(delTd);
	$("#contactUserList").append(tr);
}


/**
 * 获取联系人信息
 */
function getContactUserItem(){
	var itemList = $("#contactUserList").find("tr");
	var contactUserItem = new Array();
	var ids="";
	for(var i =1; i <itemList.length ; i++){
		var itemTemp = itemList[i];
		var contactUserSid = $(itemTemp.cells[0]).find("input[type=hidden]").val();
		var contactUserName = $(itemTemp.cells[0]).find("input[type=text]").val();
		var contactUserTel = $(itemTemp.cells[1]).find("input").val();
		var contactUserMobile = $(itemTemp.cells[2]).find("input").val();
		var contactUserDept = $(itemTemp.cells[3]).find("input").val();
		var contactUserEmail = $(itemTemp.cells[4]).find("input").val();
		var itemp = {sid : contactUserSid , name : contactUserName , telephone: contactUserTel , mobilePhone: contactUserMobile,
				department:contactUserDept,email:contactUserEmail};
		contactUserItem.push(itemp);
		if(contactUserSid>0){
			ids +=contactUserSid+",";
		}
	}
	var temp = {arrayTemp:contactUserItem , ids:ids};
	return temp;
}

/**
 * 获取省
 */
function getProvince(){
	var url = contextPath + "/chinaCityController/getProvinceList.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var province = document.getElementById('province');
		var prcList = json.rtData;
		if(prcList.length){
			$.each(prcList,function(i,prc){
				var option = new Option(prc.cityName,prc.cityCode);
				province.options.add(option);
			});
			
		}
	}
}

/**
 * 获取市
 */
function getCity(){
	var province = document.getElementById('province').value;
	var city = document.getElementById('city');
	city.length = 1;
	var url = contextPath + "/chinaCityController/getCityListByCode.action";
	var param={cityCode:province};
	var json = tools.requestJsonRs(url,param);
	if(json.rtState){
		var prcList = json.rtData;
		if(prcList.length){
			$.each(prcList,function(i,prc){
				var option = new Option(prc.cityName,prc.cityCode);
				city.options.add(option);
			});
			
		}
	}
	
}

function goBack(){
	var url="";
	if(industryType!=""){
		url = contextPath+"/system/subsys/crm/core/customInfo/industry/customerList.jsp?industryType="+industryType;
	}else if(cityCode!=""){
		url = contextPath+"/system/subsys/crm/core/customInfo/province/customerList.jsp?cityCode="+cityCode;
	}else{
		url= contextPath+"/system/subsys/crm/core/customInfo/customerList.jsp";
	}
	location.href=url;
}
</script>
</head>
<body onload="doInit();" style="margin:0 auto;padding:0 auto;text-align:center;font-size:12px;margin-top:10px;">
<form id="form1" name="form1">
	<table style="width:90%;font-size:12px;" class='TableBlock' align="center">
		<tr class="TableHeaer" align='left'>
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					基本信息：
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				客户名称<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input type="text" required="true" class="easyui-validatebox BigInput" name="customerName" id="customerName" />
			</td>
			<td>
				类型：
				</td>
			<td>
				<select id="customerType" name="customerType" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				所属区域：
				</td>
			<td>
				省份：
				<select name="province" id="province" onchange="getCity();" class='BigSelect' title="类型值可在“客户管理”->“维护设置”->区域管理中设置。">
					<option>请选择</option>
				</select>
				城市：
				<select name="city" id="city"  class='BigSelect' title="类型值可在“客户管理”->“维护设置”->区域管理中设置。">
					<option>请选择</option>
				</select>
			</td>
			<td>
				所属行业：
				</td>
			<td>
				<select id="industry" name="industry" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				公司规模：
			</td>
			<td>
				<select id="companyScale" name="companyScale" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
			<td>
				来源：
				</td>
			<td>
				<select id="customerSource" name="customerSource" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				客户性质：
			</td>
			<td>
				<select id="type" name="type" class="BigSelect">
					<option value="1">客户</option>
					<option value="2">供应商</option>
				</select>
			</td>
			<td>
				单位性质：
			</td>
			<td>
				<select id="unitType" name="unitType" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>" >
					<option value="">请选择</option>
				</select>
			</td>
		</tr>
		<tr class="TableHeaer" align='left'>
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					联系方式：
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				公司地址：
			</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox" name="companyAddress" id="companyAddress" maxlength="100"/>
			</td>
			<td>
				公司网址：
				</td>
			<td>
				<input type="text" name="companyUrl" id="companyUrl" class="BigInput easyui-validatebox" maxlength="100" /> 
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				公司电话：
				</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox" name="companyPhone" id="companyPhone" validType="maxLength[100]"/>
			</td>
			<td>
				移动电话：
				</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox" name="companyMobile" id="companyMobile" validType='maxLength[100]'/>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				传真：
			</td>
			<td>
				<input type="text" name="companyFax" id="companyFax"  class="BigInput" maxlength="20"> 
			</td>
			<td>
				邮编：
				</td>
			<td>
				<input type="text" name="companyZipCode" id="companyZipCode"  class="BigInput easyui-validatebox" validType="zipcode" > 
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				邮件：
			</td>
			<td>
				<input type="text" name="companyEmail" id="companyEmail"  class="BigInput easyui-validatebox" validType="maxLength[100]" > 
			</td>
			<td>
				QQ：
				</td>
			<td>
				<input type="text" name="companyQQ" id="companyQQ" class="BigInput easyui-validatebox" validType="QQ"> 
			</td>
		</tr>
		<tr class="TableHeaer" align='left'>
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					概要：
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				关系等级：
			</td>
			<td>
				<select id="relationLevel" name="relationLevel" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
			<td>
				重要程度：
				</td>
			<td>
				<select id="importantLevel" name="importantLevel" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				投资来源：
				</td>
			<td>
				<select id="sourcesOfInvestment" name="sourcesOfInvestment" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
			<td>
				信用等级：
				</td>
			<td>
				<select id="trustLevel" name="trustLevel" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td colspan='1'>
				销售市场：
			</td>
			<td  colspan='3'>
				<select id="salesMarket" name="salesMarket" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
		</tr>
		<tr class="TableHeaer" align="left">
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					开票资料：
				</div>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td>
				单位名称：
			</td>
			<td>
				<input type="text"  class="BigInput" name="billUnitName" id="billUnitName" maxlength="100" />
			</td>
			<td>
				公司地址：
				</td>
			<td>
				<input type="text" name="billUnitAddress" id="billUnitAddress"  class="BigInput" maxlength="100"> 
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td>
				银行账号：
				</td>
			<td>
				<input type="text" class="BigInput" name="bankAccount" id="bankAccount" maxlength="100"/>
			</td>
			<td>
				税号：
				</td>
			<td>
				<input type="text" class="BigInput" name="taxNo" id="taxNo" maxlength="100"/>
			</td>
		</tr >
		<tr class='TableData' align="left">
			<td>
				开户银行：
			</td>
			<td>
				<input type="text" name="bankName" id="bankName"  class="BigInput" maxlength="100"/> 
			</td>
			<td>
				开票联系电话：
				</td>
			<td>
				<input type="text" name="billPhone" id="billPhone" class="BigInput easyui-validatebox" validType="maxLength[100]"/> 
			</td>
		</tr>
		<tr class="TableHeaer" align="left">
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					联系人：
				</div>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td colspan='4'>
				<table style='width:100%;font-size:12px;' class='TableBlock' id="contactUserList" name="contactUserList">
					<tr class='TableHeader'>
						<td>联系人姓名</td>
						<td>联系电话</td>
						<td>移动电话</td>
						<td>所属部门</td>
						<td>邮箱</td>
						<td align='center' style="padding-left:5px;"><input type="button" name="addUser" id="addUser" required="true" value="+" class="btn btn-primary" onclick="addContactUser();"/> </td>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="TableHeaer" align="left">
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					共享与设置：
				</div>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td>
				负责人：
			</td>
			<td colspan='3'>
				<input type="hidden" name="managePersonId" id="managePersonId"> 
				<input cols="45" name="managePersonName" id="managePersonName" rows="1" style="overflow-y: auto;"  class="BigInput" wrap="yes" readonly />
				<span id="addSpan" name="addSpan"><a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['managePersonId', 'managePersonName'])">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('managePersonId', 'managePersonName')">清空</a></span>
				<span style='color:red;'>(注：当负责人为空代表该客户信息为公海客户)</span>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td>
				共享人员：
				</td>
			<td colspan='3'>
				<textarea id="sharePersonNames"  name ="sharePersonNames" style="width:280px;height:60px;background:#f0f0f0;padding:5px" class="BigTextarea" readonly></textarea>
				<input type="hidden" id="sharePersonIds" name="sharePersonIds"/>
				<a href="javascript:void(0)" onclick="selectUser(['sharePersonIds','sharePersonNames'])">选择</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="clearData('sharePersonIds','sharePersonNames')">清除</a>
			</td>
		</tr >
		<tr class='TableData' align="left">
			<td>
				备注：
			</td>
			<td colspan='3'>
				<textarea id="remark"  name ="remark"  class="BigTextarea" cols='70' rows='7' ></textarea>
			</td>
		</tr >
	</table>
	<br/>
	<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%"'>
		<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
		<input id="saveInfo" name="saveInfo" type='button' class="btn btn-primary" value="保存" onclick='commit();'/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="back" name="back" type='button' class="btn btn-primary" value='返回' onclick='goBack();'/>
	</div>
	<br/><br/>
</form>
</body>
</html>