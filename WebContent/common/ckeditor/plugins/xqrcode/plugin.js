CKEDITOR.plugins.add('xqrcode', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xqrcode', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xqrcode/plugin.jsp";
	    		bsWindow(url,"二维码控件",{width:"550",height:"300",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xqrcode', {
            label: "二维码控件",
            command: 'xqrcode',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xqrcode:{
					label : "二维码控件",
					command : 'xqrcode',
					group : 'xqrcode',
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
					
					if ( isInput && element.getAttribute('xtype')=="xqrcode")
					{
						return {
							xqrcode : CKEDITOR.TRISTATE_OFF
						};
					} 
					return null;
				} );
		}
    }
});