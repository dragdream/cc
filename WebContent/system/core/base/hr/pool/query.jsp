<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<title>管理人才档案</title>
<script type="text/javascript">

function doInit(){
	getHrCode();
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form1"));
	
	var employeeStatusStr = "";
	$("#form1").find("input[name='employeeStatusStr']").each(function(i,obj){
		if($(obj).attr("checked")){
			if(employeeStatusStr != ""){
				employeeStatusStr = employeeStatusStr + "," + $(obj).val() ;
			}else{
				employeeStatusStr =  $(obj).val() ;
			}
		}
	});
	queryParams["employeeStatusStr"] = employeeStatusStr;
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/hrPoolController/queryHrPoolList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		queryParams:queryParams,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planName',title:'计划名称',width:100,
				formatter:function(value,rowData,rowIndex){
					return "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.planId+")'>"+ rowData.planName +"</a>";
			}},
			{field:'employeeName',title:'应聘者姓名',width:40},
			
		
			{field:'employeeBirthStr',title:'出生日期',width:40,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'employeePhone',title:'联系电话',width:40},
			
			{field:'employeeHighestSchoolDesc',title:'学历',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			
			
			{field:'employeeMajorDesc',title:'专业',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'positionDesc',title:'岗位',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'expectedSalary',title:'期望薪水',width:50,
			},
			{field:'createTimeStr',title:'创建日期',width:80,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'2',title:'操作',width:80,formatter:function(value, rowData, rowIndex){
				var str = "";//"<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
			
				str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='toAddOrUpdate("+rowData.sid+")'>修改</a>";
			
				str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='deleteObjFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}


/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘计划详细信息";
  var url = contextPath + "/system/core/base/hr/recruit/plan/recruitPlanDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 编辑信息
 */
function toAddOrUpdate(sid){
    var url = contextPath + "/system/core/base/hr/pool/addOrUpdate.jsp?sid=" + sid;
 	window.location.href = url;
}




/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	  $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		  var url = contextPath + "/hrPoolController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){	
				$.MsgBox.Alert_auto("删除成功！",function(){
					datagrid.datagrid('reload');
				});
				
			}
	  });
}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}
//根据条件查询
function doSearch(){
	 $('#searchDiv').toggle();
	 $("#optItem").show();
	 $(".datagrid-view").show();

	  getInfoList();
	 
}
//返回
function toReturn(){
	window.location.reload();

}
function doSearchFunc(){
	  datagrid.datagrid("reload"); 
}
/**
 * 获取所有代码
 */
