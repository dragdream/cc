<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
	initAddressCtr();
	getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType");
	getCrmCodeByParentCodeNo("INDUSTRY_TYPE","industry");
	getCrmCodeByParentCodeNo("COMPANY_SCALE","companyScale");
	getCrmCodeByParentCodeNo("CUSTOMER_SOURCE","customerSource");
	getCrmCodeByParentCodeNo("UNIT_TYPE","unitType");//单位性质

	renderCustomerField();

 	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	if(sid>0){
		getInfoBySid(sid);
	}else{
		
	}

}

//编辑的时候获取初始化数据
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmCustomerController/getById.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		//alert(tools.jsonObj2String(data));
		 if(data.filedType=="下拉列表"){
				$("#optionNameTr").show();
				$("#optionValueTr").show();  
			$("#codeTypeTr").show();
			 if(data.codeType=="CRM系统编码"){
				$("#sysCodeTr").show();
				$("#optionNameTr").hide();
				$("#optionValueTr").hide();
			}else if(data.codeType=="自定义选项"){
				$("#sysCodeTr").hide();
			} 
		 }else{ 
			 $("#codeTypeTr").hide();
			$("#sysCodeTr").hide(); 
		 	$("#optionNameTr").hide();
			$("#optionValueTr").hide();
			
		} 
		
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}

}


/**
 * 保存
 */
function save(callback){
	var url="";
	//编辑客户
	if(sid>0){
		if(check()&&checkCustomerIsExist()){
		    url=contextPath+"/TeeCrmCustomerController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	    $.MsgBox.Alert_auto("编辑成功！",function(){
				        window.location.href=contextPath+"/system/subsys/crm/core/customer/customerInfo.jsp?sid="+sid;
			    	    });
						   parent.xparent.datagrid.datagrid("unselectAll");
						   parent.xparent.datagrid.datagrid('reload');
			          }else{
			        	  $.MsgBox.Alert(json.rtMsg);
			          }	
		}
	}else{//添加客户
		if(check()&&checkCustomerIsExist()){
			url=contextPath+"/TeeCrmCustomerController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	parent.$.MsgBox.Alert_auto("添加成功！",function(){
			         window.location.href=contextPath+"/system/subsys/crm/core/customer/cutomerIndex.jsp";
		    	});
		         };	
		}
	    
	};
	
}

/**
 * 验证
 */
function check(){
	var customerName=$("#customerName").val();
	//var customerNum=$("#customerNum").val();
	var managePersonId = $("#managePersonId").val();
	if(customerName==""||customerName==null){
		$.MsgBox.Alert_auto("请填写客户名称！");
		return false;	
	}
	/* if(customerNum==""||customerNum==null){
		$.MsgBox.Alert_auto("请填写客户编号！");
		return false;	
	} */
	if(managePersonId==""||managePersonId==null){
		$.MsgBox.Alert_auto("请选择客户负责人！");
		return false;	
	}
	return true;
}

//判断客户名称是否已经存在
function checkCustomerIsExist(){
	var customerName=$("#customerName").val();
	var url=contextPath+"/TeeCrmCustomerController/isExistCustomer.action";
	var json=tools.requestJsonRs(url,{customerName:customerName,sid:sid});
	if(json.rtState){
		var data=json.rtData;
		if(data>=1){
			$.MsgBox.Alert_auto("客户名称已存在，请重新填写！");
			return false;
		  }else{
				return true;
			}
		}
}


//初始化级联地址控件
function initAddressCtr(){
	addressInit("province","city","district");
}

//地图选点
function selectMapPoint(){
	var province=$("#province").val();
	var district=$("#district").val();
	var city=$("#city").val();
	var addr=province+city+district;
	var url=contextPath+"/system/subsys/crm/core/customer/map.jsp?addr="+addr;
	openFullWindow(url);
}


function goBack(){
	var url="";
	if(sid>0){
		url= contextPath+"/system/subsys/crm/core/customer/customerInfo.jsp?sid="+sid;
	}else{
	    url= contextPath+"/system/subsys/crm/core/customer/cutomerIndex.jsp";
	}
	location.href=url;
}

//动态渲染自定义字段
function renderCustomerField(){
	$("#customTbody").html("");
	var url=contextPath+"/TeeCrmCustomerController/getListFieldByCustomer.action";
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			var name="EXTRA_"+data[i].sid;
			if(data[i].filedType=="单行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\">"
						   +    "<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" />"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="多行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
						   +"<td  class=\"TableData\" align=\"left\">"
						   +    "<textarea  type=\"text\" rows=\"6\" name='"+name+"' id='"+name+"' style=\"width: 550px\" ></textarea>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="下拉列表"){
				/* var fieldCtrModel=data[i].fieldCtrModel;
				var j=tools.strToJson(fieldCtrModel); */
				if(data[i].codeType=="CRM系统编码"){
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\">"
							   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
							   +"</td>"
							   +"</tr>");
					getCrmCodeByParentCodeNo(data[i].sysCode,name);
					//getSysCodeByParentCodeNo(j.value,name);
				}else if(data[i].codeType=="自定义选项"){
					//var values=j.value;
					var optionNames=data[i].optionName.split(",");
					var optionValues=data[i].optionValue.split(",");
					$("#customTbody").append("<tr>"
							   +"<td  class=\"TableData\" width=\"150\" style=\"text-indent:15px\">"+data[i].extendFiledName+"：</td>"
							   +"<td  class=\"TableData\" align=\"left\">"
							   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
							   +"</td>"
							   +"</tr>");
					for(var j=0;j<optionNames.length;j++){
						$("#"+name).append("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
					}
					
				}
				
			}
			
		}
		
	}
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_bjkh.png">
		<span class="title">新增/编辑客户</span>
	</div>
   <div class="fr right">
      <input type="button" value="保存" class="btn-win-white" onclick="save();"/>
     <!--  <input type="button" value="提交" class="btn-win-white" onclick="save(2);"/> -->
      <input type="button" value="返回" class="btn-win-white" onclick="goBack();"/>
   </div>
