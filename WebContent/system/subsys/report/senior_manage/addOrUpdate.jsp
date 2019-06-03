<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script>
var id = "<%=id%>";

function doInit(){
	//加载视图
	var json = tools.requestJsonRs(contextPath+"/bisView/listBisView.action",{rows:10000,page:1});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$("#dataIdentity").append("<option value='"+list[i].identity+"' type='"+list[i].type+"'>"+list[i].name+"</option>");
		$("#ctView").append("<option value='"+list[i].identity+"' type='"+list[i].type+"'>"+list[i].name+"</option>");
	}
	
	//加载报表
	var json = tools.requestJsonRs(contextPath+"/seniorReport/datagrid.action",{rows:10000,page:1});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$("#ctReport").append("<option value='"+list[i].uuid+"'>"+list[i].tplName+"</option>");
	}
	
	//加载分类
	var json = tools.requestJsonRs(contextPath+"/seniorReportCat/datagrid.action",{rows:10000,page:1});
	var render = [];
	for(var i=0;i<json.rows.length;i++){
		render.push("<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>");
	}
	$("#catId").append(render.join(""));
	
	var json;
	if(id!="null"){
		json = tools.requestJsonRs(contextPath+"/seniorReport/getReport.action",{uuid:id});
		bindJsonObj2Cntrl(json.rtData);
		
		var header = eval("("+json.rtData.header+")");
		var body = eval("("+json.rtData.body+")");
		var footer = eval("("+json.rtData.footer+")");
		
		//先清除之前列
		$("#theadTr1").children().eq(1).remove();
		$("#tbodyTr1").children().eq(1).remove();
		$("#tbodyTr2").children().eq(1).remove();
		
		//组合最新列
		var renderHeader = [];
		var renderBody = [];
		var renderFooter = [];
		for(var i=0;i<header.length;i++){
			renderHeader.push("<td fixed="+(!header[i].fixed?"":header[i].fixed)+">");
			renderHeader.push("<img src='"+systemImagePath+"/other/setting.png' style='cursor:pointer' onclick='detail(this)' />&nbsp;<img src='"+systemImagePath+"/other/devide.png' style='cursor:pointer' onclick='delHeaderItem(this)' /><input value='"+header[i].field+"' /><img src='"+systemImagePath+"/other/plus.png' onclick='addHeaderItem(this)'  style='cursor:pointer'/>");
			renderHeader.push("</td>");
			
			renderBody.push("<td>");
			renderBody.push("<input value='"+body[i].exp+"'/>");
			renderBody.push("</td>");
			
			renderFooter.push("<td>");
			renderFooter.push("<input value='"+footer[i].exp+"'/>");
			renderFooter.push("</td>");
		}
		
		$("#theadTr1").children().eq(0).after(renderHeader.join(""));
		$("#tbodyTr1").children().eq(0).after(renderBody.join(""));
		$("#tbodyTr2").children().eq(0).after(renderFooter.join(""));
		
		
	}
	
	doChange(dataIdentity);
	
	if(id!="null"){
		var model = json.rtData.model;
		model = eval("("+model+")");
		var rows = model.rows;
		var cols = model.cols;
		var sums = model.sums;
		if(rows){
			$("#rows").val(rows.fieldType+","+rows.field+","+rows.name);
			if(rows.fieldType=="DATE" || rows.fieldType=="DATETIME"){
				$("#rowsDate").show().val(rows.date);
				$("#rowsDateRange").show().val(rows.dateRange);
			}
		}
		if(cols){
			$("#cols").val(cols.fieldType+","+cols.field+","+cols.name);
			if(cols.fieldType=="DATE" || cols.fieldType=="DATETIME"){
				$("#colsDate").show().val(cols.date);
				$("#colsDateRange").show().val(cols.dateRange);
			}
		}
		if(sums){
			$("#sums").val(sums.fieldType+","+sums.field+","+sums.name);
			$("#sumsType").val(sums.type);
		}
		
	}
	
	ctChange();
	
}

//增加表头列
function addHeaderItem(obj){
	var index = $(obj).parent().index();
	$(obj).parent().after("<td fixed=0><img src='"+systemImagePath+"/other/setting.png' style='cursor:pointer' onclick='detail(this)' />&nbsp;<img src='"+systemImagePath+"/other/devide.png' style='cursor:pointer' onclick='delHeaderItem(this)' /><input value='表头' /><img src='"+systemImagePath+"/other/plus.png' onclick='addHeaderItem(this)'  style='cursor:pointer'/></td>");
	$("#tbodyTr1").children().eq(index).after("<td><input value=''/></td>");
	$("#tbodyTr2").children().eq(index).after("<td><input value=''/></td>");
}

