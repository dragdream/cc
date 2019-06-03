<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<% 
	int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
    int contentPriv=TeeStringUtil.getInteger(request.getParameter("contentPriv"), 0);
%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>公文传阅</title>
</head>
<script>
var runId=<%=runId%>;
var contentPriv=<%=contentPriv%>;
//选择人员后  调用回掉函数
function showUserTab(itemId,itemName,itemIds,itemNames,obj,optType){
	//alert(itemId+"  "+itemName+"  "+optType);
	if(optType=="ADD_ITEM"){//增加
		var id="userTr_"+itemId;
	
        if($("#"+id).length==0){
        	$("#userTabView").append("<tr id="+id+" class='userClazz'>"+
    				"<td class='TableData' style='text-align: center;' title="+itemId+">"+itemName+"</td>"+
    				"<td class='TableData' style='text-align: center;'><input type='text' class='viewPrintNum'  /></td>"+
    				"<td class='TableData' style='text-align: center;'><input type='checkbox' class='viewDownLoad'/></td>"+
    		        "</tr>");		
        }
		
	}else if(optType=="REMOVE_ITEM"){//移除
	   $("#userTr_"+itemId).remove();
	}
}


//批量设置打印的份数
function setBatchViewPrintNum(){
	//获取批量打印数量的数值
	var batchPrintNum=$("#viewBatchPrintNum")[0].value;
	
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	if(!reg.test(batchPrintNum)){
		//alert("批量打印份数格式错误！");
		$.MsgBox.Alert_auto("批量打印份数格式错误！");
		return;	
	}
	
	if(batchPrintNum==""||batchPrintNum==null){
		batchPrintNum=0;
	}
	//获取所有部门打印分数的input标签
	var viewPrintNumArray=$(".viewPrintNum");
	for(var i=0;i<viewPrintNumArray.length;i++){
		viewPrintNumArray[i].value=batchPrintNum;
	}
}




//批量设置是否下载
function  setBatchViewDownLoad(){
	//获取所有部门是否可以下载的复选框数组
	var viewDownLoadArry=$(".viewDownLoad");
	//获取是否批量下载
	if($("#viewBatchDownLoad")[0].checked==true){
		for(var i=0;i<viewDownLoadArry.length;i++){			
			viewDownLoadArry[i].checked=true;
		}	
	}else{
		for(var i=0;i<viewDownLoadArry.length;i++){			
			viewDownLoadArry[i].checked=false;
		}		
	}
	
}


//验证输入的值是非负整数
function checkViewNum(obj){
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	//获取所有部门打印分数的input标签
	var viewPrintNumArray=$(".viewPrintNum");
	for(var i=0;i<viewPrintNumArray.length;i++){
		if(!reg.test(viewPrintNumArray[i].value)){
			//alert("打印份数格式错误！");
			return false;
		}else{
			return true;
		}
	}
}

//传阅公文
function doViewDoc(){
	if($("#sendUserIds").val()==""){
		//alert("请选择人员");
		$.MsgBox.Alert_auto("请选择人员！");
		return;
	}
	
	
	 if(!checkViewNum()){
		//alert("打印份数格式错误！");
		$.MsgBox.Alert_auto("打印份数格式错误！");
		return;
	}
	
	
	
	//是否消息提醒
	var isReadRemind="";
	if($("#isReadRemind").prop("checked")==true){
		isReadRemind="1";	
	}else{
		isReadRemind="0";
	}
	
	//是否进行短信提醒
	var isPhoneRemind="";
	if($("#isPhoneRemind1").prop("checked")==true){
		isPhoneRemind="1";	
	}else{
		isPhoneRemind="0";
	}
	//公文传阅的 时候拼接部门打印份数和是否允许下载的json字符串
	var userArray=$(".userClazz");
	var str="[";
	var userId=0;
	var printNum=0;
	var isDownLoad=0;
	for(var i=0;i<userArray.length;i++){
		userId=userArray[i].children[0].title;
		if(userArray[i].children[1].children[0].value!=null&&userArray[i].children[1].children[0].value!=""){
			printNum=userArray[i].children[1].children[0].value;
		}else{
			printNum=0;
		}
		
		if(userArray[i].children[2].children[0].checked==true){
			isDownLoad=1;	
		}else{
			isDownLoad=0;	
		} 
		if(i==userArray.length-1){
			str+="{userId:"+userId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"}";	
		}else{
			str+="{userId:"+userId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"},";
		}
		
	}
	   str+="]";
	
    //获取内容权限
    var content=$(".viewContent");
    var priv=0;
    for(var i=0;i<content.length;i++){
    	if($(content[i]).prop("checked")==true){
    		priv=priv+parseInt($(content[i]).val());
    	}	
    }
    if(priv==0){
    	$.MsgBox.Alert_auto("请至少选择一项传阅内容！");
    	return;
    }
    
	var json = tools.requestJsonRs(contextPath+"/doc/viewDoc.action",{runId:runId,sendUserIds:$("#sendUserIds").val(),isReadRemind:isReadRemind,jsonStr:str,isPhoneRemind:isPhoneRemind,contentPriv:priv});
	return json.rtState; 
	

}

