<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String provinceCode = request.getParameter("provinceCode");
String cityCodeStr = request.getParameter("cityCodeStr");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>县管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/setting/chinaCity/js/chinaCity.js"></script>
<script type="text/javascript">

function doInit(){
	getCityTitle('<%=provinceCode%>','<%=cityCodeStr%>');
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/chinaCityController/getCountyByCityCode.action?cityCodeStr=<%=cityCodeStr%>",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'cityCode',title:'城市编号',width:50},
			{field:'cityName',title:'城市名称',width:100},
			{field:'createTimeStr',title:'创建时间',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'2',title:'操作',width:50,formatter:function(value, rowData, rowIndex){
				var str = "&nbsp;&nbsp;<a href='#' onclick='addOrUpdateInfo("+rowData.sid+")'>修改</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}




/**
 * 新建信息
 */
 function addOrUpdateInfo(sid){
   var title = "新建县信息";
   if(sid>0){
	  title = "编辑县信息";
   }
   var url = contextPath + "/system/subsys/crm/setting/chinaCity/addOrUpdateCounty.jsp?cityCodeStr=<%=cityCodeStr%>&sid=" + sid;
   bsWindow(url ,title,{width:"500",height:"200",buttons:
 		[
 		 {name:"保存",classStyle:"btn btn-primary"},
 	 	 {name:"关闭",classStyle:"btn btn-primary"}
 		 ]
 		,submit:function(v,h){
 		var cw = h[0].contentWindow;
 		if(v=="保存"){
 			cw.doSaveOrUpdate(function(){
 			  $.jBox.tip("保存成功！", "info", {timeout: 1800});
 			  datagrid.datagrid('reload');
 			  BSWINDOW.modal("hide");
 			});
 		}else if(v=="关闭"){
 			return true;
 		}
 	}});
 }


/**
 * 单个信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		alert("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除信息
 */
function deleteObjFunc(ids){
  $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/chinaCityController/deleteCountyObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("删除成功！", "info", {timeout: 1800});
				datagrid.datagrid('reload');
			}
		}
	});
}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
}

function getCityTitle(provinceCode,cityCode){
	var provinceObj = getInfoByCityCode(provinceCode);
	var cityObj = getInfoByCityCode(cityCode);
	
	if(provinceObj&&cityObj){
		var spanStr = "<span class='glyphicon glyphicon-chevron-right'></span>";
		var allProvinceStr = "<a href='#;' onclick='backProvinceFunc();'>全省</a>" + spanStr;
		allProvinceStr += "<a href='#;' onclick='backCityFunc(" + provinceObj.cityCode + ");'>" + provinceObj.cityName +  "</a>";
		allProvinceStr +=  spanStr + "<span style='color:#000000'>" + cityObj.cityName + "</span>";
		$("#cityTitle").html(allProvinceStr);
	}
}
function backProvinceFunc(){
	var url = contextPath + "/system/subsys/crm/setting/chinaCity/provinceManage.jsp";
	location.href = url;
}
function backCityFunc(cityCode){
	var url = contextPath + "/system/subsys/crm/setting/chinaCity/cityManage.jsp?provinceCode=" + cityCode;
	location.href = url;
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;<span id="cityTitle"></span></b>
		</div>
		<div style="text-align:left;">
			<button class="btn btn-primary btn-xs" onclick="addOrUpdateInfo(0)">添加县</button>&nbsp;&nbsp;
			<button class="btn btn-danger btn-xs" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			<button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
		<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title" id="myModalLabel">查询条件</h4>
					</div>
					<div class="modal-body">
						<table class="TableBlock" width="80%"  style="text-align:left;" align="center">
							<tr>
								<td class="TableData" >县编号：</td>
								<td class="TableData">
									<input type='text' class="BigInput" id='cityCode' name='cityCode' style="width:180px;">
								</td>
							</tr>
							<tr>
								<td class="TableData">县名称：</td>
								<td class="TableData">
									<input type='text' class="BigInput" id='cityName' name='cityName' style="width:180px;"/>
								</td>
							</tr>
							<tr>
								<td colspan='4' align='center'>
									<input type="reset" class="btn btn-primary" value="清空">
									<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	</div>
</body>
</html>