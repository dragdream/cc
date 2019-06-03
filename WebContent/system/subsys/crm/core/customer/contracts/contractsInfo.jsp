<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
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
	<title>合同详情</title>
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
	getInfoBySid(sid);
	
}

var managerPerName = '';
var managerPerId = "";
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmContractsController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		managerPerName = data.managePersonName;
		managerPerId = data.managePersonId;

		addMenu(data.contractsStatusDesc);
		}
}

//更多操作中根据合同状态追加操作
function addMenu(data){
	var str;
	$(".btn-content").empty();
	if(managerPerId==loginPersonId){
		if(data == "正常"){
			str ='<li onclick="edit();"><a href="javascript:void(0);" >编辑</a></li>'+
	        '<li onclick="cancel('+sid+');"><a href="javascript:void(0);" >作废</a></li>'+
	        '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
		}else if(data =="已作废"){
			str = '<li onclick="deleteById('+sid+');"><a href="javascript:void(0);">删除</a></li>'+
			 '<li onclick="recovery('+sid+');"><a href="javascript:void(0);">恢复</a></li>'+
			 '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
		}
	}else{
		str ='<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
	}
	
	$(".btn-content").append(str);
}


//作废
function cancel(sid){
	 $.MsgBox.Confirm ("提示", "是否作废此合同？",function(){
		 var url=contextPath+"/TeeCrmContractsController/cancel.action?sid="+sid+"&contractsStatus=2";
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

//恢复
function recovery(sid){
	 $.MsgBox.Confirm ("提示", "确定恢复此合同的状态到作废前？",function(){
		 var url=contextPath+"/TeeCrmContractsController/recovery.action?sid="+sid;
		 var jsonRs = tools.requestJsonRs(url,null);
			if(jsonRs.rtState){
				$.MsgBox.Alert_auto("操作成功！");
				window.location.reload();
				opener.datagrid.datagrid("unselectAll");
				opener.datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
		 });
}

/**
* 删除
*/
function deleteById(sid){
	$.MsgBox.Confirm("提示","确定删除此合同？删除后不可恢复！",function(){
		var url = contextPath+ "/TeeCrmContractsController/deleteById.action?sid="+sid;
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
	 var title = "编辑合同";
	  var url = contextPath+"/system/subsys/crm/core/customer/contracts/addOrUpdate.jsp?sid="+sid+"&customerId="+customerId+"&customerName="+customerName;
	  bsWindow(url ,title,{width:"700",height:"300",buttons:
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
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_xjht.png">
		<span class="title">{<%=customerName %>}--- 合同详情</span>
	</div>

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
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同编号：
			</td>
			<td  id='contractsNo' name='contractsNo'>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				合同标题：
			</td>
			<td  id='contractsTitle' name='contractsTitle'>
			</td>
		</tr>
		<tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">开始日期：</td>
		   <td id="contractsStartTimeDesc" name="contractsStartTimeDesc">
		   </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">结束日期：</td>
		   <td id="contractsEndTimeDesc" name="contractsEndTimeDesc">
		   </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">合同金额（元）：</td>
		   <td id="contractsAmount" name="contractsAmount">
		   </td>
        </tr>
        <tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">负责人：
			</td>
		    <td name="managePersonName" id="managePersonName">
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
		    <td name="contractsStatusDesc" id="contractsStatusDesc">
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