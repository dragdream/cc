CKEDITOR.plugins.add('xdocnum', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xdocnum', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xdocnum/plugin.jsp";
	    		bsWindow(url,"文号控件",{width:"360",height:"245",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xdocnum', {
            label: "文号控件",
            command: 'xdocnum',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xdocnum:{
					label : "文号控件",
					command : 'xdocnum',
					group : 'xdocnum',
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

					var isInput = element.hasAscendant( 'input', 1 );
					
					if ( isInput && element.getAttribute('xtype')=="xdocnum")
					{
						return {
							xdocnum : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});