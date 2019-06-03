<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	getHrCodeByParentCodeNo("PM_EMPLOYEE_TYPE","employeeType");
	getHrCodeByParentCodeNo("PM_MARRIAGE","marriage");
	getHrCodeByParentCodeNo("PM_HOUSEHOLD","household");//户口类型
	getHrCodeByParentCodeNo("PM_POLITICS","politics");
	getHrCodeByParentCodeNo("PM_EDUCATIONDEGREE","educationDegree");
	getHrCodeByParentCodeNo("PM_DEGREE","degree");
}

</script>
</head>
<body onload="doInit()" style="font-size:12px">
	<form id="form1" name="form1" method="post" action="searchList.jsp">
		<br/>
		<center>
			<button class="btn btn-warning" type="submit">查询</button>
			&nbsp;&nbsp;
			<button class="btn btn-default" type="reset">重置</button>
		</center>
		<br/>
		<table style="width:800px;font-size:12px" class='TableBlock' align="center">
			<tr class="TableHeader" >
				<td colspan="4" style="width:100%;height:36px;">
					基本信息检索字段
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					人员姓名：
				</td>
				<td>
					<input type="text"  class=" BigInput" name="personName" id="personName" />
				</td>
				<td>
					身份证号：
					</td>
				<td>
					<input class="BigInput " id="idCard" name="idCard" type="text" />
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					籍贯：
					</td>
				<td>
					<input type="text" class="BigInput" name="nativePlace" id="nativePlace" />
				</td>
				<td>
					档案编号：
					</td>
				<td>
					<input class="BigInput" id="codeNumber" name="codeNumber" type="text"/>
				</td>
			</tr >
			<tr class='TableData' style="display:none">
				<td colspan="4">
					<input type="hidden" id="isOaUser" name="isOaUser" value="false"/>
					<input type="checkbox" id="isOaUserFlag" name="isOaUserFlag" onClick="changeCheckStatus()"/>
					是否为oa用户
				</td>
			</tr>
			<tr class='TableData' id="addSpan">
				<td>
					角色：
				</td>
				<td>
					<input type="hidden" name="roleId" id="roleId"  value=""> 
					<input cols="45" name="roleName" id="roleName" rows="1" style="overflow-y: auto;"  class="BigInput readonly" wrap="yes" readonly />
					<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleRole(['roleId', 'roleName'])">添加</a>
				</td>
				<td>
					关联用户：
				</td>
				<td>
					<input type="hidden" name="userId" id="userId"  value=""> 
					<input cols="45" name="userName" id="userName" rows="1" style="overflow-y: auto;"  class="BigInput readonly" wrap="yes" readonly />
					<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userId', 'userName'])">添加</a>
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					部门：
				</td>
				<td>
					<input id="deptId" name="deptId"  type="hidden" />
					<input id="deptIdName" name="deptIdName"  type="text" class="BigInput  readonly" required readonly/>
					<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptIdName'])">选择</a>
				</td>
				<td>
					工号：
				</td>
				<td>
					<input class="BigInput" id="workNumber" name="workNumber" type="text"/>
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					员工状态：
				</td>
				<td>
					<select class="BigSelect"  name="statusType" id="statusType" >
						<option value=""></option>
						<option value="在职">在职</option>
						<option value="离职">离职</option>
						<option value="退休">退休</option>
					</select>
				</td>
				<td>
					员工类型：
					</td>
				<td>
					<select class="BigSelect" id="employeeType" name="employeeType">
						<option value=""></option>
					</select>
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					英文名：
					</td>
				<td>
					<input class="BigInput" id="englishName" name="englishName" type="text"/>
				</td>
				<td>
					性别：
				</td>
				<td>
					<select  class="BigSelect" id="gender" name="gender">
						<option value=""></option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select>
				</td>
			</tr>
			<tr class='TableData'>
				<td >
					出生日期：
					</td>
				<td>
					<input type="text" style="width:80px" id='birthdayDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:80px" width="50px" id='birthdayDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc2' class="Wdate BigInput" />
			   </td>
				<td>
					民族：
				</td>
				<td>
					<input type="text" class="BigInput" name="ethnicity" id="ethnicity" />
				</td>
				
			</tr>
			<tr class='TableData'>
				<td>
					默认年假天数：
					</td>
				<td>
					<input type="text" class="BigInput " validType='integeZero[]'  id="defaultAnnualLeaveDays" name="defaultAnnualLeaveDays" />
				</td>
				<td >
					职务：
					</td>
				<td>
					<input class="BigInput" id="postState" name="postState" type="text" />
				</td>
			</tr>
			<tr class='TableData'>
				
			   <td >
					婚姻状况：
					</td>
				<td>
						<select class="BigSelect" id="marriage" name="marriage">
							<option value=""></option>
					</select>
			   </td>
			   <td>
					毕业学校：
					</td>
				<td >
					<input class="BigInput" id="graduateSchool" name="graduateSchool" type="text" />
				</td>
			</tr>
				<tr class='TableData'>
				<td>
					户口类型：
					</td>
				<td>
					<select class="BigSelect" id="household" name="household">
						<option value=""></option>
					</select>
				</td>
				<td>
					健康状况：
					</td>
				<td>
					<input class="BigInput" id="health" name="health" type="text"/>
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					户口所在地：
					</td>
				<td>
					<input class="BigInput" id="householdPlace" name="householdPlace" type="text"/>
				</td>
				<td >
					入职时间：
					</td>
				<td>
					<input type="text" style="width:80px" id='joinDateDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinDateDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:80px" id='joinDateDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinDateDesc2' class="Wdate BigInput" />
				</td>
			</tr>
			<tr class='TableData'>
				<td >
					政治面貌：
					</td>
				<td>
					<select class="BigSelect" id="politics" name="politics">
						<option value=""></option>
					</select>
				</td>
				<td >
					入党（团）时间：
					</td>
				<td>
					<input type="text" style="width:80px" id='joinPartyDateDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinPartyDateDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:80px" id='joinPartyDateDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinPartyDateDesc2' class="Wdate BigInput" />
				</td>
			</tr>
			<tr class='TableData'>
				<td >
					专业：
					</td>
				<td>
					<input class="BigInput" id="major" name="major"  type="text"/>
				</td>
				<td >
					毕业时间：
					</td>
				<td>
					<input type="text" style="width:80px" id='graduateDateDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='graduateDateDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:80px" id='graduateDateDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='graduateDateDesc2' class="Wdate BigInput" />
				</td>
			</tr>
				<tr class='TableData'>
				<td >
					学历：
					</td>
				<td>
					<select class="BigSelect" id="educationDegree" name="educationDegree" >
					<option value=""></option>
					</select>
					
				</td>
				<td >
					学位：
					</td>
				<td>
					<select class="BigSelect" id="degree" name="degree" >
					<option value=""></option>
					</select>
				</td>
			</tr>
			<tr class='TableHeader'>
				<td colspan="4" >
					<div class="">
						联系信息检索字段：
					</div>
				</td>
			</tr>
				<tr class='TableData'>
				<td >
					手机号码：
					</td>
				<td>
					<input class="BigInput" validType='mobile' type="text" id="mobileNo" name="mobileNo" />
				</td>
				<td >
					电话号码：
					</td>
				<td>
					<input class="BigInput" validType='tel[]' type="text" id='telNo' name="telNo"/>
				</td>
			</tr>
				<tr class='TableData'>
				<td>
					电子邮件：
					</td>
				<td colspan="3">
					<input class="BigInput " validType='email[]' type="text" id="email" name="email" style='width:522px;' />
				</td>
			</tr>
				<tr class='TableData'>
				<td >
					QQ号码：
					</td>
				<td>
					<input class="BigInput " validType='QQ[]' id="qqNo" name="qqNo" type="text" />
				</td>
				<td >
					MSN：
					</td>
				<td>
					<input class="BigInput" type="text" id='msn'  name='msn'  type="text"/>
				</td>
			</tr>
				<tr class='TableData'>
				<td >
					家庭地址：
					</td>
				<td colspan="3">
					<input class="BigInput" id="address" name="address" style='width:522px;'type="text" />
				</td>
			</tr>
			<tr class='TableData'>
				<td>
					其他联系地址：
					</td>
				<td colspan="3" >
					<input class="BigInput" id="otherAddress" name="otherAddress" style='width:522px;' type="text" />
				</td>
			</tr>
		</table>
		<input type="hidden" class="BigInput" id="sid" name="sid" value="21"/>
	</form>
</body>
</html>