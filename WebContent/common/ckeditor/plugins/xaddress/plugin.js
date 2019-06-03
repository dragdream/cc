CKEDITOR.plugins.add('xaddress', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xaddress', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xaddress/plugin.jsp";
	    		bsWindow(url,"区域联动控件",{width:"360",height:"150",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xaddress', {
            label: "区域联动控件",
            command: 'xaddress',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xaddress:{
					label : "区域联动控件",
					command : 'xaddress',
					group : 'xaddress',
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

					var isTag = element.hasAscendant( 'select', 1 );
					if ( isTag && element.getAttribute('xtype')=="xaddress")
					{
						return {
							xaddress : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});