<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="">
<head>
<%
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"),"");
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
<title>项目变更</title>
<script >
var uuid="<%=uuid%>";

function doInit(){
	initProjectType();
	if(isNoApprove()==0){//不是免审批人员
		//$("#approveTr").show();
		initApprover();
	} 

	initAddressCtr();
	var projectTypeId=$("#projectTypeId").val();
	renderCustomField(projectTypeId);
	
	if(uuid!=""){//编辑
		getInfoByUuid(uuid);
	}
	
	
	
	
	
}
//根据主键获取详情
function getInfoByUuid(uuid){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var projectTypeId=json.rtData.projectTypeId;
		renderCustomField(projectTypeId);
		bindJsonObj2Cntrl(json.rtData);
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
}


//判断当前登录的用户是不是可以免审批
function isNoApprove(){	
	var url=contextPath+"/projectApprovalRuleController/isNoApprove.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		return data;
	}else{
		return 0;
	}
	
}

//初始化级联地址控件
function initAddressCtr(){
	addressInit("province","city","district");
}
//初始化项目类型
function initProjectType(){
	var url=contextPath+"/projectTypeController/getTypeList.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#projectTypeId").append("<option value="+data[i].sid+" >"+data[i].typeName+"</option>");
		}
	}
}
//动态渲染自定义字段
function renderCustomField(projectTypeId){
	$("#customTbody").html("");
	if(projectTypeId==0){
		projectTypeId=$("#projectTypeId").val();
	}
	var url=contextPath+"/projectCustomFieldController/getListByProjectType.action";
	var json=tools.requestJsonRs(url,{projectTypeId:projectTypeId});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			var name="FIELD_"+data[i].sid;
			if(data[i].fieldType=="单行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\">"
						   +    "<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" />"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].fieldType=="多行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\">"
						   +    "<textarea  type=\"text\" rows=\"6\" name='"+name+"' id='"+name+"' style=\"width: 550px\" ></textarea>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].fieldType=="下拉列表"){
				var fieldCtrModel=data[i].fieldCtrModel;
				var j=tools.strToJson(fieldCtrModel);
				if(j.codeType=="系统编码"){
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\">"
							   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
							   +"</td>"
							   +"</tr>");
					getSysCodeByParentCodeNo(j.value,name);
				}else if(j.codeType=="自定义选项"){
					var values=j.value;
					var optionNames=values[0].split(",");
					var optionValues=values[1].split(",");
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\">"
							   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
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
//初始化项目审批人
function initApprover(){
	var url=contextPath+"/projectApprovalRuleController/getApproverByLoginUser.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#approverId").append("<option value="+data[i].uuid+" >"+data[i].userName+"</option>");
		}
	}
}
//返回
function back(){
	history.go(-1);
}

//地图选点
function selectMapPoint(){
	var province=$("#province").val();
	var district=$("#district").val();
	var city=$("#city").val();
	var addr=province+city+district;
	var url=contextPath+"/system/subsys/project/myproject/map.jsp?addr="+addr;
	openFullWindow(url);
}

