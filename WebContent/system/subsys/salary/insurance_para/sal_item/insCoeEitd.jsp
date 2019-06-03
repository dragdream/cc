<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>保险系数设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script language="javascript"> 
function isNumberchar(num) 
 { 
    var pattern =/^100$|^100\.[0]{1,2}$|^[0-9]{1,2}\.[0-9]{1,2}$|^[0-9]{1,2}$/;
    if(!pattern.exec(num.value))
    {  
       alert("保险系数必须为0-100的数字且小数点之后最多保留两位!");
       num.focus();
      return (false);
    } 
 } 
 
function checkForm(){
    var check = $("#form1").form('validate'); 
    if(!check  ){
        return false; 
    }
    return true;
}

function doInit(){
	 var url = "<%=contextPath%>/salaryManage/getHrPara.action";
     var para =  {};
     var jsonRs = tools.requestJsonRs(url,para);
     if(jsonRs.rtState){
        if(jsonRs.rtData!=null){
        	var json = jsonRs.rtData;
        	bindJsonObj2Cntrl(json);
        	if(json.yesOther == "1"){
        		$("#yesOther").attr("checked",true);
        	}
        }
     }
}
 
function doSave(){
    if(checkForm()){
    	var sid = $("#sid").val();
        var url = "<%=contextPath%>/salaryManage/addOrUpdateHrPara.action?sid="+sid;
        var para =  tools.formToJson($("#form1"));
        var jsonRs = tools.requestJsonRs(url,para);
        if(jsonRs.rtState){
            alert(jsonRs.rtMsg);
            location.reload();
        }
    }
}

</script>
<script Language="JavaScript">
</script>
</head>
<body class="bodycolor" topmargin="5" onload="doInit();">
<table border="0" width="90%" cellspacing="0" cellpadding="3" >
  <tr>
    <td>保险系数设置</span>&nbsp;&nbsp;
    </td>
  </tr>
</table>
<br>
<form enctype="multipart/form-data" action=""  method="post" name="form1" id="form1" >
<table class="TableBlock" width="60%" align="center">

    <tr>
        <td class="TableData" size="40"><input type="hidden" id = "sid" name = "sid ">显示设置：<input type="checkbox" name="yesOther"  id="yesOther" >
            <label for="yesOther">是否显示在工资项目中</label></td>
      </tr>
    <tr >
      <td nowrap  class="TableHeader" style="text-align:left;">养老保险系数：</td>
    </tr>
    <tr >
      <td nowrap class="TableData" style="padding-left:10px;" >个人支付：
      <input type="text" name="pensionPPay" id="pensionPPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="pensionPPayAdd" id="pensionPPayAdd" class="BigInput easyui-validatebox" size="15" value=""  style="text-align:left;"  required="true" validType ='pointTwoNumber[]'></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">单位支付：
      <input type="text" name="pensionUPay" id="pensionUPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="pensionUPayAdd" id="pensionUPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    <tr>
      <td nowrap  class="TableHeader" style="text-align:left;">医疗保险系数：</td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">个人支付：
      <input type="text" name="healthPPay" id="healthPPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="healthPPayAdd" id="healthPPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">单位支付：
      <input type="text" name="healthUPay" id="healthUPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="healthUPayAdd" id="healthUPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    <tr>
      <td nowrap  class="TableHeader" style="text-align:left;">失业保险系数：</td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">个人支付：
      <input type="text" name="unemploymentPPay" id="unemploymentPPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="unemploymentPPayAdd" id="unemploymentPPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">单位支付：
      <input type="text" name="unemploymentUPay" id="unemploymentUPay"  class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="unemploymentUPayAdd" id="unemploymentUPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    <tr>
      <td nowrap  class="TableHeader" style="text-align:left;">住房公积金系数：</td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">个人支付：
      <input type="text" name="housingPPay" id="housingPPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="housingPPayAdd" id="housingPPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">单位支付：
      <input type="text" name="housingUPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="housingUPayAdd" id="housingUPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
     <tr>
      <td nowrap  class="TableHeader" style="text-align:left;">工伤保险系数：</td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">单位支付：
      <input type="text" name="injuryUPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="injuryUPayAdd" id="injuryUPayAdd" class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
     <tr>
      <td nowrap  class="TableHeader" style="text-align:left;">生育保险系数：</td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:10px;">单位支付：
      <input type="text" name="maternityUPay" class="BigInput easyui-validatebox" size="15" required="true" validType ='numberBetweenLength[0,100]' value=""  style="text-align:left;">%  +
      <input type="text" name="maternityUPayAdd" id="maternityUPayAdd"  class="BigInput easyui-validatebox" size="15" required="true" validType ='pointTwoNumber[]' value=""  style="text-align:left;">
    </tr>
    
   <tr align="center" class="TableControl">
      <td colspan=2 nowrap>
        <input type="button" value="保存" class="btn btn-success" onclick="doSave();">
        <input type="reset" value="重置" class="btn btn-success">
      </td>
   </tr>
  </table>
  <br>
</form>
</body>
</html>