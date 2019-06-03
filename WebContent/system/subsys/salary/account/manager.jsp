<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int accountId = TeeStringUtil.getInteger(
			request.getParameter("accountId"), 0);//账套
	String accountName = TeeStringUtil.getString(
			request.getParameter("accountName"), "");//账套名称
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>账套工资项设定</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">
var accountId = <%=accountId%>;
function doInit(){
	if(accountId > 0){
		$("#tbody").empty();
		var prcs = getItemListByAccountId(accountId);
		var tbody = "";
		for(var i = 0; i < prcs.length ; i++){
			var prc = prcs[i];
			var itemTypeDesc = "";
			if(prc.itemType == 1){
				itemTypeDesc = "计算项";
			}else if(prc.itemType == 0){
				itemTypeDesc = "输入项";
			}else if(prc.itemType == 2){
				itemTypeDesc = "提取项";
			}else if(prc.itemType == 3){
				itemTypeDesc = "SQL项";
			}
			var itemFlag = "√";
			if(prc.itemFlag == '1'){
				itemFlag = "";
			}
			var pIncome = "";;
			if(prc.pIncome==1){
				pIncome = "√";
			}else{
				pIncome = "";
			}
			
			tbody = tbody + "<tr>"
				+ "<td class='TableData' align='center'>" + prc.itemColumn + "</td>"
				+ "<td class='TableData'>" + prc.itemName + "</td>"
				+ "<td  class='TableData' align='center'>" + itemTypeDesc + "</td>"
				+ "<td  class='TableData' align='center'>" + prc.numberPoint + "</td>"
				+ "<td  class='TableData' align='center'>" + pIncome + "</td>"
				+ "<td  class='TableData' align='center'>" + itemFlag + "</td>"
				+ "<td class='TableData'>"
					+ "<input type='button' value='编辑' onclick='toAddOrUpdate("+prc.sid+")' class='btn btn-primary btn-xs'/>&nbsp;&nbsp;";
			if(prc.itemColumn!="finalPayAmount"){
				tbody = tbody + "<input type='button' value='删除' onclick='toDelete("+prc.sid+")' class='btn btn-danger btn-xs'/>";
			}
					
			tbody = tbody + "</td>"
				+ "</tr>";
		}
		$("#tbody").append(tbody);
	}
}

function toEdit(id,name){
    
    var typeName = encodeURIComponent(name);
    var url = "<%=contextPath%>/system/subsys/salary/insurance_para/sal_item/toEdit.jsp?typeName="+typeName+"&sid="+id;
    location.href = url;
}

function toDelete(id){
    var url = "<%=contextPath%>/teeSalItemController/deleteSalItem.action";
    var para =  {sid:id};
    if(confirm('确实要删除该工资项吗?')){    
        var jsonRs = tools.requestJsonRs(url,para);
        if(jsonRs.rtState){
            alert(jsonRs.rtMsg);
            location.reload();
        }
    }
}

function checkForm(){
    return $("#form1").form('validate'); 
}

function doSave(){
	if(checkForm()){
	    var url = "<%=contextPath%>/teeSalItemController/addOrUpdate.action";
	    var para =  tools.formToJson($("#form1"));
	    para["accountId"] = "<%=accountId%>";
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	        $.jBox.tip(jsonRs.rtMsg , 'info' , {timeout : 1500});
	        $('#productTypeModal').modal('toggle');
	        doInit();
	    }else{
	    	alert(jsonRs.rtMsg);
	    }
	}
}

