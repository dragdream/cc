(function(){

	//格式化数字
	window.xFormatNumber=function(number,pattern){
		
		var tokenArr = new Array();
		tokenArr[0] = '';
		tokenArr[1] = '';
		tokenArr[2] = '';
		for(var i=0,j=0;i<pattern.length;i++){
			if(pattern.charAt(i)=='#' || pattern.charAt(i)=='0'){
				tokenArr[j]+=pattern.charAt(i);
			}else if(pattern.charAt(i)==','){
				j=1;
			}else if(pattern.charAt(i)=='.'){
				j=2;
			}
		}
		
		var exp = '';
		var replace = "";
		if(tokenArr[1]==''){//#或者#.###
			exp+='(-?\\d+)';
			replace+="$1";
		}else{//#,###或者#,###.###
			exp+='(-?\\d+)(\\d{'+tokenArr[1].length+'})';
			replace+="$1,$2";
		}
		
		var sNumber;
		var left='';
		var right='';
		if(tokenArr[2]!=''){
			sNumber = number.toFixed(tokenArr[2].length);
			left = analyzeLeft(sNumber);
			right = analyzeRight(sNumber);
		}else{
			sNumber = parseInt(number)+"";
			left = sNumber;
		}
		

		var regExp = new RegExp(exp,"gi");
		var regExp = /(-?\d+)(\d{3})/;
		var cycle = 0;
		while(regExp.test(left) && cycle<32){
			if(tokenArr[1]==''){
				break;
			}
			left = left.replace(regExp,replace);
			cycle++;
		}
		
		
		//处理格式为0的小数位
		if(tokenArr[2]!='' && tokenArr[2].indexOf("0")!=-1){
			var n = right.length;
			right = "";
			for(var i=0;i<n;i++){
				right+="0";
			}
		}
		
		right=(right==""?"":"."+right);
		var v = left+right;
		if(v=="NaN"){
			v = "";
		}
		return v;
		
	}
	
	//还原格式化后的数字
	window.xParseNumber=function(number){
		var sNumber = ""+number;
		sNumber = sNumber.replace(/,/g,"");
		if(sNumber==""){
			return 0;
		}
		if(isNaN(Number(sNumber))){
			return 0;
		}
		
		return sNumber;
	}
	
	//格式化金钱
	window.xFormatMoney=function(currencyDigits) {
		currencyDigits = xParseNumber(currencyDigits.toString());
		//判断是否为整钱
		var sp = currencyDigits.split(".");
		if(sp.length==2 && (sp[1]=="0" || sp[1]=="00" || sp[1]=="000")){
			currencyDigits = sp[0];
		}
		
		// Constants:
		var MAXIMUM_NUMBER = 99999999999.99;
		// Predefine the radix characters and currency symbols for output:
		var CN_ZERO = "零";
		var CN_ONE = "壹";
		var CN_TWO = "贰";
		var CN_THREE = "叁";
		var CN_FOUR = "肆";
		var CN_FIVE = "伍";
		var CN_SIX = "陆";
		var CN_SEVEN = "柒";
		var CN_EIGHT = "捌";
		var CN_NINE = "玖";
		var CN_TEN = "拾";
		var CN_HUNDRED = "佰";
		var CN_THOUSAND = "仟";
		var CN_TEN_THOUSAND = "万";
		var CN_HUNDRED_MILLION = "亿";
		var CN_SYMBOL = "";
		var CN_DOLLAR = "元";
		var CN_TEN_CENT = "角";
		var CN_CENT = "分";
		var CN_INTEGER = "整";

		// Variables:
		var integral; // Represent integral part of digit number.
		var decimal; // Represent decimal part of digit number.
		var outputCharacters; // The output result.
		var parts;
		var digits, radices, bigRadices, decimals;
		var zeroCount;
		var i, p, d;
		var quotient, modulus;

		// Validate input string:
		currencyDigits = currencyDigits.toString();
		if (currencyDigits == "") {
		  //alert("Empty input!");
		  return "";
		}
		if (currencyDigits.match(/[^,.\d]/) != null) {
		  //alert("Invalid characters in the input string!");
		  return "";
		}
		if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		  //alert("Illegal format of digit number!");
		  return "";
		}

		// Normalize the format of input digits:
		currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
		currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
		// Assert the number is not greater than the maximum number.
		if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		  alert("Too large a number to convert!");
		  return "";
		}

		// Process the coversion from currency digits to characters:
		// Separate integral and decimal parts before processing coversion:
		parts = currencyDigits.split(".");
		if (parts.length > 1) {
		  integral = parts[0];
		  decimal = parts[1];
		  // Cut down redundant decimal digits that are after the second.
		  decimal = decimal.substr(0, 2);
		}
		else {
		  integral = parts[0];
		  decimal = "";
		}
		// Prepare the characters corresponding to the digits:
		digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
		radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
		bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
		decimals = new Array(CN_TEN_CENT, CN_CENT);
		// Start processing:
		outputCharacters = "";
		// Process integral part if it is larger than 0:
		if (Number(integral) > 0) {
		  zeroCount = 0;
		  for (i = 0; i < integral.length; i++) {
		   p = integral.length - i - 1;
		   d = integral.substr(i, 1);
		   quotient = p / 4;
		   modulus = p % 4;
		   if (d == "0") {
			zeroCount++;
		   }
		   else {
			if (zeroCount > 0)
			{
			 outputCharacters += digits[0];
			}
			zeroCount = 0;
			outputCharacters += digits[Number(d)] + radices[modulus];
		   }
		   if (modulus == 0 && zeroCount < 4) {
			outputCharacters += bigRadices[quotient];
		   }
		  }
		  outputCharacters += CN_DOLLAR;
		}
		// Process decimal part if there is:
		if (decimal != "") {
		  for (i = 0; i < decimal.length; i++) {
		   d = decimal.substr(i, 1);
		   if (d != "0") {
			outputCharacters += digits[Number(d)] + decimals[i];
		   }
		  }
		}
		// Confirm and return the final output string:
		if (outputCharacters == "") {
		  outputCharacters = CN_ZERO + CN_DOLLAR;
		}
		if (decimal == "") {
		  outputCharacters += CN_INTEGER;
		}
		outputCharacters = CN_SYMBOL + outputCharacters;
		return outputCharacters;
	}

	function analyzeLeft(sNumber){
		if(sNumber.indexOf('.')==-1){
			return sNumber;
		}
		var sp = sNumber.split('.');
		return sp[0];
	}
	
	function analyzeRight(sNumber){
		if(sNumber.indexOf('.')==-1){
			return "";
		}
		var sp = sNumber.split('.');
		if(sp.length==2){
			return sp[1];
		}
		return "";
	}
	
	window.focusRMB=function(obj){
		var ovalue = obj.getAttribute("ovalue");
		ovalue = Number(ovalue);
		obj.value = ovalue;
	}

	window.blurRMB=function(obj){
		var ovalue = obj.getAttribute("ovalue");
		var value = obj.value;
		var rmb = xFormatMoney(Number(value));
		if(rmb!=''){
			ovalue = value;
			obj.setAttribute("ovalue",ovalue);
		}
		obj.value = rmb;
	}
	
	window.blurRequired=function(obj){
		if(obj.value==""){
			alert("["+obj.title+"]不能为空");
			obj.focus();
		}
	}
	
	window.blurFormatNumber=function(obj,format){
		var val = obj.value;
		var n = window.xFormatNumber(Number(val),format);
		if(isNaN(window.xParseNumber(n)) || val==""){
			obj.value = "";
			obj.ovalue= "";
		}else{
			obj.value = n;
			obj.ovalue= val;
		}
	}
	
	window.focusFormatNumber=function(obj){
		var val = obj.value;
		var oval = window.xParseNumber(val);
		if(isNaN(oval)){
			obj.ovalue = "";
			obj.value = "";
		}else{
			obj.ovalue = oval;
			obj.value = oval;
		}
//		var e =window.event.srcElement;   
//	    var r =e.createTextRange();   
//	    r.moveStart('character',e.value.length);
//	    r.collapse(true);   
//	    r.select();
	}
	
	
	window.initialize=function(){
		//初始化计算控件
		xCalculating();
		//定时循环遍历所有列表控件，进行内部计算
		xlistCalculating();
		//初始化select下拉选项框
		xSelectRelating();
		//初始化区域联动控件
		xAddress();
	}
	
	//初始化区域联动控件
	window.xAddress=function(){	
		var addressCtrls=$("select[xtype='xaddress']");
		var atypeArr=['A','B','C','D','E','F'];	
		for(var i=0;i<atypeArr.length;i++){//遍历分组
			var province = "";
			var city = "";
			var district = "";
			var provinceValue = "";
			var cityValue = "";
			var districtValue = "";
			for(var j=0;j<addressCtrls.length;j++){//遍历区域联动控件
				if($(addressCtrls[j]).attr("agroup")==atypeArr[i]){						
					if($(addressCtrls[j]).attr("atype")=="province"){	
						province=$(addressCtrls[j]).attr("id");
						provinceValue=$(addressCtrls[j]).attr("ovalue");
					}
					if($(addressCtrls[j]).attr("atype")=="city"){
						city=$(addressCtrls[j]).attr("id");
						cityValue=$(addressCtrls[j]).attr("ovalue");
					}
					if($(addressCtrls[j]).attr("atype")=="district"){
						district=$(addressCtrls[j]).attr("id");
						districtValue=$(addressCtrls[j]).attr("ovalue");
					}	
				}	
			}
                //alert(provinceValue+" "+cityValue+" "+districtValue);
				if(province!="" && city!="" && district!=""){
					addressInit(province,city,district,provinceValue,cityValue,districtValue);//级联效果   
				}else if(province!="" && city!=""){
					addressInit(province,city,"",provinceValue,cityValue,districtValue);//级联效果   
				}else{
					addressInit(province,"","",provinceValue,cityValue,districtValue);//级联效果   
				}			
		}
	}
	
	
	window.xCalculating=function(){
		var calculateCtrls = $("input[formula][writable]");
		calculateCtrls.each(function(i,obj){
			var calCtrl = $(obj);
			var model = eval("("+calCtrl.attr("model")+")");
			var formula = model.formula;
			
			calculaing(calCtrl);
//			var exp = /{[0-9 a-z A-Z \u4e00-\u9fa5]+}/gi;
//			var arr = formula.match(exp);//获取匹配数组
//			if(arr!=undefined && arr!=null){
//				for(var i=0;i<arr.length;i++){
//					var title = arr[i].replace("{","").replace("}","");
//					var target = findTargetByTitle(title);
//					if(target.length!=0){//如果存在目标
//						calculaing(calCtrl);
//					}
//				}
//			}else{
//				calculaing(calCtrl);
//			}
		});
		setTimeout(xCalculating,1000);
	}
	
	//[{'title':'品名','width':'100','sum':'0','formula':'','type':'2','model':''},{'title':'型号','width':'40','sum':'0','formula':'','type':'3','model':'E300,S350,R200'},{'title':'数量','width':'40','sum':'0','formula':'','type':'1','model':''},{'title':'单价','width':'40','sum':'0','formula':'','type':'1','model':''},{'title':'合计','width':'40','sum':'1','formula':'数量*单价','type':'1','model':''},{'title':'备注','width':'40','sum':'0','formula':'','type':'1','model':''}]
	window.xlistCalculating=function(){
		
		$("table[xtype='xlist']").each(function(i,obj){
			var model = eval("("+obj.getAttribute("model")+")");
			var id = obj.getAttribute("id").split("_")[1];
			for(var i=0;i<model.length;i++){
				var modelItem = model[i];
				//处理有公式的字段值
				if(modelItem.formula!=""){
					//遍历主体数据tr
					var trs = $("#xlist_tbody_"+id+" tr").each(function(i1,obj1){
						var tds = $(obj1).find("td");
						xlistFormulaRenderDatas(model,modelItem,tds);
					});
				}
				//处理有合计的字段值
				if(modelItem.sum!="0"){
					xlistSumRenderDatas(i+1,id);
				}
			}
		});
		
		setTimeout(xlistCalculating,1000);
	}
	
	/**
	 * 处理列表控件公式
	 */
	window.xlistFormulaRenderDatas=function(model,modelItem,tds){
		var maxBit=0;
		var formula = modelItem.formula;
		var exp = /{[0-9 a-z A-Z \u4e00-\u9fa5]+}/gi;
		var arr = formula.match(exp);//获取匹配数组
		if(arr==null || arr==undefined){
			return;
		}
		for(var i=0;i<arr.length;i++){
			var fieldName = arr[i].replace("{","").replace("}","");
			for(var j=0;j<model.length;j++){
				if(model[j].title==fieldName){
					formula = formula.replace(arr[i],xParseNumber($(tds[j]).children(":first")[0].value));
					var sp=(arr[i],xParseNumber($(tds[j]).children(":first")[0].value)+"").split(".");
					if(sp.length==2&&maxBit<sp[1].length){
						maxBit=sp[1].length;
					}
					
					break;
				}
			}
		}
		//alert(formula);
		//alert(maxBit);
		for(var j=0;j<model.length;j++){
			if(model[j].title==modelItem.title){
				try{
					$(tds[j]).children(":first")[0].value = eval("("+formula+")").toFixed(maxBit);
				}catch(e){}
				break;
			}
		}
	}
	
	/**
	 * 处理列表控件合计
	 */
	window.xlistSumRenderDatas=function(colIndex,itemId){
		 var val = 0;
		 var maxBit = 0;//小数点最大位数
		 //先叠加指定列数据
		 var tmp = 0;
		 var trs = $("#xlist_tbody_"+itemId+" tr").each(function(i,obj){
			 tmp = Number(xParseNumber(xGetListCellData(itemId,i+1,colIndex)));
			 var sp = (tmp+"").split(".");
			 if(sp.length==2 && maxBit<sp[1].length){
				 maxBit = sp[1].length;
			 }
			 val+= tmp;
		 });
		// alert("val："+val);
		 //alert("四舍五入后："+val.toFixed(2));
		 var sumCell = $("#xlist_tfoot_"+itemId+" tr:first td[index="+colIndex+"]:first");
		 sumCell.html("合计："+val.toFixed(maxBit));
	}
	 
	 /**
	  * 获取列表控件指定单元格数据
	  */
	 window.xGetListCellData=function(itemId,rowIndex,colIndex){
		 var cell = $("#xlist_tbody_"+itemId+" tr[index="+rowIndex+"]:first td[index="+colIndex+"]:first");
		   //处理单行输入框
			var dom = cell.children("input[type='text']:first");
			if(dom.length!=0){
				return dom[0].value;
			}
			
			//处理多行文本框
			var dom = cell.children("textarea:first");
			if(dom.length!=0){
				return dom[0].value;
			}
			
			//处理下拉菜单
			var dom = cell.children("select:first");
			if(dom.length!=0){
				return dom[0].value;
			}
			
			//处理单选框
			var dom = cell.children("input[type='radio']");
			if(dom.length!=0){
				var val = "";
				dom.each(function(i,obj){
					if(obj.checked){
						val = obj.value;
					}
				});
				return val;
			}
			
			//处理多选框
			var dom = cell.children("input[type='checkbox']");
			if(dom.length!=0){
				var val = "";
				dom.each(function(i,obj){
					if(obj.checked){
						val += obj.value+",";
					}
				});
				if(val!=""){
					val = val.substring(0,val.length-1);
				}
				return val;
			}
			
			//默认选择
			var val = cell.html();
		 return val;
	 }
	 
	 /**
	  * 列表控件合计
	  * itemName：列表控件名称
	  * colName：列表控件列名称
	  */
	 window.xListColSum=function(itemName,colName){
		 var target = findTargetByTitle(itemName);
		 var itemId = target.attr("id").replace("DATA_","");
		 //获取列的索引位置
		 var colIndex = 0;
		 var model = eval("("+$("#XLIST_"+itemId).attr("model")+")");
		 for(var i=0;i<model.length;i++){
			 if(model[i].title==colName){
				 colIndex = i+1;
				 break;
			 }
		 }
		 
		 //先叠加指定列数据
		 var val = 0;
		 var trs = $("#xlist_tbody_"+itemId+" tr").each(function(i,obj){
			 val = accAdd(val,Number(xParseNumber(xGetListCellData(itemId,i+1,colIndex))));
		 });
		 
		 return val;
	 }
	 
	 
	 // 两个浮点数求和  
	    function accAdd(num1,num2){  
	       var r1,r2,m;  
	       try{  
	           r1 = num1.toString().split('.')[1].length;  
	       }catch(e){  
	           r1 = 0;  
	       }  
	       try{  
	           r2=num2.toString().split(".")[1].length;  
	       }catch(e){  
	           r2=0;  
	       }  
	       m=Math.pow(10,Math.max(r1,r2));  
	       // return (num1*m+num2*m)/m;  
	       return Math.round(num1*m+num2*m)/m;  
	    }  
	      
	    // 两个浮点数相减  
	    function accSub(num1,num2){  
	       var r1,r2,m;  
	       try{  
	           r1 = num1.toString().split('.')[1].length;  
	       }catch(e){  
	           r1 = 0;  
	       }  
	       try{  
	           r2=num2.toString().split(".")[1].length;  
	       }catch(e){  
	           r2=0;  
	       }  
	       m=Math.pow(10,Math.max(r1,r2));  
	       n=(r1>=r2)?r1:r2;  
	       return (Math.round(num1*m-num2*m)/m).toFixed(n);  
	    }  
	    
	    // 两数相除  
	    function accDiv(num1,num2){  
	       var t1,t2,r1,r2;  
	       try{  
	           t1 = num1.toString().split('.')[1].length;  
	       }catch(e){  
	           t1 = 0;  
	       }  
	       try{  
	           t2=num2.toString().split(".")[1].length;  
	       }catch(e){  
	           t2=0;  
	       }  
	       r1=Number(num1.toString().replace(".",""));  
	       r2=Number(num2.toString().replace(".",""));  
	       return (r1/r2)*Math.pow(10,t2-t1);  
	    }  
	      
	    function accMul(num1,num2){  
	       var m=0,s1=num1.toString(),s2=num2.toString();   
		    try{m+=s1.split(".")[1].length}catch(e){};  
		    try{m+=s2.split(".")[1].length}catch(e){};  
		    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);  
	    }  

	window.findTargetByTitle=function(title){
		return $("[title='"+title+"']:first");
	}

	window.calculaing=function(target){
		var model = eval("("+target.attr("model")+")");
		var formula = model.formula;
		var sp = formula.split("`");
		formula = sp.join("'");
		var exp = /{[0-9 a-z A-Z \u4e00-\u9fa5]+}/gi;
		var arr = formula.match(exp);//获取匹配数组
		if(arr!=undefined && arr!=null){
			for(var i=0;i<arr.length;i++){
				var ctrl = findTargetByTitle(arr[i].replace("{","").replace("}",""));
				var val = ctrl.attr("value");
				//判断是否为日期格式
				var tmp = strDate2Date(val);
				if(tmp==undefined){//非日期格式
					if(isNaN(Number(val))){
						//将逗号去掉，再判断是否为数字格式
						if(!val){
							val = "";
						}
						val = val.replace(/,/gi,"");
						if(isNaN(Number(val))){
							val = 0;
						}
					}else if(val==""){
						val = 0;
					}
				}
				formula = formula.replace(arr[i],val);
			}
		}
		var precision = model.precision;
		var valuetype = model.valuetype;
		var format = model.format;
		
		var val = "";
		try{
			val = eval(formula);
		}catch(e){}
		if(!isNaN(val)){
			if(format=='0'){//无格式
				var precision = parseInt(precision);//精度
				var valSp = (val+"").split(".");//
				//alert("長度"+valSp.length);
				if(valSp.length==2){
					if(valSp[1].length<=precision){
						var zero = "";
						for(var i=0;i<precision-valSp[1].length;i++){
							zero+="0";
						}
						val = valSp[0]+"."+valSp[1]+zero;
					}else{
						var sub = valSp[1].substring(0,precision);
						if(sub.length<=precision){
							var zero = "";
							for(var i=0;i<precision-sub.length;i++){
								zero+="0";
							}
							sub+=zero;
						}
						val = valSp[0]+"."+sub;
					}
				}else{
					var zero = "";
					for(var i=0;i<precision;i++){
						zero+="0";
					}
					val =valSp[0]+"."+zero;
				}
			}else{//四舍五入
				if(val){
					val = val.toFixed(parseInt(precision));
				}
			}
			
			if(valuetype=='1'){//整型
				val = parseInt(val);
			}
		}
		
		
		if(!isNaN(val)){
			target.attr("value",val);
		}else{
			if(val && val.indexOf("元")!=-1){
				target.attr("value",val);
			}else{
				target.attr("value","");
			}
		}
	}
	
	window.xRMB=function(val){
		return xFormatMoney(val);
	}
	
	/**
	 * 增加一行
	 */
	window.addRow=function(itemId,datas){
		if(!datas){
			datas = {};
		}
		var tb = $("#XLIST_"+itemId);
		var tbody = $("#xlist_tbody_"+itemId);
		var oper = tbody.attr("oper");
		var model = eval("("+tb.attr("model")+")");
		var width = tbody.attr("width");
		var header = tbody.attr("header");
		var dataStyle = tbody.attr("dataStyle");
		var ctrlModel = eval("("+tb.attr("ctrlModel")+")");
		var rows = tbody.find("tr").length;
		var html = "<tr index=\""+(rows+1)+"\" bisKey=\""+(!datas["bisKey"]?"":datas["bisKey"])+"\">";
		for(var i=0;i<model.length;i++){
			var data = model[i];
			var writable = "0";
			var required = "0";
			var hidden = "0";
			var style=data.style;
			var ctrlModelItemObj = {};
			for(var j=0;j<ctrlModel.length;j++){
				if(ctrlModel[j].title==model[i].title){
					ctrlModelItemObj = ctrlModel[j];
					writable = ctrlModel[j].writable;
					required = ctrlModel[j].required;
					hidden = ctrlModel[j].hidden;
					break;
				}
			}
			if(style!=""&&style!=null&&style!="undefined"){
				if(hidden=="1"){
					html+="<td index=\""+(i+1)+"\" hidden "+(required=="0"?"":"required")+"  style=\""+style+"\">";
				}else{
					html+="<td index=\""+(i+1)+"\" "+(required=="0"?"":"required")+"   style=\""+style+"\"  >";
				}
			}else{
				if(hidden=="1"){
					html+="<td index=\""+(i+1)+"\" hidden "+(required=="0"?"":"required")+"  style=\""+dataStyle+"\">";
				}else{
					html+="<td index=\""+(i+1)+"\" "+(required=="0"?"":"required")+"   style=\""+dataStyle+"\"  >";
				}
			}
			
			
			html+=renderColumnByType(data,writable,required,dataStyle,datas,ctrlModelItemObj,itemId);
			html+="</td>";
		}
		
		if(oper==""){
			html+="<td oper=\"oper\" style=\""+dataStyle+"\">";
			html+="<input type=\"button\" value=\"删除\" onclick=\"deleteRow(this)\" />";
			html+="</td>";
		}
		html+="</tr>";
		tbody.append($(html));
	}
	 
	 function renderColumnByType(model,writable,required,dataStyle,datas,ctrlModelItemObj,itemId){
		 var type = model.type;
		 var valueModel = model.model;
		 var data = datas[model.title];
		 if(!data){
			 data = "";
		 }
		 var tbody = $("#xlist_tbody_"+itemId);
		 var eventStr = "";
		 var eventType = "";
		 var eventScript = "";
		 if(ctrlModelItemObj && ctrlModelItemObj!=null){
			 eventType = ctrlModelItemObj.eventType;
			 eventScript = ctrlModelItemObj.eventScript;
		 }
		 
		 if(eventType=="1"){
				eventStr = "onClick=\""+eventScript+"\"";
			}else if(eventType=="2"){
				eventStr = "onFocus=\""+eventScript+"\"";
			}else if(eventType=="3"){
				eventStr = "onBlur=\""+eventScript+"\"";
			}else if(eventType=="4"){
				eventStr = "onChange=\""+eventScript+"\"";
			}else if(eventType=="5"){
				eventStr = "onKeyDown=\""+eventScript+"\"";
			}else if(eventType=="6"){
				eventStr = "onKeyUp=\""+eventScript+"\"";
			}else if(eventType=="7"){
				eventStr = "onKeyPress=\""+eventScript+"\"";
			}else if(eventType=="8"){
				eventStr = "onMouseDown=\""+eventScript+"\"";
			}else if(eventType=="9"){
				eventStr = "onMouseUp=\""+eventScript+"\"";
			}
		 
		 if(type=="1"){//单行输入框
			 return "<input "+eventStr+" "+(writable=="0"?"readonly class='readonly'":"")+" value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
		 }else if(type=="2"){//多行输入框
			 return "<textarea "+eventStr+" "+(writable=="0"?"readonly class='readonly'":"")+" style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+">"+(data?data:valueModel)+"</textarea>";
		 }else if(type=="3"){//下拉菜单
			 var html = "<select "+eventStr+" "+(writable=="0"?"disabled class='readonly'":"")+" style='width:"+model.width+"' value=\""+(data?data:"")+"\">";
		     if(valueModel!=""){
		    	 var sp = valueModel.split(",");
		    	 for(var i=0;i<sp.length;i++){
		    		 if(data==sp[i]){
		    			 html+="<option selected value='"+sp[i]+"'>"+sp[i]+"</option>";
		    		 }else{
		    			 html+="<option value='"+sp[i]+"'>"+sp[i]+"</option>";
		    		 }
		    		
		    	 }
		     }
		     html+="</select>";
			 return html;
		 }else if(type=="4"){//单选框
			 var html = "";
		     if(valueModel!=""){
		    	 var sp = valueModel.split(",");
		    	 var rand = new Date().getTime();
		    	 for(var i=0;i<sp.length;i++){
		    		 if(data==sp[i]){
		    			 html+="<input "+eventStr+" checked "+(writable=="0"?"disabled":"")+" type='radio' name='rdo"+rand+"' value='"+sp[i]+"'>"+sp[i];
		    		 }else{
		    			 html+="<input "+eventStr+" "+(writable=="0"?"disabled":"")+" type='radio' name='rdo"+rand+"' value='"+sp[i]+"'>"+sp[i];
		    		 }
		    	 }
		     }
			 return html;
		 }else if(type=="5"){//复选框
			 var html = "";
		     if(valueModel!=""){
		    	 var sp = valueModel.split(",");
		    	 var datasp = data.split(",");
		    	 for(var i=0;i<sp.length;i++){
		    		 var exists = false;
		    		 for(var j=0;j<datasp.length;j++){
		    			 if(datasp[j]==sp[i]){
		    				 exists = true;
		    				 break;
		    			 }
		    		 }
		    		 if(exists){
		    			 html+="<input "+eventStr+" checked "+(writable=="0"?"disabled":"")+" type='checkbox' value='"+sp[i]+"'>"+sp[i];
		    		 }else{
		    			 html+="<input "+eventStr+" "+(writable=="0"?"disabled":"")+" type='checkbox' value='"+sp[i]+"'>"+sp[i];
		    		 }
		    		 
		    	 }
		     }else if(type=="6"){//序号
		    	 
		     }
			 return html;
		 }else if(type=="6"){//序号
	    	 var html = tbody.children().length+1;
	    	 return html;
	     }else if(type=="7"){//时间控件
	    	 var focus = "onfocus=\"WdatePicker({isShowClear:false,readOnly:true})\"";
	    	 if(valueModel!=""){
	    		 focus = "onfocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'"+valueModel+"'})\"";
	    	 }
	    	 var html = "<input "+eventStr+" "+(writable=="0"?"readonly class='readonly Wdate'":"class='Wdate'")+" value='' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly Wdate' readonly")+" "+focus+"/>";
	    	 return html;
	     }else if(type=="8"){//数据选择 
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input    valueModel='"+valueModel+"'  "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:"")+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input "+eventStr+"  onclick=\"xListItemSelectData(this,"+itemId+")\"   valueModel='"+valueModel+"'  "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:"")+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	    	 
	     }else if(type=="9"){//单选人员
	    	 var rand=parseInt(Math.random()*100000000000);
	    	 var xListItemId = "XLIST_ITEM_ID_"+rand;
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input   "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input   id=\""+xListItemId+"\"  "+eventStr+"  onclick=\"selectSingleUser(['"+xListItemId+"','"+xListItemId+"'])\"    "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	     }else if(type=="10"){//多选人员
	    	 var rand=parseInt(Math.random()*100000000000);
	    	 var xListItemId = "XLIST_ITEM_ID_"+rand;
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input   "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input   id=\""+xListItemId+"\"  "+eventStr+"  onclick=\"selectUser(['"+xListItemId+"','"+xListItemId+"'])\"    "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	     }else if(type=="11"){//部门单选
	    	 var rand=parseInt(Math.random()*100000000000);
	    	 var xListItemId = "XLIST_ITEM_ID_"+rand;
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input   "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input   id=\""+xListItemId+"\"  "+eventStr+"  onclick=\"selectSingleDept(['"+xListItemId+"','"+xListItemId+"'])\"    "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	     }else if(type=="12"){//部门多选
	    	 var rand=parseInt(Math.random()*100000000000);
	    	 var xListItemId = "XLIST_ITEM_ID_"+rand;
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input   "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input   id=\""+xListItemId+"\"  "+eventStr+"  onclick=\"selectDept(['"+xListItemId+"','"+xListItemId+"'])\"    "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	     }else if(type=="13"){//角色单选
	    	 var rand=parseInt(Math.random()*100000000000);
	    	 var xListItemId = "XLIST_ITEM_ID_"+rand;
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input   "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input   id=\""+xListItemId+"\"  "+eventStr+"  onclick=\"selectSingleRole(['"+xListItemId+"','"+xListItemId+"'])\"    "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	     }else if(type=="14"){//角色多选
	    	 var rand=parseInt(Math.random()*100000000000);
	    	 var xListItemId = "XLIST_ITEM_ID_"+rand;
	    	 if(writable=="0"){//不可写也不能选
	    		 return "<input   "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }else{
	    		 return "<input   id=\""+xListItemId+"\"  "+eventStr+"  onclick=\"selectRole(['"+xListItemId+"','"+xListItemId+"'])\"    "+(writable=="0"?"readonly class='readonly'":"")+"  value='"+(data?data:valueModel)+"' type='text' style='width:"+model.width+"' "+(model.formula==""?"":"class='readonly' readonly")+"/>";
	    	 }
	     }
	 }
	
	 

	 
	 
	 
	 
	/**
	 * 删除一行
	 */
	window.deleteRow=function(row){
		var tbody = $(row).parent().parent().parent();
		var tb = $(row).parent().parent().parent().parent();
		var model = eval("("+tb.attr("model")+")");
		$(row).parent().parent().remove();
		for(var i=0;i<model.length;i++){
			if(model[i].type=="6"){
				var child = tbody.children();
				for(var j=0;j<child.length;j++){
					$($(child[j]).children()[i]).html(j+1);
				}
			}
		}
	}
	 
	/**
	 * 保存前进行列表数据组合
	 */
	window.listViewPreSaving=function(form){
		var tbs = $(form).find("table[class='xlist_tb']");
		for(var idx=0;idx<tbs.length;idx++){
			var tb = tbs[idx];
			//获取ID
			var id = $(tb).attr("id").split("_")[1];
			//获取模型
			var model = eval("("+$(tb).attr("model")+")");
			//获取tbody
			var tbody = $("#xlist_tbody_"+id);
			var trs = tbody.children();
			var dataArray = [];
			for(var i=0;i<trs.length;i++){//逐行扫描数据
				var tds = $(trs[i]).children("[oper!='oper']");
				var data = {};
				data["bisKey"] = $(trs[i]).attr("bisKey");
				for(var j=0;j<tds.length;j++){//逐列扫描
					var td = $(tds[j]);
					var title = model[j].title;
					//处理单行输入框
					var dom = td.children("input[type='text']:first");
					if(dom.length!=0){
						data[title] = dom[0].value;
						if(td.attr("required") && dom[0].value==""){
							dom.focus();
							alert("["+title+"]为必填项，不能为空");
							return false;
						}
						continue;
					}
					
					//处理多行文本框
					var dom = td.children("textarea:first");
					if(dom.length!=0){
						data[title] = dom[0].value;
						if(td.attr("required") && dom[0].value==""){
							dom.focus();
							alert("["+title+"]为必填项，不能为空");
							return false;
						}
						
						continue;
					}
					
					//处理下拉菜单
					var dom = td.children("select:first");
					if(dom.length!=0){
						data[title] = dom[0].value;
						if(td.attr("required") && dom[0].value==""){
							dom.focus();
							alert("["+title+"]为必填项，不能为空");
							return false;
						}
						continue;
					}
					
					//处理单选框
					var dom = td.children("input[type='radio']");
					if(dom.length!=0){
						data[title] = "";
						dom.each(function(i,obj){
							if(obj.checked){
								data[title] = obj.value;
							}
						});
						if(td.attr("required") && data[title].value==""){
							alert("["+title+"]为必填项，不能为空");
							return false;
						}
						continue;
					}
					
					//处理多选框
					var dom = td.children("input[type='checkbox']");
					if(dom.length!=0){
						data[title] = "";
						dom.each(function(i,obj){
							if(obj.checked){
								data[title] += obj.value+",";
							}
						});
						if(data[title]!=""){
							data[title] = data[title].substring(0,data[title].length-1);
						}
						if(td.attr("required") && data[title].value==""){
							alert("["+title+"]为必填项，不能为空");
							return false;
						}
						continue;
					}
					
					//默认选择
					data[title] = td.html();
					if(td.attr("required") && data[title].value==""){
						alert("["+title+"]为必填项，不能为空");
						return false;
					}
				}
				dataArray.push(data);
			}
			
			var dataString = tools.jsonArray2String(dataArray);
			if($("#DATA_"+id).length!=0){
				$("#DATA_"+id).attr("value",dataString);
			}
		}
		return true;
	}
	
	 /**
	  * 保存前校验
	  */
	window.ctrlValidate=function(form){
		var ctrls = $(form).find("[required=true]");
		for(var i=0;i<ctrls.length;i++){		
			var obj = $(ctrls[i]);
		        var type=obj.attr("xtype");
		        if(type=="xfeedback"){
		        	if(obj.val()==""){//先判断会签框中是否有内容
		        		if(checkFbRequired()==false){
			        		//alert("会签不能为空！");
			        		top.$.jBox.tip("["+obj.attr("title")+"]不能为空","error");
			        		return  false;
			        	}else{
			        		return true;
			        	}	
		        		
		        	}else{
		        		return true;
		        	}        	
		        	
		        }else{
		        	if(obj.val()==""){
						top.$.jBox.tip("["+obj.attr("title")+"]不能为空","error");
						obj.focus();
						return false;
					}	
		        	
		        }
		}
		return true;
	}
	  
	  window.docNumOpening = function(obj){
		  var json = tools.requestJsonRs(contextPath+"/docNumController/checkExistsDocNum.action?flowId="+flowId+"&runId="+runId);
		  if(json.rtData==true){//如果存在，则弹出修改文号界面
			  var url = contextPath+"/system/core/workflow/flowrun/prcs/docnumEdit.jsp?flowId="+flowId+"&runId="+runId;
			  top.bsWindow(url,"文号修改",{width:"300",height:"200",buttons:[{name:"确定",classStyle:"btn-alert-blue"}],submit:function(v,h){
				  var cw = $(h)[0].contentWindow;
				  var docNumStyle = cw.generate();
				  
				  if(v=="确定"){
					  if(docNumStyle!=false){
						  obj.value = docNumStyle;
						  return true;
					  }
					}
				  
				  
			  }});
		  }else{//不存在，则弹出文号生成界面
			  var url = contextPath+"/system/core/workflow/flowrun/prcs/docnum.jsp?flowId="+flowId+"&runId="+runId;
			  top.bsWindow(url,"文号生成",{width:"300",height:"200",buttons:[{name:"确定",classStyle:"btn-alert-blue"}],submit:function(v,h){
				  var cw = $(h)[0].contentWindow;
				  var docNumStyle = cw.generate();
				  
				  if(v=="确定"){
					  if(docNumStyle!=false){
						  obj.value = docNumStyle;
						  return true;
					  }
					}
				  
				  
			  }});
		  }
	  }
	  
	  window.optChanged=function(obj,itemId){
		  try{
			  var selected = $(obj).find("option:selected");
			  $("#EXTRA_"+itemId).attr("value",selected.attr("extValue"));
		  }catch(e){
			  
		  }
	  }
	  
	  window.selectDataSource=function(dfid,itemName){
		  var IM_OA;
		  try{
		      IM_OA = window.external.IM_OA;
		  }catch(e){}

		  if(window.showModelDialog || IM_OA){
			  dialog0(contextPath+"/system/core/workflow/flowrun/prcs/dataList.jsp?dfid="+dfid+"&itemName="+itemName,800,600);
		    }else{
		  	  openWindow(contextPath+"/system/core/workflow/flowrun/prcs/dataList.jsp?dfid="+dfid+"&itemName="+itemName,"选择数据", 800, 600);
		    }
	  }
	  
	//流程数据选择控件
	 window.selectFlowDataSource=function(obj){
		  var flowId=$(obj).attr("flowid");
		  var mappingstr=$(obj).attr("mappingstr");
		  var IM_OA;
		  try{
		      IM_OA = window.external.IM_OA;
		  }catch(e){}

		  if(window.showModelDialog || IM_OA){//IM_OA 是从精灵中打开的 ,浏览器没有IM_OA的全局变量    ie才有showModelDialog   
			  dialog0(contextPath+"/system/core/workflow/flowrun/prcs/flowDataList.jsp?flowId="+flowId+"&mappingstr="+mappingstr,800,600);
		   }else{
		  	  openWindow(contextPath+"/system/core/workflow/flowrun/prcs/flowDataList.jsp?flowId="+flowId+"&mappingstr="+mappingstr,"流程数据选择", 800, 600);
		   }
	  }
	  
	  
	 
	 window.selectFlowDataSourceXList=function(obj){
		  var flowId=$(obj).attr("flowid");
		  var mappingstr=$(obj).attr("mappingstr");
		  var itemId=$(obj).attr("itemid");
		  var IM_OA;
		  try{
		      IM_OA = window.external.IM_OA;
		  }catch(e){}

		  if(window.showModelDialog || IM_OA){//IM_OA 是从精灵中打开的 ,浏览器没有IM_OA的全局变量    ie才有showModelDialog   
			  dialog0(contextPath+"/system/core/workflow/flowrun/prcs/flowDataForXList.jsp?flowId="+flowId+"&mappingstr="+mappingstr+"&itemId="+itemId,800,600);
		   }else{
		  	  openWindow(contextPath+"/system/core/workflow/flowrun/prcs/flowDataForXList.jsp?flowId="+flowId+"&mappingstr="+mappingstr+"&itemId="+itemId,"流程数据选择", 800, 600);
		   }
	  }
	 
	  window.selectListDataSource = function(dfid,itemId){
		  var IM_OA;
		  try{
		      IM_OA = window.external.IM_OA;
		  }catch(e){}

		  if(window.showModelDialog || IM_OA){
			  dialog0(contextPath+"/system/core/workflow/flowrun/prcs/xlistCtrlDataList.jsp?dfid="+dfid+"&itemId="+itemId,800,600);
		    }else{
		  	  openWindow(contextPath+"/system/core/workflow/flowrun/prcs/xlistCtrlDataList.jsp?dfid="+dfid+"&itemId="+itemId,"选择数据", 800, 600);
		    }
	  }
	  
	  window.strDate2Date=function(d){
			var date;
			if(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/.test(d)){//匹配yyyy-MM-dd
				date = new Date(d.replace(/-/gi,"/"));
			}else if(/^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}$/.test(d)){//匹配yyyy-MM-dd HH:mm
				date = new Date(d.replace(/-/gi,"/"));
			}else if(/^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$/.test(d)){//匹配yyyy-MM-dd HH:mm:ss
				date = new Date(d.replace(/-/gi,"/"));
			}else if(/^[0-9]{4}-[0-9]{2}$/.test(d)){//匹配yyyy-MM
				date = new Date(d.replace(/-/gi,"/")+"/01");
			}else if(/^[0-9]{2}-[0-9]{2}-[0-9]{2}$/.test(d)){//匹配yy-MM-dd
				date = new Date("20"+d.replace(/-/gi,"/"));
			}else if(/^[0-9]{4}[0-9]{2}[0-9]{2}$/.test(d)){//匹配yyyyMMdd
				date = new Date(d.substring(0,4)+"/"+d.substring(4,6)+"/"+d.substring(6,8));
			}else if(/^[0-9]{2}-[0-9]{2} [0-9]{4}$/.test(d)){//匹配MM-dd yyyy
				date = new Date(d.substring(3,5)+"/"+d.substring(0,2)+"/"+d.substring(6,10));
			}else if(/^[0-9]{4}年[0-9]{2}月$/.test(d)){//匹配yyyy年MM月
				date = new Date(d.substring(0,4)+"/"+d.substring(5,7)+"/01");
			}else if(/^[0-9]{4}年[0-9]{2}月[0-9]{2}日$/.test(d)){//匹配yyyy年MM月dd日
				date = new Date(d.substring(0,4)+"/"+d.substring(5,7)+"/"+d.substring(8,10));
			}else if(/^[0-9]{4}年[0-9]{2}月[0-9]{2}日 [0-9]{2}:[0-9]{2}$/.test(d)){//匹配yyyy年MM月dd日 HH:mm
				date = new Date(d.substring(0,4)+"/"+d.substring(5,7)+"/"+d.substring(8,10)+" "+d.split(" ")[1]);
			}else if(/^[0-9]{2}月[0-9]{2}日$/.test(d)){//匹配MM月dd日
				date = new Date("1900/"+d.substring(0,2)+"/"+d.substring(3,5));
			}else if(/^[0-9]{4}\.[0-9]{2}$/.test(d)){//yyyy.MM
				date = new Date(d.replace(/\./gi,"/")+"/01");
			}else if(/^[0-9]{4}\.[0-9]{2}\.[0-9]{2}$/.test(d)){//yyyy.MM.dd
				date = new Date(d.replace(/\./gi,"/"));
			}else if(/^[0-9]{2}\.[0-9]{2}$/.test(d)){//MM.dd
				date = new Date("1900/"+d.replace(/\./gi,"/"));
			}else if(/^[0-9]{2}时$/.test(d)){//MM时
				date = new Date("1900/01/01 "+d.substring(0,2)+":00:00");
			}else if(/^[0-9]{2}时[0-9]{2}分$/.test(d)){//MM时mm分
				date = new Date("1900/01/01 "+d.substring(0,2)+":"+d.substring(3,5)+":00");
			}else if(/^[0-9]{2}时[0-9]{2}分[0-9]{2}秒$/.test(d)){//MM时mm分ss秒
				date = new Date("1900/01/01 "+d.substring(0,2)+":"+d.substring(3,5)+":"+d.substring(6,8));
			}else if(/^[0-9]{2}:[0-9]{2}$/.test(d)){//HH:mm
				date = new Date("1900/01/01 "+d.substring(0,2)+":"+d.substring(3,5)+":00");
			}else if(/^[0-9]{2}:[0-9]{2}:[0-9]{2}$/.test(d)){//HH:mm:ss
				date = new Date("1900/01/01 "+d.substring(0,2)+":"+d.substring(3,5)+":"+d.substring(6,8));
			}
			
			return date;
		}
	  

	  //xDeltaDays：差值天数
	  window.xDeltaDays = function(date1,date2,workhours){
		  var begintime_ms = strDate2Date(date1).getTime(); //begintime 为开始时间
		  var endtime_ms = strDate2Date(date2).getTime();   // endtime 为结束时间
		  var date3=endtime_ms-begintime_ms;  //时间差的毫秒数
		  
		  //计算出相差天数
		  var days=Math.floor(date3/(24*3600*1000));
		  //计算出小时数
		  var leave1=date3%(24*3600*1000);//计算天数后剩余的毫秒数
		  var hours=Math.floor(leave1/(3600*1000));
		  
		  //默认一天12小时工作日
		  if(!workhours){
			  return days;
		  }else{
			  return days+Number(hours/workhours);
		  }
	  }
	  
	  //差值小时数
	  window.xDeltaHours = function(date1,date2){
		  return (strDate2Date(date2).getTime()-strDate2Date(date1).getTime())/(1000*60*60);
	  }
	  
	  //差值分钟数
	  window.xDeltaMinutes = function(date1,date2){
		  return (strDate2Date(date2).getTime()-strDate2Date(date1).getTime())/(1000*60);
	  }
	  
	  //全局的工作日缓存对象
	  var GLOBAL_XWORKDAYS_CACHE = {};
	//xWorkDays：差值天数
	  window.xWorkDays = function(date1,date2){
		  if(date1==0 || date2==0){
			  return 0;
		  }
		  
		  if(date1.indexOf(" ")==-1){
			  date1 = date1+" 00:00";
		  }
		  
		  if(date2.indexOf(" ")==-1){
			  date2 = date2+" 23:59";
		  }
		  
		  if(GLOBAL_XWORKDAYS_CACHE[date1+date2]){
			  return GLOBAL_XWORKDAYS_CACHE[date1+date2];
		  }
		  
		  var json = tools.requestJsonRs("/TeeAttendDutyController/getTimeDiffByDutyConfig.action",{startTimeStr:date1,endTimeStr:date2});
		  if(json.rtState){
			  GLOBAL_XWORKDAYS_CACHE[date1+date2] = json.rtData;
			  return json.rtData;
		  }
		  
	  }
	  
	//xDeltaDays：差值天数
	  window.xDeltaDate = function(date1,date2){
		  var begintime_ms = strDate2Date(date1).getTime(); //begintime 为开始时间
		  var endtime_ms = strDate2Date(date2).getTime();   // endtime 为结束时间
		  var date3=endtime_ms-begintime_ms;  //时间差的毫秒数
		  
		  //计算出相差天数
		  var days=Math.floor(date3/(24*3600*1000));
		  //计算出小时数
		  var leave1=date3%(24*3600*1000);//计算天数后剩余的毫秒数
		  var hours=Math.floor(leave1/(3600*1000));
		  //计算相差分钟数
		  var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
		  var minutes=Math.floor(leave2/(60*1000));
		  //计算相差秒数
		  var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
		  var seconds=Math.round(leave3/1000);
		  
		  return days+"天"+hours+"小时"+minutes+"分"+seconds+"秒";
	  }
	  
	  //移动签章盖章操作
	  window.addMobileSeal=function(ctrlName){
		  var IM_OA;
		  try{
		      IM_OA = window.external.IM_OA;
		  }catch(e){}

		  if(window.showModelDialog || IM_OA){
			  dialog0(contextPath+"/system/core/workflow/flowrun/prcs/mobileSeals.jsp?itemId="+ctrlName,800,600);
		    }else{
		  	  openWindow(contextPath+"/system/core/workflow/flowrun/prcs/mobileSeals.jsp?itemId="+ctrlName,"手机签章", 800, 600);
		    }
	  }
	  
	  /**
	   * 签章格式事例   data;image/png;base64,签章数据,表单数据MD5加密,左边距,上边据
	   */
	  
	  window.doAddMobileSeal=function(ctrlName,sealData){
		  var targetObject = $("#MOBILE_SEAL_IMG_"+ctrlName);
		  if(targetObject.length==0){
			  targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+ctrlName+"\" target=\""+ctrlName+"\" />").appendTo($("#MOBILE_SIGN_POS_"+ctrlName));
		  }
		  targetObject.css({opacity:1,"position":"absolute",left:0,top:0}).show();
		  try{
			  targetObject.draggable({
				  stop: function() {
					  var target = $("#"+$(this).attr("target"));
					  var sp = target.val().split(",");
					  sp[3] = $(this).position().left;
					  sp[4] = $(this).position().top;
					  target.val(sp.join(","));
				  }
			  });
			  
			//加入下拉菜单
			  targetObject.TeeMenu({
				  menuData:[{name:"删除",action:function(itemId,obj){
					  if(window.confirm("是否删除该签章？")){
						  $("#"+itemId).val("");
						  obj.remove();
					  }
					} ,extData:[ctrlName,targetObject]}],eventPosition:true
			  });
		  }catch(e){
			  
		  }
		  
		  var ctrl = $("#"+ctrlName);
		  var fieldValid = ctrl.attr("validField");
		  var sp = fieldValid.split(",");
		  var datas = [];
		  if(fieldValid!=""){
			  for(var i=0;i<sp.length;i++){
				  datas.push(findTargetByTitle(sp[i]).val());
			  }
		  }
		  var md5Code = MD5(datas.join(""));
		  ctrl.val(sealData+","+md5Code+",0,0");
		  
		  targetObject.attr("src",sealData);
	  }
	  
	  //初始化子项关联下拉选择框级联映射
	  window.xSelectRelating = function(){
		  var selects = $("select[rel]");
		  selects.each(function(i,obj){
			  if($(obj).attr("rel")==""){
				  return;
			  }
			  
			  //将内部的所有options进行转换
			  var opts = $(obj).find("option");
			  var trueValue = $(obj).attr("ovalue").split("|")[0];
			  
			  var optsArray = [];
			  opts.each(function(j,obj1){
//				  var sp = $(obj1).val().split("|");
//				  $(obj1).attr("pv",sp[1]);
//				  $(obj1).html(sp[0]);
//				  $(obj1).val(sp[0]);
//				  $(obj1).removeAttr("selected");
				  optsArray.push($(obj1).val());
				  $(obj1).remove();
			  });
			  $(obj).data("optsArray",optsArray);
			  
			  var rel = $(obj).attr("rel");
			  var target = findTargetByTitle(rel);
			  var childs = target.data("childs");
			  if(!childs){
				  childs = [];
			  }
			  childs.push($(obj).attr("title"));
			  target.data("childs",childs);
			  target.change(xSelectChange);
		  });
		  
		  //初始化数据
		  selects = $("select[rel]");
		  selects.each(function(i,obj){
			 $(obj).trigger("change");
		  });
	  }
	  
	  function xSelectChange(){
		  childTrigger($(this));
	  }
	  
	  function childTrigger(target){
		  var childs = target.data("childs");
		  if(!childs || childs==null){
			  return;
		  }
		  var value = target.val();
		  for(var i=0;i<childs.length;i++){//遍历子集
			  var child = findTargetByTitle(childs[i]);
			  child.html("");
			  var ovalue = child.attr("ovalue");
			  var optsArray = child.data("optsArray");
			  for(var j=0;j<optsArray.length;j++){
				  var sp = optsArray[j].split("|");
				  if(sp[1]==value){
					  child.append("<option "+(ovalue==sp[0]?"selected":"")+" value=\""+sp[0]+"\">"+sp[0]+"</option>");
				  }
			  }
			  childTrigger(child);
		  }
	  }
	  
})();



