 <%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//筛选ID
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/filter.js"></script>

<title>筛选</title>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	getHrCode();
	if(sid > 0){
		getInfoById(sid);
	}
}

function getHrCode(){
	//整治面貌
	var prcs = getHrCodeByParentCodeNo("HR_RECRUIT_FILTER" , "filterMethod");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/hrFilterController/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			var filterStateDesc = "(待筛选)";
			if(prc.filterState == '1'){
				filterStateDesc = "(筛选已通过)";
			}else if(prc.filterState == '2'){
				filterStateDesc = "(筛选未通过)";
			}
			$("#filterStateDesc").append(filterStateDesc);
			$("#nextDatetimeStr").empty();
			if(prc.nextDatetime > 0){
				$("#nextDatetimeStr").append(getFormatDateStr(prc.nextDatetime,'yyyy-MM-dd HH:mm:ss'));
			}
			
			var itemModelList = prc.itemModelList;
			var type= 0;
			var currUserhandle = false;//判断是否是当前用户办理的
			if(itemModelList.length > 0 ){
				for(var i =0 ;i<itemModelList.length ; i++){
					var filterItem = itemModelList[i];
					var filterState = filterItem.filterState;
					var filterStateDesc = "未通过";
					if(filterState == '1'){
						filterStateDesc = "已通过";
					}
					var tableStr = '<table class="TableBlock" width="80%" align="center" id="table1">'
					  +'<tr class="TableHeader">'
					  +' <td nowrap colspan="4" align="center" >筛选步骤'+filterItemType[i].itemId+'</td>'
					  +' </tr>'
					  +' <tr>'
					   +'  <td nowrap class="TableData">'+filterItemType[i].itemName+'时间：</td>'
					   +'  <td class="TableData">'+getFormatDateStr(filterItem.filterDatetime,'yyyy-MM-dd HH:mm:ss')+'</td>'
					   +' <td nowrap class="TableData">'+filterItemType[i].itemName+'方式：</td>'
					   +' <td class="TableData" >'+filterItem.filterMethodDesc +'</td> '
					   +' </tr>'
					   +'<tr>'
					   +'  <td nowrap class="TableData">'+filterItemType[i].itemName+'内容：</td>'
					   +'  <td class="TableData" colspan=3>'+filterItem.filterContent+'</td>'
					   +' </tr>'
					   +'<tr>'
					   +' <td nowrap class="TableData">'+filterItemType[i].itemName+'意见：</td>'
					   +' <td class="TableData" colspan=3>'+filterItem.filterView+' </td>'
					   +' </tr>  '
					   +' <tr>'
					   +'<td nowrap class="TableData">'+filterItemType[i].itemName+'办理人：</td>'
					   +' <td class="TableData" >'+filterItem.transactorStepName+ '</td>'
					   +' <td nowrap class="TableData">是否通过：</td>'
					   +' <td class="TableData">'+filterStateDesc+'</td>'
					   +' </tr>'
					   +'<tr>'
					   +' <td nowrap class="TableData">下一步骤办理人：</td>'
					   +' <td class="TableData">'+filterItem.nextTransactorStepName+ '</td>'
					   +'<td nowrap class="TableData">下一次筛选时间：</td>'
					   +'<td class="TableData">'+getFormatDateStr(filterItem.nextFilterDatetime,'yyyy-MM-dd HH:mm:ss')+' </td>'
					   +'</tr>'
					   +'</table>';
					$("#filterInfo").append(tableStr + "<br>");
					
					if(i== (itemModelList.length-1)){//最一个,判断办理用户是否是当前用户
						var nextTransactorStepId = filterItem.nextTransactorStepId;
						if(nextTransactorStepId  == loginPersonId){
							currUserhandle = true;
						}
					}
				}
				type = itemModelList.length ;
			}else{
				var  nextTransactorId= prc.nextTransactorId;
				if(nextTransactorId  == loginPersonId){
					currUserhandle = true;
				}
			}
			
			if(currUserhandle){//如果是，则办理
				$("#currHeadle").show();
			}
			
			$("#filterTypeDesc").append(filterItemType[type].itemId);
			$("#filterDatetimeContent").append(filterItemType[type].itemName);
			$("#filterMethodContent").append(filterItemType[type].itemName);
		
			$("#filterContentContent").append(filterItemType[type].itemName);
			$("#filterViewContent").append(filterItemType[type].itemName);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}
function checkForm(type){
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}

	if(type == '0'){
		if($("#nextTransactorStepName").val() == ''){
			alert("下一步筛选人不能为空！");
			return false;
		}
		
		if($("#nextFilterDatetime").val() == ''){
			alert("下一步筛选时间不能为空！");
			return false;
		}
	}
	
	
	return true;
}


function doSaveOrUpdate(type){
	if(checkForm(type)){
	    var url = "<%=contextPath %>/hrFilterItemController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    para['type'] = type;
	    var jsonRs = tools.requestJsonRs(url,para);
	   	if(jsonRs.rtState){
		   window.location.href = "manager.jsp";
		   top.$.jBox.tip("保存成功!",'info',{timeout:1500});
	   }else{
		   alert(jsonRs.rtMsg);
	   }
	}
}

/**
 * 返回
 */
function toReturn(){
	window.location.href = "manager.jsp";
}
function setNextInfo(value){
	if(value == '1'){
		$("#nextInfo").show();
		$("#BTN_NEXT").show();
	}else{
		$("#BTN_NEXT").hide();
		$("#nextInfo").hide();
	}
}

