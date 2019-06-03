<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>流程委托</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/common/js/src/orgselect.js"></script>
<script type="text/javascript">
var frpSid = <%=frpSid%>;

function doInit(){
	var url = contextPath+"/workflowManage/getDelegateHandlerData.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid});
	var tbody = $("#tbody");
	if(json.rtState){
		var userFilters="";
		var userFiltersArray = json.rtData.userFilters;
		for(var i=0;i<userFiltersArray.length;i++){
			userFilters+=userFiltersArray[i];
			if(i!=userFiltersArray.length-1){
				userFilters+=",";
			}
		}
		userFilters = userFilters+"";
		var prcsList = json.rtData.prcsList;
		var group = groupBy(prcsList);
		var render = "";
		for(var i=0;i<group.length;i++){
			render+="<tr>";
			var set = group[i];
			var rows = 0;
			for(var key in set){
				rows++;
			}
			render+="<td rowspan="+rows+">第"+(i+1)+"步</td>";
			for(var key in set){
				var arr = set[key];
				render+="<td>步骤："+arr[0].prcsName+"</td>";
				render+="<td>";
				//先扫描与当前步骤主键相同的项
				var hasBreak = false;
				for(var index=0;index<arr.length;index++){
					var prcsInfo = arr[index];
					if(prcsInfo.sid==frpSid){//如果是当前步骤流程
						render+="<input type='hidden'  id='delegate' name='delegate'/><input readonly type='text' id='delegateDesc'  name='delegateDesc' class='BigInput'/><a href='javascript:void(0)' onclick='selectEvent(\""+userFilters+"\")'>选择</a>";
						hasBreak = true;
						break;
					}
				}
				if(!hasBreak){
					for(var index=0;index<arr.length;index++){
						var prcsInfo = arr[index];
						if(prcsInfo.topFlag==1){
							render+=prcsInfo.prcsUserName;
							break;
						}
					}
				}
				render+="</td>";
				if(rows!=1){
					render+="</tr>";
					render+="<tr>";
				}
			}
			render+="</tr>";
		}
		tbody.html(render);
	}else{
		messageMsg(json.rtMsg,"content","error");
		window.commit = function(){};
	}
}

function selectEvent(userFilters){
	selectSingleUser(['delegate','delegateDesc'],'','',userFilters,'');
}

function groupBy(prcsList){
	var group = new Array;
	var maxPrcsId = prcsList[prcsList.length-1].prcsId;
	for(var i=1;i<=maxPrcsId;i++){
		var set = {};
		for(var j=0;j<prcsList.length;j++){
			var prcsId = prcsList[j].prcsId;
			var flowPrcsId = prcsList[j].flowPrcsId;
			if(prcsId!=i){
				continue;
			}
			var id = prcsId+""+flowPrcsId;
			var arr = set[id];
			if(!arr){
				arr = new Array();
			}
			arr.push(prcsList[j]);
			set[id] = arr;
		}
		group.push(set);
	}
	return group;
}

function commit(){
	var delegate = $("#delegate");
	var delegateDesc = $("#delegateDesc");
	if(delegate.val()==""){
		top.$.jBox.tip("请选择被委托人","info");
		return;
	}

	var url = contextPath+"/workflowManage/flowRunDelegate.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid,delegateTo:delegate.val()});
	if(json.rtState){
		top.$.jBox.tip(json.rtMsg,"info");
		if(parent.pageHandler){
			parent.pageHandler();//处理调用父页面的处理方法
		}
		top.$.jBox.close(true);
		return true;
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}

</script>
<style>

</style>
</head>
<body onload="doInit()" style="font-size:12px">
<center id="content">
<table class="TableBlock" width="100%" align="center">
   <tr>
    <td nowrap  colspan="2"  class="TableData">
    	<table style="width:100%;font-size:12px">
    		<tbody id="tbody" ></tbody>
    	</table>
    </td>
   </tr>
 	<TR class=TableControl style="display:none">
			<TD style="text-align: center; border-right: medium none"  align=middle>
				 <input class="btn btn-success"  value="确认委托" type="button" onclick="commit()" >&nbsp;&nbsp; 
			</TD>
	</TR>
</table>
</center>
</body>
</html>
