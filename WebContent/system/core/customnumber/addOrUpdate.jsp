<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String uuid = request.getParameter("uuid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<%@include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
#layout{
	width:710px;
}
table td {
	vertical-align: middle;
	padding: 5px;
	/* text-align:center; */
}
.TableBlock{
	/* width:670px;
	overflow:auto; */
}
.TableData .radio,.TableData .string{
	display:inline-block;
}
.TableData .radio{
	margin-top:5px;
}
.zhuijia input{
	display:inline-block;
	float:left;
	margin:0 25px;
}
.save_back{
	overflow:hidden;
}
.save_back input{
	float:right;
	margin:0 5px;
}
</style>
<script>
var uuid = "<%=uuid%>";
	function doInit() {
		if (uuid != "" && uuid != "null") {
			var json = tools.requestJsonRs(contextPath
					+ "/cusNumberController/get.action", {uuid:uuid});//增加type,用于编辑和详情的区分，对附件权限进行区分
			bindJsonObj2Cntrl(json.rtData);
					styleOnBlur();
					$("#showExample").html();
		}

	}
	
	/* function submitButton(linkType) {
		alert();
	    if (linkType == "SAVE") {
			if (!writeReceiver()) return false;
		}
		var functionNameObj = document.getElementById("functionName");
		if(functionNameObj) functionNameObj.value = linkType;
		document.getElementById("actForm").submit();
	} */

	function save() {
		if (!$("#actForm").valid()) {
			return;
		}
		var para = tools.formToJson($("#actForm"));
		var json;
		if (uuid != "" && uuid != "null") {
			json = tools.requestJsonRs(contextPath
					+ "/cusNumberController/update.action", para);
			window.location = 'index.jsp';
		} else {
			json = tools.requestJsonRs(contextPath
					+ "/cusNumberController/addCusNumber.action", para);
			window.location = 'index.jsp';
		}
		/* $.MsgBox.Alert_auto(json.rtMsg);
		if (json.rtState) {
			ret();
		} */
		
		return json;
	}

	function ret() {
		window.location = 'index.jsp';
	}
	
	function addNum() {
		var userSetStyleObj = document.getElementById("userSetStyle");
		if(!userSetStyleObj) return false;
		var digitsObj = document.getElementById("digits");
		if(!digitsObj || digitsObj.value == "") return false;
		var showExampleObj = document.getElementById("showExample");
		if(!showExampleObj) return false;
		userSetStyleObj.value = userSetStyleObj.value + "##";
		if(userSetStyleObj.value.length > 50){
			userSetStyleObj.value = userSetStyleObj.value.substring(0, 50);
		}
		var num = "";
		for (var i = 0; i < digitsObj.value - 1; i++) {
			num += "0";
		}
		num += "1";
		var showExampleContent = showExampleObj.innerHTML + num;
		if(showExampleContent.length > 50) {
			showExampleContent = showExampleContent.substring(0, 50);
		}
		showExampleObj.innerHTML = showExampleContent;
	}

	function addYear(digit) {
		var userSetStyleObj = document.getElementById("userSetStyle");
		if(!userSetStyleObj) return false;
		var showExampleObj = document.getElementById("showExample");
		if(!showExampleObj) return false;
		var showMemo = "YYYY";
		if (digit == 2) {
			showMemo = "YY";
		}
		var userSetStyleContent = userSetStyleObj.value + showMemo;
		if(userSetStyleContent.length > 50) {
			userSetStyleContent = userSetStyleContent.substring(0, 50);
		}
		userSetStyleObj.value = userSetStyleContent;

		var year = new Date().getYear();
		if(year < 1900) year += 1900;
		var today = "" + year;
		if (digit == 2) today = today.substring(2);
	    var showExampleContent = showExampleObj.innerHTML + today;
	    if(showExampleContent.length > 50) {
	        showExampleObj.innerHTML = showExampleContent.substring(0, 50);
	    } else {
	        showExampleObj.innerHTML = showExampleObj.innerHTML + today;
	    }

	}

	function addMonth() {
		var userSetStyleObj = document.getElementById("userSetStyle");
		if(!userSetStyleObj) return false;
		var showExampleObj = document.getElementById("showExample");
		if(!showExampleObj) return false;

	    userSetStyleObj.value = userSetStyleObj.value + "MM";
	    userSetStyleObj.value = userSetStyleObj.value.split();
	    if(userSetStyleObj.value.length > 50) {
	        userSetStyleObj.value = userSetStyleObj.value.substring(0, 50);
	    }
	    var today= new Date();
		var month = today.getMonth() + 1;
		var monthStr = "";
		if (month < 10) monthStr = "0";
		monthStr += month;
	    var showExampleContent =  showExampleObj.innerHTML + monthStr;
	    if (showExampleContent.length > 50){
	        showExampleObj.innerHTML = showExampleContent.substring(0, 50);
	    } else {
	        showExampleObj.innerHTML = showExampleObj.innerHTML + monthStr;
	    }
	}

	function addDay() {
		var userSetStyleObj = document.getElementById("userSetStyle");
		if(!userSetStyleObj) return false;
		var showExampleObj = document.getElementById("showExample");
		if(!showExampleObj) return false;

		var userSetStyleContent = userSetStyleObj.value + "DD";
		if(userSetStyleContent.length > 50) {
			userSetStyleContent = userSetStyleContent.substring(0, 50);
		}
		userSetStyleObj.value = userSetStyleContent;
		var today= new Date();
		var day = today.getDate();
		var dayStr = "";
		if (day < 10) dayStr = "0";
		dayStr += day;
		var showExampleContent = showExampleObj.innerHTML + dayStr;
		if(showExampleContent.length > 50){
			showExampleContent = showExampleContent.substring(0, 50);
		}
		showExampleObj.innerHTML = showExampleContent;
	}

	function addContent() {
		var appendContentObj = document.getElementById("appendContent");
		if(!appendContentObj) return false;
		if(appendContentObj.value == "") return baseAlert(appendContentObj, title_insert_content);
		var userSetStyleObj = document.getElementById("userSetStyle");
		if(!userSetStyleObj) return false;
		var showExampleObj = document.getElementById("showExample");
		if(!showExampleObj) return false;
		var userSetStyleContent = userSetStyleObj.value + appendContentObj.value;
		if (userSetStyleContent.length > 50) {
			userSetStyleContent = userSetStyleContent.substring(0, 50);
		}
		userSetStyleObj.value = userSetStyleContent;

		var showExampleContent = showExampleObj.innerHTML + appendContentObj.value;
		if(showExampleContent.length > 50) {
			showExampleContent = showExampleContent.substring(0, 50);
		}
		showExampleObj.innerHTML = showExampleContent;
		appendContentObj.value = "";
	}

	function styleOnBlur() {
		var userSetStyleObj = document.getElementById("userSetStyle");
		if(!userSetStyleObj) return false;
		var digitsObj = document.getElementById("digits");
		if(!digitsObj) return false;
		var showExampleObj = document.getElementById("showExample");
		if(!showExampleObj) return false;
	    var exm = userSetStyleObj.value;
		var year = new Date().getYear();
		if(year < 1900) year += 1900;
	    //将字符串中所有的YYYY替换成当前年
	    if (exm.indexOf("YYYY") > -1) {
	        exm = repleaceStr(exm, "YYYY", year);
	    }
	    if (exm.indexOf("YY") > -1) {
	        exm = repleaceStr(exm, "YY", year.substring(2));
	    }
	    //将字符串中所有的MM替换成当前月
	    if (exm.indexOf("MM") > -1) {
	        var today= new Date();
	        var month = today.getMonth() + 1;
	        var monthStr = "";
	        if (month < 10) monthStr = "0";
	        monthStr += month;
	        exm = repleaceStr(exm, "MM", monthStr);
	    }
	    //将字符串中所有的DD替换成当前日
	    if (exm.indexOf("DD") > -1) {
	        var today = new Date();
	        var day = today.getDate();
			var dayStr = "";
			if (day < 10) dayStr = "0";
			dayStr += day;
	        exm = repleaceStr(exm, "DD", dayStr);
	    }
	    //将字符串中所有的##替换成编号
	    if (exm.indexOf("##") > -1) {
	    	var curNumBit= (parseInt($("#nowNum").val())+1)+"";
	        var num = "";
		    for (var i = 0; i < digitsObj.value - curNumBit.length; i++) {
		        num += "0";
		    }
	        	num +=(parseInt($("#nowNum").val())+1);
	        exm = repleaceStr(exm, "##", num);
	    }
	    showExampleObj.innerHTML = exm;
	}

	function repleaceStr(str, findStr, repleaceStr) {
	    if (str == "" || findStr == "") return str;
	    var s = str;
	    s = replaceAll(s, ("$" + findStr), "^^^^^");
	    s = replaceAll(s, findStr, repleaceStr);
	    s = replaceAll(s, "^^^^^", findStr);
	    return s;
	}

	function replaceAll(s, regex, replacment) {
	    if (s == "") return s;
	    var regexSite = s.indexOf(regex);
	    while(regexSite > -1) {
	        s = s.substring(0, regexSite) + replacment + s.substring(regexSite + regex.length);
	        regexSite = s.indexOf(regex);
	    }
	    return s;
	}

