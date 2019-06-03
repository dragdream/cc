CKEDITOR.plugins.add('xsql', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xsql', new CKEDITOR.dialogCommand('xsql'));
        editor.ui.addButton('xsql', {
            label: "SQL控件",
            command: 'xsql',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xsql', this.path + 'dialogs/xsql.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xsql:{
					label : "SQL控件",
					command : 'xsql',
					group : 'xsql',
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

					var tagName = element.hasAscendant( 'input', 1 );
					
					if ( tagName && element.getAttribute('xtype')=="xsql")
					{
						return {
							xsql : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});