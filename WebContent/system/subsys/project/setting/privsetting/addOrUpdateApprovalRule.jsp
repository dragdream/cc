<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审批规则设置</title>

<script>
var sid=<%=sid%>;
//初始化
function doInit(){
	if(sid>0){
		var url=contextPath+"/projectApprovalRuleController/getApprovalRuleBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			var data=json.rtData;
		    bindJsonObj2Cntrl(data);
		}
	}
}


//保存
function  commit(){
	if(check()){
		var para =  tools.formToJson($("#form1")) ;
		var url = contextPath + "/projectApprovalRuleController/addOrUpdateApprovalRule.action";
		var json=tools.requestJsonRs(url,para);
		if(json.rtState){
		    parent.window.location.reload();
		    return true;
		}else{
			$.MsgBox.Alert_auto("保存失败！");
		}	
	}

}

//验证
function check(){
	var approverIds=$("#approverIds").val();
	var manageDeptIds=$("#manageDeptIds").val();
	if(approverIds==""){
		$.MsgBox.Alert_auto("请选择审批人员！");
		return false;
	}
	if(manageDeptIds==""){
		$.MsgBox.Alert_auto("请选择管辖部门！");
		return false;
	}
	return true;
}
</script>



<body onload="doInit()" style="background-color: #f2f2f2;overflow: hidden">
 <form id="form1">
  <div style="margin-top: 15px">
  <input type="hidden" name="sid" id="sid" value="<%=sid%>"/>
      <table class="TableBlock" style="width:100%">
        <tr>
           <td style="text-indent: 15px;width: 15%">审批人员：</td>
           <td style="width: 85%">
              <input name="approverIds" id="approverIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="approverNames" name="approverNames" style="height:60px;width:80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['approverIds','approverNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('approverIds','approverNames')" value="清空"/>
			  </span>
           </td> 
        </tr> 
        <tr>
           <td style="text-indent: 15px;width: 15%" >管辖部门：</td>
           <td style="width: 85%">
              <input name="manageDeptIds" id="manageDeptIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="manageDeptNames" name="manageDeptNames" style="height:60px;width:80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectDept(['manageDeptIds','manageDeptNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('manageDeptIds','manageDeptNames')" value="清空"/>
			  </span>
           </td> 
        </tr>
            
     </table>
  </div>
</body>
</html>