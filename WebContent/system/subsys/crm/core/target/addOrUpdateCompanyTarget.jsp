<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
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
var date=new Date();
var nowYear=date.getFullYear();
var beginYear=nowYear-3;
function doInit(){
	$("#layout").layout({auto:true});
	//动态加option
	 var selector=$('#year');
	if(sid==0){
	   for(var i=0;i<9;i++){
		  if(i==3){
			  selector.append('<option class="op" selected = "selected" value="'+(beginYear+i)+'">'+(beginYear+i)+'</option>');   
		  }else{
			  selector.append('<option class="op"  value="'+(beginYear+i)+'">'+(beginYear+i)+'</option>');   
		  }	 
	    }
	}else{
		for(var i=0;i<9;i++){	
		    selector.append('<option class="op"  value="'+(beginYear+i)+'">'+(beginYear+i)+'</option>');   	 	 
		 }
		getInfoById(sid);
	}
	
}


//查看详情
function getInfoById(id){
		
		var url ="<%=contextPath%>/crmCompanyTargetController/getCompanyTargetById.action";
		var para = {sid :id};
		
		var jsonObj = tools.requestJsonRs(url, para);
		
		//alert(jsonObj);
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
		var url = "<%=contextPath %>/crmCompanyTargetController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			goback();
			$.jBox.tip("保存成功!",'success',{timeout:300000});
		}else{
			$.jBox.tip("该年份的公司目标已经存在，请重新选择！",'error',{timeout:1500});
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
	window.location = contextPath+"/system/subsys/crm/core/target/companyTargetList.jsp";
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
						公司目标基本信息
						<input type="hidden" name="sid" value="<%=sid%>">
					</td>
			</tr>
			<tr>
				<td class="TableData" align="right" width="200px">
					财年<span style="color:red">*</span>：
				</td>
				<td class="TableData" width="300px">
				    <select class="BigInput easyui-validatebox" required name="year" style="width:100px;" id="year">
				      
				    
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