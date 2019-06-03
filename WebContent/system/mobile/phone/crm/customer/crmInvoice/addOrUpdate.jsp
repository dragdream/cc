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
	String BILLING_PERSON_IDS = "BILLING_PERSON_IDS";
	String BILLING_PERSON_NAMES = "BILLING_PERSON_NAMES";
	String title="";
	if(sid>0){
		title="编辑开票信息";
	}else{
		title="新建开票信息";	
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
			<label>开票编号</label>
		</div>
		<div class="mui-input-row">
             <input type="text" placeholder="开票编号" name="invoiceNo" id="invoiceNo">
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
			<label>开票日期</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="开票日期" name="invoiceTimeDesc" id="invoiceTimeDesc">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票金额（元）</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="开票金额" name="invoiceAmount"  id="invoiceAmount"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票类型</label>
		</div>
		<div class="mui-input-row" >
			<select id="invoiceType" placeholder="开票类型"  name="invoiceType" style="width: 550px;font-size: 14px;">
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
			<label>发票号码</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="发票号码" name="invoiceNumber"  id="invoiceNumber"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票财务</label>
		</div>
		<div class="mui-input-row">
			<select id="invoiceFinancialId"  name="invoiceFinancialId" style="width: 550px;font-size: 14px;">
	        </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>附件</label>
			<label><a href="javascript:void(0)" onclick="doUploadPublicAttach()" >添加附件</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <div id="attachList" style="padding-left: 15px"></div>
		   <div id="upfileList" style="padding-left: 15px"></div>
		    
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>联系人</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="联系人" name="contacts"  id="contacts"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>联系方式</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="联系方式" name="contactNumber"  id="contactNumber"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>寄送地址</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="寄送地址" name="sendAddress"  id="sendAddress"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>抬头类型</label>
		</div>
		<div class="mui-input-row">
			<label><input type="radio" name="headerType" value="1" checked="checked" /> 个人
			<input type="radio" name="headerType" value="2" /> 单位</label>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开票抬头</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="开票抬头" name="invoiceHeader"  id="invoiceHeader"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="电话" name="telephone"  id="telephone"/>
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
var billingPersonIds = "<%=BILLING_PERSON_IDS%>";
var billingPersonNames = "<%=BILLING_PERSON_NAMES%>";
function doInit(){
	getCrmCodeByParentCodeNo("INVOICE_TYPE","invoiceType");//开票类型
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);
	
	getInvoiceFinancial(billingPersonIds);//获取开票财务
	selectOrders();//初始化订单数据
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	invoiceTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			invoiceTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	
}

//选择所属客户
function selectCustomer(){
	var url = contextPath+'/TeeCrmCustomerController/selectAllCustomer.action';
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
					var options = "";
					for ( var i = 0; i < prcs.length; i++) {
						options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].customerName + "</option>";
					}
					$("#customerName").append(options);
				return prcs;
			}else{
				alert("数据查询失败！");
			}
		}
	});

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

//上传公共附件
function doUploadPublicAttach(){
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='removePublicAttachImg(this)' /></p>").appendTo($("#upfileList"));
	});
}

//移除公共附件项
function removePublicAttachImg(obj){
	$(obj).parent().remove();
}



//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmInvoiceController/getInfoBySid.action";
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
				
				var  attachments = data.attachmodels;
				if(attachments.length > 0){
					 $.each(attachments, function(index, item){  
						 $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='delAttach("+item.sid+")' /><div>");
					 });
				}
		
				
			}else{
				alert("查询失败！");
			}
		}
	});
}

//获取财务回款
function getInvoiceFinancial(billingPersonIds){
	//获取参数
	var params="";
	var url =   contextPath + "/sysPara/getSysParaList.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{paraNames:billingPersonIds},
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
									$("#invoiceFinancialId").append(html);
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
	var amount = $("#invoiceAmount").val();
	
	if($("#invoiceNo").val()=="" || $("#invoiceNo").val()=="null" || $("#invoiceNo").val()==null){
		$.MsgBox.Alert_auto("请填写开票编号！");
		return false;
	}
	
	if(orderId=="" || orderId=="null" || orderId==null||orderId==0){
		alert("请选择销售订单！");
		return false;
	}
	if($("#invoiceTimeDesc").val()=="" || $("#invoiceTimeDesc").val()=="null" || $("#invoiceTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请选择开票日期！");
		return false;
	}
	//验证金额必须是正数
	var positive_integer = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
	if(amount==""||amount==null){
		alert("请填写开票金额！");
		return false;	
	}else if(!(positive_integer.test(amount))){
		alert("开票金额必须是整数！");
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
		
		var filesArray = [];//组装文件数据
		$(".img").each(function(i,obj){
			filesArray.push(obj.getAttribute("path"));
		});
		
		//先上传图片  
		UploadPhoto(function(files){
			var attachIds="";
			for(var i=0;i<files.length;i++){
				attachIds=attachIds+files[i].id+"," ;
			}

			var url=contextPath+"/TeeCrmInvoiceController/addOrUpdate.action";
			var param=formToJson("#form1");
			param['sid']=sid;
			param['attachmentSidStr']=attachIds;
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
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmInvoice/invoiceDetail.jsp?sid="+sid+"&customerName="+customerName+"&type="+type+"&customerId="+customerId;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmInvoice/invoiceList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
					}
	               
				}
			}
		});
			
		},filesArray,"crmInvoice","");//一开始modelid是没有的 因为主键还没生成呢
		
	}
}

//删除附件
function delAttach(sid){
	if(confirm("是否确认删除该附件？")){
		var url=contextPath+"/attachmentController/deleteFile.action";
		var param={attachIds:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("附件删除成功！");
					window.location.reload();
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
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/crmInvoice/invoiceList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
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