<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%
	String sid = request.getParameter("sid");
	String proCode = request.getParameter("proCode");
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style type="text/css">
.TableHeader
{
    font-size:14px;
}

</style>
<script>
var sid = "<%=sid%>";
var proCode = "<%=proCode%>";

function doInit(){
	//加载物品数据
	var url = contextPath+"/officeProductController/getModelById.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	bindJsonObj2Cntrl(json.rtData);
	if(json.rtData.regType=='1'){
		$("#regTypeName").html("领用");
	}else if(json.rtData.regType=='2'){
		$("#regTypeName").html("借用");
	}
	getRecords();

}

function getRecords(){
	var url = contextPath+"/officeRecordController/getOfficeRecords.action";
	var json1 = tools.requestJsonRs(url,{proCode:proCode,recordType : 1});
	if(json1.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td> 入库数量  </td><td>原库存  </td><td>入库人员 </td><td> 入库时间  </td></tr>";
		var data = json1.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#rukuRecords").html(html);
	}else{
		$("#rukuRecords").html("没有相关记录！");
	}
	var json2 = tools.requestJsonRs(url,{proCode:proCode,recordType : 2});
	if(json2.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td>报废数量</td><td>原库存  </td><td>操作人员 </td><td> 报废时间  </td></tr>";
		var data = json2.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#baofeiRecords").html(html);
	}else{
		$("#baofeiRecords").html("没有相关记录！");
	}
	var json3 = tools.requestJsonRs(url,{proCode:proCode,recordType : 3});
	if(json3.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td> 领用数量  </td><td>原库存  </td><td>领用人员 </td><td> 调度时间  </td></tr>";
		var data = json3.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#lingyongRecords").html(html);
	}else{
		$("#lingyongRecords").html("没有相关记录！");
	}
	var json4 = tools.requestJsonRs(url,{proCode:proCode,recordType : 4});
	if(json4.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td> 借用数量  </td><td>原库存  </td><td>借用人员 </td><td> 调度时间  </td></tr>";
		var data = json4.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#jieyongRecords").html(html);
	}else{
		$("#jieyongRecords").html("没有相关记录！");
	}
	var json5 = tools.requestJsonRs(url,{proCode:proCode,recordType : 5});
	if(json5.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td> 归还数量  </td><td>原库存  </td><td>归还人员 </td><td> 归还时间  </td></tr>";
		var data = json5.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#guihuanRecords").html(html);
	}else{
		$("#guihuanRecords").html("没有相关记录！");
	}
	var json6 = tools.requestJsonRs(url,{proCode:proCode,recordType : 6});
	if(json6.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td> 维护数量  </td><td>原库存  </td><td>维护人员 </td><td> 维护时间  </td></tr>";
		var data = json6.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#weihuRecords").html(html);
	}else{
		$("#weihuRecords").html("没有相关记录！");
	}
	var json8 = tools.requestJsonRs(url,{proCode:proCode,recordType : 8});
	if(json8.rtState){
		var html="<table class='TableBlock' width='100%'><tr class='TableHeader'><td> 删除数量  </td><td>原库存  </td><td>删除人员 </td><td> 删除时间  </td></tr>";
		var data = json8.rtData;
		for(var i = 0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].regCount+"</td><td>"+data[i].originStock+"</td><td>"+data[i].regUserName+"</td><td>"+data[i].actionTimeDesc+"</td></tr>";
		}
		html+="</table>";
		$("#shanchuRecords").html(html);
	}else{
		$("#shanchuRecords").html("没有相关记录！");
	}
	
}
</script>

</head>
<body onload="doInit()" style="font-size:12px">
<form id="form1" name="form1">
	<table style="width:100%;font-size:12px" class="TableBlock">
		<tr class="TableHeader">
			<td colspan="4">
					用品基础信息
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>用品名称<span style="color:red;font-weight:bold;">*</span>：</b>
				</td>
			<td class="TableData">
				<span type="text" required="true" class="easyui-validatebox BigInput" name="proName" id="proName" > </span>
			</td>
			<td class="TableData">
				<b>用品编号：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigInput" name="proCode" id="proCode" > </span>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>计量单位：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigInput" name="proUnit" id="proUnit"> </span>
			</td>
			<td class="TableData">
				<b>规格：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigInput" name="norms" id="norms" > </span>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>供应商：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigInput" name="proSupplier" id="proSupplier" > </span>
			</td>
			<td class="TableData">
				<b>单价（元）：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigInput easyui-validatebox" validType="number[]" name="price" id="price" > </span>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>最低警戒库存：</b>
				</td>
			<td class="TableData">
				<span type="text" class="BigInput easyui-validatebox" name="minStock" id="minStock" > </span>
			</td>
			<td class="TableData">
				<b>最高警戒库存：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigInput easyui-validatebox" name="maxStock" id="maxStock" > </span>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>用品库<span style="color:red;font-weight:bold;">*</span>：</b>
			</td>
			<td class="TableData">
				<span class="BigSelect" id="depositoryName" ">
				</span>
			</td>
			<td class="TableData">
				<b>申请类型<span style="color:red;font-weight:bold;">*</span>：</b>
			</td>
			<td class="TableData">
				<span type="text" class="BigSelect" name="regTypeName" id="regTypeName">
				</span>
			</td>
		</tr>
		<tr>
			<td class="TableData">
		  		<b>用品类别<span style="color:red;font-weight:bold;">*</span>：</b>
		    </td>
			<td class="TableData" colspan="3">
				<span class="BigSelect" id="categoryName" name="categoryId">
					<option></option>
				</span>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>用品描述：</b>
		</td>
			<td class="TableData" colspan="3">
				<span class="BigTextarea" style="width:500px;height:100px" name="proDesc" id="proDesc"></span>
			</td>
		</tr>
		<tr class="TableHeader">
			<td colspan="4">
					用品记录
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>入库记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="rukuRecords">
					
				</div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>报废记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="baofeiRecords">
					
				</div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>领用记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="lingyongRecords">
					
				</div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>借用记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="jieyongRecords">
					
				</div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>归还记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="guihuanRecords">
					
				</div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>维护记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="weihuRecords">
					
				</div>
			</td>
		</tr>
		<tr>
			<td class="TableData">
				<b>删除记录：</b>
			</td>
			<td class="TableData" colspan="3">
				<div id="shanchuRecords">
					
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" name="sid" value="<%=sid %>" />
</form>
</body>
</html>