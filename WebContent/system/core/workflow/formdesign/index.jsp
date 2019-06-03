<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
	<title>表单设计器</title>
	<meta charset="utf-8">
	<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
	<style>
		.plugin_panel{
			width:150px;
			position:absolute;
			background:white;
			z-index:101;
			border-left:1px solid gray;
			background:#f0f0f0;
		}
			.btn_con{
			margin-bottom:10px;
		}
		.btn{
			border:0px;
			background:#f0f0f0;
			font-size:12px;
			border-bottom:1px solid gray;
			padding-top:2px;
			padding-bottom:2px;
		}
	</style>
</head>
<body style="margin:0px;overflow:hidden;" onload="doInit()">
<div id="layout">
	<div layout="east" width="150" >
		<center>
		<div class="btn-group-vertical btn-block">
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xinput').exec();">单行输入框</button>
			<button style="width:100%" class="btn btn-default"  onclick="editor.getCommand('xtextarea').exec();">多行文本框</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xselect').exec();">下拉菜单</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xradio').exec();">单选框</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xcheckbox').exec();">复选框</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xcalculate').exec();">计算控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xfetch').exec();">选择器</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xlist').exec();">列表控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xmacrotag').exec();">宏标记</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xmacro').exec();">宏控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xsql').exec();">SQL控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xdocnum').exec();">文号控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xdatasel').exec();">数据选择控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xflowdatasel').exec();">流程数据选择控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('ximg').exec();">图片上传控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xupload').exec();">附件上传控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xfeedback').exec();">会签控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xseal').exec();">签章控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xmobileseal').exec();">图片签章</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xh5hw').exec();">H5手写签批</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xmobilehandseal').exec();">移动签批</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xvoice').exec();">语音控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xautonumber').exec();">自动编号控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xaddress').exec();">区域联动控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xposition').exec();">定位控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xqrcode').exec();">二维码控件</button>
			<button style="width:100%" class="btn btn-default" onclick="editor.getCommand('xbarcode').exec();">条形码控件</button>
			<button style="width:100%" class="btn btn-default" onclick="cssEdit()">样式编辑器</button>
			<button style="width:100%" class="btn btn-default" onclick="scriptEdit()">脚本编辑器</button>
			<button style="width:100%" class="btn btn-default" onclick="explore()">表单预览</button>
			<button style="width:100%" class="btn btn-success" onclick="commit()">保存</button>
			<button style="width:100%" class="btn btn-one" onclick="createVersionForm()">生成版本</button>
		</div>
		</center>
	</div>
	<div layout="center">
		<textarea id="editor1" name="editor1"></textarea>
	</div>
