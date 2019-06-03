<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>薪酬基数列表</title>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  
<script>
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


	var url = "<%=contextPath%>/salaryManage/getPersonList.action";
	var deptId = "${deptId}";
    var para =  {deptId:deptId};
    var personIds = "${personIds}";//人员Id字符串，以逗号分隔

    if(personIds != null && personIds != ''){
    	var url1 = "<%=contextPath%>/salaryManage/getPersonSalData.action";
 		var para1 =  {personId:personIds};
 		var jsonRs = tools.requestJsonRs(url1,para1);
 		var json = jsonRs.rtData.modelData;
 		var itemList = jsonRs.rtData.itemList;
      	for(var i = 0;i < json.length;i++){
      		$("#"+json[i].userId+"_ALL_BASE").val(json[i].allBase);
 			$("#"+json[i].userId+"_PENSION_BASE").val(json[i].pensionBase);
 			$("#"+json[i].userId+"_PENSION_U").val(json[i].pensionU);
 			$("#"+json[i].userId+"_PENSION_P").val(json[i].pensionP);
 			$("#"+json[i].userId+"_MEDICAL_BASE").val(json[i].medicalBase);
 			$("#"+json[i].userId+"_MEDICAL_U").val(json[i].medicalU);
 			$("#"+json[i].userId+"_MEDICAL_P").val(json[i].medicalP);
 			$("#"+json[i].userId+"_FERTILITY_BASE").val(json[i].fertilityBase);
 			$("#"+json[i].userId+"_FERTILITY_U").val(json[i].fertilityU);
 			$("#"+json[i].userId+"_FERTILITY_P").val(json[i].fertilityP);
 			$("#"+json[i].userId+"_UNEMPLOYMENT_BASE").val(json[i].unemploymentBase);
 			$("#"+json[i].userId+"_UNEMPLOYMENT_U").val(json[i].unemploymentU);
 			$("#"+json[i].userId+"_UNEMPLOYMENT_P").val(json[i].unemploymentP);
 			$("#"+json[i].userId+"_INJURIES_BASE").val(json[i].injuriesBase);
 			$("#"+json[i].userId+"_INJURIES_U").val(json[i].injuriesU);
 			$("#"+json[i].userId+"_HOUSING_BASE").val(json[i].housingBase);
 			$("#"+json[i].userId+"_HOUSING_U").val(json[i].housingU);
 			$("#"+json[i].userId+"_HOUSING_P").val(json[i].housingP);
 			
 			
 			var dataTemp  = json[i];//工资自定义项
 		  	for(var j = 0;j < itemList.length;j++){
 		  		var item = itemList[j];
 		  		var column = item.itemColumn;
 		  		$("#"+json[i].userId+"_" + column).val(dataTemp[column]);
 		  	}
 		  	
      	}
      	
      	//工资自定义项求和
      	for(var j = 0;j < itemList.length;j++){
		  		var item = itemList[j];
		  		var column = item.itemColumn;
		  		total_calculate(column);
		}
      	computerAll();
    }
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
   var tabObj = document.getElementById("cal_table");    
   var rowCount = tabObj.rows.length-1;   
   var cellCount = tabObj.rows(0).cells.length; 
   var arr=document.getElementsByName("userId");
   var checkAll = 0;
   for(var i=1;i < rowCount ;i++){    
	   //养老
	   checkAll += parseFloat(eval("document.getElementById('"+arr[i-1].value+"_ALL_BASE')").value);
   }
   if(isNaN(checkAll)){
	   alert("请先设置保险基数！");
	   return ;
   }
   for(var i=1;i < rowCount ;i++)    
   {	
   	  //养老
   	  var ALL_BASE = parseFloat(eval("document.getElementById('"+arr[i-1].value+"_ALL_BASE')").value);
   	  var PENSION_U=ALL_BASE * PENSION_U_PAY + PENSION_U_PAY_ADD;
   	  var PENSION_P=ALL_BASE * PENSION_P_PAY + PENSION_P_PAY_ADD;
   	  //alert(ALL_BASE+","+PENSION_U+","+PENSION_P);
   	  eval("document.getElementById('"+arr[i-1].value+"_PENSION_U')").value=FormatNumber(PENSION_U,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_PENSION_P')").value=FormatNumber(PENSION_P,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_PENSION_BASE')").value = FormatNumber(PENSION_U + PENSION_P,2);
   	  //医疗
   	  var MEDICAL_U=ALL_BASE * HEALTH_U_PAY + HEALTH_U_PAY_ADD;
   	  var MEDICAL_P=ALL_BASE * HEALTH_P_PAY + HEALTH_P_PAY_ADD;
   	  eval("document.getElementById('"+arr[i-1].value+"_MEDICAL_U')").value=FormatNumber(MEDICAL_U,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_MEDICAL_P')").value=FormatNumber(MEDICAL_P,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_MEDICAL_BASE')").value = FormatNumber(MEDICAL_U + MEDICAL_P,2);
   	  //生育
   	  var MATERNITY_U=ALL_BASE * MATERNITY_U_PAY + MATERNITY_U_PAY_ADD;
   	  eval("document.getElementById('"+arr[i-1].value+"_FERTILITY_U')").value=FormatNumber(MATERNITY_U,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_FERTILITY_BASE')").value = FormatNumber(MATERNITY_U,2);
      //失业
   	  var UNEMPLOYMENT_U=ALL_BASE * UNEMPLOYMENT_U_PAY + UNEMPLOYMENT_U_PAY_ADD;
   	  var UNEMPLOYMENT_P=ALL_BASE * UNEMPLOYMENT_P_PAY + UNEMPLOYMENT_P_PAY_ADD;
   	  eval("document.getElementById('"+arr[i-1].value+"_UNEMPLOYMENT_U')").value=FormatNumber(UNEMPLOYMENT_U,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_UNEMPLOYMENT_P')").value=FormatNumber(UNEMPLOYMENT_P,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_UNEMPLOYMENT_BASE')").value = FormatNumber(UNEMPLOYMENT_U + UNEMPLOYMENT_P,2);
   	  //工伤
   	  var INJURIES_U=ALL_BASE * INJURY_U_PAY + INJURY_U_PAY_ADD;
   	  eval("document.getElementById('"+arr[i-1].value+"_INJURIES_U')").value=FormatNumber(INJURIES_U,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_INJURIES_BASE')").value = FormatNumber(INJURIES_U,2);
   	  
      //住房公积金
   	  var HOUSING_U=ALL_BASE * HOUSING_U_PAY + HOUSING_U_PAY_ADD;
   	  var HOUSING_P=ALL_BASE * HOUSING_P_PAY + HOUSING_P_PAY_ADD;
   	  eval("document.getElementById('"+arr[i-1].value+"_HOUSING_U')").value=FormatNumber(HOUSING_U,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_HOUSING_P')").value=FormatNumber(HOUSING_P,2);
   	  eval("document.getElementById('"+arr[i-1].value+"_HOUSING_BASE')").value = FormatNumber(HOUSING_U + HOUSING_P,2);   	  
   }    
   
   computerAll();
}
window.onscroll=window.onresize=function()
{
   op_btn=document.getElementById("OP_BTN");
   if(!op_btn) return false;
   
   op_btn.style.left=document.body.clientWidth+document.body.scrollLeft-320;
   op_btn.style.top =document.body.scrollTop +5;
};

function total_calculate(id)
{
	//if("${hrPara.yesOther}"=="1"){
		var arr=document.getElementsByName("userId");
		var total_temp_id=id+"_TOTAL";
		var total_count=0;
		for(var i=0;i<arr.length;i++)
		{
				var temp_id=arr[i].value+"_"+id;
				var temp=document.getElementById(temp_id).value;
				if(temp=="")
					temp=0;
				else
					temp=parseFloat(document.getElementById(temp_id).value);
				total_count+=temp;
		}
		if(arr.length>0){
			total_count=FormatNumber(total_count,2);
		    document.getElementById(total_temp_id).value=total_count;
		}
	//}

}
/**
 * 自动计算工资项
 */
function total_item(id)
{
		var arr=document.getElementsByName("userId");
		var total_temp_id=id+"_TOTAL";
		var total_count=0;
		for(var i=0;i<arr.length;i++)
		{
				var temp_id=arr[i].value+"_"+id;
				var temp=document.getElementById(temp_id).value;
				if(temp=="")
					temp=0;
				else
					temp=parseFloat(document.getElementById(temp_id).value);
				total_count+=temp;
		}
		if(arr.length>0){
			total_count=FormatNumber(total_count,2);
		    document.getElementById(total_temp_id).value=total_count;
		}

}

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

function queding(){
	var deptId = "${deptId}";
	var temp  = getItemList();
	var itemList = temp.itemList;
	var ids = temp.ids;
	 var url = "<%=contextPath%>/salaryManage/saveSalData.action?deptId="+deptId;
	//var para =  tools.formToJson($("#form1"));
	var itemListStr = tools.jsonArray2String(itemList);
	var para = {itemListStr : itemListStr , ids : ids};
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
        window.location.reload();
    }
}

/**
 *  获取数据转JSON数组
 */
function getItemList(){
	var itemList = $("#tbody").find("tr");
	var productItem = new Array();
	var ids = "";
	for(var i =0; i <itemList.length ; i++){
		
		
		var itemTemp = itemList[i];
		var itemp = {};
		for(var j =0; j <itemTemp.cells.length ; j++){
			var temp = $(itemTemp.cells[j]).find("input");
			itemp[temp.attr("name")] = temp.val();
			if(temp.attr("name") == 'userId'){
				ids = ids + temp.val() +",";
			}
		}
		
		productItem.push(itemp);
	
	}

	var temp = {itemList : productItem , ids : ids};
	return temp;
}
</script>

</head>

<body class="bodycolor" topmargin="5" onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
	<table border="0" width="99%" cellspacing="0" cellpadding="3" >
		<tr>
			<td>[${deptName }]员工薪酬基数列表 - ${personCount }人</td>
			<td style="float:right;">
				<c:if test="${hrPara.yesOther == 1}">
					<input type="button" onclick="calculate();" value="计算" class="btn btn-success">&nbsp;
				</c:if>
				<input type="button" onclick="queding();" value="确定" class="btn btn-success"></td>
		</tr>
	</table>
	<table align="center" width="100%" class="TableBlock" id="cal_table">
      <tr>
      	  <td nowrap align="center" style="cursor:hand" class="TableHeader" width="100px;"><b> 姓名     </b></td>
      	  <c:if test="${hrPara.yesOther == 1}">
			  <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>保险基数</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>养老保险</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>单位养老</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>个人养老</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>医疗保险</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>单位医疗</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>个人医疗</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>生育保险</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>单位生育</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>失业保险</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>单位失业</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>个人失业</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>工伤保险</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>单位工伤</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>住房公积金</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>单位住房</b></td>
		      <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>个人住房</b></td>  
	      </c:if>    
	      
	      
	      <c:forEach items="${itemList}" var="itemListSort" varStatus="itemListStatus">
              <td nowrap align="center" style="cursor:hand" class="TableHeader"><b>${itemList[itemListStatus.index].itemName}</b></td>  
          </c:forEach>     
               
       </tr>
     
   
       <tbody id="tbody">
          <c:forEach items="${personList}" var="personListSort" varStatus="personListStatus">
            <tr>
                 <td align="center" class="TableData"><input type="hidden" name="userId" value="${personList[personListStatus.index].uuid}">${personList[personListStatus.index].userName}</td>
                 <c:if test="${hrPara.yesOther == 1}">
                     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_ALL_BASE" name="allBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('ALL_BASE')"> </td>     
	                 <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_PENSION_BASE" name="pensionBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10" onblur="total_calculate('PENSION_BASE')"> </td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_PENSION_U" name="pensionU" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('PENSION_U')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_PENSION_P" name="pensionP" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('PENSION_P')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_MEDICAL_BASE" name="medicalBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('MEDICAL_BASE')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_MEDICAL_U" name="medicalU" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('MEDICAL_U')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_MEDICAL_P" name="medicalP" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('MEDICAL_P')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_FERTILITY_BASE" name="fertilityBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('FERTILITY_BASE')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_FERTILITY_U" name="fertilityU" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('FERTILITY_U')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_UNEMPLOYMENT_BASE" name="unemploymentBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('UNEMPLOYMENT_BASE')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_UNEMPLOYMENT_U" name="unemploymentU" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('UNEMPLOYMENT_U')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_UNEMPLOYMENT_P" name="unemploymentP" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('UNEMPLOYMENT_P')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_INJURIES_BASE" name="injuriesBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('INJURIES_BASE')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_INJURIES_U" name="injuriesU" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('INJURIES_U')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_HOUSING_BASE" name="housingBase" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('HOUSING_BASE')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_HOUSING_U" name="housingU" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('HOUSING_U')"></td>                   
				     <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_HOUSING_P" name="housingP" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('HOUSING_P')"></td> 
           		 </c:if>
           		 
           		 <c:forEach items="${itemList}" var="itemListSort" varStatus="itemListStatus">
            	   	 <td nowrap align="center" class="TableData"><input type="text" style="text-align: right;" id="${personList[personListStatus.index].uuid}_${itemList[itemListStatus.index].itemColumn}"  name="${itemList[itemListStatus.index].itemColumn}" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="" size="10"  onblur="total_calculate('${itemList[itemListStatus.index].itemColumn}')"></td> 
        		 </c:forEach>     
            </tr>
        </c:forEach>
        </tbody>
        <c:if test="${fn:length(personList)>0}">
	        <tr>
	  			<td nowrap align="center" class="TableData">总计</td>
	  			<c:if test="${hrPara.yesOther == 1}">
		  			<td nowrap align="center"  class="TableData">
						<input type="text" style="text-align: right;" id="ALL_BASE_TOTAL" name="ALL_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="PENSION_BASE_TOTAL" name="PENSION_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="PENSION_U_TOTAL" name="PENSION_U_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center"  class="TableData">
						<input type="text" style="text-align: right;" id="PENSION_P_TOTAL" name="PENSION_P_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="MEDICAL_BASE_TOTAL" name="MEDICAL_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">	
						<input type="text" style="text-align: right;" id="MEDICAL_U_TOTAL" name="MEDICAL_U_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="MEDICAL_P_TOTAL" name="MEDICAL_P_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="FERTILITY_BASE_TOTAL" name="FERTILITY_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="FERTILITY_U_TOTAL" name="FERTILITY_U_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="UNEMPLOYMENT_BASE_TOTAL" name="UNEMPLOYMENT_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="UNEMPLOYMENT_U_TOTAL" name="UNEMPLOYMENT_U_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="UNEMPLOYMENT_P_TOTAL" name="UNEMPLOYMENT_P_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="INJURIES_BASE_TOTAL" name="INJURIES_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="INJURIES_U_TOTAL" name="INJURIES_U_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="HOUSING_BASE_TOTAL" name="HOUSING_BASE_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
					<td nowrap align="center" class="TableData">
						<input type="text" style="text-align: right;" id="HOUSING_U_TOTAL" name="HOUSING_U_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
					</td>
				    <td nowrap align="center" class="TableData">
							<input type="text" style="text-align: right;" id="HOUSING_P_TOTAL" name="HOUSING_P_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
				    </td>
			    </c:if>
			     <c:forEach items="${itemList}" var="itemListSort" varStatus="itemListStatus">
            	   	 <td nowrap align="center" class="TableData">
            	   	 	<!-- <input type="text" style="text-align: right;" id="HOUSING_P_TOTAL" name="HOUSING_P_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value=0.00 size="10" readonly> 
				    -->
            	        <input type="text" style="text-align: right;"  id="${itemList[itemListStatus.index].itemColumn}_TOTAL" name="${itemList[itemListStatus.index].itemColumn}_TOTAL" class="BigInput easyui-validatebox" maxlength="10" required="true" validType ='number[]' value="0.0" size="10"  readonly></td> 
        		 </c:forEach>     
			</tr>
		</c:if>
	</table>
	
</form>
</body>
</html>
<script language="JavaScript">
function clickTitle(ID)
{
  var str1=document.all("STYLE_USER").value;
  var id_value_array=str1.split(",");
  var temp=id_value_array.length-2;
  for(i=0;i<=temp;i++)
  {
    control=id_value_array[i]+"_"+ID;
    if(i==0)setvalue=document.all(control).value;
    document.all(control).value=setvalue;
  }
}
</script>