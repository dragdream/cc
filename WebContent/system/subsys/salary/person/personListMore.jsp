<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>批量设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>



<script type="text/javascript">
var PENSION_U_PAY="";
var PENSION_U_PAY_ADD="";
var PENSION_P_PAY="";
var PENSION_P_PAY_ADD="";
var HEALTH_P_PAY="";
var HEALTH_P_PAY_ADD="";
var HEALTH_U_PAY="";
var HEALTH_U_PAY_ADD="";
var UNEMPLOYMENT_P_PAY="";
var UNEMPLOYMENT_P_PAY_ADD="";
var UNEMPLOYMENT_U_PAY="";
var UNEMPLOYMENT_U_PAY_ADD="";
var HOUSING_P_PAY="";
var HOUSING_P_PAY_ADD="";
var HOUSING_U_PAY="";
var HOUSING_U_PAY_ADD="";
var INJURY_U_PAY="";
var INJURY_U_PAY_ADD="";
var MATERNITY_U_PAY="";
var MATERNITY_U_PAY_ADD=""; 
var YES_OTHER = ""; 

function doInit(){
    var url2 = "<%=contextPath%>/salaryManage/getHrPara.action";
    var para2 =  {};
    var jsonRs2 = tools.requestJsonRs(url2,para2);
    if(jsonRs2.rtState){
        var json2 = jsonRs2.rtData;
        if(json2!=null){
            PENSION_U_PAY=parseFloat(json2.pensionUPay/100);
            PENSION_U_PAY_ADD=parseFloat(json2.pensionUPayAdd);
            PENSION_P_PAY=parseFloat(json2.pensionPPay/100);
            PENSION_P_PAY_ADD=parseFloat(json2.pensionPPayAdd);
            HEALTH_P_PAY=parseFloat(json2.healthPPay/100);
            HEALTH_P_PAY_ADD=parseFloat(json2.healthPPayAdd);
            HEALTH_U_PAY=parseFloat(json2.healthUPay/100);
            HEALTH_U_PAY_ADD=parseFloat(json2.healthUPayAdd);
            UNEMPLOYMENT_P_PAY=parseFloat(json2.unemploymentPPay/100);
            UNEMPLOYMENT_P_PAY_ADD=parseFloat(json2.unemploymentPPayAdd);
            UNEMPLOYMENT_U_PAY=parseFloat(json2.unemploymentUPay/100);
            UNEMPLOYMENT_U_PAY_ADD=parseFloat(json2.unemploymentUPayAdd);
            HOUSING_P_PAY=parseFloat(json2.housingPPay/100);
            HOUSING_P_PAY_ADD=parseFloat(json2.housingPPayAdd);
            HOUSING_U_PAY=parseFloat(json2.housingUPay/100);
            HOUSING_U_PAY_ADD=parseFloat(json2.housingUPayAdd);
            INJURY_U_PAY=parseFloat(json2.injuryUPay/100);
            INJURY_U_PAY_ADD=parseFloat(json2.housingPPayAdd);
            MATERNITY_U_PAY=parseFloat(json2.maternityUPay/100);
            MATERNITY_U_PAY_ADD=parseFloat(json2.maternityUPayAdd); 
            YES_OTHER = json2.yesOther; 
        }
    }
    
}

