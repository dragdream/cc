<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String templateId = request.getParameter("templateId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script src="<%=contextPath %>/common/js/workflowUtils.js"></script>
<script>
var templateId = "<%=templateId%>";
var opts = [];
var tplName;
function doInit(){
	//获取模版和模版项
	var template = tools.requestJsonRs(contextPath+"/report/getTemplate.action?templateId="+templateId).rtData;
	var templateItems = tools.requestJsonRs(contextPath+"/report/getTemplateItems.action?templateId="+templateId).rtData;
	tplName = template.tplName;
	window.statCols = [];//统计列
	
	//模版项
	
	$("#sItem1,#sItem2,#sItem3,#sItem4,#sItem5").append("<option value=''></option>");
	for(var i=0;i<templateItems.length;i++){
		var item = templateItems[i];
		var opt = {
			field:templateItems[i].item,
			title:templateItems[i].defName,
			width:templateItems[i].width,
			sortable:true
		};
		
		$("#sItem1,#sItem2,#sItem3,#sItem4,#sItem5").append("<option value='"+item.item+"'>"+item.defName+"</option>");
		
		var colModel = item.colModel;
		
		if(colModel=="" || !colModel){
			colModel = {};
		}else{
			colModel = eval("("+colModel+")");
		}
		
		var conditons = colModel.conditons;
		if(!conditons){
			conditons = [];
		}
		opt["conditons"] = conditons;
		opt["colModel"] = colModel;
		
		//是否为统计列
		if(colModel.cal!=undefined && colModel.cal!="0"){
			statCols.push(item);
		}
		
		if(conditons.length!=0){
			opt.formatter=function(data,row,index){
				var conditons0 = this.conditons;
				var cond = false;
				for(var i=0;i<conditons0.length;i++){
					cond = conditons0[i];
					if(cond.l=="或"){
						if(panduan(data.replace(this.colModel.fixName,""),cond.v1,cond.c1) || panduan(data,cond.v2,cond.c2)){
							if(cond.content!=""){
								return "<span style='color:"+cond.color+"'>"+cond.content+"</span>";
							}
							return "<span style='color:"+cond.color+"'>"+data+"</span>";
						}
					}else if(cond.l=="并"){
						if(panduan(data.replace(this.colModel.fixName,""),cond.v1,cond.c1) && panduan(data,cond.v2,cond.c2)){
							if(cond.content!=""){
								return "<span style='color:"+cond.color+"'>"+cond.content+"</span>";
							}
							return "<span style='color:"+cond.color+"'>"+data+"</span>";
						}
					}
				}
				return data;
			}
		}
		opts.push(opt);
	}
	if(template.pageSize==0){
		template.pageSize = 1000000;
	}
	window.pageSize = template.pageSize;
	
	var datagridOpt = {
			url:contextPath+'/report/reportDatas.action?templateId='+templateId,
			pagination:true,
			singleSelect:true,
			pageSize:template.pageSize,
			pageList:[template.pageSize],
			remoteSort:false,
			striped: true,
			border: false,
			toolbar:'#toolbar',//工具条对象
			checkbox:true,
			fitColumns:false,//列是否进行自动宽度适应
			columns:[opts]
		};
	
	//获取分组项目
	if(template.groupBy!="" && template.groupBy!="null" && template.groupBy!=undefined){
		datagridOpt["view"] = groupview;
		datagridOpt["groupField"] = template.groupBy;
		datagridOpt["groupFormatter"] = function(value,rows){
			return value + ' 共' + rows.length + '项';
		};
	}
	
	$('#datagrid').datagrid(datagridOpt);
	
	window.groupBy = template.groupBy;//分组
	
}

function panduan(left,right,oper){
	if(oper=="小于"){
		if(!isNaN(Number(left)) && !isNaN(Number(right))){
			return Number(left)<Number(right);
		}
		return left<right;
	}else if(oper=="大于"){
		if(!isNaN(Number(left)) && !isNaN(Number(right))){
			return Number(left)>Number(right);
		}
		return left>right;
	}else if(oper=="等于"){
		if(!isNaN(Number(left)) && !isNaN(Number(right))){
			return Number(left)==Number(right);
		}
		return left==right;
	}else if(oper=="大于等于"){
		if(!isNaN(Number(left)) && !isNaN(Number(right))){
			return Number(left)>=Number(right);
		}
		return left>=right;
	}else if(oper=="小于等于"){
		if(!isNaN(Number(left)) && !isNaN(Number(right))){
			return Number(left)<=Number(right);
		}
		return left<=right;
	}else if(oper=="包含"){
		return left.indexOf(right)!=-1;
	}else if(oper=="开始于"){
		return left.StartWith(right);
	}else if(oper=="结束于"){
		return left.EndWith(right);
	}else if(oper=="不包含"){
		return left.indexOf(right)==-1;
	}
}

String.prototype.EndWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 }

 String.prototype.StartWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 }
 
/**
 * 图形统计
 */
