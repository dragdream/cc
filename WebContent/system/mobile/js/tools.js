var tools = {
requestJsonRs:function(url,param,async,callback){
	if(!param) param = {};
    var jsonObj = null;
	$.ajax({
		type:"post",
		dataType:"html",
		url:url,
		data:param,
		async:(async?async:false),
		success:function(data){
			try{
				jsonObj = eval("("+data+")");
			}catch(e){
				jsonObj = null;
			}
			
			
			if(callback){
				callback(jsonObj);
		    }
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			jsonObj = {rtState:false,rtMsg:"Ajax Request Error"};
		}
	});
	return jsonObj;
},
strToJson:function(str){  
    var json = eval('(' + str + ')');  
	    return json;  
},
formToJson:function(form,all){
	var json = {};
	if(!all){
		$(form).find("input[name][disabled!=disabled]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("input[name][type=hidden][disabled!=disabled]").each(function(i,obj){
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
		$(form).find("input[name]").each(function(i,obj){
			json[$(obj).attr("name")] = $(obj).val();
		});
		$(form).find("input[name][type=hidden]").each(function(i,obj){
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
},
findInSet:function(target,array1){
	var sp = (array1+"").split(",");
	for(var i=0;i<sp.length;i++){
		if((sp[i]+"")==(target+"")){
			return true;
		}
	}
	return false;
},
existChinese:function(str){
	for(var i=0;i<str.length;i++){
		if(str.charAt(i)>='\u4e00' && str.charAt(i)<='\u9fa5'){
			return true;
		}
	}
	return false;
},
parseJson2String:function(json){
	var str = "";
	if(json instanceof Array){
		str = this.jsonArray2String(json);
	}else{
		str = this.jsonObj2String(json);
	}
	return str;
},
jsonArray2String:function(json){
	var str = "[";
	for(var i=0;i<json.length;i++){
		if(typeof(json[i])=="string"){
			str+="\""+json[i]+"\"";
		}else if(typeof(json[i])=="number"){
			str+=json[i];
		}else if(json[i] instanceof Array){
			str+=this.jsonArray2String(json[i]);
		}else{
			str+=this.jsonObj2String(json[i]);
		}
		
		if(i!=json.length-1){
			str+=",";
		}
	}
	str += "]";
	return str;
},
jsonObj2String:function(json){
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
},
string2JsonObj:function (text){
	try{
		return eval("("+text+")");
	}catch(e){
		return {};
	}
},
getFileTypeImg:function(ext){
	return "<img align=\"absMiddle\" src=\""+systemImagePath+"/filetype/"+ext.toLowerCase()+".gif"+"\" onerror=\"this.src='"+systemImagePath+"/filetype/defaut.gif'\"/>";
},
getAttachElement:function(attachModel,opts){
	var opts = opts;
	if(!opts){
		opts = {};
	}
	var downloadFunc = this.download;
	var ext = attachModel.ext;
	var render = "<div>";
	render += this.getFileTypeImg(ext);
	render += "&nbsp;"+attachModel.fileName+"";
	render += "</div>";
	
	var attachElement = $(render);
	var priv = attachModel.priv;//权限值
	var menuData = new Array();
	
	if((priv & 1)==1){//阅读权限
		if(this.isImage(ext)){//如果是图片
			menuData.push({name:"预览",action:function(sid){
				//var url = contextPath +"/system/core/attachment/picExplore.jsp?id="+sid;
				top.$.picExplore({src:contextPath+"/attachmentController/downFile.action?id="+sid});
				//top.bsWindow(url,"在线预览",{buttons:[]});
			} ,extData:[attachModel.sid]});
		}else if(this.isOffice(ext)){//如果是office文档
			menuData.push({name:"查看",action:function(sid,fileName){
				var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+sid+"&attachmentName="+fileName+"&moudle=workFlow&op=7";
				openFullDialog(encodeURI(ntkoURL),"在线文档阅读");
			} ,extData:[attachModel.sid,attachModel.fileName]});
		}
	}
	
	if((priv & 2)==2){//下载权限
		menuData.push({name:"下载",action:function(sid){
			var url = contextPath +"/attachmentController/downFile.action?id="+sid;
			downloadFunc(url);
		} ,extData:[attachModel.sid]});
	}
	
	if((priv & 4)==4){//删除权限
		menuData.push({name:"删除",action:function(sid){
			if(!window.confirm("确定要删除附件["+attachModel.fileName+"]吗？")){
				return;
			}
			if(opts.deleteEvent){
				opts.deleteEvent(attachModel,opts.params);
				return;
			}
			var url = contextPath +"/attachmentController/deleteFile.action?attachIds="+sid;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				attachElement.remove();
			}else{
				alert(json.rtMsg);
			}
		} ,extData:[attachModel.sid]});
	}
	
	if((priv & 8)==8 && this.isOffice(ext)){//编辑权限
		menuData.push({name:"编辑",action:function(sid,fileName){
			var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+sid+"&attachmentName="+fileName+"&moudle=workFlow&op=4";
			openFullDialog(encodeURI(ntkoURL),"在线文档编辑");
		} ,extData:[attachModel.sid,attachModel.fileName]});
	}
	
	if((priv & 2)==2){//转储权限
		menuData.push({name:"转存",action:function(sid){
			openWindow(contextPath+"/system/core/base/saveFileTo/index.jsp?attachId="+sid,"附件转存",480,350);
		} ,extData:[attachModel.sid]});
	}
	
	attachElement.TeeMenu({menuData:menuData,eventPosition:false});
	return attachElement;
},
isImage:function(ext){
	ext = ext.toLowerCase();
	if(ext=="gif" || ext=="jpg" || ext=="png" || ext=="bmp"){
		return true;
	}
	return false;
},
isOffice:function(ext){
	ext = ext.toLowerCase();
	if(ext=="doc" || ext=="xls" || ext=="ppt" || ext=="docx"){
		return true;
	}
	return false;
},
download:function(url){
	if(!this._downloadFrame){
		this._downloadFrame = document.createElement("iframe");
		this._downloadFrame.style.display = "none";
		document.body.appendChild(this._downloadFrame);
	}
	this._downloadFrame.src = url;
},
/**
* 获取文件项元素
* @return
*/
getAttachElement:function(attachModel,opts){
	var opts = opts;
	if(!opts){
		opts = {};
	}
	var downloadFunc = this.download;
	var ext = attachModel.ext;
	var render = "<div>";
	render += this.getFileTypeImg(ext);
	render += "&nbsp;"+attachModel.fileName+"";
	render += "</div>";
	
	var attachElement = $(render);
	var priv = attachModel.priv;//权限值
	var menuData = new Array();
	
	if((priv & 1)==1){//阅读权限
		if(this.isImage(ext)){//如果是图片
			menuData.push({name:"预览",action:function(sid){
				//var url = contextPath +"/system/core/attachment/picExplore.jsp?id="+sid;
				top.$.picExplore({src:contextPath+"/attachmentController/downFile.action?id="+sid});
				//top.bsWindow(url,"在线预览",{buttons:[]});
			} ,extData:[attachModel.sid]});
		}else if(this.isOffice(ext)){//如果是office文档
			menuData.push({name:"查看",action:function(sid,fileName){
				var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+sid+"&attachmentName="+fileName+"&moudle=workFlow&op=7";
				openFullDialog(encodeURI(ntkoURL),"在线文档阅读");
			} ,extData:[attachModel.sid,attachModel.fileName]});
		}
	}
	
	if((priv & 2)==2){//下载权限
		menuData.push({name:"下载",action:function(sid){
			var url = contextPath +"/attachmentController/downFile.action?id="+sid;
			downloadFunc(url);
		} ,extData:[attachModel.sid]});
	}
	
	if((priv & 4)==4){//删除权限
		menuData.push({name:"删除",action:function(sid){
			if(!window.confirm("确定要删除附件["+attachModel.fileName+"]吗？")){
				return;
			}
			if(opts.deleteEvent){
				opts.deleteEvent(attachModel,opts.params);
				return;
			}
			var url = contextPath +"/attachmentController/deleteFile.action?attachIds="+sid;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				attachElement.remove();
			}else{
				alert(json.rtMsg);
			}
		} ,extData:[attachModel.sid]});
	}
	
	if((priv & 8)==8 && this.isOffice(ext)){//编辑权限
		menuData.push({name:"编辑",action:function(sid,fileName){
			var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+sid+"&attachmentName="+fileName+"&moudle=workFlow&op=4";
			openFullDialog(encodeURI(ntkoURL),"在线文档编辑");
		} ,extData:[attachModel.sid,attachModel.fileName]});
	}
	
	if((priv & 2)==2){//转储权限
		menuData.push({name:"转存",action:function(sid){
			openWindow(contextPath+"/system/core/base/saveFileTo/index.jsp?attachId="+sid,"附件转存",480,350);
		} ,extData:[attachModel.sid]});
	}
	
	attachElement.TeeMenu({menuData:menuData,eventPosition:false});
	return attachElement;
}
};


function bindJsonObj2Cntrl(json, filters) {
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
	        cntrl.innerHTML = value;
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

