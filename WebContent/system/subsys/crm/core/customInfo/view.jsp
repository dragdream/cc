<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String industryType = request.getParameter("industryType")==null?"":request.getParameter("industryType");
	String cityCode = request.getParameter("cityCode")==null?"":request.getParameter("cityCode");
	String type = request.getParameter("type")==null?"":request.getParameter("type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
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
var type="<%=type%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeCrmCustomerInfoController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			$("#province").val(json.rtData.province);
			bindJsonObj2Cntrl(json.rtData);
			getContactUerList(sid);
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
	var contactUserName = "";
	var contactUserTel = "";
	var contactUserMobile = "";
	var contactUserDept = "";
	var contactUserEmail = "";
	if(item){
		 contactUserName = item.name;
		 contactUserTel =item.telephone;
		 contactUserMobile = item.mobilePhone;
		 contactUserDept = item.department;
		 contactUserEmail = item.email;
	}
	var contactUserNameTd=$("<td>").append($("<span>",{
		type:'text'
	}).addClass("BigInput easyui-validatebox").validatebox({ 
		required:true 
	}).html(contactUserName));

	var contactUserTelTd=$("<td>").append($("<span>").addClass("BigInput").html(contactUserTel));
	var contactUserMobileTd=$("<td>").append($("<span>").addClass("BigInput").html(contactUserMobile));
	var contactUserCompanyTd=$("<td>").append($("<span>").addClass("BigInput").html(contactUserDept));
	var contactUserEmailTd=$("<td>").append($("<span>").addClass("BigInput").html(contactUserEmail));
	var tr=$("<tr>");
	tr.append(contactUserNameTd);
	tr.append(contactUserTelTd);
	tr.append(contactUserMobileTd);
	tr.append(contactUserCompanyTd);
	tr.append(contactUserEmailTd);
	$("#contactUserList").append(tr);
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
//type=3代表公海客户，type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
function goBack(){
	var url="";
	if(industryType!=""){
		url = contextPath+"/system/subsys/crm/core/customInfo/industry/customerList.jsp?industryType="+industryType+"&type="+type;
	}else if(cityCode!=""){
		url = contextPath+"/system/subsys/crm/core/customInfo/province/customerList.jsp?cityCode="+cityCode+"&type="+type;
	}else{
		if(type==1){
			url= contextPath+"/system/subsys/crm/core/customInfo/shareCustomer.jsp";
		}else if(type==2){
			url= contextPath+"/system/subsys/crm/core/customInfo/allCustomer.jsp";	
		}else if(type==3){
			url= contextPath+"/system/subsys/crm/core/customInfo/publicCustomer.jsp";
		}else{
			url= contextPath+"/system/subsys/crm/core/customInfo/customerList.jsp";
		}
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
				客户名称：
			</td>
			<td>
				<span  required="true" class="easyui-validatebox BigInput" name="customerName" id="customerName" ></span>
			</td>
			<td>
				类型：
				</td>
			<td>
				<span id="customerTypeDesc" name="customerTypeDesc" class="BigSelect"></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				所属区域：
				</td>
			<td>
				<span name="provinceName" id="provinceName" class='BigSelect'>
				</span> &nbsp;&nbsp;&nbsp;
				<span name="cityName" id="cityName"  class='BigSelect'>
				</span>
			</td>
			<td>
				所属行业：
				</td>
			<td>
				<span id="industryDesc" name="industryDesc" class="BigSelect"></span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				公司规模：
			</td>
			<td>
				<span id="companyScaleDesc" name="companyScaleDesc" class="BigSelect"></span>
			</td>
			<td>
				来源：
				</td>
			<td>
				<span id="customerSourceDesc" name="customerSourceDesc" class="BigSelect"></span>
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
				<span  class="BigInput" name="companyAddress" id="companyAddress" />
			</td>
			<td>
				公司网址：
				</td>
			<td>
				<span  name="companyUrl" id="companyUrl" class="BigInput"> 
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				公司电话：
				</td>
			<td>
				<span  class="BigInput" name="companyPhone" id="companyPhone" />
			</td>
			<td>
				移动电话：
				</td>
			<td>
				<span  class="BigInput" name="companyMobile" id="companyMobile" />
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				传真：
			</td>
			<td>
				<span  name="companyFax" id="companyFax"  class="BigInput"> 
			</td>
			<td>
				邮编：
				</td>
			<td>
				<span  name="companyZipCode" id="companyZipCode"  class="BigInput"></span> 
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				邮件：
			</td>
			<td>
				<span  name="companyEmail" id="companyEmail"  class="BigInput"></span> 
			</td>
			<td>
				QQ：
				</td>
			<td>
				<span  name="companyQQ" id="companyQQ" class="BigInput"></span>
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
				<span id="relationLevelDesc" name="relationLevelDesc" class="BigSelect"></span>
			</td>
			<td>
				重要程度：
				</td>
			<td>
				<span id="importantLevelDesc" name="importantLevelDesc" class="BigSelect"></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				投资来源：
				</td>
			<td>
				<span id="sourcesOfInvestmentDesc" name="sourcesOfInvestmentDesc" class="BigSelect"></span>
			</td>
			<td>
				信用等级：
				</td>
			<td>
				<span id="trustLevelDesc" name="trustLevelDesc" class="BigSelect"></span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td colspan='1'>
				销售市场：
			</td>
			<td  colspan='3'>
				<span id="salesMarketDesc" name="salesMarketDesc" class="BigSelect"></span>
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
				<span  class="BigInput" name="billUnitName" id="billUnitName" ></span>
			</td>
			<td>
				公司地址：
				</td>
			<td>
				<span  name="billUnitAddress" id="billUnitAddress" required="true" value="" class="BigInput"></span>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td>
				银行账号：
				</td>
			<td>
				<span class="BigInput" name="bankAccount" id="bankAccount" ></span>
			</td>
			<td>
				税号：
				</td>
			<td>
				<span  class="BigInput" name="taxNo" id="taxNo" ></span>
			</td>
		</tr >
		<tr class='TableData' align="left">
			<td>
				开户银行：
			</td>
			<td>
				<span name="bankName" id="bankName"  class="BigInput"></span>
			</td>
			<td>
				开票联系电话：
				</td>
			<td>
				<span name="billPhone" id="billPhone" class="BigInput"></span>
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
				<span name="managePersonName" id="managePersonName"></span>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td>
				共享人员：
				</td>
			<td colspan='3'>
				<span id="sharePersonNames"  name ="sharePersonNames" ></span>
			</td>
		</tr >
	</table>
	<br/><br/>
</form>
</body>
</html>