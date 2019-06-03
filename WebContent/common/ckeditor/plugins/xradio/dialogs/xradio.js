var headerstyle = "style='text-align:center;font-weight:bold;'";
var bodystyle = "style='text-align:center;'";

CKEDITOR.dialog.add('xradio', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '单项选择控件',
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
				html:"<br/><div id='xradio_content'></div><br/><a href='javascript:void()' onclick='xradio_addDataItem()'>添加数据项</a>"
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var html = "<input type=\"radio\" xtype=\"xradio\" title=\""+title+"\" ";
			
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			var dataList = $("#xradio_tbody tr");
			var json = "[";
			dataList.each(function(i,obj){
				var data = $(obj).find("td");
				json+="{'data':'"+$(data[0]).find("input:first").val()+"','default':"+($(data[1]).find("input:first")[0].checked?"true":"false")+"}";
				if(i!=dataList.length-1){
					json+=",";
				}
			});
			
			json+="]";
			
			html+="model=\""+json+"\"";
			html+="/>";
			editor.insertHtml(html);
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
			var xradio_content = $("#xradio_content");
			
			//设置表头
			var html = "<table style='width:100%;'>";
			html+="<thead>";
			html+="<tr>";
			html+="<td "+headerstyle+">单选项</td><td "+headerstyle+">默认选中</td><td "+headerstyle+">操作</td>";
			html+="</tr>";
			html+="</thead>";

			html+="<tbody id='xradio_tbody'>";
			
			if(element!=null && element.getAttribute('xtype')=='xradio'){
				nameCode = element.getAttribute('name');
				var title = this.getContentElement("cb","title");
				if(element.getAttribute("title")){
					title.setValue(element.getAttribute("title"));
				}

				var model = element.getAttribute("model");//获取元素model属性
				if(model && model!=""){
					var list = eval("("+model+")");
					for(var i=0;i<list.length;i++){
						html+="<tr>";
						html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' value='"+list[i].data+"'></td><td "+bodystyle+"><input type='radio' name='default' "+(list[i]['default']?"checked":"")+"></td><td "+bodystyle+"><a href='javascript:void()' onclick='xradio_deleteDataItem(this)'>删除</a></td>";
						html+="</tr>";
					}
				}
				
			}else{
				nameCode = null;
			}
			html+="</tbody>";
			html+="</table>";
			xradio_content.html(html);
        }
    };
});

function xradio_addDataItem(){
	var xradio_tbody = $("#xradio_tbody");
	var html = "";

	html+="<tr>";
	html+="<td "+headerstyle+"><input type='text' class='cke_dialog_ui_input_text'/></td>";
	html+="<td "+headerstyle+"><input type='radio' name='default'/></td>";
	html+="<td "+headerstyle+"><a href='javascript:void()' onclick='xradio_deleteDataItem(this)'>删除</a></td>";
	html+="</tr>";
	
	var child = $(html);

	xradio_tbody.append(child);
}

function xradio_deleteDataItem(obj){
	$(obj).parent().parent().remove();
}