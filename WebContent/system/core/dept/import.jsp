<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>导入部门</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">

function check() {
	return checkSuppotCsvFile('importDeptFile');
}

/**
 * 保存
 */
function submitForm(){
	if(check()){
		 var para =  tools.formToJson($("#form1")) ;
		 $("#form1").ajaxSubmit({
			  url :"<%=contextPath %>/orgImportExport/importDept.action",
	          iframe: true,
	          data: para,
	          success: function(res) {
	        	  if(res.rtState){
	        		  var data = res.rtData;
	        		  var dataJson = eval('(' + data + ')');
	        		  importInfo( dataJson ,res.rtMsg );
	        	  }else{
	        		  //alert(res.rtMsg);
	        		  $.MsgBox.Alert_auto(res.rtMsg);
	        	  }
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	                //alert("保存错误");
	                $.MsgBox.Alert_auto("保存错误");
	          },
	          dataType: 'json'});
	}	
}

/**
 * 导入返回信息
 */
function importInfo( data , importDeptCount){
	if(data.length >0){
		var tableStr = "<table class='TableList' width='80%' align='center' >"
	      + " <tbody id='tbody'>"
	      + "<tr  class='TableHeader'>"
	      + "<td  width='100px;'>部门名称 </td>"
	      + "<td width='20px;' nowrap>部门排序号</td>"
	      + "<td width='150px;'> 上级部门</td>"
	      + "<td > 导入信息</td>"
	      +"</tr>";
		for(var i = 0;i<data.length ; i++){
			var obj = data[i];
			var color = obj.color;
			tableStr = tableStr + "<tr  align='center'>"
			+"<td ><font color='" + color + "'>" + obj.deptName + "</font></td>"
			+"<td ><font color='" + color + "'>" + obj.deptNo+ "</font></td>"
			+"<td ><font color='" + color + "'>" + obj.deptParent + "</font></td>"
			+"<td  ><font color='" + color + "'>" + obj.info + "</font></td>"
			+ "</tr>";		
		}
	    tableStr = tableStr + " </tbody></table>";
	    $("#importFile").hide();
	    $("#importDiv").show();
	    
		$("#importInfo").append(tableStr + "<br>");
		messageMsg("总共导入 " +  importDeptCount + " 条数据", "importSuccessInfo" ,'' ,280);
		
	}
}
</script>

</head>
<body onload="">

<div id="importFile">

<form enctype="multipart/form-data" action=""  method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="100%" align="center">
    <tr>
		<td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;text-indent: 3px'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; 
            <span>部门导入</span>
        </td>
			
	</tr>
    <tr id="avatarUpload" >
      <td nowrap class="TableData" valign="top" width='200px' style="text-indent: 15px"> 请指定用于导入的CSV文件： </td>
      <td class="TableData" id="">
          <input type="file" name="importDeptFile" id="importDeptFile" size="40" class="BigInput" title="选择附件文件" value="">
  
      </td>
    </tr>
   
    <tr align="center">
      <td colspan="2" nowrap>
       <div align="center">
          <input type="button" value="导入" class="btn-win-white" onclick="submitForm()">&nbsp;&nbsp;
        </div>
      </td>
    </tr>
  </table>
</form>
  </div>
  <br>

<!--   信息导入返回信息 -->
  <div id="importDiv" style="display:none;"  align="center">
  	   <div id="importInfo"></div>   
  	   <div id="importSuccessInfo"></div>

	   <input type='button' class="btn btn-primary" value="返回" onclick='window.location.reload();'/>
</div>
</body>
</html>