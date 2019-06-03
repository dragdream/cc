<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
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
		<!-- <div id="middlePopover" class="mui-popover">
				<ul class="mui-table-view" id="aaa">
					
				</ul>
		</div> -->
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属客户</label>
		</div>
		<div class="mui-input-row">
		    <input type="text" id="customerName"  name="customerName" style="font-size: 14px;"  placeholder="请选择所属客户"   onclick="selectCustomer();" >
		    <input type="hidden" id="customerId" name="customerId"/>
		    <iframe id="iframe1" src="" 
			  	style="display:none;;border: none;position: fixed;left: 10%;width:80%;
			  	top: 30%;height:40%;z-index: 10;" scrolling="yes"></iframe>
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
var cusName = "<%=customerName%>";
var type= "<%=type%>";
var loginPersonId = <%=loginPersonId%>;
var userName = "<%=userName%>";
function doInit(){
	//初始化客户数据
	//selectCustomer();
	getCrmCodeByParentCodeNo("VISIT_TOPIC","visitTopic");//拜访主题
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	
	
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

function selectCustomer(){
	//$("#iframe2")[0].contentWindow
	var iframe_t = document.getElementById('iframe1');
 	if(iframe_t.style.display =='block'){
		return;
	}
	$(iframe_t).slideDown(); 
	//iframe_t.style.display='block'; 
	$(".shadow").fadeIn();
	$("body").css("overflow","hidden");
	iframe_t.src='../orders/selectCustomers.jsp'; 
	
	//window.location = 'selectCustomers.jsp';

}

function selectCustomerCallBackFunc(sid,name){
	$("#customerName").val(" ");
	$("#customerId").val();
	 if(sid>0){
		$("#customerName").val(name);
		$("#customerId").val(sid);
	} 
	
}

function operPage(){
	changeShadow();
	//显示隐藏有一个异步的过程 执行该行代码是隐藏尚未完成
	if(!$("#shadow").is(":hidden")){
		$("body").css("overflow","auto");
	}
}
function changeShadow(){//这里要处理下 看是谁返回的 不然都宣布显示了
	$("#shadow").fadeToggle();
	$("#iframe1").fadeToggle();
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
	var customerId = $("#customerId").val();
	if(customerId=="" || customerId=="null" || customerId==null|| customerId=="0"){
		alert("请选择所属客户！");
		return false;
	}
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
			 url=contextPath+"/TeeCrmVisitController/addOrUpdate.action";
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
						window.location.href=contextPath+"/system/mobile/phone/crm/visit/visitInfo.jsp?sid="+sid+"&customerName="+cusName+"&type="+type;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/visit/index.jsp?type="+type;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/visit/index.jsp?type="+type;
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