function calculate()
{
      //养老
      var ALL_BASE = parseFloat(document.getElementById('ALL_BASE').value);
      if(document.getElementById('ALL_BASE').value==""){
    	  alert("请先设置保险基数！");
    	  return ;
      }
      var PENSION_U=ALL_BASE * PENSION_U_PAY + PENSION_U_PAY_ADD;
      var PENSION_P=ALL_BASE * PENSION_P_PAY + PENSION_P_PAY_ADD;
      document.getElementById('PENSION_U').value=FormatNumber(PENSION_U,2);
      document.getElementById('PENSION_P').value=FormatNumber(PENSION_P,2);
      document.getElementById('PENSION_BASE').value = FormatNumber(PENSION_U + PENSION_P,2);
      //医疗
      var MEDICAL_U=ALL_BASE * HEALTH_U_PAY + HEALTH_U_PAY_ADD;
      var MEDICAL_P=ALL_BASE * HEALTH_P_PAY + HEALTH_P_PAY_ADD;
      document.getElementById('MEDICAL_U').value=FormatNumber(MEDICAL_U,2);
      document.getElementById('MEDICAL_P').value=FormatNumber(MEDICAL_P,2);
      document.getElementById('MEDICAL_BASE').value = FormatNumber(MEDICAL_U + MEDICAL_P,2);
      //生育
      var MATERNITY_U=ALL_BASE * MATERNITY_U_PAY + MATERNITY_U_PAY_ADD;
      document.getElementById('FERTILITY_U').value=FormatNumber(MATERNITY_U,2);
      document.getElementById('FERTILITY_BASE').value = FormatNumber(MATERNITY_U,2);
      //失业
      var UNEMPLOYMENT_U=ALL_BASE * UNEMPLOYMENT_U_PAY + UNEMPLOYMENT_U_PAY_ADD;
      var UNEMPLOYMENT_P=ALL_BASE * UNEMPLOYMENT_P_PAY + UNEMPLOYMENT_P_PAY_ADD;
      document.getElementById('UNEMPLOYMENT_U').value=FormatNumber(UNEMPLOYMENT_U,2);
      document.getElementById('UNEMPLOYMENT_P').value=FormatNumber(UNEMPLOYMENT_P,2);
      document.getElementById('UNEMPLOYMENT_BASE').value = FormatNumber(UNEMPLOYMENT_U + UNEMPLOYMENT_P,2);
      //工伤
      var INJURIES_U=ALL_BASE * INJURY_U_PAY + INJURY_U_PAY_ADD;
      document.getElementById('INJURIES_U').value=FormatNumber(INJURIES_U,2);
      document.getElementById('INJURIES_BASE').value = FormatNumber(INJURIES_U,2);
      
      //住房公积金
      var HOUSING_U=ALL_BASE * HOUSING_U_PAY + HOUSING_U_PAY_ADD;
      var HOUSING_P=ALL_BASE * HOUSING_P_PAY + HOUSING_P_PAY_ADD;
      document.getElementById('HOUSING_U').value=FormatNumber(HOUSING_U,2);
      document.getElementById('HOUSING_P').value=FormatNumber(HOUSING_P,2);
      document.getElementById('HOUSING_BASE').value = FormatNumber(HOUSING_U + HOUSING_P,2);       
}
window.onscroll=window.onresize=function()
{
   op_btn=document.getElementById("OP_BTN");
   if(!op_btn) return false;
   
   op_btn.style.left=document.body.clientWidth+document.body.scrollLeft-320;
   op_btn.style.top =document.body.scrollTop +5;
};


function FormatNumber(srcStr,nAfterDot)
{ 
   var srcStr,nAfterDot; 
   var resultStr,nTen; 
   srcStr = ""+srcStr+""; 
   strLen = srcStr.length; 
   dotPos = srcStr.indexOf("."); 
   if(dotPos == -1)
   { 
      resultStr = srcStr+"."; 
      for(var i=0;i<nAfterDot;i++)
      { 
         resultStr = resultStr+"0"; 
      } 
      //return resultStr;
      return resultStr=="NaN.00"?"":resultStr;  
   }else{ 
      if((strLen - dotPos - 1) >= nAfterDot)
      { 
         nAfter = dotPos + nAfterDot + 1; 
         nTen =1; 
         for(var j=0;j<nAfterDot;j++)
         { 
            nTen = nTen*10; 
         } 
         resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen; 
         //return resultStr;
         return resultStr=="NaN.00"?"":resultStr;
      }else{ 
         resultStr = srcStr; 
         for(var i=0;i<(nAfterDot - strLen + dotPos + 1);i++)
         { 
            resultStr = resultStr+"0"; 
         } 
         //return resultStr; 
         return resultStr=="NaN.00"?"":resultStr;
      } 
   } 
}

