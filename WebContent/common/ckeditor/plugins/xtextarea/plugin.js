CKEDITOR.plugins.add('xtextarea', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xtextarea', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xtextarea/plugin.jsp";
	    		bsWindow(url,"多行文本框",{width:"360",height:"340",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xtextarea', {
            label: "多行文本框",
            command: 'xtextarea',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xtextarea', this.path + 'dialogs/xtextarea.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xtextarea:{
					label : "多行文本框属性",
					command : 'xtextarea',
					group : 'xtextarea',
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

					var tagName = element.hasAscendant( 'textarea', 1 );
					
					if ( tagName && element.getAttribute('xtype')=="xtextarea")
					{
						return {
							xtextarea : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});