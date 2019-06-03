var headerstyle = "style='text-align:center;font-weight:bold;'";
var bodystyle = "style='text-align:center;'";

CKEDITOR.dialog.add('xlist', function(editor){
	var nameCode = null;
    var escape = function(value){
        return value;
    };
    return {
        title: '明细表',
        width: 625,
        height: 470,
        contents: [{
            id: 'cb',
			padding:0,
            elements: [{
				id:'title',
				label:'名称',
				type:"text"
			},{
				type:'html',
				html:"<br/><div id='xlist_content'></div><br/><a href='javascript:void()' onclick='xlist_addDataItem()'>添加表项目</a>"
			}]
        }],
        onOk: function(){
			var title = this.getValueOf("cb","title");
			var html = "<img src=\""+(contextPath+"/common/images/workflow/xlist.png")+"\" xtype=\"xlist\" title=\""+title+"\" ";
			
			if(nameCode && nameCode!=null){
				html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
			}else{
				maxItem++;
				html+=" name=\"DATA_"+maxItem+"\" id=\"DATA_"+maxItem+"\" ";
			}
			
			var dataList = $("#xlist_tbody tr");
			var json = "[";
			dataList.each(function(i,obj){
				var data = $(obj).find("td");
				var title = $(data[0]).find("input:first").val();
				var width = $(data[1]).find("input:first").val();
				var sum = $(data[2]).find("input:first").attr("checked")?"1":"0";
				var formula = $(data[3]).find("input:first").val();
				var type = $(data[4]).find("select:first").val();
				var model = $(data[5]).find("input:first").val();
				json+="{'title':'"+title+"','width':'"+width+"','sum':'"+sum+"','formula':'"+formula+"','type':'"+type+"','model':'"+model+"'}";
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
			var xlist_content = $("#xlist_content");
			
			//设置表头
			var html = "<table style='width:100%;'>";
			html+="<thead>";
			html+="<tr>";
			html+="<td "+headerstyle+">表头项名称</td><td "+headerstyle+">宽度</td><td "+headerstyle+">列合计</td><td "+headerstyle+">计算公式</td><td "+headerstyle+">字段类型</td><td "+headerstyle+">值数据</td><td "+headerstyle+">操作</td>";
			html+="</tr>";
			html+="</thead>";

			html+="<tbody id='xlist_tbody'>";
			
			if(element!=null && element.getAttribute('xtype')=='xlist'){
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
						html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' value='"+list[i].title+"'  name=\"title\" ></td>" +
							"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name='width' value=\""+(list[i].width)+"\" /></td>" +
							"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"checkbox\" name=\"sum\" "+(list[i].sum=="1"?"checked":"")+" /></td>" +
							"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"formula\" value=\""+(list[i].formula)+"\" /></td>" +
							"<td "+bodystyle+">"+xlist_renderType(list[i].type)+"</td>" +
							"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"model\"  value=\""+(list[i].model)+"\" /></td>" +
							"<td "+bodystyle+"><a href='javascript:void()' onclick='xlist_deleteDataItem(this)'>删除</a></td>";
						html+="</tr>";
					}
				}
				
			}else{
				nameCode = null;
			}
			html+="</tbody>";
			html+="</table>";
			xlist_content.html(html);
        }
    };
});

function xlist_addDataItem(){
	var xlist_tbody = $("#xlist_tbody");
	var html = "";

	html+="<tr>";
	html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name=\"title\" /></td>" +
			"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' name='width'  /></td>" +
			"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"checkbox\" name=\"sum\"  /></td>" +
			"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"formula\"  /></td>" +
			"<td "+bodystyle+">"+xlist_renderType("")+"</td>" +
			"<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type=\"text\" name=\"model\"  /></td>" +
			"<td "+bodystyle+"><a href='javascript:void()' onclick='xlist_deleteDataItem(this)'>删除</a></td>";
	html+="</tr>";
	
	var child = $(html);

	xlist_tbody.append(child);
}

function xlist_deleteDataItem(obj){
	$(obj).parent().parent().remove();
}

function xlist_renderType(value){
	var html = "<select class='cke_dialog_ui_input_text' type=\"text\" name=\"type\">";
	html+="<option value='1' "+(value=="1"?"selected":"")+">单行输入框</option>";
	html+="<option value='2' "+(value=="2"?"selected":"")+">多行文本框</option>";
	html+="<option value='3' "+(value=="3"?"selected":"")+">下拉菜单</option>";
	html+="<option value='4' "+(value=="4"?"selected":"")+">单选框</option>";
	html+="<option value='5' "+(value=="5"?"selected":"")+">多选框</option>";
	html+="</select>";
	return html;
}