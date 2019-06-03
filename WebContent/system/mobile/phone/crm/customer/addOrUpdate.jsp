<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
    String title="";
    if(sid>0){
    	title="编辑客户";
    }else{
    	title="新建客户";	
    }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
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
			<label>客户名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="客户名称" name="customerName" id="customerName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户编号</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="客户编号" name="customerNum" id="customerNum">
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>客户级别</label>
		</div>
		<div class="mui-input-row">
			 <select id="customerType" placeholder="客户级别"  name="customerType" style="width: 550px;font-size: 14px;">
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>来源</label>
		</div>
		<div class="mui-input-row">
		     <select id="customerSource" placeholder="来源"  name="customerSource" style="width: 550px;font-size: 14px;">
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属行业</label>
		</div>
		<div class="mui-input-row">
		     <select id="industry" placeholder="所属行业"  name="industry" style="width: 550px;font-size: 14px;">
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户所属省</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<select id="province" name="province" style="width: 550px;font-size: 14px;"></select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户所属市</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <select id="city" name="city" style="width: 550px;font-size: 14px;"></select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户所属区</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <select id="district" name="district" style="width: 550px;font-size: 14px;"></select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>定位</label>
			<label><a href="javascript:void(0)" onclick="selectMapPoint()">地图选点</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" name="addressDesc" id="addressDesc" style="width: 350px" placeholder="请选择客户具体位置" />
		    
		    <input type="hidden" name="coordinate" id="coordinate" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司规模</label>
		</div>
		<div class="mui-input-row" >
		     <select id="companyScale" placeholder="公司规模"  name="companyScale" style="width: 550px;font-size: 14px;">
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户性质</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			 <select name="type" id="type" style="width: 550px;font-size: 14px;">
		       <option value="1">客户</option>
		       <option value="2">供应商</option>
		    </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>单位性质</label>
		</div>
		<div class="mui-input-row" >
		    <select name="unitType" id="unitType" style="width: 550px;font-size: 14px;">
		       <option value="">请选择</option>
		    </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司地址</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="公司地址" name="companyAddress"  id="companyAddress" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>联系电话</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="联系电话" name="companyPhone"  id="companyPhone" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮编</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="邮编" name="companyZipCode"  id="companyZipCode" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司网址</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="公司网址" name="companyUrl"  id="companyUrl" />
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
			<label>共享人员</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <input type="text" id="sharePersonNames" name="sharePersonNames" readonly placeholder="请选择共享人员" />
			<input type="hidden" id="sharePersonIds" name="sharePersonIds"/>
		</div>
	</div>
	<div id="customTbody">
	
	</div>
	
</form>	
</div>


<!--右上角弹出菜单-->

<script>
var sid="<%=sid%>";
var type = <%=type%>;
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



//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmCustomerController/getById.action";
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
			}
		}
	});
}

//初始化级联地址控件
function initAddressCtr(){
	addressInit("province","city","district");
}

//动态渲染自定义字段
function renderCustomerField(){
	$("#customTbody").html("");
	var url=contextPath+"/TeeCrmCustomerController/getListFieldByCustomer.action";
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					var name="EXTRA_"+data[i].sid;
					if(data[i].filedType=="单行输入框"){
						
						$("#customTbody").append("<div class=\"mui-input-group\">"
								   +"<div class=\"mui-input-row\">"
								   +"<label>"+data[i].extendFiledName+"</label>"
								   +"</div>"
								   +"<div class=\"mui-input-row\">"
								   +    "<input type=\"text\" placeholder='"+data[i].extendFiledName+"' name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" />"
								   +"</div>"
								   +"</div>");
					}else if(data[i].filedType=="多行输入框"){
						$("#customTbody").append("<div class=\"mui-input-group\">"
								   +"<div class=\"mui-input-row\">"
								   +"<label>"+data[i].extendFiledName+"</label>"
								   +"</div>"
								   +"<div class=\"mui-input-row\">"
								   +    "<textarea  type=\"text\" rows=\"6\" placeholder='"+data[i].extendFiledName+"' name='"+name+"' id='"+name+"' style=\"width: 550px\" ></textarea>"
								   +"</div>"
								   +"</div>");
					}else if(data[i].filedType=="下拉列表"){
						/* var fieldCtrModel=data[i].fieldCtrModel;
						var j=tools.strToJson(fieldCtrModel); */
						if(data[i].codeType=="CRM系统编码"){
							$("#customTbody").append("<div class=\"mui-input-group\">"
									   +"<div class=\"mui-input-row\">"
									   +"<label>"+data[i].extendFiledName+"</label>"
									   +"</div>"
									   +"<div class=\"mui-input-row\">"
									   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
									   +"</div>"
									   +"</div>");
							getCrmCodeByParentCodeNo(data[i].sysCode,name);
							//getSysCodeByParentCodeNo(j.value,name);
						}else if(data[i].codeType=="自定义选项"){
							var optionNames=data[i].optionName.split(",");
							var optionValues=data[i].optionValue.split(",");
							$("#customTbody").append("<div class=\"mui-input-group\">"
									   +"<div class=\"mui-input-row\">"
									   +"<label>"+data[i].extendFiledName+"</label>"
									   +"</div>"
									   +"<div class=\"mui-input-row\">"
									   +    "<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
									   +"</div>"
									   +"</div>");
							for(var j=0;j<optionNames.length;j++){
								$("#"+name).append("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
							}
							
						}
						
					}
					
				}
			}
		}
	});

}


//地图选点
function selectMapPoint(){
	var province=$("#province").val();
	var district=$("#district").val();
	var city=$("#city").val();
	var addr=province+city+district;
	var url=contextPath+"/system/mobile/phone/crm/customer/map.jsp?addr="+addr;
	$("#mapFrameDiv").show();
	$("#mapFrame").attr("src",url);
}


//验证
function check(){
	var customerName=$("#customerName").val();
	var customerNum=$("#customerNum").val();
	var managePersonId=$("#managePersonId").val();
	if(customerName==""||customerName==null){
		alert("请输入客户名称！");
		return false;	
	}
	if(customerNum==""||customerNum==null){
		alert("请输入客户编号！");
		return false;	
	}
	if(managePersonId==""||managePersonId==null){
		alert("请选择负责人！");
		return false;	
	}
	
	return true;
}

//判断客户名称是否已经存在
function checkCustomerIsExist(){
	var customerName=$("#customerName").val();
	var url=contextPath+"/TeeCrmCustomerController/isExistCustomer.action";
	var bool=false;
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		async:false,
		data:{customerName:customerName,sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				if(data==1){
					alert("客户名称已存在，请重新填写！");
					bool=false;;
				}else{
					bool=true;
					
				}
			}
		}
	});
	return bool;
	
}

//保存/提交
function save(){
	if(check()&&checkCustomerIsExist()){
		 url=contextPath+"/TeeCrmCustomerController/addOrUpdate.action";
		var param=formToJson("#form1");
		var customerName = param.customerName;
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
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerInfo.jsp?sid="+sid+"&customerName="+customerName+"&type="+type;
				}else{
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/index.jsp?type="+type;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/index.jsp?type="+type;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);
	//选择共享人员
	sharePersonNames.addEventListener('tap', function() {
		selectUser("sharePersonIds","sharePersonNames");
	}, false);
});
</script>
<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>