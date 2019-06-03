<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>

<style>
td{
padding:5px;
}
</style>
<script>
var sid='<%=sid%>';
var deptName;
function doInit(){
	var url = contextPath+"/humanDocController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		if(json.rtData.attachMentModel.length>0){
			//$("#attachTr").show();
			var attaches = json.rtData.attachMentModel;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}else{
		alert(json.rtMsg);
	}
}

function getOtherInfo(){
	var type=$("#otherInfo").val();
	var url="";
	switch(type){
		case "1":
			url=contextPath+"/system/core/base/pm/detailList/ht.jsp";
			break;
		case "2":
			url=contextPath+"/system/core/base/pm/detailList/jc.jsp";
			break;
		case "3":
			url=contextPath+"/system/core/base/pm/detailList/zs.jsp";
			break;
		case "4":
			url=contextPath+"/system/core/base/pm/detailList/px.jsp";
			break;
		case "5":
			url=contextPath+"/system/core/base/pm/detailList/xxjl.jsp";
			break;
		case "6":
			url=contextPath+"/system/core/base/pm/detailList/gzjl.jsp";
			break;
		case "7":
			url=contextPath+"/system/core/base/pm/detailList/gzjn.jsp";
			break;
		case "8":
			url=contextPath+"/system/core/base/pm/detailList/shgx.jsp";
			break;
		case "9":
			url=contextPath+"/system/core/base/pm/detailList/rsdd.jsp";
			break;
		case "10":
			url=contextPath+"/system/core/base/pm/detailList/lz.jsp";
			break;
		case "11":
			url=contextPath+"/system/core/base/pm/detailList/fz.jsp";
			break;
	}
	openFullWindow(url+"?humanDocSid="+sid,"人事信息");
	
}
</script>
</head>
<body onload="doInit();" style="font-size:12px;margin:0 auto;">
<form id="form1" name="form1">
	<table style="width:700px;font-size:12px;margin:0 auto;" class="TableBlock">
		<tr class="TableHeader">
			<td colspan="4" >
				<div class="">
					人员基本信息：
					<span style='float:right;height:36px;line-height:36px;'>
						<select id="otherInfo" name="otherInfo" style="margin-top:5px;" class="BigSelect" onchange="getOtherInfo()" >
							<option value="0">请选择其他的信息</option>
							<option value="1">合同信息</option>
							<option value="2">奖惩信息</option>
							<option value="3">证书信息</option>
							<option value="4">培训信息</option>
							<option value="5">学习经历</option>
							<option value="6">工作经历</option>
							<option value="7">工作技能</option>
							<option value="8">社会关系</option>
							<option value="9">人事调动</option>
							<option value="10">离职信息</option>
							<option value="11">复职信息</option>
						</select>
					</span>
				</div>
			</td>
		</tr>
		<tr class='TableData'>
			<td style="width:100px">
				人员姓名：
			</td>
			<td>
				<div id="personName"></div>
			</td>
			<td style="width:100px">
				身份证号：
				</td>
			<td>
				<div id="idCard"></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				籍贯：
				</td>
			<td>
				<div id="nativePlace" ></div>
			</td>
			<td>
				档案编号：
				</td>
			<td>
				<div id="codeNumber" ></div>
			</td>
		</tr >
		<tr class='TableData'>
			<td colspan="4">
				关联用户：<span id="userName"></span>
				</td>
		</tr>
		<tr class='TableData'>
			<td>
				所在部门：
			</td>
			<td>
				<div id="deptIdName"></div>
			</td>
			<td>
				工号：
			</td>
			<td>
				<div  id="workNumber" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				员工状态：
			</td>
			<td>
				<div id="statusType" ></div>
			</td>
			<td>
				员工类型：
				</td>
			<td>
				<div id="employeeTypeDesc"></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				英文名：
				</td>
			<td>
				<div id="englishName" ></div>
			</td>
			<td>
				性别：
			</td>
			<td>
				<div id="gender" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				出生日期：
				</td>
			<td>
				<div id='birthdayDesc' ></div>
		   </td>
			<td>
				民族：
			</td>
			<td>
				<div id="ethnicity"></div>
			</td>
			
		</tr>
		<tr class='TableData'>
			<td>
				默认年假天数：
				</td>
			<td>
				<div id="defaultAnnualLeaveDays" name="defaultAnnualLeaveDays" ></div>
			</td>
			<td >
				职务：
				</td>
			<td>
				<div class="Bigdiv" id="postState" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			
		   <td >
				婚姻状况：
				</td>
			<td>
				<div id="marriageDesc"></div>
		   </td>
		   <td>
				毕业学校：
				</td>
			<td >
				<div id="graduateSchool" ></div>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				户口类型：
				</td>
			<td>
				<div id="householdDesc"></div>
			</td>
			<td>
				健康状况：
				</td>
			<td>
				<div id="health" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				户口所在地：
				</td>
			<td>
				<div id="householdPlace" ></div>
			</td>
			<td >
				入职时间：
				</td>
			<td>
				<div id='joinDateDesc' ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				政治面貌：
				</td>
			<td>
				<div id="politicsDesc"></div>
			</td>
			<td >
				入党（团）时间：
				</td>
			<td>
				<div id='joinPartyDateDesc'></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td >
				专业：
				</td>
			<td>
				<div class="Bigdiv" id="major" ></div>
			</td>
			<td >
				毕业时间：
				</td>
			<td>
				<div  id='graduateDateDesc' ></div>
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				学历：
				</td>
			<td>
				<div id="educationDegreeDesc"></div>
			</td>
			<td >
				学位：
				</td>
			<td>
				<div id="degreeDesc"></div>
			</td>
		</tr>
		<tr  class="TableHeader">
			<td colspan="4"  class='TableHeader'>
				<div class="">
					联系信息：
				</div>
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				手机号码：
				</td>
			<td>
				<div  id="mobileNo"  ></div>
			</td>
			<td >
				电话号码：
				</td>
			<td>
				<div  id='telNo' ></div>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				电子邮件：
				</td>
			<td colspan="3">
				<div id="email" ></div>
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				QQ号码：
				</td>
			<td>
				<div id="qqNo"  ></div>
			</td>
			<td >
				MSN：
				</td>
			<td>
				<div  id='msn' ></div>
			</td>
		</tr>
			<tr class='TableData'>
			<td >
				家庭地址：
				</td>
			<td colspan="3">
				<div class="Bigdiv" id="address"></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>相关附件：</b>
			</td>
			<td colspan="3">
				<div style="min-height:50px;">
		      			<span id="attachments"></span>
			      	<%-- 	<div id="fileContainer2"></div>
						<a id="uploadHolder2" class="add_swfupload">
							<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
						</a>
						<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		      		</div> --%>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				其他联系地址：
				</td>
			<td colspan="3" >
				<div id="otherAddress" ></div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>