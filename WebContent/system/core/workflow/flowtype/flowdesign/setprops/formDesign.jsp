<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
    //步骤主键
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
	<title>独立表单设计器</title>
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
	</style>
</head>
<body style="margin:0px;overflow:hidden" onload="doInit()">
<div id="layout">
	<div layout="east" width="150" style="overflow-Y:auto;overflow-X:hidden">
		<center>
		<div class="btn-group-vertical btn-block" id="button">
		    <p style="height:14px;font-size: 14px;font-weight: bold;">字段名称</p>
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
	var prcsId = <%=prcsId %>;
	
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

			//获取主表单的所有formItem
			getBasicFormItem();
			
            //设置原有的内容
            var form=getFormInfo();
           
		    editor.setData(form);
			//maxItem = form.itemMax;
			
			
			editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'editor1' ).getParent().$.offsetHeight );
			$(window).resize(function(){
				editor.resize( editor.container.getStyle( 'width' ), CKEDITOR.document.getById( 'cke_'+'editor1' ).getParent().$.offsetHeight );
			});

		});
	}

	//获取主表单的所有formItem
	function getBasicFormItem(){
		var url = contextPath+"/flowProcess/getBasicFormItems.action";
		var json = tools.requestJsonRs(url,{prcsId:prcsId});
		if(json.rtState){
			 var fieldList=json.rtData;
			 for(var i=0;i<fieldList.length;i++){
				 var btn = $("<button style='width:100%' class='btn btn-default' onclick='insertField($(this))' id="+fieldList[i].sid+"  name='"+fieldList[i].title+"'  >"+fieldList[i].title+"</button>");
				 btn.data("object",fieldList[i]);
				 $("#button").append(btn); 
			 }
			 $("#button").append("<button style='width:100%' class='btn btn-success' onclick='commit()'>保存</button>");
		}
	}
	
	//获取表单的详情
	function getFormInfo(){
		var url = contextPath+"/flowProcess/getFormShortById.action";
		var json = tools.requestJsonRs(url,{prcsId:prcsId});
		if(json.rtState){
			 var form=json.rtData;	
			 return form;
		}else{
			return null;
		}
	}
	
	
	//点击插入html
	function insertField(obj){
		var object = obj.data("object");
		editor.insertHtml(object.content); 
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


	//保存
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
		
		var content = editor.getData();
		var url = contextPath+"/flowProcess/updateFormShort.action";
		var json=tools.requestJsonRs(url,{prcsId:prcsId,content:content});
		if(json.rtState){
			alert("保存成功！");
		}	
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
		bsWindow(url,"脚本编辑器",{width:"700px",height:"400px",submit:function(v,h){
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