</div>

<form method="post" name="form1" id="form1" >
	<table class="TableBlock_page" width="60%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">固定字段</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				客户名称<span style="color:red;font-weight:bold;">&nbsp;&nbsp;*</span>：
			</td>
		    <td class="TableData">
                <input type='text' name="customerName" id="customerName" style="height: 23px;width: 350px"/>
            </td>
		</tr>
		<tr>
            <td class="TableData" width="150" style="text-indent:15px">客户编号：</td>
            <td class="TableData" align="left" id="groupSelect">
                <input type="text" name="customerNum" id="customerNum" style="height: 23px;width: 350px" />
           </td>
        </tr>
        <tr>
           <td class="TableData" width="150" style="text-indent:15px">客户级别：</td>
		   <td class="TableData">
				<select id="customerType" name="customerType" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
		   </td>
        </tr>
        <tr>
            <td class="TableData" width="150px;" style="text-indent: 15px;">来源：</td>
			<td class="TableData">
				<select id="customerSource" name="customerSource" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
        </tr>
        <tr>
           <td class="TableData" width="150px;" style="text-indent: 15px;">所属行业：</td>
			<td class="TableData">
				<select id="industry" name="industry" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
        </tr>
        <tr>
		    <td  class="TableData" width="150" style="text-indent:15px">客户所属区域：</td>
		    <td  class="TableData">
		        <select id="province" name="province" style="height: 23px;width: 110px"></select>&nbsp;&nbsp;
		        <select id="city" name="city" style="height: 23px;width: 110px"></select>&nbsp;&nbsp;
		        <select id="district" name="district" style="height: 23px;width: 110px"></select>
		    </td>
	   </tr>
	  <!--  <tr>
	       <td class="TableData" width="150" style="text-indent:15px">详细地址：</td>
	       <td class="TableData">
				<input type="text" name="detailAddress" id="detailAddress"/>
		   </td>
	   </tr> -->
	   	   <tr>
		<td  class="TableData" width="150" style="text-indent:15px">定位：</td>
		<td  class="TableData">
		   <input type="text" name="addressDesc" id="addressDesc" style="height: 23px;width: 350px"  />
		   &nbsp;&nbsp;<a href="#" onclick="selectMapPoint()">地图选点</a>
		   <input type="hidden" name="coordinate" id="coordinate" />
		</td>
	  </tr>
	   <tr>
	        <td class="TableData" width="150px" style="text-indent:15px">公司规模：</td>
			<td class="TableData">
				<select id="companyScale" name="companyScale" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">客户性质：</td>
			<td class="TableData">
				<select id="type" name="type" class="BigSelect">
					<option value="1">客户</option>
					<option value="2">供应商</option>
				</select>
			</td>
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">单位性质：</td>
			<td class="TableData">
				<select id="unitType" name="unitType" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>" >
					<option value="">请选择</option>
				</select>
			</td>
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">公司地址：</td>
			<td class="TableData">
				<input type="text" class="BigInput easyui-validatebox" name="companyAddress" id="companyAddress" maxlength="100"/>
			</td>
	   </tr>
	   <tr>
            <td class="TableData" width="150" style="text-indent:15px">联系电话：</td>
			<td class="TableData">
				<input type="text" class="BigInput easyui-validatebox" name="companyPhone" id="companyPhone" validType="maxLength[100]"/>
			</td>	   
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">邮编：</td>
			<td class="TableData">
				<input type="text" name="companyZipCode" id="companyZipCode"  class="BigInput easyui-validatebox" validType="zipcode" > 
			</td>
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">公司网址：</td>
			<td class="TableData">
				<input type="text" name="companyUrl" id="companyUrl" class="BigInput easyui-validatebox" maxlength="100" /> 
			</td>
	   </tr>
	  <tr>
	      <td class="TableData" width="150" style="text-indent:15px">负责人：</td>
			<td class="TableData">
				<input type="hidden" name="managePersonId" id="managePersonId"> 
				<input name="managePersonName" id="managePersonName" style="height:23px;width:350px;border: 1px solid #dadada;"   class="BigInput" wrap="yes" readonly />
				   <span class='addSpan'>
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectSingleUser(['managePersonId', 'managePersonName'],'14')" value="选择"/>
				           &nbsp;&nbsp;
				           <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('managePersonId', 'managePersonName')" value="清空"/>
			       </span>
			</td>
	  </tr>
	  <tr>
			<td  class="TableData" width="150" style="text-indent:15px">共享人员：</td>
			<td>
				<textarea id="sharePersonNames"  name ="sharePersonNames" style="width:350px;height:60px;" class="BigTextarea" readonly></textarea>
				<input type="hidden" id="sharePersonIds" name="sharePersonIds"/>
				  <span class='addSpan'>
			           <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectUser(['sharePersonIds','sharePersonNames'])" value="选择"/>
				           &nbsp;&nbsp;
				       <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('sharePersonIds','sharePersonNames')" value="清空"/>
			       </span>
			</td>
	 </tr >
	 </table>
	 <table class="TableBlock_page" width="60%" align="center" id="customTable">
  <thead>
     <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">自定义字段</B></TD>
     </tr>
  </thead>
  <tbody id="customTbody" >
     
  </tbody>
</table> 
</form>
</body>
</html>