function genGraphics(){
	//获取分组数据
	var rows = $("#datagrid").datagrid("getRows");
	window.rows = [];
	for(var i=0;i<rows.length;i++){
		if(rows[i].GROUPFLAG){
			var rowData = {};
			for(var key in rows[i]){
				rowData[key] = rows[i][key];
				if(key!=window.groupBy && rowData[key]!=undefined){
					for(var j=0;j<window.statCols.length;j++){
						if(window.statCols[j].item==key){//过滤字符串
							var colModel = window.statCols[j].colModel;
							if(colModel=="" || !colModel){
								colModel = {};
							}else{
								colModel = eval("("+colModel+")");
							}
							if(!rowData[key]){
								rowData[key] = "";
							}else{
								rowData[key] = xParseNumber((rowData[key]+"").replace(colModel.fixName,""));
							}
							break;
						}
					}
				}
			}
			
			//过滤字符串为数字
			window.rows.push(rowData);
		}
	}
	
	openFullDialog(contextPath+"/system/subsys/report/flow_report_gen_graphics.jsp","图形报表");
}

function doSearch(){
	var para = tools.formToJson($("#searchTable"));
	$("#datagrid").datagrid("reload",para);
	$("#searchDiv").modal("hide");
}

function exportExcel(){
	
	var titleFields = [];
	var titleDescFields = [];
	
	for(var i=0;i<opts.length;i++){
		var opt = opts[i];
		titleFields.push(opt.field);
		titleDescFields.push(opt.title);
	}
	
	$("#frame0").attr("src",contextPath+"/report/exportExcel.action?templateId="+templateId+"&titleFields="+encodeURI(titleFields.join(","))+"&titleDescFields="+encodeURI(titleDescFields.join(","))
			+"&reportName="+encodeURI(tplName)+"&rows="+pageSize+"&page=1");
}
</script>
</head>
<body onload="doInit()" >
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1"><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;报表查看</span>
				</td>
				<td align=right>
					<button class="btn btn-default" onclick="window.location = 'flow_report_list.jsp'">返回</button>
					<button class="btn btn-warning" onclick="genGraphics()">图形统计</button>
					<button class="btn btn-warning" onclick="exportExcel()">导出Excel</button>
					<button class="btn btn-success" onclick="$('#searchDiv').modal('show')">高级查询</button>
				</td>
			</tr>
		</table>
	</div>
	<br/>
</div>

<div class="modal fade" id="searchDiv">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">高级查询</h4>
      </div>
      <div class="modal-body" style="font-size:12px">
		<table style="font-size:12px" id="searchTable">
			<tr>
				<td>
					<select id="sItem1" name="sItem1" class="BigSelect">
						
					</select>
				</td>
				<td>
					<select id="sCdt1" name="sCdt1" class="BigSelect">
						<option value="等于">等于</option>
						<option value="不等于">不等于</option>
						<option value="包含">包含</option>
						<option value="大于">大于</option>
						<option value="小于">小于</option>
						<option value="大于等于">大于等于</option>
						<option value="小于等于">小于等于</option>
					</select>
				</td>
				<td>
					<input id="sTxt1" name="sTxt1" type="text" class="BigInput" />
				</td>
			</tr>
			<tr>
				<td>
					<select id="sItem2" name="sItem2" class="BigSelect">
						
					</select>
				</td>
				<td>
					<select id="sCdt2" name="sCdt2" class="BigSelect">
						<option value="等于">等于</option>
						<option value="不等于">不等于</option>
						<option value="包含">包含</option>
						<option value="大于">大于</option>
						<option value="小于">小于</option>
						<option value="大于等于">大于等于</option>
						<option value="小于等于">小于等于</option>
					</select>
				</td>
				<td>
					<input id="sTxt2" name="sTxt2" type="text" class="BigInput" />
				</td>
			</tr>
			<tr>
				<td>
					<select id="sItem3" name="sItem3" class="BigSelect">
						
					</select>
				</td>
				<td>
					<select id="sCdt3" name="sCdt3" class="BigSelect">
						<option value="等于">等于</option>
						<option value="不等于">不等于</option>
						<option value="包含">包含</option>
						<option value="大于">大于</option>
						<option value="小于">小于</option>
						<option value="大于等于">大于等于</option>
						<option value="小于等于">小于等于</option>
					</select>
				</td>
				<td>
					<input id="sTxt3" name="sTxt3" type="text" class="BigInput" />
				</td>
			</tr>
			<tr>
				<td>
					<select id="sItem4" name="sItem4" class="BigSelect">
						
					</select>
				</td>
				<td>
					<select id="sCdt4" name="sCdt4" class="BigSelect">
						<option value="等于">等于</option>
						<option value="不等于">不等于</option>
						<option value="包含">包含</option>
						<option value="大于">大于</option>
						<option value="小于">小于</option>
						<option value="大于等于">大于等于</option>
						<option value="小于等于">小于等于</option>
					</select>
				</td>
				<td>
					<input id="sTxt4" name="sTxt4" type="text" class="BigInput" />
				</td>
			</tr>
			<tr>
				<td>
					<select id="sItem5" name="sItem5" class="BigSelect">
						
					</select>
				</td>
				<td>
					<select id="sCdt5" name="sCdt5" class="BigSelect">
						<option value="等于">等于</option>
						<option value="不等于">不等于</option>
						<option value="包含">包含</option>
						<option value="大于">大于</option>
						<option value="小于">小于</option>
						<option value="大于等于">大于等于</option>
						<option value="小于等于">小于等于</option>
					</select>
				</td>
				<td>
					<input id="sTxt5" name="sTxt5" type="text" class="BigInput" />
				</td>
			</tr>
		</table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="doSearch()">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<iframe id="frame0" style="display:none"></iframe>
</body>
</html>