function getHrCode(){
	//整治面貌
	var prcs = getHrCodeByParentCodeNo("STAFF_POLITICAL_STATUS" , "employeePoliticalStatus");
	var prcs = getHrCodeByParentCodeNo("JOB_CATEGORY" , "jobCategory");//期望工作性质
	var prcs = getHrCodeByParentCodeNo("POOL_POSITION" , "position");//岗位
	var prcs = getHrCodeByParentCodeNo("POOL_EMPLOYEE_MAJOR" , "employeeMajor");//专业
	var prcs = getHrCodeByParentCodeNo("EMPLOYEE_HIGHEST_DEGREE" , "employeeHighestDegree");//学位
	var prcs = getHrCodeByParentCodeNo("STAFF_HIGHEST_SCHOOL" , "employeeHighestSchool");//学历
	
	
	var prcs = getHrCodeByParentCodeNo("AREA" , "employeeNativePlace");///籍贯
	var prcs = getHrCodeByParentCodeNo("PLAN_DITCH" , "recruChannel");///招聘渠道
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		
		
		<form id="form1" name="form1" method="post">
			<div style="margin-top:10px;" id="searchDiv">
				<table class="TableBlock_page"  style="text-align: left;" align="center" >
					<tr>
						<td class="TableData" width="15%" style="text-indent: 10px;">计划编号：</td>
						<td class="TableData">
							<input class="BigInput" type="text" id = "planNo" name='planNo' size="20"/>
						</td>
				     </tr>
				      <tr>
						<td class="TableData" style="text-indent: 10px;">应聘人姓名：</td>
						<td class="TableData">
							<input type="text" name="employeeName" class="BigInput">
						</td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px;">人才状态：</td>
						<td class="TableData">
							<input  type="checkbox" name="employeeStatusStr"   value="1" id="employeeStatus1"/><label for="employeeStatus1"> &nbsp;未入职 &nbsp;&nbsp;</label> 
							<input  type="checkbox" name="employeeStatusStr" value="2"  id="employeeStatus2" /><label for="employeeStatus2">&nbsp; 已通过&nbsp;&nbsp;</label>
							<input  type="checkbox" name="employeeStatusStr" value="3"  id="employeeStatus3" /> <label for="employeeStatus3">&nbsp;未通过 &nbsp;&nbsp;</label>
							<input  type="checkbox" name="employeeStatusStr" value="4"  id="employeeStatus4" /><label for="employeeStatus4">&nbsp; 已发Offer&nbsp;&nbsp;</label>
							<input  type="checkbox" name="employeeStatusStr" value="5"  id="employeeStatus5" /> <label for="employeeStatus5">&nbsp;已入职</label>
						</td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px;">性别：</td>
						<td class="TableData">
							<select name="employeeSex" class="BigSelect">
								 <option value="">请选择</option>
						         <option value="0">男</option>
						         <option value="1">女</option>
						     </select>
						</td>
					
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px;">籍贯：</td>
						<td class="TableData">
							<select name="employeeNativePlace" id="employeeNativePlace" class="BigSelect">
			 					<option value="">请选择</option>
	     					 </select>
						</td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px;">政治面貌：</td>
						<td class="TableData">
							<select name="employeePoliticalStatus" id="employeePoliticalStatus" class="BigSelect">
					          <option value="">请选择</option>
					         
					        </select>
						</td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px;">婚姻状况：</td>
						<td class="TableData">
							 <select name="employeeMaritalStatus" class="BigSelect">
						        <option value="">请选择</option>
						        <option value="0">未婚&nbsp;&nbsp;</option>
						        <option value="1">已婚</option>
						        <option value="2">离异</option>
						        <option value="3">丧偶</option>
						      </select>
						</td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px;">期望工作性质：</td>
						<td  class="TableData">
						    <select name="jobCategory" id="jobCategory" class="BigSelect " >
							    <option value=""> 请选择</option>
					        </select>
						</td>
                     </tr>
                     <tr>
						<td class="TableData" style="text-indent: 10px;">岗位：</td>
						<td class="TableData">
							<select name="position" id="position"  class="BigSelect">
									<option value="">请选择</option>
					
						     </select>
						</td>
					</tr>
					
					<tr>
						<td class="TableData" style="text-indent: 10px;">期望工作城市：</td>
						<td  class="TableData">
		
					        <input type="text" name="workCity" class="BigInput" validType="number[]">
						</td>
                     </tr>
                     <tr>
						<td class="TableData" style="text-indent: 10px;">期望薪水(税前)：</td>
						<td class="TableData">
						
						 <input type="text" name="minExpectedSalary" id="minExpectedSalary"
							class="BigInput  easyui-validatebox" size="5" maxlength="8"  validType ='number[]'>
							~
							 <input type="text" name="maxExpectedSalary" id="maxExpectedSalary"
							class="BigInput  easyui-validatebox" size="5" maxlength="8"  validType ='number[]'>
		
						</td>
					</tr>
					 <tr>
					    <td nowrap class="TableData" width="100" style="text-indent: 10px;">所学专业：</td>
					    <td class="TableData"  width="180">
					    	<select name="employeeMajor" id="employeeMajor" class="BigSelect">
								<option value="">请选择</option>
							
					     </select>
					    </td>
					</tr>
					<tr>
					    <td nowrap class="TableData" width="100" style="text-indent: 10px;">学历：</td>
					    <td class="TableData"  width="180">
					        <select name="employeeHighestSchool" id="employeeHighestSchool"  class="BigSelect " >
					        	<option value="">请选择</option>
									
					        </select>
					    </td>
					  </tr>
					<tr align="center">
						<td class="TableData" colspan="2">
						   <div align="right">
						      <input type="button" class="btn-win-white" onclick="doSearch();" value="查询">
							&nbsp;&nbsp;<input type="reset" value="重置" class="btn-del-red" >
						   </div>
							
						</td>
						
					</tr>
					
				</table>
			</div>
		
		
		</form>
		
		<div style="text-align:left;display:none;margin: 10px;" id="optItem">
			<button class="btn-del-red" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			<button class="btn-win-white" onclick="toReturn()">返回</button>&nbsp;&nbsp;
		</div>
	</div>
</body>
</html>