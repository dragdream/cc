<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title>客户信息</title>
	
	<style>
	#table_name{
		width:60%;
		margin-top:20px;
	}
	#table_name tr td{
		text-align:center;
	}
	
.modal-test {
	width: 500px;
	height: 230px;
	position: absolute;
	display: none;
	z-index: 999;
}

.modal-test .modal-header {
	width: 100%;
	height: 50px;
	background-color: #8ab0e6;
}

.modal-test .modal-header .modal-title {
	color: #fff;
	font-size: 16px;
	line-height: 50px;
	margin-left: 20px;
	float: left;
}

.modal-test .modal-header .modal-win-close {
	color: #fff;
	font-size: 24px;
	line-height: 50px;
	margin-right: 20px;
	float: right;
	cursor: pointer;
}

.modal-test .modal-body {
	width: 100%;
	height: 120px;
	background-color: #f4f4f4;
}

.modal-test .modal-body ul {
	overflow: hidden;
	clear: both;
}

.modal-test .modal-body ul li {
	width: 510px;
	height: 30px;
	line-height: 30px;
	margin-top: 25px;
	margin-left: 20px;
}

.modal-test .modal-body ul li span {
	display: inline-block;
	float: left;
	vertical-align: middle;
}

.modal-test .modal-body ul li input {
	display: inline-block;
	/* float: left; */
	width: 400px;
	height: 25px;
}

.modal-test .modal-footer {
	width: 100%;
	height: 60px;
	background-color: #f4f4f4;
}

.modal-test .modal-footer input {
	margin-top: 12px;
	float: right;
	margin-right: 20px;
}
</style>
	
<script type="text/javascript" >
var sid = "<%=sid%>";
var xparent;
/**
   初始化列表
 */
function doInit(){
	getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType");
	//alert(getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType")[0].codeName);
	getCrmCodeByParentCodeNo("CUSTOMER_SOURCE","customerSource");
	getCrmCodeByParentCodeNo("INDUSTRY_TYPE","industry");
	getCrmCodeByParentCodeNo("UNIT_TYPE","unitType");//单位性质
	getCrmCodeByParentCodeNo("COMPANY_SCALE","companyScale");
	renderCustomerField();
	
	getInfoBySid(sid);
}
var managerPerName = '';
var managerPerId = "";
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmCustomerController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		//获取当前负责人
		managerPerName = data.managePersonName;
		managerPerId = data.managePersonId;
		customerStatus = data.customerStatus;
		addMenu(customerStatus);
		//alert(tools.jsonObj2String(data));
		}
}

//更多操作中根据客户状态追加操作
function addMenu(data){
	var str;
	$(".btn-content").empty();
	if(managerPerId==loginPersonId){
	if(data == "已分配"){
		$(".btn-group").show();
		str = '<li onclick="editCustomer();"><a href="javascript:void(0);" >编辑</a></li>'+
	    	  '<li class="modal-menu-test" onclick="setValue();$(this).modal();"><a href="javascript:void(0);" >更换负责人</a></li>'+
	    	  '<li onclick="cancel('+sid+');"><a href="javascript:void(0);" >作废</a></li>';
	}else if(data == "已作废"){
		$(".btn-group").show();
		str = '<li onclick="deleteById('+sid+');"><a href="javascript:void(0);">删除</a></li>'+
  	 		  '<li onclick="recovery('+sid+');"><a href="javascript:void(0);" >恢复</a></li>';
	    }
	}else{
		$(".btn-group").hide();
	}
	$(".btn-content").append(str);
}

//作废
function cancel(sid){
	 $.MsgBox.Confirm ("提示", "确定作废这个客户？",function(){
	 var url=contextPath+"/TeeCrmCustomerController/cancel.action?sid="+sid+"&customerStatus=2";
	 var jsonRs = tools.requestJsonRs(url,null);
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
				parent.xparent.datagrid.datagrid("unselectAll");
				parent.xparent.datagrid.datagrid('reload');
				parent.CloseWindow();
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	 });
}

/**
 * 删除
 */
function deleteById(id){
	$.MsgBox.Confirm("提示","确定删除删除这个客户？删除客户将删除此客户下关联的数据，删除后不可恢复！",function(){
		var url = contextPath+ "/TeeCrmCustomerController/delCustomer.action?sids="+sid;
		var json = tools.requestJsonRs(url, {sids : sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				parent.xparent.datagrid.datagrid("unselectAll");
				parent.xparent.datagrid.datagrid('reload');
				parent.CloseWindow();
			});
		} else {
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	
	});
}

//恢复
function recovery(sid){
	$.MsgBox.Confirm("提示","确定恢复到作废之前的状态？",function(){
		var url = contextPath+ "/TeeCrmCustomerController/recovery.action?sid="+sid;
		var json = tools.requestJsonRs(url, {sid : sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				window.location.href = "<%=contextPath%>/system/subsys/crm/core/customer/customerInfo.jsp?sid="+sid;
			});
		} else {
			$.MsgBox.Alert_auto("恢复失败！");
		}
	
	});
}




/**
 * 更新激活装填
 */

