var headerstyle = "style='text-align:center;font-weight:bold;'";
var bodystyle = "style='text-align:center;'";

CKEDITOR.dialog.add('xselect', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '下拉列表控件',
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
				type:'html',
				html:"<br/><div id='xselect_content'></div><br/><a href='javascript:void()' onclick='xselect_addDataItem()'>添加数据项</a>"
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var html = "<select  xtype=\"xselect\" title=\""+title+"\" ";
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			html+=">";
			
			var dataList = $("#xselect_tbody tr");
			dataList.each(function(i,obj){
				var data = $(obj).find("td");
				var value = $(data[0]).find("input:first").val();
				var checked = $(data[1]).find("input:first")[0].checked;
				html+="<option value=\""+value+"\" "+(checked?"selected":"")+">"+value+"</option>";
			});

			html+="</select>";
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			var xselect_content = $("#xselect_content");
			
			//设置表头
			var html = "<table style='width:100%;'>";
			html+="<thead>";
			html+="<tr>";
			html+="<td "+headerstyle+">单选项</td><td "+headerstyle+">默认选中</td><td "+headerstyle+">操作</td>";
			html+="</tr>";
			html+="</thead>";

			html+="<tbody id='xselect_tbody'>";
			
			if(element!=null && element.getAttribute('xtype')=='xselect'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement("cb","title");
				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}
				
				var opts = $(element.getHtml());
				opts.each(function(i,obj){
					html+="<tr>";
					html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' value='"+obj.value+"'></td><td "+bodystyle+"><input type='radio' name='default' "+(obj.selected?"checked":"")+"></td><td "+bodystyle+"><a href='javascript:void()' onclick='xselect_deleteDataItem(this)'>删除</a></td>";
					html+="</tr>";
				});
			}else{
				nameCode = null;
			}
			html+="</tbody>";
			html+="</table>";
			xselect_content.html(html);
        }
    };
});

function xselect_addDataItem(){
	var xselect_tbody = $("#xselect_tbody");
	var html = "";

	html+="<tr>";
	html+="<td "+headerstyle+"><input type='text' class='cke_dialog_ui_input_text'/></td>";
	html+="<td "+headerstyle+"><input type='radio' name='default'/></td>";
	html+="<td "+headerstyle+"><a href='javascript:void()' onclick='xselect_deleteDataItem(this)'>删除</a></td>";
	html+="</tr>";

	var child = $(html);
	
	xselect_tbody.append(child);
}

function xselect_deleteDataItem(obj){
	$(obj).parent().parent().remove();
}