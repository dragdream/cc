
//自定义esayui 校验
$.extend($.fn.validatebox.defaults.rules, {  
    //机关名称特殊字符
    organNameCheck : {  
        validator: function(value, param){  
			return isSpecial(value);  
        },  
        message: '请输入正确的机关名称'  
    },  
    //机关编码
	orgCodeCheck : {  
        validator: function(value, param){  
            return isNumberText(value.trim());
        },  
        message: '请输入正确的机关编码'  
    },  
    //法人
	legalRepresentativeCheck: {
        validator: function(value, param){  
			return isSpecial(value);   
        },  
        message: '请输入正确的法人名称'  
    },  
    //编制人数
	compilersNumCheck: {
        validator: function(value, param){  
			return isFloat(value.trim());  
        }, 
        message: '请输入正确的编制人数'  
    },  
    //联系人
	contactsCheck: {
        validator: function(value, param){  
			return isSpecial(value); 
        },  
        message: '请输入正确的联系人'  
    },  
    //联系人电话
	contactsPhoneCheck: { 
        validator: function(value, param){  
			return checkPhone(value);  
        },  
        message: '请输入正确的联系人电话'  
    }
   
});  

