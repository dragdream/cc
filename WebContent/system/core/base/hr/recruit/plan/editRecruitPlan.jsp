<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String model = TeeAttachmentModelKeys.RECRUIT_PLAN;
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);

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
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hrPool.js"></script>

<title>编辑招聘计划</title>
<style>
.TableList {border:1px solid #F2f2f2} 

</style>
<script type="text/javascript">
var sid = "<%=sid%>";
var editor;
var recruitRemarkEditor;
var uEditorObj;//uEditor编辑器
var recruitRemarkUEditorObj; //uEditor编辑器

function doInit(){
	
	uEditorObj = UE.getEditor('recruitDescription');//获取编辑器对象
	recruitRemarkUEditorObj= UE.getEditor('recruitRemark');
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	    
		recruitRemarkUEditorObj.ready(function(){//初始化方法
			recruitRemarkUEditorObj.setHeight(200);
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
		});

	});

}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/recruitPlanController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			
			
			uEditorObj.setContent(prc.recruitDescription);
			recruitRemarkUEditorObj.setContent(prc.recruitRemark);
			var attaches = prc.attacheModels;
		
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#fileContainer").append(fileItem);
			}
			
			//招聘需求
			var recruitRequirementsModelList = prc.recruitRequirementsModelList;
			for(var i=0;i<recruitRequirementsModelList.length;i++){
				addHrRecruiRequItem(recruitRequirementsModelList[i]);
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
	}
	if($("#planDitch").val() == ""){
		$.MsgBox.Alert_auto("招聘渠道不能为空！");
		return false; 
	}
	
	if(!isExistsPlanNo()){
		$.MsgBox.Alert_auto("该计划编号已经存在，请重新填写！");
		return false; 
	}
	return true;
}

//判断计划编号是否已经存在
function isExistsPlanNo(){
   var planNo=$("#planNo").val();
   var url=contextPath+"/recruitPlanController/isExistsPlanNo.action";
   var json=tools.requestJsonRs(url,{sid:sid,planNo:planNo});
   if(json.rtState){
	   var data=json.rtData;
	   if(data==0){
		  return true;//不存在 
	   }else{
		   return  false;//计划编号已经存在
	   }
   }
}



function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/recruitPlanController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		para["recruitDescription"] = uEditorObj.getContent();
		para["recruitRemark"] = recruitRemarkUEditorObj.getContent();
		para["requirementIds"] = requirementIds.toString();//关联招聘需求id字符串
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
	window.location.href = contextPath + "/system/core/base/hr/recruit/plan/index.jsp";
}


function doSaveOrUpdate2(callback){
	if(checkForm()){
	    var url = "<%=contextPath %>/recruitPlanController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	      callback();
	      /* setTimeout(function(){
	        //刷新父页面
	        parent.location.reload();
			//return true;
	      },1800); */
	    }else{
	      $.MsgBox.Alert_auto(jsonRs.rtMsg);
	    }
	}
}

/**
 * 点击人才库调用函数
 */
function queryHrRecruiRequCallBackFunc(dataList){
	for(var i = 0 ; i<dataList.length ; i++){
		var item = dataList[i];
		addHrRecruiRequItem(item , 1);
	}
}

/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 */
var requirementIds = new Array();
var selectPoolsIds = {};
function addHrRecruiRequItem(item , optType){
	var sid = item.sid
	if(selectPoolsIds[""+sid]  &&  selectPoolsIds[""+sid]  != "" ){//如果已经存在
		return;
	}
	selectPoolsIds[""+sid] = sid;
	requirementIds.push(sid);
	var index = requirementIds.length -1;//返回索引
	// 需求编号
	var requNo=$("<td>").append(item.requNo);
	//需求岗位
	var requJob = $("<td>").append(item.requJob);
	//创建者名称
	var createUserName = $("<td>").append(item.createUserName);
	//人人数
	var requNum = $("<td>").append(item.requNum);
	//需求部门
	var requDeptStrName = $("<td>").append(item.requDeptStrName);
	//用户日期
	var requTimeStr = $("<td>").append(item.requTimeStr);
	 
	var delTd=$("<td>").append($("<button class='btn-win-white' style='width:30px;' name='"+sid+"' index='"+index+"'>-</button>"
	).addClass("btn-primary").addClass("btn").click(function(){
		selectPoolsIds[$(this).attr("name") + ""] = "";//将对象某个属性赋值为空
		$(this).parent("td").parent("tr").remove();
		var indexTemp = $(this).attr("index") ;
		requirementIds.splice(indexTemp,1);
	}));
	var tr=$("<tr>");
	tr.append(requNo);
	tr.append(requJob);
	tr.append(createUserName);
	tr.append(requNum);
	tr.append(requDeptStrName);
	tr.append(requTimeStr);
	tr.append(delTd);
	$("#hrList").append(tr);
}

