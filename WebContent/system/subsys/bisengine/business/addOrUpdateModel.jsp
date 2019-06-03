<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%
	String bisKey = request.getParameter("bisKey");
    String type=request.getParameter("type");
%>
<title>编辑业务模型</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var bisKey ='<%=bisKey%>';
var contextPath='<%=contextPath%>';
var type='<%=type%>';
function doInit() {
	//获取所有的业务分类
	getBusinessCatList();	
	//获取所有的业务表单
	getBisFormList();
	//动态渲染字段
	renderFields();
	//渲染tableField
	renderTableField();
	//编辑   初始化数据
	if(type=="update"){
		var url = contextPath+"/businessModelController/getBusinessModelByBisKey.action";
		var json = tools.requestJsonRs(url,{bisKey:bisKey});
		if(json.rtState){
			var data = json.rtData;
		    bindJsonObj2Cntrl(data);
		    //渲染触发步骤
		    renderFlowStep();
		    //给触发步骤赋值
		    $("#flowStep").val(data.flowStep);
		    //渲染流程字段
		    renderFlowField();
		    
		    //渲染映射关系
		    var mappingList=data.mappingList;
		    if(mappingList!=null&&mappingList.length>0){
		    	for(var n=0;n<mappingList.length;n++){
		    		
		    		var item = $("<div value='"+(mappingList[n].flowItemId+":"+mappingList[n].tableFieldId)+"'>["+(mappingList[n].flowFieldName+"]=&gt;["+mappingList[n].tableFieldName)+"]&nbsp;<img src='"+systemImagePath+"/upload/remove.png'  style='cursor:pointer;vertical-align: middle;' onclick='remove(this)'/></div>");
		    		$("#mappingContainer").append(item);
		    	}
		    }
		    
		}
		//动态渲染字段
		renderFields();
		//获取操作的数据
		var operate=json.rtData.businessOperation.split(",");
		var operation=document.getElementsByName("operation");
		for (var i=0;i<operation.length;i++){
		  for(var j=0;j<operate.length;j++){
			  if(operation[i].value==operate[j]){
				  operation[i].checked=true;
			  }
		  }
		}
		//获取表头字段
		var header=json.rtData.headerFields.split(",");
	    var hField=$("#hField input");
	    for(var a=0;a<hField.length;a++){
	    	for(var b=0;b<header.length;b++){
	    		if(hField[a].value == header[b]){
	    			hField[a].checked=true;
		  	  }
	    	}
	    }
	    
	  //获取查询字段
		var query=json.rtData.queryFields.split(",");
	    var qField=$("#qField input");
	    for(var c=0;c<qField.length;c++){
	    	for(var d=0;d<query.length;d++){
	    		if(qField[c].value == query[d]){
	    			qField[c].checked=true;
		  	  }
	    	}
	    }
	   //获取列表分组字段
	   var  group=json.rtData.groupField;
	   $("#group").val(group);
	   
	   //获取列表排序字段
	   var order=json.rtData.orderField;
	   //转换成json对象
	   var orders =tools.strToJson(order);
	   var orderField=orders.fieldId;
	   var orderType=orders.orderType;
	   $("#order").val(orderField);
	   $("#orderType").val(orderType);
		
	}
}

