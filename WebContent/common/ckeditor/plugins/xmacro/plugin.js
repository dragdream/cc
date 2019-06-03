CKEDITOR.plugins.add('xmacro', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xmacro', {
        	exec:function(){
        		var url = contextPath+"/common/ckeditor/plugins/xmacro/plugin.jsp";
        		bsWindow(url,"宏控件",{width:"400",height:"250",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
        	}
        });
        editor.ui.addButton('xmacro', {
            label: "宏控件",
            command: 'xmacro',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xmacro', this.path + 'dialogs/xmacro.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xmacro:{
					label : "宏控件",
					command : 'xmacro',
					group : 'xmacro',
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
					
					if ( tagName && element.getAttribute('xtype')=="xmacro")
					{
						return {
							xmacro : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});