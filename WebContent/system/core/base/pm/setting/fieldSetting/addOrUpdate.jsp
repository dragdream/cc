<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);

%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增/编辑自定义字段</title>
<script>
var sid=<%=sid%>;
//初始化
function doInit(){
	//初始化系统编码
	initHRcode();
	if(sid>0){
		getInfoBySid(sid);
	}else{
		
	}
}

//编辑的时候获取初始化数据
function getInfoBySid(sid){
	var url=contextPath+"/pmCustomerFieldController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		 if(data.filedType=="下拉列表"){
				$("#optionNameTr").show();
				$("#optionValueTr").show();  
			$("#codeTypeTr").show();
			 if(data.codeType=="HR系统编码"){
				$("#sysCodeTr").show();
				$("#optionNameTr").hide();
				$("#optionValueTr").hide();
			}else if(data.codeType=="自定义选项"){
				$("#sysCodeTr").hide();
			} 
		 }else{ 
			 $("#codeTypeTr").hide();
			$("#sysCodeTr").hide(); 
		 	$("#optionNameTr").hide();
			$("#optionValueTr").hide();
			
		} 
		
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}

}
//初始化系统编码
function initHRcode(){
	var url = contextPath+"/hrCode/getSysPara.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#sysCode").append("<option value="+data[i].codeNo+">"+data[i].codeName+"</option>");
		}
		
	}
}


//改变字段类型调用的 方法
function changefiledType(){
	var filedType=$("#filedType").val();
	if(filedType=="下拉列表"){
		$("#codeTypeTr").show();
		$("#sysCodeTr").show();
		$("input[type='radio'][name='codeType']").get(0).checked = true;//选中第一个
	}else{
		$("#codeTypeTr").hide();
		$("#sysCodeTr").hide();
		$("#optionNameTr").hide();
		$("#optionValueTr").hide();
		/* $("#optionName").val("");
		$("#optionValue").val(""); */
		$("input[type='radio'][name='codeType']").get(0).checked = true;//选中第一个
	}
}

//根据代码类型的值判断相应输入框的显示和隐藏
function changeCodeType(num){
	if(num==1){//系统编码
		$("#sysCodeTr").show();
		$("#optionNameTr").hide();
		$("#optionValueTr").hide();
	}else if(num==2){//自定义选项
		$("#sysCodeTr").hide();
		$("#optionNameTr").show();
		$("#optionValueTr").show();
	}
	
}


//提交
function commit(){
	var url="";
	if(check()){
		var param=tools.formToJson($("#form1")) ;
		if(sid>0){
				url = contextPath+"/pmCustomerFieldController/updateField.action?sid="+sid;
		}else{
			 url=contextPath+"/pmCustomerFieldController/addOrUpdate.action";
		}
		var json=tools.requestJsonRs(url,param);
		if(json.rtState){
			parent.$.MsgBox.Alert_auto("保存成功！",function(){
				window.location.href=contextPath+"/system/core/base/pm/setting/fieldSetting/index.jsp";
			});
			 opener.datagrid.datagrid("unselectAll");
			 opener.datagrid.datagrid('reload');
			 CloseWindow();
			
		}else{
			$.MsgBox.Alert_auto("保存失败！");
		}	
	}
}

//验证
function check(){
	return  $("#form1").valid();
}

//返回
function back(){
	window.location.href=contextPath+"/system/core/base/pm/setting/fieldSetting/index.jsp";
}
</script>


<body onload="doInit()"  style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_bjzd.png">
		<span class="title">新增/编辑字段</span>
	</div>
   <div class="fr right">
      <input type="button" value="确定" class="btn-win-white" onclick="commit();"/>
      <input type="button" value="返回" class="btn-win-white" onclick="back();"/>
   </div>
</div>
<form  id="form1" name="form1" method="post">
<div>
 <input type="hidden" value="<%=sid%>" name="sid"/>
 <table class="TableBlock_page">
    <tr>
       <td style="text-indent: 15px">字段名称：</td>
       <td><input type="text" name=extendFiledName id="extendFiledName" style="height: 23px;width: 350px" required/></td>
    </tr>
    <tr>
       <td style="text-indent: 15px">排序号：</td>
       <td><input type="text" name="orderNum" id="orderNum" style="height: 23px;width: 350px" no_negative_number="true"/></td>
    </tr>
    <tr>
       <td style="text-indent: 15px">字段类型：</td>
       <td>
           <select id="filedType" name="filedType" onchange="changefiledType()" style="width:200px;height: 23px"> 
              <option value="单行输入框">单行输入框</option>
              <option value="多行输入框">多行输入框</option>
              <option value="下拉列表">下拉列表</option>
           </select>
       </td>
    </tr>
     <tr style="display: none" id="codeTypeTr">
       <td style="text-indent: 15px;">代码类型：</td>
       <td>
           <input type="radio" name="codeType" id="xtbm"  value="HR系统编码" onclick="changeCodeType(1)" checked="checked"/>&nbsp;系统编码  &nbsp;&nbsp;
           <input type="radio" name="codeType"  value="自定义选项" onclick="changeCodeType(2)"/>&nbsp;自定义选项 
       </td>
    </tr>
    <tr style="display: none" id="sysCodeTr">
       <td style="text-indent: 15px">HR系统代码：</td>
       <td>
          <select name="sysCode" id="sysCode" style="width:200px;height: 23px">
             
          </select>
       </td>
    </tr>
    <tr style="display: none" id="optionNameTr">
       <td style="text-indent: 15px">选项名称：</td>
       <td>
           <input type="text" name="optionName" style="height: 23px;width: 350px" required />
           &nbsp;&nbsp;<span>显示的选项，用逗号隔开如:（选项1,选项2,选项3）</span>
       </td>
    </tr>
    <tr style="display: none" id="optionValueTr">
       <td style="text-indent: 15px">选项的值：</td>
       <td>
           <input type="text" name="optionValue" style="height: 23px;width: 350px" required/>
           &nbsp;&nbsp;<span>选项对应存储的值，非重复的数字，用逗号隔开如:（1,2,3）</span>
       </td>
    </tr>
    <tr>
       <td style="text-indent: 15px">是否作为查询字段：</td>
       <td>
           <input type="radio" name="isQuery" value="1"/>&nbsp;是  &nbsp;&nbsp;
           <input type="radio" name="isQuery" value="0" checked="checked"/>&nbsp;否 
       </td>
    </tr>
    <tr>
       <td style="text-indent: 15px">是否显示在列表：</td>
       <td>
           <input type="radio" name="isShow" value="1"/>&nbsp;是  &nbsp;&nbsp;
           <input type="radio" name="isShow" value="0" checked="checked"/>&nbsp;否 
       </td>
    </tr>
 </table>
</div>
</form>
</body>
</html>