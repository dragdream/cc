<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审批权限</title>
<script>
//初始化
function doInit(){
	//获取免审批规则的值
	getNoApprovalRule();
	//获取审批规则数据
	getApprovalRule();
}
//获取审批规则数据列表
function getApprovalRule(){
	var url=contextPath+"/projectApprovalRuleController/getApprovalRuleList.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#approvtbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
					+"<td nowrap align='center' style='width:10%;text-indent:10px;'>" +data[i].approverNames+ "</td>"
					+"<td nowrap align='center' style='width:30%;'>" + data[i].manageDeptNames + "</td>"
					+"<td nowrap align='center' style='width:10%;'><a href='#' onclick='addOrUpdateApprovalRule("+data[i].sid+");'>编辑</a>&nbsp;&nbsp;<a href='#' onclick='deleteBySid("+data[i].sid+")'>删除</a></td>"
                  
		  	+ "</tr>"); 	
		}
		 
	}
	
}
//删除
function deleteBySid(sid){	
	 $.MsgBox.Confirm ("提示", "是否确认删除该审批规则?", function(){
		 var url=contextPath+"/projectApprovalRuleController/deleteBySid.action";
			var json=tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				window.location.reload();
			}
	  });
}


//添加或者修改审批规则
function addOrUpdateApprovalRule(sid){
	var url=contextPath+"/system/subsys/project/setting/privsetting/addOrUpdateApprovalRule.jsp?sid="+sid;
	bsWindow(url ,"设置审批规则",{width:"600",height:"200",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.commit();
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}

//获取免审批规则的值
function getNoApprovalRule(){
	var url=contextPath+"/projectApprovalRuleController/getNoApprovalRule.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		 $("#noapprovtbody").append("<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
					+"<td nowrap align='center' style='width:10%;text-indent:10px;'>" +json.rtData.deptNames+ "</td>"
					+"<td nowrap align='center' style='width:30%;'>" + json.rtData.roleNames + "</td>"
					+"<td nowrap align='center' style='width:10%;'>" + json.rtData.userNames + "</td>"
                  
		  	+ "</tr>"); 
	}

}

//设置免审批规则
function setNoApproval(){
	var url=contextPath+"/system/subsys/project/setting/privsetting/noApproval.jsp";
	bsWindow(url ,"设置免审批规则",{width:"600",height:"260",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.commit();
		}else if(v=="关闭"){
			return true;
		}
	}});
}

</script>

<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div class="clearfix" style="padding-top: 5px;">
	<b style="font-size: 14px">审批规则</b>
	<input   type="button" class="btn-win-white fr" value="设置" onclick="addOrUpdateApprovalRule(0)"/>
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
     <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center" >
      <tbody id="approvtbody">
        <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     	    <td style="text-indent:10px;width:33%;width:33.3333%;">审批人员</td>
     	    <td style="width:33%;width:33.3333%;">管辖部门</td>
      		<td style="width:33%;width:33.3333%;">操作</td>
       </tr>
      </tbody>
   </table>
   </div>
</div>
<br />
<br />
<div class="clearfix" style="padding-top: 5px;">
	<b style="font-size: 14px">免审批规则</b>
	<input  type="button" class="btn-win-white fr" value="设置"  onclick="setNoApproval();"  />
	<span class="basic_border" style="padding-top: 5px;"></span>
	<div style="padding-top: 10px;">
     <table style="border-collapse: collapse;border: 1px solid #f2f2f2;" width="100%" align="center" >
      <tbody id="noapprovtbody">
        <tr style="line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     	    <td style="text-indent:10px;width:33%;width:33.3333%;">部门范围</td>
     	    <td style="width:33%;width:33.3333%;">角色范围</td>
      		<td style="width:33%;width:33.3333%;">人员范围</td>
       </tr>
       
      </tbody>
   </table>
   </div>
</div>
</body>
</html>