</script>
</head>
<body onload="doInit()" >
<!-- <div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;
		筛选办理</b>
</div> -->

<table align="center" width="80%" class="TableBlock" >
  <tr class="TableHeader">
    <td nowrap  colspan="4" align="center">筛选准备 &nbsp;&nbsp;<font color="red" id="filterStateDesc"></font</td>
  </tr>
  <tr>
    <td nowrap class="TableData">应聘者姓名：</td>
    <td class="TableData" id="hrPoolName">td>
   <td nowrap class="TableData">计划名称：</td>
    <td class="TableData"  id="planName" ></td>  
  </tr>
  <tr>
    <td nowrap class="TableData"  >应聘岗位：</td>
    <td class="TableData" id="position">  </td> 
    <td nowrap class="TableData" >所学专业：</td>
    <td class="TableData" id="employeeMajor" ></td>    
  </tr>
  <tr>
    <td nowrap class="TableData"  >联系电话：</td>
    <td class="TableData" id="employeePhone">  </td>
    <td nowrap class="TableData">发起人：</td>
    <td class="TableData" id="sendPersonId" >
    </td>     
  </tr>
  <tr>
    <td nowrap class="TableData">下一次筛选办理人：</td>
    <td class="TableData" id="nextTransactorName" > </td>
    <td nowrap class="TableData" >下一次筛选时间：</td>
    <td class="TableData" id="nextDatetimeStr" > </td>    
  </tr>
</table>
<br>
<div id="filterInfo"></div>
<div id="currHeadle" style="display:none;">
	<form action=""  method="post" name="form1" id="form1">
	
		<input type="hidden" name="filterId" id="filterId" value="<%=sid%>">
		
		<table class="TableBlock" width="80%" align="center" id="table4">
			 <tr class="TableHeader">
			    <td nowrap  colspan="4" align="center" >筛选步骤<span id="filterTypeDesc"></span></td>
			  </tr>
			 </tr>
			    <tr>
			    <td nowrap class="TableData"  ><span id="filterDatetimeContent"></span>日期：</td>
			    <td class="TableData">
			      <input type="text" name="filterDatetime" size="20" maxlength="20" class="BigInput" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			      
			    </td>
			    <td nowrap class="TableData"><span id="filterMethodContent"></span>方式：</td>
			    <td class="TableData" >
			      <select name="filterMethod" id="filterMethod" style="" class="BigSelect" title="筛选方式可在“人事管理”->“人力资源设置”->“HR代码设置”模块设置。">
	
			      </select>
			    </td> 
			  </tr>
			  <tr>
			    <td nowrap class="TableData"><span id="filterContentContent"></span>内容：</td>
			    <td class="TableData" colspan=3>
			      <textarea name="filterContent" cols="67" rows="4" class="BigTextarea" value=""></textarea>
			    </td>
			  </tr>
			  <tr>
			    <td nowrap class="TableData"><span id="filterViewContent"></span>意见：</td>
			    <td class="TableData" colspan=3>
			      <textarea name="filterView" cols="67" rows="4" class="BigTextarea" value=""></textarea>
			    </td>
			  </tr>  
			  <tr>
				  	<td nowrap class="TableData">当前办理人：</td>
				    <td class="TableData" >
				      <INPUT type="text" name="transactorStepName" size="15" class="BigStatic BigInput" readonly value="<%=userName %>">&nbsp;
				    </td>
				    <td nowrap class="TableData">是否通过：</td>
				    <td class="TableData">
				      <select name="filterState" class="BigSelect" title="" onchange="setNextInfo(this.value)">
				        <option value="2" selected>未通过</option>
				        <option value="1" >通过</option>
				      </select>
				    </td>
			  </tr>
			  
			  <tr style="display:none;" id="nextInfo">
				  	<td nowrap class="TableData">下一次筛选人：</td>
				    <td class="TableData" >
				      <INPUT type="text" name="nextTransactorStepName"  id="nextTransactorStepName" size="15" class="BigStatic BigInput" readonly value="">&nbsp;
				      <INPUT type="hidden" name="nextTransactorStepId" id="nextTransactorStepId" value="">
				     <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['nextTransactorStepId', 'nextTransactorStepName']);">添加</a>
					  <a href="javascript:void(0);" class="orgClear" onClick="$('#nextTransactorStepId').val('');$('#nextTransactorStepName').val('');">清空</a>
			    
				    </td>
				    <td nowrap class="TableData">下一次筛选时间：</td>
				    <td class="TableData">
				     <input type="text" name="nextFilterDatetime" id="nextFilterDatetime" size="20" maxlength="20" class="BigInput" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			   
				    </td>
			  </tr>
			 <!--  <tr>
			    <td nowrap class="TableData"> 提醒：</td>
			    <td class="TableData" colspan=3></td>
			  </tr> -->
			  
			  <tr align="center" class="TableControl">
	  			  <td colspan=4 nowrap>
			        <input type="button" id="BTN_NEXT" value="下一步骤" class="btn btn-primary" style="display:none" onclick="doSaveOrUpdate('0');">&nbsp;
				    <input type="button" id="BTN_FINISH" value="结束筛选" class="btn btn-primary"  onclick="doSaveOrUpdate('1');">&nbsp;
				    
				    <input type="button" id="" value="返回" class="btn"  onclick="window.location.href='manager.jsp';">&nbsp;
		
		    </td>
	  </tr>
	</table>	
	</form>
</div>
<br>

</body>
</html>
 