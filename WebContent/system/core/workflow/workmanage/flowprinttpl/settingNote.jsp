<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "0" : request.getParameter("flowTypeId");
	String sid = request.getParameter("sid") == null ? "0" : request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>设置节点属性</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 



<script type="text/javascript">
var flowTypeId = '<%=flowTypeId%>';	
var sid = "<%=sid%>";

var parentWindowObj = window.dialogArguments;
var to_id_field ;
var to_name_field;
var single_select = true;//是否是单用户选择
var ctroltime=null,key="";

function doInit(){
	 changeField('0');
	setFontSize("fieldSize");
}

var fontSizeType = [{value:'42',name:'初号'},{value:'36',name:'小初'}
,{value:'26',name:'一号'},{value:'24',name:'小一'}
,{value:'22',name:'二号'},{value:'18',name:'小二'}
,{value:'16',name:'三号'},{value:'15',name:'小三'}
,{value:'14',name:'四号'},{value:'12',name:'小四'}
,{value:'10',name:'五号'},{value:'9',name:'小五'}
,{value:'5',name:'5'},{value:'7',name:'7'}
,{value:'8',name:'8'},{value:'11',name:'11'}
,{value:'20',name:'20'},{value:'28',name:'28'}
,{value:'48',name:'48'},{value:'72',name:'72'}
];

var fontSizeTypeStr = "<option value = '42'>初号 </option>" + "<option value = '36'>小初 </option>"
					+ "<option value = '26'> 一号</option>" + "<option value = '24'>小一 </option>"
					+ "<option value = '22'>二号 </option>" + "<option value = '18'>小二 </option>"
					+ "<option value = '16'>三号 </option>" + "<option value = '15'>小三</option>"
					+ "<option value = '14' selected>四号 </option>" + "<option value = '12' >小四 </option>"
					+ "<option value = '10'>五号</option>" + "<option value = '9'>小五 </option>"
					//+ "<option value = '5'>5</option>" + "<option value = '7'>7 </option>"
					//+ "<option value = '8'>8</option>" + "<option value = '11'>11 </option>"
					+ "<option value = '20'>20</option>" + "<option value = '28'>28 </option>"
					+ "<option value = '48'>48</option>" 
					;
/**
 * 设置字体大小select
 * @param id
 */
function setFontSize(id){
	$("#" + id).append(fontSizeTypeStr);
}



var menuDataFont = [{ name:'<div  style="padding-top:5px;margin-left:10px">宋体</div>',action:getFieldFont,extData:['宋体']}
,{ name:'<div  style="font-family:黑体;padding-top:5px;margin-left:10px">黑体</div>',action:getFieldFont,extData:['黑体']}
,{ name:'<div  style="font-family:楷体;padding-top:5px;margin-left:10px">楷体</div>',action:getFieldFont,extData:['楷体']}
,{ name:'<div  style="font-family:隶书;padding-top:5px;margin-left:10px">隶书</div>',action:getFieldFont,extData:['隶书']}
,{ name:'<div  style="font-family:幼圆;padding-top:5px;margin-left:10px">幼圆</div>',action:getFieldFont,extData:['幼圆']}
,{ name:'<div  style="font-family:微软雅黑;padding-top:5px;margin-left:10px">微软雅黑</div>',action:getFieldFont,extData:['微软雅黑']}
,{ name:'<div  style="font-family:Arial;padding-top:5px;margin-left:10px">Arial</div>',action:getFieldFont,extData:['Arial']}
,{ name:'<div  style="font-family:Fixedsys;padding-top:5px;margin-left:10px">Fixedsys</div>',action:getFieldFont,extData:['Fixedsys']}
];
/**
 * 显示字体类型
 */
function showFieldFont(id,aid,aName){
	var menuData = new Array();
	//alert('dd')
	menuData.push({ name:'查看',action: getFieldFont,extData:['ss','test']});
	$("#" + id).TeeMenu({menuData:menuDataFont,eventPosition:false});
}
function getFieldFont(value ){
	$("#fieldFont").val(value);
	$("#fieldFontA").html(value);
	//alert(value + ":"+ name)
}




/**
 * 颜色
 * @param event
 * @return
 */
var  menuDataColor = [{ name:LoadForeColorTable('set_font_color') , action:getFieldFont ,extData:['ss','test'] }];