//选择公共文件夹目录
function selectFileNetDisk(){
	var url=contextPath+"/system/subsys/project/myproject/fileFolder.jsp";
	bsWindow(url ,"选择公共文件柜目录",{width:"600",height:"200",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		   cw.commit();
		   return true;
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//验证
function check(){
	var projectNum=$("#projectNum").val();
	var projectName=$("#projectName").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var managerId=$("#managerId").val();
	var approverId=$("#approverId").val();
	
	if(projectNum==""||projectNum==null){
		$.MsgBox.Alert_auto("请填写项目编号！");
		return false;	
	}
	if(projectName==""||projectName==null){
		$.MsgBox.Alert_auto("请填写项目名称！");
		return false;	
	}
	if(startTime==""||startTime==null){
		$.MsgBox.Alert_auto("请填写项目开始时间！");
		return false;	
	}
	if(endTime==""||endTime==null){
		$.MsgBox.Alert_auto("请填写项目结束时间！");
		return false;	
	}
	if(managerId==""||managerId==null){
		$.MsgBox.Alert_auto("请选择项目负责人！");
		return false;	
	}
	if(isNoApprove()==0){//需要审批
		if(approverId==""||approverId==null){
			$.MsgBox.Alert_auto("请选择项目审批人！");
			return false;	
		}
		
	}
	
	return true;
}

//判断项目编号是否已经存在
function checkPNumIsExist(){
	var projectNum=$("#projectNum").val();
	var url=contextPath+"/projectController/isExistPNum.action";
	var json=tools.requestJsonRs(url,{projectNum:projectNum,uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		if(data==1){
			$.MsgBox.Alert_auto("项目编号已存在，请重新填写！");
			return false;
		}else{
			return true;
		}
	}
}



//保存/提交
function save(status){
	if(check()&&checkPNumIsExist()){
		var url=contextPath+"/projectController/projectChange.action";
		var param=tools.formToJson("#form1");
		param['status']=status;
		var json=tools.requestJsonRs(url,param);
		if(json.rtState){
			if(isNoApprove()==0){//不是免审批人
				opener.datagrid.datagrid("reload");
				window.close();
			}else{//是免审批   提交--项目详情页面
				opener.datagrid.datagrid("reload");
				history.go(-1);
			}
			
		}	
	}
	
}


</script>
</head>


<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_xiangmubiangeng.png">
		<span class="title">变更项目</span>
	</div>
   <div class="fr right">
     <!--  <input type="button" value="保存" class="btn-win-white" onclick="save(1);"/> -->
      <input type="button" value="提交" class="btn-win-white" onclick="save(2);"/>
      <input type="button" value="返回" class="btn-win-white" onclick="back();"/>
   </div>
</div>

<form  method="post" name="form1" id="form1" >
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>"/>
<table class="TableBlock_page" width="60%" align="center">
	<tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">固定字段</B></TD>
	</tr>
	<tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目编号：</td>
    <td  class="TableData" align="left" id="groupSelect">
       <input type="text" name="projectNum" id="projectNum" style="height: 23px;width: 350px" />
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目类型：</td>
    <td  class="TableData" align="left">
        <select id="projectTypeId" onchange="renderCustomField(0)" name="projectTypeId" style="height: 23px;width: 150px">
        
        </select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目名称：</td>
    <td  class="TableData" align="left">
        <input type='text' name="projectName" id="projectName" style="height: 23px;width: 350px"/>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目周期：</td>
    <td  class="TableData" align="left">
       <input type="text" id='startTime' name='startTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput" style="width: 160px" />
				&nbsp;至&nbsp;
	   <input type="text" id='endTime' name='endTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" class="Wdate BigInput" style="width: 160px" />
    </td>
   </tr>
   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项所在区域：</td>
		<td  class="TableData">
		   <select id="province" name="province" style="height: 23px;width: 110px"></select>&nbsp;&nbsp;
		   <select id="city" name="city" style="height: 23px;width: 110px"></select>&nbsp;&nbsp;
		   <select id="district" name="district" style="height: 23px;width: 110px"></select>
		</td>
	</tr>
   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目位置：</td>
		<td  class="TableData">
		   <input type="text" name="addressDesc" id="addressDesc" style="height: 23px;width: 350px"  />
		   &nbsp;&nbsp;<a href="#" onclick="selectMapPoint()">地图选点</a>
		   <input type="hidden" name="coordinate" id="coordinate" />
		</td>
	</tr>
	 <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目级别：</td>
		<td  class="TableData">
		    <select name="projectLevel" id="projectLevel" style="height: 23px;width: 150px">
		       <option value="A">A</option>
		       <option value="B">B</option>
		       <option value="C">C</option>
		       <option value="D">D</option>
		    </select>
		</td>
	</tr>
   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目文档目录：</td>
		<td  class="TableData">
		   <input type="hidden" name="diskIds" id="diskIds"/>
		   <textarea rows="4" cols="" style="width: 350px" id="diskNames" class="BigTextarea" readonly="readonly"></textarea>
		   &nbsp;&nbsp;<a href="#" onclick="selectFileNetDisk()">选择公共文件柜位置</a>
		</td>
	</tr>
	 <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目预算：</td>
		<td  class="TableData">
		  <input type="text" name="projectBudget"  id="projectBudget" style="height:23px;width: 350px"/>
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目负责人：</td>
		<td  class="TableData">
		   <input name="managerId" id="managerId" type="hidden"/>
			<input class="BigInput readonly" type="text" id="managerName" name="managerName" style="height:23px;width:350px"  readonly/>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleUser(['managerId','managerName'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('managerId','managerName')" value="清空"/>
			</span>
		</td>
	</tr>
	 <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目成员：</td>
		<td  class="TableData">
		    <input name="projectMemberIds" id="projectMemberIds" type="hidden"/>
			<textarea class="BigTextarea readonly" id="projectMemberNames" name="projectMemberNames" style="height:45px;width:550px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['projectMemberIds','projectMemberNames'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('projectMemberIds','projectMemberNames')" value="清空"/>
			</span>
		</td>
	</tr>
	<tr style="display: none">
		<td  class="TableData" width="150" style="text-indent:15px">共享人员：</td>
		<td  class="TableData">
		    <input name="projectShareIds" id="projectShareIds" type="hidden"/>
			<textarea class="BigTextarea readonly" id="projectShareNames" name="projectShareNames" style="height:45px;width:550px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['projectShareIds','projectShareNames'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('projectShareIds','projectShareNames')" value="清空"/>
			</span>
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目观察者：</td>
		<td  class="TableData">
		    <input name="projectViewIds" id="projectViewIds" type="hidden"/>
			<textarea class="BigTextarea readonly" id="projectViewNames" name="projectViewNames" style="height:45px;width:550px"  readonly></textarea>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['projectViewIds','projectViewNames'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('projectViewIds','projectViewNames')" value="清空"/>
			</span>
		</td>
	</tr>
	 <tr id="approveTr" style="display: none">
		<td  class="TableData" width="150" style="text-indent:15px">项目审批人：</td>
		<td  class="TableData">
		   <select id="approverId" name="approverId" style="width: 150px;height: 23px">
		   
		   </select>
		</td>
	</tr> 
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目内容：</td>
		<td  class="TableData">
		   <textarea rows="6" style="width: 550px" name="projectContent" id="projectContent"></textarea>
		</td>
	</tr>
</table>

<table class="TableBlock_page" width="60%" align="center" id="customTable">
  <thead>
     <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">自定义字段</B></TD>
     </tr>
  </thead>
  <tbody id="customTbody" >
     
  </tbody>
</table>
  </form>
</body>
</html>