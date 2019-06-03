CKEDITOR.plugins.add('xmacrotag', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xmacrotag',{
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xmacrotag/plugin.jsp";
	    		bsWindow(url,"宏标记",{width:"550",height:"300",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			/*if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;*/
	    			return true;
	    		}});
	    	}
        }
       );
        editor.ui.addButton('xmacrotag', {
            label: "宏标记",
            command: 'xmacrotag',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xmacrotag', this.path + 'dialogs/xmacrotag.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xmacrotag:{
					label : "宏标记",
					command : 'xmacrotag',
					group : 'xmacrotag',
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

					var tagName = element.hasAscendant( 'img', 1 );
					
					if ( tagName && element.getAttribute('xtype')=="xmacrotag")
					{
						return {
							xmacrotag : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});