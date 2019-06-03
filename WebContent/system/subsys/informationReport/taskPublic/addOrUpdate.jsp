<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>新增/编辑</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var sid=<%=sid %>;

var taskType; //任务类型        1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性
var preTimeStr;//预估时间
var ycxTimeStr;//一次性  修改之前的时间点


var flag=1;//默认是设置预估时间
//初始化
function doInit(){
   if(sid>0){
	   $("#selectDiv").hide(); 
	   $("#inputDiv").show(); 
	   getInfoBySid();
	   
   }else{
	  $("#selectDiv").show(); 
	  $("#inputDiv").hide(); 
   }
	
}

//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/TeeTaskTemplateController/getInfoBySid.action?sid="+sid;
    var json=tools.requestJsonRs(url);
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    	//解析上报频次
    	taskType=json.rtData.taskType; //1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性
    	preTimeStr=json.rtData.preTimeStr;//预估时间
    	
    	var model=json.rtData.model;
    	var modelJson=tools.strToJson(model);
    	if(taskType==1){//日报
    		$("#rbTime").val(modelJson["rbTime"]);
    	}else if(taskType==2){//周报
    		$("#week").val(modelJson["week"]);
    		$("#zbTime").val(modelJson["zbTime"]);
    	}else if(taskType==3){//月报
    		$("#ybDate").val(modelJson["ybDate"]);
    		$("#ybTime").val(modelJson["ybTime"]);
    	}else if(taskType==4){//季报
    		$("#jbMonth").val(modelJson["jbMonth"]);
    		$("#jbDate").val(modelJson["jbDate"]);
    		$("#jbTime").val(modelJson["jbTime"]);
    	}else if(taskType==5){//年报
    		$("#nbMonth").val(modelJson["nbMonth"]);
    		$("#nbDate").val(modelJson["nbDate"]);
    		$("#nbTime").val(modelJson["nbTime"]);
    	}else if(taskType==6){//一次性
    		$("#ycxTime").val(modelJson["ycxTime"]);
    		ycxTimeStr=modelJson["ycxTime"];
    	}
    	changePubType();
    	changeTaskType();
    	
    }else{
    	$.MsgBox.Alert_auto("数据获取失败！");
    }
}

//改变发布类型
function changePubType(){
	var pubType=$("#pubType").val();
	if(pubType==1){//按人员发布
		$("#pubUserTr").show();
        $("#pubDeptTr").hide();
	}else{//按部门发布
		$("#pubUserTr").hide();
		$("#pubDeptTr").show();
	}
}

//改变上报类型
function changeTaskType(){
	var taskType=$("#taskType").val();
	if(taskType==1){//日报
		$("#rb").show();
		$("#zb").hide();
		$("#yb").hide();
		$("#jb").hide();
		$("#nb").hide();
		$("#oneTime").hide();
	}else if(taskType==2){//周报
		$("#rb").hide();
		$("#zb").show();
		$("#yb").hide();
		$("#jb").hide();
		$("#nb").hide();
		$("#oneTime").hide();
	}else if(taskType==3){//月报
		$("#rb").hide();
		$("#zb").hide();
		$("#yb").show();
		$("#jb").hide();
		$("#nb").hide();
		$("#oneTime").hide();
	}else if(taskType==4){//季报
		$("#rb").hide();
		$("#zb").hide();
		$("#yb").hide();
		$("#jb").show();
		$("#nb").hide();
		$("#oneTime").hide();
	}else if(taskType==5){//年报
		$("#rb").hide();
		$("#zb").hide();
		$("#yb").hide();
		$("#jb").hide();
		$("#nb").show();
		$("#oneTime").hide();
	}else if(taskType==6){//一次性
		$("#rb").hide();
		$("#zb").hide();
		$("#yb").hide();
		$("#jb").hide();
		$("#nb").hide();
		$("#oneTime").show();
	}
}

//返回
function back(){
	window.location.href=contextPath+"/system/subsys/informationReport/taskPublic/index.jsp";
}

