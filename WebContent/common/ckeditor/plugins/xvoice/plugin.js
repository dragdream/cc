CKEDITOR.plugins.add('xvoice', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xvoice', new CKEDITOR.dialogCommand('xvoice'));
        editor.ui.addButton('xvoice', {
            label: "语音控件",
            command: 'xvoice',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xvoice', this.path + 'dialogs/xvoice.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xvoice:{
					label : "语音控件",
					command : 'xvoice',
					group : 'xvoice',
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
					
					if ( tagName && element.getAttribute('xtype')=="xvoice")
					{
						return {
							xvoice : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});