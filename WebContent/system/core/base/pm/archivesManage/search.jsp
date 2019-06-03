<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String uuid = request.getParameter("uuid");
String name = request.getParameter("name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
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
	renderCostomFileds();
}

//渲染自定义字段 查询字段
function renderCostomFileds(){
		var url=contextPath+"/humanDocController/getQueryFieldListById.action";
		var json=tools.requestJsonRs(url,null);
		if(json.rtState){
			var data=json.rtData;
			//先移除之前渲染的自定义字段的数据
			$(".customTr").remove();
			var html = ["<tr class='customTr'>"];	
//			$("#searchTable").append("<tr class='customTr'>");
			for(var i=1;i<=data.length;i++){	
				
				var name="EXTRA_"+data[i-1].sid;
				if(i%3==0){
					html.push("</tr><tr class='customTr'>");
				}
				
				if(data[i-1].filedType=="单行输入框"){
					html.push("<td style=\"text-indent:15px;\"  width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
							   +"<td  width=\"40%\" >"
							   +"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 300px\" />"
							   +"</td>");
				}else if(data[i-1].filedType=="多行输入框"){
					html.push("<td style=\"text-indent:15px;\" width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
							   +"<td  width=\"40%\">"
							   +"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 300px\" />"
							   +"</td>");
				}else if(data[i-1].filedType=="下拉列表"){
					if(data[i-1].codeType=="HR系统编码"){
						html.push("<td  style=\"text-indent:15px;\"  width=\"10%\" >"+data[i-1].extendFiledName+"：</td>"
								   +"<td   width=\"40%\">"
								   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" >");
						
						
						html.push("<option value=''>全部</option>");
						var url1 =   contextPath + "/hrCode/getSysCodeByParentCodeNo.action";
						var para = {codeNo:data[i-1].sysCode};
						var jsonObj = tools.requestJsonRs(url1 ,para);
						if(jsonObj.rtState){
							var prcs = jsonObj.rtData;
							for ( var n = 0; n < prcs.length; n++) {
								html.push("<option value='"+prcs[n].codeNo+"'>" + prcs[n].codeName + "</option>");
							}				
						}
						
						html.push("</select></td>");
					}else if(data[i-1].codeType=="自定义选项"){
						var values=data[i-1].sysCode;
						var optionNames=data[i-1].optionName.split(",");
						var optionValues=data[i-1].optionValue.split(",");
						html.push("<td width=\"10%\" style=\"text-indent:15px;\" >"+data[i-1].extendFiledName+"：</td>"
								   +"<td   width=\"40%\">"
								   +    "<select style=\"width:200px;height:23px;\"  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" >");
						html.push("<option value=''>全部</option>");
						for(var j=0;j<optionNames.length;j++){
							html.push("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
						
						}	
						html.push("</select></td>");	
					}	
				}
			}
			html.push("</tr>");
			$("#searchTable").append(html.join(""));
		}
	
}

function back(){
	changePage("<%=contextPath%>/system/core/base/pm/archivesManage/humanIndex.jsp?uuid=<%=uuid%>&name=<%=name%>&pa=2");
}
function  changePage(url){
	$('#frame0', parent.document).attr("src", url);
	//$("#frame0").attr("src", url);
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px;">
	<form id="form1" name="form1" method="post" action="searchList.jsp?uuid=<%=uuid%>&name=<%=name%>">
<div class="topbar clearfix" id="toolbar">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_detail.png">
		&nbsp;&nbsp;<span class="title"> 档案查询</span>
	</div>
	<div class="fr">
	       <button class="btn-win-white" type="submit">查询</button>
			&nbsp;&nbsp;
			<button class="btn-win-white" type="reset">重置</button>
			&nbsp;&nbsp;
			<input type='button' class='btn-win-white' value='取消' onclick="back();">
	
	</div>
</div>

		<table class="TableBlock_page" width="100%"  style="font-size:12px" id="searchTable">
			<tr>
				<td colspan="4" style="width:100%;height:36px;font-size: 14px;">
					基本信息检索字段
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					人员姓名：
				</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" type="text"  class=" BigInput" name="personName" id="personName" />
				</td>
				<td width="10%">
					身份证号：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput " id="idCard" name="idCard" type="text" />
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					籍贯：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" type="text" class="BigInput" name="nativePlace" id="nativePlace" />
				</td>
				<td width="10%">
					档案编号：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="codeNumber" name="codeNumber" type="text"/>
				</td>
			</tr >
			<tr style="display:none">
				<td colspan="4">
					<input type="hidden" id="isOaUser" name="isOaUser" value="false"/>
					<input type="checkbox" id="isOaUserFlag" name="isOaUserFlag" onClick="changeCheckStatus()"/>
					是否为oa用户
				</td>
			</tr>
			<tr id="addSpan">
				<td width="10%" style="text-indent:15px;">
					角色：
				</td>
				<td width="40%">
					<input type="hidden" name="roleId" id="roleId"  value=""> 
					<input style="width: 350px;height: 23px;border: 1px solid #dadada;" name="roleName" id="roleName" rows="1" style="overflow-y: auto;"  class="BigInput readonly" wrap="yes" readonly />
					<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleRole(['roleId', 'roleName'])">添加</a>
				</td>
				<td width="10%">
					关联用户：
				</td>
				<td width="40%">
					<input type="hidden" name="userId" id="userId"  value=""> 
					<input style="width: 350px;height: 23px;border: 1px solid #dadada;" name="userName" id="userName" rows="1" style="overflow-y: auto;"  class="BigInput readonly" wrap="yes" readonly />
					<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userId', 'userName'])">添加</a>
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					部门：
				</td>
				<td width="40%">
				    <input id="deptId" name="deptId"  type="hidden" value="<%=request.getParameter("uuid")%>"/>
				    <input style="width: 350px;height: 23px;" id="deptIdName" name="deptIdName"  type="text" value="<%=request.getParameter("name")%>" class="BigInput" required readonly="readonly"/>
					<!-- <a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptIdName'])">选择</a> -->
				</td>
				<td width="10%">
					工号：
				</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="workNumber" name="workNumber" type="text"/>
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					员工状态：
				</td>
				<td width="40%">
					<select style="width: 200px;height: 23px;" class="BigSelect"  name="statusType" id="statusType" >
						<option value="">全部</option>
						<option value="在职">在职</option>
						<option value="离职">离职</option>
						<option value="退休">退休</option>
					</select>
				</td>
				<td width="10%">
					员工类型：
					</td>
				<td width="40%">
					<select style="width: 200px;height: 23px;" class="BigSelect" id="employeeType" name="employeeType">
						<option value="">全部</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					英文名：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="englishName" name="englishName" type="text"/>
				</td>
				<td width="10%">
					性别：
				</td>
				<td width="40%">
					<select style="width: 50px;height: 23px;"  class="BigSelect" id="gender" name="gender">
						<option value="">全部</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					出生日期：
					</td>
				<td width="40%">
					<input style="width: 165px;height: 23px;" type="text" id='birthdayDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width: 165px;height: 23px;" width="50px" id='birthdayDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc2' class="Wdate BigInput" />
			   </td>
				<td width="10%">
					民族：
				</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" type="text" class="BigInput" name="ethnicity" id="ethnicity" />
				</td>
				
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					默认年假天数：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" type="text" class="BigInput " validType='integeZero[]'  id="defaultAnnualLeaveDays" name="defaultAnnualLeaveDays" />
				</td>
				<td width="10%">
					职务：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="postState" name="postState" type="text" />
				</td>
			</tr>
			<tr>
			   <td width="10%" style="text-indent:15px;">
					婚姻状况：
					</td>
				<td width="40%">
						<select style="width: 200px;height: 23px;" class="BigSelect" id="marriage" name="marriage">
							<option value="">全部</option>
					</select>
			   </td>
			   <td width="10%">
					毕业学校：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="graduateSchool" name="graduateSchool" type="text" />
				</td>
			</tr>
				<tr>
				<td width="10%" style="text-indent:15px;">
					户口类型：
					</td>
				<td width="40%">
					<select style="width: 200px;height: 23px;" class="BigSelect" id="household" name="household">
						<option value="">全部</option>
					</select>
				</td>
				<td width="10%">
					健康状况：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="health" name="health" type="text"/>
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					户口所在地：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="householdPlace" name="householdPlace" type="text"/>
				</td>
				<td width="10%">
					入职时间：
					</td>
				<td width="40%">
					<input style="width: 165px;height: 23px;" type="text" id='joinDateDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinDateDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:165px;height: 23px;" id='joinDateDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinDateDesc2' class="Wdate BigInput" />
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					政治面貌：
					</td>
				<td width="40%">
					<select style="width: 200px;height: 23px;" class="BigSelect" id="politics" name="politics">
						<option value="">全部</option>
					</select>
				</td>
				<td width="10%">
					入党（团）时间：
					</td>
				<td width="40%">
					<input type="text" style="width:165px;height: 23px;" id='joinPartyDateDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinPartyDateDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:165px;height: 23px;" id='joinPartyDateDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='joinPartyDateDesc2' class="Wdate BigInput" />
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					专业：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="major" name="major"  type="text"/>
				</td>
				<td width="10%">
					毕业时间：
					</td>
				<td width="40%">
					<input type="text" style="width:165px;height: 23px;" id='graduateDateDesc1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='graduateDateDesc1' class="Wdate BigInput" />
					至
					<input type="text" style="width:165px;height: 23px;" id='graduateDateDesc2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='graduateDateDesc2' class="Wdate BigInput" />
				</td>
			</tr>
				<tr>
				<td width="10%" style="text-indent:15px;">
					学历：
					</td>
				<td width="40%">
					<select style="width: 200px;height: 23px;" class="BigSelect" id="educationDegree" name="educationDegree" >
					<option value="">全部</option>
					</select>
					
				</td>
				<td width="10%">
					学位：
					</td>
				<td width="40%">
					<select style="width: 200px;height: 23px;" class="BigSelect" id="degree" name="degree" >
					<option value="">全部</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4" >
					<div class="" style="font-size: 14px;">
						联系信息检索字段：
					</div>
				</td>
			</tr>
				<tr>
				<td width="10%" style="text-indent:15px;">
					手机号码：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" validType='mobile' type="text" id="mobileNo" name="mobileNo" />
				</td>
				<td width="10%">
					电话号码：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" validType='tel[]' type="text" id='telNo' name="telNo"/>
				</td>
			</tr>
				<tr>
				<td width="10%" style="text-indent:15px;">
					电子邮件：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput " validType='email[]' type="text" id="email" name="email" style='width:522px;' />
				</td>
				<td width="10%">
					QQ号码：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput " validType='QQ[]' id="qqNo" name="qqNo" type="text" />
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					MSN：
					</td>
				<td width="40%">
					<input class="BigInput" style="width: 350px;height: 23px;" type="text" id='msn'  name='msn'  type="text"/>
				</td>
				<td width="10%">
					家庭地址：
					</td>
				<td width="40%">
					<input style="width: 350px;height: 23px;" class="BigInput" id="address" name="address"type="text" />
				</td>
			</tr>
			<tr>
				<td width="10%" style="text-indent:15px;">
					其他联系地址：
					</td>
				<td width="40%" >
					<input style="width: 350px;height: 23px;" class="BigInput" id="otherAddress" name="otherAddress" type="text" />
				</td>
			</tr>
			<tr>
				<td colspan="4" style="width:100%;height:36px;font-size: 14px;">
					自定义检索字段
				</td>
			</tr>
		</table>
		<input type="hidden" class="BigInput" id="sid" name="sid" value="21"/>
	</form>
</body>
</html>