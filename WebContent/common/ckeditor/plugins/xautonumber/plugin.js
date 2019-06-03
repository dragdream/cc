CKEDITOR.plugins.add('xautonumber', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xautonumber', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xautonumber/plugin.jsp";
	    		bsWindow(url,"自动编号控件",{width:"360",height:"100",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xautonumber', {
            label: "自动编号控件",
            command: 'xautonumber',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xautonumber:{
					label : "自动编号控件",
					command : 'xautonumber',
					group : 'xautonumber',
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

					var isTag = element.hasAscendant( 'input', 1 );
					
					if ( isTag && element.getAttribute('xtype')=="xautonumber")
					{
						return {
							xautonumber : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});