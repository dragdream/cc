<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int voteType  = TeeStringUtil.getInteger(request.getParameter("voteType"), 0); //投票Id
	int voteId  = TeeStringUtil.getInteger(request.getParameter("voteId"), 0); //投票Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>新增/编辑投票项</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link  href="<%=contextPath%>/common/jquery/portlet/jquery.portlet.css?v=1.1.2" type="text/css" rel="stylesheet" />
<link  href="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.custom.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="<%=contextPath%>/common/jquery/portlet/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/system/core/base/vote/js/portlet.js" type="text/javascript"></script>
<style type="text/css">
    	.template {display:none;}
    	.version {margin-left: 0.5em; margin-right: 0.5em;}
    	.trace {margin-right: 0.5em;}
        .center {
            width: 99.3%;
            margin-left:1em;
            margin-right:1em;
        }
        
        li ul, li ol {margin:0;}
		ul, ol {margin:0 1.5em 1.5em 0;padding-left:1.5em;}
		ul {list-style-type:disc;}
		ol {list-style-type:decimal;}
		caption, th, td {text-align:left;font-weight:normal;float:none !important;}
		th, td, caption {padding:4px 10px 4px 5px;}
		caption {background:#eee;}
		.ui-widget-header{
			border-top-style:none;
			border-right-style:none;
			border-bottom-style:none;
			border-left-style:none;
			
		}
		.item{
			padding:3px;
			border-bottom:1px dotted gray;
		}
		.item:hover{
			background:#dde8fb;
			cursor:pointer;
		}
		
    </style>

<script type="text/javascript">
var voteId = "<%=voteId%>";
var voteType = "<%=voteType%>";
var i = 0;
var j = 0;

function doInit(){
	var url = "<%=contextPath %>/voteManage/getVoteBySid.action?voteId="+voteId;
	var para = {};
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		$("#headerDiv").append("&nbsp;主题："+json.subject);
	}else{
		alert(jsonObj.rtMsg);
	}
	var itemNum = jsonObj.rtData.itemNum;
	j = itemNum - 1;
	if(j<0){
		j=0;
	}
	//alert(j);
	url = "<%=contextPath %>/voteManage/getVoteListBySid.action?voteId="+voteId;
	para = {};
	jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtData.length>0){
		for(var n = jsonObj.rtData.length-1;n >=0;n--){
			//alert(jsonObj.rtData[n].textHtml);
			var title = "";
			switch (parseInt(jsonObj.rtData[n].voteType,10)) {
			case 0:
				title = "<div title='复选框'  id= 'title_"+n+"' align='center' ><span id='span_"+n+"'>"+jsonObj.rtData[n].subject+"</span>| 说明 <input onchange='explain("+n+");' type='checkbox' id='explain_"+n+"' name='explain_"+n+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+n+"' name='required_"+n+"'>&nbsp;|最小可选：<input style='width:50px;' id='optionalMin_"+n+"' name='optionalMin_"+n+"' value='"+jsonObj.rtData[n].minNum+"'> 最大可选：<input style='width:50px;' id='optionalMax_"+n+"' name='optionalMax_"+n+"' value='"+jsonObj.rtData[n].maxNum+"'></div>";
				break;
			case 1:
				title = "<div title='单选框' id= 'title_"+n+"' align='center' ><span id='span_"+n+"'>"+jsonObj.rtData[n].subject+"</span>| 说明 <input onchange='explain("+n+");' type='checkbox' id='explain_"+n+"' name='explain_"+n+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+n+"' name='required_"+n+"'>";
				break;
			case 2:
				title = "<div title='文本框' id= 'title_"+n+"' align='center' ><span id='span_"+n+"'>"+jsonObj.rtData[n].subject+"</span>| 说明 <input onchange='explain("+n+");' type='checkbox' id='explain_"+n+"' name='explain_"+n+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+n+"' name='required_"+n+"'>";
				break;
			case 3:
				title = "<div title='多行文本框' id= 'title_"+n+"' align='center' ><span id='span_"+n+"'>"+jsonObj.rtData[n].subject+"</span>| 说明 <input onchange='explain("+n+");' type='checkbox' id='explain_"+n+"' name='explain_"+n+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+n+"' name='required_"+n+"'>";
				break;
			case 4:
				title = "<div title='下拉菜单框' id= 'title_"+n+"' align='center' ><span id='span_"+n+"'>"+jsonObj.rtData[n].subject+"</span>| 说明 <input onchange='explain("+n+");' type='checkbox' id='explain_"+n+"' name='explain_"+n+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+n+"' name='required_"+n+"'>";
				break;
			}
			//alert(jsonObj.rtData[n].voteType);
			if(n==jsonObj.rtData.length-1){//第一个模块，需要初始化处理
				//i++;
				$('#portlet-container').portlet({
					sortable: true,
					columns: [{
						width: '95%',
						portlets: [{
					        attrs: {
					            id: 'portlet_'+n,
					            type : jsonObj.rtData[n].voteType
					        },
							title: title,
							content: {
								style: {
									//maxHeight: 300
								},
								type: 'text',
								text: "<div id='textDiv_"+n+"'>"+jsonObj.rtData[n].textHtml.replace(/<br>/gi,"\n")+"</div>",
								afterShow: function() {
									//i++;
									//j++;
								}
							}
						}]
					}]
				});
			}else{//第二个模块开始统一处理
				$('#portlet-container').portlet('option', 'add', {
				    position: {
					x: 0,
					y: 1
				    },
				    portlet: {
				        attrs: {
				            id: 'portlet_'+n,
				            type : jsonObj.rtData[n].voteType
				        },
				        title: title,
				        afterCreated: function() {
				            
				        },
				        content: {
				            style: {
				                //height: '100'
				            },
				            type: 'text',
				            //text: text
				            text: "<div id='textDiv_"+n+"'>"+jsonObj.rtData[n].textHtml.replace(/<br>/gi,"\n")+"</div>"
				        }
				    }
				});
			}
			if(jsonObj.rtData[n].ifContent=="1"){
				$("#explain_"+n).attr("checked",true);
				//$("#textarea_"+n).val(jsonObj.rtData[n].content);
			}
			if(jsonObj.rtData[n].required=="1"){
				$("#required_"+n).attr("checked",true);
			}
			i = jsonObj.rtData.length - n;
			//alert(parseInt(jsonObj.rtData[n].voteType,10));
			var x = jsonObj.rtData.length - n - 1;
			if((parseInt(jsonObj.rtData[n].voteType,10)==2)||(parseInt(jsonObj.rtData[n].voteType,10)==3)){
				$("#add_"+x).hide();
			}
		}	
	}else{//没保存过布局，初始化一个模块
		$('#portlet-container').portlet({
			sortable: true,
			columns: [{
				width: '95%',
				portlets: [{
			        attrs: {
			            id: 'portlet_'+i,
			            type : voteType
			        },
					title: "<div title='复选框' id= 'title_"+i+"' align='center' ><span id='span_"+i+"'>新建问题</span>| 说明 <input onchange='explain("+i+");' type='checkbox' id='explain_"+i+"' name='explain_"+i+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+i+"' name='required_"+i+"'>&nbsp;|最小可选：<input style='width:50px;' id='optionalMin_"+i+"' name='optionalMin_"+i+"'> 最大可选：<input style='width:50px;' id='optionalMax_"+i+"' name='optionalMax_"+i+"'></div>",
					content: {
						style: {
							//maxHeight: 300
						},
						type: 'text',
						text: "<div id='textDiv_"+i+"'><div id='div_"+j+"' ><table><tr><td><input type='checkbox' id='input_"+j+"' name='input_"+j+"'></td><td><span id='span_item_"+j+"' onclick='renameItem("+voteType+","+j+");'>新建选项</span></td><td><a  id='delete_"+j+"'  href='javascript:delItem("+j+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div></div>",
						afterShow: function() {
							//i++;
							//j++;
						}
					}
				}]
			}]
		});//初始化桌面
	}
}

