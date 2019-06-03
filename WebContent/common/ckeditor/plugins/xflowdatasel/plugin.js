CKEDITOR.plugins.add('xflowdatasel', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xflowdatasel', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xflowdatasel/plugin.jsp";
	    		bsWindow(url,"流程数据选择控件",{width:"500",height:"300",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xflowdatasel', {
            label: "流程数据选择控件",
            command: 'xflowdatasel',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xflowdatasel:{
					label : "流程数据选择控件",
					command : 'xflowdatasel',
					group : 'xflowdatasel',
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
					
					if ( isInput && element.getAttribute('xtype')=="xflowdatasel")
					{
						return {
							xflowdatasel : CKEDITOR.TRISTATE_OFF
						};
					} 
					return null;
				} );
		}
    }
});