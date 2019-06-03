CKEDITOR.plugins.add('xfetch', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xfetch', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xfetch/plugin.jsp";
	    		bsWindow(url,"选择器控件",{width:"408",height:"270",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xfetch', {
            label: "选择器控件",
            command: 'xfetch',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xfetch', this.path + 'dialogs/xfetch.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xfetch:{
					label : "选择器控件属性",
					command : 'xfetch',
					group : 'xfetch',
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
					
					if ( isInput && element.getAttribute('xtype')=="xfetch")
					{
						return {
							xfetch : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});