function commit() {
	if (!$("#form1").valid()) {
		return false;
	}
	var operation=document.getElementsByName("operation");
	var operationStr="";
	for (var i=0;i<operation.length;i++){
	  if(operation[i].checked == true){
	      operationStr=operationStr+operation[i].value+",";
	  }
	}
	operationStr=operationStr.substring(0, operationStr.length-1);
    //将操作放到隐藏域
    $("#businessOperation").val(operationStr);
	
    //获取表头字段
    var hField=$("#hField input");
    var hFieldStr="";
    for(var j=0;j<hField.length;j++){
    	if(hField[j].checked == true){
  			hFieldStr=hFieldStr+hField[j].id+",";
  	  }
    }
    hFieldStr=hFieldStr.substring(0, hFieldStr.length-1);
     //将表头字段放到隐藏域
    $("#headerFields").val(hFieldStr);
     
     
  //获取查询字段
    var qField=$("#qField input");
    var qFieldStr="";
    for(var k=0;k<qField.length;k++){
    	if(qField[k].checked == true){
  			qFieldStr=qFieldStr+qField[k].id+",";
  	  }
    }
    qFieldStr=qFieldStr.substring(0, qFieldStr.length-1);
     //将查询字段放到隐藏域
    $("#queryFields").val(qFieldStr);

	
    //获取排序字段
    var order=$("#order").val();
    var orderType=$("#orderType").val();
    var orderStr="{fieldId:'"+order+"',orderType:'"+orderType+"'}";
    //将排序字段放到隐藏域
    $("#orderField").val(orderStr);
    
    
    //获取分组字段
     var groupStr=$("#group").val();
   //将分组字段放到隐藏域
     $("#groupField").val(groupStr);
	
   
   //获取映射关系
   var mapping=[];
   $("#mappingContainer").find("div").each(function(i,obj){
		var value = $(obj).attr("value");
		var flowItemId=value.split(":")[0];
		var tableFieldId=value.split(":")[1];
		mapping.push({flowItemId:flowItemId,tableFieldId:tableFieldId});
	});
   $("#mapping").val(tools.jsonArray2String(mapping));
   
    //获取业务编码
     var bisKeyNew=$("#bisKey").val();
	 if((type=="add"&&checkBisKey())||(type=="update"&&bisKeyNew==bisKey)||(type=="update"&&bisKeyNew!=bisKey&&checkBisKey())){
		var url =contextPath+ "/businessModelController/addOrUpdate.action?type="+type;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
				back();
			});
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}else{
		$.MsgBox.Alert_auto("业务编号已存在，请重新输入！");
	} 		
}

//验证业务编号是否存在
function checkBisKey(){
	var bisKey=$("#bisKey").val();
	var url =contextPath+ "/businessModelController/checkSidIsExist.action";
	var json = tools.requestJsonRs(url,{bisKey:bisKey});
	return json.rtState;
}

$(function(){
	
	//业务表单发生变化   字段发生变化
	$("#bisFormId").bind("change",function(){
		renderFields();	
		renderTableField();
		
		//清空映射
		$("#mappingContainer").html("");
	});
	
	
});

//动态渲染字段
function renderFields(){
	var formId=$("#bisFormId").val();
	var url = contextPath+"/bisFormController/getFields.action";
	var json = tools.requestJsonRs(url,{sid:formId});
	 //动态添加所属表格
	if(json.rtState){
		var html1="";
		var html2="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html1+=("<option value="+datas[i].sid+">"+datas[i].fieldName+"</option>");
		}
		$("#order").html(html1);
		$("#group").html("<option value=''></option>"+html1);	
		
		
		for(var i=0;i<datas.length;i++){	
			html2+=("<input type='checkbox' name='"+datas[i].fieldName+"' id='"+datas[i].sid+"' value='"+datas[i].sid+"' />&nbsp;"+datas[i].fieldDesc+"&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;&nbsp;&nbsp;");
			if((i+1)%3==0){
				html2+=("<br/>");
			}
		}
		
		$("#hField").html(html2);
		$("#qField").html(html2);	
		
	} 

}
//动态获取所有的业务表单
function getBisFormList(){
	var url = contextPath+"/bisFormController/getAllBisFormList.action";
	var json = tools.requestJsonRs(url,{});
	 //动态添加所属表格
	if(json.rtState){
		var html="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html+=("<option value="+datas[i].sid+">"+datas[i].formName+"</option>");
		}
		$("#bisFormId").html(html);
	} 
}
//动态获取业务分类列表  
function getBusinessCatList(){
	var url = contextPath+"/businessCatController/getBusinessCatList.action";
	var json = tools.requestJsonRs(url,{});
	 //动态添加所属表格
	if(json.rtState){
		var html="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			html+=("<option value="+datas[i].sid+">"+datas[i].catName+"</option>");
		}
		$("#businessCatId").html(html);
	} 
}
//返回
function back(){
	window.location=contextPath+"/system/subsys/bisengine/business/managerModel.jsp";
}