function delItem(n){
	$("#div_"+n).remove();
}

function explain(n){
	var textarea = "<span style='display:' id='textareaSpan_"+n+"'><font color='red'>说明：</font><br><textarea cols='50' rows='5' id='textarea_"+n+"' name='textarea_"+n+"'></textarea></span>";
	if ($("#explain_"+n).attr("checked")){
		$("#textDiv_"+n).append(textarea);
	}else{
		$("#textareaSpan_"+n).remove();
	}
}

function renameItem(type,n){
	//alert("type:"+type+",n:"+n);
	var text = $("#span_item_"+n).html();
	var t = "<table><tr><td><input type='checkbox' id='item_"+n+"' name='item_"+n+"'></td><td><input id='item_title_"+n+"' name='item_title_"+n+"' value='"+text+"'></td><td><a id='delete_"+n+"'  href='javascript:delItem("+n+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>";
	switch (type) {
	case 0:

		break;
	case 1:
		t = "<table><tr><td><input type='radio' name='input_radio' id ='input_"+n+"' ></td><td><input id='item_title_"+n+"' name='item_title_"+n+"' value='"+text+"'></td><td><a  id='delete_"+n+"'  href='javascript:delItem("+n+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>";
		break;
	case 2:
		t = "";
		break;
	case 3:
		t = "";
		break;
	case 4:
		t = "<table><tr><td><input id='item_title_"+n+"' name='item_title_"+n+"' value='"+text+"'></td><td><a href='javascript:delItem("+n+")'  id='delete_"+n+"'  title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>";
		break;
	}
	$("#div_"+n).html(t);
	$("#item_title_"+n).focus();
	$("#item_title_"+n).select();
	$("#item_title_"+n).blur(function (){
		var title = $("#item_title_"+n).val();
		var y="<table><tr><td><input type='checkbox' id='input_"+n+"' name='input_"+n+"'></td><td><span id='span_item_"+n+"' onclick='renameItem("+type+","+n+");'>"+title+"</span></td><td><a  id='delete_"+n+"'  href='javascript:delItem("+n+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>"; 
		switch (type) {
		case 0:

			break;
		case 1:
			y = "<table><tr><td><input type='radio' name='input_radio' id ='input_"+n+"' ></td><td><span id='span_item_"+n+"' onclick='renameItem("+type+","+n+");'>"+title+"</span></td><td><a  id='delete_"+n+"'  href='javascript:delItem("+n+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>";
			break;
		case 2:
			y = "";
			break;
		case 3:
			y = "";
			break;
		case 4:
			y = "<table><tr><td><span id='span_item_"+n+"' onclick='renameItem("+type+","+n+");'>"+title+"</span></td><td><a href='javascript:delItem("+n+")'  id='delete_"+n+"'  title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>";
			break;
		}
		$("#div_"+n).html(y);
	});
	$("#item_title_"+n).bind('keypress',function(event){
        if(event.keyCode == "13"){
        	var title = $("#item_title_"+n).val();
    		var y="<table><tr><td><input type='checkbox' id='input_"+n+"' name='input_"+n+"'></td><td><span id='span_item_"+n+"' onclick='renameItem("+type+","+n+");'>"+title+"</span></td><td><a href='javascript:delItem("+n+")'  id='delete_"+n+"'  title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table>"; 
    		switch (type) {
    		case 0:

    			break;
    		case 1:
    			y = "<table><tr><td><input type='radio' name='input_radio' id ='input_"+n+"' ></td><td><span id='span_item_"+n+"' onclick='renameItem("+type+","+n+");'>"+title+"</span></td><td><a href='javascript:delItem("+n+")' title='删除'  id='delete_"+n+"'  class='glyphicon glyphicon-remove'></a></td></tr></table>";
    			break;
    		case 2:
    			y = "";
    			break;
    		case 3:
    			y = "";
    			break;
    		case 4:
    			y = "<table><tr><td><span id='span_item_"+n+"' onclick='renameItem("+type+","+n+");'>"+title+"</span></td><td><a href='javascript:delItem("+n+")' title='删除'  id='delete_"+n+"'  class='glyphicon glyphicon-remove'></a></td></tr></table>";
    			break;
    		}
    		$("#div_"+n).html(y);
        }
    });
}

