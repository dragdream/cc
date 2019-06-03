<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>桌面全局设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">


function doInit(){
	var url = "<%=contextPath %>/sysPara/getSysParaList.action";
	var paraNames = "IS_USER_SETTING_PORTLET_POS,IS_USER_SETTING_PORTLET_HEIGHT,IS_USER_SETTING_PORTLET_FOLDED,"
		+"PORTLET_COL"
		+ ",PORTLET_COL_TOTAL_WIDTH_VALUE"	;
	var para =  {paraNames:paraNames} ;
	var jsonRs = tools.requestJsonRs(url,para);
	var PORTLET_COL = 3;
	var firstColValue = '34';
	var secondColValue = '33';
	var thirdColValue = "33";
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		for(var i = 0;i<dataList.length;i++){
			var data = dataList[i];
			 setValue(data);
			 if(data.paraName == 'PORTLET_COL'){
				 PORTLET_COL = data.paraValue;
			 }
			 if(data.paraName == 'PORTLET_COL_TOTAL_WIDTH_VALUE' && data.paraValue != ''){
				 var arrParaValue = data.paraValue.split("\|");
				 for(var j=0 ; j < arrParaValue.length ; j++){
					 if(j==0){
						 firstColValue = arrParaValue[0];
					 }else if(j == 1){
						 secondColValue = arrParaValue[1];
					 }else if(j == 2){
						 thirdColValue = arrParaValue[2];
					 }
				 }
				 
			 }
		}
		
	}else{
		alert(jsonRs.rtMsg);
		
	}
	
	setPortletCol(PORTLET_COL);
	$("#firstCol").val(firstColValue);
	$("#secondCol").val(secondColValue);
	$("#thirdCol").val(thirdColValue);

}


/**
 * 设置属性
 */
function setValue(data){
    var cntrlArray = document.getElementsByName(data.paraName);    
    var cntrlCnt = cntrlArray.length;
    var value = data.paraValue;
    for(var i = 0;i< cntrlCnt ;i++){
    	var cntrl = cntrlArray[i];
    	if (cntrl.tagName.toLowerCase() == "input" && (cntrl.type.toLowerCase() == "checkbox" || cntrl.type.toLowerCase() == "radio") ) {
          //alert(data.paraName + ":" + cntrl.value + ":"+ value);
    		if (cntrl.value == value) {
              cntrl.checked = true;
            }else {
              cntrl.checked = false;
            }
        }else{
        	cntrl.value = value;
        }
    }

}

/**
 * 保存
 */
function doSave(){
	if (check()){
		var url = "<%=contextPath %>/sysPara/addOrUpdatePortletSysPara.action";
		var para =  tools.formToJson($("#form1")) ;
	
		var jsonRs = tools.requestJsonRs(url,para);
		//alert(jsonRs);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
		}else{
			alert(jsonRs.rtMsg);
			
		} 
	}
	
}

/**
 * 检查
 */
function check() {
	var isTrue = $("#form1").form('validate'); 
	
	if(isTrue){
		var PORTLET_COL_TOTAL_WIDTH = 0; 
		var PORTLET_COL_TOTAL_WIDTH_VALUE = '';
		$("#PORTLET_COL_DIV").children("input[type='text']").each(function(i, data){
			PORTLET_COL_TOTAL_WIDTH = PORTLET_COL_TOTAL_WIDTH + parseInt($(this).val(),10);
			PORTLET_COL_TOTAL_WIDTH_VALUE = PORTLET_COL_TOTAL_WIDTH_VALUE +  $(this).val() + "|";
			
		});
		if(PORTLET_COL_TOTAL_WIDTH != 100){
			alert("所有栏目宽度之和要等于100");
			return false;
		}
		
		$("#PORTLET_COL_TOTAL_WIDTH_VALUE").val(PORTLET_COL_TOTAL_WIDTH_VALUE.substring(0,PORTLET_COL_TOTAL_WIDTH_VALUE.length-1));


	}else{
		return false;
	}
	return true;
}

/**
 * 设置栏目
 */