function detail(obj){
	target = $(obj).parent();
	$("#configDiv").modal("show");
	$("#fixed").val(target.attr("fixed"));
}

function ok(){
	target.attr("fixed",$("#fixed").val());
	$("#configDiv").modal("hide");
}

//移除表头列
function delHeaderItem(obj){
	var index = $(obj).parent().index();
	$(obj).parent().remove();
	$("#tbodyTr1").children().eq(index).remove();
	$("#tbodyTr2").children().eq(index).remove();
}

function commit(){
	var header = [];
	var body = [];
	var footer = [];
	
	var td = $("#theadTr1 td").each(function(i,obj){
		if(i!=0){
			header.push({field:$(obj).find("input:first").val(),fixed:$(obj).attr("fixed")});
		}
	});
	
	var td = $("#tbodyTr1 td").each(function(i,obj){
		if(i!=0){
			body.push({exp:$(obj).find("input:first").val()});
		}
	});
	
	var td = $("#tbodyTr2 td").each(function(i,obj){
		if(i!=0){
			footer.push({exp:$(obj).find("input:first").val()});
		}
	});
	
	var para = {header:tools.jsonArray2String(header),
			body:tools.jsonArray2String(body),
			footer:tools.jsonArray2String(footer),
			tplName:$("#tplName").val(),
			dataIdentity:$("#dataIdentity").val(),
			pageSize:$("#pageSize").val(),
			group:$("#group").val(),
			userPrivIds:$("#userPrivIds").val(),
			deptPrivIds:$("#deptPrivIds").val(),
			chartType:$("#chartType").val(),
			reverse:$("#reverse").val(),
			catId:$("#catId").val(),
			params:$("#params").val(),
			status:$("#status").val(),
			uuid:id=="null"?"":id};
	
	
	var url = "";
	if(id=="null"){
		url = contextPath+"/seniorReport/addReport.action";
	}else{
		url = contextPath+"/seniorReport/updateReport.action";
	}
	
	
	var rows = $("#rows").val();
	var rowsDate = $("#rowsDate").val();
	var rowsDateRange = $("#rowsDateRange").val();
	
	var cols = $("#cols").val();
	var colsDate = $("#colsDate").val();
	var colsDateRange = $("#colsDateRange").val();
	
	var sums = $("#sums").val();
	var sumsType = $("#sumsType").val();
	
	var model = {};
	if(rows!=""){
		var sp = rows.split(",");
		var rowsModel = {};
		rowsModel.fieldType = sp[0];
		rowsModel.field = sp[1];
		rowsModel.name = sp[2];
		rowsModel.date = rowsDate;
		rowsModel.dateRange = rowsDateRange;
		model["rows"] = rowsModel;
	}
	
	if(cols!=""){
		var sp = cols.split(",");
		var colsModel = {};
		colsModel.fieldType = sp[0];
		colsModel.field = sp[1];
		colsModel.name = sp[2];
		colsModel.date = colsDate;
		colsModel.dateRange = colsDateRange;
		model["cols"] = colsModel;
	}
	
	if(sums!=""){
		var sumsModel = {};
		sumsModel.field = sums.split(",")[1];
		sumsModel.type = sumsType;
		sumsModel.name = sums.split(",")[2];
		sumsModel.fieldType = sums.split(",")[0];
		model["sums"] = sumsModel;
	}
	
	para["model"] = tools.jsonObj2String(model);
	para["ctType"] = $("#ctType").val();
	para["ctView"] = $("#ctView").val();
	para["ctReport"] = $("#ctReport").val();
	para["ctParams"] = $("#ctParams").val();
	
	var json = tools.requestJsonRs(url,para);
	alert(json.rtMsg);
	if(json.rtState){
		if(id=="null"){
			window.location = "addOrUpdate.jsp?sid="+json.rtData+"&bbType="+bbType;
		}
	}
}

function addCol(header,body,footer){
	
}

function doChange(obj){
	var opt = $(obj).find("option:selected");
	var type = opt.attr("type");
	if(type==1){//设计
		$("#type1").show();
		$("#type2").hide();
	}else{//高级
		$("#type1").hide();
		$("#type2").show();
	}
	var json = tools.requestJsonRs(contextPath+"/bisView/listBisViewListItem.action?identity="+dataIdentity.value);
	$("#rows").html("<option value=''></option>");
	$("#cols").html("<option value=''></option>");
	$("#sums").html("<option value=''></option>");
	for(var i=0;i<json.rows.length;i++){
		$("#rows").append("<option value='"+json.rows[i].fieldType+","+json.rows[i].searchField+","+json.rows[i].field+"'>"+json.rows[i].field+"</option>");
		$("#cols").append("<option value='"+json.rows[i].fieldType+","+json.rows[i].searchField+","+json.rows[i].field+"'>"+json.rows[i].field+"</option>");
		$("#sums").append("<option value='"+json.rows[i].fieldType+","+json.rows[i].searchField+","+json.rows[i].field+"'>"+json.rows[i].field+"</option>");
	}
}