function sel_change(input)
{

  if(form1.itemType.value=="1")
  {
	  
	  document.all("getTypeDiv").style.display="none";
	  document.all("bisViewDiv").style.display="none";
      document.all("formu").style.display="";
      document.form1.formula.value="";
      document.form1.formulaName.value="";
      
  }else if(form1.itemType.value=="0"){
	  
	  document.all("getTypeDiv").style.display="none";
	  document.all("bisViewDiv").style.display="none";
	  document.all("formu").style.display="";
      document.form1.formula.value="";
      document.form1.formulaName.value="";
      
  }else if(form1.itemType.value=="2"){
	  
	  document.all("getTypeDiv").style.display="";
	  document.all("bisViewDiv").style.display="none";
	  document.all("formu").style.display="none";
	  document.form1.formula.value="";
      document.form1.formulaName.value="";
	  
  }else if(form1.itemType.value=="3"){
	  
	  document.all("getTypeDiv").style.display="none";
	  document.all("bisViewDiv").style.display="";
	  document.all("formu").style.display="none";
	  document.form1.formula.value="";
      document.form1.formulaName.value="";
	  
  }

}

/**
 * 编辑
 */
function toAddOrUpdate(sid){
	if(sid > 0){
		$('#productTypeModal').modal('toggle');
		var url = "<%=contextPath%>/teeSalItemController/getSalItem.action";
			var para = {
				sid : sid
			};
			var jsonRs = tools.requestJsonRs(url, para);
			if (jsonRs.rtState) {
				var json = jsonRs.rtData;
				bindJsonObj2Cntrl(json);
				if (json.itemType == "1") {
					document.all("getTypeDiv").style.display = "none";
					document.all("bisViewDiv").style.display = "none";
					document.all("formu").style.display = "";
				} else if (json.itemType == "2") {
					document.all("getTypeDiv").style.display = "";
					document.all("bisViewDiv").style.display = "none";
					document.all("formu").style.display = "none";
				} else if (json.itemType == "3") {
					document.all("getTypeDiv").style.display = "none";
					document.all("bisViewDiv").style.display = "";
					document.all("formu").style.display = "none";
				} else if (json.itemType == "0") {
					document.all("getTypeDiv").style.display = "none";
					document.all("bisViewDiv").style.display = "none";
					document.all("formu").style.display = "";
				}
			}
		} else {
			//重置
			document.all("getTypeDiv").style.display = "none";
			document.all("bisViewDiv").style.display = "none";
			document.all("formu").style.display = "";
			$("#sid").val(0);
			$("#form1")[0].reset();
		}
	}

	function LoadWindow2() {
		var URL = contextPath
				+ "/system/subsys/salary/insurance_para/sal_item/formula_edit.jsp?accountId="
				+ accountId;
		//   myleft=(screen.availWidth-650)/2;
		//   window.open(URL,"formul_edit","height=400,width=650,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=150,left="+myleft+",resizable=yes");
		dialog(URL, 650, 400);
	}
</script>

</head>
<body onload="doInit();">
	<div class="base_layout_top">
		<span class="easyui_h1">【<%=accountName%>】工资项设定
		</span>
	</div>
	<div class="base_layout_center">
		<!-- Modal -->
		<form id="form1" name="form1" method="post">
			<div class="modal fade" id="productTypeModal" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 550px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">新建/更新工资项目</h4>

						</div>
						<div class="modal-body">
							<table align="center" width="60%" class="TableBlock">
								<tr>
									<td nowrap class="TableData">项目名称：<font style='color: red'>*</font></td>
									<td class="TableData" colspan="3"><input type="text"
										name="itemName" id="itemName" size="40" maxlength="300"
										class="BigInput easyui-validatebox" value="" required="true">
									</td>
								</tr>
								<tr>
									<td nowrap class="TableData">项目类型：</td>
									<td nowrap class="TableData"><select name="itemType"
										id="itemType" class="BigSelect" onChange="sel_change()">
											<option value="0">录入项</option>
											<option value="1">计算项</option>
