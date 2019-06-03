<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp"%>
	<%@ include file="/header/upload.jsp"%>
	<%@ include file="/header/easyui.jsp"%>
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
function commit(){
	
	var para =  tools.formToJson($("#form1")) ;
	var  url = "<%=contextPath%>/humanDocController/importHumanDocInfo.action";
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
			 			var data = res.rtData;
			 			var dataJson = data;
			 			importInfo(dataJson ,res.rtMsg ); 
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
}

/**
 * 模板下载
 */
function downLoadTemplate(){
	var  url = "<%=contextPath%>/humanDocController/downPdLoadTemplate.action";
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
		var tableStr = "<table class='TableList' width='95%' align='center' >"
	      + "<tbody id='tbody'>"
	      + "<tr  class='TableHeader'>"
	      + "<td >部门名称 </td>"
	      + "<td >用户名</td>"
	      + "<td >姓名</td>"
	      + "<td >角色</td>"
	      + "<td > 导入信息</td>"
	      +"</tr>";
		for(var i = 0;i<data.length ; i++){
			var obj = data[i];
			var color = obj.color;
			tableStr = tableStr + "<tr  align='center'>"
			+"<td width='150px;'><font color='" + color + "'>" + obj.deptName + "</font></td>"
			+"<td width='100px;'><font color='" + color + "'>" + obj.userName+ "</font></td>"
			+"<td width='120px;'><font color='" + color + "'>" + obj.personName+ "</font></td>"
			+"<td width='100px;'><font color='" + color + "'>" + obj.roleName+ "</font></td>"
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
<body style="font-size: 12px">
    <div id="importFile">
	<form id="form1" name="form1" method="post"
		enctype="multipart/form-data">

		<table align="center" class="TableBlock" width="550px">
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG" width="100px">下载导入模板：</td>
				<td class="TableData" width="250px">
				<a href='javascript:void(0);' onclick='downLoadTemplate()'
				style='text-decoration: underline;'>人事档案模板下载</a>
				</td>
			</tr>
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG">选择导入文件：</td>
				<td class="TableData"><input id="excelFile" name="excelFile" type="file"
				style="width: 260px; margin-top: 5px;" size="30" class="BigInput"
				title="选excel文件">
				</td>
			</tr>
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG">说明：</td>
				<td class="TableData">
                   <p>1、使用人事档案模板的前三列为姓名、用户名、部门，部门名必须在OA系统管理中存在，余下的列名称必须与人员档案基本信息相对应；</p>
                   <p>2、人事档案模板中，姓名不能为空，为空则不能导入；</p>
                   <p>3、日期的格式应如：2009-10-15。</p>
                </td>
			</tr>
			<tr>
			  <td colspan="2" align="center">
			  	 <button type="button" onclick="commit()" class="btn btn-default"><b>导入</b></button>
<!-- 			     <input type="button" value="" style="width: 80px;height: 40px" onclick="commit()"/> -->
			  </td>
			</tr>
		</table>
	</form>
	</div>
	
	
	<!--   信息导入返回信息 -->
  <div id="importDiv" style="display:none;"  align="center">
  	   <div id="importInfo"></div>   
  	   <div id="importSuccessInfo"></div>

	   <input type='button' class="btn btn-primary" value="返回" onclick='window.location.reload();'/>
  </div>
</body>
</html>