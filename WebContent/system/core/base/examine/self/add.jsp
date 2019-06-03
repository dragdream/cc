<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      
<%
      	int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);//指标集Id
      	int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);//考核任务Id
      %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title >自评 </title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript">
var groupId = <%=groupId%>;
var taskId = <%=taskId%>;

function doInit(){
	var url =   "<%=contextPath%>/TeeExamineItemManage/getAllByGroupId.action";
	var para = {groupId:groupId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		$("#tbody").empty();
		var prcs = jsonObj.rtData;
		$(prcs).each(function(i,item){
			
			var temp = item.itemName  + "<font color='red'>(" + item.itemMin +" ~ " + item.itemMax + ")</font><br>分值说明 ：" + item.itemDesc;
			var tempStr = '<tr class="TableData">'
			   + '<td width="140">'
			   + temp
			   +  '</td>'
			   + '<td align="left">'
			    	+ '<input name="item" itemId="' + item.sid + '" itemMin="' + item.itemMin + '"  itemMax="' + item.itemMax + '"  maxlength="8" size="5" class="BigInput easyui-validatebox" required="true" />'
			   + ' </td>'
			   + ' <td align="left">'
			     	+ '<textarea rows="2" cols="60" name="itemDesc" class="BigTextarea"></textarea>'
			   +' </td>'
			   + '</tr>';
			   $("#tbody").append(tempStr);
			   //validType:validTypeStr
		});
		//给输入数据添加判断
		$("#tbody").find("input[name='item']").each(function(i,item){
			var itemMin = $(item).attr("itemMin");
			var itemMax = $(item).attr("itemMax");
			var validTypeStr = "numberBetweenLength[" +  itemMin + "," + itemMax + "]";
			$(item).validatebox({ 
				required:true 
				,validType:validTypeStr
			});
		});
		
		$("#tbody").find("textarea[name='itemDesc']").each(function(i,item){
			$(item).validatebox({ 
				required:true 
			});
		});
		
		
	} else {
		alert(jsonObj.rtMsg);
	}
}
/**
 * 新建
 */
function doSave(){
	if(checkFrom()){
		
		var itemArray = getSelfInfo();
		
		var url =   "<%=contextPath%>/TeeExamineSelfDataManage/addOrUpdate.action";
		var para = {groupId: groupId , taskId:taskId,selfData:itemArray} ;
		 var jsonObj = tools.requestJsonRs(url, para);
		
		if (jsonObj.rtState) {
			//window.location.reload();
			$.jBox.tip("保存成功!","info", {timeout:1500});
			// doInit();
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
	 return true;
}


/**
 * 获取每个考核信息
 */
function getSelfInfo(){
	//[{itemId:2,score:20.4,desc:”测试”},{item_id:3,score:2.4,desc:”测试2”}]	考核内容：item_id为表EXAMINE_item.sidscore ：考核分数desc：考核描述
	var itemArray = new Array();
	$("#tbody").find("tr").each(function(i,item){
		var itemObj = $(item).find("input[name='item']")[0];
		var itemDescObj =$(item).find("textarea[name='itemDesc']")[0];
		var itemDesc = itemDescObj.value;
		var itemId = $(itemObj).attr("itemId");
		var score = itemObj.value; 
		//alert(itemId +":"+ score +":"+ itemDesc);
		var temp = {itemId:itemId ,score:score ,itemDesc: itemDesc };
		itemArray.push(temp);
	});
	return  tools.jsonArray2String(itemArray);//json转字符串
}
</script>

</head>

<body onload="doInit();">
<table border="0" width="100%" cellspacing="0" cellpadding="3"   style="margin:8px 0px;"  class="small">
  <tr>
    <td class="Big3"><span class="big3">&nbsp;自评信息录入(<%=userName %>) </span>
    </td>
  </tr>
</table>

<div style="margin:0 auto;">
	<form name="form1" id="form1" method="post">
		<table class="TableList" width="85%" align="center">
			<tr class="TableHeader" align="center">
			    <td width="350">自评考核项目</td>
			    <td width="100">分数</td>
			    <td width=>自评说明</td>
			</tr>
			<tbody id="tbody">
				
			</tbody>
			<tr class="TableData" align="center" >
				<td colspan="3">
					<input type="button" class="btn btn-primary" value="保存" onclick="doSave();">&nbsp;&nbsp;
					<input type="button" class="btn btn-primary" value="返回" onclick="history.go(-1);">
				</td>
				
			</tr>	   
		</table>
	
	</form>
</div>

</body>
</html>

