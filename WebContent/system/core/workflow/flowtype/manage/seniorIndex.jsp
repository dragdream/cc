<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
	<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" /> 
	<link  href="<%=contextPath%>/common/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" /> 
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.min_cxt.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/jquery/portlet/jquery.min_cxt.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript">
		$(function() {
			
		});
		
		/**
		 * 打开桌面设置窗口
		 * @param id
		 */
		function queryClumn(flowId) {
			$('<div/>', {
				'class': 'portlet_class',
				title: '设置查询字段： 用于设置工作流高级查询界面，显示的表单查询字段 ',
				html: '<span class="ui-loading">正在读取表单……</span>'
			}).dialog({
				modal: true,
				width: 400,
				height: 385,
				open: function() {
					loadQueryClumn.call(this,flowId);
				},
				close: function() {
					$('.portlet_class').remove();
				},
				buttons: [{
					text: '保存',
					click: function(){saveQueryClumn(flowId);}
				}]
			});
		}
		
		function loadQueryClumn(flowId) {
			// 清空对话框内容
			$('.portlet_class').html('<span class="ui-loading">正在读取表单……</span>');
			$('.portlet_class').html("<form id = 'form1' name='form1' class='dynamic-form' method='post'><table style=\"border:'0'; width:'350px';\"  class='TableBlock' id='table1'></table></form>");
			var form = $("#form1");
			$("#table1").html(
				"<tr class='TableData'>"+
            		"<td align='center' width='150px'><b>显示以下字段作为表格列</b>"+
            		"</td>"+
            		"<td align='center' width='20px'>&nbsp;</td>"+
            		"<td align='center' valign='top' width='150px'><b>不显示以下字段</b></td>"+
          		"</tr>"+
          		"<tr class='TableData'>"+
            		"<td valign='top' align='center' bgcolor='#CCCCCC'>"+
            			"<input type='hidden' value='' id='dispFld' name='dispFld'>"+
            			"<input type='hidden' value='${flowId}' id='flowId' name='flowId'>"+
            			"<input type='hidden' value='' id='sumFld' name='sumFld'>"+
            			"<select  name='sumFldList' id = 'sumFldList' style='width:150px; height:150px' ondblclick='func_delete();' multiple class='form-control' >"+
			            "<c:forEach items='${formItem}' var='formItemSort' varStatus='formItemStatus'>"+
			            	"<option value='${formItem[formItemStatus.index].name}' >${formItem[formItemStatus.index].title}</option>"+
						"</c:forEach>"+
            			"</select>"+
            			"<input type='button' value='全选' onClick='select_all1(1);' class='btn btn-success btn-xs'>&nbsp;"+
            			"<input type='button' value='取消选择' onClick='select_all1(0);' class='btn btn-warning btn-xs'>"+
            		"</td>"+
            		"<td align='center' bgcolor='#999999'>"+
              			"<input type='button' class='btn btn-info btn-xs' value=' ← ' onClick='func_insert();'>"+
              			"<br><br>"+
              			"<input type='button' class='btn btn-info btn-xs' value=' → ' onClick='func_delete();'>"+
            		"</td>"+
            		"<td align='center' valign='top' bgcolor='#CCCCCC'>"+
            			"<select  name='standbyFldList' id = 'standbyFldList' style='width:150px; height:150px' ondblclick='func_insert();' multiple class='form-control' >"+
            			"</select>"+
            			"<input type='button' value='全选' onClick='select_all2();' class='btn btn-success btn-xs'>"+
            		"</td>"+
          		"</tr>"
			);
			var queryClumn = "${queryClumn}";
			for (var i = document.form1.sumFldList.options.length-1; i>=0; i--){
				var option_text=document.form1.sumFldList.options[i].text;
				var option_value=document.form1.sumFldList.options[i].value;
				if(queryClumn.indexOf(option_value)==-1){
				    var my_option = document.createElement("OPTION");
				    my_option.text=option_text;
				    my_option.value=option_value;
				    var pos=func_find(document.form1.standbyFldList,option_text);
				    document.form1.standbyFldList.add(my_option,pos);
				    document.form1.sumFldList.remove(i);
				}
			}
		}
		
		//左侧->右侧
		function func_delete(){
			 for (var i = document.form1.sumFldList.options.length-1; i>=0; i--){
				   option_text=document.form1.sumFldList.options[i].text;
				   option_value=document.form1.sumFldList.options[i].value;
				   if(document.form1.sumFldList.options[i].selected){
				     var my_option = document.createElement("OPTION");
				     my_option.text=option_text;
				     my_option.value=option_value;
		
				     var pos=func_find(document.form1.standbyFldList,option_text);
				     document.form1.standbyFldList.add(my_option,pos);
				     document.form1.sumFldList.remove(i);
				  }
			 }
		}
		
		function func_find(select_obj,option_text){
		   pos=option_text.indexOf("] ")+1;
		   option_text=option_text.substr(0,pos);

		   for (var j=0; j<select_obj.options.length; j++){
		       str=select_obj.options[j].text;
		       if(str.indexOf(option_text)>=0)
		       return j;
		   }

		   return j;
		}
		//右侧->左侧
		function func_insert(){
		    for (var i=document.form1.standbyFldList.options.length-1; i>=0; i--){
		        if(document.form1.standbyFldList.options[i].selected){
		     	    option_text=document.form1.standbyFldList.options[i].text;
		            option_value=document.form1.standbyFldList.options[i].value;
		     	    option_style_color=document.form1.standbyFldList.options[i].style.color;
			        var my_option = document.createElement("OPTION");
			        my_option.text=option_text;
			        my_option.value=option_value;
			        my_option.style.color=option_style_color;
			        var pos=func_find(document.form1.sumFldList,option_text);
			        document.form1.sumFldList.add(my_option,pos);
			        document.form1.standbyFldList.remove(i);
		  		 }
		    }
		}
		
		function select_all1(flag){
		    for (var i=0; i<document.form1.sumFldList.options.length; i++){
		        document.form1.sumFldList.options[i].selected=flag;
		    }
		}
		function select_all2(){
		    for (var i=0; i<document.form1.standbyFldList.options.length; i++){
		        document.form1.standbyFldList.options[i].selected=1;
		    }
		}
		
		function saveQueryClumn(flowId) {
			
			document.form1.sumFld.value="";
			document.form1.dispFld.value="";
			for (var i = 0; i < document.form1.sumFldList.options.length; i++){
			    option_value = document.form1.sumFldList.options[i].value;
			    document.form1.dispFld.value+=option_value+",";
			    if(document.form1.sumFldList.options[i].selected){
			        document.form1.sumFld.value+=option_value+",";
			    }
			}
			var url = "<%=contextPath%>/seniorQuery/saveQueryClumn.action";
			var para =  tools.formToJson($("#form1")) ;
			var jsonRs = tools.requestJsonRs(url,para);
			if(jsonRs.rtState){
				alert(jsonRs.rtMsg);
				window.location.reload();
			}

		}
		function newTpl(flowId){
			//跳转页面，springMVC 返回对象
			window.location = contextPath + "/seniorQuery/toOrderTpl.action?flowId="+flowId;
		}
		function tplList(flowId){
		  	var URL = contextPath + "/seniorQuery/sysQueryTplByFlowId.action?flowId="+flowId;
		  	window.location = URL;
		}
	</script>
	
</head>
<body style="margin-top: 1em;overflow-x:hidden;">
<table style="border:'0' ;width:'100%' ;cellspacing:'0' ;cellpadding:'3'" class="small">
  	<tr>
    	<td class="Big"><button class="btn btn-default btn-sm"  onclick="queryClumn(${flowId});">查询字段</button>
    	</td>
  	</tr>
  	<tr>
    	<td class="Big"><button class="btn btn-default btn-sm"  onclick="newTpl(${flowId});">新建模板</button>
    	</td>
  	</tr>
	<tr>
    	<td class="Big"><button class="btn btn-default btn-sm"  onclick="tplList(${flowId});">模板列表</button>
    	</td>
  	</tr>
</table>
</body>
</html>
