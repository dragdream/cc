<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp"%>
	<%@ include file="/header/upload.jsp"%>
	<%@ include file="/header/easyui2.0.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript"
		src="<%=contextPath%>/common/js/layout/layout.js"></script>
	<link rel="stylesheet"
		href="<%=contextPath%>/common/jquery/ztree/css/demo.css"
		type="text/css">
		<link rel="stylesheet"
			href="<%=contextPath%>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css">
			<script type="text/javascript"
				src="<%=contextPath%>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
			<script type="text/javascript"
				src="<%=contextPath%>/common/js/easyuiTools.js"></script>
			<script type="text/javascript"
				src="<%=contextPath%>/common/js/ZTreeSync.js"></script>
			<script type="text/javascript"
				src="<%=contextPath%>/common/js/tools.js"></script>
			<script type="text/javascript"
				src="<%=contextPath%>/common/js/sys.js"></script>
			<script type="text/javascript"
				src="<%=contextPath%>/system/core/person/js/person.js"></script>
			<script type="text/javascript"
				src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
			<style>
td {
	line-height: 36px;
	min-height: 36px;
}

table{
    border:3px;
}
</style>

<script>
function doinit(){
	findDutyTypeAll();
}
//值班类型
function findDutyTypeAll(){
	var url = "<%=contextPath%>/teePbDutyTypeController/findDutyTypeAll.action";
	var json=tools.requestJsonRs(url,null);
	var data=json.rtData;
	if(data!=null && data.length>0){
		for(var i=0;i<data.length;i++){
			$("#dutyTypeName").append("<option value='"+data[i].sid+"'>"+data[i].typeName+"</option>");
		}
	}
}
function commit(){
	var dutyTypeName=$("#dutyTypeName").val();
	var para =  tools.formToJson($("#form1")) ;
	var  url = "<%=contextPath%>/teePbOnDutyController/importHumanDocInfo.action?typeId="+dutyTypeName;
	var flag=false;
	if(checkFileExt()){
		 $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 	 	if(res.rtState){
			 			//$.jBox.tip("导入成功", 'info');
			 			flag= true;
			 			alert("导入成功"+res.rtMsg+"条数据");
			 			window.location.href=contextPath + "/system/core/base/onduty/manager.jsp";
			 		}else{
			 			alert(res.rtMsg);
			 			flag= false;
			 		} 
			 		//callback(res);
	            },
	           error: function(arg1, arg2, ex) {
	                alert("导入错误");
	                flag= false;
	          }, 
	          dataType: 'json'}); 
	}
	//return flag;
}

/**
 * 模板下载
 */
function downLoadTemplate(){
	var dutyTypeName=$("#dutyTypeName").val();
	var  url = "<%=contextPath%>/teePbOnDutyController/downPdLoadTemplate.action?typeId="+dutyTypeName;
					document.form1.action = url;
					document.form1.submit();
					return true;
}

/**
 * 对导入的文件进行检查
 */
function checkFileExt() {
	var filepath = document.getElementById("excelFile").value;
	//为了避免转义反斜杠出问题，这里将对其进行转换
	var re = /(\\+)/g;
	var filename = filepath.replace(re, "#");
	//对路径字符串进行剪切截取
	var one = filename.split("#");
	//获取数组中最后一个，即文件名
	var two = one[one.length - 1];
	//再对文件名进行截取，以取得后缀名
	var three = two.split(".");
	//获取截取的最后一个字符串，即为后缀名
	var last = three[three.length - 1];
	//添加需要判断的后缀名类型
	var tp = "xls,xlsx";
	//返回符合条件的后缀名在字符串中的位置
	var rs = tp.indexOf(last);
	//如果返回的结果大于或等于0，说明包含允许上传的文件类型
	if ($("#excelFile").val() == "") {
		alert("导入文件为空，请选择文件！");
	return false;
		}
	if (rs >= 0) {
		return true;
	} else {
		alert("您选择的上传文件格式不对，文件必须是excel文件");
		return false;
	}
}



/**
 * 导入返回信息
 */
function importInfo( data, importCount){
	//alert("======");
	if(data.length >0){
		var tableStr = "<table class='TableBlock_page' width='95%' align='center' >"
	      + "<tbody id='tbody'>"
	      + "<tr  class='TableHeader'>"
	      + "<td >部门名称 </td>"
	      + "<td >带班领导</td>"
	      + "<td >值班类型</td>"
	      + "<td > 导入信息</td>"
	      +"</tr>";
		for(var i = 0;i<data.length ; i++){
			var obj = data[i];
			var color = obj.color;
			tableStr = tableStr + "<tr  align='center'>"
			+"<td width='150px;'><font color='" + color + "'>" + obj.deptName + "</font></td>"
			+"<td width='100px;'><font color='" + color + "'>" + obj.userName+ "</font></td>"
			+"<td width='120px;'><font color='" + color + "'>" + obj.typeName+ "</font></td>"
			+"<td width='' ><font color='" + color + "'>" + obj.info + "</font></td>"
			+ "</tr>";		
		}
	    tableStr = tableStr + " </tbody></table>";
	    $("#importFile").hide();
	    $("#importDiv").show();
	    
		$("#importInfo").append(tableStr + "<br>");
		messageMsg("总共导入 " +  importCount + " 条数据", "importSuccessInfo" ,'' ,280);
		
	}
}
</script>
</head>
<body onload="doinit();" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src=<%=contextPath %>"/system/subsys/contract/icon_import.png">&nbsp;&nbsp;
		<span class="title">人事档案导入</span>
	</div>
	<span class="basic_border" style="padding-top: 10px;"></span>
</div>
    <div id="importFile" style="margin: 10px;font-size: 12px;">
	<form id="form1" name="form1" method="post" enctype="multipart/form-data">

		<table align="center"  class="TableBlock_page" width="80%">
		   <tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG" width="100px">值班类型：</td>
				<td class="TableData" width="250px">
				 <select style="height: 25px;width: 100px;" id="dutyTypeName" class="BigSelect">
			     
			     </select>
				</td>
			</tr>
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG" width="100px">下载导入模板：</td>
				<td class="TableData" width="250px">
				<a href='javascript:void(0);' onclick='downLoadTemplate()'
				style='text-decoration: underline;font-size: 14px;'>值班排班模板下载</a>
				</td>
			</tr>
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG">选择导入文件：</td>
				<td class="TableData"><input id="excelFile" name="excelFile" type="file"
				style="width: 260px; margin-top: 5px;" size="30" class="BigInput"
				title="选excel文件">
				 <button type="button" onclick="commit()" class="btn-win-white">导入</button>
				</td>
			</tr>
		
		
		</table>
	</form>
	</div>
	
	
	<!--   信息导入返回信息 -->
  <div id="importDiv" style="display:none;"  align="center">
  	   <div id="importInfo"></div>   
  	   <div id="importSuccessInfo"></div>

	   <input type='button' class="btn-win-white" value="返回" onclick='window.location.reload();'/>
  </div>
</body>
</html>