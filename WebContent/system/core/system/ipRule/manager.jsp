<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>

	<title>IP规则管理</title>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
	
	<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	
<script type="text/javascript" >

$.extend($.fn.validatebox.defaults.rules, {
	checkIpComp: {   
	 	validator: function(value, param){   
	 		var endIpNum = ip2Number(value);
	 		var beginIpNum = ip2Number(param[0]);
  			return  endIpNum >= beginIpNum ; 
 	    },  
  	   message: '起始IP不能大于结束IP'
    } 
});
/**
 * 将IP转成数字进行比较大小
 */
function ip2Number(ip)  {
	  var ipArray = ip.split(".");
	  var lvl = [255*255*255 , 255*255 ,255 , 1];
	  var value = 0;
	  for(var i =0; i<ipArray.length ; i++ ){
		  value += ipArray[i] * lvl[i];
	  }
	  return value;
}
	
function doInit(){
	var url = "<%=contextPath %>/ipRuleController/getAllIpRule.action";
	//var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data && data.length > 0){
			var tableStr = "<table class='TableList' width='90%' align='center' >"
			      + " <tbody id='tbody'>"
			      + "<tr  class='TableHeader'>"
			      + "<td > 起始IP</td>"
			      + "<td > 结束IP</td>"
			      + "<td > 规则类型</td>"
			      + "<td > 备注</td>"
			      + "<td > 操作</td>"
			      +"</tr>";
			for(var i = 0;i<data.length ; i++){
				var obj = data[i];
				var ipType = obj.ipType;
				var ipTypeDesc = "系统登录规则";
				if(ipType == 1){
					ipTypeDesc = "考勤限制规则";
				}
				var beginIp =  obj.beginIp || "";
				var endIp =  obj.endIp || "";
				var remark =  obj.remark || "";
				tableStr = tableStr + "<tr>"
				+"<td width='130px;'>" + beginIp  + "</td>"
				+"<td width='130px;'>" + endIp + "</td>"
				+"<td width='80px;'>" + ipTypeDesc + "</td>"
				+"<td>" + remark + "</td>"

				+"<td width='150px;'>" 

				+ "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='toAddUpdate(" + obj.sid  + ")'>编辑  </a>"
				+ "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='deleteById(" + obj.sid + ")'>删除  </a>"
				+ "</td>"
				
				+ "</tr>";
				
			}
			  tableStr = tableStr + " </tbody></table>";
			$("#ipRuleList").append(tableStr);
		}else{
			messageMsg("未找到相关数据", "ipRuleList" ,'' ,280);
	
		}
	
		
		
		
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 跳转去新增页面
 */
function toAddUpdate(id)
{
	$("#formId").show();
	var title = "新建IP规则";
	setFormClearValue($('#form1'));
	if(id){
		getById(id );
		title = "编辑IP规则";
	}
	$('#addOrUpdateIpRule').dialog({
		 title:title,
		 width: 520,
		 height: 300,
		 closed: false,
		 cache: false,
		 modal: true
	 });
   $('#addOrUpdateIpRule').dialog('open');
   
   
}

function closeDia(){
	 $('#addOrUpdateIpRule').dialog('close');
}

/**
 * 保存
 */
function doSave(){
	if(checkForm()){
		var url = "<%=contextPath %>/ipRuleController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url , para);
		if(jsonRs.rtState){
			alert("保存成功！");
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	 return $("#form1").form('validate'); 
}

/**
 * 删除
 */
function deleteById(id)
{
	if(confirm("确定删除所选记录,删除后将不可恢复！")){
		var url = "<%=contextPath %>/ipRuleController/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{id:id});
		if(jsonRs.rtState){
			alert("删除成功！");
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
}

/**
 * 更新激活装填
 */
function getById(id )
{
	var url = "<%=contextPath %>/ipRuleController/getById.action";
	var jsonRs = tools.requestJsonRs(url,{id:id });
	if(jsonRs.rtState){
		 var obj = jsonRs.rtData;
		 $("#id").val(id);
		 bindJsonObj2Cntrl(obj);
	}else{
		alert(jsonRs.rtMsg);
	}
	
}

</script>
</head>

<body onload="doInit()" style="padding-top:10px;">
   <table border="0" width="100%" cellspacing="0" cellpadding="3"  align="">
    <tr>
      <td class="">
      <%--   <img src="<%=imgPath %>/notify_new.gif" align="absmiddle"> --%>
        <span class="Big3">
        	新建IP规则
         </span>
       </td>
    </tr>
  </table><br>
  
  <div align='center'>
     <input type='button' value='新建IP规则' class='btn btn-primary' onclick='toAddUpdate();'/>

</div>
  <br>
   <table border="0" width="100%" cellspacing="0" cellpadding="3"  align="">
    <tr>
      <td class="Big">
      <%--   <img src="<%=imgPath %>/notify_open.gif" align="absmiddle"> --%>
        <span class="Big3">
        	  访问控制列表
         </span>
       </td>
    </tr>
  </table>
  
  <div style="padding-top:10px;" id="ipRuleList">
     
  

  </div>



<div id="addOrUpdateIpRule" class="easyui-dialog"   closed="true" >
<div id="formId" style="display:none;">
<br>
	<form action="" method="post" id="form1" name="form1">
	   <table class="TableBlock" width="90%" align="center">
	     <tr>
	      <td class="TableContent" width=80>起始IP: <font color='red'>* </font></td>
	      <td class="TableData">
	      	<input type="text" name="beginIp" id="beginIp" maxlength="30" class="easyui-validatebox BigInput"   required="true" validType ="ipRule[]" /> (例如：192.168.0.100)
	      	
	      	
	      </td>
	    </tr>
	     <tr>
	      <td class="TableContent" width=80>结束IP: <font color='red'>* </font></td>
	      <td class="TableData">
	      	<input type="text" name="endIp" id="endIp" maxlength="30" class="easyui-validatebox BigInput"  required="true"  validType="ipRule[]&checkIpComp[$('#beginIp').val()]" /> (例如：192.168.0.100)
	      </td>
	    </tr>    
	     <tr>
	      <td class="TableContent">访问规则类型:</td>
	      <td class="TableData">
	      	 <select name="ipType" class="BigSelect">
	      	 	<option value="0">系统登录规则</option>
	      	 	<option value="1">考勤限制规则</option>
	      	 </select>
	      
	      </td>
	    </tr> 
	     <tr>
	      <td class="TableContent">备注:</td>
	      <td class="TableData">
	      
	      	<textarea rows="3" cols="40" name="remark" class="BigTextarea" maxlength="500"></textarea>
	      </td>
	    </tr> 
	     <tr>
	      <td class="TableContent" colspan="2" align='center'>
	    	  	<input type="hidden" name="id" id="id" >
	      		<input type="button" value="保存" class='btn btn-primary' onclick="doSave();"/>
	      		<input type="button" value="关闭" class='btn btn-primary' onclick="closeDia();"/>
	      </td>

	    </tr> 
	  </table>
  </form>
</div>
 </div>

</body>
</html>