function addItem(type,a,x){
	//alert(type+","+a+","+x);
	var text = "<div id='div_"+x+"'><table><tr><td><input type='checkbox' id='input_"+x+"' name='input_"+x+"'></td><td><span id='span_item_"+x+"' onclick='renameItem("+type+","+x+");'>新建选项</span></td><td><a href='javascript:delItem("+x+")'  id='delete_"+x+"'  title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div>";
	switch (parseInt(type,10)) {
	case 0:
		break;
	case 1:
		text = "<div id='div_"+x+"'><table><tr><td><input type='radio' name='input_radio' id ='input_"+x+"' ></td><td><span id='span_item_"+x+"' onclick='renameItem("+type+","+x+");'>新建选项</span></td><td><a href='javascript:delItem("+x+")'  id='delete_"+x+"'  title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div>";
		break;
	case 2:
		//text = "<div id='div_"+x+"'><table><tr><td><input type='radio' name='input_"+x+"'></td><td><span id='span_item_"+x+"' onclick='renameItem("+type+","+x+");'>新建选项</span></td><td><a href='javascript:delItem("+x+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div>";
		break;
	case 3:
		//text = "<div id='div_"+x+"'><table><tr><td><input type='radio' name='input_"+x+"'></td><td><span id='span_item_"+x+"' onclick='renameItem("+type+","+x+");'>新建选项</span></td><td><a href='javascript:delItem("+x+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div>";
		break;
	case 4:
		text = "<div id='div_"+x+"'><table><tr><td><span id='span_item_"+x+"' onclick='renameItem("+type+","+x+");'>新建选项</span></td><td><a href='javascript:delItem("+x+")' title='删除'  id='delete_"+x+"'  class='glyphicon glyphicon-remove'></a></td></tr></table></div>";
		break;
	}
	$("#textDiv_"+a).append(text);
}

