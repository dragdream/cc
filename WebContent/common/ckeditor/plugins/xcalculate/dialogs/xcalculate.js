CKEDITOR.dialog.add('xcalculate', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '计算控件',
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
					},{
						type:'select',
						label:'值类型',
						items:[["整数[整型]","1"],["小数[浮点型]","2"]],
						onChange:function(){
							
						},
						id:'valuetype'
					}]
				},{
					type:'vbox',
					padding:0,
					children:[{
						type:'text',
						label:'小数保留位',
						id:'precision'
					},{
						type:'select',
						label:'数据格式',
						items:[["四舍五入","1"],["结果加一","2"]],
						id:'format'
					}]	
				}]
            },{
				type:'textarea',
				id:'formula',
				label:'公式数据',
				validate : CKEDITOR.dialog.validate.notEmpty("公式不允许为空")
			}]
        }],
        onOk: function(){

			title = this.getValueOf('cb','title');
			precision = this.getValueOf('cb','precision');
			valuetype = this.getValueOf('cb','valuetype');
			format0 = this.getValueOf('cb','format');
			formula = this.getValueOf('cb','formula');
			
			var html = "<img xtype=\"xcalculate\" ";
			if(title){
				html+=" title=\""+title+"\"";
			}
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			var json = "{";
			if(precision){
				json+="'precision':"+precision+",";
			}
			if(valuetype){
				json+="'valuetype':"+valuetype+",";
			}
			if(format0){
				json+="'format':"+format0+",";
			}
			if(formula){
				json+="'formula':'"+formula+"',";
			}
			if(json.length!=1){
				json = json.substring(0,json.length-1);
			}
			json+="}";

			html+=" model=\""+json+"\"/>";
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();

			if(element!=null && element.getAttribute("xtype")=="xcalculate"){
				nameCode = element.getAttribute('name');
				title = this.getContentElement('cb','title');
				precision = this.getContentElement('cb','precision');
				valuetype = this.getContentElement('cb','valuetype');
				format0 = this.getContentElement('cb','format');
				formula = this.getContentElement('cb','formula');

				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}
				var model = element.getAttribute("model");
				model = eval("("+model+")");
				if(model){
					if(model.precision){
						precision.setValue(model.precision);
					}
					if(model.valuetype){
						valuetype.setValue(model.valuetype);
					}
					if(model.format){
						format0.setValue(model.format);
					}
					if(model.formula){
						formula.setValue(model.formula);
					}
				}
			}else{
				nameCode = null;
			}
        }
    };
});
