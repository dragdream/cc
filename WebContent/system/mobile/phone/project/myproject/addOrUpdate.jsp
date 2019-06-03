<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String uuid = request.getParameter("uuid");
    String title="";
    if(uuid!=null&&!("").equals(uuid)){
    	title="项目变更";
    }else{
    	title="新建项目";	
    }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>项目编号</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="项目编号" name="projectNum" id="projectNum">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目类型</label>
		</div>
		<div class="mui-input-row">
			 <select id="projectTypeId" onchange="renderCustomField(0)" name="projectTypeId" style="height: 23px;width: 150px">
        
        </select>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>项目名称</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="项目名称" name="projectName" id="projectName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目计划开始</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='startTime' name='startTime' class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目计划结束</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='endTime' name='endTime'  class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目区域 (省)</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<select id="province" name="province" style="height: 23px;width: 110px"></select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目区域 (市)</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <select id="city" name="city" style="height: 23px;width: 110px"></select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目区域 (区)</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <select id="district" name="district" style="height: 23px;width: 110px"></select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目位置</label>
			<label><a href="javascript:void(0)" onclick="selectMapPoint()">地图选点</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" name="addressDesc" id="addressDesc" style="width: 350px" placeholder="请选择项目具体位置" />
		    
		    <input type="hidden" name="coordinate" id="coordinate" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目级别</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			 <select name="projectLevel" id="projectLevel" style="height: 23px;width: 150px">
		       <option value="A">A</option>
		       <option value="B">B</option>
		       <option value="C">C</option>
		       <option value="D">D</option>
		    </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目预算</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="项目预算" name="projectBudget"  id="projectBudget" style="height:23px;width: 350px"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="managerName" name="managerName" readonly placeholder="请选择项目负责人" />
			<input type="hidden" id="managerId" name="managerId"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目成员</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <input type="text" id="projectMemberNames" name="projectMemberNames" readonly placeholder="请选择项目成员" />
			<input type="hidden" id="projectMemberIds" name="projectMemberIds"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>项目观察者</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			 <input type="text" id="projectViewNames" name="projectViewNames" readonly placeholder="请选择项目观察者" />
			<input type="hidden" id="projectViewIds" name="projectViewIds"/>
		</div>
	</div>
	<div class="mui-input-group"  id="approverDiv" style="display: none">
		<div class="mui-input-row">
			<label>项目审批人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <select id="approverId" name="approverId" style="width: 150px;height: 23px">
		   
		   </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="projectContent" id="projectContent" placeholder="项目内容" ></textarea>
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li id="saveBtn" class="mui-table-view-cell" onclick="save(1);" style="display: none">保存</li>
		   <li class="mui-table-view-cell" onclick="save(2)">提交</li>
		  </ul>
	</div>

<script>
var uuid="<%=uuid%>";

function doInit(){
	//判断是项目变更  还是新增项目    如果是项目变更则不显示保存按钮  值显示提交   如果是新增    两个按钮都显示
	if(uuid!=""&&uuid!=null&&uuid!="null"){//项目变更
		getInfoByUuid(uuid);  
	}else{
		$("#saveBtn").show();
	}
	
	
	
	initProjectType();
	if(isNoApprove()==0){ //不是免审批人员
		$("#approverDiv").show();
		initApprover();
	}

	initAddressCtr();
	var projectTypeId=$("#projectTypeId").val();
	renderCustomField(projectTypeId);
	
	if(uuid!=""&&uuid!=null&&uuid!="null"){//编辑
		getInfoByUuid(uuid);
	    
	}
	
	
	//启动加载完毕的逻辑	
	//开始时间
	startTime.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		startTime.value = rs.text;	
		picker.dispose(); 
		});
	}, false);

	
	endTime.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		endTime.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
}
//根据主键获取详情
function getInfoByUuid(uuid){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{uuid:uuid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var projectTypeId=json.rtData.projectTypeId;
				renderCustomField(projectTypeId);
				bindJsonObj2Cntrl(json.rtData);
			}
		}
	});
}


//判断当前登录的用户是不是可以免审批
function isNoApprove(){	
	var url=contextPath+"/projectApprovalRuleController/isNoApprove.action";
	var bool=0;
	mui.ajax(url,{
		type:"post",
		async:false,
		dataType:"html",
		//data:{state:-1,rows:20,page:page++},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				bool = data;
			}else{
				bool = 0;
			}
		}
	});
	return bool;
}

