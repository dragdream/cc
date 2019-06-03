<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%

	String humanDocSid = request.getParameter("sid");
    String personName = request.getParameter("personName");
    String deptId = request.getParameter("deptId");
   	String deptName = request.getParameter("deptName");
    String model = TeeAttachmentModelKeys.humanDoc;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<style>
td{
padding:5px;
}
</style>
<script>
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
var deptId = "<%=deptId%>";
var deptName;
function doInit(){
	//getDeptParent();
 	getHrCodeByParentCodeNo("PM_STATUS_TYPE","statusType");
    //doInitUpload();
	getHrCodeByParentCodeNo("PM_EMPLOYEE_TYPE","employeeType");
	getHrCodeByParentCodeNo("PM_MARRIAGE","marriage");
	getHrCodeByParentCodeNo("PM_HOUSEHOLD","household");//户口类型
	getHrCodeByParentCodeNo("PM_POLITICS","politics");
	getHrCodeByParentCodeNo("PM_EDUCATIONDEGREE","educationDegree");
	getHrCodeByParentCodeNo("PM_DEGREE","degree");
	getInsurances();
	renderCustomerField();
	var url = contextPath+"/humanDocController/getInfoById.action?sid="+humanDocSid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
		//alert(tools.jsonObj2String(json.rtData));
		var  attachmodels = json.rtData.attachmodels;
		for(var i=0;i<attachmodels.length;i++){
			var temp = attachmodels[i];
			temp["priv"] = 3;
			var fileItem = tools.getAttachElement(temp);
			$("#attachments").append(fileItem);
		}
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

function getInsurances(){
	var url = contextPath+"/salaryManage/datagridInsurances.action";
	var json = tools.requestJsonRs(url,{});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$("#insuranceId").append("<option value='"+list[i].sid+"'>"+list[i].insuranceName+"</option>");
	}
}

//动态渲染自定义字段
function renderCustomerField(){
	$("#customTbody").html("");
	var url=contextPath+"/humanDocController/getListFieldByHuman.action";
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			var name="EXTRA_"+data[i].sid;
			if(data[i].filedType=="单行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
						   +"<td align=\"left\" name='"+name+"' id='"+name+"'>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="多行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
						   +"<td  align=\"left\" name='"+name+"' id='"+name+"'>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="下拉列表"){
				/* var fieldCtrModel=data[i].fieldCtrModel;
				var j=tools.strToJson(fieldCtrModel); */
				if(data[i].codeType=="HR系统编码"){
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
							   +"<td  align=\"left\" name='"+name+"' id='"+name+"'>"
							   +"</td>"
							   +"</tr>");
					getHrCodeByParentCodeNo(data[i].sysCode,name);
					//getSysCodeByParentCodeNo(j.value,name);
				}else if(data[i].codeType=="自定义选项"){
					//var values=j.value;
					var optionNames=data[i].optionName.split(",");
					var optionValues=data[i].optionValue.split(",");
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
							   +"<td  align=\"left\" name='"+name+"' id='"+name+"'>"
							   +"</td>"
							   +"</tr>");
					for(var j=0;j<optionNames.length;j++){
						$("#"+name).append("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
					}
					
				}
				
			}
			
		}
		
	}
}

