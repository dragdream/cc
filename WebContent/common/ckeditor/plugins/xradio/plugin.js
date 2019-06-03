CKEDITOR.plugins.add('xradio', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xradio', {
        	exec:function(){
        		var url = contextPath+"/common/ckeditor/plugins/xradio/plugin.jsp";
        		bsWindow(url,"单项选择控件",{width:"400",height:"350",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
        	}
        });
        editor.ui.addButton('xradio', {
            label: "单项选择控件",
            command: 'xradio',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xradio', this.path + 'dialogs/xradio.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xradio:{
					label : "单项选择控件属性",
					command : 'xradio',
					group : 'xradio',
					order : 5
				}
			});
		}
		
		if ( editor.contextMenu )
		{
			editor.contextMenu.addListener( function( element, selection )
				{
					if ( !element || element.isReadOnly() )
						return null;

					var tagName = element.hasAscendant( 'input', 1 );
					
					if ( tagName && element.getAttribute('xtype')=="xradio" && element.getAttribute('type')=="radio")
					{
						return {
							xradio : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});