function rowsChange(obj){
	var sp = obj.value.split(",");
	if(sp[0]=="DATE" || sp[0]=="DATETIME"){
		$("#rowsDate").show();
		$("#rowsDateRange").show();
	}else{
		$("#rowsDate").hide();
		$("#rowsDateRange").hide();
	}
}

function colsChange(obj){
	var sp = obj.value.split(",");
	if(sp[0]=="DATE" || sp[0]=="DATETIME"){
		$("#colsDate").show();
		$("#colsDateRange").show();
	}else{
		$("#colsDate").hide();
		$("#colsDateRange").hide();
	}
}

function ctChange(){
	var ctType = $("#ctType").val();
	if(ctType==1){
		$("#ctView").show();
		$("#ctReport").hide();
	}else if(ctType==2){
		$("#ctView").hide();
		$("#ctReport").show();
	}else{
		$("#ctView").hide();
		$("#ctReport").hide();
	}
}

</script>
</head>
<body onload="doInit()" style="margin:5px;">
	<div class="moduleHeader">
		<b>通用报表设置</b>
	</div>
	<button class="btn btn-primary" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn btn-default" onclick="CloseWindow()">关闭</button>
	<table style="font-size:12px;margin:5px;width:800px" class="TableBlock">
		<tr>
			<td class="TableHeader">模版名称：</td>
			<td class="TableData"><input type="text" class="BigInput" id="tplName" name="tplName"/></td>
			<td class="TableHeader">绑定视图：</td>
			<td class="TableData">
			<select id="dataIdentity" name="dataIdentity" class="BigSelect" onchange="doChange(this)">
				
			</select>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">分页大小：</td>
			<td class="TableData">
				<input type="text" class="BigInput" id="pageSize" name="pageSize"/>
			</td>
			<td class="TableHeader">统计图形：</td>
			<td class="TableData">
				<select id="chartType" class="BigSelect">
					<option value="table">表格</option>
					<option value="column">柱状图</option>
					<option value="bar">条形图</option>
					<option value="line">曲线图</option>
					<option value="scatter">散列图</option>
					<option value="area">区域图</option>
					<option value="polar">雷达图</option>
					<option value="pie">饼状图</option>
					<option value="funnel">漏斗图</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">行列转置：</td>
			<td class="TableData">
				<select id="reverse" class="BigSelect">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
			<td class="TableHeader">报表分类：</td>
			<td class="TableData">
				<select id="catId" name="catId" class="BigSelect">
					<option value="0">默认分类</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">初始化参数：</td>
			<td class="TableData">
				<input type="text" name="params" id="params" class="BigInput" placeholder="形如a=xxx&b=xxx&c=xx"/>
				<br/>
				<span style="color:green">形如a=xxx&b=xxx&c=xx，<br/>该参数会传入到视图中动态解析SQL语句</span>
			</td>
			<td class="TableHeader">启用状态</td>
			<td class="TableData">
				<select id="status" name="status" class="BigSelect">
					<option value="0">禁用</option>
					<option value="1" selected>启用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">穿透类型：</td>
			<td class="TableData">
				<select id="ctType" name="ctType" class="BigSelect" onchange="ctChange()">
					<option value="0" >无穿透</option>
					<option value="1" >视图/列表</option>
