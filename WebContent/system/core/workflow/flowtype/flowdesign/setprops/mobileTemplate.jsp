<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="/header/header.jsp" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>移动表单模板</title>
</head>
<script type="text/javascript">
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;
function doInit(){
	//动态显示表单的字段
	getFormItems();
	
	//初始化手机端字段显示控制模型
	//根据prcsId获取TeeFlowProcess对象
	var url = contextPath+"/flowProcess/getFlowProcess.action";
	var json = tools.requestJsonRs(url,{prcsId:prcsId});
	var filedModel=tools.string2JsonObj(json.rtData[0]);
	 var checkBox=$(".item");
	 if(filedModel!=null&&filedModel.length>0){
		 for(var i=0;i<checkBox.length;i++){
		     for(var j=0;j<filedModel.length;j++){
		    	 if(checkBox[i].value==filedModel[j].itemId){
		    		 checkBox[i].checked=filedModel[j].isShow;
		    	 }	 
		     }	
		 }
	 }
	 
    //初始化  是否开启 
    var  isOpenMobileCtr=json.rtData[1];
    if(isOpenMobileCtr==1){
    	$("#isOpenMobileCtr")[0].checked=true;
    }else{
    	$("#isOpenMobileCtr")[0].checked=false;
    }
    
    //初始化流程信息控制模型
    var workFlowCtrModel=tools.string2JsonObj(json.rtData[2]);
    if(workFlowCtrModel!=null){
    	$("#workName")[0].checked=workFlowCtrModel[0]["workName"];
        $("#fromUser")[0].checked=workFlowCtrModel[0]["fromUser"];
        $("#beginTime")[0].checked=workFlowCtrModel[0]["beginTime"];
        $("#currStep")[0].checked=workFlowCtrModel[0]["currStep"];
        $("#ysbd")[0].checked=workFlowCtrModel[0]["ysbd"];
    }
    
    
}

function getFormItems(){
	//根据流程的主键获取表单的所有的字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	var render = [];
	render.push("<table align='center' border='0' cellpadding='0' cellspacing='0'><tr>");
	for (var i = 0; i < items.length; i++) {
		var itemId=items[i].itemId;
		var itemName=items[i].title;
		render.push("<td style='border:0px'><input type='checkbox' value="+itemId+" class='item' />"+itemName+"</td>");
	    if((i+1)%3==0){
	    	render.push("</tr><tr>");
	    }
	}
	render.push("</table>");
	$("#formItems").append(render.join(""));
	
	
}
//点击全选按钮  设置全选和非全选
function setIsAll(){
	//先判断设置全选的复选框是否选中
	var isAll=$("#isAll").is(':checked');
	var inputs =$(".item"); 

	if(isAll==true){
		for(var i=0;i<inputs.length;i++) { 
			 if(inputs[i].getAttribute('type')=='checkbox') { 
			    inputs[i].checked = true; 
			 } 
		} 	
	}else{
		for(var i=0;i<inputs.length;i++) { 
			 if(inputs[i].getAttribute('type')=='checkbox') { 
			    inputs[i].checked = false; 
			 } 
		}
	}
		
}


//保存
function commit(){
	//获取是否开启移动控制模板
	var isOpenMobileCtrol=$("#isOpenMobileCtr");
	var isOpenMobileCtr="0";
	if(isOpenMobileCtrol[0].checked==true){//开启
		isOpenMobileCtr="1";	
	}else{//不开启
		isOpenMobileCtr="0";	
	}
	
	
	//拼接字段显示模型
    var mobileFieldContrlModel="[";
    //获取所有的字段复选框    
    var checkBox=$(".item");
    for(var i=0;i<checkBox.length;i++){
       if(checkBox[i].checked==true){
    	   mobileFieldContrlModel+="{itemId:"+checkBox[i].value+",isShow:"+true+"},";
       }else{
    	   mobileFieldContrlModel+="{itemId:"+checkBox[i].value+",isShow:"+false+"},";   
       }	
    }
    if(mobileFieldContrlModel.charAt(mobileFieldContrlModel.length-1)==","){
    	mobileFieldContrlModel=mobileFieldContrlModel.substring(0, mobileFieldContrlModel.length-1);   	
    }
    
    mobileFieldContrlModel+="]";
	
	//获取流程信息控制模型
	
	var workName=$("#workName")[0];
	var fromUser=$("#fromUser")[0];
	var beginTime=$("#beginTime")[0];
	var currStep=$("#currStep")[0];
	var ysbd=$("#ysbd")[0];
	var workFlowCtrlModel="[{workName:"+workName.checked+",fromUser:"+fromUser.checked+",beginTime:"+beginTime.checked+",currStep:"+currStep.checked+",ysbd:"+ysbd.checked+"}]";
	
	
	var url = contextPath+"/flowProcess/updateMobileFieldCtrlModel.action";
	var json = tools.requestJsonRs(url,{prcsId:prcsId,mobileFieldContrlModel:mobileFieldContrlModel,isOpenMobileCtr:isOpenMobileCtr,workFlowCtrlModel:workFlowCtrlModel});

	/* if(json.rtState){
		alert("保存成功！");
	} */ 

}
</script>
<body onload="doInit();">
   <table class="TableBlock" align="center">
      <tr class="TableData">
         <td width="200px" style="text-align:center">是否开启移动表单模板</td>
         <td width="400px" style="text-align:center"><input type="checkbox" id="isOpenMobileCtr" />开启</td>
      </tr>
      <tr class="TableData">
         <td width="200px" style="text-align:center">表单字段是否全选设置</td>
         <td width="400px" style="text-align:center"><input type="checkbox" id="isAll" onclick="setIsAll();"/>全选</td>
      </tr>
      <tr class="TableData">
         <td width="200px" style="text-align:center">表单字段</td>
         <td width="400px" style="text-align:center" id="formItems"></td>
      </tr>
   
   <tr class="TableData">
         <td width="200px" style="text-align:center">流程信息控制模型</td>
         <td width="400px" style="text-align:center">
            <input type="checkbox" name="workName" id="workName" />工作名称&nbsp;&nbsp;
            <input type="checkbox" name="fromUser" id="fromUser" />流程发起人&nbsp;&nbsp;
            <input type="checkbox" name="beginTime" id="beginTime" />开始时间&nbsp;&nbsp;
            <input type="checkbox" name="currStep" id="currStep" />当前步骤&nbsp;&nbsp;
            <input type="checkbox" name="ysbd" id="ysbd" />原始表单
         
         </td>
      </tr>
   </table>
</body>
</html>