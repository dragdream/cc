CKEDITOR.plugins.add('xupload', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xupload', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xupload/plugin.jsp";
	    		bsWindow(url,"附件上传控件",{width:"360",height:"100",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xupload', {
            label: "附件上传控件",
            command: 'xupload',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xupload:{
					label : "附件上传控件",
					command : 'xupload',
					group : 'xupload',
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
					
					if ( isTag && element.getAttribute('xtype')=="xupload")
					{
						return {
							xupload : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});