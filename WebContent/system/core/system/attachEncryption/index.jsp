<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>附件加密设置</title>
<script type="text/javascript">
function doInit(){
	var url = "<%=contextPath %>/sysPara/getSysParaList.action";
	var paraNames = "ATTACH_ENCRY,ENCRY_MODULES,ENCRY_FILES,ENCRY_TYPE,ENCRY_SIZE,ENCRY_ALGO";
	var para =  {paraNames:paraNames} ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		var modules="";
		for(var i = 0;i<dataList.length;i++){
			var data = dataList[i];
			 setValue(data);
			 if(data.paraName=="ENCRY_MODULES" && data.paraValue!=""){
				  modules = data.paraValue.split(",");
			 }
			 if(data.paraName=="ENCRY_TYPE" && data.paraValue=="2"){
				 $('#encrySizeSpan').show();
			 }
		}
		for(var i = 0;i<modules.length;i++){
			 $("input[name=MODULES]").each(function(){
				 if($(this).val()==modules[i]){
					 $(this).attr("checked","checked");
				 }
			 });
	 	}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
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
	if (checkFrom()){
		var url = "<%=contextPath %>/sysPara/addOrUpdateSysParaList.action";
		setEncryModules();
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
		    $.MsgBox.Alert_auto("保存成功！",function(){	
		    	window.location.reload();
		    });
		    
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
	
}
function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function checkFrom() {
	/*   return $("#form1").form('validate'); */ 
	return  true;
}

function setEncryModules(){
	var selectedValue="";
	$("input[name=MODULES]").each(function(){
		if($(this).attr("checked")) //如果被选中
			selectedValue += $(this).val() + ",";
	});
	$("#ENCRY_MODULES").val(selectedValue);
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/icon_fjjmsz.png" alt="">
	   &nbsp;<span class="title">附件加密设置</span>
	   
	   <button class="btn-win-white fr" onclick="doSave()">保存</button>	   
	</div>

<form name="form1" id="form1" method="post">
	<table class="TableBlock_page" width="100%">
	<tr class="TableHeader" align="center">
	    <td width="15%" class="TableData"><b>选项</b></td>
	    <td class="55%"><b>参数</b></td>
	    <td width="30%" class="TableData"><b>备注</b></td>
	  </tr>
	  <tr align="center" >
	    <td  class="TableData TableBG">启用附件加密</td>
	    <td align="left" class="TableData">
	       <input type="radio" name="ATTACH_ENCRY" id="ATTACH_ENCRY1" value="1" ><label for="ATTACH_ENCRY1">是</label>
	       &nbsp;&nbsp;&nbsp;
	       <input type="radio" name="ATTACH_ENCRY" id="ATTACH_ENCRY0" value="0" ><label for="ATTACH_ENCRY0">否</label>
	    </td>
	    <td  align="left" class="TableData">
	       是否启用附件加密
	    </td>
	  </tr>
	  <tr align="center">
	    <td class="TableData TableBG" width="140">加密模块</td>
	    <td  class="TableData" align="left">
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES1" value="notify"><label for="ENCRY_MODULES1">通知公告</label>
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES2" value="news" ><label for="ENCRY_MODULES2">新闻</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES3" value="email" ><label for="ENCRY_MODULES3">内部邮件</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES4" value="meeting" ><label for="ENCRY_MODULES4">会议</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES5" value="fileNetdisk" ><label for="ENCRY_MODULES5">公共网盘</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES6" value="workFlow" ><label for="ENCRY_MODULES6">工作流程</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES7" value="workFlowFeedBack" ><label for="ENCRY_MODULES7">工作流会签</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES8" value="workFlowDoc" ><label for="ENCRY_MODULES8">工作流文档</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES12" value="diary" ><label for="ENCRY_MODULES12">工作日志</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES13" value="vehicle" ><label for="ENCRY_MODULES13">车辆管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES14" value="fileNetdiskPerson" ><label for="ENCRY_MODULES14">个人网盘</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES15" value="vote" ><label for="ENCRY_MODULES15">投票</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES17" value="exam" ><label for="ENCRY_MODULES17">在线考试</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES18" value="assetsRecord" ><label for="ENCRY_MODULES18">固定资产</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES19" value="assetsInfo" ><label for="ENCRY_MODULES19">固定资产信息</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES20" value="dam" ><label for="ENCRY_MODULES20">公文档案</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES21" value="book" ><label for="ENCRY_MODULES21">图书管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES22" value="recruitRequirements" ><label for="ENCRY_MODULES22">招聘需求</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES23" value="recruitPlan" ><label for="ENCRY_MODULES23">招聘计划</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES24" value="trainingPlan" ><label for="ENCRY_MODULES24">培训计划</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES25" value="coWork" ><label for="ENCRY_MODULES25">任务管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES26" value="crm" ><label for="ENCRY_MODULES26">客户关系管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES27" value="hrPool" ><label for="ENCRY_MODULES27">人力资源</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES28" value="crmProducts" ><label for="ENCRY_MODULES28">CRM产品管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES29" value="crmAfterSaleServ" ><label for="ENCRY_MODULES29">CRM售后管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES30" value="crmCustomerCantract" ><label for="ENCRY_MODULES30">CRM合同管理</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES31" value="crmCompetitor" ><label for="ENCRY_MODULES31">CRM竞争对手</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES32" value="contract" ><label for="ENCRY_MODULES32">合同信息</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES34" value="topic" ><label for="ENCRY_MODULES34">讨论区</label> &nbsp;
	       <input type="checkbox" name="MODULES" id="ENCRY_MODULES35" value="topicReply" ><label for="ENCRY_MODULES35">讨论区回复</label> &nbsp;
	        <input type="hidden"  id="ENCRY_MODULES" name="ENCRY_MODULES" class='BigInput'>
	    </td>
	    <td  class="TableData" width="300" align="left" >
	    	  选择需要加密的模块
	    </td>
	  </tr>
	  <tr align="center" >
	    <td width="140" class="TableData TableBG">文件类型</td>
	    <td align="left" class="TableData">
	       <textarea type="text" name="ENCRY_FILES" id="ENCRY_FILES" class='BigTextarea' style="width: 500px;height: 70px" ></textarea>
	    </td>
	    <td width="300" align="left" class="TableData">
	      仅对设置了后缀名的文件进行加密
	    </td>
	  </tr>
	  <tr align="center" >
	    <td width="140" class="TableData TableBG">附件加密方式</td>
	    <td align="left" class="TableData">
	   		 <input type="radio" name="ENCRY_TYPE" id="ENCRY_TYPE1" value="1" onfocus="$('#encrySizeSpan').hide();"><label for="ENCRY_TYPE1">部分加密</label>&nbsp;&nbsp;&nbsp;
	       	 <input type="radio" name="ENCRY_TYPE" id="ENCRY_TYPE2" value="2" onfocus="$('#encrySizeSpan').show();"><label for="ENCRY_TYPE2">完整加密</label>
	         <span id='encrySizeSpan' style='display:none;'>&nbsp;&nbsp;&nbsp;附件大小限制<Input type="text" name="ENCRY_SIZE" id="ENCRY_SIZE" class="easyui-validatebox BigInput" validType='integeZero[]' style="text-align:center;" value='0'>KB</span>
	    </td>
	    <td width="300" align="left" class="TableData">
	      部分加密：只对文件部分内容进行加密，速度快，效率高<br/>
	     全部加密：对整个文件进行加密，如果文件大的话，加密慢，效率低，但安全性高 <br/>
	      附件大小限制：当选择全部加密时，仅对文件大小不超过该值的内容进行加密，0为不限制
	    </td>
	  </tr>
	  <tr align="center">
	    <td width="140" class="TableData TableBG">附件加密算法</td>
	    <td align="left" class="TableData">
	       <input type="radio" name="ENCRY_ALGO" id="ENCRY_ALGO0" value="XXTEA128" ><label for="ENCRY_ALGO0">XXTEA128</label>&nbsp;&nbsp;&nbsp;
	       <input type="radio" name="ENCRY_ALGO" id="ENCRY_ALGO1" value="XXTEA256" ><label for="ENCRY_ALGO1">XXTEA256</label>&nbsp;&nbsp;&nbsp;
	       <input type="radio" name="ENCRY_ALGO" id="ENCRY_ALGO2" value="BLOWFISH128" ><label for="ENCRY_ALGO2">BLOWFISH128</label>&nbsp;&nbsp;&nbsp;
	       <input type="radio" name="ENCRY_ALGO" id="ENCRY_ALGO3" value="BLOWFISH256" ><label for="ENCRY_ALGO3">BLOWFISH256</label>
	    </td>
	    <td width="250" align="left" class="TableData">
	    XXTEA128:速度快，效率高<br/>
	    XXTEA256:速度快，效率高<br/>
	    BLOWFISH128:标准的BLOWFISH算法，效率适中<br/>
	    BLOWFISH256:标准的BLOWFISH算法，效率适中<br/>
	    </td>
	  </tr>
	 
	</table>
	</form>


</body>
</html>