function updateFlagById(id ,flag)
{
	var url = "<%=contextPath %>/messageGroupManage/updateFlagById.action";
	var jsonRs = tools.requestJsonRs(url,{id:id ,groupflag: flag});
	if(jsonRs.rtState){
		parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);
		datagrid.datagrid('reload');
		//window.location.reload();
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
	
}

//动态渲染自定义字段
function renderCustomerField(){
	$("#customTbody").html("");
	/* if(projectTypeId==0){
		projectTypeId=$("#projectTypeId").val();
	} */
	var url=contextPath+"/TeeCrmCustomerController/getListFieldByCustomer.action";
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			var name="EXTRA_"+data[i].sid;
			if(data[i].filedType=="单行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td width=\"150\" style=\"text-indent:15px;line-height: 30px;\">"+data[i].extendFiledName+"：</td>"
						   +"<td align=\"left\" name='"+name+"' id='"+name+"'>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="多行输入框"){
				$("#customTbody").append("<tr>"
						   +"<td  width=\"150\" style=\"text-indent:15px;line-height: 30px;\">"+data[i].extendFiledName+"：</td>"
						   +"<td  align=\"left\" name='"+name+"' id='"+name+"'>"
						   +"</td>"
						   +"</tr>");
			}else if(data[i].filedType=="下拉列表"){
				/* var fieldCtrModel=data[i].fieldCtrModel;
				var j=tools.strToJson(fieldCtrModel); */
				if(data[i].codeType=="CRM系统编码"){
					$("#customTbody").append("<tr>"
							   +"<td width=\"150\" style=\"text-indent:15px;line-height: 30px;\">"+data[i].extendFiledName+"：</td>"
							   +"<td align=\"left\" name='"+name+"' id='"+name+"'>"
							   +"</td>"
							   +"</tr>");
					getCrmCodeByParentCodeNo(data[i].sysCode,name);
					//getSysCodeByParentCodeNo(j.value,name);
				}else if(data[i].codeType=="自定义选项"){
					//var values=j.value;
					var optionNames=data[i].optionName.split(",");
					var optionValues=data[i].optionValue.split(",");
					$("#customTbody").append("<tr>"
							   +"<td  width=\"150\" style=\"text-indent:15px;line-height: 30px;\">"+data[i].extendFiledName+"：</td>"
							   +"<td  align=\"left\" name='"+name+"' id='"+name+"'>"
							   +"</td>"
							   +"</tr>");
				}
				
			}
			
		}
		
	}
}

//编辑
function editCustomer(){
	window.location.href = "<%=contextPath%>/system/subsys/crm/core/customer/addOrEditCustomer.jsp?sid=" + sid;
}

//返回
function back(){
	parent.window.location.href = "<%=contextPath%>/system/subsys/crm/core/customer/customerList.jsp";
}

//验证模态框负责人必填
function check(){
	if($("#managerPerName").val()=="" || $("#managerPerName").val()=="null" || $("#managerPerName").val()==null){
		$.MsgBox.Alert_auto("请选择人员！");
		return false;
	}
	return true;
}
//更换负责人
function changeManager(){
	if(check()){
		var url=contextPath+"/TeeCrmCustomerController/changeManage.action?sid="+sid;
	    var param=tools.formToJson("#form1");
		var jsonRs = tools.requestJsonRs(url,param);
		if(jsonRs.rtState){
			$(".modal-win-close").click();
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
				window.location.href = "<%=contextPath%>/system/subsys/crm/core/customer/customerInfo.jsp?sid="+sid;
			});
			//window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
		
	}
}

//更换负责人前获取当前负责人
function setValue(){
	$("#managerPerName").val(managerPerName);
	$("#managerPerId").val(managerPerId);
}
</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;" onload="doInit();">

<div id="toolbar" class = "clearfix">
     <div class="fl" style='width:90%'>
          <table id='table_name' style='text-align:center;line-height:25px;'>
	          <tbody>
	          	<tr>
	          		<td rowspan = '2' style='font-size:18px;font-weight:bold;'>汇总信息</td>
	          		<td style='color:#aaa;'>商机总额</td>
	          		<td style='color:#aaa;'>订单总额</td>
	          		<td style='color:#aaa;'>退货总额</td>
	          		<td style='color:#aaa;'>回款总额</td>
	          		<td style='color:#aaa;'>退款总额</td>
	          		<td style='color:#aaa;'>待回款总额</td>
	          	</tr>
	          	<tr style='text-align:center;font-family: MicroSoft YaHei;'>
	          		<td><span style="font-size: 16px;font-weight:bold;" id='forecast' name="forecast"></span> <span style='color:#aaa;'>元</span></td>
	          		<td><span style="font-size: 16px;font-weight:bold;" id='orderCount' name="orderCount"></span> <span style='color:#aaa;'>元</span></td>
	          		<td><span style="font-size: 16px;font-weight:bold;" id='returnOrderCount' name="returnOrderCount"> </span><span style='color:#aaa;'>元</span></td>
	          		<td><span style="font-size: 16px;font-weight:bold;" id='payBackCount' name="payBackCount"></span> <span style='color:#aaa;'>元</span></td>
	          		<td><span style="font-size: 16px;font-weight:bold;" id='drawBackCount' name="drawBackCount"></span> <span style='color:#aaa;'>元</span></td>
	          		<td><span style="font-size: 16px;font-weight:bold;" id='payBackCount2' name="payBackCount2"></span> <span style='color:#aaa;'>元</span></td>
	          	</tr>
	          	
	          </tbody>
          </table>
     
     </div>
     <div class="btn-group fr" style="margin-right: 20px;margin-top: 20px;display: none;">
		  <button type="button" class="btn-win-white btn-menu" >
		    更多操作<span class="caret-down"></span>
		  </button>
		  <ul class="btn-content">
		  </ul>
		 
	</div>