//标题更改
function changeTitle(a,text,type){
	//alert(type);
	var explain = false;
	type = parseInt(type,10);
	if($("#explain_"+a).attr("checked")=="checked"){
		explain = true;
	}
	var required = false;
	if($("#required_"+a).attr("checked")=="checked"){
		required = true;
	}
	var title = "";
	switch (type) {
	case 0:
		var max = $("#optionalMax_"+a).val();
		var min = $("#optionalMin_"+a).val();
		title = "<input id='inputTitle_"+a+"' name='inputTitle_"+a+"' value='"+text+"'> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>&nbsp;|最小可选：<input style='width:50px;' id='optionalMin_"+a+"' name='optionalMin_"+a+"' value="+min+"> 最大可选：<input style='width:50px;' id='optionalMax_"+a+"' name='optionalMax_"+a+"' value="+max+">";
		break;
	case 1:
		title = "<input id='inputTitle_"+a+"' name='inputTitle_"+a+"' value='"+text+"'> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
		break;
	case 2:
		title = "<input id='inputTitle_"+a+"' name='inputTitle_"+a+"' value='"+text+"'> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
		break;
	case 3:
		title = "<input id='inputTitle_"+a+"' name='inputTitle_"+a+"' value='"+text+"'> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
		break;
	case 4:
		title = "<input id='inputTitle_"+a+"' name='inputTitle_"+a+"' value='"+text+"'> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
		break;
		
	}
	$("#title_"+a).html(title);
	$("#explain_"+a).attr("checked",explain);
	$("#required_"+a).attr("checked",required);
	$("#inputTitle_"+a).focus();
	$("#inputTitle_"+a).select();
	$("#inputTitle_"+a).blur(function (){
		var value = $("#inputTitle_"+a).val();
		var explain1 = false;
		if($("#explain_"+a).attr("checked")=="checked"){
			explain1 = true;
		}
		var required1 = false;
		if($("#required_"+a).attr("checked")=="checked"){
			required1 = true;
		}
		var t = "";
		switch (type) {
		case 0:
			var max1 = $("#optionalMax_"+a).val();
			var min1 = $("#optionalMin_"+a).val();
			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>&nbsp;|最小可选：<input style='width:50px;' id='optionalMin_"+a+"' name='optionalMin_"+a+"' value="+min1+"> 最大可选：<input style='width:50px;' id='optionalMax_"+a+"' name='optionalMax_"+a+"' value="+max1+">";
			break;
		case 1:
			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
			break;
		case 2:
			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
			break;
		case 3:
			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
			break;
		case 4:
			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
			break;
		}
		$("#title_"+a).html(t);
		$("#explain_"+a).attr("checked",explain1);
		$("#required_"+a).attr("checked",required1);
	});
	$("#inputTitle_"+a).bind('keypress',function(event){
        if(event.keyCode == "13"){
        	var value = $("#inputTitle_"+a).val();
    		var explain1 = false;
    		if($("#explain_"+a).attr("checked")=="checked"){
    			explain1 = true;
    		}
    		var required1 = false;
    		if($("#required_"+a).attr("checked")=="checked"){
    			required1 = true;
    		}
    		var t = "";
    		switch (type) {
    		case 0:
    			var max1 = $("#optionalMax_"+a).val();
    			var min1 = $("#optionalMin_"+a).val();
    			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>&nbsp;|最小可选：<input style='width:50px;' id='optionalMin_"+a+"' name='optionalMin_"+a+"' value="+min1+"> 最大可选：<input style='width:50px;' id='optionalMax_"+a+"' name='optionalMax_"+a+"' value="+max1+">";
    			break;
    		case 1:
    			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
    			break;
    		case 2:
    			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
    			break;
    		case 3:
    			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
    			break;
    		case 4:
    			t = "<span id='span_"+a+"'>"+value+"</span> | 说明 <input onchange='explain("+a+");' type='checkbox' id='explain_"+a+"' name='explain_"+a+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+a+"' name='required_"+a+"'>";
    			break;
    			
    		}
    		$("#title_"+a).html(t);
    		$("#explain_"+a).attr("checked",explain1);
    		$("#required_"+a).attr("checked",required1);
        }
    });

}

