<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%

	int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);//考核任务Id
	int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);//被考核人Id
	String userName = TeeStringUtil.getString(request.getParameter("userName"), "");//被考核人Id
	int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);//考核集Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title >考核</title>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/examine/js/examine.js"></script>
<script type="text/javascript">
var taskId = <%=taskId%>;
var userId = "<%=userId%>";
var  groupId = "<%=groupId%>";
function doInit(){

	var url =   "<%=contextPath%>/TeeExamineDataManage/getSelfData.action";
	var para = {groupId:groupId , taskId: taskId , userId : userId};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		
		var prc = jsonObj.rtData;
		var isSelfData = prc.isSelfData;//是否已经自评
		if(userId > 0){
			if(isSelfData){
				$("#save").show();
				var itemList = prc.itemList;//考核集项目明细
				var data = prc.data;//考核数据
				$(itemList).each(function(i,item){
					var temp = item.itemName  + "<font color='red'>(" + item.itemMin +" ~ " + item.itemMax + ")</font><br>分值说明 ：" + item.itemDesc;
					var tempStr = '<tr class="TableData">'
					   + '<td width="140">'
					   + temp
					   +  '</td>'
					   + '<td align="left">'
					    	+ '<input name="item" id="item_' + item.sid + '" itemId="'+ item.sid  + '" itemMin="' + item.itemMin + '"  itemMax="' + item.itemMax + '"  maxlength="8" size="5" class="BigInput easyui-validatebox" required="true" />'
					   + ' </td>'
					   + ' <td align="left">'
					     	+ '<textarea rows="2" cols="60" id="itemDesc_' + item.sid  + '" name="itemDesc" class="BigTextarea"></textarea>'
					   +' </td>'
					   + '</tr>';
					   $("#tbody").append(tempStr);
					   //validType:validTypeStr
				});

				if(data.sid > 0){
					var dataArray = tools.string2JsonObj(data.examineData);
					$.each(dataArray,function(i , e){
						var itemId = e.itemId;
						var score = e.score ;
						var itemDesc =  e.itemDesc;
						//alert(itemId +":" + score  +":" + itemDesc);
						$("#item_" + itemId).val(score);
						$("#itemDesc_" + itemId).val(itemDesc);
					});
				}
				
				
				
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
				
				var selfData = prc.selfData;
				if(selfData && selfData.sid > 0){//
 					var task = prc.task;
					groupId = task.groupId;
					$("#tbody2").append("<tr  class='TableData'><td colspan='3'>"
						+ " <a href='javascript:void();' onclick='getSelfData(" + selfData.sid + "," + groupId + ");'>查看自评详情</a></td> </tr>");
				}
				
			}else{
				$("#listDiv").empty();
				messageMsg("该考核任务需要被考核人先进行自评，然后再进行考核", "listDiv" ,'' ,420);
			}
		}else{
			$("#listDiv").empty();
			var task = prc.task;
			messageMsg(task.taskTitle, "listDiv" ,'' ,420);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}

function returnIndex(){
	parent.location.href = "<%=contextPath%>/system/core/base/examine/manage/manager.jsp";
}



/**
 * 新建
 */
function doSave(){
	if(checkFrom()){
		var itemArray = getSelfInfo();
		var url =   "<%=contextPath%>/TeeExamineDataManage/addOrUpdate.action";
		var para = {groupId: groupId , taskId:taskId, participantId:userId,examineData:itemArray} ;
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
    <td class="Big3"><span class="big3">&nbsp;考核数据录入 <font color="red"><%=userName %></font> </span>
    </td>
  </tr>
</table>

<div style="margin:0 auto;" id="listDiv">
	<form name="form1" id="form1" method="post">
		<table class="TableList" width="85%" align="center">
			<tr class="TableHeader" align="center">
			    <td width="350">考核项目</td>
			    <td width="100">分数</td>
			    <td width=>考核说明</td>
			</tr>
			<tbody id="tbody">
				
			</tbody>	
			
			   <tbody id="tbody2">
				
			</tbody>	
		</table>
		<input type="hidden" name="participantId" value="<%=userId%>"> 
	</form>
</div>


<div style="text-align: center;margin-top:20px;">
	<input type="button" style="display:none;" id="save" class="btn btn-primary" value="保存" onclick="doSave();">&nbsp;&nbsp;
	<input type="button" class="btn btn-primary" value="返回" onclick="returnIndex();">
</div>
</body>
</html>