function getOtherInfo(){
	var type=$("#otherInfo").val();
	var url="";
	switch(type){
		case "1":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/ht.jsp";
			break;
		case "2":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/jc.jsp";
			break;
		case "3":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/zs.jsp";
			break;
		case "4":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/px.jsp";
			break;
		case "5":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/xxjl.jsp";
			break;
		case "6":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/gzjl.jsp";
			break;
		case "7":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/gzjn.jsp";
			break;
		case "8":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/shgx.jsp";
			break;
		case "9":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/rsdd.jsp";
			break;
		case "10":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/lz.jsp";
			break;
		case "11":
			url=contextPath+"/system/core/base/pm/archivesManage/detailList/fz.jsp";
			break;
	    default:
	    	return;
	}
	openFullWindow(url+"?humanDocSid="+humanDocSid+"&personName="+personName,"人事信息");

}
</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;overflow-x:hidden;"" onload="doInit();">
<div class="topbar clearfix" id="toolbar">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_detail.png">
		&nbsp;&nbsp;<span class="title"><%=personName %>  的档案详情</span>
	</div>
    <div class="fr">
					<span style='float:right;'>
						<select style="width: 200px;height: 28px;" id="otherInfo" name="otherInfo"  class="BigSelect" onchange="getOtherInfo()" >
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
</div>
	<table style="width: 100%;margin-top: 10px;">
	<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">基本信息</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				人员姓名：
			</td>
			<td>
				<div id="personName"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				身份证号：
				</td>
			<td>
				<div id="idCard"></div>
			</td>
				</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				籍贯：
				</td>
			<td>
				<div id="nativePlace" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				档案编号：
				</td>
			<td>
				<div id="codeNumber" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				工号：
			</td>
			<td>
				<div  id="workNumber" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				员工状态：
			</td>
			<td>
				<div id="statusType" name="statusType" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				员工类型：
				</td>
			<td>
				<div id="employeeType"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				英文名：
				</td>
			<td>
				<div id="englishName" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				性别：
			</td>
			<td>
				<div id="gender" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				出生日期：
				</td>
			<td>
				<div id='birthdayDesc' ></div>
		   </td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				民族：
			</td>
			<td>
				<div id="ethnicity"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				默认年假天数：
				</td>
			<td>
				<div id="defaultAnnualLeaveDays" name="defaultAnnualLeaveDays" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				职务：
				</td>
			<td>
				<div class="Bigdiv" id="postState" ></div>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				婚姻状况：
				</td>
			<td>
				<div id="marriage"></div>
		   </td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				毕业学校：
				</td>
			<td >
				<div id="graduateSchool" ></div>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				户口类型：
				</td>
			<td>
				<div id="household"></div>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				健康状况：
				</td>
			<td>
				<div id="health" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				户口所在地：
				</td>
			<td>
				<div id="householdPlace" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				入职时间：
				</td>
			<td>
				<div id='joinDateDesc' ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				政治面貌：
				</td>
			<td>
				<div id="politics"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				入党（团）时间：
				</td>
			<td>
				<div id='joinPartyDateDesc'></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				专业：
				</td>
			<td>
				<div class="Bigdiv" id="major" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				毕业时间：
				</td>
			<td>
				<div  id='graduateDateDesc' ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				学历：
				</td>
			<td>
				<div id="educationDegree"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				学位：
				</td>
			<td>
				<div id="degree"></div>
			</td>
		</tr>
		<tr class='TableData' align="left">
			<td class="TableData" width="150" style="text-indent: 15px;">
				保险套账：
			</td>
			<td>
			    <div id="insuranceName" name="insuranceName"></div>
			</td>
		</tr>
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">关联信息</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	</tr>
	<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				关联用户：
				</td>
				<td>
				<div id="userName"></div>
				</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				所在部门：
			</td>
			<td>
				<div id="deptName"></div>
			</td>
		</tr>
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">联系方式</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				手机号码：
				</td>
			<td>
				<div  id="mobileNo"  ></div>
			</td>		
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				电话号码：
				</td>
			<td>
				<div  id='telNo' ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				电子邮件：
				</td>
			<td>
				<div id="email" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				QQ号码：
				</td>
			<td>
				<div id="qqNo"  ></div>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				MSN：
				</td>
			<td>
				<div  id='msn' ></div>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				家庭地址：
				</td>
			<td>
				<div class="Bigdiv" id="address"></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				其他联系地址：
				</td>
			<td>
				<div id="otherAddress" ></div>
			</td>
		</tr>
	<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">相关附件</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				附件：
			</td>
			<td>
				<div style="min-height:30px;">
		      			<span id="attachments"></span>
			</td>
		</tr>
	</table>
	 <table style="width: 100%;">
  <thead>
     <tr>
		<TD colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		&nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">自定义字段</span>
	    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
     </tr>
  </thead>
  <tbody id="customTbody" >
     
  </tbody>
</table> 
</br>
</br>
</body>
</html>