<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>薪酬项编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">

function doInit(){
    
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
	    var url = "<%=contextPath%>/teeSalItemController/addSalItem.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	        top.$.jBox.tip(jsonRs.rtMsg , 'info' , {timeout : 1500});
	        location.reload();
	    }else{
	    	alert(jsonRs.rtMsg);
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
</script>

</head>
<body onload="doInit();">
<div class="Big3" style="padding:5px 0px 10px 5px;">
	薪酬项目自定义管理&nbsp;<font color='red'>(最多只能自定义80项)</font>
</div>
<form action=""  method="post" name="form1" id="form1">
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
     <!--   <option value="1">部门上报项</option> -->
       <option value="1">计算项</option>
       </select>
    </td>
     <tr>

	    <td nowrap class="TableData">是否显示：</td>
	    <td nowrap class="TableData">
	         <select name="itemFlag" id="itemFlag" class="BigSelect" >
	       <option value="0">是</option>
	       <option value="1">否</option>
	       </select>
	    </td>
   </tr>
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
            <input id="button" type="button" value="添加" onclick="doSave();" class="btn btn-success"/>
        </td>
     </tr>
    </table>
<br>
</form>
    <table align="center" width="40%" class="TableBlock">
        <tr class="TableHeader">
            <td nowrap align="center" width="10%">序号</td>
            <td nowrap align="center" width="30%">名称</td>
            <td nowrap align="center" width="30%">项目类型</td>
            <td nowrap align="center" width="10%">是否显示</td>
            <td nowrap align="center" width="30%">操作</td>
        </tr>
        <c:forEach items="${salItemList}" var="bookTypeListSort" varStatus="salItemListStatus">
            <tr class="TableLine">
                <td align="center" width="10%">${salItemListStatus.count}</td>
                <td align="center" nowrap width="30%">${salItemList[salItemListStatus.index].itemName}</td>
                <c:if test="${salItemList[salItemListStatus.index].itemType == 0}">
                    <td align="center" nowrap width="30%">输入项</td>
                </c:if>
                <c:if test="${salItemList[salItemListStatus.index].itemType == 1}">
                    <td align="center" nowrap width="30%">计算项</td>
                </c:if>
             
                
                 <c:if test="${salItemList[salItemListStatus.index].itemFlag == 1}">
                    <td align="center" nowrap width="30%">否</td>
                </c:if>
                <c:if test="${salItemList[salItemListStatus.index].itemFlag == 0}">
                    <td align="center" nowrap width="30%">是</td>
                </c:if>
                <td align="center" nowrap width="30%">
                    <a href="javascript:toEdit('${salItemList[salItemListStatus.index].sid}','${salItemList[salItemListStatus.index].itemName}');">编辑</a>
                    <a href="javascript:toDelete('${salItemList[salItemListStatus.index].sid}');">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
</body>
</html>
