CKEDITOR.plugins.add('xcheckbox', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xcheckbox', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xcheckbox/plugin.jsp";
	    		bsWindow(url,"复选框控件",{width:"360",height:"100",submit:function(v,h){
	    			var cw = h[0].contentWindow;
	    			if(cw.validate()){
    					editor.insertHtml(cw.toDomStr());
    					return true;
    				}
    				return false;
	    		}});
	    	}
	    });
        editor.ui.addButton('xcheckbox', {
            label: "复选框控件",
            command: 'xcheckbox',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xcheckbox', this.path + 'dialogs/xcheckbox.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xcheckbox:{
					label : "复选框控件属性",
					command : 'xcheckbox',
					group : 'xcheckbox',
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
					
					if ( isInput && element.getAttribute('xtype')=="xcheckbox" && element.getAttribute('type')=="checkbox")
					{
						return {
							xcheckbox : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});