<!-- 											<option value="2">提取项</option> -->
<!-- 											<option value="3">SQL项</option> -->
									</select></td>
								</tr>

								<tr id="bisViewDiv" style="display: none">
									<td nowrap class="TableData">视图ID：</td>
									<td nowrap class="TableData"><input type="text"
										class="BigInput" type name="bisViewId" id="bisViewId" /></td>
								</tr>

								<tr id="getTypeDiv" style="display: none">
									<td nowrap class="TableData">提取类型：</td>
									<td nowrap class="TableData"><select name="getType"
										id="getType" class="BigSelect">
											<optgroup label="考勤数据">
												<option value="1">当月请假工时</option>
												<option value="2">当月事假工时</option>
												<option value="3">当月病假工时</option>
												<option value="4">当月工伤假工时</option>
												<option value="5">当月婚嫁工时</option>
												<option value="6">当月丧假工时</option>
												<option value="7">当月产假工时</option>
												<option value="8">当月探亲假工时</option>
												<option value="9">当月公假工时</option>
												<option value="10">当月其他假工时</option>
												<option value="11">当月外出工时</option>
												<option value="12">当月出差工时</option>
												<option value="13">当月加班工时</option>
											</optgroup>
									</select></td>
								</tr>
								<tr>
									<td nowrap class="TableData">工资组成<br/>（参与纳税和五险一金）：</td>
									<td nowrap class="TableData"><select name="pIncome"
										id="pIncome" class="BigSelect">
											<option value="0">否</option>
											<option value="1">是</option>
									</select></td>
								</tr>
								<tr>
									<td nowrap class="TableData">是否显示：</td>
									<td nowrap class="TableData"><select name="itemFlag"
										id="itemFlag" class="BigSelect">
											<option value="0">是</option>
											<option value="1">否</option>
									</select></td>
								</tr>
<!-- 								<tr style="display: none"> -->
<!-- 									<td nowrap class="TableData">初始值：</td> -->
<!-- 									<td nowrap class="TableData"><input type="text" -->
<!-- 										name="defaultValue" class="BigInput easyui-validatebox " -->
<!-- 										size="8" maxlength="9" required="true" validType='number[]'></input> -->
<!-- 									</td> -->
<!-- 								</tr> -->
								<tr>
									<td nowrap class="TableData">小数位数：</td>
									<td nowrap class="TableData"><input type="text"
										name="numberPoint" class="BigInput easyui-validatebox "
										size="8" maxlength="9" required="true"
										validType='integeZero[]' value="2"></input></td>
								</tr>

								</tr>
								<tr id="formu" style="display: none">
									<td nowrap class="TableData">值或计算公式：</td>
									<td  class="TableData"><input type="hidden"
										name="formula" id="formula"> <textarea cols=37
											name="formulaName" id="formulaName" rows="4"
											class="BigTextarea" readonly wrap="yes"></textarea><br/><a href="javascript:void(0)"
										onClick="LoadWindow2()"
										title="编辑公式" >编辑公式</a></td>
								</tr>
							</table>
						</div>
						<div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        <button type="button" class="btn btn-primary" onclick="doSave();">保存</button>
					        <input type="hidden" name='sid' id='sid' value="0"></input>
					        <input type="hidden" name='sortNo' id='sortNo' value="0"></input>
					      </div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
		</form>
		<center>
			<input type="button" class="btn btn-default" value="返回"
				onclick="window.location='index.jsp';"> <input type="button"
				class="btn btn-primary" data-toggle="modal"
				data-target="#productTypeModal" value="新建工资项目"
				onclick="toAddOrUpdate(0);">
		</center>
		<br />
		<table align="center" width="95%" class="TableBlock">
			<tr class="TableHeader">
				<td nowrap align="center" width="60px"
					style="padding: 0px 0px 0px 0px;">编号</td>
				<td nowrap align="center" width="" style="padding: 0px 0px 0px 0px;">名称</td>
				<td nowrap align="center" width="80px"
					style="padding: 0px 0px 0px 0px;">项目类型</td>
				<td nowrap align="center" width="70px"
					style="padding: 0px 0px 0px 0px;">小数位数</td>
				<td nowrap align="center" width="70px"
					style="padding: 0px 0px 0px 0px;">工资组成</td>
				<td nowrap align="center" width="60px"
					style="padding: 0px 0px 0px 0px;">是否显示</td>
				<td nowrap align="center" width="120px;"
					style="padding: 0px 0px 0px 0px;">操作</td>
			</tr>
			<tbody id="tbody">
			</tbody>
		</table>
	</div>

</body>
</html>
