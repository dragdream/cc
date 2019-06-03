CKEDITOR.plugins.add('quickfill', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('quickfill', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/quickfill/commonwords.jsp";
	    		  window.CK_EDITOR_OBJ = editor;
	    		  var IM_OA;
	    		  try{
	    		      IM_OA = window.external.IM_OA;
	    		  }catch(e){}

	    		  if(window.showModelDialog || IM_OA){
	    		  	  dialogChangesize(url, 760, 400);
	    		    }else{
	    		  	  openWindow(url,"快速回填", 760, 400);
	    		    }
	    	}
	    });
        editor.ui.addButton('quickfill', {
            label: "常用语",
            command: 'quickfill',
            icon: this.path + 'images/icon.png'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				quickfill:{
					label : "常用语",
					command : 'quickfill',
					group : 'quickfill',
					order : 5
				}
			});
		}
    }
});