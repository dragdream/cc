CKEDITOR.plugins.add('xmobileseal', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xmobileseal', new CKEDITOR.dialogCommand('xmobileseal'));
        editor.ui.addButton('xmobileseal', {
            label: "移动签章控件",
            command: 'xmobileseal',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xmobileseal', this.path + 'dialogs/xmobileseal.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xmobileseal:{
					label : "移动签章控件",
					command : 'xmobileseal',
					group : 'xmobileseal',
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
					
					if ( tagName && element.getAttribute('xtype')=="xmobileseal")
					{
						return {
							xmobileseal : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});