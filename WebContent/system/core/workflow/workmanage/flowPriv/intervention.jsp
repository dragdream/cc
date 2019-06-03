<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
  int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
  int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
  int currFrpSid=TeeStringUtil.getInteger(request.getParameter("currFrpSid"),0);
 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>流程干预</title>
<script type="text/javascript">
var runId=<%=runId %>;
var flowId=<%=flowId %>;
var currFrpSid=<%=currFrpSid %>;
//初始化
function doInit(){
	renderFlowProcess();
	changeDiv();
}

//渲染流程节点步骤
function renderFlowProcess(){
	var render=[];
	var url=contextPath+"/flowProcess/getProcessList.action?flowId="+flowId;
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].prcsType!=2 && data[i].prcsType!=4){//除去结束节点 和  并发节点
					 render.push("<option value="+data[i].sid+">"+data[i].prcsName+"</option>");
				}
			}
			$("#toFpSid").append(render.join(""));
		}
	}
	
}

//改变节点    则改点选人规则
function changeDiv(){
	//获取当前选择的流程节点id
	var toFpSid = $("#toFpSid").val();
	var url=contextPath+"/flowProcess/getProcessInfo.action";
	var json=tools.requestJsonRs(url,{prcsSeqId:toFpSid});
	if(json.rtState){//主办人选项 1：明确主办人 2：无主办会签 3：先接收为主办人
		var opFlag=json.rtData.opFlag;
	    if(opFlag==1){
	    	$("#singleSelectDiv").show();
	    	$("#multiSelectDiv").hide();
	    }else{
	    	$("#singleSelectDiv").hide();
	    	$("#multiSelectDiv").show();
	    }
	} 
}


function commit(){
	if($("#form1").valid()){
		var url=contextPath+"/flowRun/intervention.action";
		var param=tools.formToJson($("#form1"));
		var json=tools.requestJsonRs(url,param);
		return json;
	}
}
</script>

</head>
<body  onload="doInit();" style="background-color: #f2f2f2">
  <form id="form1">
     <input type="hidden" name="runId" id="runId" value="<%=runId %>" />
     <input type="hidden" name="flowId" id="flowId" value="<%=flowId %>" />
     <input type="hidden" name="currFrpSid" id="currFrpSid" value="<%=currFrpSid %>" />
     <table class="TableBlock" width="100%">
        <tr>
           <td nowrap="nowrap" width="15%" style="text-indent: 10px">流转至节点：</td>
           <td>
              <select id="toFpSid" name="toFpSid" onchange="changeDiv();">
                  
              </select>
           </td>
        </tr>
        <tr>
           <td nowrap="nowrap" style="text-indent: 10px">节点办理人：</td>
           <td>
              <div id="singleSelectDiv" style="width: 100%;display: none;" >
                  <input type="hidden" name="userId" id="userId" />
                 <input type="text" name="userName" id="userName" style="height: 23px;width:250px" readonly="readonly" required/>
                 <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['userId','userName'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userId','userName')" value="清空"/>
				 </span>
              </div>
              <div id="multiSelectDiv" style="width: 100%;display: none;">
                  <input type="hidden" name="userIds" id="userIds" />
                  <textarea style="width: 380px" rows="6" name="userNames" id="userNames" readonly="readonly" required></textarea>
                  <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
				  </span>
              </div>
           </td>
        </tr>
     </table>
     
    
  </form>
</body>
<script type="text/javascript">
  $("#form1").validate();
</script>
</html>