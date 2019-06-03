<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目详情</title>
</head>
<style>
 input,textarea {
	border:none;
}
</style>
<script>
var uuid="<%=uuid%>";
//初始化方法
function doInit(){
	getInfoByUuid(uuid);
	
}

//根据主键获取详情
function getInfoByUuid(uuid){
	var url=contextPath+"/projectController/getBasicInfoBySid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		var projectTypeId=json.rtData.projectTypeId;
		renderCustomField(projectTypeId);
		//获取所有class=val  的td
		var tds=$("td[class=val]");
// 		alert(tds.length);
// 		for(var i=0;i<tds.length;i++ ){
// 			var tdId=tds[i].id;
// 			$("#"+tdId).html(data[tdId]);
// 		}
 
        if(data.approverName!=""&&data.approverName!=null){
        	$("#approveTr").show();
        }
		bindJsonObj2Cntrl(data);
		var html=data.addressDesc;
		html+="&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\"  onclick=\"map('"+data.coordinate+"','"+data.addressDesc+"')\">地图</a>";
		$("#addressDesc").html(html);
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}
}


//地图

function map(coordinate,addr){
	var url=contextPath+"/system/subsys/project/projectdetail/mapPoint.jsp?coordinate="+coordinate+"&&addr="+addr;
	bsWindow(url ,"查看地址",{width:"800",height:"350",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}}); 
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
						   +"<td  class=\"TableData\" align=\"left\" id="+name+" name="+name+" class='val'>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].fieldType=="多行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\" id="+name+" name="+name+" class='val'>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].fieldType=="下拉列表"){
				var fieldCtrModel=data[i].fieldCtrModel;
				var j=tools.strToJson(fieldCtrModel);
				if(j.codeType=="系统编码"){
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\" id="+name+" name="+name+" class='val'>"  
							   +"</td>"
							   +"</tr>");
				}else if(j.codeType=="自定义选项"){
					var values=j.value;
					var optionNames=values[0].split(",");
					var optionValues=values[1].split(",");
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].fieldName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\" name="+name+" id="+name+" class='val'>"
							   +"</td>"
							   +"</tr>");
							
				}
				
			}
			
		}
		
	}
}
</script>


<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<%-- <div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_jibenxiangqing.png">
		<span class="title">项目基本信息</span>
	</div>
</div> --%>

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
    <td  class="TableData" class="val" align="left" id="groupSelect" id="projectNum" name="projectNum">
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目类型：</td>
    <td  class="TableData" align="left" class="val" name="projectTypeName" id="projectTypeName">
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目名称：</td>
    <td  class="TableData" class="val" align="left" name="projectName" id="projectName">
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">项目周期：</td>
    <td  class="TableData" align="left" id="time" name="time" class="val">
       
    </td>
   </tr>
   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项所在区域：</td>
		<td  class="TableData" id="address" name="address" class="val">
		   
		</td>
	</tr>
   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目位置：</td>
		<td  class="TableData" name="addressDesc" id="addressDesc" class="val">
		</td>
	</tr>
	 <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目级别：</td>
		<td  class="TableData" name="projectLevel" id="projectLevel" class="val">
		</td>
	</tr>
   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目文档目录：</td>
		<td  class="TableData" id="diskNames" name="diskNames" class="val">
		 
		</td>
	</tr>
	 <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目预算：</td>
		<td  class="TableData" name="projectBudget"  id="projectBudget" class="val">
		
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目负责人：</td>
		<td  class="TableData" id="managerName" name="managerName" class="val">
		</td>
	</tr>
	 <tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目成员：</td>
		<td  class="TableData" id="projectMemberNames" name="projectMemberNames" class="val">
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
		<td  class="TableData" id="projectViewNames" name="projectViewNames" class="val">
		</td>
	</tr>
	<tr id="approveTr" style="display:none;">
		<td  class="TableData" width="150" style="text-indent:15px">项目审批人：</td>
		<td  class="TableData" name="approverName" id="approverName" class="vals">
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">项目内容：</td>
		<td  class="TableData" name="projectContent" id="projectContent" class="val">
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