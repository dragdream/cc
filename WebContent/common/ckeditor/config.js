CKEDITOR.editorConfig = function( config ){
	config.extraPlugins = 'xinput,xtextarea,xradio,xselect,xcheckbox,xcalculate,xfetch,xlist,xseal,xmacrotag,xmacro,ximg,xdocnum,xfeedback,pubmodel,imgupload,xdatasel,xmobileseal,xmobilehandseal,xvoice,xh5hw,xupload,ximg,xsql,quickfill,xautonumber,xaddress,xposition,xqrcode,xflowdatasel,xbarcode';
	config.menu_groups = "clipboard,form,tablecell,tablecellproperties,tablerow,tablecolumn,table,anchor,link,flash,hiddenfield,imagebutton,button,div,xinput,xtextarea,xradio,xselect,xcheckbox,xcalculate,xfetch,xlist,xseal,xmacrotag,xmacro,ximg,xdocnum,xfeedback,xdatasel,xmobileseal,xmobilehandseal,xvoice,xupload,ximg,xh5hw,xsql,xautonumber,xaddress,xposition,xqrcode,xflowdatasel,xbarcode";
	config.allowedContent = true;
	config.baseFloatZIndex = 100;// 编辑器的z-index值 
	config.removePlugins='forms';
	
	
	//config.uiColor = '#FFF'; // 背景颜色 
	//config.undoStackSize =20; //撤销的记录步数 plugins/undo/plugin.js 
	//config.resize_enabled = true;// 取消 “拖拽以改变尺寸”功能 plugins/resize/plugin.js 
	//config.toolbarLocation = 'top';//工具栏的位置 可选：bottom 
//	config.toolbarCanCollapse = true;//工具栏是否可以被收缩
//	config.toolbarStartupExpanded = true;//工具栏默认是否展开

	config.toolbar = 'MyToolbar';// 工具栏（基础'Basic'、全能'Full'、自定义）plugins/toolbar/plugin.js 
	 
    config.toolbar_MyToolbar =
    [
            { name: 'document', items : [ 'Source','NewPage','Preview' ] },
            { name: 'basicstyles', items : [ 'Bold','Italic','Strike','-','RemoveFormat' ] },
        	{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
            { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','Scayt' ] },
            '/',
            { name: 'styles', items : ['Font', 'Styles','FontSize','TextColor','BGColor','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'  ] },
            { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote' ] },
            { name: 'insert', items :[ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'
             ,'Iframe' ] },
            { name: 'links', items : [ 'Link','Unlink','Anchor' ]},
            { name: 'tools', items : [ 'Maximize','-','pubmodel','imgupload','quickfill' ] }
    ]; 
    
//    config.font_style={
//    		element:"span",
//    };
    
    config.fontSize_defaultLabel="12px";//默认字体大小
    config.tabSpaces = 4;//当用户键入TAB时，编辑器走过的空格数，(&nbsp;) 当值为0时，焦点将移出编辑框 plugins/tab/plugin.js 
    config.enterMode = CKEDITOR.ENTER_DIV;//编辑器中回车产生的标签 ，可选：CKEDITOR.ENTER_BR或CKEDITOR.ENTER_DIV 
    config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+ config.font_names;
};

