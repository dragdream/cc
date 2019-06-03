/* ========================================================= 
 * 
 * 在原作者的基础修改支持 bootstrap3
 *
 * check-type=
 *   required 不能为空，并在后面自动加*号
 *   url  表示 输入网址
 *   date 日期格式 xxxx-xx-xx
 *   mail 邮箱
 *   number 数字，可以整型，浮点型。
 *   char 
 *   chinese 中文
 * mail-message="扩展提示内容" ， 可以扩展data-message,url-message  
 * minlength="6" 表示长度大于等于6
 * range="2.1~3"   表示值在[2.1~3]之间，并check-type="number"
 * range="2.1,2,4,5"   表示值在只能填现数字，并check-type="number" 
 *
 *
 * 例如:
 * $("form").validation(function(obj,params){
 *     if (obj.id=='mail'){
 *       $.post("/verifymail",{mail :$(obj).val()},function(data){
 *         params.err = !data.success;
 *         params.msg = data.msg;
 *       });
 *     }},
 *     {reqmark:false}
 *   );
 *
 *
 *  编号   版本号      作者     修改日期        修改内容
 *   1    1.0.0     mrlong    2013-10-2      创建文件
 ×   2    1.0.1     mrlong    2013-10-5      callback显示提示的信息。
 *   3.   1.0.2     mrlong    2013-10-7     增加基本表单与内联表单样式。
 *   4.   1.0.3     mrlong    2013-11-04     修改支持IE8，不能Array.indexOf() 改为 $.inArray()
 *
 *
/* =========================================================
 * bootstrap-validation.js 
 * Original Idea: http:/www.newkou.org (Copyright 2012 Stefan Petre)
 * Updated by 不会飞的羊 (https://github.com/FateSheep/Validation-for-Bootstrap)
 * =========================================================
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================= */
!function($) {
    $.fn.validation = function(callback,options) {

        if ( !this.length ) {
            if ( options && options.debug && window.console ) {
                console.warn( "Nothing selected, can't validate, returning nothing." );
            }
            return;
        }

        return this.each(function() {
            globalOptions = $.extend({}, $.fn.validation.defaults, options);
            globalOptions.callback = callback;
            // Add novalidate tag if HTML5.
            $(this).attr( "novalidate", "novalidate" );
            fform_style = isformstyle(this);
            validationForm(this)
        });
    };

    $.fn.valid=function(options){
        if (formState) { // 重复提交则返回
            return false;
        }
        formState = true;
        var validationError = false; 
        //取出验证的
        $('input, textarea', this).each(function () {
            var el = $(this), 
                controlGroup = el.parents('.form-group'),
                //check-type="required chinese"  //支持多个，以空格隔开。
                valid = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' '); 
            if (!controlGroup.hasClass('has-success') && valid != null && valid.length > 0) {
                if (!validateField(this, valid)) {
                    if (wFocus == false) {
                        scrollTo(0, el[0].offsetTop - 50);
                        wFocus = true;
                    }
                    validationError = true;
                }
            }
        });

        wFocus = false;
        formState = false;
        return !validationError;        
    }

   $.fn.validation.defaults = {
        validRules : [
            {name: 'required', validate: function(value) {return ($.trim(value) == '');}, defaultMsg: '请输入内容。'},
            //{name: 'number', validate: function(value) {return (!/^[0-9]\d*$/.test(value));}, defaultMsg: '请输入数字。'},
            {name: 'number', validate: function(value) {return (!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value));}, defaultMsg: '请输入数字。'},
            //{name: 'mail', validate: function(value) {return (!/^[a-zA-Z0-9]{1}([\._a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+){1,3}$/.test(value));}, defaultMsg: '请输入邮箱地址。'},
            {name: 'mail', validate: function(value) {return (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value));}, defaultMsg: '请输入邮箱地址。'},
            {name: 'char', validate: function(value) {return (!/^[a-z\_\-A-Z]*$/.test(value));}, defaultMsg: '请输入英文字符。'},
            {name: 'chinese', validate: function(value) {return (!/^[\u4e00-\u9fff]$/.test(value));}, defaultMsg: '请输入汉字。'},
            {name: 'url',validate:function(value){return(!/^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value))},defaultMsg:'请输入网址'},
            {name: 'date',validate:function(value){return(/Invalid|NaN/.test(new Date(value).toString()));},defaultMsg:"日期格式XXXX-XX-XX。"}
        
           , {name: 'integeZero',
        	   	validate:function(value){
        	   		if(value != null && value != ''){
        	   			return  !regexEnum.integeZero.test(value); //整数
        	   		}
        		   return  false; //整数
        	    }
           		,defaultMsg:"请输入整数类型。"}
            ],
        reqmark:true,
        callback:null  //function(obj,params){};           
    };

    var formState = false, 
        fieldState = false, 
        wFocus = false, 
        fform_style=0,    //0=表示基本表单 1=表示内联表单 2=水平排列的表单
        globalOptions = {};

    function isformstyle(form){
        if($(form).hasClass('form-inline')){
            return  1; 
        }
        else if($(form).hasClass('form-horizontal')){
            return  2;
        }
        else{
            return  0;
        };  
    };

    //验证字段
    var validateField = function(field, valid) { 
        var el = $(field), error = false, errorMsg = '';
        var minlength=(el.attr('minlength')?el.attr('minlength'):null);
        var range=(el.attr('range')?el.attr('range'):null); //
        var msg;
        for (i = 0; i < valid.length; i++) {
            var x = true, 
                flag = valid[i];
            msg = (el.attr(flag + '-message')==undefined)?null:el.attr(flag + '-message');
                
            if (flag.substr(0, 1) == '!') {
                x = false;
                flag = flag.substr(1, flag.length - 1);
            }

            var rules = globalOptions.validRules;
            for (j = 0; j < rules.length; j++) {
                var rule = rules[j];
                if (flag == rule.name) {
                    var value;
                    if (el.attr('type')=='checkbox'){
                        value = el.is(":checked")?'true':'';
                    }
                    else{
                        value=el.val();
                    };
                    if (rule.validate.call(field, value) == x) {
                        error = true;
                        if (el.attr('type').toLowerCase()=='file'){
                            errorMsg = (msg == null)?'请选择文件。':msg;
                        }
                        else{
                            errorMsg = (msg == null)?rule.defaultMsg:msg;
                        }
                        break;
                    }
                }
            }
            if (error) {break;}
        }

        //验证长度
        if ( minlength && !error){
            error = el.val().length < minlength;
            if (error && (msg==null || errorMsg=='')){
                errorMsg = '输入长度大于等于' + minlength;
            }       
        };

        //值区间
        if ($.inArray('number',valid)>=0 && range && !error){
            var values = range.split("~");
            
            if(values.length==2){ 
                error = parseFloat(el.val())<parseFloat(values[0]) || parseFloat(el.val())>parseFloat(values[1]);
                if (error && (msg==null || errorMsg=='')){
                    errorMsg = '输入值在［' + values[0] + '~' + values[1] + ']之间。';
                }       
            }
            else{
                var values = range.split(",");
                if (values.length>0){
                    //error =  values.indexOf(el.val())<0;
                    error = $.inArray(el.val(),values)<0;
                    if (error && (msg==null || errorMsg=='')){
                        errorMsg = '输入值为' +range +'的其中一个。';
                    }
                }
            }
        };

        //外部验证回调方法
        if (!error && globalOptions.callback){
            var params={
                msg:'',
                err:error
            };
            var b = $.ajaxSettings.async;
            $.ajaxSetup({async : false});
            globalOptions.callback(field,params); 
            error = params.err;   
            if (error && (msg==null || errorMsg=='')){
                errorMsg = params.msg;
            }
            else if(params.msg!=''){
                errorMsg = params.msg;
            }
            $.ajaxSetup({async : b});
        };


        var controlGroup = el.parents(".form-validate");
        controlGroup.removeClass('has-error has-success');
        controlGroup.addClass(error==false?'has-success':'has-error');
        var form = el.parents("form");
        if(form){
            var fstyle = isformstyle(form);
            if(fstyle == 0){
                controlGroup.find("#valierr").remove();
                //el.after('<span class="help-block" id="valierr">' + errorMsg +'</span>');
                checkValidate(el , errorMsg);
            }
            else if(fstyle == 1){

            }
            else if (fstyle == 2){
                controlGroup.find("#valierr").remove();
               // el.parent().after('<span class="help-block" id="valierr">' + errorMsg +'</span>');
                checkValidate(el  , errorMsg);
            }
        };//end !form
        return !error;
    };

    //表单验证方法
    var validationForm = function(obj) {
           
        //1.丢失焦点事件
        $(obj).find('input, textarea').each(function(){
            var el = $(this);
            el.on('blur',function(){ // 失去焦点时
                valid = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' ');
                if (valid){
                    validateField(this, valid);
                }
            });
        });

        //2.如是文件选择则要处理onchange事件
        $(obj).find("input[type='file']").each(function(){
           var el = $(this);
            el.on('change',function(){ //
                valid = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' ');
                if (valid){
                    validateField(this, valid);
                }
            }); 
        });

        //3.设置必填的标志*号
        if (globalOptions.reqmark==true){
            if(fform_style==0){
                $(obj).find(".form-group>label").each(function(){
                    var el=$(this);
                    var controlGroup = el.parents();
                    controlGroup.removeClass('has-error has-success');
                    controlGroup.find("#autoreqmark").remove();
                    el.after('<span id="autoreqmark" style="color:#FF9966"> *</span>')
                });
            }
            else if(fform_style==1){

            }   
            else if(fform_style==2){

                $(obj).find('input, textarea').each(function(){
                    var el = $(this);
                    var controlGroup = el.parents('.form-validate');
                    controlGroup.removeClass('has-error has-success');
                    controlGroup.find("#valierr").remove();
                  
                    valid = (el.attr('check-type')==undefined)?null:el.attr('check-type').split(' ');
                    if (valid){
                        if ($.inArray('required',valid)>=0){
                            //el.parent().after('<span class="help-block" id="valierr" style="color:#FF9966">*</span>');
                        	  checkValidate(el , "*");
                        }
                    };
                }); 
            };
        };//end showrequired

    };
}(window.jQuery);