function addPortlet(vateType){
	i++;
	j++;
	var title = "<div title='复选框'  id= 'title_"+i+"' align='center' ><span id='span_"+i+"'>新建问题</span>| 说明 <input onchange='explain("+i+");' type='checkbox' id='explain_"+i+"' name='explain_"+i+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+i+"' name='required_"+i+"'>&nbsp;|最小可选：<input style='width:50px;' id='optionalMin_"+i+"' name='optionalMin_"+i+"'> 最大可选：<input style='width:50px;' id='optionalMax_"+i+"' name='optionalMax_"+i+"'></div>";
	var text = "";
	switch (vateType) {
	case 0:
		text = "<div  id='textDiv_"+i+"'><div id='div_"+j+"'><table><tr><td><input type='checkbox' id='input_"+j+"' name='input_"+j+"'></td><td><span id='span_item_"+j+"' onclick='renameItem("+vateType+","+j+");'>新建选项</span></td><td><a  id='delete_"+j+"'  href='javascript:delItem("+j+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div></div>";
		break;
	case 1:
		title = "<div title='单选框' id= 'title_"+i+"' align='center' ><span id='span_"+i+"'>新建问题</span>| 说明 <input onchange='explain("+i+");' type='checkbox' id='explain_"+i+"' name='explain_"+i+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+i+"' name='required_"+i+"'></div>";
		text = "<div id='textDiv_"+i+"'><div id='div_"+j+"'><table><tr><td><input type='radio' name='input_radio' id ='input_"+j+"' ></td><td><span id='span_item_"+j+"' onclick='renameItem("+vateType+","+j+");'>新建选项</span></td><td><a  id='delete_"+j+"'  href='javascript:delItem("+j+")' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div></div>";
		break;
	case 2:
		title = "<div title='文本框'  id= 'title_"+i+"' align='center' ><span id='span_"+i+"'>新建问题</span>| 说明 <input onchange='explain("+i+");' type='checkbox' id='explain_"+i+"' name='explain_"+i+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+i+"' name='required_"+i+"'></div>";
		text = "<div id='textDiv_"+i+"'><div id='div_"+j+"'><input id='input_"+j+"' name='input_"+j+"'></div></div>";
		break;
	case 3:
		title = "<div title='多行文本框' id= 'title_"+i+"' align='center' ><span id='span_"+i+"'>新建问题</span>| 说明 <input onchange='explain("+i+");' type='checkbox' id='explain_"+i+"' name='explain_"+i+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+i+"' name='required_"+i+"'></div>";
		text = "<div id='textDiv_"+i+"'><div id='div_"+j+"'><textarea cols='50' rows='5' id='input_"+j+"' name='input_"+j+"'></textarea></div></div>";
		break;
	case 4:
		title = "<div title='下拉菜单框'  id= 'title_"+i+"' align='center' ><span id='span_"+i+"'>新建问题</span>| 说明 <input onchange='explain("+i+");' type='checkbox' id='explain_"+i+"' name='explain_"+i+"'>&nbsp; | 必填 <input type='checkbox' id='required_"+i+"' name='required_"+i+"'></div>";
		text = "<div id='textDiv_"+i+"'><div id='div_"+j+"'><table><tr><td><span id='span_item_"+j+"' onclick='renameItem("+vateType+","+j+");'>新建选项</span></td><td><a href='javascript:delItem("+j+")' id='delete_"+j+"' title='删除' class='glyphicon glyphicon-remove'></a></td></tr></table></div></div>";
		break;
	}
	
	$('#portlet-container').portlet('option', 'add', {
	    position: {
		x: 0,
		y: 1
	    },
	    portlet: {
	        attrs: {
	            id: 'portlet_'+i,
	            type : vateType
	        },
	        title: title,
	        afterCreated: function() {
	            
	        },
	        content: {
	            style: {
	                //height: '100'
	            },
	            type: 'text',
	            text: text
	        }
	    }
	});
	if(vateType==2||vateType==3){
		$("#add_"+i).hide();
	}
}


