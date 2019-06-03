<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//会议申请Id

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<title>招聘需求详细信息</title>
<style>
  .TableList {border:1px solid #F2f2f2} 
</style>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>


<script type="text/javascript">
var STAFF_HIGHEST_SCHOOL_STR = "";//学历
var POOL_EMPLOYEE_MAJOR_STR = "";//专业
var POOL_POSITION_STR = "";//岗位
function doInit(){

	var STAFF_HIGHEST_SCHOOL_STR_LIST = getHrCodeByParentCodeNo("STAFF_HIGHEST_SCHOOL","");// 
	for(var i = 0; i <STAFF_HIGHEST_SCHOOL_STR_LIST.length ; i++){
		var prcTemp = STAFF_HIGHEST_SCHOOL_STR_LIST[i];
		STAFF_HIGHEST_SCHOOL_STR = STAFF_HIGHEST_SCHOOL_STR + "<option value='"+prcTemp.codeNo+"'>" + prcTemp.codeName + "</option>";
	}

	var POOL_EMPLOYEE_MAJOR_STR_LIST = getHrCodeByParentCodeNo("POOL_EMPLOYEE_MAJOR","");//
	for(var i = 0; i <POOL_EMPLOYEE_MAJOR_STR_LIST.length ; i++){
		var prcTemp = POOL_EMPLOYEE_MAJOR_STR_LIST[i];
		POOL_EMPLOYEE_MAJOR_STR = POOL_EMPLOYEE_MAJOR_STR + "<option value='"+prcTemp.codeNo+"'>" + prcTemp.codeName + "</option>";
	}
	var POOL_POSITION_STR_LIST = getHrCodeByParentCodeNo("POOL_POSITION","");// 
	for(var i = 0; i <POOL_POSITION_STR_LIST.length ; i++){
		var prcTemp = POOL_POSITION_STR_LIST[i];
		POOL_POSITION_STR = POOL_POSITION_STR + "<option value='"+prcTemp.codeNo+"'>" + prcTemp.codeName + "</option>";
	}
	getInfoById("<%=sid%>");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/recruitRequirementsController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			//editor.setData(prc.requRequires);
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var attach = attaches[i];
				attach["priv"] = 3;
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachment").append(fileItem);
			}
			
			var hrPoolsModelList = prc.hrPoolsModel;
			for(var i=0;i<hrPoolsModelList.length;i++){
				var hrPoolsModel = hrPoolsModelList[i];
				addHrPoolsItem(hrPoolsModel);
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 */
var selectPoolsIds = {} ;
function addHrPoolsItem(item , optType){
	var employeeName_value = "";//姓名
	var sex_value= "0";//性别
	var employeePhone_value = "";//电话
	var employeeEmail_value ="";//邮箱
	var employeeBirthStr_value = "";//出生日期
	var employeeHighestSchoolDesc_value = ""; //学历
	var employeeMajorDesc_value="";//专业
	var positionDesc_value="";//岗位
	var expectedSalary_value= "";//薪水
	var disabled  = "";
	var isSelectType = "0";//是否选择过来的
	var sid = "0";
	if(item){
		sid = item.sid;
		if(selectPoolsIds[""+sid]  &&  selectPoolsIds[""+sid]  != "" ){//如果已经存在
			return;
		}
		selectPoolsIds[""+sid] = sid;
		disabled = "disabled";
		isSelectType = sid;
		employeeName_value = item.employeeName;
		employeeBirthStr_value = item.employeeBirthStr;
		employeePhone_value = item.employeePhone;
		units_value = item.units;
		employeeHighestSchoolDesc_value = item.employeeHighestSchool;
		employeeMajorDesc_value = item.employeeMajor;
		positionDesc_value = item.position;
		employeeEmail_value = item.employeeEmail;
		expectedSalary_value = item.expectedSalary;
	}
	//姓名
	var employeeName_input = $("<input "+disabled+" style='width:80px;'>").addClass("BigInput easyui-validatebox").val(employeeName_value);
	var employeeName=$("<td>").append(employeeName_input);
	employeeName_input.attr("required","true"); 
	//性别
	var sex = $("<td>").append($("<select "+disabled+">").append("<option value='0'>男</option><option value='1'>女</option>").addClass("BigSelect").val(sex_value));
	
	//联系电话
	var employeePhone_input = $("<input "+disabled+" style='width:100px;'>").addClass("BigInput easyui-validatebox").val(employeePhone_value);
	var employeePhone=$("<td>").append(employeePhone_input);
	employeePhone_input.attr("required","true");
	//邮箱
	var employeeEmail_input = $("<input "+disabled+">").addClass("BigInput easyui-validatebox").val(employeeEmail_value);
	var employeeEmail =$("<td>").append(employeeEmail_input);
	employeeEmail_input.attr("required","true");
	
	//出生日期 
	var employeeBirthStr_input = $("<input "+disabled+" style='width:100px;' name='employeeBirth' class='Wdate BigInput' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" >").addClass("BigInput easyui-validatebox").val(employeeBirthStr_value);
	var employeeBirth=$("<td>").append(employeeBirthStr_input);
	
	//薪水
	var expectedSalary_input = $("<input "+disabled+" style='width:50px;'>").addClass("BigInput easyui-validatebox").val(expectedSalary_value);
	var expectedSalary=$("<td>").append(expectedSalary_input);
	expectedSalary_input.attr("required","true");
	//学历
	var STAFF_HIGHEST_SCHOOL=$("<td>").append($("<select "+disabled+">").append(STAFF_HIGHEST_SCHOOL_STR).addClass("BigSelect").val(employeeHighestSchoolDesc_value));
	//专业
	var POOL_EMPLOYEE_MAJOR=$("<td>").append($("<select "+disabled+">").append(POOL_EMPLOYEE_MAJOR_STR).addClass("BigSelect").val(employeeMajorDesc_value));
	//岗位
	var POOL_POSITION =$("<td>").append($("<select "+disabled+">").append(POOL_POSITION_STR).addClass("BigSelect").val(positionDesc_value));
	var tr=$("<tr isSelectType = '"+isSelectType+"'>");
	tr.append(employeeName);
	tr.append(sex);
	tr.append(employeePhone);
	tr.append(employeeEmail);
	tr.append(employeeBirth);
	tr.append(expectedSalary);
	tr.append(STAFF_HIGHEST_SCHOOL);
	tr.append(POOL_EMPLOYEE_MAJOR);
	tr.append(POOL_POSITION);
	$("#hrPoolList").append(tr);
}

</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 10px;">需求编号：</td>
		<td class="TableData" width="30%;"  id="requNo">
		</td>
		<td nowrap class="TableData" width="15%;" >需求部门：</td>
		<td class="TableData" id="requDeptStrName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">需求岗位：</td>
		<td class="TableData" width="" id="requJob">
		</td>
		<td nowrap class="TableData" width="" >需求人数：</td>
		<td class="TableData" id="requNum">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">用工日期：</td>
		<td class="TableData" width="" id="requTimeStr">
		</td>
		<td nowrap class="TableData" width="" > 申请人 ：</td>
		<td class="TableData" id="createUserName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">岗位要求：</td>
		<td class="TableData" width="" colspan="3" id="requRequires">
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">备　　注：</td>
		<td class="TableData" width="" colspan="3" id="remark">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">关联人才库：</td>
		<td class="TableData" width="" colspan="3">
	
	
	<div style="padding:0px 5px 0px 5px;">
	    			 <table id="" class="TableList" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">姓名</td>
		    		 		<td  class="TableData" align="center">性别</td>
		    		 		<td class="TableData" align="center">联系方式</td>
		    		 		<td class="TableData" align="center">邮箱</td>
		    		 		<td class="TableData" align="center">出生日期</td>
		    		 		<td class="TableData" align="center">薪水</td>
		    		 		<td class="TableData" align="center">学历</td>
		    		 		<td class="TableData" align="center">专业</td>
		    		 		<td class="TableData" align="center">岗位</td>
		    		 	</tr>
		    		 	<tbody id="hrPoolList">
		    		 	
		    		 	</tbody>
		    		 
	    		 	</table>
	    		 </div>	
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">附件文档：</td>
		<td class="TableData" width="" colspan="3" id="attachment">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px;">登记时间：</td>
		<td class="TableData" width="" colspan="3" id="createTimeStr">
		</td>
	</tr>
</table>
		
</body>
</html>