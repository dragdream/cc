<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<META content="IE=11.0000" http-equiv="X-UA-Compatible">
<META http-equiv="X-UA-Compatible" content="IE=Edge">
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<!-- jQuery库 -->
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.core.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.widget.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.mouse.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.draggable.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.droppable.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.resizable.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.selectable.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ui.sortable.js" type="text/javascript"></SCRIPT>
<SCRIPT src="<%=contextPath %>/system/frame/3/个人桌面_files/jquery.ux.slidebox.js" type="text/javascript"></SCRIPT>
<TITLE></TITLE>
<SCRIPT type="text/javascript">
	var modules;
	$(function() {
		var json = tools.requestJsonRs(contextPath+"/mobileModule/list.action");
		var list = json.rtData;
		var html = [];
		for(var i=0;i<list.length;i++){
			html.push("<li class=\"ui-state-default\" sid=\""+list[i].sid+"\">");
			html.push("<div class=\"fl\"><img src=\"icons/"+list[i].pic+"\" class=\"img\" /></div>");
			html.push("<div class=\"fl\">"+list[i].appName+"</div>");
			html.push("<div class=\"fr\">");
			if(list[i].sid>=100){
				html.push("<button class=\"btn btn-danger\" onclick=\"del("+list[i].sid+")\">删除</button>");
			}
			html.push("&nbsp;&nbsp;<button class=\"btn btn-default\" onclick=\"showDialog("+list[i].sid+")\">管理</button>");
			html.push("</div>");
			html.push("</li>");
		  	
		}
		
		$("#sortable").append(html.join(""));
		
		$( "#sortable" ).sortable({
	      placeholder: "ui-state-highlight",
	      stop: function( event, ui ) {
	    	  var ids = [];
	    	  $("#sortable li").each(function(i,obj){
	    		  ids.push(obj.getAttribute("sid"));
	    	  });
	    	  tools.requestJsonRs(contextPath+"/mobileModule/sorting.action",{ids:ids.join(",")});
	      }
	    });
	    $( "#sortable" ).disableSelection();
	});
	
	function showDialog(id){
		$("#form")[0].reset();
		if(id){
			$("#myModalLabel").html("编辑");
			$("#sid").attr("readonly","");
			$("#isEdit").val(1);
			var json = tools.requestJsonRs(contextPath+"/mobileModule/get.action",{sid:id});
			bindJsonObj2Cntrl(json.rtData);
		}else{
			$("#myModalLabel").html("新增");
			$("#sid").removeAttr("readonly");
			$("#isEdit").val(0);
		}
		$("#myModal").modal("show");
	}
	
	function addOrUpdate(){
		if($("#sid").val()==""){
			$("#sid").focus();
			alert("ID标识不能为空");
			return;
		}
		
		if(($("#isEdit").val()+"")=="0"){
			var num = parseInt($("#sid").val());
			if(num<100 || num>255){
				$("#sid").focus();
				alert("ID标识范围必须在100-255之间");
				return;
			}
		}
		
		
		var para = tools.formToJson($("#form"));
		var json = tools.requestJsonRs(contextPath+"/mobileModule/addOrUpdate.action",para);
		if(json.rtState){
			window.location.reload();
		}else{
			alert("保存失败");
		}
	}
	
	function del(sid){
		if(window.confirm("确认要删除该模块吗？")){
			tools.requestJsonRs(contextPath+"/mobileModule/del.action",{sid:sid});
			window.location.reload();
		}
	}
	
