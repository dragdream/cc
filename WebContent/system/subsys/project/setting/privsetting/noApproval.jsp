<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>免审批规则设置</title>

<script>
//初始化
function doInit(){
	var url=contextPath+"/projectApprovalRuleController/getNoApprovalRule.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
	    bindJsonObj2Cntrl(data);
	}
}


//保存
function  commit(){
	var para =  tools.formToJson($("#form1")) ;
	var url = contextPath + "/projectApprovalRuleController/setNoApprovalRule.action";
	var json=tools.requestJsonRs(url,para);
	if(json.rtState){
	    parent.window.location.reload();
	    return true;
	}else{
		$.MsgBox.Alert_auto("保存失败！");
	}
	
	
}


</script>



<body onload="doInit()" style="background-color: #f2f2f2;overflow: hidden">
 <form id="form1">
  <div style="margin-top: 15px">
      <table class="TableBlock" style="width:100%">
        <tr>
           <td style="text-indent: 15px;width: 15%" >部门范围：</td>
           <td style="width: 85%">
              <input name="deptIds" id="deptIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="deptNames" name="deptNames" style="height:60px;width:80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('deptIds','deptNames')" value="清空"/>
			  </span>
           </td> 
        </tr>
        <tr>
           <td style="text-indent: 15px;width: 15%">角色范围：</td>
           <td style="width: 85%">
              <input name="roleIds" id="roleIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="roleNames" name="roleNames" style="height:60px;width:80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectRole(['roleIds','roleNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('roleIds','roleNames')" value="清空"/>
			  </span>
           </td> 
        </tr>
        <tr>
           <td style="text-indent: 15px;width: 15%">人员范围：</td>
           <td style="width: 85%">
              <input name="userIds" id="userIds" type="hidden"/>
			  <textarea class="BigTextarea readonly" id="userNames" name="userNames" style="height:60px;width:80%"  readonly></textarea>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
			  </span>
           </td> 
        </tr>     
     </table>
  </div>
</body>
</html>