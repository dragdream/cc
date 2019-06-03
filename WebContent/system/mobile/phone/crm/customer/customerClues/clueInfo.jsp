<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>线索详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

#topPopover1 {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}

.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
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
</head>

<script type="text/javascript">
var sid = "<%=sid%>";
var customerId = "<%=customerId%>";
var loginPersonId = <%=loginPersonId%>;
var type = <%=type%>;
//初始化方法
function doInit(){
	if(sid>0){
		getInfoBySid(sid);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
 		history.go(-1);
	});
	
}



//渲染操作
function renderOptBtns(data){
	var str;
	$("#topPopover1").empty();
 	if(managerPerId==loginPersonId){
 		//客户下的线索都是通过线索转换客户来的
			$("#topTop").show();
			str ='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById('+sid+');">删除</li>'+
			' </ul>';
		}else{
			$("#topTop").hide();
		}
 	$("#topPopover1").append(str);

}

var managerPerName = '';
var managerPerId = "";
//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmClueController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var str = "";
			if(json.rtState){
				var data=json.rtData;
				var customerName = data.customerName;
				//绑定客户姓名
				if(customerName!=null){
					str= "<div class=\"mui-input-group\">"
						+"<div class=\"mui-input-row\">"
						+"<label>客户名称</label>"
						+"</div>"
						+"<div class=\"app-row-content\" id=\"customerName\" name=\"customerName\">"
						+"</div>"
						+"</div>";
				}
				//绑定联系人姓名
				if(data.contactName!=null){
					str += "<div class=\"mui-input-group\">"
						+"<div class=\"mui-input-row\">"
						+"<label>联系人名称</label>"
						+"</div>"
						+"<div class=\"app-row-content\" id=\"contactName\" name=\"contactName\">"
						+"</div>"
						+"</div>";
				}
				//绑定商机
				if(data.chanceName!=null){
					str += "<div class=\"mui-input-group\">"
						+"<div class=\"mui-input-row\">"
						+"<label>商机名称</label>"
						+"</div>"
						+"<div class=\"app-row-content\" id=\"chanceName\" name=\"chanceName\">"
						+"</div>"
						+"</div>";
				}
				$("#muiContent").prepend(str);
				
				bindJsonObj2Cntrl(data);
				//获取当前负责人
				managerPerName = data.managePersonName;
				managerPerId = data.managePersonId;
				var clueStatus = data.clueStatusDesc;
				renderOptBtns(clueStatus);
				
			}
		}
	});
}


/**
 * 删除
 */
function deleteById(id){
	if(window.confirm("确定删除选中线索？")){
		var url = contextPath+ "/TeeCrmClueController/delById.action";
		var param={sids:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("删除成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/customer/customerClues/clueList.jsp?type="+type+"&customerId="+customerId;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
}


</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
		    <span class="mui-icon mui-icon-left-nav" ></span>返回
		</button>
	    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right"  href="#topPopover1" id="topTop" style="display: none;">
	        <span style="padding-right: 10px;"><a href="#topPopover1">操作</a></span>
	    </button> 
	    <h1 class="mui-title">线索详情</h1>
		
	</header>
<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>姓名</label>
		</div>
		<div class="app-row-content" id="name">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司名称</label>
		</div>
		<div class="app-row-content" id="companyName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>销售线索详情</label>
		</div>
		<div class="app-row-content" id="culeDetail">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>来源</label>
		</div>
		<div class="app-row-content" id="clueSourceDesc" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		<div class="app-row-content" id="department">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>职务</label>
		</div>
		<div class="app-row-content" id='duties'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="app-row-content" id='telephone'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>手机</label>
		</div>
		<div class="app-row-content" id='mobilePhone'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>网址</label>
		</div>
		<div class="app-row-content" id='url'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮箱</label>
		</div>
		<div class="app-row-content" id='email'>
		
		</div>
	</div>
	<div class="mui-input-group" id="approverDiv" style="display: none;">
		<div class="mui-input-row">
			<label>地址</label>
		</div>
		<div class="app-row-content" id='address'>
		
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>处理结果</label>
		</div>
		<div class="app-row-content" id='dealResult'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="app-row-content" id='managePersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='clueStatusDesc'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建人</label>
		</div>
		<div class="app-row-content" id='addPersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建时间</label>
		</div>
		<div class="app-row-content" id='createTimeDesc'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>最后变化时间</label>
		</div>
		<div class="app-row-content" id='lastEditTimeDesc'>
			
		</div>
			
	</div>

</div>

    <div id="topPopover1" class="mui-popover">
	
	</div>

<br/><br/><br/>
</body>
</html>