<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%
	int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);//groupId
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title >考核指标集明细 </title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script>
var groupId = <%=groupId%>;
function doInit(){
	var url =   "<%=contextPath%>/TeeExamineItemManage/getAllByGroupId.action";
	var para = {groupId:groupId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		$("#tbody").empty();
		var prcs = jsonObj.rtData;
		$(prcs).each(function(i,item){
			var tempStr = '<tr class="TableData">'
			   + '<td width="140">'
			    +	'<textarea rows="2" cols="40" id="itemName_' +  item.sid +'" class="BigTextarea easyui-validatebox" required="true"  maxlength="200">' + item.itemName + '</textarea>'
			   +  '</td>'
			   + '<td align="left">'
			    	+ '<input id="itemMin_' +  item.sid +'" maxlength="8" size="5" class="BigInput easyui-validatebox" required="true" value="' + item.itemMin + '"/> ~'
			    	+ '<input id="itemMax_' +  item.sid +'" maxlength="8" size="5" class="BigInput easyui-validatebox" required="true" value="' + item.itemMax + '"/> '
			   + ' </td>'
			   + '  <td align="left"> '
			     	+ '<textarea rows="2" cols="60" id="itemDesc_' +  item.sid +'" class="BigTextarea">' + item.itemDesc + '</textarea>'
			   +' </td>'
			   + '<td align="left">'
			     	+ '<a href="javascript:void();" onclick="update(' +  item.sid + ' , this)" >更新</a>'
			     	+ '<a href="javascript:delById(' + item.sid + ');" >&nbsp;&nbsp;删除</a>'
			   + '</td>'
			   + '</tr>';
			   $("#tbody").append(tempStr);
			   //validType:validTypeStr
			   
		});
		 $("#tbody").find("textarea[id^='itemName_'").validatebox({ 
				required:true 
		 }); 
		 
		 $("#tbody").find("input[id^='itemMin'").validatebox({ 
				required:true ,
				validType:'number[]'
		 }); 
		 
		 $("#tbody").find("input[id^='itemMax'").each(function(i,item){
			 var prev = $(item).prev('input');
			 $(item).validatebox({ 
					required:true ,
					validType:'number[]&numberComparTo[' + prev.val() + ']'
			 }); 
		 })


	} else {
		alert(jsonObj.rtMsg);
	}
}
/**
 * 新建
 */
function doSave(){
	if(checkFrom()){
		var url =   "<%=contextPath%>/TeeExamineItemManage/addOrUpdate.action?groupId=" + groupId;
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url, para);
		
		if (jsonObj.rtState) {
			window.location.reload();
		}else{
			alert(jsonObj.rtMsg);
		}
	}
}
	
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").form('validate'); 
	 if(!check  ){
		 return false; 
	 }
/* 	 if($("#itemMin")){
		 
	 } */
	 return true;
}

function returnFun(){
	window.location.href = "<%=contextPath%>/system/core/base/examine/group/index.jsp";
}

/**
 * 删除
 */
function delById(sid){
	$.jBox.confirm("确认删除该指标集明细吗，","确认",function(v){
		if(v=="ok"){
			var url =   "<%=contextPath%>/TeeExamineItemManage/delById.action?sid=" + sid;
			var para =  {sid:sid} ;
			var jsonObj = tools.requestJsonRs(url, para);
			if (jsonObj.rtState) {
				$.jBox.tip("删除成功","info",{timeout:1500});
				doInit();
			}else{
				alert(jsonObj.rtMsg);
			}
		}
	});
	
}

/**
 * 更新
 */
function update(sid , obj){
	var flag = true;
	$(obj).parent().parent().find('input').each(function () {
	    if ($(this).attr('required') || $(this).attr('validType')) {
		    if (!$(this).validatebox('isValid')) {
		        flag = false;
		        return;
		    }
   		}
	});

	if (flag){
		var itemName = $("#itemName_" + sid).val();
		var itemMin = $("#itemMin_" + sid).val();
		var itemMax = $("#itemMax_" + sid).val();
		var itemDesc = $("#itemDesc_" + sid).val();
		var url =   "<%=contextPath%>/TeeExamineItemManage/addOrUpdate.action?groupId=" + groupId;
		var para =  {itemName: itemName , itemMin:itemMin,itemMax:itemMax, itemDesc:itemDesc , sid:sid} ;
		var jsonObj = tools.requestJsonRs(url, para);
		
		if (jsonObj.rtState) {
			$.jBox.tip("修改成功","info",{timeout:1500});
			doInit();
		}else{
			alert(jsonObj.rtMsg);
		}
		//alert(itemName +":"+ itemMin +":"+itemMax +":"+  itemDesc);
	}else{
		//alert('');
		return;
	}
    

}
</script>

</head>

<body onload="doInit();">
<table border="0" width="100%" cellspacing="0" cellpadding="3"   style="margin:8px 0px;"  class="small">
  <tr>
    <td class="Big3"><span class="big3">&nbsp;考核指标集明细管理</span>
    </td>
  </tr>
</table>

<div style="margin:0 auto;">
	<form name="form1" id="form1" method="post">
		<table class="TableList" width="90%" align="center">
		<tr class="TableHeader" align="center">
		    <td width="">考核项目</td>
		    <td  nowrap="nowrap">分值范围</td>
		    <td width=>分值说明</td>
		    <td>操作</td>
		</tr>
		<tbody id="tbody">
			
		</tbody>
		<tr class="TableData" align="center" >
			
			    <td width="140" align="left">
			    	<textarea rows="2" cols="40" name="itemName" class="BigTextarea easyui-validatebox" maxlength="200" required="true"></textarea>
			    </td>
			    <td align="left" >
			    	<input type="text" name="itemMin" id="itemMin" size="5" maxlength="8" class="BigInput easyui-validatebox" required="true" validType ="number[]"/> ~
			    	
			    	<input type="text" name="itemMax" id="itemMax" size="5"  maxlength="8" class="BigInput easyui-validatebox" required="true"  validType ="number[]&numberComparTo[$('#itemMin').val()]"/> 
			    </td>
			     <td align="left">
			     	<textarea rows="2" cols="60" name="itemDesc" class="BigTextarea"></textarea>
			    </td>
			    
			     <td align="left">
			     	<input value="新建" type="button" class="btn btn-primary" onclick="doSave()"/>
			    </td>
		</tr>	   
	</table>
	</form>
	<div style="margin:0 auto;text-align: center;padding-top:20px;">
		<input type="button" class="btn btn-primary" value="返回" onclick="returnFun();">
	</div>
</div>

</body>
</html>