function set_font_color(color){
   $("#fieldFontColor")[0].value = color;
   $('#fieldFontColorA')[0].style.color = color;
}
/**
 * 颜色处理
 */
function LoadForeColorTable(ClickFunc){
	var tColor = "";
	  var tRowNum = 8;
	  var tColorAry = new Array();
	  tColorAry[0]="#000000";tColorAry[1]="#993300";tColorAry[2]="#333300";tColorAry[3]="#003300";
	  tColorAry[4]="#003366";tColorAry[5]="#000080";tColorAry[6]="#333399";tColorAry[7]="#333333";

	  tColorAry[8]="#800000";tColorAry[9]="#FF6600";tColorAry[10]="#808000";tColorAry[11]="#008000";
	  tColorAry[12]="#008080";tColorAry[13]="#0000FF";tColorAry[14]="#666699";tColorAry[15]="#808080";

	  tColorAry[16]="#0066CC";tColorAry[17]="#FF9900";tColorAry[18]="#99CC00";tColorAry[19]="#339966";
	  tColorAry[20]="#33CCCC";tColorAry[21]="#3366FF";tColorAry[22]="#800080";tColorAry[23]="#999999";

	  tColorAry[24]="#FF00FF";tColorAry[25]="#FFCC00";tColorAry[26]="#FFFF00";tColorAry[27]="#00FF00";
	  tColorAry[28]="#00FFFF";tColorAry[29]="#00CCFF";tColorAry[30]="#993366";tColorAry[31]="#CCCCCC";

	  tColorAry[32]="#FF99CC";tColorAry[33]="#FFCC99";tColorAry[34]="#FFFF99";tColorAry[35]="#CCFFCC";
	  tColorAry[36]="#CCFFFF";tColorAry[37]="#99CCFF";tColorAry[38]="#CC99FF";tColorAry[39]="#FFFFFF";

	  var tColorTableHTML = '<table cellpadding="0" cellspacing="0" class="ColorTable">';
	  tColorTableHTML += '  <tr>';
	  for (var ti = 0; ti < tColorAry.length; ti++)
	  {
	        tColorTableHTML +='    <td onmouseover="this.className=\'Selected\';" onmouseout="this.className=\'\';" onclick="' + ClickFunc + '(\'' + tColorAry[ti] + '\');"';
	        if(tColor.toUpperCase() == tColorAry[ti])
	           tColorTableHTML +=' class="Selected"';
	        tColorTableHTML +='><div style="width:11px;height:11px;background-color:' + tColorAry[ti] + ';"></div></td>';
	        if ((ti+1) % tRowNum == 0 && ti+1 != tColorAry.length)
	        {
	          tColorTableHTML += '  </tr>';
	          tColorTableHTML += '  <tr>';
	        };
	  }; 
	  tColorTableHTML += '  </tr>';
	  tColorTableHTML += '</table>';
	  return tColorTableHTML;
}
/**
 * 显示字体颜色
 */
function showFieldFontColor(id){
	
	$("#" + id).TeeMenu({menuData:menuDataColor,eventPosition:false});
}
function getFieldFont(value ){

	//alert(value + ":"+ name)
}



function saveFieldInfo()
{
	 if(checkFrom()){ 
		 var flag = parentWindowObj.setFieldInfo(
				 $('#fieldType')[0].value,
				 $('#fieldName').val(),
				 $('#border').val(),
				 $('#fieldFont').val(),
				 $('#fieldFontColor').val(),
				 $('#fieldSize').val(), 
				 $('#fieldHalign').val(),
				 $('#fieldValign').val()
		
				 ); 
					
			if (!flag) {
				CloseWindow();
			}
	 }

}

function checkFrom(){
	if($("#fieldName")[0].value == ''){
		alert("表单字段或者区域名称不能为空");
		return false;
	}
	return true;
}
/**
 * 关闭页面执行事件
 */
function  onUnloadFunc(){
	 parentWindowObj.isOpenPropWindow = false;
}

/**
 * 改变节点类型
 */
