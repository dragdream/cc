<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String model = TeeAttachmentModelKeys.RECRUIT_REQUIREMENTS;
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
int optType = TeeStringUtil.getInteger(request.getParameter("optType"), 0);//操作类型 1-人事专员 人才推荐 0-其他


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<style>
.TableList {border:1px solid #F2f2f2} 

</style>
<title>编辑招聘信息</title>
<script type="text/javascript">
var sid = "<%=sid%>";
var editor;
var uEditorObj;//uEditor编辑器
var STAFF_HIGHEST_SCHOOL_STR = "";//学历
var POOL_EMPLOYEE_MAJOR_STR = "";//专业
var POOL_POSITION_STR = "";//岗位
function doInit(){
	uEditorObj = UE.getEditor('requRequires');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	    
		//初始化附件
		new TeeSWFUpload({
			fileContainer:"fileContainer",//文件列表容器
			uploadHolder:"uploadHolder",//上传按钮放置容器
			valuesHolder:"attacheIds",//附件主键返回值容器，是个input
			queueComplele:function(){//队列上传成功回调函数，可有可无
				
			},
			renderFiles:true,//渲染附件
			post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
		});
		if(sid > 0){
			getInfoById(sid);
		}
	
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
	
	});

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
			uEditorObj.setContent(prc.requRequires);
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#fileContainer").append(fileItem);
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



function checkForm(){
	
	var check = $("#form1").valid(); 
	if(!check){
		return false; 
	}else{
		//判断岗位要求
		var requRequires=uEditorObj.getContent();
		if(requRequires==null||requRequires==""){
			$.MsgBox.Alert_auto("请填写岗位要求！");
			return false;
		}else{
			return true;
		}
	}

}


