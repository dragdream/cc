CKEDITOR.plugins.add('xfeedback', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xfeedback', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xfeedback/plugin.jsp";
	    		bsWindow(url,"会签控件",{width:"600",height:"400",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xfeedback', {
            label: "会签控件",
            command: 'xfeedback',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xfeedback:{
					label : "会签控件",
					command : 'xfeedback',
					group : 'xfeedback',
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

					var isInput = element.hasAscendant( 'textarea', 1 );
					
					if ( isInput && element.getAttribute('xtype')=="xfeedback")
					{
						return {
							xfeedback : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});