function setPortletCol(value){
	$("#PORTLET_COL").val(value);
	var PORTLET_COL_DIV_STR =  "第一栏:<input type='text' size='3' maxlength='3' id='firstCol' class='easyui-validatebox' value='40'/>%&nbsp;"
		+ "第二栏:<input type='text' size='3' maxlength='3' id='secondCol' class='easyui-validatebox' value='30' />%&nbsp;"
		+ "第三栏:<input type='text' size='3' maxlength='3' id='thirdCol' class='easyui-validatebox' value='30' />%";

	if(value == '1'){
		PORTLET_COL_DIV_STR = "第一栏:<input type='text' size='3' maxlength='3' id='firstCol' value='100' class='easyui-validatebox' />%";
	}else if(value == '2'){
		PORTLET_COL_DIV_STR = "第一栏:<input type='text' size='3' maxlength='3' id='firstCol' class='easyui-validatebox' value='50' />%&nbsp;"
		+ "第二栏:<input type='text' size='3' maxlength='3' id='secondCol' class='easyui-validatebox'  value='50'/>%";
	}
	$("#PORTLET_COL_DIV").empty();
	$("#PORTLET_COL_DIV").append(PORTLET_COL_DIV_STR + " <font color='red'>&nbsp;&nbsp;(所有栏目之和等于100) </font>");
	
	$('#firstCol').validatebox({ 
		required:true,
		validType:'integeBetweenLength[1,100]'
	});
	
	$('#secondCol').validatebox({ 
		required:true,
		validType:'integeBetweenLength[1,100]'
	});
	
	$('#thirdCol').validatebox({ 
		required:true,
		validType:'integeBetweenLength[1,100]'
	});
}
</script>

</head>
<body onload="doInit()">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><img src="<%=imgPath%>/notify_open.gif" align="absmiddle"><span class="big3">
      </td>
    </tr>
  </table><br>
  <form   method="post" name="form1" id="form1">
<table class="TableBlock" width="80%" align="center">
			<tr class="TableHeader">
				<td colspan="2" align="">全局设置</td>
			</tr>
			<tr class="TableData">
				<td nowrap  width="200px;">桌面设置：</td>
				<td nowrap>
					<input type="checkbox" name="IS_USER_SETTING_PORTLET_POS" id="IS_USER_SETTING_PORTLET_POS" value='1' checked="checked"/>&nbsp;是否允许用户调整各桌面位置
					<br>
					<input type="checkbox" name="IS_USER_SETTING_PORTLET_HEIGHT" id="IS_USER_SETTING_PORTLET_HEIGHT" value='1' checked="checked"/>&nbsp;是否允许用户调整栏目高度
					<br>
					<input type="checkbox" name="IS_USER_SETTING_PORTLET_FOLDED" id="IS_USER_SETTING_PORTLET_FOLDED" value='1' checked="checked"/>&nbsp;是否允许用户展开/折叠桌面模块
					<br>
				</td>
			</tr>
		<tr class="TableData">
				<td nowrap>栏目设置宽度：</td>
				<td nowrap>
					<input type="radio" name="PORTLET_COL1"  value='1' onclick="setPortletCol(this.value);"/>&nbsp;1栏
					<input type="radio" name="PORTLET_COL1"  value='2'  onclick="setPortletCol(this.value);" />&nbsp;2栏
					<input type="radio" name="PORTLET_COL1"  value='3' checked="checked" onclick="setPortletCol(this.value);"/>&nbsp;3栏
					<input type = "hidden" name ="PORTLET_COL" id = "PORTLET_COL" value = "3"/>
					<div id="PORTLET_COL_DIV">
					
					</div>
					
				</td>
			</tr>

			<tr class="TableControl">
				<td colspan="2" align="center">
					<input type="button" value="保存" class="BigButtonA" onclick="doSave();">&nbsp;&nbsp;
					<input type="hidden" value="" name="PORTLET_COL_TOTAL_WIDTH_VALUE" id="PORTLET_COL_TOTAL_WIDTH_VALUE">&nbsp;&nbsp;			
					</td>
			</tr>
		</table>
   </form>
</body>
</html>