String.prototype.replaceAll = function (str1,str2){
	  var str    = this;     
	  var result   = str.replace(/eval(str1)/gi,str2);
	  return result;
}


function checkSave(){
	var indexs = $('#portlet-container').portlet('index');
	var flag = true;
	$.each(indexs, function(k, v) {
		var id = k.split("_")[1];
		var type =v.t;
		var text = $("#textDiv_"+id).children("div");
		switch (parseInt(type,10)) {
		case 0:
			var minNum = 0;	
			var maxNum = 0;
			var num = 0;
			$.each(text,function(x,div){
				num++;
			});
			if($("#optionalMax_"+id).val()!=""){
				maxNum = $("#optionalMax_"+id).val();
			}
			if($("#optionalMin_"+id).val()!=""){
				minNum = $("#optionalMin_"+id).val();
			}
			var voteSubject = $("#span_"+id).text();
			var reg = /^[0-9]*[0-9][0-9]*$/;
			if(!reg.test(minNum)){
				alert(voteSubject+"的最小可选数必须为整数！");
				flag = false;
				return;
			}
			if(!reg.test(maxNum)){
				alert(voteSubject+"的最大可选数必须为整数！");
				flag = false;
				return;
			}
			if(minNum > maxNum){
				alert(voteSubject+"的最小可选数不能大于最大可选数！");
				flag = false;
				return;
			}
			if(maxNum > num){
				alert(voteSubject+"的最大可选数不能大于总项数！");
				flag = false;
				return;
			}
			break;
		}
	});
	return flag;
}

function replaceAllBack(html,str1,str2){
	var url = "<%=contextPath %>/voteManage/replaceAll.action";
    var para = {html:html,str1:str1,str2:str2};
    var jsonObj = tools.requestJsonRs(url,para);
    var json = "";
    if(jsonObj.rtState){
        json = jsonObj.rtData;
        
    }
    return json;
}

