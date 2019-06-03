<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int loginPersonId = person.getUuid();
	String userName = person.getUserName();
	String DRAWBACK_PERSON_IDS = "DRAWBACK_PERSON_IDS";
	String DRAWBACK_PERSON_NAMES = "DRAWBACK_PERSON_NAMES";
	String title="";
	if(sid>0){
		title="编辑退款";
	}else{
		title="新建退款";	
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#middlePopover {
	position: fixed;
	top: 100px;
	right: 200px;
	width: 200px;
}
#middlePopover .mui-popover-arrow {
	left: auto;
	right: 100px;
}
</style>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">

    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
	    <span class="mui-icon mui-icon-left-nav" ></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='save()'>
	    保存
	</button>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退款编号</label>
		</div>
		<div class="mui-input-row">
             <input type="text" placeholder="退款编号" name="drawbackNo" id="drawbackNo">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属客户</label>
		</div>
		<div class="mui-input-row">
			<input type="hidden" id="customerId" name="customerId"/>
			<input  id='customerName' name='customerName' type='text' readonly="readonly"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>销售订单编号</label>
		</div>
		<div class="mui-input-row">
            <input type="hidden" id="orderId" name="orderId"/>
			<select id="orderNo"  name="orderNo" style="width: 550px;font-size: 14px;">
	       		<option value="0">选择销售订单</option>
	        </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退款日期</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="退款日期" name="drawbackTimeDesc" id="drawbackTimeDesc">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退款金额（元）</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="退款金额" name="drawbackAmount"  id="drawbackAmount"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退款方式</label>
		</div>
		<div class="mui-input-row" >
			<select id="drawbackStyle" placeholder="退款方式"  name="drawbackStyle" style="width: 550px;font-size: 14px;">
	        </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="4" style="width: 550px" name="remark" id="remark" placeholder="备注" ></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="managePersonName" name="managePersonName" readonly placeholder="请选择负责人" />
			<input type="hidden" id="managePersonId" name="managePersonId"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退款财务</label>
		</div>
		<div class="mui-input-row">
			<select id="refundFinancialId"  name="refundFinancialId" style="width: 550px;font-size: 14px;">
	        </select>
		</div>
	</div>
 
</form>	
</div>


<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var customerName ="<%=customerName%>";
var customerId = "<%=customerId%>";
var loginPersonId = <%=loginPersonId%>;
var userName = "<%=userName%>";
var type=<%=type%>;
var drawbackPersonIds = "<%=DRAWBACK_PERSON_IDS%>";
var drawbackPersonNames = "<%=DRAWBACK_PERSON_NAMES%>";
function doInit(){
	getCrmCodeByParentCodeNo("DRAWBACK_STYLE","drawbackStyle");//退款方式
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);
	
	getRefundFinancal(drawbackPersonIds);//获取退款财务
	selectOrders();
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	drawbackTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			drawbackTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
}

//选择销售订单
function selectOrders(){
	var cusId = document.getElementById("customerId").value;
	if(cusId=="" || cusId=="null" || cusId==null|| cusId=="0"){
		alert("请先选择客户！");
	}else{
		var url = contextPath+'/TeeCrmOrderController/selectOrders.action';
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{cusId:cusId},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					var prcs = json.rtData;
						var options = "";
						for ( var i = 0; i < prcs.length; i++) {
							options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].orderNo + "</option>";
						}
						$("#orderNo").html(options);
					return prcs;
				}else{
					alert("数据查询失败！");
				}
			}
		});
	}

}


//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmDrawbackController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				bindJsonObj2Cntrl(data);
				$("#orderNo").val(data.orderId);
				
			}else{
				alert("查询失败！");
			}
		}
	});
}

//获取财务回款
function getRefundFinancal(drawbackPersonIds){
	//获取参数
	var params="";
	var url =   contextPath + "/sysPara/getSysParaList.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{paraNames:drawbackPersonIds},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
				if(prcs.length > 0){
					params =prcs[0].paraValue;
					if(params != "" ){
						var personId="";
						var personName="";
						var url =   contextPath + "/personManager/getPersonNameAndUuidByUuids.action";
						mui.ajax(url,{
							type:"post",
							dataType:"html",
							data:{uuid:params},
							timeout:10000,
							success:function(json){
							   json = eval("("+json+")");
								if(json.rtState){
									personId=json.rtData.sid.split(",");
									personName =json.rtData.userName.split(",");
									var html ="";
									for(var i=0;i<personId.length;i++){
										 html +="<option value='"+personId[i]+"'>"+personName[i]+"</option>";
									}
									$("#refundFinancialId").append(html);
								}else{
									alert(json.rtMsg);
								}
							}
						});

					}
				}
			}else{
				alert(json.rtMsg);
			}
		}
	});
	
}


/**手机端
 * 根据CRM主类编号  获取子集代码列表
 * 
 * @param codeNo 系统代码编号  主类编码
 * @param codeSelectId 对象Id
 * @returns 返回人员数组 对象 [{codeNo:'' , codeName:''}]
 */
function getCrmCodeByParentCodeNo(codeNo , codeSelectId ){
	var url =   contextPath + "/crmCode/getSysCodeByParentCodeNo.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{codeNo:codeNo},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
				if(codeSelectId && $("#" + codeSelectId)[0]){//存在此对象
					var options = "";
					for ( var i = 0; i < prcs.length; i++) {
						options = options + "<option value='"+prcs[i].codeNo+"'>" + prcs[i].codeName + "</option>";
					}
					$("#" + codeSelectId).append(options);
				}
				return prcs;
			}else{
				alert(jsonObj.rtMsg);
			}
		}
	});

}


//验证
function check(){
	var orderId=$("#orderNo").val();
	var amount = $("#drawbackAmount").val();
	
	if($("#drawbackNo").val()=="" || $("#drawbackNo").val()=="null" || $("#drawbackNo").val()==null){
		$.MsgBox.Alert_auto("请填写退款编号！");
		return false;
	}
	if(orderId=="" || orderId=="null" || orderId==null||orderId==0){
		alert("请选择销售订单！");
		return false;
	}
	if($("#drawbackTimeDesc").val()=="" || $("#drawbackTimeDesc").val()=="null" || $("#drawbackTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请选择退款日期！");
		return false;
	}
	//验证金额必须是正数
	var positive_integer = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
	if(amount==""||amount==null){
		alert("请填写退款金额！");
		return false;	
	}else if(!(positive_integer.test(amount))){
		alert("退款金额必须为整数！");
		return false;	
	}
	if($("#managePersonId").val()=="" || $("#managePersonId").val()=="null" || $("#managePersonId").val()==null){
		$.MsgBox.Alert_auto("请选择负责人！");
		return false;
	}
	
	
	return true;
}



//保存/提交
function save(){
	if(check()){
		var orderId = $("#orderNo").val();
		$("#orderId").val(orderId);
		
		var url=contextPath+"/TeeCrmDrawbackController/addOrUpdate.action";
		var param=formToJson("#form1");
		param['sid']=sid;
		param["isPhone"]=1;
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				if(sid>0){
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmDrawback/drawbackDetail.jsp?sid="+sid+"&customerName="+customerName+"&type="+type+"&customerId="+customerId;
				}else{
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmDrawback/drawbackList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}
			}
		}
	});	
		
	}
}

mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(sid>0){
			history.go(-1);
		}else{
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmDrawback/drawbackList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);
	
});


</script>

</body>
</html>