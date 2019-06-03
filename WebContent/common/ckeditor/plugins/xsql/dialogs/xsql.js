CKEDITOR.dialog.add('xsql', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: 'SQL控件',
        width: 340,
        height: 80,
        contents: [{
            id: 'cb',
			padding:0,
            elements: [{
				id:'title',
				label:'名称',
				type:"text"
			},{
				type:'text',
				label:'视图标识ID',
				id:'dfid'
			},{
				type:'select',
				label:'显示类型',
				id:'show',
				'default':'1',
				items : [ [ '单行文本','1'], [ '下拉菜单','2']]
			},{
				type:'textarea',
				label:'样式',
				id:'style'
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var dfid = this.getValueOf("cb","dfid");
			var show = this.getValueOf("cb","show");
			var style = this.getValueOf("cb","style");
			
			var html = "<input xtype=\"xsql\" title=\""+title+"\" dfid=\""+dfid+"\" show=\""+show+"\" style=\""+style+"\" xtype=\"xsql\" value=\"{SQL控件}"+title+"\"";
			
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
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
			
			if(element!=null && element.getAttribute('xtype')=='xsql'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement("cb","title");
				var dfid = this.getContentElement("cb","dfid");
				var show = this.getContentElement("cb","show");
				var style = this.getContentElement("cb","style");
				
				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}
				
				if(element.getAttribute("dfid")){
					dfid.setValue(element.getAttribute("dfid"));
				}
				
				if(element.getAttribute("show")){
					show.setValue(element.getAttribute("show"));
				}
				if(element.getAttribute("style")){
					style.setValue(element.getAttribute("style"));
				}
				
			}else{
				nameCode = null;
			}
			
        }
    };
});
