<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年假规则管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript">
var delArrayItem = new Array();
function doInit(){
	doInitParam();
	getContactUerList();
	
}

function doInitParam(){
	//[{paraName:'TYPE_XXXX' , paraValue:'111'}]
	var hrAnnualLeaveRuleOpenObj = getSysParamByNames("HR_ANNUAL_LEAVE_RULE_OPEN");//是否启用年假规则  1-启用 ;  0 -不启用 
	var hrAnnualLeaveTypeObj = getSysParamByNames("HR_ANNUAL_LEAVE_TYPE");//1=按工龄   2=按司年
	
	if(hrAnnualLeaveRuleOpenObj !=''){
		var hrAnnualLeaveRuleOpen = hrAnnualLeaveRuleOpenObj[0].paraValue;
		if(hrAnnualLeaveRuleOpen =='1'){
			$("#hrAnnualLeaveRuleOpen").attr('checked','checked');
		}else{
			$("#hrAnnualLeaveRuleOpen").removeAttr('checked');
		}
	}
	if(hrAnnualLeaveRuleOpenObj !=''){
		var hrAnnualLeaveType = hrAnnualLeaveTypeObj[0].paraValue;
		if(hrAnnualLeaveType =='1'){
			$("#type1").attr('checked','checked');
		}else if(hrAnnualLeaveType =='2'){
			$("#type2").attr('checked','checked');
		}
	}
}


function getContactUerList(){
	var url = "<%=contextPath%>/annualLeaveController/getManageList.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var contactUserList = json.rtData;
		$.each( contactUserList, function(j, item){
			addContactUser(item);
		});
	}
}





function addContactUser(item){
	var contactUserSid ="0";
	var yearCount = "";
	var vacationDays = "";
	if(item){
		 contactUserSid=item.sid;
		 yearCount = item.yearCount;
		 vacationDays =item.vacationDays;
		 contactUserMobile = item.mobilePhone;
	}
	var contactUserSId=$("<input>",{
		type:'hidden'
	}).addClass("BigInput").val(contactUserSid);
	
	var contactUserNameTd=$("<td>").append(contactUserSId).append("年限小于或等于");

	var contactUserTelTd=$("<td>").append($("<input>").addClass("BigInput easyui-validatebox").attr({
		size:'5',
		maxLength:'2'
	}).validatebox({validType:'integeZero[]',required:true }).val(yearCount)).append("年，");
	
	var contactUserMobileTd=$("<td>").append("假期天数为");
	
	var contactUserCompanyTd=$("<td>").append($("<input>",{
		maxLength:'2',
		size:'5'
	}).addClass("BigInput easyui-validatebox").attr({size:'5'}).validatebox({validType:'integeZero[]',required:true }).val(vacationDays)).append("天");
	
	
	var delTd=$("<td>").append($("<input>",{
		type:'button',
		value:'-'
	}).addClass("btn btn-primary").click(function(){
		var sid = $(this).parent("td").parent("tr").find("input[type=hidden]").val();
		if(sid!='0'){
			delArrayItem.push(sid);
		}
		$(this).parent("td").parent("tr").remove();
	}));
	var tr=$("<tr>").addClass("TableHeader");
	tr.append(contactUserNameTd);
	tr.append(contactUserTelTd);
	tr.append(contactUserMobileTd);
	tr.append(contactUserCompanyTd);
	tr.append(delTd);
	$("#contactUserList").append(tr);
}




/**
 * 获取联系人信息
 */
function getContactUserItem(){
	var itemList = $("#contactUserList").find("tr");
	var arrayItem = new Array();
	var ids="";
	for(var i =0; i <itemList.length ; i++){
		var itemTemp = itemList[i];
		var vacationSid = $(itemTemp.cells[0]).find("input[type=hidden]").val();
		var yearCount = $(itemTemp.cells[1]).find("input").val();
		var vacationDays = $(itemTemp.cells[3]).find("input").val();
		var defaultFlag = 0;
		if(i==0 || i ==1){
			defaultFlag=1;
		}
		var itemp = {sid:vacationSid , yearCount:yearCount , vacationDays:vacationDays,defaultFlag:defaultFlag};
		arrayItem.push(itemp);
		if(vacationSid>0){
			ids +=vacationSid+",";
		}
		//alert("vacationSid>" + vacationSid + " yearCount>" + yearCount + " vacationDays>" + vacationDays);
	}
	var temp = {arrayTemp:arrayItem , ids:ids};
	return temp;
}

function getDelArrayItem(){
	var idStr = "";
	if(delArrayItem.length){
		for(var i=0;i<delArrayItem.length;i++){
			if(idStr){
				idStr +=","
			}
			idStr += delArrayItem[i];
		}
	}
	return idStr
}

function doSaveOrUpdate(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var temp = getContactUserItem();
		var str = tools.jsonArray2String(temp.arrayTemp);
		var delIdStr = getDelArrayItem();
		
		var sids = temp.ids;
		param["annualLeaveList"]=str;
		param["delIdStr"]=delIdStr;
		var url = contextPath+"/annualLeaveController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.jBox.tip("保存成功!",'info',{timeout:1500});
			window.location.reload();
		}
		
	}
	
	
	
	
}




</script>

</head>
<body style="margin-top: 10px;margin-bottom: 10px;" onload="doInit();">
<form id="form1" name="form1">
<table style="width:60%;font-size:12px;" class='TableBlock' align="center">
	<div align="center" style="margin-bottom: 10px;">
		<div>
			<input type="checkbox" id="hrAnnualLeaveRuleOpen" name="hrAnnualLeaveRuleOpen"  value=""/>启用年假规则
		</div>
		<div style="font-size:12px">
			年假计算方式：<input type="radio" name="hrAnnualLeaveType" value="1" id="type1" /> <label for="type1">按工龄计算</label> 
			<span style="display:none;" >&nbsp;&nbsp;<input type="radio" name="hrAnnualLeaveType" value="2"  id="type2" /> <label for="type2">按司龄计算</label></span> 
			&nbsp;&nbsp;<input type="button"  value="保存" class="btn btn-primary" onclick="doSaveOrUpdate();"/>
			&nbsp;&nbsp;<input type="button" name="addUser" id="addUser" required="true" value="添加规则项" class="btn btn-info" onclick="addContactUser();"/>
		</div>
	</div>
	<tr class='TableData' align="left">
		<td colspan='4' >
			<table style='width:700;font-size:12px;' align="center" class='TableBlock' id="contactUserList" name="contactUserList">
			</table>
		</td>
	</tr>
</table>
</form>


</body>
</html>