</div>
<script>
	var contextPath = "<%=contextPath%>";
	var maxItem = 0;
	var formId = <%=formId%>;
	
	var editor;
	function doInit(){
		$("#layout").layout({auto:true});
		
		//初始化控件
		$("#panel").floatToolsPanel();
		CKEDITOR.replace('editor1',{
			width : 'auto',
			height:600
		});
		CKEDITOR.on('instanceReady', function (e) {
			//var command = e.editor.getCommand( 'maximize' );
			editor = e.editor;
			//command.exec();

			//获取表单信息
			var form = getFlowForm();
			if(!form){
				alert("无表单数据");
				return ;
			}

			editor.setData(form.printModel);
			maxItem = form.itemMax;
			
			editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'editor1' ).getParent().$.offsetHeight );
			$(window).resize(function(){
				editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'editor1' ).getParent().$.offsetHeight );
			});

		});
	}

	//获取表单实体
	function getFlowForm(){
		var url = contextPath+"/flowForm/get.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			return json.rtData;
		}else{
			return undefined;
		}
	}

	(function($){
		$.fn.floatToolsPanel=function(){
			var cur = $(this);
			var resize = function(){
				var wheight = $(window).height();
				var wwidth = $(window).width();
				cur.css({height:wheight,top:0,left:wwidth-parseInt(cur.css("width"))});
			}
			$(window).resize(function(){
				resize();
			});
			var wheight = $(window).height();
			var wwidth = $(document).width();
			cur.css({height:wheight,top:0,left:wwidth-parseInt(cur.css("width"))});

			var hidePixes = 120;

			cur.attr("owidth",parseInt(cur.css("width")));
			var wheight = $(window).height();
			var wwidth = $(document).width();
			var oWidth = cur.attr("owidth");
			//cur.css({left:wwidth-(oWidth-hidePixes)});

			cur.mouseover(function(){
				var wheight = $(window).height();
				var wwidth = $(document).width();
				var oWidth = cur.attr("owidth");
				cur.stop();
				//cur.animate({left:wwidth-oWidth},200);
			});

			cur.mouseout(function(){
				var wheight = $(window).height();
				var wwidth = $(document).width();
				var oWidth = cur.attr("owidth");
				cur.stop();
				//cur.animate({left:wwidth-(oWidth-hidePixes)},200);
			});
		}
	})(jQuery);

	//检查重名控件
	function checkNameRepeated(title){
		var maps = getNamesMap();
		if(maps[title]){
			return false;
		}
		return true;
	}

	function getNamesMap(){
		var dom = $(editor.getData());
		var imgs = dom.find("img[title]");
		var inputs = dom.find("input[title]");
		var selects = dom.find("select[title]");
		var textareas = dom.find("textarea[title]");

		var maps = {};
		imgs.each(function(i,obj){
			var key = $(obj).attr("title");
			if(!maps[key]){
				maps[key]=1;
			}
		});
		inputs.each(function(i,obj){
			var key = $(obj).attr("title");
			if(!maps[key]){
				maps[key]=1;
			}
		});
		selects.each(function(i,obj){
			var key = $(obj).attr("title");
			if(!maps[key]){
				maps[key]=1;
			}
		});
		textareas.each(function(i,obj){
			var key = $(obj).attr("title");
			if(!maps[key]){
				maps[key]=1;
			}
		});
		return maps;
	}

	function createVersionForm(){
		if(window.confirm("是否确定生成表单版本？")){
			var url = contextPath+"/flowForm/createVersionForm.action";
			var json = tools.requestJsonRs(url,{formId:formId});
			alert(json.rtMsg);
			if(json.rtState){
				window.location = "index.jsp?formId="+json.rtData;
			}
		}
	}
	
	function commit(){
		var regexp = /name=\"DATA_[0-9]*\"/gi;
		var content = editor.getData();
		var match = "";
		var count = {};
		while((match = regexp.exec(content))!=null){
			if(!count[match]){
				count[match] = 1;
			}else{
				alert("存在重复控件ID，请仔细检查表单控件！");
				return ;
			}
		}
		$.MsgBox.Loading();
		var url = contextPath+"/flowForm/commitForm.action";
		tools.requestJsonRs(url,{content:editor.getData(),formId:formId},true,function(json){
			$.MsgBox.Alert_auto(json.rtMsg);
			$.MsgBox.CloseLoading();
		});
	}

	function cssEdit(){
		var url = contextPath+"/system/core/workflow/formdesign/style.jsp?formId="+formId+"&rand="+new Date().getTime();
		bsWindow(url,"样式编辑器",{width:"700px",height:"400px",submit:function(v,h){
			var cw = h[0].contentWindow;
			cw.commit();
			var css = cw.getCssContent();
			
			var styleElement = editor.document.getById('FORM_STYLE_ELEMENT');
			if(styleElement==null){
				//styleElement = new CKEDITOR.dom.element( 'style' );
				//styleElement.setAttribute("id","FORM_STYLE_ELEMENT");
				editor.setData(editor.getData()+"<style id=\"FORM_STYLE_ELEMENT\"><\/style>");
				styleElement = editor.document.getById('FORM_STYLE_ELEMENT');
			}
			styleElement.setHtml(css);
			return true;
		}});
	}

	function scriptEdit(){
		var url = contextPath+"/system/core/workflow/formdesign/script.jsp?formId="+formId+"&rand="+new Date().getTime();
		bsWindow(url,"脚本编辑器",{width:"700",height:"400",submit:function(v,h){
			var cw = h[0].contentWindow;
			return cw.commit();
		}});
	}
	
	function explore(){
		openFullWindow(contextPath+"/system/core/workflow/formdesign/printExplore.jsp?formId="+formId);
	}
</script>
</body>
</html>