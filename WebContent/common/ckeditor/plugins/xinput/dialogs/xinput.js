CKEDITOR.dialog.add('xinput', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
	var renderIntType = function(){
		var dom = "<option value='#'>#</option>";
		dom+="<option value='#,###'>#,###</option>";
		return dom;
	}
	var renderFloatType = function(){
		var dom = "<option value='#.#'>#.#</option>";
		dom+="<option value='#.##'>#.##</option>";
		dom+="<option value='#.###'>#.###</option>";
		dom+="<option value='#,###.#'>#,###.#</option>";
		dom+="<option value='#,###.##'>#,###.##</option>";
		dom+="<option value='#,###.###'>#,###.###</option>";
		dom+="<option value='#.0'>#.0</option>";
		dom+="<option value='#.00'>#.00</option>";
		dom+="<option value='#.000'>#.000</option>";
		dom+="<option value='#,###.0'>#,###.0</option>";
		dom+="<option value='#,###.00'>#,###.00</option>";
		dom+="<option value='#,###.000'>#,###.000</option>";
		return dom;
	}
    return {
        title: '单行输入框',
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
                widths : [ null, null],
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
						type:'text',
						label:'宽度',
						id:'rows'
					},{
						type:'select',
						label:'数据类型',
						items:[['字符型[限255以内字符]','1'],['整数[整型]','2'],['小数[浮点型]','3']],
						onChange:function(obj){
							var dialog = this.getDialog();
							format = dialog.getContentElement('cb','format').getInputElement().$;
							format = $(format);
							
							var dom="";
							if(this.getValue()=='1'){
								dom = "";
							}else if(this.getValue()=='2'){
								dom = renderIntType();
							}else if(this.getValue()=='3'){
								dom = renderFloatType();
							}
							format.html(dom);
						},
						id:'datatype'
					}]
				},{
					type:'vbox',
					padding:0,
					children:[{
						type:'text',
						label:'初始值',
						id:'defaultValue'
					},{
						type:'text',
						label:'字符长度',
						id:'maxLength'
					},{
						type:'select',
						label:'格式',
						items:[['无','0']],
						id:'format'
					}]	
				}]
            }]
        }],
        onOk: function(){

			title = this.getValueOf('cb','title');
			rows = this.getValueOf('cb','rows');
			defaultValue = this.getValueOf('cb','defaultValue');
			maxLength = this.getValueOf('cb','maxLength');
			datatype = this.getValueOf('cb','datatype');
			format = this.getValueOf('cb','format');

			var html = "<input type=\"text\" xtype=\"xinput\" ";
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
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
			if(defaultValue){
				html+=" value=\""+defaultValue+"\"";
			}
			if(maxLength){
				html+=" maxlength=\""+maxLength+"\"";
			}

			var json = "{";
			if(datatype){
				json+="'datatype':'"+datatype+"'";
			}
			if(format){
				json+=",'format':'"+format+"'";
			}
			json+="}";
			
			html+=" model=\""+json+"\"/>"
			editor.insertHtml(html);
        },
        onShow: function(){
			
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			
			if(element!=null && element.getAttribute('xtype')=='xinput'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement('cb','title');
				var rows = this.getContentElement('cb','rows');
				var defaultValue = this.getContentElement('cb','defaultValue');
				var maxLength = this.getContentElement('cb','maxLength');
				var datatype = this.getContentElement('cb','datatype');
				var format = this.getContentElement('cb','format');

				if(element.getAttribute('title')){
					title.setValue(element.getAttribute('title'));
				}
				if(element.getAttribute('rows')){
					rows.setValue(element.getAttribute('rows'));
				}
				if(element.getAttribute('value')){
					defaultValue.setValue(element.getAttribute('value'));
				}
				if(element.getAttribute('maxlength')){
					maxLength.setValue(element.getAttribute('maxlength'));
				}
				if(element.getAttribute('model')){
					model = element.getAttribute('model');
					model = eval('('+model+')');
					if(model.datatype){
						var dataType = model.datatype;
						datatype.setValue(dataType);
						
						var _format = format.getInputElement().$;
						_format = $(_format);
						_format.html('');
						
						if(dataType=='1'){
							
						}else if(dataType=='2'){
							_format.html(renderIntType());
						}else if(dataType=='3'){
							_format.html(renderFloatType());
						}
						format.setValue(model.format);
					}
				}
			}else{
				nameCode = null;
			}
			
        }
    };
});
