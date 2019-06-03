<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
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
	<title>线索详情</title>
	
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

.secondMenu{
	position:absolute;
	right:101%;
	top:0;
	background-color:#fff;
	display:none;
	border:1px solid #eee;
}
.secondMenu li{
    width:100px;
	height:30px;
	padding:0 20px;
	line-height:30px;
	
}
.secondMenu li:hover{
	background-color:#daeeff;
}
</style>
	
<script type="text/javascript" >



var sid = "<%=sid%>";
var customerId = "<%=customerId%>";
/**
   初始化列表
 */
function doInit(){
	getCrmCodeByParentCodeNo("CLUE_SOURCE","clueSource"); //线索来源
	
	getInfoBySid(sid);
	
	$("body").on("mouseover",".hasMenu",function(){
		$(".secondMenu").show();
	});
	$("body").on("mouseleave",".hasMenu",function(){
		$(".secondMenu").hide();
	});
}

function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmClueController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	var str = "";
	if(json.rtState){
		var data=json.rtData;
		var customerName = data.customerName;
		//绑定客户姓名
		if(customerName!=null){
			str= "<tr class=\"TableData\" align=\"left\"><td width=\"150\" style=\"text-indent:15px;line-height: 30px;\">客户名称："+
			      "</td><td name=\"customerName\" id=\"customerName\"></td></tr>";
		}
		//绑定联系人姓名
		if(data.contactName!=null){
			str += '<tr class="TableData" align="left"><td width="150" style="text-indent:15px;line-height: 30px;">联系人名称：'+
			      '</td><td name ="contactName" id="contactName"></td></tr>';
		}
		//绑定商机
		if(data.chanceName!=null){
			str += '<tr class="TableData" align="left"><td width="150" style="text-indent:15px;line-height: 30px;">商机名称：'+
			      '</td><td name ="chanceName" id="chanceName"></td></tr>';
		}
		
		$("#table1").prepend(str);
		
		
		bindJsonObj2Cntrl(data);
		//获取当前负责人
		managerPerName = data.managePersonName;
		managerPerId = data.managePersonId;
		var clueStatus = data.clueStatusDesc;
		addMenu(clueStatus);
		//alert(tools.jsonObj2String(data));
		}
}

//更多操作中根据客户状态追加操作
function addMenu(data){
	var str;
	$(".btn-content").empty();
	//客户下的线索都是通过线索转换客户来的
	if(managerPerId==loginPersonId){
         str = '<li onclick="deleteById('+sid+');"><a href="javascript:void(0);">删除</a></li>'+
		'<li onclick="back()"><a href="javascript:void(0);" >返回</a></li>';
	}else{
		str ='<li onclick="back();"><a href="javascript:void(0);" >返回</a></li>';
	}
	$(".btn-content").append(str);
}


/**
 * 删除
 */
function deleteById(id){
	$.MsgBox.Confirm("提示","确定删除选中线索？",function(){
		var url = contextPath+ "/TeeCrmClueController/delById.action?sids="+sid;
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

//返回
function back(){
	history.go(-1);
}

</script>
</head>
<body style="padding-left: 10px;padding-right: 10px;overflow-x:hidden;" onload="doInit();">

<div class="topbar clearfix" id="toolbar">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_xsxq.png">
		<span class="title">线索详情</span>
	</div>
     <div class="btn-group fr" style="margin-right: 20px;margin-top: 5px;">
		  <button type="button" class="btn-win-white btn-menu" >
		    更多操作<span class="caret-down"></span>
		  </button>
		  <ul class="btn-content">
		  </ul>
		</div>
</div>

	<table style="width: 100%;margin-top: 10px;" id="table1">
	   <div style="margin-top: 15px;margin-bottom: 5px;">
	       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align: text-top;"/>
		    &nbsp;<span style="font-size: 16px;line-height: 16px;color: #0050aa;font-family: 'MicroSoft YaHei';">基本信息</span>
		    <span style="padding-top: 10px;padding-bottom: 5px;width:45%;display:block;" class="basic_border_grey"></span>
	   </div>

		<tr class='TableData' align='left'>
			<td width="150" style="text-indent:15px;line-height: 30px;">
				姓名：
			</td>
		    <td name ="name" id="name">
            </td>
		</tr>
		<tr>
            <td width="150" style="text-indent:15px;line-height: 30px;">公司：</td>
            <td align="left" name="companyName" id="companyName">
           </td>
        </tr>
        <tr>
           <td width="150" style="text-indent:15px;line-height: 30px;">销售线索详情：</td>
		   <td id="culeDetail" name="culeDetail">
		   </td>
        </tr>
        <tr>
            <td width="150px;" style="text-indent: 15px;line-height: 30px;">来源：</td>
			<td id="clueSourceDesc" name ="clueSourceDesc">
			</td>
        </tr>
        <tr>
           <td width="150px;" style="text-indent: 15px;line-height: 30px;">部门：</td>
			<td name="department" id="department">
			</td>
        </tr>
        <tr>
		    <td width="150" style="text-indent:15px;line-height: 30px;">职务：</td>
		    <td name="duties" id="duties">
		    </td>
	   </tr>
	   <tr>
		<td width="150" style="text-indent:15px;line-height: 30px;">电话：</td>
		<td name="telephone" id="telephone">
		</td>
	  </tr>
	   <tr>
	        <td width="150px" style="text-indent:15px;line-height: 30px;">手机：</td>
			<td name="mobilePhone" id="mobilePhone">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">网址：</td>
			<td id="url" name="url">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">邮箱：</td>
			<td id="email" name="email">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">地址：</td>
			<td name="address" id="address">
			</td>
	   </tr>
	   <tr>
	        <td width="150" style="text-indent:15px;line-height: 30px;">处理结果：</td>
			<td name="dealResult" id="dealResult">
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
			<td width="150" style="text-indent:15px;line-height: 30px;">负责人：
			</td>
		    <td name="managePersonName" id="managePersonName">
            </td>
	</tr>
	<tr>
			<td width="150" style="text-indent:15px;line-height: 30px;">状态：
			</td>
		    <td name="clueStatusDesc" id="clueStatusDesc">
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