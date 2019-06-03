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
	String title="";
	if(sid>0){
		title="编辑拜访记录";
	}else{
		title="新建拜访记录";	
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>

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
			<label>所属客户</label>
		</div>
		<div class="mui-input-row">
			<input type="hidden" id="customerId" name="customerId"/>
			<input  id='customerName' name='customerName' type='text' readonly="readonly"/>
			<!-- <select id="customerName"  name="customerName" style="width: 550px;font-size: 14px;">
	       		<option value="0">选择所属客户</option>
	        </select> -->
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>拜访主题</label>
		</div>
		<div class="mui-input-row">
             <select id="visitTopic" placeholder="拜访主题"  name="visitTopic" style="width: 550px;font-size: 14px;">
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>计划日期</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="计划日期" name="visitTimeDesc"  id="visitTimeDesc" />
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
function doInit(){
	getCrmCodeByParentCodeNo("VISIT_TOPIC","visitTopic");//拜访主题
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);

	//初始化客户数据
	selectCustomer();
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	//开始时间
	visitTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			visitTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
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


//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmVisitController/getInfoBySid.action";
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
				
			}else{
				alert("查询失败！");
			}
		}
	});
}


//验证
function check(){
	
	if($("#visitTimeDesc").val()=="" || $("#visitTimeDesc").val()=="null" || $("#visitTimeDesc").val()==null){
		alert("请选择计划日期！");
		return false;
	}
	if($("#managePersonId").val()=="" || $("#managePersonId").val()=="null" || $("#managePersonId").val()==null){
		alert("请选择负责人！");
		return false;
	}
	
	return true;
}




//保存/提交
function save(){
	if(check()){
			var url=contextPath+"/TeeCrmVisitController/addOrUpdate.action";
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
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerVisit/visitDetail.jsp?sid="+sid+"&customerName="+customerName+"&type="+type+"&customerId="+customerId;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerVisit/visitList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerVisit/visitList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
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