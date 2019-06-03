<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目查询</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	
	
	<h1 class="mui-title">项目查询</h1>
	<button id="handleBtn" class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='query()' >查询</button>
</header>

<div id="muiContent" class="mui-content" >
<form id="form1" name="form1" action="list.jsp" method="post">
	<div class="mui-input-group" >
	    <div class="mui-input-row">
			<label>项目名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="项目名称" name="projectName" id="projectName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目编码</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="项目编码" name="projectNum" id="projectNum">
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>项目类型</label>
		</div>
		<div class="mui-input-row">
		   <select id="projectTypeId" name="projectTypeId" onchange="renderCostomFileds()">
              <option value="0">全部</option>
           </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目成员</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <input type="text" id="userName" name="userName" readonly placeholder="请选择项目成员" />
			<input type="hidden" id="userId" name="userId"/>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>起始时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='beginTime1' name='beginTime1'  placeholder="请选择最小起始时间"  style="width:155px"/>
		    &nbsp;~&nbsp;
		    <input type="text" id='beginTime2' name='beginTime2'  placeholder="请选择最大起始时间"  style="width:155px"/>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='endTime1' name='endTime1'  placeholder="请选择最小结束时间" style="width:155px"/>
		    &nbsp;~&nbsp;
		    <input type="text" id='endTime2' name='endTime2'  placeholder="请选择最小结束时间" style="width:155px"/>
		</div>
	</div>
	
	
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目状态</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			 <select id="status" name="status">
                  <option value="0">全部</option>
                  <option value="1">立项中</option>
                  <option value="2">审批中</option>
                  <option value="3">办理中</option>
                  <option value="4">挂起中</option>
                  <option value="5">已结束</option>
                  <option value="6">未批准</option>
               </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目进度</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <input type="text" name="minProgress" id="minProgress" style="width:155px" placeholder="最小进度值"/>
		   &nbsp;~&nbsp;
		   <input type="text" name="maxProgress" id="maxProgress" style="width:155px" placeholder="最大进度值"/>
		</div>
	</div>

</form>	
</div>
<script>
//初始化方法
function doInit(){
	//选择项目成员
	userName.addEventListener('tap', function() {
		selectUser("userId","userName");
	}, false);
	
	//开始时间
	beginTime1.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		beginTime1.value = rs.text;	
		picker.dispose(); 
		});
	}, false);

	beginTime2.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		beginTime2.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	endTime1.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		endTime1.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	endTime2.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		endTime2.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	
	initProjectType();//初始化项目类型
	renderCostomFileds();//渲染自定义查询字段
}


//初始化项目类型
function initProjectType(){
	var url=contextPath+"/projectTypeController/getTypeList.action";	
	mui.ajax(url,{
		type:"post",
		async:false,
		dataType:"html",
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					$("#projectTypeId").append("<option value="+data[i].sid+">"+data[i].typeName+"</option>");
				}
			}
		}
	});
}



//渲染自定义字段 查询字段
function renderCostomFileds(){
	var projectTypeId=$("#projectTypeId").val();
	if(projectTypeId>0){
		var url=contextPath+"/projectCustomFieldController/getQueryFieldListByProjectTypeId.action";
		
		mui.ajax(url,{
			type:"post",
			async:false,
			data:{projectTypeId:projectTypeId},
			dataType:"html",
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				
				if(json.rtState){
					var data=json.rtData;
					
					//先移除之前渲染的自定义字段的数据
					$(".customDiv").remove();
		
					var html = [];	

					for(var i=1;i<=data.length;i++){	
						
						var name="FIELD_"+data[i-1].sid;
					    html.push("<div class=\"mui-input-group customDiv\">");
						html.push("<div class=\"mui-input-row\">");
						html.push("<label>"+data[i-1].fieldName+"</label>");
						html.push("</div>");
						html.push("<div class=\"mui-input-row\">");
						
						
						if(data[i-1].fieldType=="单行输入框"){
							html.push("<input type=\"text\" name='"+name+"' id='"+name+"' placeholder='"+data[i-1].fieldName+"' />");
							html.push("</div>");
						}else if(data[i-1].fieldType=="多行输入框"){
							html.push("<textarea   placeholder='"+data[i-1].fieldName+"'   name='"+name+"' id='"+name+"' ></textarea>");
							html.push("</div>");
						}else if(data[i-1].fieldType=="下拉列表"){
							var fieldCtrModel=data[i-1].fieldCtrModel;
							var j= eval("("+fieldCtrModel+")");
							if(j.codeType=="系统编码"){
								html.push("<select  name='"+name+"' id='"+name+"'  >");
								html.push("<option value=''>全部</option>");
								var url1 =   contextPath + "/sysCode/getSysCodeByParentCodeNo.action";
								
								mui.ajax(url1,{
									type:"post",
									async:false,
									dataType:"html",
									timeout:10000,
									data:{codeNo:j.value},
									success:function(jsonObj){
										jsonObj = eval("("+jsonObj+")");
										if(jsonObj.rtState){
											var prcs = jsonObj.rtData;
											for ( var n = 0; n < prcs.length; n++) {
												html.push("<option value='"+prcs[n].codeNo+"'>" + prcs[n].codeName + "</option>");
											}				
										}
									}
								});
								
								
								html.push("</select>");
								html.push("</div>");
								/* getSysCodeByParentCodeNo(j.value,name); */
							}else if(j.codeType=="自定义选项"){
								
								var values=j.value;
								var optionNames=values[0].split(",");
								var optionValues=values[1].split(",");
								html.push("<select  name='"+name+"' id='"+name+"'  >");
								html.push("<option value=''>全部</option>");
								for(var j=0;j<optionNames.length;j++){
									html.push("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
								
								}	
								html.push("</select>");	
								html.push("</div>");	
							}	
						}
					}
					$("#form1").append(html.join(""));
				}
			}
		});
		
		
		
	}else{
		//先移除之前渲染的自定义字段的数据
		$(".customDiv").remove();
	}
}



mui.ready(function() {
	
	backBtn.addEventListener("tap",function(){
		window.location.href = "../index.jsp";
	});//返回
	
});


//查询方法
function query(){
	$("#form1").submit();
}
</script>
</body>
</html>