</SCRIPT>
<style>
#sortable { list-style-type: none; margin: 0; padding: 0;  }
#sortable li { margin: 0 5px 5px 5px; padding: 5px; font-size: 14px; height: 1.5em; }
html>body #sortable li { height:40px; line-height: 40px }
.ui-state-highlight { height:40px; line-height: 40px }
.ui-state-default{background:#f0f0f0;cursor:move}
.img{height:35px;width:35px;margin-right:10px}
table{border-collapse:collapse}
td{border:1px solid #c5c5c5}
.fl{float:left;}
.fr{float:right}
</style>
</HEAD>
<BODY style="background: #FFF;">
<br/><br/>
<center>
<table style="width:800px;font-size:14px">
	<tr>
		<td style="padding:10px"><b>功能模块</b>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="showDialog()">自定义模块</a></td>
	</tr>
	<tr>
		<td>
			<ul id="sortable" style="padding:10px">
			  
			</ul>
		</td>
	</tr>
</table>
</center>


<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
        <form id="form">
        	<div class="form-group">
			    <label for="sid">ID标识（100-255）：</label>
			    <input type="text" class="form-control easyui-validatebox" required id="sid" name="sid" placeholder="">
			</div>
       		<div class="form-group">
			    <label for="appName">APP名称：</label>
			    <input type="text" class="form-control" id="appName" name="appName" placeholder="">
			</div>
			<div class="form-group">
			    <label for="url">模块地址：</label>
			    <input type="text" class="form-control" id="url" name="url" placeholder="">
			</div>
			<div class="form-group">
			    <label for="pic">应用图标（图标目录/system/mobile/icons）：</label>
			    <input type="text" class="form-control" id="pic" name="pic" placeholder="">
			</div>
			<div class="form-group">
			    <label for="viewId">角标视图ID：</label>
			    <input type="text" class="form-control" id="viewId" name="viewId" placeholder="">
			</div>
        	<div class="form-group">
			    <label for="desc">模块描述：</label>
			    <textarea id="desc" name="desc" class="form-control" rows="3"></textarea>
			</div>
			<fieldset>
				<legend>查看权限</legend>
				<div class="form-group">
				    <label for="deptPriv">部门：</label>
				    <textarea id="deptPrivDesc" name="deptPrivDesc"  class="form-control" rows="3" readonly></textarea>
				    <input type="hidden" id="deptPriv"  name="deptPriv"/>
				    <a href="javascript:void(0)" onclick="selectDept(['deptPriv','deptPrivDesc'])">选择</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="clearData('deptPriv','deptPrivDesc')">清空</a>
				</div>
				<div class="form-group">
				    <label for="userPriv">人员：</label>
				    <textarea id="userPrivDesc"  name="userPrivDesc" class="form-control" rows="3" readonly></textarea>
				    <input type="hidden" id="userPriv"  name="userPriv"/>
				    <a href="javascript:void(0)" onclick="selectUser(['userPriv','userPrivDesc'])">选择</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="clearData('userPriv','userPrivDesc')">清空</a>
				</div>
				<div class="form-group">
				    <label for="rolePriv">角色：</label>
				    <textarea id="rolePrivDesc" class="form-control" name="rolePrivDesc" rows="3" readonly></textarea>
				    <input type="hidden" id="rolePriv"  name="rolePriv" />
				   	<a href="javascript:void(0)" onclick="selectRole(['rolePriv','rolePrivDesc'])">选择</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="clearData('rolePriv','rolePrivDesc')">清空</a>
				</div>
			</fieldset> 
			<fieldset>
				<legend>管理权限</legend>
				<div class="form-group">
				    <label for="managePriv">人员：</label>
				    <textarea id="managePrivDesc" class="form-control" name="managePrivDesc" rows="3" readonly></textarea>
				    <input type="hidden" id="managePriv"  name="managePriv" />
				   	<a href="javascript:void(0)" onclick="selectUser(['managePriv','managePrivDesc'])">选择</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="clearData('managePriv','managePrivDesc')">清空</a>
				</div>
			</fieldset>
			<input type="hidden" name="isEdit" id="isEdit" value="0"/>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="addOrUpdate()">确定</button>
      </div>
    </div>
  </div>
</div>
<br/><br/>
</BODY>
</HTML>
