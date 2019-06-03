<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	String model = TeeAttachmentModelKeys.CRM_PAYBACK;
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
	<title>回款信息</title>
<style>

</style>
	
<script type="text/javascript" >
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
var customerId = "<%=customerId%>";
/**
   初始化列表
 */
function doInit(){
	getCrmCodeByParentCodeNo("PAYBACK_STYLE","paybackStyle");//回款方式
	getPaybackInfoBySid(sid);
	
}

var managerPerName = '';
var managerPerId = "";
var paymentPersonIds = "";
function getPaybackInfoBySid(sid){
	var url=contextPath+"/TeeCrmPaybackController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		managerPerName = data.managePersonName;
		managerPerId = data.managePersonId;
		paymentPersonIds = data.paymentFinancialId;
		if(data.paybackStatus==3){
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

		addMenu(data.paybackStatusDesc);
		}
}

//更多操作中根据回款状态追加操作
function addMenu(data){
	var str;
	$(".btn-content").empty();
    if(managerPerId==loginPersonId){
		if(data == "待回款"){
		str ='<li onclick="edit();"><a href="javascript:void(0);" >编辑</a></li>'+
        '<li onclick="deleteById('+sid+');"><a href="javascript:void(0);" >删除</a></li>'+
        '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
		}else if(data =="已回款"){
			str = '<li onclick="deleteById('+sid+');"><a href="javascript:void(0);">删除</a></li>'+
			 '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
		}else if(data =="已驳回"){
			str ='<li onclick="deleteById('+sid+');"><a href="javascript:void(0);" >删除</a></li>'+
			 '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
		}
	}else{
		str = '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
	}
	
	$(".btn-content").append(str);
}


//同意
function agree(sid){
	 $.MsgBox.Confirm ("提示", "确定回款信息无误？",function(){
		 var url=contextPath+"/TeeCrmPaybackController/agree.action?sid="+sid+"&paybackStatus=2";
		 var jsonRs = tools.requestJsonRs(url,null);
			if(jsonRs.rtState){
				$.MsgBox.Alert_auto("操作成功！",function(){
					history.go(-1);
				});
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
		 });
}

//驳回
function reject(sid){
	var title = "驳回";
	  var url = contextPath + "/system/subsys/crm/core/payback/reject.jsp?sid="+sid;
	  bsWindow(url ,title,{width:"600",height:"300",buttons:
			[
			 {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
				cw.doSaveOrUpdate(function(){
					$.MsgBox.Alert_auto("操作成功！",function(){
						history.go(-1);
					});
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
	$.MsgBox.Confirm("提示","确定删除这条回款信息？删除后不可恢复！",function(){
		var url = contextPath+ "/TeeCrmPaybackController/deleteById.action?sid="+sid;
		var json = tools.requestJsonRs(url, {sid:sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				history.go(-1);
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	
	});
}

//编辑
function edit(){
	 var title = "编辑回款";
	  var url = contextPath+"/system/subsys/crm/core/customer/payback/addOrUpdate.jsp?sid="+sid+"&customerId="+customerId+"&customerName="+customerName;
	  bsWindow(url ,title,{width:"900",height:"350",buttons:
			[
			 {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
				cw.save(function(){
				  window.location.reload();
				});
			}else if(v=="关闭"){
				return true;
			}
		}});
}

//返回
function back(){
	history.go(-1);
}


</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;overflow-x:hidden;" onload="doInit();">

<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_hkxq.png">
		<span class="title">{<%=customerName %>}--- 回款详情</span>
	</div>

    <!--  <input style="height: 25px;margin-right: 10px;"  type='button' value='编辑' class='btn-win-white' onclick='toAddUpdate();'/> -->
     <div class="btn-group fr" style="margin-right: 10px;margin-top: 5px;">
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
           <td width="150" style="text-indent:15px;line-height: 30px;">回款日期：</td>
		   <td id="paybackTimeDesc" name="paybackTimeDesc">
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
         <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">提醒时间：</td>
		   <td id="remindTimeDesc" name="remindTimeDesc">
		   </td>
        </tr>
        <tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">回款编号：</td>
            <td align="left" name="paybackNo" id="paybackNo">
           </td>
        </tr>
        <tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">回款金额（元）：</td>
            <td align="left" name="paybackAmount" id="paybackAmount"> 
           </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">回款方式：</td>
		   <td id="paybackStyleDesc" name="paybackStyleDesc">
		   </td>
        </tr>
        <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">备注：
			</td>
		    <td name="remark" id="remark">
            </td>
	    </tr>
        <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">负责人：
			</td>
		    <td name="managePersonName" id="managePersonName">
            </td>
	   </tr>
	   <tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">回款财务：</td>
			<td name="paymentFinancialName" id="paymentFinancialName">
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
		       &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">其他</span>
		       <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		       
		   </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">状态：
			</td>
		    <td name="paybackStatusDesc" id="paybackStatusDesc">
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