//选择绑定流程
function selectFlowType(){
	bsWindow(contextPath+"/system/subsys/bisengine/business/flowTree.jsp","选择流程",{width:"400",height:"250",buttons:
		[{name:"选择",classStyle:"btn-alert-blue"} ,
	 	 {name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="选择"){
			if(isNaN(parseInt(h.contents().find("#flowId").val()))){//不是数字
  		    	$.MsgBox.Alert_auto("请选择流程！");
  		        return;
  		    }else{
  		    	//获取弹出页面的流程的名称和流程的id
                 $("#flowName").val(h.contents().find("#flowName").val());
                 $("#flowId").val(h.contents().find("#flowId").val());  
                 
                 //渲染相关流程步骤
                 renderFlowStep();
                 //渲染流程表单字段
                 renderFlowField();
                 
             	//清空映射
             	$("#mappingContainer").html("");
  		    }
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//清空绑定的数据
function clearFlow(){
	$("#flowName").val("");
	$("#flowId").val("");
	
	//清空相关触发步骤
	$("#flowStep").empty();
	//清空流程字段
	$("#flowField").empty();
	//清空映射
	$("#mappingContainer").html("");
}


//渲染流程相关步骤
function renderFlowStep(){
	var flowId=$("#flowId").val();
	//先清空select框
	$("#flowStep").empty();
	//渲染绑定流程的步骤
	var url=contextPath+"/flowProcess/getProcessList.action";
	var json=tools.requestJsonRs(url,{flowId:flowId});
	var render=[];
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].prcsType!=2){//去除结束节点
					render.push("<option value="+data[i].sid+">"+data[i].prcsName+"</option>");
				}
				
			}
		}
		$("#flowStep").append(render.join(""));
	}
}


//渲染流程表单字段
function renderFlowField(){
	//先清空select框
	$("#flowField").empty();
	
	var flowId=$("#flowId").val();
	var url=contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json=tools.requestJsonRs(url,{flowId:flowId});
	var render=[];
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				render.push("<option value="+data[i].itemId+">"+data[i].title+"</option>");
			}
		}
		$("#flowField").append(render.join(""));
	}
	
}

//渲染tableField
function renderTableField(){
	var formId=$("#bisFormId").val();
	var url = contextPath+"/bisFormController/getFields.action";
	var json = tools.requestJsonRs(url,{sid:formId});
	 //动态添加所属表格
	if(json.rtState){
		var html1="";
		var datas = json.rtData;
		for(var i=0;i<datas.length;i++){
			if(datas[i].primaryKeyFlag!=1){
				html1+=("<option value="+datas[i].sid+">"+datas[i].fieldDesc+"</option>");
			}
		}
		$("#tableField").html(html1);
	} 
}


function doMapping(){
	
	if($("#flowField").val()=="" ||$("#flowField").val()=="null"||$("#flowField").val()==null|| $("#tableField").val()==""||$("#tableField").val()=="null"||$("#tableField").val()==null){
		$.MsgBox.Alert_auto("双向都需要设置映射字段！");
		return;
	}
	var left = $("#flowField").val();
	var right = $("#tableField").val();
	
	var leftOption=$("#flowField option:selected");  //获取选中的项
	var leftText=leftOption.text();
	
	var rightOption=$("#tableField option:selected");  //获取选中的项
	var rightText=rightOption.text();
	
	var hasExist = false;
	$("#mappingContainer").find("div").each(function(i,obj){
		var value = $(obj).attr("value");
		if(value==(left+":"+right)){
			hasExist = true;
		}
	});

	if(hasExist){
		$.MsgBox.Alert_auto("已存在关系映射","info");
		return;
	}

	var item = $("<div value='"+(left+":"+right)+"'>["+(leftText+"]=&gt;["+rightText)+"]&nbsp;<img src='"+systemImagePath+"/upload/remove.png'  style='cursor:pointer;vertical-align: middle;' onclick='remove(this)'/></div>");
	$("#mappingContainer").append(item);
}


//移除映射关系
function remove(obj){
	$(obj).parent().remove();
}
	</script>
