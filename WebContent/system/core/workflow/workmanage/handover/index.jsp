<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>工作移交</title>
	<script>
	function doInit(){
		tools.requestJsonRs(contextPath+"/workQuery/getFlowType2SelectCtrl.action",{},true,function(json){
			var list = json.rtData;
			var sort = [];
			var group = {};
			//搜索出分组
			for(var i=0;i<list.length;i++){
				var item = list[i];
				if(item.id=="0"){
					continue;
				}
				if(item.id.indexOf("sort")!=-1){//处理分类，初始化
					sort.push({id:item.id,name:item.name});
					group[item.id] = [];
					continue;
				}
				group[item.pId].push({id:item.id,name:item.name});
			}
			
			var render = [];
			for(var i=0;i<sort.length;i++){
				render.push("<optgroup label='"+sort[i].name+"'>");
				var itemArr = group[sort[i].id];
				for(var j=0;j<itemArr.length;j++){
					render.push("<option value='"+itemArr[j].id+"'>"+itemArr[j].name+"</option>");
				}
				render.push("</optgroup>");
			}
			
			$("#flowIds").html(render.join(""));
		});
	}
	
	
	function doCommit(btn){
		var flowIdsArr = [];
		var fromUser = $("#fromUser").val();
		var toUser = $("#toUser").val();
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var beginRunId = $("#beginRunId").val();
		var endRunId = $("#endRunId").val();
		
		$("#flowIds option:selected").each(function(i,obj){
			flowIdsArr.push(obj.value);
		});
		if(flowIdsArr.length==0){
			$.MsgBox.Alert_auto("请选择至少一项流程！");
			return;
		}
		if(fromUser==""){
			$.MsgBox.Alert_auto("请选择原办理人！");
			return;
		}
		if(toUser==""){
			$.MsgBox.Alert_auto("请选择移交对象！");
			return;
		}
		if(fromUser==toUser){
			$.MsgBox.Alert_auto("原办理人不能与移交对象相同！");
			return;
		}
		
		$(btn).attr("disabled","disabled");
		var json = tools.requestJsonRs(contextPath+"/workflowManage/handover.action",{
			flowIds:flowIdsArr.join(","),
			fromUser:fromUser,
			toUser:toUser,
			beginTime:beginTime,
			endTime:endTime,
			beginRunId:beginRunId,
			endRunId:endRunId
		});
		
		$(btn).removeAttr("disabled");
		if(json.rtState){
			parent.$.MsgBox.Alert_auto("移交成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
		
	}
	</script>
</head>
<body  onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzyj/icon_gzyj.png">&nbsp;&nbsp;
		<span class="title">工作移交</span>
	</div>
</div>

<div class="base_layout_center" style="padding:10px">
<table class="TableBlock_page" style="width:90%;">
	<tr>
		<td class="TableHeader" style="width:120px;text-indent: 10px;">
			<font color="red">*&nbsp;</font>选择流程：
		</td>
		<td class="TableData">
			<select id="flowIds" class="BigSelect" multiple style="width:300px;height:200px;text-align:left;font-family:Microsoft YaHei;font-size: 12px; ">
				
			</select>
		</td>
	</tr>
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 10px;">
			<font color="red">*&nbsp;</font>原办理人：
		</td>
		<td class="TableData">
			<input type="hidden" id="fromUser" name="fromUser"/>
			<input style="width:300px;font-family: MicroSoft YaHei;" type="text" id="fromUserName" name="fromUserName" class="BigInput readonly" readonly/>
			 <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzyj/add.png" onClick="selectSingleUser(['fromUser','fromUserName'])" value="选择"/>
		     </span>
		</td>
	</tr>
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 10px;">
			<font color="red">*&nbsp;</font>移交对象：
		</td>
		<td class="TableData">
			<input type="hidden" id="toUser" name="toUser"/>
			<input style="width:300px;font-family: MicroSoft YaHei;" type="text" id="toUserName" name="toUserName" class="BigInput readonly" readonly/>
			 <span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzyj/add.png" onClick="selectSingleUser(['toUser','toUserName'])" value="选择"/>
		     </span>
		</td>
	</tr>
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			时间范围：
		</td>
		<td class="TableData">
			<input style="width:300px;" type="text" name="beginTime" id="beginTime" size="20" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput">
			至
			<input style="width:300px;" type="text" name="endTime" id="endTime" size="20" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" class="Wdate BigInput">
		</td>
	</tr>
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			流水号范围：
		</td>
		<td class="TableData">
			<input style="width:300px;" type="text" name="beginRunId" id="beginRunId" size="20" class="BigInput">
			至
			<input style="width:300px;" type="text" name="endRunId" id="endRunId" size="20" class="BigInput">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<center>
				<button class="btn-win-white" onclick="doCommit(this)">确定移交</button>
			</center>
		</td>
	</tr>
</table>
	
</div>
</body>

</html>