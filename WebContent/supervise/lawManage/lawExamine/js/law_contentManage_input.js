
var isEdit = false;
function doInit(){
    
	if(id!=0){//编辑
		var json = tools.requestJsonRs("/detailTempController/get.action", {id:id});
 		bindJsonObj2Easyui(json.rtData , "form1");
	}
	
	initTextbox("detailSeries", '请输入编');
	initTextbox("detailChapter", '请输入章');
	initTextbox("detailSection", '请输入节');
	initTextbox("detailStrip", '请输入条');
	initTextbox("detailFund", '请输入款');
	initTextbox("detailItem", '请输入项');
	initTextbox("detailCatalog", '请输入目');
	$("#content").textbox({
	    validType:'length[1,4000]',
	    multiline:true,
	    novalidate:true,
	    required:true,
	    missingMessage:'请输入内容'
	});

	$('.easyui-textbox').textbox({
        onChange: function(){
            isEdit = true;
        }
    });
    $('.easyui-combobox').combobox({
        onChange: function(){
            isEdit = true;
            
        }
    });
    $('.easyui-radiobutton').radiobutton({
        onChange: function(){
            isEdit = true;
        }
    });
}

function initTextbox(elementId, msg){
    $("#" + elementId).textbox({
        validType:'length[1,5]',
        novalidate:true,
        required:true,
        missingMessage: msg,
        panelHeight:'auto'
    });
}
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		//章
		var text = /^\d*$/;
		var flag = text.test(param.detailChapter);
		if(!flag){
			$.MsgBox.Alert_auto("章输入不正确！"); 
			return false;
		}
		//条
//		var text = /^\d*$/;
		var flag = text.test(param.detailStrip);
		if(!flag){
			$.MsgBox.Alert_auto("条输入不正确！");
			return false;
		}
		//款
//		var text = /^\d*$/;
		var flag = text.test(param.detailFund);
		if(!flag){
			$.MsgBox.Alert_auto("款输入不正确！"); 
			return false;
		}
		//项
//		var text = /^\d*$/;
		var flag = text.test(param.detailItem);
		if(!flag){
			$.MsgBox.Alert_auto("项输入不正确！"); 
			return false;
		}
		//目
//		var text = /^\d*$/;
		var flag = text.test(param.detailCatalog);
		if(!flag){
			$.MsgBox.Alert_auto("目输入不正确！"); 
			return false;
		}
		param.isDelete = 0;
		param.lawId = lawId;
		param.id = id;
		debugger;
		if(id!=0){//编辑
    		var json = tools.requestJsonRs("/detailTempController/update.action",param);
		    if(json.rtState){
		        $.MsgBox.Alert_auto("保存成功！");
		        return true;
		    }else{
		        $.MsgBox.Alert_auto("保存失败！");
                return false;
		    }
		}else{//新增
			var json = tools.requestJsonRs("/detailTempController/save.action",param);
			if(json.rtState){
                $.MsgBox.Alert_auto("保存成功！");
                return true;
            }else{
                $.MsgBox.Alert_auto("保存失败！");
                return false;
            }
		}
			
	}
}