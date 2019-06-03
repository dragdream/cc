<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String sid = request.getParameter("sid");
	String type = request.getParameter("type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<style type="text/css">
	.title{
		positon:relative;
		width:200px;
		height:26px;
		line-height:26px;
		margin:5px 0;
		display:block;
		padding:0 5px;
		background:#cdcdcd;
		color:black;
		text-align:center;
	}
	.columnStyle{
		min-height:450px;
		width:210px;
		float:left;
		border:1px solid #CCCCCC;
		margin:0 10px;
	}
	
	 #sortable, #sortable1, #sortable2,#sortable3,#sortable4 {
	    border: 1px solid #eee;
	    width: 210px;
	    min-height: 450px;
	    list-style-type: none;
	    margin: 0;
	    padding: 5px 0 0 0;
	    float: left;
	    margin-right: 10px;
	  }
	  #sortable li,#sortable1 li, #sortable2 li ,#sortable3 li,#sortable4 li{
	    margin: 0 5px 5px 5px;
	    padding: 5px;
	    font-size:12px;
	    width: 190px;
	    cursor:pointer;
	  }
</style>
<script>
var sid = <%=sid%>;
var type = <%=type%>;
var ids="";
function doInit(){
	//获取当前站点些模板列表
	if(sid>0){
		var url = "<%=contextPath%>/teePortalTemplateController/getPortalTemplate.action";
		var jsonRs = tools.requestJsonRs(url,{"sid":sid});
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			bindJsonObj2Cntrl(data);
			$("#sid").val(sid);
			if(parseInt(data.cols)>0){
				setCols(data.cols);
				initCols(data.portalModel);
				$("#sortable").find("li").each(function(i,item){
					if(isExist($(this).attr('id'))){
						$(this).remove();
					}
				});
			}else{
				setCols(1);
			}
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function getModuleList(){
	var urls = contextPath+"/portlet/datagrid.action";
	var json = tools.requestJsonRs(urls);
	if(json.total>0){
		var data = json.rows;
		var html = "<span class='title'>模块候选区</span><ul id='sortable' class=\"connectedSortable\">";
		for(var i=0;i<data.length;i++){
			html+="<li class='ui-state-default' id='"+data[i].sid+"'>"+data[i].modelTitle+"</li>";
		}
		html+="</ul>";
		$("#moduleList").html(html);
	}
}
function commit(){
	if($("#cols").val()=="0"){
		alert("请选择模板列数");
		return;
	}
	if(sid!=null){//更新
		var url = contextPath+"/teePortalTemplateController/editPortalTemplate.action";
		var portalModel = "[";
		$(".columnStyle").find("ul").each(function(i, item) {
			portalModel+="[";
			$(item).find("li").each(function(i,item){
				portalModel+="{'id':'"+$(this).attr('id')+"','modelTitle':'"+$(this).text()+"'},";
			});
			if(portalModel.length>2){
				portalModel = portalModel.substring(0, portalModel.length-1);
			}
			portalModel+="],";
		});
		if(portalModel.length>2){
			portalModel = portalModel.substring(0, portalModel.length-1);
		}
		portalModel+="]";
		$("#portalModel").val(portalModel);
		var param = tools.formToJson($("#form"));
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			$.jBox.tip("保存成功","success");
		}
	}
}

function setCols(value){
	var html="";
	getModuleList();
	if(value=="1" || value=="2" || value=="3"){
		html="<div class='columnStyle'><span class='title'>区域1</span><ul id='sortable1' class=\"connectedSortable\"></ul></div><div class='columnStyle'><span class='title'>区域2</span><ul id='sortable2' class=\"connectedSortable\"></ul></div>";
		$("#colsList").html(html);
		$("#sortable,#sortable1,#sortable2").sortable({
		      connectWith: ".connectedSortable"
		    }).disableSelection();
	}else if(value=="4" || value=="5" || value=="6"){
		html="<div class='columnStyle'><span class='title'>区域1</span><ul id='sortable1' class=\"connectedSortable\"></ul></div><div class='columnStyle'><span class='title'>区域2</span><ul id='sortable2' class=\"connectedSortable\"></ul></div><div class='columnStyle'><span class='title'>区域3</span><ul id='sortable3' class=\"connectedSortable\"></ul></div>";
		$("#colsList").html(html);
		$("#sortable,#sortable1, #sortable2,#sortable3").sortable({
		      connectWith: ".connectedSortable"
		    }).disableSelection();
	}
}


function initCols(portalModel){
	var json = eval('(' + portalModel + ')'); 
	for(var i = 0 ;i<json.length;i++){
		for(var j = 0 ;j<json[i].length;j++){
			var li = $("<li class='ui-state-default' id='"+json[i][j].id+"'>"+json[i][j].modelTitle+"</li>");
			$("#sortable"+(i+1)).append(li);
			ids+=json[i][j].id+",";
		}
	}
}

function isExist(id){
	var tems = ids;
	if(tems!=null && tems.length>0){
		tems = tems.substring(0,tems.length-1);
		var ts = tems.split(",");
		for(var i = 0 ;i<ts.length;i++){
			if(ts[i]==id){
				return true;
			}
		}
	}
	return false;
}

</script>
<style>
.Tabb td{
font-size:12px;
padding:10px;
}
</style>
</head>
<body onload="doInit();" style="margin:10px;">
&nbsp;&nbsp;
<input type='button' class='btn btn-default' value='返回' onclick='history.go(-1)'/>&nbsp;
<input type='button' class='btn btn-success' value='保存' onclick='commit();'/>
<form id="form">
<table class="Tabb" style="width:500px;text-align:center">
	<tr>
		<td>
			<img alt="" src="1.png"><br/>
			<input type="radio" name="cols" value="1" onclick="setCols('1')"/>
		</td>
		<td>
			<img alt="" src="2.png"><br/>
			<input type="radio" name="cols"  value="2" onclick="setCols('2')"/>
		</td>
		<td>
			<img alt="" src="3.png"><br/>
			<input type="radio" name="cols" value="3"  onclick="setCols('3')"/>
		</td>
		<td>
			<img alt="" src="4.png"><br/>
			<input type="radio" name="cols" value="4" onclick="setCols('4')" />
		</td>
		<td>
			<img alt="" src="5.png"><br/>
			<input type="radio" name="cols" value="5" onclick="setCols('5')"/>
		</td>
		<td>
			<img alt="" src="6.png"><br/>
			<input type="radio" name="cols" value="6" onclick="setCols('6')"/>
		</td>
	</tr>
</table>


<table style="width:100%;font-size:12px;display:none;" class="TableBlock">
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center"><b>基础信息</b></td>
		</tr>
		<tr>
			<td nowrap class="TableData" >模板名称：</td>
			<td nowrap class="TableData" >
				<input type="text" class="BigInput" name="templateName" id="templateName"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" >排序号：</td>
			<td nowrap class="TableData" >
			<input type="text" class="BigInput" name="sortNo" id="sortNo"/>
			</td>
		</tr>
		  <tr >
   			 <td nowrap class="TableData"  >使用权限（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="deptPriv" id="deptPriv" value=""/>
       		 <textarea cols=60 name="deptPrivDesc" id="deptPrivDesc" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
       		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['deptPriv','deptPrivDesc'],'','1')">选择</a>
       		 <a href="javascript:void(0);" class="orgClear" onClick="clearData('deptPriv','deptPrivDesc')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" >使用权限（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="rolePriv" id="rolePriv" value="">
        <textarea cols=60 name="rolePrivDesc" id="rolePrivDesc" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['rolePriv','rolePrivDesc'],'','1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('rolePriv','rolePrivDesc')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">使用权限（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="userPriv" id="userPriv" value="">
        <textarea cols=60 name="userPrivDesc" id="userPrivDesc" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userPriv', 'userPrivDesc'],'' , '1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('userPriv','userPrivDesc')">清空</a>
    </td>
   </tr>
	</table>
	<table class="" style='width:100%;'>
		<tr class='' style='height:auto;'>
			<td>
				<div style='width:100%;min-height:450px;'>
					<div id='colsList' style='width:auto;float:left;'>
					</div>
					<div id='moduleList' style='min-height:450px;width:210px;float:left;border:1px solid #CCCCCC;;margin:0 10px;' >
						
					</div>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid==null?0:sid%>"/>
	<input type="hidden" id="portalModel" name="portalModel" />
</form>
</body>
</html>