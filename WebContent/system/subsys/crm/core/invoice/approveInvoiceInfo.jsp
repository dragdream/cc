<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String model = TeeAttachmentModelKeys.CRM_INVOICE;
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<title>开票信息</title>
<style>

</style>
	
<script type="text/javascript" >
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
/**
   初始化列表
 */
function doInit(){
	getCrmCodeByParentCodeNo("INVOICE_TYPE","invoiceType");//开票类型
	getInvoiceInfoBySid(sid);
	
}

var managerPerName = '';
var managerPerId = "";
var billingPersonIds = "";
function getInvoiceInfoBySid(sid){
	var url=contextPath+"/TeeCrmInvoiceController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		managerPerName = data.managePersonName;
		managerPerId = data.managePersonId;
		billingPersonIds = data.invoiceFinancialId;
		var status = data.invoiceStatusDesc;
		if(status=="已驳回"){
			$("#reasons").show();
		}

		//附件
		var  attachmodels = data.attachmodels;
		for(var i=0;i<attachmodels.length;i++){
			var temp = attachmodels[i];
			temp["priv"] = 3;
			var fileItem = tools.getAttachElement(temp);
			$("#attachments").append(fileItem);
		}

		addMenu(data.invoiceStatusDesc);
		}
}

//更多操作中根据开票状态追加操作
function addMenu(data){
	var str="";
	$(".btn-content").empty();
    if(loginPersonId==billingPersonIds){
		if(data == "待开票"){
			$(".btn-group").show();
			str = '<li onclick="edit();"><a href="javascript:void(0);">编辑</a></li>'+
			      '<li onclick="agree('+sid+');"><a href="javascript:void(0);">同意</a></li>'+
			      '<li onclick="reject('+sid+');"><a href="javascript:void(0);">驳回</a></li>';
			}else{
				$(".btn-group").hide();
			}
	}else{
		$(".btn-group").hide();
	}
	
	$(".btn-content").append(str);
}


//同意
function agree(sid){
	 $.MsgBox.Confirm ("提示", "确定开票信息无误？",function(){
		 var url=contextPath+"/TeeCrmInvoiceController/agree.action?sid="+sid+"&invoiceStatus=2";
		 var jsonRs = tools.requestJsonRs(url,null);
			if(jsonRs.rtState){
				opener.$.MsgBox.Alert_auto("操作成功！");
				opener.datagrid.datagrid("unselectAll");
				opener.datagrid.datagrid('reload');
				CloseWindow();
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
		 });
}

//驳回
function reject(sid){
		var title = "驳回";
		  var url = contextPath + "/system/subsys/crm/core/invoice/reject.jsp?sid="+sid;
		  bsWindow(url ,title,{width:"600",height:"300",buttons:
				[
				 {name:"确定",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					cw.doSaveOrUpdate(function(){
						//$.MsgBox.Alert_auto(json.rtMsg);
						opener.$.MsgBox.Alert_auto("操作成功！");
						opener.datagrid.datagrid("unselectAll");
						opener.datagrid.datagrid('reload');
						CloseWindow();
					 // window.location.reload();
					});
				}else if(v=="关闭"){
					return true;
				}
			}});
}


/**
* 删除
*/
function deleteById(sid){
	$.MsgBox.Confirm("提示","确定删除这条开票信息？删除后不可恢复！",function(){
		var url = contextPath+ "/TeeCrmInvoiceController/deleteById.action?sid="+sid;
		var json = tools.requestJsonRs(url, {sid:sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg);
			opener.$.MsgBox.Alert_auto("删除成功！");
			opener.datagrid.datagrid("unselectAll");
			opener.datagrid.datagrid('reload');
			CloseWindow();
		} else {
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	
	});
}

//编辑
function edit(){
	window.location.href = "<%=contextPath%>/system/subsys/crm/core/invoice/addOrUpdate.jsp?sid=" + sid +"&customerName="+customerName+"&type=2";
}

</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;overflow-x:hidden;" onload="doInit();">

<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_xjkp.png">
		<span class="title">{<%=customerName %>}--- 开票详情</span>
	</div>

    <!--  <input style="height: 25px;margin-right: 10px;"  type='button' value='编辑' class='btn-win-white' onclick='toAddUpdate();'/> -->
     <div class="btn-group fr" style="margin-right: 10px;margin-top: 5px;display: none;">
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
			<td  id='customerName' name='customerName'>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
			</td>
		</tr>
	    <tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				销售订单编号：
			</td>
			<td  id='orderNo' name='orderNo'>
				<input id='orderId' name='orderId' class="BigInput" type='hidden'/>
			</td>
		</tr>
		<tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">开票日期：</td>
		   <td id="invoiceTimeDesc" name="invoiceTimeDesc">
		   </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">开票申请编号：</td>
		   <td id="invoiceNo" name="invoiceNo">
		   </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">开票金额（元）：</td>
		   <td id="invoiceAmount" name="invoiceAmount">
		   </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">开票类型：</td>
		   <td id="invoiceTypeDesc" name="invoiceTypeDesc">
		   </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">备注：</td>
		   <td id="remark" name="remark">
		   </td>
        </tr>
        <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">负责人：
			</td>
		    <td name="managePersonName" id="managePersonName">
            </td>
	   </tr>
	    <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">发票号码：</td>
		   <td id="invoiceNumber" name="invoiceNumber">
		   </td>
        </tr>
	   <tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">开票财务：</td>
			<td name="invoiceFinancialName" id="invoiceFinancialName">
			</td>
      </tr>
		<tr>
           <td style="text-indent:15px;line-height: 30px;"> 附件：</td>
		    <td class="TableData">
		    	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
					<div id="fileContainer2"></div>			
		    </td>
        </tr>
        <tr style="display: none;" id="reasons">
           <td width="150" style="text-indent:15px;line-height: 30px;">驳回原因：</td>
		   <td id="rejectReason" name="rejectReason">
		   </td>
        </tr>
	 </table>
	 <br />
	 <table style="width: 100%;">
    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">发票信息</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">抬头类型：
			</td>
		    <td name="headerTypeDesc" id="headerTypeDesc">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">开票抬头：
			</td>
		    <td name="invoiceHeader" id="invoiceHeader">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">纳税人识别号：
			</td>
		    <td name="nsrNumber" id="nsrNumber">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">开户行账号：
			</td>
		    <td name="khhNumber" id="khhNumber">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">开户行地址：
			</td>
		    <td name="khhAddress" id="khhAddress">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">开户行名称：
			</td>
		    <td name="khhName" id="khhName">
            </td>
	</tr>
		<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">电话：
			</td>
		    <td name="telephone" id="telephone">
            </td>
	</tr>
</table>
</br>
<table style="width:100%;">
    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">寄送信息</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">联系人：
			</td>
		    <td name="contacts" id="contacts">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">联系方式：
			</td>
		    <td name="contactNumber" id="contactNumber">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">寄送地址：
			</td>
		    <td name="sendAddress" id="sendAddress">
            </td>
	</tr>
</table>
</br>

<table style="width: 100%;">
    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:text-top;"/>
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">其他</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">状态：
			</td>
		    <td name="invoiceStatusDesc" id="invoiceStatusDesc">
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
		    <td name=createTimeDesc id="createTimeDesc">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">最后变化时间：
			</td>
		    <td name="lastEditTimeDesc" id="lastEditTimeDesc">
            </td>
	</tr>

</table>
</br>
</br>
</body>

</html>