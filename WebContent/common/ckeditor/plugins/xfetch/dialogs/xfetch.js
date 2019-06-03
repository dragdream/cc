CKEDITOR.dialog.add('xfetch', function(editor){
	var nameCode = null;
	var renderDatetime = function(){
		var dom = "";
		dom+="<option value='yyyy-MM-dd'>yyyy-MM-dd</option>";
		dom+="<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>";
		dom+="<option value='yyyy-MM'>yyyy-MM</option>";
		dom+="<option value='yy-MM-dd'>yy-MM-dd</option>";
		dom+="<option value='yyyyMMdd'>yyyyMMdd</option>";
		dom+="<option value='MM-dd yyyy'>MM-dd yyyy</option>";
		dom+="<option value='yyyy年MM月'>yyyy年MM月</option>";
		dom+="<option value='yyyy年MM月dd日'>yyyy年MM月dd日</option>";
		dom+="<option value='yyyy年MM月dd日 HH:mm'>yyyy年MM月dd日 HH:mm</option>";
		dom+="<option value='MM月dd日'>MM月dd日</option>";
		dom+="<option value='yyyy.MM'>yyyy.MM</option>";
		dom+="<option value='yyyy.MM.dd'>yyyy.MM.dd</option>";
		dom+="<option value='MM.dd'>MM.dd</option>";
		return dom;
	}
	var renderTime = function(){
		var dom = "";
		dom+="<option value='HH时'>HH时</option>";
		dom+="<option value='HH时mm分'>HH时mm分</option>";
		dom+="<option value='HH时mm分ss秒'>HH时mm分ss秒</option>";
		dom+="<option value='HH:mm'>HH:mm</option>";
		dom+="<option value='HH:mm:ss'>HH:mm:ss</option>";
		return dom;
	}
    var escape = function(value){
        return value;
    };
    return {
        title: '选择器控件',
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
				label:'控件类型',
				id:'type',
				onChange:function(){
					var dialog = this.getDialog();
					var format0 = dialog.getContentElement('cb','format').getInputElement().$;
					var _format0 = $(format0);
					if(this.getValue()=="1"){
						dialog.getContentElement('cb','format').enable();
						_format0.html(renderDatetime());
					}else if(this.getValue()=="2"){
						dialog.getContentElement('cb','format').enable();
						_format0.html(renderTime());
					}else{
						_format0.html("");
						dialog.getContentElement('cb','format').disable();
					}
				},
				items:[["日期选择器","1"],["时间选择器","2"],["部门选择器","3"],["部门选择器[多部门]","4"],["角色选择器","5"],["角色选择器[多角色]","6"],["人员选择器","7"],["人员选择器[多人员]","8"],["附件上传","9"]],
				required : true
            },{
                type:'select',
				label:'显示格式',
				id:'format',
				items:[],
				required : true
            },{
                type:'select',
				label:'默认值',
				id:'def',
				items:[["默认值","1"],["置空","2"]],
				required : true
            }]
        }],
        onOk: function(){
			var title = this.getValueOf('cb','title');
			var type = this.getValueOf('cb','type');
			var format = this.getValueOf('cb','format');
			var def = this.getValueOf('cb','def');
			
			var imgName = "";
			if(type=="1" || type=="2"){//时间
				imgName = "xfetch_time";
			}else if(type=="3" || type=="4"){//部门
				imgName = "xfetch_dept";
			}else if(type=="5" || type=="6"){//角色
				imgName = "xfetch_role";
			}else if(type=="7" || type=="8"){//人员
				imgName = "xfetch_person";
			}
			
			var html = "<img src=\""+(contextPath+"/common/images/workflow/"+imgName+".png")+"\" title=\""+title+"\" xtype=\"xfetch\" ";
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			var json = "{";
			if(type){
				json+="'type':"+type+",";
			}
			if(format){
				json+="'format':'"+format+"',";
			}
			if(def){
				json+="'def':"+def+",";
			}
			if(json.length!=1){
				json = json.substring(0,json.length-1);
			}
			json+="}";

			html+="model=\""+json+"\" />";
			editor.insertHtml(html);
        },
        onShow: function(){
			
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			

			var title = this.getContentElement('cb','title');
			var type = this.getContentElement('cb','type');
			var format = this.getContentElement('cb','format');
			var def = this.getContentElement('cb','def');

			if(element!=null && element.getAttribute("xtype")=="xfetch"){
				nameCode = element.getAttribute('name');
				if(element.getAttribute('title')){
					title.setValue(element.getAttribute('title'));
				}

				var model = element.getAttribute('model');
				model = eval("("+model+")");
				if(model){
					if(model.type){
						var _format = $(format.getInputElement().$);
						if(model.type==1){
							type.setValue('1');
							_format.html(renderDatetime());
						}else if(model.type==2){
							type.setValue('2');
							_format.html(renderTime());
						}else{
							type.setValue(model.type);
						}
					}
					if(model.format){
						format.setValue(model.format);
					}
					if(model.def){
						def.setValue(model.def);
					}
				}
			}else{
				var _format = $(format.getInputElement().$);
				_format.html(renderDatetime());
				nameCode = null;
			}
			
        }
    };
});
