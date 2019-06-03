<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);//主鍵
   int taskTemplateSid=TeeStringUtil.getInteger(request.getParameter("taskTemplateSid"),0);//所属任务模板主键
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>新建/编辑任务模板项</title>
<script type="text/javascript">
var sid=<%=sid %>;
var taskTemplateSid=<%=taskTemplateSid %>;//所属任务模板主键
//初始化
function doInit(){
   if(sid>0){//编辑
	   $("#spanDiv").show();
	   $("#selectDiv").hide();
	   getInfoBySid();
   }else{//新建
	   $("#spanDiv").hide();
	   $("#selectDiv").show();
   }
}




//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/TeeTaskTemplateItemController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		$("#fieldType").val(json.rtData.fieldType);
		changeFieldType();
		bindJsonObj2Cntrl(json.rtData);
			
		if(json.rtData.showAtList==1){//表头列显示
			$("#showAtList").attr("checked","checked");
		}
		
		if(json.rtData.fieldType==1){//单行文本
			$("#fieldTypeName").html("单行文本");
		}else if(json.rtData.fieldType==2){//多行文本
			$("#fieldTypeName").html("多行文本");
		}else if(json.rtData.fieldType==3){//数字文本
			$("#fieldTypeName").html("数字文本");
		}else if(json.rtData.fieldType==4){//日期时间
			$("#fieldTypeName").html("日期时间");
		}else{//下拉菜单
			$("#fieldTypeName").html("下拉菜单");
		    $("#showType1").val(json.rtData.showType);
		}
		 
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
}
//改变字段类型
function changeFieldType(){
	var fieldType=$("#fieldType").val();
	if(fieldType==1){//单行文本
		$("#showTypeTr").hide();
	    $("#calTr").hide();
	}else if(fieldType==3){//数字文本
		$("#showTypeTr").hide();
		 $("#calTr").show();
	}else if(fieldType==2){//多行文本
		$("#calTr").hide();
		$("#showTypeTr").show();
	    //清空下拉列表
	    $("#showType").empty();
	    //隐藏input框  显示下拉列表
		$("#selDiv").show();
		$("#inpDiv").hide();
	    //增加选项
	    $("#showType").append("<option value='普通文本'>普通文本</option><option value='富文本'>富文本</option>");
	    
	}else if(fieldType==5){//下拉列表
		$("#calTr").hide();
		$("#showTypeTr").show();
		//清空下拉列表
	    $("#showType").empty();
		//显示input框  隐藏下拉列表
		$("#selDiv").hide();
		$("#inpDiv").show();
	}else{//日期时间
		$("#calTr").hide();
		$("#showTypeTr").show();
		//清空下拉列表
	    $("#showType").empty();
	    //隐藏input框  显示下拉列表
		$("#selDiv").show();
		$("#inpDiv").hide();
	    //增加选项
	    $("#showType").append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option><option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>");
	}	
}


//保存
function doSave(){
	if($("#form1").valid()){
		var url=contextPath+"/TeeTaskTemplateItemController/addOrUpdate.action";
		var param=tools.formToJson($("#form1"));
		
		var fieldType=$("#fieldType").val();
		if(fieldType==1||fieldType==3){
			param["showType"]="";
		}else if(fieldType==5){//下拉列表
			param["showType"]=$("#showType1").val();
		}
		
		var json=tools.requestJsonRs(url,param);
		return json;
	}
	
}
</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post" >
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData" width="100">字段名称：</td>
			<td class="TableData">
				<input type="text" name="fieldName" id="fieldName" style="height: 23px;width:250px" required/>
			</td>
		</tr>
		<tr><!-- 1=单行文本    2=多行文本   3=数字文本   4=日期时间   5=下拉菜单 -->
			<td nowrap class="TableData" width="100">字段类型：</td>
			<td class="TableData">
			    <div id="selectDiv" style="display: none">
			       <select id="fieldType" name="fieldType" onchange="changeFieldType()">
					   <option value="1">单行文本</option>
					   <option value="2">多行文本</option>
					   <option value="3">数字文本</option>
					   <option value="4">日期时间</option>
					   <option value="5">下拉菜单</option>
				   </select>
			    </div>
				<div id="spanDiv" style="display: none">
			        <span id="fieldTypeName"></span>
			    </div>
			</td>
		</tr>
		<tr id="showTypeTr" style="display: none">
			<td nowrap class="TableData" width="100">显示方式：</td>
			<td class="TableData">
			    <div id="selDiv" style="display: none">
			        <select id="showType" name="showType">
				     
				    </select> 
			    </div>
				 <div id="inpDiv" style="display: none">
				     <input type="text" id="showType1" name="showType1" style="height: 23px;width:250px" required />
				     <span style="">例如：男,女</span>
				 </div> 
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">表头显示：</td>
			<td class="TableData">
				<input type="checkbox" name="showAtList" id="showAtList"/> 
			</td>
		</tr>
		<tr class="TableData" id="calTr" style="display: none">
			<td nowrap>计算方式：</td>
			<td class="TableData">
				<select id="cal" name="cal">
				    <option value=""></option>
				    <option value="SUM">求和</option>
				    <option value="AVG">求平均</option>
				    <option value="MAX">求最大</option>
				    <option value="MIN">求最小</option>
				</select>
			</td>
		</tr>
		
	</table>
	<input id="sid" name="sid" type="hidden" value="<%=sid %>"> 
	<input id="taskTemplateSid" name="taskTemplateSid" type="hidden" value="<%=taskTemplateSid %>"> 
</form>
<script type="text/javascript">
 $("#form1").validate();
</script>
</body>
</html>