function changeField(value){

	if(value == '0'){//表单关联
		loadFlowField();//加载工作流表单
		$("#fieldNameTitle").html("关联表单字段:");
		$("#fieldNameInfo").html("<select name=\"fieldName\" id=\"fieldName\"  class=\"BigSelect\">"
			+ render.join("")
			+"</select>");
	}else if(value == '1'){//文本框
		$("#fieldNameTitle").html("区域名称:");
		$("#fieldNameInfo").html("<input name=\"fieldName\" id=\"fieldName\" class=\"BigInput\"  />");
	}else if(value == '2'){//手写区域
		$("#fieldNameTitle").html("区域名称:");
		$("#fieldNameInfo").html("<input name=\"fieldName\" id=\"fieldName\" class=\"BigInput\"/>");
	}
}
var render = [];
function loadFlowField(){
	xlist = [];
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json1 = tools.requestJsonRs(url,{flowId:flowTypeId});
	var items = json1.rtData;
	//render.push("<option value='[占位字段]'>[占位字段]</option>");
	for(var i=0;i<items.length;i++){
		var data = items[i];
		if(data.xtype=="xlist"){
			xlist.push(data);
		}
		render.push("<option value='"+data.title+"'>"+data.title+"</option>");
	}
	//$("#flowField").html(render.join(""));
}
</script>




</head>
<body onload="doInit()" onUnload="onUnloadFunc();" style="overflow-x:hidden;">


	<table class="TableBlock" width="100%" align="center">
   <tr>
    <td nowrap class="TableContent" style="width:120px;">节点类型：</td>
    <td nowrap class="TableData" id="">
    	<select name="fieldType" id="fieldType" onchange="changeField(this.value)"  class="BigSelect">
    		<option value='0'>表单关联</option>
    		<option value='1'>文本</option>
    		<option value='2'>手写区域</option>
    	</select>
     
    </td>
	</tr>
	<tr>
    <td nowrap class="TableContent" id="fieldNameTitle">关联表单字段:</td>
    <td nowrap class="TableData" id="fieldNameInfo">
    
    	
    </td>
    </tr>
    <tr>
	<td nowrap class="TableContent" align="">边框样式:</td>
    <td nowrap class="TableData">
    	<select name="border" id="border"  class="BigSelect">
    		<option value='0'>无边框</option>
    		<option value='1'>3D边框</option>
    		<option value='2'>实线边框</option>
    		<option value='3'>下滑下边框</option>
   
    	</select>
    	
    </td>
    </tr>
    
     <tr>
	<td nowrap class="TableContent" align="">字体类型</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="fieldFont" id="fieldFont" value="宋体" />
    	<a href="javascript:void(0);" id='fieldFontA' onclick="showFieldFont('fieldFontA','','')" >宋体</a>
    </td>
    </tr>
       <tr style="display:none;">
	<td nowrap class="TableContent" align="">文字颜色</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="fieldFontColor" id="fieldFontColor" value=""  class="BigInput"/>
    	<a id='fieldFontColorA' onclick="showFieldFontColor('fieldFontColorA')" style="cursor: hand" >文字颜色</a> 
    </td>
    </tr>
    
    
    
    <tr>
	<td nowrap class="TableContent" align="">字体大小</td>
    <td nowrap class="TableData">
    	<select name="fieldSize" id="fieldSize" class="BigSelect" >
    	
    		
    	</select>
    </td>
    </tr>
	  <tr>
	<td nowrap class="TableContent" align="">文字水平对齐方式</td>
    <td nowrap class="TableData">
    	<select name="fieldHalign" id="fieldHalign" class="BigSelect">
    		<option value='0'>左对齐</option>
    		<option value='1' >居中对齐</option>
    		<option value='2'>右对齐</option>
    	</select>
    </td>
    </tr>
    
    <tr>
	<td nowrap class="TableContent" align="">文字垂直对齐方式</td>
    <td nowrap class="TableData">
    	<select name="fieldValign" id="fieldValign"  class="BigSelect">
    		<option value='0'>上对齐</option>
    		<option value='1' selected>纵向对齐</option>
    		<option value='2'>下对齐</option>
    	</select>
    </td>
    </tr>
    
    
      
    
     <tr class="TableData">
	<td nowrap class="" align="center" colspan="2">
	  <input type="button" value="保存" onclick="saveFieldInfo();" class="btn btn-primary">
	  &nbsp;&nbsp;<input type="button" value="关闭" class="btn btn-primary" onclick="CloseWindow();">
	</td>
  
    </tr>

	</table>


	
</body>
</html>
 
 
 