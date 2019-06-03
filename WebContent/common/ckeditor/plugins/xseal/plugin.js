CKEDITOR.plugins.add('xseal', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xseal', new CKEDITOR.dialogCommand('xseal'));
        editor.ui.addButton('xseal', {
            label: "签章控件",
            command: 'xseal',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xseal', this.path + 'dialogs/xseal.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xseal:{
					label : "签章控件",
					command : 'xseal',
					group : 'xseal',
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

					var tagName = element.hasAscendant( 'img', 1 );
					
					if ( tagName && element.getAttribute('xtype')=="xseal")
					{
						return {
							xseal : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});