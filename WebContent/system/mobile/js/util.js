 /**  
  * 对Date的扩展，将 Date 转化为指定格式的String  
  * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符  
  * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)  
  * eg:  
  * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423  
  * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04  
  * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04  
  * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04  
  * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18  
  */  
 Date.prototype.pattern=function(fmt) {   
     var o = {   
     "M+" : this.getMonth()+1, //月份   
     "d+" : this.getDate(), //日   
     "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时   
     "H+" : this.getHours(), //小时   
     "m+" : this.getMinutes(), //分   
     "s+" : this.getSeconds(), //秒   
     "q+" : Math.floor((this.getMonth()+3)/3), //季度   
     "S" : this.getMilliseconds() //毫秒   
     };   
     var week = {   
     "0" : "/u65e5",   
     "1" : "/u4e00",   
     "2" : "/u4e8c",   
     "3" : "/u4e09",   
     "4" : "/u56db",   
     "5" : "/u4e94",   
     "6" : "/u516d"  
     };   
     if(/(y+)/.test(fmt)){   
         fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
     }   
     if(/(E+)/.test(fmt)){   
         fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);   
     }   
     for(var k in o){   
         if(new RegExp("("+ k +")").test(fmt)){   
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
         }   
     }   
     return fmt;   
} 
 
 
/**
 * 转换日期字符串
 * @param time 时间戳  --时间为空则为系统当前时间
 * @param format  日期转换格式
 */
function getFormatDateStr(time , format){
	var timeStr = "";
	if(time){
		var timeDate =new Date(parseInt(time,10));
	    timeStr = new Date(timeDate).pattern(format);
	}else{
		timeStr = new Date(new Date()).pattern(format);
	}
    return timeStr;
}

/**
 * 转换日期字符串
 * @param time 时间戳
 * @param format  日期转换格式
 */
function getFormatDateTimeStr(time , format){
	var timeStr = "";
	if(time){
		var timeDate =new Date(parseInt(time,10));
	    timeStr = new Date(timeDate).pattern(format);
	}else{
		//timeStr = new Date(new Date()).pattern(format);
	}
    return timeStr;
}


/**
 * 把Json数据绑定到控件

  json : json对象
  filters : 过滤不需要绑定的控件
  optType: 操作类型  1-添加文字 其他重新赋值
 */
function bindJsonObj2Cntrl(json, filters , optType) {
  for (var property in json) {
    if (filters) {
      if (Object.isString(filters) && filters.indexOf(",") > 0) {
        var filterArray = filters.split(",");
        if (!filterArray.contains(property)) {
          continue;
        }
      }else if (Object.isString(filters)) {
        var ancestor = $(filters);
        var elem = $(property);
        if (ancestor && elem && !Element.descendantOf(elem, ancestor)) {
          continue;
        }
      }else if (Object.isArray(filters)) {
        if (!filters.contains(property)) {
          continue;
        }
      }else if (Object.isElement(filters)) {
        var elem = $(property);
        if (elem && !Element.descendantOf(elem, ancestor)) {
          continue;
        }
      }
    }
    var value = json[property];  
    var cntrlArray = document.getElementsByName(property);    
    var cntrlCnt = cntrlArray.length;
    if (!cntrlArray || cntrlCnt < 1) {
      if (document.getElementById(property)) {
        cntrlArray = [document.getElementById(property)];
        cntrlCnt = 1;
      }else {
        continue;
      }
    }
    if (cntrlCnt == 1) {
      var cntrl = cntrlArray[0];
      if (cntrl.tagName.toLowerCase() == "input" && cntrl.type.toLowerCase() == "checkbox") {
    	  
        if (cntrl.value == value) {
          cntrl.checked = true;
        }else {
          cntrl.checked = false;
        }
      }else if (cntrl.tagName.toLowerCase() == "td"
          || cntrl.tagName.toLowerCase() == "div"
          || cntrl.tagName.toLowerCase() == "span") {
       
        if(optType && optType == 1){
        	 cntrl.innerHTML = cntrl.innerHTML + value;
        }else{
        	 cntrl.innerHTML = value;
        }
      } else if (cntrl.tagName.toLowerCase() == 'select') {
        for (var i = 0; i < cntrl.childNodes.length; i++) {
          if (cntrl.childNodes[i].value == value) {
            cntrl.childNodes[i].selected = "selected";
            break;
          }
        }
      }else {
        cntrl.value = value==null?"":value;
      }
    }else {
      for (var i = 0; i < cntrlCnt; i++) {
        var cntrl = cntrlArray[i];
        if (cntrl.value == value) {
          cntrl.checked = true;
        }else {
          cntrl.checked = false;
        }
      }
    }
  }
}




/**
 * 根据主类编号  获取子集代码列表
 * 
 * @param codeNo 系统代码编号  主类编码
 * @param codeSelectId 对象Id
 * @returns 返回人员数组 对象 [{codeNo:'' , codeName:''}]
 */
function getSysCodeByParentCodeNo(codeNo , codeSelectId ){
	var url =   contextPath + "/sysCode/getSysCodeByParentCodeNo.action";
	var para = {codeNo:codeNo};
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:para,
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
				if(codeSelectId && $("#" + codeSelectId)[0]){//存在此对象
					var options = "";
					for ( var i = 0; i < prcs.length; i++) {
						options = options + "<option value='"+prcs[i].codeNo+"'>" + prcs[i].codeName + "</option>";
					}
					$("#" + codeSelectId).append(options);
				}
				return prcs;
			}else{
				alert(json.rtMsg);
			}
		}
	});
	
}




/**
 * 表单内控件转换为POST请求的JSON格式
 * form : 表单对象
 */
function formToJson(form,all){
	var json = {};
	if(!all){
		$(form).find("input[name][disabled!=disabled][type!=checkbox][type!=radio]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("input[name][type=hidden][disabled!=disabled][type!=checkbox][type!=radio]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("textarea[name][disabled!=disabled]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("select[name][disabled!=disabled]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("input[name][type=checkbox][disabled!=disabled]").each(function(i,obj){
			if($(obj).attr("checked")){
				json[$(obj).attr("name")] = 1;
			}else{
				json[$(obj).attr("name")] = 0;
			}
		});
		$(form).find("input[name][type=radio][disabled!=disabled]:checked").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		
		$(form).find("input[name][type=password][disabled!=disabled]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
	}else{
		$(form).find("input[name][type!=checkbox][type!=radio]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("input[name][type=hidden][type!=checkbox][type!=radio]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("textarea[name]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("select[name]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("input[name][type=checkbox]").each(function(i,obj){
			if($(obj).attr("checked")){
				json[$(obj).attr("name")] = 1;
			}else{
				json[$(obj).attr("name")] = 0;
			}
		});
		$(form).find("input[name][type=radio]:checked").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		
		$(form).find("input[name][type=password]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
	}
	return json;
}

function jsonObj2String(json){
	var str = "{";
	var value;
	var exist = false;
	for(var key in json){
		exist = true;
		str+="\""+key+"\":";
		value = json[key];
		if(value instanceof Array){
			str+=this.jsonArray2String(value);
		}else if(typeof(value)=="number"){
			str+=value;
		}else if(typeof(value)=="string"){
			str+="\""+(value+"").replace(/[\"]/gi, "\\\"").replace(/[\r\n]/gi, "\\n").replace(/[\n]/gi, "\\n").replace(/[\r]/gi, "\\n") +"\"";
		}else{
			str+=this.jsonObj2String(value);
		}
		str+=",";
	}
	if(exist){
		str = str.substring(0,str.length-1);
	}
	str +="}";
	return str;
}