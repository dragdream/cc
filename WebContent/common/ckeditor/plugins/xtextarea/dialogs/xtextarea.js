CKEDITOR.dialog.add('xtextarea', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '多行文本框',
        resizable: CKEDITOR.DIALOG_RESIZE_BOTH,
        minWidth: 340,
        minHeight: 210,
        contents: [{
            id: 'cb',
            name: 'cb',
            label: 'cb',
            title: 'cb',
            elements: [{
                type: 'hbox',
                widths : [ null, null ],
				styles : [ 'vertical-align:top' ],
                children :[{
					type:'vbox',
					padding:0,
					children:[{
						type:'text',
						label:'名称',
						id:'title',
						required : true,
						validate : CKEDITOR.dialog.validate.notEmpty("名称不允许为空")
					}]
				},{
					type:'vbox',
					padding:0,
					children:[{
						type:'text',
						label:'高度',
						id:'cols'
					},{
						type:'text',
						label:'宽度',
						id:'rows'
					}]	
				}]
            },{
					type: 'hbox',
					widths : [ null, null ],
					styles : [ 'vertical-align:top' ],
					children :[{
						type:'textarea',
						label:'初始值',
						id:'defaultValue'
					}]
				}
			]
        }],
        onOk: function(){

			title = this.getValueOf('cb','title');
			rows = this.getValueOf('cb','rows');
			defaultValue = this.getValueOf('cb','defaultValue');
			cols = this.getValueOf('cb','cols');
			
			var html = "<textarea xtype=\"xtextarea\" ";
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			if(title){
				html+=" title=\""+title+"\"";
			}
			if(rows){
				html+=" rows=\""+rows+"\"";
			}
			if(cols){
				html+=" cols=\""+cols+"\"";
			}
			
			html+=">"

			if(defaultValue){
				html+=defaultValue;
			}

			html+="</textarea>"
			try{
				editor.insertHtml(html);
			}catch(e){
				
			}
        },
        onShow: function(){
        	var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			
			if(element!=null && element.getAttribute('xtype')=='xtextarea'){
				nameCode = element.getAttribute('name');
				title = this.getContentElement('cb','title');
				rows = this.getContentElement('cb','rows');
				defaultValue = this.getContentElement('cb','defaultValue');
				cols = this.getContentElement('cb','cols');

				if(element.getAttribute('title')){
					title.setValue(element.getAttribute('title'));
				}
				if(element.getAttribute('rows')){
					rows.setValue(element.getAttribute('rows'));
				}
				if(element.getAttribute('value')){
					defaultValue.setValue(element.getAttribute('value'));
				}
				if(element.getAttribute('cols')){
					cols.setValue(element.getAttribute('cols'));
				}
			}else{
				nameCode = null;
			}
        }
    };
});
