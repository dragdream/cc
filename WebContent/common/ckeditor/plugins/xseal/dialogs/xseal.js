CKEDITOR.dialog.add('xseal', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '签章控件',
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
			},{
				type:'html',
				html:"签章类型<br/><div id=\"sealType\"><input type=\"checkbox\" value=\"1\"/>盖章<input type=\"checkbox\" value=\"2\"/>手写</div>"
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var validField = this.getValueOf("cb","validField");
			var html = "<img xtype=\"xseal\" title=\""+title+"\" src=\""+(contextPath+"/common/images/workflow/seal.png")+"\"";
			
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			var dataList = $("#sealType input[type=checkbox]");
			var priv = 0;
			dataList.each(function(i,obj){
				if(obj.checked){
					priv+=parseInt(obj.value);
				}
			});
			var json = "{'validField':'"+validField+"','sealType':'"+priv+"'}";
			
			html+="model=\""+json+"\"";
			html+="/>";
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			
			if(element!=null && element.getAttribute('xtype')=='xseal'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement("cb","title");
				var validField = this.getContentElement("cb","validField");
				var sealType = this.getContentElement("cb","sealType");
				
				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}

				var model = element.getAttribute("model");//获取元素model属性
				if(model && model!=""){
					var m = eval("("+model+")");
					validField.setValue(m.validField);
					var priv = parseInt(m.sealType);
					var dataList = $("#sealType input[type=checkbox]");
					dataList.each(function(i,obj){
						var v = parseInt(obj.value);
						if((priv & v)==v){
							obj.checked = "checked";
						}
					});
				}
				
			}else{
				nameCode = null;
			}
			
        }
    };
});