</div>

	<table style="width: 100%;margin-top: 10px;">
		<tr>
		   <td colSpan=2 noWrap>
		    <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">基本信息</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		    
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				客户名称：
			</td>
		    <td name ="CUSTOMER_NAME" id="CUSTOMER_NAME">
            </td>
		</tr>
		<tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">客户编号：</td>
            <td align="left" id="groupSelect" name="CUSTOMER_NUM" id="CUSTOMER_NUM">
           </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">客户级别：</td>
		   <td id="customerType" name="customerType">
		   </td>
        </tr>
        <tr>
            <td width="150px;" style="text-indent: 15px;line-height: 30px;">来源：</td>
			<td id="customerSource" name ="customerSource">
			</td>
        </tr>
        <tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">所属行业：</td>
			<td name="industryType" id="industryType">
			</td>
        </tr>
        <tr>
		    <td width="150" style="text-indent:15px;line-height: 30px;">客户所属区域：</td>
		    <td name="address" id="address">
		    </td>
	   </tr>
	  <!--  <tr>
	       <td class="TableData" width="150" style="text-indent:15px">详细地址：</td>
	       <td class="TableData">
				<input type="text" name="detailAddress" id="detailAddress"/>
		   </td>
	   </tr> -->
	   	   <tr>
		<td width="150" style="text-indent:15px;line-height: 30px;">定位：</td>
		<td name="LOCATE_INFORMATION" id="LOCATE_INFORMATION">
		</td>
	  </tr>
	   <tr>
	        <td width="150px" style="text-indent:15px;line-height: 30px;">公司规模：</td>
			<td name="companyScale" id="companyScale">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">客户性质：</td>
			<td id="type" name="type">
				</select>
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">单位性质：</td>
			<td id="unitType" name="unitType">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">公司地址：</td>
			<td name="COMPANY_ADDRESS" id="COMPANY_ADDRESS">
			</td>
	   </tr>
	   <tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">联系电话：</td>
			<td id="COMPANY_PHONE" name="COMPANY_PHONE">
			</td>	   
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">邮编：</td>
			<td id="COMPANY_ZIPCODE" name="COMPANY_ZIPCODE">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">公司网址：</td>
			<td id="COMPANY_URL" name="COMPANY_URL">
			</td>
	   </tr>
	  <tr>
	      <td  width="150" style="text-indent:15px;line-height: 30px;">负责人：</td>
			<td name="managePersonName" id="managePersonName">
			</td>
	  </tr>
	  <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">共享人员：</td>
			<td name="sharePersonNames" id="sharePersonNames">
			
			</td>
	 </tr >
	 </table>
	  </br>
	 <table style="width: 100%;">
    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">系统信息</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">状态：
			</td>
		    <td name="customerStatus" id="customerStatus">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">创建人：
			</td>
		    <td name="addPersonName" id="addPersonName">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">创建时间：
			</td>
		    <td name="addTime" id="addTime">
            </td>
	</tr>

</table>
 </br>
	 <table style="width: 100%;">
  <thead>
     <tr>
		<TD colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		&nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">扩展字段</span>
	    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
     </tr>
  </thead>
  <tbody id="customTbody" >
     
  </tbody>
</table> 
</br>
</br>

	<form id="form1" name="form1">
		<div class="modal-test">
			<div class="modal-header">
				<p class="modal-title">更换负责人</p>
				<span class="modal-win-close">×</span>
			</div>

			<div class="modal-body">
				<table class="TableBlock" width="100%">
					<br/>
				 <tr>
				      <td style="text-indent:15px">请选择：</td>
						<td >
							<input type="hidden" name="managerPerId" id="managerPerId"> 
							<input required name="managerPerName" id="managerPerName" style="height:23px;width:300px;border: 1px solid #dadada;font-family: MicroSoft YaHei;font-size: 12px;" readonly />&nbsp;&nbsp;
							   <span class='addSpan'>
						               <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectSingleUser(['managerPerId', 'managerPerName'])" value="选择"/>
							           &nbsp;&nbsp;&nbsp;
							           <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('managerPerId', 'managerPerName')" value="清空"/>
						       </span>
						</td>
	             </tr>
				</table>
			</div>
			<div class="modal-footer clearfix">
				<input type="reset" class="btn-alert-gray" value="取消" onclick="$('.modal-win-close').click()" />
				<input type="button" class="modal-save btn-alert-blue" onclick="changeManager();" value="确定" />
			</div>
		</div>
	</form>


</body>

</html>