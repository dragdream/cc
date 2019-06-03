<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String evalTemplateId = request.getParameter("evalTemplateId")==null?"0":request.getParameter("evalTemplateId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var sid = "<%=sid%>";
var evalTemplateId = "<%=evalTemplateId%>";
function doInit(){
	getScoreItemType();
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeEvalScoringDesignController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}

function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeEvalScoringDesignController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}

function getScoreItemType(){
	var url = contextPath+'/TeeEvalScoringItemController/datagrid.action'
	var json = tools.requestJsonRs(url,{page:1,pageSize:10000});
	var html="";
	for(var i=0;i<json.rows.length;i++){
		html+="<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>";
	}
	$("#scoreItemTypeId").html($("#scoreItemTypeId").html()+html);
}

</script>
</head>
<body onload="doInit();">
<form id="form1" name="form1">
	<table class='TableBlock' style='width:100%;'>
		<tr class='TableData' align='left'>
			<td width="180px">
				评分类型：
			</td>
			<td>
				<select id='scoreItemTypeId' name='scoreItemTypeId'>
					
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="80px">
				权重<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input type="text" id="weight" name="weight" required style="width:80px"class="BigInput easyui-validatebox" validType="intege[]"/>%
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="80px">
				评分人<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
					<textarea readonly id='personNames' name='personNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
					<input id='persons' name='persons' class="BigInput" type='hidden'/>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['persons','personNames'])">选择</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('persons','personNames')">清空</a>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="80px">
				评分项<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				
			</td>
		</tr>
	</table>
		<input type='hidden' id='sid' name='sid' value='<%= sid%>'/>
		<input type='hidden' id='evalTemplateId' name='evalTemplateId' value='<%= evalTemplateId%>'/>
</form>
</body>
</html>