//获取频次
function getPcModel(){
	var model=[];
	var taskType=$("#taskType").val();
	if(taskType==1){//日报
		model["rbTime"]=$("#rbTime").val();
	}else if(taskType==2){//周报
		model["week"]=$("#week").val();
		model["zbTime"]=$("#zbTime").val();
	}else if(taskType==3){//月报
		model["ybDate"]=$("#ybDate").val();
		model["ybTime"]=$("#ybTime").val();
	}else if(taskType==4){//季报
		model["jbDate"]=$("#jbDate").val();
		model["jbMonth"]=$("#jbMonth").val();
		model["jbTime"]=$("#jbTime").val();
	}else if(taskType==5){//年报
		model["nbDate"]=$("#nbDate").val();
		model["nbMonth"]=$("#nbMonth").val();
		model["nbTime"]=$("#nbTime").val();
	}else if(taskType==6){//一次性
		model["ycxTime"]=$("#ycxTime").val();
	}
	return tools.jsonObj2String(model);
}

//保存
function doSave(){
	//alert(111);
	if($("#form1").valid()){
		var param=tools.formToJson($("#form1"));
		param["model"]=getPcModel();
		
		if(sid>0){//代表编辑
			if(taskType==6 && preTimeStr==""){//一次性且预估时间为null   説明定時任务已经轮循过了  已经产生了相应的数据
				var ycxTime=$("#ycxTime").val();
				if(ycxTime==ycxTimeStr){//未修改时间
					flag=0;
					param["flag"]=flag;
					save(param);	
				}else{//修改时间
					  $.MsgBox.Confirm("提示", "该任务为一次性上报任务，并且已经生成上报数据，目前您修改了触发时间，是否重新发布该任务？", function(){
						  flag=1;
						  param["flag"]=flag;
						  save(param);
					  },function(){
						   flag=0;
						   param["flag"]=flag;
						   save(param);
					  });	
				}
			}else{
				param["flag"]=flag;
				save(param);
			}
		}else{
			param["flag"]=flag;
			save(param);
		}	
	}
}

//通用的保存
function save(param){
	var url=contextPath+"/TeeTaskTemplateController/addOrUpdate.action";
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功！",function(){
			back();
		});
	}else{
		$.MsgBox.Alert_auto("保存失败！");
	}
}
</script>
</head>
<body  onload="doInit();"  style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_bj.png">
		<span class="title">新增/编辑任务信息</span>
	</div>
   <div class="fr right">
      <input type="button" value="保存" class="btn-win-white" onclick="doSave();"/>
      <input type="button" value="返回" class="btn-del-red" onclick="back();"/>
   </div>
