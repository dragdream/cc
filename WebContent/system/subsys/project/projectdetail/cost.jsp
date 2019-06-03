<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预算及成本</title>
</head>
<script>
var uuid="<%=uuid%>";
var status=0;//项目状态
function doInit(){
	getProjectStatus();
	isManagerOrMember();
	getData();
	
}

//根据项目主键  获取项目状态
function getProjectStatus(){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		status=data.status;
	}
}

//获取数据
function getData(){
	var url=contextPath+"/projectCostController/getCostSumList.action";
	var json=tools.requestJsonRs(url,{projectId:uuid});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#tbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
					+"<td nowrap align='center' style='width:10%;text-indent:10px;'>" +data[i].typeName+ "</td>"
					+"<td nowrap align='center' style='width:30%;'>" + data[i].sum + "</td>"
					+"<td nowrap align='center' style='width:10%;'><a href=\"#\" onclick=\"detail("+data[i].typeId+")\">明细</a></td>"
                  
		  	+ "</tr>");
		}
	}
}


//明细
function detail(typeId){
	var url=contextPath+"/system/subsys/project/projectdetail/costDetail.jsp?projectId="+uuid+"&&typeId="+typeId;
	bsWindow(url ,"费用明细",{width:"600",height:"240",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		 if(v=="关闭"){
			return true;
		}
	}}); 
	
}





//判断当前登录人  是不是项目成员或者负责人
function isManagerOrMember(){
	var url=contextPath+"/projectController/isManagerOrMember.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		if(data==1&&status==3){
			$("#applyBtn").show();
		}else{
			$("#applyBtn").hide();
		}
	}
}
//预算申请
function add(){
	var url=contextPath+"/system/subsys/project/projectdetail/addCost.jsp?uuid="+uuid;
	bsWindow(url ,"预算申请",{width:"600",height:"240",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var a=cw.commit();
		    if(a){
		    	$.MsgBox.Alert_auto("提交成功！");
		    	return true;
		    }else{
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


</script>
<body onload="doInit()">
  <div id="toolbar" class="toolbar clearfix">
      <div class="left fl setHeight" style="margin-bottom: 10px">
         <input type="button" id="applyBtn" class="btn-win-white" value="预算申请"   onclick="add()" style="display: none"/> 
      </div>
   </div>
   
   <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center">
      <thead>
         <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
            <td style="width:33%;width:33.3333%;text-indent: 10px">费用类型</td>
            <td style="width:33%;width:33.3333%;">实际产生费用</td>
            <td style="width:33%;width:33.3333%;">操作</td>
         </tr>
      </thead>
      <tbody id="tbody">
         
      </tbody>
   </table>
</body>
</html>