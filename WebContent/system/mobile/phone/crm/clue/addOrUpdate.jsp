<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int type=TeeStringUtil.getInteger(request.getParameter("type"), 0);
	String cusName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String title="";
    if(sid>0){
    	title="编辑线索";
    }else{
    	title="新建线索";	
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
			<label>姓名</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="姓名" name="name" id="name">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司名称</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="公司名称" name="companyName" id="companyName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>销售线索详情</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="culeDetail" id="culeDetail" placeholder="销售线索详情" ></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>来源</label>
		</div>
		<div class="mui-input-row">
		     <select id="clueSource" placeholder="来源"  name="clueSource" style="height: 23px;width: 150px;font-size: 14px;">
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="部门" name="department" id="department">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>职务</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="职务" name="duties" id="duties">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="电话" name="telephone" id="telephone">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>手机</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="手机" name="mobilePhone" id="mobilePhone">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>网址</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="网址" name="url" id="url">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮箱</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="邮箱" name="email" id="email">
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>地址</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="地址" name="address" id="address">
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var type = "<%=type%>";
var cusName = "<%=cusName%>";
function doInit(){

	getCrmCodeByParentCodeNo("CLUE_SOURCE","clueSource"); //线索来源

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



//编辑的时候获取初始化数据
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmClueController/getInfoBySid.action";
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


//验证
function check(){
	var clueName=$("#name").val();
	var companyName = $("#companyName").val();
	var culeDetail = $("#culeDetail").val();
	var telephone = $("#telephone").val();
	if(clueName==""||clueName==null){
		alert("请填写姓名！");
		return false;	
	}
	if(companyName==""||companyName==null){
		alert("请填写公司！");
		return false;	
	}
	if(culeDetail==""||culeDetail==null){
		alert("请填写线索详情！");
		return false;	
	}
	if(telephone==""||telephone==null){
		alert("请填写联系电话！");
		return false;	
	}
	
	return true;
}


//保存/提交
function save(){
	if(check()){
		 url=contextPath+"/TeeCrmClueController/addOrUpdate.action";
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
					window.location.href=contextPath+"/system/mobile/phone/crm/clue/clueInfo.jsp?sid="+sid+"&customerName="+cusName;
				}else{
					window.location.href=contextPath+"/system/mobile/phone/crm/clue/index.jsp?type="+type;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/clue/index.jsp?type="+type;
		}
	});
	
});
</script>

</body>
</html>