</div>
<form action="" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid %>"/>
  <table align="center" width="100%" class="TableBlock_page">
     <tr>
	    <td nowrap class="TableHeader" colspan="2" >
	        <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">基本信息</b>
	    </td>
	  </tr>
	  <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >任务名称：</td>
	     <td>
	        <input type="text" name="taskName" id="taskName" required />
	     </td>
	  </tr>
	  <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >任务描述：</td>
	     <td style="width: 80%">
	        <textarea rows="5" cols="60" id="taskDesc" name="taskDesc" style="width: 500px"></textarea>
	     </td>
	  </tr>
      <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >发布类型：</td>
	     <td>
	        <select name="pubType" id="pubType" onchange="changePubType();">
	           <option value="1">按人员</option>
	           <option value="2">按部门</option>
	        </select>
	     </td>
	  </tr>
	  <tr  id="pubUserTr">
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >发布范围（人员）：</td>
	     <td>
	        <input type="hidden" name="pubUserIds" id="pubUserIds"/>
	        <textarea rows="5" cols="60" name="pubUserNames" id="pubUserNames" style="width: 500px" required readonly="readonly"></textarea>
	        <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['pubUserIds','pubUserNames'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('pubUserIds','pubUserNames')" value="清空"/>
			</span>
	     </td>
	  </tr>
	   <tr style="display: none;" id="pubDeptTr">
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >发布范围（部门）：</td>
	     <td>
	        <input type="hidden" name="pubDeptIds" id="pubDeptIds"/>
	        <textarea rows="5" cols="60" id="pubDeptNames" name="pubDeptNames" style="width: 500px" required  readonly="readonly"></textarea>
	        <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['pubDeptIds','pubDeptNames'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('pubDeptIds','pubDeptNames')" value="清空"/>
			</span>
	     </td>
	  </tr>
	  <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >上报对象（人员）：</td>
	     <td>
	        <input type="hidden" name="reportUserIds" id="reportUserIds" style="width:500px"/>
	        <textarea rows="5" cols="60" id="reportUserNames" name="reportUserNames" style="width: 500px"></textarea>
	        <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['reportUserIds','reportUserNames'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('reportUserIds','reportUserNames')" value="清空"/>
			</span>
	     </td>
	  </tr>
	   <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >上报对象（部门）：</td>
	     <td>
	        <input type="hidden" name="reportDeptIds" id="reportDeptIds"/>
	        <textarea rows="5" cols="60" id="reportDeptNames" name="reportDeptNames" style="width: 500px"></textarea>
	        <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['reportDeptIds','reportDeptNames'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('reportDeptIds','reportDeptNames')" value="清空"/>
			</span>
	     </td>
	  </tr>
	  <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%">上报对象（角色）：</td>
	     <td>
	        <input type="hidden" name="reportRoleIds" id="reportRoleIds"/>
	        <textarea rows="5" cols="60" id="reportRoleNames" name="reportRoleNames"  style="width: 500px;"></textarea>
	        <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['reportRoleIds','reportRoleNames'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('reportRoleIds','reportRoleNames')" value="清空"/>
			</span>
	     </td>
	  </tr>
	  <tr>
	     <!--  1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性 -->
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >上报类型：</td>
	     <td>
	        <div id="selectDiv" style="display: none">
	            <select name="taskType" id="taskType" onchange="changeTaskType()">
		           <option value="1">日报</option>
		           <option value="2">周报</option>
		           <option value="3">月报</option>
		           <option value="4">季报</option>
		           <option value="5">年报</option>
		           <option value="6">一次性</option>
	            </select> 
	        </div>
	        <div id="inputDiv" style="display: none">
	            <span name="taskTypeDesc" id="taskTypeDesc"></span>
	        </div>
	     </td>
	  </tr>
	  <tr>
	     <td nowrap class="TableData" style="text-indent: 15px;width: 20%" >频次：</td>
	     <td>
	         <div id="rb">
	           <span>每天&nbsp;<input type="text" name="rbTime" id="rbTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 100px" class="Wdate"  required/>&nbsp;开始</span>
	         </div>
	         <div id="zb" style="display: none">
	           <span>每周&nbsp;
	           <select name="week" id="week" style="height: 25px;">
	              <option value="1">一</option>
	              <option value="2">二</option>
	              <option value="3">三</option>
	              <option value="4">四</option>
	              <option value="5">五</option>
	              <option value="6">六</option>
	              <option value="7">日</option>
	           </select>
	           &nbsp;<input type="text" name="zbTime" id="zbTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 100px" class="Wdate" required/>&nbsp;开始</span>
	         </div>
	         <div id="yb"  style="display: none">
	           <span>每月&nbsp;
	           <select name="ybDate" id="ybDate" style="height: 25px;">
	              <option value="1">1号</option>
	              <option value="2">2号</option>
	              <option value="3">3号</option>
	              <option value="4">4号</option>
	              <option value="5">5号</option>
	              <option value="6">6号</option>
	              <option value="7">7号</option>
	              <option value="8">8号</option>
	              <option value="9">9号</option>
	              <option value="10">10号</option>
	              <option value="11">11号</option>
	              <option value="12">12号</option>
	              <option value="13">13号</option>
	              <option value="14">14号</option>
	              <option value="15">15号</option>
	              <option value="16">16号</option>
	              <option value="17">17号</option>
	              <option value="18">18号</option>
	              <option value="19">19号</option>
	              <option value="20">20号</option>
	              <option value="21">21号</option>
	              <option value="22">22号</option>
	              <option value="23">23号</option>
	              <option value="24">24号</option>
	              <option value="25">25号</option>
	              <option value="26">26号</option>
	              <option value="27">27号</option>
	              <option value="28">28号</option>
	              <option value="29">29号</option>
	              <option value="30">30号</option>
	              <option value="31">31号</option>
	           </select>
	           &nbsp;<input type="text" name="ybTime" id="ybTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 100px" class="Wdate" required/>&nbsp;开始</span>
	         </div>
	          <div id="jb"  style="display: none">
	           <span>每季度第&nbsp;
	           <select name="jbMonth" id="jbMonth" style="height: 25px;">
	              <option value="1">一</option>
	              <option value="2">二</option>
	              <option value="3">三</option>
	           </select>
	           &nbsp;个月&nbsp;<select name="jbDate" id="jbDate" style="height: 25px;">
	              <option value="1">1号</option>
	              <option value="2">2号</option>
	              <option value="3">3号</option>
	              <option value="4">4号</option>
	              <option value="5">5号</option>
	              <option value="6">6号</option>
	              <option value="7">7号</option>
	              <option value="8">8号</option>
	              <option value="9">9号</option>
	              <option value="10">10号</option>
	              <option value="11">11号</option>
	              <option value="12">12号</option>
	              <option value="13">13号</option>
	              <option value="14">14号</option>
	              <option value="15">15号</option>
	              <option value="16">16号</option>
	              <option value="17">17号</option>
	              <option value="18">18号</option>
	              <option value="19">19号</option>
	              <option value="20">20号</option>
	              <option value="21">21号</option>
	              <option value="22">22号</option>
	              <option value="23">23号</option>
	              <option value="24">24号</option>
	              <option value="25">25号</option>
	              <option value="26">26号</option>
	              <option value="27">27号</option>
	              <option value="28">28号</option>
	              <option value="29">29号</option>
	              <option value="30">30号</option>
	              <option value="31">31号</option>
	           </select>
	           &nbsp;<input type="text" name="jbTime" id="jbTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 100px" class="Wdate" required/>&nbsp;开始</span>
	         </div>
	         
	         <div id="nb"  style="display: none">
	           <span>每年&nbsp;
	           <select name="nbMonth" id="nbMonth" style="height: 25px;">
	              <option value="1">一月</option>
	              <option value="2">二月</option>
	              <option value="3">三月</option>
	              <option value="4">四月</option>
	              <option value="5">五月</option>
	              <option value="6">六月</option>
	              <option value="7">七月</option>
	              <option value="8">八月</option>
	              <option value="9">九月</option>
	              <option value="10">十月</option>
	              <option value="11">十一月</option>
	              <option value="12">十二月</option>
	           </select>
	           &nbsp;<select name="nbDate" id="nbDate" style="height: 25px;">
	              <option value="1">1号</option>
	              <option value="2">2号</option>
	              <option value="3">3号</option>
	              <option value="4">4号</option>
	              <option value="5">5号</option>
	              <option value="6">6号</option>
	              <option value="7">7号</option>
	              <option value="8">8号</option>
	              <option value="9">9号</option>
	              <option value="10">10号</option>
	              <option value="11">11号</option>
	              <option value="12">12号</option>
	              <option value="13">13号</option>
	              <option value="14">14号</option>
	              <option value="15">15号</option>
	              <option value="16">16号</option>
	              <option value="17">17号</option>
	              <option value="18">18号</option>
	              <option value="19">19号</option>
	              <option value="20">20号</option>
	              <option value="21">21号</option>
	              <option value="22">22号</option>
	              <option value="23">23号</option>
	              <option value="24">24号</option>
	              <option value="25">25号</option>
	              <option value="26">26号</option>
	              <option value="27">27号</option>
	              <option value="28">28号</option>
	              <option value="29">29号</option>
	              <option value="30">30号</option>
	              <option value="31">31号</option>
	           </select>
	           &nbsp;<input type="text" name="nbTime" id="nbTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 100px" class="Wdate" required/>&nbsp;开始</span>
	         </div>
	         <div id="oneTime"   style="display: none">
	             <span><input type="text" name="ycxTime" id="ycxTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 170px" class="Wdate" required/>&nbsp;开始</span>
	         </div>
	     </td>
	  </tr>
  </table>
</form>
<script type="text/javascript">
$("#form1").validate();
</script>
</body>
</html>