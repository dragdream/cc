function doInit(){
	/*var caseId = getQueryString("caseId");
	caseId = "10be0f867b10407e90009295889ac860";
	//请求后台获取页面顶部案件编号、案件状态、每个阶段倒计时
	var json = tools.requestJsonRs("//.action", {caseId: caseId});
	if(json.rtState){
		
	}else{
		
	}*/
}
//特殊字符校验
var special = "[`~!@#$%^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]";

//校验电话号码
var z_reg = /^13[0-9]{9}|15[012356789][0-9]{8}|18[0-9]{9}|(14[57][0-9]{8})|(17[015678][0-9]{8})$/;  

// 获取请求url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
       return unescape(r[2]);
    }else{
       return null;
    } 
}

//校验特殊字符
function isSpecial(value){
	var pattern = new RegExp(special);
	var result = pattern.test(value.trim()); 
	if(result){
		return false;
	}
	else{
		return true;
	}
}

//判断是否为空字符串
function isBlank(str){    
    if(str == null || typeof str == "undefined" ||     
            str == "" || str.trim() == ""){    
        return true;    
    }    
    return false;    
}; 
 
//验证电话号码
function checkPhone(phone) {  
    var res = z_reg.test(phone);  
	if(res){
		return true;
	}
	else{
		return false;
	}
};  


//检查是否数组组成的字符串
function isNumberText(checkValue){  
    var z_reg = /^([0-9]+)$/;  
    return z_reg.test($.trim(checkValue));  
};

//可以判断是否为数字、金额、浮点数 
function isFloat(checkValue){  
	//.是特殊字符，需要转义 
    var z_reg = /^((([0-9])|([1-9][0-9]+))(\.([0-9]+))?)$/; 
    return z_reg.test($.trim(checkValue));  
};

//验证身份证
function isIDCard(card){
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if(reg.test(card) === false)  
	{  
		return false;  
	}
	else{
		return true;
	}
}

//特殊字符过滤
function validateValue(textbox) {
	var textboxvalue = textbox.value;
	var index = textboxvalue.length - 1;
	var s = textbox.value.charAt(index);
	if (special.indexOf(s) >= 0) {
		s = textboxvalue.substring(0, index);
		textbox.value = s;
	}
}


//创建下拉单选框HTML
function createSelectHtml(json,code){
	var html = "<option value=''>--请选择--</option>";
	if( json.rtState == true ){
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			html = html + "<option value='"+list[i].code+"'";
			if(list[i].code == code){
				html = html + " selected='selected'";
			}
			html = html + ">"+list[i].codeDesc+"</option>";
		}
	}
	return html;
}