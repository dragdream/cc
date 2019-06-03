<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>费用审批权限</title>
</head>
<script>
//初始化
function doInit(){
	var url=contextPath+"/costApprovalController/getCostApprovalRule.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
	    bindJsonObj2Cntrl(data);
	}
}


//保存
function  commit(){
	var para =  tools.formToJson($("#form1")) ;
	var url = contextPath + "/costApprovalController/setCostApprovalRule.action";
	var json=tools.requestJsonRs(url,para);
	if(json.rtState){
	    window.location.reload();
	    return true;
	}else{
		$.MsgBox.Alert_auto("保存失败！");
	}
	
	
}
</script>

<body onload="doInit()">
   <form id="form1">
      <div style="margin-top: 15px">
      <table class="TableBlock_page">
          <tr>
             <td style="text-indent: 15px;width: 15%">授权范围（人员）：</td>
             <td style="width: 85%">
                <input name="userIds" id="userIds" type="hidden"/>
			    <textarea class="BigTextarea readonly" id="userNames" name="userNames" style="height:120px;width:55%"  readonly></textarea>
			    <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
			    </span>
             </td> 
          </tr>
      </table>
      </div>
      
      <div align="center" style="margin-top: 15px;width:70%">
         <input type="button" class="btn-win-white" value="确定" onclick="commit();"/> 
      </div>
   </form>
</body>
</html>