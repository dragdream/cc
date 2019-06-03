CKEDITOR.plugins.add('imgupload', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('imgupload', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/imgupload/index.jsp";
//	    		var locX = (screen.width - 320) / 2;
//	    		  var locY = (screen.height - 240) / 2;
//	    		  var attrs = null;
//	    		  
//	    		  attrs = "status:no;directories:no;";
//	    		  attrs += "dialogWidth:" + 320 + "px;";
//	    		  attrs += "dialogHeight:" + 240 + "px;";
//	    		  attrs += "dialogLeft:" + locX + "px;";
//	    		  attrs += "dialogTop:" + locY + "px;";
//	    		  window.showModalDialog(url, editor, attrs);
	    		  window.CK_EDITOR_OBJ = editor;
	    		  var IM_OA;
	    		  try{
	    		      IM_OA = window.external.IM_OA;
	    		  }catch(e){}

	    		  if(window.showModelDialog || IM_OA){
	    		  	  dialogChangesize(url, 760, 400);
	    		    }else{
	    		  	  openWindow(url,"上传图片", 760, 400);
	    		    }
	    		  
//	    		parent.$.jBox.open("iframe:"+url,"图片上传",360,380,{buttons:{"确定":"ok","关闭":true},submit:function(v,h,f){
//	    			if(v=="ok"){
//	    				var cw = $(h).find("iframe:first")[0].contentWindow;
//	    				var attachIds = cw.valuesHolder2.value;
//	    				if(attachIds==""){
//	    					alert("无上传的图片！");
//	    					return false;
//	    				}
//	    				
//	    				var sp = attachIds.split(",");
//	    				for(var i=0;i<sp.length;i++){
//	    					editor.insertHtml("<img src=\"/oaop/attachmentController/downFile.action?id="+sp[i]+"\" />");
//	    				}
//	    			}
//	    		}});
	    	}
	    });
        editor.ui.addButton('imgupload', {
            label: "图片上传",
            command: 'imgupload',
            icon: this.path + 'images/icon.png'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				imgupload:{
					label : "图片上传",
					command : 'imgupload',
					group : 'imgupload',
					order : 5
				}
			});
		}
    }
});