</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
	<div>
			<form id="actForm">
					<input name="functionName" id="functionName" type="hidden">
					<input name="listMemo" id="listMemo" type="hidden">
					<input name="style" id="style" type="hidden" value="BLUE">
					<input name="style" id="style"	type="hidden" value="BLUE"> 
					
					
					<table width="100%" class="TableBlock" >
					         
					         <tr>
								<TD class=TableHeader colSpan=2 noWrap>
								<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
								<B style="color: #0050aa">名称设置</B></TD>
						     </tr>
						     <tr>
						        <td class="TableData" style="text-indent: 10px;width: 110px">编号名称</td>
								<td class="TableData" >
									<input class="BigInput" name="codeName"  id="codeName" type="text" style="width: 300px;height: 23px;" required/>
								</td>
							</tr>
						     <tr>
								<TD class=TableHeader colSpan=2 noWrap>
								<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
								<B style="color: #0050aa">点击下述按钮添加编号相关信息</B></TD>
						     </tr>
						 
							<tr>
								<td class="TableData zhuijia" style="text-align: left; padding-left: 50px;" colspan="2">
									<input class="btn-win-white" onclick="addYear();" type="button" value="追加年">
                                    <input name="style" id="style" type="hidden" value="BLUE">
									<input class="btn-win-white" onclick="addMonth();" type="button" value="追加月"> 
									<input name="style" id="style" type="hidden" value="BLUE">
									<input class="btn-win-white" onclick="addDay();" type="button" value="追加日"> 
									<input name="style" id="style" type="hidden" value="BLUE">
								    <input class="btn-win-white" onclick="addNum();" type="button" value="追加固定编号">
								    <input name="style" id="style" type="hidden" value="BLUE">
								</td>
							</tr>
							<tr>
								<td  class="TableData" style="text-indent: 10px">编号样式编辑</td>
								<td class="TableData">
								<input name="userSetStyle" class="BigInput" required id="userSetStyle"
									style="width: 300px;height: 23px;"
									onchange="styleOnBlur();" type="text" maxlength="50">
									</br><span style="color:red">年：YYYY 月：MM 日： DD 编号：##</span>
								</td>
							</tr>
							<tr>
								<td  class="TableData" style="text-indent: 10px">编号样式显示例</td>
								<td class="TableData"  id="showExample" ></td>
							</tr>
						    
						    <tr>
								<TD class=TableHeader colSpan=2 noWrap>
								<img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
								<B style="color: #0050aa">固定编号样式设置</B></TD>
						     </tr>
						      
							<tr>
								<td class="TableData" style="text-indent:10px">累加方式</td>
								<td class="TableData" >
							
										<input name="additonalType" type="radio" checked="checked" value="1">自动累加&nbsp;&nbsp;&nbsp;
										<input name="additonalType" type="radio" value="2">按年累加&nbsp;&nbsp;&nbsp;
										<input name="additonalType" type="radio" value="3">按月累加&nbsp;&nbsp;&nbsp;
										<input name="additonalType" type="radio" value="4">按日累加
								
							</tr>
							<tr id="shutcutsTR">
								<td class="TableData" style="text-indent:10px">编号位数</td>
								<td class="TableData" >
								    <input name="numberBit"  id="digits"
								    style="width: 300px;height: 23px;"
									onblur="checkInt(this,'');" type="text" maxlength="5"
									value="3">
								</td>
							</tr>
							<tr id="shutcutsTR">
								<td class="TableData" style="text-indent:10px">当前编号</td>
								<td class="TableData" >
								    <input name="currentNumber"  id="nowNum"
								    style="width: 300px;height: 23px;"
									onblur="checkInt(this,'');" type="text" maxlength="5"
									value="0">
								</td>
							</tr>
											
						</table>

				<input type="hidden" id="uuid" name="uuid"/>
				<input name="wosid" type="hidden" value="EQASU0n7ba7dlz6zwptQjM">
			</form>
	</div>
</body>
<script>
$("#form1").validate();
</script>
</html>