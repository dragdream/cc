<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%
    String itemName = request.getParameter("itemName");
    String sid = request.getParameter("sid");
%>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>工资项管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">
var sid = "<%=sid%>";
var itemName = "<%=itemName%>";
function doInit(){
	var url = "<%=contextPath%>/teeSalItemController/getSalItem.action";
    var para =  {sid:sid};
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
        var json = jsonRs.rtData;
        bindJsonObj2Cntrl(json);
        if(json.iscomputer == "1"){
        	$("#formu").show();
        	$("#itemType").val(2);
        }else if(json.isprint == "1"){
        	$("#itemType").val(1);
        }
    }
}
function checkForm(){
    return $("#form1").form('validate'); 
}

function doSave(){
	if(checkForm()){
	    var url = "<%=contextPath%>/teeSalItemController/updateSalItem.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	       // alert();
	        top.$.jBox.tip(jsonRs.rtMsg , 'info' , {timeout:1500});
	        toReturn();
	    }
	}
}

function sel_change(input)
{

  if(form1.itemType.value=="1")
   {
      document.all("formu").style.display="";
    }
  else
      {
          document.all("formu").style.display="none";
          document.form1.formula.value="";
          document.form1.formulaName.value="";
      }

}

function LoadWindow2(){
  var URL="/system/subsys/salary/insurance_para/sal_item/formula_edit.jsp";
  myleft=(screen.availWidth-650)/2;
  window.open(URL,"formul_edit","height=350,width=650,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=150,left="+myleft+",resizable=yes");
}

function toReturn(){
    var url = "<%=contextPath%>/teeSalItemController/sal_item_index.action";
    location.href = url;
}
</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id = "sid" value="<%=sid%>">
<br>
    <table align="center" width="40%" class="TableBlock">
      <tr>
          <td nowrap class="TableData"> 类别名称：<font style='color:red'>*</font></td>
          <td class="TableData" colspan="3">
              <input type="text" name="itemName" id="itemName" size="40" maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
          </td>
      </tr>
     <tr>

	    <td nowrap class="TableData">项目类型：</td>
	    <td nowrap class="TableData">
	         <select name="itemType" id="itemType" class="BigSelect" onChange="sel_change()">
	       <option value="0">录入项</option>
	       <!-- <option value="1">部门上报项</option> -->
	       <option value="1">计算项</option>
	       </select>
	    </td>
   </tr>
    <tr>

	    <td nowrap class="TableData">是否显示：</td>
	    <td nowrap class="TableData">
	         <select name="itemFlag" id="itemFlag" class="BigSelect" >
	       <option value="0">是</option>
	       <option value="1">否</option>
	       </select>
	    </td>
   </tr>
    <tr id="formu" style="display:none">
	    <td nowrap class="TableData">计算公式：</td>
	    <td nowrap class="TableData">
	        <input type="hidden" name="formula" id = "formula">
	        <textarea cols=37 name="formulaName" id="formulaName" rows="4" class="BigStatic" readonly  wrap="yes"></textarea>&nbsp;
	        <input type="button" value="编辑公式" onClick="LoadWindow2()" title="编辑公式" name="button" class="btn btn-success">
	    </td>
   </tr>
     <tr>
        <td nowrap class="TableData" colspan="4" align="center">
            <input id="button" type="button" value="添加" onclick="doSave();" class="btn btn-success"/>&nbsp;
            <input id="button" type="button" value="返回" onclick="toReturn();" class="btn btn-success"/>
        </td>
     </tr>
    </table>
<br>
</form>
</body>
</html>