//点击清空  清空公文部门传阅显示列表
function clearUsersTab(){
	$("#userTabView").children().remove();
	
}


//初始化
function doInit(){
	if((contentPriv&16)==16){
		$("#qpd").show();
	}
	if((contentPriv&1)==1){
		$("#bd").show();
	}
	if((contentPriv&2)==2){
		$("#zw").show();
	}
	if((contentPriv&4)==4){
		$("#bszw").show();
	}
	if((contentPriv&8)==8){
		$("#fj").show();
	}
}
</script>
<body style="background-color: #f2f2f2" onload="doInit()">
   <form id="form1" name="form1">
      <table class="TableBlock" width="100%">
         <tr>
            <td width="15%" style="text-indent: 10px">
                                      传阅内容：
            </td>
            <td>	
               <span id="bd" style="display: none"><input type="checkbox"  class="viewContent" value="1" />表单 </span>&nbsp;&nbsp;&nbsp;
                <span id="qpd" style="display: none"><input type="checkbox"  class="viewContent" value="16" />签批单</span>&nbsp;&nbsp;&nbsp;
               <span id="zw" style="display: none"><input type="checkbox"  class="viewContent"  value="2"/>正文</span> &nbsp;&nbsp;&nbsp;
               <span id="bszw" style="display: none"><input type="checkbox"  class="viewContent" value="4"/>版式正文</span>&nbsp;&nbsp;&nbsp;
               <span id="fj" style="display: none"><input type="checkbox"  class="viewContent" value="8"/>附件</span>
		    </td>
         </tr>
         <tr>
            <td width="15%" style="text-indent: 10px">
                                      传阅人员：
            </td>
            <td>	
               <textarea class="BigTextarea readonly" id="sendUserNames" readonly style="width:400px;height:100px"></textarea>
			   <input type="hidden" id="sendUserIds" />
			   <a href="javascript:void(0)" onclick="selectUser(['sendUserIds','sendUserNames'],undefined,undefined,undefined,'showUserTab')">选择</a>
			   &nbsp;
			   <a href="javascript:void(0)" onclick="clearData('sendUserIds','sendUserNames');clearUsersTab();">清空</a>
		    </td>
         </tr>
         <tr>
            <td colspan="2" style="text-indent:10px">
              <input type="checkbox"  id="isReadRemind" name="isReadRemind"/>&nbsp;&nbsp;签阅是否进行消息提醒
            	&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox"  id="isPhoneRemind1" name="isPhoneRemind1"/>&nbsp;&nbsp;是否进行短信提醒
		    </td>
         </tr>
         <tr>
         <td colspan="2">
	        <table  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'>
				<thead>
				 <tr class='TableHeader' style='background-color:#e8ecf9' >
				    <td width='33%' style="text-indent:10px;text-align: center">人员名称</td>
					<td width='34%' style="text-align: center">打印份数&nbsp;&nbsp;<input type="text" id="viewBatchPrintNum" style="width: 25px" onblur="checkBatchPrintNum(this);"/>
			          &nbsp;&nbsp;<button type="button" class="btn btn-default" style="width: 50px" onclick="setBatchViewPrintNum();"/>批量</button></td>
					<td width='33%' style="text-align: center">是否允许下载&nbsp;&nbsp;<input type="checkBox" id="viewBatchDownLoad" onclick="setBatchViewDownLoad();"/></td>
					
		  		 </tr>
		  		</thead>
		  		<tbody id='userTabView'></tbody>
	        </table>
	        <div id="message" style="margin-top: 10px;margin-bottom: 10px"></div>
	     </td>
         
         </tr>
      
      </table>
   </form>
</body>
</html>