<!-- 					<option value="2" >报表</option> -->
				</select>
				<br/>
				<select id="ctView" name="ctView" class="BigSelect" style="display:none">
					
				</select>
				<select id="ctReport" name="ctReport" class="BigSelect" style="display:none">
					
				</select>
			</td>
			<td class="TableHeader">穿透参数</td>
			<td class="TableData">
				<input type="text" name="ctParams" id="ctParams" class="BigInput" placeholder="形如a={字段名1}&b={字段名2}&c={字段名3}"/>
					<br/>
					<span style="color:green">形如a={字段名1}&b={字段名2}&c={字段名3}，<br/>该参数会传入到下一个视图/列表（报表）中<br/>进行动态解析</span>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">人员权限：</td>
			<td class="TableData">
				<input type="hidden" name="userPrivIds" id="userPrivIds"/>
				<textarea id="userPrivNames" style="height:50px;width:150px;" readonly class="BigTextarea readonly"></textarea>
				<br/>
				<a href="javascript:void(0)" onclick="selectUser(['userPrivIds','userPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('userPrivIds','userPrivNames')">清空</a>
			</td>
			<td class="TableHeader">部门权限：</td>
			<td class="TableData">
				<input type="hidden" name="deptPrivIds" id="deptPrivIds"/>
				<textarea id="deptPrivNames" style="height:50px;width:150px;" readonly class="BigTextarea readonly"></textarea>
				<br/>
				<a href="javascript:void(0)" onclick="selectDept(['deptPrivIds','deptPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('deptPrivIds','deptPrivNames')">清空</a>
			</td>
		</tr>
	</table>
	<div id="type1" style="display:none">
		<table  style="font-size:12px;margin:5px;">
			<tr>
				<td>行标签</td>
				<td>
					<select id="rows" class="BigSelect" onclick="rowsChange(this)">
						
					</select>
					<select id="rowsDate" class="BigSelect" style="display:none">
						<option value="YEAR">年</option>
						<option value="MONTH">月</option>
						<option value="DATE">日</option>
					</select>
					<input id="rowsDateRange" class="BigInput" style="display:none" title="请输入时间范围，例如年：2015,2016；月：2015|01,02,03,04；日：2015|03|21,22,23,24" placeholder="请输入时间范围，例如年：2015,2016；月：2015|01,02,03,04；日：2015|03|21,22,23,24"/>
				</td>
			</tr>
			<tr>
				<td>列标签</td>
				<td>
					<select id="cols" class="BigSelect" onchange="colsChange(this)">
						
					</select>
					<select id="colsDate" class="BigSelect" style="display:none">
						<option value="YEAR">年</option>
						<option value="MONTH">月</option>
						<option value="DATE">日</option>
					</select>
					<input id="colsDateRange" class="BigInput" style="display:none" title="请输入时间范围，例如年：2015,2016；月：2015|01,02,03,04；日：2015|03|21,22,23,24" placeholder="请输入时间范围，例如年：2015,2016；月：2015|01,02,03,04；日：2015|03|21,22,23,24"/>
				</td>
			</tr>
			<tr>
				<td>汇总字段</td>
				<td>
					<select id="sums" class="BigSelect">
						
					</select>
					<select id="sumsType" class="BigSelect">
						<option value="COUNT">计数</option>
						<option value="DISTINCT">计数（投影）</option>
						<option value="SUM">求和</option>
						<option value="AVG">平均</option>
						<option value="MAX">最大值</option>
						<option value="MIN">最小值</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
	<div id="type2" style="display:none">
		<table id="table" class="table table-bordered table-striped" style="font-size:12px;width:auto;margin-top:20px" nowrap="nowrap">
			<thead id="thead">
				<tr id="theadTr1">
					<td></td>
					<td><a href="javascript:void(0)" onclick="delHeaderItem(this)">-</a><input value="表头" /><a href="javascript:void(0)" onclick="addHeaderItem(this)">+</a></td>
				</tr>
			</thead>
			<tbody id="tbody">
				<tr id="tbodyTr1">
					<td style="width:70px">分组字段（A）<br/><input id="group" value=""/></td>
					<td><input value=""/></td>
				</tr>
				<tr id="tbodyTr2">
					<td style="width:70px">统计字段</td>
					<td><input value="" /></td>
				</tr>
			</tbody>
		</table>
		<hr/> 
		<table class="MessageBox" style="width:340px"> 
		<tbody><tr>  
		<td class="msg info">
		<div class="" style="">函数公式：
		<br/>
		=SUM(A1,A2,A3,...) 行求和
		<br/>
		=AVG(A1,A2,A3,...) 行平均值
		<br/>
		=MAX(A1,A2,A3,...) 行最大值
		<br/>
		=MIN(A1,A2,A3,...) 行最小值
		<br/>
		<b>针对于统计字段：</b>
		<br/>
		=SUM 列求和
		<br/>
		=AVG 列平均
		<br/>
		=MAX 列最大
		<br/>
		=MIN 列最小
		</div> 
		</td> 
		</tr> 
		</tbody>
		</table>
		<img src="demo.jpg" />
	</div>
	
<div class="modal fade" id="configDiv" style="display:none;">
  <div class="modal-dialog"  style="width:600px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">详细设置</h4>
      </div>
      <div class="modal-body" style="font-size:12px;">
        <form id="form1" name="form1" method="post">
			<table  style="width:100%;font-size:12px" >
					<tr>
						<td>
							保留位数：
						</td>
						<td>
							<input type="text"  id="fixed" name="fixed"  class="BigInput"/>
						</td>
					</tr>
			</table>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="ok();">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>