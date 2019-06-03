<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
   int year=TeeStringUtil.getInteger(request.getParameter("year"), 0);
   int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
   String deptName=request.getParameter("deptName");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<style>
</style>
<script>
var sid = <%=sid%>;
var year=<%=year%>;
var deptId=<%=deptId%>;
var deptName="<%=deptName%>";
function doInit(){
	$("#layout").layout({auto:true});
	getPersonList();
	if(sid>0){
		getInfoById(sid);
		
	}else{
		$("#year").val(year);
		
	}
	
	
}

//获取部门下的所有员工
function getPersonList(){
	var url ="<%=contextPath%>/personManager/getPersonByDept.action";
	var para = {deptId :deptId};
	
	var jsonObj = tools.requestJsonRs(url, para);
	
	//alert(tools.jsonObj2String(jsonObj));
	if (jsonObj.rtState) {
		 var selector=$('#targetUserId');     
		 for(var o in jsonObj.rtData){  
			 //alert("text:"+jsonObj.rtData[o].name+" value:"+jsonObj.rtData[o].id ); 
			 selector.append('<option class="op" value="'+jsonObj.rtData[o].id +'">'+jsonObj.rtData[o].name+'</option>');   
		}    
	} else {
		alert(jsonObj.rtMsg);
	} 
	
	
	
}

//查看详情
function getInfoById(id){
		
		var url ="<%=contextPath%>/crmPersonTargetController/getPersonTargetById.action";
		var para = {sid :id};
		
		var jsonObj = tools.requestJsonRs(url, para);
		
		if (jsonObj.rtState) {	
			var target = jsonObj.rtData;		
		    bindJsonObj2Cntrl(target);
	 
		} else {
			
			alert(jsonObj.rtMsg);
		}
	}


//保存
function save(){
	if(checkForm()){
		var url = "<%=contextPath %>/crmPersonTargetController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			goback();
			top.$.jBox.tip("保存成功!",'success',{timeout:1500});
		}else{
			top.$.jBox.tip(jsonRs.rtMsg,'error',{timeout:1500});
		}
	}
	
}

//计算总额
 function sum(){
	
		var sum=0;
		for(var i=1;i<=12;i++){
			var m=$("#m"+i).val();
			if(m==""||m=="undefined"||m==null){
	            m=0;
			}else{
				m=parseFloat(m);
			}
			sum=parseFloat(sum)+m;	
		}
	    $("#total").val(sum);	

}
//验证
 function checkForm(){
		var check = $("#form1").form('validate'); 
		if(!check){
			return false; 
		}
		return true;
	}
//返回
function goback(){
	window.location = contextPath+"/system/subsys/crm/core/target/personTargetList.jsp?year="+year+"&deptId="+deptId+"&deptName="+deptName;
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;">
<div id="layout">
	<div layout="north" height="85">
		<div class="moduleHeader">
			<div>
				<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;新增/编辑目标</b>
			</div>
		</div>
		<div style='text-align:center;'>
			<button class="btn btn-default" type="button" onclick="goback()"><i class="glyphicon glyphicon-repeat"></i>&nbsp;返回</button>
			<button class="btn btn-default" type="button" onclick="save()"><i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;保存</button>
		</div>
	</div>
	
	
	<div layout="center" style="padding:10px;overflow:auto">
	<form id="form1">
		<table class="TableBlock" style="font-size:12px;margin:0 auto;">
			<tr class="TableHeader">
					<td colspan="2">
						个体目标基本信息
						<input type="hidden" name="sid" value="<%=sid%>">
					</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					财年<span style="color:red">*</span>：
				</td>
				<td class="TableData" width="300px">
				    <input type="text" class="BigInput easyui-validatebox"   name="year" id="year" readonly="readonly"/>
				</td>
			</tr>
			<tr>
			    <td class="TableData" align="right" width="200px">
					所属人员 ：
				</td>
				<td class="TableData">
				    <select id="targetUserId" name="targetUserId" class="BigInput easyui-validatebox" style="width: 155px;">
				    
				    </select>
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					一月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox"  validtype="pointTwoNumber[]" name="m1" id="m1" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					二月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox"  validtype="pointTwoNumber[]" name="m2" id="m2" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					三月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]"  name="m3" id="m3" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					四月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]"  name="m4" id="m4" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					五月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m5" id="m5" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					六月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m6" id="m6" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					七月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m7" id="m7" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					八月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m8" id="m8" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					九月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m9" id="m9" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					十月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m10" id="m10" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					十一月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]"  name="m11" id="m11" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					十二月：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox" validtype="pointTwoNumber[]" name="m12" id="m12" onchange="sum();"/>		
				</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					总额：
				</td>
				<td class="TableData">
				   <input type="text" class="BigInput easyui-validatebox"  name="total" readonly="readonly" id="total"/>		
				</td>
			</tr>
			
		</table>
		</form>
	</div>
</div>
</body>
</html>