</head>
<body style="padding-left: 10px;padding-right: 10px" onload="doInit()">
    <div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1"  class = 'title_img' src="<%=contextPath %>/system/subsys/bisengine/img/icon_bjywmx.png">
			<span class="title">新建/编辑业务模型</span>
		</div>
		<div class = "right fr clearfix">
			<input type="button" class="btn-win-white fl" onclick="commit()" value="提交"/>
			<input type="button" class="btn-win-white fl" onclick="back()" value="返回"/>
		</div>
	</div>
	<form id="form1">
			<table class="TableBlock_page" style="width: 100%">
				<tbody>
					<tr>
						<TD class=TableHeader colSpan=2 noWrap>
						<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
						<B style="color: #0050aa">基础设置</B></TD>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">业务编号：</td>
						<td class="TableData"> 
						<input type="text"
							class="BigInput" required="true" name="bisKey"
							id="bisKey" <%="update".equals(type)?"readonly":""%>/></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">业务名称：</td>
						<td class="TableData"><input type="text"
							class="BigInput" required="true"
							name="businessName" id="businessName" /></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">业务标题：</td>
						<td class="TableData"><input type="text"
							class="BigInput" required="true"
							name="businessTitle" id="businessTitle" /></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">业务类别：</td>
						<td class="TableData"><select name="businessCatId"
							id="businessCatId" class="BigSelect"></select></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">业务表单：</td>
						<td class="TableData"><select name="bisFormId" id="bisFormId"
							class="BigSelect">
						</select></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">业务操作：</td>
						<td class="TableData" align="center"><input type="checkbox"
							name="operation" id="add" value="add"/>&nbsp;添加&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" name="operation"
							id="update" value="update"/>&nbsp;修改&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" name="operation"
							id="delete" value="delete"/>&nbsp;删除&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; <br /> <input type="checkbox"
							name="operation" id="view" value="view"/>&nbsp;查看&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" name="operation"
							id="export" value="export"/>&nbsp;导出&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" name="operation"
							id="query"  value="query"/>&nbsp;查询&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>

					<tr>
						<td class="TableData" style="text-indent: 10px">列表表头字段：</td>
						<td class="TableData" id="hField" align="center"></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">查询条件字段：</td>
						<td class="TableData" id="qField" align="center"></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">列表排序字段：</td>
						<td class="TableData"><select name="order"
							id="order" class="BigSelect"></select> <select
							name="orderType" id="orderType" class="BigSelect">
								<option value="asc">正序</option>
								<option value="desc">倒序</option>
						</select></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">列表分组字段：</td>
						<td class="TableData"><select name="group"
							id="group" class="BigSelect"></select></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">列表显示条件：</td>
						<td class="TableData"><textarea rows="5" cols="52"
								id="displayCondition" name="displayCondition" class="BigTextarea"></textarea>
							<br>$[USER_NAME] 当前用户名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[USER_ID] 当前用户ID</br>
							    $[DEPT_NAME] 当前部门名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[DEPT_ID] 当前部门ID</br>
							    $[ROLE_NAME] 当前角色名 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$[ROLE_ID] 当前角色ID</br>
						</td>
					</tr>
					<tr>
						<TD class=TableHeader colSpan=2 noWrap>
						<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
						<B style="color: #0050aa">流程设置</B></TD>
					</tr>
                     <tr>
						<td class="TableData" style="text-indent: 10px">绑定流程：</td>
						<td class="TableData">
						   <input type="text" name="flowName" id="flowName"  readonly="readonly"/>
						   <a href="#" onclick="selectFlowType();" >选择</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="clearFlow();">清空</a>
						   <input type="hidden" name="flowId" id="flowId"/>
					</tr>
					
					<tr>
						<td class="TableData" style="text-indent: 10px">触发步骤：</td>
						<td class="TableData">
						   <select name="flowStep"
							id="flowStep" class="BigSelect"></select></td>
					</tr>
					<tr>
						<td class="TableData" style="text-indent: 10px">字段映射：</td>
						<td class="TableData">
						    流程字段：<select id="flowField" name="flowField">
						    
						        </select>
						  &nbsp;&nbsp; 业务表字段：<select name="tableField" id="tableField">
						    
						        </select>
						  &nbsp;&nbsp;&nbsp;&nbsp;<input type="button"  value="映射" class="btn-win-white"  onclick="doMapping();" />
						  <div id="mappingContainer">
						       
						  </div>
						</td>
					</tr>
					
                      <input type="hidden" name="businessOperation" id="businessOperation"/>
                      <input type="hidden" name="headerFields" id="headerFields"/>
                      <input type="hidden" name="queryFields" id="queryFields"/>
                      <input type="hidden" name="orderField" id="orderField"/>
                      <input type="hidden" name="groupField" id="groupField"/>
                      <input type="hidden" name="mapping" id="mapping"/>
				</tbody>
				<tbody id="container">
				</tbody>		
			</table>
		</form>
</body>
<script>
$("#form1").validate();
</script>
</html>
