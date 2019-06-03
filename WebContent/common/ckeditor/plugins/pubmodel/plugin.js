CKEDITOR.plugins.add('pubmodel', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('pubmodel', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/pubmodel/index.jsp";
	    		parent.$.jBox.open("post:"+url,"公共模版",360,380,{buttons:{"确定":"ok","关闭":true},submit:function(v,h,f){
	    			if(v=="ok"){
	    				var o;
	    				h.find("div[id=pubmodel_plugin]:first").children().each(function(i,obj){
	    					if($(obj).hasClass("pubmodel_plugin_item_active")){
	    						o = $(obj);
	    					}
	    				});
	    				
	    				if(o){
	    					var json = tools.requestJsonRs(contextPath+"/pubTemplate/getTemplate.action?sid="+o.attr("sid"));
	    					if(json.rtState){
	    						editor.insertHtml(json.rtData.tplContent);
	    					}
	    					return true;
	    				}
	    				alert("请选择一个模板");
	    				return false;
	    			}
	    		}});
	    	}
	    });
        editor.ui.addButton('pubmodel', {
            label: "公共模版",
            command: 'pubmodel',
            icon: this.path + 'images/icon.png'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xfeedback:{
					label : "公共模板",
					command : 'pubmodel',
					group : 'pubmodel',
					order : 5
				}
			});
		}
    }
});