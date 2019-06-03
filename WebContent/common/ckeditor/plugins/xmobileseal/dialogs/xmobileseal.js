CKEDITOR.dialog.add('xmobileseal', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '移动签章控件',
        width: 340,
        height: 210,
        contents: [{
            id: 'cb',
			padding:0,
            elements: [{
				id:'title',
				label:'名称',
				type:"text"
			},{
				type:'textarea',
				label:'校验字段',
				id:'validField'
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var validField = this.getValueOf("cb","validField");
			var html = "<img xtype=\"xmobileseal\" title=\""+title+"\" src=\""+(contextPath+"/common/images/workflow/seal.png")+"\"";
			
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			var json = "{'validField':'"+validField+"'}";
			
			html+="model=\""+json+"\"";
			html+="/>";
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			
			if(element!=null && element.getAttribute('xtype')=='xmobileseal'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement("cb","title");
				var validField = this.getContentElement("cb","validField");
				
				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}

				var model = element.getAttribute("model");//获取元素model属性
				if(model && model!=""){
					var m = eval("("+model+")");
					validField.setValue(m.validField);
				}
				
			}else{
				nameCode = null;
			}
			
        }
    };
});
