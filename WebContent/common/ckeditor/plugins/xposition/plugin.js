CKEDITOR.plugins.add('xposition', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xposition', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xposition/plugin.jsp";
	    		bsWindow(url,"定位控件",{width:"360",height:"140",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xposition', {
            label: "定位控件",
            command: 'xposition',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xposition:{
					label : "定位控件",
					command : 'xposition',
					group : 'xposition',
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

					var isInput = element.hasAscendant( 'img', 1 );
					
					if ( isInput && element.getAttribute('xtype')=="xposition")
					{
						return {
							xposition : CKEDITOR.TRISTATE_OFF
						};
					} 
					return null;
				} );
		}
    }
});