<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>合同导入</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.form.min.js"></script>
<script type="text/javascript">

function commit(){
	var para =  tools.formToJson($("#form1")) ;
	var  url = "<%=contextPath%>/contract/importContract.action";
	var flag=false;
	if(checkFileExt()){
		
		 $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
	        	  if(res.rtState){
	        		  $("#importExtport").hide();

	        		    //window.location.reload();
	        	        parent.$.MsgBox.Alert_auto("导入成功！");
	        			messageMsg("总共导入 " +  res.rtMsg + " 条数据", "importDiv" ,'' ,280);
	        	  }else{
	        		  $.MsgBox.Alert_auto(res.rtMsg);
	        	  }
	            },
	          error: function(arg1, arg2, ex) {
	              
	        	  $.MsgBox.Alert_auto("导入错误");
	          },
	          dataType: 'json'}); 
	          
	}
}

/**
 * 模板下载
 */
function downLoadTemplate(){
	var  url = "<%=contextPath%>/contract/downLoadTemplate.action";
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
		$.MsgBox.Alert_auto("导入文件为空，请选择文件！");
	return false;
		}
	if (rs >= 0) {
		return true;
	} else {
		$.MsgBox.Alert_auto("您选择的上传文件格式不对，文件必须是excel文件");
		return false;
	}
}

</script>

</head>
<body onload=" " style="padding-left: 10px;padding-right: 10px;overflow: hidden;">
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src=<%=contextPath %>"/system/subsys/contract/icon_import.png">&nbsp;&nbsp;
		<span class="title">合同导入</span>
	</div>
	<span class="basic_border" style="padding-top: 10px;"></span>
</div>

 <div id="importFile" style="margin: 10px;font-size: 12px;">
	<form id="form1" name="form1" method="post" enctype="multipart/form-data">

		<table align="center" class="TableBlock_page" width="80%">
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG" width="80px">下载导入模板：</td>
				<td class="TableData" width="200px">
				<a href='javascript:void(0);' onclick='downLoadTemplate()'
				style='text-decoration: underline;font-size: 14px;'>合同模板下载</a>
				</td>
			</tr>
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG">选择导入文件：</td>
				<td class="TableData"><input id="excelFile" name="excelFile" type="file"
				style="width: 260px; margin-top: 5px;" size="30" class="BigInput"
				title="选excel文件"> <input type="button" value="导入" class="btn-win-white" onclick="javascript:commit();"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td align="right" style="font-weight: bold;" class="TableData TableBG">说明：</td>
				<td class="TableData">
                   <p>1、先下载合同模版，在对应列填上相应数据。注意，导入文件中的合同名称不能为空；</p>
                   <p>2、合同分类和合同类型以及所在部门要填入已存在的合同分类、合同类型、部门，如果填写的数据不存在，则不导入；</p>
                   <p>3、合同开始时间和合同结束时间以及合同签订时间格式必须满足：yyyy-MM-dd，否则不导入。</p>
                   <p>4、合同分类为空时，导入的合同项在合同管理列表中显示不出来。</p>
                   <p>5、合同金额应该输入数字，否则导入失败。</p>
                </td>
			</tr>
			
		</table>
	</form>
	</div>
<div id="importExtport">
	
</div>

	
<div id="importDiv">

</div>
	

</body>
</html>