/**
 * 删除数组by 索引
 */
Array.prototype.removeForIndex = function(index) {
	if (index > -1) {
		this.splice(index, 1);
	}
};
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/hr/imgs/icon_bjzpjhxx.png">
		<span class="title">新建/编辑招聘计划信息</span>
	</div>
	<div class="right fr clearfix" style="position:static;">
       <input type="button"  value="保存" class="btn-win-white" onclick="doSaveOrUpdate();"/>
	   <input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>
	</div>
</div>


<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock_page" >
	<tr>
		<td nowrap class="TableData"  width="10%;" style="text-indent: 10px" >计划编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="planNo" id="planNo" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
		<td nowrap class="TableData"  width="10%;" >计划名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="planName" id="planName" size="" class="BigInput" required="true" value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="10%;"  style="text-indent: 10px">招聘渠道：</td>
		<td class="TableData" width="35%;" >
			<select id="planDitch" name="planDitch" class="BigSelect">
				<option value="">请选择</option>
				<option value="01">网络招聘</option>
				<option value="02">招聘会招聘</option>
				<option value="03">人才猎头推荐</option>
			</select>
		</td>
		<td nowrap class="TableData"  width="10%;" >预算费用：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="planCost" id="planCost" maxlength ="9" class="BigInput" required="true" validType ='pointTwoNumber[]' value="">元
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="10%;" style="text-indent: 10px">开始日期：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="startDateStr" id="startDateStr" size="" class="BigInput Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDateStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		<td nowrap class="TableData"  width="10%;" >结束日期：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type="text" name="endDateStr" id="endDateStr" size="" class="BigInput Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDateStr\',{d:0});}',dateFmt:'yyyy-MM-dd'})" required="true" value="">
		</td>
		
				
	</tr>
	<tr>
		<td nowrap class="TableData"  width="10%;"  style="text-indent: 10px">招聘人数：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="planRecrNum" id="planRecrNum" size="" maxlength="9" class="BigInput"  required="true"   positive_integer="true"  >（人）
		</td>
		<td nowrap class="TableData"  width="10%;" >审批人：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" >
			<input type=hidden name="approvePersonId" id="approvePersonId" value="">
			<input type="text"  name="approvePersonName" id="approvePersonName" class="BigInput" size="10"  readonly value=""  required="true"></input>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');" value="清空"/>
			</span>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">关联需求：</td>
		<td class="TableData" width="" colspan="3">
		<div style="padding:0px;">
   		 	 <input type="button" value="选择招聘需求" class="btn-win-white" title="人才库查询" onClick="queryHrRecruitRequ([],'queryHrRecruiRequCallBackFunc');">&nbsp;&nbsp;
   			 <table id="" class="TableList" style="width:100%;margin-top:8px;" align="center" >
    		 	<tr>
    		 		<td class="TableData" align="center">需求编号</td>
    		 		<td  class="TableData" align="center">需求岗位</td>
    		 		<td  class="TableData" align="center">提交人</td>
    		 		<td class="TableData" align="center">需求人数</td>
    		 		<td class="TableData" align="center">需求部门</td>
    		 		<td class="TableData" align="center">用工日期</td>
    		 		<td class="TableData" align='center' style="padding-left:5px;">操作</td>
    		 	</tr>
    		 	<tbody id="hrList">
    		 	</tbody>
   		 	</table>
   		 </div>	
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">招聘说明：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="4" cols="80" id="recruitDescription" name="recruitDescription" class="BigTextarea" ></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   style="text-indent: 10px">招聘备注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="4" cols="80" id="recruitRemark" name="recruitRemark"  class="BigTextarea"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px" >附件文档：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"  style="text-indent: 10px">附件上传：</td>
		<td class="TableData"  colspan="3">
			
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" />
		</td>
	</tr>
	
</table>
</form>

</body>
<script>
$("#form1").validate();

</script>
</html>