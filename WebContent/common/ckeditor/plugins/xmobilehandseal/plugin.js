CKEDITOR.plugins.add('xmobilehandseal', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xmobilehandseal', new CKEDITOR.dialogCommand('xmobilehandseal'));
        editor.ui.addButton('xmobilehandseal', {
            label: "移动手写控件",
            command: 'xmobilehandseal',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xmobilehandseal', this.path + 'dialogs/xmobilehandseal.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xmobilehandseal:{
					label : "移动手写控件",
					command : 'xmobilehandseal',
					group : 'xmobilehandseal',
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
					
					if ( tagName && element.getAttribute('xtype')=="xmobilehandseal")
					{
						return {
							xmobilehandseal : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});