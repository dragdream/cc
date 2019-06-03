CKEDITOR.dialog.add('xcheckbox', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '复选框控件',
        resizable: CKEDITOR.DIALOG_RESIZE_BOTH,
        minWidth: 340,
        minHeight: 210,
        contents: [{
            id: 'cb',
            name: 'cb',
            label: 'cb',
            title: 'cb',
            elements: [{
                type:'text',
				label:'名称',
				id:'title',
				required : true,
				validate : CKEDITOR.dialog.validate.notEmpty("名称不允许为空")
            },{
				type:'select',
				label:'默认值',
				id:'def',
				items:[["选中","checked"],["不选中",""]]
			}]
        }],
        onOk: function(){

			title = this.getValueOf('cb','title');
			def = this.getValueOf('cb','def');

			var html = "<input type=\"checkbox\" xtype=\"xcheckbox\" ";
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			if(title){
				html+=" title=\""+title+"\"";
			}
			if(def){
				html+=def;
			}
			
			html+="/>"
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			
			if(element!=null && element.getAttribute('xtype')=="xcheckbox"){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement('cb','title');
				var def = this.getContentElement('cb','def');
				if(element.getAttribute('title')){
					title.setValue(element.getAttribute('title'));
				}
				
				if(element.getAttribute('checked')){
					def.setValue('checked');
				}else{
					def.setValue('');
				}
			}else{
				nameCode = null;
			}
        }
    };
});
