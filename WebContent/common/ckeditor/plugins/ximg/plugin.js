CKEDITOR.plugins.add('ximg', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('ximg', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/ximg/plugin.jsp";
	    		bsWindow(url,"图片上传控件",{width:"360",height:"150",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('ximg', {
            label: "图片上传控件",
            command: 'ximg',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				ximg:{
					label : "图片上传控件",
					command : 'ximg',
					group : 'ximg',
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

					var isTag = element.hasAscendant( 'img', 1 );
					
					if ( isTag && element.getAttribute('xtype')=="ximg")
					{
						return {
							ximg : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});