function computerAll(){
    total_calculate('ALL_BASE');
    total_calculate('PENSION_BASE');
    total_calculate('PENSION_U');
    total_calculate('PENSION_P');
    total_calculate('MEDICAL_BASE');
    total_calculate('MEDICAL_U');
    total_calculate('MEDICAL_P');
    total_calculate('FERTILITY_BASE');
    total_calculate('FERTILITY_U');
    total_calculate('UNEMPLOYMENT_BASE');
    total_calculate('UNEMPLOYMENT_U');
    total_calculate('UNEMPLOYMENT_P');
    total_calculate('INJURIES_BASE');
    total_calculate('INJURIES_U');
    total_calculate('HOUSING_BASE');
    total_calculate('HOUSING_U');
    total_calculate('HOUSING_P');
}

function checkForm(){
	if($("#postDeptIds").val()==""&&$("#postUserRoleIds").val()==""&&$("#postUserIds").val()==""){
		alert("请选择批量人员范围");
		return false;
	}
	return true;
}

function doSaveOrUpdate(){
	if(checkForm()){
	    var url = "<%=contextPath%>/salaryManage/saveSalDataMore.action";
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	        window.location.reload();
	    }
	}

}
</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<br>
<table align="center" width="60%" class="TableBlock" enctype="multipart/form-data">
    <tr>
        <td nowrap class="TableHeader" colspan="3" style="text-align:left;">选择批量设置的人员范围:</td>
    </tr>
    <tr>
        <td nowrap class="TableData">按部门设置：</td>
        <td class="TableData" colspan="3">
             <input type="hidden" name="postDeptIds" id="postDeptIds" value="">
             <textarea cols=50 name="postDeptNames" id="postDeptNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
             <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptIds', 'postDeptNames']);">添加</a>
            <a href="javascript:void(0);" class="orgClear" onClick="$('#postDeptIds').val('');$('#postDeptNames').val('');">清空</a>
        </td>
    </tr>
    <tr>
      <td nowrap class="TableData">按角色设置：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="postUserRoleIds" id="postUserRoleIds" value="">
          <textarea cols=50 name="postUserRoleNames" id="postUserRoleNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
          <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['postUserRoleIds', 'postUserRoleNames']);">添加</a>
          <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserRoleIds').val('');$('#postUserRoleNames').val('');">清空</a>
      </td>
   </tr>
   <tr>
      <td nowrap class="TableData">按人员设置：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="postUserIds" id="postUserIds" value="">
          <textarea cols=50 name="postUserNames" id="postUserNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
          
          <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['postUserIds', 'postUserNames']);">添加</a>
          <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserIds').val('');$('#postUserNames').val('');">清空</a>
          <br>设置范围取部门、人员和角色的并集 
      </td>
   </tr> 
  </table>
  <table align="center" width="60%" class="TableBlock">
   <tr class="TableHeader">
     <td nowrap align="center">保险项</td>
     <td nowrap align="center">金额</td>
  </tr>     
  <tr class="TableData">
    <td nowrap align="center" width="150">保险基数</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="ALL_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="ALL_BASE" value="" size="20" maxlength="14">
       <input type="button" value="计算" class="btn btn-success" onclick="calculate()">
    </td>                     
  </tr>
  <tr class="TableLine1">
    <td nowrap align="center" width="150">养老保险</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="PENSION_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="PENSION_BASE" value="" size="20" maxlength="14">
    </td>                     
  </tr>
  <tr class="TableLine1">
    <td nowrap align="center" width="150">单位养老</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="PENSION_U" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="PENSION_U" value="" size="20" maxlength="14">
    </td>                     
  </tr>
  <tr class="TableLine1">
    <td nowrap align="center" width="150">个人养老</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="PENSION_P" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="PENSION_P" value="" size="20" maxlength="14">
    </td>                     
  </tr>  
  
  <tr class="TableLine2">
    <td nowrap align="center" width="150">医疗保险</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="MEDICAL_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="MEDICAL_BASE" value="" size="20" maxlength="14">
    </td>                     
  </tr>
  <tr class="TableLine2">
    <td nowrap align="center" width="150">单位医疗</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="MEDICAL_U" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="MEDICAL_U" value="" size="20" maxlength="14">
    </td>                     
  </tr>
  <tr class="TableLine2">
    <td nowrap align="center" width="150">个人医疗</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="MEDICAL_P" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="MEDICAL_P" value="" size="20" maxlength="14">
    </td>                     
  </tr>  
    
  <tr class="TableLine1">
    <td nowrap align="center" width="150">生育保险</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="FERTILITY_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="FERTILITY_BASE" value="" size="20" maxlength="14">
    </td>                     
  </tr>    
  <tr class="TableLine1">
    <td nowrap align="center" width="150">单位生育</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="FERTILITY_U" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="FERTILITY_U" value="" size="20" maxlength="14">
    </td>                     
  </tr>    
  <tr class="TableLine2">
    <td nowrap align="center" width="150">失业保险</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="UNEMPLOYMENT_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="UNEMPLOYMENT_BASE" value="" size="20" maxlength="14">
    </td>                     
  </tr>    
  <tr class="TableLine2">
    <td nowrap align="center" width="150">单位失业</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="UNEMPLOYMENT_U" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="UNEMPLOYMENT_U" value="" size="20" maxlength="14">
    </td>                     
  </tr>    
  <tr class="TableLine2">
    <td nowrap align="center" width="150">个人失业</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="UNEMPLOYMENT_P" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="UNEMPLOYMENT_P" value="" size="20" maxlength="14">
    </td>                     
  </tr>      
  <tr class="TableLine1">
    <td nowrap align="center" width="150">工伤保险</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="INJURIES_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="INJURIES_BASE" value="" size="20" maxlength="14">
    </td>                     
  </tr>      
  <tr class="TableLine1">
    <td nowrap align="center" width="150">单位工伤</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="INJURIES_U" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="INJURIES_U" value="" size="20" maxlength="14">
    </td>                     
  </tr>      
  <tr class="TableLine2">
    <td nowrap align="center" width="150">住房公积金</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="HOUSING_BASE" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="HOUSING_BASE" value="" size="20" maxlength="14">
    </td>                     
  </tr>      
  <tr class="TableLine2">
    <td nowrap align="center" width="150">单位住房</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="HOUSING_U" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="HOUSING_U" value="" size="20" maxlength="14">
    </td>                     
  </tr>    
  <tr class="TableLine2">
    <td nowrap align="center" width="150">个人住房</td>
    <td nowrap align="left">
       <input type="text" style="text-align: right;" id="HOUSING_P" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="HOUSING_P" value="" size="20" maxlength="14">
    </td>                     
  </tr>
  <c:if test="${fn:length(shuruList)>0}">
        <tr class="TableHeader">
              <td nowrap align="center">录入项</td>
              <td nowrap align="center">金额</td>
        </tr> 
        <c:forEach items="${shuruList}" var="caiwuListSort" varStatus="caiwuListStatus">
            <tr class="TableLine2">
                <td nowrap align="center" width="150">${shuruList[caiwuListStatus.index].itemName}</td>
                <td nowrap align="left">
                   <input type="text" style="text-align: right;" id="${shuruList[caiwuListStatus.index].itemColumn}" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="${shuruList[caiwuListStatus.index].itemColumn}" value="" size="20" maxlength="10">
                </td>   
           </tr>
        </c:forEach>
    </c:if>   
    
    <c:if test="${fn:length(jisuanList)>0}">
        <tr class="TableHeader">
              <td nowrap align="center">计算项</td>
              <td nowrap align="center">金额</td>
        </tr> 
        <c:forEach items="${jisuanList}" var="jisuanListSort" varStatus="jisuanListStatus">
            <tr class="TableLine2">
                <td nowrap align="center" width="150">${jisuanList[jisuanListStatus.index].itemName}</td>
                <td nowrap align="left">
                   <input type="text" style="text-align: right;" id="${jisuanList[jisuanListStatus.index].itemColumn}" class="BigInput easyui-validatebox" required="true" validType ='number[]' name="${jisuanList[jisuanListStatus.index].itemColumn}" value="" size="20" maxlength="10">
                   <br>
                                                计算公式：${jisuanList[jisuanListStatus.index].formulaName}
                </td>   
           </tr>
        </c:forEach>
    </c:if>  
   <tr>
      <td nowrap class="TableData" colspan="2" align="center">
         <input id="button" type="button" value="保存" onclick="doSaveOrUpdate();" class="btn btn-success"/>&nbsp;
         <input type="reset" value="清空" class="btn btn-success" >
      </td>
   </tr>
</table> 
  <br>
</form> 
</body>
</html>
