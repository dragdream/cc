<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
    String model = TeeAttachmentModelKeys.CRM_CUSTOMER_CONTACTS;
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
	<title>联系人信息</title>
	
	<style>
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
var customerName ="<%=customerName%>";
var customerId = "<%=customerId%>";
/**
   初始化列表
 */
function doInit(){
	getContactsInfoBySid(sid);
	
}

var managerPerName = '';
var managerPerId = "";
function getContactsInfoBySid(sid){
	var url=contextPath+"/TeeCrmContactsController/getContactsInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		managerPerName = data.managePersonName;
		managerPerId = data.managePersonId;

		//附件
		var  attachmodels = data.attachmodels;
		for(var i=0;i<attachmodels.length;i++){
			var temp = attachmodels[i];
			temp["priv"] = 3;
			var fileItem = tools.getAttachElement(temp);
			$("#attachments").append(fileItem);
		}
		addMenu(data.contactsStatusDesc);
		}
}

//更多操作中根据联系人状态追加操作
function addMenu(data){
	var str;
	$(".btn-content").empty();
	if(managerPerId==loginPersonId){
		if(data == "正常"){
			str = '<li onclick="edit();"><a href="javascript:void(0);" >编辑</a></li>'+
		    	  '<li class="modal-menu-test" onclick="setValue();$(this).modal();"><a href="javascript:void(0);" >更换负责人</a></li>'+
		    	  '<li onclick="cancel('+sid+');"><a href="javascript:void(0);" >作废</a></li>'+
		    	  '<li onclick="back('+sid+');"><a href="javascript:void(0);" >返回</a></li>';
		}else if(data == "已作废"){
			str = '<li onclick="deleteById('+sid+');"><a href="javascript:void(0);">删除</a></li>'+
	  	 		  '<li onclick="recovery('+sid+');"><a href="javascript:void(0);" >恢复</a></li>'+
	  	 		  '<li onclick="back('+sid+');"><a href="javascript:void(0);" >返回</a></li>';
		}
	}else{
		str = '<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
	}
	$(".btn-content").append(str);
}

//作废
function cancel(sid){
	 $.MsgBox.Confirm ("提示", "确定作废这个联系人？",function(){
	 var url=contextPath+"/TeeCrmContactsController/cancel.action?sid="+sid+"&contactsStatus=1";
	 var jsonRs = tools.requestJsonRs(url,null);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
				history.go(-1);
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	 });
}

function back(sid){
	history.go(-1);
}

/**
 * 删除
 */
function deleteById(id){
	$.MsgBox.Confirm("提示","确定删除这个联系人？",function(){
		var url = contextPath+ "/TeeCrmContactsController/delContacts.action?sids="+sid;
		var json = tools.requestJsonRs(url, {sids : sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				history.go(-1);
			});
		} else {
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	
	});
}

//恢复
function recovery(sid){
	$.MsgBox.Confirm("提示","确定恢复到作废之前的状态？",function(){
		var url = contextPath+ "/TeeCrmContactsController/recovery.action?sid="+sid;
		var json = tools.requestJsonRs(url, {sid : sid});
		if (json.rtState) {
			$.MsgBox.Alert_auto(json.rtMsg,function(){
				window.location.reload();
			});
		} else {
			$.MsgBox.Alert_auto("恢复失败！");
		}
	
	});
}




//编辑
function edit(){
	 var title = "编辑联系人";
	  var url = contextPath+"/system/subsys/crm/core/customer/linkman/addOrEditLinkman.jsp?sid="+ sid+"&customerId="+customerId+"&customerName="+customerName;
	  bsWindow(url ,title,{width:"900",height:"350",buttons:
			[
			 {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
				cw.save(function(){
				  //$.MsgBox.Alert_auto("保存成功！"); 
				  window.location.reload();
				});
			}else if(v=="关闭"){
				return true;
			}
		}});

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
	var url=contextPath+"/TeeCrmContactsController/changeManage.action?sid="+sid;
    var param=tools.formToJson("#form1");
	var jsonRs = tools.requestJsonRs(url,param);
		if(jsonRs.rtState){
			$(".modal-win-close").click();
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
				window.location.reload();
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

<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_khlxrxq.png">
		<span class="title">{<%=customerName %>}--联系人详情</span>
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
				所属客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td  id='customerName' name='customerName'>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
			</td>
		</tr>
		<tr>
           <td style="text-indent:15px;line-height: 30px;"> 名片：</td>
		    <td class="TableData">
		    	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
					<div id="fileContainer2"></div>			
		    </td>

        </tr>
        <tr>
          <td style="text-indent:15px;line-height: 30px;">姓名：</td>
		   <td class="TableData"  id="contactName" name="contactName">
		   </td>
        </tr>
        <tr>
          <td style="text-indent:15px;line-height: 30px;">所属部门：</td>
			<td id="department" name="department" >
			</td>
        </tr>
        <tr>
          <td style="text-indent:15px;line-height: 30px;">职务：</td>
			<td id="duties" name="duties">
			</td>
        </tr>
        <tr>
	        <td style="text-indent:15px;line-height: 30px;">关键决策人：</td>
			<td id="keyPersonDesc" name="keyPersonDesc"> 
			</td>
	   </tr>
	 </table>
	 <br />
	<table width="100%">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		        <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">联系方式</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	   </tr>
		<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">电话：</td>
			<td name="telephone" id="telephone">
			</td>
		</tr>
		<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">手机：</td>
			<td name="mobilePhone" id="mobilePhone">
			</td>
		</tr >
		<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">邮箱：</td>
			<td name="email" id="email">
			</td>
		</tr>
		<tr>
			<td style="text-indent:15px;line-height: 30px;">地址：</td>
			<td name="address" id="address" >
			</td>
		</tr>
	</table>
	<br />
		<table width="100%">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		         <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">附加信息</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;" class="basic_border_grey fl"></span>
		   </td>
	   </tr>
	   <tr>
	   	<td width="150" style="text-indent:15px;line-height: 30px;">出生日期：</td>
			<td id='birthdayDesc' name="birthdayDesc">
			</td>
	   </tr>
	   <tr>
	       <td width="150" style="text-indent:15px;line-height: 30px;">性别：</td>
			<td id="genderDesc" name="genderDesc">
			</td>
	   </tr>
	   <tr>
	       <td width="150" style="text-indent:15px;line-height: 30px;">公司名称：</td>
	       <td name=companyName id="companyName" >
			</td>
	   </tr>
	   <tr>
	      	<td width="150" style="text-indent:15px;line-height: 30px;">介绍人：</td>
			<td id='introduceName' name='introduceName'>
				<input id='introduceId' name='introduceId' class="BigInput" type='hidden'/>
			</td>
	   </tr>
	   <tr>
	       <td width="150" style="text-indent:15px;line-height: 30px;">备注：</td>
			<td id="remark" name="remark" >
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
			<td width="150" style="text-indent:15px;line-height: 30px;">负责人：
			</td>
		    <td name="managePersonName" id="managePersonName">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">状态：
			</td>
		    <td name="contactsStatusDesc" id="contactsStatusDesc">
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
		    <td name="createTimeDesc" id="createTimeDesc">
            </td>
	</tr>

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
				      <td style="text-indent:15px">变更为：</td>
						<td >
							<input type="hidden" name="managerPerId" id="managerPerId"> 
							<input name="managerPerName" id="managerPerName" style="height:23px;width:300px;border: 1px solid #dadada;font-family: MicroSoft YaHei;font-size: 12px;" readonly />&nbsp;&nbsp;
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