//初始化级联地址控件
function initAddressCtr(){
	addressInit("province","city","district");
}
//初始化项目类型
function initProjectType(){
	var url=contextPath+"/projectTypeController/getTypeList.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		//data:{state:-1,rows:20,page:page++},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					$("#projectTypeId").append("<option value="+data[i].sid+" >"+data[i].typeName+"</option>");
				}
			}
		}
	});
}
//动态渲染自定义字段
function renderCustomField(projectTypeId){
	$("#customTbody").html("");
	if(projectTypeId==0){
		projectTypeId=$("#projectTypeId").val();
	}
	var url=contextPath+"/projectCustomFieldController/getListByProjectType.action";
	
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{projectTypeId:projectTypeId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					var name="FIELD_"+data[i].sid;
					if(data[i].fieldType=="单行输入框"){
						$("#muiContent").append("<div class=\"mui-input-group\">"
								+"<div class=\"mui-input-row\">"
								+"<label>"+data[i].fieldName+"</label>"
								+"</div>"
								+"<div class=\"mui-input-row\" style=\"height:inherit\">"
								+"<input type=\"text\" name='"+name+"' id='"+name+"' style=\"height: 23px;width: 350px\" placeholder='"+data[i].fieldName+"' />"
								+"</div>"
								+"</div>");
					}else if(data[i].fieldType=="多行输入框"){
						$("#muiContent").append("<div class=\"mui-input-group\">"
								+"<div class=\"mui-input-row\">"
								+"<label>"+data[i].fieldName+"</label>"
								+"</div>"
								+"<div class=\"mui-input-row\" style=\"height:inherit\">"
								+"<textarea  type=\"text\" rows=\"6\" name='"+name+"' id='"+name+"' style=\"width: 550px\" placeholder='"+data[i].fieldName+"' ></textarea>"
								+"</div>"
								+"</div>");
					}else if(data[i].fieldType=="下拉列表"){
						var fieldCtrModel=data[i].fieldCtrModel;
						var j= eval("("+fieldCtrModel+")");
						if(j.codeType=="系统编码"){
							$("#muiContent").append("<div class=\"mui-input-group\">"
									+"<div class=\"mui-input-row\">"
									+"<label>"+data[i].fieldName+"</label>"
									+"</div>"
									+"<div class=\"mui-input-row\" style=\"height:inherit\">"
									+"<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
									+"</div>"
									+"</div>");
							getSysCodeByParentCodeNo(j.value,name);
						}else if(j.codeType=="自定义选项"){
							var values=j.value;
							var optionNames=values[0].split(",");
							var optionValues=values[1].split(",");
							$("#muiContent").append("<div class=\"mui-input-group\">"
									+"<div class=\"mui-input-row\">"
									+"<label>"+data[i].fieldName+"</label>"
									+"</div>"
									+"<div class=\"mui-input-row\" style=\"height:inherit\">"
									+"<select  name='"+name+"' id='"+name+"' style=\"height:23px;width:150px\" ></select>"
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
//初始化项目审批人
function initApprover(){
	var url=contextPath+"/projectApprovalRuleController/getApproverByLoginUser.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		//data:{state:-1,rows:20,page:page++},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					$("#approverId").append("<option value="+data[i].uuid+" >"+data[i].userName+"</option>");
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
	var url=contextPath+"/system/mobile/phone/project/myproject/map.jsp?addr="+addr;
	$("#mapFrameDiv").show();
	$("#mapFrame").attr("src",url);
}




//验证
function check(){
	var projectNum=$("#projectNum").val();
	var projectName=$("#projectName").val();
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var managerId=$("#managerId").val();
	var approverId=$("#approverId").val();
	
	if(projectNum==""||projectNum==null){
		alert("请填写项目编号！");
		return false;	
	}
	if(projectName==""||projectName==null){
		alert("请填写项目名称！");
		return false;	
	}
	if(startTime==""||startTime==null){
		alert("请填写项目开始时间！");
		return false;	
	}
	if(endTime==""||endTime==null){
		alert("请填写项目结束时间！");
		return false;	
	}
	if(managerId==""||managerId==null){
		alert("请选择项目负责人！");
		return false;	
	}
	if(isNoApprove()==0){//需要审批
		if(approverId==""||approverId==null){
			alert("请选择项目审批人！");
			return false;	
		}	
	}
	
	
	var start=new Date(startTime.replace(/\-/g,'/'));
	var end=new Date(endTime.replace(/\-/g,'/'));
	if(start>end){
		alert("开始时间不能大于结束时间！");
		return false;
	}
	
	
	return true;
}

//判断项目编号是否已经存在
function checkPNumIsExist(){
	var projectNum=$("#projectNum").val();
	var url=contextPath+"/projectController/isExistPNum.action";
	//var json=tools.requestJsonRs(url,{projectNum:projectNum,uuid:uuid});
	var bool=false;
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		async:false,
		data:{projectNum:projectNum,uuid:uuid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				if(data==1){
					alert("项目编号已存在，请重新填写！");
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
function save(status){
	if(check()&&checkPNumIsExist()){
		var url=contextPath+"/projectController/addOrUpdate.action";
		var param=formToJson("#form1");
		param['status']=status;
		param['uuid']=uuid;
		param["isPhone"]=1;
		//var json=tools.requestJsonRs(url,param);
		mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:param,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				if(status==1){//保存  
					window.location.href=contextPath+"/system/mobile/phone/project/myproject/index.jsp?status=1";
				}else if(status==2){//提交
					if(isNoApprove()==0){//不是免审批人员
						window.location.href=contextPath+"/system/mobile/phone/project/myproject/index.jsp?status=2";
					}else if(isNoApprove()==1){//免审批
						window.location.href=contextPath+"/system/mobile/phone/project/myproject/index.jsp?status=3";
					}
				}
				
			}
		}
	});	
   }	
}





mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		history.go(-1);
	});
	
	//选择项目负责人
	managerName.addEventListener('tap', function() {
		selectSingleUser("managerId","managerName");
	}, false);
	//选择项目成员
	projectMemberNames.addEventListener('tap', function() {
		selectUser("projectMemberIds","projectMemberNames");
	}, false);
	//选择项目观察者
	projectViewNames.addEventListener('tap', function() {
		selectUser("projectViewIds","projectViewNames");
	}, false);
});
</script>
<div id="mapFrameDiv" style="display:none;z-index:10000000;position:fixed;top:0px;bottom:0px;left:0px;right:0px;background:white">
<iframe id="mapFrame" frameborder="no" style="width:100%;height:100%"></iframe>
</div>

</body>
</html>