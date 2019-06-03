CKEDITOR.dialog.add('xvoice', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '语音控件',
        width: 340,
        height: 120,
        contents: [{
            id: 'cb',
			padding:0,
            elements: [{
				id:'title',
				label:'名称',
				type:"text"
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var html = "<img xtype=\"xvoice\" title=\""+title+"\" src=\""+(contextPath+"/common/images/workflow/voice.png")+"\"";
			
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			html+="/>";
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			
			if(element!=null && element.getAttribute('xtype')=='xvoice'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement("cb","title");
				
				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}

			}else{
				nameCode = null;
			}
			
        }
    };
});