function doSaveOrUpdate(optType){
	if(checkForm()){
		getHrPoolItem();
		var url = "<%=contextPath %>/recruitRequirementsController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		para["requRequires"] = uEditorObj.getContent();
		para["isSelectPoolsIds"]  = isSelectPoolsIds;
		var addHrPoolsItem = tools.jsonArray2String(hrPoolsItem);
		para["addHrPoolsItem"]  = addHrPoolsItem;
		para["optType"]  = optType;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			
			$.MsgBox.Alert_auto("保存成功!",function(){
				toReturn();
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function toReturn(){
	window.location.href = contextPath + "/system/core/base/hr/recruit/requirements/index.jsp";
}

/**
 * 点击人才库调用函数
 */
function queryHrPoolsCallBackFunc(dataList){
	for(var i = 0 ; i<dataList.length ; i++){
		var item = dataList[i];
		addHrPoolsItem(item , 1);
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
	var employeeName_input = $("<input "+disabled+" style='width:80px;height:19px;' type='text'>").addClass("BigInput").val(employeeName_value);
	var employeeName=$("<td>").append(employeeName_input);
	employeeName_input.attr("required","true"); 
	//性别
	var sex = $("<td>").append($("<select "+disabled+">").append("<option value='0'>男</option><option value='1'>女</option>").addClass("BigSelect").val(sex_value));
	
	//联系电话
	var employeePhone_input = $("<input "+disabled+" style='width:100px;height:19px;' type='text'>").addClass("BigInput").val(employeePhone_value);
	var employeePhone=$("<td>").append(employeePhone_input);
	employeePhone_input.attr("required","true");
	//邮箱
	var employeeEmail_input = $("<input "+disabled+" type='text' style='width:100px;height:19px;'>").addClass("BigInput").val(employeeEmail_value);
	var employeeEmail =$("<td>").append(employeeEmail_input);
	employeeEmail_input.attr("required","true");
	
	//出生日期 
	var employeeBirthStr_input = $("<input "+disabled+" style='width:100px;height:19px;' type='text' name='employeeBirth' class='Wdate BigInput' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" >").addClass("BigInput1").val(employeeBirthStr_value);
	var employeeBirth=$("<td>").append(employeeBirthStr_input);
	
	//薪水
	var expectedSalary_input = $("<input "+disabled+" style='width:50px;height:19px;' type='text'>").addClass("BigInput").val(expectedSalary_value);
	var expectedSalary=$("<td>").append(expectedSalary_input);
	expectedSalary_input.attr("required","true");
	//学历
	var STAFF_HIGHEST_SCHOOL=$("<td>").append($("<select "+disabled+">").append(STAFF_HIGHEST_SCHOOL_STR).addClass("BigSelect").val(employeeHighestSchoolDesc_value));
	//专业
	var POOL_EMPLOYEE_MAJOR=$("<td>").append($("<select "+disabled+">").append(POOL_EMPLOYEE_MAJOR_STR).addClass("BigSelect").val(employeeMajorDesc_value));
	//岗位
	var POOL_POSITION =$("<td>").append($("<select "+disabled+">").append(POOL_POSITION_STR).addClass("BigSelect").val(positionDesc_value));
	var delTd=$("<td>").append($("<button name='"+sid+"' style='width:23px;'>-</button>"
	).addClass("btn-del-red").addClass("btn").click(function(){
		selectPoolsIds[$(this).attr("name") + ""] = "";//将对象某个属性赋值为空
		$(this).parent("td").parent("tr").remove();
		
	}));
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
	tr.append(delTd);
	$("#hrPoolList").append(tr);
}

/**
 *获取人才库明细列表转JSON数组
 */
var hrPoolsItem ;
var isSelectPoolsIds;
function getHrPoolItem(){
	hrPoolsItem = new Array();
	isSelectPoolsIds = "";
	var itemList = $("#hrPoolList").find("tr");
	for(var i =0; i <itemList.length ; i++){
		var itemTemp = itemList[i];
		var isSelectTypeTemp = $(itemTemp).attr("isSelectType");
		if(isSelectTypeTemp == '0'){
			var employeeName = $(itemTemp.cells[0]).find("input").val();
			var sex = $(itemTemp.cells[1]).find("select").val();
			var employeePhone = $(itemTemp.cells[2]).find("input").val();
			var employeeEmail = $(itemTemp.cells[3]).find("input").val();
			var employeeBirth = $(itemTemp.cells[4]).find("input").val();
			var expectedSalary = $(itemTemp.cells[5]).find("input").val();
			var STAFF_HIGHEST_SCHOOL = $(itemTemp.cells[6]).find("select").val();
			var POOL_EMPLOYEE_MAJOR = $(itemTemp.cells[7]).find("select").val();
			var POOL_POSITION = $(itemTemp.cells[8]).find("select").val();
			var itemp = {employeeName : employeeName ,sex : sex, employeePhone: employeePhone , employeeEmail: employeeEmail,
					employeeBirth:employeeBirth,expectedSalary:expectedSalary,employeeHighestSchool:STAFF_HIGHEST_SCHOOL , employeeMajor:POOL_EMPLOYEE_MAJOR,position:POOL_POSITION};
			hrPoolsItem.push(itemp);
		}else{
			if(isSelectPoolsIds != ""){
				isSelectPoolsIds = isSelectPoolsIds + "," + isSelectTypeTemp;
			}else{
				isSelectPoolsIds = isSelectTypeTemp;
			}
		}
	}
	return hrPoolsItem;
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px" >
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/hr/imgs/icon_bjxqxx.png">
		<span class="title">新建/编辑需求信息</span>
	</div>
	<div class="right fr clearfix" style="position:static;">
        <%if(optType == 0){ %>
		<input type="button"  value="保存" class="btn-win-white fl" onclick="doSaveOrUpdate(0);"/>
		<input type="button"  value="需求确认" class="btn-win-white fl" onclick="doSaveOrUpdate(2);"/>
		<%}else{ %>
		<input type="button"  value="人才推荐" class="btn-win-white fl" onclick="doSaveOrUpdate(1);"/>
		<%} %>
		<input type="button"  value="返回" class="btn-win-white fl" onclick="toReturn();"/>
	</div>
</div>


<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock_page" >
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent: 15px;">需求编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="requNo" id="requNo" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
		<td nowrap class="TableData"  width="15%;" >需求岗位：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="requJob" id="requJob" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 15px;">用工日期：</td>
		<td class="TableData" width="" >
			<input type="text" name="requTimeStr" id="requTimeStr" size="" class="BigInput Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="">需求人数：</td>
		<td class="TableData" >
			<input type="text" name="requNum" id="requNum" size="" maxlength="9" class="BigInput"  required="true"   positive_integer="true">（人）
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent: 15px;" >需求部门：</td>
		<td class="TableData"  colspan="3">
			<input type='hidden' id='requDeptStr' name='requDeptStr' />
			<textarea cols='60' name='requDeptStrName' id='requDeptStrName' rows='4' style='overflow-y: auto;' class='SmallStatic BigTextarea' wrap='yes' readonly >
			</textarea>
			
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectDept(['requDeptStr','requDeptStrName'],'1')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('requDeptStr','requDeptStrName')" value="清空"/>
			</span>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 15px;">备　　注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="4" cols="60" id="remark" name="remark" class='BigTextarea'></textarea>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  style="text-indent: 15px;">关联人才库：</td>
		<td class="TableData" width="" colspan="3">
	
	
	<div style="padding:0px 5px 0px 5px;">
	    		 	 <input type="button" value="查询人才库" class="btn-win-white" title="人才库查询" onClick="queryHrPools([],'queryHrPoolsCallBackFunc');">&nbsp;&nbsp;
	    			 <table id="" class="TableList" style="width:100%;margin-top:8px;" align="center" >
		    		 	<tr>
		    		 		<td  align="center">姓名</td>
		    		 		<td  align="center">性别</td>
		    		 		<td  align="center">联系方式</td>
		    		 		<td  align="center">邮箱</td>
		    		 		<td  align="center">出生日期</td>
		    		 		<td  align="center">期望薪水</td>
		    		 		<td  align="center">学历</td>
		    		 		<td  align="center">专业</td>
		    		 		<td  align="center">岗位</td>
		    		 		<td  align='center' style="padding-left:5px;"><button name="addUser" id="addUser" required="true" value="+" class="btn-win-white" onclick="addHrPoolsItem();">+</button> </td>
		    		 	</tr>
		    		 	<tbody id="hrPoolList">
		    		 	
		    		 	</tbody>
		    		 
	    		 	</table>
	    		 </div>	
		</td>
	</tr>
	
	
	<tr>
		<td nowrap class="TableData" style="text-indent: 15px;" >附件上传：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" value=""/>
		</td>
	</tr>
	<tr>
		<td class="TableData" style="text-indent: 15px;">
			岗位要求：<font style='color:red'>*</font>
	    </td>
	    <td colspan="3">
			<DIV>
				<textarea  id="requRequires" name="requRequires" class="BigTextarea" required="true" ></textarea>
			</DIV>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			
		</td>
	</tr>
	
</table>
</form>

</body>
<script>
$("#form1").validate();
</script>
</html>