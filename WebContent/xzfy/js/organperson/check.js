
//自定义esayui 校验
$.extend($.fn.validatebox.defaults.rules, {  
    //姓名特殊字符
    personNameCheck : {  
        validator: function(value, param){  
			return isSpecial(value);  
        },  
        message: '请输入正确的姓名'  
    },  
    //身份证验证
	idCardCheck : {  
        validator: function(value, param){  
            return isIDCard(value.trim());
        },  
        message: '请输入正确的机关编码'  
    },  
    //联系人电话
	phoneCheck: { 
        validator: function(value, param){  
			return checkPhone(value);  
        },  
        message: '请输入正确的联系人电话'  
    }
});  

