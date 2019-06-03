CKEDITOR.plugins.add('xh5hw', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xh5hw', new CKEDITOR.dialogCommand('xh5hw'));
        editor.ui.addButton('xh5hw', {
            label: "H5手写签批控件",
            command: 'xh5hw',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xh5hw', this.path + 'dialogs/xh5hw.js');
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xh5hw:{
					label : "H5手写签批控件",
					command : 'xh5hw',
					group : 'xh5hw',
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
					
					if ( tagName && element.getAttribute('xtype')=="xh5hw")
					{
						return {
							xh5hw : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});