function WorkFlowUtil(runId,frpSid,flowId,goBack){
	this.runId = runId;
	this.frpSid = frpSid;
	this.flowId = flowId;
	this.goBack = goBack;
	
	this.turnNext = function(prcsEvent,callback) {
		var url = "/flowRun/preTurnHandlerData.action";
		var para = "runId="+this.runId+"&frpSid="+this.frpSid+"&flowId="+this.flowId;
		if(prcsEvent){
			para+="&prcsEvent="+encodeURI(prcsEvent);
		}
		var json = tools.requestJsonRs(url,{runId:this.runId,frpSid:this.frpSid,flowId:this.flowId});
		if(json.rtState){
			var opCode = json.rtData.opCode;
			if(opCode=="1"){
				var url = "/system/core/workflow/flowrun/prcs/turn/turnFixedNext.jsp?"+para;
				bsWindow(url,"转交",{width:"800",height:"350",submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="确定"){
						callback(cw.wfCluster.commit());
					}else{
						return true;
					}
				},buttons:
					[
			         {name:"确定",classStyle:"btn-alert-blue"},
				 	 {name:"关闭",classStyle:"btn-alert-gray"}
					 ]});
			}else if(opCode=="2"){
				var url = "/system/core/workflow/flowrun/prcs/turn/turnFreeNext.jsp?"+para;
				bsWindow(url,"转交",{width:"800",height:"300",submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="确定"){
						callback(cw.turnNext());
					}else{
						return true;
					}
				},buttons:
					[
			         {name:"确定",classStyle:"btn-alert-blue"},
				 	 {name:"关闭",classStyle:"btn-alert-gray"}
					 ]});
			}else{
				callback(true);
			}
		}else{
			callback(false,json.rtMsg);
		}
	};
	
	//回退上一步骤
    this.backTo = function(callback){
        var para = "runId="+this.runId+"&frpSid="+this.frpSid+"&flowId="+this.flowId;
        
        if(this.goBack=="0"){
            alert("当前步骤不允许回退");
        }else if(this.goBack=="1"){//回退上一步
            var url = "/system/core/workflow/flowrun/prcs/backTo.jsp?"+para;
            bsWindow(url,"回退",{width:"400",height:"250",submit:function(v,h){
                var cw = h[0].contentWindow;
                if(v=="确定"){
                    callback(cw.commit(),"回退成功");
                }
            }});
        }else if(this.goBack=="2"){//回退之前步骤
            var url = "/system/core/workflow/flowrun/prcs/backToOther.jsp?"+para;
            bsWindow(url,"回退",{width:"400",height:"250",submit:function(v,h){
                var cw = h[0].contentWindow;
                if(v=="确定"){
                    callback(cw.commit(),"回退成功");
                }
            }});
        }else if(this.goBack=="3"){//回退指定步骤
            var url = "/system/core/workflow/flowrun/prcs/backToFixed.jsp?"+para;
            bsWindow(url,"回退",{width:"400",height:"250",submit:function(v,h){
                var cw = h[0].contentWindow;
                if(v=="确定"){
                    callback(cw.commit(),"回退成功");
                }
            }});
        }
    }
	
	//创建流程
	this.createNewWork = function(params){
		var runJson = tools.requestJsonRs(contextPath + "/flowRun/createNewWork.action",params);
		return runJson;
	}
	
	
	 
	
}