/**
 * 创建validate
 */
function checkValidate(el , errorMsg){
	 var cityObj = $(el);
		var cityOffset = cityObj.offset();
		var leftLength = cityOffset.left;
		var topLength = cityOffset.top;
		if (isBrowserVersonTop()) {  //判断是否需要处理兼容模式
			leftLength = leftLength + xScrollLength;
			topLength = topLength + yScrollLength;
		}
		leftLength = leftLength+ cityObj.outerWidth() + 10 ;
		topLength  = topLength + 10;
		var sss = "<div tabindex=\"-1\" id=\"valierr\" class=\"\" style=\"position:absolute;left:" + leftLength + "px;top:" + topLength + "px;diaplay:black;color:rgb(0,0,0);border-color:rgb(204,153,51); blackgroud-color:rgb(255,255,204)\">"
		+ "<div class=\"col-sm-1\" align=\"center\" style=\"color:red\">" + errorMsg + "</div>"
		+ "<div class=\"tooltip-arrow-outer\"></div>"
		+ "<div class=\"tooltip-arrow\"></div>" + "</div>";
		//.appendTo("body");
		
		el.after(sss);
}
var regexEnum = 
{
	integeZero:/^\d+$/,            //正整数 + 0
	intege:/^[-+]?[1-9][0-9]*$/,					//整数
	positivIntege:/^[1-9][0-9]*$/,					//正整数
	negativeIntege:/^-[1-9]\d*$/,					//负整数
	num:/^([+-]?)\d*\.?\d+$/,		//数字
	positivNum:/^[1-9]\d*|0$/,					//正数（正整数 + 0）
	negativeNum:/^-[1-9]\d*|0$/,					//负数（负整数 + 0）
	decmal:/^([+-]?)\d*\.\d+$/,			//浮点数
	positivDecmal:/^[1-9]\d*.\d*|0.d*[1-9]\d*$/,	//正浮点数
	negativeDecmal:/^-([1-9]\d*.\d*|0.\d*[1-9]\d*)$/,//负浮点数
	decmal3:/^-?([1-9]\d*.\d*|0.\d*[1-9]\d*|0?.0+|0)$/,//浮点数
	decmal4:/^[1-9]\d*.\d*|0.\d*[1-9]\d*|0?.0+|0$/,//非负浮点数（正浮点数 + 0）
	decmal5:/^(-([1-9]\d*.\d*|0.\d*[1-9]\d*))|0?.0+|0$/,//非正浮点数（负浮点数 + 0）
	email:/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/, //邮件		
			//url:/^http[s]?:\/\/([\w-]+\.)+[\w-]+([\w-./?%&=]*)?$/,
	chinese:/^[\u4E00-\u9FA5\uF900-\uFA2D]+$/,					//仅中文
	ascii:/^[\x00-\xFF]+$/,				//仅ACSII字符
	zipcode:/^[1-9]\d{5}$/	,					//邮编
	mobile:/^13[0-9]{9}|15[012356789][0-9]{8}|18[0256789][0-9]{8}|147[0-9]{8}$/,///^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/				//手机
	ip4:/^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/,	//ip地址
	notempty:/^\S+$/,						//非空
	picture:/(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/,	//图片
	rar:/(.*)\.(rar|zip|7zip|tgz)$/,								//压缩文件
	date:/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/,					//日期
	QQ:/^[1-9]\d{4,10}$/,				//QQ号码
	tel:/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/,	//电话号码的函数(包括验证国内区号,国际区号,分机号)
	username:/^\w+$/,						//用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	letter_intege:/^[a-zA-Z0-9]*$/,        //数字+ 字母
	letter_and_intege:/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/,
	letter:/[A-Za-z]+$/,					//字母
	letter_u:/^[A-Z]+$/,					//大写字母
	letter_l:/^[a-z]+$/,					//小写字母
	idcard:/^[1-9]([0-9]{14}|[0-9]{17})$/,	//身份证
    numberstr:/^\d+$/    //数字
 


};

var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}; 
	
	/* 密码由字母和数字组成，至少6位 */
	var safePassword = function (value) {
		return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
	};
	/**
	 * 身份证
	 */
	var idCardTee = function (value) {
		if (value.length == 18 && 18 != value.length) return false;
		var number = value.toLowerCase();
		var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
		var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
		if (re == null || a.indexOf(re[1]) < 0) return false;
		if (re[2].length == 9) {
		    number = number.substr(0, 6) + '19' + number.substr(6);
		    d = ['19' + re[4], re[5], re[6]].join('-');
		} else d = [re[9], re[10], re[11]].join('-');
		if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
		for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
		return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
	};
	/***
	 * 日期类型
	 */
	/*var isDateTime = function (format, reObj) {
		format = format || 'yyyy-MM-dd';
		var input = this, o = {}, d = new Date();
		var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
		var len = f1.length, len1 = f3.length;
		if (len != f2.length || len1 != f4.length) return false;
		for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
		for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
		o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
		o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
		o.dd = s(o.dd, o.d, d.getDate(), 31);
		o.hh = s(o.hh, o.h, d.getHours(), 24);
		o.mm = s(o.mm, o.m, d.getMinutes());
		o.ss = s(o.ss, o.s, d.getSeconds());
		o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
		if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
		if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
		d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
		var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
		return reVal && reObj ? d : reVal;
		function s(s1, s2, s3, s4, s5) {
		    s4 = s4 || 60, s5 = s5 || 2;
		    var reVal = s3;
		    if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
		    if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
		    return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
		}
	};*/

/*	function isCardID(sId){ 
		var iSum=0 ;
		var info="" ;
		if(!/^\d{17}(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误"; 
		sId=sId.replace(/x$/i,"a"); 
		if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法"; 
		sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
		var d=new Date(sBirthday.replace(/-/g,"/")) ;
		if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法"; 
		for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
		if(iSum%11!=1) return "你输入的身份证号非法"; 
		return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女") 
	} */

	//短时间，形如 (13:04:06)
	function isTimeTee(str)
	{
		var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
		if (a == null) {return false}
		if (a[1]>24 || a[3]>60 || a[4]>60)
		{
			return false;
		}
		return true;
	}

	//短日期，形如 (2003-12-05)
	function isDateTee(str)
	{
		var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
		if(r==null)return false; 
		var d= new Date(r[1], r[3]-1, r[4]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
	}

	//长时间，形如 (2003-12-05 13:04:06)
	function isDateTimeTee(str)
	{
		var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
		var r = str.match(reg); 
		if(r==null) return false; 
		var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
		return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
	}