<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=request.getContextPath() %>/system/subsys/salary/js/account.js"></script>


<script>

function doInit(){
	getAllAccountTemp();
}

function getAllAccountTemp(){
	var prcs = getAllAccount();
	$("#tbody").empty();
	if(prcs && prcs.length > 0){
		var table = "";
		for(var i = 0 ; i<prcs.length ; i++){
			var prc = prcs[i];
			var sid = prc.sid;
			table = table + "<tr>"
				+ "<td class='TableData'>" + prc.accountNo + "</td>"
				+ "<td class='TableData'>" + prc.accountName + "</td>"
				+ "<td class='TableData'>"
						+"<a role='menuitem' tabindex='-1' href='javascript:void(0)' itemName='" + prc.accountName + "' onclick='toSetItem("+sid+" , this)'>工资项设定</a>"
						+"&nbsp;&nbsp;<a role='menuitem' tabindex='-1' href='javascript:void(0)'  itemName='" + prc.accountName + "' onclick='addPersons("+sid+" , this)'>帐套成员</a>"
						+"&nbsp;&nbsp;<a role='menuitem' tabindex='-1' href='javascript:void(0)' onclick='toAddOrUpdate("+sid+")'>编辑</a>"
						+"&nbsp;&nbsp;<a role='menuitem' tabindex='-1'  href='javascript:void(0)' onclick='deleteById("+sid+")'>删除</a>"
					+"</td>"
				+"</tr>";
		}
		$("#tbody").append(table);
	}
}

function toAddOrUpdate(sid){
	$('#productTypeModal').modal('toggle');
	if(sid){
		var account = getAccountById(sid);
		bindJsonObj2Cntrl(account);
	}
}
/**
 * 新建或者更新
 */
function addOrUpdate(){
	if(checkForm()){
		var url = contextPath+"/teeSalAccountController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var json = tools.requestJsonRs(url , para);
		if(json.rtState){
			$.jBox.tip("保存成功！", 'info' , {timeout:1500});
			$('#productTypeModal').modal('toggle');
			getAllAccountTemp();
		}else{
			alert(json.rtMsg);
			return;
		}
	}
}
function checkForm(){
	return $("#form1").form("validate");
}
/*
 * 进入工资想设定
 */
function toSetItem(sid , obj){
	var itemName = $(obj).attr("itemName");
	window.location = contextPath+"/system/subsys/salary/account/manager.jsp?accountId=" +sid  + "&accountName=" + itemName;
}

/*
 * 成员设置
 */
function addPersons(sid , obj){
	var itemName = $(obj).attr("itemName");
	window.location = contextPath+"/system/subsys/salary/account/settingAccount.jsp?accountId=" +sid  + "&accountName=" + itemName;
}
/**
 * 删除byId
 */
function deleteById(sid){
  $.jBox.confirm("确定要删除该工资账套，删除后所有相关数据将不可恢复？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/teeSalAccountController/deleteById.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("删除成功！", "info", {timeout: 1800});
				getAllAccountTemp();
			}else{
				alert(json.rtMsg);
			}
		}
	});
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
<div class="base_layout_top">
	<span class="easyui_h1">工资帐套管理</span>
</div>
<div class="base_layout_center">
	<div align="center">
		 	<input type="button" class="btn btn-primary" data-toggle="modal" data-target="#productTypeModal" value="新建工资账套"  onclick="toAddOrUpdate(0,0);">
		   	<!-- Modal -->
		   	<form id="form1" name="form1" method="post">
				<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog"  style="width:500px;">
				    <div class="modal-content">
				    	 <div class="modal-header">
				        	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				       		<h4 class="modal-title" id="myModalLabel">新建工资账套</h4>
				   	
				      	</div> 
				      <div class="modal-body">
				          <table  class="TableBlock" width="100%" align="center">
							    <tr>
							        <td nowrap class="TableData" width="100"> 账套名称：</td>
							        <td class="TableData">
							      	    <input type="text" id="accountName" name="accountName" size="20" maxlength="200"  class="BigInput easyui-validatebox" required="true" value="">
							        </td>
							    </tr>
							    <tr>
							        <td nowrap class="TableData" width="100"> 账套编号：</td>
							        <td class="TableData">
							      	    <input type="text" id="accountNo" name="accountNo" size="20" maxlength="200"  class="BigInput easyui-validatebox" required="true" value="">
							        </td>
							    </tr>
							     <tr>
							        <td nowrap class="TableData" width="100"> 排序号：</td>
							        <td class="TableData" align="left">
							      	    <input type="text" id="accountSort" name="accountSort" size="20" maxlength="9"  class="BigInput easyui-validatebox" value="0" required="true" validType ='integeZero[]'>
							        </td>
							    </tr>
				
							    <tr >
							    	<td colspan="2"  >
							    	 <div class="" align="center">
							    	 	<input id="sid" name="sid" type="hidden" value="0">

				       				 	<button type="button" class="btn btn-primary" onclick="addOrUpdate();">保存</button>
							    		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							    	</div>
							    	</td>
							    </tr>
						 </table>
				      </div>
		
				    </div><!-- /.modal-content -->
				  </div><!-- /.modal-dialog -->
				</div>
			 </form>
		</div> 
	 	<div style="padding-top:5px;">
			<table class='TableBlock' width="600px" align="center">
				<tr class='TableHeader'>
						<td> 账套编号</td>
						<td> 账套名称</td>
						<td> 操作</td>
					</tr>
				<tbody id = 'tbody'>
					
				</tbody>
			</table>
		</div> 
</div>
</body>
</html>