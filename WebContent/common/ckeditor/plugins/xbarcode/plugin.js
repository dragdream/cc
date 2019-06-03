CKEDITOR.plugins.add('xbarcode', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xbarcode', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xbarcode/plugin.jsp";
	    		bsWindow(url,"条形码控件",{width:"550",height:"300",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xbarcode', {
            label: "条形码控件",
            command: 'xbarcode',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xbarcode:{
					label : "条形码控件",
					command : 'xbarcode',
					group : 'xbarcode',
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
					
					if ( isInput && element.getAttribute('xtype')=="xbarcode")
					{
						return {
							xbarcode : CKEDITOR.TRISTATE_OFF
						};
					} 
					return null;
				} );
		}
    }
});