function saveVote(){
	if(checkSave()){
	    var indexs = $('#portlet-container').portlet('index');
		var itemNum = 0;
		var json = "{\"vote\":[";
	    $.each(indexs, function(k, v) {
	    	var y = parseInt(v.y);
			var type =v.t;
	    	var id = k.split("_")[1];
	    	var text = $("#textDiv_"+id).children("div");
	    	var content = "";
	    	var minNum = 0;
	    	var maxNum = 0;
	    	var ifContent=0;
	    	var required=0;
	    	var textHtml = "";
	    	var voteSubject = $("#span_"+id).text();
	    	if ($("#required_"+id).attr("checked")){
	    		required = 1;
	    	}
	    	switch (parseInt(type,10)) {
			case 0:
				var num = 0;
				$.each(text,function(x,div){
					num++;
				});
				if($("#optionalMax_"+id).val()==""){
					maxNum = num;
				}else{
					maxNum = $("#optionalMax_"+id).val();
				}
				if($("#optionalMin_"+id).val()!=""){
					minNum = $("#optionalMin_"+id).val();
				}
				break;
			}
	    	json += "{\"item\":[";
	    	$.each(text,function(x,div){
	    		var divId = "div_"+div.id.split("_")[1];
	    		//alert(div.id);
	    		var i = div.id.split("_")[1];
	    		var aId = "delete_"+div.id.split("_")[1];
	    		var idOld = "_"+i;
	    		var idNew = "_"+itemNum;
	            var html = $("#"+divId).html();
	            html = html.replaceAll(idOld,idNew);
	    		html = replaceAllBack(html,i,itemNum);
	    		html = "<div id='div_"+itemNum+"'>" + html +"</div>";
	    		html = html.replace(/\"/g,"'");
	    		textHtml += html; 
	    		var itemSubject = "";
	    		if($("#span_item_"+i)){
	    			itemSubject = $("#span_item_"+i).text();
	    			//alert(itemSubject);
	    		}
	    		json += "{\"itemSubject\":\""+itemSubject+"\",\"voteNo\":\""+itemNum+"\"},";
	    		itemNum++;
	    	});
	    	if(json.charAt(json.length-1)==","){
	    		json = json.substring(0,json.length-1);
	    	}
	    	if ($("#explain_"+id).attr("checked")){
	    		ifContent = 1;
	    		content = $("#textarea_"+id).val();
	    		textHtml += "<span style='display:' id='textareaSpan_"+y+"'><font color='red'>说明：</font><br><textarea cols='50' rows='5' id='textarea_"+y+"' name='textarea_"+y+"'>"+content+"</textarea></span>";
	    	}
	    	json+="],\"type\":\""+type+"\",\"content\":\""+content+"\",\"minNum\":\""+minNum+"\",\"maxNum\":\""+maxNum+"\",\"ifContent\":\""+ifContent+"\",\"required\":\""+required+"\",\"voteSubject\":\""+voteSubject+"\",\"textHtml\":\""+textHtml+"\",\"voteNo\":\""+y+"\"},";
	    });
	    if(json.charAt(json.length-1)==","){
			json = json.substring(0,json.length-1);
		}
	    json +="],";
	    json += "\"itemNum\":\""+itemNum+"\",\"voteId\":\""+voteId+"\"}";
		var url = "<%=contextPath %>/voteManage/saveVote.action";
		var para = {json:json};
		$.jBox.tip("正在保存...","loading");
		tools.requestJsonRs(url,para,true,function(jsonObj){
			$.jBox.tip(jsonObj.rtMsg,"success");
			location.reload();
		});
	}
}

function preview(){
    var indexs = $('#portlet-container').portlet('index');
	var itemNum = 0;
	var json = "{\"vote\":[";
    $.each(indexs, function(k, v) {
    	var y = parseInt(v.y);
		var type =v.t;
    	var id = k.split("_")[1];
    	//alert("id:"+id);
    	var text = $("#textDiv_"+id).children("div");
    	var content = "";
    	var minNum = 0;
    	var maxNum = 0;
    	var ifContent=0;
    	var required=0;
    	var textHtml = "";
    	var voteSubject = $("#span_"+id).text();

    	if ($("#required_"+id).attr("checked")){
    		required = 1;
    	}
    	switch (parseInt(type,10)) {
		case 0:
			if($("#optionalMax_"+id).val()!=""){
				maxNum = $("#optionalMax_"+id).val();
			}
			if($("#optionalMin_"+id).val()!=""){
				minNum = $("#optionalMin_"+id).val();
			}
			break;
		}
    	
    	json += "{\"item\":[";
    	$.each(text,function(x,div){
    		
    		
    		var divId = "div_"+div.id.split("_")[1];
    		var i = div.id.split("_")[1];
    		var aId = "delete_"+div.id.split("_")[1];
    		var idOld = "_"+i;
    		var idNew = "_"+itemNum;
    		var html = $("#"+divId).html();
    		html = html.replaceAll(idOld,idNew);
            html = replaceAllBack(html,i,itemNum);
    		html = "<div id='div_"+itemNum+"'>" + html +"</div>";
    		html = html.replace(/\"/g,"'");
    		textHtml += html; 
    		var itemSubject = "";
    		if($("#span_item_"+i)){
    			itemSubject = $("#span_item_"+i).text();
    		}
    		json += "{\"itemSubject\":\""+itemSubject+"\",\"voteNo\":\""+itemNum+"\"},";
    		itemNum++;
    	});
    	if(json.charAt(json.length-1)==","){
    		json = json.substring(0,json.length-1);
    	}
    	//alert(id+","+$("#textarea_"+id).val());
    	if ($("#explain_"+id).attr("checked")){
    		ifContent = 1;
    		content = $("#textarea_"+id).val();
    		textHtml += "<span style='display:' id='textareaSpan_"+y+"'><font color='red'>说明：</font><br><textarea cols='50' rows='5' id='textarea_"+y+"' name='textarea_"+y+"'>"+content+"</textarea></span>";
    	}
    	json+="],\"type\":\""+type+"\",\"content\":\""+content+"\",\"minNum\":\""+minNum+"\",\"maxNum\":\""+maxNum+"\",\"ifContent\":\""+ifContent+"\",\"required\":\""+required+"\",\"voteSubject\":\""+voteSubject+"\",\"textHtml\":\""+textHtml+"\",\"voteNo\":\""+y+"\"},";
    });
    if(json.charAt(json.length-1)==","){
		json = json.substring(0,json.length-1);
	}
    json +="],";
    json += "\"itemNum\":\""+itemNum+"\",\"voteId\":\""+voteId+"\"}";
    //alert(json);
    var jsonObj = eval('('+json+')');
    //调用jq ui模态窗口
    showDialog(jsonObj);
}

/**
 * 打开预览模态窗口
 * @param json
 */
function showDialog(json) {
	//alert(json.itemNum);
	var htmlStr = "<div class='portlet_class' title='投票预览'><span class='ui-loading'>正在加载数据……</span></div>";  
    /*
	dig = window.top.$(htmlStr).appendTo(window.top.document.body);  
	dig.dialog({
		modal: true,
		width: 600,
		height: 400,//screen.availHeight / 2,
		open: function() {
			getPreViewStr(json);
		},
		close: function() {
			window.top.$('.portlet_class').remove();
		},
		buttons: [{
			text: '关闭',
			'class' : 'btn btn-primary',
			click: function(){window.top.$('.portlet_class').remove();}
		}]
	});
	*/
	htmlStr = getPreViewStr(json);
	top.$.jBox(htmlStr, { title: "预览", width:600,height:400,buttons:{'关闭': true}});
}

function getPreViewStr(json){
    var html = "<table style='margin-left: 10px;'>";
	for(var i = 0;i<json.vote.length;i++){
		var content = "";
		if(json.vote[i].ifContent==1){
			content = "说明："+json.vote[i].content+"，";
		}
		var required = "";
		if(json.vote[i].required==1){
			required = "必填，";
		}
		var minNum = "";
		if(json.vote[i].minNum!=""&&json.vote[i].minNum>0){
			minNum = "最小可选："+json.vote[i].minNum+"，";
		}
		var maxNum = "";
		if(json.vote[i].maxNum!=0&&json.vote[i].maxNum!=""&&json.vote[i].minNum!="0"){
			maxNum = "最大可选："+json.vote[i].maxNum+"，";
		}
		var appendix = content + required + minNum + maxNum;
		if(appendix!=""){
		    if(appendix.charAt(appendix.length-1)=="，"){
		    	appendix = appendix.substring(0,appendix.length-1);
			}
			appendix = "<font color='red'>（"+appendix+"）</font>";
		}
		var order = i+1+"、";
		html += "<tr style='min-height:30px;'><td><strong>"+order+json.vote[i].voteSubject+"</strong>"+appendix+"</td></tr>";
		var textHtml = json.vote[i].textHtml;
		textHtml = textHtml.replace(/glyphicon glyphicon-remove/g,"");
		textHtml = textHtml.replace(/display:/g,"display:none;");
		textHtml = textHtml.replace(/onclick=/g,"");
		if(json.vote[i].type=="4"){
			textHtml = textHtml.replace(/\<table\>\<tbody\>\<tr\>\<td\>/g,"");
			textHtml = textHtml.replace(/\<\/td\>\<td\>/g,"");
			textHtml = textHtml.replace(/\<\/td\>\<\/tr\>\<\/tbody\>\<\/table\>/g,"");
			textHtml = textHtml.replace(/div/g,"option");
			textHtml = textHtml.replace(/\<span style/g,"</select><span style");
			if(textHtml.indexOf("</select>")>0){
				textHtml = "<select> " + textHtml;
			}else{
				textHtml = "<select> " + textHtml + "</select>";
			}
			
		}
		html += "<tr><td>"+textHtml+"</td></tr>";
	}	
	html+="</table>";
	//alert(window.top.$('.portlet_class').html());
	//window.top.$('.portlet_class').html(html);
	return html;
}

function toReturn(){
	window.parent.location = contextPath + "/system/core/base/vote/manager/index.jsp";
}
</script>

</head>
<body onload="doInit();" style='overflow-x:hidden;'>
	<div class="moduleHeader" style="margin-left: 10px;">
		<b>
			<div align="left" id="headerDiv">
			</div>
		</b>
	</div>
	<div style="margin-left: 10px;">
		<input type="button"  value="保存" class="btn btn-primary" onClick="saveVote();" title="保存" />
<!-- 	<input type="button"  value="预览" class="btn btn-warning" onClick="preview();" title="预览" /> -->
	<input type="button"  value="返回" class="btn btn-danger" onClick="toReturn();" title="返回" />
	</div>
	
	
	<form action=""  method="post" name="form1" id="form1">
	<div class="center" style="margin-top:5px">
        <div style="text-align: center;">
        </div>
        <div id='portlet-container' ></div>
    </div>
	</form>	
</body>
</html>
